<%@tag trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@tag description="Suggestion History Tooltip" pageEncoding="UTF-8"%>
<%@attribute name="suggestion" required="true" type="gov.idaho.isp.suggestion.domain.Suggestion" %>
<%@attribute name="cssClass" required="false" type="java.lang.String" %>

<c:set var="tip">
  <h5>Suggestion History</h5>
  <c:forEach var="sh" items="${suggestion.history}">
    ${dateFormatter.format(sh.suggestedOn)} - ${sh.suggestedTo != null ? sh.suggestedTo.fullName : 'anonymous'}<br/>
  </c:forEach>
</c:set>

<i class="fas fa-history ${cssClass}" data-toggle="tooltip" data-placement="auto" data-html="true" title="${tip}"></i>
