package angelo.com.sales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

   @Bean
   public Docket docket() {
      return new Docket(DocumentationType.SWAGGER_2)
              .useDefaultResponseMessages(false)
              .select()
              .apis(RequestHandlerSelectors.basePackage("angelo.com.sales.rest.controller"))
              .paths(PathSelectors.any())
              .build()
              .securityContexts(Arrays.asList(securityContext()))
              .securitySchemes(Arrays.asList(apiKey()))
              .apiInfo(apiInfo());
   }

   private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
              .title("Sales API")
              .description("Api sales project")
              .version("1.0.0")
              .contact(contact())
              .build();
   }


   private Contact contact() {
      return new Contact("Angelo Lopes"
                       , "https://www.linkedin.com/in/angelo-/"
                       ,"angelo.lop.alv@gmail.com");
   }

   private SecurityContext securityContext() {
      return SecurityContext.builder()
              .securityReferences(defaultAuth())
              .operationSelector(operationContext -> true).build();
   }

   public ApiKey apiKey() {
      return new ApiKey("JWT", "Authorization", "header");
   }

   private List<SecurityReference> defaultAuth() {
      AuthorizationScope authorizationScope = new AuthorizationScope("global", "AccessEverything");
      AuthorizationScope[] scopes = new AuthorizationScope[1];
      scopes[0] = authorizationScope;
      SecurityReference reference = new SecurityReference("JWT", scopes);
      List<SecurityReference> auths = new ArrayList<>();
      auths.add(reference);
      return auths;
   }
}
