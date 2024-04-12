/*
 * Created on Jan 5, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.view.webaction;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.model.FeeInformationDataM;
import com.eaf.orig.shared.utility.SerializeUtil;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadFeeInformationPopupWebAction extends WebActionHelper implements WebAction {
	private String nextAction;
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.ACTION;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        String seq = getRequest().getParameter("seq");
        int iSeq = -1;
        if (seq!=null && seq.length()>0){
            iSeq = Integer.parseInt(seq);
        }
    	CollateralDataM collateralDataM = (CollateralDataM)getRequest().getSession().getAttribute("COLLATERAL_POPUP_DATA");
    	if (collateralDataM==null){
    		collateralDataM = new CollateralDataM();
    	}
    	FeeInformationDataM feeInformationDataM = new FeeInformationDataM();
		if (iSeq>=0 && collateralDataM!=null && collateralDataM.getFeeInformationVect()!=null && collateralDataM.getFeeInformationVect().size()>0){
		    feeInformationDataM = (FeeInformationDataM)collateralDataM.getFeeInformationVect().get(iSeq);
		    feeInformationDataM.setSeq(iSeq);
		}else{
		    feeInformationDataM.setSeq(-1);
		}
        getRequest().getSession().setAttribute("FEE_INFO_POPUP_DATA", (FeeInformationDataM) SerializeUtil.clone(feeInformationDataM));
        nextAction = "page=FEE_INFORMATION_POPUP";
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
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return nextAction;
    }
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
