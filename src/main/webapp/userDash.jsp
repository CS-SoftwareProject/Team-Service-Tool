<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>Insert title here</title>

<%@include file="/WEB-INF/jsp/commons/T_header.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
	<h1>
		<i class="glyphicon glyphicon-dashboard"></i> 개인 대시보드 <small>사용자에 한하는 정보가 대시보드로 간략하게 표기됩니다.</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="/users/userDashBoard"><i class="fa fa-dashboard"></i>Home</a></li>
		<li class="active">User-DashBoard</li>
	</ol>
	</section>

	<!-- Main content -->
	<section class="content container-fluid">

	<div class="alert alert-success alert-dismissible" style="margin-top: 15px;">
		<h4>
			<i class="glyphicon glyphicon-hand-right" style="margin-right: 5px;"></i> USER : ${userId}
		</h4>
	</div>
	
	<c:if test="${projectCount eq 0}">
	<div class="alert alert-info alert-dismissible" style="margin-top: 15px;">
		<h4>
			<i class="icon fa fa-info" style="margin-right: 5px;"></i>
			현재 참여 중인 프로젝트가 없습니다 !
		</h4>
		<div>
			<a href="/project/projectlist" style="display: -webkit-inline-box;"><h1>여기</h1></a> 를 눌러 프로젝트를 시작하세요.
		</div>
	</div>
	</c:if>
	
	<div>
		<!-- Donut chart -->
		<div class="box box-primary">
			<div class="box-header with-border">
				<i class="fa fa-bar-chart-o"></i>

				<h3 class="box-title">My Counter</h3>
			</div>
			<div class="box-body">
				<div id="donut-chart" style="height: 300px;">
					<c:if test="${projectCount eq 0}" >
					<a><h1 class="box-title" style="margin-left: 45%;">참여 중인 프로젝트가 없습니다.</h1></a>
					</c:if>
				</div>
			</div>
			<!-- /.box-body-->
		</div>
	</div>

	<div class="col-md-6">
		<div class="box">
			<div class="box-header with-border">
				<i class="fa fa-list"></i>
				<h3 class="box-title">Join Project List</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<table class="table table-bordered" style="margin-top: 10px; margin-left: 10%; width: 80%;">
					<tr>
						<th style="width: 10px">#</th>
						<th>ProjectName</th>
					</tr>
					<c:choose>
						<c:when test="${not empty userProjectList}">
							<c:forEach var="projectList" items="${userProjectList}" varStatus="status">
							<tr>
								<td>${status.count}.</td>
								<td>
									<a href="/board/boardlist?projectName=${projectList.projectName}">${projectList.projectName}</a>
								</td>
							</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
						<tr>
							<td colspan="4" align="center">
								<a href="#" onclick=""><h3 class="box-title">프로젝트를 시작하세요.</h3></a>
							</td>
						</tr>
						</c:otherwise>
					</c:choose>
						
				</table>
			</div>
		</div>

	</div>

	<div class="col-md-6">
		<div class="box">
			<div class="box-header with-border">
				<i class="fa fa-list"></i>
				<h3 class="box-title">My Assignee List</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<table class="table table-bordered">
					<tr>
						<th style="width: 10px">#</th>
						<th>Card</th>
						<th>Role</th>
						<th style="width: 40px">Progress</th>
					</tr>
					<c:choose>
						<c:when test="${not empty userAssigneeList}">
							<c:forEach var="assigneeList" items="${userAssigneeList}" varStatus="status">
							<tr>
								<td>${status.count}.</td>
								<td>
								<a href="#" onclick="javascript:cardNavigator(${assigneeList.boardNum}, ${assigneeList.cardNum})">${assigneeList.cardSubject}</a>
								</td>
								<td>
								<a>${assigneeList.roleName}</a>
								</td>
								<td>
								<span class="badge bg-green"> 
								<c:choose>
									<c:when test="${assigneeList.cardProgress ne -1}">
										${assigneeList.cardProgress}%
									</c:when>
									<c:otherwise>
										0%
									</c:otherwise>
								</c:choose>
									</span>
								</td>
							</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<td colspan="4" align="center">
								<a><h3 class="box-title">나에게 할당된 역할이 없습니다.</h3></a>
							</td>
						</c:otherwise>
					</c:choose>
					
				</table>
			</div>
			<!-- /.box-body -->
		</div>
	</div>
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->


<%@include file="/WEB-INF/jsp/commons/T_footer.jsp"%>

<script>
var num = -1
</script>

<script>
function ajaxError(){
	   alert("데이터 로딩 오류");
}

/*
 * DONUT CHART
 * -----------
 */

var donutData = [
  { label: 'Project', data: ${projectCount}, color: '#3c8dbc' },
  { label: 'My Role', data: ${roleCount}, color: '#0073b7' },
]

if(${projectCount} != 0) {
	$.plot('#donut-chart', donutData, {
	  series: {
	    pie: {
	      show       : true,
	      radius     : 1,
	      innerRadius: 0.5,
	      label      : {
	      show     : true,
	      radius   : 2 / 2.7,
	      formatter: labelFormatter,
	      threshold: 0.1
	      }
	    }
	  },
	  legend: {
	    show: false
	  }
	})
}
else {
	$('#donut-chart').css("height","100px");
}
	/*
 * END DONUT CHART
 */
 
 /*
  * Custom Label formatter
  * ----------------------
  */

 function labelFormatter(label) {
   return '<div style="font-size:13px; text-align:center; padding:2px; color: #fff; font-weight: 600;">'
     + label
     + '<br>'
     + donutData[++num].data + ' 개</div>'
 }
	
 function cardNavigator(boardNum, cardNum) {
	 location.href="/lists/cardlist?boardNum=" + boardNum;
 }
</script>
</body>
