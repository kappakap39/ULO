<%@page import="com.eaf.service.module.model.CheckProductDupRequestDataM"%>
<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
<script type="text/javascript" src="/ServiceCenterWeb/js/pace.js"></script>
	<script>
		function request_service()
		{
			if(!$("#QRNo_input").val())
			{
				alert('Please input QR No.');
			}
			else
			{
				Pace.block();
				//Get mode to do
				var mode = [];
				$('input[name^=cbopt]').each(function()
				{
					if(this.checked == true)
					{
						mode.push($(this).val());
					}
				});
				
				if(mode.length < 1)
				{
					alert('Please select atleast 1 search mode.');
					Pace.unblockFlag = true;
			        Pace.unblock(); 
					return;
				}
				//alert('mode =' + mode);
				
				//clear and hide all results
				$('div[id^=divTable]').hide();
			    //$('table[id^=table]').tbody.empty();
				
				//Call QRInfo servlet
				$.ajax({
					url : "/ServiceCenterWeb/QRInfo?QRNo=" + $("#QRNo_input").val() + "&Mode=" + mode.toString(),
					type : "get",
					success : function(data){
						//alert(data);
						var obj = $.parseJSON(data);
						var ULOAL = obj.ULOAL;
						var ULOAH = obj.ULOAH;
						var ULOOII = obj.ULOOII;
						var ULOBII = obj.ULOBII;
						var OLCI = obj.OLCI;
						var IMOII = obj.IMOII;
						var IMOJI = obj.IMOJI;
						var ODMET = obj.ODMET;
						var OLTL = obj.OLTL;
						if(ULOAL)
						{
							$("#divTableULOAL").show();
							$("#divTableULOAL").children().show();
							$("#tableULOAL tbody").empty();
						    $("#tableULOAL tbody").append(ULOAL);
						}
						if(ULOAH)
						{
							$("#divTableULOAH").show();
							$("#divTableULOAH").children().show();
							$("#tableULOAH tbody").empty();
						    $("#tableULOAH tbody").append(ULOAH);
						}
						if(ULOOII)
						{
							$("#divTableULOOII").show();
							$("#divTableULOOII").children().show();
							$("#tableULOOII tbody").empty();
						    $("#tableULOOII tbody").append(ULOOII);
						}
						if(ULOBII)
						{
							$("#divTableULOBII").show();
							$("#divTableULOBII").children().show();
							$("#tableULOBII tbody").empty();
						    $("#tableULOBII tbody").append(ULOBII);
						}
						if(OLCI)
						{
							$("#divTableOLCI").show();
							$("#divTableOLCI").children().show();
							$("#tableOLCI tbody").empty();
						    $("#tableOLCI tbody").append(OLCI);
						}
						if(IMOII)
						{
							$("#divTableIMOII").show();
							$("#divTableIMOII").children().show();
							$("#tableIMOII tbody").empty();
						    $("#tableIMOII tbody").append(IMOII);
						}
						if(IMOJI)
						{
							$("#divTableIMOJI").show();
							$("#divTableIMOJI").children().show();
							$("#tableIMOJI tbody").empty();
						    $("#tableIMOJI tbody").append(IMOJI);
						}
						if(ODMET)
						{
							$("#divTableODMET").show();
							$("#divTableODMET").children().show();
							$("#tableODMET tbody").empty();
						    $("#tableODMET tbody").append(ODMET);
					    }
					    if(OLTL)
						{
							$("#divTableOLTL").show();
							$("#divTableOLTL").children().show();
							$("#tableOLTL tbody").empty();
						    $("#tableOLTL tbody").append(OLTL);
						}
					},
					complete: function(data){
						Pace.unblockFlag = true;
			            Pace.unblock(); 
			        }
				});
			}
		}
	</script>
	<script>
		function displayDiv(ele)
		{
			//alert('ele = ' + ele.id);
			var divId = 'div' + ele.id;
			var div = $('#' + divId);
		    div.toggle();
		}
		function toggleTable(silderele, ele)
		{
			silderele.slideToggle();
			if($(ele).hasClass('glyphicon-minus-sign'))
			{
				$(ele).removeClass('glyphicon-minus-sign').addClass('glyphicon-plus-sign');
			}
			else
			{
				$(ele).removeClass('glyphicon-plus-sign').addClass('glyphicon-minus-sign');
			}
			$(ele).parent().find('.glyphicon-file').toggle();
		}
		function copyTable(ele, alertele)
		{
			//Expand all link
			var aList = ele.find('a');
			var tempHtmlList = new Array();
			aList.each(function(idx, aele)
			{
				$(aele).hide();
				var divEle = $('#div' + aele.id);
				tempHtmlList.push(divEle.html());
				divEle.text(divEle.text());
    			divEle.show();
    		});
			
			var range = document.createRange();
			range.selectNode(document.getElementById($(ele).attr('id')));
			window.getSelection().removeAllRanges();
			window.getSelection().addRange(range);
			document.execCommand('copy');
			window.getSelection().removeAllRanges();
			
			//Hide all link
			aList.each(function(idx, aele)
			{
				$(aele).show();
				var divEle = $('#div' + aele.id);
				divEle.html(tempHtmlList.shift());
    			divEle.hide();
			});
			
			$('<span">Copied!</span>').insertAfter(alertele).delay(2000).fadeOut();
		}
	</script>
	<script>
		$("#selectAll").change(function(){  
			var status = this.checked; 
			$('input[name^=cbopt]').each(function()
			{
				this.checked = status;
			});
		});
		
		$('input[name^=cbopt]').change(function(){
			//uncheck "select all", if one of the listed checkbox item is unchecked
			if(this.checked == false){
				$("#selectAll")[0].checked = false;
			}
			
			//check "select all" if all checkbox items are checked
			if ($('input[name^=cbopt]:checked').length == $('input[name^=cbopt]').length ){ 
				$("#selectAll")[0].checked = true;
			}
		});
	</script>
</head>
<body>
	<Strong>QRInfo</Strong>
	<form class="form-inline well" id="idNoForm">
		<table>
			<tr>
				<td>QR No</td>
				<td><input id="QRNo_input" type="text" class="form-control input-sm" size="60" name="QRNo_input"></td>
				<td id="result-status">
				</td>
			</tr>
		</table>
		<br>
		<div class="container">
			  <div class="row">
				    <div class="col-sm">
				      	<input type="checkbox" id="selectAll" value=""> All
				    </div>
			  </div>
			  <div class="row">
			  		<div class="form-group col-sm-3">
				  		<input type="checkbox" name="cboptULOAL" value="ULOAL"> ULO Application Log 
					</div>
				  	<div class="form-group col-sm-3">
				  		<input type="checkbox" name="cboptULOAH" value="ULOAH"> ULO Application History 
					</div>
				  	<div class="form-group col-sm-3">
				  		<input type="checkbox" name="cboptULOOII" value="ULOOII"> ULO Online Interface Info 
					</div>
				  	<div class="form-group col-sm-3">
				  		<input type="checkbox" name="cboptULOBII" value="ULOBII"> ULO Batch Interface Info 
					</div>
			  </div>
			  <div class="row">
			  		<div class="form-group col-sm-3">
				  		<input type="checkbox" name="cboptODMET" value="ODMET"> ODM Execution Trace 
				  	</div>
				  	
				  	<div class="form-group col-sm-3">
				  		<input type="checkbox" name="cboptOLCI" value="OLCI"> OL Cache Info 
					</div>
					<div class="form-group col-sm-3">
						<input type="checkbox" name="cboptIMOII" value="IMOII"> IM Online Interface Info
					</div>
					<div class="form-group col-sm-3">
						<input type="checkbox" name="cboptIMOJI" value="IMOJI"> IM Online Job Info
					</div>
			  </div>
			  <div class="row">
			  		<div class="form-group col-sm-3">
				  		<input type="checkbox" name="cboptOLTL" value="OLTL"> OL Transaction Log 
				  	</div>
			  </div>
		</div>
	</form>
	
	<button type="button" class="btn btn-info" onclick="request_service()">Request Service</button>
	<br><br>
	<div id="divTableULOAL" style="display: none;">
		<div>
			<Strong> ULO Application Log </Strong>
			<span onclick="toggleTable($('#tableULOALSlider'),this);" class="glyphicon glyphicon-minus-sign"></span>
			<span onclick="copyTable($('#tableULOAL'),this);" class="glyphicon glyphicon-file"></span>
		</div>
		<div id="tableULOALSlider">
		<table id="tableULOAL" class="table table-bordered" >
			<thead>
				<tr>
					<td style="text-align: center;"><Strong>QR</Strong></td>
					<td style="text-align: center;"><Strong>Create Datetime</Strong></td>
					<td style="text-align: center;"><Strong>User</Strong></td>
					<td style="text-align: center;"><Strong>Action</Strong></td>
					<td style="text-align: center;"><Strong>App Status</Strong></td>				
					<td style="text-align: center;"><Strong>Life Cycle</Strong></td>
					<td style="text-align: center;"><Strong>Next State</Strong></td>
					<td style="text-align: center;"><Strong>Next Station</Strong></td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<br><br>
	</div>
	<div id="divTableULOAH" style="display: none;">
		<div>
			<Strong> ULO Application History </Strong>
			<span onclick="toggleTable($('#tableULOAHSlider'),this);" class="glyphicon glyphicon-minus-sign"></span>
			<span onclick="copyTable($('#tableULOAH'),this);" class="glyphicon glyphicon-file"></span>
		</div>
		<div id="tableULOAHSlider">
		<table id="tableULOAH" class="table table-bordered" >
			<thead>
				<tr>
					<td style="text-align: center;"><Strong>QR</Strong></td>
					<td style="text-align: center;"><Strong>Create Datetime</Strong></td>
					<td style="text-align: center;"><Strong>Create By</Strong></td>
					<td style="text-align: center;"><Strong>Role</Strong></td>
					<td style="text-align: center;"><Strong>Msg</Strong></td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<br><br>
	</div>
	<div id="divTableULOOII" style="display: none;">
		<div>
			<Strong> ULO Online Interface Info </Strong>
			<span onclick="toggleTable($('#tableULOOIISlider'),this);" class="glyphicon glyphicon-minus-sign"></span>
			<span onclick="copyTable($('#tableULOOII'),this);" class="glyphicon glyphicon-file"></span>
		</div>
		<div id="tableULOOIISlider">
		<table id="tableULOOII" class="table table-bordered" >
			<thead>
				<tr>
					<td style="text-align: center;"><Strong>QR</Strong></td>
					<td style="text-align: center;"><Strong>Create Datetime</Strong></td>
					<td style="text-align: center;"><Strong>Service</Strong></td>
					<td style="text-align: center;"><Strong>AC</Strong></td>
					<td style="text-align: center;"><Strong>Content Msg</Strong></td>
					<td style="text-align: center;"><Strong>Status</Strong></td>
					<td style="text-align: center;"><Strong>Desc</Strong></td>
					<td style="text-align: center;"><Strong>Error Msg</Strong></td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<br><br>
	</div>
	<div id="divTableULOBII" style="display: none;">
		<div>
			<Strong> ULO Batch Interface Info </Strong>
			<span onclick="toggleTable($('#tableULOBIISlider'),this);" class="glyphicon glyphicon-minus-sign"></span>
			<span onclick="copyTable($('#tableULOBII'),this);" class="glyphicon glyphicon-file"></span>
		</div>
		<div id="tableULOBIISlider">
		<table id="tableULOBII" class="table table-bordered" >
			<thead>
				<tr>
					<td style="text-align: center;"><Strong>QR</Strong></td>
					<td style="text-align: center;"><Strong>AppGroupId</Strong></td>
					<td style="text-align: center;"><Strong>LifeCycle</Strong></td>
					<td style="text-align: center;"><Strong>AppRecordId</Strong></td>
					<td style="text-align: center;"><Strong>Interface</Strong></td>
					<td style="text-align: center;"><Strong>Status</Strong></td>
					<td style="text-align: center;"><Strong>RefId</Strong></td>
					<td style="text-align: center;"><Strong>System01</Strong></td>
					<td style="text-align: center;"><Strong>CreateDate</Strong></td>
					<td style="text-align: center;"><Strong>Message</Strong></td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<br><br>
	</div>
	<div id="divTableOLCI" style="display: none;">
		<div>
			<Strong> OL Cache Info </Strong>
			<span onclick="toggleTable($('#tableOLCISlider'),this);" class="glyphicon glyphicon-minus-sign"></span> 
			<span onclick="copyTable($('#tableOLCI'),this);" class="glyphicon glyphicon-file"></span>
		</div>
		<div id="tableOLCISlider">
		<table id="tableOLCI" class="table table-bordered" >
			<thead>
				<tr>
					<td style="text-align: center;"><Strong>QR</Strong></td>
					<td style="text-align: center;"><Strong>ID_NO</Strong></td>
					<td style="text-align: center;"><Strong>LIFE_CYC</Strong></td>
					<td style="text-align: center;"><Strong>CREATE_DATE</Strong></td>
					<td style="text-align: center;"><Strong>EXPIRE_DATE</Strong></td>
					<td style="text-align: center;"><Strong>SERVICE</Strong></td>
					<td style="text-align: center;"><Strong>MSG</Strong></td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<br><br>
	</div>
	<div id="divTableIMOII" style="display: none;">
		<div>
			<Strong> IM Online Interface Info </Strong>
			<span onclick="toggleTable($('#tableIMOIISlider'),this);" class="glyphicon glyphicon-minus-sign"></span>
			<span onclick="copyTable($('#tableIMOII'),this);" class="glyphicon glyphicon-file"></span>
		</div>
		<div id="tableIMOIISlider">
		<table id="tableIMOII" class="table table-bordered" >
			<thead>
				<tr>
					<td style="text-align: center;"><Strong>QR</Strong></td>
					<td style="text-align: center;"><Strong>Create Datetime</Strong></td>
					<td style="text-align: center;"><Strong>Service</Strong></td>
					<td style="text-align: center;"><Strong>AC</Strong></td>
					<td style="text-align: center;"><Strong>Status</Strong></td>
					<td style="text-align: center;"><Strong>Desc</Strong></td>
					<td style="text-align: center;"><Strong>Error Msg</Strong></td>
					<td style="text-align: center;"><Strong>Content Msg</Strong></td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<br><br>
	</div>
	<div id="divTableIMOJI" style="display: none;">
		<div>
			<Strong> IM Online Job Info </Strong>
			<span onclick="toggleTable($('#tableIMOJISlider'),this);" class="glyphicon glyphicon-minus-sign"></span>
			<span onclick="copyTable($('#tableIMOJI'),this);" class="glyphicon glyphicon-file"></span>
		</div>
		<div id="tableIMOJISlider">
		<table id="tableIMOJI" class="table table-bordered" >
			<thead>
				<tr>
					<td style="text-align: center;"><Strong>PKID</Strong></td>
					<td style="text-align: center;"><Strong>TranID</Strong></td>
					<td style="text-align: center;"><Strong>QR</Strong></td>
					<td style="text-align: center;"><Strong>Form</Strong></td>
					<td style="text-align: center;"><Strong>RC</Strong></td>
					<td style="text-align: center;"><Strong>Channel</Strong></td>
					<td style="text-align: center;"><Strong>CrateDate</Strong></td>
					<td style="text-align: center;"><Strong>Status</Strong></td>
					<td style="text-align: center;"><Strong>Msg</Strong></td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<br><br>
	</div>
	<div id="divTableODMET" style="display: none;">
		<div>
			<Strong> ODM Execution Trace </Strong>
			<span onclick="toggleTable($('#tableODMETSlider'),this);" class="glyphicon glyphicon-minus-sign"></span>
			<span onclick="copyTable($('#tableODMET'),this);" class="glyphicon glyphicon-file"></span>
		</div>
		<div id="tableODMETSlider">
		<table id="tableODMET" class="table table-bordered" >
			<thead>
				<tr>
					<td style="width: 19%; text-align: center;"><Strong>Execution ID</Strong></td>
					<td style="width: 16%; text-align: center;"><Strong>Execution Date</Strong></td>
					<td style="width: 22%; text-align: center;"><Strong>Request</Strong></td>
					<td style="width: 22%; text-align: center;"><Strong>Response</Strong></td>
					<td style="width: 21%; text-align: center;"><Strong>Execution Trace Tree</Strong></td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<br><br>
	</div>
	<div id="divTableOLTL" style="display: none;">
		<div>
			<Strong> OL Transaction Log </Strong>
			<span onclick="toggleTable($('#tableOLTLSlider'),this);" class="glyphicon glyphicon-minus-sign"></span>
			<span onclick="copyTable($('#tableOLTL'),this);" class="glyphicon glyphicon-file"></span>
		</div>
		<div id="tableOLTLSlider">
		<table id="tableOLTL" class="table table-bordered" >
			<thead>
				<tr>
					<td style="text-align: center;"><Strong>QR</Strong></td>
					<td style="text-align: center;"><Strong>SERVICE_ID</Strong></td>
					<td style="text-align: center;"><Strong>ACTIVITY_TYPE</Strong></td>
					<td style="text-align: center;"><Strong>CREATE_DATE</Strong></td>
					<td style="text-align: center;"><Strong>SERVICE</Strong></td>
					<td style="text-align: center;"><Strong>TERMINAL_TYPE</Strong></td>
					<td style="text-align: center;"><Strong>MSG</Strong></td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</div>
		<br><br>
	</div>
</body>
</html>