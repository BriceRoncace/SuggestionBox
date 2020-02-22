package gov.idaho.isp.suggestion;

import gov.idaho.isp.suggestion.formatter.LocalDateFormatter;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }
  
  @Bean
  public Formatter<LocalDate> getLocalDateFormatter(@Value("${date.format.print:MM/dd/yyyy}") String printFormat, @Value("${date.format.parse:yyyy-MM-dd}") String parseFormat) {
    return new LocalDateFormatter(printFormat, Stream.of(parseFormat.split(",")).map(StringUtils::trim).collect(Collectors.toList()));
  }
}