package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ProductGroupDataM implements Serializable, Cloneable {
	public ProductGroupDataM() {
		super();
	}

	private ApplicationGroupDataM applicationGroup;
	private ArrayList<ApplicationDataM> applications;
	private PersonalInfoDataM personalInfo;
	
	public ApplicationGroupDataM getApplicationGroup() {
		return applicationGroup;
	}

	public void setApplicationGroup(ApplicationGroupDataM applicationGroup) {
		this.applicationGroup = applicationGroup;
	}
	
	public String getApplicationGroupId() {
		return applicationGroup.getApplicationGroupId();
	}

	public PersonalInfoDataM getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(PersonalInfoDataM personalInfo) {
		this.personalInfo = personalInfo;
	}

	public ArrayList<ApplicationDataM> getApplications() {
		return applications;
	}

	public void setApplications(ArrayList<ApplicationDataM> applications) {
		this.applications = applications;
	}

	public ArrayList<ApplicationDataM> getApplications(String applicationGroupId, String personalId, String personalType,
			String relationLevel) {
		if (null == personalId || null == personalType || null == relationLevel) {
			return null;
		}

		HashMap<String, ApplicationDataM> applicationsMap = new HashMap<>();
		ArrayList<ApplicationDataM> applications = getApplications();
		if (null == applications) {
			return null;
		}
		for (ApplicationDataM application : applications) {
			applicationsMap.put(application.getApplicationRecordId(), application);
		}

		ArrayList<ApplicationDataM> supApplications = new ArrayList<>();
		ArrayList<PersonalRelationDataM> personalRelations = getPersonalRelation(applicationGroupId, relationLevel);
		if (null != personalRelations) {
			for (PersonalRelationDataM personalRelation : personalRelations) {
				if (personalId.equals(personalRelation.getPersonalId())) {
					supApplications.add(applicationsMap.get(personalRelation.getRefId()));
				}
			}
		}
		return supApplications;
	}

	public ArrayList<PersonalRelationDataM> getPersonalRelation(String applicationRecordId, String relationLevel) {
		ArrayList<PersonalRelationDataM> _personalRelations = new ArrayList<PersonalRelationDataM>();

		ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
		if (null != personalRelations) {
			for (PersonalRelationDataM personalRelation : personalRelations) {
				if (personalRelation.getRefId().equals(applicationRecordId) && personalRelation.getRelationLevel().equals(relationLevel)) {
					_personalRelations.add(personalRelation);
				}
			}
		}

		return _personalRelations;
	}

	public ArrayList<String> getProducts(String filterProduct[]) {
		ArrayList<String> compare = new ArrayList<String>(Arrays.asList(filterProduct));
		ArrayList<String> products = new ArrayList<String>();
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				String product = applicationM.getProduct();
				if (null != product && !products.contains(product) && compare.contains(product)) {
					products.add(product);
				}
			}
		}
		Collections.sort(products);
		return products;
	}
	
	public ArrayList<ApplicationDataM> getApplicationsProduct(String product) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())) {
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	
	public ArrayList<ApplicationDataM> getApplicationsProduct(ArrayList<ApplicationDataM> applicationM, String product) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		if (null != applicationM) {
			for (ApplicationDataM application : applicationM) {
				if (null != product && product.equals(application.getProduct())) {
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	
	public ArrayList<CardDataM> getCards() {
		ArrayList<CardDataM> cards = new ArrayList<CardDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				ArrayList<LoanDataM> loans = application.getLoans();
				if (null != loans) {
					for (LoanDataM loan : loans) {
						CardDataM card = loan.getCard();
						cards.add(card);

					}
				}
			}
		}
		return cards;
	}
}
