package com.eaf.orig.ulo.fraudonline.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.eaf.ulo.cache.controller.CacheController;

public class CatchReadUtil {

	
	public static String displayName(String cacheId,String code,String fin,String fout){
		
		String result="";
		
		HashMap<String,HashMap<String, String>> catchDatas=CacheController.getCacheData(cacheId);
		Iterator it = catchDatas.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        HashMap<String, String> data=(HashMap<String, String>) pair.getValue();
	        
	        String fvalue=data.get(fin);
	        
	        if(fvalue!=null&&fvalue.equals(code)){
	        	result=data.get(fout);
	        }
	        
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	       // it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    return result;
	}
	
	
	
	
}
