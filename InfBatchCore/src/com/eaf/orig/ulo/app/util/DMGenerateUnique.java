package com.eaf.orig.ulo.app.util;

import com.eaf.orig.ulo.app.factory.DMModuleFactory;

public class DMGenerateUnique {
	public static String generate(String name)throws Exception{
		return DMModuleFactory.getDmUniqueIDGeneratorDAO().getUniqueBySequence(name);
	}	
	public static String generate(String name,int dbType)throws Exception{
		return DMModuleFactory.getDmUniqueIDGeneratorDAO().getUniqueBySequence(name,dbType);
	}
}
