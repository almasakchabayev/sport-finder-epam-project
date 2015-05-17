<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base>
    <jsp:body>
        <div class="row">
            <!--Content-->
            <div class="col-md-9">
                <header>
                    <h1 class="page-title">Listing</h1>
                </header>
                <figure class="filter clearfix">
                    <div class="pull-right">
                        <aside class="sorting">
                            <span>Sport</span>
                            <div class="form-group">
                                <select class="framed" name="sort" onchange="window.location.href = '<c:url value="/index?sport=" />' + this.options[this.selectedIndex].value;">
                                    <option value="${sport.id}">
                                        All
                                    </option>
                                    <c:forEach items="${sports}" var="sport">
                                        <option value="${sport.id}">
                                            ${sport.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!-- /.form-group -->
                        </aside>
                    </div>
                </figure>

                <!--Listing List-->
                <section class="block listing">
                    <c:choose>
                        <c:when test="${sportPlaces.isEmpty()}">
                            <h3>No sport places yet</h3>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${sportPlaces}" var="sportPlace">
                                <t:itempreview item="${sportPlace}" deleted="false" admin="false" />
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    <!-- /.item-->
                </section>
                <!--end Listing List-->
                <!--Pagination-->
                <nav>
                    <ul class="pagination pull-right">
                        <c:forEach begin="1" end="${numberOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <li class="active"><a href="<c:url value="/index?page=${i}" />">${i}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="<c:url value="/index?page=${i}" />">${i}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </nav>
                <!--end Pagination-->
            </div>
            <!--Sidebar-->
            <%--<div class="col-md-3">--%>
                <%--<aside id="sidebar">--%>
                    <%--<section>--%>
                        <%--<header><h2>New Places</h2></header>--%>
                        <%--<a href="item-detail.html" class="item-horizontal small">--%>
                            <%--<h3>Cash Cow Restaurante</h3>--%>
                            <%--<figure>63 Birch Street</figure>--%>
                            <%--<div class="wrapper">--%>
                                <%--<div class="image"><img src="assets/img/items/1.jpg" alt=""></div>--%>
                                <%--<div class="info">--%>
                                    <%--<div class="type">--%>
                                        <%--<i><img src="assets/icons/restaurants-bars/restaurants/restaurant.png" alt=""></i>--%>
                                        <%--<span>Restaurant</span>--%>
                                    <%--</div>--%>
                                    <%--<div class="rating" data-rating="4"></div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</a>--%>
                        <%--<!--/.item-horizontal small-->--%>
                        <%--<a href="item-detail.html" class="item-horizontal small">--%>
                            <%--<h3>Blue Chilli</h3>--%>
                            <%--<figure>2476 Whispering Pines Circle</figure>--%>
                            <%--<div class="wrapper">--%>
                                <%--<div class="image"><img src="assets/img/items/2.jpg" alt=""></div>--%>
                                <%--<div class="info">--%>
                                    <%--<div class="type">--%>
                                        <%--<i><img src="assets/icons/restaurants-bars/restaurants/restaurant.png" alt=""></i>--%>
                                        <%--<span>Restaurant</span>--%>
                                    <%--</div>--%>
                                    <%--<div class="rating" data-rating="3"></div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</a>--%>
                        <%--<!--/.item-horizontal small-->--%>
                        <%--<a href="item-detail.html" class="item-horizontal small">--%>
                            <%--<h3>Eddie’s Fast Food</h3>--%>
                            <%--<figure>4365 Bruce Street</figure>--%>
                            <%--<div class="wrapper">--%>
                                <%--<div class="image"><img src="assets/img/items/3.jpg" alt=""></div>--%>
                                <%--<div class="info">--%>
                                    <%--<div class="type">--%>
                                        <%--<i><img src="assets/icons/restaurants-bars/restaurants/restaurant.png" alt=""></i>--%>
                                        <%--<span>Restaurant</span>--%>
                                    <%--</div>--%>
                                    <%--<div class="rating" data-rating="5"></div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</a>--%>
                        <%--<!--/.item-horizontal small-->--%>
                    <%--</section>--%>
                    <%--<section>--%>
                        <%--<a href="#"><img src="assets/img/ad-banner-sidebar.png" alt=""></a>--%>
                    <%--</section>--%>
                    <%--<section>--%>
                        <%--<header><h2>Categories</h2></header>--%>
                        <%--<ul class="bullets">--%>
                            <%--<li><a href="#" >Restaurant</a></li>--%>
                            <%--<li><a href="#" >Steak House & Grill</a></li>--%>
                            <%--<li><a href="#" >Fast Food</a></li>--%>
                            <%--<li><a href="#" >Breakfast</a></li>--%>
                            <%--<li><a href="#" >Winery</a></li>--%>
                            <%--<li><a href="#" >Bar & Lounge</a></li>--%>
                            <%--<li><a href="#" >Pub</a></li>--%>
                        <%--</ul>--%>
                    <%--</section>--%>
                    <%--<section>--%>
                        <%--<header><h2>Events</h2></header>--%>
                        <%--<div class="form-group">--%>
                            <%--<select class="framed" name="events">--%>
                                <%--<option value="">Select Your City</option>--%>
                                <%--<option value="1">London</option>--%>
                                <%--<option value="2">New York</option>--%>
                                <%--<option value="3">Barcelona</option>--%>
                                <%--<option value="4">Moscow</option>--%>
                                <%--<option value="5">Tokyo</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                        <%--<!-- /.form-group -->--%>
                    <%--</section>--%>
                <%--</aside>--%>
                <%--<!-- /#sidebar-->--%>
            <%--</div>--%>
            <!-- /.col-md-3-->
            <!--end Sidebar-->
        </div>
    </jsp:body>
</t:base>