var webSocket = new WebSocket('ws://localhost:8080/chat');
var inputMessage = document.getElementById('inputMessage');
var imageId;
var flag=true;

webSocket.onerror = function(event) {
  onError(event)
};
webSocket.onopen = function(event) {
  onOpen(event);
};
webSocket.onmessage = function(event) {
    onMessage(event)
  };
 
  
function onMessage(event) {
   var jsonDecode=JSON.parse(event.data);
   
   
   if(jsonDecode[0].header=='Image'){
   console.log("이미지 받음 :"+jsonDecode[0].data);
   saveActions();
   
    var undoImg = new Image();
      $(undoImg).load(
            function() {
               var context = document.getElementById("chat-image-area-canvas").getContext("2d");
               context.drawImage(undoImg, 0, 0);
            });
      undoImg.src = jsonDecode[0].data;
   }   
    
    else if(jsonDecode[0].header=='undo'){
       undoDraw();
    }
    else if(jsonDecode[0].header=='sync'){
      console.log("요청 받음");
       imageSend();
       clickSync();
    }
    else if(jsonDecode[0].header=='Text'){
    	var temp='';
    	temp+="<div class="+"'direct-chat-msg'>";
        temp+="<div class="+"'direct-chat-info clearfix'>";
          temp+="<span class="+"'direct-chat-name pull-left'>"+jsonDecode[0].user+"</span>";
          temp+="<span class="+"'direct-chat-timestamp pull-right'>"+jsonDecode[0].time+"</span>";
        temp+="</div>";
        temp+="<img class="+"'direct-chat-img'"+ "src='"+jsonDecode[0].profile+"' alt='User Image'>";
        temp+="<div class="+"'direct-chat-text'>";
        temp+=jsonDecode[0].data;
        temp+="</div>";
      temp+="</div>";
       $('.direct-chat-messages').append(temp);
       fixScrollDown();
    }
    else if(jsonDecode[0].header=='click'){
       imageId=jsonDecode[0].data;
       if(imageId!=''&&imageId!=null){
       $('#chat-image-list-display button').removeClass('selected');
       var clickcontext=document.getElementById(imageId);
       clickcontext.className='selected';
       }
    }
    else if(jsonDecode[0].header=='reload'){
       window.location.reload();
    }
    else{
       canvasInit();
       $('#chat-image-list-display button').removeClass('selected');
    }
}
function onOpen(event) {
   console.log("세션 열림");
   $('.direct-chat-messages').append("---------- 지난 대화들 ----------\n");
   fixScrollDown();
  requestNowImage();
}
function onError(event) {
  alert(event.data);
}
function requestNowImage(){
   var jsonEncode =new Array(
      {header:'sync'}      
   );
   console.log("요청 보냄");
   webSocket.send(JSON.stringify(jsonEncode));   
}

function textSend(){
	var time=new Date();
	var temp='';
	console.log("Date :", new Date());
	console.log(new Date().format("y-MM-dd HH:mm"));
	temp+="<div class="+"'direct-chat-msg right'>";
    temp+="<div class="+"'direct-chat-info clearfix'>";
    temp+="<span class="+"'direct-chat-name pull-right'>"+now_userId+"</span>";
    temp+="<span class="+"'direct-chat-timestamp pull-left'>"+time.format("y-MM-dd HH:mm")+"</span>";
    temp+="</div>";
    temp+="<img class="+"'direct-chat-img'"+ "src='"+now_userProfile+"' alt='User Image'>";
    temp+="<div class="+"'direct-chat-text'>";
    temp+=inputMessage.value;
    temp+="</div>";
  temp+="</div>";
   $('.direct-chat-messages').append(temp);
	
   $.ajax({
	   type:'post',
	   dataType:'json',
	   data:{
		   chatMessage:inputMessage.value,
		   chatTime:time.getTime(),
		   writer:now_userId,
	   },
	   url:'/projects/createChatMessage',
	   success:function(data){
		   if(data!=true){
			   ajaxError();
		   }
	   }
   });
   
   fixScrollDown();
   
   jsonEncode=new Array(
      {header:'Text' ,
      user:now_userId,
      time:time.format("y-MM-dd HH:mm"),
      profile:now_userProfile,
      data:inputMessage.value
      }
   );
   webSocket.send(JSON.stringify(jsonEncode));
   inputMessage.value = "";

}
function imageSend() {
   var jsonEncode = new Array(
      {header:'Image',
      data: document.getElementById("chat-image-area-canvas").toDataURL("image/png")   
      }
   );
   webSocket.send(JSON.stringify(jsonEncode));
}
function undoSend() {
   var jsonEncode = new Array(
      {header:'undo'}      
   );
    webSocket.send(JSON.stringify(jsonEncode));
}
function clearSend() {
   var jsonEncode=new Array(
      {header:'clear'}   
   );
    webSocket.send(JSON.stringify(jsonEncode));
}
function clickSync(){
   var jsonEncode=new Array(
      {header:'click',
      data:imageId
      }   
   );
   webSocket.send(JSON.stringify(jsonEncode));
}
function reloadSend(){
   var jsonEncode=new Array(
      {header:'reload'}   
   );
   webSocket.send(JSON.stringify(jsonEncode));
}

  // run the currently selected effect
  function runEffect() {
    // get effect type from
    var selectedEffect = 'slide';

    // Most effect types need no options passed by default
    var options = {};
    // Run the effect
    $( "#effect" ).show( selectedEffect, options, 500 );
  }

  function fixScrollDown() {
	  $('.direct-chat-messages').scrollTop($('.direct-chat-messages').prop("scrollHeight"));
	  scrollFix();
  }

function confirmScrollHeight(){
	//스크롤이 끝이 아닐때
	if(($('.direct-chat-messages').scrollTop()+$('.direct-chat-messages').outerHeight())<$('.direct-chat-messages').prop("scrollHeight")-2){
		flag=false;
		runEffect();
	}
	//끝일때
	else{
		flag=true;
		$( "#effect" ).hide();
	}
}
function scrollFix(){
	confirmScrollHeight();
	if(flag==true){
		//맨 아래로 스크롤 고정
		$('.direct-chat-messages').scrollTop($('.direct-chat-messages').prop("scrollHeight"));
	}
}