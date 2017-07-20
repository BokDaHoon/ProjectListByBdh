<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String cp = request.getContextPath(); %> <%--ContextPath 선언 --%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Lotte.com</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <!-- Favicon -->
    <link rel="shortcut icon" href="<%=cp%>/resources/bootstrap/img/favicon.ico" type="image/x-icon">
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/plugins/bootstrap/css/bootstrap.min.css">
    <!-- Fonts  -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/simple-line-icons.css">
    <!-- CSS Animate -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/animate.css">
    <!-- Daterange Picker -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/plugins/daterangepicker/daterangepicker-bs3.css">
    <!-- Calendar demo -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/clndr.css">
    <!-- Switchery -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/plugins/switchery/switchery.min.css">
    <!-- Custom styles for this theme -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/main.css">
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/custom.css">
    <!-- Feature detection -->
    <script src="<%=cp%>/resources/bootstrap/js/vendor/modernizr-2.6.2.min.js"></script>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=cp%>/resources/bootstrap/js/vendor/html5shiv.js"></script>
    <script src="<%=cp%>/resources/bootstrap/js/vendor/respond.min.js"></script>
    <![endif]-->
</head>

<body>
    <section id="main-wrapper" class="theme-red">
        <jsp:include page="head_menu.jsp"/>
        <!--sidebar left start-->
        <jsp:include page="aside_menu.jsp"/>
        <!--sidebar left end-->
        <!--main content start-->
        <section class="main-content-wrapper">
            <div class="pageheader">
                <h1>고객님에게 추천하는 상품</h1>
            </div>
            <section id="main-content" class="animated fadeInUp">
                <div class="row">
	                <c:forEach items="${productList}" var="status">
	                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-3">
	                        <div class="panel panel-default browser-chart">
	                            <div class="panel-heading">
	                                <h4>${status.PRD_NM}</h4><br/>
	                               	<h5>판매가 : <fmt:formatNumber value="${status.PRICE}" pattern="#,###원" /></h5>
	                            </div>
	                            <div class="panel-image" style="background-image : url(${status.PRD_IMG_URL})">
	                            	&nbsp;
	                            </div>
	                        </div>
	                    </div>
                    </c:forEach>
                </div>
            </section>
        </section>
        <!--main content end-->
    </section>
    <!--sidebar right start-->
    <aside id="sidebar-right">
        <h4 class="sidebar-title">contact List</h4>
        <div id="contact-list-wrapper">
            <div class="heading">
                <ul>
                    <li class="new-contact"><a href="javascript:void(0)"><i class="fa fa-plus"></i></a>
                    </li>
                    <li>
                        <input type="text" class="search" placeholder="Search">
                        <button type="submit" class="btn btn-sm btn-search"><i class="fa fa-search"></i>
                        </button>
                    </li>
                </ul>
            </div>
            <div id="contact-list">
                <ul>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="<%=cp%>/resources/bootstrap/img/avatar3.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Ashley Bell </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Sarasota, FL</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="<%=cp%>/resources/bootstrap/img/avatar1.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Brian Johnson </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> San Francisco, CA</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="<%=cp%>/resources/bootstrap/img/avatar2.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Chris Jones </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Brooklyn, NY</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="<%=cp%>/resources/bootstrap/img/avatar4.jpg" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Erica Hill </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Palo Alto, Ca</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="<%=cp%>/resources/bootstrap/img/avatar5.png" class="img-circle" alt="">
                          <i class="away animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Greg Smith </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> London, UK</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="<%=cp%>/resources/bootstrap/img/avatar6.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Jason Kendall</div>
                                <small class="location text-muted"><i class="icon-pointer"></i> New York, NY </small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="<%=cp%>/resources/bootstrap/img/avatar7.png" class="img-circle" alt="">
                          <i class="on animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Kristen Davis </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Greenville, SC</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="<%=cp%>/resources/bootstrap/img/avatar8.png" class="img-circle off" alt="">
                          <i class="off animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Michael Shepard </div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Vancouver, BC</small>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="row">
                            <div class="col-md-3">
                                <span class="avatar">
                        <img src="<%=cp%>/resources/bootstrap/img/avatar9.png" class="img-circle off" alt="">
                          <i class="off animated bounceIn"></i>
                        </span>
                            </div>
                            <div class="col-md-9">
                                <div class="name">Paul Allen</div>
                                <small class="location text-muted"><i class="icon-pointer"></i> Savannah, GA</small>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div id="contact-user">
                <div class="chat-user active"><span><i class="icon-bubble"></i></span>
                </div>
                <div class="email-user"><span><i class="icon-envelope-open"></i></span>
                </div>
                <div class="call-user"><span><i class="icon-call-out"></i></span>
                </div>
            </div>
        </div>
    </aside>
    <!--/sidebar right end-->
    <!--Config demo-->
    
    <!--/Config demo-->
    <!--Global JS-->
    <script src="<%=cp%>/resources/bootstrap/js/vendor/jquery-1.11.1.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/navgoco/jquery.navgoco.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/pace/pace.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/fullscreen/jquery.fullscreen-min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/js/src/app.js"></script>
    <!--Page Level JS-->
    <script src="<%=cp%>/resources/bootstrap/plugins/countTo/jquery.countTo.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/weather/js/skycons.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/daterangepicker/moment.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- ChartJS  -->
    <script src="<%=cp%>/resources/bootstrap/plugins/chartjs/Chart.min.js"></script>
    <!-- Morris  -->
    <script src="<%=cp%>/resources/bootstrap/plugins/morris/js/morris.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/morris/js/raphael.2.1.0.min.js"></script>
    <!-- Vector Map  -->
    <script src="<%=cp%>/resources/bootstrap/plugins/jvectormap/js/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/jvectormap/js/jquery-jvectormap-world-mill-en.js"></script>
    <!-- Gauge  -->
    <script src="<%=cp%>/resources/bootstrap/plugins/gauge/gauge.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/gauge/gauge-demo.js"></script>
    <!-- Calendar  -->
    <script src="<%=cp%>/resources/bootstrap/plugins/calendar/clndr.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/calendar/clndr-demo.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
    <!-- Switch -->
    <script src="<%=cp%>/resources/bootstrap/plugins/switchery/switchery.min.js"></script>
    <!--Load these page level functions-->
    <script>
    $(document).ready(function() {
        app.dateRangePicker();
        app.chartJs();
        app.weather();
        app.spinStart();
        app.spinStop();
        
        $("#menu").children().eq(0).attr("class", "nav-dropdown open active");//현재 메뉴 표시
		$("#menu").children().eq(0).attr("class", "nav-dropdown open active").children().eq(1).attr("style", "display: block").children().eq(1).attr("class", "active");
		$(".brand").css("background-color", "white");
    });
    </script>
</body>

</html>
