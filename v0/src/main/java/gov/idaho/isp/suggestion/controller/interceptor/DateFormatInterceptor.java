package gov.idaho.isp.suggestion.controller.interceptor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DateFormatInterceptor extends HandlerInterceptorAdapter {
  private final String dateFormat;

  public DateFormatInterceptor(@Value("${date.format.print}") String dateFormat) {
    this.dateFormat = dateFormat;
  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
    req.setAttribute("dateFormatter", new NullSafeLocalDateFormatter(DateTimeFormatter.ofPattern(dateFormat)));
    return true;
  }

  public String getDateFormat() {
    return dateFormat;
  }

  public class NullSafeLocalDateFormatter {
    private final DateTimeFormatter formatter;

    public NullSafeLocalDateFormatter(DateTimeFormatter formatter) {
      this.formatter = formatter;
    }

    public String format(LocalDate ld) {
      return ld != null ? formatter.format(ld) : "";
    }
  }
}
