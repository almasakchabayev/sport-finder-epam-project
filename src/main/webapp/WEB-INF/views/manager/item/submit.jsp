<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base>
    <jsp:body>
        <div class="row">
            <!--Content-->
            <div class="col-md-9">
                <header>
                    <h1 class="page-title">Submit Sport Place</h1>
                </header>
                <%--todo put ?id=$id} in here , then in the hidden input in the form--%>
                <form id="form-submit" role="form" method="post" action="<c:url value="/manager/item/submit"/>" enctype="multipart/form-data">
                    <t:itemform />
                    <hr>
                    <section>
                        <%--<figure class="pull-left margin-top-15">--%>
                            <%--<p>By clicking “Submit & Pay” button you agree with <a href="terms-conditions.html" class="link">Terms & Conditions</a></p>--%>
                        <%--</figure>--%>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default pull-right" id="submit">Submit</button>
                        </div>
                        <!-- /.form-group -->
                    </section>
                </form>
                <!--/#form-submit-->
            </div>
            <!--/.col-md-9-->
            <!--Sidebar-->
            <div class="col-md-3">
                <%--<aside id="sidebar">--%>
                    <%--<div class="sidebar-box">--%>
                        <%--<h3>Payment</h3>--%>
                        <%--<div class="form-group">--%>
                            <%--<label for="package">Your Package</label>--%>
                            <%--<select name="package" id="package" class="framed">--%>
                                <%--<option value="">Select your package</option>--%>
                                <%--<option value="1">Free</option>--%>
                                <%--<option value="2">Silver</option>--%>
                                <%--<option value="3">Gold</option>--%>
                                <%--<option value="4">Platinum</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                        <%--<!-- /.form-group -->--%>
                        <%--<h4>This package includes</h4>--%>
                        <%--<ul class="bullets">--%>
                            <%--<li>1 Property</li>--%>
                            <%--<li>1 Agent Profile</li>--%>
                            <%--<li class="disabled">Agency Profile</li>--%>
                            <%--<li class="disabled">Featured Properties</li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</aside>--%>
                <!-- /#sidebar-->
            </div>
            <!-- /.col-md-3-->
            <!--end Sidebar-->
        </div>
    </jsp:body>
</t:base>