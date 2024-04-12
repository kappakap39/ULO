package com.eaf.orig.ulo.app.ejb.view;

import com.eaf.orig.ulo.model.app.CardDataM;

public interface GeneratorManager {
	public String generateUniqueID(String name);
	public String generateNextSequenceID(String seqName);
	public String generateCardNo(CardDataM card);
	public String generateCardLinkCustNo(CardDataM card);
	public String generateHisHerMembershipNo(CardDataM card);
	public String generatePriorityPassNo(CardDataM card);
	public String generateCardLinkRefNo();
	public String generateStatckMembershipNo(String paramId,String paramType);
}
