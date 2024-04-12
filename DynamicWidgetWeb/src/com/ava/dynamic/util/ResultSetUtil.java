package com.ava.dynamic.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.ava.dynamic.model.widget.Chart;
import com.ava.dynamic.model.widget.ChartResultSet;
import com.ava.dynamic.model.widget.Series;

public class ResultSetUtil {
	static final transient Logger log = LogManager.getLogger(ResultSetUtil.class);
	public static Integer getInteger(ResultSet rs, String column) throws SQLException{
		if(rs == null){
			return null;
		}
		Integer result = rs.getInt(column);
		if(rs.wasNull()){
			result = null;
		}
		return result;
	}
	public static Long getLong(ResultSet rs, String column) throws SQLException{
		if(rs == null){
			return null;
		}
		Long result = rs.getLong(column);
		if(rs.wasNull()){
			result = null;
		}
		return result;
	}
	
	public static void mapChartData(List<ChartResultSet> chartResultSet, Chart chart){
		if(chartResultSet == null || chartResultSet.isEmpty()){
			log.error("Either result set information is null or empty, result = "+chartResultSet);
		}
		if(chart == null){
			log.error("Given chart information is null, chart = "+chart);
			return;
		}
		List<String> cateList = chart.getCategories();
		List<Series> series = chart.getSeries();
		
		Set<String> cateSet = new LinkedHashSet<String>();
		Map<String, List<Double>> serieMap = new HashMap<String, List<Double>>();
		for(ChartResultSet ele : chartResultSet){
			if(ele.getCategory() == null || ele.getCategory().isEmpty()){
				continue;
			}
			cateSet.add(ele.getCategory());
			
			if(ele.getSeriesName() == null || ele.getSeriesName().isEmpty()){
				ele.setSeriesName("Uncategorized");
			}
			
			List<Double> dataList = serieMap.get(ele.getSeriesName());
			if(dataList == null){
				dataList = new ArrayList<Double>();
			}
			dataList.add(ele.getValue());
			serieMap.put(ele.getSeriesName(), dataList);
		}
		cateList.addAll(cateSet);
		
		for(Map.Entry<String, List<Double>> ele : serieMap.entrySet()){
			Series serie = new Series();
			List<Double> dataList = ele.getValue();			
			serie.setName(ele.getKey());
			serie.setData(dataList);
			series.add(serie);
		}
	}
}
