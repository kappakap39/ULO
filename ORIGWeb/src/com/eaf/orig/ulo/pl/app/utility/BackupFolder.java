package com.eaf.orig.ulo.pl.app.utility;

import java.io.Serializable;


@SuppressWarnings("serial")
public class BackupFolder implements Serializable,Cloneable{
	private String folderName;
	private long folderId;
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
}
