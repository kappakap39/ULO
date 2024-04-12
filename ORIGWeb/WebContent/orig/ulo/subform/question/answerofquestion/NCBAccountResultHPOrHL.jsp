
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.app.ncb.dao.OrigNCBInfoDAO"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAccountDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/subform/question/answerofquestion/js/NCBAccountResultHPOrHL.js"></script>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String NCB_CREDIT_TYPE_HL = SystemConstant.getConstant("NCB_CREDIT_TYPE_HL");
	String NCB_CREDIT_TYPE_HP = SystemConstant.getConstant("NCB_CREDIT_TYPE_HP");
	String NCB_ACC_TYPE_CACHE = SystemConstant.getConstant("NCB_ACC_TYPE_CACHE");
	
	
	
	 ArrayList<HashMap<String,Object>> NCBAccountTypeInfos = new ArrayList<HashMap<String,Object>>();
	ArrayList<HashMap<String,Object>> NCBAccountTypeInfoHLs = CacheControl.search(NCB_ACC_TYPE_CACHE, "CREDIT_TYPE", NCB_CREDIT_TYPE_HL);
	ArrayList<HashMap<String,Object>> NCBAccountTypeInfoHPs = CacheControl.search(NCB_ACC_TYPE_CACHE, "CREDIT_TYPE", NCB_CREDIT_TYPE_HP);
	if(!Util.empty(NCBAccountTypeInfoHLs)){
		NCBAccountTypeInfos.addAll(NCBAccountTypeInfoHLs);
	}
	if(!Util.empty(NCBAccountTypeInfoHPs)){
		NCBAccountTypeInfos.addAll(NCBAccountTypeInfoHPs);
	}
	
	
		
	String displayMode = HtmlUtil.EDIT;
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)ModuleForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
	OrigNCBInfoDAO dao =  com.eaf.orig.ulo.app.dao.ORIGDAOFactory.getNCBInfoDAO();	
	ArrayList<NcbInfoDataM>	 ncbInfos =	dao.loadOrigNcbInfos(personalInfo.getPersonalId());
	if(Util.empty(ncbInfos)){
		ncbInfos = new ArrayList<NcbInfoDataM>();
		personalInfo.setNcbInfos(ncbInfos);
	}
	

%>

<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			
				<table class="table table-bordered">
					<tbody>
					<tr class="tabletheme_header">
						<th><%=HtmlUtil.getLabel(request, "NUMBER_ABBR", "col-sm-8 col-md-12") %></th>
						<th><%=HtmlUtil.getLabel(request, "LOAN_TYPE", "col-sm-8 col-md-12") %></th>
						<th><%=HtmlUtil.getLabel(request, "LIMIT", "col-sm-8 col-md-12") %></th>
						
					</tr>
						<%
							if(!Util.empty(ncbInfos)){
								for(NcbInfoDataM ncbInfo:ncbInfos){
									ArrayList<NcbAccountDataM> ncbAccounts	= ncbInfo.getNcbAccounts();
									if(Util.empty(ncbAccounts)){
										ncbAccounts = new ArrayList<NcbAccountDataM>();
										ncbInfo.setNcbAccounts(ncbAccounts);
									}
									if(!Util.empty(ncbAccounts)){
										for(NcbAccountDataM ncbAccount:ncbAccounts){
											int seq = 0;
											
											if(!Util.empty(NCBAccountTypeInfos)){
												logger.debug("NCBAccountTypeInfos >> "+NCBAccountTypeInfos.size());
												for(HashMap<String,Object> NCBAccountTypeInfo:NCBAccountTypeInfos){	
												logger.debug("NCBAccountTypeInfo >>> "+NCBAccountTypeInfo);
													String accountCode =(String)NCBAccountTypeInfo.get("ACCOUNT_CODE");
	 												String accountDescription=(String)NCBAccountTypeInfo.get("ACCOUNT_TYPE");
	 												
	 													if(!Util.empty(ncbAccount.getSeq()) && accountCode.equals(ncbAccount.getAccountType())){
// 															seq = String.valueOf(ncbAccount.getSeq());
															seq++;
															%>
															<tr>
																<td><%=FormatUtil.display(seq)%></td>
																<td><%=FormatUtil.display(accountDescription) %></td>
																<td><%=FormatUtil.displayCurrency(ncbAccount.getInstallAmt(),true) %></td>
															
															</tr>
															<%
															}
												}
												
											}
										}
									}
								}
							}else{%>
								<tr >
									<td colspan="9" align="center"><%=HtmlUtil.getText(request, "NO_RECORD_FOUND") %> </td>
								</tr>
								<%
							}
					 	%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
