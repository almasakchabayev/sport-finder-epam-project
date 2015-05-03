<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base>
    <jsp:body>
        <div class="block">
            <div class="row">
                <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                    <header>
                        <h1 class="page-title">Register As</h1>
                    </header>
                    <hr>
                    <div>
                        <a href="<c:url value="/customer/register" />">
                            <h2 class="page-title">Customer</h2>
                        </a>
                        <a href="<c:url value="/manager/register" />">
                            <h2 class="page-title">Manager</h2>
                        </a>
                    </div><!-- /.form-group -->
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>