package com.eaf.orig.ulo.app.dao;

public class CompareSignatureFactory {
	public static CompareSignatureDAO getCompareSignatureDAO(){
		return new CompareSignatureDAOImpl();
	}
}
