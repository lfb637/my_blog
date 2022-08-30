package com.lfb.handler;

import com.sun.deploy.net.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lfb
 * @data 2022/8/18 9:23
 */
@ControllerAdvice
public class ControllerExceptionHandler {
     private Logger logger = LoggerFactory.getLogger(this.getClass());
     @ExceptionHandler(Exception.class)
     public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
          // 错误信息
          logger.error("Request URL:{}，Exception:{}", request.getRequestURL(), e.getMessage());
          // 非自定义的错误信息由spring抛出
          if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
               throw e;
          }
          // 错误信息载体
          ModelAndView mv = new ModelAndView();
          mv.addObject("url", request.getRequestURL());
          mv.addObject("exception",e);
          // 错误信息页面(spring会自动找error文件)
          mv.setViewName("error/error");
          return mv;
     }
}
