package com.eaf.orig.ulo.app.olddata.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.AttachmentDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public interface OldDataDAO 
{
	ApplicationGroupDataM loadOldAppGroup(String applicationGroupId) throws Exception;
	ArrayList<ApplicationDataM> loadOldApp(Connection conn, String applicationGroupId) throws Exception;
	ArrayList<PersonalInfoDataM> loadOldPersonalInfo(Connection conn, String applicationGroupId) throws Exception;
	ArrayList<ApplicationImageDataM> loadOldImage(Connection conn, String applicationGroupId) throws Exception;
	ArrayList<AttachmentDataM> loadOldAttachment(Connection conn, String applicationGroupId) throws Exception;
	AttachmentDataM loadAttachmentInfo(String attachId) throws Exception;
}
