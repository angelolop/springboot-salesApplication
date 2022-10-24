package angelo.com.sales.rest.controller;

import angelo.com.sales.domain.entity.Users;
import angelo.com.sales.exception.InvalidPasswordException;
import angelo.com.sales.rest.dto.GetCredentialsDTO;
import angelo.com.sales.rest.dto.TokenDTO;
import angelo.com.sales.security.jwt.JwtService;
import angelo.com.sales.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

   private final UserServiceImpl userService;
   private final PasswordEncoder encoder;

   private final JwtService jwtService;

   @PostMapping
   @ResponseStatus(CREATED)
   public Users saveUser(@RequestBody @Valid Users user) {
      String encriptedPassword = encoder.encode(user.getPassword());
      user.setPassword(encriptedPassword);
      return userService.saveUser(user);
   }

   @PostMapping("/auth")
   public TokenDTO authentication(@RequestBody GetCredentialsDTO credentials) {
      try {
         Users user = Users.builder()
                         .login(credentials.getLogin())
                         .password(credentials.getPassword())
                         .build();
         userService.authenticating(user);
         String token = jwtService.generatingToken(user);
         return new TokenDTO(user.getLogin(), token);
      } catch (UsernameNotFoundException | InvalidPasswordException e) {
         throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
      }
   }

}
