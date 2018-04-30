<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Due date -->
<div class="modal fade" id="duedate-modal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content" style="width: auto; height: auto; margin-top: 200px;">
			<div class="modal-header" style="text-align: center;">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3>Due Date</h3>
				<div class="box-body">
					<div class="form-group">
						<form>
							<label>Date:</label>
							<div class="input-group date" style="margin-bottom: 10px;">
								<div class="input-group-addon">
									<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
								</div>
								<input type="text" class="form-control pull-right" id="datepicker" name="start"  placeholder="날짜를 선택하세요." >
							</div>
							<div class="input-group duration">
								<div class="input-group-addon">
									<span class="fa fa-hourglass-half" aria-hidden="true"></span>
								</div>
								<input type="text" class="form-control pull-right" id="duration" name="duration" placeholder="진행 일 수를 입력하세요.">
							</div>	
							<div class="btn-group" style="display: inline-flex; margin-top: 10px;">
								<button id="apply-btn" class="btn btn-success" style="margin-right: 40px;" type="button" onclick="createStart();">Apply</button>
								<button id="close-btn" class="btn btn-default" data-dismiss="modal">Cancel</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- bootstrap datepicker -->
<script src="/resources/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
<script>
$('#datepicker').datepicker({
	autoclose : true,
	format : 'yyyy-mm-dd',
})

// -- DueDate Ajax --
function ajaxError(){
	   alert("데이터 로딩 오류");
}

function createStart(){
	$.ajax({
		type:'get',
		data:{
			cardNum:$('#cardNum').val(),
			start:new Date($('#datepicker').val()).getTime(),
			duration:$('#duration').val()
			},
		url:"/cards/CreateDueDate",
		dataType: "json",
		
		success:function(data){
			var str='';
			$('#card-start').html(str);
			console.log("date",new Date(data.start).format("y-MM-dd"));
			console.log("Connected Ajax");
			console.log(data.cardNum);
			console.log(data.start);
			console.log("Due date Added");
				str+="<td>START DATE</td>";
				str+="<td>";
				str+="<input id='start' type='hidden' class='form-control' name='start' value='" + new Date(data.start).format("y-MM-dd") + "'>";
				str+=new Date(data.start).format("y-MM-dd");
				str+="</td>";
				$('#card-start').html(str);
		},
		
 		complete:function() {
			$('#close-btn').click();
		}, 
		error:ajaxError
	});
}
</script>