<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal fade" id="userProfile-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style="width: 430px; margin-left: 15%;">
			<div class="modal-header" style="color: black; text-align: center;"><h3>User Profile</h3></div>
			<div class="modal-body">
			 <!-- Profile Image -->
	          <div class="box box-primary">
	            <div class="box-body box-profile">
	              <img id="profile-userImage" class="profile-user-img img-responsive img-circle" alt="User profile picture">
	
	              <h3 id="profile-userId" class="profile-username text-center">Nina Mcintire</h3>
	
	              <p id="profile-userName" class="text-muted text-center">Software Engineer</p>
	
	              <ul class="list-group list-group-unbordered">
	                <li class="list-group-item">
	                  <b>Birth</b> <a id="profile-userBirth" class="pull-right">1,322</a>
	                </li>
	                <li class="list-group-item">
	                  <b>Email</b><a id="profile-userEmail" class="pull-right">543</a>
	                </li>
	                <li class="list-group-item">
	                  <b>Join</b>
		                  <table class="table table-bordered" style="margin-top: 10px; margin-left: 10%; width: 80%;">
							<tr>
								<th style="width: 10px">#</th>
								<th>PROJECT</th>
							</tr>
							<tbody id="profile-joinlist"></tbody>
						  </table>
	                </li>
	              </ul>
	              <button type="button" class="btn btn-primary btn-block" data-dismiss="modal"><b>Close</b></a>
	            </div>
	            <!-- /.box-body -->
	          </div>
	          <!-- /.box -->
			</div>
		</div>
	</div>
</div>

<script>

</script>