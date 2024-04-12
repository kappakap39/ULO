package com.eaf.inf.batch.ulo.letter.reject.template.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterDAO;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterConfigDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterProcessDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionResponseDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateReasonCodeDetailDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateRejectProductRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.product.inf.RejectTemplateReasonProductHelper;
import com.eaf.inf.batch.ulo.letter.reject.template.sendto.inf.RejectLetterSendingInf;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.service.common.util.ServiceUtil;

public class KECAllAppTemplateRejectReason extends RejectTemplateReasonProductHelper{
	private static transient Logger logger = Logger.getLogger(CCAllAppTemplateRejectReason.class);
	String REJECT_LETTER_PRODUCT_NAME_KEC=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_KEC");
	String REJECT_LETTER_PRIORITY_FIRST=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_FIRST");
	String REJECT_LETTER_PRIORITY_SECOND=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_SECOND");
	String REJECT_LETTER_PRIORITY_THIRD=InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRIORITY_THIRD");
	@Override
	public ArrayList<RejectLetterProcessDataM> getTemplateRejectProduct(Object object)throws Exception{
		try {
			 //RejectLetterDataM rejectLetterDataM = (RejectLetterDataM)object;
			 TemplateRejectProductRequestDataM templateRequest = (TemplateRejectProductRequestDataM)object;
			 RejectLetterDataM rejectLetterDataM = (RejectLetterDataM)templateRequest.getRequestObject();
			 RejectLetterConfigDataM config = (RejectLetterConfigDataM)templateRequest.getConfig();
			 
			 RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
			 
			 boolean isInfiniteWisdomPremierApplication = GenerateLetterUtil.isInfiniteWisdomPremierApplication(rejectLetterDataM.getApplicationTemplateId());
			 logger.debug("isInfiniteWisdomPremierApplication>>"+isInfiniteWisdomPremierApplication);
			 ArrayList<TemplateReasonCodeDetailDataM> templateRejectReasonDetails= GenerateLetterUtil.getLetterConditionCase(rejectLetterDataM, REJECT_LETTER_PRODUCT_NAME_KEC,isInfiniteWisdomPremierApplication,dao);
			  	 
			 if(null!=templateRejectReasonDetails && templateRejectReasonDetails.size()>0){
				 String sendTo = rejectLetterDataM.getSendTo();
				 String className = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_ALL_APP_CONDITION_SEND_TO_"+sendTo);
				 RejectLetterSendingInf sendInf = (RejectLetterSendingInf)Class.forName(className).newInstance();
//				 logger.debug("Generate templateRejectReasons >>"+new Gson().toJson(templateRejectReasonDetails));
				 ArrayList<RejectLetterProcessDataM>   rejectLetterProcess  = new ArrayList<RejectLetterProcessDataM>();
				 HashMap<String, ArrayList<TemplateReasonCodeDetailDataM>>  hReasonTempateCodeDataList = new HashMap<String, ArrayList<TemplateReasonCodeDetailDataM>>();
				 for(TemplateReasonCodeDetailDataM templateReasonCodeDetailDataM : templateRejectReasonDetails){
					 //String letter_type = GenerateLetterUtil.getRejectLetterTypeByCondition(isInfiniteWisdomPremierApplication, rejectLetterDataM,config,templateReasonCodeDetailDataM, dao);
					 SendingConditionRequestDataM conditionRequest = new SendingConditionRequestDataM();
					 	conditionRequest.setConfig(config);
					 	conditionRequest.setInfiniteWisdomPremierApplication(isInfiniteWisdomPremierApplication);
					 	conditionRequest.setRejectLetterDataM(rejectLetterDataM);
					 	conditionRequest.setTemplateReasonCodeDetailDataM(templateReasonCodeDetailDataM);
					 SendingConditionResponseDataM conditionResponse = (SendingConditionResponseDataM)sendInf.processSendingCondition(conditionRequest);
					 String letter_type = (!InfBatchUtil.empty(conditionResponse)) ? conditionResponse.getLetterType() : "" ;
					 String postLetterType = (String)sendInf.postCondition(conditionRequest, conditionResponse);
					 if(!InfBatchUtil.empty(postLetterType)){
						 letter_type = postLetterType;
					 }
					 //logger.debug("letter_type>>"+letter_type);
					 if( !ServiceUtil.empty(templateReasonCodeDetailDataM.getReasonCode()) &&  !ServiceUtil.empty(letter_type)){
						 String templateCode = dao.getTemplateCode(templateReasonCodeDetailDataM.getReasonCode());
						 String language = templateReasonCodeDetailDataM.getLanguage();
						 String rejectApplyType =RejectLetterUtil.getLetterTemplateAppyType(rejectLetterDataM.getApplicationType(),templateReasonCodeDetailDataM.getBusinessClassId());
					 
						 String key = templateCode+","+language+","+rejectApplyType+","+templateReasonCodeDetailDataM.getReasonCode()+","+letter_type;
						 
						 ArrayList<TemplateReasonCodeDetailDataM> templateCodeProducts = new ArrayList<TemplateReasonCodeDetailDataM>();
						 if(hReasonTempateCodeDataList.containsKey(key)){
							 templateCodeProducts =  hReasonTempateCodeDataList.get(key);
						 }else{
							 hReasonTempateCodeDataList.put(key,templateCodeProducts);
						 }
						 templateCodeProducts.add(templateReasonCodeDetailDataM);
					 }
				 }
				 if(!InfBatchUtil.empty(hReasonTempateCodeDataList)){
					 ArrayList<String> templateKeys = new ArrayList<String>(hReasonTempateCodeDataList.keySet());
					 for(String templateKey : templateKeys){
						 ArrayList<TemplateReasonCodeDetailDataM> dataTemplates = hReasonTempateCodeDataList.get(templateKey);
						 String[] keyValuses =  templateKey.split(",");
						 String templateCode = keyValuses[0];
						 String language=keyValuses[1];
						 String rejectApplyType=keyValuses[2];
						 String reasonCode=keyValuses[3];
						 String letterType=keyValuses[4];
						  
						 RejectLetterProcessDataM rejectLetterProcessDataM = new RejectLetterProcessDataM();
						 rejectLetterProcessDataM.setApplyType(rejectApplyType);
						 rejectLetterProcessDataM.setLanguage(language);
						 rejectLetterProcessDataM.setMinRankReasonCode(reasonCode);
						 rejectLetterProcessDataM.setTemplateCode(templateCode);						 
						 rejectLetterProcessDataM.setProduct(REJECT_LETTER_PRODUCT_NAME_KEC);
						 rejectLetterProcessDataM.setTemplateReasonCodes(dataTemplates);	
						 rejectLetterProcessDataM.setLifeCycle(rejectLetterDataM.getLifeCycle());
						 rejectLetterProcessDataM.setSendTo(rejectLetterDataM.getSendTo());
						 rejectLetterProcessDataM.setEmailAllAfp(false);
						 if(RejectLetterUtil.EMAIL.equals(letterType)){
							 rejectLetterProcessDataM.setEmail(true);
						 }else if(RejectLetterUtil.EMAIL_ALL_AFP.equals(letterType)){
							 rejectLetterProcessDataM.setEmail(true);
							 rejectLetterProcessDataM.setEmailAllAfp(true);
						 }else{
							 rejectLetterProcessDataM.setPaper(true);
						 }
						 rejectLetterProcessDataM.setApplicationGroupId(rejectLetterDataM.getApplicationGroupId());
						 rejectLetterProcess.add(rejectLetterProcessDataM);
					 }
				 }
				 return rejectLetterProcess;
			 }	
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new Exception(e);
		}
		return null;
	
	}
}
