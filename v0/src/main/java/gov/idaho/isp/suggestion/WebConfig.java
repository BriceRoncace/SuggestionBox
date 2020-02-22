package gov.idaho.isp.suggestion;

import gov.idaho.isp.suggestion.controller.interceptor.DateFormatInterceptor;
import gov.idaho.isp.suggestion.controller.interceptor.ProfileInterceptor;
import gov.idaho.isp.suggestion.controller.interceptor.UserInterceptor;
import java.util.List;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final Environment environment;
  private final String dateFormat;

  public WebConfig(Environment environment, @Value("${date.format.print:MM/dd/yyyy}") String dateFormat) {
    this.environment = environment;
    this.dateFormat = dateFormat;
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("index");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new UserInterceptor());
    registry.addInterceptor(new ProfileInterceptor(environment));
    registry.addInterceptor(new DateFormatInterceptor(dateFormat));
  }
  
  @Bean
  public FilterRegistrationBean hiddenHttpMethodFilter() {
    FilterRegistrationBean frb = new FilterRegistrationBean(new HiddenHttpMethodFilter());
    frb.setUrlPatterns(List.of("/*"));
    return frb;
  }

  @Bean
  public ServletRegistrationBean h2servletRegistration() {
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
    registrationBean.addUrlMappings("/h2-console/*");
    return registrationBean;
  }
}