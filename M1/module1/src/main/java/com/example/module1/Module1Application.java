package com.example.module1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Module1Application {
	private final HelloService helloService;

    public Module1Application(HelloService helloService) {
        this.helloService = helloService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Module1Application.class, args);
		System.out.println("http://localhost:8080/hello");
		System.out.println("http://localhost:8080/hello?name=pradeep");
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false) String name) {
        return helloService.getMessage(name);
    }
}
