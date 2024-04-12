package com.eaf.orig.ulo.app.dao;

import java.sql.SQLException;
import java.util.List;

public interface BpmTaskDAO {
	public String foundEappTaskInstance(List<String> roles, String prefixInstanceName) throws SQLException;
	public void saveUpdateCacheParameter(String cacheName, String cacheValue) throws SQLException;
}
