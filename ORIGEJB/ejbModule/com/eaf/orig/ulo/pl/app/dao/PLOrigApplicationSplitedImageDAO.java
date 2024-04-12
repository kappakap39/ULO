package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;;

public interface PLOrigApplicationSplitedImageDAO {
	
	public void saveUpdateApplicationImageSplit (Vector<PLApplicationImageSplitDataM> appImageSplitVect ,String appImgID) throws PLOrigApplicationException;
	public Vector<PLApplicationImageSplitDataM> loadOrigApplicationImageSplit (String appImgId) throws PLOrigApplicationException;
	public Vector<PLApplicationImageSplitDataM> loadImageSplitFromAppRecId (String appRecId) throws PLOrigApplicationException;
}