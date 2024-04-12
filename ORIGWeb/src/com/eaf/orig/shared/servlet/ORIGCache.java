package com.eaf.orig.shared.servlet;

import java.io.IOException;
//import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.cache.MandatoryFieldCache;
//import com.eaf.cache.SubFormCache;
import com.eaf.cache.TableLookupCache;
//import com.eaf.ncb.util.KCBSServiceLOG;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
//import com.eaf.orig.ulo.pl.config.ORIGConfig;
//import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
//import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
//import com.eaf.ulo.pl.eai.util.EAIServiceLOG;

public class ORIGCache extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(ORIGCache.class);
    public ORIGCache() {
        super();      
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("do ORIGCache()..");
		try{
			TableLookupCache.refreshAll();
			MandatoryFieldCache.refreshAll();
			ORIGCacheUtil.LoadFiledIDCaches();
//			com.eaf.xrules.cache.data.TableLookupCache.refreshAll();
//			String configPath = GetConfigPath();
//			String propPath = getServletContext().getRealPath(configPath);
//			logger.info("properties path >> "+propPath);
//			new ORIGConfig(propPath);			
//			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
//			HashMap<String, String> data = origBean.getServiceLOG();
//			EAIServiceLOG.create(data);
//			KCBSServiceLOG.create(data);	
//			ORIGConfig.create(data);		
//			com.eaf.xrules.cache.data.MatrixCache.create();	
//			SubFormCache.create();
//			com.eaf.cache.ORIGCache.create();
		}catch (Exception e) {
			logger.fatal("ERROR ",e);
		}
		logger.info("success ORIGCache()..");
	}
	
//	public static String GetConfigPath(){
//		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
//		String envSystem = origBean.GetGeneralParam("ENVIRONMENT_SYSTEM", "ALL_ALL_ALL");
//		logger.debug("ENVIRONMENT_SYSTEM >> "+envSystem);
//		StringBuilder str = new StringBuilder();
//			str.append("/");
//			str.append("WEB-INF");
//			if(null != envSystem){
//				str.append("/");
//				str.append("config");
//				str.append("/");
//				str.append(envSystem.toLowerCase());
//			}
//			str.append("/");
//			str.append("orig-config.properties");
//		return str.toString();
//	}
}
