<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:page>
  <jsp:attribute name="body">
    
    <span class="float-right pt-2">
      <a href="<c:url value="/suggestions/new"/>">
        <span class="fa-stack fa-1x" title="Make Suggestion">
          <i class="far fa-square fa-stack-2x text-success"></i>
          <i class="fa fa-plus fa-stack-1x text-success"></i>
        </span>
      </a>
    </span>
      
    <h2 class="border-bottom mb-3 pb-2">Suggestions</h2>
           
    <c:forEach var="suggestion" items="${suggestions}">
      <div class="border-bottom mb-2 pb-2">
        <a href="<c:url value="/suggestions/${suggestion.id}"/>" class="h5">${suggestion.title}</a>
        <c:if test="${suggestion.video}">
          <i class="fas fa-video"></i>
        </c:if>
        
        <c:if test="${!empty suggestion.tags}">
          <br/>         
          <span data-provide="tagify" data-tagify-allow-removal="false" data-tags='<t:toJson value="${suggestion.tags}"/>'></span>
        </c:if>
          
        <br/>
        <small class="font-italic">Suggested by ${suggestion.author != null ? suggestion.author.fullName : 'anonymous'} on ${dateFormatter.format(suggestion.created)}</small>
      </div>
    </c:forEach>
    
  </jsp:attribute>
  <jsp:attribute name="javascript">
    <script type="text/javascript" src="<c:url value="/assets/js/tagify.jquery.js"/>"></script>
    <script>
      $(function() {
      });
    </script>
  </jsp:attribute>
</t:page>