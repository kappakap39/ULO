package com.ava.dynamic.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.dynamic.model.widget.Chart;
import com.ava.dynamic.model.widget.Series;

@Repository
public class ChartDAOImp implements ChartDAO {
	static final transient Logger log = LogManager.getLogger(ChartDAOImp.class);
	@Autowired
	private DataSource widgetDatasource;
	@Override
	public Chart getChart() {
		Chart chart = new Chart();
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
		try {
			ResultSet rs = widgetDatasource.getConnection().prepareStatement("SELECT 1 AS COL1 FROM DUAL").executeQuery();
			rs.next();
			log.debug("Result from DB = "+rs.getInt("COL1"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.debug("######################### Log4j");
		return chart;
	}
	
	private List<Double> getRandom(int size, int max){
		List<Double> list = new ArrayList<Double>();
		for(int i = 0; i < size; i++){
			double maxd = (double) max;
			double ran = Math.random();
			double ele = Math.round(maxd * ran * 100.0)/100.0;
			list.add(ele);
		}
		return list;
	}
	
}
