package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class SUP_CARD_TYPEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(SUP_CARD_TYPEProperty.class);
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_CARD_LEVEL_SUP = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL_SUP");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String CARD_TYPE = "CARD_TYPE";
	String CARD_LEVEL = "CARD_LEVEL";

	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) objectForm;
		ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM) lastObjectForm;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		ArrayList<ApplicationDataM> lastApplications = lastApplicationGroup.filterApplicationLifeCycle();

		ArrayList<HashMap<String, String>> cards = getCompareCard(applications);
		ArrayList<HashMap<String, String>> lastCards = getCompareCard(lastApplications);

		for (int i = 0; i < getMaxSize(cards, lastCards); i++) {
			HashMap<String, String> card = null;
			HashMap<String, String> lastCard = null;
			if (i < cards.size()) {
				card = cards.get(i);
			}
			if (i < lastCards.size()) {
				lastCard = lastCards.get(i);
			}
			if (!Util.empty(card) && !Util.empty(lastCard)) {
				String cardCode = null;
				String lastCardCode = null;
				String cardType = card.get(CARD_TYPE);
				String lastCardType = lastCard.get(CARD_TYPE);
				if (!Util.empty(cardType)) {
					cardCode = CardInfoUtil.getCardDetail(cardType, "CARD_CODE");
				}
				if (!Util.empty(lastCardType)) {
					lastCardCode = CardInfoUtil.getCardDetail(lastCardType, "CARD_CODE");
				}
				String cardLevel = card.get(CARD_LEVEL);
				String lastCardLevel = lastCard.get(CARD_LEVEL);

				boolean compareCardTypeFlag = CompareObject.compare(cardCode, lastCardCode, null);
				if (!compareCardTypeFlag) {
					AuditDataM audit = new AuditDataM();
					audit.setFieldName(AuditFormUtil.getAuditFieldName(PERSONAL_TYPE_SUPPLEMENTARY, LabelUtil.getText(request, "CARD_TYPE_SUP"),
							request));
					audit.setOldValue(FormatUtil.displayText(CardInfoUtil.getCardDetail(lastCardType, "CARD_TYPE_DESC")));
					audit.setNewValue(FormatUtil.displayText(CardInfoUtil.getCardDetail(cardType, "CARD_TYPE_DESC")));
					audits.add(audit);
				}

				boolean compareCardLevelFlag = CompareObject.compare(cardLevel, lastCardLevel, null);
				if (!compareCardLevelFlag) {
					AuditDataM audit = new AuditDataM();
					audit.setFieldName(AuditFormUtil.getAuditFieldName(PERSONAL_TYPE_SUPPLEMENTARY, LabelUtil.getText(request, "CARD_LEVEL_SUP"),
							request));
					audit.setOldValue(FormatUtil.displayText(CardInfoUtil.getCardDetail(lastCardType, "CARD_TYPE_DESC")) + " - "
							+ getCardLevelDesc(lastCardLevel));
					audit.setNewValue(FormatUtil.displayText(CardInfoUtil.getCardDetail(cardType, "CARD_TYPE_DESC")) + " - "
							+ getCardLevelDesc(cardLevel));
					audits.add(audit);
				}

			} else if (!Util.empty(card) && Util.empty(lastCard)) {
				String cardType = card.get(CARD_TYPE);
				String cardLevel = card.get(CARD_LEVEL);
				addAuditTrail(request, audits, CardInfoUtil.getCardDetail(cardType, "CARD_TYPE_DESC"), null, "CARD_TYPE_SUP");
				addAuditTrail(request, audits, FormatUtil.displayText(CardInfoUtil.getCardDetail(cardType, "CARD_TYPE_DESC")) + " - "
						+ getCardLevelDesc(cardLevel), null, "CARD_LEVEL_SUP");

			} else if (Util.empty(card) && !Util.empty(lastCard)) {
				String lastCardType = lastCard.get(CARD_TYPE);
				String lastCardLevel = lastCard.get(CARD_LEVEL);
				addAuditTrail(request, audits, null, CardInfoUtil.getCardDetail(lastCardType, "CARD_TYPE_DESC"), "CARD_TYPE_SUP");
				addAuditTrail(request, audits, null, FormatUtil.displayText(CardInfoUtil.getCardDetail(lastCardType, "CARD_TYPE_DESC")) + " - "
						+ getCardLevelDesc(lastCardLevel), "CARD_LEVEL_SUP");

			}

		}

		return audits;
	}

	private ArrayList<HashMap<String, String>> getCompareCard(ArrayList<ApplicationDataM> applications) {
		ArrayList<HashMap<String, String>> cards = new ArrayList<HashMap<String, String>>();
		if (!Util.empty(applications)) {
			for (ApplicationDataM application : applications) {
				if (Util.empty(application))
					application = new ApplicationDataM();
				ArrayList<LoanDataM> loans = application.getLoans();
				if (Util.empty(loans))
					loans = new ArrayList<LoanDataM>();

				for (LoanDataM loan : loans) {
					CardDataM card = loan.getCard();
					if (!Util.empty(card) && !Util.empty(card.getCardType())) {
						if (APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(card.getApplicationType())) {
							HashMap<String, String> _card = new HashMap();
							_card.put(CARD_TYPE, card.getCardType());
							_card.put(CARD_LEVEL, card.getCardLevel());
							cards.add(_card);
						}
					}
				}
			}
		}
		return cards;
	}

	private void addAuditTrail(HttpServletRequest request, ArrayList<AuditDataM> audits, String description, String lastDescription, String textCode) {
		AuditDataM audit = new AuditDataM();
		audit.setFieldName(AuditFormUtil.getAuditFieldName(PERSONAL_TYPE_SUPPLEMENTARY, LabelUtil.getText(request, textCode), request));
		audit.setOldValue(FormatUtil.displayText(lastDescription));
		audit.setNewValue(FormatUtil.displayText(description));
		audits.add(audit);
	}

	private int getMaxSize(ArrayList<HashMap<String, String>> cards, ArrayList<HashMap<String, String>> lastCards) {
		return cards.size() > lastCards.size() ? cards.size() : lastCards.size();
	}

	private String getCardLevelDesc(String cardLevel) {
		return ListBoxControl.getName(FIELD_ID_CARD_LEVEL, cardLevel);
	}
}
