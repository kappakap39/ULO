package com.eaf.orig.ulo.pl.app.utility;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import com.eaf.orig.cache.properties.ORIGCacheDataM;

public interface OrigImportFileInf {
	public void processImportFile(HttpServletRequest request, ORIGCacheDataM interfaceTypeCacheM, FileItem importFile, FileItem attachFile);
}
