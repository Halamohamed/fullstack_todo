package se.ecutb.fullstack_todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class ApplicationControllerException {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleAppResourceNotFoundException(UsernameNotFoundException ex){
        return createModel(HttpStatus.NOT_FOUND, ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleNotYetImplementedException(RuntimeException ex){
        return createModel(HttpStatus.NOT_FOUND, ex);
    }

    private ModelAndView createModel(HttpStatus status, Exception ex){
        ModelAndView model = new ModelAndView();
        Map<Object, Object> map = new HashMap<>();
        map.put("statusName", status.getReasonPhrase());
        map.put("statusCode", status.value());
        map.put("message", ex.getMessage());
        model.setViewName("error-page");
        model.addObject("modelMap", map);
        return model;
    }
}
