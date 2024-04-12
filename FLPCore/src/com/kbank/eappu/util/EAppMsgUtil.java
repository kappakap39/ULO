package com.kbank.eappu.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.utility.SerializeUtil;
import com.kbank.eappu.dummycore.EAppDummyTemplateGen;
import com.kbank.eappu.lookup.SingletonEAU;
import com.kbank.eappu.model.Message;

/**
 * A utilities class for Message.
 * @author Pinyo.L 
 **/
public class EAppMsgUtil {
	private static Logger logger = Logger.getLogger(EAppMsgUtil.class);

	public static void main(String[] args) throws Exception {
		
		//S0008
		Message S0008 =  createMsgTemplate("S0008");
		EAppDummyTemplateGen.fillDummyDataS000X(S0008);
		logger.debug(S0008.toJson());
		
		
		//S0005	
		Message S0005 =  createMsgTemplate("S0005");
		EAppDummyTemplateGen.fillDummyDataS000X(S0005);
		logger.debug(S0005.toJson());

		//S0006	
		Message S0006 =  createMsgTemplate("S0006");
		EAppDummyTemplateGen.fillDummyDataS000X(S0006);
		logger.debug(S0006.toJson());
		
		//S0007	
		Message S0007 =  createMsgTemplate("S0007");
		EAppDummyTemplateGen.fillDummyDataS000X(S0007);
		logger.debug(S0007.toJson());
		
		//S0001	
		Message S0001 =  createMsgTemplate("S0001");
		EAppDummyTemplateGen.fillDummyDataS000X(S0001);
		logger.debug(S0001.toJson());
		
		//S0004	
		Message S0004 =  createMsgTemplate("S0004");
		EAppDummyTemplateGen.fillDummyDataS000X(S0004);
		logger.debug(S0004.toJson());
		
		//S0015
		Message S0015 =  createMsgTemplate("S0015");
		EAppDummyTemplateGen.fillDummyDataS000X(S0015);
		logger.debug(S0015.toJson());		
		
		//S0035
		Message S0035 =  createMsgTemplate("S0035");
		EAppDummyTemplateGen.fillDummyDataS000X(S0035);
		logger.debug(S0035.toJson());
		
		//S0045	
		Message S0045 =  createMsgTemplate("S0045");
		EAppDummyTemplateGen.fillDummyDataS000X(S0045);
		logger.debug(S0045.toJson());
		
		//S0012	
		Message S0012 =  createMsgTemplate("S0012");
		EAppDummyTemplateGen.fillDummyDataS000X(S0012);
		logger.debug(S0012.toJson());
		
		//S0013	
		Message S0013 =  createMsgTemplate("S0013");
		EAppDummyTemplateGen.fillDummyDataS000X(S0013);
		logger.debug(S0013.toJson());
		
		//S0014	
		Message S0014 =  createMsgTemplate("S0014");
		EAppDummyTemplateGen.fillDummyDataS000X(S0014);
		logger.debug(S0014.toJson());
		
		//S0016	
		Message S0016 =  createMsgTemplate("S0016");
		EAppDummyTemplateGen.fillDummyDataS000X(S0016);
		logger.debug(S0016.toJson());
		
		//S0028	
		Message S0028 =  createMsgTemplate("S0028");
		EAppDummyTemplateGen.fillDummyDataS000X(S0028);
		logger.debug(S0028.toJson());
		
		//S0029	
		Message S0029 =  createMsgTemplate("S0029");
		EAppDummyTemplateGen.fillDummyDataS000X(S0029);
		logger.debug(S0029.toJson());
		
		//S0031	
		Message S0031 =  createMsgTemplate("S0031");
		EAppDummyTemplateGen.fillDummyDataS000X(S0031);
		logger.debug(S0031.toJson());
		
		//S0032	
		Message S0032 =  createMsgTemplate("S0032");
		EAppDummyTemplateGen.fillDummyDataS000X(S0032);
		logger.debug(S0032.toJson());
		
		//S0033	
		Message S0033 =  createMsgTemplate("S0033");
		EAppDummyTemplateGen.fillDummyDataS000X(S0033);
		logger.debug(S0033.toJson());
		
		//S0034	
		Message S0034 =  createMsgTemplate("S0034");
		EAppDummyTemplateGen.fillDummyDataS000X(S0034);
		logger.debug(S0034.toJson());
		
		//S0036	
		Message S0036 =  createMsgTemplate("S0036");
		EAppDummyTemplateGen.fillDummyDataS000X(S0036);
		logger.debug(S0036.toJson());
		
		//S0038	
		Message S0038 =  createMsgTemplate("S0038");
		EAppDummyTemplateGen.fillDummyDataS000X(S0038);
		logger.debug(S0038.toJson());
		
		//S0039	
		Message S0039 =  createMsgTemplate("S0039");
		EAppDummyTemplateGen.fillDummyDataS000X(S0039);
		logger.debug(S0039.toJson());
		
		//S0041		
		Message S0041 =  createMsgTemplate("S0041");
		EAppDummyTemplateGen.fillDummyDataS000X(S0041);
		logger.debug(S0041.toJson());
		
		//S0042		
		Message S0042 =  createMsgTemplate("S0042");
		EAppDummyTemplateGen.fillDummyDataS000X(S0042);
		logger.debug(S0042.toJson());
		
		//S0044	
		Message S0044 =  createMsgTemplate("S0044");
		EAppDummyTemplateGen.fillDummyDataS000X(S0044);
		logger.debug(S0044.toJson());
		
		//S0046	
		Message S0046 =  createMsgTemplate("S0046");
		EAppDummyTemplateGen.fillDummyDataS000X(S0046);
		logger.debug(S0046.toJson());
		
		//S0071
		Message S0071 =  createMsgTemplate("S0071");
		EAppDummyTemplateGen.fillDummyDataS000X(S0071);
		logger.debug(S0071.toJson());
		/**/
	}

	/**
	 * Retrieve clone of message-object correspond to template from database by code
	 * @param templateId is id correspond to template
	 * @param clazz is class correspond to template
	 * @return Instance of inputed class.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static Message createMsgTemplate(String templateId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvocationTargetException, NoSuchMethodException {
		Message msg = SingletonEAU.generateMsgTemplates(templateId);

		//Clone instance of it and return to client.
		return msg.cloneSelf();
	}
	
	public static HashMap<String, String> getArtworkByCardType(String cardTypeId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvocationTargetException, NoSuchMethodException {
		return SingletonEAU.getArtworkByCardType(cardTypeId);
	}

}
