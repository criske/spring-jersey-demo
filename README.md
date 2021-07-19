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


#### TODO:
Make _jax-rs_ decoupled from from String. This includes persistence, security, validation and dependency injection.

- DI (jsr-330 and jersey 2):
  Started working on a custom binder that auto-scann for components annotated with `@Named`. https://gist.github.com/criske/d97775733a84b9f6625efe55da1523af
  Usage:
  ```java
  package mypack;
  
  interface MyService {}
  
  @Named("myServiceA")
  class MyServiceA implements MyService{}
  
  @Named("myServiceB")
  class MyServiceB implements MyService{}
  ```
  ```java
  Resource config = new ResourceConfig();
  config.register(MyResource.class);
  NamedComponentsBinder.selfRegister(config, "myPack");
  ```
  Now MyService will injected into `MyResource`
  ```java
  @Path("/hello")
  class MyResource {
    
    @Inject
    @Named("myServiceA")
    MyService service;
  
  }
  ```

