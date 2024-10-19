package com.example.porj.config.mongovalidationconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

//Below @Beans are Provided by the Spring Starter Validation
@Configuration
public class ValidationConfig {

    //This method will be trigger when passing data to the database, while processing the data it checks if the user pass
    //  any NULL values then it trigger a CONSTRAINT_VIOLATION_EXCEPTION
    //These two beans are compulsory, just adding @NotNull don't solve the problem
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(){
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }
}
