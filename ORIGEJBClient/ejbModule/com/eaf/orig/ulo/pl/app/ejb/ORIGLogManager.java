package com.eaf.orig.ulo.pl.app.ejb;

import javax.ejb.Local;

import com.eaf.orig.inf.log.model.ServiceReqRespDataM;
import com.eaf.orig.tool.performance.PerformanceLogDataM;

@Local
public interface ORIGLogManager {
	public void SaveLogServiceReqResp(ServiceReqRespDataM servReqRespM);
	public void SavePerformanceLog(PerformanceLogDataM perfLogM);
}
