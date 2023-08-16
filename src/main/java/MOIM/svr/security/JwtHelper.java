package MOIM.svr.security;

import MOIM.svr.user.User;
import MOIM.svr.user.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@Component
public class JwtHelper {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30 * 24;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private Environment env;
    private UserRepository userRepository;


    public String getEmailFromJwtToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("jwt.key.secret")));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("jwt.key.secret")));

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String createAccessToken(String username) {
        User user = userRepository.findByEmail(username);
        Map<String, String> map = new HashMap<>();
        map.put("type", "access");
        map.put("name", user.getUserNickname());
        SecretKey accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("jwt.key.secret")));

        return Jwts.builder()
                .setClaims(map)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(accessKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String username) {
        User user = userRepository.findByEmail(username);
        SecretKey accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("jwt.key.secret")));

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(accessKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
