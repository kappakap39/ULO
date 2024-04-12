(function($) {
	/*Put attribute 'data-dynamic-[data_type]="[index]"' to any elements that need to retrieve data from Chart object
	 * Author : TOP V1.0
	 */
	$.fn.dynamic = function(options) {
		var opts = $.extend({}, $.fn.dynamic.defaults, options),
		data = opts.series[0]?opts.series[0].data:null||[],
		cates = opts.categories||[],
		types = $.fn.dynamic.dataTypes,
		children;
		
		//Validation	
//		console.log(JSON.stringify(data, null, '\t'));
		
		//Series data type
		children = this.find('['+types.series+']');
		children.each(function(){
			var ele = $(this);
			var index = parseInt(ele.attr(types.series))||0;
			var val = data[Math.min(index,data.length-1)]+' '+(ele.attr(types.dataSuffix)||'');//Value + Suffix
			ele.html(val);
			if(ele.is(':input'))ele.val(val);
		});
		
		//Category data type
		children = this.find('['+types.categories+']');
		children.each(function(){
			var ele = $(this);
			var index = parseInt(ele.attr(types.categories))||0;
			var val = cates[Math.min(index,cates.length-1)]+' '+(ele.attr(types.dataSuffix)||'');//Value + Suffix
			ele.html(val);
			if(ele.is(':input'))ele.val(val);
		});
		return this;
	};
	
	$.fn.dynamic.defaults = {
		categories : [ 'Uncategorized' ],
		series : [ {
			name : 'Unnamed',
			data : [ 0.0 ],
			stack : 'Unstacked'
		} ],
		roundNumber : true,
		
	};
	$.fn.dynamic.dataTypes = {
		categories : 'data-dynamic-cate',
		series : 'data-dynamic-series',
		dataSuffix : 'data-dynamic-suffix'
	};
}(jQuery));