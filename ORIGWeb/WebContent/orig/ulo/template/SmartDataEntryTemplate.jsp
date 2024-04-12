<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.control.util.SmartDataUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationImageDataM"%>
<%@page import="com.eaf.im.common.cont.IMConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="org.json.JSONArray"%>
<link href="../SmartData/resources/css/style.css" rel="stylesheet" />
<link href="../SmartData/resources/css/bootstrap-slider.css" rel="stylesheet" /> 
<link href="../SmartData/resources/css/style.override.css" rel="stylesheet" /> 
<script src="../SmartData/resources/js/util.js"></script>
<script src="../SmartData/resources/js/bootstrap-notify.js"></script>
<script src="../SmartData/resources/js/bootstrap-slider.js"></script>
<script src="../SmartData/resources/js/canvas_user.js"></script>
<style>.SmartDataEntry{padding: 0px 0px !important;margin: -15px !important;}</style>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<%
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	String roleId = ORIGForm.getRoleId();
	String CACHE_SMARTDATA_DOC_MAPPING = SystemConstant.getConstant("CACHE_SMARTDATA_DOC_MAPPING");
	String CACH_NAME_DOCUMENT_LIST = SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST");
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
	String IM_IMAGE_PART = SystemConfig.getProperty("IM_IMAGE_PART");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String uniqueId = applicationGroup.getApplicationGroupNo();
	String templateId = CacheControl.getName(CACHE_TEMPLATE,applicationGroup.getApplicationTemplate(),"TEMPLATE_CODE");
	String smartId = CacheControl.getName(CACHE_TEMPLATE,applicationGroup.getApplicationTemplate(),"SM_TEMPLATE_CODE");
	ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
	JSONArray jsonArrayImage = new JSONArray();
	if(!Util.empty(applicationImages)){
		int seq = 1;
		for(ApplicationImageDataM applicationImage:applicationImages){
			String host = applicationImage.getPath();
			ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
			if(!Util.empty(applicationImageSplits)){
				for(ApplicationImageSplitDataM applicationImageSplit:applicationImageSplits){
					JSONObject jsonImage = new JSONObject();
					PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(applicationImageSplit.getPersonalId());
					if(Util.empty(personalInfo)){
						personalInfo = new PersonalInfoDataM();
					}
// 					String imageURL = applicationImage.getPath()+IM_IMAGE_PART.replace("{ImageSize}",IMConstant.ImageSize.ORIGINAL)
// 							.replace("{IMInternalID}",FormatUtil.displayText(applicationImageSplit.getImageId()));
					String imageURL = host+IM_IMAGE_PART.replace("{ImageSize}",IMConstant.ImageSize.ORIGINAL)
							.replace("{IMInternalID}",FormatUtil.displayText(applicationImageSplit.getImageId()));
					
// 					String thumbnailURL = applicationImage.getPath()+IM_IMAGE_PART.replace("{ImageSize}",IMConstant.ImageSize.THUMBNAIL)
// 							.replace("{IMInternalID}",FormatUtil.displayText(applicationImageSplit.getImageId()));
					String thumbnailURL = host+IM_IMAGE_PART.replace("{ImageSize}",IMConstant.ImageSize.THUMBNAIL)
							.replace("{IMInternalID}",FormatUtil.displayText(applicationImageSplit.getImageId()));
					jsonImage.put("personalTypeDesc", ListBoxControl.getName(FIELD_ID_PERSONAL_TYPE, personalInfo.getPersonalType()));
					jsonImage.put("docTypeDesc", CacheControl.getName(CACH_NAME_DOCUMENT_LIST, applicationImageSplit.getDocType()));
					jsonImage.put("imgpath",imageURL);
					jsonImage.put("isPrint",SmartDataUtil.isPrint(applicationImageSplit, roleId));
					jsonImage.put("imgthumbpath",thumbnailURL);
					jsonImage.put("doctypeid",FormatUtil.getInt(CacheControl.getName(CACHE_SMARTDATA_DOC_MAPPING,applicationImageSplit.getDocType())));
					jsonImage.put("seqingroup",applicationImageSplit.getSeq());
					jsonArrayImage.put(jsonImage);
				}
			}
		}
	}
%>
<input type="hidden" id="selectedTemplateId" value="<%=smartId%>">
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="canvascontainer SmartDataEntry-inner"><br>
                    <input type="hidden" id="imgurl"/> <input id="scale" type=hidden value="1.00" /><input id="selected_fld" type="hidden" value=""><input type="hidden" id=px value=0><input type="hidden" id=py value=0><input type="hidden" id="cp" value=""><input type="hidden" id="cx" value=""><input type="hidden" id="cy" value=""><input type="hidden" id="cw" value=""><input type="hidden" id="ch" value="">
                </div>
            </div>
        </div>
    </div>
</div>
<script type='text/javascript'>
	var imgdata = <%=jsonArrayImage.toString()%>;
</script>
<script>
    var items = ['text','text','text','radio','checkbox'];
    var slider = null;
    var boxcontainer_original = [];
    var containerwidth = $('.SmartDataEntry').width() + 36;
    var contailerheight= 400;//$(window).height()-240;//$('.SmartDataEntry').height() + 0;
    var canvassettings = {'w':400,'h':380,'dynamiczoom':true,'dynamiczoomvalue':2.00};
    
    function initConfig() {
    	 boxcontainer_original.length = 0;
         boxcontainer.length = 0;

        $('#canvas').removeAttr('style');
        $('#canvas').css({'margin': '0px -16px', 'cursor': 'default'});

        $.ajax({
            url: "../SmartData/api/template/coordinate",
            type: "GET",
            data: {"templateId": $('#selectedTemplateId').val()},
            cache: false,
            async: false,
            error: function (xhr, status, error) {
                log(error);
            },
            success: function (data1, status, xhr) {
                var result = JSON.parse(data1);
                var json = result.data;
                if (result.totalRecordCount < 1) {
                    notif('Template', 'No templates data available!', 'pastel-danger', 5000);
                    return;
                }
                var factor = $('canvas').width() / 1000;
                $.each(json, function (idx, obj) {
                    addRect(obj.pagenumber, parseInt((obj.x * factor).toFixed(0)), parseInt((obj.y * factor).toFixed(0)), parseInt((obj.w * factor).toFixed(0)), parseInt((obj.h * factor).toFixed(0)), obj.fieldid);
                });
                openCarouselPage(1);
            }
        });
        boxcontainer_original = cloneBoxContainer(boxcontainer);
        clear(ctx);
        clear(gctx);
        $('#cp').val('0');
    }
</script>
<script type='text/javascript'>
    
	$(function() {
		$.ajax({
			url : "../SmartData/api/viewer",
			type : "POST",
			data : {
				"width" : parseInt(containerwidth),
				"height" : canvassettings['h'],
				"templateId" : $('#selectedTemplateId').val(),
				"btnZoom" : false,
				"btnSlider" : true,
				"btnPlayPause" : true,
				"mouseMove" : true,
				"mode" : "advance",
				"resource" : JSON.stringify(imgdata),
			},
			cache : false,
			async : false,
			error : function(xhr, status, error) {
				//                  alert('Error creating viewer :'+error);
			},
			success : function(data1, status, xhr) {

				$(".canvascontainer").append(data1);
				offsetCanvas_x = $("#canvas").offset().left;
				offsetCanvas_y = $("#canvas").offset().top;
				$.getScript("../SmartData/resources/js/lib-user.js");
				slider = new Slider("#CanvasResizer", {
					min : 50,
					max : 800,
					value : 100,
					step : 2,
					reversed : false,
					formatter : function(b) {
						var a = (b / 100).toFixed(2);
						resetBoxes(boxcontainer, a, imgX, imgY);
						$("#canvas").css({
							"background-size" : b + "%"
						});
						return "Current Zoom: " + b + "%";
					}
				});
			}
		});
	});

	var SmartDataEntry = {
		isShow : true,
		show : function(e) {
			$('.SmartDataEntry').removeClass('collapse');
			if (e != false) {
				SmartDataEntry.isShow = true;
			}
		},
		hide : function(e) {
			$('.SmartDataEntry').addClass('collapse');
			if (e != false) {
				SmartDataEntry.isShow = false;
			}
		},
		toggle : function() {
			$('.SmartDataEntry').toggleClass('collapse');
			if ($('.SmartDataEntry').hasClass('collapse')) {
				SmartDataEntry.isShow = false;
			} else {
				SmartDataEntry.isShow = true;
			}
		}
	};
	
	var loop = 50;
	var threadId = 0;
	$(function() {
		$('.SmartDataEntry').resize(function() {
			resizeSmartDataEntry();
			resizeFormHeader();
			modalreposition();
		});
		threadId = setInterval(function() {
			resizeSmartDataEntry();
			resizeFormHeader();
			modalreposition();
			loop--;
			if (loop == 0) {
				clearInterval(threadId);
				$('.SmartDataEntry').css('opacity', '');
			}
		}, 10);
		resizeSmartDataEntry();
		resizeFormHeader();
		modalreposition();
	});
</script>
