package com.eaf.orig.shared.dao;

import java.util.Vector;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationImageMException;
import com.eaf.orig.shared.model.ImageCategoryDataM;

public interface OrigApplicationImageMDAO {	
	
	public Vector<ImageCategoryDataM> LoadModelImageCategoryDataM(String appRecordId) throws OrigApplicationImageMException;	
	
	public void saveUpdateModelImageCategoryDataM(ImageCategoryDataM imageCateM)  throws OrigApplicationImageMException;
}
