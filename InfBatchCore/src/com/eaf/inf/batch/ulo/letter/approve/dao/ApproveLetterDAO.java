package com.eaf.inf.batch.ulo.letter.approve.dao;

import java.util.ArrayList;

import com.eaf.inf.batch.ulo.letter.approve.model.ApproveLetterDataM;

public interface ApproveLetterDAO {
	ArrayList<ApproveLetterDataM> getApproveLetterData() throws Exception;
}
