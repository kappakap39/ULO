package com.ava.dynamic.repo;

public interface SeqDAO {

	Long getNextVal(String seqName) throws Exception;

}
