<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal fade" id="uploadmodal" tabindex="-1" role="dialog" aria-labelledby="myUpload" aria-hidden="true" data-backdrop="false">
  <div class="modal-dialog">
    <div class="modal-content" style="margin-top: 150px;">
      <div class="modal-header" style="text-align:center;">
      <h3>Upload Image</h3>
   	  </div>
	      <div class="modal-body">
			<h2 class="modal-title" id="MyModalLabel"></h2>
			<form action="/imageUpload" method="post" enctype="multipart/form-data">
				<div style="display: -webkit-inline-box;">
				<h4>첨부파일 :</h4>
				<input type="file" class="form-control" name="uploadFile" accept="image/*" value="보내기" style="border: 0px" ><br/>
				</div>
				<div style="display: -webkit-inline-box;margin-left: 35%; margin-top: 30px;">
					<button type="submit" class="btn btn-default" style="margin-right: 20px;">보내기</button>
				    <button type="button" class="btn btn-default" data-dismiss="modal" aria-label="Close">Close</button>
				</div>
			</form>
		  </div>

	</div>
  </div>
</div>
