<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:page>
  <jsp:attribute name="body">

    <c:if test="${suggestion.author == null || suggestion.author == user}">
      <span class="float-right pt-2">
        <a href="<c:url value="/suggestions/${suggestion.id}/edit"/>"><i class="fa fa-edit" style="font-size:26px;" ></i></a>
      </span>  
    </c:if>
    
    <h2 class="border-bottom pb-2 mb-3">Suggestion <small><t:history-tooltip suggestion="${suggestion}" cssClass="small"/></small></h2>

    <div class="form-row">
      <div class="form-group col-md-6">
        <label for="title" class="mb-0">Title</label>
        <div class="font-weight-bold">${suggestion.title}</div>
      </div>
      <c:if test="${suggestion.url != null}">
        <div class="form-group col-md-6">
          <label for="link" class="mb-0">Link</label>
          <div class="font-weight-bold"><a href="<c:url value="${suggestion.url}"/>" target="_blank">${suggestion.url}</a></div>
        </div>
      </c:if>
    </div>

    <c:if test="${!empty suggestion.tags}">
      <div class="form-row">
        <div class="form-group col-md-12">
          <label for="tags" class="mb-0">Tags</label>
          <br/>         
          <span data-provide="tagify" data-tagify-allow-removal="false" data-tags='<t:toJson value="${suggestion.tags}"/>'></span>
        </div>
      </div>
    </c:if>

    <c:if test="${suggestion.video}">
      <div class="form-row">
      <div class="form-group col-md-6">
        <label for="seconds" class="mb-0">Length</label>
        <div class="font-weight-bold">${suggestion.videoDetails.durationPrettyPrint}</div>
      </div>
      <div class="form-group col-md-6">
        <label for="posted" class="mb-0">Posted</label>
        <div class="font-weight-bold">${suggestion.videoDetails.posted}</div>
      </div>
      </div>
    </c:if>
      
    <c:if test="${suggestion.details != null}">  
      <div class="form-row">
        <div class="form-group col-md-12">
          <label for="details" class="mb-0">Details</label>
          <p class="font-weight-bold" style="white-space: pre;">${suggestion.details}</p>
        </div>
      </div>
    </c:if>

  </jsp:attribute>
  <jsp:attribute name="javascript">
    <script type="text/javascript" src="<c:url value="/assets/js/tagify.jquery.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/duration.jquery.js"/>"></script>
    <script>
          $(function () {
            $('[data-toggle="tooltip"]').tooltip();
          });
    </script>
  </jsp:attribute>
</t:page>