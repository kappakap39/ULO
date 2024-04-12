<%@ page import='com.eaf.j2ee.system.LoadXML' %>
<%@ page import='java.security.MessageDigest' %>
<%
String docId=request.getParameter("docId");
if(docId!=null && !docId.equals("")){
	String CM_SERVER_NAME_EXT = String.valueOf(LoadXML.getCmServerEx().get("1"));
	String SERVER_CM_PROJECT = String.valueOf(LoadXML.getServerCmProject().get("1"));
	String CM_USERID = String.valueOf(LoadXML.getCmUserid().get("1"));
	String CM_PASSWORD = String.valueOf(LoadXML.getCmPassword().get("1"));
	String CM_SERVER = String.valueOf(LoadXML.getCmServerName().get("1"));
	String CM_DOC_ITEMTYPE = String.valueOf(LoadXML.getCmDocItemtype().get("1"));
	String hashStr=getSHA(request.getSession().getId());
	String queryStr="?sessionIdF="+hashStr+"&dataType="+CM_DOC_ITEMTYPE+"&docId="+docId+"&server="+CM_SERVER+"&userid="+CM_USERID+"&password="+CM_PASSWORD+"&sessionIdB="+hashStr;
	String url="http://"+CM_SERVER_NAME_EXT+"/"+SERVER_CM_PROJECT+"/ConnectCM"+queryStr;
	response.sendRedirect(url);
}
%>
<%!
	public String getSHA(String data) throws Exception{
		char[] hexits="0123456789ABCDEF".toCharArray();

		MessageDigest md = null;
		byte [] ba = null;
		md = MessageDigest.getInstance("SHA");
		md.update(data.getBytes("UTF-8"));
		ba = md.digest();
		StringBuffer sb = new StringBuffer(ba.length*2);
		for (int i = 0; i < ba.length; i++) {
			sb.append(hexits[(((int)ba[i] & 0xFF) / 16) & 0x0F]);
			sb.append(hexits[((int)ba[i] & 0xFF) % 16]);
		}
		return sb.toString();
	}
%>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>