package bg.an.englishacademy.aop;

import bg.an.englishacademy.service.LessonLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LessonLogAspect {

    private final LessonLogService lessonLogService;

    public LessonLogAspect(LessonLogService lessonLogService) {
        this.lessonLogService = lessonLogService;
    }


    @Pointcut("execution(* bg.an.englishacademy.web.controllers.LessonController.details(..))")
    public void lessonDetailsPointcut(){};

    @After("lessonDetailsPointcut()")
    public void afterAdvice(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();

        Long lessonId = (Long) args[1];
        String action = joinPoint.getSignature().getName();

        lessonLogService.createLog(action, lessonId);
    }
}
