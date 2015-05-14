<%@ attribute name="deleted" %>
<%@ attribute name="item" required ="true" type="com.epam.aa.sportfinder.model.SportPlace"%>
<%@ tag description="Overall Page template" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="item list admin-view">
    <div class="image">
        <div class="quick-view" data-toggle="modal" data-target="#modal-bar"><i class="fa fa-eye"></i><span>Quick View</span></div>
        <a href="<c:url value="/item/detail?id=${item.id}" />">
            <div class="overlay">
                <div class="inner">
                    <div class="content">
                        <h4>Description</h4>
                        <p>Curabitur odio nibh, luctus non pulvinar a, ultricies ac diam. Donec neque massa</p>
                    </div>
                </div>
            </div>
            <div class="item-specific">
                <span title="capacity"><img src="assets/img/bedrooms.png" alt="">${item.capacity}</span>
                <span title="size"><img src="assets/img/bathrooms.png" alt="">${item.size}</span>
                <span title="indoor"><img src="assets/img/area.png" alt="">
                    <c:choose>
                        <c:when test="${item.indoor}">+</c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </span>
                <span title="changing-room"><img src="assets/img/garages.png" alt="">
                    <c:choose>
                        <c:when test="${item.changingRoom}">+</c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </span>
            </div>
            <%--<div class="icon">--%>
                <%--<i class="fa fa-thumbs-up"></i>--%>
            <%--</div>--%>
            <img src="<c:if test="${item.images.size() >= 1}"><c:url value="/image?id=${item.images.get(0).id}" /></c:if>" alt="">
        </a>
    </div>
    <div class="wrapper">
        <a href="<c:url value="/item/detail?id=${item.id}" />">
            <h3><% request.setAttribute("prefix", ""); %>
                <c:forEach items="${item.sports}" var="sport">
                    ${prefix}
                    ${sport.name}
                    <% request.setAttribute("prefix", "/"); %>
                </c:forEach>
            </h3>
        </a>
        <figure>${item.address.addressLine2}, ${item.address.addressLine1}</figure>
        <div class="info">
            <div class="type">
                <i><img src="assets/icons/restaurants-bars/restaurants/restaurant.png" alt=""></i>
                <span>
                    <c:forEach items="${item.sports}" var="sport">
                        ${sport.name}
                    </c:forEach>
                </span>
            </div>
            <%--<div class="rating" data-rating="4"></div>--%>
            <div class="item-description">
                <span>${item.description}</span>
            </div>
        </div>
    </div>
    <div class="description">
        <ul class="list-unstyled actions">
            <li><a href="<c:url value="/manager/item/submit?id=${item.id}" />"><i class="fa fa-pencil"></i></a></li>
            <li><a href="<c:url value="/item/detail?id=${item.id}" />" class="hide-item"><i class="fa fa-eye"></i></a></li>
            <c:choose>
                <c:when test="${deleted.equals('true')}">
                    <li><a href="<c:url value="/manager/item/undelete?id=${item.id}" />"><i class="fa fa-trash"></i></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="<c:url value="/manager/item/delete?id=${item.id}" />"><i class="fa fa-trash"></i></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    <div class="ribbon approved">
        <i class="fa fa-check"></i>
    </div>
</div>
<!-- /.item-->