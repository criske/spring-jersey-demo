package pcf.crskdev.jersey.demo.mvc.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pcf.crskdev.jersey.demo.common.enitities.Student;
import pcf.crskdev.jersey.demo.common.input.StudentInput;
import pcf.crskdev.jersey.demo.common.services.StudentService;

import java.security.Principal;

@RestController
@RequestMapping("/student")
public final class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public Student me(Principal principal) {
        return this.service.me(principal.getName());
    }

    @PutMapping
    public Student update(
        Principal principal,
        @RequestBody StudentInput input
    ) {
        return this.service.update(principal.getName(), input);
    }

    @PostMapping
    public Student create(@RequestBody StudentInput input) {
        return this.service.register(input);
    }

}
