<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:page>
  <jsp:attribute name="body">
    
    <div class="col-sm-12 text-center">
      <span class="fa-stack fa-8x">
        <i class="far fa-square fa-stack-2x"></i>
        <i class="fa fa-lightbulb fa-stack-1x"></i>
        <span style="font-size:20px; padding-left: 65px;">${suggestionCount}</span>
      </span>
    </div>
          
    <hr/>
    <h3>Todo</h3>
    * Add "Suggest" button to front page to randomly select a suggestion<br/>
    * Add Suggestion advanced search to refine how selection occurs<br/>
    * Add Suggestion history to store when suggestion was suggested<br/>
    * Add review to allow users to review a suggestion and give their feedback<br/>
    * Make index (this page) have pretty logo (video background?) with tags shown in a box -- show total number of suggestions somewhere<br/>
      
  </jsp:attribute>
  <jsp:attribute name="javascript">
    <script type="text/javascript" src="<c:url value="/assets/js/cjis.index.js"/>"></script>
    <script>
      $(function() {
        cjis.index.init();
      });
    </script>
  </jsp:attribute>
</t:page>