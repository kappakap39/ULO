package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageDataM;

public interface PLOrigApplicationImageDAO {
	
	public void saveUpdateOrigApplicationImage (Vector<PLApplicationImageDataM> appImageVect, String appRecId) throws PLOrigApplicationException;
	public Vector<PLApplicationImageDataM> loadOrigApplicationImage (String appRecId) throws PLOrigApplicationException;
	public void SaveUpdateFollowImage(PLApplicationImageDataM appImgM, String appRecId) throws PLOrigApplicationException;

}
