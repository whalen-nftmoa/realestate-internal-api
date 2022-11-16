package com.labshigh.realestate.internal.api.config;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

  private boolean enableSwagger = false;
  private String profile;

  public SwaggerConfig(@Value("${app.profile}") String profile) {

    if (profile != null) {
      if (profile.equalsIgnoreCase("dev") ||  profile.equalsIgnoreCase("local")) {
        enableSwagger = true;
      }
    }
  }

  @Bean
  public Docket api() {

    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("API")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.labshigh.realestate.internal.api"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo())
        .enable(enableSwagger);
  }

  private ApiInfo apiInfo() {

    return new ApiInfo(
        "NFT Real Estate Internal Api",
        "NFT Real Estate Internal Api",
        "1.0",
        "Terms of service",
        new Contact("Labshigh Platform Group", "https://www.labshigh.io", ""),
        "Labshigh License 1.0", "https://www.labshigh.io", Collections.emptyList());
  }
}