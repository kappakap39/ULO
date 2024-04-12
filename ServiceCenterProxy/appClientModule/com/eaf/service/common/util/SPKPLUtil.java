package com.eaf.service.common.util;

import java.util.ArrayList;
import java.util.Arrays;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class SPKPLUtil 
{
	public static String[] KPL_APPLICATION_TEMPLATE = SystemConstant.getArrayConstant("KPL_APPLICATION_TEMPLATE");
	static ArrayList<String> kplApplicationTemplate = new ArrayList<String>(Arrays.asList(KPL_APPLICATION_TEMPLATE));
	private static String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	
	
	public static boolean isKPL(ApplicationGroupDataM applicationGroup) 
	{
		return (isKPLTemplate(applicationGroup) || hasKPLProduct(applicationGroup));
	}
	
	public static boolean isKPLTemplate(ApplicationGroupDataM applicationGroup) 
	{
		String templateId = "";
		if(applicationGroup != null && applicationGroup.getApplicationTemplate() != null)
		{
			templateId = applicationGroup.getApplicationTemplate();
			if(kplApplicationTemplate.contains(templateId))
			{	
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasKPLProduct(ApplicationGroupDataM applicationGroup)
	{
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplicationsProduct(PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(applications))
		{
			for(ApplicationDataM applicationItem : applications)
			{
				return true;
			}
		}
		return false;
	}
}
