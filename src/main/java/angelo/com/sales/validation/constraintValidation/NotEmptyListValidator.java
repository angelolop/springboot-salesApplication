package angelo.com.sales.validation.constraintValidation;

import angelo.com.sales.rest.dto.PostOrderItemDTO;
import angelo.com.sales.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List<PostOrderItemDTO>> {

   @Override
   public boolean isValid(List<PostOrderItemDTO> postOrderItems, ConstraintValidatorContext constraintValidatorContext) {
      return postOrderItems != null && !postOrderItems.isEmpty();
   }


   @Override
   public void initialize(NotEmptyList constraintAnnotation) {
   }
}


