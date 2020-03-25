package gov.idaho.isp.suggestion.controller.advice;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class StringTrimmerAdvice {

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    StringTrimmerEditor strTrimmer = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, strTrimmer);
  }
}
