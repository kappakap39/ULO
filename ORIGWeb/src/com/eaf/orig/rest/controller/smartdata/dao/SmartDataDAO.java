package com.eaf.orig.rest.controller.smartdata.dao;

import java.util.List;

import com.eaf.orig.rest.controller.smartdata.model.SMImgM;
import com.eaf.orig.rest.controller.smartdata.model.SMMainM;

public interface SmartDataDAO {

	List<SMImgM> selectImageByTemplateId(String templateId);

	List<SMMainM> selectFieldByTemplateId(String templateId);

}
