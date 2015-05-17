<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base>
    <jsp:body>
        <div class="row">
            <!--Item Detail Content-->
            <div class="col-md-9">
                <section class="block" id="main-content">
                    <header class="page-title">
                        <div class="title">
                            <h1>${item.address.country} ${item.address.city}</h1>
                            <figure>${item.address.addressLine2} ${item.address.addressLine1}</figure>
                        </div>
                        <div class="info">
                            <div class="type">
                                <i><img src="assets/icons/restaurants-bars/restaurants/restaurant.png" alt=""></i>
                                <span><c:forEach items="${item.sports}" var="sport">
                                    ${sport.name}&nbsp;
                                </c:forEach></span>
                            </div>
                        </div>
                    </header>
                    <div class="row">
                        <!--Detail Sidebar-->
                        <aside class="col-md-4 col-sm-4" id="detail-sidebar">
                            <!--Contact-->
                            <section>
                                <header><h3>Contact</h3></header>
                                <address>
                                    <div>${item.address.country} ${item.address.city}</div>
                                    <div>${item.address.addressLine2} ${item.address.addressLine1}</div>
                                    <div>${item.address.zipcode}</div>
                                    <figure>
                                        <div class="info">
                                            <i class="fa fa-mobile"></i>
                                            <span>${item.manager.phoneNumbers.get(0).number}</span>
                                        </div>
                                        <c:if test="${item.manager.phoneNumbers.size() >= 2}">
                                            <div class="info">
                                                <i class="fa fa-phone"></i>
                                                <span>${item.manager.phoneNumbers.get(1).number}</span>
                                            </div>
                                        </c:if>
                                        <%--<div class="info">--%>
                                            <%--<i class="fa fa-globe"></i>--%>
                                            <%--<a href="#">www.maxfivelounge.com</a>--%>
                                        <%--</div>--%>
                                    </figure>
                                </address>
                            </section>
                            <!--end Contact-->
                            <!--Rating-->
                            <%--<section class="clearfix">--%>
                                <%--<header class="pull-left"><a href="#reviews" class="roll"><h3>Rating</h3></a></header>--%>
                                <%--<figure class="rating big pull-right" data-rating="4"></figure>--%>
                            <%--</section>--%>
                            <!--end Rating-->
                            <!--Events-->
                            <%--<section>--%>
                                <%--<header><h3>Events</h3></header>--%>
                                <%--<figure>--%>
                                    <%--<div class="expandable-content collapsed show-60" id="detail-sidebar-event">--%>
                                        <%--<div class="content">--%>
                                            <%--<p>Maecenas purus sapien, pellentesque non consectetur eu, rhoncus in mauris.--%>
                                                <%--Duis et nisl metus. Sed ut pulvinar mauris, bibendum ullamcorper ex.--%>
                                                <%--Aliquam vitae ante diam. Nam eu blandit odio. Cras erat lorem, iaculis eu nulla eu, sodales aliquam eros.--%>
                                            <%--</p>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<a href="#" class="show-more expand-content" data-expand="#detail-sidebar-event" >Show More</a>--%>
                                <%--</figure>--%>
                            <%--</section>--%>
                            <!--end Events-->
                            <!--Contact Form-->
                            <%--<section>--%>
                                <%--<header><h3>Contact Form</h3></header>--%>
                                <%--<figure>--%>
                                    <%--<form id="item-detail-form" role="form" method="post" action="?">--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="item-detail-name">Name</label>--%>
                                            <%--<input type="text" class="form-control framed" id="item-detail-name" name="item-detail-name" required="">--%>
                                        <%--</div>--%>
                                        <%--<!-- /.form-group -->--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="item-detail-email">Email</label>--%>
                                            <%--<input type="email" class="form-control framed" id="item-detail-email" name="item-detail-email" required="">--%>
                                        <%--</div>--%>
                                        <%--<!-- /.form-group -->--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="item-detail-message">Message</label>--%>
                                            <%--<textarea class="form-control framed" id="item-detail-message" name="item-detail-message"  rows="3" required=""></textarea>--%>
                                        <%--</div>--%>
                                        <%--<!-- /.form-group -->--%>
                                        <%--<div class="form-group">--%>
                                            <%--<button type="submit" class="btn framed icon">Send<i class="fa fa-angle-right"></i></button>--%>
                                        <%--</div>--%>
                                        <%--<!-- /.form-group -->--%>
                                    <%--</form>--%>
                                <%--</figure>--%>
                            <%--</section>--%>
                            <!--end Contact Form-->
                        </aside>
                        <!--end Detail Sidebar-->
                        <!--Content-->
                        <div class="col-md-8 col-sm-8">
                            <section>
                                <article class="item-gallery">
                                    <div class="owl-carousel item-slider">
                                        <c:if test="${item.images.size() > 0}">
                                            <c:forEach items="${item.images}" var="image">
                                                <c:if test="${image != null}">
                                                <div class="slide">
                                                    <img src="<c:url value="/image?id=${image.id}" />" data-hash="${image.id}" alt="">
                                                </div>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                    <!-- /.item-slider -->
                                    <div class="thumbnails">
                                        <span class="expand-content btn framed icon" data-expand="#gallery-thumbnails" >More<i class="fa fa-plus"></i></span>
                                        <div class="expandable-content height collapsed show-70" id="gallery-thumbnails">
                                            <div class="content">
                                                <c:if test="${item.images.size() > 0}">
                                                    <c:forEach items="${item.images}" var="image">
                                                        <c:if test="${image != null}">
                                                        <a href="#${image.id}" id="thumbnail-${image.id}">
                                                            <img src="<c:url value="/image?id=${image.id}" />" alt="">
                                                        </a>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </article>
                                <!-- /.item-gallery -->
                                <article class="block">
                                    <header><h2>Description</h2></header>
                                    <p>
                                        ${item.description}
                                    </p>
                                </article>
                                <!-- /.block -->
                                <%--<article class="block">--%>
                                    <%--<header><h2>Daily Menu</h2></header>--%>
                                    <%--<div class="list-slider owl-carousel">--%>
                                        <%--<div class="slide">--%>
                                            <%--<header>--%>
                                                <%--<h3><i class="fa fa-calendar"></i>Monday (today)</h3>--%>
                                            <%--</header>--%>
                                            <%--<div class="list-item">--%>
                                                <%--<div class="left">--%>
                                                    <%--<h4>Chicken wings</h4>--%>
                                                    <%--<figure>Curabitur odio nibh, luctus non pulvinar</figure>--%>
                                                <%--</div>--%>
                                                <%--<div class="right">$4.50</div>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.list-item -->--%>
                                            <%--<div class="list-item">--%>
                                                <%--<div class="left">--%>
                                                    <%--<h4>Mushroom ragout</h4>--%>
                                                    <%--<figure>Donec a odio rutrum, hendrerit sapien</figure>--%>
                                                <%--</div>--%>
                                                <%--<div class="right">$3.60</div>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.list-item -->--%>
                                            <%--<div class="list-item">--%>
                                                <%--<div class="left">--%>
                                                    <%--<h4>Nice salad with tuna, beans and hard-boiled egg</h4>--%>
                                                    <%--<figure>Urabitur suscipit, justo eu dignissim lacinia </figure>--%>
                                                <%--</div>--%>
                                                <%--<div class="right">$1.20</div>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.list-item -->--%>
                                        <%--</div>--%>
                                        <%--<!-- /.slide -->--%>
                                        <%--<div class="slide">--%>
                                            <%--<header>--%>
                                                <%--<h3><i class="fa fa-calendar"></i>Tuesday</h3>--%>
                                            <%--</header>--%>
                                            <%--<div class="list-item">--%>
                                                <%--<div class="left">--%>
                                                    <%--<h4>Chicken wings</h4>--%>
                                                    <%--<figure>Curabitur odio nibh, luctus non pulvinar</figure>--%>
                                                <%--</div>--%>
                                                <%--<div class="right">$4.50</div>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.list-item -->--%>
                                            <%--<div class="list-item">--%>
                                                <%--<div class="left">--%>
                                                    <%--<h4>Mushroom ragout</h4>--%>
                                                    <%--<figure>Donec a odio rutrum, hendrerit sapien</figure>--%>
                                                <%--</div>--%>
                                                <%--<div class="right">$3.60</div>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.list-item -->--%>
                                            <%--<div class="list-item">--%>
                                                <%--<div class="left">--%>
                                                    <%--<h4>Nice salad with tuna, beans and hard-boiled egg</h4>--%>
                                                    <%--<figure>Urabitur suscipit, justo eu dignissim lacinia </figure>--%>
                                                <%--</div>--%>
                                                <%--<div class="right">$1.20</div>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.list-item -->--%>
                                        <%--</div>--%>
                                        <%--<!-- /.slide -->--%>
                                    <%--</div>--%>
                                    <%--<!-- /.list-slider -->--%>
                                <%--</article>--%>
                                <!-- /.block -->
                                <article class="block">
                                    <header><h2>Features</h2></header>
                                    <ul class="bullets">
                                        <c:if test="${item.indoor}">
                                            <li>Indoor: ${item.indoor}</li>
                                        </c:if>
                                        <c:if test="${item.changingRoom}">
                                            <li>Changing room: ${item.changingRoom}</li>
                                        </c:if>
                                        <c:if test="${item.shower}">
                                            <li>Shower: ${item.shower}</li>
                                        </c:if>
                                        <c:if test="${item.lightening}">
                                            <li>Lightening: ${item.lightening}</li>
                                        </c:if>
                                        <li>Capacity: ${item.capacity}</li>
                                        <li>Size: ${item.size}</li>
                                        <li>floorCoverage: ${item.floorCoverage.name}</li>
                                        <li>Tribune capacity: ${item.tribuneCapacity}</li>
                                        <li>Price per hour: ${item.pricePerHour}</li>
                                    </ul>
                                </article>
                                <article class="block">
                                    <header><h2>Other Infrastructure Features</h2></header>
                                    <p>
                                            ${item.otherInfrastructureFeatures}
                                    </p>
                                </article>
                                <!-- /.block -->
                                <%--<article class="block">--%>
                                    <%--<header><h2>Opening Hours</h2></header>--%>
                                    <%--<dl class="lines">--%>
                                        <%--<dt>Monday</dt>--%>
                                        <%--<dd>08:00 am - 11:00 pm</dd>--%>
                                        <%--<dt>Tuesday</dt>--%>
                                        <%--<dd>08:00 am - 11:00 pm</dd>--%>
                                        <%--<dt>Wednesday</dt>--%>
                                        <%--<dd>08:00 am - 11:00 pm</dd>--%>
                                        <%--<dt>Thursday</dt>--%>
                                        <%--<dd>08:00 am - 11:00 pm</dd>--%>
                                        <%--<dt>Friday</dt>--%>
                                        <%--<dd>08:00 am - 11:00 pm</dd>--%>
                                        <%--<dt>Saturday</dt>--%>
                                        <%--<dd>08:00 am - 11:00 pm</dd>--%>
                                    <%--</dl>--%>
                                <%--</article>--%>
                                <!-- /.block -->
                            </section>
                            <!--Reviews-->
                            <%--<section class="block" id="reviews">--%>
                                <%--<header class="clearfix">--%>
                                    <%--<h2 class="pull-left">Reviews</h2>--%>
                                    <%--<a href="#write-review" class="btn framed icon pull-right roll">Write a review <i class="fa fa-pencil"></i></a>--%>
                                <%--</header>--%>
                                <%--<article class="clearfix overall-rating">--%>
                                    <%--<strong class="pull-left">Over Rating</strong>--%>
                                    <%--<figure class="rating big color pull-right" data-rating="4"></figure>--%>
                                    <%--<!-- /.rating -->--%>
                                <%--</article><!-- /.overall-rating-->--%>
                                <%--<section class="reviews">--%>
                                    <%--<article class="review">--%>
                                        <%--<figure class="author">--%>
                                            <%--<img src="assets/img/default-avatar.png" alt="">--%>
                                            <%--<div class="date">12.05.2014</div>--%>
                                        <%--</figure>--%>
                                        <%--<!-- /.author-->--%>
                                        <%--<div class="wrapper">--%>
                                            <%--<h5>Catherine Brown</h5>--%>
                                            <%--<figure class="rating big color" data-rating="4"></figure>--%>
                                            <%--<p>--%>
                                                <%--Lorem ipsum dolor sit amet, consectetur adipiscing elit.--%>
                                                <%--Nulla vestibulum, sem ut sollicitudin consectetur, augue diam ornare massa,--%>
                                                <%--ac vehicula leo turpis eget purus. Nunc pellentesque vestibulum mauris,--%>
                                                <%--eget suscipit mauris imperdiet vel. Nulla et massa metus.--%>
                                            <%--</p>--%>
                                            <%--<div class="individual-rating">--%>
                                                <%--<span>Value</span>--%>
                                                <%--<figure class="rating" data-rating="4"></figure>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.user-rating -->--%>
                                            <%--<div class="individual-rating">--%>
                                                <%--<span>Service</span>--%>
                                                <%--<figure class="rating" data-rating="4"></figure>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.user-rating -->--%>
                                        <%--</div>--%>
                                        <%--<!-- /.wrapper-->--%>
                                    <%--</article>--%>
                                    <%--<!-- /.review -->--%>
                                    <%--<article class="review">--%>
                                        <%--<figure class="author">--%>
                                            <%--<img src="assets/img/default-avatar.png" alt="">--%>
                                            <%--<div class="date">10.05.2014</div>--%>
                                        <%--</figure>--%>
                                        <%--<!-- /.author-->--%>
                                        <%--<div class="wrapper">--%>
                                            <%--<h5>John Doe</h5>--%>
                                            <%--<figure class="rating big color" data-rating="5"></figure>--%>
                                            <%--<p>--%>
                                                <%--Nunc pellentesque vestibulum mauris, eget suscipit mauris--%>
                                                <%--imperdiet vel. Nulla et massa metus. Nam porttitor quam eget ante--%>
                                            <%--</p>--%>
                                            <%--<div class="individual-rating">--%>
                                                <%--<span>Value</span>--%>
                                                <%--<figure class="rating" data-rating="5"></figure>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.user-rating -->--%>
                                            <%--<div class="individual-rating">--%>
                                                <%--<span>Service</span>--%>
                                                <%--<figure class="rating" data-rating="5"></figure>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.user-rating -->--%>
                                        <%--</div>--%>
                                        <%--<!-- /.wrapper-->--%>
                                    <%--</article>--%>
                                    <%--<!-- /.review -->--%>
                                <%--</section>--%>
                                <%--<!-- /.reviews-->--%>
                            <%--</section>--%>
                            <!-- /#reviews -->
                            <!--end Reviews-->
                            <!--Review Form-->
                            <%--<section id="write-review">--%>
                                <%--<header>--%>
                                    <%--<h2>Write a Review</h2>--%>
                                <%--</header>--%>
                                <%--<form id="form-review" role="form" method="post" action="?" class="background-color-white">--%>
                                    <%--<div class="row">--%>
                                        <%--<div class="col-md-8">--%>
                                            <%--<div class="form-group">--%>
                                                <%--<label for="form-review-name">Name</label>--%>
                                                <%--<input type="text" class="form-control" id="form-review-name" name="form-review-name" required="">--%>
                                            <%--</div>--%>
                                            <%--<!-- /.form-group -->--%>
                                            <%--<div class="form-group">--%>
                                                <%--<label for="form-review-email">Email</label>--%>
                                                <%--<input type="email" class="form-control" id="form-review-email" name="form-review-email" required="">--%>
                                            <%--</div>--%>
                                            <%--<!-- /.form-group -->--%>
                                            <%--<div class="form-group">--%>
                                                <%--<label for="form-review-message">Message</label>--%>
                                                <%--<textarea class="form-control" id="form-review-message" name="form-review-message"  rows="3" required=""></textarea>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.form-group -->--%>
                                            <%--<div class="form-group">--%>
                                                <%--<button type="submit" class="btn btn-default">Submit Review</button>--%>
                                            <%--</div>--%>
                                            <%--<!-- /.form-group -->--%>
                                        <%--</div>--%>
                                        <%--<div class="col-md-4">--%>
                                            <%--<aside class="user-rating">--%>
                                                <%--<label>Value</label>--%>
                                                <%--<figure class="rating active" data-name="value"></figure>--%>
                                            <%--</aside>--%>
                                            <%--<aside class="user-rating">--%>
                                                <%--<label>Service</label>--%>
                                                <%--<figure class="rating active" data-name="score"></figure>--%>
                                            <%--</aside>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</form>--%>
                                <%--<!-- /.main-search -->--%>
                            <%--</section>--%>
                            <!--end Review Form-->
                        </div>
                        <!-- /.col-md-8-->
                    </div>
                    <!-- /.row -->
                </section>
                <!-- /#main-content-->
            </div>
            <!-- /.col-md-8-->
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
        </div><!-- /.row-->
    </jsp:body>
</t:base>