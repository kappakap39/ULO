package com.eaf.orig.ulo.app.wmtask;

import java.util.HashMap;
import java.util.List;

import com.eaf.core.ulo.common.exception.EngineException;

public interface WMTaskDAO {

	public void setRetry(String taskId) throws EngineException;
	public void setResubmit(String refCode) throws EngineException;
	public String getMessage(String taskId) throws EngineException;
	public List<HashMap<String, Object>> getTask(String refCode) throws EngineException;
	
}
