package MOIM.svr.security;

import MOIM.svr.user.UserService;
import MOIM.svr.user.userDto.UserLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserService userService;
    private JwtHelper jwtHelper;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JwtHelper jwtHelper, Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtHelper = jwtHelper;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDto userLoginDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getEmail(),
                            userLoginDto.getPw(),
                            new ArrayList<>()
                    )
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        String username = ((User)authResult.getPrincipal()).getUsername();
        MOIM.svr.user.User user = userService.findByEmail(username);
        String accessToken = jwtHelper.createAccessToken(username);
        String refreshToken = jwtHelper.createRefreshToken(username);

        SecretKey privateKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("token.secret")));
        String token = Jwts.builder()
                .setSubject(user.getEmail()) //이토큰에 대한 주 내용
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(Objects.requireNonNull(env.getProperty("token.expirationTime")))))
                .signWith(privateKey, SignatureAlgorithm.HS512) //암호화 한번 더
                .compact();
        @Getter
        @Setter
        class ResponseToken {
            private String accessToken;
            private String refreshToken;
        }

        ResponseToken responseToken = new ResponseToken();
        responseToken.setAccessToken(accessToken);
        responseToken.setRefreshToken(refreshToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); //크로스오리진
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseToken));
    }
}
