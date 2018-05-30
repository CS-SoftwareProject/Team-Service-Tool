<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- Modal -->
<div class="modal fade" id="createTimeline" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">타임라인 글쓰기</</h4>
      </div>
      <div class="modal-body">
	   <form class="form-inline" action="/timelines/createContent" method="post">
         <table class = "table table-stripeds" style = "text-align : center; border : 1px solid #dddddd">
          <tbody>
              <tr>
                  <td><textarea class = "form-control" placeholder ="글 내용" name = "timeLineContent" maxlength = "2048" style = "height : 150px; width: -webkit-fill-available;"> </textarea></td>
              </tr>
          </tbody>
         </table>
  	 	 <div class="modal-footer">
           <input type = "hidden" name = "userId" value = "${user.userId}">
       	   <input type = "hidden" name = "timelineNum" value = "${timelineNum}">
   		   <button type="button" class="btn btn-default" data-dismiss="modal">나가기</button>
   		   <button type="submit" class="btn btn-primary">등록하기</button>
   	     </div>
  	   </form>
    </div>
  </div>
</div>