<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<!-- MasterFrameTemplate -->
<script>
	// Remove main bar
	if ($('.navigation').length > 0) {
		$('.navigation').remove();
		$('#page-wrapper').css('padding-top', 0);
	}
</script>
<div class="SmartDataEntry left-side">
	<div class="preload-wrapper" style="display:none;position: fixed;width: 28.55%;">
		<div class="sk-fading-circle" style="position: fixed;margin: 180px;">
			<div class="sk-circle1 sk-circle"></div>
			<div class="sk-circle2 sk-circle"></div>
			<div class="sk-circle3 sk-circle"></div>
			<div class="sk-circle4 sk-circle"></div>
			<div class="sk-circle5 sk-circle"></div>
			<div class="sk-circle6 sk-circle"></div>
			<div class="sk-circle7 sk-circle"></div>
			<div class="sk-circle8 sk-circle"></div>
			<div class="sk-circle9 sk-circle"></div>
			<div class="sk-circle10 sk-circle"></div>
			<div class="sk-circle11 sk-circle"></div>
			<div class="sk-circle12 sk-circle"></div>
		</div>
	</div>
	<jsp:include page="/orig/ulo/view/SmartDataEntry.jsp" />
	<script>
// 		$(function() {
// 			setTimeout(function() {
// 				$.get('orig/ulo/template/SmartDataEntryTemplate.jsp', function(data) {
// 					$('.SmartDataEntry').css('opacity', 0);
// 					setTimeout(function(data) {
// 						$('.SmartDataEntry').html(data);
// 					}, 40, data);
// 				});
// 			}, 900);
// 		});
	</script>
</div>
<div class="MasterForm right-side"><jsp:include page="/orig/ulo/template/MasterFormTemplate.jsp" /></div>
<%
String FEATURE_SMARTDATA_FIELD = SystemConfig.getProperty("FEATURE_SMARTDATA_FIELD");
if(MConstant.FLAG_Y.equals(FEATURE_SMARTDATA_FIELD)){
%>
<script>
// up up down down left right left right b a enter
Mousetrap.bind(['ctrl+e','up up down down left right left right b a enter'],function(){
	var options = {
        url: "/ORIGWeb/orig/ulo/view/SmartDataField.jsp",
        title:'Smart Data Field',
        size: eModal.size.lg,
         buttons: [
	            {text: 'Update', style: 'success', close: false, click: updateSmartData }
	        ]
    };
	eModal.ajax(options);
});
function updateSmartData(){
	var smartdataElements = $('.smartdata');
	var smartData = $('#element-smartid').val();
	var smartDataElementJson = [];
	if(smartdataElements != undefined){
		$.each(smartdataElements, function (i, smartdataElement){
			var smartSubformId = $(smartdataElement).find('#element-smartdata').val();
			if(smartSubformId != ''){
				var elementFields = $(smartdataElement).find('.element-field'); 
				$.each(elementFields, function (j, elementField){
					var elementLabelDesc = $.trim($(elementField).find('.element-name').text().replace(':','').replace('*','').replace(':',''));
					smartDataElementJson.push({
						subFormId:smartSubformId,
						elementLabel:encodeURIComponent(elementLabelDesc),
						elementInput:$(elementField).attr('elementinput')
					});
				});
			}
		});
	}	
	var smartDataJson = {smartDataId:smartData,smartDataElement:smartDataElementJson};
	var $data = 'smartData='+getPrettyJson(smartDataJson);
	ajax('com.eaf.orig.ulo.app.view.util.manual.SmartDataAction',$data,function(data){
		alertBox(data);
	});
}
function getPrettyJson(json){
	var jsonObj = (typeof json == 'string'?JSON.parse(json):json);
	var jsonPretty = JSON.stringify(jsonObj, null, '\t');
	if(jsonPretty)jsonPretty = jsonPretty.replace(/\\/g,'');
	return jsonPretty;
}	
</script>
<%}%>