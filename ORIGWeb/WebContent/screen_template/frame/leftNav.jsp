<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@page import="com.eaf.orig.shared.model.ProcessMenuM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.master.model.menu.MenuM"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
<link href="../../css/ulo-le../../js/template/jquery-1.7.1.jssheet" type="text/css" />
<script type="text/jav../../js/template/jquery-ui-1.8.16.jsy-1.7.1.js"></script>
<script type="text/javascr../../js/template/slimScroll.jsery-ui-1.8.16.js"></script>
<script type="text/j../../js/template/DisableInvalidKey.jsScroll.js"></script>
<script type="text/javascript" src="../../js/DisableInvalidKey.js"></script>
</head>
<body background="" topmargin="0" leftmargin="0" class="htme-body">
<%
	// Processing menus to get a tree hierarchy
	Logger logger = Logger.getLogger(this.getClass());
	Vector<MenuM> vecMenus = (Vector<MenuM>)request.getSession().getAttribute("vecMenus");
	if(vecMenus == null) vecMenus = new Vector<MenuM>();
	ArrayList<MenuM> topLevelMenus = new ArrayList<MenuM>();
	HashMap<String,ArrayList<MenuM>> subMenu = new HashMap<String,ArrayList<MenuM>>();
/**	ArrayList<MenuM> overflows = new ArrayList<MenuM>();*/	
	logger.debug("[leftNav.jsp]...vecMenus : "+vecMenus.size());
	
	for(MenuM menuM :vecMenus){
	    //Sankom 20120213  menu Exit
		if("EX000".equalsIgnoreCase(menuM.getMenuID().trim())) {
			continue;
		}		
		if("LABEL".equalsIgnoreCase(menuM.getMenuType().trim())) {		
			topLevelMenus.add(menuM);
			subMenu.put(menuM.getMenuID(), new ArrayList<MenuM>());
		}else {
			ArrayList<MenuM> subGroup = subMenu.get(menuM.getMenuReference());			
// 			if(subGroup == null){overflows.add(menuM);}else{subGroup.add(menuM);}		
			if(null != subGroup){
				subGroup.add(menuM);
			}	
		}		
	}
	ProcessMenuM currentMenuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
	if(currentMenuM == null) currentMenuM = new ProcessMenuM();

%>
	<div class="some-content-related-div">
		<div id="inner-content-div">
			<div id="accordionMenu" style="width:100%">			
<%
				for(MenuM menuM: topLevelMenus){
%>				
				<H3 class="menu-top-header"><img height="6" width="9" src="../../images/arrowdown.png" />
						<A href="javascript:void();">&nbsp;<%=menuM.getMenuName()%></A></H3>
						
					<div class="accordion">
						<%
							ArrayList<MenuM> subMenus = subMenu.get(menuM.getMenuID());
							String styleMenu = "";
							String mUrl = "";
							
// 							if("Y".equals(menuM.getOutsideURL())){
// 								mUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() 
// 											+menuM.getMenuAction();
// 							}else{
// 								mUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() 
// 											+request.getContextPath()+"/FrontController?action=Menu_Show&handleForm=N&MenuID=";
// 							}
														
							for(MenuM sub: subMenus) {
							
								String url = "";
							
								if("Y".equals(sub.getOutsideURL())){
									url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() 
												+sub.getMenuAction()+"&handleForm=N&MENU_ID="+sub.getMenuID();
								}else{
									mUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() 
												+request.getContextPath()+"/FrontController?action=Menu_Show&handleForm=N&MENU_ID=";
									url = mUrl+sub.getMenuID();	
								}
																											
								styleMenu = "list-menu";
								if(sub.getMenuID().equals(currentMenuM.getMenuID())){
									styleMenu = "list-menu list-menu-active";
								}					
						%>
								<H3 class="<%=styleMenu%>" id="<%=sub.getMenuID()%>">
									<img height="9" width="8" src="../../images/arrowright.png" />
									&nbsp;
									<A href="<%=url%>" class="menu-link <%=sub.getMenuID()%>" target="<%=sub.getMenuTarget()%>" style="text-decoration:none;">
											<%=sub.getMenuName()%>
									</A>
								</H3>
						<%
							}
					 	%>
					</div>			
			<%									
				}
			%>
 		</div>
 		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
			   <td class="menu-footer">&nbsp;</td>
			</tr>
		</table>
 	</div>
</div>
		
<script language="JavaScript">
$(document).ready(function (){
	//#septemwi fix memory leak jquery event	
	if ( window.attachEvent && !window.addEventListener ) {
		window.attachEvent("onunload", function() {
			for ( var id in jQuery.cache ) {
				if ( jQuery.cache[ id ].handle ) {
					// Try/Catch is to handle iframes being unloaded, see #4280
					try {
						jQuery.event.remove( jQuery.cache[ id ].handle.elem );
					} catch(e) {}
				}
			}
			// The following line added by me to fix leak!
			window.detachEvent( "onload", jQuery.ready );
		});
	}
	
	$("#accordionMenu").accordion({
			autoHeight: false});
	 $('#inner-content-div').slimScroll({
        height: $(window).height()
    });
    $(".list-menu").hover(
	    function(){$(this).addClass("list-menu-haver");},
	    function(){$(this).removeClass("list-menu-haver");}
	);
	$(".menu-link").click(function() {
        $(".list-menu").removeClass("list-menu-active");
		var classname = $(this).attr('class').split(/\s+/);
		$(document.getElementById(classname[1])).addClass("list-menu-active");
    });
});
function menuSubmit(menuSequence){
	var dataFrame = top.document.getElementById("bodyFrameset");
	var appForm = dataFrame.document.forms['appFormName'];
	appForm.action.value= "Menu_Show";
	appForm.handleForm.value = "N"; 
	appForm.menuSequence.value=menuSequence;			
	appFormName.submit();
}
</script>
</body>
</html>
