<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.KVIIncomeDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/KVIPopup.js"></script>
<%
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	IncomeInfoDataM incomeM = (IncomeInfoDataM) ModuleForm.getObjectForm();
	System.out.println("incomeM "+incomeM);
	ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
	KVIIncomeDataM kviM = null;
	if(incomeList.size() > 0){
		kviM = (KVIIncomeDataM)incomeList.get(0);
	}
	String url = SystemConfig.getProperty("KVI_URL")+"?tokenId="+(Util.empty(kviM.getTokenId())?"":kviM.getTokenId())+"&fId="+(Util.empty(kviM.getKviFid())?"":kviM.getKviFid());
	
 %>
 <%=HtmlUtil.hidden("VERIFIED_INCOME", FormatUtil.display(kviM.getVerifiedIncome(),true)) %>
 <%=HtmlUtil.hidden("PERCENT_CHEQUE", FormatUtil.display(kviM.getPercentChequeReturn(),true)) %>
<div class="panel panel-default">
<div class="panel-body">
	<div class="row form-horizontal" style="height: 400px">
	<div class="col-sm-12" style="height: 100%" >
		<iframe id="kviFrame" src="<%=url%>" width="100%" height="100%" seamless="seamless" >Do not support iframes</iframe>			
	</div>
		
	</div>
</div>
</div>

