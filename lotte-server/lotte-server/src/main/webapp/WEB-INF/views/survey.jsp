<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>NeuBoard</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <!-- Favicon -->
    <link rel="shortcut icon" href="<%=cp%>/resources/bootstrap/img/favicon.ico" type="image/x-icon">
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/plugins/bootstrap/css/bootstrap.min.css">
    <!-- Fonts  -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/simple-line-icons.css">
    <!-- Switchery -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/plugins/switchery/switchery.min.css">
    <!-- CSS Animate -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/animate.css">
    <!--Page Level CSS-->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/plugins/icheck/css/all.css">
    <!-- Custom styles for this theme -->
    <link rel="stylesheet" href="<%=cp%>/resources/bootstrap/css/main.css">
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
                <h1>Survey</h1>
            </div>
            <section id="main-content" class="animated fadeInUp">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">신체 정보</h3>
                            </div>
                            <div class="panel-body">
                                <form class="form-horizontal form-border">
	                                <c:forEach items="${itemList}" var="status">
	                                    <div class="form-group">
	                                        <label class="col-sm-3 control-label">${status.CODE_NM}</label>
	                                        <div class="col-sm-6">
	                                            <input id="info_${status.CODE_VALUE}" type="text" class="form-control body_info" placeholder="${status.CODE_DESC}">
	                                        </div>
	                                    </div>
	                                </c:forEach>
                                </form>
                                <div class="col-sm-5"></div><button id="body_info_save" type="button" class="btn btn-primary">저장</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">관심 분야</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                    <i class="fa fa-times"></i>
                                </div>
                            </div>
                            <div class="panel-body">
                                <form id="survey_form" class="form-horizontal form-border">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">Inline Checkbox</label>
                                        <div class="col-sm-6">
                                            <label class="checkbox-inline">
                                                <input class="icheck" type="checkbox" checked="" name="rad1">Option 1</label>
                                            <label class="checkbox-inline">
                                                <input class="icheck" type="checkbox" name="rad1">Option 2</label>
                                            <label class="checkbox-inline">
                                                <input class="icheck" type="checkbox" name="rad1">Option 3</label>
                                        </div>
                                    </div>
                                </form>
                                <br/>
                                <div class="col-sm-5"></div><button id="survey_save" type="button" class="btn btn-primary">저장</button>
                            </div>
                        </div>
                    </div>
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
    <!--sidebar right end-->
    <!--Global JS-->
    <script src="<%=cp%>/resources/bootstrap/js/vendor/jquery-1.11.1.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/navgoco/jquery.navgoco.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/icheck/js/icheck.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/switchery/switchery.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/pace/pace.min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/plugins/fullscreen/jquery.fullscreen-min.js"></script>
    <script src="<%=cp%>/resources/bootstrap/js/src/app.js"></script>
    <script src="<%=cp%>/resources/bootstrap/js/src/map.js"></script>
    <script src="<%=cp%>/resources/bootstrap/js/src/stringbuilder.js"></script>
    <script src="http://ajax.cdnjs.com/ajax/libs/json2/20110223/json2.js"></script>
    <!--Page Level JS-->

    <script>
    $(document).ready(function() {
        app.customCheckbox();
        init();
        
        function init() {
        	
        	// 신체치수 확인
        	$.ajax({ 
    			type:"GET", 
    			url: '/survey/bodydata',
    			dataType: "json", 
    			success : function(resData) {
    				for (var index in resData) {
    					$("#info_" + resData[index].CODE_VALUE).val(resData[index].VALUE);					
    				}
    			}, 
    			error : function(xhr, status, e) {
    				alert("신체치수 불러오기 실패");
    			} 
    		});
        	
        	// 설문조사 항목 출력
        	$.ajax({ 
    			type:"GET", 
    			url: '/survey/surveyItem',
    			dataType: "json", 
    			contentType : "application/json; charset=UTF-8",
    			success : function(response) {
    				
    				var map = new Map();
    				var obj, temp;
    				
    				for (var index in response) {
    					obj = response[index];
    					
    					if (map.containsKey(obj.LCLS_ID)) {
    						temp = map.get(obj.LCLS_ID);
    						temp.push(obj);
    						
    						map.put(obj.LCLS_ID, temp);
    					} else {
    						temp = [];
    						temp.push(obj);
    						map.put(obj.LCLS_ID, temp);
    					}
    				}

					var tag = new StringBuilderEx();
    				
					map.forEach(function(key, value) {
						
						tag.append("<div class='form-group'>");
						tag.append("<label class='col-sm-3 control-label'>");
						tag.append(value[0].LCLS_NM)
						tag.append("</label>");
						tag.append("<div class='col-sm-9'>");
						for (var index in value) {
							tag.append("<label class='col-sm-3 checkbox-inline'>");
							tag.append("<input id='");
							tag.append("" + value[index].LCLS_ID + "_" + value[index].MCLS_ID);
							tag.append("' class='icheck' type='checkbox' name='rad1' value='");
							tag.append("" + value[index].LCLS_ID + "_" + value[index].MCLS_ID);
							tag.append("'>");
							tag.append("&nbsp;&nbsp;" + value[index].MCLS_NM);
							tag.append("</label>");
						}
						tag.append("</div>");
						tag.append("</div>");
						
					});
					
					$("#survey_form").html(tag.toString());
					checkSurveyItem();
    				
    			}, 
    			error : function(xhr, status, e) {
    				alert("설문항목 불러오기 실패");
    			} 
    		});
        	
        }
        	
       	$("#body_info_save").click(function() {
       		var param = [];
       		
       		$(".body_info").each(function() {
       			param.push(
       					  {"codeValue" : $(this).attr("id").substring(5) ,
       					   "value"		: $(this).val()}
				);
       		});
       		
       		console.log(param);
       		
       		$.ajax({ 
				type:"POST", 
				url: '/survey/putBodyData',
				contentType:'application/json',
				data: JSON.stringify(param),
				success : function(resData) {
					alert("성공");
					console.log(resData); 
				}, 
				error : function(xhr, status, e) {
					console.log(e);
					alert("실패");
				} 
			});
       	});
       	
       	$("#survey_save").click(function() {
       		
       		var param = [];
       		
       		$(".checked").each(function() {
       			param.push($(this).children().eq(0).val());
       		});
       		
       		console.log(param);
       		
       		$.ajax({ 
				type:"POST", 
				url: '/survey/putSurveyData',
				contentType:'application/json',
				data: JSON.stringify(param),
				success : function(resData) {
					alert("성공");
					console.log(resData); 
				}, 
				error : function(xhr, status, e) {
					console.log(e);
					alert("실패");
				} 
			});
       	});
       	
       	function checkSurveyItem() {
    		// 설문조사 선택했던 항목들 체크
        	$.ajax({ 
    			type:"GET", 
    			url: '/survey/checkSurveyItem',
    			dataType: "json", 
    			contentType : "application/json; charset=UTF-8",
    			success : function(resData) {
					
    				var obj;
    				
    				for (var index in resData) {
    					obj = resData[index];
    					var target_id = "#" + obj.LCLS_ID + "_" + obj.MCLS_ID;
    					
    					$(target_id).attr("checked", true);
    				}

    				app.customCheckbox();

    			}, 
    			error : function(xhr, status, e) {
    				console.log(e);
    				alert("구매건수 불러오기 실패");
    			} 
    		});
    	}
       	
       	$("#menu").children().eq(1).attr("class", "nav-dropdown open active");//현재 메뉴 표시
		$("#menu").children().eq(1).attr("class", "nav-dropdown open active").children().eq(1).attr("style", "display: block").children().eq(1).attr("class", "active");
       	$(".brand").css("background-color", "white");
        
    });
    </script>
</body>

</html>