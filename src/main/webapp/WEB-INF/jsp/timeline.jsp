<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        ${projectName}
        
        <small>Timeline</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i>Dash Board</a></li>
        <li class="active">Timeline</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#createTimeline">글쓰기</button>
      <!-- row -->
      <div class="row">
        <div class="col-md-12">
          <!-- The time line -->
          <ul class="timeline">
          <c:set value= "null" var="date"/>
          	 <c:choose>
	          	 <c:when test="${not empty TLlist }">
					<c:forEach var="list" items="${TLlist}" varStatus="status">
			  		
			  	<c:if test = "${list.getTimeLineContent_date().substring(0, 11) != date}">
		            <li class="time-label">
	                <span class="bg-red">
	                   	 ${list.getTimeLineContent_date().substring(0, 11)}
	                </span>
	                </li>
					<c:set value= "${list.getTimeLineContent_date().substring(0, 11)}" var="date"/>
	            </c:if>	
                  <li>
                <i class="fa fa-envelope bg-blue"></i>
             	<div class="timeline-item">
                <span class="time"><i class="fa fa-clock-o"></i> ${list.getTimeLineContent_date().substring(11,16)}</span>

                <h3 class="timeline-header"><a href="#">${list.userId}</a>님이 글을 남겼습니다.
 				 <c:if test = "${list.getUserId() == user.userId}">
           		 <form method = "post" action = "/timelines/deleteContent">
                 <input type = "hidden" name = "timelineNum" value = "${list.getTimeLineContent_Num()}">
           		 <input type = "submit" class = "btn btn-danger btn-xs" value = "Delete">
           		 </form>
           		 </c:if>
                </h3>

               
                <div class="timeline-body">
               	${list.getTimeLineContent()}      
                </div>
                <div class="timeline-footer">
           
                </div>
                

			    
			    <div class="timeline-body">
			       <form method = "post" action = "/timelines/createComment">
			       	<input type = "text" placeholder ="댓글 내용" name = "comment" style="width:90%; ">
			           <input type = "hidden" name = "userId" value = "${user.userId}"">
			           <input type = "hidden" name = "timelineNum" value = "${timelineNum}">
			           <input type = "hidden" name = "timelineContent_num" value = "${list.getTimeLineContent_Num()}">
			           <input type = "submit" class = "btn btn-primary btn-xs" value = "댓글쓰기">
			    </form>
			        <!-- comment -->
			        <div style="width:90%; margin-top : 5px">
	       			<c:choose>
	          	 		<c:when test="${not empty CLlist }">
							<c:forEach var="comment" items="${CLlist}" varStatus="status">
								<c:if test = "${comment.getTimeLineContent_Num() == list.getTimeLineContent_Num()}">
									<div class = "comment-item">		
										<div class = "timeline-comment">
											<a href="#" onclick="javascript:showUserProfile($(this))" style = "color:#8AC8F3; font-weight: bold;">${comment.getUserId()}
											<input type="hidden" name="userId" value="${comment.getUserId()}">
											</a>
											<span style = "color : black; font-weight: 500"> ${comment.getComment_Content()} </span>
											<span class="time" style = "float : right"><i class="fa fa-clock-o"></i> ${comment.getComment_date()}</span>
										</div>
										<div class = "comment-delete-form">
											 <c:if test = "${comment.getUserId() == user.userId}">
							               	 <form class="timeline-comment-delete" method = "post" action = "/timelines/deleteComment">
							                  <input type = "hidden" name = "commentNum" value = "${comment.getComment_Num()}">
							               	  <input type = "submit" class = "btn btn-danger btn-xs " style = "font-size: 3px" value = "Delete">
							               	 </form>
							               	 </c:if>
										</div>
									</div>	
	            				</c:if>	
	            			</c:forEach>
	            		</c:when>
	       			</c:choose>
	       			</div>
	       			<!-- comment -->
			  	  </div> 
                </div>
			        
            	</li>
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
<%@include file = "/WEB-INF/jsp/modalpage/create_timeline_form.jsp"%>