package com.eaf.inf.batch.ulo.letter.reject.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFPersonalInfoDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public interface RejectLetterPDFDAO {
	public ArrayList<TaskObjectDataM> selectRejectLetterPDFInfo() throws InfBatchException;
	public ArrayList<TaskObjectDataM> selectSellerRejectLetterPDFInfo() throws InfBatchException;
	public ArrayList<RejectTemplateVariableDataM> getRejectPDFTemplate(RejectLetterPDFDataM rejectTemplateDataM,RejectLetterPDFPersonalInfoDataM rejectPdfPersonal) throws InfBatchException;
	public ArrayList<RejectLetterPDFPersonalInfoDataM> selectRejectPDFTemplateDatas(RejectLetterPDFDataM rejectTemplateDataM ) throws InfBatchException;
	public HashMap<String, String> getContactCallCenterNoProduct(ArrayList<String> products,String language) throws InfBatchException;
	public void insertInfBatchLog(InfBatchLogDataM infBatchLog,Connection conn) throws InfBatchException;
	public RejectLetterPDFRequestDataM loadRejectLetterPDFRequest()throws InfBatchException;
}
