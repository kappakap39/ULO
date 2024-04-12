<%@page import="com.eaf.orig.ulo.app.view.form.manual.SearchAddressZipCodeForm"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>

<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/js/AddressZipcodePopup.js"></script>
<%
	HashMap<String,String> hData = (HashMap<String,String>)request.getSession().getAttribute(SearchAddressZipCodeForm.ZIP_CODE_SESSION);
	
	String SEARCH_BY_PROVINCE = SystemConstant.getConstant("SEARCH_BY_PROVINCE");
	String SEARCH_BY_TAMBOL = SystemConstant.getConstant("SEARCH_BY_TAMBOL");
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<HashMap<String,Object>> results = SearchForm.getResults();
	BigDecimal TOTAL = FormatUtil.toBigDecimal(String.valueOf(SearchForm.getCount()));
	String subformId="ADDRESS_ZIPCODE_POPUP";
	String ADDRESS_ELEMENT_ID = hData.get("ADDRESS_ELEMENT_ID");
	String ADDRESS_TYPE = hData.get("ADDRESS_TYPE");
	String PERSONAL_SEQ = hData.get("PERSONAL_SEQ");
	String FUNCTION_POPUP=hData.get("FUNCTION_POPUP");
	if(Util.empty(ADDRESS_ELEMENT_ID)){
		ADDRESS_ELEMENT_ID = hData.get("PERSONAL_TYPE")+"_"+PERSONAL_SEQ+"_"+ADDRESS_TYPE;
	}
	FormUtil formUtil = new FormUtil(subformId,request);

%>
<section class="work_area padding-top">
<div class="row">
<div class="col-xs-12">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row form-horizontal">
				<%if(!SEARCH_BY_PROVINCE.equals(FUNCTION_POPUP)){%>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"SEARCH_TAMBOL","SEARCH_TAMBOL","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("SEARCH_TAMBOL","SEARCH_TAMBOL_" + ADDRESS_TYPE,"SEARCH_TAMBOL",
						SearchForm.getString("SEARCH_TAMBOL"),"","30","","col-sm-8 col-md-7",formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"SEARCH_AMPHUR","SEARCH_AMPHUR","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("SEARCH_AMPHUR","SEARCH_AMPHUR_" + ADDRESS_TYPE,"SEARCH_AMPHUR",SearchForm.getString("SEARCH_AMPHUR"),"","30","","col-sm-8 col-md-7",formUtil)%>
				</div>
				</div>	
				<%} %>	
				<div class="clearfix"></div> 	
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE","PROVINCE","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("SEARCH_PROVINCE","SEARCH_PROVINCE_" + ADDRESS_TYPE,"SEARCH_PROVINCE",
									SearchForm.getString("SEARCH_PROVINCE"),"","15","","col-sm-8 col-md-7",formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE","ZIPCODE","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("SEARCH_ZIPCODE","SEARCH_ZIPCODE_" + ADDRESS_TYPE,"SEARCH_ZIPCODE",
									SearchForm.getString("SEARCH_ZIPCODE"),"","15","","col-sm-8 col-md-7",formUtil)%>
					</div>
				</div>	
				 
				 	<div class="col-sm-12">
					<div class="form-group">
						<div class="row">
							<div class="col-md-12 text-center">
							 <%=HtmlUtil.button("ADDRESS_SEARCH_BTN","DM_SEARCH_BTN",HtmlUtil.EDIT,"btn2 btn2-green","",request) %>
							</div>
						</div>
					</div>
				</div>	
			</div>
		</div>
	</div>
</div>
</div>
</section>
<div class="row padding-top">
	<div class="col-md-12">
		<div class="titlesearch">
			<%=HtmlUtil.getFieldLabel(request, "TOTAL_RECORD")%><%=FormatUtil.display(TOTAL,FormatUtil.Format.NUMBER_FORMAT)%>
		</div>
	</div>
</div>
<div class="row padding-top">

	<div class="panel panel-default"  id="dataSearch">
	<table id="dataSearch" class="table table-striped table-hover" >
		<thead>
			<tr class="tabletheme_header">
				<th></th>
				<th><%=LabelUtil.getText(request,"TAMBOL")%></th>
				<th><%=LabelUtil.getText(request,"AMPHUR")%></th>
				<th><%=LabelUtil.getText(request,"PROVINCE")%></th>
				<th><%=LabelUtil.getText(request,"ZIPCODE")%></th>
			</tr>
		</thead>
		<tbody>
		<%if(!Util.empty(results)){
				for(HashMap<String,Object> Row:results) {
					String ZIPCODE_ID = FormatUtil.display(Row, "ZIPCODE_ID");
					String TAMBOL = FormatUtil.display(Row, "TAMBOL");
					String AMPHUR = FormatUtil.display(Row, "AMPHUR");
					String PROVINCE = FormatUtil.display(Row, "PROVINCE");
					String ZIPCODE = FormatUtil.display(Row, "ZIPCODE");
					String ZIPCODE_VALUE = "{zipcodeId:"+ZIPCODE_ID+",tambol:"+TAMBOL+",amphur:"+AMPHUR+",province:"+PROVINCE+",zipcode:"+ZIPCODE+"}";
		%>
			<tr>
				<td  align="center"><%=HtmlUtil.radio("SEARCH_ADDRESS_SELECT","", "", ZIPCODE_VALUE, HtmlUtil.EDIT, "", "", request) %></td>
				<td  align="center"><%=FormatUtil.display(Row,"TAMBOL")%></td>
				<td  align="center"><%=FormatUtil.display(Row,"AMPHUR")%></td>
				<td  align="center"><%=FormatUtil.display(Row,"PROVINCE")%></td>
				<td  align="center"><%=FormatUtil.display(Row,"ZIPCODE")%></td>
			</tr>
		
		<%}
		}else{ %>
			<tr>
				<td colspan="5" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
			</tr>
		<%} %>
		</tbody>
	</table>
	</div>
</div>
<section>
	<div class="row">
		<div class="col-md-12">
		<jsp:include page="/orig/ulo/util/AddressValuelist.jsp">
		</jsp:include>
		</div>
	</div>
</section>
<%=HtmlUtil.hidden("CRITERIA_FIELDS", "")%>
<%=HtmlUtil.hidden("ADDRESS_TYPE",ADDRESS_TYPE)%>