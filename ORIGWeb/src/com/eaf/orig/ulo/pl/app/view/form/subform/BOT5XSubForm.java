package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.orig.cache.properties.BOT5XProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLBOT5XDataM;

public class BOT5XSubForm extends ORIGSubForm {
	static Logger logger = Logger.getLogger(BOT5XSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		try{		
			PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);			
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");				
			PLApplicationDataM applicationM = formHandler.getAppForm();
			
			BigDecimal totalBOT5X = new BigDecimal(0);	
			
			HashMap h = TableLookupCache.getCacheStructure();
			Vector<BOT5XProperties> cacheVect = (Vector<BOT5XProperties>) (h.get("Bot5X"));
			PLBOT5XDataM bot5XM = null;
			Vector<PLBOT5XDataM> bot5xVect = new Vector<PLBOT5XDataM>();			
			if(null != cacheVect){
				for(BOT5XProperties dataM : cacheVect){
					BigDecimal amount = DataFormatUtility.StringToBigDecimalEmptyNull(request.getParameter(dataM.getProductID()));
					if(null != amount && amount.compareTo(BigDecimal.ZERO) > 0){
						totalBOT5X = totalBOT5X.add(amount);
						bot5XM = new PLBOT5XDataM();
						bot5XM.setProductID(dataM.getProductID());
						bot5XM.setAmount(amount);
						bot5XM.setCreateBy(userM.getUserName());
						bot5XM.setUpdateBy(userM.getUserName());
						bot5xVect.add(bot5XM);
					}
				}
			}			
			totalBOT5X.setScale(2, BigDecimal.ROUND_HALF_UP);	
			
			applicationM.setBot5xVect(bot5xVect);
			applicationM.setBot5xTotal(totalBOT5X);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {		
		return false;
	}

}
