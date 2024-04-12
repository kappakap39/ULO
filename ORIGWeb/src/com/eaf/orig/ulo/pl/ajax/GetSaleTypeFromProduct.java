package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.MainSaleTypeProperties;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;

/**
 * @author Pipe 05/03/12
 *
 * Type: GetSaleTypeFromProduct
 */
public class GetSaleTypeFromProduct implements AjaxDisplayGenerateInf {
	
	static Logger log = Logger.getLogger(GetSaleTypeFromProduct.class);
	
	public String getDisplayObject(HttpServletRequest request) {
		
        String product = (String)request.getParameter("product");        
        String xmlObj = "";
        String filter = (String)request.getParameter("filter");

        if(!OrigUtil.isEmptyString(product)){
        	
        	ORIGCacheUtil utility = new ORIGCacheUtil();
            MainSaleTypeProperties saleTypeObj = new MainSaleTypeProperties();
            Vector vSaleType = utility.loadCacheByActive("MainSaleType");
            Vector vSaleTypeTmp = new Vector();
            
            
			for (int i = 0; i < vSaleType.size(); i++) {
				saleTypeObj = (MainSaleTypeProperties)vSaleType.get(i);
				if(filter!=null && "Normal".equals(filter)){
		             	MainSaleTypeProperties saleTypeObjTmp = new MainSaleTypeProperties();
				    	saleTypeObjTmp.setCode(saleTypeObj.getCode());
				    	saleTypeObjTmp.setEnDesc(saleTypeObj.getEnDesc());
				    	saleTypeObjTmp.setThDesc(saleTypeObj.getThDesc());	
				    	if(OrigConstant.SaleType.X_SELL.equals(saleTypeObj.getCode())||OrigConstant.SaleType.NORMAL.equals(saleTypeObj.getCode())){
				    		vSaleTypeTmp.add(saleTypeObjTmp);
				    	}
				}else if(filter!=null && "Bundling".equals(filter)){
			            if(saleTypeObj.getEnDesc()!=null&&(saleTypeObj.getEnDesc().indexOf(filter)!=-1)){
			             	MainSaleTypeProperties saleTypeObjTmp = new MainSaleTypeProperties();
					    	saleTypeObjTmp.setCode(saleTypeObj.getCode());
					    	saleTypeObjTmp.setEnDesc(saleTypeObj.getEnDesc());
					    	saleTypeObjTmp.setThDesc(saleTypeObj.getThDesc());	
					    	if(!OrigConstant.SaleType.BUNDING_CREDIT_CARD_GENERIC.equals(saleTypeObj.getCode())){
					    		vSaleTypeTmp.add(saleTypeObjTmp);
					    	}			        	   
			            }
			      }else{
			    	  MainSaleTypeProperties saleTypeObjTmp = new MainSaleTypeProperties();
				    	saleTypeObjTmp.setCode(saleTypeObj.getCode());
				    	saleTypeObjTmp.setEnDesc(saleTypeObj.getEnDesc());
				    	saleTypeObjTmp.setThDesc(saleTypeObj.getThDesc());
				    	 vSaleTypeTmp.add(saleTypeObjTmp);			    	  
			      }
			}
			
			xmlObj = HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vSaleTypeTmp,"","saleType",HTMLRenderUtil.DISPLAY_MODE_EDIT," style=\"width:165px;\"  ");

			return xmlObj;
        }else{
        	xmlObj = HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null,"" ,"saleType",HTMLRenderUtil.DISPLAY_MODE_EDIT," style=\"width:165px;\"  ");
        	
        }
        
        return xmlObj;
    }

}

