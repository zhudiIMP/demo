package com.example.aop;

import com.example.service.RedisCacheService;
import com.example.common.exception.BaseException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品配置切面锁
 */
@Aspect
@Component
public class RedisLockAspect {
    private static final Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);
    @Autowired
    private RedisCacheService redisCacheService;

    @Pointcut("@annotation(com.example.aop.RedisLockAnnotation)")
    private void webPointcut() {
    }
    @Value("${syn.redis.max.time}")
    private  Integer redisMaxTime;

    /**
     * Description:进入方法之前
     * @Version1.0
     */
    @Before(value = "webPointcut()")
    private void before(JoinPoint joinPoint) {
        Object[] args =null;
        args = joinPoint.getArgs();
        String key = (String)args[0];
        try{
            logger.info(String.format("[before][进入方法之前]target=%s",joinPoint.getTarget().toString()));
            if(args==null||args.length==0){
                logger.error(String.format("[before][进入方法之前]target=%s;args=%s;无参数,无法加锁",joinPoint.getTarget().toString(),args));
                return;
            }
            logger.debug(String.format("[before][进入方法之前][加锁]target=%s;args=%s",joinPoint.getTarget().toString(),args.toString()));
            boolean lock = redisCacheService.getLock(key, redisMaxTime);
            logger.debug(String.format("[before][进入方法之前][加锁]target=%s;args=%s;isLock=%s", joinPoint.getTarget().toString(),args.toString(), lock));
            if (!lock) {
                delLock(key);
                //throw new BaseException(ReturnCodeExceptionEnum.PARAM_INVALID_ERROR.getErrorCode(), ReturnCodeExceptionEnum.PARAM_INVALID_ERROR.getErrorMessage());

            }

        }catch (Exception e){
            delLock(key);
            logger.error(String.format("[before][进入方法之前]出现异常target=%s;args=%s;e=%s", joinPoint.getTarget().toString(),args.toString(), e.getMessage()));
            //throw new BaseException(ReturnCodeExceptionEnum.SYSTEM_IS_BUSY_ERROR.getErrorCode(), ReturnCodeExceptionEnum.SYSTEM_IS_BUSY_ERROR.getErrorMessage());
        }
    }

    public void delLock(String key){
        redisCacheService.delLock(key);
    }

    /**
     * Description:方法退出之后(包含正常退出和异常退出)
     * @Version1.0
     */
    @After(value = "webPointcut()")
    private void after(JoinPoint joinPoint){
        Object[] args=null;
        try{
            logger.debug(String.format("[after][方法退出之前解锁]target=%s",joinPoint.getTarget()));
            args = joinPoint.getArgs();
            if(args==null||args.length==0){
                logger.error("[after][方法退出之前解锁]无请求参数target=" + joinPoint.getTarget());
                return;
            }
            String key = (String)args[0];
            logger.debug(String.format("[after][方法退出之前解锁]target=%s;args=%s",joinPoint.getTarget(),args.toString()));
            logger.debug(String.format("[after][方法退出之前][解锁]target=%s;args=%s",joinPoint.getTarget().toString(),args.toString()));
            boolean lock = redisCacheService.delLock(key);
            logger.debug(String.format("[after][方法退出之前][解锁]target=%s;args=%s;isLock=%s", joinPoint.getTarget().toString(),args.toString(), lock));
        }catch (Exception e){
            logger.info(String.format("[after][方法退出之前解锁]target=%s;args=%s;e=%s",joinPoint.getTarget(),args.toString(),e.getMessage()));
        }finally {
            logger.info(String.format("[after][方法退出之前解锁][解锁]target=%s;args=%s",joinPoint.getTarget(),args.toString()));
        }

    }

}
