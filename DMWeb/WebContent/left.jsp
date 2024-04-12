<%@page import="com.eaf.orig.menu.view.webaction.Menu_ShowWebAction"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Vector"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.master.model.menu.MenuM"%>
<jsp:useBean id="menuHandlerManager" class="com.eaf.core.ulo.common.model.MenuHandlerManager" scope="session" />
<jsp:useBean id="FlowControl" class="com.eaf.orig.ulo.model.control.FlowControlDataM" scope="session" />
<%
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<MenuM> topLevelMenus = menuHandlerManager.getTopLevelMenus();
	HashMap<String,ArrayList<MenuM>> subLevelMenus = menuHandlerManager.getSubMenus();
	String currentMenuId = FlowControl.getMenuId();
	String currentTopMenuId = FlowControl.getTobMenuId();
	logger.debug("currentMenuId >> "+currentMenuId);
	logger.debug("currentTopMenuId >> "+currentTopMenuId);
%>
<%=HtmlUtil.hidden("currentTopMenuId",currentTopMenuId)%>
<%=HtmlUtil.hidden("currentMenuId",currentMenuId)%>

<div class="navbar-default sidebar fixed" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			<%
				String LOGON_CONTEXT = SystemConfig.getProperty("LOGON_CONTEXT");
				if(!Util.empty(topLevelMenus)) {
					for(MenuM topLevelMenu: topLevelMenus) {
					String topLevelMenuId = topLevelMenu.getMenuID();
					out.println("<li><a id='"+topLevelMenuId+"' href='#'><span class='fa fa-check fa-fw'></span><span class='sidebar-title'>"+topLevelMenu.getMenuName()+"</span><span class='fa arrow'></span></a>");
						out.println("<ul class='nav nav-second-level'>");
						if(!Util.empty(subLevelMenus)){
							ArrayList<MenuM> subMenus = subLevelMenus.get(topLevelMenuId);	
							for(MenuM subMenu: subMenus){		
								String MENU = subMenu.getMenuID();
								String CONTEXT = subMenu.getMenuTarget();			
								String DEFAULT_CONTEXT = request.getContextPath().replaceAll("\\/","");								
								String style = (null != MENU && MENU.equals(currentMenuId))?"active":"";
								String URL = "/"+CONTEXT+"/FrontController?action=Menu_Show&handleForm=N&MENU_ID="+MENU;
								out.println("<li><a id='"+subMenu.getMenuID()+"' class='"+style+"' href='"+URL+"'><span class='fa fa-bar-chart-o fa-fw'></span> "+subMenu.getMenuName()+"</a></li>");
							}
						}
						out.println("</ul>");
						out.println("</li>");
					}
				}
			%>
		</ul>		
	</div>
</div>
<div class="sidebar-toggle-mini">
    <a>
        <span class="fa fa-chevron-right"></span>
    </a>
</div>
<script type="text/javascript">
	$(function(){
		var element = $('ul#side-menu a').filter(function() {
	        return $(this).attr('id') == '<%= currentMenuId %>';
	    }).addClass('active').parent().parent().addClass('in').parent();
	    if (element.is('li')) {
	        element.addClass('active');
	    }
	    
	    $('.sidebar-toggle-mini a').click(function (e) {
	    	e.preventDefault();
	    	$(this).disableSelection();
	    	$('body').toggleClass('sb-l-m');
	    	if (webStorage.checkSupport()) {
	    		if ($('body').hasClass('sb-l-m')) {
	    			console.log('Sidebar Collaped!');
	    			webStorage.getSessStorage().sidebarCollapsed = true;
	    		} else {
	    			console.log('Sidebar Uncollaped!');
	    			webStorage.getSessStorage().sidebarCollapsed = false;
	    		}
	    	}
	    });
	});
	

</script>