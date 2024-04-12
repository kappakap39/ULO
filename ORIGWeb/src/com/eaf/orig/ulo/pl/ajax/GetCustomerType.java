package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ListboxProperties;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
/**
 * @author Pipe 05/03/12
 *
 * Type: GetRegisterType
 */
public class GetCustomerType implements AjaxDisplayGenerateInf {
	Logger log = Logger.getLogger(GetCustomerType.class);
	
	public String getDisplayObject(HttpServletRequest request) {
			
	        String customerType = (String)request.getParameter("customerType");
	        log.debug("customerType = "+customerType);
	        
	        if(customerType!=null && !"".equals(customerType)){
	            ORIGUtility utility = new ORIGUtility();
	            //ProductProperties productObj = new ProductProperties();
	            ListboxProperties productObj = new ListboxProperties();
	            //Vector vProduct = utility.loadCacheByName("Product");
	            Vector vCustomerType = utility.loadCacheByActive("ListboxType");
	            Vector vCustomerTypeTmp = new Vector();
	            String xmlObj = "";
	            
				for (int i = 0; i < vCustomerType.size(); i++) {
				    //productObj = (ProductProperties) vProduct.get(i);
					productObj = (ListboxProperties)vCustomerType.get(i);
					log.debug("productObj.getCode = "+productObj.getCode());
					
				    //if(product.equals(productObj.getCode())){
				        //ProductProperties productObjTmp = new ProductProperties();
				    	ListboxProperties productObjTmp = new ListboxProperties();
				        productObjTmp.setCode(productObj.getCode());
				        productObjTmp.setEnDesc(productObj.getEnDesc());
				        productObjTmp.setThDesc(productObj.getThDesc());
				        vCustomerTypeTmp.add(productObjTmp);
				    //}
				}
				
				xmlObj = ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(vCustomerTypeTmp,"" ,"customerType",ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT," style=\"width:50%;\" ");

//				AjaxUtils util = new AjaxUtils();			
//				String xmlStr = util.getXML4OneItem("div_product",xmlObj);
				return xmlObj;
	        }
	        
	        return null;
	    }

	}