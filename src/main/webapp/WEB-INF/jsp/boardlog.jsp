<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/commons/T_header.jsp"%>
 
<head>
<!-- Custom fonts for this template -->
<link href="/resources/startbootstrap/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css">

</head>

<!-- Main content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        ${board.boardName}
        <small>보드에 대한 Activity 가 기록됩니다.</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i>Dash Board</a></li>
        <li class="active">BoardLog</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <!-- row -->
      <div class="row">
        <div class="col-md-12">
          <!-- The time line -->
          <ul class="timeline">
          	 <c:choose>
	          	 <c:when test="${not empty dateList}">
					<c:forEach var="list" items="${dateList}" varStatus="status">
			            <li class="time-label">
		                <span class="bg-red">
		                   	 ${list}
		                </span>
		                </li>
		              	<c:forEach var="balList" items="${balList[status.index]}" varStatus="stauts2">
		                <li>
		                <i class="fa fa-clock-o bg-gray"></i>
		             	<div class="timeline-item">
			                <span class="time"><i class="fa fa-clock-o"></i>${balList.getActivityDate()}</span>
				                <h3 class="timeline-header">
				                ${balList.activity}
				                </h3>
		                </div>
		            	</li>
		              	</c:forEach>
			       	</c:forEach>
				 </c:when>
		     </c:choose>    		     
          </ul>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>
 <!-- /.content-wrapper -->
  
<%@include file="/WEB-INF/jsp/commons/T_footer.jsp"%>
    