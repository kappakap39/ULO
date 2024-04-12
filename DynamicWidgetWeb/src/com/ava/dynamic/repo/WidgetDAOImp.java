package com.ava.dynamic.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ava.dynamic.config.WidgetConfig;
import com.ava.dynamic.config.WidgetConstant;
import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.grid.Grid;
import com.ava.dynamic.model.param.ReportParameter;
import com.ava.dynamic.model.widget.Chart;
import com.ava.dynamic.model.widget.ChartResultSet;
import com.ava.dynamic.model.widget.Series;
import com.ava.dynamic.model.widget.StatBox;
import com.ava.dynamic.model.widget.StatBoxData;
import com.ava.dynamic.model.widget.TeamPerformance;
import com.ava.dynamic.model.widget.TeamPerformanceDetail;
import com.ava.dynamic.repo.mapper.WidgetMapper;
import com.ava.dynamic.service.ChartService;

@Repository
public class WidgetDAOImp implements WidgetDAO {
	static final transient Logger log = LogManager.getLogger(WidgetDAOImp.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private NamedParameterJdbcTemplate uloNamedParameterJdbcTemplate;
	private SimpleJdbcInsert insertWidget;
	@Autowired
	private SeqDAO generator;
	@Autowired
	private TeamPerformanceDAO teamPerformanceDAO;
	@Autowired
	private WidgetConfig widgetConfig;

	private static final String updateSQL = "UPDATE WIDGET SET" + " ID = :id, TYPE_ID = :typeId, TITLE = :title, YAXIS_TITLE = :yAxisTitle,"
			+ " YAXIS_MIN = :yAxisMin, YAXIS_MAX = :yAxisMax, DATA_SUFFIX = :dataSuffix," + " TOOLTIP_SUFFIX = :toolTipSuffix, GRID_ITEM_ID = :gridItemId, WIDTH = :width,"
			+ " HEIGHT = :height, CSS_CLASS = :cssClass, CUSTOM_STYLE = :customStyle," + " SEQ = :seq, DHB_TYPE = :dhbType";

	@Autowired
	public WidgetDAOImp(@Value("#{widgetDatasource}") DataSource widgetDatasource, @Value("#{uloDatasource}") DataSource uloDatasource) {// Refer to DatabaseConfig.java
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(widgetDatasource);
		uloNamedParameterJdbcTemplate = new NamedParameterJdbcTemplate(uloDatasource);
		insertWidget = new SimpleJdbcInsert(widgetDatasource).withTableName("WIDGET");
	}

	private List<ChartResultSet> getChartDataByType(String dataTypeId, Map<String, String> conditions) {
		if (dataTypeId == null || dataTypeId.isEmpty()) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT NVL(VALUE1,0) AS VALUE, NVL(VALUE2,0) AS VALUE2, NVL(VALUE3,0) AS VALUE3, NVL(VALUE4,0) AS VALUE4,");
		sql.append(" NVL(DESCRIPTION1,'') AS category, NVL(DESCRIPTION2,'') AS seriesName, NVL(DESCRIPTION3,'') AS stack ");
		sql.append(" FROM DHB_SUMMARY_DATA WHERE DHB_TYPE = :data_type_id and STATUS = 'A' ");
		Map<String, String> params = new HashMap<String, String>();
		params.put("data_type_id", dataTypeId);
		if (conditions != null && !conditions.isEmpty()) {
			for (Map.Entry<String, String> entry : conditions.entrySet()) {
				String column = entry.getKey();
				String value = entry.getValue();

				sql.append(" AND " + column + " = " + value);
			}
		}
		sql.append(" ORDER BY DHP_SEQ, DESCRIPTION3 ");
		log.debug("DHB_TYPE = "+dataTypeId+", SQL = " + sql.toString());
		List<ChartResultSet> result = null;
		try {
			result = uloNamedParameterJdbcTemplate.query(sql.toString(), params, BeanPropertyRowMapper.newInstance(ChartResultSet.class));
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		log.debug("Result = " + result);
		return result;
	}
	
	public List<ChartResultSet> getChartDataByType(String dataTypeId, Map<String, String> conditions, String teamId, String teamPositionId) {
		if (dataTypeId == null || dataTypeId.isEmpty()) {
			return null;
		}
		if(teamId == null){
			return getChartDataByType(dataTypeId, conditions);
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT NVL(VALUE1,0) AS VALUE, NVL(VALUE2,0) AS VALUE2, NVL(VALUE3,0) AS VALUE3, NVL(VALUE4,0) AS VALUE4,");
		sql.append(" NVL(DESCRIPTION1,'') AS category, NVL(DESCRIPTION2,'') AS seriesName, NVL(DESCRIPTION3,'') AS stack ");
		sql.append(" FROM DHB_SUMMARY_DATA WHERE DHB_TYPE = :data_type_id ");
		sql.append(" AND DHB_OWNER = (SELECT USER_ID FROM MS_USER_TEAM WHERE TEAM_ID = :team_id AND POSITION_ID = :team_position_id AND ROWNUM = 1) ");
		Map<String, String> params = new HashMap<String, String>();
		params.put("data_type_id", dataTypeId);
		params.put("team_id", teamId);
		params.put("team_position_id", teamPositionId);
		if (conditions != null && !conditions.isEmpty()) {
			for (Map.Entry<String, String> entry : conditions.entrySet()) {
				String column = entry.getKey();
				String value = entry.getValue();
				
				sql.append(" AND " + column + " = " + value);
			}
		}
		sql.append(" ORDER BY DHB_GROUP, DHP_SEQ, DESCRIPTION3 ");
		log.debug("DHB_TYPE = "+dataTypeId+", SQL = " + sql.toString());
		List<ChartResultSet> result = null;
		try {
			result = uloNamedParameterJdbcTemplate.query(sql.toString(), params, BeanPropertyRowMapper.newInstance(ChartResultSet.class));
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		log.debug("Result = " + result);
		return result;
	}

	private List<StatBoxData> getStatBoxDataByType(String dataTypeId, Map<String, String> conditions) {
		if (dataTypeId == null || dataTypeId.isEmpty()) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT NVL(VALUE1,0) AS VALUE1, NVL(VALUE2,0) AS VALUE2, NVL(VALUE3,0) AS VALUE3, NVL(VALUE4,0) AS VALUE4,");
		sql.append(" NVL(DESCRIPTION1,'') AS firstBoxUnit, NVL(DESCRIPTION2,'') AS firstBoxUnitDesc, ");
		sql.append(" NVL(DESCRIPTION3,'') AS secondBoxUnit, NVL(DESCRIPTION4,'') AS secondBoxUnitDesc ");
		sql.append(" FROM DHB_SUMMARY_DATA WHERE DHB_TYPE = :data_type_id and STATUS = 'A' ");
		Map<String, String> params = new HashMap<String, String>();
		params.put("data_type_id", dataTypeId);
		if (conditions != null && !conditions.isEmpty()) {
			for (Map.Entry<String, String> entry : conditions.entrySet()) {
				String column = entry.getKey();
				String value = entry.getValue();

				sql.append(" AND " + column + " = " + value);
			}
		}
		sql.append(" ORDER BY DHB_GROUP, DHP_SEQ ");
		log.debug("DHB_TYPE = "+dataTypeId+", SQL = " + sql.toString());
		List<StatBoxData> result = null;
		try {
			result = uloNamedParameterJdbcTemplate.query(sql.toString(), params, BeanPropertyRowMapper.newInstance(StatBoxData.class));
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		log.debug("Result = " + result);
		return result;
	}
	
	public List<StatBoxData> getStatBoxDataByType(String dataTypeId, Map<String, String> conditions, String teamId, String teamPositionId) {
		if (dataTypeId == null || dataTypeId.isEmpty()) {
			return null;
		}
		if(teamId == null){
			return getStatBoxDataByType(dataTypeId, conditions);
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT NVL(VALUE1,0) AS VALUE1, NVL(VALUE2,0) AS VALUE2, NVL(VALUE3,0) AS VALUE3, NVL(VALUE4,0) AS VALUE4,");
		sql.append(" NVL(DESCRIPTION1,'') AS firstBoxUnit, NVL(DESCRIPTION2,'') AS firstBoxUnitDesc, ");
		sql.append(" NVL(DESCRIPTION3,'') AS secondBoxUnit, NVL(DESCRIPTION4,'') AS secondBoxUnitDesc ");
		sql.append(" FROM DHB_SUMMARY_DATA WHERE DHB_TYPE = :data_type_id and STATUS = 'A'");
		sql.append(" AND DHB_OWNER = (SELECT USER_ID FROM MS_USER_TEAM WHERE TEAM_ID = :team_id AND POSITION_ID = :team_position_id AND ROWNUM = 1) ");
		Map<String, String> params = new HashMap<String, String>();
		params.put("data_type_id", dataTypeId);
		params.put("team_id", teamId);
		params.put("team_position_id", teamPositionId);
		if (conditions != null && !conditions.isEmpty()) {
			for (Map.Entry<String, String> entry : conditions.entrySet()) {
				String column = entry.getKey();
				String value = entry.getValue();
				
				sql.append(" AND " + column + " = " + value);
			}
		}
		sql.append(" ORDER BY DHB_GROUP, DHP_SEQ ");
		log.debug("DHB_TYPE = "+dataTypeId+", SQL = " + sql.toString());
		List<StatBoxData> result = null;
		try {
			result = uloNamedParameterJdbcTemplate.query(sql.toString(), params, BeanPropertyRowMapper.newInstance(StatBoxData.class));
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		log.debug("Result = " + result);
		return result;
	}

	@Override
	public void loadWidgetData(Widget widget, String username, String teamId, Grid grid) throws IllegalStateException {
		if (widget == null) {
			log.error("Widget is null... terminating data loading");
			return;
		}
		if (grid == null) {
			log.error("Grid is null... terminating data loading");
			return;
		}

		if (widget instanceof Chart) {
			Map<String, String> conditions = new HashMap<String, String>();
			if(teamId == null)
				conditions.put("DHB_OWNER", "'" + username + "'");
			if (grid.getPositionLevel() != null)
				conditions.put("POSITION_LEVEL", "'" + grid.getPositionLevel() + "'");
			if (grid.getDynamicItems())
				conditions.put("DHB_GROUP", "'" + widget.getDynamicSeq() + "'");

			Chart chart = (Chart) widget;
			log.debug("loadWidgetData() chart Id : " + chart.getId() + " , Title : " + chart.getTitle());
			List<ChartResultSet> dataList = getChartDataByType(chart.getDhbType(), conditions, teamId, grid.getPositionLevel());
			log.debug("loadWidgetData() data : " + dataList);
			ChartService.mapChartData(dataList, chart);
		} else if (widget instanceof TeamPerformance) {
			TeamPerformance team = (TeamPerformance) widget;
			List<TeamPerformanceDetail> detail = teamPerformanceDAO.getTeamPerformance(teamId);
			team.setTeamPerformanceDetailList(detail);
		} else if (widget instanceof StatBox) {
			Map<String, String> conditions = new HashMap<String, String>();
			if(teamId == null)
				conditions.put("DHB_OWNER", "'" + username + "'");
			conditions.put("POSITION_LEVEL", "'" + grid.getPositionLevel() + "'");
			if (grid.getDynamicItems())
				conditions.put("DHB_GROUP", "'" + widget.getDynamicSeq() + "'");

			StatBox box = (StatBox) widget;
			List<StatBoxData> dataList = getStatBoxDataByType(box.getDhbType(), conditions, teamId, grid.getPositionLevel());
			box.setDataList(dataList);
		} else {
			log.debug("loadWidgetData() else : ");
		}

	}
	
	@Override
	public void loadWidgetData(Widget widget) throws IllegalStateException {
		if (widget == null) {
			log.error("Widget is null... terminating data loading");
			return;
		}
		
		String teamId = widget.getTeamId();
		String username = widget.getOwner();
		String positionLevel = widget.getPositionLevel();
		if(username == null || username.isEmpty()){
			log.warn("loadWidgetData () Unable to find Widget Owner Username! widget : "+widget);
		}
		if(teamId == null || teamId.isEmpty()){
			log.warn("loadWidgetData () Unable to find Widget Owner TeamID! widget : "+widget);
		}
		
		if (widget instanceof Chart) {
			Map<String, String> conditions = new HashMap<String, String>();
			conditions.put("DHB_OWNER", "'" + username + "'");
			if (widget.getPositionLevel() != null)
				conditions.put("POSITION_LEVEL", "'" + widget.getPositionLevel() + "'");
			
			Chart chart = (Chart) widget;
			log.debug("loadWidgetData() chart Id : " + chart.getId() + " , Name : " + chart.getTypeName());
			List<ChartResultSet> dataList = getChartDataByType(chart.getDhbType(), conditions);
			log.debug("loadWidgetData() data : " + dataList);
			ChartService.mapChartData(dataList, chart);
			decorateChart(chart);
		} else if (widget instanceof TeamPerformance) {
			TeamPerformance team = (TeamPerformance) widget;
			List<TeamPerformanceDetail> detail = teamPerformanceDAO.getTeamPerformance(teamId);
			team.setTeamPerformanceDetailList(detail);
		} else if (widget instanceof StatBox) {
			Map<String, String> conditions = new HashMap<String, String>();
			conditions.put("DHB_OWNER", "'" + username + "'");
			conditions.put("POSITION_LEVEL", "'" + positionLevel + "'");
			
			StatBox box = (StatBox) widget;
			log.debug("loadWidgetData() stat box Id : " + box.getId() + " , Name : " + box.getTypeName());
			List<StatBoxData> dataList = getStatBoxDataByType(box.getDhbType(), conditions);
			log.debug("loadWidgetData() data : " + dataList);
			box.setDataList(dataList);
		} else {
			log.debug("loadWidgetData() else : ");
		}
		
	}

	@Override
	public List<Widget> getWidgetByGridItemId(Long gridItemId) {
		if (gridItemId == null) {
			return null;
		}
		String widgetSql = WidgetConstant.SELECT_SQL + "WHERE GRID_ITEM_ID = " + gridItemId + " order by SELECT_SEQ, SEQ";

		List<Widget> widgets = namedParameterJdbcTemplate.query(widgetSql, new WidgetMapper());

		return widgets;
	}

	@Override
	public int saveWidgets(List<Widget> widgets) throws Exception {
		int result = 0;
		if (widgets == null || widgets.isEmpty()) {
			return 0;
		}

		List<Widget> updateList = new ArrayList<Widget>();
		List<Widget> insertList = new ArrayList<Widget>();
		List<Long> ids = new ArrayList<Long>();
		List<BeanPropertySqlParameterSource> insertParams = new ArrayList<BeanPropertySqlParameterSource>();
		List<BeanPropertySqlParameterSource> updateParams = new ArrayList<BeanPropertySqlParameterSource>();

		// Prepare data for batch process
		for (Widget widget : widgets) {
			BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(widget);

			if (widget.getId() == null || widget.getId() == 0L) {
				Long id = generator.getNextVal(WidgetConstant.SEQ.WIDGET_ID);
				widget.setId(id);

				insertList.add(widget);
				insertParams.add(params);
			} else {
				updateList.add(widget);
				updateParams.add(params);
			}
			ids.add(widget.getId());
		}

		if (!insertList.isEmpty()) {
			log.debug("Insert list : " + insertList.toString());
			int[] rows = insertWidget.executeBatch(insertParams.toArray(new BeanPropertySqlParameterSource[insertParams.size()]));
			log.debug("Inserted rows = " + (rows == null ? null : rows.toString()));
		}
		if (!updateList.isEmpty()) {
			log.debug("Update list : " + updateList.toString());
			String sql = updateSQL + " WHERE ID = :id";
			int[] rows = namedParameterJdbcTemplate.batchUpdate(sql, updateParams.toArray(new BeanPropertySqlParameterSource[updateParams.size()]));
			log.debug("Updated rows = " + (rows == null ? null : rows.toString()));
		}

		Long gridItemId = widgets.get(0).getGridItemId();
		deleteExceedWidget(gridItemId, ids);

		return result;
	}

	private int deleteExceedWidget(Long gridItemId, List<Long> retainIds) {
		int result = 0;
		if (gridItemId == null || gridItemId == 0L || retainIds == null || retainIds.isEmpty()) {
			log.info("No item to delete, GridItemID = " + gridItemId + " , widget id list = " + retainIds);
			return result;
		}

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM WIDGET WHERE GRID_ITEM_ID = :gridItemId AND ID NOT IN (");
		int size = retainIds.size();
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				sql.append(retainIds.get(i));
			} else {
				sql.append(", " + retainIds.get(i));
			}
		}
		sql.append(")");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("gridItemId", gridItemId);
		log.debug("sql = " + sql.toString());
		result = namedParameterJdbcTemplate.update(sql.toString(), paramMap);
		return result;
	}
	
	/**This is an odd way to decorate Widget. For temporarily use only!
	 * 
	 * @param chart
	 */
	private void decorateChart(Chart chart){
		if(chart == null)return;
		if(chart.getTypeId() == 16L){//Apply color to Progress bar by using value from Stack of Series
			ReportParameter param = getReportParam();
			List<Series> series = chart.getSeries();
			if(series != null && !series.isEmpty()){
				for(int i = 0, s = series.size(); i < s; i++){
					Series serie = series.get(i);
					if(serie.getData() != null && !serie.getData().isEmpty()){
						double data = serie.getData().get(0);
						if(i == 0){
							if(data < param.getPercentOLATargetLeg1()){
								serie.setStack("red");
							}else{
								serie.setStack("green");
							}
						}
						else if(i == 1){
							if(data < param.getPercentOLATargetLeg2()){
								serie.setStack("red");
							}else{
								serie.setStack("green");
							}
						}
						else if(i == 2){
							if(data < param.getPercentOLATargetLeg3()){
								serie.setStack("red");
							}else{
								serie.setStack("green");
							}
						}
						else if(i == 3){
							if(data < param.getPercentOLATargetLeg4()){
								serie.setStack("red");
							}else{
								serie.setStack("green");
							}
						}else{
							serie.setStack("green");
						}
					}						
				}
			}
		}
	}
	
	private ReportParameter getReportParam(){
		String sql = "SELECT (SELECT VALUE1 FROM REPORT_PARAM WHERE PARAM_ID = '3') percentOLATargetLeg1" +
				", (SELECT VALUE1 FROM REPORT_PARAM WHERE PARAM_ID = '4') percentOLATargetLeg2" +
				", (SELECT VALUE1 FROM REPORT_PARAM WHERE PARAM_ID = '5') percentOLATargetLeg3" +
				", (SELECT VALUE1 FROM REPORT_PARAM WHERE PARAM_ID = '6') percentOLATargetLeg4 FROM DUAL";
		Map<String, Object> paramMap = null;
		ReportParameter param = null;
		try{
			param = uloNamedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(ReportParameter.class));
		}catch(Exception e){
			log.error("getReportParam() Error "+e.getLocalizedMessage());
		}		
		return param;
	}

}
