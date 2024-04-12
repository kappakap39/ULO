package com.eaf.service.rest.resource.bpm;

//import javax.ws.rs.Consumes;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
//import org.apache.log4j.Logger;
//
//import com.eaf.service.common.api.ServiceCache;
//import com.eaf.service.rest.model.BPMUserRequest;
//import com.eaf.service.rest.model.BPMUserResponse;
//import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
//import com.orig.bpm.workflow.handle.WorkflowManager;

@Deprecated
//@Path("/service")
public class BPMService {
//	private static transient Logger logger = Logger.getLogger(BPMService.class);
//	@PUT
//	@Path("/add")
//	@Consumes(MediaType.APPLICATION_JSON)	
//	public Response assignUserBpm(@Context UriInfo uriInfo,BPMUserRequest userRequest){
//		String BPM_HOST = ServiceCache.getProperty("BPM_HOST");
//		String BPM_PORT = ServiceCache.getProperty("BPM_PORT");
//		BPMUserResponse workflow = new BPMUserResponse();
//		try{
//			WorkflowManager manager = new WorkflowManager(BPM_HOST,BPM_PORT,"bpm_admin","bpm_admin");
//				String USER_NAME = userRequest.getUsername();
//			WorkflowResponseDataM workflowResponseDataM  = manager.assignSingleGroupToUser(USER_NAME,null);
//				workflow.setResultCode(workflowResponseDataM.getResultCode());
//				workflow.setResultDesc(workflowResponseDataM.getResultDesc());
//		}catch(Exception e){			 
//			logger.fatal("ERROR",e);
//		}
//        return Response.ok().entity(workflow).build();
//    }
}