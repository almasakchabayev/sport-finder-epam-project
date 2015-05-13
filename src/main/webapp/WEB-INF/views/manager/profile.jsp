<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base>
    <jsp:body>
        <header>
            <ul class="nav nav-pills">
                <li class="active"><a href="<c:url value="/manager/profile" />"><h1 class="page-title">${user.firstName} ${user.lastName}</h1></a></li>
                <li><a href="<c:url value="/manager/items" />"><h1 class="page-title">My Items</h1></a></li>
            </ul>
        </header>
        <div class="row">
            <div class="col-md-9">
                <c:if test="${success != null}">
                    <div><span>${success}</span></div>
                </c:if>
                <form id="form-profile" role="form" method="post" action="<c:url value="/manager/profile" />" enctype="multipart/form-data">
                    <div class="row">
                        <!--Profile Picture-->
                        <div class="col-md-3 col-sm-3">
                            <section>
                                <h3><i class="fa fa-image"></i>Profile Picture</h3>
                                <article class="item-gallery">
                                    <!-- /.item-slider -->
                                    <div class="thumbnails">
                                        <div id="gallery-thumbnails">
                                            <div class="content">
                                                <c:if test="${user.image != null}">
                                                    <a href="<c:url value="/image?id=${user.image.id}" />" id="thumbnail-${image.id}">
                                                        <img src="<c:url value="/image?id=${user.image.id}" />" />
                                                    </a>
                                                </c:if>
                                                <c:if test="${errors.containsKey('image')}">
                                                    <span class="error">${errors.get('image')}</span>
                                                </c:if>
                                                <input type="file" name="profile-picture" accept="image/jpeg,image/gif,image/png" />
                                            </div>
                                        </div>
                                    </div>
                                </article>
                            </section>
                        </div>
                        <!--/.col-md-3-->

                        <!--Contact Info-->
                        <div class="col-md-9 col-sm-9">
                            <section>
                                <h3><i class="fa fa-user"></i>Personal Info</h3>
                                <div class="row">
                                    <div class="col-md-6 col-sm-6">
                                        <div class="form-group">
                                            <label for="first-name">$</label>
                                            <c:if test="${errors.containsKey('firstName')}">
                                                <span class="error">${errors.get('firstName')}</span>
                                            </c:if>
                                            <input type="text" class="form-control" id="first-name" name="first-name" value="${user.firstName}">
                                        </div>
                                        <!--/.form-group-->
                                    </div>
                                    <div class="col-md-6 col-sm-6">
                                        <div class="form-group">
                                            <label for="last-name">$</label>
                                            <c:if test="${errors.containsKey('lastName')}">
                                                <span class="error">${errors.get('lastName')}</span>
                                            </c:if>
                                            <input type="text" class="form-control" id="last-name" name="last-name" value="${user.lastName}">
                                        </div>
                                        <!--/.form-group-->
                                    </div>
                                    <!--/.col-md-3-->
                                    <div class="col-md-6 col-sm-6">
                                        <div class="form-group">
                                            <label for="email">Email</label>
                                            <c:if test="${errors.containsKey('email')}">
                                                <span class="error">${errors.get('email')}</span>
                                            </c:if>
                                            <input type="email" class="form-control" id="email" name="email" value="${user.email}">
                                        </div>
                                        <!--/.form-group-->
                                    </div>
                                    <!--/.col-md-3-->
                                    <div class="col-md-6 col-sm-6">
                                        <div class="form-group">
                                            <label for="phone-number">Mobile</label>
                                            <c:if test="${errors.containsKey('phoneNumbers[0].number')}">
                                                <span class="error">${errors.get('phoneNumbers[0].number')}</span>
                                            </c:if>
                                            <input type="text" class="form-control" id="phone-number" name="phone-number" pattern="\d*" value="${user.phoneNumbers.get(0).number}">
                                        </div>
                                        <!--/.form-group-->
                                    </div>
                                    <!--/.col-md-3-->
                                    <div class="col-md-6 col-sm-6">
                                        <div class="form-group">
                                            <label for="phone-number-1">Home</label>
                                            <input type="text" class="form-control" id="phone-number-1" name="phone-number-1" pattern="\d*"
                                                   value="<c:if test="${manager.phoneNumbers.size() >= 2}">${manager.phoneNumbers.get(1).number}</c:if>">
                                        </div>
                                        <!--/.form-group-->
                                    </div>
                                    <!--/.col-md-3-->
                                </div>
                            </section>
                            <section>
                                <h3><i class="fa fa-map-marker"></i>Company</h3>
                                <div class="form-group">
                                    <label for="company-name">Name</label>
                                    <c:if test="${errors.containsKey('company.name')}">
                                        <span class="error">${errors.get('company.name')}</span>
                                    </c:if>
                                    <input type="text" class="form-control" id="company-name" name="company-name" value="${user.company.name}">
                                </div>
                                <div class="form-group">
                                    <label for="country">Country</label>
                                    <c:if test="${errors.containsKey('company.address.country')}">
                                        <span class="error">${errors.get('company.address.country')}</span>
                                    </c:if>
                                    <input type="text" class="form-control" id="country" name="country" value="${user.company.address.country}">
                                </div>
                                <!--/.form-group-->
                                <div class="form-group">
                                    <label for="city">City</label>
                                    <c:if test="${errors.containsKey('company.address.city')}">
                                        <span class="error">${errors.get('company.address.city')}</span>
                                    </c:if>
                                    <input type="text" class="form-control" id="city" name="city" value="${user.company.address.city}">
                                </div>
                                <!--/.form-group-->
                                <div class="row">
                                    <div class="col-md-8 col-sm-8">
                                        <div class="form-group">
                                            <label for="address-line-1">Address Line 1</label>
                                            <c:if test="${errors.containsKey('company.address.addressLine1')}">
                                                <span class="error">${errors.get('company.address.addressLine1')}</span>
                                            </c:if>
                                            <input type="text" class="form-control" id="address-line-1" name="address-line-1" value="${user.company.address.addressLine1}">
                                        </div>
                                        <!--/.form-group-->
                                    </div>
                                    <div class="col-md-8 col-sm-8">
                                        <div class="form-group">
                                            <label for="address-line-2">Address Line 2</label>
                                            <c:if test="${errors.containsKey('company.address.addressLine2')}">
                                                <span class="error">${errors.get('company.address.addressLine2')}</span>
                                            </c:if>
                                            <input type="text" class="form-control" id="address-line-2" name="address-line-2" value="${user.company.address.addressLine2}">
                                        </div>
                                        <!--/.form-group-->
                                    </div>
                                    <!--/.col-md-8-->
                                    <div class="col-md-4 col-sm-4">
                                        <div class="form-group">
                                            <label for="zipcode">zipcode</label>
                                            <c:if test="${errors.containsKey('company.address.zipcode')}">
                                                <span class="error">${errors.get('company.address.zipcode')}</span>
                                            </c:if>
                                            <input type="text" class="form-control" id="zipcode" name="zipcode" value="${user.company.address.zipcode}">
                                        </div>
                                        <!--/.form-group-->
                                    </div>
                                </div>
                                <!--/.col-md-3-->
                                <%--<div class="form-group">--%>
                                    <%--<label for="additional-address">Additional Address Line</label>--%>
                                    <%--<input type="text" class="form-control" id="additional-address" name="additional-address">--%>
                                <%--</div>--%>
                                <%--<!--/.form-group-->--%>
                            </section>
                            <%--<section>--%>
                                <%--<h3><i class="fa fa-info-circle"></i>About Me</h3>--%>
                                <%--<div class="form-group">--%>
                                    <%--<label for="about-me">Some Words About Me</label>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<textarea class="form-control" id="about-me" rows="3" name="about-me" required></textarea>--%>
                                    <%--</div>--%>
                                    <%--<!--/.form-group-->--%>
                                <%--</div>--%>
                                <%--<!--/.form-group-->--%>
                            <%--</section>--%>
                            <div class="form-group">
                                <button type="submit" class="btn btn-large btn-default" id="submit">Save Changes</button>
                            </div>
                            <!-- /.form-group -->
                        </div>
                        <!--/.col-md-6-->
                    </div>
                </form>
            </div>
            <!--Password-->
            <div class="col-md-3 col-sm-9">
                <h3><i class="fa fa-asterisk"></i>Password Change</h3>
                <form class="framed" id="form-password" role="form" method="post" action="?" >
                    <div class="form-group">
                        <label for="current-password">Current Password</label>
                        <input type="password" class="form-control" id="current-password" name="current-password">
                    </div>
                    <!--/.form-group-->
                    <div class="form-group">
                        <label for="new-password">New Password</label>
                        <input type="password" class="form-control" id="new-password" name="new-password">
                    </div>
                    <!--/.form-group-->
                    <div class="form-group">
                        <label for="confirm-new-password">Confirm New Password</label>
                        <input type="password" class="form-control" id="confirm-new-password" name="confirm-new-password">
                    </div>
                    <!--/.form-group-->
                    <div class="form-group">
                        <button type="submit" class="btn btn-default">Change Password</button>
                    </div>
                    <!-- /.form-group -->
                </form>
            </div>
            <!-- /.col-md-3-->
        </div>
    </jsp:body>
</t:base>