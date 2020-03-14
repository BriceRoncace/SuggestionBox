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

    <div class="card bg-light">
      <div class="card-body">
        <form id="suggestionForm" action="<c:url value="/suggestions"/>" method="GET">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="row">
            <div class="form-group col-lg-2">
              <label for="title" class="mb-0">Title</label>
              <input type="text" id="title" class="form-control" name="title" value="${spec.title}">
            </div>

            <div class="form-group col-lg-3">
              <label for="details" class="mb-0">Details</label>
              <input type="text" id="details" name="details" class="form-control" value="${spec.details}">
            </div>

            <div class="form-group col-lg-2">
              <label for="tags" class="mb-0">Tags</label>
              <select name="tags" multiple="true" size="3" class="form-control">
                <c:forEach var="tag" items="${tags}">
                  <option value="${tag}" ${spec.tags.contains(tag) ? 'selected' : ''}>${tag}</option>
                </c:forEach>
              </select>
            </div>

            <div class="form-group col-lg-2">
              <label for="video" class="mb-0">Video <input type="checkbox" id="video" name="video" value="true" ${spec.video ? 'checked' : ''}></label>
              <span class="float-right">
                <div class="btn-group btn-group-toggle" style="margin-top:-4px;" data-toggle="buttons">
                  <label class="btn btn-outline-secondary btn-xs active" title="less than or equal" onclick="$('#videoLength2').addClass('d-none').val('');">
                    <input type="radio" name="videoLength.searchType" value="LESS_THAN_OR_EQUAL" checked/> <=
                  </label>
                  <label class="btn btn-outline-secondary btn-xs" title="greater than or equal" onclick="$('#videoLength2').addClass('d-none').val('');">
                    <input type="radio" name="videoLength.searchType" value="GREATER_THAN_OR_EQUAL" /> >=
                  </label>
                  <label class="btn btn-outline-secondary btn-xs" title="between" style="margin-right: 0;" onclick="$('#videoLength2').removeClass('d-none');">
                    <input type="radio" name="videoLength.searchType" value="BETWEEN" /> Between
                  </label>
                </div>
              </span>
               <div class="input-group">
              <input type="text" name="videoLength.number1" data-provide="duration" class="form-control" value="${spec.videoLength.number1}" />
              <span id="videoLength2" class="${spec.videoLength.number2 > 0? '' : 'd-none'}">
              <input type="text" name="videoLength.number2" data-provide="duration" class="form-control" value="${spec.videoLength.number2}" />
              </span>
               </div>
            </div>

            <div class="form-group col-lg-1">
              <br>
              <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="unsuggested" name="unsuggested" value="true" ${spec.unsuggested ? 'checked' : ''}>
                <label class="form-check-label" for="unsuggested">Unsuggested</label>
              </div>
            </div>

            <div class="form-group col-lg-2 p-1 text-center">
              <a href="<c:url value="/suggestions"/>" class="btn btn-sm btn-outline-secondary mt-4"><i class="fas fa-sync" title="Reset"></i> Reset</a> 
              <button type="submit" onclick="$('#suggestionForm').attr('method', 'post').attr('action', '<c:url value="/suggest"/>').submit();" class="btn btn-sm btn-outline-primary mt-4"><i class="fas fa-concierge-bell" title="Suggest"></i> Suggest </button> 
              <button type="submit" onclick="$('#suggestionForm').attr('action', '<c:url value="/suggestions"/>').submit();" class="btn btn-sm btn-outline-secondary mt-4"><i class="fas fa-search" title="Search"></i> Search</button> 
            </div>
          </div>
        </form>
      </div>
    </div>

    <hr/>

    <c:forEach var="suggestion" items="${page.content}">
      <div class="border-bottom mb-2 pb-2">
        <a href="<c:url value="/suggestions/${suggestion.id}"/>" class="h5">${suggestion.title}</a>
        <c:if test="${suggestion.video}">
          <i class="fas fa-video"></i> <small>${suggestion.videoDetails.durationPrettyPrint}</small>
        </c:if>

        <c:if test="${!empty suggestion.tags}">
          <br/>         
          <span data-provide="tagify" data-tagify-allow-removal="false" data-tags='<t:toJson value="${suggestion.tags}"/>'></span>
        </c:if>

        <br/>
        <small class="font-italic">Suggested by ${suggestion.author != null ? suggestion.author.fullName : 'anonymous'} on ${dateFormatter.format(suggestion.created)}</small>
      </div>
    </c:forEach>

    <t:pager page="${page}" showInfo="true"/>

  </jsp:attribute>
  <jsp:attribute name="javascript">
    <script type="text/javascript" src="<c:url value="/assets/js/tagify.jquery.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/duration.jquery.js"/>"></script>
    <script>
      $(function () {
      });
    </script>
  </jsp:attribute>
</t:page>