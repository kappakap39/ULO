package com.ava.dynamic.repo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.ava.dynamic.config.WidgetConstant;
import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.widget.Chart;
import com.ava.dynamic.model.widget.Series;
import com.ava.dynamic.model.widget.TeamPerformance;
import com.ava.dynamic.repo.TeamPerformanceDAO;
import com.ava.dynamic.repo.WidgetDAO;
import com.ava.dynamic.service.ChartService;
import com.ava.dynamic.util.ResultSetUtil;

public class WidgetMapper implements RowMapper<Widget>{
	private static final transient Logger log = LogManager.getLogger(WidgetMapper.class);
	@Autowired
	private TeamPerformanceDAO teamPerformanceDAO;
	@Autowired
	private ChartService chartService;
	@Autowired
	private WidgetDAO widgetDAO;
	
	@Override
	public Widget mapRow(ResultSet rs, int arg1) throws SQLException {
//		log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ START MAPPING @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		String widgetClass = rs.getString("CLASS");
//		log.debug("Current Class : "+widgetClass);
		Widget widget = null;
		try{
			widget = (Widget) Class.forName("com.ava.dynamic.model.widget."+widgetClass).newInstance();
//			log.debug("Instantiate Widget sucessfully!");
		}catch(Exception e){
			log.debug("!!!!!!!!!!!!!!!!!!!!! Fail to Instantiate Widget : "+e.getLocalizedMessage());
			throw new SQLException(e);
		}
		
		//Set common fields
		widget.setId(ResultSetUtil.getLong(rs, "ID"));
		widget.setTypeId(ResultSetUtil.getLong(rs,"TYPE_ID"));
		widget.setTypeCode(rs.getString("CODE"));
		widget.setTypeName(rs.getString("NAME"));
		widget.setTypeDesc(rs.getString("DESCRIPTION"));
		widget.setTypeClass(rs.getString("CLASS"));
		widget.setViewPath(rs.getString("VIEW_PATH"));
		widget.setFragment(rs.getString("FRAGMENT"));
		widget.setTitle(rs.getString("TITLE"));
		widget.setyAxisTitle(rs.getString("YAXIS_TITLE"));
		widget.setyAxisMin(ResultSetUtil.getInteger(rs, "YAXIS_MIN"));
		widget.setyAxisMax(ResultSetUtil.getInteger(rs, "YAXIS_MAX"));
		widget.setDataSuffix(rs.getString("DATA_SUFFIX"));
		widget.setToolTipSuffix(rs.getString("TOOLTIP_SUFFIX"));
		widget.setGridItemId(ResultSetUtil.getLong(rs, "GRID_ITEM_ID"));
		widget.setWidth(ResultSetUtil.getInteger(rs, "WIDTH"));
		widget.setHeight(ResultSetUtil.getInteger(rs, "HEIGHT"));
		widget.setCssClass(rs.getString("CSS_CLASS"));
		widget.setCustomStyle(rs.getString("CUSTOM_STYLE"));
		widget.setSeq(ResultSetUtil.getInteger(rs, "SEQ"));
		widget.setDhbType(rs.getString("DHB_TYPE"));
		widget.setIntervalTime(ResultSetUtil.getInteger(rs, "INTERVAL_TIME"));
		
//		log.debug("Widget detail : "+widget);
		
		//Set specific fields
		if(widget instanceof Chart){

		}
		else if(widget instanceof TeamPerformance){

		}		

		return widget;
	}
	
	/**For unit testing purpose only
	 * 
	 */
	public static Chart constructChartPrototype(Chart chart){
		if(chart == null || chart.getTypeCode()==null){
			return null;
		}
		Chart returnChart = null;
		String type = chart.getTypeCode();
		Long id = chart.getId();
		if(WidgetConstant.WidgetCode.SOLID_GAUGE_CHART.equals(type)){
			returnChart = ChartService.getGuagePrototype(chart,id);
		}
		else if(WidgetConstant.WidgetCode.BAR_CHART.equals(type)){
			returnChart = ChartService.getBarPrototype(chart,id);
		}
		else if(WidgetConstant.WidgetCode.BASIC_LINE_CHART.equals(type)){
			returnChart = ChartService.getLineChartPrototype(chart,id);
		}
		else if(WidgetConstant.WidgetCode.CIRCLE_CHART.equals(type)){
			returnChart = ChartService.getCirclePrototype(chart, id);
		}
		else if(WidgetConstant.WidgetCode.SINGLE_STATISTICS_BOX.equals(type)){
			returnChart = chart;
		}
		else if(WidgetConstant.WidgetCode.DOUBLE_STATISTICS_BOX.equals(type)){
			returnChart = getChart(chart);
		}
		else if(WidgetConstant.WidgetCode.STACKED_BAR.equals(type)){
			returnChart = getChart(chart);
		}
		else if(WidgetConstant.WidgetCode.TEXT_AND_BOX.equals(type)){
			List<String> lists = new ArrayList<String>();
			lists.add("Urget app (D+1)");
			lists.add("Urgent app (D+2)");
			chart.setCategories(lists);
			Series series = new Series();
			series.getData().add((double)503);
			series.setName("Urget app (D+1)");
			Series series2 = new Series();
			series2.getData().add((double)933);
			series2.setName("Urgent app (D+2)");
			chart.getSeries().add(series);
			chart.getSeries().add(series2);
			returnChart = chart;
		}
		
		
		return returnChart;
	}
	
	private static List<Double> getRandom(int size, int max){
		List<Double> list = new ArrayList<Double>();
		for(int i = 0; i < size; i++){
			double maxd = (double) max;
			double ran = Math.random();
			double ele = (int)(Math.round(maxd * ran * 100.0)/100.0);
			list.add(ele);
		}
		return list;
	}
	
	public static Chart getChart(Chart chart) {
		if(chart==null)chart = new Chart();
		List<String> categories = new ArrayList<String>();
		List<Series> series = new ArrayList<Series>();
		chart.setTitle("Demographic Graph");
		
		Series serie1 = new Series();
		Series serie2 = new Series();
		Series serie3 = new Series();
		serie1.setName("OLA");
		serie2.setName("SLA");
		serie3.setName("Target");
		series.add(serie1);
		series.add(serie2);
		series.add(serie3);
		int max = 10;
		for(int i=0; i < max; i++){
			categories.add("ABC"+i);
			serie1.setData(getRandom(max, 20));
			serie2.setData(getRandom(max, 20));
			serie3.setData(getRandom(max, 20));

		}
		chart.setCategories(categories);
		chart.setSeries(series);
		return chart;
	}
	

}
