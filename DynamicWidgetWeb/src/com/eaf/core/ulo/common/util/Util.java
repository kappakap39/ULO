package com.eaf.core.ulo.common.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Util {
    public static boolean empty(String data){
       return (data == null || "null".equals(data) || data.length() == 0 || data.trim().length() == 0);
    } 
    public static boolean empty(ArrayList<?> vList){
    	return (null == vList || vList.size() == 0 || vList.isEmpty());
    }
    public static boolean empty(Vector<?> vVect){
    	return (null == vVect || vVect.size() == 0 || vVect.isEmpty());
    }
    public static boolean empty(BigDecimal num){
    	return (null == num);
    }
    public static boolean empty(HashMap<?,?> hashMap){
    	return (null == hashMap || hashMap.size() == 0 || hashMap.isEmpty());
    }
    public static boolean empty(Date date){
    	return (null == date);
    }
    public static boolean empty(List<?> vList){
    	return (null == vList || vList.size() == 0 || vList.isEmpty());
    }
    public static boolean empty(Set<?> vSet){
    	return (null == vSet || vSet.size() == 0 || vSet.isEmpty());
    }
    public static boolean empty(Object obj){    	
    	if(obj instanceof String){
    		return empty((String)obj);
    	}else if(obj instanceof ArrayList<?>){
    		return empty((ArrayList<?>)obj);
    	}else if(obj instanceof Vector<?>){
    		return empty((Vector<?>)obj);
    	}else if(obj instanceof BigDecimal){
    		return empty((BigDecimal)obj);
    	}else if(obj instanceof Date){
    		return empty((Date)obj);
    	}else if(obj instanceof HashMap<?,?>){
    		return empty((HashMap<?,?>)obj);
    	}else if(obj instanceof List<?>){
    		return empty((List<?>)obj);
    	} else if(obj instanceof Set<?>){
    		return empty((Set<?>)obj);
    	} else if(obj instanceof Integer) {
    		int val = ((Integer)obj).intValue();
    		if(val == 0 ){
    			return true;
    		}
    	}
    	return (null == obj);
    }
}
