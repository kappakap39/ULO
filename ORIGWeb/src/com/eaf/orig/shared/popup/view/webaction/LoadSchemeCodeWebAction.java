/*
 * Created on Dec 22, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.popup.view.webaction;

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.cache.data.CacheDataM;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadSchemeCodeWebAction extends WebActionHelper implements WebAction {
    private static Logger logger = Logger.getLogger(LoadSchemeCodeWebAction.class);
	private String nextAction = "";
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
        StringBuffer sql = new StringBuffer();
        String scheme_code = getRequest().getParameter("scheme_code");
        String code = getRequest().getParameter("code");
        String textboxCode = getRequest().getParameter("textboxCode");
        String textboxDesc = getRequest().getParameter("textboxDesc");
        String campaign = getRequest().getParameter("campaign");
        boolean isFrist = true;
        
        logger.debug("scheme_code = "+scheme_code);
        logger.debug("code = "+code);
        logger.debug("campaign = "+campaign);
        if (campaign==null){
            campaign = "";
        }
        
        try {
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT sch.SCHEME_CODE, sch.SCHEME_DESC, mkt.RECSTS ");
			sql.append("FROM INT_SCHEME sch, MKT_SCHEME_MAPPING mkt_sch, CAMPAIGN mkt ");
            
			if(!ORIGUtility.isEmptyString(scheme_code)){
			    sql.append("WHERE sch.SCHEME_CODE = ? AND mkt_sch.SCHEME_CODE=sch.SCHEME_CODE AND mkt_sch.MKT_ID=mkt.CAMPCDE AND mkt.CAMPCDE = '"+campaign+"' ");
			    valueListM.setString(++index,scheme_code);
//			    valueListM.setString(++index,campaign);
			    isFrist = false;
			}else if(!ORIGUtility.isEmptyString(code)){
			    sql.append("WHERE UPPER(sch.SCHEME_DESC) like ? AND mkt_sch.SCHEME_CODE=sch.SCHEME_CODE AND mkt_sch.MKT_ID=mkt.CAMPCDE AND mkt.CAMPCDE = '"+campaign+"' ");
			    valueListM.setString(++index,"%"+code.toUpperCase()+"%");
//			    valueListM.setString(++index,campaign);
			    isFrist = false;
			}else{
			    sql.append("WHERE mkt_sch.SCHEME_CODE=sch.SCHEME_CODE AND mkt_sch.MKT_ID=mkt.CAMPCDE AND mkt.CAMPCDE = '"+campaign+"' ");
//			    valueListM.setString(++index,campaign);
			}
			sql.append("ORDER BY sch.SCHEME_CODE ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadSchemeCode");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	//getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, mkt_code);
            }

       } catch (Exception e) {
           logger.error("exception ",e);
       }
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.ACTION;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return nextAction;
    }
	
	public Vector getSchemeByMktID(Vector vSchemeCode,String MktID){
		Vector vNewSchemeCode = new Vector();
		HashMap hash=TableLookupCache.getCacheStructure();	
		Vector vMktScheme = (Vector)(hash.get("MktSchemeCacheDataM"));
		//logger.debug(">> vSchemeCode : "+vSchemeCode);
		//logger.debug(">> vMktScheme : "+vMktScheme);
		//logger.debug(">> MktID : "+MktID);
		try{
			for(int i=0; i<vMktScheme.size(); i++){
				CacheDataM mktSchemeM = (CacheDataM)vMktScheme.get(i);
				if(mktSchemeM.getCode().equals(MktID)){
					//logger.debug(">> mktSchemeM.getCode() : "+mktSchemeM.getCode());
					for(int j=0; j<vSchemeCode.size(); j++){
						CacheDataM SchemeCodeM = (CacheDataM)vSchemeCode.get(j);
						//logger.debug(">> SchemeCodeM.getCode() : "+SchemeCodeM.getCode());
						if(mktSchemeM.getShortDesc().equals(SchemeCodeM.getCode())){
							vNewSchemeCode.add(SchemeCodeM);
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return vNewSchemeCode;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
