package com.eaf.inf.batch.ulo.lotusnote.dao;

public class LotusNoteFactory{
	public static LotusNoteDAO getLotusNoteDAO(){
		return new LotusNoteDAOImpl();
	}
}
