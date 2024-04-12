package test;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

public class Test {
	public int val = 2;
	private static Gson gson = new Gson();

	public static void main(String args[]){
		String s = "25%\n3 25%\n3 25%\n325%\n3";
		System.out.println(s.replace("\\\n", System.lineSeparator()));
		
	}

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

		System.out.println("classToModify: "+classToModify);
		System.out.println("wildCardSearch: "+wildCardSearch);
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
}
