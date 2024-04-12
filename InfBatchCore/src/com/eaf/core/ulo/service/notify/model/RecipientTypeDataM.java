package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.eaf.core.ulo.common.util.InfBatchUtil;

@SuppressWarnings("serial")
public class RecipientTypeDataM implements Serializable,Cloneable{
	public RecipientTypeDataM(){
		super();
	}
	private ArrayList<EmailRecipientDataM> emailRecipients = new ArrayList<EmailRecipientDataM>();
	private ArrayList<SMSRecipientDataM> smsRecipients = new ArrayList<SMSRecipientDataM>();
	private ArrayList<SMSKMobileRecipientDataM> smsKMobileRecipients = new ArrayList<SMSKMobileRecipientDataM>();
	
	public void put(EmailRecipientDataM recipient){
		emailRecipients.add(recipient);
	}
	public void put(SMSRecipientDataM recipient){
		smsRecipients.add(recipient);
	}
	public ArrayList<EmailRecipientDataM> getEmailRecipients(){
		return emailRecipients;
	}
	public ArrayList<SMSRecipientDataM> getSmsRecipients(){
		return smsRecipients;
	}
	public ArrayList<EmailRecipientDataM> getEmailRecipients(String recipientType){
		ArrayList<EmailRecipientDataM> filters = new ArrayList<EmailRecipientDataM>();
		for (EmailRecipientDataM recipient : emailRecipients) {
			if(null != recipient.getRecipientType() && recipient.getRecipientType().equals(recipientType) && !filters.contains(recipient)){
				filters.add(recipient);
			}
		}
		return filters;
	}
	public ArrayList<SMSRecipientDataM> getSmsRecipients(String recipientTypes){
		ArrayList<SMSRecipientDataM> filters = new ArrayList<SMSRecipientDataM>();
		for (SMSRecipientDataM recipient : smsRecipients) {
			if(null != recipient.getRecipientType() && recipient.getRecipientType().equals(recipientTypes)){
				filters.add(recipient);
			}
		}
		return filters;
	}
	public ArrayList<String> getMobileNos(){
		ArrayList<String> mobileNos = new ArrayList<String>();
		if(null!=smsRecipients){
			for(SMSRecipientDataM recipient : smsRecipients){
				if(!InfBatchUtil.empty(recipient.getMobileNo())){
					mobileNos.add(recipient.getMobileNo());
				}
			}
		}
		return  mobileNos;
	}
	public ArrayList<String> getMobileNos(ArrayList<String> recipientTypes){
		ArrayList<String> mobileNos = new ArrayList<String>();
		if(null!=smsRecipients){
			for(SMSRecipientDataM recipient : smsRecipients){
				if(!InfBatchUtil.empty(recipient.getMobileNo()) && recipientTypes.contains(recipient.getRecipientType()) && !mobileNos.contains(recipient.getMobileNo())){
					mobileNos.add(recipient.getMobileNo());
				}
			}
		}
		return  mobileNos;
	}
	
	public ArrayList<String> getKMobileNos(ArrayList<String> recipientTypes,String templateId){
		ArrayList<String> mobileNos = new ArrayList<String>();
		if(null!=smsKMobileRecipients && null!=recipientTypes && null!=templateId){
			for(SMSKMobileRecipientDataM recipient : smsKMobileRecipients){
				if(!InfBatchUtil.empty(recipient.getMobileNo()) && recipientTypes.contains(recipient.getRecipientType())
				&& !mobileNos.contains(recipient.getMobileNo()) && null!=recipient.getTemplates() && recipient.getTemplates().contains(templateId)){
					mobileNos.add(recipient.getMobileNo());
				}
			}
		}
		return  mobileNos;
	}
	public ArrayList<String> getMobileNos(ArrayList<String> recipientTypes,String templateId){
		ArrayList<String> mobileNos = new ArrayList<String>();
		if(null!=smsRecipients && null!=templateId && null!=recipientTypes){
			for(SMSRecipientDataM recipient : smsRecipients){
				if(!InfBatchUtil.empty(recipient.getMobileNo()) && recipientTypes.contains(recipient.getRecipientType()) 
						&& !mobileNos.contains(recipient.getMobileNo()) && null!= recipient.getTemplates() && recipient.getTemplates().contains(templateId)){
					mobileNos.add(recipient.getMobileNo());
				}
			}
		}
		return  mobileNos;
	}
	
	public ArrayList<SMSRecipientDataM> getMobileRecipients(ArrayList<String> recipientTypes){
		ArrayList<SMSRecipientDataM> recipients = new ArrayList<SMSRecipientDataM>();
		ArrayList<String> mobileNoCheckDup= new ArrayList<String>();
		if(null!=smsRecipients){
			for(SMSRecipientDataM recipient : smsRecipients){
				if(!InfBatchUtil.empty(recipient.getMobileNo()) && recipientTypes.contains(recipient.getRecipientType()) && !mobileNoCheckDup.contains(recipient.getMobileNo())){
					mobileNoCheckDup.add(recipient.getMobileNo());
					recipients.add(recipient);
				}
			}
		}
		return  recipients;
	}
	public ArrayList<String> getEmails(ArrayList<String> recipientTypes){
		ArrayList<String> emails = new ArrayList<String>();
		if(null!=emailRecipients){
			for(EmailRecipientDataM recipient : emailRecipients){
				if(!InfBatchUtil.empty(recipient.getEmail()) && recipientTypes.contains(recipient.getRecipientType()) && !emails.contains(recipient.getEmail())){
					emails.add(recipient.getEmail());
				}
			}
		}
		return  emails;
	}
	public String[] getEmailAddress(ArrayList<String> recipientTypes){
		return getEmails(recipientTypes).toArray(new String[0]);
	}
	public ArrayList<String> getEmails(){
		ArrayList<String> emails = new ArrayList<String>();
		if(null!=emailRecipients){
			for(EmailRecipientDataM recipient : emailRecipients){
				if(!InfBatchUtil.empty(recipient.getEmail()) && !emails.contains(recipient.getEmail())){
					emails.add(recipient.getEmail());
				}
			}
		}
		return  emails;
	}
	public String[] getEmailAddress(){
		return getEmails().toArray(new String[0]);
	}
	public ArrayList<String> getCcEmails(){
		ArrayList<String> emails = new ArrayList<String>();
		if(null!=emailRecipients){
			for(EmailRecipientDataM recipient : emailRecipients){
				if(!InfBatchUtil.empty(recipient.getCcEmail()) && !emails.contains(recipient.getCcEmail())){
					emails.add(recipient.getCcEmail());
				}
			}
		}
		return  emails;
	}
	public String[] getCcEmailAddress(){
		return getCcEmails().toArray(new String[0]);
	}
	public ArrayList<String> getCcEmails(ArrayList<String> recipientTypes){
		ArrayList<String> emails = new ArrayList<String>();
		if(null!=emailRecipients){
			for(EmailRecipientDataM recipient : emailRecipients){
				if(!InfBatchUtil.empty(recipient.getCcEmail()) && recipientTypes.contains(recipient.getRecipientType()) && !emails.contains(recipient.getCcEmail())){
					emails.add(recipient.getCcEmail());
				}
			}
		}
		return  emails;
	}
	public String[] getCcEmailAddress(ArrayList<String> recipientTypes){
		return getCcEmails(recipientTypes).toArray(new String[0]);
	}
	
	public String getEmailToString(){
		StringBuilder emailToString = new StringBuilder("");
		if(null!=emailRecipients){
			String COMMA ="";
			for(EmailRecipientDataM recipient : emailRecipients){
				if(!InfBatchUtil.empty(recipient.getEmail())){
					emailToString.append(COMMA+recipient.getEmail());
					COMMA =",";
				}
			}
		}
		return  emailToString.toString();
	}
	public String getEmailToString(ArrayList<String> recipientTypes){
		StringBuilder emailToString = new StringBuilder("");
		if(null!=emailRecipients){
			String COMMA ="";
			for(EmailRecipientDataM recipient : emailRecipients){
				if(!InfBatchUtil.empty(recipient.getEmail()) && recipientTypes.contains(recipient.getRecipientType())){
					emailToString.append(COMMA+recipient.getEmail());
					COMMA =",";
				}
			}
		}
		return  emailToString.toString();
	}
	public String getMobileToString(){
		StringBuilder mobileToString = new StringBuilder("");
		ArrayList<String> mobiles = getMobileNos();
		if(null!=mobiles){
			String COMMA ="";
			for(String mobile : mobiles){
				mobileToString.append(COMMA+mobile);
				COMMA =",";
			}
		}
		return  mobileToString.toString();
	}
	public String getMobileToString(ArrayList<String> recipientTypes){
		StringBuilder mobileToString = new StringBuilder("");
		ArrayList<String> mobiles = getMobileNos(recipientTypes);
		if(null!=mobiles){
			String COMMA ="";
			for(String  mobile : mobiles){
				mobileToString.append(COMMA+mobile);
				COMMA =",";
			}
		}
		return  mobileToString.toString();
	}
	public boolean isValid() {
		if(null!=emailRecipients && emailRecipients.size()>0){
			return true;
		}
		if(null!=smsRecipients && smsRecipients.size()>0){
			return true;
		}
		if(null!=smsKMobileRecipients && smsKMobileRecipients.size()>0){
			return true;
		}
		return false;
	}
	public ArrayList<SMSKMobileRecipientDataM> getSmsKMobileRecipients() {
		return smsKMobileRecipients;
	}
	public void put(SMSKMobileRecipientDataM recipient){
		smsKMobileRecipients.add(recipient);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecipientTypeDataM [");
		if (emailRecipients != null) {
			builder.append("emailRecipients=");
			builder.append(emailRecipients);
			builder.append(", ");
		}
		if (smsRecipients != null) {
			builder.append("smsRecipients=");
			builder.append(smsRecipients);
			builder.append(", ");
		}
		if (smsKMobileRecipients != null) {
			builder.append("smsKMobileRecipients=");
			builder.append(smsKMobileRecipients);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
