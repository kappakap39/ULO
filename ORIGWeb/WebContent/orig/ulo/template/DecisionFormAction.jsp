<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<section class="work_area">
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">		
			<%=HtmlUtil.getLabel(request,"MANUAL_DECISION","col-sm-2 col-md-3 control-label")%>
			<%=HtmlUtil.dropdown("MANUAL_DECISION","","ManualDecisionAction","","","110","ALL",HtmlUtil.EDIT,"","col-sm-10 col-md-9",request)%>		
		</div>
	</div>
</div>
</div>
</div>
</section>