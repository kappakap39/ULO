package flp.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;



public class Util {
	public Util(){
		
	}
	public static Boolean empty(String data){
	       return (data==null||"null".equalsIgnoreCase(data)||"(null)".equalsIgnoreCase(data)  
	    		   ||data.length()== 0||data.trim().length()==0||"Null".equalsIgnoreCase(data)||"Null".equals(data)
	    		   		||"Blank".equalsIgnoreCase(data));
	}
	public static Boolean emptyNull(String data){
	       return (data == null || "null".equalsIgnoreCase(data)|| "(null)".equalsIgnoreCase(data)  
	    		   || data.length() == 0 || data.trim().length() == 0 ||"Null".equalsIgnoreCase(data));
	}
	public static String replaceBlank(String data){
		return Util.empty(data)?"Blank":data;
	}

    public static boolean empty(BigDecimal num){
    	return (null == num);
    }
    public static boolean empty(Date date){
    	return (null == date);
    }
    @SuppressWarnings("rawtypes")
	private static boolean empty(List vList){
    	return (null == vList || vList.size() == 0 || vList.isEmpty());
    }
    @SuppressWarnings("rawtypes")
	public static boolean empty(Set vSet){
    	return (null == vSet || vSet.size() == 0 || vSet.isEmpty());
    }
    @SuppressWarnings("rawtypes")
	public static boolean empty(Object obj){    	
    	if(obj instanceof String){
    		return empty((String)obj);
    	}else if(obj instanceof BigDecimal){
    		return empty((BigDecimal)obj);
    	}else if(obj instanceof Date){
    		return empty((Date)obj);
    	}else if(obj instanceof HashMap<?,?>){
    		return empty((HashMap<?,?>)obj);
    	}else if(obj instanceof List){
    		return empty((List)obj);
    	} else if(obj instanceof Integer) {
    		int val = ((Integer)obj).intValue();
    		if(val == 0 ){
    			return true;
    		}
    	}
    	return (null == obj);
    }
    public static String toString(int number){
    	return String.valueOf(number);
    }
    public static Integer toInteger(String value){
    	try{
    		return Integer.valueOf(value);
    	}catch(Exception e){}
    	return 0;
    }
    public static BigDecimal toBigDecimal(String num){
    	try{
    		return new BigDecimal(num);
    	}catch(Exception e){
    	}
    	return BigDecimal.ZERO;
    }
    public static BigDecimal round(BigDecimal num,int precision,RoundingMode roundingMode){
    	if(null!=num){
    		MathContext mathContext = new MathContext(precision, roundingMode);
    		return num.round(mathContext).setScale(0, roundingMode);
    	}
    	return BigDecimal.ZERO;
    }
    public static BigDecimal scale(BigDecimal num){
    	if(null!=num){
    		return num.setScale(2,RoundingMode.HALF_UP);
    	}
    	return BigDecimal.ZERO.setScale(2);
    }
    public static BigDecimal min(List<BigDecimal> nums){
    	if(!empty(nums))return Collections.min(nums);
    	return BigDecimal.ZERO;
    }
    public static BigDecimal max(List<BigDecimal> nums){
    	if(!empty(nums))return Collections.max(nums);
    	return BigDecimal.ZERO;
    }
    public static int intValue(BigDecimal num){
    	if(null!=num)return num.intValue();
    	return 0;
    }
    public static BigDecimal round(BigDecimal num){
    	if(null==num)return BigDecimal.ZERO;
    	return new BigDecimal(num.setScale(-3,RoundingMode.DOWN).toPlainString());
    }
    public static BigDecimal roundLessThanZero(BigDecimal num){
    	try{
    		if(null==num||num.compareTo(BigDecimal.ZERO)<0)return BigDecimal.ZERO;
    		return new BigDecimal(num.setScale(-3,RoundingMode.DOWN).toPlainString());
    	}catch(Exception e){    		
    	}
    	return BigDecimal.ZERO;
    }
    public static String numString(BigDecimal num){
    	if(BigDecimal.ZERO.equals(num)){
    		return "Null";
    	}
    	return String.valueOf(num);
    }
    public static String leftPad(String originalString, int length, String padCharacter) {
		StringBuilder sb = new StringBuilder();
		try {
			while (sb.length() + originalString.length() < length) {
				sb.append(padCharacter);
			}
			sb.append(originalString);
		} catch (Exception e) {}
		return sb.toString();
   }
    
    public static BigDecimal roundUp(BigDecimal num, Integer scale){
    	if(null==num)return BigDecimal.ZERO;
    	return new BigDecimal(num.setScale(scale,RoundingMode.UP).toPlainString());
    }
    
    public static BigDecimal roundDown(BigDecimal num, Integer scale){
    	if(null==num)return BigDecimal.ZERO;
    	return new BigDecimal(num.setScale(scale,RoundingMode.DOWN).toPlainString());
    }
    
    public static BigDecimal toRate(BigDecimal factor, String sign, BigDecimal margin) {
    	try{
	    	if ( !empty(factor) && !empty(sign) && !empty(margin) ) {
	    		if("-".equals(sign)) {
		    		return factor.subtract(margin);
		    	} else {
		    		return factor.add(margin);
		    	}
	    	} else {
	    		return BigDecimal.ZERO;
	    	}
    	}catch(Exception e){}
    	return BigDecimal.ZERO;
    }
}
