package angelo.com.sales.security.jwt;

import angelo.com.sales.service.impl.UserServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

   private JwtService jwtService;
   private UserServiceImpl userService;

   public JwtAuthFilter(JwtService jwtService, UserServiceImpl userService) {
      this.jwtService = jwtService;
      this.userService = userService;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws ServletException, IOException {
      String authorization = request.getHeader("Authorization");

      if(authorization != null && authorization.startsWith("Bearer")) {
         String token = authorization.split(" ")[1];
         if(jwtService.validToken(token)){
            UserDetails userDetails = userService.loadUserByUsername(jwtService.getUserLogin(token));
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                                                userDetails,
                                       null,
                                                userDetails.getAuthorities());

            user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(user);
         }
      }
      filterChain.doFilter(request, response);
   }
}
