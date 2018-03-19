<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<!-- Gantt style -->
  <link rel=stylesheet href="platform.css" type="text/css">
  <link rel=stylesheet href="libs/jquery/dateField/jquery.dateField.css" type="text/css">

  <link rel=stylesheet href="gantt.css" type="text/css">
  <link rel=stylesheet href="ganttPrint.css" type="text/css" media="print">


<!-- Gantt -->
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

  <script src="libs/jquery/jquery.livequery.1.1.1.min.js"></script>
  <script src="libs/jquery/jquery.timers.js"></script>

  <script src="libs/utilities.js"></script>
  <script src="libs/forms.js"></script>
  <script src="libs/date.js"></script>
  <script src="libs/dialogs.js"></script>
  <script src="libs/layout.js"></script>
  <script src="libs/i18nJs.js"></script>
  <script src="libs/jquery/dateField/jquery.dateField.js"></script>
  <script src="libs/jquery/JST/jquery.JST.js"></script>

  <script type="text/javascript" src="libs/jquery/svg/jquery.svg.min.js"></script>
  <script type="text/javascript" src="libs/jquery/svg/jquery.svgdom.1.8.js"></script>


  <script src="ganttUtilities.js"></script>
  <script src="ganttTask.js"></script>
  <script src="ganttDrawerSVG.js"></script>
  <script src="ganttGridEditor.js"></script>
  <script src="ganttMaster.js"></script>  
</head>

<body>


<div id="workSpace" style="padding:0px; overflow-y:auto; overflow-x:hidden;border:1px solid #e5e5e5;position:relative;margin:0 5px"></div>

<style>
  .resEdit {
    padding: 15px;
  }

  .resLine {
    width: 95%;
    padding: 3px;
    margin: 5px;
    border: 1px solid #d0d0d0;
  }

  body {
    overflow: hidden;
  }

  .ganttButtonBar h1{
    color: #000000;
    font-weight: bold;
    font-size: 28px;
    margin-left: 10px;
  }

</style>


<script type="text/javascript">
var ganttNum='${ganttNum}';
var userId='${user.userId}';

var ge;
$(function() {
  var canWrite=true; //this is the default for test purposes

  // here starts gantt initialization
  ge = new GanttMaster();
  ge.set100OnClose=true;

  ge.init($("#workSpace"));
  loadI18n(); //overwrite with localized ones

  //in order to force compute the best-fitting zoom level
  delete ge.gantt.zoom;


  loadGanttFromServer();

});



function loadGanttFromServer() {

  /* //this is a simulation: load data from the local storage if you have already played with the demo or a textarea with starting demo data
  loadFromLocalStorage(); */

  //this is the real implementation
  //var taskId = $("#taskSelector").val();
  //var prof = new Profiler("loadServerSide");
  //prof.reset();

  $.ajax({ 
		data:{
			ganttNum:ganttNum
			},
		url:"/api/gantts/loadGantt",
		dataType:'json',
		type:'get',
		success:function(data) {
    		console.log("response: "+data);
    		
    		if (!data.canWrite)
    		    $(".ganttButtonBar button.requireWrite").attr("disabled","true");

    		  ge.loadProject(data);
    		  ge.checkpoint(); //empty the undo stack

    		  ge.editor.element.oneTime(100, "cl", function () {$(this).find("tr.emptyRow:first").click()}); 
      	}, 
      	error:ajaxError
  });
}


function saveGanttOnServer() {
 
/*   //this is a simulation: save data to the local storage or to the textarea
  saveInLocalStorage();  */

  
  var gantt = ge.saveProject();

/*   delete prj.resources;
  delete prj.roles; */
 
  /* var prof = new Profiler("saveServerSide");
  prof.reset(); 
  
  if (ge.deletedTaskIds.length>0) {
    if (!confirm("TASK_THAT_WILL_BE_REMOVED\n"+ge.deletedTaskIds.length)) {
      return;
    }
  } */

   $.ajax({
	url:"/gantts/saveGantt",	  
    dataType:"json",
    data: {
    	gantt:JSON.stringify(gantt),
    	userId:userId,
    	ganttNum:ganttNum
    	},
    type:"POST",

     success: function(data) {
       // prof.stop();
       
        if (data) {
       	  console.log("load gantt after save",data);
          ge.loadProject(data); //must reload as "tmp_" ids are now the good ones
        }
     	else {
       		console.log("load error after save");
     		ge.reset();
      }
    }, 
    error:ajaxError
  });
  
}

function ajaxError(){
	   alert("데이터 로딩 오류");
	}

//-------------------------------------------  Create some demo data ------------------------------------------------------

function clearGantt() {
  ge.reset();
}

function loadI18n() {
  GanttMaster.messages = {
    "CANNOT_WRITE":                  "CANNOT_WRITE",
    "CHANGE_OUT_OF_SCOPE":"NO_RIGHTS_FOR_UPDATE_PARENTS_OUT_OF_EDITOR_SCOPE",
    "START_IS_MILESTONE":"START_IS_MILESTONE",
    "END_IS_MILESTONE":"END_IS_MILESTONE",
    "TASK_HAS_CONSTRAINTS":"TASK_HAS_CONSTRAINTS",
    "GANTT_ERROR_DEPENDS_ON_OPEN_TASK":"GANTT_ERROR_DEPENDS_ON_OPEN_TASK",
    "GANTT_ERROR_DESCENDANT_OF_CLOSED_TASK":"GANTT_ERROR_DESCENDANT_OF_CLOSED_TASK",
    "TASK_HAS_EXTERNAL_DEPS":"TASK_HAS_EXTERNAL_DEPS",
    "GANTT_ERROR_LOADING_DATA_TASK_REMOVED":"GANTT_ERROR_LOADING_DATA_TASK_REMOVED",
    "ERROR_SETTING_DATES":"ERROR_SETTING_DATES",
    "CIRCULAR_REFERENCE":"CIRCULAR_REFERENCE",
    "CANNOT_DEPENDS_ON_ANCESTORS":"CANNOT_DEPENDS_ON_ANCESTORS",
    "CANNOT_DEPENDS_ON_DESCENDANTS":"CANNOT_DEPENDS_ON_DESCENDANTS",
    "INVALID_DATE_FORMAT":"INVALID_DATE_FORMAT",
    "TASK_MOVE_INCONSISTENT_LEVEL":"TASK_MOVE_INCONSISTENT_LEVEL",

    "GANTT_QUARTER_SHORT":"trim.",
    "GANTT_SEMESTER_SHORT":"sem."
  };
}



//-------------------------------------------  Get project file as JSON (used for migrate project from gantt to Teamwork) ------------------------------------------------------

function loadFromLocalStorage() {
	console.log('ganttNum:',ganttNum);
  var ret;
  if (localStorage) {
    if (localStorage.getObject("teamworkGantDemo")) {
      ret = localStorage.getObject("teamworkGantDemo");
    }
  }

  //if not found create a new example task
  if (!ret || !ret.tasks || ret.tasks.length == 0){
	  ret= {"tasks":    [
	      {"cardNum": 1, "subject": "sdfsdf","start":1508079600000 ,"content": "fdf","assigs": [{assigneeNum: 1, projectMemberNum: 1, roleNum: 3}]}
/* 	      {"id": 4, "name": "editor part", "progress": 0, "progressByWorklog": false,  "description": "", "level": 2, "status": "STATUS_SUSPENDED",  "canWrite": true, "start": 1507906800000, "duration": 4, "end": 1397685670000,"assigs": [{id: 1, resourceId: 1, roleId: 1},{id: 2, resourceId: 2, roleId: 2}], "hasChild": false},
	      {"id": 5, "name": "editor part", "progress": 0, "progressByWorklog": false,  "description": "", "level": 0, "start": 1507819923240, "duration": 0,"hasChild": false} */
	    ],
      "resources": [
      {"num": 1, "userId": "flzl2008"},
      {"num": 2, "userId": "test"}
    ],
      "roles":       [
      {"roleNum": 2, "roleName": "A"},
      {"roleNum": 3, "roleName": "B"}
    ], 
    "canWrite":    true, 
    "canDelete":true, 
    "canWriteOnParent": true, 
    "zoom": "w3",
    "addListNum":2 ,
    "boardNum":1
    }
  }
  return ret;
}


function saveInLocalStorage() {
  var prj = ge.saveProject();
  if (localStorage) {
    localStorage.setObject("teamworkGantDemo", prj);
  }
}


function editRoles(){

  //make role editor
  var roleEditor = $.JST.createFromTemplate({}, "ROLE_EDITOR");
  var roleTbl=roleEditor.find("#rolesTable");

  for (var i=0;i<ge.roles.length;i++){
    var role=ge.roles[i];
    roleTbl.append($.JST.createFromTemplate(role, "ROLE_ROW"))
  }


  //bind add role
  roleEditor.find("#addRole").click(function(){
    roleTbl.append($.JST.createFromTemplate({roleNum:"new",roleName:"role"}, "ROLE_ROW"))
  });

  //bind save event
  roleEditor.find("#roleSaveButton").click(function(){
	var oldProject=JSON.parse(ge.__undoStack[0]);
	
    var newRoles=[];
    //find for deleted or changed roles
    for (var i=0;i<ge.roles.length;i++){
      var role=ge.roles[i];
      var row = roleEditor.find("[roleId="+role.roleNum+"]");
      if (row.length>0){
        //if still there save it
        var name = row.find("input[name]").val();
        if (name && name!=""){
        	//check changed role.name
        	if(role.roleName!=name){
    	  		ge.roleUnchanged=false;
        	}
        	role.roleName=name;
        }
        newRoles.push(role);
      } else {
    	  ge.deletedRoleIds.push(role.roleNum);
        //remove assignments
        for (var j=0;j<ge.tasks.length;j++){
          var task=ge.tasks[j];
          var newAss=[];
          for (var k=0;k<task.assigs.length;k++){
            var ass=task.assigs[k];
            if (ass.roleNum!=role.roleNum){
              newAss.push(ass);
            }
          }
          task.assigs=newAss;
        }
      }
    }

    //loop on new rows
    var cnt=0
    roleEditor.find("[roleId=new]").each(function(){
      cnt++;
      var row = $(this);
      var name = row.find("input[name]").val();
      if (name && name!=""){
    	  newRoles.push (new Role(parseInt(new Date().getTime()/(cnt*-1)),name,false));
    	  ge.roleUnchanged=false;
      }
    });
	
    console.log("newRoles",newRoles);
    ge.roles=newRoles;

    closeBlackPopup();
    ge.redraw();
  });


  var ndo = createModalPopup(400, 500).append(roleEditor);
}
</script>





<div id="gantEditorTemplates" style="display:none;"> 
<div class="__template__" type="GANTBUTTONS"><!--
  <div class="ganttButtonBar noprint">
    <div class="buttons">

      <button onclick="$('#workSpace').trigger('undo.gantt');return false;" class="button textual icon requireCanWrite" title="undo"><span class="teamworkIcon">&#39;</span></button>
      <button onclick="$('#workSpace').trigger('redo.gantt');return false;" class="button textual icon requireCanWrite" title="redo"><span class="teamworkIcon">&middot;</span></button>
      <span class="ganttButtonSeparator requireCanWrite requireCanAdd"></span>
      <button onclick="$('#workSpace').trigger('addAboveCurrentTask.gantt');return false;" class="button textual icon requireCanWrite requireCanAdd" title="insert above"><span class="teamworkIcon">l</span></button>
      <button onclick="$('#workSpace').trigger('addBelowCurrentTask.gantt');return false;" class="button textual icon requireCanWrite requireCanAdd" title="insert below"><span class="teamworkIcon">X</span></button>
      <span class="ganttButtonSeparator requireCanWrite requireCanInOutdent"></span>
      <button onclick="$('#workSpace').trigger('outdentCurrentTask.gantt');return false;" class="button textual icon requireCanWrite requireCanInOutdent" title="un-indent task"><span class="teamworkIcon">.</span></button>
      <button onclick="$('#workSpace').trigger('indentCurrentTask.gantt');return false;" class="button textual icon requireCanWrite requireCanInOutdent" title="indent task"><span class="teamworkIcon">:</span></button>
      <span class="ganttButtonSeparator requireCanWrite requireCanMoveUpDown"></span>
      <button onclick="$('#workSpace').trigger('moveUpCurrentTask.gantt');return false;" class="button textual icon requireCanWrite requireCanMoveUpDown" title="move up"><span class="teamworkIcon">k</span></button>
      <button onclick="$('#workSpace').trigger('moveDownCurrentTask.gantt');return false;" class="button textual icon requireCanWrite requireCanMoveUpDown" title="move down"><span class="teamworkIcon">j</span></button>
      <span class="ganttButtonSeparator requireCanDelete"></span>
      <button onclick="$('#workSpace').trigger('deleteFocused.gantt');return false;" class="button textual icon delete requireCanWrite" title="Delete"><span class="teamworkIcon">&cent;</span></button>
      <span class="ganttButtonSeparator"></span>
      <button onclick="$('#workSpace').trigger('expandAll.gantt');return false;" class="button textual icon " title="EXPAND_ALL"><span class="teamworkIcon">6</span></button>
      <button onclick="$('#workSpace').trigger('collapseAll.gantt'); return false;" class="button textual icon " title="COLLAPSE_ALL"><span class="teamworkIcon">5</span></button>

    <span class="ganttButtonSeparator"></span>
      <button onclick="$('#workSpace').trigger('zoomMinus.gantt'); return false;" class="button textual icon " title="zoom out"><span class="teamworkIcon">)</span></button>
      <button onclick="$('#workSpace').trigger('zoomPlus.gantt');return false;" class="button textual icon " title="zoom in"><span class="teamworkIcon">(</span></button>
    <span class="ganttButtonSeparator"></span>
      <button onclick="print();return false;" class="button textual icon " title="Print"><span class="teamworkIcon">p</span></button>
    <span class="ganttButtonSeparator"></span>
      <button onclick="ge.splitter.resize(.1);return false;" class="button textual icon" ><span class="teamworkIcon">F</span></button>
      <button onclick="ge.splitter.resize(50);return false;" class="button textual icon" ><span class="teamworkIcon">O</span></button>
      <button onclick="ge.splitter.resize(100);return false;" class="button textual icon"><span class="teamworkIcon">R</span></button>
      <span class="ganttButtonSeparator"></span>

    <button onclick="editRoles();" class="button textual requireWrite" title="edit roles"><span class="teamworkIcon">M</span></button>
      &nbsp; &nbsp; &nbsp; &nbsp;
    <button onclick="saveGanttOnServer();" class="button first big requireWrite" id="saveGanttButton" title="Save">Save</button>
    </div></div>
 --></div>

<div class="__template__" type="TASKSEDITHEAD"><!-- 
  <table class="gdfTable" cellspacing="0" cellpadding="0">
    <thead>
    <tr style="height:40px">
      <th class="gdfColHeader" style="width:35px; border-right: none"></th>
      <th class="gdfColHeader" style="width:25px;"></th>
      <th class="gdfColHeader gdfResizable" style="width:300px;">name</th>
      <th class="gdfColHeader gdfResizable" style="width:80px;">Start</th>
      <th class="gdfColHeader gdfResizable" style="width:80px;">End</th>
      <th class="gdfColHeader gdfResizable" style="width:50px;">dur.</th>
      <th class="gdfColHeader gdfResizable" style="width:20px;">%</th>
      <th class="gdfColHeader gdfResizable" style="width:1000px; text-align: left; padding-left: 10px;">assignees</th>
    </tr>
    </thead>
  </table>
--></div> 

<div class="__template__" type="TASKROW"><!--
  <tr taskid="(#=obj.cardNum#)" class="taskEditRow (#=obj.isParent()?'isParent':''#) (#=obj.collapsed?'collapsed':''#)" level="(#=level#)">
    <th class="gdfCell edit" align="right" style="cursor:pointer;"><span class="taskRowIndex">(#=obj.getRow()+1#)</span> <span class="teamworkIcon" style="font-size:12px;" >e</span></th>
    <td class="gdfCell noClip" align="center"><div class="taskStatus cvcColorSquare" status="(#=obj.status#)"></div></td>
    <td class="gdfCell indentCell" style="padding-left:(#=obj.level*10+18#)px;">
      <div class="exp-controller" align="center"></div>
      <input type="text" name="name" value="(#=obj.subject#)" placeholder="name">
    </td>
    <td class="gdfCell"><input type="text" name="start"  value="" class="date"></td>
    <td class="gdfCell"><input type="text" name="end" value="" class="date"></td>
    <td class="gdfCell"><input type="text" name="duration" autocomplete="off" value="(#=obj.duration#)"></td>
    <td class="gdfCell"><input type="text" name="progress" class="validated" entrytype="PERCENTILE" autocomplete="off" value="(#=obj.progress?obj.progress:''#)" (#=obj.progressByWorklog?"readOnly":""#)></td>
    <td class="gdfCell taskAssigs">(#=obj.getAssigsString()#)</td>
  </tr>
  --></div>

<div class="__template__" type="TASKEMPTYROW"><!--
  <tr class="taskEditRow emptyRow" >
    <th class="gdfCell" align="right"></th>
    <td class="gdfCell noClip" align="center"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell requireCanSeeDep"></td>
    <td class="gdfCell"></td>
  </tr>
  --></div>

<div class="__template__" type="TASKBAR"><!--
  <div class="taskBox taskBoxDiv" taskid="(#=obj.cardNum#)" >
    <div class="layout (#=obj.hasExternalDep?'extDep':''#)">
      <div class="taskStatus" status="(#=obj.status#)"></div>
      <div class="taskProgress" style="width:(#=obj.progress>100?100:obj.progress#)%; background-color:(#=obj.progress>100?'red':'rgb(153,255,51);'#);"></div>

      <div class="taskLabel"></div>
    </div>
  </div>
  --></div>


<div class="__template__" type="CHANGE_STATUS"><!--
    <div class="taskStatusBox">
      <div class="taskStatus cvcColorSquare" status="STATUS_ACTIVE" title="active"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_DONE" title="completed"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_FAILED" title="failed"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_SUSPENDED" title="suspended"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_UNDEFINED" title="undefined"></div>
    </div>
  --></div>





<div class="__template__" type="TASK_EDITOR"><!--
  <div class="ganttTaskEditor">
    <h2 class="taskData">Task editor</h2>
    <table  cellspacing="1" cellpadding="5" width="100%" class="taskData table" border="0">
          <tr>
        <td colspan="3" valign="top"><label for="name" class="required">name</label><br><input type="text" name="name" id="name"class="formElements" autocomplete='off' maxlength=255 style='width:100%' value="" required="true" oldvalue="1"></td>
          </tr>


      <tr class="dateRow">
        <td nowrap="">
          <div style="position:relative">
            <label for="start">start</label>&nbsp;&nbsp;&nbsp;&nbsp;
            <br><input type="text" name="start" id="start" size="8" class="formElements dateField validated date" autocomplete="off" maxlength="255" value="" oldvalue="1" entrytype="DATE">
            <span title="calendar" id="starts_inputDate" class="teamworkIcon openCalendar" onclick="$(this).dateField({inputField:$(this).prevAll(':input:first'),isSearchField:false});">m</span>          </div>
        </td>
        <td nowrap="">
          <label for="end">End</label>&nbsp;&nbsp;&nbsp;&nbsp;
          <br><input type="text" name="end" id="end" size="8" class="formElements dateField validated date" autocomplete="off" maxlength="255" value="" oldvalue="1" entrytype="DATE">
          <span title="calendar" id="ends_inputDate" class="teamworkIcon openCalendar" onclick="$(this).dateField({inputField:$(this).prevAll(':input:first'),isSearchField:false});">m</span>
        </td>
        <td nowrap="" >
          <label for="duration" class=" ">Days</label><br>
          <input type="text" name="duration" id="duration" size="4" class="formElements validated durationdays" title="Duration is in working days." autocomplete="off" maxlength="255" value="" oldvalue="1" entrytype="DURATIONDAYS">&nbsp;
        </td>
      </tr>

      <tr>
        <td  colspan="2">
          <label for="status" class=" ">status</label><br>
          <select id="status" name="status" class="taskStatus" status="(#=obj.status#)"  onchange="$(this).attr('STATUS',$(this).val());">
            <option value="STATUS_ACTIVE" class="taskStatus" status="STATUS_ACTIVE" >active</option>
            <option value="STATUS_SUSPENDED" class="taskStatus" status="STATUS_SUSPENDED" >suspended</option>
            <option value="STATUS_DONE" class="taskStatus" status="STATUS_DONE" >completed</option>
            <option value="STATUS_FAILED" class="taskStatus" status="STATUS_FAILED" >failed</option>
            <option value="STATUS_UNDEFINED" class="taskStatus" status="STATUS_UNDEFINED" >undefined</option>
          </select>
        </td>

        <td valign="top" nowrap>
          <label>progress</label><br>
          <input type="text" name="progress" id="progress" size="7" class="formElements validated percentile" autocomplete="off" maxlength="255" value="" oldvalue="1" entrytype="PERCENTILE">
        </td>
      </tr>

          </tr>
          <tr>
            <td colspan="4">
              <label for="description">Description</label><br>
              <textarea rows="3" cols="30" id="description" name="description" class="formElements" style="width:100%"></textarea>
            </td>
          </tr>
        </table>

    <h2>Assignments</h2>
  <table  cellspacing="1" cellpadding="0" width="100%" id="assigsTable">
    <tr>
      <th style="width:100px;">name</th>
      <th style="width:70px;">Role</th>
      <th style="width:30px;" id="addAssig"><span class="teamworkIcon" style="cursor: pointer">+</span></th>
    </tr>
  </table>

  <div style="text-align: right; padding-top: 20px">
    <span id="saveButton" class="button first" onClick="$(this).trigger('saveFullEditor.gantt');">Save</span>
  </div>

  </div>
  --></div>



<div class="__template__" type="ASSIGNMENT_ROW"><!--
  <tr taskid="(#=obj.task.cardNum#)" assId="(#=obj.assig.assigneeNum#)" class="assigEditRow" >
    <td ><select name="resourceId"  class="formElements" (#=obj.assig.assigneeNum<0?"":"disabled"#) ></select></td>
    <td ><select type="select" name="roleId"  class="formElements"></select></td>
    <td align="center"><span class="teamworkIcon delAssig del" style="cursor: pointer">d</span></td>
  </tr>
  --></div>



<div class="__template__" type="ROLE_EDITOR"><!--
  <div class="resourceEditor" style="padding: 5px;">

    <h2>Board Roles</h2>
    <table  cellspacing="1" cellpadding="0" width="100%" id="rolesTable">
      <tr>
        <th style="width:100px;">Role</th>
        <th style="width:30px;" id="addRole"><span class="teamworkIcon" style="cursor: pointer">+</span></th>
      </tr>
    </table>

    <div style="text-align: right; padding-top: 20px"><button id="roleSaveButton" class="button big">Save</button></div>
  </div>
  --></div>



<div class="__template__" type="ROLE_ROW"><!--
  <tr roleId="(#=obj.roleNum#)" class="roleRow" >
    <td ><input type="text" name="name" value="(#=obj.roleName#)" style="width:100%;" class="formElements"></td>
    <td align="center"><span class="teamworkIcon delRes del" style="cursor: pointer">d</span></td>
  </tr>
  --></div>


</div>
<script type="text/javascript">
  $.JST.loadDecorator("ROLE_ROW", function(roleTr, role){
    roleTr.find(".delRes").click(
    		function(){$(this).closest("tr").fadeOut(200, function(){$(this).remove()})}
    		)
  });

  $.JST.loadDecorator("ASSIGNMENT_ROW", function(assigTr, taskAssig){
    var resEl = assigTr.find("[name=resourceId]");
    var opt = $("<option>");
    resEl.append(opt);
    for(var i=0; i< taskAssig.task.master.resources.length;i++){
      var res = taskAssig.task.master.resources[i];
      opt = $("<option>");
      opt.val(res.num).html(res.userId);
      if(taskAssig.assig.projectMemberNum == res.num)
        opt.attr("selected", "true");
      resEl.append(opt);
    }
    var roleEl = assigTr.find("[name=roleId]");
    for(var i=0; i< taskAssig.task.master.roles.length;i++){
      var role = taskAssig.task.master.roles[i];
      var optr = $("<option>");
      optr.val(role.roleNum).html(role.roleName);
      if(taskAssig.assig.roleNum == role.roleNum)
        optr.attr("selected", "true");
      roleEl.append(optr);
    }

    if(taskAssig.task.master.permissions.canWrite && taskAssig.task.canWrite){
      assigTr.find(".delAssig").click(function(){
    	var delAssignNum=parseInt($(this).parent().parent().attr('assid'));
        if(delAssignNum>0)
        	ge.deletedAssigIds.push(delAssignNum);
        
        var tr = $(this).closest("[assId]").fadeOut(200, function(){$(this).remove()});
      });
    }

  });


  function loadI18n(){
    GanttMaster.messages = {
      "CANNOT_WRITE":"No permission to change the following task:",
      "CHANGE_OUT_OF_SCOPE":"Project update not possible as you lack rights for updating a parent project.",
      "START_IS_MILESTONE":"Start date is a milestone.",
      "END_IS_MILESTONE":"End date is a milestone.",
      "TASK_HAS_CONSTRAINTS":"Task has constraints.",
      "GANTT_ERROR_DEPENDS_ON_OPEN_TASK":"Error: there is a dependency on an open task.",
      "GANTT_ERROR_DESCENDANT_OF_CLOSED_TASK":"Error: due to a descendant of a closed task.",
      "TASK_HAS_EXTERNAL_DEPS":"This task has external dependencies.",
      "GANNT_ERROR_LOADING_DATA_TASK_REMOVED":"GANNT_ERROR_LOADING_DATA_TASK_REMOVED",
      "CIRCULAR_REFERENCE":"Circular reference.",
      "CANNOT_DEPENDS_ON_ANCESTORS":"Cannot depend on ancestors.",
      "INVALID_DATE_FORMAT":"The data inserted are invalid for the field format.",
      "GANTT_ERROR_LOADING_DATA_TASK_REMOVED":"An error has occurred while loading the data. A task has been trashed.",
      "CANNOT_CLOSE_TASK_IF_OPEN_ISSUE":"Cannot close a task with open issues",
      "TASK_MOVE_INCONSISTENT_LEVEL":"You cannot exchange tasks of different depth.",
      "GANTT_QUARTER_SHORT":"Quarter",
      "GANTT_SEMESTER_SHORT":"반기",
      "CANNOT_MOVE_TASK":"CANNOT_MOVE_TASK",
      "PLEASE_SAVE_PROJECT":"PLEASE_SAVE_PROJECT"
    };
  }



  function createNewResource(el) {
    var row = el.closest("tr[taskid]");
    var name = row.find("[name=resourceId_txt]").val();
    var url = contextPath + "/applications/teamwork/resource/resourceNew.jsp?CM=ADD&name=" + encodeURI(name);

    openBlackPopup(url, 700, 320, function (response) {
      //fillare lo smart combo
      if (response && response.resId && response.resName) {
        //fillare lo smart combo e chiudere l'editor
        row.find("[name=resourceId]").val(response.resId);
        row.find("[name=resourceId_txt]").val(response.resName).focus().blur();
      }

    });
  }
</script>

</body>
</html>