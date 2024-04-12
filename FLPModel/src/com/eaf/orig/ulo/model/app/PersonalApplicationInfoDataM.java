package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
//import java.util.HashMap;
//import java.util.Iterator;
import java.util.Iterator;

@SuppressWarnings("serial")
public class PersonalApplicationInfoDataM implements Serializable, Cloneable {
	public PersonalApplicationInfoDataM() {
		super();
	}
	private PersonalInfoDataM personalInfo;
	private String applicationType;
	private ArrayList<ApplicationDataM> applications = new ArrayList<>();
	private ArrayList<PaymentMethodDataM> paymentMethods = new ArrayList<PaymentMethodDataM>();
	private ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = new ArrayList<SpecialAdditionalServiceDataM>();
	
//	private HashMap<String,PaymentMethodDataM> paymentMethods = new HashMap<String, PaymentMethodDataM>();
//	private HashMap<String,ArrayList<SpecialAdditionalServiceDataM>> additionalServices = new HashMap<>();

	public String getPersonalId(){
		return personalInfo.getPersonalId();
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
//	public ArrayList<ApplicationDataM> getApplicationsDeleteFlag(boolean deleteFalg) {
//		ArrayList<ApplicationDataM> applicationList  =new ArrayList<ApplicationDataM>();
//		if(null!=applications){
//			for(ApplicationDataM applicationInfo :applications ){
////			 boolean applicationDeleteFrag = applicationInfo.isDeleteFlag();
////				if(deleteFalg!=applicationDeleteFrag){
//					applicationList.add(applicationInfo);
////				}
//			}
//		}
//		return applicationList;
//	}
	public void setApplications(ArrayList<ApplicationDataM> applications) {
		this.applications = applications;
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
	public CardDataM getCardApplicationRecordId(String applicationRecordId){
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if(null != applicationRecordId && applicationRecordId.equals(application.getApplicationRecordId())){
					return application.getCard();
				}
			}
		}
		return null;
	}
	
//	public HashMap<String,PaymentMethodDataM> getPaymentMethods() {
//		return paymentMethods;
//	}
//	public void setPaymentMethods(HashMap<String,PaymentMethodDataM> paymentMethods) {
//		this.paymentMethods = paymentMethods;
//	}
//	
//	public PaymentMethodDataM getPaymentMethodByProduct(String productType){
//		if(null != paymentMethods){
//			PaymentMethodDataM paymentMethod = paymentMethods.get(productType);
//			return paymentMethod;
//		}
//			
//		return null;
//	}
//	public void addPaymentMethod(PaymentMethodDataM paymentMethod){
//		if(null == paymentMethods){
//			paymentMethods = new HashMap<String,PaymentMethodDataM>();
//		}
//		paymentMethods.put(paymentMethod.getProductType(), paymentMethod);
//	}

//	public HashMap<String,ArrayList<SpecialAdditionalServiceDataM>> getAdditionalServices() {
//		return additionalServices;
//	}
//
//	public void setAdditionalServices(HashMap<String,ArrayList<SpecialAdditionalServiceDataM>> additionalServices) {
//		this.additionalServices = additionalServices;
//	}
//
//	public void addSpecialAdditionalService(String product, SpecialAdditionalServiceDataM specialAdditionalService){
//		if(null == additionalServices){
//			additionalServices = new HashMap<String,ArrayList<SpecialAdditionalServiceDataM>>(); 
//		}
//		if(null != product){
//			ArrayList<SpecialAdditionalServiceDataM> servicesList = additionalServices.get(product);
//			if(servicesList == null) {
//				servicesList = new ArrayList<SpecialAdditionalServiceDataM>();
//				additionalServices.put(product, servicesList);
//			}
//			servicesList.add(specialAdditionalService);
//		}
//	}
//	public void addSpecialAdditionalService(String applicantType, String product, SpecialAdditionalServiceDataM specialAdditionalService){
//		if(null == additionalServices){
//			additionalServices = new HashMap<String,ArrayList<SpecialAdditionalServiceDataM>>(); 
//		}
//		if(null != applicantType && null != product){
//			String qualifier = applicantType+"_"+product;
//			ArrayList<SpecialAdditionalServiceDataM> servicesList = additionalServices.get(qualifier);
//			if(servicesList == null) {
//				servicesList = new ArrayList<SpecialAdditionalServiceDataM>();
//				additionalServices.put(qualifier, servicesList);
//			}
//			servicesList.add(specialAdditionalService);
//		}
//	}
//	public SpecialAdditionalServiceDataM getSpecialAdditionalService(String product, String serviceType){
//		if(null != additionalServices && !additionalServices.isEmpty()){
//			if(null != product){
//				ArrayList<SpecialAdditionalServiceDataM> servicesList = additionalServices.get(product);
//				if(servicesList != null && !servicesList.isEmpty()) {
//					for(SpecialAdditionalServiceDataM specialAdditionalService : servicesList) {
//						if(null != serviceType && serviceType.equals(specialAdditionalService.getServiceType())){
//							return specialAdditionalService;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
	
	public int getMaxLifeCycle() {
		int maxLifeCycle = 1;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				maxLifeCycle = Math.max(application.getLifeCycle(), maxLifeCycle);
			}
		}
		return maxLifeCycle;
	}

	public ArrayList<ApplicationDataM> filterApplicationLifeCycle(String productCode){
		ArrayList<ApplicationDataM> applicationLifeCycles = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if(maxLifeCycle == applicationM.getLifeCycle()
						&& null != applicationM.getProduct()
							&& applicationM.getProduct().equals(productCode)){
					applicationLifeCycles.add(applicationM);
				}
			}
		}
		return applicationLifeCycles;
	}
	
	public ArrayList<ApplicationDataM> filterApplicationLifeCycle(){
		ArrayList<ApplicationDataM> applicationLifeCycles = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if(maxLifeCycle == applicationM.getLifeCycle()){
					applicationLifeCycles.add(applicationM);
				}
			}
		}
		return applicationLifeCycles;
	}
	
//	public SpecialAdditionalServiceDataM getSpecialAdditionalService(String applicantType, String product, String serviceType){
//		if(null != additionalServices && !additionalServices.isEmpty()){
//			if(null != applicantType && null != product){
//				String qualifier = applicantType+"_"+product;
//				ArrayList<SpecialAdditionalServiceDataM> servicesList = additionalServices.get(qualifier);
//				if(servicesList != null && !servicesList.isEmpty()) {
//					for(SpecialAdditionalServiceDataM specialAdditionalService : servicesList) {
//						if(null != serviceType && serviceType.equals(specialAdditionalService.getServiceType())){
//							return specialAdditionalService;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
//	public void removeSpecialAdditionalService(String product, String serviceType){
//		if(null != additionalServices && !additionalServices.isEmpty()){
//			if(null != product){
//				ArrayList<SpecialAdditionalServiceDataM> servicesList = additionalServices.get(product);
//				if(servicesList != null && !servicesList.isEmpty()) {
//					Iterator<SpecialAdditionalServiceDataM> iter = servicesList.iterator();
//					while(iter.hasNext()){
//						SpecialAdditionalServiceDataM specialAdditionalService = iter.next();
//						if(null != specialAdditionalService.getServiceType() && specialAdditionalService.getServiceType().equals(serviceType)){
//							iter.remove();
//						}
//					}
//				}
//			}
//		}
//	}
//	
//	public void removeSpecialAdditionalService(String applicantType, String product, String serviceType){
//		if(null != additionalServices && !additionalServices.isEmpty()){
//			if(null != applicantType && null != product){
//				String qualifier = applicantType+"_"+product;
//				ArrayList<SpecialAdditionalServiceDataM> servicesList = additionalServices.get(qualifier);
//				if(servicesList != null && !servicesList.isEmpty()) {
//					Iterator<SpecialAdditionalServiceDataM> iter = servicesList.iterator();
//					while(iter.hasNext()){
//						SpecialAdditionalServiceDataM specialAdditionalService = iter.next();
//						if(null != specialAdditionalService.getServiceType() && specialAdditionalService.getServiceType().equals(serviceType)){
//							iter.remove();
//						}
//					}
//				}
//			}
//		}
//	}
	
	public int getApplicationIndex(String applicationRecordId){
		int applicationIndex = -1;
		if(null != applications){
			for (ApplicationDataM application : applications) {
				++applicationIndex;
				if(null != applicationRecordId && applicationRecordId.equals(application.getApplicationRecordId())){
					return applicationIndex;
				}
			}
		}
		return -1;
	}
	public ApplicationDataM getApplicationById(String applicationRecordId) {
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != application.getApplicationRecordId() && application.getApplicationRecordId().equals(applicationRecordId)) {
					return application;
				}
			}
		}
		return null;
	}
	public ArrayList<ApplicationDataM> getApplicationMaxLifeCycle(){
		int maxLifeCycle = 1;
		ArrayList<ApplicationDataM> applicationsMaxLifeCycle = new ArrayList<>();
		for(ApplicationDataM application : applications){
			maxLifeCycle = Math.max(application.getLifeCycle(), maxLifeCycle);
		}
		for(ApplicationDataM application : applications){
			if(maxLifeCycle == application.getLifeCycle()){
				applicationsMaxLifeCycle.add(application);
			}
		}
		return applicationsMaxLifeCycle;
	}
//	public PaymentMethodDataM getPaymentMethodLifeCycleByCardTypeOfPersonalId(String personalId,String product,String cardType){
//		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycleCardType(personalId,product,cardType);
//		if(null != filterApplications && filterApplications.size() > 0){
//			for(ApplicationDataM application : filterApplications){
//				LoanDataM loan = application.getLoan();
//				if(null != loan && null != paymentMethods){
//					String paymentMethodId = loan.getPaymentMethodId();
//					for (PaymentMethodDataM paymentMethod : paymentMethods) {
//						if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
//							return paymentMethod;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
//	public ArrayList<ApplicationDataM> filterApplicationRelationLifeCycleCardType(String personalId,String product,String cardType) {
//		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
//		int maxLifeCycle = getMaxLifeCycle() ;
//		if (null != applications) {
//			for (ApplicationDataM application : applications) {
//				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()) {
//					String applicationRecordId = application.getApplicationRecordId();
//					PersonalRelationDataM personalRelation = getPersonalRelationByRefId(applicationRecordId, personalId);
//					if(null != personalRelation && personalRelation.getRefId() != null && personalRelation.getRefId().equals(applicationRecordId)){
//						CardDataM card = application.getCard();
//						if(null != card && null != card.getApplicationType() && card.getApplicationType().equals(cardType)){
//							filterApplications.add(application);
//						}
//					}
//				}
//			}
//		}
//		return filterApplications;
//	}
	public PaymentMethodDataM getPaymentMethodLifeCycleByPersonalId(String personalId,String product){
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId,product);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan && null != paymentMethods){
					String paymentMethodId = loan.getPaymentMethodId();
					for (PaymentMethodDataM paymentMethod : paymentMethods) {
						if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
							return paymentMethod;
						}
					}
				}
			}
		}
		return null;
	}
	public PaymentMethodDataM getPaymentMethodLifeCycleByPersonalId(String personalId){
		ArrayList<String> paymentMethodIds = getPaymentMethodIdLastLifeCycle();
		if(null != paymentMethods){
			for (PaymentMethodDataM paymentMethod : paymentMethods) {
				if(null != personalId && personalId.equals(paymentMethod.getPersonalId())){
					if(!paymentMethodIds.contains(paymentMethod.getPaymentMethodId())){
						return paymentMethod;
					}
				}
			}
		}
		return null;
	}
	public ArrayList<String> getPaymentMethodIdLastLifeCycle(){
		int lifeCycle = getMaxLifeCycle();
		ArrayList<String> paymentMethodIds = new ArrayList<String>();
		if(null != applications){
			for(ApplicationDataM application : applications){
				if(application.getLifeCycle() != lifeCycle ){
					LoanDataM loan = application.getLoan();
					if(null != loan){
						paymentMethodIds.add(loan.getPaymentMethodId());
					}
				}				
			}
		}
		return paymentMethodIds;
	}
	public ArrayList<PaymentMethodDataM> getListPaymentMethodLifeCycleByPersonalId(String personalId){
		ArrayList<PaymentMethodDataM> filterPaymentMethods = new ArrayList<PaymentMethodDataM>();
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan && null != paymentMethods){
					String paymentMethodId = loan.getPaymentMethodId();
					for (PaymentMethodDataM paymentMethod : paymentMethods) {
						if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
							filterPaymentMethods.add(paymentMethod);
						}
					}
				}
			}
		}
		return filterPaymentMethods;
	}
	public ArrayList<ApplicationDataM> filterApplicationRelationLifeCycle(String personalId,String product) {
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()) {
					String applicationRecordId = application.getApplicationRecordId();
					PersonalRelationDataM personalRelation = getPersonalRelationByRefId(applicationRecordId, personalId);
					if(null != personalRelation && personalRelation.getRefId() != null && personalRelation.getRefId().equals(applicationRecordId)){
						filterApplications.add(application);
					}
				}
			}
		}
		return filterApplications;
	}
	public ArrayList<ApplicationDataM> filterApplicationRelationLifeCycle(String personalId) {
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (maxLifeCycle==application.getLifeCycle()) {
					String applicationRecordId = application.getApplicationRecordId();
					PersonalRelationDataM personalRelation = getPersonalRelationByRefId(applicationRecordId, personalId);
					if(null != personalRelation && personalRelation.getRefId() != null && personalRelation.getRefId().equals(applicationRecordId)){
						filterApplications.add(application);
					}
				}
			}
		}
		return filterApplications;
	}
	public PersonalRelationDataM getPersonalRelationByRefId(String refId,String personalId){
		ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
		if(null != personalRelations){
			for (PersonalRelationDataM personalRelation : personalRelations) {
				if(null != personalRelation){
					if(null != personalId && personalId.equals(personalRelation.getPersonalId()) 
							&& null != refId && refId.equals(personalRelation.getRefId())){
						return personalRelation;
					}
				}
			}
		}
		return null;
	}
	public ArrayList<PaymentMethodDataM> getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(ArrayList<PaymentMethodDataM> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public ArrayList<SpecialAdditionalServiceDataM> getSpecialAdditionalServices() {
		return specialAdditionalServices;
	}
	public void setSpecialAdditionalServices(ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices) {
		this.specialAdditionalServices = specialAdditionalServices;
	}	
	public void addPaymentMethod(PaymentMethodDataM paymentMethod){
		if(null == paymentMethods){
			this.paymentMethods = new ArrayList<PaymentMethodDataM>();
		}
		this.paymentMethods.add(paymentMethod);
	}
	public void addSpecialAdditionalService(SpecialAdditionalServiceDataM specialAdditionalService){
		if(null == this.specialAdditionalServices){
			this.specialAdditionalServices = new ArrayList<SpecialAdditionalServiceDataM>();
		}
		this.specialAdditionalServices.add(specialAdditionalService);
	}
	public SpecialAdditionalServiceDataM getSpecialAdditionalService(ArrayList<String> specialAdditionalServiceIds,String serviceType){
		if(null != specialAdditionalServices){
			for(SpecialAdditionalServiceDataM specialAdditionalService : specialAdditionalServices){
				String serviceId = specialAdditionalService.getServiceId();
				if(specialAdditionalServiceIds.contains(serviceId)){
					if(null != serviceType && serviceType.equals(specialAdditionalService.getServiceType())){
						return specialAdditionalService;
					}
				}
			}
		}
		return null;
	}
	public SpecialAdditionalServiceDataM getSpecialAdditionalServiceLifeCycleByPersonalId(String personalId, String product, String serviceType){
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId,product);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan){
					ArrayList<String> specialAdditionalServiceIds = loan.getSpecialAdditionalServiceIds();
					if(null != specialAdditionalServiceIds){
						return getSpecialAdditionalService(specialAdditionalServiceIds,serviceType);
					}
				}
			}
		}
		return null;
	}
	
	public SpecialAdditionalServiceDataM getSpecialAdditionalServiceLifeCycleByPersonalId(String personalId,String serviceType){
		ArrayList<String> serviceIds = getServiceIdLastLifeCycle();
		if(null != specialAdditionalServices){
			for (SpecialAdditionalServiceDataM specialAdditionalService : specialAdditionalServices) {
				String serviceId = specialAdditionalService.getServiceId();
				if(!serviceIds.contains(serviceId)&& null != serviceType && serviceType.equals(specialAdditionalService.getServiceType())){
					return specialAdditionalService;
				}
			}
		}
		return null;
	}
	
	public ArrayList<String> getServiceIdLastLifeCycle(){
		int lifeCycle = getMaxLifeCycle();
		ArrayList<String> serviceIds = new ArrayList<String>();
		if(null != applications){
			for(ApplicationDataM application : applications){
				if(application.getLifeCycle() != lifeCycle ){
					LoanDataM loan = application.getLoan();					
					if(null != loan){
						if(null != loan.getSpecialAdditionalServiceIds()){
							serviceIds.addAll(loan.getSpecialAdditionalServiceIds());
						}
					}
				}				
			}
		}
		return serviceIds;
	}
	
	public void removeSpecialAdditionalLifeCycleByPersonalId(String personalId,String product,String serviceType){
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId,product);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan){
					ArrayList<String> specialAdditionalServiceIds = loan.getSpecialAdditionalServiceIds();
					if(null != specialAdditionalServiceIds){
						SpecialAdditionalServiceDataM specialAdditionalService =  getSpecialAdditionalService(specialAdditionalServiceIds,serviceType);
						if(null != specialAdditionalService){
							String serviceId = specialAdditionalService.getServiceId();
							if(null != specialAdditionalServices){
								for (Iterator<SpecialAdditionalServiceDataM> iterator = specialAdditionalServices.iterator(); iterator.hasNext();) {
									SpecialAdditionalServiceDataM specialAdditionalServiceM = iterator.next();
									if(null != specialAdditionalServiceM && null != specialAdditionalServiceM.getServiceId() 
											&& specialAdditionalServiceM.getServiceId().equals(serviceId)){
										iterator.remove();
									}
								}
							}
						}
					}
				}
			}
		}
	}	
	public void removeSpecialAdditionalLifeCycleByPersonalId(String personalId,String serviceType){
		ArrayList<String> serviceIds = getServiceIdLastLifeCycle();
		if(null != specialAdditionalServices){
			for (Iterator<SpecialAdditionalServiceDataM> iterator = specialAdditionalServices.iterator(); iterator.hasNext();) {
				SpecialAdditionalServiceDataM specialAdditionalServiceM = iterator.next();
				if(null != specialAdditionalServiceM && null != specialAdditionalServiceM.getServiceId() 
						&& !serviceIds.contains(specialAdditionalServiceM.getServiceId())
							&& null != serviceType && serviceType.equals(specialAdditionalServiceM.getServiceType())){
					iterator.remove();
				}
			}
		}
	}	
	public ArrayList<ApplicationDataM> filterApplicationLifeCycleByPersonalId(String personalId,String product){
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				PersonalRelationDataM personalRelation = getPersonalReation(application.getApplicationRecordId());
				if(null != personalRelation && null != personalRelation.getPersonalId() && personalRelation.getPersonalId().equals(personalId)){
					if(maxLifeCycle == application.getLifeCycle() 
							&& null != application.getProduct() && application.getProduct().equals(product)){
						filterApplications.add(application);
					}
				}
			}
		}
		return filterApplications;
	}	
	public PersonalRelationDataM getPersonalReation(String refId){
		PersonalRelationDataM personalRelation = personalInfo.getPersonalRelation(refId);
		if(null != personalRelation){
			return personalRelation;
		}
		return null;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}	
	public ArrayList<String> getPaymentMethodIds(){
		ArrayList<String> paymentMethodIds = new ArrayList<String>();
		if(null != applications){
			for(ApplicationDataM application : applications){
				LoanDataM loan = application.getLoan();
				if(null != loan){
					paymentMethodIds.add(loan.getPaymentMethodId());
				}
			}
		}
		return paymentMethodIds;
	}
	public ArrayList<String> getSpecialAdditionalServiceIds(){
		ArrayList<String> specialAdditionalServiceIds = new ArrayList<String>();
		if(null != applications){
			for(ApplicationDataM application : applications){
				LoanDataM loan = application.getLoan();
				if(null != loan && null != loan.getSpecialAdditionalServiceIds()){
					specialAdditionalServiceIds.addAll( loan.getSpecialAdditionalServiceIds());
				}
			}
		}
		return specialAdditionalServiceIds;
	}
	public ArrayList<String> getApplicationIds() {
		ArrayList<String> applicationIds  = new ArrayList<String>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				applicationIds.add(application.getApplicationRecordId());
			}
		}
		return applicationIds;
	}
	public int itemLifeCycle(int maxLifeCycle){
		int lifeCycleSize = 0;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if(application.getLifeCycle() == maxLifeCycle){
					lifeCycleSize++;
				}
			}
		}
		return lifeCycleSize;
	}
	public ApplicationDataM getApplicationProduct(String product) {
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())) {
					return application;
				}
			}
		}
		return null;
	}
}
