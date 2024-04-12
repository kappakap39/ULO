/*
 * Created on Nov 6, 2007
 * Created by Prawit Limwattanachai
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

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.CorperatedDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SaveCorperatedWebAction
 */
public class SaveCorperatedWebAction extends WebActionHelper implements
		WebAction {
	Logger logger = Logger.getLogger(SaveCorperatedWebAction.class);

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		 
		return null;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		 
		return false;
	}

	public boolean processEventResponse(EventResponse response) {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        
        try{
        	
	        String seq = getRequest().getParameter("seq");
	        String yearFromLink = getRequest().getParameter("yearFromLink");
	        String year = getRequest().getParameter("year");
	        
	        //Asset
    		String asstCurrentAssets = getRequest().getParameter("asstCurrentAssets");
    		String asstCashInHandAtBank = getRequest().getParameter("asstCashInHandAtBank");
    		String asstAccountReceivableNet = getRequest().getParameter("asstAccountReceivableNet");
    		String asstNoteReceivable = getRequest().getParameter("asstNoteReceivable");
    		String asstAccruedIncome = getRequest().getParameter("asstAccruedIncome");
    		String asstInventories = getRequest().getParameter("asstInventories");
    		String asstOtherCurAssets = getRequest().getParameter("asstOtherCurAssets");
    		String asstTotalCurAssets = getRequest().getParameter("asstTotalCurAssets");
    		String asstDeposit = getRequest().getParameter("asstDeposit");
    		String asstPropertyPlantEquipment = getRequest().getParameter("asstPropertyPlantEquipment");
    		String asstOtherAssets = getRequest().getParameter("asstOtherAssets");
    		String asstTotalAssets = getRequest().getParameter("asstTotalAssets");
    		
    		logger.debug("//////////////////////////////////////");
    		logger.debug("asstCurrentAssets == "+asstCurrentAssets);
    		logger.debug("asstCashInHandAtBank == "+asstCashInHandAtBank);
    		logger.debug("asstAccountReceivableNet == "+asstAccountReceivableNet);
    		logger.debug("asstNoteReceivable == "+asstNoteReceivable);
    		logger.debug("//////////////////////////////////////");
    	
    	//Liabilities
    		String lbCurrentLb = getRequest().getParameter("lbCurrentLb");
    		String lbBankOverdraftAndLoan = getRequest().getParameter("lbBankOverdraftAndLoan");
    		String lbAccountPayable = getRequest().getParameter("lbAccountPayable");
    		String lbCurrentPortionOfLongTermDebt = getRequest().getParameter("lbCurrentPortionOfLongTermDebt");
    		String lbNotesPayable = getRequest().getParameter("lbNotesPayable");
    		String lbLoanFromFinanInstitution = getRequest().getParameter("lbLoanFromFinanInstitution");
    		String lbAccruedExpense = getRequest().getParameter("lbAccruedExpense");
    		String lbOtherCurLb = getRequest().getParameter("lbOtherCurLb");
    		String lbTotalCurLb = getRequest().getParameter("lbTotalCurLb");
    		String lbLongTermDebt = getRequest().getParameter("lbLongTermDebt");
    		String lbPayHirePurchase = getRequest().getParameter("lbPayHirePurchase");
    		String lbTotalLb = getRequest().getParameter("lbTotalLb");
    		    	
    	//Shareholder Equity
    		String shdEqShareCapital = getRequest().getParameter("shdEqShareCapital");
    		String shdEqIssuePaidUpCapital = getRequest().getParameter("shdEqIssuePaidUpCapital");
    		String shdEqPremium = getRequest().getParameter("shdEqPremium");
    		String shdEqRetainEarning = getRequest().getParameter("shdEqRetainEarning");
    		String shdEqLegalReserve = getRequest().getParameter("shdEqLegalReserve");
    		String shdEqUnappropriated = getRequest().getParameter("shdEqUnappropriated");
    		String shdEqTotalShareHdEqity = getRequest().getParameter("shdEqTotalShareHdEqity");
    	
    	//Income Statement
    		String IncStmtRevenue = getRequest().getParameter("IncStmtRevenue");
    		String IncStmtSales = getRequest().getParameter("IncStmtSales");
    		String IncStmtOtherIncome = getRequest().getParameter("IncStmtOtherIncome");
    		String IncStmtTotalRevenue = getRequest().getParameter("IncStmtTotalRevenue");
    		String IncStmtExpense = getRequest().getParameter("IncStmtExpense");
    		String IncStmtCostofSale = getRequest().getParameter("IncStmtCostofSale");
    		String IncStmtSAExpense = getRequest().getParameter("IncStmtSAExpense");
    		String IncStmtShareNetLossSubsidiaries = getRequest().getParameter("IncStmtShareNetLossSubsidiaries");
    		String IncStmtLossGoodsDeterioration = getRequest().getParameter("IncStmtLossGoodsDeterioration");
    		String IncStmtTotalExpense = getRequest().getParameter("IncStmtTotalExpense");
    		String IncStmtEarnBfInterestAndTax = getRequest().getParameter("IncStmtEarnBfInterestAndTax");
    		String IncStmtInterestExpense = getRequest().getParameter("IncStmtInterestExpense");
    		String IncStmtEarnBfTax = getRequest().getParameter("IncStmtEarnBfTax");
    		String IncStmtIncTax = getRequest().getParameter("IncStmtIncTax");
    		String IncStmtNetIncome = getRequest().getParameter("IncStmtNetIncome");
    	
    	//Ratios
    		String ratCurrentRatio = getRequest().getParameter("ratCurrentRatio");
    		String ratQuickRatio = getRequest().getParameter("ratQuickRatio");
    		String ratDebtToEquity = getRequest().getParameter("ratDebtToEquity");
    		String ratGrossProfitMargin = getRequest().getParameter("ratGrossProfitMargin");
    		String ratNetProfitSale = getRequest().getParameter("ratNetProfitSale");
    		
    		Vector vErrors = formHandler.getFormErrors();  
    		
    	//Validate all total value & cost of sale
//    		BigDecimal asstTotalAssetsBigD = utility.stringToBigDecimal(asstTotalAssets);    		
//    		BigDecimal lbTotalLbBigD = utility.stringToBigDecimal(lbTotalLb);
//    		BigDecimal shdEqTotalShareHdEqityBigD = utility.stringToBigDecimal(shdEqTotalShareHdEqity);
//    		BigDecimal IncStmtCostofSaleBigD = utility.stringToBigDecimal(IncStmtCostofSale);
//    		boolean isTotalAccurate = true;
//    		
//    		if((asstTotalAssetsBigD.compareTo(new BigDecimal(0))== 0) || (asstTotalAssetsBigD.compareTo(new BigDecimal(0))== -1)){
//    			vErrors.add(ErrorUtil.getShortErrorMessage(getRequest(),"TOTAL_ASSET_MUST_MORE_THAN_ZERO"));
//    			isTotalAccurate = false;
//    		}
//    		if( (lbTotalLbBigD.compareTo(new BigDecimal(0))== 0) || (lbTotalLbBigD.compareTo(new BigDecimal(0))== -1)){
//    			vErrors.add(ErrorUtil.getShortErrorMessage(getRequest(),"TOTAL_LIABILITIES_MUST_MORE_THAN_ZERO"));
//    			isTotalAccurate = false;
//    		}
//    		if( (shdEqTotalShareHdEqityBigD.compareTo(new BigDecimal(0))== 0) || (shdEqTotalShareHdEqityBigD.compareTo(new BigDecimal(0))== -1)){
//    			vErrors.add(ErrorUtil.getShortErrorMessage(getRequest(),"TOTAL_SHARE_EQ_MUST_MORE_THAN_ZERO"));
//    			isTotalAccurate = false;
//    		}
//    		if( (IncStmtCostofSaleBigD.compareTo(new BigDecimal(0))== 0) || (IncStmtCostofSaleBigD.compareTo(new BigDecimal(0))== -1)){
//    			vErrors.add(ErrorUtil.getShortErrorMessage(getRequest(),"COST_OF_SALES_MUST_MORE_THAN_ZERO"));
//    			isTotalAccurate = false;
//    		}
//    		if(year==null || "".equals(year)){
//    			vErrors.add(ErrorUtil.getShortErrorMessage(getRequest(),"YEAR_IS_REQUIRED"));
//    			isTotalAccurate = false;
//    		}
	        
	        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
	        
	        logger.debug("corperated seq = "+seq);
	        logger.debug("Personal Type = "+personalType);	        
	    
	        PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
	    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
	    	}else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
	    		personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	    	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
	            personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	        }
	        
	    	Vector corperatedVect = personalInfoDataM.getCorperatedVect();
	    	CorperatedDataM corperatedDataM;
	    	
	    	if(Integer.parseInt(seq) == 0){// Add new corperated
	    		corperatedDataM = new CorperatedDataM();
	    		
	    		corperatedVect.add(corperatedDataM);	    		
	    		
	        }else{//Edit change name
	        	
	        	//corperatedDataM = utility.getCorperatedByYear(personalInfoDataM, yearFromLink);
	        	corperatedDataM = utility.getCorperatedBySeq(corperatedVect, Integer.parseInt(seq));
	        }
	    	
	    	
	    	
	/*        if(!ORIGUtility.isEmptyString(change_date)){
	            changeNameDataM.setChangeDate(ORIGDisplayFormatUtil.StringToDate(change_date, "dd/mm/yyyy"));
	        }else{
	            changeNameDataM.setChangeDate(null);
	        } */
	    	
	    	
	    	
	        //Assets
	        corperatedDataM.setAsstCurrentAssets(utility.stringToBigDecimal(asstCurrentAssets));
	        corperatedDataM.setAsstCashInHandAtBank(utility.stringToBigDecimal(asstCashInHandAtBank));
	        corperatedDataM.setAsstAccountReceivableNet(utility.stringToBigDecimal(asstAccountReceivableNet));
	        corperatedDataM.setAsstNoteReceivable(utility.stringToBigDecimal(asstNoteReceivable));
	        corperatedDataM.setAsstAccruedIncome(utility.stringToBigDecimal(asstAccruedIncome));
	        corperatedDataM.setAsstInventories(utility.stringToBigDecimal(asstInventories));
	        corperatedDataM.setAsstOtherCurAssets(utility.stringToBigDecimal(asstOtherCurAssets));
	        corperatedDataM.setAsstTotalCurAssets(utility.stringToBigDecimal(asstTotalCurAssets));
	        corperatedDataM.setAsstDeposit(utility.stringToBigDecimal(asstDeposit));
	        corperatedDataM.setAsstPropertyPlantEquipment(utility.stringToBigDecimal(asstPropertyPlantEquipment));
	        corperatedDataM.setAsstOtherAssets(utility.stringToBigDecimal(asstOtherAssets));
	        corperatedDataM.setAsstTotalAssets(utility.stringToBigDecimal(asstTotalAssets));
	        	                
	        //Liabilities
	        corperatedDataM.setLbCurrentLb(utility.stringToBigDecimal(lbCurrentLb));
	        corperatedDataM.setLbBankOverdraftAndLoan(utility.stringToBigDecimal(lbBankOverdraftAndLoan));
	        corperatedDataM.setLbAccountPayable(utility.stringToBigDecimal(lbAccountPayable));
	        corperatedDataM.setLbCurrentPortionOfLongTermDebt(utility.stringToBigDecimal(lbCurrentPortionOfLongTermDebt));
	        corperatedDataM.setLbNotesPayable(utility.stringToBigDecimal(lbNotesPayable));
	        corperatedDataM.setLbLoanFromFinanInstitution(utility.stringToBigDecimal(lbLoanFromFinanInstitution));
	        corperatedDataM.setLbAccruedExpense(utility.stringToBigDecimal(lbAccruedExpense));
	        corperatedDataM.setLbOtherCurLb(utility.stringToBigDecimal(lbOtherCurLb));
	        corperatedDataM.setLbTotalCurLb(utility.stringToBigDecimal(lbTotalCurLb));
	        corperatedDataM.setLbLongTermDebt(utility.stringToBigDecimal(lbLongTermDebt));
	        corperatedDataM.setLbPayHirePurchase(utility.stringToBigDecimal(lbPayHirePurchase));
	        corperatedDataM.setLbTotalLb(utility.stringToBigDecimal(lbTotalLb));	        
	        	        
	        //Shareholder Equity
	        corperatedDataM.setShdEqShareCapital(utility.stringToBigDecimal(shdEqShareCapital));   
	        corperatedDataM.setShdEqIssuePaidUpCapital(utility.stringToBigDecimal(shdEqIssuePaidUpCapital));
	        corperatedDataM.setShdEqPremium(utility.stringToBigDecimal(shdEqPremium));
	        corperatedDataM.setShdEqRetainEarning(utility.stringToBigDecimal(shdEqRetainEarning));
	        corperatedDataM.setShdEqLegalReserve(utility.stringToBigDecimal(shdEqLegalReserve));
	        corperatedDataM.setShdEqUnappropriated(utility.stringToBigDecimal(shdEqUnappropriated));
	        corperatedDataM.setShdEqTotalShareHdEqity(utility.stringToBigDecimal(shdEqTotalShareHdEqity));     
	        	        
	        //Income Statement
	        corperatedDataM.setIncStmtRevenue(utility.stringToBigDecimal(IncStmtRevenue));
	        corperatedDataM.setIncStmtSales(utility.stringToBigDecimal(IncStmtSales));
	        corperatedDataM.setIncStmtOtherIncome(utility.stringToBigDecimal(IncStmtOtherIncome));
	        corperatedDataM.setIncStmtTotalRevenue(utility.stringToBigDecimal(IncStmtTotalRevenue));
	        corperatedDataM.setIncStmtExpense(utility.stringToBigDecimal(IncStmtExpense));
	        corperatedDataM.setIncStmtCostofSale(utility.stringToBigDecimal(IncStmtCostofSale));
	        corperatedDataM.setIncStmtSAExpense(utility.stringToBigDecimal(IncStmtSAExpense));
	        corperatedDataM.setIncStmtShareNetLossSubsidiaries(utility.stringToBigDecimal(IncStmtShareNetLossSubsidiaries));
	        corperatedDataM.setIncStmtLossGoodsDeterioration(utility.stringToBigDecimal(IncStmtLossGoodsDeterioration));
	        corperatedDataM.setIncStmtTotalExpense(utility.stringToBigDecimal(IncStmtTotalExpense));
	        corperatedDataM.setIncStmtEarnBfInterestAndTax(utility.stringToBigDecimal(IncStmtEarnBfInterestAndTax));
	        corperatedDataM.setIncStmtInterestExpense(utility.stringToBigDecimal(IncStmtInterestExpense));
	        corperatedDataM.setIncStmtEarnBfTax(utility.stringToBigDecimal(IncStmtEarnBfTax));
	        corperatedDataM.setIncStmtIncTax(utility.stringToBigDecimal(IncStmtIncTax));
	        corperatedDataM.setIncStmtNetIncome(utility.stringToBigDecimal(IncStmtNetIncome));
	        	        
	        //Ratios
	        corperatedDataM.setRatCurrentRatio(utility.stringToBigDecimal(ratCurrentRatio));
	        corperatedDataM.setRatQuickRatio(utility.stringToBigDecimal(ratQuickRatio));
	        corperatedDataM.setRatDebtToEquity(utility.stringToBigDecimal(ratDebtToEquity));
	        corperatedDataM.setRatGrossProfitMargin(utility.stringToBigDecimal(ratGrossProfitMargin));
	        corperatedDataM.setRatNetProfitSale(utility.stringToBigDecimal(ratNetProfitSale)); 	        	        
	        
	        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	        if(ORIGUtility.isEmptyString(corperatedDataM.getCreateBy())){
	        	corperatedDataM.setCreateBy(userM.getUserName());
	        }else{
	        	corperatedDataM.setUpdateBy(userM.getUserName());
	        }
	        getRequest().getSession().setAttribute("POPUP_DATA", corperatedDataM);
	        
	        //Validate data
//	        if(!(isTotalAccurate)){
//	        	if(Integer.parseInt(seq) == 0){
//	        		personalInfoDataM.setCorperatedVect(personalInfoDataM.getCorperatedTmpVect());
//	        	}
//    			return false;
//    		}
	        
	        boolean hvCorYear = utility.haveCorperatedYear(personalInfoDataM, year);	    	
	    	
	    	if(Integer.parseInt(seq) == 0){// Add new corperated
	    		logger.debug("PoNg>>>year = "+year);
	    		logger.debug("hvCorYear = "+hvCorYear);	    		
	    		if(hvCorYear){
		    		vErrors.add(ErrorUtil.getShortErrorMessage(getRequest(),"PLEASE_INPUT_OTHER_YEAR"));
		    		personalInfoDataM.setCorperatedVect(personalInfoDataM.getCorperatedTmpVect());
	    			return false;
	    		}	   
	    		corperatedDataM.setSeq(corperatedVect.size()+1);
	    	}else{
	    		logger.debug("///pongchkyear///");
	        	logger.debug("PoNg>>>year = "+year);
	        	logger.debug("PoNg>>>yearFromLink = "+yearFromLink);
	        	if( !(yearFromLink.equals(year)) ){
	        		if(hvCorYear){
			    		vErrors.add(ErrorUtil.getShortErrorMessage(getRequest(),"PLEASE_INPUT_OTHER_YEAR"));
			    		//personalInfoDataM.setCorperatedVect(personalInfoDataM.getCorperatedTmpVect());
		    			return false;
		    		}	        		
	        	}
	    	}
	    	 
	    	corperatedDataM.setYear(year);
	    	 //personalInfoDataM.setCorperatedVect(personalInfoDataM.getCorperatedTmpVect());
	        //Rewrite
	        String tableData = utility.getCorperatedTable(corperatedVect, getRequest());
	        
	        PopulatePopupM populatePopupM = new PopulatePopupM("Corperated",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData.toString()));
			Vector popMs = new Vector();
			popMs.add(populatePopupM);
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
        }catch(Exception e){
            logger.error("Error in SaveCorperatedWebAction.preModelRequest() : ", e);
        }
        
        return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
        return FrontController.FORWARD;
    }
    /* (non-Javadoc)
	 * @see com.bara.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return "orig/appform/filterMainScreen.jsp";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
