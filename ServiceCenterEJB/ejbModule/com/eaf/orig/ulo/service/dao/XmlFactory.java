package com.eaf.orig.ulo.service.dao;

public class XmlFactory {
	public static XmlDAO getXmlDAO(){
		return new XmlDAOImpl();
	}
}
