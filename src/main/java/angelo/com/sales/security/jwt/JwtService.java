package angelo.com.sales.security.jwt;

import angelo.com.sales.domain.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

   @Value("${security.jwt.expiration}")
   private String expiration;

   @Value("${security.jwt.assignature-key}")
   private String assignatureKey;

   public String generatingToken(Users user){
      long expString = Long.parseLong(expiration);
      LocalDateTime dateHourExpiration = LocalDateTime.now().plusMinutes(expString);
      Instant instant = dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant();
      Date date = Date.from(instant);

      return Jwts
              .builder()
              .setSubject(user.getLogin())
              .setExpiration(date)
              .signWith(SignatureAlgorithm.HS512, assignatureKey)
              .compact();
   }

   private Claims getClaims(String token) throws ExpiredJwtException {
      return Jwts.parser()
              .setSigningKey(assignatureKey)
              .parseClaimsJws(token)
              .getBody();
   }

   public boolean validToken(String token) {
      try {
         Claims claims = getClaims(token);
         Date dateExpiration = claims.getExpiration();
         LocalDateTime date = dateExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
         return LocalDateTime.now().isAfter(date);
      } catch (Exception e) {
         return false;
      }
   }

   public String getUserLogin(String token) throws ExpiredJwtException {
      return getClaims(token).getSubject();
   }
}
