<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-bordered table-striped admin-table" style="margin-top: 20px;"><!-- inbox-table admin-table -->
	<thead>
		<tr>
			<td>Instance ID</td>
			<td>Priority</td>
			<td>Create Date</td>
			<td>Due Date</td>
			<td>Owner</td>
			<td>Task ID</td>
			<td align="center">Action</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${!empty instanceList}">
				<c:forEach items="${instanceList}" var="inst">
					<tr class="view_app adm_view_app" data-inst-id="${inst.instanceId }" data-task-id="${inst.taskId }">
						<td>${inst.instanceId }<input type="hidden" class="adm-inst-id" value="${inst.instanceId }"></td>
						<td>${inst.taskPriority }</td>
						<td>${inst.instanceCreateDate }</td>
						<td>${inst.instanceDueDate }<input type="hidden" class="adm-task-q-type" value="${inst.taskAssignedToType }"></td>
						<td>${inst.taskAssignedToType == "Group"? "Central Queue": inst.taskAssignedToWho}</td>
						<td>${inst.taskId }<input type="hidden" class="adm-task-id" value="${inst.taskId }"></td>
						<td>
							<button type="button" class="delete-instance" data-value="${inst.instanceId }">Delete</button>			
							<button type="button" class="move-instance" data-act="ia" data-value="${inst.instanceId }">IA</button>					
							<button type="button" class="move-instance" data-act="de1.1" data-value="${inst.instanceId }">DE1.1</button>						
							<button type="button" class="move-instance" data-act="de1.2" data-value="${inst.instanceId }">DE1.2</button>						
							<button type="button" class="move-instance" data-act="de2a" data-value="${inst.instanceId }">DE2A</button>
							<button type="button" class="move-instance" data-act="de2r" data-value="${inst.instanceId }">DE2R</button>
							<button type="button" class="move-instance" data-act="dv1" data-value="${inst.instanceId }">DV</button>
							<button type="button" class="move-instance" data-act="caa" data-value="${inst.instanceId }">CA</button>
							<button type="button" class="move-instance" data-act="fraud" data-value="${inst.instanceId }">Fraud</button>
						</td>
					</tr>
				</c:forEach>
				<script>
// 					$(function(){
// 						$('#admin-tool-link').off('click').click(function(){
// 							searchInbox();
// 						});

// 					});
				</script>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="7" style="text-align:center;">No Record</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>

<style>
table.admin-table td{
	white-space: nowrap;
	padding: 3px 5px;
}

</style>
