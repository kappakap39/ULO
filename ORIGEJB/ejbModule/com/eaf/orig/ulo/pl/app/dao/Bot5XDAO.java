package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBOT5XDataM;

public interface Bot5XDAO {
	public void saveORIG_BOT(String appRecID,Vector<PLBOT5XDataM> bot5xVect) throws PLOrigApplicationException;
	public Vector<PLBOT5XDataM> selectORIG_BOT(String appRecID) throws PLOrigApplicationException;
	public void deleteORIG_BOT(String appRecID) throws PLOrigApplicationException;
}
