package org.springframework.samples.petclinic.system;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.views.Error;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<String> handleError(HttpServletRequest req, Exception ex) {
		return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(Error.view.render(ex));
	}

}
