package com.ava.dynamic.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.SerializationUtils;

public class ObjectUtils {
	static final transient Logger log = LogManager.getLogger(ObjectUtils.class);

	@SuppressWarnings("unchecked")
	public static <T> T cloneObject(T t) {
		if (t == null) {
			return null;
		}
		Object obj = null;
		try {
			byte[] bytes = SerializationUtils.serialize(t);
			// Write the object out to a byte array
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
			obj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return (T) obj;
	}
}
