<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@include file="/WEB-INF/jsp/commons/T_header.jsp"%>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
      ${board.boardName}
      <small>${board.boardInfo}</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="/project/projectlist"><i class="fa fa-list-ol"></i>프로젝트 목록</a></li>
        <li><a href="/board/boardlist?projectName=${projectName}"><i class="fa fa-list-ol"></i>보드 목록</a></li>
        <li class="active">카드 목록</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

         <div class="wrap">
            <div id="top">
              <div id="mini-menu"> 
               <div class="btn-group-sm" role="group" aria-label="...">   
               <button type="button" class="btn btn-info" onclick="location.href='/board/boardlist?projectName=${projectName}'">목록</button>
               <button type="button" class="btn btn-info" data-toggle="modal" data-target="#invite-modal">초대</button>
               <button type="button" class="btn btn-info" onclick="getRoleList();">역할</button>
               <c:if test="${isExistGantt == false }">
			       <button type="button" class="btn btn-info" data-toggle="modal" data-target="#gantt-alert">간트차트</button>
		       </c:if>		
               </div>
              </div>
            </div>  
              <div id="card-container" style = "overflow-x: auto;"> 
                 <div id="tt" style="overflow:auto; width:10000px; text-align: left; ">  
                  <%@include file = "/WEB-INF/jsp/modalpage/card.jsp"%>
                  <ul id="cardlists" class = "boxsort">        
                    <c:forEach var="list" items="${lists}" varStatus="status">
                       <li class="ui-state-default card_margin">
                             <div class="card_wrap_top">
                                <div>
                                   <textarea class="list_name" id="${list.listNum }-list-name" onkeyup="resize(this)" spellcheck="false" dir="auto" maxlength="512">${list.listName}</textarea>
                                </div>
                                <div>
                                   <a id="${list.listNum}-card-add-btn" class="btn btn-default" data-toggle="modal" href="#cardmodal">카드 추가</a>
                                   <script>
                                     $(function ()
                                             {
                                                 $('#'+${list.listNum }+'-list-name').change(function () {
                                                    updateListName(${list.listNum },$('#'+${list.listNum }+'-list-name').val())
                                                 });
                                             });
                                   
                                      $('#${list.listNum }-card-add-btn').click(function(){
                                         addCard(${list.listNum},$('#list-${list.listNum} li').length);
                                      });
                                   </script>
                                </div>
                               </div>
                            
                             <div >
                                <div class="card_wrap">
                                   <ul id="list-${list.listNum}" class="listSortable sort_css">  
                                      <c:forEach var="card" items="${list.cards}" varStatus="status">
                                         <li id="${card.cardNum }"class="ui-state-default">
                                         <a id="${card.cardNum}-card-view-btn" class="card-modal-btn" data-toggle="modal" href="#cardmodal">${card.subject}</a>
                                         <script>
                                            $(document).ready(function(){
                                               $('#${card.cardNum}-card-view-btn').click(function(){
                                                  viewCard(${card.cardNum});
                                               });
                                            });
                                            
                                       
                                         </script>
                                         </li>
                                      </c:forEach>
                                   </ul>
                                </div>
                             </div>
                             <button type="button" class="btn btn-cardlist" href="#"  onclick="location.href='/lists/removeList?boardNum=${param.boardNum}&listOrder=${list.listOrder }'" style = "margin-bottom: 5px;">삭제</button>
                          </li>      
                       </c:forEach>
                    </ul>
                    <button id ="addbutton" class="btn btn-cardlist" type="button" data-toggle="collapse" data-target="#addList" aria-expanded="false" aria-controls="collapseExample" >
                       List add.
                    </button>
                    <div class="collapse collapse-list" id="addList">
                       <form method="post" action="/lists/addList">
                         <div class="form-group">
                           <label for="newListName">List name.</label>
                           <input type="text" class="form-control" name="listName" placeholder="리스트 이름을 입력하세요">
                           <input type="hidden" class="form-control" name="boardNum" value="${param.boardNum }"/>
                           <input id="currentListNum" type="hidden" class="form-control" name="listOrder" />
                          <script>$('#currentListNum').val($('.listSortable').length);</script>
                         </div>
                         <button type="submit" class="btn btn-default" >생성</button>
                        </form>
                    </div>
                  </div>
              </div>
            </div>
            
    	    <div class="modal fade" id="gantt-alert">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">간트차트 생성</h4>
					</div>
					<div class="modal-body">
					  <div class="alert alert-info alert-dismissible">
		                <h4><i class="icon fa fa-info"></i> 알림</h4>
		                                    현재 보드와 데이터를 연동하여 간트차트 게시판을 만들수 있습니다.<br>
						 생성후 부터는 간트차트에서 추가하는 테스크가 보드에도 카드형태로 <br>
						 추가되므로 카드가 추가될 리스트를 선택한 후 확인을 눌러주세요.
		              </div>
						 카드가 추가될 리스트 선택:
						 <select class="form-control" id="list-selection">
							<c:forEach var="list" items="${lists}" varStatus="status">
							 	<option value="${list.listNum }">${list.listName }</option>
							 </c:forEach>
						 </select>
					</div>
					<div class="modal-footer">
						<form id="gantt-form" action="/gantts/createGantt" method="get">
							<input type="hidden" name="listNum" id="selectList"/>
							<button type="button" class="btn btn-default pull-left"
								data-dismiss="modal">취소</button>
							<button type="button" onclick="ganttAlert()" class="btn btn-primary">확인</button>
						</form>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		 </div>
		 <!-- /.modal -->
         
         <%@include file = "/WEB-INF/jsp/modalpage/invite.jsp" %>
         <%@include file = "/WEB-INF/jsp/modalpage/role.jsp" %>
         
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
<script>
var currentCardNum;

function ganttAlert(){
	$('#selectList').val($('#list-selection')[0].selectedOptions[0].value);
	$('#gantt-form').submit();
}

function viewCard(currentCardNum){
   $('#cardAssignee-field').empty();
   $('#cardProgress-field').empty();
   $.ajax({
      type:'get',
      data:{
         cardNum:currentCardNum
      },
      url:'/cards/viewcard',
      dataType:'json',
      success:function (data){
        $('#cardListNum').val(data.listNum);
        $('#cardmodal-body').css("width", "630px");
           $('#cardmodal-body').css("height", "650px");
           $('#btn-area').css("margin-left", "100px");
           var addStr='';
          <!-- Card Add-btn Group -->
         addStr+="<h3>Add</h3>";
         addStr+="<button type='button' class='btn btn-primary btn-lg' onclick='showProgressModal();' style='margin-bottom: 7px;'>Progress</button>";
         addStr+="<button type='button' class='btn btn-primary btn-lg' onclick='showDueDateModal();' style='margin-bottom: 7px;'>Start Date</button>";
         addStr+="<button type='button' class='btn btn-primary btn-lg' onclick='showAssigneeModal();' style='margin-bottom: 7px;'>Assignee</button>";
         /* addStr+="<button type='button' class='btn btn-primary btn-lg' style='margin-bottom: 7px;'>CheckList</button>";
         addStr+="<button type='button' class='btn btn-primary btn-lg' style='margin-bottom: 7px;'>Labels</button>";    */
        $('#add-menu').html(addStr);
           
        $('#progress-control').html("<button id='apply-btn' class='btn btn-success' style='margin-right: 40px;' type='button' onclick='javascript:cuProgress()'>Apply</button>")
        var str='';
        $('#card-start').html(str);
         $('#card-field').attr("action","/cards/updatecard");
         $('#Title').html("<h2>" + data.subject + "</h2>");
         console.log("[JS.ViewCard] : {}", data.assignees);
         
         if(data.progress && data.progress != -1) {
              var ProgressStr='';
              ProgressStr+="<div id='progressBar' class='progress progress-sm active' style='width: 600px;margin-left: -20px;'>";
             ProgressStr+="<div id='progressLabel' class='progress-bar progress-bar-success progress-bar-striped' role='progressbar' aria-valuenow='20' aria-valuemin='0' aria-valuemax='100' style='width:" + data.progress + "%'>";
             ProgressStr+="<p style='margin-top: -5px;'>" + data.progress + "%</p>"
             ProgressStr+="</div>";
             ProgressStr+="</div>";
          $('#cardProgress-field').html(ProgressStr);
         }
         
         if(data.assigs) {
           data.assigs.forEach(function(item) {
            var thisStr ='';
              thisStr+="<span id='assignee-label" + item.assigneeNum +"'  class='label label-warning' style='margin-right:20px; margin-left:-15px;'>" + item.userId + " : '" + item.roleName + " '</span>";
      		  $('#cardAssignee-field').append(thisStr);
           });
         }
         
         if(data.start) {
             str+="<td>STARTDATE</td>";
         str+="<td>";
         str+="<input type='hidden' class='form-control'>";
         str+=new Date(data.start).format("y-MM-dd");
         str+="</td>"
         $('#card-start').html(str);
         };
         
         $('#cardNum').val(data.cardNum);
         $('#card-user').html("<input type='hidden' name='userId' value='"+data.userId+"'>"+data.userId+"</input>");
         $('#cardSubject').val(data.subject);
         $('#cardContent').val(data.content);
         var btn="";
         btn+="<input type='submit' class='btn btn-lg' value='Modify' style='margin-right: 10px;'/>";
         btn+="<input type='reset' class='btn btn-lg' value='Reset' style='margin-right: 10px;'/>";
         btn+="<input type='button' class='btn btn-lg' value='List' data-dismiss='modal' style='margin-right: 10px;'/>";
         btn+="<input type='button' id='delete-card' class='btn btn-lg' value='Delete' style='margin-right: 10px;'/>";
         $('#btn-area').html(btn);
         $('#delete-card').attr("onclick","location.href='/cards/removecard?num="+data.cardNum+"&listNum="+data.listNum+"&cardOrder="+data.cardOrder+"'");
          $('#close-card-btn').remove();
      },
      error:ajaxError
   });
}
function addCard(cardListNum,currentCardOrder){
   $('#add-menu').empty();
   $('#cardmodal-body').css("width", "500px");
   $('#cardmodal-body').css("height", "550px");
   $('#btn-area').css("margin-left", "25px");
   $('.assigneeMember').remove();
   $('.roleName').remove();
   $('#cardAssignee-field').empty();
   $('#cardProgress-field').empty();
   $('#assign-form').empty();
   $.ajax({
      type:'get',
      data:{
         listNum:cardListNum,
         cardOrder:currentCardOrder
      },
      url:"/cards/createcardForm",
      dataType:'json',
      success:function (data){
        $('#progress-control').html("<button id='apply-btn' class='btn btn-success' style='margin-right: 40px;' type='button' onclick='javascript:applyProgressToCard()'>Apply</button>")
        $('#cardNum').val(data.cardNum);
        $('#card-start').empty();
         $('#cardSubject').val(data.subject);
         $('#cardContent').val(data.content);
         $('#card-field').attr("action","/cards/createcard");
         $('#Title').html("<h2>CREATE CARD</h2>");
         $('#card-user').html("<input type='hidden' name='userId' value='${user.userId}'>${user.userId}</input>");
         console.log(data.start);
         $('#cardListNum').val(cardListNum);
         $('#cardOrder').val(currentCardOrder);
         var btn="";
         btn+="<input type='submit' class='btn btn-lg' value='Submit' style='margin-right: 10px;'/>";
         btn+="<input type='reset' class='btn btn-lg' value='Reset'  style='margin-right: 10px;'/>";
         btn+="<input type='button' class='btn btn-lg' value='List' data-dismiss='modal' style='margin-right: 10px;'/>";
         $('#btn-area').html(btn);
      },
      error:ajaxError
   });
}

function ajaxError(){
   alert("데이터 로딩 오류");
}
</script>

<script>
 
 var boardNum=${boardNum};
 var startListOrder;
 var updateListOrder;
 
  $( function() {
       $( ".boxsort" ).sortable({
            update: function(event, ul) { 
               updateListOrder=ul.item.index();
                 console.log('list update: '+ updateListOrder);
                 changeListOrder();
             },
             start: function(event, ul) {
                startListOrder=ul.item.index();
                 console.log('list start: ' + startListOrder);
             },
         });
       $(".card_wrap_top").disableSelection();
  } );
  
  function changeListOrder(){
        $.ajax({
           type:'get',
         data:{
            num:boardNum,
            current:startListOrder,
            update:updateListOrder
         },
         url:"/lists/updateListOrder",
         dataType:'text',
         error:ajaxError
      });        
   }

  var startListOrder;
  var updateListOrder;
  var startCardOrder;
  var updateCardOrder;
  
  $( function() {
    $( ".listSortable" ).sortable({
          update:function(event, ui) { 
            updateListOrder=ui.item.parent().parent().parent().parent().index();
            updateCardOrder= ui.item.index();
            if(updateListOrder==startListOrder){
               
                 console.log('update> card receive: ' + updateListOrder);
                 console.log('card update: '+ updateCardOrder);
                 changeCardOrder();
            }
          },
          receive:function(event, ui) { 
            updateListOrder=ui.item.parent().parent().parent().parent().index();
            updateCardOrder= ui.item.index();
            console.log('receive> card receive: ' + updateListOrder);
              console.log('card update: '+ updateCardOrder);
              changeCardOrder();
          },
          start: function(event, ui) {
            startListOrder=ui.item.parent().parent().parent().parent().index();
           startCardOrder= ui.item.index();
             console.log('start> card receive: ' + startListOrder);
             console.log('card start: ' + startCardOrder);
          },
          
      connectWith: ".listSortable"
    }).disableSelection();
  } ); 
  
function changeCardOrder(){
        $.ajax({
           type:'get',
         data:{
            num:boardNum,
            currentList:startListOrder,
            updateList:updateListOrder,
            currentCard:startCardOrder,
            updateCard:updateCardOrder
         },
         url:"/lists/updateCardOrder",
         dataType:'text',
         error:ajaxError
      });        
   }
  
</script>
  
<script>
function resize(obj) {
  obj.style.height = "1px";
  obj.style.height = (12+obj.scrollHeight)+"px";
}

function updateListName(listNum,listName){
   $.ajax({
   type:'post',
   data:{
      num:listNum,
      name:listName
   },
   url:"/lists/updateListName",
   error:ajaxError
   });
}
</script>

<script>
function getRoleList() {
   $('#role-modal').find('form')[0].reset();
   $('#role-list').empty();
   $('#role-modal').modal('show');
   
   $.ajax({
      type:'get',
      url:"/roles/readRoleList",
      dataType:'json',
      success:function (data) {
         var str='';
         if(data.length>0) { 
            console.log("Getting Role List");
            data.forEach(function(item) {
            str+="<tr id=role" + item.roleNum +">";
            str+='<td>·</td>';
            str+="<td>";
            str+="<p id=updateRole-control" + item.roleNum + ">";
            str+=item.roleName;
            str+="</p>";
            str+="</td>";
            str+="<td>";
            str+="<a href='#modifyRole" + item.roleNum +"' data-toggle='collapse' aria-controls='modifyRole'>";
            str+="<i id=update-btn" + item.roleNum + " class='glyphicon glyphicon-pencil' style='margin-top: 4px; margin-right: 2px;'></i>";
            str+="</a>";
            
            <!-- Modify Role Dropdown -->
            str+="<form id='updateRole-form' class='form-inline'>";
            str+="<div class='collapse' id='modifyRole" + item.roleNum + "'>";
            str+="<div class='input-group' >";
            str+="<div class='input-group' style='margin-top: 15px;'>";
            str+="<input type='text' id='updateRole-form" + item.roleNum + "' class='form-control input-sm' style='width: 100px;' placeholder='New name'>";
            str+="<span class='input-group-addon'>";
            str+="<a href='#' onclick='javascript:updateRole(" + item.roleNum + ")'><i class='fa fa-check'></i></a>";
            str+="</span>";
            str+="</div>";
            str+="</div>";
            str+="</div>"; 
            str+="</form>";
            str+="</td>";
            
            str+="<td>";
            str+="<form name='deleteRole-form'>";
            str+="<input type='hidden' id='roleNum-form" + item.roleNum + "' value='" + item.roleNum + "'>";
            str+="<a href='#' onclick='javascript:deleteRole($(this)," + item.roleNum + ")'>";
            str+="<i class='glyphicon glyphicon-trash' style='margin-top: 4px; margin-right: 2px;'></i>";
            str+="</a>";
            str+="</form>";
            str+="</td>";
            str+="</tr>";
            $('#role-list').html(str);
            });
         }
      },
      error:ajaxError
   });
   }
   
</script>

<%@ include file="/WEB-INF/jsp/modalpage/userProfile.jsp"%>
<%@include file="/WEB-INF/jsp/commons/T_footer.jsp"%>