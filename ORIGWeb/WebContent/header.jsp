<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<jsp:useBean id="ORIGUser" class="com.eaf.orig.profile.model.UserDetailM" scope="session" />
<nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0; white-space: nowrap;">
	<div class="navbar-header">
		<a class="navbar-brand"><img src="images/ulo/logo.jpg"></a>
	</div>
	<div class="nav navbar-top-links navbar-left col-xs-6">
		<div class="row">
			<div class="col-xs-12 line1"><%=LabelUtil.getText(request, "HEADER_TEXT")%> 
			<span class="profilename"><%=CacheControl.getName("User", ORIGUser.getUserName())%></span>
			</div>			
		</div>
		<div class="row">
			<div class="col-xs-6 line2" style="font-size: 12px">
			<%
				out.print(LabelUtil.getText(request, "LAST_LOGIN"));
				out.print(" ");
				out.print(FormatUtil.getTime(ORIGUser.getLastLogonDate(), FormatUtil.Format.HHMMSS));
				out.print(" | ");
				out.print(FormatUtil.getDate(ORIGUser.getLastLogonDate(), FormatUtil.Format.dd_MMM_yyyy,FormatUtil.TH));
			%>				
			</div>			
		</div>
	</div>
	<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
	</button>
	<ul class="nav navbar-top-links navbar-right">
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<%					
					String imageUrl = "images/template/user.png";
					String userName = ORIGUser.getUserName();
					if(!Util.empty(userName)){
						imageUrl = SystemConfig.getProperty("USER_IMAGE_URL").replace("{userName}",userName);
					}
				 %>
				<span class="avatar"><img src="<%=imageUrl%>" onerror="this.src='images/template/user.png';"></span> <i class="fa fa-chevron-down" style="vertical-align: bottom"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
			<%if(ORIGUser.contains(OrigConstant.SystemName.ORIG)){%>
				<li><a href="index.jsp"><i class="fa fa-home fa-fw"></i> Main</a></li>
				<li class="divider"></li>
			<%}%>
			<%if(ORIGUser.contains(OrigConstant.SystemName.DASHBOARD)){%>
				<li><a href="<%=SystemConfig.getProperty("DASHBOARD_URL")%>"><i class="fa fa-bar-chart fa-fw"></i> Dashboard</a></li>
				<li class="divider"></li>
			<%}%>
				<li><a href="logout.jsp" onclick="return confirmLink('Do you want to logout?', this)"><i class="fa fa-power-off fa-fw"></i> Logout</a></li>
			</ul>
		</li>
	</ul>
</nav>