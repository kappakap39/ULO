<%@ page import="com.eaf.orig.ulo.pl.scheduler.task.PLSchedulerManager" %>
<%@ page import="com.eaf.orig.shared.utility.OrigSchedulerUtil" %>
<link rel="StyleSheet" href="../css/MainStylesheet.css" type="text/css">

<script type="text/javascript" src="../js/template/jquery-1.7.1.js"></script>
<script type="text/javascript" src="../js/template/jquery-ui-1.8.16.js"></script>
<%
//process delete scheduler
String processType = request.getParameter("processType");
if(processType!=null && !processType.trim().equals("")){
	PLSchedulerManager schedulerObj = OrigSchedulerUtil.getInstance().getSchedulerManager();
	if(processType.trim().equalsIgnoreCase("delete")){
		String[] taskNames = request.getParameterValues("taskNames");
		if(taskNames!=null && taskNames.length>0){
			for(int i=0;i<taskNames.length;i++){
				try{
					schedulerObj.cancelTaskScheduler(taskNames[i]);
					System.out.println("Delete taskName["+i+"] : "+taskNames[i]+" Successed");
				}catch(Exception e){
					System.out.println("Cannot delete scheduler taskName["+i+"] : "+taskNames[i]);
					e.printStackTrace();
				}
			}
		}
	}else if(processType.trim().equalsIgnoreCase("create")){
		String taskName = request.getParameter("schedulerName");
		String startIntervalStr = request.getParameter("startInterval");
		String repeat = request.getParameter("repeat");
		String repeatInterval = request.getParameter("repeatInterval");
		String taskType = request.getParameter("schedulerType");
		String runDay = request.getParameter("runDay");
		String startHour = request.getParameter("startHours");
		String startMinute = request.getParameter("startMinute");
		if(!"CRON".equals(taskType)){
			int startInterval = Integer.parseInt(startIntervalStr);
			
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.add(java.util.Calendar.MINUTE, startInterval);
			
			schedulerObj.createScheduleTask(calendar.getTime(), Integer.parseInt(repeat), repeatInterval.trim()+"minutes",taskName);
		}else{
			schedulerObj.createScheduleTaskCRON(runDay,startHour,startMinute,taskName);
		}
	}
}
%>

<script>

function onSchedulerOver(obj){
	obj.style.background = '#929292';
	obj.style.cursor='hand';
}

function onSchedulerOut(obj){
	obj.style.background = '#e4e4e4';
}

function deleteScheduler(){
	if(confirm('Delete !')){
		document.schedulerForm.action = "scheduler_process.jsp";
		document.schedulerForm.submit();
	}
}
function createScheduler(){
	document.schedulerForm.action = "scheduler_create.jsp";
	document.schedulerForm.submit();
}

$(document).ready(function (){
	$("Tr.ResultOdd").hover(
    	function(){
	 		$(this).addClass("ResultOdd-haver");
    	},
    	function(){
			$(this).removeClass("ResultOdd-haver");
		}
    );
	$("Tr.ResultEven").hover(
	    function(){
	    	$(this).addClass("ResultEven-haver");	
	    },
	    function(){
	    	$(this).removeClass("ResultEven-haver");
	    }
	);
});
</script>

<form name="schedulerForm" action="main.jsp">
<input type="hidden" name="processType" value="delete">
<input type="hidden" name="pageProcess" value="1">
<!--<table width="100%" border="1" cellpadding="0" cellspacing="0" bgcolor="#e4e4e4" bordercolor="#000000" frame="below" rules="rows">-->
<div class="TextHeaderNormal">Scheduler Task</div>
<table class="TableFrame">
  <tr class="Header">
    <td height="20" align="center">&nbsp;</td>
    <td height="20" align="center">&nbsp;Task Id</td>
    <td height="20" align="center">&nbsp;Name</td>
    <td height="20" align="center">&nbsp;Repeat Interval</td>
    <td height="20" align="center">&nbsp;Next Time</td>
    <td height="20" align="center">&nbsp;Total Repeat</td>
    <td height="20" align="center">&nbsp;Left Repeat</td>
    <td height="20" align="center">&nbsp;Create Time</td>
  </tr>
<%
try{
	PLSchedulerManager schedulerObj = OrigSchedulerUtil.getInstance().getSchedulerManager();
	java.util.Vector schedulerVt = schedulerObj.getAllTaskSchedulerInfo();	
	if(schedulerVt!=null && schedulerVt.size()>0){
		for(int i=0;i<schedulerVt.size();i++){
			com.eaf.orig.ulo.pl.scheduler.model.SchedulerM schedulerM = (com.eaf.orig.ulo.pl.scheduler.model.SchedulerM)schedulerVt.elementAt(i);
			StringBuilder style = new StringBuilder();
		 	style.append((i%2==0)?"ResultEven":"ResultOdd");
%>
  <tr class="<%=style.toString()%>"> 
    <td align="center">&nbsp;<input type='checkbox' name='taskNames' value='<%=schedulerM.getName()%>'>&nbsp;</td>
    <td align="left">&nbsp;<%=schedulerM.getId()%>&nbsp;</td>
    <td align="left">&nbsp;<%=schedulerM.getName()%>&nbsp;</td>
    <td align="center">&nbsp;<%=schedulerM.getRepeatInterval()%>&nbsp;</td>
    <td align="center">&nbsp;<%=schedulerM.getNextFireTime()%>&nbsp;</td>
    <td align="center">&nbsp;<%=schedulerM.getMaxRepeats()%>&nbsp;</td>
    <td align="center">&nbsp;<%=schedulerM.getLeftRepeats()%>&nbsp;</td>
    <td align="center">&nbsp;<%=schedulerM.getCreateTime()%>&nbsp;</td>
  </tr>
<%
		}
	}else{
%>
  <tr class="ResultNotFound"> 
    <td colspan="8" align="center">&nbsp;No Scheduler.&nbsp;</td>
  </tr>
<%
	}
}catch(Exception e){
	e.printStackTrace();
}
%>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr bgcolor="#ffffff"> 
    <td align="right">
		<input type="button" class="button" name="create" value="Create" onclick="createScheduler();">&nbsp;&nbsp;
		<input type="button" class="button" name="delete" value="Delete" onclick="deleteScheduler();">
  </tr>
</table>
</form>
