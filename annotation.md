# 自定义注解
-  注解的定义

```  java
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface Timer(){   
	//这里可以添加各种属性方法
    String value() default "";
}

```

-  注解的解释  

```  java
@Aspect// 这个注解表明 使用spring 的aop，需要开启aop <!--开启AOP自动代理 --><aop:aspectj-autoproxy />  
@Component  
public class TimerAspect {   

    // 标示遇到Timer这个注解的方法进行拦截 @Around 等注解可查看 spring aop相关知识  
    @Around(value = "@annotation(timer)")
    public Object recordTime(ProceedingJoinPoint join, Timer timer) throws Throwable  
    {  
        // 获取到使用了注解的方法的参数
        Object[] args = join.getArgs(); 
        
        //获取使用了注解的方法的 注解的参数 
        String value=timer.value();  
        
        //此处可以添加方法执行前的操作
        ...
        
        // 执行使用注解方法并返回信息     
        Object ret = join.proceed();  
        
        //此处可以添加方法执行后的操作
        ...
        return ret;
    }  
  
} 
```