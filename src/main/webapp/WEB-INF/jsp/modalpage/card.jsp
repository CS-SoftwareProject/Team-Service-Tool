<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta name="viewport" content="width=device-width, initial-scale=1">

<div class="modal fade" id="cardmodal" tabindex="-1" role="dialog" data-focus-on="input:first" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content" id="cardmodal-body" style="padding: 20px; width: 630px; height: 650px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<p id="Title" style="margin-top: -25px; text-align: center;"></p>
				<div id="cardProgress-field"></div>
				<div id="cardAssignee-field"></div>
			</div>
			<form id="card-field" method="post" style="float: left;">
				<input id="cardNum" type="hidden" name="num" />
				<table class="table">
					<tr>
						<td>USER</td>
						<td>
							<div id=card-user></div>
						</td>
					</tr>
					<tr id="card-start">
					</tr>
					<tr>
						<td>SUBJECT</td>
						<td>
							<input id="cardSubject" type="text" class="form-control" style="width: 358px;" name="subject">
						</td>
					</tr>
					<tr>
						<td>CONTENT</td>
						<td>
							<textarea id="cardContent" name="content" class="form-control" rows="13" cols="40"></textarea>
						</td>
					</tr>
					<input id="cardListNum" type="hidden" name="listNum" />
					<input id="cardOrder" type="hidden" name="cardOrder" />
					<input id="progress" type="hidden" name="progress" value="-1" />
				</table>
				<div id="btn-area" style="text-align: center; margin-left: 100px"></div>
			</form>
			<div id="add-menu" style="float: right; display: inline-grid;"></div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/jsp/modalpage/datepicker.jsp"%>
<%@ include file="/WEB-INF/jsp/modalpage/assignee.jsp" %>
<%@ include file="/WEB-INF/jsp/modalpage/progress.jsp" %>

<script>
function showDueDateModal() { 
	$('#duedate-modal').find('form')[0].reset();
 	$('#duedate-modal').modal('show');
}

function showAssigneeModal() {
	var thisClass = this;
	$('#assignee-modal').modal('show');
	if($('#cardNum').val() != 0) {
		$('#assign-form').empty();
	}
	$.ajax({
		type:'get',
		data:{
			cardNum:thisClass.$('#cardNum').val()
		},
		url:"/assignees/assigneelist",
		dataType:'json',
		success:function(data) {
			var str="";
			if(data.length > 0) {
				console.log("Assignee Listing...");
				data.forEach(function(item) {
					console.log("[JS] item : ", item);
					str+="<tr id=assignee" + item.assigneeNum + " class='tr-table'>";
					str+="<input class='assigneeNum' name='assigneeNum' type='hidden' value='" + item.assigneeNum + "'>";
					str+="<td>";
					str+="<p class='assignee-area' style='margin-top: 8px;'>" + item.userId + "</p>";
					str+="</td>";
					str+="<td>";
					str+="<p class='assignee-area' style='margin-top: 8px;'>" + item.roleName + "</p>";
					str+="<input class='roleNum' name'roleNum' type='hidden' value'" + item.roleNum + "'>";
					str+="</td>";
					str+="<td>";
					
					str+="<a class='text-muted' href='#' onclick='javascript:deleteAssignee($(this))'>";
					str+="<i class='glyphicon glyphicon-trash' style='margin-top: 11px; margin-right: 2px;'></i>";
					str+="</a>";
					str+="</td>";
					str+="<td>"
					str+="<a href='#' onclick='javascript:updateAssigneeTable($(this)," + item.assigneeNum + ")'>";
					str+="<i class='glyphicon glyphicon-pencil' style='margin-top: 9px; margin-right: 2px;'></i>";
					str+="</a>";
					str+="</td>";
					str+="</tr>";
					$('#assign-form').html(str);
				});
			}
		},
		error:ajaxError
	});
}

<!-- Fix : Bug when using multiple modal -->
var recentModalList = []; $(document).ready(function () { 
	$.fn.modal.Constructor.prototype.enforceFocus = function () { }; 
	$('.modal').on('shown.bs.modal', function (e) { recentModalList.push(e.target); }); 
	$('.modal').on('hide.bs.modal', function (e) { customModalClosed(e); console.log(recentModalList.length); }); }); 
	var customModalClosed = function (e) {
		for (var i = recentModalList.length - 1; i >= 0; i--) { 
			if (recentModalList[i] == e.target) { recentModalList.splice(i, 1); } 
		}
		if (recentModalList.length > 0) { recentModalList[recentModalList.length - 1].focus(); } 
};

function showProgressModal() {
	$('#progress-modal').modal('show');
}
</script>