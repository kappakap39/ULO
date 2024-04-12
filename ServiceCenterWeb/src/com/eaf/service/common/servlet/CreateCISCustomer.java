package com.eaf.service.common.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.dih.CISCustomerDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.CISCustomerServiceProxy;

@WebServlet("/CreateCISCustomer")
public class CreateCISCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(CreateCISCustomer.class);
	
    public CreateCISCustomer() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		try{
			if(process.equals("request")){
				ProcessResponse cisCustomerResponse = new ProcessResponse();
				String applicationGroupNo = request.getParameter("applicationGroupNo_input");
				OrigApplicationGroupDAO applicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
				String applicationGroupId = applicationGroupDAO.getApplicationGroupIdByApplicationGroupNo(applicationGroupNo);
				ApplicationGroupDataM applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
				logger.debug("applicationGroupNo >> "+applicationGroupNo);
				logger.debug("applicationGroupId >> "+applicationGroup.getApplicationGroupId());
				
				ApplicationDataM application = applicationGroup.filterApplicationItemLifeCycle();
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());
				
				DIHProxy dihProxy = new DIHProxy();
				DIHQueryResult<CISCustomerDataM>  dihResult = dihProxy.selectCISCustomer(personalInfo.getCidType(), personalInfo.getIdno());
				CISCustomerDataM cisCustomer = dihResult.getResult();
				if(!ResponseData.SUCCESS.equals(dihResult.getStatusCode())){
					cisCustomerResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					cisCustomerResponse.setErrorData(dihResult.getErrorData());
				}else{
					CISCustomerServiceProxy service = new CISCustomerServiceProxy();
					cisCustomerResponse = service.createCISCustomer(applicationGroup, personalInfo,cisCustomer,null,null);
				}
				
				ResponseUtils.sendJsonResponse(response, null);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}

}
