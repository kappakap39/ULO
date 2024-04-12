package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.PLOrigUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;

public class SUPDecisionSubForm extends ORIGSubForm {
	
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {

		PLOrigFormHandler plorigform = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		PLApplicationDataM plAppdataM = plorigform.getAppForm();
		
		String docRefID = request.getParameter("doc_ref_id");
		String[] reasonArray= request.getParameterValues("reasonOption");		
		String decision=request.getParameter("sup_decision");
//		String decision_option=request.getParameter("decision_value");
		
		String reasonType=request.getParameter("reasonType");
		String role=userM.getCurrentRole();
//		if(role.equals(OrigConstant.ROLE_DE_FULL)){
//			role = OrigConstant.ROLE_DE;
//		}
		
		plAppdataM.setDocRefNo(docRefID);
		plAppdataM.setAppDecision(decision);
		PLOrigUtility.setDecisionFromRole(plAppdataM, role, decision);
		
		Vector<PLReasonDataM> plReasonDataVect = plAppdataM.getReasonVect();
		
		if(reasonArray!=null && reasonArray.length>0){
		   if(plReasonDataVect == null){
			   plReasonDataVect = new Vector<PLReasonDataM>();
		   }
		   for(int i=0; i<reasonArray.length; i++){
			   String reason=reasonArray[i];
			   PLReasonDataM plReasonDataM=new PLReasonDataM();
			   plReasonDataM.setReasonCode(reason);
			   plReasonDataM.setReasonType(reasonType);
			   plReasonDataM.setRole(role);
			   plReasonDataM.setApplicationRecordId(plAppdataM.getAppRecordID());
			   plReasonDataVect.add(plReasonDataM);
		   }
		}
		plAppdataM.setReasonVect(plReasonDataVect);	
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
