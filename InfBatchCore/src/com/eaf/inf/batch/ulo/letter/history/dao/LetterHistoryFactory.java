package com.eaf.inf.batch.ulo.letter.history.dao;

import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryDAO;
import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryDAOImpl;

public class LetterHistoryFactory {
	public static LetterHistoryDAO getLetterHistoryDAO(){
		return new LetterHistoryDAOImpl();
	}
}
