<%@ page import="java.util.*" %>
<%@ page import="java.io.Serializable" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.ObjectOutputStream" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="com.eaf.cache.TableLookupCache" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Session & Cache Info</TITLE>
</HEAD>

<BODY>
<DIV align="center"><b>Session Info</b></DIV>
<table cellpadding="0" cellspacing="0" border="1" align="center">
<tr bgcolor="gray">
<td align="center"><b>AttributeName</b></td>
<td align="center"><b>Size (Byte)</b></td>
<td align="center"><b>Remark</b></td>
</tr>
<%!
public static byte[] serialize(Serializable obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
		serialize(obj, baos);
		return baos.toByteArray();
}

public static void serialize(Serializable obj, OutputStream outputStream) {
		if (outputStream == null) {
			throw new IllegalArgumentException("The OutputStream must not be null");
		}
		ObjectOutputStream out = null;
		try {
			// stream closed in the finally
			out = new ObjectOutputStream(outputStream);
			out.writeObject(obj);

		} catch (IOException ex) {
			//throw new SerializationException(ex);
			//ex.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				// ignore;
			}
		}
	}
%>

<%
Enumeration attributeAll = request.getSession().getAttributeNames();
int count = 0;
int totalSize = 0;
		while(attributeAll.hasMoreElements()){
			String error = "-";
			String attributeName = (String) attributeAll.nextElement();
			Object sessionObj = (Object)request.getSession().getAttribute(attributeName);
			byte[] sessionByte = null;
			try{
				Serializable sessionSerial = (Serializable)sessionObj;
				sessionByte =  serialize(sessionSerial);
			}  catch(Exception e){
				error = "This Object is not Serializable";
				//error = e.getMessage();
			}
			int length = 0;
			if(sessionByte==null){
				length = 0;
			}else{
				length = sessionByte.length;
			}
			count = count+1;
			totalSize = totalSize + length;
%>

<tr>
<td><%=attributeName%></td>
<td align="center"><%=length%></td>
<td align="center"><%=error%></td>
</tr>
<%
		}
%>
<tr>
<td align="center"><b>Total (<%=count%>)</b></td>
<td align="center"><b><%=totalSize%></b></td>
<td>&nbsp;</td>
</tr>
</table>

<br>
<!-- Cache Info -->

<DIV align="center"><b>Cache Info</b></DIV>
<table cellpadding="0" cellspacing="0" border="1" align="center">
<tr bgcolor="gray">
<td align="center"><b>Cache Name</b></td>
<td align="center"><b>Record</b></td>
<td align="center"><b>Size (Byte)</b></td>
<td align="center"><b>Remark</b></td>
</tr>
<%
		HashMap hCacheStruct =  TableLookupCache.getCacheStructure();
		Set key = hCacheStruct.keySet();
		Iterator i = key.iterator();
		int cacheTotalSize = 0;
		int cacheTotalRecord = 0;
			
		while(i.hasNext()){
		String error = "-";
		Vector vCache = new Vector();
		HashMap hCache = new HashMap();
		String cacheName = "";
		int length = 0;
		cacheName = i.next().toString();
		System.out.println(">>>>test"+hCacheStruct.get(cacheName));
		try{
			
			byte[] cacheByte = null;
			String type = "";
			
			try{
				vCache = (Vector)hCacheStruct.get(cacheName);
				type = "V";
			}catch(ClassCastException e){
				hCache = (HashMap)hCacheStruct.get(cacheName);
				type = "H";
			}
			
			try{
				if(vCache!=null && vCache.size()>0){
					Serializable testCache = (Serializable)vCache.get(0);
				}
			}  catch(Exception e){
				error = "This Object is not Serializable";
				//error = e.getMessage();
			}
			try{
				Serializable cacheSerial = (Serializable)vCache;
				cacheByte =  serialize(cacheSerial);
			}  catch(Exception e){
				error = "This Object is not Serializable";
				//error = e.getMessage();
			}
			
			if("H".equals(type)){
				try{
					Serializable cacheSerial = (Serializable)hCache;
					cacheByte =  serialize(cacheSerial);
				}  catch(Exception e){
					error = "This Object is not Serializable";
					//error = e.getMessage();
				}
			}

			if(cacheByte==null){
				length = 0;
			}else{
				length = cacheByte.length;
			}

			cacheTotalSize = cacheTotalSize + cacheByte.length;
			cacheTotalRecord = cacheTotalRecord + vCache.size();

		}catch(Exception e){
			error = "Unknown Object type";
		}
%>

<tr>
<td align="center"><%=cacheName%></td>
<td align="center"><%=vCache.size()%></td>
<td align="center"><%=length%></td>
<td align="center"><%=error%></td>
</tr>

<%
		}
%>
<tr>
<td align="center"><b>Total Cache (<%=hCacheStruct.size()%>)</b></td>
<td align="center"><b><%=cacheTotalRecord%></b></td>
<td align="center"><b><%=cacheTotalSize%></b></td>
<td>&nbsp;</td>
</tr>
</table>

</BODY>
</HTML>
