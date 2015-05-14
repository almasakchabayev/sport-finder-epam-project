<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
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
  <link rel="stylesheet" href="<c:url value="/assets/css/dropzone.css" />" type="text/css">
  <link rel="stylesheet" href="<c:url value="/assets/css/jquery.ui.timepicker.css" />" type="text/css">
  <link rel="stylesheet" href="<c:url value="/assets/css/style.css" />" type="text/css">
  <link rel="stylesheet" href="<c:url value="/assets/css/user.style.css" />" type="text/css">

  <title>Sport Finder</title>

</head>

<body onunload="" class="page-subpage" id="page-top">

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
<script type="text/javascript" src="<c:url value="/assets/js/jquery-ui.min.js" />"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&amp;libraries=places"></script>
<script type="text/javascript" src="<c:url value="/assets/js/richmarker-compiled.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/jquery-migrate-1.2.1.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/smoothscroll.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/bootstrap-select.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/jquery.hotkeys.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/icheck.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/dropzone.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/jquery.ui.timepicker.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/custom.js" />"></script>
<script type="text/javascript" src="<c:url value="/assets/js/maps.js" />"></script>

<script>
  $(window).load(function(){
    var rtl = false; // Use RTL
    initializeOwl(rtl);

    var _latitude = 51.541599;
    var _longitude = -0.112588;
    var draggableMarker = true;
    simpleMap(_latitude, _longitude,draggableMarker);
  });
  autoComplete();
</script>

<!--[if lte IE 9]>
<script type="text/javascript" src="assets/js/ie-scripts.js"></script>
<![endif]-->
</body>
</html>