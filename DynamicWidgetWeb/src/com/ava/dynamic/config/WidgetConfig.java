package com.ava.dynamic.config;

import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.ava.dynamic.model.Widget;
import com.ava.dynamic.model.WidgetCache;
import com.ava.dynamic.model.WidgetProperties;
import com.ava.dynamic.model.WidgetType;
import com.ava.dynamic.model.grid.Grid;
import com.ava.dynamic.model.grid.GridItem;
import com.ava.dynamic.model.grid.Menu;
import com.ava.dynamic.model.grid.MenuItem;
import com.ava.dynamic.repo.mapper.WidgetMapper;
import com.ava.dynamic.util.JSONUtil;

@Configuration
public class WidgetConfig {
	static final transient Logger log = LogManager.getLogger(WidgetConfig.class);
	private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Value("#{widgetDatasource}")
    private DataSource widgetDatasource;
	@Autowired
	private WidgetProperties widgetProperties;
	@Autowired
	private WidgetCache widgetCache;
	public static final String WIDGET_PARENT_MAP_SQL = WidgetConstant.SELECT_SQL + " WHERE PARENT_ID IS NOT NULL order by SEQ";
	
	@Bean(name="widgetProperties")
	@Lazy
	public WidgetProperties initWidgetProperties() {
		
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(widgetDatasource);
		WidgetProperties widget = WidgetProperties.getInstance();
		String sql = "SELECT ID AS id, CODE AS code, NAME AS name, DESCRIPTION AS description, CLASS AS type, VIEW_PATH AS path, FRAGMENT AS fragment FROM CF_WIDGET_TYPE ORDER BY SELECT_SEQ";

		try {
			List<WidgetType> propList = namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(WidgetType.class));
			log.info("DB WidgetProperties : "+(propList==null?null:propList.toString()));
			widget.addProperty(propList);
			log.info("Bean WidgetProperties : "+WidgetProperties.propMap.toString());
			log.info("Bean WidgetProperties View Path : "+WidgetProperties.propPathMap.toString());
			log.info("Bean WidgetProperties View Fragment : "+WidgetProperties.propFragmentMap.toString());
			log.info("------------------------------- Initialize WidgetProperties Succesfully -------------------------------");
		} catch (Exception e) {
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Initialize WidgetProperties Failed !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			log.error(e.getLocalizedMessage());			
		}
		
		return widget;
	}
	
	@Bean(name="gridCache")
	@Lazy
	public WidgetCache generateGridCache(){
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(widgetDatasource);
		loadGridCache(namedParameterJdbcTemplate);
		
		if(WidgetCache.getAllGrid() == null){
			return null;
		}else{		
			log.info("------------------------------- Initialize WidgetCache Successfully -------------------------------");
			return WidgetCache.getInstance();
		}
		
	}
	
	public static void loadGridCache(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
		log.info("------------------------------- Initialize Grid WidgetCache -------------------------------");
		
		String widgetSql = WidgetConstant.SELECT_SQL + " order by SELECT_SEQ, SEQ";
		List<Widget> widgets = namedParameterJdbcTemplate.query(widgetSql, new WidgetMapper());
		log.debug("widgets size = "+(widgets==null?"null":widgets.size()));
		if(log.isDebugEnabled())
			log.debug("widgets = "+(widgets==null?"null":JSONUtil.toJson(widgets)));
		WidgetCache.constructWidgetOfGridItemCache(widgets);	
		
		
		log.info("------------------------------- Initialize Grid Item Cache -------------------------------");
		String gridItemSql = "SELECT * FROM GRID_ITEM ORDER BY SEQ";
		List<GridItem> items = namedParameterJdbcTemplate.query(gridItemSql, BeanPropertyRowMapper.newInstance(GridItem.class));
		if(log.isDebugEnabled())
			log.debug("GridItem = "+JSONUtil.toJson(items));
		WidgetCache.constructGridItemOfGridCache(items);
		log.info("------------------------------- Initialize Grid Item Successfully -------------------------------");
		
		log.info("------------------------------- Initialize Menu Item Cache -------------------------------");
		String menuItemSql = "SELECT * FROM MENU_ITEM ORDER BY PARENT_ID DESC, SEQ";
		List<MenuItem> menuItems = namedParameterJdbcTemplate.query(menuItemSql, BeanPropertyRowMapper.newInstance(MenuItem.class));
		if(log.isDebugEnabled())
			log.debug("menuItems = "+JSONUtil.toJson(menuItems));
		WidgetCache.constructMenuItemCache(menuItems);
		log.info("------------------------------- Initialize Menu Item Successfully -------------------------------");
		
		log.info("------------------------------- Initialize Menu Cache -------------------------------");
		String menuSql = "SELECT * FROM MENU";
		List<Menu> menus = namedParameterJdbcTemplate.query(menuSql, BeanPropertyRowMapper.newInstance(Menu.class));
		if(log.isDebugEnabled())
			log.debug("menus = "+JSONUtil.toJson(menus));
		WidgetCache.constructMenuCache(menus);
		log.info("------------------------------- Initialize Menu Successfully -------------------------------");
		
		
		log.info("------------------------------- Initialize Grid Cache -------------------------------");
		String gridSql = "SELECT * FROM GRID ORDER BY ID";
		List<Grid> grids = namedParameterJdbcTemplate.query(gridSql, BeanPropertyRowMapper.newInstance(Grid.class));
		if(log.isDebugEnabled())
			log.debug("Grid = "+JSONUtil.toJson(grids));
		WidgetCache.constructGridCache(grids);
		log.info("------------------------------- Initialize Grid Successfully -------------------------------");		
		
	}
	
}
