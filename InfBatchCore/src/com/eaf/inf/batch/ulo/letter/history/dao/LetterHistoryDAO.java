package com.eaf.inf.batch.ulo.letter.history.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.inf.batch.ulo.letter.approve.model.ApproveLetterDataM;
import com.eaf.inf.batch.ulo.letter.history.model.LetterHistoryDataM;

public interface LetterHistoryDAO {
	public ArrayList<LetterHistoryDataM> getLetterHistoryData() throws Exception;
	public ArrayList<LetterHistoryDataM> getLetterHistoryData(String appGroupNo) throws Exception;
	public void updateLetterHistory(LetterHistoryDataM letterHistoryDataM) throws Exception;
	public void createLetterHistory(LetterHistoryDataM letterHistoryDataM) throws Exception;
	public void createLetterHistory(LetterHistoryDataM letterHistoryDataM, Connection conn) throws Exception;
	public void setEmailProperties(String letterNo, String sendTo, String email, String language, Connection conn) throws Exception;
	public void updateSendFlag(String cusOrSeller, String letterNo, String sendFlag, Connection conn) throws Exception;
}
