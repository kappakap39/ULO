<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
%>
<script>
<%
	Vector<HashMap> scForm = SQLQueryEngine.queryResult("select * from sc_form");
	if(!Util.empty(scForm)){
		for(HashMap smTemplate:scForm){
			out.print("var "+SQLQueryEngine.display(smTemplate,"FORM_ID")+"='"+SQLQueryEngine.display(smTemplate,"FORM_NAME")+"';");
		}
	}
%>
<%
	String subformSmartdata = "<option value=''>Please Select</option>";
	Vector<HashMap> smSubformGroups = SQLQueryEngine.queryResult("select * from sm_subformgroup");
	if(!Util.empty(smSubformGroups)){
		for(HashMap smSubformGroup:smSubformGroups){
			subformSmartdata += "<option value='"+SQLQueryEngine.display(smSubformGroup,"PKID")+"'>"
			+SQLQueryEngine.display(smSubformGroup,"GROUPNAME")+"</option>";
		}
	}
	System.out.println("subformSmartdata : "+subformSmartdata);
	out.print("var subformSmartdata = \""+subformSmartdata+"\";");
%>
function loadSubform(){
	var elementFormId = $('#element-formid').val();
	var subformIds = $("#"+elementFormId+" [name='subformId']");
	 $('#element-subformid').html('');
	 $('#element-subformid').append($('<option>', { 
	        value: 'ALL',
	        text : 'All Subform'
	    }));
	$.each(subformIds, function (i, item) {
		var subformName = $("#SECTION_"+$(item).attr('value')+" .titlecontent").text();
		if(subformName == ''){
			subformName = $("#SECTION_"+subformId+'  .subtitlecontent').text();
		}
		var subformId = $(item).attr('value');		
		if(subformId == 'DOCUMENT_CHECK_LIST'){
			subformName = 'Document Check List';
		}
	    $('#element-subformid').append($('<option>', { 
	        value: subformId,
	        text : subformName
	    }));
	});
	
}
function loadFormElement(){
	$('#div-element-subform').html('');
	var elementFormId = $('#element-formid').val();
	var elementSubFormId = $('#element-subformid').val();	
	var subformIds = $("#"+elementFormId+" [name='subformId']");
	$.each(subformIds, function (i, item){
		var subformId =  $(item).attr('value');
		if(elementSubFormId == 'ALL' || elementSubFormId == subformId){
			var elementSubformId = 'ELEMENT_'+subformId;
			$('#div-element-subform').append('<div class="well well-sm smartdata" id="'+elementSubformId+'"></div>');
			var subformTitle = $("#SECTION_"+subformId+'  .titlecontent').text();
			if(subformTitle == ''){
				subformTitle = $("#SECTION_"+subformId+'  .subtitlecontent').text();
			}		
			if(subformId == 'DOCUMENT_CHECK_LIST'){
				subformTitle = 'Document Check List';
			}
			$('#'+elementSubformId).append('<div class=element-subform-header>'+subformTitle+'</div>');
			$('#'+elementSubformId).append('<div class=><span class=element-subform-smartdata>Smart Data SubForm : </span><select id=element-smartdata>'+subformSmartdata+'</select></div>');
			var formGroups = $('#'+elementFormId+' #SECTION_'+subformId+' .form-group');			
			$.each(formGroups, function (j, formGroupElement){
				var elementLabel = $.trim(($(formGroupElement).find('label').text()).replace(':','').replace('*','').replace(':',''));
				var elementInputs = $(formGroupElement).find('input,textarea,select');				
				var elementInput = '';
				var comma = '';
				$.each(elementInputs, function (k, inputElement){
					if($(inputElement).attr('id') != undefined){
						elementInput += comma+$(inputElement).attr('id');
						comma = ',';
					}					
				});
				if(elementLabel != '' && elementInput != ''){
					$('#'+elementSubformId)
					.append('<div class=element-field elementInput='+elementInput+'>'
					+'<span class=element-name>'+elementLabel+' : </span>'
					+elementInput+'</div>');				
				}				
			});
		}
	});
}
$(function(){
// 	$('#eFooter').hide();
	var formIds = $("[name='formId']");
	$.each(formIds, function (i, item) {
	    $('#element-formid').append($('<option>', { 
	        value: $(item).attr('value'),
	        text : eval($(item).attr('value'))
	    }));
	});
	loadSubform();
});
</script>
<div class="well well-sm">
	<div id='div-element-formid'>Form:<select id='element-formid' onchange='loadSubform();'></select></div>
	<div id='div-element-subformid'>SubForm:<select id='element-subformid'></select></div>
	<div id='div-element-smartid'>
		Smart Data Template:
		<select id='element-smartid'>
<%
	Vector<HashMap> smTemplates = SQLQueryEngine.queryResult("select * from sm_template");
	out.print("<option value=''>Please Select</option>");
	if(!Util.empty(smTemplates)){
		for(HashMap smTemplate:smTemplates){
			out.print("<option value='"+SQLQueryEngine.display(smTemplate,"PKID")+"'>"+SQLQueryEngine.display(smTemplate,"TEMPLATENAME")+"</option>");
		}
	}
%>	
		</select>
	</div>
	<div id='button'><button class="btn btn-warning" style="success" onclick="loadFormElement();">Load Form Element</button></div>
</div>
<div id='div-element-subform' style="overflow-y: scroll; height: 300px;">

</div>
<style>
.element-subform-header{
	font-size: 1.2em;
	font-weight: bold;
    line-height: 0.8em;
    color: #00a950;
    text-shadow: 0px 1px #fbfbfb;
    float: left;
    margin: 0 0 10px;
    width: 100%;
}
.element-subform-smartdata{
	font-size: 1em;
	color: #f0ad4e;
	font-weight: bold;
	text-shadow: 0px 1px #fbfbfb;
}
.element-name{
	font-weight: bold;
}
.modal.fade.in{
	z-index: 9999;
}
</style>
<script>
$(function(){
	var height = $(window).height();
	$('#div-element-subform').css({
		'height':height-350+'px'
	});
});
</script>