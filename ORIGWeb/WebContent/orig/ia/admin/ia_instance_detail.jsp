	<div id="adm-view-instance">
		<form name="adm_v_instance_form" id="adm_v_instance_form">
			<button type="button" id="search-instance">Search</button>
			<label>InstanceId : </label><input type="text" name="search_instance" id="search_instance" value="" /> 
			<div><pre id="adm-instance-canvas"></pre></div>
		</form>
	</div>
	<script>
	$('#search-instance').click(function() {
		getInstanceDetail();
	});
	function getInstanceDetail() {
		showProgress();
		disableSearch();
		$.ajax({
			url : "/ORIGWeb/bpm/instance/detail",
			type : "GET",
			data : $('#adm-view-instance *').serialize(),
			success : function(data) {
				$('#adm-instance-canvas').text(getPrettyJson(data));
			},
			error : function(xhr, status, error) {
				$("#adm-instance-canvas").text("Search Inbox! " + xhr.responseText).show("fast");
			},
			complete : function(data) {
				enableSearch();
				hideProgress();
			}
		});
	}
	</script>