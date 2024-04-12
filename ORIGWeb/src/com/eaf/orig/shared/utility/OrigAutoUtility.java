/*
 * Created on Oct 3, 2007
 * Created by Sankom Sanpunya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import com.eaf.cache.TableLookupCache;
import com.eaf.j2ee.pattern.util.DisplayFormatUtil;
import com.eaf.orig.cache.properties.SubFormMainCusTypeProperties;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author Sankom Sanpunya
 *
 * Type: OrigAutoUtility
 */
public class OrigAutoUtility {

	private static OrigAutoUtility me;

	/**
	 *  Create instance of NaosAutoUtility
	 */
	public synchronized static OrigAutoUtility getInstance_AutoSG() {
		if (me == null) {
			me = new OrigAutoUtility();
		}
		return me;
	}
	
	/**
	 *  Convert String to Int defualt is zero
	 */
	public int stringToInt(String str){
		try {
			if(str != null){
				str = replaceAll(str, ",","");
				return Integer.parseInt(str);
			} else {
				return 0;
			}			
		} catch (Exception e) {
			return 0;
		}
	}


	/**
	 *  Convert int to String defualt is empty
	 */	
	public String intToString(int intNumber){
		try {
			return Integer.toString(intNumber);
		} catch (Exception e) {
			return "";
		}
	}	
	
	/**
	 *  Convert String to float defualt is zero
	 */
	public float stringToFloat(String str){
		try {
			if(str != null){
				str = replaceAll(str, ",","");
				return Float.parseFloat(str);
			} else {
				return 0;
			}			
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 *  Convert String to double defualt is zero
	 */
	public double stringToDouble(String str){
		try {
			if(str != null){
				str = replaceAll(str, ",","");
				return Double.parseDouble(str);
			} else {
				return 0;
			}			
		} catch (Exception e) {
			return 0;
		}
	}			
	
	/**
	 *  Convert float to String defualt is empty
	 */	
	public String floatToString(float fltNumber){
		try {
			return Float.toString(fltNumber);
		} catch (Exception e) {
			return "";
		}
	}	
	
	/**
	 *  Convert double to String defualt is empty
	 */	
	public String doubleToString(double fltNumber){
		try {
			return Double.toString(fltNumber);
		} catch (Exception e) {
			return "";
		}
	}		
	
	/**
	 *  Convert String to Timestamp defualt is empty
	 */	
	public Date stringToDate(String strDate){
		try {
		return convertUtilToSqlDate(strDate );//DisplayFormatUtil.StringToDate(strDate,"dd/MM/yyyy"));	
		} catch (Exception e) {
			return null;
		}				
	}
	
    public java.sql.Date convertUtilToSqlDate(String utilDate) {

        if ((utilDate != null) && !utilDate.equals("")) {
        	utilDate = DisplayFormatUtil.stringDelChar(utilDate, '/');

            java.util.Date tmpDate = null;

            try {
                tmpDate = DisplayFormatUtil.StringToDate(utilDate, "ddMMyyyy");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return new java.sql.Date(tmpDate.getTime());
        }

        return null;
    }
    
    
	/**
	 *  Replace String 
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
    
    public boolean isContainJobState(String jobState){
		String[] aryJobState = new String[]{"ST0300","ST0301","ST0302","ST0400","ST0401","ST0402",
																			"ST0403","ST1000","ST1200","ST1201","ST1202","ST1203",
																			"ST1205","ST1206","ST1207","ST1208","ST1209","ST1210",
																			"ST1300","ST1301","ST1302","ST1400","ST1401","ST1402",
																			"ST1403"};
																			    	
    	if(jobState != null){
			for(int i = 0; i < aryJobState.length; i++){
				if(jobState.equals(aryJobState[i])){
					return true;
				}
			}
    	}
    	    	
  		return false;  
    }
    
   /**
     * @param   vtModel
     * @param   sortMethod
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
     * @param   vtModel
     * @param   sortMethod
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
     * @param   className
     * @param   obj
     * @param   methodName
     * @param   objParam
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
    
	public static HashMap filterSubformByMainCusType(HashMap allSubforms, String mainCusType){
		System.out.println("+++++++++++++++++++++ filterSubformByMainCusType ++++++++++++++++++++++++");
		mainCusType = (mainCusType != null)?mainCusType:"";
		HashMap h = TableLookupCache.getCacheStructure();
		Vector allSubFormMainCusType = (Vector) (h.get("SubFormByMainCusTypeCacheDataM"));
		HashMap hsSubFormMainCusType = new HashMap();
		if(allSubFormMainCusType != null){
//			System.out.println("in put ");	
			for(int i = 0; i < allSubFormMainCusType.size(); i ++){
				SubFormMainCusTypeProperties subform = (SubFormMainCusTypeProperties)allSubFormMainCusType.get(i);

//				System.out.println("put subform = " + subform.getSubFormID());
				hsSubFormMainCusType.put((String)subform.getSubFormID(), subform);
			}
		}			
		
		Vector vtSubforms  = new Vector(allSubforms.values());
		HashMap newAllSubforms = new HashMap();
		
		System.out.println("Subform size = " + ((allSubforms != null)?allSubforms.size():0));
		
		if(vtSubforms != null){
			for(int i = 0; i < vtSubforms.size(); i++){
				ORIGSubForm subForm =  (ORIGSubForm)vtSubforms.get(i);
				System.out.println("++checked subform = " +subForm.getSubFormID());
				if(hsSubFormMainCusType != null && hsSubFormMainCusType.containsKey(subForm.getSubFormID())){
					SubFormMainCusTypeProperties hsMainCusType = (SubFormMainCusTypeProperties)hsSubFormMainCusType.get(subForm.getSubFormID());
					if(mainCusType.equalsIgnoreCase(hsMainCusType.getMainCustomerType())){
						newAllSubforms.put(subForm.getSubFormID(), subForm);
						System.out.println("====> save new Subform = " + subForm.getSubFormID());
					}else {
						continue;
					}
				}else{
					newAllSubforms.put(subForm.getSubFormID(), subForm);
					System.out.println("====> save new Subform = " + subForm.getSubFormID());
				}					
				
			}
		}
		
		System.out.println("New Subform size = " + ((newAllSubforms != null)?newAllSubforms.size():0));
		
		return newAllSubforms;
	}
	
	public static boolean isContainSubform(HashMap allSubforms, String subformName){
		System.out.println("++++++++++++++++++Start  isContainSubform  ++++++++++++++++++++++++++");
		System.out.println("784allSubforms = " + allSubforms);
		System.out.println("784subformName = " + subformName);
		boolean isError = false;
		try {
			if(allSubforms != null && subformName != null){
				Vector vtAllSubForms = new Vector(allSubforms.values());
				for(int i = 0; i < vtAllSubForms.size(); i++){
					ORIGSubForm subForm =  (ORIGSubForm)vtAllSubForms.get(i);
					String subformID = (subForm != null)?subForm.getSubFormID():"";
					System.out.println("SubformName of vector = " + subForm.getSubFormID());
					System.out.println("SubformName of param = " + subformName);					
					System.out.println("Result Check subform = " + ((subformID.equalsIgnoreCase(subformName))?"Yes":"No"));
					if(subformID != null && subformID.equalsIgnoreCase(subformName)){
						System.out.println("Validate  Subform Name= " + subformID);
						isError =true;
						break;
					}
//					if(subformName != null && subForm != null && subformName.trim().equalsIgnoreCase(subForm.getSubFormID())){
//						System.out.println("Validate  Subform Name= " + subForm.getSubFormID());
//						isError =true;
//						break;
//					}
				}			
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println("++++++++++++++++++End  isContainSubform  ++++++++++++++++++++++++++");
		return isError;		
	}	
	
    /**
     * Convert String to int
     * @param   strInt
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
     * @param   strDouble
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
     * @param   obj
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
     * @param   strDate
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
     * - input Date format is dd/mm/yyyy
     *
     * @param   strDate
     *
     * @return String array of date
     */
    public static String getCurrentDateString() {
		//try {
			//Timestamp currentTime = ORIGDAOFactory.getApplicationMDAO_SG().loadSysDay_SG( OrigServiceLocator.ORIG_DB);
			//String currentDate = DisplayFormatUtil.dateTimetoString(currentTime).substring(0,DisplayFormatUtil.dateTimetoString(currentTime).lastIndexOf(" "));
			return null;// currentDate;
		//} catch (ApplicationMException e) {
		//	return null;
	//	}
    }
}
