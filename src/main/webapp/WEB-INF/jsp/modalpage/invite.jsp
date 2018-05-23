<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="viser.project.*"%>
<%@ page import="viser.user.*"%>

<div class="modal fade" id="invite-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="color: black; text-align: center;">
				<h2 class="modal-title" id="MyModalLabel">Invite User</h2>
			</div>
			<div class="modal-body">
			
				<div class="input-group">
					<span class="input-group-addon" aria-hidden="false"> 
					<i class="fa fa-tripadvisor"></i>
					</span> 
					<input type="text" id="keyword" class="form-control input-lg" name="keyword" placeholder="Input userId.." style="width: 455px">
					<button id="search-btn" type="button" class="btn btn-primary btn-lg">검색</button>
				</div>
				
				<div>
					<table class="table" style="color: black; text-align: center; margin-top: 20px;">
						<thead>
							<tr>
								<!-- LIST -->
								<td align="center" width="10">#</td>
								<td align="center" width="60">ID</td>
								<td align="center" width="15">NAME</td>
								<td align="center" width="15">BIRTH</td>
								<td align="center" width="10">Image</td>
							</tr>
						</thead>
						<tbody id="data"></tbody>
					</table>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
function nullkeyword() {
	alert("값을 입력해 주세요.");
}

$('#search-btn').click(function(){
	var key = $('#keyword').val();
	if (key == '')
		location.href='javascript:nullkeyword()';
	else {
	$.ajax({
		type:'get',
		data:{
			keyword:key
		},
		dataType: "json",
		url:"/projects/searchUser",
		success:function(data){
			var str='';
			if(data.length>0){
				console.log("enter");
				data.forEach(function(data){
					str+="<tr>";
					str+="<td>";
					if (data.invited) {
						str+="<button type='button' disabled='true' id='user-btn' class='btn btn-default' aria-label='Left Align'>";
					}
					else {
						str+="<button type='button' id='user-btn' class='btn btn-default' aria-label='Left Align' onclick='inviteUser(`"+data.user.userId+"`,$(this))'>";
					}
					str+="<span class='glyphicon glyphicon-envelope' aria-hidden='true' ></span>";
					str+="</button>";
					str+="</td>";
					str+='<td>'+data.user.userId+'</td>';
					str+='<td>'+data.user.name+'</td>';
					str+='<td>'+data.user.birth+'</td>';
					str+='<td><img src='+data.user.image+' style="width: 33px;"></td>';
					str+="</tr>";
					$('#data').html(str);
				});	
			}
	
			else{
				str+='<tr>';
				str+='<td colspan="5">';
				str+='<div class="alert alert-warning alert-dismissible" style="margin-top: 5%;">';
				str+='<h4><i class="icon fa fa-warning"></i>Not Found Error</h4>';
				str+='해당 검색 값에 대한 사용자 정보가 존재하지 않습니다.';
				str+='</div>';
				str+='</td>';
				str+='</tr>';
				$('#data').html(str);
			}
		},
		error:ajaxError
	});
	}
});	

function inviteUser(user, target) {
	if (confirm(user + "님을 초대 하시겠습니까?")) {
		$.ajax({
			type:'get',
			url: "/projects/inviteUser?userId=" + user,
			success:function(){
				target.attr("disabled", true);
			} 
		});
	} else {
		return;
	}
}
</script>