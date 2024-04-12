package com.eaf.orig.ulo.service.dao;

import java.util.List;

import com.eaf.service.common.exception.ServiceControlException;

public interface XmlDAO {
	public List<String> getPrimaryKeyAppGroup() throws ServiceControlException;
}
