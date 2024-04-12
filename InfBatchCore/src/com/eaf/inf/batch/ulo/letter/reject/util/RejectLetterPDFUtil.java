package com.eaf.inf.batch.ulo.letter.reject.util;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;

public class RejectLetterPDFUtil {
	private static transient Logger logger = Logger.getLogger(RejectLetterPDFUtil.class);
	public static String generatePDFCustomerName(String sendTo,String customerName,String sellerCustomerName){
		String SENDTO_SELLER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_SENDTO_SELLER");
		if(sendTo.equals(SENDTO_SELLER)){
			return sellerCustomerName;
		}
		return customerName;
	}
}
