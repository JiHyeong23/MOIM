package MOIM.svr.security;

import MOIM.svr.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfig {
    private Environment env;
    private UserService userService;
    private JwtHelper jwtHelper;
    private AuthenticationManager authenticationManager;
    public WebConfig(Environment env, UserService userService, JwtHelper jwtHelper) {
        this.env = env;
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean //필터들을 관리한다 + 시큐리티 설정 등등
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        try {
            AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
            authenticationManagerBuilder.userDetailsService(userService); //userDetailsService를 스프링 시큐리티가 써야하해서 memberService 에서 상속받아서 만들어놨었다!!!
            authenticationManager = authenticationManagerBuilder.build();

            httpSecurity
                    .csrf().disable() //보안에서 가장 취약한 쿠키와 세션을 쓰지 않겟다
                    .authorizeHttpRequests() //이 밑에오는 url들은 허락해준다
                    .antMatchers(HttpMethod.GET, "/")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, "/")
                    .permitAll()
//                    .antMatchers("/**")
//                    .authenticated()
                    .and()
                    .addFilter(getAuthenticationFilter(httpSecurity.getSharedObject(AuthenticationConfiguration.class)));

            httpSecurity //h2때문에 화면상 안나오는 애들이 있을 수 있어서 추가
                    .headers().frameOptions().disable();

            httpSecurity
                    .authenticationManager(authenticationManager)
                    .sessionManagement() //세션에 대한 규칙 설정
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //안쓴다
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpSecurity.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(getAuthenticationManager(authenticationConfiguration), userService, jwtHelper, env);
        authenticationFilter.setAuthenticationManager(getAuthenticationManager(authenticationConfiguration));
        return authenticationFilter;
    }
}
