package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;

public class SellInformationSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(SellInformationSubForm.class);

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		PLOrigFormHandler plorigform = (PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM plappdataM = plorigform.getAppForm();
		PLSaleInfoDataM plsaleinfo = plappdataM.getSaleInfo();

		if (plsaleinfo == null) {
			plsaleinfo = new PLSaleInfoDataM();
			plappdataM.setSaleInfo(plsaleinfo);
		}

		String ref_sell_guarantee = request.getParameter("certification");
		String ref_recommend_title = request.getParameter("ref_recommend_title");
		String ref_branch_code = request.getParameter("ref_branch_code");
		String ref_telephone = request.getParameter("ref_telephone");
		String ref_fax_no = request.getParameter("ref_fax_no");
		String seller_title = request.getParameter("seller_title");
		String seller_branch_code = request.getParameter("seller_branch_code");
		String sell_telephone = request.getParameter("sell_telephone");
		String sell_fax_no = request.getParameter("sell_fax_no");
		String service_seller_title = request.getParameter("service_seller_title");
		String service_seller_branch_code = request.getParameter("service_seller_branch_code");
		String service_telephone = request.getParameter("service_telephone");
		String service_fax = request.getParameter("service_fax");
		String remark = request.getParameter("more_info");

		plsaleinfo.setSalesGuarantee(ref_sell_guarantee);
		
		plsaleinfo.setRefName(ref_recommend_title);
		plsaleinfo.setRefBranchCode(ref_branch_code);
		plsaleinfo.setRefPhoneNo(ref_telephone);
		plsaleinfo.setRefFaxNo(ref_fax_no);
		
		plsaleinfo.setSalesName(seller_title);
		plsaleinfo.setSalesBranchCode(seller_branch_code);
		plsaleinfo.setSalesPhoneNo(sell_telephone);
		plsaleinfo.setSalesFaxNo(sell_fax_no);
		
		plsaleinfo.setCashDayOneName(service_seller_title);
		plsaleinfo.setCashDayOneBranchCode(service_seller_branch_code);
		plsaleinfo.setCashDayOnePhoneNo(service_telephone);
		plsaleinfo.setCashDayOneFax(service_fax);
		
		plsaleinfo.setRemark(remark);

//		logger.debug("plsaleinfo.getRefName()" + plsaleinfo.getRefName());
//		logger.debug("plsaleinfo.getRefBranchCode()" + plsaleinfo.getRefBranchCode());
//		logger.debug("plsaleinfo.getRefPhoneNo()" + plsaleinfo.getRefPhoneNo());
//		logger.debug("plsaleinfo.getRefFaxNo()" + plsaleinfo.getRefFaxNo());
//		
//		logger.debug("plsaleinfo.getSalesName()" + plsaleinfo.getSalesName());
//		logger.debug("plsaleinfo.getSalesBranchCode()" + plsaleinfo.getSalesBranchCode());
//		logger.debug("plsaleinfo.getSalesPhoneNo()" + plsaleinfo.getSalesPhoneNo());
//		logger.debug("plsaleinfo.getSalesFaxNo()" + plsaleinfo.getSalesFaxNo());
//		
//		logger.debug("plsaleinfo.getCashDayOneName()" + plsaleinfo.getCashDayOneName());
//		logger.debug("plsaleinfo.getCashDayOneBranchCode()" + plsaleinfo.getCashDayOneBranchCode());
//		logger.debug("plsaleinfo.getCashDayOnePhoneNo()" + plsaleinfo.getCashDayOnePhoneNo());
//		logger.debug("plsaleinfo.getCashDayOneFax()" + plsaleinfo.getCashDayOneFax());
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String currentRole = userM.getCurrentRole();

		String seller_title = request.getParameter("seller_title");
		String seller_branch_code = request.getParameter("seller_branch_code");
		String cashDay1Flag = request.getParameter("cash_day1_day1Flag");
		String errorMsg = "";

		PLApplicationDataM plappdataM = formHandler.getAppForm();
		String busclass = plappdataM.getBusinessClassId();
		String decision = request.getParameter("decision_option");
		if (OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())
				&& !currentRole.equalsIgnoreCase(OrigConstant.ROLE_DF_REJECT)
				&& !busclass.equalsIgnoreCase(OrigConstant.BusClass.FCP_KEC_IC)
				&& !busclass.equalsIgnoreCase(OrigConstant.BusClass.FCP_KEC_DC)
				&& ((OrigConstant.ROLE_DC.equals(currentRole) && OrigConstant.wfProcessState.SEND.equals(decision)) || !OrigConstant.ROLE_DC
						.equals(currentRole))) {
			if(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType()){ //Praisan 20121218 validate only type submit (1)
				//check cash day1 #Vikrom 20121204
				if (ORIGUtility.isEmptyString(seller_title) && "DC1".equals(cashDay1Flag)) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "SELLER_WARNING");
					formHandler.PushErrorMessage("seller_title", errorMsg);
					formHandler.PushErrorMessage("seller_name_th", "");
				}
				if (ORIGUtility.isEmptyString(seller_branch_code) && "DC1".equals(cashDay1Flag)) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "BRANCH_SELLER_WARNING");
					formHandler.PushErrorMessage("seller_branch_code", errorMsg);
					formHandler.PushErrorMessage("seller_branch_name", "");
				}
	
				String cash_day1_day1Flag = request.getParameter("cash_day1_day1Flag");
				if (null != cash_day1_day1Flag && !"".equals(cash_day1_day1Flag)
						&& !"NA".equalsIgnoreCase(cash_day1_day1Flag) && !"NP".equalsIgnoreCase(cash_day1_day1Flag)) {
					String service_seller_title = request.getParameter("service_seller_title");
					String service_seller_branch_code = request.getParameter("service_seller_branch_code");
					if (ORIGUtility.isEmptyString(service_seller_title)) {
						errorMsg = ErrorUtil.getShortErrorMessage(request, "SELLER_SERVICE_WARNING");
						formHandler.PushErrorMessage("service_seller_title", errorMsg);
					}
	
					if (ORIGUtility.isEmptyString(service_seller_branch_code)) {
						errorMsg = ErrorUtil.getShortErrorMessage(request, "SELLER_BRANCH_SERVICE_WARNING");
						formHandler.PushErrorMessage("service_seller_branch_code", errorMsg);
					}
				}
				
				//#septemwi modify				
				if(ORIGLogic.isManadatoryFiledSellerBranchCode(busclass, userM.getCurrentRole())){
					String sellertitle = request.getParameter("seller_title");
					if(OrigUtil.isEmptyString(sellertitle)){
						errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_SELLER");
						formHandler.PushErrorMessage("seller_title", errorMsg);
					}
				}
				
				if(ORIGLogic.isMandatoryFieldSeller(busclass, userM.getCurrentRole())){
					String seller_branchcode = request.getParameter("seller_branch_code");
					if(OrigUtil.isEmptyString(seller_branchcode)){
						errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_SELLER_BRANCH_CODE");
						formHandler.PushErrorMessage("seller_branch_code", errorMsg);
					}
				}
				
				if (errorMsg.length() != 0) {
					return true;
				}
			}
		}
		return false;
	}

}
