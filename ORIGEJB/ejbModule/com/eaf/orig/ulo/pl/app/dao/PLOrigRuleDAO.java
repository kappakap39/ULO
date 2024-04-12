package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.RulesDetailsDataM;

public interface PLOrigRuleDAO {
	public Vector<RulesDetailsDataM> getRulesDetailsConfig(String busClass) throws PLOrigApplicationException;
	public Vector<RulesDetailsDataM> getRulesDetailsVt(String policyRulesIDs) throws PLOrigApplicationException;
}
