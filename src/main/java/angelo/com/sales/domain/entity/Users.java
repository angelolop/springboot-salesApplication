package angelo.com.sales.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_db")
public class Users {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotEmpty(message = "{field.login.necessary}")
   private String login;

   @NotEmpty(message = "{field.password.necessary}")
   private String password;

   @Column
   private Boolean admin;

}
