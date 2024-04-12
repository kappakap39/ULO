<%@page import="com.master.model.menu.MenuM"%>
<%@page import="java.util.Vector"%>
var arrayobj = [];
<%
/**[MenuID, MenuReference, MenuName, MenuAction, MenuTarget, icon]**/
Vector vecMenus = (Vector)request.getSession().getAttribute("vecMenus");
for(int i=0;i<vecMenus.size();i++){
	MenuM menuM = (MenuM)vecMenus.elementAt(i);
	System.out.println(">>> MenuID="+menuM.getMenuID());
	if("PAGE".equals(menuM.getMenuType())){
		if("1".equals(menuM.getMenuLevel())){
			out.print("arrayobj["+i+"] = ['"+menuM.getMenuID()+"',-1,'"+menuM.getMenuName()+"'");
			if(menuM.getMenuAction()!=null)out.print(",'/ORIGWeb/FrontController?action=Menu_Show&handleForm=N&menuSequence="+menuM.getMenuSeq()+"&userName="+request.getRemoteUser()+"'");
			if(menuM.getMenuTarget()!=null)out.print(",'"+menuM.getMenuTarget()+"'");
			out.println("]");
		}else{
			out.print("arrayobj["+i+"] = ['"+menuM.getMenuID()+"','"+menuM.getMenuReference()+"','"+menuM.getMenuName()+"'");
			if(menuM.getMenuAction()!=null)out.print(",'/ORIGWeb/FrontController?action=Menu_Show&handleForm=N&menuSequence="+menuM.getMenuSeq()+"&userName="+request.getRemoteUser()+"'");
			if(menuM.getMenuTarget()!=null)out.print(",'"+menuM.getMenuTarget()+"'");
			out.println("]");
		}
	}else{
		if("1".equals(menuM.getMenuLevel())){
			out.println("arrayobj["+i+"] = ['"+menuM.getMenuID()+"',-1,'"+menuM.getMenuName()+"']");
		}else{
			out.println("arrayobj["+i+"] = ['"+menuM.getMenuID()+"','"+menuM.getMenuReference()+"','"+menuM.getMenuName()+"']");
		}
	}
}
%>
<%--
var arrayobj = [];
arrayobj[0] = ['0','-1','Application Menu'];
<%
	/**[MenuID, MenuReference, MenuName, MenuAction, MenuTarget, icon]**/
	Vector vecMenus = (Vector)request.getSession().getAttribute("vecMenus");
	for(int i=0;i<vecMenus.size();i++){
		MenuM menuM = (MenuM)vecMenus.elementAt(i);
		if("PAGE".equals(menuM.getMenuType())){
			if("1".equals(menuM.getMenuLevel())){
				out.println("arrayobj["+(i+1)+"] = ['"+menuM.getMenuID()+"','0','"+menuM.getMenuName()+"','"+menuM.getMenuAction()+"','"+menuM.getMenuTarget()+"']");
			}else{
				out.println("arrayobj["+(i+1)+"] = ['"+menuM.getMenuID()+"','"+menuM.getMenuReference()+"','"+menuM.getMenuName()+"','"+menuM.getMenuAction()+"','"+menuM.getMenuTarget()+"']");
			}
		}else{
			if("1".equals(menuM.getMenuLevel())){
				out.println("arrayobj["+(i+1)+"] = ['"+menuM.getMenuID()+"','0','"+menuM.getMenuName()+"']");
			}else{
				out.println("arrayobj["+(i+1)+"] = ['"+menuM.getMenuID()+"','"+menuM.getMenuReference()+"','"+menuM.getMenuName()+"']");
			}
		}
	}
%>
arrayobj[<%=vecMenus.size()+1%>] = ['-2','0','Log Out','javascript:logOut()','','scripts/dtree/img/downloads-arrows.gif'];
--%>