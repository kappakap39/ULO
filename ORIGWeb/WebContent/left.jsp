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
	logger.debug("currentMenuId >> "+currentMenuId);
%>
<%=HtmlUtil.hidden("currentMenuId",currentMenuId)%>

<div class="navbar-default sidebar fixed" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			<%
				String LOGON_CONTEXT = SystemConfig.getProperty("LOGON_CONTEXT");
				if(!Util.empty(topLevelMenus)) {
					for(MenuM topLevelMenu: topLevelMenus) {
					String topLevelMenuId = topLevelMenu.getMenuID();
					String topMenuIcon = topLevelMenu.getMenuIcon();
					out.println("<li><a id='"+topLevelMenuId+"' href='#'><span class='fa "+topMenuIcon+" fa-fw'></span><span class='sidebar-title'>"+topLevelMenu.getMenuName()+"</span><span class='fa arrow'></span></a>");
						out.println("<ul class='nav nav-second-level small-scrollable'>");
						if(!Util.empty(subLevelMenus)){
							logger.debug("topLevelMenuId : "+topLevelMenuId);
							ArrayList<MenuM> subMenus = subLevelMenus.get(topLevelMenuId);							
							logger.debug("subMenus : "+subMenus.size());	
							for(MenuM subMenu: subMenus){		
								String menuId = subMenu.getMenuID();		
								logger.debug("menuId : "+menuId);						
								String style = (null != menuId && menuId.equals(currentMenuId))?"active":"";
								String menuIcon = subMenu.getMenuIcon();
								String url = "FrontController?action=MenuAction&handleForm=N&menuId="+menuId;
								out.println("<li><a id='"+menuId+"' class='"+style+"' href='"+url+"'><span class='fa "+menuIcon+" fa-fw'></span> "+subMenu.getMenuName()+"</a></li>");
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