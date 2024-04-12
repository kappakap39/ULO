/*
 * Created on Jan 5, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.model.FeeInformationDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveFeeInformationPopupWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SaveFeeInformationPopupWebAction.class);
    
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
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        try{
	        ORIGUtility utility = new ORIGUtility();
	        
	        FeeInformationDataM tempFeeInformationDataM = (FeeInformationDataM) getRequest().getSession().getAttribute("FEE_INFO_POPUP_DATA");
	        if (tempFeeInformationDataM==null){
	            tempFeeInformationDataM = new FeeInformationDataM();
	        }
	    	FeeInformationDataM feeInformationDataM = new FeeInformationDataM();
	        String fee_type = getRequest().getParameter("fee_type");
	        String fee_amount = getRequest().getParameter("fee_amount");
	        String fee_payment_option = getRequest().getParameter("fee_payment_option");
	
	    	CollateralDataM collateralDataM = (CollateralDataM)getRequest().getSession().getAttribute("COLLATERAL_POPUP_DATA");
	    	if (collateralDataM==null){//no collateralDataM
	    		collateralDataM = new CollateralDataM();
	    		Vector feeInfoVect = new Vector();
	    		feeInfoVect.add(feeInformationDataM);
	    		collateralDataM.setFeeInformationVect(feeInfoVect);
	    	}else if (collateralDataM!=null && collateralDataM.getFeeInformationVect()!=null && collateralDataM.getFeeInformationVect().size()>0 && tempFeeInformationDataM.getSeq()>=0){//edit
			    feeInformationDataM = (FeeInformationDataM)collateralDataM.getFeeInformationVect().get(tempFeeInformationDataM.getSeq());
			}else if (collateralDataM!=null && collateralDataM.getFeeInformationVect()!=null && collateralDataM.getFeeInformationVect().size()>0 && tempFeeInformationDataM.getSeq()==-1){//add
	    		Vector feeInfoVect = collateralDataM.getFeeInformationVect();
	    		feeInformationDataM.setSeq(collateralDataM.getFeeInformationVect().size());
	    		feeInfoVect.add(feeInformationDataM);
			}else{//first add
	    		Vector feeInfoVect = new Vector();
	    		feeInfoVect.add(feeInformationDataM);
	    		collateralDataM.setFeeInformationVect(feeInfoVect);
			}
	    	
	        feeInformationDataM.setFeeType(fee_type);
	        feeInformationDataM.setFeeAmount(utility.stringToBigDecimal(fee_amount));
	        feeInformationDataM.setFeePaymentOption(fee_payment_option);
	    	
	        //Rewrite
	        String tableData = utility.getFeeInformationTable(collateralDataM.getFeeInformationVect(), getRequest());
	        
	        PopulatePopupM populatePopupM = new PopulatePopupM("FeeInfo",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData));
			Vector popMs = new Vector();
			popMs.add(populatePopupM);
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			getRequest().getSession(true).setAttribute("COLLATERAL_POPUP_DATA",collateralDataM);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
        }catch(Exception e){
            logger.error("Error in SaveFeeInformationPopupWebAction.preModelRequest() >> ", e);
        }
        
        return true;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
        // TODO Auto-generated method stub
        return false;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
        // TODO Auto-generated method stub
        return false;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        // TODO Auto-generated method stub
        return null;
    }
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
