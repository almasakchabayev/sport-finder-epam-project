<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base>
  <jsp:body>
    <header>
      <ul class="nav nav-pills">
        <li><a href="<c:url value="/profile" />"><h1 class="page-title">${user.firstName} ${user.lastName}</h1></a></li>
        <li class="active"><a href="<c:url value="/manager/items" />"><h1 class="page-title">My Items</h1></a></li>
      </ul>
    </header>
    <div class="row">
      <div class="col-md-3 col-sm-3">
        <aside id="sidebar">
          <ul class="navigation-sidebar list-unstyled">
            <li class="active">
              <a href="#">
                <i class="fa fa-folder"></i>
                <span>All Items</span>
              </a>
            </li>
            <li>
              <a href="#">
                <i class="fa fa-check"></i>
                <span>Approved</span>
              </a>
            </li>
            <li>
              <a href="#">
                <i class="fa fa-clock-o"></i>
                <span>In Queue</span>
              </a>
            </li>
            <li>
              <a href="#">
                <i class="fa fa-eye-slash"></i>
                <span>Hidden</span>
              </a>
            </li>
          </ul>
        </aside>
      </div>
      <div class="col-md-9 col-sm-9">
        <section id="items">
          <c:choose>
            <c:when test="${sportPlaces.isEmpty()}">
              <h3>You do no have any sport places</h3>
            </c:when>
            <c:otherwise>
              <c:forEach items="${sportPlaces}" var="sportPlace">
                <t:itempreview item="${sportPlace}" />
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </section>
      </div>
    </div>
  </jsp:body>
</t:base>