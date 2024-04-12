package com.eaf.orig.ulo.service.app.submit.dao;

public class SubmitApplicationFactory {
	
 public static SubmitApplicationDAO getSubmitApplicationDAO(){
	 return new SubmitApplicationDAOImpl();
 }
}
