package com.ava.dynamic.util;

public class CSSUtil {
	public static String modifyCssClass(String source, String classToModify, String newClass){
		if(source == null || source.isEmpty() || classToModify == null || classToModify.isEmpty()){
			return null;
		}
		
		if(newClass == null){
			newClass = "";
		}
		
		boolean wildCardSearch = classToModify.endsWith("*");
		if(wildCardSearch){
			classToModify = classToModify.substring(0, classToModify.length()-1);
		}

		String[] classes = source.split("\\W");
		StringBuilder result = new StringBuilder();
		for(String clazz : classes){
			if(wildCardSearch){
				if(clazz.startsWith(classToModify)){
					result.append(newClass);
				}else{
					result.append(clazz);
				}
			}else{
				if(clazz.equalsIgnoreCase(classToModify)){
					result.append(newClass);
				}else{
					result.append(clazz);
				}
			}
			result.append(" ");
		}
		return result.toString();
	}
	
	public static String modifySpan(String fullCss, String newSpan){
		return modifyCssClass(fullCss, "span*", newSpan);
	}
}
