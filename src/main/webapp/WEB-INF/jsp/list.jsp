<%@ page language="java" contentType="text/html; charset=UTF-8" 
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="viser.project.*"%>
<%@ page import="viser.board.*" %>

<c:set var = "Target" value = "프로젝트" />
<c:set var = "Modal_target" value = "#CreateNewProJect" />
<c:set var = "ModifyUrl" value = "/project/updateProject" />

<c:if test = "${isReadBoard}">
<c:set var = "Target" value = "보드" />
<c:set var = "Modal_target" value = "#CreateNewBoard" />
<c:set var = "ModifyUrl" value = "/board/updateBoard" />
</c:if>

<head>
<link href="/stylesheets/style.css" rel="stylesheet" type="text/css">
</head>

<%@include file="/WEB-INF/jsp/commons/T_header.jsp"%>   
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
      <i class="fa fa-list-ol"></i>
        ${Target} 목록
        <small>현재 참여 중인 ${Target} 목록이 표기됩니다.</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="/users/userDashBoard"><i class="fa fa-dashboard"></i>HOME</a></li>
        <c:if test="${isReadBoard}">
        <li><a href="/project/projectlist"><i class="fa fa-list-ol"></i>프로젝트 목록</a></li>
        </c:if>
        <li class="active"><i class="fa fa-list-ol"></i>${Target} 목록</li>
      </ol>
    </section>

<!-- Main content -->
    <section class="content container-fluid">
		 <div class="wrap">
         <div id="card-container_wrap">
            <div class="project-container">
               <div class="container" style = "length: 500px;">
                     <table class="table table-hover">
                     <thead>
                     <tr>
                        <!-- LIST -->
                        <th style="width: 10%">NUM</th>
                        <th style="width: 40%">${Target}</th>
                     <c:choose>
                        <c:when test="${isReadProject}">
                     	<th>생성일자</th>
                        </c:when>
                        <c:otherwise>
                        <th>보드 유형</th>
                        </c:otherwise>
                    </c:choose>
                     </tr>
                     </thead>

                     <div class="project-content-body">

                        <c:choose>
                           <c:when test="${not empty PBlist }">
                              <c:forEach var="list" items="${PBlist}" varStatus="status">
                                 <tr>
                                    <td>${status.count}</td> <!-- 형근:프로젝트 번호는 가져온 순서대로 -->
                                    <c:choose>
                                       <c:when test="${isReadBoard}">
                                       <td>
                                       <a href="/lists/cardlist?boardNum=${list.boardNum}" > 
                                          ${list.boardName}
                                       </a>
                                       
                                       <div class="btn-group" role="group" style="font-size: righ; margin-left: 10px;">
                                           <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                           <span class="caret" aria-hidden="true"></span>
                                           </button>
                                           <ul class="dropdown-menu" role="menu">
                                           <li><a href="#modifyAction${status.count}" data-toggle="collapse" aria-controls="modifyAction">Modify</a></li>
                                           <li><a href="javascript:dropmsg('${list.boardName}', 'board')">Delete</a></li>
                                           </ul>
                                         </div>
                                         
                                            <form class="form-inline" action="${ModifyUrl}" method="get">
                                             <div class="collapse" id="modifyAction${status.count}">
                                              <div class="input-group" >
                                                <input type="text" class="form-control" id="exampleInputName2" name = "newBoardName" placeholder="Input new name. . .">
                                                <input type="hidden" name = "preBoardName" value="${list.boardName}">
                                                <span class="input-group-btn">
                                                <button class="btn btn-default" type="submit">Modify</button>
                                                </span>
                                                 <span class="input-group-btn">
                                                   <button type="button" class="close" aria-label="Close" style="margin-left: 4px;">
                                                   <a href="#modifyAction${status.count}" data-toggle="collapse" aria-controls="modifyAction${status.count}"> X </a>
                                                   </button>
                                                </span>
                                              </div>
                                             </div>
                                          </form>
                                       </td>
                                       <td><a>${list.boardInfo}</a></td>
                                       </c:when>
                                       <c:otherwise>
                                       <td>
                                       <a href="/board/boardlist?projectName=${list.projectName}">
                                          ${list.projectName}
                                       </a>
                                          
                                       <div class="btn-group" role="group" style="float: right; padding-right: 10px;">
                                           <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                           <span class="caret" aria-hidden="true"></span>
                                           </button>
                                           <ul class="dropdown-menu" role="menu">
                                           <li><a href="#modifyAction${status.count}" data-toggle="collapse" aria-controls="modifyAction">Modify</a></li>
                                           <li><a href="javascript:dropmsg('${list.projectName}', 'project')">Delete</a></li>
                                           </ul>
                                         </div>
                                          <form class="form-inline" action="${ModifyUrl}" method="get">
                                             <div class="collapse" id="modifyAction${status.count}" style="float: right;">
                                              <div class="input-group" >
                                                <input type="text" class="form-control" id="exampleInputName2" name = "newProjectName" placeholder="Input new name. . .">
                                                <input type="hidden" name = "preProjectName" value="${list.projectName}">
                                                <span class="input-group-btn">
                                                <button class="btn btn-default" type="submit">Modify</button>
                                                </span>
                                                 <span class="input-group-btn">
                                                   <button type="button" class="close" aria-label="Close" style="margin-left: 4px;">
                                                   <a href="#modifyAction${status.count}" data-toggle="collapse" aria-controls="modifyAction${status.count}"> X </a>
                                                   </button>
                                                </span>
                                              </div>
                                             </div>
                                          </form>
                                    </td>
                                    <td><a>${list.projectDate }</a></td>
                                 </tr>
                                       </c:otherwise>
                                    </c:choose>
                           
                              </c:forEach>
                           </c:when>                        
                           <c:otherwise>
                              <tr>
                                 <td colspan="5">
                                 <div class="alert alert-success alert-dismissible" style="margin-top: 15px; width: 90%;">
									<h4>
										<i class="fa fa-hand-o-down" style="margin-right: 5px;"></i>
										아래 버튼을 눌러 ${Target}를 생성하세요!
									</h4>
								 </div>
                                 </td>
                              </tr>
                           </c:otherwise>
                        </c:choose>
                     </div>
                  </table>
            </div>
                  
                  <!-- Create Project Modal Field -->
                  <button type="button" class="btn btn-default btn-lg" data-toggle="modal" data-target="${Modal_target}" style="margin: 50; margin-left: 30%;">
                    ${Target} 생성하기
                  </button>
	              <%@include file="/WEB-INF/jsp/modalpage/create_form.jsp" %>
	              <c:if test = "${isReadBoard}">
	              <button type="button" onclick="location.href='/project/projectlist'" class="btn btn-default btn-lg">목록으로</button>
	              </c:if>
               </div>
            </div>
         </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
<%@include file="/WEB-INF/jsp/commons/T_footer.jsp"%>

<script>
      function dropmsg(value, list) {
         var url;
         if(list == 'project') {
            url = '/project/deleteProject?projectName=';
         }
         else url = '/board/deleteBoard?boardName=';
         
         if (confirm("정말 삭제 하시겠습니까?")) {
            location.href = url+value;
         } else {
            return;
         }
      }
</script>