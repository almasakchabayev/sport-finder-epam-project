<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% response.setHeader("Refresh", "10;url=/manager/items"); %>

<t:base>
    <jsp:body>
        <div class="block">
            <div class="row">
                <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                    <header>
                        <h1 class="page-title">Congratulation! You have been registered successfully</h1>
                    </header>
                    <hr>
                    <div>
                        <h2>You will soon be redirected to manager's home page</h2>
                    </div><!-- /.form-group -->
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>