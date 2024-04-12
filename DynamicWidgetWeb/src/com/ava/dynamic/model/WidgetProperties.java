package com.ava.dynamic.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**Initialization on demand singleton holds an instance of itself that contains blue print of all widgets.
 * 
 * 
 * @author TOP
 *
 */
public class WidgetProperties {
	private static class Holder{
		static final WidgetProperties INSTANCE = new WidgetProperties();
	}
	public static Map<String, WidgetType> propMap = new LinkedHashMap<String, WidgetType>();
	
	
	/**Essential properties, value of this prop is isolated to increase response time
	 * 
	 */
	public static Map<String, String> propPathMap = new HashMap<String, String>();
	public static Map<String, String> propFragmentMap = new HashMap<String, String>();
	
	private WidgetProperties(){};
	
	public static WidgetProperties getInstance(){
		return Holder.INSTANCE;
	}
	public void addProperty(WidgetType property){
		if(property == null){
			return;
		}
		propMap.put(property.getCode(), property);
		propPathMap.put(property.getCode(), property.getPath());
		propFragmentMap.put(property.getCode(), property.getFragment());
	}
	
	public void addProperty(Collection<WidgetType> collection){
		if(collection == null || collection.isEmpty()){
			return;
		}
		for(WidgetType prop : collection){
			addProperty(prop);
		}
	}
	
	public static String getPathByCode(String widgetCode){
		return propPathMap.get(widgetCode);
	}
	
	public static String getFragmentByCode(String widgetCode){
		return propFragmentMap.get(widgetCode);
	}
	

}
