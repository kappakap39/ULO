
package com.eaf.orig.ulo.pl.ajax;

import java.util.Date;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;


public class AddNotePadData implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public String getDisplayObject(HttpServletRequest request)	throws PLOrigApplicationException {
		JsonObjectUtil json = new JsonObjectUtil();
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		Vector<NotePadDataM> notepadVect = (Vector<NotePadDataM>) request.getSession().getAttribute("NOTE_PAD_DATAM");
		
		String notepad = request.getParameter("notepad-input");
				
		if(null == notepadVect){
			notepadVect = new Vector<NotePadDataM>();
		}
		
		NotePadDataM notepadM = new NotePadDataM();
			notepadM.setCreateBy(userM.getUserName());
			notepadM.setCreateDate( new java.sql.Timestamp( new  Date().getTime()));
			notepadM.setNotePadDesc(notepad);
			
		notepadVect.add(notepadM);
			
		request.getSession().setAttribute("NOTE_PAD_DATAM", notepadVect);	
		
		json.CreateJsonObject("notepad-div",createNotePad(request, notepadVect));
		
		return json.returnJson();		
	}
	
	public String createNotePad(HttpServletRequest request,Vector<NotePadDataM> notepadVect){
		StringBuilder HTML = new StringBuilder("");
		ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
		HTML.append("<table class='TableFrame' id='notepad-table'>");
		HTML.append("<tr class='Header'>");
		HTML.append("<td width='20%'>").append(MessageResourceUtil.getTextDescription(request, "DATE_TIME")).append("</td>");
		HTML.append("<td width='15%'>").append(MessageResourceUtil.getTextDescription(request, "USER_ID")).append("</td>");
		HTML.append("<td width='60%'>").append(MessageResourceUtil.getTextDescription(request, "NOTEPAD")).append("</td>");	
        HTML.append("</tr>");
			 if(!OrigUtil.isEmptyVector(notepadVect)){
					for(int i=notepadVect.size()-1; i >= 0; --i){ 
					NotePadDataM noteM = (NotePadDataM) notepadVect.get(i);
					String styleTr = (i%2==0)?"ResultEven":"ResultOdd";
				HTML.append("<tr class='").append(styleTr).append("'>");
				HTML.append("<td>").append(DataFormatUtility.DateEnToStringDateTh(noteM.getCreateDate(), 
								DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS)).append("</td>");
				HTML.append("<td>").append(HTMLRenderUtil.displayHTML(origCache.GetUserNameByuserID(noteM.getCreateBy()))).append("</td>");
				HTML.append("<td><div class='textL'>").append(NotePadNotification.DisplayNotePad(noteM.getNotePadDesc())).append("</div></td>");
				HTML.append("</tr>");
			}
		}else{
			HTML.append("<tr class='ResultNotFound' id='notepad-notfound'>");
			HTML.append("<td colspan='3'>No record found</td>");
			HTML.append("</tr>");
		}
		HTML.append("</table>");
		return HTML.toString();
	}
	
}
