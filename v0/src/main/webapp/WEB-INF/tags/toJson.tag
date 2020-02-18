<%@tag trimDirectiveWhitespaces="true" %>
<%@attribute name="value" type="java.lang.Object" %>
<%@tag import="gov.idaho.isp.suggestion.util.JacksonUtils" %>
${JacksonUtils.toJson(value)}