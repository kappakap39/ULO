package com.eaf.inf.batch.ulo.pega.dao;

public class UpdateApprovalStatusFactory {
	public static UpdateApprovalStatusDAO getUpdateApprovalStatusDAO(){
		return new UpdateApprovalStatusDAOImpl();
	}
}
