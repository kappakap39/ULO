package com.eaf.core.ulo.common.inf;

import java.sql.Connection;

import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;

public interface GenerateFileInf {
	public InfResultDataM create(GenerateFileDataM generateFile)throws Exception;
	public InfResultDataM create(GenerateFileDataM generateFile,Connection conn)throws Exception;
}
