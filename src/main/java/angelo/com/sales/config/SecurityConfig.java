package angelo.com.sales.config;

import angelo.com.sales.security.jwt.JwtAuthFilter;
import angelo.com.sales.security.jwt.JwtService;
import angelo.com.sales.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig {

   @Autowired
   private UserServiceImpl userService;

   @Autowired
   private JwtService jwtService;

   @Bean
   public OncePerRequestFilter jwtFilter() {
      return new JwtAuthFilter(jwtService, userService);
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf()
              .disable()
              .authorizeRequests()
              .antMatchers("/clients/**")
              .hasRole("USER")
              .antMatchers("/products/**")
              .hasRole("ADMIN")
              .antMatchers("/orders/**")
              .hasRole("USER")
              .antMatchers(HttpMethod.POST,"/users/**")
              .permitAll()
              .antMatchers("/v2/api-docs/**",
                      "configuration/ui/",
                      "/swagger-resources/**",
                      "/configuration/security/",
                      "/swagger-ui.html/",
                      "/swagger-ui/**",
                      "/swagger-ui",
                      "/webjars/**")
              .permitAll()
              .anyRequest().authenticated()
              .and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

      return http.build();
   }
}
