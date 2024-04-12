<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.cache.properties.CardTypeProperties"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="/orig/ulo/product/kec/popup/js/SubCardInfoSubForm.js"></script>
<%
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	Logger logger = Logger.getLogger(this.getClass());
	CardDataM cardM = (CardDataM)ModuleForm.getObjectForm();
	logger.debug("cardM >> "+cardM);
	if(Util.empty(cardM)){
		cardM = new CardDataM();
	}	
	
	String displayMode = HtmlUtil.EDIT;
 %>

<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "M_TH_NAME")%>
						<%=FormatUtil.display(cardM.getPersonalId())%>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "CARD_LEVEL")%>
						<%=HtmlUtil.dropdown("CARD_LEVEL", PRODUCT_K_EXPRESS_CASH, "", cardM.getCardId(), "", "", "", displayMode, HtmlUtil.elementTagId("CARD_LEVEL"), "col-sm-4 col-md-5 control-label", request)%>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "GROUP_NO")%>
						<%=HtmlUtil.textBox("GROUP_NO", PRODUCT_K_EXPRESS_CASH, cardM.getGangNo(), "", "10", displayMode, HtmlUtil.elementTagId("GROUP_NO"), "col-sm-4 col-md-5 control-label", request)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "NO_APP_IN_GANG")%>
						<%=HtmlUtil.textBox("NO_APP_IN_GANG", PRODUCT_K_EXPRESS_CASH, cardM.getGangNo(), "", "10", displayMode, HtmlUtil.elementTagId("NO_APP_IN_GANG"), "col-sm-4 col-md-5 control-label", request)%>
					</div>
				</div>
			</div>
		</div>
</div>


