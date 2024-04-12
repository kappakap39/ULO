<%@page contentType="text/html;charset=UTF-8"%>
<div class="row">
	<div class="sd-toggle-mini">
		<a> <span class="fa fa-chevron-left"></span></a>
	</div>
	<script>
		$('.sd-toggle-mini').click(function(e) {
			e.preventDefault();
			$(this).disableSelection();
			SmartDataEntry.toggle();
		});
		
		// Need to hide scroll bar
		$('.page-frame').css('overflow', 'hidden');
		$('body').css('overflow-x', 'hidden');
	</script>
	<div class="">
		<div id="FormHeaderManager" class="FormHeaderManager col-md-12 right-side">
			<jsp:include page="FormHeader.jsp" />
		</div>
	</div>
	<div class="col-md-12 nopadding-right" style="border-left: solid 2px #DDD;background-color: #fff;">
		<div id="FormHandlerManager" class="FormHandlerManager right-side">
			<script>resizeFormTemplate();</script>
			<jsp:include page="FormTemplate.jsp" />
		</div>
	</div>
</div>