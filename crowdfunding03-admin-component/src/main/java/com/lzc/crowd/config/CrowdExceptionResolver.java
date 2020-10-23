package com.lzc.crowd.config;

import com.google.gson.Gson;
import com.lzc.crowd.exception.AccessForbiddenException;
import com.lzc.crowd.util.CrowdUtil;
import com.lzc.crowd.exception.LoginFailedException;
import com.lzc.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.lzc.crowd.constant.CrowdConstant.ATTR_NAME_EXCEPTION;


/**
 * 异常处理器类
 * @author Administrator
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    /**
     * 通用异常处理
     * @param viewName 异常处理后要去的页面
     * @param exception 捕获到的异常类型
     * @param request   当前请求对象
     * @param response  当前响应对象
     * @return
     * @throws IOException
     */

    private ModelAndView commonResolver(String viewName,Exception exception,HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.判断请求类型
        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        //2.如果是ajax请求
        if (judgeResult){

            //3.创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

            //4.创建Gson对象
            Gson gson = new Gson();

            //5.将ResultEntity转化为JSON字符串
            String json = gson.toJson(resultEntity);

            //6.将JSON字符串作为响应体返回给浏览器
            response.getWriter().write(json);

            //7.由于通过response返回了 不提供ModelAndView对象
            return null;
        }

        //8.如果不是ajax请求
        ModelAndView modelAndView = new ModelAndView();

        //9.将Exception对象存入模型
        modelAndView.addObject(ATTR_NAME_EXCEPTION,exception);


        //10.返回视图
        modelAndView.setViewName(viewName);

        return modelAndView;
    }

    //@ExceptionHandler将具体异常类型和方法关联
    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView resolverArithmeticException(ArithmeticException exception,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolver(viewName,exception,request,response);
    }


    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView resolverLoginFailedException(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolver(viewName,exception,request,response);
    }

    @ExceptionHandler(AccessForbiddenException.class)
    public ModelAndView resolverAccessForbiddenException(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolver(viewName,exception,request,response);
    }
}
