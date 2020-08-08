package cn.fcars.infomgr.custom;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        if(e.getClass().getName().equals("org.springframework.web.multipart.MultipartException"))
        {
            if(e.getCause().toString().contains("FileSizeLimitExceededException")) {
//              throw new MaxUploadSizeExceededException(2097152);
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("forward:/MaxUploadSizeExceededException");
                return modelAndView;
            }
        }
        return null;
    }
}
