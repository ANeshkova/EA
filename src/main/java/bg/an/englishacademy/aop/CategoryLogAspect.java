package bg.an.englishacademy.aop;

import bg.an.englishacademy.service.CategoryLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CategoryLogAspect {

    private final CategoryLogService categoryLogService;

    public CategoryLogAspect(CategoryLogService categoryLogService) {
        this.categoryLogService = categoryLogService;
    }

    @Pointcut("execution(* bg.an.englishacademy.web.controllers.WordController.details(..))")
    public void categoryDetailsPointcut(){};

    @After("categoryDetailsPointcut()")
    public void afterAdvice(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();

        String categoryName = (String) args[1];
        String action = joinPoint.getSignature().getName();

        categoryLogService.createLog(action, categoryName);
    }
}
