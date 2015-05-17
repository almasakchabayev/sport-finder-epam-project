    <%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Navigation-->
<div class="header">
    <div class="wrapper">
        <div class="brand">
            <a href="<c:url value="/index" />"><img src="<c:url value="/assets/img/logo.png" />" alt="logo"></a>
        </div>
        <nav class="navigation-items">
            <div class="wrapper">
                <ul class="main-navigation navigation-top-header"></ul>
                <c:choose>
                    <c:when test="${sessionScope.user != null && user['class'].simpleName eq 'Manager'}">
                        <ul class="user-area">
                            <li><a href="<c:url value="/manager/profile" />"><strong>${user.firstName} ${user.lastName}</strong></a></li>
                            <li><a href="<c:url value="/manager/items" />">My Items</a></li>
                            <li><a href="<c:url value="/logout" />"><i class="fa fa-sign-out"></i></a></li>
                        </ul>
                    </c:when>
                    <c:when test="${sessionScope.user != null && user['class'].simpleName eq 'Customer'}">
                        <ul class="user-area">
                            <li><a href="<c:url value="/customer/profile" />"><strong>${user.firstName} ${user.lastName}</strong></a></li>
                            <li><a href="<c:url value="/customer/items" />">My Items</a></li>
                            <li><a href="<c:url value="/logout" />"><i class="fa fa-sign-out"></i></a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul class="user-area">
                            <li><a href="<c:url value="/login" />">Sign In</a></li>
                            <li><a href="<c:url value="/register" />"><strong>Register</strong></a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>

                <a href="<c:url value="/manager/item/submit" />" class="submit-item">
                    <div class="content"><span>Submit Your Item</span></div>
                    <div class="icon">
                        <i class="fa fa-plus"></i>
                    </div>
                </a>
                <%--<div class="toggle-navigation">--%>
                    <%--<div class="icon">--%>
                        <%--<div class="line"></div>--%>
                        <%--<div class="line"></div>--%>
                        <%--<div class="line"></div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
        </nav>
    </div>
</div>
<!-- end Navigation-->