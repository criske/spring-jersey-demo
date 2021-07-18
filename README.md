## Jax-Rs/Spring-Mvc common logic demo.

3 Modules.

- _common_: contains all business logic using only java ee specification annotations regarding injection, security,
  persistence and validation. This module is shared by jax-rs and mvc.
  
- _jax-rs_: jersey server launched via spring-boot (for now) along with jax-rs resources endpoints and
  exception mappers.
  
- _mvc_: spring-boot/spring-mvc containing controllers and exception handlers.

Root sources contains security (authentication) managed by spring-security and some implementations 
needed by common module:
- validation Aspect proxy - since @Validated flag is spring specific, common 
  module uses its annotation - @CommonValidated. This aspect searches for beans
  classes with @CommonValidated and then, within these, method parameters annotated with @Valid. On match, it
  applies validation using a Validator.
    
- common password encoder - adapter for Spring's PasswordEncoder.  