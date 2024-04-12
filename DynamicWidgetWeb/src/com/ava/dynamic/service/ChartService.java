package com.ava.dynamic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.dynamic.model.widget.Chart;
import com.ava.dynamic.model.widget.ChartResultSet;
import com.ava.dynamic.model.widget.Series;
import com.ava.dynamic.repo.ChartDAO;

@Service
public class ChartService {
	static final transient Logger log = LogManager.getLogger(ChartService.class);

	@Autowired
	private ChartDAO chartDAO;

	public Chart getChart() {
		Chart chart = chartDAO.getChart();
		return chart;
	}

	public static Chart getGuagePrototype(Chart chart, Long id) {
		Chart solidGuage = null;
		if (chart == null) {
			solidGuage = new Chart();
			solidGuage.setViewPath("fragments/wg_gauge");
			solidGuage.setFragment("wg-solid-gauge");
		}else{
			solidGuage = chart;
		}
		Series series = new Series();
		series.getData().add(Math.random()*200d);
		series.setName("Hourly");
		solidGuage.setId(id);
		solidGuage.setToolTipSuffix("(Hourly)");
		solidGuage.getSeries().add(series);
		solidGuage.setyAxisMin(0);
		solidGuage.setyAxisMax(solidGuage.getyAxisMax()==null?200:solidGuage.getyAxisMax());
		return solidGuage;
	}
	public static Chart getTextAndNumberButtonPrototype(Chart chart, Long id) {
		Chart textNButton = null;
		if (chart == null) {
			textNButton = new Chart();
			textNButton.setViewPath("fragments/wg_stat_box");
			textNButton.setFragment("text-n-button");
		}else{
			textNButton = chart;
		}
		String[] series = {"Default"};
		String[] cates = {"Urgent app (D+1)", "Urgent app (D+2)"};
		List<ChartResultSet> resultSet = randomResult(2, 1000,cates,series);
		log.debug("ResultSet = " + resultSet.toString());
		mapChartData(resultSet, textNButton);
		return textNButton;
	}
	public static Chart getTextAndTextButtonPrototype(Chart chart, Long id) {
		Chart textNButton = null;
		if (chart == null) {
			textNButton = new Chart();
			textNButton.setViewPath("fragments/wg_stat_box");
			textNButton.setFragment("text-n-button");
		}else{
			textNButton = chart;
		}
		
		String[] series = {"Urgent","Normal"};
		String[] cates = {"IA","DE1.1","DE1.2","DV1","DV2","CA","DE2"};
		List<ChartResultSet> resultSet = randomResult(2, 1000,cates,series);
		log.debug("ResultSet = " + resultSet.toString());
		mapChartData(resultSet, textNButton);
		return textNButton;
	}

	public static Chart getBarPrototype(Chart chart, Long id) {
		Chart bar = null;
		if(chart == null){
			bar = new Chart();
			bar.setViewPath("fragments/wg_progress_bar");
			bar.setFragment("wg-bar");
		}else{
			bar = chart;
		}
		
		String[] series = {"%SLA Archievement","%OLA Archievement"};
		List<ChartResultSet> resultSet = randomResult(2, 100, null, series);
		log.debug("ResultSet = " + resultSet.toString());
		mapChartData(resultSet, bar);
		return bar;
	}

	public static Chart getUrgentAppPrototype(Long id) {
		Chart urgentApp = new Chart();
		Series series = new Series();
		series.getData().add((double) 503);
		series.setName("Urget app (D+1)");
		Series series2 = new Series();
		series2.getData().add((double) 933);
		series2.setName("Urgent app (D+2)");
		urgentApp.setId(id);
		urgentApp.setViewPath("fragments/wg_table_liked_box");
		urgentApp.setFragment("wg-single-stat-box");
		urgentApp.getSeries().add(series);
		urgentApp.getSeries().add(series2);
		return urgentApp;
	}
	
	public static Chart getSingleStatPrototype(Chart chart, Long id) {
		Chart singleStat = null;
		if(chart == null){
			singleStat =  new Chart();
		}else{
			singleStat = chart;
		}
		Series series = new Series();
		series.getData().add((double) 4000);
		series.getData().add((double) 6000);
		series.setName("Input");
		Series series2 = new Series();
		series2.getData().add((double) 933);
		series2.setName("Output");
		singleStat.setId(id);
		singleStat.setViewPath("fragments/wg_table_liked_box");
		singleStat.setFragment("wg-single-stat-box");
		singleStat.getSeries().add(series);
		singleStat.getSeries().add(series2);
		return singleStat;
	}
	
	public static Chart getDoubleStatPrototype(Chart chart, Long id) {
		Chart singleStat = null;
		if(chart == null){
			singleStat =  new Chart();
		}else{
			singleStat = chart;
		}
		Series series = new Series();
		series.getData().add((double) 4000);
		series.getData().add((double) 6000);
		series.setName("Input");
		Series series2 = new Series();
		series2.getData().add((double) 933);
		series2.setName("Output");
		singleStat.setId(id);
		singleStat.setViewPath("fragments/wg_table_liked_box");
		singleStat.setFragment("wg-single-stat-box");
		singleStat.getSeries().add(series);
		singleStat.getSeries().add(series2);
		return singleStat;
	}

	public static Chart getStackedBarPrototype(Chart chart, Long id) {
		Chart stackBar = null;
		if (chart == null) {
			stackBar = new Chart();
			stackBar.setViewPath("fragments/wg_stacked_bar");
			stackBar.setFragment("wg-stacked-bar");
		}else{
			stackBar = chart;
		}
		stackBar.setId(id);
		
		String[] series = {"Urgent","Normal"};
		String[] cates = {"IA","DE1.1","DE1.2","DV1","DV2","CA","DE2"};
		List<ChartResultSet> resultSet = randomResult(14, 70,cates,series);
		log.debug("ResultSet = " + resultSet.toString());
		mapChartData(resultSet, stackBar);

		return stackBar;
	}

	public static Chart getTextAndBoxPrototype(Long id) {
		Chart stackBar = new Chart();

		stackBar.setId(id);
		stackBar.setViewPath("fragments/wg_table_liked_box");
		stackBar.setFragment("wg-text-n-box");

		List<ChartResultSet> resultSet = randomResult(20, 100, null, null);
		log.debug("ResultSet = " + resultSet.toString());
		mapChartData(resultSet, stackBar);

		return stackBar;
	}

	public static Chart getCirclePrototype(Chart chart, Long id) {
		Chart circle = null;
		if(chart == null){
			circle = new Chart();
			circle.setViewPath("fragments/wg_circle");
			circle.setFragment("wg-circle");
		}else{
			circle = chart;
		}
		circle.setId(id);

		String[] cates = {"% Near miss"};
		List<ChartResultSet> resultSet = randomResult(1, 100, cates, null);
		log.debug("ResultSet = " + resultSet.toString());
		mapChartData(resultSet, circle);
		return circle;
	}
	
	public static Chart getMultiCirclePrototype(Chart chart, Long id) {
		Chart circle = null;
		if(chart == null){
			circle = new Chart();
			circle.setViewPath("fragments/wg_circle");
			circle.setFragment("wg-multi-circle");
		}else{
			circle = chart;
		}
		circle.setId(id);
		
		String[] cates = {"% Near miss", "% Miss"};
		String[] series = {"Default"};
		List<ChartResultSet> resultSet = randomResult(2, 100, cates, series);
		log.debug("ResultSet = " + resultSet.toString());
		mapChartData(resultSet, circle);
		return circle;
	}

	public static Chart getLineChartPrototype(Chart chart,Long id) {
		if(chart == null){
			chart = new Chart();
			chart.setViewPath("fragments/wg_line_chart");
			chart.setFragment("wg-basic-line");
		}
		chart.setId(id);
		
		String[] series = {"OLA", "SLA","Target"};
		List<ChartResultSet> resultSet = randomResult(21, 100, null, series);
		mapChartData(resultSet, chart);
		
		return chart;
	}

	private static List<ChartResultSet> randomResult(int quantity, double max, String[] cates, String[] series) {
		List<ChartResultSet> list = new ArrayList<ChartResultSet>();
		if (quantity < 0) {
			quantity = 1;
		} else if (quantity > 3) {
			quantity = 3;
		}
		String[] defaultCates = { "IA", "DE1.1", "DE1.2", "DV1", "DV2", "CA", "DE2" };
		String[] defaultSeries = { "Urgent", "Normal", "Target" };
		String[] defaultStack = {"In Queue", "Input", "Assign", "Output", "WIP_CC"};

		cates = (cates == null?defaultCates:cates);
		series = (series == null?defaultSeries:series);
		
		int maxLoop = cates.length * series.length;
		for (int i = 0; i < maxLoop; i++) {
			int cateIndex = i % cates.length;
			int seriIndex = i % series.length;
			double ranVal = (int) (Math.round((Math.random() * max * 100.0)) / 100.0);
			String currentCate = cates[cateIndex];
			String currentSerie = series[seriIndex];

			ChartResultSet result = new ChartResultSet();
			result.setCategory(currentCate);
			result.setSeriesName(currentSerie);
			result.setValue(ranVal);
			result.setStack(defaultStack[i % defaultStack.length]);

			list.add(result);
		}

		return list;
	}

	public static void mapChartData(List<ChartResultSet> resultSet, Chart chart) {
		if(resultSet == null || resultSet.isEmpty()){
			log.error("mapChartData() No result found from chart : "+chart);
			return;
		}
		List<String> cateList = chart.getCategories();
		List<Series> series = chart.getSeries();

		Set<String> cateSet = new LinkedHashSet<String>();
		Map<String, List<Double>> serieMap = new LinkedHashMap<String, List<Double>>();
		Map<String, String> serieStackMap = new HashMap<String, String>();
		for (ChartResultSet ele : resultSet) {
			//Prepare Category
			if (ele.getCategory() == null || ele.getCategory().isEmpty()) {
				continue;
			}
			cateSet.add(ele.getCategory());

			if (ele.getSeriesName() == null || ele.getSeriesName().isEmpty()) {
				ele.setSeriesName("Uncategorized");
			}
			
			//Prepare Series
			List<Double> dataList = serieMap.get(ele.getSeriesName());
			if (dataList == null) {
				dataList = new ArrayList<Double>();
			}
			dataList.add(ele.getValue());
			serieMap.put(ele.getSeriesName(), dataList);
			
			//Map Series and Stack
			if(ele.getStack() != null && !ele.getStack().isEmpty()){
				serieStackMap.put(ele.getSeriesName(), ele.getStack());
			}
		}
		cateList.addAll(cateSet);

		for (Map.Entry<String, List<Double>> ele : serieMap.entrySet()) {
			Series serie = new Series();
			List<Double> dataList = ele.getValue();
			serie.setName(ele.getKey());
			serie.setData(dataList);
			serie.setStack(serieStackMap.get(serie.getName()));
			series.add(serie);
		}
	}

}
