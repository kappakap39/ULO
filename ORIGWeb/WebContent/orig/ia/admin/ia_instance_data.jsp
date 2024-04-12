	<div id="adm-view-instance-data">
		<form name="adm_v_instance_data_form" id="adm_v_instance_data_form">
			<button type="button" id="search-instancedata">Search</button>
			<label>InstanceId : </label><input type="text" name="search_instance_data" id="search_instance_data" value="" /> 
			<div><pre id="adm-instance-data-canvas"></pre></div>
		</form>
	</div>
	<script>
	$('#search-instancedata').click(function() {
		getInstanceData();
	});
	function getInstanceData() {
		showProgress();
		disableSearch();
		$.ajax({
			url : "/ORIGWeb/bpm/instance/data",
			type : "GET",
			data : $('#adm-view-instance-data *').serialize(),
			success : function(data) {
				$('#adm-instance-data-canvas').text(getPrettyJson(data));
			},
			error : function(xhr, status, error) {
				$("#adm-instance-data-canvas").text("Search Inbox! " + xhr.responseText).show("fast");
			},
			complete : function(data) {
				enableSearch();
				hideProgress();
			}
		});
	}
	</script>