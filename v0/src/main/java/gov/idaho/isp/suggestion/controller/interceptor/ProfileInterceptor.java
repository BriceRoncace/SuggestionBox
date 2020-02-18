package gov.idaho.isp.suggestion.controller.interceptor;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ProfileInterceptor extends HandlerInterceptorAdapter {
  private final Environment environment;

  public ProfileInterceptor(Environment environment) {
    this.environment = environment;
  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
    req.setAttribute("profiles", Arrays.asList(environment.getActiveProfiles()));
    return true;
  }
}
