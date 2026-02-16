package com.example.module1;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

@Service
public class HelloService {
     public String getMessage(String name) {
        if (StringUtils.isBlank(name)) {
            return "Hello Guest from Service via DI!";
        }

        return "Hello " + name + " from Service via DI!";
    }
}
