<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<script>
	var row;
	$(document).ready(function(){
		if($("#retentionType_hidden").val()!="" && $("#retentionTypeDescription_hidden").val()!=""){
			$("#addRetention").hide();
			$("#editRetention").hide();
			$.ajax({
				url : "/ServiceCenterWeb/CBS1215I01?process=viewDetail&retentionType="+$('#retentionType_hidden').val()+"&retentionTypeDescription="+$('#retentionTypeDescription_hidden').val(),
				type : "GET",
				success : function(data){
					console.log(data);
					var obj = $.parseJSON(data);
					$("#cancelAuthId").val(obj.cancelAuthUserId);
					$("#cancelBranchDesc").val(obj.cancelBranchDesc);
					$("#cancelBranchId").val(obj.cancelBranchId);
					$("#cancelReteReason").val(obj.cancelRententionReason);
					$("#cancelUserId").val(obj.cancelUserId);
					$("#concept").val(obj.concept);
					$("#createAuthId").val(obj.createAuthUserId);
					$("#curCode").val(obj.currencyCode);
					$("#entryOrigin").val(obj.entryOrigin);
					$("#indexSeq").val(obj.indexSequence);
					$("#reteAmt").val(obj.retentionAmount);
					$("#reteCancelDate").val(obj.retentionCancelDate);
					$("#reteCancelTime").val(obj.retentionCancelTime);
					$("#reteCreateTime").val(obj.retentionCreateTime);
					$("#reteCreateDate").val(obj.retentionCreateDate);
					$("#reteInd").val(obj.retentionIndicator);
					$("#reteIndDesc").val(obj.retentionIndicatorDesc);
					$("#reteMaturityDate").val(obj.retentionMaturityDate);
					$("#reteNum").val(obj.retentionNumber);
					$("#reteSitnCode").val(obj.retentionSituationCode);
					$("#reteType").val(obj.retentionType);
					$("#reteTypeDesc").val(obj.retentionTypeDesc);
					$("#subAccNum").val(obj.subAccountNumber);
					$("#tranDate").val(obj.transactionDate);
					$("#tranBranchId").val(obj.transactionBranchId);
					$("#tranBranchName").val(obj.transactionBranchName);
					$("#userId").val(obj.userId);
					$("#retentionType_hidden").val("");
					$("#retentionTypeDescription_hidden").val("");
				}
			});
		}else if($("#rowEdit").val()!=""){
			row = $("#rowEdit").val();
			$("#editRetention").show();
			$("#addRetention").hide();
			$("#cancelAuthId").attr("readonly",false);
			$("#cancelBranchDesc").attr("readonly",false);
			$("#cancelBranchId").attr("readonly",false);
			$("#cancelReteReason").attr("readonly",false);
			$("#cancelUserId").attr("readonly",false);
			$("#concept").attr("readonly",false);
			$("#createAuthId").attr("readonly",false);
			$("#curCode").attr("readonly",false);
			$("#entryOrigin").attr("readonly",false);
			$("#indexSeq").attr("readonly",false);
			$("#reteAmt").attr("readonly",false);
			$("#reteCancelDate").attr("readonly",false);
			$("#reteCancelTime").attr("readonly",false);
			$("#reteCreateTime").attr("readonly",false);
			$("#reteCreateDate").attr("readonly",false);
			$("#reteInd").attr("readonly",false);
			$("#reteIndDesc").attr("readonly",false);
			$("#reteMaturityDate").attr("readonly",false);
			$("#reteNum").attr("readonly",false);
			$("#reteSitnCode").attr("readonly",false);
			$("#reteType").attr("readonly",false);
			$("#reteTypeDesc").attr("readonly",false);
			$("#subAccNum").attr("readonly",false);
			$("#tranDate").attr("readonly",false);
			$("#tranBranchId").attr("readonly",false);
			$("#tranBranchName").attr("readonly",false);
			$("#userId").attr("readonly",false);
			
			$.ajax({
				url : "/ServiceCenterWeb/CBS1215I01?process=viewDetail&row="+row,
				type : "GET",
				success : function(data){
					console.log(data);
					var obj = $.parseJSON(data);
					$("#cancelAuthId").val(obj.cancelAuthUserId);
					$("#cancelBranchDesc").val(obj.cancelBranchDesc);
					$("#cancelBranchId").val(obj.cancelBranchId);
					$("#cancelReteReason").val(obj.cancelRententionReason);
					$("#cancelUserId").val(obj.cancelUserId);
					$("#concept").val(obj.concept);
					$("#createAuthId").val(obj.createAuthUserId);
					$("#curCode").val(obj.currencyCode);
					$("#entryOrigin").val(obj.entryOrigin);
					$("#indexSeq").val(obj.indexSequence);
					$("#reteAmt").val(obj.retentionAmount);
					$("#reteCancelDate").val(obj.retentionCancelDate);
					$("#reteCancelTime").val(obj.retentionCancelTime);
					$("#reteCreateTime").val(obj.retentionCreateTime);
					$("#reteCreateDate").val(obj.retentionCreateDate);
					$("#reteInd").val(obj.retentionIndicator);
					$("#reteIndDesc").val(obj.retentionIndicatorDesc);
					$("#reteMaturityDate").val(obj.retentionMaturityDate);
					$("#reteNum").val(obj.retentionNumber);
					$("#reteSitnCode").val(obj.retentionSituationCode);
					$("#reteType").val(obj.retentionType);
					$("#reteTypeDesc").val(obj.retentionTypeDesc);
					$("#subAccNum").val(obj.subAccountNumber);
					$("#tranDate").val(obj.transactionDate);
					$("#tranBranchId").val(obj.transactionBranchId);
					$("#tranBranchName").val(obj.transactionBranchName);
					$("#userId").val(obj.userId);
					$("#rowEdit").val("");
				}
			});
		}else{
			$("#addRetention").show();
			$("#editRetention").hide();
			$("#cancelAuthId").attr("readonly",false);
			$("#cancelBranchDesc").attr("readonly",false);
			$("#cancelBranchId").attr("readonly",false);
			$("#cancelReteReason").attr("readonly",false);
			$("#cancelUserId").attr("readonly",false);
			$("#concept").attr("readonly",false);
			$("#createAuthId").attr("readonly",false);
			$("#curCode").attr("readonly",false);
			$("#entryOrigin").attr("readonly",false);
			$("#indexSeq").attr("readonly",false);
			$("#reteAmt").attr("readonly",false);
			$("#reteCancelDate").attr("readonly",false);
			$("#reteCancelTime").attr("readonly",false);
			$("#reteCreateTime").attr("readonly",false);
			$("#reteCreateDate").attr("readonly",false);
			$("#reteInd").attr("readonly",false);
			$("#reteIndDesc").attr("readonly",false);
			$("#reteMaturityDate").attr("readonly",false);
			$("#reteNum").attr("readonly",false);
			$("#reteSitnCode").attr("readonly",false);
			$("#reteType").attr("readonly",false);
			$("#reteTypeDesc").attr("readonly",false);
			$("#subAccNum").attr("readonly",false);
			$("#tranDate").attr("readonly",false);
			$("#tranBranchId").attr("readonly",false);
			$("#tranBranchName").attr("readonly",false);
			$("#userId").attr("readonly",false);
		}
	});
	

</script>
<body>
	<form id="retentionDetailForm">
		<table>
			<tr>
				<td>Retention Type</td>
				<td><input type="text" name="reteType" id="reteType" class="form-control" size="30" readonly></td>
				<td>Retention Type Description</td>
				<td><input type="text" name="reteTypeDesc" id="reteTypeDesc" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Cancel Authurize Id</td>
				<td><input type="text" name="cancelAuthId" id="cancelAuthId" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Cancel Branch Description</td>
				<td><input type="text" name="cancelBranchDesc" id="cancelBranchDesc" class="form-control" size="30" readonly></td>
				<td>Cancel Branch Id</td>
				<td><input type="text" name="cancelBranchId" id="cancelBranchId" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Cancel Retention Reason</td>
				<td><input type="text" name="cancelReteReason" id="cancelReteReason" class="form-control" size="30" readonly></td>
				<td>Cancel User Id</td>
				<td><input type="text" name="cancelUserId" id="cancelUserId" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Concept</td>
				<td><input type="text" name="concept" id="concept" class="form-control" size="30" readonly></td>
				<td>Create Autheruze User Id</td>
				<td><input type="text" name="createAuthId" id="createAuthId" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Currency Code</td>
				<td><input type="text" name="curCode" id="curCode" class="form-control" size="30" readonly></td>
				<td>Entry Origin</td>
				<td><input type="text" name="entryOrigin" id="entryOrigin" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Index Sequence</td>
				<td><input type="text" name="indexSeq" id="indexSeq" class="form-control" size="30" readonly></td>
				<td>Retention Amount(BigDecimal)</td>
				<td><input type="text" name="reteAmt" id="reteAmt" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Retention Cancel Date(yyyy-MM-dd)</td>
				<td><input type="text" name="reteCancelDate" id="reteCancelDate" class="form-control" size="30" readonly></td>
				<td>Retention Cancel Time(yyyy-MM-dd HH:mm:ss)</td>
				<td><input type="text" name="reteCancelTime" id="reteCancelTime" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Retention Create Time(yyyy-MM-dd HH:mm:ss)</td>
				<td><input type="text" name="reteCreateTime" id="reteCreateTime" class="form-control" size="30" readonly></td>
				<td>Retention Create Date(yyyy-MM-dd)</td>
				<td><input type="text" name="reteCreateDate" id="reteCreateDate" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Retention Indicator</td>
				<td><input type="text" name="reteInd" id="reteInd" class="form-control" size="30" readonly></td>
				<td>Retention Indicator Description</td>
				<td><input type="text" name="reteIndDesc" id="reteIndDesc" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Retention Maturity Date(yyyy-MM-dd)</td>
				<td><input type="text" name="reteMaturityDate" id="reteMaturityDate" class="form-control" size="30" readonly></td>
				<td>Retention Number</td>
				<td><input type="text" name="reteNum" id="reteNum" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Retention Situation Code</td>
				<td><input type="text" name="reteSitnCode" id="reteSitnCode" class="form-control" size="30" readonly></td>
			</tr>			
			<tr>
				<td>Sub Account Number</td>
				<td><input type="text" name="subAccNum" id="subAccNum" class="form-control" size="30" readonly></td>
				<td>Transaction Date(yyyy-MM-dd)</td>
				<td><input type="text" name="tranDate" id="tranDate" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>Transaction Branch Id</td>
				<td><input type="text" name="tranBranchId" id="tranBranchId" class="form-control" size="30" readonly></td>
				<td>Transaction Branch Name</td>
				<td><input type="text" name="tranBranchName" id="tranBranchName" class="form-control" size="30" readonly></td>
			</tr>
			<tr>
				<td>User Id</td>
				<td><input type="text" name="userId" id="userId" class="form-control" size="30" readonly></td>
			</tr>
		</table>
	</form>
<!-- 	<button type="button" class="btn btn-info btn-md" onclick="addRetention()" id="addRetention">Add</button> -->
<!-- 	<button type="button" class="btn btn-info btn-md" onclick="editRetention()" id="editRetention">Edit</button> -->
</body>
</html>
