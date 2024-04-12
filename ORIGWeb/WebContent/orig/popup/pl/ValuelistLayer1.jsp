<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.util.HashMap"%>  
<%@ page import = "com.eaf.j2ee.system.LoadXML"%>
<%@ page import="com.eaf.orig.shared.model.ValueListM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
	Vector resultVt=null;
	ValueListM valueListM = (ValueListM)request.getSession().getAttribute("VALUE_LIST");
   	if (valueListM == null) {
	    valueListM = new  ValueListM();
	}
	resultVt=valueListM.getResult();  
	if(resultVt==null){
		resultVt=new Vector();
	}
	String popup_search ="popup_search";
	if(valueListM.getTextboxCode()!=null&&valueListM.getTextboxCode().equalsIgnoreCase("title_eng")){
		popup_search=null;
		popup_search="popup_search_en";
	}
%>

<!-- Start Page List of Value List -->
<%	
	if((resultVt != null) && (resultVt.size() > 1)){
	
	HashMap pagePerPageMap = LoadXML.getPagePerPageMap();
	HashMap itemPerPageMap = LoadXML.getItemPerPageMap();
			
	int pagePerPage = (pagePerPageMap != null && pagePerPageMap.get("1") != null)?Integer.parseInt((String)pagePerPageMap.get("1")):1;
	int allItemCount = valueListM.getCount();
	System.out.println("allItemCount = "+allItemCount);
	// int itemsPerPage = valueListM.getItemsPerPage();
	int itemsPerPage = 20;
	int pageCount = ((allItemCount%itemsPerPage)==0)?(allItemCount/itemsPerPage):(allItemCount/itemsPerPage)+1;
	int periodCount = ((pageCount%pagePerPage)==0)?(pageCount/pagePerPage):(pageCount/pagePerPage)+1;
	int atPage = valueListM.getAtPage();
	int atPeriod = ((atPage%pagePerPage)==0)?(atPage/pagePerPage):((atPage/pagePerPage)+1);
	int beginPage = ((atPeriod-1)*pagePerPage)+1;
	int endPage = ((beginPage+pagePerPage-1)>pageCount)?pageCount:(beginPage+pagePerPage-1);
%> 


<%
	String code = (String)request.getSession(true).getAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
	request.getSession(true).removeAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
 %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
		<td align="left" width="35%">
			<input type="text" name="code" id="code" class="textbox" maxlength="100" value="<%= (code != null && !"".equals(code))? code:"" %>" size="10">
			<input type="button" name="Search" class="buttonNew" value="<%=PLMessageResourceUtil.getTextDescription(request, "SEARCH") %>" onclick="javascript:clickSearch()">
		</td>
		<td class="inputL">
		<% if(pageCount==0){pageCount=pageCount+1;}
		 %>
		<%=PLMessageResourceUtil.getTextDescription(request, "PAGE") %> : <%=atPage%> / <%=pageCount%>
		<%
			if(atPeriod!=1){
		%>	&nbsp;<A href="javascript:clickPageList('<%=(beginPage-1)%>')">&lt;&lt;</A>&nbsp;&nbsp;												<%
			}%> 
	<%
		for(int i=beginPage;i<=endPage;i++){
			if(i!=atPage){
	%> &nbsp;<A href="javascript:clickPageList('<%=i%>')"><FONT class="bk"><%=i%></FONT></A>&nbsp;
	<%
		}else{
	%> &nbsp;<FONT color="red"><%=i%></FONT>&nbsp; <%
		}
	%> <%
		}
	%> <%
		if(atPeriod<periodCount){
	%> 	&nbsp;&nbsp;<A href="javascript:clickPageList('<%=(beginPage+pagePerPage)%>')">&gt;&gt;</A>&nbsp;&nbsp;
	<%
		}
	%> 
		&nbsp; 
		<SELECT name="select2" id="select2" onchange="javascript:clickItemPerPageList(this.value)">
		<OPTION value='<%=itemsPerPage%>'><%=itemsPerPage%></OPTION>
		</SELECT> 
		</td>
	</tr>
</table>
<%}else{
	String code = (String)request.getSession(true).getAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
%>
<input type="text" name="code" id="code" class="textbox" maxlength="100" value="<%= (code != null && !"".equals(code))? code:"" %>" size="10" >
<input type="button" name="Search" class="buttonNew" value="<%=PLMessageResourceUtil.getTextDescription(request, "SEARCH") %>" onclick="javascript:clickSearch()">
<%}%> 
<!-- End Page List of Value List -->
<%=HTMLRenderUtil.displayHiddenField("", "atPage") %>
<%=HTMLRenderUtil.displayHiddenField("", "itemsPerPage") %>
<%=HTMLRenderUtil.displayHiddenField("", "bank") %>
<%=HTMLRenderUtil.displayHiddenField("", "car_brand") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getCacheName(), "cacheName") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getTextboxCode(), "textboxCode") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getTextboxDesc(), "textboxDesc") %>
<SCRIPT language="JavaScript">
function clickPageList(atPage){
// alert(atPage);
var item = $('#select2').val();
	var action =  '<%=valueListM.getSearchAction()%>';
	var codeValue = $('#code').val();
	var textboxCode = '<%=valueListM.getTextboxCode()%>';
	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
	var url = "/ORIGWeb/FrontController?action="+action+"&itemsPerPage="+item+"&handleForm=N"+"&atPage="+atPage+"&textboxCode="+textboxCode+"&textboxDesc="+textboxDesc;
	var title = getPopupTitle('<%=valueListM.getSearchAction()%>');
if(action!='LoadProvince'&&action!='LoadDistrict'&&action!='LoadSubDistrict'&&action!='LoadPLZipCode'&&action!='LoadNational'&&action!='LoadFileCategoryPopup'){
// alert("in if");
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(document.body)
    		},
		    close: function(){
		    	closeDialog();
		    }
	});
   $dialog.dialog("open");

}else{
// alert("in else");
		$dialog2 = $dialogEmpty;
		var width = 1000;
		var higth = $(window).height()-50;
// 		$(".plan-dialog-2").parent().remove();
 		$(".plan-dialog-2").remove();
		$("div.popupLayer2").remove();

		$dialog2 = $('<div class="plan-dialog-2" id="plan-dialog-2"/>').load(url);
		$dialog2.dialog({
				dialogClass: "popupLayer2",
			    resizable : false,
			    modal : true,
			    autoOpen: false,
			    draggable: true,
			    width: width,
			    height: higth,
			    title:title,
			    position: { 
			        my: 'center',
			        at: 'center',
			        of: $(document.body)
	    		},
			    close: function(){
			    	fullclosedialog2();
			    }
		});
	   $dialog2.dialog("open");
	   $dialog2.dialog("moveToTop");
	}

}
function clickItemPerPageList(atItem){
  
	var item = $('#select2').val();
	var action =  'ValueListPopupWebAction';
	var codeValue = $('#code').val();
	var textboxCode = '<%=valueListM.getTextboxCode()%>';
	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
	

	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	$(".plan-dialog").remove();
	var url = "/ORIGWeb/FrontController?action="+action+"&itemsPerPage="+item+"&handleForm=N";
	var title = getPopupTitle('<%=valueListM.getSearchAction()%>');
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(document.body)
    		},
		    close: function(){
				$(".plan-dialog").remove();
		    	fullclosedialog();
		    }
	});
   $dialog.dialog("open");
}

function clickSearch(){
	var action =  '<%=valueListM.getSearchAction()%>';
	var codeValue = $('#code').val();
	var textboxCode = '<%=valueListM.getTextboxCode()%>';
	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
	
	var seller_branch=null;
	var currentBranch=null;
	if(textboxCode=='service_seller_title'){ seller_branch = $('#seller_branch_code').val();}

	if(textboxCode=='ref_recommend_title'){currentBranch=$('#ref_branch_code').val();}
	else if(textboxCode=='seller_title'){currentBranch=$('#seller_branch_code').val();}
	else if(textboxCode=='service_seller_title'){currentBranch=$('#service_seller_branch_code').val();}
	
	var url ='';
	var title ='';
	if(action=='LoadRecommendInformation'){
		var channel = $('#channel').val();
		var channelVal = $('#saleChannel').val();
		 url = "/ORIGWeb/FrontController?action="+action+"&code="+encodeURI(codeValue)+"&textboxCode="+textboxCode+"&textboxDesc="+textboxDesc+"&channel="+channel+"&saleChannel="+channelVal+"&seller_branch="+seller_branch+"&currentBranch="+currentBranch;
		 title = getSellerPopupTitle(textboxCode);
	}else if(action=='LoadProjectCode'){
		var exception_project = $('#exception_project').attr('checked');
		var product_feature = $('#product_feature').val();
		var product_featureSysID2 = getSysID2(product_feature);
		url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadProjectCode"+"&project_code="+codeValue+"&exception_project="+exception_project+"&product_feature="+product_featureSysID2;
		title = getPopupTitle(action);
	} else if(action=='LoadProjectCodeAll'){
		url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadProjectCodeAll&project_code="+code.value;
		title = getPopupTitle(action);
	} else if(action=='LoadUserNameWebAction'){
		var role = $('#role').val();
		url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadUserNameWebAction&role="+role+"&firstName="+encodeURI(codeValue);
		title = FULL_NAME;
// 		alert("firstName "+firstName);
// 		$dialog.dialog("close");
// 		closeDialog();
// 		LoadPopup(role, firstName);
// 	    return null;
	} else {
		 url = "/ORIGWeb/FrontController?action="+action+"&code="+encodeURI(codeValue)+"&textboxCode="+textboxCode+"&textboxDesc="+textboxDesc;
		 title = getPopupTitle(action);
	}

if(action!='LoadProvince'&&action!='LoadDistrict'&&action!='LoadSubDistrict'&&action!='LoadPLZipCode'&&action!='LoadNational'&&action!='LoadFileCategoryPopup'){
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(document.body)
    		},
		    close: function(){
		    	closeDialog();
		    }
	});
   $dialog.dialog("open");

}else{
		$dialog2 = $dialogEmpty;
		var width = 1000;
		var higth = $(window).height()-50;
		$(".plan-dialog-2").parent().remove();
 		$(".plan-dialog-2").remove();
// 		$("div.popupLayer2").remove();

		$dialog2 = $('<div class="plan-dialog-2" id="plan-dialog-2"/>').load(url);
		$dialog2.dialog({
				dialogClass: "popupLayer2",
			    resizable : false,
			    modal : true,
			    autoOpen: false,
			    draggable: true,
			    width: width,
			    height: higth,
			    title:title,
			    position: { 
			        my: 'center',
			        at: 'center',
			        of: $(document.body)
	    		},
			    close: function(){
			    	fullclosedialog2();
			    }
		});
	   $dialog2.dialog("open");
	   $dialog2.dialog("moveToTop");
	}
}


function setData(code, desc){
	
 	var textboxCode = '<%=valueListM.getTextboxCode()%>'; 
 	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
 	var obj1 = $('#'+textboxCode);
 	obj1.val(code);
 	if(textboxDesc != null && textboxDesc != "" && textboxDesc != "undefined"){ 
		var obj2 = $('#'+textboxDesc);
 		if(obj2 != null){obj2.val(desc);} 
	}
 	if(textboxCode == 'project_code'){
// 	 	$dialog.dialog("close");
	 	getvalue();
	 	$dialog.dialog("close");
 	} 
 	if(textboxCode == 'seller_branch_code'){
//  		$dialog.dialog("close");
 		getChannelDropdown(); 	
 	}
 	if(textboxCode =='tambol'||textboxCode =='amphur'||textboxCode =='province'||textboxCode =='file_category_code'||textboxCode =='country_no'||textboxCode =='zipcode'){
	 	if(textboxCode =='tambol'){getZipCode();}
	 		var parentlayer2 = $("#plan-dialog-2").parent();
		    $("#plan-dialog-2").empty().remove();
		    parentlayer2.empty().remove();
	 		return $dialog2.dialog("close");
 	}else{
 		if($dialog!=undefined){
 			return $dialog.dialog("close");
 		}
		
	}
 	
} 
function setDataProjectCode(code, desc, bussClass){
    var textboxCode = '<%=valueListM.getTextboxCode()%>'; 
 	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
 	var obj1 = $('#'+textboxCode);
 	obj1.val(code);
 	if(textboxDesc != null && textboxDesc != "" && textboxDesc != "undefined"){ 
		var obj2 = $('#'+textboxDesc);
 		if(obj2 != null){obj2.val(desc);} 
	}
    if(textboxCode == 'project_code'){
// 	 	$dialog.dialog("close");
	 	getvalue(bussClass);
	 	$dialog.dialog("close");
 	} 
}
function setDataProjectCodeAll(code){
 	$('#project_code').val(code);
	 $dialog.dialog("close");
}
function setDataAddress(code, desc, sub_districtID, sub_district_desc,districtID, district_desc, proviceID, provice_desc){
// alert("setDataAddress");
	var textboxCode = '<%=valueListM.getTextboxCode()%>';
	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
	var obj1 = $("#"+textboxCode);
	obj1.val(code);	

 	if(textboxCode == 'province'){$('#zipcode').val(""); }	
 	if(textboxCode == 'tambol'){
 	getZipCode();
 	}
 	if(textboxDesc != null && textboxDesc != "" && textboxDesc != "undefined"){
		var obj2 = $("#"+textboxDesc);	
		if(obj2 != null){
			obj2.val(desc);
		}
	}
	 $("#province").val(proviceID);
	 $("#province_desc").val(provice_desc);
	 $("#amphur").val(districtID);
	 $("#amphur_desc").val(district_desc);
	 $("#tambol").val(sub_districtID);
	 $("#tambol_desc").val(sub_district_desc);
	 
// 	 $("div#plan-dialog-2").remove();
// 	$("div.popupLayer2").remove();
// 	 $dialog2.dialog("close");
	$dialog.dialog("close");
// 	 fullclosedialog2();
}
function clearSchemeByCampaign(){
	form = window.opener.appFormName;
	if (form.scheme_code!=undefined && form.campaign!=undefined){
		window.opener.clearSchemeByCampaign();
	}
}
function setDataTitle(code, desc, gender, eng_desc){

	var textboxCode = '<%=valueListM.getTextboxCode()%>';
	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
	var obj1 = $('#'+textboxCode);
	obj1.val(code);
	if(textboxDesc != null && textboxDesc != "" && textboxDesc != "undefined"){
		var obj2 = $('#'+textboxDesc);
		if(obj2 != null){obj2.val(desc);}
	}
	if(gender!=null){$('#gender').val(gender);}
	if(eng_desc!=null){
		$('#titleEng').val(eng_desc);
		$('#title_eng').val(code);
	}
	if($dialog!=undefined){
		$dialog.dialog("close");
	}
	
}
 
</SCRIPT>
