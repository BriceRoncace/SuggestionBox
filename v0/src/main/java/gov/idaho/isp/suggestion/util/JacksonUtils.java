package gov.idaho.isp.suggestion.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JacksonUtils {
  private static ObjectMapper OBJECT_MAPPER;

  public static String toJson(Object obj) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(obj);
  }

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    OBJECT_MAPPER = objectMapper;
  }
}
