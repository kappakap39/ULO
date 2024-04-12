package com.ava.dynamic.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ava.dynamic.config.WidgetConstant;
import com.ava.dynamic.model.grid.Grid;
import com.ava.dynamic.model.grid.GridItem;

@Repository
public class GridDAOImp implements GridDAO{
	static final transient Logger log = LogManager.getLogger(GridDAOImp.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertGrid;
	@Autowired
	private SeqDAO generator;
	
	@Autowired
	private GridItemDAO gridItemDAO;
	
	@Autowired
	public GridDAOImp(@Value("#{widgetDatasource}")DataSource widgetDatasource){//Refer to DatabaseConfig.java
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(widgetDatasource);
		insertGrid = new SimpleJdbcInsert(widgetDatasource).withTableName("GRID");
	}
	
//	public Grid getGridById(Long id){
//		if(id==null){
//			return null;
//		}
//		
//		String sql = "SELECT ID AS id, CODE AS code, TITLE AS title FROM GRID WHERE ID = :id";
//		return null;
//	}
	
	@Transactional
	@Override
	public int saveGrid(Grid grid) throws Exception{
		if(grid == null){
			return 0;
		}
		Long id = grid.getId();
		int result = 0;
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(grid);
		parameterSource.registerSqlType("displayMenu", java.sql.Types.BIT);
		parameterSource.registerSqlType("dynamicItems", java.sql.Types.BIT);
		if(id == null || id == 0){
			Long seq = generator.getNextVal(WidgetConstant.SEQ.GRID_ID);
			grid.setId(seq);
			result = insertGrid.execute(parameterSource);
		}
		else{
//			String updateSql = "UPDATE GRID SET CODE = :code, TITLE = :title, POSITION_LEVEL = :positionLevel WHERE ID = :id";
//			result = namedParameterJdbcTemplate.update(updateSql, parameterSource);
		}
		
//		if(result == 0){
//			throw new SQLException();
//		}
		
		List<GridItem> items = grid.getItems();
		if(items != null && !items.isEmpty()){
			Long gridId = grid.getId();
			int size = items.size();
			for(int i = 0; i < size; i++){
				GridItem item = items.get(i);
				item.setSeq(i+1);
				item.setGridId(gridId);
			}
		}
		
		//Start saving children
		if(items == null || items.isEmpty()){
			gridItemDAO.deleteItemsByGridId(id);
		}else{
			gridItemDAO.saveGridItem(items);
		}
		
		return result;
	}
	
	@Override
	public int saveOnlyGrid(Grid grid) throws Exception{
		if(grid == null){
			return 0;
		}
		Long id = grid.getId();
		int result = 0;
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(grid);
		parameterSource.registerSqlType("displayMenu", java.sql.Types.BIT);
		parameterSource.registerSqlType("dynamicItems", java.sql.Types.BIT);
		if(id == null || id == 0){
			Long seq = generator.getNextVal(WidgetConstant.SEQ.GRID_ID);
			grid.setId(seq);
			result = insertGrid.execute(parameterSource);
		}
		else{
			String updateSql = "UPDATE GRID SET CODE = :code, TITLE = :title, POSITION_LEVEL = :positionLevel WHERE ID = :id";
			result = namedParameterJdbcTemplate.update(updateSql, parameterSource);
		}
		return result;
	}
	
	@Override
	public int deleteGridById(Long id) throws Exception{
		if(id == null){
			return 0;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", String.valueOf(id));
		String deleteSql = "DELETE FROM GRID WHERE ID = :id";
		return namedParameterJdbcTemplate.update(deleteSql, params);
	}
}
