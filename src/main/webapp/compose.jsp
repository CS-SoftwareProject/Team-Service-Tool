<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/commons/T_header.jsp"%>
<!-- bootstrap wysihtml5 - text editor -->
<link rel="stylesheet" href="/resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Mailbox
        <small>13 new messages</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Mailbox</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-md-3">
          <a href="/users/messagelist" class="btn btn-primary btn-block margin-bottom">Back to Inbox</a>

          <div class="box box-solid">
            <div class="box-header with-border">
              <h3 class="box-title">Folders</h3>

              <div class="box-tools">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
              </div>
            </div>
            <div class="box-body no-padding">
              <ul class="nav nav-pills nav-stacked">
                <li><a href="/users/messagelist"><i class="fa fa-inbox"></i> Inbox
                  <span class="label label-primary pull-right">12</span></a></li>
                <li><a href="/users/sendmessagelist"><i class="fa fa-envelope-o"></i> Sent</a></li>
                <li><a href="#"><i class="fa fa-file-text-o"></i> Drafts</a></li>
                <li><a href="#"><i class="fa fa-filter"></i> Junk <span class="label label-warning pull-right">65</span></a>
                </li>
                <li><a href="#"><i class="fa fa-trash-o"></i> Trash</a></li>
              </ul>
            </div>
            <!-- /.box-body -->
          </div>
        </div>
        <!-- /.col -->
        <div class="col-md-9">
          <div class="box box-primary">
            <form action="/users/createMessage" method="post">
            <div class="box-header with-border">
              <h3 class="box-title">Compose New Message</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <div class="form-group To">
                <input class="form-control receiver" type="text" name="receiver" onkeyup="realTimeSearchUser()" placeholder="사용자 ID를 입력하세요.">
                <div class="pop-over" style="width: 230px;" onblur="leaveReceiverForm()">
                  <div class="pop-over-content">;
                  
                  </div>
                </div>
              </div>
              <div class="form-group">
                <input class="form-control" name="subject" placeholder="Subject:">
              </div>
              <div class="form-group">
                    <textarea id="compose-textarea" name="content" class="form-control" style="height: 300px" placeholder="Entered content ..">
                    </textarea>
              </div>
              <div class="form-group">
                <div class="btn btn-defaut btn-file">
                  <i class="fa fa-paperclip"></i> Attachment
                  <input type="file" name="attachment">
                </div>
                <p class="help-block">Max. 32MB</p>
              </div>
            </div>
            <!-- /.box-body -->
            <div class="box-footer">
              <div class="pull-right">
                <button type="button" class="btn btn-default"><i class="fa fa-pencil"></i> Draft</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-envelope-o"></i> Send</button>
              </div>
              <button type="reset" class="btn btn-default" onclick="location.href='/users/messagelist'"><i class="fa fa-times"></i> Discard</button>
            </div>
            <!-- /.box-footer -->
            </form> 
          </div>
          <!-- /. box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper --> 
<%@include file="/WEB-INF/jsp/commons/T_footer.jsp"%>

<!-- Bootstrap WYSIHTML5 -->
<script src="/resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>

<script>
  $(function () {
    //Add text editor
    $("#compose-textarea").wysihtml5();
  });
  
  function realTimeSearchUser() {
	  var key = $('.receiver').val();
	  if(key != '') {
		  $.ajax({
			  type:'get',
			  url:'/message/realtimelist',
			  dataType:'json',
			  data:{
				  keyword:key
			  },
			  success:function(data) {
				  var str = '';
				  if(data.length > 0) {
				  	data.forEach(function(data){
					  str+='<div>';
					  str+='<a href="javascript:selectFromSearchList(`' + data.userId + '`)">';
					  str+='<div class="search-user-item">';
					  str+='<img src='+ data.image +' class="search-user-image">';
					  str+='<h4>' + data.userId + '</h4>';
					  str+='<h4>' + data.name + '</h4>';
					  str+='</div>';
					  str+='</a>';
					  str+='</div>';
					});
				  }
				  else {
					  str+='<div class="search-user-notfound">';
					  str+='<a><h3>Not Found</h3></a>';
					  str+='</div>';
				  }
				  $('.pop-over').addClass("mod-search-over");
				  $('.pop-over').addClass("is-shown");
				  $('.pop-over-content').html(str);
			  }
		  });
	  }
	  else {
		  $('.pop-over').removeClass("mod-search-over");
		  $('.pop-over').removeClass("is-shown");
	  }
  }
  
  function leaveReceiverForm() {
	  $.ajax({
		  success:function() {
			  $('.receiver').val('');
			  $('.pop-over').removeClass("mod-search-over");
			  $('.pop-over').removeClass("is-shown");
			  $('.pop-over-content').text('');
		  }
	  });
  }
  
  function selectFromSearchList(userId) {
	  $.ajax({
		  success:function() {
			  $('.receiver').val(userId);
			  $('.pop-over').removeClass("mod-search-over");
			  $('.pop-over').removeClass("is-shown");
			  $('.pop-over-content').text('');
		  }
	  });
  }
</script>