<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>T·T | Registration Page</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="/resources/bower_components/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="/resources/bower_components/Ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="/resources/dist/css/AdminLTE.min.css">

<!-- Datepicker style-->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<!-- Integration style -->
<link rel="stylesheet" href="/stylesheets/style.css">
<link href="/resources/startbootstrap/css/freelancer.min.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!-- Google Font -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition register-page">
	<div class="register-box">
		<div class="register-logo" style="background-color: #2c3e4f;">
			<a href="/T_index.jsp"><b style="color: white;">T·T</b></a>
		</div>

		<div class="register-box-body">
			<c:set var = "pageMessage" value = "회원정보를 등록하세요" />
			<c:if test = "${isUpdate}">
			<c:set var = "pageMessage" value = "회원정보를 수정할 수 있습니다." />
			</c:if>
			
			<p class="login-box-msg">${pageMessage}</p>
			
			<c:if test="${not empty formErrorMessage}">
					<label class="error alert alert-warning">
						${formErrorMessage}  
					</label>
			</c:if>
			
			<c:set var = "actionUrl" value = "/users/create" />
			<c:if test="${isUpdate}">
			<c:set var = "actionUrl" value = "/users/update" />
			</c:if>

			<form id="form-sign" action="${actionUrl}" method="post" enctype="multipart/form-data">
			
				<c:if test="${isUpdate}">
					<div id="image-holder" class="form-group has-feedback">
						<img id="profile" src="${user.image }" class="thumb-image"/>
					</div>
					<div id="profile-border" class="form-group has-feedback">
						프로필 사진   
						<input id="profileUpload" type="file" name="image" accept="image/*"/> 
					</div>
				</c:if>
			
				<c:choose>
					<c:when test="${isUpdate}">
					<div class="form-group has-feedback update-hidden-box">
						<input type="hidden" class="form-control" name="userId" value="${user.userId}">
						<p>${user.userId }
						<span class="glyphicon glyphicon-tag form-control-feedback"></span>
						</p>
					</div>
					</c:when>
						
					<c:otherwise>
					<div class="form-group has-feedback">
						<input type="text" class="form-control" placeholder="아이디" name="userId" value="${user.userId}">
						<span class="glyphicon glyphicon-tag form-control-feedback"></span>
					</div>
					</c:otherwise>
				</c:choose>  

				<div class="form-group has-feedback">
					<input type="password" id="password" class="form-control" placeholder="비밀번호" name="password" value="${user.password}"> 
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" id="password2" class="form-control" placeholder="비밀번호 확인"> 
					<span class="glyphicon glyphicon-log-in form-control-feedback"></span>
				</div>

					<c:choose>
					<c:when test="${isUpdate}">
					<div class="form-group has-feedback update-hidden-box">
						<input type="hidden" class="form-control" placeholder="이름" name="name" value="${user.name}">
						<p>${user.name}
						<span class="glyphicon glyphicon-user form-control-feedback"></span>
						</p>
					</div>
					</c:when>
			
					<c:otherwise>
					<div class="form-group has-feedback">
						<input type="text" class="form-control" placeholder="이름" name="name" value="${user.name}">
						<span class="glyphicon glyphicon-user form-control-feedback"></span>
					</div>
					</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${isUpdate}">
						<div class="form-group has-feedback update-hidden-box">
							<input type="hidden" id="datepicker" class="form-control" placeholder="생년월일" name="birth" value="${user.birth}">
							<p>${user.birth} 
							<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
							</p>
						</div>	
						</c:when>
						
						<c:otherwise>
						<div class="form-group has-feedback">
							<input type="text" id="datepicker" class="form-control" placeholder="생년월일" name="birth" value="${user.birth}"> 
							<span class="glyphicon glyphicon-calendar form-control-feedback"></span>
						</div>
						</c:otherwise>
					</c:choose>

				<div class="form-group has-feedback">
					<input type="email" class="form-control" placeholder="이메일" name="email" value="${user.email}">
					<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>

				<div class="row" style="margin-right: -75px; margin-left: 12%;">
					<c:choose>
						<c:when test="${isUpdate}">
						<div class="col-xs-4 ">
							<a class="btn btn-primary btn-block btn-flat " href="/main.jsp">취소</a>
							<button type="button" class="btn btn-danger btn-block btn-flat" onclick="msg();">회원탈퇴</button>
						</div>
						
						<div class="col-xs-4 ">
							<button type="button" class="btn btn-primary btn-block btn-flat" onclick="passwordVaild();">수정</button>
						</div>
						</c:when>
						
						<c:otherwise>
						<div class="col-xs-4 ">
							<a class="btn btn-primary btn-block btn-flat " href="/login.jsp">취소</a>
						</div>
						
						<div class="col-xs-4 ">
							<button type="button" class="btn btn-primary btn-block btn-flat" onclick="passwordVaild();">회원가입</button>
						</div>
						</c:otherwise>
					</c:choose>
					</div>
					
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</form>
			
			<c:if test="${empty user.userId }">
			<div class="social-auth-links text-center">
				<!-- <p>- OR -</p>
				<a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i
					class="fa fa-facebook"></i> Sign up using Facebook</a> <a href="#"
					class="btn btn-block btn-social btn-google btn-flat"><i
					class="fa fa-google-plus"></i> Sign up using Google+</a> -->
			
			<a href="/login.jsp" class="text-center">로그인 하러가기</a>
			</div>
			</c:if>

		</div>
		<!-- /.form-box -->

	</div>
	<!-- /.register-box -->

	<!-- jQuery 3 -->
	<script src="/resources/bower_components/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script
		src="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- datepicker -->
	<script>
		$(function() {
			$("#datepicker").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat: "yy-mm-dd",
				maxDate: "+0Y",
			    yearRange: "1900:2100"
			});
		});
	</script>
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!-- image preview -->
	<script src="/scripts/imageUpload.js"></script>
	
	<!-- password check -->
	<script>
	function passwordVaild(){
		if($('#password').val()!=$('#password2').val()){
			alert("비밀번호를 다시 확인해주세요.");
		}
		else{
			$('#form-sign').submit();
		}
	}
	</script>
	
	<!-- 회원탈퇴 -->
   <script>
   function msg() {
      if (confirm("정말 탈퇴 하시겠습니까?")) {
         location.href = "/users/dropuser";
      } else {
         return;
      }
   }
   </script>

</body>
</html>