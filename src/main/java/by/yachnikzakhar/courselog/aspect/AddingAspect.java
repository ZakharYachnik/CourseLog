package by.yachnikzakhar.courselog.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AddingAspect {

    @Before("execution(public void add(..))")
    public void addAspect() {
        System.out.println("Adding aspect is working");
    }
}
