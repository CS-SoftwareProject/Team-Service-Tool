<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- This is a starter template page. Use this page to start your new project from scratch. This page gets rid of all links and provides the needed markup only. -->
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Team_project Tool</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="/resources/bower_components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="/resources/bower_components/Ionicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="/resources/dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect. -->
  <link rel="stylesheet" href="/resources/dist/css/skins/skin-black.min.css">
  
  <!-- TotalStyle -->
  <link href="/stylesheets/style.css?" rel="stylesheet" type="text/css">
  
  <!-- Morris charts -->
  <link rel="stylesheet" href="/resources/bower_components/morris.js/morris.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

<!-- Google Font -->
<link rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-black sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <header class="main-header">

    <!-- Logo -->
    <a href="/main.jsp" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>TT</b></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>T</b>eam_project  <b>T</b>ool</span>
    </a> 

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- Messages: style can be found in dropdown.less-->
          <li class="dropdown messages-menu">
            <!-- Menu toggle button -->
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-envelope-o"></i>
              <span class="label label-success">4</span>
            </a>
            <ul class="dropdown-menu">
              <li class="header">You have 4 messages</li>
              <li>
                <!-- inner menu: contains the messages -->
                <ul class="menu">
                  <li><!-- start message -->
                    <a href="#">
                      <div class="pull-left">
                        <!-- User Image -->
                        <img src="/resources/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                      </div>
                      <!-- Message title and timestamp -->
                      <h4>
                        Support Team
                        <small><i class="fa fa-clock-o"></i> 5 mins</small>
                      </h4>
                      <!-- The message -->
                      <p>Why not buy a new awesome theme?</p>
                    </a>
                  </li>
                  <!-- end message -->
                </ul>
                <!-- /.menu -->
              </li>
              <li class="footer"><a href="#">See All Messages</a></li>
            </ul>
          </li>
          <!-- /.messages-menu -->

          <!-- User Account Menu -->
          <li class="dropdown user user-menu">
            <!-- Menu Toggle Button -->
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <!-- The user image in the navbar-->
              <c:set var = "imgPath" value ="/upload_image/default.png" />
              <c:if test = "${not empty user.image}">
              <c:set var = "imgPath" value = "${user.image}" />
              </c:if>
              <img src="${imgPath}" class="user-image" alt="User Image">
              <!-- hidden-xs hides the username on small devices so only the image appears. -->
              <span class="hidden-xs">${user.userId }</span>
            </a>
            <ul class="dropdown-menu">
              <!-- The user image in the menu -->
              <li class="user-header">
                <img src="${user.image }" class="img-circle" alt="User Image">

                <p>
                  ${user.name }
                  <small>출생: ${user.birth } </small>
                </p>
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="/users/updateForm" class="btn btn-default btn-flat">내정보 수정</a>
                </div>
                <div class="pull-right">
                  <a href="/users/logout" class="btn btn-default btn-flat"> 로그아웃 </a>
                </div>
              </li>
            </ul>
          </li>
          
          <c:if test="${isReadBoard}">
	          <!-- Control Sidebar Toggle Button -->
	          <li>
	            <a href="#" data-toggle="control-sidebar"><i class="glyphicon glyphicon-option-horizontal"></i></a>
	          </li>
          </c:if>
          
        </ul>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar user panel (optional) -->
      <div class="user-panel" style="margin-top: -25%;">
        <div class="pull-left image">
          <img src="${imgPath}" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${user.userId }</p>
          <!-- Status -->
          <i class="fa fa-circle text-success"></i> 접속중
        </div>
      </div>

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">MENU</li>
        <!-- Optionally, you can add icons to the links -->
        <li class="active"><a href="/users/userDashBoard"><i class="glyphicon glyphicon-dashboard"></i> <span>개인 대시보드</span></a></li>
        <li><a href="/project/projectlist"><i class="fa fa-list-ol"></i><span>프로젝트 목록</span></a></li>
        
      </ul>
      
      <c:if test="${isReadBoard}">
      <!-- Sidebar Menu 2 -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">프로젝트 명 : ${projectName}</li>
        <!-- Optionally, you can add icons to the links -->
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i><span>프로젝트 관리</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
	        <li><a href="/project/projectDashBoard"><i class="fa fa-circle-o"></i>프로젝트 대시보드</a></li>
	        <c:if test="${not empty ganttList}">
            <li class="treeview">
             <a href="#"><i class="fa fa-list-ol"></i><span>간트 차트</span>
               <span class="pull-right-container">
             	 <i class="fa fa-angle-left pull-right"></i>
         	   </span>
       	   	 </a>
       	   	 	 <ul class="treeview-menu">
       	   	 	 <c:forEach var="list" items="${ganttList}" varStatus="status">
       	   	 		<li><a href="/gantts/loadGantt?ganttNum=${list.ganttNum}"><i class="fa fa-circle-o"></i>${list.boardName}</a></li>
       	   	 	 </c:forEach>
       	   	 	 </ul>
            </li>
            </c:if>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu 2-->
      </c:if>
    </section>
    <!-- /.sidebar -->
    <!-- 업로드 페이지 임폴팅-->
  </aside>

<script>
function addImage(){
	$('#uploadmodal').modal('show');
}

/* $('.sidebar li').click(function(){
    $('.sidebar li').removeClass('active');
    $(this).addClass('active');
});
 */</script>

<!-- jQuery 3 -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

