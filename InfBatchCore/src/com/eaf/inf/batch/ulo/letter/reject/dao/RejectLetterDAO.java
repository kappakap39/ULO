package com.eaf.inf.batch.ulo.letter.reject.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterBuildTemplateEntity;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterProcessDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateReasonCodeDetailDataM;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplicationSegment;

public interface RejectLetterDAO {
	public HashMap<String,RejectLetterDataM> selectRejectLetterInfo() throws InfBatchException;
	public RejectLetterRequestDataM loadRejectLetter() throws InfBatchException;
	public RejectLetterRequestDataM loadFullRejectLetterRequest() throws InfBatchException;
	public HashMap<String, ArrayList<TemplateReasonCodeDetailDataM>> selectTemplateResonInfo(RejectLetterDataM rejectLetterDataM,NotifyApplicationSegment notifyApplicationSegment) throws InfBatchException;
	public boolean isExsitingInterfaceBatch(String applicationRecordId) throws InfBatchException;
	public String getTemplateCode(String reasonCode) throws InfBatchException;
	public String generateLetterNo(String langauge) throws InfBatchException;
	public HashMap<String, String> getContactCallCenterNoProduct(ArrayList<String> products,String language) throws InfBatchException;
	public ArrayList<RejectTemplateVariableDataM> getRejectTemplate1Values(RejectLetterProcessDataM rejectLetterProcess) throws InfBatchException;
	public ArrayList<RejectTemplateVariableDataM> getNewTemplate4Values(RejectLetterProcessDataM rejectLetterProcess) throws InfBatchException;
	public String getRejectDocumentList(String applicationGroupId,String personalType) throws InfBatchException;
	public String getRejectDocumentListNotComplete(String personalId, String personalType) throws InfBatchException;
	public void resetLetterNOSequences() throws InfBatchException;
	public String minRankReasonCode(String applicationRecordId,String applicationGroupId) throws InfBatchException;
	public int getSenddingTimeOfCustomer(String applicationGroupId,String contactType) throws InfBatchException;
	public HashMap<String, String> getRejectLetterSpace() throws InfBatchException;
	public HashMap<String, ArrayList<TemplateReasonCodeDetailDataM>> selectTemplateResonInfoByProduct(RejectLetterDataM rejectLetterDataM, String product) throws InfBatchException;
	public ArrayList<RejectTemplateVariableDataM> getBundleTemplateValues(RejectLetterBuildTemplateEntity appGroup) throws InfBatchException;
}