package angelo.com.sales.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ApiErrors {

   private Long timestamp;
   private List<String> errors;
   private String path;

   public ApiErrors (Long timestamp, List<String> errors, String path) {
      this.timestamp = timestamp;
      this.errors = errors;
      this.path = path;
   }

   public ApiErrors (Long timestamp, String errors, String path) {
      this.timestamp = timestamp;
      this.errors = Arrays.asList(errors);
      this.path = path;
   }
}
