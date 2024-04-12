package com.eaf.orig.ulo.app.view.form.process;

import com.eaf.orig.ulo.control.util.ApplicationUtil;

public class ProcessActionFactory {

	public static com.eaf.core.ulo.common.display.ProcessActionInf getProcessActionDE2(String source){
		if(ApplicationUtil.eApp(source)){
			return new EProcessActionDE2();
		}else{
			return new _ProcessActionDE2();
		}
	}
}
