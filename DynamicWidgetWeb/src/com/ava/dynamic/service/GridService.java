package com.ava.dynamic.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.ava.dynamic.config.WidgetConfig;
import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.grid.Grid;
import com.ava.dynamic.model.grid.GridItem;
import com.ava.dynamic.model.widget.Chart;
import com.ava.dynamic.model.widget.Series;
import com.ava.dynamic.model.widget.StatBox;
import com.ava.dynamic.model.widget.StatBoxData;
import com.ava.dynamic.model.widget.TeamPerformance;
import com.ava.dynamic.model.widget.TeamPerformanceDetail;
import com.ava.dynamic.repo.GridDAO;
import com.ava.dynamic.repo.UtilityDAO;
import com.ava.dynamic.repo.WidgetDAO;
import com.ava.dynamic.util.WidgetUtil;

@Service
public class GridService {
	static final transient Logger log = LogManager.getLogger(GridService.class);
	@Autowired
	private GridDAO gridDAO;
	@Autowired
	private WidgetDAO widgetDAO;
	@Resource(name="widgetDatasource")
	private DataSource widgetDatasource;
	@Autowired
	private TeamPerformanceService teamPerformanceService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private GridItemService gridItemService;
	@Autowired
	private UtilityDAO utilityDAO;
	
	public Grid parseGridJson(String json) throws Exception{
		if(json == null){
			throw new IllegalStateException("JSON is null!");
		}
		
		
		return null;
	}
	
	public int saveGrid(Grid grid) {
		if(grid==null){
			return 0;
		}
		
		int result = 0;
		try {
			result = gridDAO.saveGrid(grid);
		} catch (Exception e) {
			log.error("Save grid error : "+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	public int saveOnlyGrid(Grid grid) throws Exception{
		if(grid==null){
			return 0;
		}
		
		int result = 0;
		try {
			result = gridDAO.saveOnlyGrid(grid);
		} catch (Exception e) {
			log.error("Save grid error : "+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	public void refreshGridCache(){
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(widgetDatasource);
		WidgetConfig.loadGridCache(namedParameterJdbcTemplate);
	}
	
	public void loanGridData(Grid grid, boolean prototypeMode, String username, String teamId){
		if(grid == null || grid.getItems() == null || grid.getItems().isEmpty()){
			return;
		}
		
		//Prepare Dynamic Items
		log.debug("Grid dynamic Item Flag : "+grid.getDynamicItems());
		if(grid.getDynamicItems()){
			gridItemService.constructDynamicGridItemByTeamId(grid, teamId);
		}
		
		//Prepare All Widgets in grid
		List<GridItem> items = grid.getItems();
		List<Widget> widgets = new ArrayList<Widget>();
		for(GridItem item : items){
			if(item.getWidgetList() == null){
				continue;
			}
			widgets.addAll(item.getWidgetList());
		}
		
		if(widgets.isEmpty()){
			return;
		}
		
		//Start Logic
		if(prototypeMode){
			for(Widget widget : widgets){
				loadWidgetPrototype(widget, widget.getTypeId());
			}
		}else{
			for(Widget widget : widgets){
				WidgetUtil.setWidgetDataLoadingCriteria(widget, username, grid.getPositionLevel(), teamId, false);
//				widgetDAO.loadWidgetData(widget, username, teamId, grid);
				widgetDAO.loadWidgetData(widget);
			}
			try {
				menuService.loadGridMenu(grid, username, teamId);
			} catch (Exception e) {
				log.error("Error occurs while loading Grid's menu ",e);
				e.printStackTrace();
			}
		}
	}
	
	public void loadWidgetPrototype(Widget widget, Long type){
		if(widget==null){
			log.error("Cannot load Widget data, widget is null!");
		}
		if(type == null || type == 0L){
			log.error("Cannot load Widget data, widget type is not specified!");
		}
		
		if(widget instanceof Chart){
			Chart chart = (Chart) widget;
			WidgetUtil.constructChartPrototype(chart);
		}
		else if(widget instanceof TeamPerformance){
			TeamPerformance team = (TeamPerformance) widget;
			team.setTeamPerformanceDetailList(teamPerformanceService.getTeamPerformance());
		}
		else if(widget instanceof StatBox){
			StatBox box = (StatBox)widget;
			WidgetUtil.constructStatBoxPrototype(box);
		}
	}
	
	public int deleteGrid(Long id) throws Exception{
		return gridDAO.deleteGridById(id);
	}
	
	public Widget updateWidgetData(Widget widget){
		if(widget == null)return widget;
		
		//Clear chart data
		if(widget instanceof Chart){
			Chart chart = (Chart) widget;
			List<Series> series = chart.getSeries();
			if(series != null && !series.isEmpty()){
				series.clear();
			}
		}
		
		//Clear teamPerformance data
		if(widget instanceof TeamPerformance){
			TeamPerformance team = (TeamPerformance) widget;
			List<TeamPerformanceDetail> list = team.getTeamPerformanceDetailList();
			if(list != null && !list.isEmpty()){
				list.clear();
			}
		}
		
		//Clear statBox data
		if(widget instanceof StatBox){
			StatBox team = (StatBox) widget;
			List<StatBoxData> list = team.getDataList();
			if(list != null && !list.isEmpty()){
				list.clear();
			}
		}

		widgetDAO.loadWidgetData(widget);
		return widget;
	}
}
