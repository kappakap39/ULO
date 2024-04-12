<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<script type="text/javascript">	
$(function(){
	$('#SendNextStation').click(function(e){
		if(validateForm()){
			showProgress();
			$this = $(this);
			$this.prop("disabled", true);
			var form = $(document.ia_form);
			$.ajax({
				url: "/ORIGWeb/bpm/app",
				type: "POST",
				data: form.serialize()+"&action=submit&username="+$('#username').val()+'&password='+$('#password').val(),
				success: function(data){
					var func = function(){$('#username').prop("readonly",false); $('#password').prop("readonly",false);};
					displayInbox($('#ia-inbox-content'),func);
					$this.prop("disabled", false);
				},
				error: function(data){
					$('#errorForm').html("ERROR\n"+JSON.stringify(data, null, '\t'));
					$this.prop("disabled", false);
					hideProgress();
				}			
			});	
		}	
	});
});
function validateForm(){
	$('.errorForm').html('');
	var CID_TYPE = $("[name='CID_TYPE']").val();
	var TH_TITLE_CODE = $("[name='TH_TITLE_CODE']").val();
	var TH_FIRST_NAME = $("[name='TH_FIRST_NAME']").val();
	var TH_LAST_NAME = $("[name='TH_LAST_NAME']").val();
	var TH_BIRTH_DATE = $("[name='TH_BIRTH_DATE']").val();
	var CHANNEL = $("[name='CHANNEL']").val();
	var CID_TYPE = $("[name='CID_TYPE']").val();
	var TEMPLATE = $("[name='TEMPLATE']").val();
	var APPLY_DATE = $("[name='APPLY_DATE']").val();
	var BRANCH_NO = $("[name='BRANCH_NO']").val();
	var IDNO = $("[name='IDNO']").val();
	var APPLICATION_TYPE = $("[name='APPLICATION_TYPE']").val();
	if(isEmpty(CID_TYPE)||isEmpty(TH_TITLE_CODE)||isEmpty(TH_FIRST_NAME)||isEmpty(TH_LAST_NAME)
		||isEmpty(TH_BIRTH_DATE)||isEmpty(CHANNEL)||isEmpty(TEMPLATE)||isEmpty(APPLICATION_TYPE)||isEmpty(APPLY_DATE)||isEmpty(BRANCH_NO)||isEmpty(IDNO)){
		$('#errorForm').html('Please Input Require Field.');
		return false;
	}
	return true;
}
</script>
<style type="text/css">
	input, select{
		box-sizing: border-box;
		width: 200px;
	}
</style>
<%
	String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE");
	String FIELD_ID_GENDER = SystemConstant.getConstant("FIELD_ID_GENDER");
	String FIELD_ID_NATIONALITY = SystemConstant.getConstant("FIELD_ID_NATIONALITY");
	String FIELD_ID_MARRIED = SystemConstant.getConstant("FIELD_ID_MARRIED");
	String FIELD_ID_DEGREE = SystemConstant.getConstant("FIELD_ID_DEGREE");
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
%>
<span class='require' id='errorForm'></span>
<form name="ia_form" id="ia_form">
	<table>
		<tbody>
			<tr>
				<td><label>Task ID <span class='require'>*</span> : </label></td>
				<td><input type="text" name="taskId" id="taskId" readonly class='textbox'/></td>
			</tr>
			<tr>
				<td><label>Card Type <span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.dropdown("CID_TYPE","",FIELD_ID_CID_TYPE,"ALL_ALL_ALL",HtmlUtil.EDIT,"",request)%></td>
			</tr>
			<tr>
				<td><label>Id No <span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.textBox("IDNO","","","","","15","","",request)%></td>
			</tr>
			<tr>
				<td><label>Title <span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.dropdown("TH_TITLE_CODE","","2","ALL_ALL_ALL",HtmlUtil.EDIT,"",request)%></td>
			</tr>
			<tr>
				<td><label>First Name <span class='require'>*</span> : </label> </td>
				<td><%=HtmlUtil.textBoxTH("TH_FIRST_NAME","","120",HtmlUtil.EDIT,"", request)%></td>
			</tr>
			<tr>
				<td><label>Last Name <span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.textBoxTH("TH_LAST_NAME","","120",HtmlUtil.EDIT,"", request)%></td>
			</tr>
			<tr>
				<td><label>Birth Date <span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.calendar("TH_BIRTH_DATE",null,"",HtmlUtil.EDIT,"",HtmlUtil.TH,request)%></td>
			</tr>
			<tr>
				<td><label>Channel<span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.dropdown("CHANNEL","","54","ALL_ALL_ALL",HtmlUtil.EDIT,"",request)%></td>
			</tr>
			<tr>
				<td><label>Template <span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.dropdown("TEMPLATE","","Template",HtmlUtil.EDIT,"",request)%></td>				
			</tr>
			<tr>
				<td><label>ApplicationType <span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.dropdown("APPLICATION_TYPE","","101",HtmlUtil.EDIT,"",request)%></td>				
			</tr>
			<tr>
				<td><label>Branch No <span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.search("BRANCH_NO","",SEARCH_BRANCH_INFO,"","","","",HtmlUtil.EDIT,"","",request)%></td>				
			</tr>
			<tr>
				<td><label>Apply Date <span class='require'>*</span> : </label></td>
				<td><%=HtmlUtil.calendar("APPLY_DATE",null,"",HtmlUtil.EDIT,"",HtmlUtil.TH,request)%></td>				
			</tr>
		</tbody>
	</table>
	<br>
	<input type='hidden' name='instantId' id='instantId' value='' />
	<input type='button' id="SendNextStation" value='SendNextStation'/>
	 
</form>
