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
  
  <link rel="stylesheet" href="/resources/dist/css/skins/skin-black.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="/resources//plugins/iCheck/flat/blue.css">
  
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
  <link href="/resources/startbootstrap/css/freelancer.min.css" rel="stylesheet">
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
    <a href="/users/userDashBoard" class="logo">
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
              <span class="label label-success">${notRead}</span>
            </a>
            <ul class="dropdown-menu">
            <div style="text-align: center;">
              <li class="header" style="margin: 5%; display: inline-flex;">
              <i class="fa fa-quote-left"></i>
              <h4>You have <a class="noRead"></a> messages</h4>
              <i class="fa fa-quote-right"></i>
              </li>
            </div>
              <li id="last-message-area"></li>
              <li class="#"><a href="/users/messagelist">See All Messages</a></li>
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
          
          <c:if test="${isReadCard}">
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
      <div class="user-panel">
        <div class="pull-left image">
          <img src="${imgPath}" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${user.userId }</p>
          <!-- Status -->
          <i class="fa fa-circle text-success">${msgCnt}</i> 접속중
          <script>console.log("하이 : ", ${msgCnt})</script>
        </div>
      </div>

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu tree" data-widget="tree">
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
	        <li><a href="/project/timeline"><i class="fa fa-circle-o"></i>프로젝트 타임라인</a></li>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu 2-->
      </c:if>
      
      <c:if test="${isReadCard}">
      <!-- Sidebar Menu 3 -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">보드 명 : ${board.boardName}</li>
        <!-- Optionally, you can add icons to the links -->
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i><span>보드 관리</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
	        <li><a href="/board/boardlist?projectName=${projectName}"><i class="fa fa-circle-o"></i>보드 목록</a></li>
	        <li><a href="/boards/loglist?boardNum=${board.boardNum}"><i class="fa fa-circle-o"></i>보드 로그</a></li>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu 3-->
      </c:if>
    </section>
    <!-- /.sidebar -->
   	<%@include file = "/WEB-INF/jsp/modalpage/upload.jsp"%> 
  </aside>

<!-- jQuery 3 -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/scripts/date.js"></script>
<!-- iCheck -->
<script src="/resources/plugins/iCheck/icheck.min.js"></script>
<script>
function addImage(){
	$('#uploadmodal').modal('show');
}

function notRead() {
	$.ajax({
		type:'get',
		dataType:'json',
		url:'/message/notread',
		success:function (data) {
			if(data.length > 0) {
				var str='';
				data.forEach(function(item) {
 					$('.noRead').text(item.notRead);
					$('.label-success').text(item.notRead);			
					$('.label-primary').text(item.notRead);
					str+='<ul class="menu">';
					str+='<li>';
					str+='<a href="/users/readmail?msgNum=' + item.message.msgNum + '">';
					str+='<div class="pull-left">';
					str+='<img src="' + item.image + '">';
					str+='</div>';
					str+='<h4 class="last-message-subject">' + item.message.subject;
					str+='<small class="last-message-dateDiff">';
					str+='<i class="fa fa-clock-o"></i>' + ' ' + item.message.dateDiff;
					str+='</small>';
					str+='</h4>';
					str+='</a>';
					str+='</li>';
					str+='</ul>';
				});
					$('#last-message-area').html(str);
			}
			else {
				$('.noRead').text(0);
				$('.label-success').text();			
				$('.label-primary').text();
				var str='';
				str+='<div class="alert alert-warning alert-dismissible" style="margin-top: 5%;">';
				str+='<h4><i class="icon fa fa-warning"></i>Not Found</h4>';
				str+='최근 읽지 않은 메시지가 없습니다.';
				str+='</div>';
				$('#last-message-area').html(str);
			}
		},
		error:ajaxError
	});
}

$(function() {
	notRead();
 	timer = setInterval( function test() {
 		notRead();
	}, 3000); 
});
</script>
<!-- AdminLTE for demo purposes -->
