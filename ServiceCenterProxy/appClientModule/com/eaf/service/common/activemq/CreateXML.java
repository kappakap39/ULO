package com.eaf.service.common.activemq;

import java.util.List;



import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class CreateXML {

	public static String getXML(Object body) throws Exception {
		return getXML(body, null, null, null, null, null);
	}

	public static String getXML(Object body, Converter dataConverter) throws Exception {
		return getXML(body, null, null, null, null, dataConverter);
	}

	public static String getXML(Object body, List<String> aliases, List<Class<?>> aliasClasses, Converter dataConverter) throws Exception {
		return getXML(body, aliases, aliasClasses, null, null, dataConverter);
	}

	public static String getXML(Object body, List<String> aliases, List<Class<?>> aliasClasses, List<String> implicitCollectionFieldNames, List<Class<?>> implicitCollectionClasses, Converter dataConverter) throws Exception {
		XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("_-", "_")));
		xstream.autodetectAnnotations(true);
		xstream.aliasSystemAttribute(null, "class");
		xstream.registerConverter(new DateConverter());
		if (MyCommonUtils.isCollectionNotEmpty(aliasClasses)) {
			for (int i = 0; i < aliasClasses.size(); i++) {
				String aliase = aliases.get(i);
				if (MyStringUtils.isNotEmpty(aliase)) {
					xstream.alias(aliase, aliasClasses.get(i));
				}
				xstream.processAnnotations(aliasClasses.get(i));
			}
		}

		if (MyCommonUtils.isCollectionNotEmpty(aliasClasses)) {
			for (int i = 0; i < implicitCollectionClasses.size(); i++) {
				xstream.addImplicitCollection(implicitCollectionClasses.get(i), implicitCollectionFieldNames.get(i));
			}
		}
		if (dataConverter != null) {
			xstream.registerConverter(dataConverter);
		}
		return xstream.toXML(body);
	}
}
