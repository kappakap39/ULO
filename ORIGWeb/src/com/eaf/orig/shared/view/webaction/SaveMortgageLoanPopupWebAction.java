/*
 * Created on Dec 26, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.view.webaction;

import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.DisplayFormatUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.AppraisalDataM;
import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveMortgageLoanPopupWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SaveMortgageLoanPopupWebAction.class);
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
        try{
            ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");
            ORIGUtility utility = new ORIGUtility();
            ORIGForm.setPopupForm(null);
            
            ApplicationDataM applicationDataM = ORIGForm.getAppForm();
            Vector collateralV = applicationDataM.getCollateralVect();
            if(collateralV==null){
                collateralV = new Vector();
                applicationDataM.setCollateralVect(collateralV);
            }
            
            CollateralDataM collateralDataM = (CollateralDataM) getRequest().getSession().getAttribute("COLLATERAL_POPUP_DATA");
            logger.debug("collateralDataM="+collateralDataM);
            
            logger.debug("collateralDataM.getSeq()="+collateralDataM.getSeq());
            logger.debug("collateralV.size()="+collateralV.size());
            if(collateralDataM==null){
                collateralDataM = new CollateralDataM();
            }else if(collateralDataM.getSeq()==0){
                collateralDataM.setSeq(collateralV.size()+1);
                collateralV.add(collateralDataM);
            }else if(collateralDataM.getSeq()>0){
                collateralDataM = (CollateralDataM)collateralV.get(collateralDataM.getSeq()-1);
            }
            
            UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
            if(ORIGUtility.isEmptyString(collateralDataM.getCreateBy())){
                collateralDataM.setCreateBy(userM.getUserName());
	        }else{
	            collateralDataM.setUpdateBy(userM.getUserName());
	        }
            
            //Collateral Information 
            String collateralType = getRequest().getParameter("collateralType");
            String propertyType = getRequest().getParameter("propertyType");
            String accreditedProperty = getRequest().getParameter("accreditedProperty");
            String propertySubType = getRequest().getParameter("propertySubType");
            String withUnderTaking = getRequest().getParameter("withUnderTaking");
            String area = getRequest().getParameter("area");
            String propertyDev = getRequest().getParameter("propertyDev");
            
            collateralDataM.setCollateralType(collateralType);
            collateralDataM.setPropertyType(propertyType);
            collateralDataM.setAccreditedProperty(accreditedProperty);
            collateralDataM.setPropertySubType(propertySubType);
            collateralDataM.setWithUndertaking(withUnderTaking);
            collateralDataM.setArea(utility.stringToBigDecimal(area));
            collateralDataM.setPropertyDevelopers(propertyDev);
            
            //Collateral Address
            String address_type_copy = getRequest().getParameter("address_type_copy");
            String address_no = getRequest().getParameter("address_no");
            String road = getRequest().getParameter("road");
            String soi = getRequest().getParameter("soi");
            String apartment = getRequest().getParameter("apartment");
            String moo = getRequest().getParameter("moo");
            String room = getRequest().getParameter("room");
            String floor = getRequest().getParameter("floor");
            String province = getRequest().getParameter("province");
            String zipcode = getRequest().getParameter("zipcode");
            String tambol = getRequest().getParameter("tambol");
            String amphur = getRequest().getParameter("amphur");
            
            AddressDataM addressM = new AddressDataM();
            addressM.setAddressNo(address_no);
            addressM.setRoad(road);
            addressM.setSoi(soi);
            addressM.setApartment(apartment);
            addressM.setMoo(moo);
            addressM.setRoom(room);
            addressM.setFloor(floor);
            addressM.setProvince(province);
            addressM.setZipcode(zipcode);
            addressM.setTambol(tambol);
            addressM.setAmphur(amphur);
            
            collateralDataM.setAddress(addressM);
            
            //Appraisal Information 
            String performedBy = getRequest().getParameter("performedBy");
            String appraiserName = getRequest().getParameter("appraiserName");
            String appraisalDate = getRequest().getParameter("appraisalDate");
            String contractPrice = getRequest().getParameter("contractPrice");
            String totalFMV = getRequest().getParameter("totalFMV");
            String totalAppraiseValue = getRequest().getParameter("totalAppraiseValue");
            String totalLV = getRequest().getParameter("totalLV");
            String ltvPercent = getRequest().getParameter("ltvPercent");
            String unacceptable = getRequest().getParameter("unacceptable");
            
            AppraisalDataM appraisalM = new AppraisalDataM();
            appraisalM.setAppraisalPerformedBy(performedBy);
            appraisalM.setAppraisalName(appraiserName);
            appraisalM.setAppraisalDate(ORIGUtility.parseThaiToEngDate(appraisalDate));
            appraisalM.setTotalContactPrice(utility.stringToBigDecimal(contractPrice));
            appraisalM.setTotalFMV(utility.stringToBigDecimal(totalFMV));
            //appraisalM.set
            appraisalM.setTotalLoanableValue(utility.stringToBigDecimal(totalLV));
            appraisalM.setLtv(utility.stringToBigDecimal(ltvPercent));
            appraisalM.setUnacceptableCollateral(unacceptable);
            
            collateralDataM.setAppraisal(appraisalM);
            
            
            //Rewrite
	        String tableData = utility.getCollateralTable(collateralV, getRequest());
	        PopulatePopupM populatePopupM = new PopulatePopupM("Collateral",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData.toString()));
	        Vector popMs = new Vector();
			popMs.add(populatePopupM);
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
            
    		getRequest().getSession().removeAttribute("MORTGAGE_POPUP_DATA");
    		
        }catch(Exception e){
            logger.error("Error in SaveGuarantorWebAction.perModelRequest()", e);
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
