package com.eaf.inf.batch.ulo.lotusnote.dao;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.lotusnote.model.LotusNoteDataM;

public interface LotusNoteDAO {
	public void insertUpdateMSSaleInfo(LotusNoteDataM lotusNote) throws InfBatchException;
}
