package com.technicalinterest.group.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/** 异常 code 转换
 * @description:
 * @author: Guo Lixiao
 * @date: 2018-1-9 19:15
 * @see:
 * @since:
 **/
@ControllerAdvice
@Slf4j
public class ControllerExceptionAOP {


//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    Response<Object> handleException(Exception e, HttpServletRequest request) {
//        log.error("出错啦！",e);
//        return new Response(Code.E_400, "网络不给力，请稍后重试[503]!");
//    }


}
