package angelo.com.sales.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCredentialsDTO {

   private String login;
   private String password;

}
