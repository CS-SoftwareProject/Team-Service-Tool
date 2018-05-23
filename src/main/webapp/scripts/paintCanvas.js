var undoHistory = [];

//추가부분 시작
function message(){
	alert('업로드 성공');
	reloadSend();
}

function checkImageClick(){
	list=$('.imageCheck:checked');
	var array=new Array();
	for(i=0; i<list.length; i++){
		array.push(list[i].parentNode.attributes.id.value);
	}
	console.log(array);
	deleteImage(JSON.stringify(array));
}

function deleteImage(val){
	console.log(val);
	$.ajax({
	 type:'get',
	 url:'/project/imagedelete',
	 dataType:'json',
	 data:{
		 Image_List:val
	 },
	 success:function(data){
		 if(data==true){
			 canvasInit();
			 clearSend();
			 getImageList();
			 reloadSend();
		 }
		 else ajaxError();
	 }
	});
}

//추가부분 끝

function saveActions() {
   var imgData = document.getElementById("chat-image-area-canvas").toDataURL("image/png"); 
   undoHistory.push(imgData);
   $('#undo').removeAttr('disabled');
}

function undoDraw() {
   if (undoHistory.length > 0) {
      var undoImg = new Image();
      $(undoImg).load(
            function() {
               var context = document.getElementById("chat-image-area-canvas").getContext("2d");
               context.drawImage(undoImg, 0, 0);
            });
      undoImg.src = undoHistory.pop();
      if (undoHistory.length == 0)
         $('#undo').attr('disabled', 'disabled');
   }
}

function canvasInit() {
   context = document.getElementById("chat-image-area-canvas").getContext("2d");
   context.lineCap = "round";
   context.fillStyle = '#fff';
   context.fillRect(0, 0, context.canvas.width, context.canvas.height);
   $('#undo').attr('disabled','disabled');
   $('#chat-image-list-display button').removeClass('selected');
}

function draw(event) {
   var coors = {
      x : event.offsetX,
      y : event.offsetY
   };
   drawer[event.type](coors);
}

$(function() {
   var canvas, cntxt, top, left, draw = 0;
   canvas = document.getElementById("chat-image-area-canvas");
   cntxt = canvas.getContext("2d");
   top = $('#chat-image-area-canvas').offset().top; 
   left = $('#chat-image-area-canvas').offset().left;
   canvasInit();

/*   canvas.addEventListener('touchstart', draw, false);
   canvas.addEventListener('touchmove', draw, false);
   canvas.addEventListener('touchend', draw, false);*/

   var drawer = {
      isDrawing : false,
      touchstart : function(coors) {
         cntxt.beginPath();
         cntxt.moveTo(coors.x, coors.y);
         this.isDrawing = true;
      },
      touchmove : function(coors) {
         if (this.isDrawing) {
            cntxt.lineTo(coors.x, coors.y);
            cntxt.stroke();
         }
      },
      touchend : function(coors) {
         if (this.isDrawing) {
            this.touchmove(coors);
            this.isDrawing = false;
         }
      }
   };

   document.body.addEventListener('touchmove', function(event) {
      event.preventDefault();
   }, false);

   $('#chat-image-area-canvas').mousedown(function(e) {
      if (e.button == 0) {
         draw = 1;
         saveActions();
         cntxt.beginPath();
         cntxt.moveTo(e.offsetX, e.offsetY);
      } else {
         draw = 0;
      }
   }).mouseup(function(e) {
      if (e.button != 0) {  
         draw = 1;
      } else {   
         draw = 0;
         cntxt.lineTo(e.offsetX, e.offsetY);
         cntxt.stroke();
         cntxt.closePath();
         console.log("마우스땜");
         imageSend();
      }
   }).mousemove(function(e) {
      if (draw == 1) {
         cntxt.lineTo(e.offsetX, e.offsetY);
         console.log("좌표: "+(e.offsetX)+","+ (e.offsetY));
         console.log("offset: "+($('#chat-image-area-canvas').offset().left)+","+ ($('#chat-image-area-canvas').offset().top));
         cntxt.stroke();
      }
   });

   $('#export').click(
         function(e) {
            e.preventDefault();
            $('#download').attr('href',canvas.toDataURL("image/png"));
            $('#download')[0].click();
         });
   $('#clear').click(function(e) {
      e.preventDefault();
      canvas.width = canvas.width;
      canvas.height = canvas.height;
      canvasInit();
      $('#chat-image-area-colors li:first').click();
      $('#brush_size').change();
      undoHistory = [];
      clearSend();
   });
   $('#brush_size').change(function(e) {
      cntxt.lineWidth = 1;
   });
   $('#chat-image-area-colors li').click(function(e) {
      e.preventDefault();
      $('#chat-image-area-colors li').removeClass('selected');
      $(this).addClass('selected');
      cntxt.strokeStyle = $(this).css('background-color');
   });

   $('#undo').click(function(e) {
      e.preventDefault();
      undoDraw();
      undoSend();
   });
   
   $('#disconnect').click(function(e){
      e.preventDefault();
      webSocket.close();
   });

})