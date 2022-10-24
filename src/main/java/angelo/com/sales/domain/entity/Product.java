package angelo.com.sales.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_db")
public class Product implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotEmpty(message = "{field.description.necessary}")
   private String description;

   @NotNull(message = "{field.price.necessary}")
   private Double price;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      Product product = (Product) o;
      return id != null && Objects.equals(id, product.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }
}
