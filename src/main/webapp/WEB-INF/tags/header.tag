<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Navigation-->
<div class="header">
    <div class="wrapper">
        <div class="brand">
            <a href="index-directory.html"><img src="../../assets/img/logo.png" alt="logo"></a>
        </div>
        <nav class="navigation-items">
            <div class="wrapper">
                <ul class="main-navigation navigation-top-header"></ul>
                <%--TODO: if logged in header shows this--%>
                <%--<ul class="user-area">--%>
                    <%--<li><a href="profile.html"><strong>John Doe</strong></a></li>--%>
                    <%--<li><a href="my-items.html">My Items</a></li>--%>
                    <%--<li><a href="#"><i class="fa fa-cog"></i></a></li>--%>
                <%--</ul>--%>
                <ul class="user-area">
                    <li><a href="sign-in.html">Sign In</a></li>
                    <li><a href="register.html"><strong>Register</strong></a></li>
                </ul>
                <a href="submit.html" class="submit-item">
                    <div class="content"><span>Submit Your Item</span></div>
                    <div class="icon">
                        <i class="fa fa-plus"></i>
                    </div>
                </a>
                <div class="toggle-navigation">
                    <div class="icon">
                        <div class="line"></div>
                        <div class="line"></div>
                        <div class="line"></div>
                    </div>
                </div>
            </div>
        </nav>
    </div>
</div>
<!-- end Navigation-->