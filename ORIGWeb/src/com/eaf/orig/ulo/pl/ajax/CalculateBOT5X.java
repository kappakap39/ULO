package com.eaf.orig.ulo.pl.ajax;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.orig.cache.properties.BOT5XProperties;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class CalculateBOT5X implements AjaxDisplayGenerateInf{
	static Logger logger = Logger.getLogger(CalculateBOT5X.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		BigDecimal totalBOT5X = new BigDecimal(0);	
		JsonObjectUtil json = new JsonObjectUtil();
		try{	
			HashMap h = TableLookupCache.getCacheStructure();
			Vector<BOT5XProperties> cacheVect = (Vector<BOT5XProperties>) (h.get("Bot5X"));
			if(null != cacheVect){
				for(BOT5XProperties dataM : cacheVect){
					BigDecimal amount = DataFormatUtility.StringToBigDecimalEmptyNull(request.getParameter(dataM.getProductID()));
					if(null != amount && amount.compareTo(BigDecimal.ZERO) > 0){
						totalBOT5X = totalBOT5X.add(amount);
					}
				}
			}
			totalBOT5X.setScale(2, BigDecimal.ROUND_HALF_UP);	
			
			logger.debug("Total BOT5X >> "+totalBOT5X);
			
			json.CreateJsonObject("bot5x_total", DataFormatUtility.displayCommaNumber(totalBOT5X));
			
		}catch(Exception e){
			logger.fatal("ERROR "+e.getMessage());
		}
		return json.returnJson();
	}

}
