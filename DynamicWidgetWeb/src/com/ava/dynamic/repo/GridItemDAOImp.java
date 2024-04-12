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

import com.ava.dynamic.config.WidgetConstant;
import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.grid.GridItem;

@Repository
public class GridItemDAOImp implements GridItemDAO {
	static final transient Logger log = LogManager.getLogger(GridItemDAOImp.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private NamedParameterJdbcTemplate uloNamedParameterJdbcTemplate;
	private SimpleJdbcInsert insertGridItem;
	@Autowired
	private SeqDAO generator;
	@Autowired
	private WidgetDAO widgetDAO;

	private static final String updateSQL = "UPDATE GRID_ITEM SET " + "POS_X = :posX, POS_Y = :posY, WIDTH = :width, HEIGHT = :height, TITLE = :title,"
			+ " SEQ = :seq, GRID_ID = :gridId, HEADER_ICON = :headerIcon, FOOTER = :footer, MIN_WIDTH = :minWidth,"
			+ " MAX_WIDTH = :maxWidth, MIN_HEIGHT = :minHeight, MAX_HEIGHT = :maxHeight, NO_RESIZE = :noResize, NO_MOVE = :noMove," + " WIDGET_ALIGNMENT = :widgetAlignment ";

	@Autowired
	public GridItemDAOImp(@Value("#{widgetDatasource}") DataSource widgetDatasource, @Value("#{uloDatasource}") DataSource uloDatasource) {// Refer to DatabaseConfig.java
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(widgetDatasource);
		uloNamedParameterJdbcTemplate = new NamedParameterJdbcTemplate(uloDatasource);
		insertGridItem = new SimpleJdbcInsert(widgetDatasource).withTableName("GRID_ITEM");
	}

	@Override
	public void saveGridItem(List<GridItem> items) throws Exception {
		List<GridItem> updateList = new ArrayList<GridItem>();
		List<GridItem> insertList = new ArrayList<GridItem>();
		List<Long> ids = new ArrayList<Long>();
		List<BeanPropertySqlParameterSource> insertParams = new ArrayList<BeanPropertySqlParameterSource>();
		List<BeanPropertySqlParameterSource> updateParams = new ArrayList<BeanPropertySqlParameterSource>();

		for (GridItem item : items) {
			BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(item);
			params.registerSqlType("noResize", java.sql.Types.BIT);
			params.registerSqlType("noMove", java.sql.Types.BIT);

			if (item.getId() == null || item.getId() == 0L) {
				Long id = generator.getNextVal(WidgetConstant.SEQ.GRID_ITEM_ID);
				item.setId(id);

				insertList.add(item);
				insertParams.add(params);
			} else {
				updateList.add(item);
				updateParams.add(params);
			}
			ids.add(item.getId());
		}

		if (!insertList.isEmpty()) {
			log.debug("Insert list : " + insertList.toString());
			int[] rows = insertGridItem.executeBatch(insertParams.toArray(new BeanPropertySqlParameterSource[insertParams.size()]));
			log.debug("Inserted rows = " + (rows == null ? null : rows.toString()));
		}
		if (!updateList.isEmpty()) {
			log.debug("Update list : " + updateList.toString());
			String sql = updateSQL + " WHERE ID = :id";
			int[] rows = namedParameterJdbcTemplate.batchUpdate(sql, updateParams.toArray(new BeanPropertySqlParameterSource[updateParams.size()]));
			log.debug("Updated rows = " + (rows == null ? null : rows.toString()));
		}

		Long gridId = items.get(0).getGridId();
		deleteExceedGridItem(gridId, ids);

		// Save children
		saveWidgetOfGridItem(items);
	}

	private void saveWidgetOfGridItem(List<GridItem> items) throws Exception {
		if (items == null || items.isEmpty()) {
			return;
		}

		for (GridItem item : items) {
			List<Widget> widgets = item.getWidgetList();
			setGridItemIdOfChildrenWidget(item.getId(), widgets);
			widgetDAO.saveWidgets(widgets);
		}
	}

	private void setGridItemIdOfChildrenWidget(Long gridItemId, List<Widget> widgets) {
		if (widgets == null || widgets.isEmpty()) {
			return;
		}
		for (Widget widget : widgets) {
			widget.setGridItemId(gridItemId);
		}
	}

	private int deleteExceedGridItem(Long gridId, List<Long> retainIds) {
		int result = 0;
		if (gridId == null || gridId == 0L || retainIds == null || retainIds.isEmpty()) {
			log.info("No item to delete, GridID = " + gridId + " , item id list = " + retainIds);
			return result;
		}

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM GRID_ITEM WHERE GRID_ID = :gridId AND ID NOT IN (");
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
		paramMap.put("gridId", gridId);
		log.debug("sql = " + sql.toString());
		result = namedParameterJdbcTemplate.update(sql.toString(), paramMap);
		return result;
	}

	@Override
	public int deleteItemsByGridId(Long gridId) {
		if (gridId == null) {
			return 0;
		}

		int result = 0;
		String sql = "DELETE FORM GRID_ITEM WHERE GRID_ID = :gridId";

		Map<String, Long> paramMap = new HashMap<String, Long>();
		paramMap.put("gridId", gridId);
		result = namedParameterJdbcTemplate.update(sql.toString(), paramMap);

		return result;
	}

	@Override
	public int countDynamicItemQuantity(String positionLevel, String username) {
		Map<String, String> cons = new HashMap<String, String>();
		cons.put("POSITION_LEVEL", positionLevel);
		cons.put("DHB_OWNER", username);
		String sql = "SELECT COUNT(*) NUM FROM (SELECT DHB_GROUP FROM DHB_SUMMARY_DATA S "
				+ "WHERE S.POSITION_LEVEL = :POSITION_LEVEL AND S.DHB_OWNER = :DHB_OWNER GROUP BY DHB_GROUP)";
		Integer result = uloNamedParameterJdbcTemplate.queryForObject(sql, cons, Integer.class);

		if (result == null) {
			return 0;
		} else {
			return result;
		}
	}

	@Override
	public String getDynamicItemHeader(String positionLevel, String username, String groupId) {
		Map<String, String> cons = new HashMap<String, String>();
		cons.put("POSITION_LEVEL", positionLevel);
		cons.put("DHB_OWNER", username);
		cons.put("DHB_GROUP", groupId);
		String sql = "SELECT S.DHB_GROUP FROM DHB_SUMMARY_DATA S WHERE S.POSITION_LEVEL = :POSITION_LEVEL AND S.DHB_OWNER = :DHB_OWNER AND S.DHB_GROUP = :DHB_GROUP AND S.DESCRIPTION4 IS NOT NULL AND ROWNUM = 1";
		String result = "";
		try {
			result = uloNamedParameterJdbcTemplate.queryForObject(sql, cons, String.class);
		} catch (Exception e) {
			log.error("Not able to find Header Title for Grid Item : " + groupId);
		}

		return result;
	}
	
	@Override
	public List<GridItem> getDynamicGridItemByTeamId(String teamId, String positionId){
		if(teamId == null){
			return null;
		}
		String sql = "SELECT T.TEAM_ID, T.TEAM_NAME TITLE, (SELECT U.USER_ID FROM MS_USER_TEAM U WHERE T.TEAM_ID = U.TEAM_ID AND U.POSITION_ID = :position_id AND ROWNUM = 1) OWNER FROM MS_TEAM T WHERE T.UNDER = :team_id";
		Map<String, String> params = new HashMap<String, String>();
		params.put("position_id", positionId);
		params.put("team_id", teamId);
		List<GridItem> result = null;
		try{
			result = uloNamedParameterJdbcTemplate.query(sql.toString(), params, BeanPropertyRowMapper.newInstance(GridItem.class));
		}catch(Exception e){
			log.error("Not able to find Dynamic Grid item, reanson : " + e.getLocalizedMessage());
		}
		return result;
	}
}
