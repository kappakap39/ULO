package com.eaf.orig.shared.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
//import com.eaf.cache.data.CacheDataM;
import com.eaf.j2ee.pattern.util.DisplayFormatUtil;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.j2ee.pattern.view.form.exception.InvalidDateFormatException;
import com.eaf.orig.cache.CacheDataInf;
import com.eaf.orig.cache.properties.AddressTypeProperties;
//import com.eaf.orig.cache.properties.CampaignCacheProperties;
import com.eaf.orig.cache.properties.CollectionCodeProperties;
import com.eaf.orig.cache.properties.DefaultDateProperties;
import com.eaf.orig.cache.properties.GeneralParamProperties;
import com.eaf.orig.cache.properties.InternalCheckerProperties;
import com.eaf.orig.cache.properties.ParameterDetailCodeProperties;
import com.eaf.orig.cache.properties.ReasonProperties;
import com.eaf.orig.cache.properties.SubFormMainCusTypeProperties;
//import com.eaf.orig.master.shared.model.ApprovAuthorM;
//import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM;
//import com.eaf.orig.master.shared.model.OrigPolicyLevelGroupTotalDataM;
import com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM;
//import com.eaf.orig.master.shared.model.OrigPolicyRulesExceptionDataM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.master.shared.model.PolicyRulesDataM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigPolicyRulesExceptionDAO;
//import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
//import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;
//import com.eaf.orig.shared.dao.utility.EjbUtil;
//import com.eaf.orig.shared.dao.utility.UtilityDAO;
//import com.eaf.orig.shared.dao.utility.exception.EjbUtilException;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.AppraisalDataM;
import com.eaf.orig.shared.model.BankDataM;
//import com.eaf.orig.shared.model.CampaignDataM;
import com.eaf.orig.shared.model.CardInformationDataM;
import com.eaf.orig.shared.model.ChangeNameDataM;
import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.model.CorperatedDataM;
import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.model.DocumentCheckListMapDataM;
import com.eaf.orig.shared.model.FeeInformationDataM;
import com.eaf.orig.shared.model.InstallmentDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.OtherNameDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.ReasonDataM;
import com.eaf.orig.shared.model.SLADataM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.wf.shared.ORIGWFConstant;
//import com.eaf.xrules.shared.constant.XRulesConstant;
//import com.eaf.xrules.shared.model.XRulesPolicyRulesDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Administrator
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class ORIGUtility {
	
    private static ORIGUtility me;

    private static Logger logger = Logger.getLogger(ORIGUtility.class);

    /**
     * Create instance of ORIGUtility
     */
    public static ORIGUtility getInstance() {
        if (me == null) {
            me = new ORIGUtility();
        }
        return me;
    }

    /**
     * Convert String to Int defualt is zero
     */
    public int stringToInt(String str) {
        try {
            if (str != null) {
                str = replaceAll(str, ",", "");
                return Integer.parseInt(str);
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Convert int to String defualt is empty
     */
    public String intToString(int intNumber) {
        try {
            return Integer.toString(intNumber);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convert String to float defualt is zero
     */
    public float stringToFloat(String str) {
        try {
            if (str != null) {
                str = replaceAll(str, ",", "");
                return Float.parseFloat(str);
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Convert String to double defualt is zero
     */
    public double stringToDouble(String str) {
        try {
            if (str != null) {
                str = replaceAll(str, ",", "");
                return Double.parseDouble(str);
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Convert float to String defualt is empty
     */
    public String floatToString(float fltNumber) {
        try {
            return Float.toString(fltNumber);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convert double to String defualt is empty
     */
    public String doubleToString(double fltNumber) {
        try {
            return Double.toString(fltNumber);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convert String to Timestamp defualt is empty
     */
    public Date stringToDate(String strDate) {
        try {
            return convertUtilToSqlDate(DisplayFormatUtil.StringToDate(strDate, "dd/MM/yyyy"));
        } catch (Exception e) {
            return null;
        }
    }

    public java.sql.Date convertUtilToSqlDate(java.util.Date utilDate) {
        String strDate = DisplayFormatUtil.datetoString(utilDate);

        if ((strDate != null) && !strDate.equals("")) {
            strDate = DisplayFormatUtil.stringDelChar(strDate, '/');

            java.util.Date tmpDate = null;

            try {
                tmpDate = DisplayFormatUtil.StringToDate(strDate, "ddMMyyyy");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return new java.sql.Date(tmpDate.getTime());
        }

        return null;
    }

    /**
     * Replace String
     */
    public static String replaceAll(String inputString, String patternString, String replaceString) {

        if ((patternString == null) || patternString.equals("")) {
            return inputString;
        }

        if (replaceString == null) {
            return inputString;
        }

        StringBuffer result = new StringBuffer();
        int startIdx = 0;
        int idxOld = 0;

        try {

            while ((idxOld = inputString.indexOf(patternString, startIdx)) >= 0) {
                result.append(inputString.substring(startIdx, idxOld));
                result.append(replaceString);
                startIdx = idxOld + patternString.length();
            }

            result.append(inputString.substring(startIdx));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
    @Deprecated
    public boolean isContainJobState(String jobState) {
        String[] aryJobState = new String[] { "ST0300", "ST0301", "ST0302", "ST0400", "ST0401", "ST0402", "ST0403", "ST1000", "ST1200", "ST1201", "ST1202",
                "ST1203", "ST1205", "ST1206", "ST1207", "ST1208", "ST1209", "ST1210", "ST1300", "ST1301", "ST1302", "ST1400", "ST1401", "ST1402", "ST1403" };

        if (jobState != null) {
            for (int i = 0; i < aryJobState.length; i++) {
                if (jobState.equals(aryJobState[i])) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param vtModel
     * @param sortMethod
     * 
     * @return
     */
    public static Vector sortObjectInModel(Vector vtModel, String sortMethod) {

        if ((vtModel == null) || (vtModel.size() == 0)) {
            return new Vector();
        }

        Object[] objArray = new Object[vtModel.size()];

        for (int i = 0; i < vtModel.size(); i++) {
            Object obj = vtModel.get(i);
            Class cls = obj.getClass();
            objArray[i] = getReturnMethod(cls, (Object) obj, sortMethod, null);
        }

        java.util.Arrays.sort(objArray);

        for (int i = 0; i < objArray.length; i++) {
            Object strKey = objArray[i];

            for (int j = 0; j < vtModel.size(); j++) {
                Object obj = vtModel.get(j);
                Class cls = obj.getClass();
                Object objModel = getReturnMethod(cls, (Object) obj, sortMethod, null);

                if (objModel.equals(strKey)) {
                    Stack stack = new Stack();
                    stack.push(vtModel.get(i));
                    stack.push(vtModel.get(j));

                    vtModel.setElementAt(stack.pop(), i);
                    vtModel.setElementAt(stack.pop(), j);
                }
            }
        }

        return vtModel;
    }

    /**
     * @param vtModel
     * @param sortMethod
     * 
     * @return
     */
    public static Vector sortObjectInModelDESC(Vector vtModel, String sortMethod) {
        Vector vtObjectModel = sortObjectInModel(vtModel, sortMethod);
        Vector tmpVector = new Vector();

        if (vtObjectModel != null) {

            for (int i = (vtObjectModel.size() - 1); i >= 0; --i) {
                tmpVector.add(vtObjectModel.get(i));
            }

            vtObjectModel = (Vector) tmpVector.clone();
        }

        return vtObjectModel;
    }

    /**
     * @param className
     * @param obj
     * @param methodName
     * @param objParam
     * 
     * @return
     */
    private static Object getReturnMethod(Class className, Object obj, String methodName, Object[] objParam) {

        try {
            java.lang.reflect.Method[] methods = className.getMethods();

            for (int i = 0; i < methods.length; i++) {

                if ((methods[i].getName() != null) && (methods[i].getName().equalsIgnoreCase(methodName))) {
                    Object ret = methods[i].invoke(obj, objParam);
                    ret = (ret != null) ? ret : "";

                    return ret;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static HashMap filterSubformByMainCusType(HashMap allSubforms, String mainCusType) {
        logger.debug("+++++++++++++++++++++ filterSubformByMainCusType ++++++++++++++++++++++++");
        logger.debug("allSubforms="+allSubforms);
        mainCusType = (mainCusType != null) ? mainCusType : "";
        HashMap h = TableLookupCache.getCacheStructure();
        Vector allSubFormMainCusType = (Vector) (h.get("SubFormByMainCusTypeCacheDataM"));
        HashMap hsSubFormMainCusType = new HashMap();
        if (allSubFormMainCusType != null) {
            logger.debug("in put ");
            for (int i = 0; i < allSubFormMainCusType.size(); i++) {
                SubFormMainCusTypeProperties subform = (SubFormMainCusTypeProperties) allSubFormMainCusType.get(i);

                logger.debug("put subform = " + subform.getSubFormID());
                hsSubFormMainCusType.put((String) subform.getSubFormID(), subform);
            }
        }

        Vector vtSubforms = new Vector(allSubforms.values());
        HashMap newAllSubforms = new HashMap();

        logger.debug("Subform size = " + ((allSubforms != null) ? allSubforms.size() : 0));

        if (vtSubforms != null) {
            for (int i = 0; i < vtSubforms.size(); i++) {
                ORIGSubForm subForm = (ORIGSubForm) vtSubforms.get(i);
                logger.debug("++checked subform = " + subForm.getSubFormID());
                if (hsSubFormMainCusType != null && hsSubFormMainCusType.containsKey(subForm.getSubFormID())) {
                    SubFormMainCusTypeProperties hsMainCusType = (SubFormMainCusTypeProperties) hsSubFormMainCusType.get(subForm.getSubFormID());
                    if (mainCusType.equalsIgnoreCase(hsMainCusType.getMainCustomerType())) {
                        newAllSubforms.put(subForm.getSubFormID(), subForm);
                        System.out.println("====> save new Subform = " + subForm.getSubFormID());
                    } else {
                        continue;
                    }
                } else {
                    newAllSubforms.put(subForm.getSubFormID(), subForm);
                    logger.debug("====> save new Subform = " + subForm.getSubFormID());
                }

            }
        }

        logger.debug("New Subform size = " + ((newAllSubforms != null) ? newAllSubforms.size() : 0));

        return newAllSubforms;
    }

    public static boolean isContainSubform(HashMap allSubforms, String subformName) {
        logger.debug("++++++++++++++++++Start  isContainSubform  ++++++++++++++++++++++++++");
        logger.debug("784allSubforms = " + allSubforms);
        logger.debug("784subformName = " + subformName);
        boolean isError = false;
        try {
            if (allSubforms != null && subformName != null) {
                Vector vtAllSubForms = new Vector(allSubforms.values());
                for (int i = 0; i < vtAllSubForms.size(); i++) {
                    ORIGSubForm subForm = (ORIGSubForm) vtAllSubForms.get(i);
                    String subformID = (subForm != null) ? subForm.getSubFormID() : "";
                    logger.debug("SubformName of vector = " + subForm.getSubFormID());
                    logger.debug("SubformName of param = " + subformName);
                    logger.debug("Result Check subform = " + ((subformID.equalsIgnoreCase(subformName)) ? "Yes" : "No"));
                    if (subformID != null && subformID.equalsIgnoreCase(subformName)) {
                        logger.debug("Validate  Subform Name= " + subformID);
                        isError = true;
                        break;
                    }
                    //					if(subformName != null && subForm != null &&
                    // subformName.trim().equalsIgnoreCase(subForm.getSubFormID())){
                    //						logger.debug("Validate Subform Name= " +
                    // subForm.getSubFormID());
                    //						isError =true;
                    //						break;
                    //					}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        logger.debug("++++++++++++++++++End  isContainSubform  ++++++++++++++++++++++++++");
        return isError;
    }

    /**
     * Convert String to int
     * 
     * @param strInt
     * 
     * @return
     */
    public static int getInt(String strInt) {

        try {

            if ((strInt != null) && (strInt.length() > 0)) {
                return Integer.parseInt(strInt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Convert String to double
     * 
     * @param strDouble
     * 
     * @return
     */
    public static double getDouble(String strDouble) {

        try {
            strDouble = replaceAll(strDouble, ",", "");

            if ((strDouble != null) && (strDouble.length() > 0)) {
                return Double.parseDouble(strDouble);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Clone object using for coppy object by value of front end
     * 
     * @param obj
     * 
     * @return
     */
    public static Object cloneObject(Object obj) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();

            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);

            return ois.readObject();

        } catch (IOException e) {

            try {

                if (oos != null) {
                    oos.close();
                }

                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e2) {

                e2.printStackTrace();
            }

            e.printStackTrace();
        } catch (ClassNotFoundException e1) {

            e1.printStackTrace();

        }

        return null;
    }

    /**
     * - input Date format is dd/mm/yyyy
     * 
     * @param strDate
     * 
     * @return String array of date
     */
    public static String[] splitDate(String strDate) {
        String strSplitSimbo = "";

        if (strDate.indexOf("/") >= 0) {
            strSplitSimbo = "/";

        } else if (strDate.indexOf("-") >= 0) {
            strSplitSimbo = "-";
        }

        StringTokenizer strToken = new StringTokenizer(strDate, strSplitSimbo);
        String[] arrayDate = new String[3];

        if ((arrayDate != null) && (strToken.countTokens() == 3) && (arrayDate.length == 3)) {

            arrayDate[0] = (String) strToken.nextElement(); // Date
            arrayDate[1] = (String) strToken.nextElement(); // Month
            arrayDate[2] = (String) strToken.nextElement(); // Year
        }

        return arrayDate;
    }
    
    /**
     * @author septemwi  
     * @deprecated
     * {@link ORIGCacheUtil#loadCacheByName(String)}
     * */
    @Deprecated
    public Vector loadCacheByName(String cacheName) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector dataVect = (Vector) (h.get(cacheName));
        return dataVect;
    }
    
    /**
     * @author septemwi 
     * @deprecated use loadCacheByActive(String cacheName) ORIGCacheUtil 
     * */
    public Vector loadCacheByActive(String cacheName){
    	HashMap h = TableLookupCache.getCacheStructure();
        Vector dataVect = (Vector) (h.get(cacheName));
        Vector dataVect2 = new Vector();
        CacheDataInf obj = null;
        for(int i=0;i<dataVect.size();i++){
        obj = (CacheDataInf)dataVect.get(i);
        if(OrigConstant.Status.STATUS_ACTIVE.equalsIgnoreCase(obj.getActiveStatus().trim())){
        	dataVect2.add(obj);
        	logger.debug(obj.getCode());
        }
    }
        
        return dataVect2;
    }
    /**
     * @author septemwi 
     * @deprecated use loadCacheByNameAndCheckStatus(String cacheName, String id) ORIGCacheUtil 
     * */
    public Vector loadCacheByNameAndCheckStatus(String cacheName, String id) {
    	HashMap h = TableLookupCache.getCacheStructure();
        Vector dataVect = (Vector) (h.get(cacheName));
        String inActive = "I";
        CacheDataInf obj = null;
        Vector v = (Vector) dataVect.clone();
        //Vector index = new Vector();
        int count = v.size();
        int countInAct = 0;
        for(int i=0;i<count-countInAct;i++) {
        	obj = (CacheDataInf) v.get(i);
        	String code = obj.getCode();
        	//String value = obj.getThDesc();
        	String status = obj.getActiveStatus();
        	if(!code.equals(id) && status.equals(inActive)) {
        		v.removeElementAt(i);
        		countInAct++;
        		i--;
        	}
        }
        return v;
    }
    /**
     * @author septemwi 
     * @deprecated use loadCacheByNameByBusClass(String cacheName, String BusinessClass) ORIGCacheUtil 
     * */
    public Vector loadCacheByNameByBusClass(String cacheName, String BusinessClass) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector dataVect = (Vector) (h.get(cacheName));
        Vector resultVect = new Vector();
        
        String[] arrayBusClass;
        String orgU = "";
		String proU = "";
		String chaU = "";
        if (BusinessClass!=null){
        	arrayBusClass = BusinessClass.split("_");
        	if (arrayBusClass.length==3){
	        	orgU = arrayBusClass[0];
	        	proU = arrayBusClass[1];
	        	chaU = arrayBusClass[2];
        	}
        }
        
        if (dataVect!=null && dataVect.size()>0){
	        for (int i=0;i<dataVect.size();i++){
	        	CacheDataInf temp = (CacheDataInf)dataVect.get(i);
				String orgT = "";
				String proT = "";
				String chaT = "";
				String[] arrayTempBusClass = temp.getBusinessClass().split("_");
				if (arrayTempBusClass.length==3){
					orgT = arrayTempBusClass[0];
					proT = arrayTempBusClass[1];
					chaT = arrayTempBusClass[2];
				}
				if ((orgU.equalsIgnoreCase("ALL") ? true : (orgT.equalsIgnoreCase("ALL"))) || (orgU.equalsIgnoreCase(orgT))) {
					if ((proU.equalsIgnoreCase("ALL") ? true : (proT.equalsIgnoreCase("ALL"))) || (proU.equalsIgnoreCase(proT))) {
						if ((chaU.equalsIgnoreCase("ALL") ? true : (chaT.equalsIgnoreCase("ALL"))) || (chaU.equalsIgnoreCase(chaT))) {
							resultVect.add(dataVect.get(i));
						}
					}
				}
	        }
        }
        return resultVect;
    }
    /**
     * @author septemwi
     * @deprecated use loadCacheByNameByBusClassAndCheckStatus(String cacheName, String BusinessClass, String value) ORIGCacheUtil 
     * */
    public Vector loadCacheByNameByBusClassAndCheckStatus(String cacheName, String BusinessClass, String value) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector dataVect = (Vector) (h.get(cacheName));
        Vector resultVect = new Vector();
        
        String[] arrayBusClass;
        String orgU = "";
		String proU = "";
		String chaU = "";
        if (BusinessClass!=null){
        	arrayBusClass = BusinessClass.split("_");
        	if (arrayBusClass.length==3){
	        	orgU = arrayBusClass[0];
	        	proU = arrayBusClass[1];
	        	chaU = arrayBusClass[2];
        	}
        }
        String active = "A";
        if (dataVect!=null && dataVect.size()>0){
	        for (int i=0;i<dataVect.size();i++){
	        	CacheDataInf temp = (CacheDataInf)dataVect.get(i);
	        	String code = temp.getCode();
	        	//String value = obj.getThDesc();
	        	String status = temp.getActiveStatus();
	        	if(code.equals(value) || status.equals(active)) {
		        	String orgT = "";
					String proT = "";
					String chaT = "";
					String[] arrayTempBusClass = temp.getBusinessClass().split("_");
					if (arrayTempBusClass.length==3){
						orgT = arrayTempBusClass[0];
						proT = arrayTempBusClass[1];
						chaT = arrayTempBusClass[2];
					}
					if ((orgU.equalsIgnoreCase("ALL") ? true : (orgT.equalsIgnoreCase("ALL"))) || (orgU.equalsIgnoreCase(orgT))) {
						if ((proU.equalsIgnoreCase("ALL") ? true : (proT.equalsIgnoreCase("ALL"))) || (proU.equalsIgnoreCase(proT))) {
							if ((chaU.equalsIgnoreCase("ALL") ? true : (chaT.equalsIgnoreCase("ALL"))) || (chaU.equalsIgnoreCase(chaT))) {
								resultVect.add(dataVect.get(i));
							}
						}
					}
	        	}
	        }
        }
        return resultVect;
    }

    /**
     * Get personalInfo by type
     * 
     * @param applicationDataM
     * @param personalType
     * @return PersonalInfoDataM
     */
    @Deprecated
    public PersonalInfoDataM getPersonalInfoByType(ApplicationDataM applicationDataM, String personalType) {
        Vector personalInfoVect = applicationDataM.getPersonalInfoVect();
        PersonalInfoDataM personalInfoDataM = null;
        if (personalInfoVect != null && personalInfoVect.size() > 0) {
            for (int i = 0; i < personalInfoVect.size(); i++) {
                personalInfoDataM = (PersonalInfoDataM) personalInfoVect.get(i);
                if (personalInfoDataM!=null && personalType.equals(personalInfoDataM.getPersonalType())) {
                    return personalInfoDataM;
                } else {
                    personalInfoDataM = null;
                }
            }
        }
        return personalInfoDataM;
    }

    /**
     * Get personalInfo by type and seq
     * 
     * @param applicationDataM
     * @param personalType
     * @param seq
     * @return PersonalInfoDataM
     */
    @Deprecated
    public PersonalInfoDataM getPersonalInfoByTypeAndSeq(ApplicationDataM applicationDataM, String personalType, int seq) {
        Vector personalInfoVect = applicationDataM.getPersonalInfoVect();
        PersonalInfoDataM personalInfoDataM = null;
        if (personalInfoVect != null && personalInfoVect.size() > 0) {
            for (int i = 0; i < personalInfoVect.size(); i++) {
                personalInfoDataM = (PersonalInfoDataM) personalInfoVect.get(i);
                if (personalType.equals(personalInfoDataM.getPersonalType()) && seq == personalInfoDataM.getPersonalSeq()) {
                    return personalInfoDataM;
                } else {
                    personalInfoDataM = null;
                }
            }
        }
        return personalInfoDataM;
    }

    /**
     * Get personalInfo by type
     * 
     * @param applicationDataM
     * @param personalType
     * @return Vector of personal
     */
    @Deprecated
    public Vector getVectorPersonalInfoByType(ApplicationDataM applicationDataM, String personalType) {
        Vector personalInfoVect = applicationDataM.getPersonalInfoVect();
        PersonalInfoDataM personalInfoDataM = null;
        Vector personalVect = new Vector();
        if (personalInfoVect != null && personalInfoVect.size() > 0) {
            for (int i = 0; i < personalInfoVect.size(); i++) {
                personalInfoDataM = (PersonalInfoDataM) personalInfoVect.get(i);
                if (personalType.equals(personalInfoDataM.getPersonalType())) {
                    personalVect.add(personalInfoDataM);
                }
            }
        }
        return personalVect;
    }

    /**
     * Get data form cache by code
     * 
     * @param code
     * @return Vector
     */
    @Deprecated
    public Vector getMasterDataFormCache(String code) {
        HashMap h = TableLookupCache.getCacheStructure();
        //Vector paramHeader = (Vector) (h.get("ParameterHeaderCode"));
        Vector paramDetail = (Vector) (h.get("ParameterDetailCode"));
        Vector dataVect = new Vector();
        if (paramDetail != null) {
            for (int i = 0; i < paramDetail.size(); i++) {
                ParameterDetailCodeProperties detailCodeProperties = (ParameterDetailCodeProperties) paramDetail.get(i);
                if (code.equals(detailCodeProperties.getPRMTYP())) {
                    dataVect.add(detailCodeProperties);
                }
            }
        }
        logger.debug("Code : "+code +"   dataVect.size() : "+dataVect.size());
        return dataVect;
    }

    /** Check Empty String* */
    public static boolean isEmptyString(String data){
    	if(data == null || data.trim().equals("null") || data.length() == 0 ||  data.trim().length() == 0){
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isEmptyString(Object obj) {
    	String data = (String) obj;    	
    	if(data == null || data.trim().equals("null") || data.length() == 0 ||  data.trim().length() == 0){
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isEmptyVector(Object obj){ 
    	Vector vect = (Vector) obj;    	
       	if(null == vect || vect.size() == 0) 
       		return true;
       	return false;
    }
    
    /**
     * Get address by type
     * 
     * @param applicationDataM
     * @param addressType
     * @return AddressDataM
     */
    @Deprecated
    public AddressDataM getAddressByType(PersonalInfoDataM personalInfoDataM, String addressType) {
        Vector addressVect = personalInfoDataM.getAddressVect();
        AddressDataM addressDataM = null;
        logger.debug("addressVect.size() = " + addressVect.size());
        logger.debug("addressType = " + addressType);
        if (addressVect != null && addressVect.size() > 0) {
            for (int i = 0; i < addressVect.size(); i++) {
                addressDataM = (AddressDataM) addressVect.get(i);
                logger.debug("addressDataM.getAddressType() = " + addressDataM.getAddressType());
                if (addressType.equals(addressDataM.getAddressType())) {
                    return addressDataM;
                } else {
                    addressDataM = null;
                }
            }
        }
        return addressDataM;
    }

    /**
	 * Get fianance by seq
	 * 
	 * @param financeVect
	 * @param seq
	 * @return BankDataM
	 */
    @Deprecated
	public BankDataM getFinanceBySeq(Vector financeVect, int seq) {
	    BankDataM bankDataM = null;
	    if (financeVect != null) {
	        for (int i = 0; i < financeVect.size(); i++) {
	            bankDataM = (BankDataM) financeVect.get(i);
	            if (bankDataM.getSeq() == seq) {
	                return bankDataM;
	            } else {
	                bankDataM = null;
	            }
	        }
	    }
	    return bankDataM;
	}

	/**
     * Get address by type and seq
     * 
     * @param applicationDataM
     * @param addressType
     * @param seq
     * @return AddressDataM
     */
    @Deprecated
    public AddressDataM getAddressByTypeAndSeq(PersonalInfoDataM personalInfoDataM, String addressType, int seq) {
        Vector addressVect = personalInfoDataM.getAddressVect();
        AddressDataM addressDataM = null;
        if (addressVect != null && addressVect.size() > 0) {
            for (int i = 0; i < addressVect.size(); i++) {
                addressDataM = (AddressDataM) addressVect.get(i);
                if (addressType.equals(addressDataM.getAddressType()) && seq == addressDataM.getAddressSeq()) {
                    return addressDataM;
                } else {
                    addressDataM = null;
                }
            }
        }
        return addressDataM;
    }

    /**
     * Get address by seq
     * 
     * @param applicationDataM
     * @param seq
     * @return AddressDataM
     */
    @Deprecated
    public AddressDataM getAddressBySeq(PersonalInfoDataM personalInfoDataM, int seq) {
        Vector addressVect = personalInfoDataM.getAddressVect();
        AddressDataM addressDataM = null;
        if (addressVect != null && addressVect.size() > 0) {
            for (int i = 0; i < addressVect.size(); i++) {
                addressDataM = (AddressDataM) addressVect.get(i);
                if (seq == addressDataM.getAddressSeq()) {
                    return addressDataM;
                } else {
                    addressDataM = null;
                }
            }
        }
        return addressDataM;
    }
    @Deprecated
    public AddressDataM getAddressBySeq(Vector addressVect, int seq) {
        AddressDataM addressDataM = null;
        if (addressVect != null && addressVect.size() > 0) {
            for (int i = 0; i < addressVect.size(); i++) {
                addressDataM = (AddressDataM) addressVect.get(i);
                if (seq == addressDataM.getAddressSeq()) {
                    return addressDataM;
                } else {
                    addressDataM = null;
                }
            }
        }
        return addressDataM;
    }

    /**
     * Get address by type
     * 
     * @param applicationDataM
     * @param addressType
     * @return Vector of address
     */
    @Deprecated
    public Vector getVectorAddressByType(PersonalInfoDataM personalInfoDataM, String addressType) {
        Vector addressVect = personalInfoDataM.getAddressVect();
        AddressDataM addressDataM = null;
        Vector addressVectTmp = new Vector();
        if (addressVect != null && addressVect.size() > 0) {
            for (int i = 0; i < addressVect.size(); i++) {
                addressDataM = (AddressDataM) addressVect.get(i);
                if (addressType.equals(addressDataM.getAddressType())) {
                    addressVectTmp.add(addressDataM);
                }
            }
        }
        return addressVectTmp;
    }

    /**
     * Get change name by seq
     * 
     * @param personalInfoDataM
     * @param seq
     * @return ChangeNameDataM
     */
    @Deprecated
    public ChangeNameDataM getChangNameBySeq(PersonalInfoDataM personalInfoDataM, int seq) {
        Vector changeNameVect = personalInfoDataM.getChangeNameVect();
        ChangeNameDataM changeNameDataM = null;
        if (changeNameVect != null && changeNameVect.size() > 0) {
            for (int i = 0; i < changeNameVect.size(); i++) {
                changeNameDataM = (ChangeNameDataM) changeNameVect.get(i);
                if (seq == changeNameDataM.getSeq()) {
                    return changeNameDataM;
                } else {
                    changeNameDataM = null;
                }
            }
        }
        return changeNameDataM;
    }

    /**
     * Get address by ID
     * 
     * @param personalInfoDataM
     * @param id
     * @return ChangeNameDataM
     */
    @Deprecated
    public ChangeNameDataM getChangNameById(PersonalInfoDataM personalInfoDataM, String id) {
        Vector changeNameVect = personalInfoDataM.getChangeNameVect();
        ChangeNameDataM changeNameDataM = null;
        if (changeNameVect != null && changeNameVect.size() > 0) {
            for (int i = 0; i < changeNameVect.size(); i++) {
                changeNameDataM = (ChangeNameDataM) changeNameVect.get(i);
                if (id.equals(changeNameDataM.getIdNo())) {
                    return changeNameDataM;
                } else {
                    changeNameDataM = null;
                }
            }
        }
        return changeNameDataM;
    }

    /*
     * Get otherNameDataM by seq @applicationDataM @param seq @return
     * otherNameDataM by PoNg
     */
    @Deprecated
    public OtherNameDataM getOtherNameBySeq(ApplicationDataM applicationDataM, int seq) {
        Vector otherNameVect = applicationDataM.getOtherNameDataM();
        OtherNameDataM otherNameDataM = null;
        if (otherNameVect != null && otherNameVect.size() > 0) {
            for (int i = 0; i < otherNameVect.size(); i++) {
                otherNameDataM = (OtherNameDataM) otherNameVect.get(i);
                if (seq == otherNameDataM.getSeq()) {
                    return otherNameDataM;
                } else {
                    otherNameDataM = null;
                }
            }
        }
        return otherNameDataM;
    }

    /*
     * Get corperatedDataM by seq @personalInfoDataM @param seq @return
     * corperatedDataM by PoNg
     */
    @Deprecated
    public CorperatedDataM getCorperatedByYear(PersonalInfoDataM personalInfoDataM, String year) {
        Vector corperatedVect = personalInfoDataM.getCorperatedVect();
        CorperatedDataM corperatedDataM = null;
        if (corperatedVect != null && corperatedVect.size() > 0) {
            for (int i = 0; i < corperatedVect.size(); i++) {
                corperatedDataM = (CorperatedDataM) corperatedVect.get(i);
                if (year.equals(corperatedDataM.getYear())) {
                    return corperatedDataM;
                } else {
                    corperatedDataM = null;
                }
            }
        }
        return corperatedDataM;
    }
    @Deprecated
    public CorperatedDataM getCorperatedBySeq(Vector corperatedVect, int seq) {
        CorperatedDataM corperatedDataM = null;
        if (corperatedVect != null && corperatedVect.size() > 0) {
            for (int i = 0; i < corperatedVect.size(); i++) {
                corperatedDataM = (CorperatedDataM) corperatedVect.get(i);
                if (corperatedDataM.getSeq() == seq) {
                    return corperatedDataM;
                } else {
                    corperatedDataM = null;
                }
            }
        }
        return corperatedDataM;
    }

    /*
     * check year in corperated @personalInfoDataM @param year @return true if
     * already hv that year @return false if don't hv that year by PoNg
     */
    @Deprecated
    public boolean haveCorperatedYear(PersonalInfoDataM personalInfoDataM, String year) {
        Vector corperatedVect = personalInfoDataM.getCorperatedTmpVect();
        CorperatedDataM corperatedDataM = null;
        if (corperatedVect != null && corperatedVect.size() > 0) {
            for (int i = 0; i < corperatedVect.size(); i++) {
                corperatedDataM = (CorperatedDataM) corperatedVect.get(i);
                if (year.equals(corperatedDataM.getYear())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get loan by type and seq
     * 
     * @param applicationDataM
     * @param seq
     * @return LoanDataM
     */
    @Deprecated
    public LoanDataM getLoanBySeq(PersonalInfoDataM personalInfoDataM, int seq) {
        Vector loanVect = personalInfoDataM.getChangeNameVect();
        LoanDataM loanDataM = null;
        if (loanVect != null && loanVect.size() > 0) {
            for (int i = 0; i < loanVect.size(); i++) {
                loanDataM = (LoanDataM) loanVect.get(i);
                if (seq == loanDataM.getSeq()) {
                    return loanDataM;
                } else {
                    loanDataM = null;
                }
            }
        }
        return loanDataM;
    }

    public BigDecimal stringToBigDecimal(String data) {
        if (!isEmptyString(data)) {
            try {
                return ORIGDisplayFormatUtil.replaceCommaForBigDecimal(data);
            } catch (Exception e) {
                return new BigDecimal(0);
            }
        } else {
            return new BigDecimal(0);
        }
    }
    
    @Deprecated
    public int getPersonalSizeByType(ApplicationDataM applicationDataM, String personalType) {
        int size = 0;
        Vector personalVect = getVectorPersonalInfoByType(applicationDataM, personalType);
        if (personalVect != null) {
            size = personalVect.size();
        }
        return size;
    }
    @Deprecated
    public ValueListM getDataFromCache(Vector allCacheDataVect, ValueListM valueListM) {
        try {
            Vector resultVect = new Vector();
            int atPage = valueListM.getAtPage();
            int itemsPerPage = valueListM.getItemsPerPage();
            int startInx = (atPage - 1) * 10;
            int endInx = itemsPerPage * atPage;
            int size = 0;
            if (allCacheDataVect != null) {
                size = allCacheDataVect.size();
            }
            if (size > 0) {
                logger.debug("startInx = " + startInx);
                logger.debug("itemsPerPage = " + itemsPerPage);
                CacheDataInf cacheDataInf;
                for (int i = startInx; i < endInx; i++) {
                    if (i <= size - 1) {
                        cacheDataInf = (CacheDataInf) allCacheDataVect.get(i);
                        resultVect.add(cacheDataInf);
                    }
                }
            }
            valueListM.setResult(resultVect);
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return valueListM;
    }

    /**
     * Get data form cache by cache and code
     * 
     * @param code
     * @return Vector
     */
    @Deprecated
    public Vector getDataFormCacheByValue(String cacheName, String value) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector dataVect = (Vector) (h.get(cacheName));
        Vector resultVect = new Vector();
        if (!isEmptyString(value)) {
            if (dataVect != null) {
                for (int i = 0; i < dataVect.size(); i++) {
                    CacheDataInf cacheDataInf = (CacheDataInf) dataVect.get(i);
                    if (value.equals(cacheDataInf.getCode())) {
                        resultVect.add(cacheDataInf);
                    }
                }
            }
        } else {
            return dataVect;
        }
        return resultVect;
    }
    
    /**
    *@Deprecated use ORIGCacheUtil.getGeneralParamByCode(String code)instead 
    **/
    public String getGeneralParamByCode(String code) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector dataVect = (Vector) (h.get("GeneralParamDataM"));
        String result = null;
        if (dataVect != null) {
            for (int i = 0; i < dataVect.size(); i++) {
                GeneralParamProperties generalParamProperties = (GeneralParamProperties) dataVect.get(i);
                if (code.equals(generalParamProperties.getCode())) {
                    return generalParamProperties.getParamvalue();
                }
            }
        }
        return result;
    }
    
    @Deprecated
    public Vector getDocumentListByCustomerType(String customerType) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector documentVect = (Vector) (h.get("DocumentList"));
        Vector documentMapVect = (Vector) (h.get("DocumentList_Map"));
        Vector result = new Vector();
        if (documentVect != null) {
            DocumentCheckListDataM checkListDataM;
            DocumentCheckListMapDataM checkListMapDataM;
            for (int i = 0; i < documentMapVect.size(); i++) {
                //checkListDataM = (DocumentCheckListDataM) documentVect.get(i);
                checkListMapDataM = (DocumentCheckListMapDataM) documentMapVect.get(i);
                //logger.debug("checkListDataM.getCustomerType() =
                // "+checkListDataM.getCustomerType());
                if (customerType.equals(checkListMapDataM.getCUST_TYPE())) {
                	String docID = checkListMapDataM.getDOC_ID();
                	for(int j = 0; j < documentVect.size(); j++) {
                		checkListDataM = (DocumentCheckListDataM) documentVect.get(j);
                		if(checkListDataM.getDocTypeId().equals(docID) && checkListDataM.getACTIVE_STATUS().equals(ORIGCacheUtil.ACTIVE)) {
                			result.add(checkListDataM);
                			break;
                		}
                	}
                }
            }
        }
        return result;
    }
    @Deprecated
    public Vector getAddressByCustomerType(String customerType) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector addressVect = (Vector) (h.get("AddressType"));
        Vector result = new Vector();
        if (addressVect != null) {
            AddressTypeProperties addressTypeProperties;
            for (int i = 0; i < addressVect.size(); i++) {
                addressTypeProperties = (AddressTypeProperties) addressVect.get(i);
                if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(customerType) || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(customerType)) {
                    if (("Y").equals(addressTypeProperties.getINDADRTYP())) {
                        result.add(addressTypeProperties);
                    }
                } else {
                    if (("Y").equals(addressTypeProperties.getCORADRTYP())) {
                        result.add(addressTypeProperties);
                    }
                }
            }
        }
        return result;
    }
    @Deprecated
    public Vector getAddressByCustomerTypeAndNotUsed(Vector addressVect, String customerType, String currentAddress) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector addressTypeVect = (Vector) (h.get("AddressType"));
        Vector result = new Vector();
        boolean matchType;
        if (addressTypeVect != null) {
            AddressTypeProperties addressTypeProperties;
            for (int i = 0; i < addressTypeVect.size(); i++) {
                matchType = false;
                AddressDataM addressDataM;
                addressTypeProperties = (AddressTypeProperties) addressTypeVect.get(i);
                for (int j = 0; j < addressVect.size(); j++) {
                    addressDataM = (AddressDataM) addressVect.get(j);
                    if (addressTypeProperties.getADRTYP().equals(addressDataM.getAddressType()) && !addressTypeProperties.getADRTYP().equals(currentAddress)) {
                        matchType = true;
                    }
                }
                if (!matchType) {
                    if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(customerType) || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(customerType)) {
                        if (("Y").equals(addressTypeProperties.getINDADRTYP())) {
                            result.add(addressTypeProperties);
                        }
                    } else {
                        if (("Y").equals(addressTypeProperties.getCORADRTYP())) {
                            result.add(addressTypeProperties);
                        }
                    }
                }
            }
        }
        return result;
    }
    @Deprecated
    public Vector getAddressByCustomerTypePersonalTypeAndNotUsed(Vector addressVect, String customerType, String currentAddress, String personalType) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector addressTypeVect = (Vector) (h.get("AddressType"));
        Vector result = new Vector();
        boolean matchType;
        if (addressTypeVect != null) {
            AddressTypeProperties addressTypeProperties;
            for (int i = 0; i < addressTypeVect.size(); i++) {
                matchType = false;
                AddressDataM addressDataM;
                addressTypeProperties = (AddressTypeProperties) addressTypeVect.get(i);
                for (int j = 0; j < addressVect.size(); j++) {
                    addressDataM = (AddressDataM) addressVect.get(j);
                    String m1 = addressTypeProperties.getADRTYP();
                    String m2 = addressDataM.getAddressType();
                    String m3 = currentAddress;
                    if (addressTypeProperties.getADRTYP().equals(addressDataM.getAddressType()) && !addressTypeProperties.getADRTYP().equals(currentAddress)) {
                        matchType = true;
                    }
                }
            	if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType) && !OrigConstant.ADDRESS_TYPE_HOME.equals(addressTypeProperties.getADRTYP())){
            		matchType = true;
            	}
                if (!matchType) {
                    if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(customerType) || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(customerType)) {
                        if (("Y").equals(addressTypeProperties.getINDADRTYP())) {
                            result.add(addressTypeProperties);
                        }
                    } else {
                        if (("Y").equals(addressTypeProperties.getCORADRTYP())) {
                            result.add(addressTypeProperties);
                        }
                    }
                }
            }
        }
        return result;
    }
    
    @Deprecated
    //method by Pong
    public Vector getAddressByCustomerTypeAndUsed(Vector addressVect, String customerType) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector addressTypeVect = (Vector) (h.get("AddressType"));
        Vector result = new Vector();
        boolean matchType;
        if (addressTypeVect != null) {
            AddressTypeProperties addressTypeProperties;
            for (int i = 0; i < addressTypeVect.size(); i++) {
                matchType = false;
                AddressDataM addressDataM;
                addressTypeProperties = (AddressTypeProperties) addressTypeVect.get(i);
                for (int j = 0; j < addressVect.size(); j++) {
                    addressDataM = (AddressDataM) addressVect.get(j);
                    if (addressTypeProperties.getADRTYP().equals(addressDataM.getAddressType())) {
                        matchType = true;
                    }
                }
                if (matchType) {
                    if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(customerType) || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(customerType)) {
                        if (("Y").equals(addressTypeProperties.getINDADRTYP())) {
                            result.add(addressTypeProperties);
                        }
                    } else {
                        if (("Y").equals(addressTypeProperties.getCORADRTYP())) {
                            result.add(addressTypeProperties);
                        }
                    }
                }
            }
        }
        return result;
    }

    //***
    /**
     * @deprecated DateFormat.StringToDate(String dateStr , int type)
     * */
    public static Date parseThaiToEngDate(String thaiDate) {
        try {
            if (!isEmptyString(thaiDate)) {

                int fromIndex;
                int toIndex;
                String dd;
                String mm;
                String yyyy;
                fromIndex = 0;
                toIndex = thaiDate.indexOf("/");
                if (toIndex >= 0) { // found
                    dd = thaiDate.substring(fromIndex, toIndex);
                    dd = (dd.length() == 1 ? "0" + dd : dd);
                    fromIndex = toIndex + 1;
                    toIndex = thaiDate.indexOf("/", fromIndex);
                    if (toIndex >= 0) { // found
                        mm = thaiDate.substring(fromIndex, toIndex);
                        mm = (mm.length() == 1 ? "0" + mm : mm);
                        fromIndex = toIndex + 1;
                        yyyy = thaiDate.substring(fromIndex);
                    } else {
                        throw new InvalidDateFormatException("DisplayFormatUtil>>StringToDate>>Date format received is " + thaiDate
                                + "---> Date format expected is dd/mm/yyyy");
                    }
                } else {
                    throw new InvalidDateFormatException("DisplayFormatUtil>>StringToDate>>Date format received is " + thaiDate
                            + "---> Date format expected is dd/mm/yyyy");
                }
                Calendar cal = Calendar.getInstance();
                int d = ORIGDisplayFormatUtil.StringToInt(dd);
                int m = ORIGDisplayFormatUtil.StringToInt(mm) - 1;
                int y = ORIGDisplayFormatUtil.StringToInt(yyyy) - 543;
                cal.set(y, m, d);
                return new Date(cal.getTimeInMillis());

                /*
                 * Calendar sbirthDate2 = Calendar.getInstance(); int year =
                 * Integer.parseInt(thaiDate.substring(6,thaiDate.length()))-543;
                 * int month = Integer.parseInt(thaiDate.substring(3,5)); int
                 * day = Integer.parseInt(thaiDate.substring(0,2));
                 * sbirthDate2.set(year,month-1,day); //sbirthDate = new
                 * java.sql.Date(dateFormat.parse(birthDate).getTime()); return
                 * new java.sql.Date(sbirthDate2.getTime().getTime());
                 */
            }
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return null;
    }

    public static String displayEngToThaiDate(java.sql.Date dateValue) {
        if (dateValue != null) {
            DecimalFormat dformat = new DecimalFormat("00");
            Calendar c = Calendar.getInstance();
            c.setTime(dateValue);
            StringBuffer date = new StringBuffer();
            date.append(dformat.format(c.get(Calendar.DAY_OF_MONTH))).append("/");
            date.append(dformat.format(c.get(Calendar.MONTH) + 1)).append("/");
            date.append(c.get(Calendar.YEAR) + 543);
            return date.toString();
        } else {
            return "";
        }
    }

    public String getAddressFormatDesc(String addressFormat) {
        if (OrigConstant.ADDRESS_FORMAT_NORMAL.equals(addressFormat)) {
            return "New";
        }
        return "";
    }
    @Deprecated
    public int getMaxAddressSeq(Vector addressVect) {
        int seq = 0;
        int seqTmp = 0;
        AddressDataM addressDataM;
        if (addressVect != null) {
            for (int i = 0; i < addressVect.size(); i++) {
                addressDataM = (AddressDataM) addressVect.get(i);
                seqTmp = addressDataM.getAddressSeq();
                logger.debug("seqTmp: seq" + seqTmp + ":" + seq);
                if (seqTmp > seq) {
                    seq = seqTmp;
                }
            }
        }
        logger.debug("Max address seq = " + seq);
        return seq;
    }
    @Deprecated
    public int getMaxAddressSeqEXT(Vector addressVect) {
        int seq = 0;
        int seqTmp = 0;
        AddressDataM addressDataM;
        for (int i = 0; i < addressVect.size(); i++) {
            addressDataM = (AddressDataM) addressVect.get(i);
            seqTmp = addressDataM.getAddressSeq();
            logger.debug("seqTmp: seq" + seqTmp + ":" + seq);
            if (seqTmp > seq) {
                seq = seqTmp;
            }
        }
        logger.debug("Max address seq = " + seq);
        return seq;
    }
    @Deprecated
    public int getMaxChangeNameSeq(Vector changeNameVect) {
        int seq = 0;
        int seqTmp = 0;
        ChangeNameDataM changeNameDataM;
        for (int i = 0; i < changeNameVect.size(); i++) {
            changeNameDataM = (ChangeNameDataM) changeNameVect.get(i);
            seqTmp = changeNameDataM.getSeq();
            logger.debug("seqTmp: seq" + seqTmp + ":" + seq);
            if (seqTmp > seq) {
                seq = seqTmp;
            }
        }
        logger.debug("Max change name seq = " + seq);
        return seq;
    }
    @Deprecated
    public int getMaxFinanceSeq(Vector financeVect) {
        int seq = 0;
        int seqTmp = 0;
        BankDataM bankDataM;
        for (int i = 0; i < financeVect.size(); i++) {
            bankDataM = (BankDataM) financeVect.get(i);
            seqTmp = bankDataM.getSeq();
            logger.debug("seqTmp: seq" + seqTmp + ":" + seq);
            if (seqTmp > seq) {
                seq = seqTmp;
            }
        }
        logger.debug("Max finance seq = " + seq);
        return seq;
    }
    @Deprecated
    public int getMaxReasonSeq(Vector reasonVect) {
        int seq = 0;
        int seqTmp = 0;
        ReasonDataM reasonDataM;
        for (int i = 0; i < reasonVect.size(); i++) {
            reasonDataM = (ReasonDataM) reasonVect.get(i);
            seqTmp = reasonDataM.getReasonSeq();
            logger.debug("seqTmp: seq" + seqTmp + ":" + seq);
            if (seqTmp > seq) {
                seq = seqTmp;
            }
        }
        logger.debug("Max reason seq = " + seq);
        return seq;
    }

    public String convertXMLIllegalChar(String data) {
        String correctData = data;

        if (data != null) {
            boolean hasIllegal = false;
            int l = data.length();
            for (int i = 0; i < l; i++) {
                char c = data.charAt(i);
                if ((c == '<') || (c == '>') || (c == '&') || (c == '\"')) {
                    hasIllegal = true;
                    break;
                }
            }
            if (!hasIllegal)
                return data;

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < l; i++) {
                char c = data.charAt(i);
                if (c == '<')
                    sb.append("&lt;");
                else if (c == '>')
                    sb.append("&gt;");
                else if (c == '&')
                    sb.append("&amp;");
                else if (c == '\"')
                    sb.append("&quot;");
                else
                    sb.append(c);
            }
            correctData = sb.toString();
        }

        return correctData;
    }
    @Deprecated
    public String getAddressTable(Vector addressVect, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        AddressDataM addressDataM;
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
        logger.debug("ssssssssssssssssssssssssss");
        String personalType = (String) request.getSession().getAttribute("PersonalType");
        try {
        	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\"></td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"18%\">" + MessageResourceUtil.getTextDescription(request, "ADDRESS_TYPE") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\">" + MessageResourceUtil.getTextDescription(request, "SEQ") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "ADDRESS_FORMAT") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"12%\">" + MessageResourceUtil.getTextDescription(request, "PHONE_NO1") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\">" + MessageResourceUtil.getTextDescription(request, "EXT1") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "ADDRESS_STATUS") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"25%\">" + MessageResourceUtil.getTextDescription(request, "CONTACT_PERSON") + "</td>");
            tableData.append("</tr></table></td></tr>");
            String addressTypeDesc = null;
            String addressStatus = null;
            String disabledChk = "";
            if (addressVect != null && addressVect.size() > 0) {
                for (int i = 0; i < addressVect.size(); i++) {
                    addressDataM = (AddressDataM) addressVect.get(i);
                    addressTypeDesc = cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType());
                    addressStatus = cacheUtil.getORIGMasterDisplayNameDataM("AddressStatus", addressDataM.getStatus());
                    logger.debug("addressDataM.getOrigOwner() = " + addressDataM.getOrigOwner());
                    /*if (("N").equals(addressDataM.getOrigOwner())) {
                        disabledChk = "disabled";
                    } else {
                        disabledChk = "";
                    }*/
                    if (!OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType) || OrigConstant.ADDRESS_TYPE_HOME.equals(addressDataM.getAddressType())) {
                    	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                        tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                        tableData.append("<tr>");
	                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">"
	                            + ORIGDisplayFormatUtil.displayCheckBox("delete", "addressSeq", String.valueOf(addressDataM.getAddressSeq()), "") + "</td>");
	                    tableData.append("<td class='jobopening2' width='18%'><a href=\"javascript:loadPopup('address','LoadAddressPopup','1150','450','200','10','" + addressDataM.getAddressSeq()
	                            + "','" + (i + 1) + "','" + personalType + "')\"><u>" + ORIGDisplayFormatUtil.forHTMLTag(addressTypeDesc) + "</u></a></td>");
	                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">" + (i + 1) + "</td>");
	                    tableData.append("<td class='jobopening2' width='15%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(this.getAddressFormatDesc(addressDataM.getAddressFormat()))
	                            + "</td>");
	                    tableData.append("<td class='jobopening2' width='12%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(addressDataM.getPhoneNo1()) + "</td>");
	                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(addressDataM.getPhoneExt1()) + "</td>");
	                    tableData.append("<td class='jobopening2' width='15%'>" + ORIGDisplayFormatUtil.forHTMLTag(addressStatus) + "</td>");
	                    tableData.append("<td class='jobopening2' width='25%'>" + ORIGDisplayFormatUtil.forHTMLTag(addressDataM.getContactPerson()) + "</td>");
	                    tableData.append("</tr></table></td></tr>");
                    }
                }
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"8\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }
            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }
    @Deprecated
    public String getChangeNameTable(Vector changeNameVect, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        ChangeNameDataM changeNameDataM;
        String personalType = (String) request.getSession().getAttribute("PersonalType");
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
        try {
            tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"5%\">" + MessageResourceUtil.getTextDescription(request, "SEQ") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"19%\">" + MessageResourceUtil.getTextDescription(request, "CHANGE_DATE") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"19%\">" + MessageResourceUtil.getTextDescription(request, "OLD_NAME") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"19%\">" + MessageResourceUtil.getTextDescription(request, "OLD_SURNAME") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"19%\">" + MessageResourceUtil.getTextDescription(request, "NEW_NAME") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"19%\">" + MessageResourceUtil.getTextDescription(request, "NEW_SURNAME") + "</td>");
            tableData.append("</tr></table></td></tr>");
            String disabledChk = "";
            String sDate;
            if (changeNameVect != null && changeNameVect.size() > 0) {
                for (int i = 0; i < changeNameVect.size(); i++) {
                    changeNameDataM = (ChangeNameDataM) changeNameVect.get(i);
                    /*if (("N").equals(changeNameDataM.getOrigOwner())) {
                        disabledChk = "disabled";
                    } else {
                        disabledChk = "";
                    }*/
                    if (changeNameDataM.getChangeDate() == null) {
                        sDate = "";
                    } else {
                        sDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(changeNameDataM.getChangeDate()));
                    }
                    tableData.append("<tr><td align='center' class='gumaygrey2'>");
                    tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                    tableData.append("<tr>");
                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">" + (i + 1) + "</td>");
                    tableData.append("<td class='jobopening2' align=\"center\"><a href=\"javascript:loadPopup('changeName','LoadChangeNamePopup','900','275','300','80','"
                            + changeNameDataM.getSeq() + "','','"+personalType+"')\"><u>" + sDate + "</u></a></td>");
                    tableData.append("<td class='jobopening2' width='19%'>" + ORIGDisplayFormatUtil.forHTMLTag(changeNameDataM.getOldName()) + "</td>");
                    tableData.append("<td class='jobopening2' width='19%'>" + ORIGDisplayFormatUtil.forHTMLTag(changeNameDataM.getOldSurName()) + "</td>");
                    tableData.append("<td class='jobopening2' width='19%'>" + ORIGDisplayFormatUtil.forHTMLTag(changeNameDataM.getNewName()) + "</td>");
                    tableData.append("<td class='jobopening2' width='19%'>" + ORIGDisplayFormatUtil.forHTMLTag(changeNameDataM.getNewSurname()) + "</td>");
                    tableData.append("</tr></table></td></tr>");
                }
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"6\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }
            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }
    @Deprecated
    public String getFinanceTable(Vector financeVect, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        BankDataM bankDataM;
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
        try {
        	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"2%\"></td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"2%\">" + MessageResourceUtil.getTextDescription(request, "SEQ") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"12%\">" + MessageResourceUtil.getTextDescription(request, "FINANCIAL_TYPE") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"21%\">" + MessageResourceUtil.getTextDescription(request, "BANK") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "BRANCH") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "ACCOUNT_NO") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"10%\">" + MessageResourceUtil.getTextDescription(request, "AMOUNT") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"10%\">" + MessageResourceUtil.getTextDescription(request, "OPEN_DATE") + "</td>");
            tableData.append("<td class=\"Bigtodotext3\" align='center' width=\"10%\">" + MessageResourceUtil.getTextDescription(request, "EXPIRY_DATE") + "</td>");
            tableData.append("</tr></table></td></tr>");
            String financeType;
            String bankDesc;
            String branchDesc;
            String disabledChk = "";
            String sDate;
            String personalType = (String) request.getSession().getAttribute("PersonalType");
            if (financeVect != null && financeVect.size() > 0) {
                for (int i = 0; i < financeVect.size(); i++) {
                    bankDataM = (BankDataM) financeVect.get(i);
                    financeType = cacheUtil.getORIGMasterDisplayNameDataM("FinancialType", bankDataM.getFinancialType());
                    bankDesc = cacheUtil.getORIGMasterDisplayNameDataM("BankInformation", bankDataM.getBankCode());
                    branchDesc = cacheUtil.getORIGCacheDisplayNameFormDB(bankDataM.getBranchCode(), "Branch", bankDataM.getBankCode());
                    logger.debug("bankDataM.getOrigOwner() = " + bankDataM.getOrigOwner());
                   /* if (("N").equals(bankDataM.getOrigOwner())) {
                        disabledChk = "disabled";
                    } else {
                        disabledChk = "";
                    }*/
                    tableData.append("<tr><td align='center' class='gumaygrey2'>");
                    tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                    tableData.append("<tr>");
                    tableData.append("<td class='jobopening2' width='2%' align=\"center\">"
                            + ORIGDisplayFormatUtil.displayCheckBox("delete", "financeSeq", String.valueOf(bankDataM.getSeq()), " " + disabledChk) + "</td>");
                    tableData.append("<td class='jobopening2' width='2%' align=\"center\">" + (i + 1) + "</td>");
                    tableData.append("<td class='jobopening2' width='12%'><a href=\"javascript:loadPopup('finance','LoadFinancePopup','900','300','300','80','" + bankDataM.getSeq()
                            + "','','" + personalType + "')\"><u>" + ORIGDisplayFormatUtil.forHTMLTag(financeType) + "</u></a></td>");
                    tableData.append("<td class='jobopening2' width='21%'>" + ORIGDisplayFormatUtil.forHTMLTag(bankDesc) + "</td>");
                    tableData.append("<td class='jobopening2' width='15%'>" + ORIGDisplayFormatUtil.forHTMLTag(branchDesc) + "</td>");
                    tableData.append("<td class='jobopening2' width='15%'>" + ORIGDisplayFormatUtil.forHTMLTag(bankDataM.getAccountNo()) + "</td>");
                    tableData.append("<td class='jobopening2' width='10%' align=\"right\">" + ORIGDisplayFormatUtil.displayCommaNumber(bankDataM.getAmount()) + "</td>");
                    if (bankDataM.getOpenDate() == null) {
                        sDate = "";
                    } else {
                        sDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(bankDataM.getOpenDate()));
                    }
                    tableData.append("<td class='jobopening2' width='10%' align=\"center\">" + sDate + "</td>");
                    if (bankDataM.getExpiryDate() == null) {
                        sDate = "";
                    } else {
                        sDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(bankDataM.getExpiryDate()));
                    }
                    tableData.append("<td class='jobopening2' width='10%' align=\"center\">" + sDate + "</td>");
                    tableData.append("</tr></table></td></tr>");
                }
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"9\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }
            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }
    @Deprecated
    public String getLoanTable(LoanDataM loanDataM, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        BankDataM bankDataM;
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();

        try {
        	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\"></td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"25%\">" + MessageResourceUtil.getTextDescription(request, "MKT_NAME") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"40%\">" + MessageResourceUtil.getTextDescription(request, "CAMPAIGN") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"30%\">" + MessageResourceUtil.getTextDescription(request, "INSTALLMENT") + "</td>");
            tableData.append("</tr></table></td></tr>");
            if (loanDataM != null) {
                String campaignDesc = cacheUtil.getORIGMasterDisplayNameDataM("Campaign", loanDataM.getCampaign());
                String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer", loanDataM.getMarketingCode());
                tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' width='5%' align=\"center\">" + ORIGDisplayFormatUtil.displayCheckBox("delete", "loanSeq", String.valueOf(loanDataM.getSeq()), "")
                        + "</td>");
                tableData.append("<td class='jobopening2' width='25%' align=\"center\"><a href=\"javascript:loadPopup('Loan','LoadLoanPopup','1150','680','100','10','','"
                        + loanDataM.getLoanType() + "')\"><u>" + ORIGDisplayFormatUtil.forHTMLTag(mktDesc) + "</u></a></td>");
                tableData.append("<td class='jobopening2' width='40%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(campaignDesc) + "</td>");
                if (OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())) {
                    tableData.append("<td class='jobopening2' width='30%' align=\"center\">Step Installment</td>");
                } else {
                    tableData.append("<td class='jobopening2' width='30%' align=\"center\">" + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfInstallment1()) + "</td>");
                }
                tableData.append("</tr></table></td></tr>");
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"4\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }

            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }
    @Deprecated
    public String getPLoanTable(LoanDataM loanDataM, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        BankDataM bankDataM;
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();

        try {
        	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\"></td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"25%\">" + MessageResourceUtil.getTextDescription(request, "MKT_NAME") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"28%\">" + MessageResourceUtil.getTextDescription(request, "CAMPAIGN") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"27%\">" + MessageResourceUtil.getTextDescription(request, "SCHEME_COME") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "LOAN_AMOUNT_APPLIED") + "</td>");
            tableData.append("</tr></table></td></tr>");
            if (loanDataM != null) {
                String campaignDesc = cacheUtil.getORIGMasterDisplayNameDataM("Campaign", loanDataM.getCampaign());
                String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer", loanDataM.getMarketingCode());
                String schemeDesc = cacheUtil.getORIGMasterDisplayNameDataM("IntScheme", loanDataM.getSchemeCode());
                tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' width='5%' align=\"center\">" + ORIGDisplayFormatUtil.displayCheckBox("delete", "loanSeq", String.valueOf(loanDataM.getSeq()), "")
                        + "</td>");
                tableData.append("<td class='jobopening2' width='25%' align=\"center\"><a href=\"javascript:loadPopup('Loan','LoadPLoanLoanPopup','1270','680','100','10','','"
                        + loanDataM.getLoanType() + "')\"><u>" + ORIGDisplayFormatUtil.forHTMLTag(mktDesc) + "</u></a></td>");
                tableData.append("<td class='jobopening2' width='28%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(campaignDesc) + "</td>");
                tableData.append("<td class='jobopening2' width='27%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(schemeDesc) + "</td>");
                if (OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())) {
                    tableData.append("<td class='jobopening2' width='15%' align=\"center\">Step Installment</td>");
                } else {
                    tableData.append("<td class='jobopening2' width='15%' align=\"center\">" + ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getLoanAmt()) + "</td>");
                }
                tableData.append("</tr></table></td></tr>");
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"5\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }

            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }
    @Deprecated
    public String getOtherNameTable(Vector otherNameVect, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        OtherNameDataM otherNameDataM;
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
        try {
        	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"3%\"></td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"14%\">" + MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"17%\">" + MessageResourceUtil.getTextDescription(request, "NAME") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"17%\">" + MessageResourceUtil.getTextDescription(request, "LASTNAME") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "POSITION") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"16%\">" + MessageResourceUtil.getTextDescription(request, "DESCRIPTION") + "</td>");
            tableData.append("</tr></table></td></tr>");
            String positionDesc;
            if (otherNameVect != null && otherNameVect.size() > 0) {
                for (int i = 0; i < otherNameVect.size(); i++) {
                    otherNameDataM = (OtherNameDataM) otherNameVect.get(i);
                    positionDesc = cacheUtil.getORIGMasterDisplayNameDataMForPosition(otherNameDataM.getPosition(), "Position", otherNameDataM.getOccupation());
                    tableData.append("<tr><td align='center' class='gumaygrey2'>");
                    tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                    tableData.append("<tr>");
                    tableData.append("<td class='jobopening2' width='3%' align=\"center\">"
                            + ORIGDisplayFormatUtil.displayCheckBox("delete", "otherNameSeq", String.valueOf(otherNameDataM.getSeq()), "") + "</td>");
                    tableData.append("<td class='jobopening2' width='14%' align=\"center\"><a href=\"javascript:loadPopup('otherName','LoadOtherNamePopup','850','280','300','80','"
                            + otherNameDataM.getSeq() + "','')\"><u>" + otherNameDataM.getCitizenId() + "</u></a></td>");
                    tableData.append("<td class='jobopening2' width='17%'>" + otherNameDataM.getName() + "</td>");
                    tableData.append("<td class='jobopening2' width='17%'>" + otherNameDataM.getLastName() + "</td>");
                    tableData.append("<td class='jobopening2' width='15%'>" + ORIGDisplayFormatUtil.forHTMLTag(positionDesc) + "</td>");
                    tableData.append("<td class='jobopening2' width='16%'>" + ORIGDisplayFormatUtil.forHTMLTag(otherNameDataM.getDescription()) + "</td>");
                    tableData.append("</tr></table></td></tr>");
                }
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"6\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }
            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }
    @Deprecated
    public String getCorperatedTable(Vector corperatedVect, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        CorperatedDataM corperatedDataM;
        try {
        	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"4%\"></td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"6%\">" + MessageResourceUtil.getTextDescription(request, "YEARS") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"18%\">" + MessageResourceUtil.getTextDescription(request, "TOTAL_ASSETS") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"18%\">" + MessageResourceUtil.getTextDescription(request, "TOTAL_LIABILITIES") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"18%\">" + MessageResourceUtil.getTextDescription(request, "TOTAL_SHAREHOLDERS_EQUITY")
                    + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"18%\">" + MessageResourceUtil.getTextDescription(request, "NET_INCOME") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"18%\">" + MessageResourceUtil.getTextDescription(request, "NET_PROFIT_OR_SALES") + "</td>");
            tableData.append("</tr></table></td></tr>");
            if (corperatedVect != null && corperatedVect.size() > 0) {
                for (int i = 0; i < corperatedVect.size(); i++) {
                    corperatedDataM = (CorperatedDataM) corperatedVect.get(i);
                    tableData.append("<tr><td align='center' class='gumaygrey2'>");
                    tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                    tableData.append("<tr>");
                    tableData.append("<td class='jobopening2' width='4%'>" + ORIGDisplayFormatUtil.displayCheckBox("delete", "corperatedSeq", String.valueOf(corperatedDataM.getSeq()), "")
                            + "</td>");
                    tableData
                            .append("<td class='jobopening2' width='6%' align=\"center\"><a href=\"javascript:loadCorperatedPopup('corperated','LoadCorperatedPopup','760','600','200','100','"
                                    + corperatedDataM.getYear()
                                    + "','"
                                    + corperatedDataM.getSeq()
                                    + "','')\"><u>"
                                    + corperatedDataM.getYear()
                                    + "</u></a></td>");
                    tableData.append("<td class='jobopening2' width='18%'>" + ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstTotalAssets()) + "</td>");
                    tableData.append("<td class='jobopening2' width='18%'>" + ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbTotalLb()) + "</td>");
                    tableData.append("<td class='jobopening2' width='18%'>" + ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getShdEqTotalShareHdEqity()) + "</td>");
                    tableData.append("<td class='jobopening2' width='18%'>" + ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtNetIncome()) + "</td>");
                    tableData.append("<td class='jobopening2' width='18%'>" + ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getRatNetProfitSale()) + "</td>");
                    tableData.append("</tr></table></td></tr>");
                }
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"7\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }
            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }
    @Deprecated
    public String getGuarantorTable(Vector guarantorVect, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        PersonalInfoDataM personalInfoDataM;
        String titleName;
        String maritalStatus;
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
        try {
        	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\"></td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\">" + MessageResourceUtil.getTextDescription(request, "SEQ") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"40%\">" + MessageResourceUtil.getTextDescription(request, "NAME") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"20%\">" + MessageResourceUtil.getTextDescription(request, "STATUS") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"30%\">" + MessageResourceUtil.getTextDescription(request, "GUARANTOR_FLAG") + "</td>");
            tableData.append("</tr></table></td></tr>");
            if (guarantorVect != null && guarantorVect.size() > 0) {
                for (int i = 0; i < guarantorVect.size(); i++) {
                    personalInfoDataM = (PersonalInfoDataM) guarantorVect.get(i);
                    titleName = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName());
                    maritalStatus = cacheUtil.getORIGMasterDisplayNameDataM("MaritalStatus", personalInfoDataM.getMaritalStatus());
                    String disabledCheckbox = "";
                    if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())
                            || OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())) {
                        disabledCheckbox = " disabled";
                    }
                    tableData.append("<tr><td align='center' class='gumaygrey2'>");
                    tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                    tableData.append("<tr>");
                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">"
                            + ORIGDisplayFormatUtil.displayCheckBox("delete", "guarantorSeq", String.valueOf(personalInfoDataM.getPersonalSeq()),
                                    disabledCheckbox) + "</td>");
                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">" + personalInfoDataM.getPersonalSeq() + "</td>");
                    tableData.append("<td class='jobopening2' width='40%'><a href=\"javascript:loadPopup('guarantor','LoadGuarantorPopup','1150','500','150','40','"
                            + personalInfoDataM.getPersonalSeq() + "','')\"><u>" + titleName + " " + personalInfoDataM.getThaiFirstName() + " "
                            + ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getThaiLastName()) + "</u></a></td>");
                    tableData.append("<td class='jobopening2' width='20%'>" + maritalStatus + "</td>");
                    String flag = "";
                    if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())) {
                        flag = "Co-Borrower  Active ";

                    } else if (OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())) {
                        flag = "Co-Borrower  Un Active ";
                    } else if (OrigConstant.COBORROWER_FLAG_NO.equals(personalInfoDataM.getCoborrowerFlag()) || personalInfoDataM.getCoborrowerFlag() == null) {
                        flag = "Guarantor ";
                        if (OrigConstant.ORIG_FLAG_Y.equals(personalInfoDataM.getDebtIncludeFlag())) {
                            flag = "Inclued debt";
                        } else {
                            flag = "not Inculde debt";
                        }
                    }
                    tableData.append("<td class='jobopening2' width='30%'>" + flag + "</td>");
                    tableData.append("</tr></table></td></tr>");
                }
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"5\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }
            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }
    @Deprecated
    public String getSupplementaryCardTable(Vector supCardVect, HttpServletRequest request, Vector vecCardInformation) {
        StringBuffer tableData = new StringBuffer("");
        PersonalInfoDataM personalInfoDataM = null;
        String titleName;
        String maritalStatus;
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
        try {
        	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\"></td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "CARD_NO") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"30%\">" + MessageResourceUtil.getTextDescription(request, "NAME") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") + "</td>");
            tableData.append("</tr></table></td></tr>");
            if (supCardVect != null && supCardVect.size() > 0) {
            	for (int i=0; i<supCardVect.size(); i++){
            		personalInfoDataM = null;
					PersonalInfoDataM temp = (PersonalInfoDataM)supCardVect.get(i);
		            if(vecCardInformation != null && vecCardInformation.size() > 0){
						CardInformationDataM cardInformationDataM = new CardInformationDataM();
		            	for(int j=0; j<vecCardInformation.size(); j++){
		            		cardInformationDataM = (CardInformationDataM) vecCardInformation.get(j);
							if (temp.getIdNo()!=null && temp.getIdNo().equalsIgnoreCase(cardInformationDataM.getIdNo()) && OrigConstant.CARD_INFORMATION_APPLICATION_TYPE_SUB.equalsIgnoreCase(cardInformationDataM.getApplicationType())){
								personalInfoDataM = temp;
								break;
							}
						}
						if (personalInfoDataM!=null){
		                    titleName = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName());
		                    maritalStatus = cacheUtil.getORIGMasterDisplayNameDataM("MaritalStatus", personalInfoDataM.getMaritalStatus());
		                    String disabledCheckbox = "";
		                    if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())
		                            || OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())) {
		                        disabledCheckbox = " disabled";
		                    }
		                    tableData.append("<tr><td align='center' class='gumaygrey2'>");
		                    tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
		                    tableData.append("<tr>");
		                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">"
		                            + ORIGDisplayFormatUtil.displayCheckBox("delete", "supCardSeq", String.valueOf(personalInfoDataM.getPersonalSeq()),
		                                    disabledCheckbox) + "</td>");
		                    tableData.append("<td class='jobopening2' width='15%' align=\"center\">" + ORIGDisplayFormatUtil.displayHTML(cardInformationDataM.getCardNo()) + "</td>");
		                    tableData.append("<td class='jobopening2' width='30%'><a href=\"javascript:loadPopup('guarantor','LoadSupplementaryCardPopup','1150','500','150','40','"
		                            + personalInfoDataM.getPersonalSeq() + "','')\"><u>" + titleName + " " + personalInfoDataM.getThaiFirstName() + " "
		                            + ORIGDisplayFormatUtil.forHTMLTag(personalInfoDataM.getThaiLastName()) + "</u></a></td>");
		                    tableData.append("<td class='jobopening2' width='15%'>" + ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getCardID()) + "</td>");
		                    tableData.append("</tr></table></td></tr>");
						}
		            } else {
		            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
		                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
		                tableData.append("<tr>");
		                tableData.append("<td class='jobopening2' colspan=\"5\" align=\"center\">No Record</td>");
		                tableData.append("</tr></table></td></tr>");
		            }
				}
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"5\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }
            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }
    @Deprecated
    public String getFeeInformationTable(Vector feeInformationDataMVect, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        BankDataM bankDataM;
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();

        try {
        	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr><td class='TableHeader'>");
            tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
            tableData.append("<tr>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\"></td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"40%\">" + MessageResourceUtil.getTextDescription(request, "FEE_TYPE") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"20%\">" + MessageResourceUtil.getTextDescription(request, "FEE_AMOUNT") + "</td>");
            tableData.append("<td class='Bigtodotext3' align='center' width=\"35%\">" + MessageResourceUtil.getTextDescription(request, "FEE_PAYMENT_OPTION") + "</td>");
            tableData.append("</tr></table></td></tr>");
            if (feeInformationDataMVect != null && feeInformationDataMVect.size()>0) {
                for (int i=0; i<feeInformationDataMVect.size(); i++){
                    FeeInformationDataM feeInformationDataM = (FeeInformationDataM)feeInformationDataMVect.get(i);
	                String feeType = cacheUtil.getORIGCacheDisplayNameDataM(31, feeInformationDataM.getFeeType());
	                String feeOption = cacheUtil.getORIGCacheDisplayNameDataM(32, feeInformationDataM.getFeePaymentOption());
	                tableData.append("<tr><td align='center' class='gumaygrey2'>");
                    tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                    tableData.append("<tr>");
	                tableData.append("<td class='jobopening2' width='5%' align=\"center\">" + ORIGDisplayFormatUtil.displayCheckBox("delete", "MGfeeSeq", String.valueOf(feeInformationDataM.getSeq()), "")
	                        + "</td>");
	                tableData.append("<td class='jobopening2' width='40%' align=\"center\"><a href=\"javascript:loadPopup('feeInfo','LoadFeeInformationPopup','500','275','200','300','"+feeInformationDataM.getSeq()+"','','');\"><u>"+ORIGDisplayFormatUtil.forHTMLTag(feeType)+"</u></a></td>");
	                tableData.append("<td class='jobopening2' width='20%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(ORIGDisplayFormatUtil.displayCommaNumber(feeInformationDataM.getFeeAmount())) + "</td>");
	                tableData.append("<td class='jobopening2' width='35%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(feeOption) + "</td>");
	                tableData.append("</tr></table></td></tr>");
                }
            } else {
            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                tableData.append("<tr>");
                tableData.append("<td class='jobopening2' colspan=\"5\" align=\"center\">No Record</td>");
                tableData.append("</tr></table></td></tr>");
            }

            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }

    public static java.util.Date convertStringToUtilDateFormat(String value, String format) {
        if (!isEmptyString(value)) {
            try {
                return ORIGDisplayFormatUtil.StringToDate(value, format);
            } catch (Exception e) {
                logger.error("Error >>>> ", e);
                return null;
            }
        } else {
            return null;
        }
    }
    @Deprecated
    public boolean haveAddressSeq(Vector addressVect, int seq) {
        AddressDataM addressDataM;
        for (int i = 0; i < addressVect.size(); i++) {
            addressDataM = (AddressDataM) addressVect.get(i);
            if (addressDataM.getAddressSeq() == seq) {
                return true;
            }
        }
        return false;
    }
    @Deprecated
    public Vector getReasonByRole(Vector reasonVect, String role) {
        Vector result = new Vector();
        if (reasonVect != null) {
            ReasonDataM reasonDataM;
            for (int i = 0; i < reasonVect.size(); i++) {
                reasonDataM = (ReasonDataM) reasonVect.get(i);
                if (role.equals(reasonDataM.getRole())) {
                    result.add(reasonDataM);
                }
            }
        }
        return result;
    }
    @Deprecated
    public Vector removeReasonByRole(Vector reasonVect, String role) {
        if (reasonVect != null) {
            ReasonDataM reasonDataM;
            for (int i = reasonVect.size() - 1; i >= 0; i--) {
                reasonDataM = (ReasonDataM) reasonVect.get(i);
                if (role.equals(reasonDataM.getRole())) {
                    reasonVect.removeElementAt(i);
                }
            }
        }
        return reasonVect;
    }

    public BigDecimal calculatePercent(BigDecimal total, BigDecimal value) {
        //Patt do for KBank Demo //Start
        //return value.multiply(new BigDecimal(100)).divide(total, 2, 0);
        return new BigDecimal(10);
        //Patt End
    }
    @Deprecated
    public boolean checkException(LoanDataM loanDataM) {
        boolean result = false;
//        HashMap h = TableLookupCache.getCacheStructure();
//        //Vector campaignVect = (Vector) (h.get("Campaign"));
//        CampaignDataM campaignDataM = null;
//
//        try {
//            campaignDataM = ORIGDAOFactory.getUtilityDAO().getCampaign(loanDataM.getCampaign());
//        } catch (EjbUtilException e) {
//            logger.error("Errot ", e);
//        }
//        if (campaignDataM == null) {
//            logger.error("Can't find campaign");
//            return false;
//            // campaignDataM=new CampaignDataM();
//        }
//        CampaignCacheProperties properties;
//        BigDecimal minRate;
//        BigDecimal minDownPayment;
//        int maxTerm;
//        //for(int i=0; i<campaignVect.size(); i++){
//        //properties = (CampaignCacheProperties) campaignVect.get(i);
//        String campaignCode = loanDataM.getCampaign();
//        //logger.debug("campaignCode = "+campaignCode);
//        //if(properties.getCAMPCDE().equals(campaignCode)){
//        BigDecimal rate = loanDataM.getRate1();
//        BigDecimal downPayment = this.calculatePercent(loanDataM.getCostOfFinancialAmt(), loanDataM.getCostOfDownPayment());
//        BigDecimal term;
//        if (("Y").equals(loanDataM.getBalloonFlag())) {
//            term = loanDataM.getBalloonTerm();
//        } else {
//            term = loanDataM.getInstallment1();
//        }
//        minRate = campaignDataM.getMINRATE();
//        minDownPayment = campaignDataM.getMINDOWNAMT();
//        maxTerm = campaignDataM.getMAXTERM();
//        logger.debug("rate = " + rate);
//        logger.debug("downPayment = " + downPayment);
//        logger.debug("term = " + term);
//        logger.debug("minRate = " + minRate);
//        logger.debug("minDownPayment = " + minDownPayment);
//        logger.debug("maxTerm = " + maxTerm);
//        logger.debug("properties.getCAMPCDE() = " + campaignDataM.getCAMPCDE());
//        if (minRate == null) {
//            minRate = new BigDecimal(0);
//        }
//        if (minDownPayment == null) {
//            minDownPayment = new BigDecimal(0);
//        }
//        if (rate.compareTo(minRate) == -1) {
//            result = true;
//            logger.debug("<<< Rate Exception >>>");
//        }
//        if (downPayment.compareTo(minDownPayment) == -1) {
//            result = true;
//            logger.debug("<<< Down Exception >>>");
//        }
//        if (term.compareTo(new BigDecimal(maxTerm)) == 1) {
//            result = true;
//            logger.debug("<<< Term Exception >>>");
//        }
//        java.util.Date currentDate = new java.util.Date(System.currentTimeMillis());
//        logger.debug("properties.getEXPDTE() = " + campaignDataM.getEXPDTE());
//        logger.debug("currentDate = " + currentDate);
//        if (currentDate.compareTo(campaignDataM.getEXPDTE()) == 1) {
//            logger.debug("<<< Campaign Expiry >>>");
//            result = true;
//        }
//        //}
//
//        //}
//        //}
        return result;
    }
    @Deprecated
    public boolean checkCompleteDoc(Vector documentVect) {
        boolean result = true;
        logger.debug("Document List = " + documentVect);
        if (documentVect != null && documentVect.size() > 0) {
            DocumentCheckListDataM documentCheckListDataM;
            for (int i = 0; i < documentVect.size(); i++) {
                documentCheckListDataM = (DocumentCheckListDataM) documentVect.get(i);
                logger.debug("documentCheckListDataM.getRequire() = " + documentCheckListDataM.getRequire());
                logger.debug("documentCheckListDataM.getReceive() = " + documentCheckListDataM.getReceive());
                if ((OrigConstant.REQUIRE_DOC).equals(documentCheckListDataM.getRequire())
                        && !(OrigConstant.RECEIVE_DOC).equals(documentCheckListDataM.getReceive())
                        && !(OrigConstant.RECEIVE_DOC).equals(documentCheckListDataM.getWaive())) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }
    @Deprecated
    public boolean checkExceptionForUW(LoanDataM loanDataM) {
        boolean result = false;
        BigDecimal installment;
        if ("Y".equals(loanDataM.getBalloonFlag())) {
            installment = loanDataM.getBalloonTerm();
        } else {
            installment = loanDataM.getInstallment1();
        }
        if (!loanDataM.getCampaign().equals(loanDataM.getCampaignTemp())) {
            result = true;
        }
        if (loanDataM.getRate1().compareTo(loanDataM.getRateTemp()) != 0) {
            result = true;
        }
        if (installment.compareTo(loanDataM.getTermTemp()) != 0) {
            result = true;
        }
        if (loanDataM.getCostOfDownPayment().compareTo(loanDataM.getDownPaymentTemp()) != 0) {
            result = true;
        }
        return result;
    }
    @Deprecated
    public boolean checkExceptionForXCMR(LoanDataM loanDataM) {
        boolean result = false;
        BigDecimal installment;

        if (!loanDataM.getCampaign().equals(loanDataM.getCampaignTemp())) {
            result = true;
        }

        return result;
    }
    @Deprecated
    public Vector checkEscalateApp(String userName, String appRecordID, String carType, String customerType, BigDecimal financeAmount, String appType,
            BigDecimal installmentTerm, BigDecimal downPayment) {
//        EjbUtil ejbUtil = new EjbUtil();
        Vector result = new Vector();
//        try {
//            String apporvalGroups = ejbUtil.getApprovalGroupAuthority(userName, appRecordID, carType, customerType, financeAmount, appType, installmentTerm,
//                    downPayment);
//
//            if (apporvalGroups != null) {
//                String[] results = apporvalGroups.split(",");
//                for (int i = 0; i < results.length; i++) {
//                    if (!isEmptyString(results[i])) {
//                        logger.debug("Group " + i + " : " + results[i]);
//                        result.add(results[i]);
//                    }
//                }
//            } else {
//                result = null;
//            }
//
//        } catch (EjbUtilException e) {
//            logger.error("Error >> ", e);
//        }
        return result;
    }
    @Deprecated
    public Vector getSLA(String qName, String status, String action, String jobState, String userName, String role, double time) {
        Vector result = new Vector();
        if (OrigConstant.ROLE_CMR.equals(role)) {
            if (ORIGWFConstant.JobState.UW_CONDITIONAL_APPROVED_STATE.equals(jobState) || ORIGWFConstant.JobState.UW_ESCALATED_STATE.equals(jobState)
                    || ORIGWFConstant.JobState.UW_PENDING_STATE.equals(jobState) || ORIGWFConstant.JobState.UW_NEW_STATE.equals(jobState)) {

                result = getSLAForUWCMR(qName, status, action, jobState, userName, time);
            } else {
                result = getSLAForCMR(qName, status, action, jobState, userName, time);
            }
        } else if (OrigConstant.ROLE_UW.equals(role)) {
            result = getSLAForUW(qName, status, action, jobState, userName, time);
        } else if (OrigConstant.ROLE_XCMR.equals(role)) {
            result = getSLAForXCMR(qName, status, action, jobState, userName, time);
        } else if (OrigConstant.ROLE_XUW.equals(role)) {
            result = getSLAForXUW(qName, status, action, jobState, userName, time);
        }
        return result;
    }
    @Deprecated
    public Vector getSLAForUW(String qName, String status, String action, String jobState, String userName, double time) {
//        UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
//        try {
//            Vector applicationSLATmpVect = utilityDAO.getSLAForUW(qName, status, action, jobState, userName, time);
//            logger.debug("applicationSLATmpVect = " + applicationSLATmpVect.size());
//            /*
//             * if(applicationSLATmpVect.size() > 0){ for(int i=0; i
//             * <applicationSLATmpVect.size(); i++){
//             * applicationSLAVect.add(applicationSLATmpVect.get(i)); } }
//             */
//            return applicationSLATmpVect;
//
//        } catch (EjbUtilException e) {
//            logger.error("Error >> ", e);
//            return null;
//        }
    	return null;
    }
    @Deprecated
    public Vector getSLAForUWCMR(String qName, String status, String action, String jobState, String userName, double time) {
//        UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
//        try {
//            Vector applicationSLATmpVect = utilityDAO.getSLAForUWCMR(qName, status, action, jobState, userName, time);
//            logger.debug("applicationSLATmpVect = " + applicationSLATmpVect.size());
//            /*
//             * if(applicationSLATmpVect.size() > 0){ for(int i=0; i
//             * <applicationSLATmpVect.size(); i++){
//             * applicationSLAVect.add(applicationSLATmpVect.get(i)); } }
//             */
//            return applicationSLATmpVect;
//
//        } catch (EjbUtilException e) {
//            logger.error("Error >> ", e);
//            return null;
//        }
    	return null;
    }
    @Deprecated
    public Vector getSLAForXCMR(String qName, String status, String action, String jobState, String userName, double time) {
//        UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
//        try {
//            Vector applicationSLATmpVect = utilityDAO.getSLAForXCMR(qName, status, action, jobState, userName, time);
//            logger.debug("applicationSLATmpVect = " + applicationSLATmpVect.size());
//            /*
//             * if(applicationSLATmpVect.size() > 0){ for(int i=0; i
//             * <applicationSLATmpVect.size(); i++){
//             * applicationSLAVect.add(applicationSLATmpVect.get(i)); } }
//             */
//            return applicationSLATmpVect;
//
//        } catch (EjbUtilException e) {
//            logger.error("Error >> ", e);
//            return null;
//        }
    	return null;
    }
    @Deprecated
    public Vector getSLAForCMR(String qName, String status, String action, String jobState, String userName, double time) {
//        UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
//        try {
//            Vector applicationSLATmpVect = utilityDAO.getSLAForCMR(qName, status, action, jobState, userName, time);
//            logger.debug("applicationSLATmpVect = " + applicationSLATmpVect.size());
//            /*
//             * if(applicationSLATmpVect.size() > 0){ for(int i=0; i
//             * <applicationSLATmpVect.size(); i++){
//             * applicationSLAVect.add(applicationSLATmpVect.get(i)); } }
//             */
//            return applicationSLATmpVect;
//
//        } catch (EjbUtilException e) {
//            logger.error("Error >> ", e);
//            return null;
//        }
    	return null;
    }

    public boolean checkUserRole(Vector roleVect, String role) {
        String roleTmp;
        for (int i = 0; i < roleVect.size(); i++) {
            roleTmp = (String) roleVect.get(i);
            if (roleTmp.equals(role)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    @Deprecated
    public Vector getSLAQName(String userRole) {
        Vector result = new Vector();
        logger.debug("userRole = " + userRole);
        HashMap h = TableLookupCache.getCacheStructure();
        Vector slaVect = (Vector) (h.get("SLA"));
        if (slaVect != null && slaVect.size() > 0) {
            SLADataM dataM;
            for (int i = 0; i < slaVect.size(); i++) {
                dataM = (SLADataM) slaVect.get(i);
                if (dataM.getRole().equals(userRole)) {
                    logger.debug("dataM.getQName() = " + dataM.getQName());
                    result.add(dataM);
                }
            }
        }

        return result;
    }

    public String getPriorityDescription(String code) {
        if (OrigConstant.Priority.HIGH.equals(code)) {
            return "High";
        } else if (OrigConstant.Priority.MEDIUM.equals(code)) {
            return "Medium";
        } else if (OrigConstant.Priority.NORMAL.equals(code)) {
            return "Normal";
        } else {
            return "-";
        }
    }
    @Deprecated
    public String getInternalChecker(String userName) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector internalVect = (Vector) (h.get("InternalChecker"));
        String result = "";
        InternalCheckerProperties checkerProperties;
        for (int i = 0; i < internalVect.size(); i++) {
            checkerProperties = (InternalCheckerProperties) internalVect.get(i);
            //Comment By Rawi Songchaisin Used USERID
            //if (userName.equalsIgnoreCase(checkerProperties.getPRM01())) {
            if (userName.equalsIgnoreCase(checkerProperties.getUSERID())) {
                return checkerProperties.getINTCHK();
            }
        }
        return result;
    }
    @Deprecated
    public String getCreditApproval(String userName) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector paramDetailVect = (Vector) (h.get("ParameterDetailCode"));
        String result = "";
        ParameterDetailCodeProperties codeProperties;
        for (int i = 0; i < paramDetailVect.size(); i++) {
            codeProperties = (ParameterDetailCodeProperties) paramDetailVect.get(i);
            if ("CREDITAPRV".equals(codeProperties.getPRMTYP()) && userName.equalsIgnoreCase(codeProperties.getPRMREF2())) {
                return codeProperties.getPRMCDE();
            }
        }
        return result;
    }
    @Deprecated
    public int getDocIdByDocTypeId(Vector documentVect, String docTypeId) {
        if (documentVect != null) {
            DocumentCheckListDataM checkListDataM;
            for (int i = 0; i < documentVect.size(); i++) {
                checkListDataM = (DocumentCheckListDataM) documentVect.get(i);
                if (checkListDataM.getDocTypeId().equals(docTypeId)) {
                    return checkListDataM.getDocId();
                }
            }
        }
        return 0;
    }
    @Deprecated
    public VehicleDataM getVehicleDataMDetailById(Vector vehicleDataMVect, int id) {
        if (vehicleDataMVect != null) {
            VehicleDataM vehicleDataM;
            for (int i = 0; i < vehicleDataMVect.size(); i++) {
                vehicleDataM = (VehicleDataM) vehicleDataMVect.get(i);
                if (vehicleDataM.getVehicleID() == id) {
                    return vehicleDataM;
                }
            }
        }
        return null;
    }
    @Deprecated
    public int getAddressSize(Vector addressVect, String customerType) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector addressTypeVect = (Vector) (h.get("AddressType"));
        int result = 0;
        boolean matchType;
        if (addressTypeVect != null) {
            AddressTypeProperties addressTypeProperties;
            for (int i = 0; i < addressTypeVect.size(); i++) {
                AddressDataM addressDataM;
                addressTypeProperties = (AddressTypeProperties) addressTypeVect.get(i);
                //if(!matchType){
                if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(customerType) || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(customerType)) {
                    if (("Y").equals(addressTypeProperties.getINDADRTYP())) {
                        result += 1;
                    }
                } else {
                    if (("Y").equals(addressTypeProperties.getCORADRTYP())) {
                        result += 1;
                    }
                }
                //}
            }
        }
        return result;
    }
    @Deprecated
    public int getAddressSizeByPersonalType(Vector addressVect, String customerType, String personalType) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector addressTypeVect = (Vector) (h.get("AddressType"));
        int result = 0;
        boolean matchType;
        if (addressTypeVect != null) {
            AddressTypeProperties addressTypeProperties;
            for (int i = 0; i < addressTypeVect.size(); i++) {
                AddressDataM addressDataM;
                addressTypeProperties = (AddressTypeProperties) addressTypeVect.get(i);
            	if(!OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType) || OrigConstant.ADDRESS_TYPE_HOME.equals(addressTypeProperties.getADRTYP())){
                    //if(!matchType){
                    if (OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(customerType) || OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(customerType)) {
                        if (("Y").equals(addressTypeProperties.getINDADRTYP())) {
                            result += 1;
                        }
                    } else {
                        if (("Y").equals(addressTypeProperties.getCORADRTYP())) {
                            result += 1;
                        }
                    }
                    //}
            	}
            }
        }
        return result;
    }
    @Deprecated
    public Vector getEscalateGroup(String userName, String appRecordID, String carType, String customerType, BigDecimal financeAmount, String appType,
            BigDecimal installmentTerm, BigDecimal downPayment) {
//        EjbUtil ejbUtil = new EjbUtil();
//        Vector result = new Vector();
//        try {
//            String apporvalGroups = ejbUtil
//                    .getEscalateGroup(userName, appRecordID, carType, customerType, financeAmount, appType, installmentTerm, downPayment);
//
//            if (apporvalGroups != null) {
//                String[] results = apporvalGroups.split(",");
//                for (int i = 0; i < results.length; i++) {
//                    if (!isEmptyString(results[i])) {
//                        logger.debug("Group " + i + " : " + results[i]);
//                        result.add(results[i]);
//                    }
//                }
//            } else {
//                result = null;
//            }
//
//        } catch (EjbUtilException e) {
//            logger.error("Error >> ", e);
//        }
//        return result;
    	return null;
    }
    @Deprecated
    public boolean isDirectDebit(String collectionCode) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector collectionVect = (Vector) (h.get("CollectionCode"));
        if (collectionVect != null) {
            CollectionCodeProperties codeProperties;
            for (int i = 0; i < collectionVect.size(); i++) {
                codeProperties = (CollectionCodeProperties) collectionVect.get(i);
                if (codeProperties.getCode().equals(collectionCode) && OrigConstant.COLLECTION_CODE_DB.equals(codeProperties.getFLAG())) {
                    return true;
                }
            }
        }
        return false;
    }
    @Deprecated
    public java.util.Date getNulldate(String officeCode) {
        try {
            if (isEmptyString(officeCode)) {
                officeCode = OrigConstant.DefaultValue.DEFAULT_OFFICE_CODE;
            }
            HashMap h = TableLookupCache.getCacheStructure();
            Vector defaultDateVect = (Vector) (h.get("DefaultDate"));
            if (defaultDateVect != null) {
                DefaultDateProperties dateProperties;
                for (int i = 0; i < defaultDateVect.size(); i++) {
                    dateProperties = (DefaultDateProperties) defaultDateVect.get(i);
                    if (dateProperties.getOFFCDE().equals(officeCode)) {
                        return dateProperties.getNULLDTE();
                    }
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("Error >>> ", e);
            return null;
        }
    }
    @Deprecated
    public java.util.Date getSysNulldate(String officeCode) {
        try {
            if (isEmptyString(officeCode)) {
                officeCode = OrigConstant.DefaultValue.DEFAULT_OFFICE_CODE;
            }
            HashMap h = TableLookupCache.getCacheStructure();
            Vector defaultDateVect = (Vector) (h.get("DefaultDate"));
            if (defaultDateVect != null) {
                DefaultDateProperties dateProperties;
                for (int i = 0; i < defaultDateVect.size(); i++) {
                    dateProperties = (DefaultDateProperties) defaultDateVect.get(i);
                    if (dateProperties.getOFFCDE().equals(officeCode)) {
                        return dateProperties.getSYSNULLDTE();
                    }
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("Error >>> ", e);
            return null;
        }
    }
    @Deprecated
    public ServiceDataM getServiceDataM() {
        ServiceDataM serviceDataM = new ServiceDataM();
        serviceDataM.setSmsHost(getGeneralParamByCode("ORIG_SMS_HOST"));
        serviceDataM.setSmsPort(getGeneralParamByCode("ORIG_SMS_PORT"));
        serviceDataM.setSmsSender(getGeneralParamByCode("ORIG_SENDER_SMS"));
        serviceDataM.setEmailHost(getGeneralParamByCode("ORIG_EMAIL_HOST"));
        serviceDataM.setEmailPort(getGeneralParamByCode("ORIG_EMAIL_PORT"));
        serviceDataM.setEmailSender(getGeneralParamByCode("ORIG_SENDER_EMAIL"));

        return serviceDataM;
    }

    public String intToStringNCBData(int intNumber) {
        if (intNumber == -1) {
            return "-";
        }
        try {
            return Integer.toString(intNumber);
        } catch (Exception e) {
            return "";
        }
    }

    public int stringToIntPrescoreData(String str) {
        try {
            if (str != null) {
                if ("-".equals(str)) {
                    return -1;
                } else {
                    str = replaceAll(str, ",", "");
                    return Integer.parseInt(str);
                }
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public String getGearDesc(String code) {
        if ("A".equals(code)) {
            return "Automatic";
        } else if ("M".equals(code)) {
            return "Manual";
        } else {
            return "";
        }
    }

    public String getPersonalTypeDesc(String code) {
        if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(code)) {
            return "Applicant";
        } else if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(code)) {
            return "Guarantor";
        } else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(code)) {
            return "Supplementary Card";
        } else {
            return "";
        }
    }

    public static boolean isZero(String data) {
        if (!isEmptyString(data)) {
            if (Float.parseFloat(data) == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    @Deprecated
    public static String getRoleByJobState(String jobState) {
        if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equals(jobState)) {
            return OrigConstant.ROLE_CMR;
        } else if (ORIGWFConstant.JobState.DE_DRAFT_STATE.equals(jobState) || ORIGWFConstant.JobState.DE_INITIAL_STATE.equals(jobState)
                || ORIGWFConstant.JobState.DE_PENDING_STATE.equals(jobState) || ORIGWFConstant.JobState.DE_REWORK_STATE.equals(jobState)) {
            return OrigConstant.ROLE_DE;
        } else if (ORIGWFConstant.JobState.UW_CONDITIONAL_APPROVED_STATE.equals(jobState) || ORIGWFConstant.JobState.UW_ESCALATED_STATE.equals(jobState)
                || ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equals(jobState) || ORIGWFConstant.JobState.UW_NEW_STATE.equals(jobState)
                || ORIGWFConstant.JobState.UW_PENDING_STATE.equals(jobState) || ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equals(jobState)
                || ORIGWFConstant.JobState.UW_REASSIGNED_STATE.equals(jobState)) {
            return OrigConstant.ROLE_UW;
        } else if (ORIGWFConstant.JobState.PD_UNCOMPLETED_DOC_STATE.equals(jobState) || ORIGWFConstant.JobState.PD_APPROVED_STATE.equals(jobState)) {
            return OrigConstant.ROLE_PD;
        } else if (ORIGWFConstant.JobState.CMREX_EXCEPTION_STATE.equals(jobState)) {
            return OrigConstant.ROLE_XCMR;
        } else if (OrigConstant.JobState.XUW_NEW_STATE.equals(jobState)) {
            return OrigConstant.ROLE_XUW;
        }
        return "";
    }
    @Deprecated
    public BigDecimal calculatePercentDownPayment(BigDecimal total, BigDecimal value) {
        return value.multiply(new BigDecimal(100)).divide(total, 2, BigDecimal.ROUND_HALF_UP);
    }
    @Deprecated
    public Vector getReasonByDesicion(String decision) {
        HashMap h = TableLookupCache.getCacheStructure();
        Vector reasonVect = (Vector) (h.get("Reason"));
        Vector reasonResult = new Vector();
        if (reasonVect != null) {
            ReasonProperties reasonProperties;
            for (int i = 0; i < reasonVect.size(); i++) {
                reasonProperties = (ReasonProperties) reasonVect.get(i);
                if (decision.equals(reasonProperties.getAPRVSTS())) {
                    reasonResult.add(reasonProperties);
                }
            }
        }

        return reasonResult;
    }
    @Deprecated
    public String getReasonDecisionByAppDecision(String decision) {
        if (decision.equals(ORIGWFConstant.ApplicationDecision.REJECT) || decision.equals(ORIGWFConstant.ApplicationDecision.REJECT_PROPOSAL)) {
            return OrigConstant.Decision.REJECT;
        } else if (decision.equals(ORIGWFConstant.ApplicationDecision.CANCEL)) {
            return OrigConstant.Decision.CANCEL;
        } else if (decision.equals(ORIGWFConstant.ApplicationDecision.APPROVE) || decision.equals(ORIGWFConstant.ApplicationDecision.APPROVE_PROPOSAL)) {
            return OrigConstant.Decision.APPROVE;
        } else if (decision.equals(ORIGWFConstant.ApplicationDecision.DE_PENDING) || decision.equals(ORIGWFConstant.ApplicationDecision.UW_PENDING)
                || decision.equals(ORIGWFConstant.ApplicationDecision.PENDING_PROPOSAL)) {
            return OrigConstant.Decision.PENDING;
        } else if (decision.equals(ORIGWFConstant.ApplicationDecision.ESCALATE) || decision.equals(ORIGWFConstant.ApplicationDecision.OVERRIDE_CAMPAIGN)
                || decision.equals(ORIGWFConstant.ApplicationDecision.SEND_BACK_TO_DE)
                || decision.equals(ORIGWFConstant.ApplicationDecision.NOT_OVERRIDE_CAMPAIGN_TO_DE)
                || decision.equals(ORIGWFConstant.ApplicationDecision.NOT_OVERRIDE_CAMPAIGN_TO_UW)) {
            return OrigConstant.Decision.FORWARD;
        } else if (decision.equals(ORIGWFConstant.ApplicationDecision.CONDITIONAL_APPROVE)) {
            return OrigConstant.Decision.CONDITIONAL;
        } else if (decision.equals(ORIGWFConstant.ApplicationDecision.WITHDRAW)) {
            return OrigConstant.Decision.WITHDRAW;
        }

        return "";
    }
    @Deprecated
    public String getStepInstallmentTable(Vector vStepInstalment, HttpServletRequest request) {
        StringBuffer tableData = new StringBuffer("");
        try {

            tableData.append("<fieldset><LEGEND><font class=\"font2\"><strong>" + MessageResourceUtil.getTextDescription(request, "STEP_INSTALLMENT")
                    + "</strong></font></Legend><br>");
            tableData.append("<table cellpadding=\"0\" cellspacing=\"1\" align=\"center\"  border=0 width=\"100%\" bgcolor=\"white\"> ");
            tableData.append("<tr><TD width=\"20%\" class=\"TableHeader\" align=\"center\"><b>Seq</b></TD>");
            tableData.append("<td width=\"40%\" class=\"TableHeader\"  align=\"center\" ><b>Installment</b></td>");
            tableData.append("<td width=\"40%\" class=\"TableHeader\" align=\"center\" ><b>Term</b></td></tr>");
            if (vStepInstalment != null && vStepInstalment.size() > 0) {
                for (int i = 0; i < vStepInstalment.size(); i++) {
                    InstallmentDataM prmInstallmentDataM = (InstallmentDataM) vStepInstalment.get(i);
                    tableData.append("<tr><td  align=\"right\" class=\"textColS\">" + (i + 1) + "&nbsp;</td>");
                    tableData.append("<td align=\"right\" class=\"textColS\">"
                            + ORIGDisplayFormatUtil.displayCommaNumber(prmInstallmentDataM.getInstallmentAmount()) + "&nbsp;</td>");
                    tableData.append("<td align=\"right\" class=\"textColS\">" + ORIGDisplayFormatUtil.displayInteger(prmInstallmentDataM.getTermDuration())
                            + "&nbsp;</td>");
                    tableData.append("</tr>");
                }
            } else {
                tableData.append("<tr>");
                tableData.append("<td colspan=\"3\" align=\"center\" class=\"textColS\">No Record</td>");
                tableData.append("</tr>");
            }
            tableData.append("</table>");
        } catch (Exception e) {
            logger.error("Error >> ", e);
        }
        return tableData.toString();
    }

    //Check App Policy Rules Pass
    /*
     * public boolean checkAppPolicyPass(ApplicationDataM appliactionDataM) {
     * Vector personalInfoVect=appliactionDataM.getPersonalInfoVect(); boolean
     * result=true; for(int i=0;i <personalInfoVect.size();i++){
     * PersonalInfoDataM
     * prmPersonalInfoDataM=(PersonalInfoDataM)personalInfoVect.get(i);
     * if(prmPersonalInfoDataM==null){continue;} XRulesVerificationResultDataM
     * prmXRulesVerification=prmPersonalInfoDataM.getXrulesVerification();
     * if(prmPersonalInfoDataM==null){continue;} //Check CoBorrow Flag Unactive
     * if(! OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(
     * prmPersonalInfoDataM.getCoborrowerFlag())){ Vector
     * vXrulesPolicyRules=prmXRulesVerification.getVXRulesPolicyRulesDataM();
     * for(int j=0;j <vXrulesPolicyRules.size();j++){ XRulesPolicyRulesDataM
     * prmXRulesPolicyRules=(XRulesPolicyRulesDataM )vXrulesPolicyRules.get(j);
     * if(
     * XRulesConstant.PolicyRulesResult.RESULT_FAIL.equals(prmXRulesPolicyRules.getResult())
     * ||XRulesConstant.PolicyRulesResult.RESULT_SELECTED.equals(prmXRulesPolicyRules.getResult())){
     * result=false; break; } } } }
     * 
     * return result; }
     */

    // Check Appoval Authority
    @Deprecated
    public boolean checkApprovalAuthority(String policyVersion, String groupName, boolean policyNotFail, String customerType, String carType, String loanType,
            String scoringResult, BigDecimal totalExposure, String appType, BigDecimal installmentTerm, BigDecimal downPayment) {
//        ApprovAuthorM approvalData = null;
//        try {
//            approvalData = OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO().selectOrigMasterApprovalAuthority(groupName, loanType, carType, customerType,
//                    scoringResult, policyVersion, policyNotFail, totalExposure, appType, installmentTerm, downPayment);
//        } catch (OrigApplicationMException e) {
//            logger.error("Error Get Approval:", e);
//        }
//        if (approvalData != null) {
//            return true;
//        } else {
//            return false;
//        }
    	return false;
    }

    //Get Escerate Group
    @Deprecated
    public Vector getEscerateGroup(String policyVersion, String customerType, String carType, String loanType, String scoringResult, BigDecimal financeAmount,
            String appType, BigDecimal installmentTerm, BigDecimal downPayment, XRulesVerificationResultDataM xrulesVerificationDataM) {
//        Vector result = new Vector();
//        try {
//            //  result= ORIGDAOFactory.getUtilityDAO().getEscarateGroup(
//            // policyVersion, customerType, carType, loanType, scoringResult,
//            // financeAmount, appType,
//
//            //      installmentTerm, downPayment);
//            //Load Approval Authoiry
//            //Vector ApprovalAutority=
//            // OrigMasterDAOFactory.getOrigMasterApprovAuthorDAO().loadOrigMasterApprovalAuthority(policyVersion);
//            //get Appoval Group
//            ORIGUtility utility = new ORIGUtility();
//            //for
//            Vector vGroup = (Vector) ORIGDAOFactory.getUtilityDAO().getPolicyGroup(policyVersion);
//
//            for (int i = 0; i < vGroup.size(); i++) {
//                CacheDataM cachGroupName = (CacheDataM) vGroup.get(i);
//                logger.debug("Group Name =" + cachGroupName.getCode());
//                boolean policyGroupPass = utility.checkPolicyPass(policyVersion, cachGroupName.getCode(), xrulesVerificationDataM);
//                logger.debug("Policy " + policyGroupPass);
//                if (utility.checkApprovalAuthority(policyVersion, cachGroupName.getCode(), policyGroupPass, customerType, carType, loanType, scoringResult,
//                        financeAmount, appType, installmentTerm, downPayment)) {
//                    logger.debug("Add Group " + cachGroupName.getCode());
//                    result.add(cachGroupName);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Error ", e);
//            e.printStackTrace();
//        }
//        return result;
    	return null;
    }

    //Check Policy excepiton XUW
    @Deprecated
    public boolean checkPolicyPass(String policyVersion, String groupName, XRulesVerificationResultDataM xrulesVerification) {
//        HashMap hPolicyMapCode = new HashMap();//Map Between policy and policy
//                                               // Code
//        HashMap hPolcicyMapLevel = new HashMap();//Map Level
//        Vector vPolicyLevelGroup = null;
//        boolean result = true;
//        logger.debug(" Policy Version " + policyVersion + " Group Name " + groupName);
//        try {
//            //ture no Excection
//            //false not pass
//            //ORIGDAOFactory.getUtilityDAO();
//            Vector vPolicyMapCode = ORIGDAOFactory.getOrigPolicyLevelMapMDAO().loadModelOrigPolicyLevelMapDataM(policyVersion);
//            for (int i = 0; i < vPolicyMapCode.size(); i++) {
//                OrigPolicyLevelMapDataM prmOrigPolicyLevelMapDataM = (OrigPolicyLevelMapDataM) vPolicyMapCode.get(i);
//                hPolicyMapCode.put(prmOrigPolicyLevelMapDataM.getPolicyCode(), prmOrigPolicyLevelMapDataM);
//            }
//        } catch (OrigMasterMException e) {
//            logger.error("Error:", e);
//        }
//        Vector vPolicy = xrulesVerification.getVXRulesPolicyRulesDataM();
//        for (int i = 0; i < vPolicy.size(); i++) {
//            XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM = (XRulesPolicyRulesDataM) vPolicy.get(i);
//            logger.debug("Policy rules Res " + prmXRulesPolicyRulesDataM.getPolicyCode() + " Type " + prmXRulesPolicyRulesDataM.getPolicyType() + "  resuslt "
//                    + prmXRulesPolicyRulesDataM.getResult());
//            if (XRulesConstant.PolicyRulesResult.RESULT_FAIL.equals(prmXRulesPolicyRulesDataM.getResult())
//                    || XRulesConstant.PolicyRulesResult.RESULT_SELECTED.equals(prmXRulesPolicyRulesDataM.getResult())) {
//                OrigPolicyLevelMapDataM resPolicyLevel = (OrigPolicyLevelMapDataM) hPolicyMapCode.get(prmXRulesPolicyRulesDataM.getPolicyCode());
//                if (resPolicyLevel != null) {
//                    BigDecimal policyCount = (BigDecimal) hPolcicyMapLevel.get(resPolicyLevel.getPolicyLevel());
//                    if (policyCount == null) {
//                        policyCount = new BigDecimal(1);
//                    } else {
//                        policyCount = policyCount.add(new BigDecimal(1));
//                    }
//                    hPolcicyMapLevel.put(resPolicyLevel.getPolicyLevel(), policyCount);
//                }
//            }
//        }
//        //Map Policy to Policy Group
//        logger.debug("Policy Level Count");
//        Iterator iPolicy = hPolcicyMapLevel.keySet().iterator();
//        BigDecimal totalPolicyCount = new BigDecimal(0);
//        while (iPolicy.hasNext()) {
//            String levelName = (String) iPolicy.next();
//            BigDecimal bPolicyCount = (BigDecimal) hPolcicyMapLevel.get(levelName);
//            logger.debug("  level " + levelName + " value " + bPolicyCount);
//            if (bPolicyCount != null) {
//                totalPolicyCount = totalPolicyCount.add(bPolicyCount);
//            }
//        }
//
//        try {
//            //Get Policy Exception
//            vPolicyLevelGroup = ORIGDAOFactory.getOrigPolicyLevelGroupMDAO().findPolicyLevelGroup(policyVersion, groupName);
//
//        } catch (OrigMasterMException e1) {
//            logger.debug("Error ", e1);
//        }
//        if (vPolicyLevelGroup != null) {
//            for (int i = 0; i < vPolicyLevelGroup.size(); i++) {
//                OrigPolicyLevelGroupDataM prmOrigPolicyLevelGroupDataM = (OrigPolicyLevelGroupDataM) vPolicyLevelGroup.get(i);
//                BigDecimal maxLevel = new BigDecimal(prmOrigPolicyLevelGroupDataM.getLevelAmount());
//                BigDecimal levelCount = (BigDecimal) hPolcicyMapLevel.get(prmOrigPolicyLevelGroupDataM.getLevelName());
//                logger.debug(" LevelName " + prmOrigPolicyLevelGroupDataM.getLevelName() + "    MAX =" + maxLevel + "   value " + levelCount);
//                if (levelCount != null && levelCount.compareTo(maxLevel) == 1) {
//                    logger.debug("Level couunt more than  Max level");
//                    result = false;
//                    break;
//                }
//            }
//        }
//        //Group Total
//        logger.debug("Result Before Check Group total "+result);
//        if (result) {
//            OrigPolicyLevelGroupTotalDataM origPolicyLevelGroupTotal = null;
//            try {
//                origPolicyLevelGroupTotal = ORIGDAOFactory.getOrigPolicyLevelGroupTotalMDAO().findPolicyLevelGroup(policyVersion, groupName);
//            } catch (OrigMasterMException e1) {
//                logger.debug("Error ", e1);
//            }
//            
//            if(origPolicyLevelGroupTotal!=null){
//                logger.debug("Total Policy Count  "+totalPolicyCount+" Total Policy   "+origPolicyLevelGroupTotal.getToalLevelAmount());
//              if(totalPolicyCount.compareTo(new BigDecimal(origPolicyLevelGroupTotal.getToalLevelAmount()))==1){
//                  logger.debug("Total Policy Fail Over  Max Total Policy   ");
//                result=false;         
//              }                
//            }
//        }
//        return result;
    	return false;
    }
    @Deprecated
    public OrigPolicyVersionDataM getPolicyVersion(Date applicationCreateDate) {
//        OrigPolicyVersionDataM origPolicyVersion = null;
//        try {
//            //applicationCreateDate
//            String appCreateDate = ORIGDisplayFormatUtil.DateToString(applicationCreateDate, "dd/MM/yyyy");
//            origPolicyVersion = ORIGDAOFactory.getOrigPolicyVersionMDAO().getPolicyVersion(appCreateDate);
//        } catch (Exception e) {
//            logger.error("Error:", e);
//        }
//        return origPolicyVersion;
    	return null;
    }
    @Deprecated
    public String getApprovalGroup(String userName) {
//        String result = null;
//        try {
//            result = OrigMasterDAOFactory.getOrigMasterUserDetailDAO().getUserApprovGroup(userName);
//        } catch (OrigApplicationMException e) {
//            logger.error("Error:", e);
//        }
//        return result;
    	return null;
    }
    @Deprecated
    public Vector getSLAForXUW(String qName, String status, String action, String jobState, String userName, double time) {
//        UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
//        try {
//            Vector applicationSLATmpVect = utilityDAO.getSLAForXUW(qName, status, action, jobState, userName, time);
//            logger.debug("applicationSLATmpVect = " + applicationSLATmpVect.size());
//            /*
//             * if(applicationSLATmpVect.size() > 0){ for(int i=0; i
//             * <applicationSLATmpVect.size(); i++){
//             * applicationSLAVect.add(applicationSLATmpVect.get(i)); } }
//             */
//            return applicationSLATmpVect;
//
//        } catch (EjbUtilException e) {
//            logger.error("Error >> ", e);
//            return null;
//        }
    	return null;
    }
    @Deprecated
    public BigDecimal getFinalCreditAppoval(ApplicationDataM applicationDataM) {
//        //Get Loan
//        //BigDecimal loanFinanceAmt=new BigDecimal(0);
//        LoanDataM loanDataM = null;
//        if (applicationDataM.getLoanVect() != null) {
//            loanDataM = (LoanDataM) applicationDataM.getLoanVect().get(0);
//            // loanFinanceAmt=loanDataM.getCostOfFinancialAmt();
//        }
//        OrigXRulesUtil xRulesUtil = OrigXRulesUtil.getInstance();
//        Vector vPersoanlInfo = applicationDataM.getPersonalInfoVect();
//        BigDecimal maxTotalExposure = new BigDecimal(0);
//        if (vPersoanlInfo == null) {
//            vPersoanlInfo = new Vector();
//        }
//        for (int count = 0; count < vPersoanlInfo.size(); count++) {
//            PersonalInfoDataM persanalInfoDataM = (PersonalInfoDataM) vPersoanlInfo.get(count);
//            logger.debug(" Personal type  " + persanalInfoDataM.getPersonalType() + " ID no" + persanalInfoDataM.getIdNo());
//            BigDecimal totalExposre = xRulesUtil.getTotalExposure(persanalInfoDataM.getXrulesVerification(), loanDataM);
//            logger.debug(" Total Exposure " + totalExposre);
//            if (maxTotalExposure.compareTo(totalExposre) == -1) {
//                logger.debug(" More Than max update Value");
//                maxTotalExposure = totalExposre;
//            } else {
//                logger.debug("not More Than max");
//            }
//        }
//        logger.debug(" Credit Approval =" + maxTotalExposure);
//
//        return maxTotalExposure;
    	return null;
    }
    @Deprecated
    public HashMap mapPolicyRules(Vector vPolicyMapRules, Vector vPolicyRules) {
        HashMap hResult = new HashMap();
        if (vPolicyMapRules != null) {
            for (int i = 0; i < vPolicyMapRules.size(); i++) {
                OrigPolicyLevelMapDataM prmOrigPolicyLevelMapDataM = (OrigPolicyLevelMapDataM) vPolicyMapRules.get(i);
                boolean remove = false;
                String policyCode = "";
                if (hResult.containsKey(prmOrigPolicyLevelMapDataM.getPolicyLevel())) {
                    Vector vPolicyGroupMap = (Vector) hResult.get(prmOrigPolicyLevelMapDataM.getPolicyLevel());
                    vPolicyGroupMap.add(prmOrigPolicyLevelMapDataM);
                    remove = true;
                    policyCode = prmOrigPolicyLevelMapDataM.getPolicyCode();
                } else {
                    if (prmOrigPolicyLevelMapDataM.getPolicyLevel() != null) {
                        Vector vPolicyGroupMap = new Vector();
                        vPolicyGroupMap.add(prmOrigPolicyLevelMapDataM);
                        hResult.put(prmOrigPolicyLevelMapDataM.getPolicyLevel(), vPolicyGroupMap);
                        remove = true;
                        policyCode = prmOrigPolicyLevelMapDataM.getPolicyCode();
                    }
                }
                if (remove) {//Remove Code
                    for (int j = vPolicyRules.size() - 1; j >= 0; j--) {
                        PolicyRulesDataM policyRulesDataM = (PolicyRulesDataM) vPolicyRules.get(j);
                        if (policyRulesDataM.getPolicyCode() != null && policyRulesDataM.getPolicyCode().equals(policyCode)) {
                            vPolicyRules.remove(j);
                        }
                    }
                }
            }
        }
        return hResult;
    }
    @Deprecated
    public boolean checkPolicyException(String policyVersion, XRulesVerificationResultDataM xrulesVerification) {
//        //policyGroupMatch
//        boolean exception = false;
//        OrigPolicyRulesExceptionDAO origPolicyRulesExceptionDAO = ORIGDAOFactory.getOrigPolicyRulesExceptionDAO();
//        Vector vPolicyException = null;
//        Vector vPolicyRules = xrulesVerification.getVXRulesPolicyRulesDataM();
//        try {
//            vPolicyException = origPolicyRulesExceptionDAO.loadModelOrigPolicyRulesExceptionDataM(policyVersion);
//
//            if (vPolicyException == null) {
//                vPolicyException = new Vector();
//            }
//            //logger.debug("vPolicyException Size "+vPolicyException.size());
//            //logger.debug("vPolicyRules Size "+vPolicyRules.size());
//            for (int i = 0; i < vPolicyException.size(); i++) {
//                OrigPolicyRulesExceptionDataM origPolicyRulesExceptionDataM = (OrigPolicyRulesExceptionDataM) vPolicyException.get(i);
//                for (int j = 0; j < vPolicyRules.size(); j++) {
//                    XRulesPolicyRulesDataM xRulesPolicyRulesDataM = (XRulesPolicyRulesDataM) vPolicyRules.get(j);
//                    if (OrigConstant.ORIG_FLAG_Y.equals(origPolicyRulesExceptionDataM.getPolicyFlag())
//                            && (XRulesConstant.PolicyRulesResult.RESULT_FAIL.equals(xRulesPolicyRulesDataM.getResult()) || OrigConstant.ORIG_FLAG_Y
//                                    .equals(xRulesPolicyRulesDataM.getResult()))
//                            && xRulesPolicyRulesDataM.getPolicyCode().equals(origPolicyRulesExceptionDataM.getPolicyCode())) {
//                        logger.debug("Policy Code " + xRulesPolicyRulesDataM.getPolicyCode() + " Hit Policy exception");
//                        return true;
//                    }
//                }
//            }
//
//        } catch (OrigMasterMException e) {
//            logger.error("Error ", e);
//        }
//        return exception;
    	return false;
    }
    @Deprecated
  public Vector getPolicyExcetption(String policyVersion, Vector vPolicyRules){
//      Vector result=new Vector();
//      if(vPolicyRules==null){vPolicyRules=new Vector();}
//      Vector vPolicyException=null;
//      try {
//          vPolicyException=ORIGDAOFactory.getOrigPolicyRulesExceptionDAO().loadModelOrigPolicyRulesExceptionDataM(policyVersion);
//    } catch (OrigMasterMException e) {                 
//        logger.error("Error" ,e);
//    }
//    if(vPolicyException==null){vPolicyException=new Vector();}
//      for(int i=0;i<vPolicyRules.size();i++){
//          XRulesPolicyRulesDataM  xrulesPolicyRulesDataM=(XRulesPolicyRulesDataM)vPolicyRules.get(i);
//          for(int j=0;j<vPolicyException.size();j++){
//           OrigPolicyRulesExceptionDataM  origPolicyException=(OrigPolicyRulesExceptionDataM)vPolicyException.get(j);
//           if(origPolicyException.getPolicyCode().equals(xrulesPolicyRulesDataM.getPolicyCode())){
//               if( XRulesConstant.PolicyRulesResult.RESULT_FAIL.equals(xrulesPolicyRulesDataM.getResult())||XRulesConstant.PolicyRulesResult.RESULT_SELECTED.equals(xrulesPolicyRulesDataM.getResult())){
//              result.add(xrulesPolicyRulesDataM);
//               }
//             }              
//          }   
//      }      
//      return result;
    	return null;
  }
  @Deprecated
  public String getCollateralTable(Vector collateralV, HttpServletRequest request) {
      StringBuffer tableData = new StringBuffer("");
      CollateralDataM collateralM;
      AppraisalDataM appraisalM;
      String titleName;
      String maritalStatus;
      ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
      String disabledCheckbox = "";
      Vector propertyTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",29);
      AddressDataM addressDataM ;
      String location = "";
      
      try {
    	  tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
          tableData.append("<tr><td class='TableHeader'>");
          tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
          tableData.append("<tr>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\"></td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"20%\">"+ MessageResourceUtil.getTextDescription(request, "PROPERTY_TYPE") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"20%\">"+ MessageResourceUtil.getTextDescription(request, "LOCATION_OF_PROPERTY") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"20%\">"+ MessageResourceUtil.getTextDescription(request, "ACCREDITED_PROPERTY") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"20%\">"+ MessageResourceUtil.getTextDescription(request, "FMV") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"15%\">"+ MessageResourceUtil.getTextDescription(request, "LTV_PERCENT") + "</td>");
          tableData.append("</tr></table></td></tr>");
          
          if(collateralV!=null && collateralV.size()>0){
              for(int i=0;i<collateralV.size();i++){
                  collateralM = (CollateralDataM)collateralV.get(i);
                  if(collateralM!=null){
                      if(collateralM.getAppraisal()!=null){
                          appraisalM = collateralM.getAppraisal();
                      }else{
                          appraisalM = new AppraisalDataM();
                      }
                      addressDataM = collateralM.getAddress();
						
                      tableData.append("<tr><td align='center' class='gumaygrey2'>");
                      tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                      tableData.append("<tr>");
	                  tableData.append("<td class='jobopening2' width='5%' align=\"center\">"+ ORIGDisplayFormatUtil.displayCheckBox("delete", "MGloanSeq", String.valueOf(i+1),disabledCheckbox) + "</td>");
	                  tableData.append("<td class='jobopening2' width='20%' align=\"center\"><a href=\"javascript:loadPopup('loan','LoadMortgagePopup','1150','680','0','0','"+collateralM.getSeq()+"','"+collateralM.getPropertyType()+"','')\">"
                              +ORIGDisplayFormatUtil.displaySelectTag(propertyTypeVect,collateralM.getPropertyType(),"propertyType",ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW)+"</a></td>");       
	                  tableData.append("<td class='jobopening2' width='20%' align=\"center\">"+DisplayFormatUtil.displayHTML(cacheUtil.getORIGMasterDisplayNameDataM("Province", addressDataM.getProvince()))+"</td>");
	                  tableData.append("<td class='jobopening2' width='20%' align=\"center\">"+DisplayFormatUtil.displayHTML(collateralM.getAccreditedProperty())+"</td>");
	                  tableData.append("<td class='jobopening2' width='20%' align=\"center\">"+DisplayFormatUtil.displayHTML(appraisalM.getTotalFMV().toString())+"</td>");
	                  tableData.append("<td class='jobopening2' width='15%' align=\"center\">"+DisplayFormatUtil.displayHTML(appraisalM.getLtv().toString())+"</td>");                  
	                  tableData.append("</tr></table></td></tr>");
                  }
              }
          }else {
        	  tableData.append("<tr><td align='center' class='gumaygrey2'>");
              tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
              tableData.append("<tr>");
              tableData.append("<td class='jobopening2' colspan=\"5\" align=\"center\">No Record</td>");
              tableData.append("</tr></table></td></tr>");
          }
          
          
          tableData.append("</table>");
      } catch (Exception e) {
          logger.error("Error >> ", e);
      }
      return tableData.toString();
  }
  @Deprecated
  public String getPLAddressTable(Vector addressVect, HttpServletRequest request) {
      StringBuffer tableData = new StringBuffer("");
      AddressDataM addressDataM;
      ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
      String personalType = (String) request.getSession().getAttribute("PersonalType");
      try {
      	tableData.append("<table class='gumayframe3' cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
          tableData.append("<tr><td class='TableHeader'>");
          tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
          tableData.append("<tr>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\"></td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"18%\">" + MessageResourceUtil.getTextDescription(request, "ADDRESS_TYPE") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\">" + MessageResourceUtil.getTextDescription(request, "SEQ") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "ADDRESS_FORMAT") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"12%\">" + MessageResourceUtil.getTextDescription(request, "PHONE_NO1") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"5%\">" + MessageResourceUtil.getTextDescription(request, "EXT1") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"15%\">" + MessageResourceUtil.getTextDescription(request, "ADDRESS_STATUS") + "</td>");
          tableData.append("<td class='Bigtodotext3' align='center' width=\"25%\">" + MessageResourceUtil.getTextDescription(request, "CONTACT_PERSON") + "</td>");
          tableData.append("</tr></table></td></tr>");
          String addressTypeDesc = null;
          String addressStatus = null;
          String disabledChk = "";
          if (addressVect != null && addressVect.size() > 0) {
              for (int i = 0; i < addressVect.size(); i++) {
                  addressDataM = (AddressDataM) addressVect.get(i);
                  addressTypeDesc = cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType());
                  addressStatus = cacheUtil.getORIGMasterDisplayNameDataM("AddressStatus", addressDataM.getStatus());
                  logger.debug("addressDataM.getOrigOwner() = " + addressDataM.getOrigOwner());
                  /*if (("N").equals(addressDataM.getOrigOwner())) {
                      disabledChk = "disabled";
                  } else {
                      disabledChk = "";
                  }*/
                  if (!OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType) || OrigConstant.ADDRESS_TYPE_HOME.equals(addressDataM.getAddressType())) {
                  	tableData.append("<tr><td align='center' class='gumaygrey2'>");
                      tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
                      tableData.append("<tr>");
	                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">"
	                            + ORIGDisplayFormatUtil.displayCheckBox("delete", "addressSeq", String.valueOf(addressDataM.getAddressSeq()), "") + "</td>");
	                    tableData.append("<td class='jobopening2' width='18%'><a href=\"javascript:loadPopup('address','LoadAddressPopup','1150','450','200','10','" + addressDataM.getAddressSeq()
	                            + "','" + (i + 1) + "','" + personalType + "')\"><u>" + ORIGDisplayFormatUtil.forHTMLTag(addressTypeDesc) + "</u></a></td>");
	                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">" + (i + 1) + "</td>");
	                    tableData.append("<td class='jobopening2' width='15%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(this.getAddressFormatDesc(addressDataM.getAddressFormat()))
	                            + "</td>");
	                    tableData.append("<td class='jobopening2' width='12%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(addressDataM.getPhoneNo1()) + "</td>");
	                    tableData.append("<td class='jobopening2' width='5%' align=\"center\">" + ORIGDisplayFormatUtil.forHTMLTag(addressDataM.getPhoneExt1()) + "</td>");
	                    tableData.append("<td class='jobopening2' width='15%'>" + ORIGDisplayFormatUtil.forHTMLTag(addressStatus) + "</td>");
	                    tableData.append("<td class='jobopening2' width='25%'>" + ORIGDisplayFormatUtil.forHTMLTag(addressDataM.getContactPerson()) + "</td>");
	                    tableData.append("</tr></table></td></tr>");
                  }
              }
          } else {
          	tableData.append("<tr><td align='center' class='gumaygrey2'>");
              tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
              tableData.append("<tr>");
              tableData.append("<td class='jobopening2' colspan=\"8\" align=\"center\">No Record</td>");
              tableData.append("</tr></table></td></tr>");
          }
          tableData.append("</table>");
      } catch (Exception e) {
          logger.error("Error >> ", e);
      }
      return tableData.toString();
  }
  
}
