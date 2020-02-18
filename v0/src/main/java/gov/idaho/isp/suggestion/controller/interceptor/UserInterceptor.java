package gov.idaho.isp.suggestion.controller.interceptor;

import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserInterceptor extends HandlerInterceptorAdapter {
  private String userRequestAttributeName = "user";
  private Function<Authentication,Object> getUserFunction = auth -> auth != null && auth.getPrincipal() instanceof UserDetails ? auth.getPrincipal() : null;

  public UserInterceptor() {
  }

  public UserInterceptor(String userRequestAttributeName) {
    this.userRequestAttributeName = userRequestAttributeName;
  }

  public UserInterceptor(Function<Authentication,Object> getUserFunction) {
    this.getUserFunction = getUserFunction;
  }

  public UserInterceptor(String userRequestAttributeName, Function<Authentication,Object> getUserFunction) {
    this.userRequestAttributeName = userRequestAttributeName;
    this.getUserFunction = getUserFunction;
  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
    if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      req.setAttribute(userRequestAttributeName, getUserFunction.apply(authentication));
    }
    return true;
  }

  public String getUserRequestAttributeName() {
    return userRequestAttributeName;
  }

  public void setUserRequestAttributeName(String userRequestAttributeName) {
    this.userRequestAttributeName = userRequestAttributeName;
  }
}