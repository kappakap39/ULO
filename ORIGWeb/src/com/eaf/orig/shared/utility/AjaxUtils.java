/*
 * Created on Apr 12, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.utility;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
//import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

public class AjaxUtils {

	Logger logger = Logger.getLogger(this.getClass());
	
	public String getXML4OneItem(String name, String value){
		StringBuffer xml = new StringBuffer();
		xml.append("<list>");
		xml.append("<alert msg=\"\"/>");
		xml.append("<item name=\""+name+"\" value=\""+org.apache.commons.lang.StringEscapeUtils.escapeXml(value)+"\"/>");
		xml.append("</list>");
		return xml.toString();
	}
	
	public String getXML4HashMap(HashMap hm, String alertMsg){
		StringBuffer xml = new StringBuffer();		
		xml.append("<list>");
		if(alertMsg != null && !alertMsg.equals("")){
			xml.append("<alert msg=\""+alertMsg+"\"/>");
		}
		if(hm != null){
		    for (Iterator j = hm.keySet ().iterator () ; j.hasNext () ; ){
		    	String key = (String) j.next ();
		    	if(hm.get(key) != null){
		    		String value = (String) hm.get(key);		
		    		xml.append("<item name=\""+key+"\" value=\""+StringEscapeUtils.escapeXml(value)+"\"/>");
		    	}
			}
		}
		xml.append("</list>");
		return xml.toString();
	}
	
	public String mandatoryErrHmToXml(HashMap hm){
		String xml = "<list>";
	    for (Iterator j = hm.keySet ().iterator () ; j.hasNext () ; ){
	    	String key = (String) j.next ();
	    	if(hm.get(key) != null){
	    		if(hm.get(key) == null){
	    			continue;
	    		}
	    		Vector errV = (Vector) hm.get(key);
	    		String errStr = "";
	    		for(int i=0; i<errV.size(); ++i){
	    			errStr += (String) errV.get(i) + ",";
	    		}
	    		xml += "<item name=\""+key+"\" value=\""+StringEscapeUtils.escapeXml(errStr)+"\"/>";
	    	}
		}
	    xml += "</list>";
		return xml;
	}
	
	public HashMap mandatoryErrXmlToHm(String xml) throws Exception{
		HashMap hm = new HashMap();
		try{
			org.w3c.dom.Node rootNode = getNodeFromXMLStr(xml);
			if(rootNode == null){
				return null;
			}
			org.w3c.dom.NodeList nodeList = rootNode.getChildNodes();
			if(nodeList == null || nodeList.getLength() == 0){
				return null;
			}
			
			// find each item in nodeList
			for(int j=0; j<nodeList.getLength(); ++j){
			
				org.w3c.dom.Node itemNode = nodeList.item(j);			
				org.w3c.dom.NamedNodeMap map = itemNode.getAttributes();
				String key = map.getNamedItem("name").getNodeValue();
				String value = map.getNamedItem("value").getNodeValue();
				logger.debug("value="+value);
				String[] arr = value.split(",");
				Vector v = new Vector();
				for(int i=0; i<arr.length; ++i){
					if(arr[i] != null && !(arr[i].equals(""))){
						v.add(arr[i]);
					}
				}
				hm.put(key, v);
			}
		}catch(Exception e){
			logger.error(">>> XML Parsing has errors",e);
		}
		return hm;
	}
		

		
	public org.w3c.dom.Node getNodeFromXMLStr(String xmlStr) throws Exception{
		org.w3c.dom.Node n = null;
		try{
			org.w3c.dom.Document doc = this.getDocFormXmlString(xmlStr);
			if(doc == null){
				return null;
			}
			n = doc.getDocumentElement();
		}catch(Exception e){
			logger.error(">>> XML Parsing has errors",e);
			throw new Exception("XML Parsing has errors",e);
		}
		return n;		
	}

	public org.w3c.dom.Document getDocFormXmlString(String strXml) throws Exception{
		org.w3c.dom.Document doc = null;
		javax.xml.parsers.DocumentBuilder documentBuilder = null;
		try{
			javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			documentBuilder = factory.newDocumentBuilder();
	
			ByteArrayInputStream byteInput = new ByteArrayInputStream(strXml.toString().getBytes("UTF-8"));
			doc = documentBuilder.parse(byteInput);
			return doc;
		}catch(Exception e){
			logger.error(">>> XML Parsing has errors",e);
			throw new Exception("XML Parsing has errors",e);
		}
	}
}
