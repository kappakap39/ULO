package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eaf.core.ulo.common.exception.EngineException;
import com.eaf.orig.model.ld.ImportOTDataM;
import com.eaf.orig.model.ld.UploadCompanyNameM;
import com.eaf.orig.model.ld.UploadForecastM;
import com.eaf.orig.model.ld.UploadProductImageM;

public interface LookupDataDAO {
	public List<String> getUserNo() throws EngineException;
	public void insertUserOTPoint(ImportOTDataM importOTDataM) throws EngineException;
	public void deleteUserOTPoint() throws EngineException;
	public void deleteUploadForecast() throws EngineException;
	public void insertUploadForecast(UploadForecastM uploadForecastDataM) throws EngineException;
	public ArrayList<UploadProductImageM> selectUploadProductImage() throws EngineException;
	public void updateUploadProductImage(UploadProductImageM uploadProductImageM) throws EngineException;
	public List<String> selectAllProductCard() throws EngineException;
	public ArrayList<HashMap<String, Object>> getCompanyList() throws EngineException;
	public void deleteUploadCompanyName() throws EngineException;
	public void insertUploadCompanyName(UploadCompanyNameM uploadCompanyNameM) throws EngineException;
	public ArrayList<HashMap<String, Object>> getSavingAccountNoList(String personalId) throws EngineException;
}
