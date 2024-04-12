package com.eaf.orig.ulo.pl.ajax;

import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class NotePadNotification implements AjaxDisplayGenerateInf{
	private static Logger logger = Logger.getLogger(NotePadNotification.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		String typeNotepad = request.getParameter("type-notepad");
		logger.debug("[typeNotepad] "+typeNotepad);
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		Vector vNotePadDataM = appM.getNotepadDataM();
		
		if (null == vNotePadDataM) {
			appM.setNotepadDataM(new Vector());
			vNotePadDataM = appM.getNotepadDataM();
		}
		
		if("ADD".equalsIgnoreCase(typeNotepad)){
			String notepadInput  = request.getParameter("notepad-input"); 
			NotePadDataM addNotePadDataM=new NotePadDataM();
			addNotePadDataM.setCreateBy(userM.getUserName());
			addNotePadDataM.setCreateDate( new java.sql.Timestamp( new  Date().getTime()));
			addNotePadDataM.setNotePadDesc(notepadInput);
			vNotePadDataM.add(addNotePadDataM);
		}
		JsonObjectUtil objectUtil = new JsonObjectUtil();		
		
		objectUtil.CreateJsonObject("notepad-body", createNotepadBody(appM));
		
		return objectUtil.returnJson();
	}
	
	public static String createNotepadBody(PLApplicationDataM appM){	
		StringBuilder str = new StringBuilder();
		Vector<NotePadDataM> noteVect = appM.getNotepadDataM();
		ORIGCacheUtil origCache = new ORIGCacheUtil();
		if(!ORIGUtility.isEmptyVector(noteVect)){
			for(int i=noteVect.size()-1; i >= 0; --i){
				NotePadDataM noteM = (NotePadDataM) noteVect.get(i);
				str.append("<div class=\"notepad-obj\">");
				str.append("<div class=\"notepad-text\">");
				str.append(DisplayNotePad(noteM.getNotePadDesc()));
				str.append("</div>");
				str.append("<div class=\"notepad-nav\">");
				str.append(DataFormatUtility.DateEnToStringDateTh(noteM.getCreateDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS));
				str.append(" ,");
				str.append(HTMLRenderUtil.displayHTML(origCache.GetUserNameByuserID(noteM.getCreateBy())));
				str.append("</div>");
				str.append("</div>");
			}
		}else{
			str.append("<div class=\"notepad-notfound\" id=\"notepad-notfound\">Not found notepad</div>");
		}
//		logger.debug("[createNotepadBody] str "+str);
		return str.toString();
	}
	
	public static String DisplayNotePad(String notePad){
		if(OrigUtil.isEmptyString(notePad)) return "";		
		String [] noteObj = notePad.split("<br/>");
		StringBuilder str = new StringBuilder("");
		for(int i = 0 ;i < noteObj.length; i++){
			str.append(HTMLRenderUtil.displayHTML(noteObj[i]));
			if(i != noteObj.length -1){
				str.append("<br/>");
			}
		}		
		return str.toString();
	}
	
}
