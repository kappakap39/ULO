package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class AutoCompleteLookupServlet extends HttpServlet implements Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4843483579202707189L;
	Logger logger = Logger.getLogger(AutoCompleteLookupServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String codeParam = (String)request.getParameter("q");
		String lookUp = (String)request.getParameter("lookUp");
		
		logger.info("Area code for "+codeParam);
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String brand = (String)request.getParameter("brand");
		
		try {
//			UtilityDAO dao = ORIGDAOFactory.getUtilityDAO();
			
			Vector<CacheDataM> vData = null;
			
//			if(lookUp.equals("areaCode")) {
//				vData = dao.getAreaCodesLike(codeParam, userM.getUserName());
//			} else if(lookUp.equals("clientGroup")) {
//				vData = dao.getClientGroupsLike(codeParam);
//			} else if(lookUp.equals("mktCode")) {
//				vData = dao.getMarketingCodesLike(codeParam);
//			} else if(lookUp.equals("carCategory")) {
//				vData = dao.getCarCategorysLike(codeParam);
//			} else if(lookUp.equals("carBrand")) {
//				vData = dao.getCarBrandsLike(codeParam);
//			} else if(lookUp.equals("carModel")) {
//				String brand = (String)request.getParameter("brand");
//				vData = dao.getCarModelsLike(codeParam, brand);
//			} else if(lookUp.equals("carColor")) {
//				vData = dao.getCarColorsLike(codeParam);
//			} else if(lookUp.equals("carLicenseType")) {
//				vData = dao.getCarLicenseTypesLike(codeParam);
//			} else if(lookUp.equals("carProvince")) {
//				vData = dao.getCarProvincesLike(codeParam);
//			}
			
			vData = PLORIGEJBService.getORIGDAOUtilLocal().getAutoCompleteCacheDataM(lookUp, codeParam, userM.getUserName(), brand);
			
			if (vData != null && !vData.isEmpty()) {
				JSONArray dataList = new JSONArray();
				JSONObject data;
				for(int i=0; i<vData.size(); i++){
					CacheDataM area = (CacheDataM) vData.get(i);
					if(area.getActiveStatus().equals(ORIGCacheUtil.ACTIVE) && null != area.getShortDesc()) {						
						logger.debug("Data: "+area.getShortDesc());
//						out.println(area.getCode()+"|"+area.getShortDesc());
						data =  new JSONObject();
						data.put("desc", area.getShortDesc());
						data.put("code", area.getCode());
						dataList = dataList.put(data);
					}
				}
				logger.info(dataList.toString());
				out.println(dataList.toString());
			} else {
				logger.debug("No matching data");
			}
		} catch(Exception e) {
			logger.error("Error occured "+ e.getMessage(),e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);
	}
}
