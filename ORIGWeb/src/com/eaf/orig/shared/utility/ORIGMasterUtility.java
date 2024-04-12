/*
 * Created on Jan 17, 2008
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.utility;

import java.util.Vector;

import org.apache.log4j.Logger;


/**
 * @author Avalant
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ORIGMasterUtility {
	Logger log = Logger.getLogger(ORIGMasterUtility.class);
	
	public Vector getSpecificValueList(String charCode){
		Vector vListbox = new Vector();
		ORIGUtility utility = new ORIGUtility();
		ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
		
		/**"002".equals(charCode) || "003".equals(charCode) || "004".equals(charCode) || "006".equals(charCode)
		|| "008".equals(charCode) || "012".equals(charCode) || "013".equals(charCode) || "020".equals(charCode)
		|| "023".equals(charCode) || "029".equals(charCode) || "030".equals(charCode) || "036".equals(charCode)**/
		
		if("002".equals(charCode)){//Marital Status
			vListbox = utility.loadCacheByName("MaritalStatus");
		}else if("003".equals(charCode)){//Education Level
			vListbox = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",2);
		}else if("004".equals(charCode)){//Sex
			vListbox = utility.getMasterDataFormCache("GENDERCDE");
		}else if("006".equals(charCode)){//Occupation
			vListbox = utility.loadCacheByName("Occupation");
		}else if("008".equals(charCode)){//Accomodation Status
			vListbox = utility.loadCacheByName("AddressStatus");
		}else if("012".equals(charCode)){//Car Brand
			vListbox = utility.loadCacheByName("CarBrand");
		}else if("013".equals(charCode)){//Car Category Type 
			vListbox = utility.loadCacheByName("CarCategoryType");
		}else if("020".equals(charCode)){//NPL/Civil Suit History/Restructure
			vListbox = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",17);
		}else if("023".equals(charCode)){//Land Ownership
			vListbox = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",6);
		}else if("029".equals(charCode)){//Car Brand
			vListbox = utility.loadCacheByName("CarBrand");
		}else if("030".equals(charCode)){//Car Category Type
			vListbox = utility.loadCacheByName("CarCategoryType");
		}else if("036".equals(charCode)){//NPL/Civil Suit History/Restructure
			vListbox = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",18);
		}
		
		
		return vListbox;
	}

}
