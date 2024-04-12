<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<jsp:useBean id="ORIGUser" class="com.eaf.orig.profile.model.UserDetailM" scope="session" />
<nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0; white-space: nowrap;">
	<div class="navbar-header">
		<a class="navbar-brand" href="index.html"><img src="images/ulo/logo.jpg"></a>
	</div>
	<!-- /.navbar-header -->
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
				out.print(FormatUtil.getDate(ORIGUser.getLastLogonDate(), FormatUtil.Format.dd_MMM_yyyy));
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
				<span class="avatar"><img src="images/template/picprofile1.jpg"></span> <i class="fa fa-chevron-down" style="vertical-align: bottom"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#"><i class="fa fa-user fa-fw"></i> Profile</a></li>
				<li class="divider"></li>
				<li><a href="logout.jsp"><i class="fa fa-power-off fa-fw"></i> Logout</a></li>
			</ul> <!-- /.dropdown-user --></li>
		<!-- /.dropdown -->
	</ul>
	<!-- /.navbar-top-links -->
</nav>
<!-- <script type="text/javascript">
// $("#countdown-time").countdown("2015/05/08",function(event){
//     $(this).text('{Developer},Death Line 8 May 2015 ('+event.strftime('%D days %H hr %M min %S sec')+')');
// });
</script> -->