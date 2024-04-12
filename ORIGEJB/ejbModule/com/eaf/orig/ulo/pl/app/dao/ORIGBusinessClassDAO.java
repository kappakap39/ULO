package com.eaf.orig.ulo.pl.app.dao;

import java.util.HashMap;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface ORIGBusinessClassDAO {
	public HashMap<String, String> getBusGroupByBusClass(String busClass) throws PLOrigApplicationException;
	public HashMap<String, String> getBusClassByBusGroup(String busGroup) throws PLOrigApplicationException;
}
