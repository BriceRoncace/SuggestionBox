<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:page>
  <jsp:attribute name="body">
    
    <c:if test="${suggestion.id != null}">
      <form id="deleteSuggestionForm" action="<c:url value="/suggestions/${suggestion.id}"/>" class="float-right pt-2" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="_method" value="DELETE"/>
        <a href="#" onclick="$('#deleteSuggestionForm').submit();">
          <span class="fa-stack fa-1x" title="Delete Suggestion">
            <i class="far fa-square fa-stack-2x text-danger"></i>
            <i class="fa fa-times fa-stack-1x text-danger"></i>
          </span>
        </a>
      </form>
    </c:if>
    
    <h2 class="border-bottom pb-2 mb-3">Suggestion</h2>
    
    <form action="<c:url value="/suggestions/${suggestion.id}"/>" method="POST">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      
      <div class="form-row">
        <div class="form-group col-md-6">
          <label for="title">Title</label>
          <input type="input" class="form-control" id="title" name="title" value="${suggestion.title}" />
        </div>
        <div class="form-group col-md-6">
          <label for="link">Link</label>
          <input type="url" class="form-control" id="link" name="url" value="${suggestion.url}" />
        </div>
      </div>
        
      <div class="form-row">
        <div class="form-group col-md-12">
          <label for="tags">Tags</label>
          <input type="input" class="form-control" data-provide="tagify" id="tags" data-tags='<t:toJson value="${suggestion.tags}"/>' />
          <input type="hidden" name="tags" />
        </div>
      </div>
      
      <div class="form-row">
        <div class="form-group col-md-12">
          <label for="details">Details</label>
          <textarea class="form-control" id="details" name="details">${suggestion.details}</textarea>
        </div>
      </div>
      
      <c:if test="${!suggestion.video}">
        <div class="form-check mb-3">
          <input class="form-check-input" type="checkbox" id="videoCheckbox" onclick="$('#videoDetails').toggleClass('d-none', $(this).not(':checked'))">
          <label class="form-check-label" for="videoCheckbox">
            Video?
          </label>
        </div>
      </c:if>
       
      <div id="videoDetails" class="form-row ${suggestion.video ? '' : 'd-none'}">
        <div class="form-group col-md-6">
          <label for="seconds">Length</label>
          <input type="text" class="form-control" data-provide="duration" id="seconds" name="videoDetails.seconds" value="${suggestion.videoDetails.seconds}" />
        </div>
        <div class="form-group col-md-6">
          <label for="posted">Posted</label>
          <input type="date" class="form-control" id="posted" name="videoDetails.posted" value="${suggestion.videoDetails.posted}"/>
        </div>
      </div>   
        
      <button type="submit" class="btn btn-primary">Submit</button>  
    </form>
  
  </jsp:attribute>
  <jsp:attribute name="javascript">
    <script type="text/javascript" src="<c:url value="/assets/js/tagify.jquery.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/duration.jquery.js"/>"></script>
    <script>
      $(function() {
      });
    </script>
  </jsp:attribute>
</t:page>