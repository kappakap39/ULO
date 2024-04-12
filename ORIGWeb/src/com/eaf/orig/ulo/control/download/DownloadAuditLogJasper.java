package com.eaf.orig.ulo.control.download;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eaf.core.ulo.common.file.DownloadControl;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;

public class DownloadAuditLogJasper implements DownloadControl{

	@Override
	public ProcessResponse processDownload(HttpServletRequest request) throws Exception {
		
		return null;
	}

	@Override
	public boolean requiredEvent() {
		return false;
	}

	@Override
	public ProcessResponse processEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

}
