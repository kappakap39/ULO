package com.eaf.orig.ulo.pl.app.utility;

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.orig.cache.properties.BOT5XProperties;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLBOT5XDataM;

public class BOT5X {
	static Logger logger = Logger.getLogger(BOT5X.class);
	public Vector<PLBOT5XDataM> getDisplay(PLApplicationDataM applicationM){
		Vector<PLBOT5XDataM> bot5XVect = new Vector<PLBOT5XDataM>();
		HashMap h = TableLookupCache.getCacheStructure();
		Vector<BOT5XProperties> cacheVect = (Vector<BOT5XProperties>) (h.get("Bot5X"));
		if(null != cacheVect){
			for(BOT5XProperties dataM : cacheVect){
				PLBOT5XDataM bot5xM = getBOT5X(dataM,applicationM);
				if(null != bot5xM){
					bot5XVect.add(bot5xM);
				}
			}
		}
		return bot5XVect;
	}
	public PLBOT5XDataM getBOT5X(BOT5XProperties dataM ,PLApplicationDataM applicationM){
		Vector<PLBOT5XDataM> bot5xVect = applicationM.getBot5xVect();
		PLBOT5XDataM bot5xM = null;
		if(null != bot5xVect){
			for(PLBOT5XDataM botM : bot5xVect){
				if(dataM.getProductID().equals(botM.getProductID())){
					bot5xM = botM;
					break;
				}
			}
		}
		if(null == bot5xM && OrigConstant.ACTIVE_FLAG.equals(dataM.getActiveStatus())){
			bot5xM = new PLBOT5XDataM();
			bot5xM.setProductID(dataM.getProductID());
		}
		if(null != bot5xM){
			bot5xM.setProductName(dataM.getProductName());
		}
		return bot5xM;
	}
}
