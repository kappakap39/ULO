<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM"%>
<%@ page import="com.eaf.xrules.shared.model.XRulesExistcustDataM"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM"%>
<%@ page import="com.eaf.orig.shared.model.LoanDataM"%>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%> 
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>

<jsp:useBean id="ORIGUser" scope="session"
	class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session"
	class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />

<%	  ORIGUtility utility = new ORIGUtility();
             OrigXRulesUtil oricXrulesUtil=OrigXRulesUtil.getInstance();
            ApplicationDataM appForm = ORIGForm.getAppForm();
            //get Personal
            PersonalInfoDataM personalInfoDataM;
            String personalType = (String) request.getSession().getAttribute("PersonalType");
            if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
                personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
            }else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
				personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
			} else {
                personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
            }
            String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
              System.out.println("viewFromReport "+request.getParameter("viewFromReport"));
   String chkDisable="";
    //=============================================
    if(request.getParameter("viewFromReport")!=null){   
    if(session.getAttribute("applicationVerification")!=null){ 
     ApplicationDataM applicationDataM=(ApplicationDataM)session.getAttribute("applicationVerification"); 	     
      String reportPersonalSeq=request.getParameter("reportPersonalSeq");     
      String reportPersonalType=request.getParameter("reportPersonalType");            
      int personalSeq=utility.stringToInt(reportPersonalSeq);
      personalInfoDataM=utility.getPersonalInfoByTypeAndSeq( applicationDataM,reportPersonalType,personalSeq);     
     } 
      displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
      chkDisable=" disabled";
    }
    //=============================================
            if (personalInfoDataM == null) {
                System.out.println("==> personalInfoDataM is Null");
                personalInfoDataM = new PersonalInfoDataM();
            }
            XRulesVerificationResultDataM xrulesVer = personalInfoDataM.getXrulesVerification();
            System.out.println("==> xrulesVer "+xrulesVer);
            
            Vector xRuesExistCustomer = xrulesVer.getVXRulesExistcustDataM();
            System.out.println("==> xRuesExistCustomer "+xRuesExistCustomer);
            if (xRuesExistCustomer == null) {
                xRuesExistCustomer = new Vector();
            }
            Vector xRuesExistCustomerInprogress = xrulesVer.getVXRulesExistcustInprogressDataM();
            System.out.println("==> xRuesExistCustomerInprogress "+xRuesExistCustomerInprogress);
            if (xRuesExistCustomerInprogress == null) {
                xRuesExistCustomerInprogress = new Vector();
            }
            
            Vector xRuesExistCustomerSurname = xrulesVer.getVXRulesExistcustSurnameDataM();
            System.out.println("==> xRuesExistCustomerSurname "+xRuesExistCustomerSurname);
            if (xRuesExistCustomerSurname == null) {
                xRuesExistCustomerSurname = new Vector();
            }
            Vector xRuesExistCustomerInprogressSurname = xrulesVer.getVXRulesExistcustInprogressSurnameDataM();
            System.out.println("==> xRuesExistCustomerInprogressSurname "+xRuesExistCustomerInprogressSurname);
            if (xRuesExistCustomerInprogressSurname == null) {
                xRuesExistCustomerInprogressSurname = new Vector();
            }
            BigDecimal totalFinanceAmt = new BigDecimal(0);
            BigDecimal totalNetFinanceAmt = new BigDecimal(0);
            BigDecimal totalExposureExclude = new BigDecimal(0);
            BigDecimal totalExposure= new BigDecimal(0);
            BigDecimal totalOriginalFinanceAmt = new BigDecimal(0);
            BigDecimal totalInstallment = BigDecimal.valueOf(0);
            for (int i = 0; i < xRuesExistCustomerInprogress.size(); i++) {
                XRulesExistcustInprogressDataM xRulesExistingCustomerInprogress = (XRulesExistcustInprogressDataM) xRuesExistCustomerInprogress.get(i);
                if(! (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(xRulesExistingCustomerInprogress.getCustomerType())&& OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(xRulesExistingCustomerInprogress.getCoBorrowerFlag())) ){
                 if (xRulesExistingCustomerInprogress.getFinanceAmt() != null  ) {
                    totalFinanceAmt = totalFinanceAmt.add(xRulesExistingCustomerInprogress.getFinanceAmt());
                  }
                }
            }

            for (int i = 0; i < xRuesExistCustomer.size(); i++) {
                XRulesExistcustDataM xRulesExistingCustomerDataM = (XRulesExistcustDataM) xRuesExistCustomer.get(i);
                if ("A".equalsIgnoreCase(xRulesExistingCustomerDataM.getContractStatus())) {
                    totalNetFinanceAmt = totalNetFinanceAmt.add(xRulesExistingCustomerDataM.getNetFinanceAmount());
                    totalOriginalFinanceAmt = totalOriginalFinanceAmt.add(xRulesExistingCustomerDataM.getOriginalFinaceAmount());
                    totalInstallment = totalInstallment.add(xRulesExistingCustomerDataM.getInstallment());
                }
            }
            totalExposureExclude = totalFinanceAmt.add(totalNetFinanceAmt);
            ORIGCacheUtil origCacheUtil = ORIGCacheUtil.getInstance();           
           LoanDataM loanDataM=null;
           if(OrigConstant.ORG_AUTO_LOAN.equals(OrigUtil.getOrgID(appForm.getBusinessClassId()))){
	           if(appForm.getLoanVect()!=null&&appForm.getLoanVect().size()>0){
	              loanDataM=(LoanDataM)appForm.getLoanVect().get(0);
	              totalExposure=totalExposureExclude.add(loanDataM.getCostOfFinancialAmt());
	           }
	       }

            %>
<HEAD>
<TITLE>Existing Customer : Existing Result</TITLE>
<META http-equiv=content-type content="text/html; charset=UTF-8">
<SCRIPT language="JavaScript">
window.onBlur = self.focus();
</SCRIPT>
</HEAD>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr>
	                          	<td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_4") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick=" window.close()">	
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center">	 
							<tr>
								<td class="textColS" width="30%" ><b>Total Exposure exclude this application.</b></td>
								<td align="right" width="20%" class="inputCol"><%=ORIGDisplayFormatUtil.displayCommaNumber(totalExposureExclude)%></td>
								<td class="textColS" width="50%">&nbsp;</td>
							</tr>
						   <%if(loanDataM!=null){ %>	
							<tr>
								<td class="textColS"><b>Finance amount of this application. </b></td>
								<td align="right" class="inputCol"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfFinancialAmt())%></td> 
								<td class="textColS">&nbsp;</td>
							</tr>
						     <tr>
								<td class="textColS"><b>Total Exposure include this application.</b>
								</td>
								<td align="right" class="inputCol"><%=ORIGDisplayFormatUtil.displayCommaNumber(totalExposure)%></td> 
								<td class="textColS">&nbsp;</td>
							</tr>
						   <% }%>	
							</table>
						</td></tr>
						<tr class="sidebar10"> <td align="center">	 
							<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0" bgcolor="#FFFFFF">
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" align="center"><B><%=MessageResourceUtil.getTextDescription(request, "INPROGRESS")%></B></td>
										</tr>
									</table>
								</td></tr>
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" width="12%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
											<TD class="Bigtodotext3" width="12%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE")%></TD>
											<TD class="Bigtodotext3" width="18%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "COBORROWER_FLAG")%></TD>					
											<TD class="Bigtodotext3" width="10%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_DATE")%></TD>
											<TD class="Bigtodotext3" width="18%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "FINANCE_AMT_EXIST")%></TD>
											<TD class="Bigtodotext3" width="18%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "EXIST_CUST_INSTALLMENT")%></TD>	
											<TD class="Bigtodotext3" width="12%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></TD>
										</tr>
									</table>
								</td></tr>
										
							<%for (int i = 0; i < xRuesExistCustomerInprogress.size(); i++) {
				                XRulesExistcustInprogressDataM xRulesExistingCustomerInprogress = (XRulesExistcustInprogressDataM) xRuesExistCustomerInprogress.get(i);
				                String appStatus = xRulesExistingCustomerInprogress.getApplicationStatus();                
				                    appStatus =oricXrulesUtil.getExistingApplicaitonInprogressStatus(appStatus); 
				                String coBorrowerFlag="";
				                String customerType="";
				                if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(xRulesExistingCustomerInprogress.getCustomerType())){
				               		 if(OrigConstant.COBORROWER_FLAG_ACTIVE.equals(xRulesExistingCustomerInprogress.getCoBorrowerFlag())    ){
				                  		coBorrowerFlag="Active";
				            		   customerType="Co-Borrower";
				               		 }else if(OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(xRulesExistingCustomerInprogress.getCoBorrowerFlag())    ){
				                  		coBorrowerFlag="Inactive ";
				                  		customerType="Co-Borrower";
				                	} else{
				                	 customerType=(String) XRulesConstant.hCustomerType.get(xRulesExistingCustomerInprogress
				                                        .getCustomerType());
				                	} 
				                }  else{
				                customerType=(String) XRulesConstant.hCustomerType.get(xRulesExistingCustomerInprogress
				                                        .getCustomerType());
				                }      
				                %>
								<tr><td align="center" class="gumaygrey2">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="jobopening2" width="12%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerInprogress.getApplicationNo())%></td>
											<TD class="jobopening2" width="12%"><%=ORIGDisplayFormatUtil.displayHTML(customerType)%></TD>
											<TD class="jobopening2" width="18%"><%=ORIGDisplayFormatUtil.displayHTML( coBorrowerFlag)%></TD>                                        
											<TD class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.datetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(ORIGDisplayFormatUtil
							                                .parseDate(xRulesExistingCustomerInprogress.getApplicationDate())))%></TD>
											<TD class="jobopening2" align="right" width="18%"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerInprogress.getFinanceAmt())%></TD>
											<TD class="jobopening2" align="right" width="18%"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerInprogress.getInstallment())%>&nbsp;&nbsp;&nbsp;</TD>
											<TD class="jobopening2" width="12%"><%=ORIGDisplayFormatUtil.displayHTML(appStatus)%></TD>
										</tr>
									</table>
								</td></tr>
								<%}%>
							</table>
						</td></tr>
						<tr><td><br>
						<hr>
						<br>
						</td></tr>
						<tr class="sidebar10"> <td align="center">	 
							<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0" bgcolor="#FFFFFF">
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" align="center"><B><%=MessageResourceUtil.getTextDescription(request, "BOOKED")%></B></td>
										</tr>
									</table>
								</td></tr>
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "CONTRACT_NO")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "BOOKING_DATE")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "ORIGINAL_FINANCE_AMT")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "NET_FINANCE_AMT")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "CONTRACT_STATUS")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "COLLECTION_STATUS")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "OVERDUE_PERIOD")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "PAID_PERIOD")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "UNPAID_PERIOD")%></td>
											<td class="Bigtodotext3" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "EXIST_CUST_INSTALLMENT")%></td>
										</tr>
									</table>
								</td></tr>
							<%if (xRuesExistCustomer.size() > 0) {

                				for (int i = 0; i < xRuesExistCustomer.size(); i++) {
                    				XRulesExistcustDataM xRulesExistingCustomerDataM = (XRulesExistcustDataM) xRuesExistCustomer.get(i);

                    		%>
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
									    <td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerDataM.getContractNo())%></td>
										<td class="jobopening2" width="5%"><%=ORIGDisplayFormatUtil.displayHTML((String) XRulesConstant.hCustomerType.get(xRulesExistingCustomerDataM.getCustomerType()))%></td>
										<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.datetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(ORIGDisplayFormatUtil
						                                    .parseDate(xRulesExistingCustomerDataM.getBookingDate())))%></td>
										<td class="jobopening2" width="10%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerDataM.getOriginalFinaceAmount())%></td>
										<td class="jobopening2" width="10%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerDataM.getNetFinanceAmount())%></td>
										<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerDataM.getContractStatus())%></td>
										<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerDataM.getCollectionStatus())%></td>
										<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayInteger(xRulesExistingCustomerDataM.getOverDuePeriod())%></td>
										<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayInteger(xRulesExistingCustomerDataM.getPaidPeriod())%></td>
										<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayInteger(xRulesExistingCustomerDataM.getUnPaidPeriod())%></td>
										<td class="jobopening2" width="10%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerDataM.getInstallment())%></td>
									</tr>
								</table>
							</td></tr>
						<%}%>
							<tr><td class="TableHeader">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
										<td class="Bigtodotext3" align="right">Total(A:Active)</td>
										<td class="Bigtodotext3" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(totalOriginalFinanceAmt)%></td>
										<td class="Bigtodotext3" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(totalNetFinanceAmt)%></td>
										<td class="Bigtodotext3" colspan="5" align="right"></td>
										<td class="Bigtodotext3" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(totalInstallment)%></td>
									</tr>
								</table>
							</td></tr>
						<%} //customer booked >0 %>
						</table>
						</td></tr>
						
						<tr><td><br>
						<hr>
						<br>
						</td></tr>
						
						<tr class="sidebar10"> <td align="center">	 
							<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0" bgcolor="#FFFFFF">
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" align="center"><B><%=MessageResourceUtil.getTextDescription(request, "INPROGRESS")%>  Surname</B></td>
										</tr>
									</table>
								</td></tr>
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" width="14%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
											<td class="Bigtodotext3" width="150" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "NAME")%></td>					
											<TD class="Bigtodotext3" width="15%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE")%></TD>
											<TD class="Bigtodotext3" width="15%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "COBORROWER_FLAG")%></TD>					
											<TD class="Bigtodotext3" width="15%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_DATE")%></TD>
											<TD class="Bigtodotext3" width="20%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "FINANCE_AMT_EXIST")%></TD>
											<TD class="Bigtodotext3" width="20%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "EXIST_CUST_INSTALLMENT")%></TD>	
											<TD class="Bigtodotext3" width="15%" align="center" nowrap><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></TD>
										</tr>
									</table>
								</td></tr>
								<%for (int i = 0; i < xRuesExistCustomerInprogressSurname.size(); i++) {
					                XRulesExistcustInprogressDataM xRulesExistingCustomerInprogress = (XRulesExistcustInprogressDataM) xRuesExistCustomerInprogressSurname.get(i);
					                String appStatus = xRulesExistingCustomerInprogress.getApplicationStatus();                
					                    appStatus =oricXrulesUtil.getExistingApplicaitonInprogressStatus(appStatus); 
					                String coBorrowerFlag="";
					                String customerType="";
					                if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(xRulesExistingCustomerInprogress.getCustomerType())){
					               		 if(OrigConstant.COBORROWER_FLAG_ACTIVE.equals(xRulesExistingCustomerInprogress.getCoBorrowerFlag())    ){
					                  		coBorrowerFlag="Active";
					            		   customerType="Co-Borrower";
					               		 }else if(OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(xRulesExistingCustomerInprogress.getCoBorrowerFlag())    ){
					                  		coBorrowerFlag="Inactive ";
					                  		customerType="Co-Borrower";
					                	} else{
					                	 customerType=(String) XRulesConstant.hCustomerType.get(xRulesExistingCustomerInprogress
					                                        .getCustomerType());
					                	} 
					                }  else{
					                customerType=(String) XRulesConstant.hCustomerType.get(xRulesExistingCustomerInprogress
					                                        .getCustomerType());
					                }      
					                %>
								<tr><td align="center" class="gumaygrey2">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
										    <td class="jobopening2" width="14%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerInprogress.getApplicationNo())%></td>
											<td class="jobopening2" width="150"><%=ORIGDisplayFormatUtil.displayHTML( origCacheUtil.getORIGMasterDisplayNameDataM("Title", xRulesExistingCustomerInprogress.getTitleCode()))%> 
														<%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerInprogress.getFirstName())%> <%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerInprogress.getLastName())%></td>
											<TD class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(customerType)%></TD>
											<TD class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML( coBorrowerFlag)%></TD>                                        
											<TD class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.datetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(ORIGDisplayFormatUtil
							                                .parseDate(xRulesExistingCustomerInprogress.getApplicationDate())))%></TD>
											<TD class="jobopening2" width="20%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerInprogress.getFinanceAmt())%></TD>
											<TD class="jobopening2" width="20%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerInprogress.getInstallment())%></TD>
											<TD class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(appStatus)%></TD>
										</tr>
									</table>
								</td></tr>
								<%}%>
							</table>
						</td></tr>
						
						<tr><td><br>
						<hr>
						<br>
						</td></tr>
						
						<tr class="sidebar10"> <td align="center">	 
							<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0" bgcolor="#FFFFFF">
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" align="center"><B>Booked  Surname</B></td>
										</tr>
									</table>
								</td></tr>
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" nowrap width="10%"><%=MessageResourceUtil.getTextDescription(request, "CONTRACT_NO")%></td>
											<td class="Bigtodotext3" nowrap width="10%"><%=MessageResourceUtil.getTextDescription(request, "NAME")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "BOOKING_DATE")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "ORIGINAL_FINANCE_AMT")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "NET_FINANCE_AMT")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "CONTRACT_STATUS")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "COLLECTION_STATUS")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "OVERDUE_PERIOD")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "PAID_PERIOD")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "UNPAID_PERIOD")%></td>
											<td class="Bigtodotext3" nowrap width="15%"><%=MessageResourceUtil.getTextDescription(request, "EXIST_CUST_INSTALLMENT")%></td>
										</tr>
									</table>
								</td></tr>
								<%if (xRuesExistCustomerSurname.size() > 0) {
					
					                for (int i = 0; i < xRuesExistCustomerSurname.size(); i++) {
					                    XRulesExistcustDataM xRulesExistingCustomerDataM = (XRulesExistcustDataM) xRuesExistCustomerSurname.get(i);
					
					                    %>
								<tr><td align="center" class="gumaygrey2">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
										    <td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerDataM.getContractNo())%></td>
											<td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayHTML(origCacheUtil.getORIGMasterDisplayNameDataM("Title", xRulesExistingCustomerDataM.getTitleCode()))%> 
												<%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerDataM.getFirstName())%> <%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerDataM.getLastName())%></td>
											<td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayHTML((String) XRulesConstant.hCustomerType.get(xRulesExistingCustomerDataM.getCustomerType()))%></td>
											<td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.datetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(ORIGDisplayFormatUtil
							                                    .parseDate(xRulesExistingCustomerDataM.getBookingDate())))%></td>
											<td class="jobopening2" width="2%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerDataM.getOriginalFinaceAmount())%></td>
											<td class="jobopening2" width="2%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerDataM.getNetFinanceAmount())%></td>
											<td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerDataM.getContractStatus())%></td>
											<td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesExistingCustomerDataM.getCollectionStatus())%></td>
											<td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayInteger(xRulesExistingCustomerDataM.getOverDuePeriod())%></td>
											<td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayInteger(xRulesExistingCustomerDataM.getPaidPeriod())%></td>
											<td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayInteger(xRulesExistingCustomerDataM.getUnPaidPeriod())%></td>
											<td class="jobopening2" width="2%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(xRulesExistingCustomerDataM.getInstallment())%></td>
										</tr>
									</table>
								</td></tr>
								<%}%>
							<%-- <tr>
								<td colspan="3" align="right" bgcolor="#FFFFFF"><strong>Total(A:Active)</strong>
								</td>
								<td align="right" bgcolor="#FFFFFF"><strong><%=ORIGDisplayFormatUtil.displayCommaNumber(totalOriginalFinanceAmt)%></strong>&nbsp;</td>
								<td align="right" bgcolor="#FFFFFF"><strong><%=ORIGDisplayFormatUtil.displayCommaNumber(totalNetFinanceAmt)%></strong>&nbsp;</td>
								<td colspan="5" align="right" bgcolor="#FFFFFF">&nbsp;</td>
								<td bgcolor="#FFFFFF" align="right">&nbsp;<strong><%=ORIGDisplayFormatUtil.displayCommaNumber(totalInstallment)%></strong></td>
							</tr>--%>
							<%} //customer booked >0 %>
						</table>
						</td></tr>
					</table>
						</td>
					</tr>
				</table>
			</td></tr>
		</table>
	</td></tr>
</table>


<%//set current screen to main Form
            com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager = (com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true)
                    .getAttribute("screenFlowManager");
            screenFlowManager.setCurrentScreen("MAIN_APPFORM");

        %>
