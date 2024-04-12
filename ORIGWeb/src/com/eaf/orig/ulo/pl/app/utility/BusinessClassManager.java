package com.eaf.orig.ulo.pl.app.utility;

import java.util.Date;
import java.util.Vector;
import com.eaf.orig.cache.properties.BussinessClassProperties;
import com.eaf.orig.cache.properties.OrganizationProperties;
import com.eaf.orig.shared.utility.ORIGCacheUtil;

public class BusinessClassManager {
	private String OrgID;
	private String BusClass;
	private String ApplicationValue;
	
	public String findBus(
			String pDomain,
			String pGroup,
			String pFamily,
			String product,
			String saleType
			){
		
		ORIGCacheUtil utility = new ORIGCacheUtil();
		Vector vOrganization =  utility.loadCacheByName("Organization");
		
		OrganizationProperties cOrg = new OrganizationProperties();
		
		
		for (int i = 0; i < vOrganization.size(); i++) {

			cOrg = (OrganizationProperties)vOrganization.get(i);
		    if(pDomain.equals(cOrg.getpDomainCode()) && pGroup.equals(cOrg.getpGroupCode()) && pFamily.equals(cOrg.getpFamilyCode())){
		       OrgID = cOrg.getCode();
		    }
		}
		
		getBusClass(OrgID,product,saleType);
		
		return BusClass;
	}
	
	public String getBusClass(String org, String product, String chanel){
		BussinessClassProperties cBus = new BussinessClassProperties();
		ORIGCacheUtil utility = new ORIGCacheUtil();
		Vector vBusClass = utility.loadCacheByName("BussinessClass");
		
		for(int i =0; i<vBusClass.size();i++){
			cBus= (BussinessClassProperties)vBusClass.get(i);
				
			if(cBus!=null&& ((cBus.getOrgID()!=null&&cBus.getOrgID().equals(OrgID))&& (cBus.getpID()!=null&&cBus.getpID().equals(product))&&(cBus.getChaID()!=null&& cBus.getChaID().equals(chanel)))){
				Date cDate = new Date();
				if(cDate!=null && cDate.compareTo(cBus.getEffDate())>=0){				
				BusClass = cBus.getCode();
				}
			}
		}
		return BusClass;
	}
	
	public String findAppValue(String OrigID){		
		ORIGCacheUtil utility = new ORIGCacheUtil();
		Vector vOrganization =  utility.loadCacheByName("Organization");		
		OrganizationProperties cOrg = new OrganizationProperties();		
		for (int i = 0; i < vOrganization.size(); i++) {
			String domainCode;
			String groupCode;
			String familyCode;			
			cOrg = (OrganizationProperties)vOrganization.get(i);
		    if(OrigID.equalsIgnoreCase(cOrg.getCode())){
		    	domainCode=(cOrg.getpDomainCode());
		    	groupCode=(cOrg.getpGroupCode());
		    	familyCode=(cOrg.getpFamilyCode());
		    	
		    	ApplicationValue = domainCode+"_"+groupCode+"_"+familyCode;
		    	return ApplicationValue;
		    }
		}
		
		return null;
		
	}
}
