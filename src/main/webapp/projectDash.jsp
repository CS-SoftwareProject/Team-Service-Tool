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
		<i class="glyphicon glyphicon-dashboard"></i> 대시보드 <small>프로젝트 관련 정보가 대시보드로 간략하게 표기됩니다.</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="/users/userDashBoard"><i class="fa fa-dashboard"></i>Home</a></li>
		<li><a href="/board/boardlist?projectName=${projectDash.projectName}">프로젝트 : ${projectDash.projectName}</a></li>
		<li class="active">프로젝트 대시보드</li>
	</ol>
	</section>

<!-- Main content -->
    <section class="content container-fluid">

    	<div class="alert alert-success alert-dismissible" style="margin-top: 15px;">
		<h4>
			<i class="glyphicon glyphicon-hand-right" style="margin-right: 5px;"></i> 프로젝트명 : ${projectDash.projectName}
		</h4>
	</div>

	<!-- Project Progress -->
	<div class="box" style="margin-top: 20px;">
		<div class="box-header with-border">
			<i class="fa fa-line-chart"></i>
			<h3 class="box-title">전체 프로젝트 진행도</h3>
		</div>
		<div class="box-body chart-responsive">
			<div class="progress progress active">
				<div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: ${projectDash.projectProgress}%">
				<c:choose>
					<c:when test="${projectDash.projectProgress eq 0}">
					<p style="color: red; margin-left: 340px; width: 200px;">프로젝트가 진행되지 않았습니다.</p>
					</c:when>
					<c:otherwise>
					<p>${projectDash.projectProgress}%Complete</p>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</div>
		<!-- /.box-body -->
	</div>

	<div class="col-md-6">
		<!-- Project Member Box -->
		<div class="box">
			<div class="box-header with-border">
				<i class="fa fa-users"></i>
				<h3 class="box-title">프로젝트 멤버</h3>
			</div>
			<%@ include file="/WEB-INF/jsp/modalpage/userProfile.jsp"%>
			<div class="box-body chart-responsive">
				<div class="chart" id="bar-chart" style="height: auto;">
					<div class="userList" style="display: -webkit-box;">
						<c:forEach var="memberList" items="${projectDash.projectMembers}" varStatus="status">
							<div class = "member">
								<image src="${memberList.imagePath}" style="width: 150px; height: 150px;"></image>
								<figcaption style="margin-left: 56px;">
								<a href="#" onclick="javascript:showUserProfile($(this))">
									<input type="hidden" id="userId" value="${memberList.userId}">
									<h3 class="box-title">${memberList.userId}</h3>
								</a>
								</figcaption>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<!-- /.box-body -->
		</div>

		<div class="box">
			<div class="box-header with-border">
				<i class="fa fa-list"></i>
				<h3 class="box-title">보드 리스트</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<table class="table table-bordered">
					<tr>
						<th style="width: 10px">#</th>
						<th>Board Name</th>
						<th>Progress</th>
						<th style="width: 40px">Percent</th>
					</tr>
					<c:choose>
					<c:when test="${not empty projectDash.boards}">
						<c:forEach var="boards" items="${projectDash.boards}" varStatus="status">
						<tr>
							<td>${status.count}.</td>
							<td><a href="/lists/cardlist?boardNum=${boards.boardNum}">${boards.boardName}</a></td>
							<td>
								<div class="progress progress-xs progress-striped active">
									<div class="progress-bar progress-bar-success" style="width:${boards.boardProgress}%"></div>
								</div>
							</td>
							<td>
								<span class="badge bg-green">${boards.boardProgress}%</span>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<td colspan="4">
						<div class="alert alert-info alert-dismissible" style="margin-top: 15px;">
							<h4>
								<i class="icon fa fa-info" style="margin-right: 5px;"></i>
								보드가 존재하지 않습니다.
							</h4>
							<div>
								<a href="/board/boardlist?projectName=${projectName}" style="display: -webkit-inline-box;"><h1>여기</h1></a> 를 눌러 보드를 생성하세요.
							</div>
						</div>
						</td>
					</c:otherwise>
					</c:choose>
					
				</table>
			</div>
			<!-- /.box-body -->
		</div>
	</div>

	<div class="col-md-6">
		<div class="box">
			<div class="box-header">
				<i class="fa fa-credit-card"></i>
				<h3 class="box-title">최근 카드 목록</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body no-padding">
				<table class="table table-striped">
					<tr>
						<th style="width: 10px">#</th>
						<th style="width: 200px">Subject</th>
						<th>Board</th>
						<th style="width: 20%">User</th>
						<th style="width: 40px">Progress</th>
					</tr>
					<c:choose>
					<c:when test="${not empty projectDash.lastCards}">
						<c:forEach var="cardList" items="${projectDash.lastCards}" varStatus="status">
						<tr>
							<td>${status.count}.</td>
							<td>
								<a href="#" onclick="javascript:cardNavigator(${cardList.boardNum}, ${cardList.cardNum})">
								${cardList.subject}
								</a>
							</td>
							<td>
								${cardList.boardName}
							</td>
							<td>
								<a href="#" onclick="javascript:showUserProfile($(this))">
								<input type="hidden" id="userId" value="${cardList.userId}">
								${cardList.userId}
								</a>
							</td>
							<td>
								<span class="badge bg-red">${cardList.progress}%</span>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<td colspan="5" align="center">
							<a><h3 class="box-title">카드가 존재하지 않습니다.</h3></a>
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
function ajaxError(){
	   alert("데이터 로딩 오류");
}

function showUserProfile(target) {
  $('#userProfile-modal').modal('show');
	$.ajax({
		type:'get',
		data:{
			userId:target.find('input').val()
		},
		url:'/users/readProfile',
		dataType:'json',
		success:function (data) {
				console.log("JSON DATA log : ", data);
				var targetUser = JSON.parse(data[0]);
				console.log("JS userProject List : ", targetUser);
				$('#profile-userId').html(targetUser.userId);
				$('#profile-userName').html(targetUser.name);
				$('#profile-userBirth').html(targetUser.birth);
				$('#profile-userEmail').html(targetUser.email);
				$('#profile-userImage').attr("src", targetUser.image);
				var userProject = JSON.parse(data[1]);
				var str='';
				var num=0;
				userProject.forEach(function(targetUser){
					str+="<tr>";
					str+="<td>" + ++num + "</td>";
					str+="<td><a>" + userProject[0].projectName + "</a></td>";
					$('#profile-joinlist').html(str);
				});
		},
		error:ajaxError
	});
}

function cardNavigator(boardNum, cardNum) {
	 location.href="/lists/cardlist?boardNum=" + boardNum + "&cardNum=" + cardNum;
}
</script>
