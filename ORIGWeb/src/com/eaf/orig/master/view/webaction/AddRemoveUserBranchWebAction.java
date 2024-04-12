/*
 * Created on Dec 3, 2007
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
package com.eaf.orig.master.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.shared.model.UserBranchM;

/**
 * @author Administrator
 *
 * Type: AddRemoveUserBranchWebAction
 */
public class AddRemoveUserBranchWebAction extends WebActionHelper implements
		WebAction {
	static Logger log = Logger.getLogger(AddRemoveUserBranchWebAction.class);

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
		//*** for show Edit Data Changed
		
		//*** END
		
		String[]  branch = getRequest().getParameterValues("branch");
		String[]  selectedBranch = getRequest().getParameterValues("selectedBranch");
		String[]  cmpCde = null;
		String[]  area = null;
		String[]  branchDesc = null;
		String[]  selCmpCde = null;
		String[]  selArea = null;
		String[]  selBranchDesc = null;
		String[]  temp = null;
		String[]  temp2 = null;
		
		if(branch!=null && branch.length>0){
			log.debug("------------------> all branch length = " + branch.length);
			cmpCde = new String[branch.length];
			area = new String[branch.length];
			branchDesc = new String[branch.length];
		    temp = new String[branch.length];
		    
				for(int i = 0; i < branch.length; i++){
					temp = branch[i].split(",");
					cmpCde[i] = temp[0];
					area[i] =	temp[1];
					branchDesc[i] =	temp[2];
				}
		}
		if(selectedBranch!=null && selectedBranch.length>0){
			selCmpCde = new String[selectedBranch.length];
			selArea = new String[selectedBranch.length];
			selBranchDesc = new String[selectedBranch.length];
			temp2 = new String[selectedBranch.length];
		    
				for(int i = 0; i < selectedBranch.length; i++){
					temp2 = selectedBranch[i].split(",");
					selCmpCde[i] = temp2[0];
					selArea[i] =	temp2[1];
					selBranchDesc[i] =	temp2[2];
				}
	    }
		
		Vector selBranchVect = (Vector) getRequest().getSession().getAttribute("SELECTED_BRANCH");
		if(selBranchVect==null){
			selBranchVect = new Vector();
		}
		Vector branchVect = (Vector) getRequest().getSession().getAttribute("SEARCH_BRANCH");
		if(branchVect==null){
			branchVect = new Vector();
		}
		String addOrRemove = getRequest().getParameter("addOrRemove");
		log.debug("------------------> addOrRemove = " + addOrRemove);
		if((addOrRemove != null) && "add".equalsIgnoreCase(addOrRemove)){
			log.debug("///im adding selBranchVect");		
				if (branch !=null && branch.length>0) {
						log.debug("------------------> all branch length = " + branch.length);
				
					for (int i=0; i<branch.length;i++) {
						String cmpCde1= cmpCde[i];
						String area1= area[i];
						String branchDesc1= branchDesc[i];
						UserBranchM userBranchM = new UserBranchM();
						
						userBranchM.setCmpCde(cmpCde1);
						userBranchM.setArea(area1);
						userBranchM.setBranchDesc(branchDesc1);
						selBranchVect.add(userBranchM);	
						
						/* remove from busVect */
						if(branchVect.size()>0){
							UserBranchM branchSearchM = new UserBranchM();
							for (int j=0; j<branchVect.size();j++){
								branchSearchM = (UserBranchM)branchVect.get(j);
								if(cmpCde1.equals(branchSearchM.getCmpCde()) && area1.equals(branchSearchM.getArea())){
									branchVect.removeElementAt(j);
								}
							}
						}
					}
				}	
		}else if((addOrRemove != null) && "remove".equalsIgnoreCase(addOrRemove)){
			log.debug("///im removing selBranchVect");		
			if (selectedBranch !=null && selectedBranch.length>0) {
					log.debug("------------------> all selectedBranch length = " + selectedBranch.length);

				for (int i=0; i<selectedBranch.length;i++) {
					String selCmpCde1 = selCmpCde[i];
					String selArea1 = selArea[i];
					String selBranchDesc1 = selBranchDesc[i];
					UserBranchM userBranchM = new UserBranchM();
					
					userBranchM.setCmpCde(selCmpCde1);
					userBranchM.setArea(selArea1);
					userBranchM.setBranchDesc(selBranchDesc1);
					branchVect.add(userBranchM);	
					
					/* remove from selBusVect */
					if(selBranchVect.size()>0){
						UserBranchM branchSelM =new UserBranchM();
						for (int j=0; j<selBranchVect.size();j++){
							branchSelM = (UserBranchM)selBranchVect.get(j);
							if(selCmpCde1.equals(branchSelM.getCmpCde()) && selArea1.equals(branchSelM.getArea())){
								selBranchVect.removeElementAt(j);
							}
						}
					}
				}			
			}
		
		}	
		
		getRequest().getSession().setAttribute("SELECTED_BRANCH",selBranchVect);
		getRequest().getSession().setAttribute("SEARCH_BRANCH",branchVect);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return 0;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
