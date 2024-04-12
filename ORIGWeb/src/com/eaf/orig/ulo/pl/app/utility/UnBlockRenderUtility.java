package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.PLOrigUnBlockDAO;
import com.eaf.orig.shared.dao.utility.PLOrigUnBlockDAOImpl;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.PLInboxUnBlockDataM;

public class UnBlockRenderUtility {
	
	static Logger log = Logger.getLogger(UnBlockRenderUtility.class);
	
	public static String renderSubTable(HttpServletRequest request,Vector listVect, String role, String userName){		
		StringBuilder HTML = new StringBuilder();		
		HTML.append("<table class='TableFrame'>");
			HTML.append(HEADER(request));				
			if(null != listVect && listVect.size() > 1){						
				HTML.append(RESULT(request, listVect, role, userName));
			}else{
				HTML.append(NODATAFOUND());
			}
		HTML.append("</table>");
		return HTML.toString();
	}
	
	private static String HEADER(HttpServletRequest request){
		StringBuilder HEADER = new StringBuilder("");
			HEADER.append("<tr class='Header'>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "LOCK_DATE")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "ID_NO")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "FULL_NAME")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "PRODUCT")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "PRIORITY")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "SYSTEM_ACCEPT_DATE")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "REMAINING_TIME")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "OWNER")+"</td>");
				HEADER.append("<td>"+MessageResourceUtil.getTextDescription(request, "UNLOCK")+"</td>");
			HEADER.append("</tr>");
		return HEADER.toString();
	}
	
	private static String RESULT(HttpServletRequest request ,Vector listVect, String role, String userName){
		ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
		Vector vUser = (Vector)origCache.loadUserNameCache(OrigConstant.ACTIVE_FLAG);
		StringBuilder RESULT = new StringBuilder("");
		Vector<PLInboxUnBlockDataM> inboxVect = null;
		int num = 0;
			for(int i=1; i<listVect.size(); i++){				
				Vector elementList = (Vector)listVect.get(i);
				String IDNO = (String) elementList.get(1);
				String STYLE = (num%2==0)?"ResultEven":"ResultOdd";				
				inboxVect = LoadUnblock(IDNO, role, userName);				
				if(null!=inboxVect && inboxVect.size()>0){
					RESULT.append(DISPLAYRESULT(request,inboxVect,IDNO,vUser,STYLE));	
					num++;
				}				
			}			
		return RESULT.toString();
	}
	
	private static String DISPLAYRESULT(HttpServletRequest request,Vector<PLInboxUnBlockDataM> inboxVect,String IDNO,Vector vUser,String STYLE){
		WorkflowTool wt = new WorkflowTool();
		StringBuilder RESULT = new StringBuilder("");
		boolean display_key = DISPLAY_KEY(inboxVect);
		for(int i=0; i<inboxVect.size(); i++) {
			PLInboxUnBlockDataM unblockM = inboxVect.get(i);
			String appRecID = unblockM.getAppRecId();
			RESULT.append("<tr class='result "+STYLE+"'>");
				RESULT.append("<td>"+DataFormatUtility.dateToStringFormatTh(unblockM.getBlockDate())+"</td>");
				if(i==0){
					RESULT.append("<td rowspan="+inboxVect.size()+">"+HTMLRenderUtil.displayHTML(IDNO)+"</td>");
				}
				RESULT.append("<td class='nev-unlock' id='"+appRecID+"'>"+HTMLRenderUtil.displayHTML(unblockM.getAppNo())+"</td>");
				RESULT.append("<td class='nev-unlock' id='"+appRecID+"'>"+HTMLRenderUtil.displayHTML(wt.GetMessageAppStatus(request, unblockM.getAppStatus()))+"</td>");
				RESULT.append("<td class='nev-unlock' id='"+appRecID+"'>"+HTMLRenderUtil.displayHTML(unblockM.getFirstName())+"&nbsp;&nbsp;"+HTMLRenderUtil.displayHTML(unblockM.getLastName())+"</td>");
				RESULT.append("<td class='nev-unlock' id='"+appRecID+"'>"+HTMLRenderUtil.displayHTML(unblockM.getProduct())+"</td>");
				RESULT.append("<td class='nev-unlock' id='"+appRecID+"'>"+HTMLRenderUtil.displayHTML(unblockM.getPriority())+"</td>");
				RESULT.append("<td class='nev-unlock' id='"+appRecID+"'>"+DataFormatUtility.dateToStringFormatTh(unblockM.getAppDate())+"</td>");
				RESULT.append("<td class='nev-unlock' id='"+appRecID+"' nowrap='nowrap'>"+HTMLRenderUtil.displayHTML(unblockM.getSlaId())+"</td>");
				RESULT.append("<td class='nev-unlock' id='"+appRecID+"'>"+HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName(unblockM.getOwner(),vUser))+"</td>");
				RESULT.append(KEY(inboxVect,unblockM,display_key));
			RESULT.append("</tr>");
		}		
		return RESULT.toString();
	}
	private static boolean DISPLAY_KEY(Vector<PLInboxUnBlockDataM> inboxVect){
		for(PLInboxUnBlockDataM unBlockM : inboxVect) {
			if(!WorkflowConstant.ActivityType.BLOCK.equals(unBlockM.getActivityType())){
				return false;
			}
		}
		return true;
	}
	private static String KEY(Vector<PLInboxUnBlockDataM> inboxVect,PLInboxUnBlockDataM blockM ,boolean display_key){
		StringBuilder KEY = new StringBuilder("");
		if(!display_key){
			KEY.append("<td class='nev-unlock'>&nbsp;</td>");
		}else{
			StringBuilder APPREC = new StringBuilder();
			StringBuilder APPNO = new StringBuilder();
			for(int j=0; j<inboxVect.size(); j++){
				PLInboxUnBlockDataM unblockM = inboxVect.get(j);
				APPREC.append(unblockM.getAppRecId());
				APPNO.append(unblockM.getAppNo());
				if(j < inboxVect.size() - 1){
					APPREC.append(",");
					APPNO.append(",");
				}
			}
			KEY.append("<td class='nev-unlock'>");
			KEY.append("<img src='/../../ORIGWeb/images/unlock.png' style='cursor: pointer;' ");
			KEY.append("onclick=\"unlockAction('"+blockM.getAppRecId()+"','"+APPREC.toString()+"','"+blockM.getAppNo()+"','"+APPNO.toString()+"')\">");
			KEY.append("</td>");
		}
		return KEY.toString();
	}
	
	private static String NODATAFOUND(){
		StringBuilder HTML = new StringBuilder("");
			HTML.append("<tr class='ResultNotFound'>");
				HTML.append("<td colspan='11'>No record found</td>");
			HTML.append("</tr>");
		return HTML.toString();
	}
	
	private static Vector<PLInboxUnBlockDataM> LoadUnblock(String IDNO, String role, String userName){
		try{
			PLOrigUnBlockDAO unBlockDAO = new PLOrigUnBlockDAOImpl();
			return unBlockDAO.loadSubTableUnBlock(IDNO, role, userName);
		}catch(Exception e){
			log.fatal("Exception >> ",e);
		}
		return new Vector<PLInboxUnBlockDataM>();
	}

}
