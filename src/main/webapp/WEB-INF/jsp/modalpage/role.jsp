<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Font Awesome -->
<link rel="stylesheet" href="/resources/bower_components/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="/resources/bower_components/Ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="/resources/dist/css/AdminLTE.min.css">

<!-- Role -->
<div class="modal fade" id="role-modal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content" style="width: auto; height: auto;">
			<div class="modal-header" style="text-align: center; text-align: center;">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3>Setting Roles</h3>
			</div>
			<div class="box-body">
				<div class="form-group">
					<form id="createRole-form" class="form-horizontal">
						<div class="input-group">
							<span class="input-group-addon" aria-hidden="false"> 
							<i class="fa fa-child"></i>
							</span> 
							<input class="form-control input-sm" type="text" id="roleName" name="roleName" placeholder="Enter the Role of Assignee" style="width: 190px; height: 34px;">
							<button id="roleAppley-btn" class="btn btn-success" style="position: absolute;" type="button" onclick="createRole();">Apply</button>
						</div>
						
						<table class="table" style="color:black; text-align:center; margin-top:10px;">
							<tr>
								<!-- Created Role list -->
								<td align="center" width="10%">#</td>
								<td align="center" width="60%">ROLE</td>
								<td align="center" width="25%">MODIFY</td>
								<td align="center" width="25%">DELETE</td>
							</tr>
							<tbody id="role-list"></tbody>
						</table>
						
						<div class="btn-group" style="margin-left: 105px; margin-top: 10px;">
							<button id="roleClose-btn" class="btn btn-default" data-dismiss="modal">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
function createRole() {
	$.ajax({
		type:'get',
		data:{
			roleName:$('#roleName').val()
		},
		url:"/roles/createRole",
		dataType:'json',
		
		success:function (data) {
			console.log(data.roleNum);
			console.log(data.roleName);
			var str='';
			str+="<tr id=role" + data.roleNum +">";
			str+='<td>·</td>';
			str+="<td>";
			str+="<p id=updateRole-control" + data.roleNum + ">";
			str+=data.roleName;
			str+="</p>";
			str+="</td>";
			str+="<td>";
			str+="<a href='#modifyRole" + data.roleNum +"' data-toggle='collapse' aria-controls='modifyRole'>";
			str+="<i id=update-btn" + data.roleNum + " class='glyphicon glyphicon-pencil' style='margin-top: 4px; margin-right: 2px;'></i>";
			str+="</a>";
			
			<!-- Modify Role Dropdown -->
			str+="<form id='updateRole-form' class='form-inline'>";
			str+="<div class='collapse' id='modifyRole" + data.roleNum + "'>";
			str+="<div class='input-group' >";
			str+="<div class='input-group' style='margin-top: 15px;'>";
			str+="<input type='text' id='updateRole-form" + data.roleNum + "' class='form-control input-sm' style='width: 100px;' placeholder='New name'>";
			str+="<span class='input-group-addon'>";
			str+="<a href='#' onclick='javascript:updateRole(" + data.roleNum + ")'><i class='fa fa-check'></i></a>";
			str+="</span>";
			str+="</div>";
			str+="</div>";
			str+="</div>"; 
			str+="</form>";
			str+="</td>";
			
			str+="<td>";
			str+="<form name='deleteRole-form'>";
			str+="<input type='hidden' id='roleNum-form" + data.roleNum + "' value='" + data.roleNum + "'>";
			str+="<a href='#' onclick='javascript:deleteRole($(this)," + data.roleNum + ")'>";
			str+="<i class='glyphicon glyphicon-trash' style='margin-top: 4px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</form>";
			str+="</td>";
			str+="</tr>";
			$('#role-list').append(str);
			$('#createRole-form')[0].reset();
			$('#')
		},
		error:ajaxError
	});
}

function deleteRole(target, id) {
	$.ajax({
		type:'get',
		data:{
			roleNum:$('#roleNum-form' + id).val()
		},
		url:"/roles/deleteRole",
		dataType:'json',
		
		success:function () {
			target.closest("tr").empty();
			console.log(target);
		},
		error:ajaxError
	});
}

function updateRole(id) {
	$.ajax({
		type:'get',
		data:{
			roleNum:$('#roleNum-form' + id).val(),
			roleName:$('#updateRole-form' + id).val()
		},
		url:"/roles/updateRole",
		dataType:'json',
		
		success:function (data) {
			console.log("Data = ", data);
			console.log("1 = ", data[0]);
		 	$('#updateRole-control' + data[1]).html(data[0]);
		 	$('#updateRole-form')[0].reset();
		 	$('#update-btn' + data[1]).click();
		},
		error:ajaxError
	});
}

$('#roleClose-btn').click(function () {
	$('#role-list').empty();
})
</script>