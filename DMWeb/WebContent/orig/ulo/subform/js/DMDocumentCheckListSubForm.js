$(document).ready(function(){
	CHECK_BOX_INCOMPLETEActionJS();
});


function CHECK_BOX_INCOMPLETEActionJS(elementId,mode,fieldName,object){
	if(!isIncomplete()){
		if(FLAG_YES!=$('[name="DM_IS_DOC_COMPLETE"]').val()){
			$('[name="DM_IS_DOC_COMPLETE"]').val(FLAG_YES);
			$('#LabelField_DM_FOLDER_NO').html($('#LabelField_DM_FOLDER_NO').text().replace(':','<span class="text-danger">*</span>:'));
			$('#LabelField_DM_BOX_NO').html($('#LabelField_DM_BOX_NO').text().replace(':','<span class="text-danger">*</span>:'));
		}
	}else{
		if(FLAG_NO!=$('[name="DM_IS_DOC_COMPLETE"]').val()){
			$('[name="DM_IS_DOC_COMPLETE"]').val(FLAG_NO);
			$('#LabelField_DM_FOLDER_NO').find('.text-danger').remove();
			$('#LabelField_DM_BOX_NO').find('.text-danger').remove();
		}
	}
}

function isIncomplete(){
	var isIncomplete =false;
	$('.table #DM_DOC_CHECKLIST').each(function(index){
		if($(this).find('input:checked').is(':checked')){
			isIncomplete = true;
		}
		
	});
	return isIncomplete;
}