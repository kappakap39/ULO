package com.eaf.orig.rest.controller.smartdata.model;

import java.util.List;

public class SmartImageRequest {
	private String templateId;
	private List<SMImgM> images;
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public List<SMImgM> getImages() {
		return images;
	}
	public void setImages(List<SMImgM> images) {
		this.images = images;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmartImageRequest [templateId=");
		builder.append(templateId);
		builder.append(", images=");
		builder.append(images);
		builder.append("]");
		return builder.toString();
	}
	
}
