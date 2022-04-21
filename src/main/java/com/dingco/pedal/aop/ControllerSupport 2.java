package com.dingco.pedal.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
public class ControllerSupport {
    private static final Logger logger = LoggerFactory.getLogger(ControllerSupport.class);

//    @ExceptionHandler({NotMatchedException.class})
//    @ResponseBody
//    public String occurException(HttpServletRequest request, NotMatchedException e){
//        String context = request.getContextPath();
//        logger.debug("context = " + context);
//        logger.debug("exception = " + e);
////        return "redirect:"+context+"/inquiry";
//        return e.getMessage();
//    }
//
//    @ExceptionHandler({SQLException.class})
//    @ResponseBody
//    public String occurException(HttpServletRequest request, SQLException e){
//        String context = request.getContextPath();
//        logger.debug("context = " + context);
//        logger.debug("exception = " + e);
////        return "redirect:"+context+"/inquiry";
//        return "현재 서비스를 이용할 수 없습니다. 잠시 후 다시 시도해주세요.";
//    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        logger.error("[exceptionHandler] ex", e);
        return "error/error";
    }
}
