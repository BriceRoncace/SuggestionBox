<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<t:page>
  <jsp:attribute name="body">

    <div style="background:#000; color:#fff;">
      <video autoplay muted loop id="video">
        <source src="<c:url value="./assets/videos/SuggestionBoxWeb.mp4"/>" type="video/mp4">
      </video>

      <div class="content col-sm-12 text-center border-top">
        <span class="fa-stack fa-8x">
          <i class="far fa-square fa-stack-2x"></i>
          <i class="fa fa-lightbulb fa-stack-1x"></i>
          <span style="font-size:20px; padding-left: 65px;">${suggestionCount}</span>
        </span>
        <br>
        <form action="<c:url value="/suggest"/>" method="POST">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <input type="hidden" name="unsuggested" value="true"/>
          <button class="btn btn-lg btn-primary">Suggest</button>
        </form>
      </div>
          
      <h3>Todo</h3>
      * Add Suggestion advanced search to refine how selection occurs<br/>
      * Add review to allow users to review a suggestion and give their feedback<br/>
    </div>
  </jsp:attribute>
  <jsp:attribute name="javascript">
    <script type="text/javascript" src="<c:url value="/assets/js/cjis.index.js"/>"></script>
    <script>
      $(function () {
        cjis.index.init();
      });
    </script>
  </jsp:attribute>
</t:page>