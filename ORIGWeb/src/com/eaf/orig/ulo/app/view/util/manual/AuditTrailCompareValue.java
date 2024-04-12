package com.eaf.orig.ulo.app.view.util.manual;
import java.sql.Timestamp;
import java.util.Date;

import com.eaf.core.ulo.common.util.Util;

public class AuditTrailCompareValue {

	public boolean compareValue(Object newValue ,Object oldValue){
		
		if(Util.empty(oldValue) && Util.empty(newValue)){
			return false;
		}
		else if(oldValue instanceof String && newValue instanceof String){
				if(!oldValue.equals(newValue)){
					return true;
				}
		}
		else if(oldValue instanceof Timestamp && newValue instanceof Timestamp){
			if(!oldValue.equals(newValue)){
				return true;
			}
		}
		
		else if(oldValue instanceof Date && newValue instanceof Date){
			if(!oldValue.equals(newValue)){
				return true;
			}
		}

		return false;
	}
}
