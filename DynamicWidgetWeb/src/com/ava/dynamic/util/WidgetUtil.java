package com.ava.dynamic.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ava.dynamic.config.WidgetConstant;
import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.widget.Chart;
import com.ava.dynamic.model.widget.Series;
import com.ava.dynamic.model.widget.StatBox;
import com.ava.dynamic.service.ChartService;
import com.ava.dynamic.service.StatBoxService;
import com.ava.dynamic.service.TeamPerformanceService;

public class WidgetUtil {
	
	@Autowired
	private TeamPerformanceService teamPerformanceService;
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
		else if(WidgetConstant.WidgetCode.MULTI_CIRCLE_CHART.equals(type)){
			returnChart = ChartService.getMultiCirclePrototype(chart, id);
		}
		else if(WidgetConstant.WidgetCode.STACKED_BAR.equals(type)){
			returnChart = ChartService.getStackedBarPrototype(chart, id);
		}
		else if(WidgetConstant.WidgetCode.TEXT_AND_NUMBER_BUTTON.equals(type)){
			returnChart = ChartService.getTextAndNumberButtonPrototype(chart, id);
		}
		else if(WidgetConstant.WidgetCode.TEXT_AND_TEXT_BUTTON.equals(type)){
			returnChart = ChartService.getTextAndTextButtonPrototype(chart, id);
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
	
	public static StatBox constructStatBoxPrototype(StatBox box){
		if(box == null){
			return null;
		}
		String type = box.getTypeCode();
//		Long id = box.getId();
		
		if(WidgetConstant.WidgetCode.SINGLE_STATISTICS_BOX.equals(type)){
			box.setDataList(StatBoxService.getStatBoxDataPrototype(6, 7000d, null));
		}
		else if(WidgetConstant.WidgetCode.DOUBLE_STATISTICS_BOX.equals(type)){
			String[] desc = {"Point per day", "Point per week"};
			box.setDataList(StatBoxService.getStatBoxDataPrototype(2, 20000d, desc));
		}
		
		return box;
	}
	
//	private static List<Double> getRandom(int size, int max){
//		List<Double> list = new ArrayList<Double>();
//		for(int i = 0; i < size; i++){
//			double maxd = (double) max;
//			double ran = Math.random();
//			double ele = (int)(Math.round(maxd * ran * 100.0)/100.0);
//			list.add(ele);
//		}
//		return list;
//	}
	
	public static void setWidgetDataLoadingCriteria(Widget widget, String owner, String positionLevel, String teamId, boolean force){
		if(widget == null){
			return;
		}
		if(force){
			widget.setOwner(owner);
			widget.setPositionLevel(positionLevel);
			widget.setTeamId(teamId);
		}else{
			if(widget.getOwner() == null){
				widget.setOwner(owner);
			}
			if(widget.getPositionLevel() == null){
				widget.setPositionLevel(positionLevel);
			}
			if(widget.getTeamId() == null){
				widget.setTeamId(teamId);
			}
		}
	}

}
