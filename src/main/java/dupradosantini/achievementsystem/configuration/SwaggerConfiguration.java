package dupradosantini.achievementsystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

//@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
   /* @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        Contact contact = new Contact("Luis Eduardo", "https://github.com/dupradosantini",
                "dupradosantini@opus-software.com.br");
        return new ApiInfo(
                "Achievement System",
                "Achievement System CRUD",
                "1.0",
                "tos",
                contact,
                "license",
                "licenseurl",
                new ArrayList<>());
    }*/
}
