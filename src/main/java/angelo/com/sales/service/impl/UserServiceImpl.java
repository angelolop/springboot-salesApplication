package angelo.com.sales.service.impl;

import angelo.com.sales.domain.entity.Users;
import angelo.com.sales.domain.repository.UserRepository;
import angelo.com.sales.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {


   @Autowired
   private UserRepository userRepository;

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   public Users saveUser(Users user) {
      return userRepository.save(user);
   }

   public UserDetails authenticating(Users user) {
      UserDetails userDetails = loadUserByUsername(user.getLogin());
      boolean passwordsEquals = passwordEncoder().matches(user.getPassword(), userDetails.getPassword());
      if (passwordsEquals) {
         return userDetails;
      }
      throw new InvalidPasswordException("Invalid password");
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Users user = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

      return User.
              builder()
              .username(user.getLogin())
              .password(user.getPassword())
              .roles("USER").build();
   }
}
