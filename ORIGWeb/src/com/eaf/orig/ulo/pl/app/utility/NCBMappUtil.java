package com.eaf.orig.ulo.pl.app.utility;

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.ncb.model.output.IDRespM;
import com.eaf.ncb.model.output.PARespM;
import com.eaf.orig.cache.properties.NCBAccountStatusProperties;
import com.eaf.orig.cache.properties.NCBAccountTypeProperties;
import com.eaf.orig.cache.properties.NCBParamMasterProperties;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

public class NCBMappUtil {
	static Logger logger = Logger.getLogger(NCBMappUtil.class);
	public class Consent{
		public static final String IDNO = "01";
		public static final String PUBLICNO = "02";
		public static final String PASSPORTNO = "07";
		public static final int TELHOME = 1;
		public static final int TELMOBLIE = 2;
		public static final int TELWORK = 3;
		public static final String Occupation = "Occupation";
		public static final String ConsentTo = "ConsentTo";
		public static final String AddressType = "AddressType";
		public static final String IDType = "IDType";
		public static final String Colatteral = "Colatteral";
		public static final String MarialStatus = "MarialStatus";
		public static final String Nationtality = "Nationtality";
		public static final String Gender = "Gender";
		public static final String OwnershipIndicator = "OwnershipIndicator";
		public static final String ResidentialStatus = "ResidentialStatus";
		public static final String TypeOfCreditCard = "TypeOfCreditCard";
		public static final String HOME_ADDRESS = "1";
	}
	
	public static String NcbConsent(String consentEnq){
		if("Y".equals(consentEnq)){
			return "\u0E21\u0E35";
		}
		if("C".equals(consentEnq)){
			return "\u0E22\u0E01\u0E40\u0E25\u0E34\u0E01";
		}
		return "\u0E44\u0E21\u0E48\u0E21\u0E35";
	}
	public static String IdNo(String fieldType,Vector<IDRespM> nIdVect){
		if(OrigUtil.isEmptyVector(nIdVect))
			return "";
		for(IDRespM idRespM : nIdVect){
			if(null != idRespM.getIdType() && idRespM.getIdType().equals(fieldType)){
				return idRespM.getIdNumber();
			}
		}
		return "";
	}
	public static String TelephoneNo(int fieldType,String telType ,String telNo){
		switch (fieldType) {
			case Consent.TELHOME:
				if("1".equals(telType))
					return telNo;
				break;
			case Consent.TELMOBLIE:
				if("2".equals(telType))
					return telNo;
				break;
			case Consent.TELWORK:
				if("3".equals(telType))
					return telNo;
				break;
			default:
				break;
		}
		return "";
	}
	public static String GetNCBParamMaster(String field,String code){
		HashMap h = TableLookupCache.getCacheStructure();
	    Vector<NCBParamMasterProperties> ncbParam = (Vector) (h.get("NcbParamMaster"));
	    if(null != ncbParam){
		    for(NCBParamMasterProperties dataM : ncbParam) {
				if( null != dataM.getField() && dataM.getField().equals(field) 
					&& null !=  dataM.getCode() && dataM.getCode().equals(code)){
						if(null == dataM.getEngDesc()){
							return "";
						}
						return dataM.getEngDesc();
				}
			}
	    }
		return "";
	}
	
	public static String GetNCBAccountType(String code){
		HashMap h = TableLookupCache.getCacheStructure();
	    Vector<NCBAccountTypeProperties> ncbAccountTypeVect = (Vector) (h.get("NcbAccountType"));
	    if(null != ncbAccountTypeVect){
	    	for(NCBAccountTypeProperties dataM : ncbAccountTypeVect) {
				if(null != dataM.getAccountCode()){
					if(dataM.getAccountCode().equals(code)){
						if(null == dataM.getAccountType()){
							return "";
						}
						return dataM.getAccountType();
					}
				}
			}
	    }
	    return "";
	}
	
	public static String GetNCBAccountStatus(String code){
		HashMap h = TableLookupCache.getCacheStructure();
	    Vector<NCBAccountStatusProperties> ncbAccountStatusVect = (Vector) (h.get("NcbAccountStatus"));
	    if(null != ncbAccountStatusVect){
	    	for(NCBAccountStatusProperties dataM : ncbAccountStatusVect){
				if(null != dataM.getAcccountStatusCode()){
					if(dataM.getAcccountStatusCode().equals(code)){
						if(null == dataM.getThAccountDescription()){
							return "";
						}
						return dataM.getThAccountDescription();
					}
				}
			}
	    }
		return "";
	}
	
	public static String Address(PARespM pARespM){
		StringBuilder str = new StringBuilder();		
		if(!ORIGUtility.isEmptyString(pARespM.getAddressLine1()))
			str.append(pARespM.getAddressLine1()).append(" ");
		
		if(!ORIGUtility.isEmptyString(pARespM.getAddressLine2()))
			str.append(pARespM.getAddressLine2()).append(" ");
		
		if(!ORIGUtility.isEmptyString(pARespM.getAddressLine3()))
			str.append(pARespM.getAddressLine3()).append(" ");
		
		if(!ORIGUtility.isEmptyString(pARespM.getSubDistrict()))
			str.append(pARespM.getSubDistrict()).append(" ");
		
		if(!ORIGUtility.isEmptyString(pARespM.getProvince()))
			str.append(pARespM.getProvince()).append(" ");
		
		if(!ORIGUtility.isEmptyString(pARespM.getPostCode()))
			str.append(pARespM.getPostCode());
		
		return str.toString();
	}
	public static PARespM GetAddressType(Vector<PARespM> nAddressVect ,String addressType){
		for(PARespM paRespM : nAddressVect) {
			if(null != paRespM.getAddressType() && paRespM.getAddressType().equals(addressType)){
				return paRespM;
			}
		}
		return new PARespM();
	}
	public static String GetResultDesc(String resultCode){
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		return cacheUtil.getNaosCacheDisplayNameDataM(72, resultCode);
	}
}
