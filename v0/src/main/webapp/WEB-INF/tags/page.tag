<%@tag trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@tag description="Page Layout" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="head" fragment="true" %>
<%@attribute name="body" fragment="true" %>
<%@attribute name="javascript" fragment="true" %>
<%@attribute name="bodyClass" %>

<!DOCTYPE html>
<html>
  <head>
    <!-- current page: ${pageContext.request.requestURI} -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title><c:choose><c:when test="${title == null}">Suggestion Box</c:when><c:otherwise><jsp:invoke fragment="title" /></c:otherwise></c:choose></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha256-L/W5Wfqfa0sdBNIKN9cG6QA5F2qx4qICmU2VgLruv9Y=" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css" integrity="sha256-mmgLkCYLUQbXn0B1SRqzHar6dCnv9oZFPEC1g1cwlkk=" crossorigin="anonymous" />      <link rel="stylesheet" type="text/css" href='<c:url value="/assets/css/style.css"/>' />
    <link rel="stylesheet" href="<c:url value="/assets/css/style.css"/>" />
    <jsp:invoke fragment="head" />
  </head>
  <body style="padding-top: 70px;" class="${bodyClass}">
    <div class="container-fluid">

      <nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="<c:url value="/"/>">
          <span class="fa-stack fa-1x">
            <i class="far fa-square fa-stack-2x"></i>
            <i class="fa fa-lightbulb fa-stack-1x"></i>
          </span>
          <span class="h4 align-middle">Suggestion Box</span>
        </a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse float-right" id="navbarNav">
          <ul class="navbar-nav">

            <li class="nav-item">
              <a class="nav-link ${pageContext.request.requestURI.endsWith('suggestions.jsp') ? 'active' : ''}" href="<c:url value="/suggestions"/>">Suggestions</a>
            </li>

            <c:if test="${user != null && profiles.contains('dev')}">
              <li class="nav-item">
                <a class="nav-link" href="<c:url value="/h2-console"/>" target="_blank">h2 Console</a>
              </li>
            </c:if>

            <c:choose>
              <c:when test="${user == null}">
                <li class="nav-item">
                  <a class="nav-link" href="#" data-toggle="modal" data-target="#signInModal">Sign in</a>
                </li>
              </c:when>
              <c:otherwise>
                <form id="logoutForm" class="form-inline" action="<c:url value="/logout"/>" method="POST">
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                  <li class="nav-item">
                    <a class="nav-link" href="#" onclick="$('#logoutForm').submit();">Sign out</a>
                  </li>
                </form>
              </c:otherwise>
            </c:choose>  

          </ul>
        </div>
      </nav>
      <%@include file="includes/login.jspf" %>
      <%@include file="includes/messages.jspf" %>

      <jsp:invoke fragment="body" />

    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.slim.min.js" integrity="sha256-pasqAKBDmFT4eHoN2ndd6lN370kFiGUFyTiUHWhU7k8=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/js/bootstrap.bundle.min.js" integrity="sha256-OUFW7hFO0/r5aEGTQOz9F/aXQOt+TwqI1Z4fbVvww04=" crossorigin="anonymous"></script>
    <jsp:invoke fragment="javascript" />
  </body>
</html>
