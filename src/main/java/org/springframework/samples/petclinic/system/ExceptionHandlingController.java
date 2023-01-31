package org.springframework.samples.petclinic.system;

import org.springframework.samples.petclinic.views.Error;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleError(HttpServletRequest req, Exception ex) {
        return Error.view.render(ex);
    }
}
