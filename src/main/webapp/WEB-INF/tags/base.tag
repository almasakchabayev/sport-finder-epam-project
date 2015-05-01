<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>

<html lang="en-US">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">


  <link href="<c:url value="/assets/fonts/font-awesome.css" />" rel="stylesheet" type="text/css">
  <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap.css" />" type="text/css">
  <link rel="stylesheet" href="<c:url value="/assets/css/bootstrap-select.min.css" />" type="text/css">
  <link rel="stylesheet" href="<c:url value="/assets/css/owl.carousel.css" />" type="text/css">
  <link rel="stylesheet" href="<c:url value="/assets/css/style.css" />" type="text/css">
  <link rel="stylesheet" href="<c:url value="/assets/css/user.style.css" />" type="text/css">

  <title>Sport Finder</title>

</head>

<body onunload="" class="page-subpage navigation-top-header" id="page-top">

<!-- Outer Wrapper-->
<div id="outer-wrapper">
  <!-- Inner Wrapper -->
  <div id="inner-wrapper">
    <t:header />
    <!-- Page Canvas-->
    <div id="page-canvas">
      <!--Off Canvas Navigation-->
      <nav class="off-canvas-navigation">
        <header>Navigation</header>
        <div class="main-navigation navigation-off-canvas"></div>
      </nav>
      <!--end Off Canvas Navigation-->

      <!--Sub Header-->
      <section class="sub-header">
        <div class="search-bar horizontal collapse" id="redefine-search-form"></div>
        <!-- /.search-bar -->
        <div class="breadcrumb-wrapper">
          <div class="container">
            <div class="redefine-search">
              <a href="#redefine-search-form" class="inner" data-toggle="collapse" aria-expanded="false" aria-controls="redefine-search-form">
                <span class="icon"></span>
                <span>Redefine Search</span>
              </a>
            </div>
            <ol class="breadcrumb">
              <li><a href="index-directory.html"><i class="fa fa-home"></i></a></li>
              <li><a href="#">Page</a></li>
              <li class="active">Detail</li>
            </ol>
            <!-- /.breadcrumb-->
          </div>
          <!-- /.container-->
        </div>
        <!-- /.breadcrumb-wrapper-->
      </section>
      <!--end Sub Header-->

      <!--Page Content-->
      <div id="page-content">
        <section class="container">
          <jsp:doBody />
        </section>
        <!-- /.block-->
      </div>
      <!-- end Page Content-->
    </div>
    <!-- end Page Canvas-->
    <t:footer />
  </div>
  <!-- end Inner Wrapper -->
</div>
<!-- end Outer Wrapper-->

<script type="text/javascript" src="<c:url value="/assets/js/jquery-2.1.0.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/before.load.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/jquery-migrate-1.2.1.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/smoothscroll.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/bootstrap-select.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/jquery.hotkeys.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/icheck.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/custom.js" />"></script>

<script>
  $(window).load(function(){
    var rtl = false; // Use RTL
    initializeOwl(rtl);
  });
</script>

<!--[if lte IE 9]>
<script type="text/javascript" src="assets/js/ie-scripts.js"></script>
<![endif]-->
</body>
</html>