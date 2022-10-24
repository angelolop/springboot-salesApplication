package angelo.com.sales.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_db")
public class Client implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Column(length = 100)
   @NotEmpty(message = "{field.name.necessary}")
   private String name;

   @Column(length = 11)
   @NotEmpty(message = "{field.cpf.necessary}")
   @CPF(message = "{field.cpf.invalid}")
   private String cpf;

   @JsonIgnore
   @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
   private Set<Order> orders;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      Client client = (Client) o;
      return id != null && Objects.equals(id, client.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }
}
