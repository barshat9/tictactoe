package com.cobalt.tictactoe.common.aspects.instrumentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ApiLoggerAspect {

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  public void controllerPointcut() {}

  @Pointcut("execution(* *(..))")
  public void allMethodsPointcut(){}

  @Before("controllerPointcut() && allMethodsPointcut()")
  public void log(JoinPoint joinPoint) {
    var target = joinPoint.getTarget();
    Object requestBody = joinPoint.getArgs()[0];
    ObjectMapper mapper = new ObjectMapper();
    try {
      log.info("{} => {}",target, mapper.writeValueAsString(requestBody));
    } catch (JsonProcessingException ex) {
      log.error(ex.getMessage());
    }
  }
}
