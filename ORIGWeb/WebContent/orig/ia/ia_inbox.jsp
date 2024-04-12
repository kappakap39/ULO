<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$('.view_app').click(function(){
		$('#app-page').show();
		var attrId = $(this).attr("id").split("_");		
		$('#taskId').val(attrId[0]);
		$('#instantId').val(attrId[1]);
		$('#username').prop("readonly",true);
		$('#password').prop("readonly",true);
	});
	
</script>
<style type="text/css">
	div#container{
		text-align: center;
	}
</style>

<div id="container">
	<table class="inbox-table">
		<thead>
			<tr>
				<td>Instance ID</td>
				<td>Priority</td>
				<td>Task ID</td>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${!empty BPMInbox.inboxInstanceList}">
					<c:forEach items="${BPMInbox.inboxInstanceList}" var="inst">
						<tr class="view_app" id="${inst.taskId}_${inst.instanceId}" >
							<td>${inst.instanceId }</td>
							<td>${inst.taskPriority }</td>
							<td>${inst.taskId }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="3">No Record</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<div id="app-page" style="display:none; text-align: left;">
		<jsp:include page="/orig/ia/ia_form.jsp"></jsp:include>
	</div>
</div>
