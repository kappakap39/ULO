/*
 * Created on Jul 22, 2008
 * Created by Avalant
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.view.webaction;

import java.math.BigDecimal;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.InstallmentDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Avalant
 *
 * Type: SaveStepInstallmentPopupWebaction
 */
public class SaveStepInstallmentPopupWebaction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SaveStepInstallmentPopupWebaction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
   private  int nextActivityType;
    public Event toEvent() {
         
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
    
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
   
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        boolean complete=true;
        String stepInstllmentAction=getRequest().getParameter("stepInstallmentAction");
        try{
	        ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
	        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	        String userRole = (String) userM.getRoles().elementAt(0);
	        ORIGUtility utility = new ORIGUtility();
	        String dateFormat = "dd/mm/yyyy";
	        
	       // ApplicationDataM applicationDataM = formHandler.getAppForm();
	        //Vector loanVect = applicationDataM.getLoanVect();
	    	LoanDataM loanDataM= (LoanDataM) getRequest().getSession().getAttribute("POPUP_DATA");
	    	//if(loanVect!=null&&loanVect.size() > 0){
	    	//	loanDataM = (LoanDataM) loanVect.elementAt(0);
	    	//}	        
	        if(loanDataM == null){
	            loanDataM = new LoanDataM();	            
	        }
	        logger.debug("action="+stepInstllmentAction);
	        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	        BigDecimal vat = cacheUtil.getVatByCode(loanDataM.getVAT());
	        if(vat.compareTo(new BigDecimal(0)) == 0){
				//vat = utility.stringToBigDecimal(cacheUtil.getORIGMasterDisplayNameDataM("VATRate", OrigConstant.HAVE_VAT));				 
			} 
	        BigDecimal vatTmp = (vat.divide((new BigDecimal(100)),2,0)).add(new BigDecimal(1));
         if("Add".equals(stepInstllmentAction)){             
             complete=addStepInsllment(loanDataM,vatTmp);
               this.setNextActivityType(FrontController.ACTION);
             this.setNextActionParameter("page=STEP_INSTALLMENT_POPUP");
        }else if ("Remove".equals(stepInstllmentAction)) {
            complete=removeStepInsllment(loanDataM,vatTmp);
            this.setNextActivityType(FrontController.ACTION);
            this.setNextActionParameter("page=STEP_INSTALLMENT_POPUP");            
        }else if ("Save".equals(stepInstllmentAction)){             
            complete=saveStepInsllment(loanDataM,vatTmp);            
            this.setNextActivityType(FrontController.FORWARD); 
            this.setNextActionParameter("orig/appform/filterMainScreen.jsp");
            
        }
        }catch (Exception e) {
           logger.error("Error:",e); 
           return false;
        }
         
        return complete;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
     public int getNextActivityType() {  
         return nextActivityType;
     }
   private boolean addStepInsllment(LoanDataM loanDataM,BigDecimal vat){
      
       Vector vStepInstallment=(Vector)getRequest().getSession().getAttribute("STEP_INSTALLMENT");
           //loanDataM.getStepInstallmentVect();
       if(vStepInstallment==null){
         vStepInstallment=new Vector();
        // loanDataM.setStepInstallmentVect(vStepInstallment);
       }       
      //vat = utility.stringToBigDecimal(cacheUtil.getORIGMasterDisplayNameDataM("VATRate", OrigConstant.HAVE_VAT));
      logger.debug("Add Step Installment "); 
      logger.debug(" VstepInstallment Size "+vStepInstallment.size()); 
      String  strTermDuration=getRequest().getParameter("term_duration");
      String  strInstallment=getRequest().getParameter("installment");
      double installment=ORIGDisplayFormatUtil.replaceComma(strInstallment);
      int terrmDuration=ORIGDisplayFormatUtil.StringToInt(strTermDuration);
      //get Last Installment
      InstallmentDataM prmInstallMentM=new InstallmentDataM();
      if(vStepInstallment.size()==0){
          prmInstallMentM.setSeq(1);
          prmInstallMentM.setInstallmentForm(1);
          prmInstallMentM.setInstallmentTo(terrmDuration);
          //prmInstallMentM.setTermDuration(iTerrmDuration);
          //prmInstallMentM.setInstallmentAmount( new BigDecimal(dInstallment ));
         // prmInstallMentM.setAmount( prmInstallMentM.getInstallmentAmount().divide(vat,2,BigDecimal.ROUND_HALF_UP));
          //prmInstallMentM.setInstallmentVat( prmInstallMentM.getInstallmentAmount().subtract(prmInstallMentM.getAmount()));
          //prmInstallMentM.setInstallmentTotal( prmInstallMentM.getInstallmentAmount().multiply(new BigDecimal(iTerrmDuration)));
          
      }else{             
      InstallmentDataM oldInstallMent=(InstallmentDataM)vStepInstallment.get(vStepInstallment.size()-1);    
      prmInstallMentM.setSeq(oldInstallMent.getSeq()+1);
      prmInstallMentM.setInstallmentForm(oldInstallMent.getInstallmentTo()+1);
      prmInstallMentM.setInstallmentTo(oldInstallMent.getInstallmentTo()+terrmDuration);      
      }
      prmInstallMentM.setTermDuration(terrmDuration);
      prmInstallMentM.setInstallmentAmount( new BigDecimal(installment ));
      prmInstallMentM.setAmount( prmInstallMentM.getInstallmentAmount().divide(vat,2,BigDecimal.ROUND_HALF_UP));
      prmInstallMentM.setInstallmentVat( prmInstallMentM.getInstallmentAmount().subtract(prmInstallMentM.getAmount()));
      prmInstallMentM.setInstallmentTotal( prmInstallMentM.getInstallmentAmount().multiply(new BigDecimal(terrmDuration)));
      vStepInstallment.add(prmInstallMentM);  
      logger.debug(" VstepInstallment Size After "+vStepInstallment.size());       
       getRequest().getSession().setAttribute("STEP_INSTALLMENT",vStepInstallment);     
        return true;
   }
   private boolean removeStepInsllment(LoanDataM loanDataM,BigDecimal vat){
      // Vector vStepInstallment=loanDataM.getStepInstallmentVect();
       Vector vStepInstallment=(Vector)getRequest().getSession().getAttribute("STEP_INSTALLMENT");
       if(vStepInstallment==null){
         vStepInstallment=new Vector();
         loanDataM.setStepInstallmentVect(vStepInstallment);
       }
       for(int i=0;i<vStepInstallment.size();i++){
          InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vStepInstallment.get(i); 
          if( OrigConstant.ORIG_FLAG_Y.equals(getRequest().getParameter("chk_"+prmInstallmentDataM.getSeq()))){
              vStepInstallment.remove(i);
              i--;
          }
       }       
       //remove Recrod              
       // new  Seq;
       int totalTerrmDuration=0;
       for(int i=0;i<vStepInstallment.size();i++){
           InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vStepInstallment.get(i);
           prmInstallmentDataM.setInstallmentForm(totalTerrmDuration+1);
           prmInstallmentDataM.setInstallmentTo(totalTerrmDuration+prmInstallmentDataM.getTermDuration());
           totalTerrmDuration+=prmInstallmentDataM.getTermDuration();
           prmInstallmentDataM.setSeq(i+1);
       }       
       getRequest().getSession().setAttribute("STEP_INSTALLMENT",vStepInstallment);
       return true;
   }
   private boolean saveStepInsllment(LoanDataM loanDataM,BigDecimal vat){
      // Vector vStepInstallment=loanDataM.getStepInstallmentVect();
       Vector vStepInstallment=(Vector)getRequest().getSession().getAttribute("STEP_INSTALLMENT");       
       int totalTermDuration=0;
       BigDecimal sumInstallmentTotal=new BigDecimal(0);
       boolean saveComplete=true;
       //BigDecimal sumTotalInstallment=new BigDecimal(0);
       for(int i=0;i<vStepInstallment.size();i++){
           InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vStepInstallment.get(i);
           prmInstallmentDataM.setSeq(i+1);
           String  strTearmDuration=getRequest().getParameter("term_duration_"+(i+1));
           String  strInstallment=getRequest().getParameter("installment_amount_"+(i+1));
           int termDuration=ORIGDisplayFormatUtil.StringToInt(strTearmDuration);
           BigDecimal installment=ORIGDisplayFormatUtil.replaceCommaForBigDecimal(strInstallment); 
           prmInstallmentDataM.setInstallmentTo(totalTermDuration+termDuration);
           prmInstallmentDataM.setTermDuration(termDuration);
           prmInstallmentDataM.setInstallmentAmount(installment);           
           prmInstallmentDataM.setAmount( installment.divide(vat ,2,BigDecimal.ROUND_HALF_UP));
           prmInstallmentDataM.setInstallmentVat(installment.subtract(prmInstallmentDataM.getAmount()) );
           totalTermDuration+=termDuration;
           BigDecimal InstallmentTotal= installment.multiply(new BigDecimal(termDuration));
           sumInstallmentTotal=sumInstallmentTotal.add(InstallmentTotal);
           prmInstallmentDataM.setInstallmentTotal(InstallmentTotal);
       }              
       if(vStepInstallment==null){
         vStepInstallment=new Vector();
        
       }   
       ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);     
       Vector vErrors = formHandler.getFormErrors();
       Vector vErrorFields = formHandler.getErrorFields();
       Vector vNotErrorFields = formHandler.getNotErrorFields();
       if (vErrors == null) {
           vErrors = new Vector();
       }
       String errorMsg = null;
       //loanDataM.setInstallment1()
       if(loanDataM.getInstallment1().compareTo(new BigDecimal(totalTermDuration))!=0){
           errorMsg = "Step installment Term not equal Term";
           vErrors.add(errorMsg);    
           saveComplete=false;
       }
       //loanDataM.setTotalOfHairPurchaseAmt()
       if(loanDataM.getTotalOfHairPurchaseAmt().compareTo(sumInstallmentTotal)!=0){
           errorMsg = "Total Hire Purchase Amount  not equal Gran Total Instalment Amount.";
           vErrors.add(errorMsg);           
           saveComplete=false;
       }
       
       loanDataM.setStepInstallmentVect(vStepInstallment);
       ORIGUtility utility = new ORIGUtility();
      //
       String tableData = utility.getStepInstallmentTable(vStepInstallment, getRequest());       
       PopulatePopupM populatePopupM = new PopulatePopupM("idStepInstallment",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData));
		Vector popMs = new Vector();
		popMs.add(populatePopupM);
		getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
       return  saveComplete;
   }
   
    /**
     * @param nextActivityType The nextActivityType to set.
     */
    public void setNextActivityType(int nextActivityType) {
        this.nextActivityType = nextActivityType;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
