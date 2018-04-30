<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<link rel="stylesheet" href="/resources/dist/css/jquery-ui.css">

<!-- Progress -->
<div class="modal fade" id="progress-modal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content" style="width: auto; height: auto;">
			<div class="modal-header" style="text-align: center;">
				<button id="closeProgress-btn" type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3>Setting Progress</h3>
			</div>
			<div class="box-body">
				<div class="form-group">
				<form>
				  <div class="input-group date">
				  	<label for="amount">진행률 : </label>
				  	<input type="text" id="amount" name="amount" readonly style="border:0; color:#f6931f; font-weight:bold;">
				  </div>
				  <div id="slider-range-min" class="progress active">
				    <div id="progressGage" class="progress-bar progress-bar-primary progress-bar-striped" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
				      <span class="sr-only"></span>
				    </div>
				  </div>
				  <div class="btn-group" style="display: inline-flex; margin-top: 10px; margin-left: 60px;">
				  	<div id="progress-control"></div>
					<button id="close-btn" class="btn btn-default" data-dismiss="modal">Cancel</button>
				  </div>
				</form>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
$( function() {
	$( "#slider-range-min" ).slider({
	  range: "min",
	  min: 0	,
	  max: 100,
	  slide: function( event, ui ) {
	    $( "#amount" ).val(ui.value );
		  console.log("hi~~~~~~~~~",ui.value);
		$( "#progressGage" ).css( "width", $( "#amount" ).val()+"%" );
	  }
	});
	$( "#amount" ).val($( "#slider-range-min" ).slider( "value" ) );
	$( "#progressGage" ).css( "width",$( "#amount" ).val()+"%" );
} );

function applyProgressToCard() {
	console.log("TEST : ", $('#amount').val());
	var amount = $('#amount').val();
	$.ajax ({
		success:function() {
			var str='';
			str+="<div id='progressBar' class='progress progress-sm active' style='width: 600px;margin-left: -20px;'>";
			str+="<div id='progressLabel' class='progress-bar progress-bar-success progress-bar-striped' role='progressbar' aria-valuenow='20' aria-valuemin='0' aria-valuemax='100' style='width:" + amount + "%'>";
			str+="<p style='margin-top: -5px;'>" + amount + "%</p>"
			str+="</div>";
			str+="</div>";
			$('#cardProgress-field').html(str);
			$('#progress').val(amount);
			$('#closeProgress-btn').click();
		}
	})
}

function cuProgress() {
	$.ajax({
		type:'get',
		data:{
			cardNum:$('#cardNum').val(),
			progress:$('#amount').val(),
			cardListNum:$('#cardListNum').val()
			},
		url:"/cards/cuProgress",
		dataType: "json",
		
		success:function(data){
			var str='';
			str+="<div id='progressBar' class='progress progress-sm active' style='width: 600px;margin-left: -20px;'>";
			str+="<div id='progressLabel' class='progress-bar progress-bar-success progress-bar-striped' role='progressbar' aria-valuenow='20' aria-valuemin='0' aria-valuemax='100' style='width:" + data.progress + "%'>";
			str+="<p style='margin-top: -5px;'>" + data.progress + "%</p>"
			str+="</div>";
			str+="</div>";
			$('#cardProgress-field').html(str);
			$('#closeProgress-btn').click();
		},
		error:ajaxError
	});
}
</script>