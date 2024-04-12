package com.ava.dynamic.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ava.dynamic.model.grid.Grid;
import com.ava.dynamic.model.grid.GridItem;
import com.ava.dynamic.model.grid.Menu;
import com.ava.dynamic.model.grid.MenuItem;
import com.ava.dynamic.util.JSONUtil;

public class WidgetCache {
	static final transient Logger log = LogManager.getLogger(WidgetCache.class);
	private static final class Holder{
		static final WidgetCache INSTANCE = new WidgetCache();
	}
	private WidgetCache(){
		
	}
	public static WidgetCache getInstance(){
		return Holder.INSTANCE;
	}
	private static final Map<Long, Widget> widgetMap = new HashMap<Long, Widget>();
	
	//Grid
	private static final Map<Long, Grid> gridMap = new HashMap<Long, Grid>();
	private static final Map<String, Grid> gridCodeMap = new HashMap<String, Grid>();
	private static final Map<Long, List<GridItem>> gridItemOfGridMap = new HashMap<Long, List<GridItem>>();
	private static final Map<Long, List<Widget>> widgetGridItemIdMap = new HashMap<Long, List<Widget>>();
	
	//Menu
	private static final Map<Long, Menu> gridMenuMap = new HashMap<Long, Menu>();
	private static final Map<Long, Menu> menuMap = new HashMap<Long, Menu>();
	private static final Map<Long, List<MenuItem>> menuItemOfMenuMap = new HashMap<Long, List<MenuItem>>();
	
	public static Widget getWidget(Long id){
		return widgetMap.get(id);
	}
	
	public static void constructGridCache(List<Grid> grids){
		if(grids == null || grids.isEmpty()){
			return;
		}
		
		gridMap.clear();
		for(Grid grid : grids){
			Menu menu = gridMenuMap.get(grid.getId());
			grid.setMenu(menu);
			grid.setItems(gridItemOfGridMap.get(grid.getId()));
			gridMap.put(grid.getId(), grid);
			gridCodeMap.put(grid.getCode(), grid);
		}
		if(log.isDebugEnabled())
			log.debug("gridMap = "+gridMap);
	}
	
	public static void constructGridItemOfGridCache(List<GridItem> gridItems){
		if(gridItems == null || gridItems.isEmpty()){
			return;
		}
		
		gridItemOfGridMap.clear();
		
		for(GridItem gridItem: gridItems){
			Long parentId = gridItem.getGridId();
			List<GridItem> gridItemByParentIdList = gridItemOfGridMap.get(parentId);
			if(gridItemByParentIdList == null){
				gridItemByParentIdList = new ArrayList<GridItem>();
			}
			
			//Set children
			gridItem.setWidgetList(widgetGridItemIdMap.get(gridItem.getId()));
			
			gridItemByParentIdList.add(gridItem);
			gridItemOfGridMap.put(parentId, gridItemByParentIdList);
		}
		if(log.isDebugEnabled())
			log.debug("gridItemParentIdMap = "+gridItemOfGridMap);
	}
	
	public static void constructWidgetOfGridItemCache(List<Widget> widgets){
		if(widgets == null || widgets.isEmpty()){
			return;
		}
		widgetMap.clear();
		widgetGridItemIdMap.clear();
		
		for(Widget widget: widgets){
			//Widget map
			widgetMap.put(widget.getId(), widget);
			
			//Widget-Parent map
			Long gridItemId = widget.getGridItemId();
			List<Widget> widgetsByParentIdList = widgetGridItemIdMap.get(gridItemId);
			if(widgetsByParentIdList == null){
				widgetsByParentIdList = new ArrayList<Widget>();
			}
			
			widgetsByParentIdList.add(widget);
			widgetGridItemIdMap.put(gridItemId, widgetsByParentIdList);
		}
		if(log.isDebugEnabled())
			log.debug("widgetGridParentIdMap = "+widgetGridItemIdMap);
	}
	
	public static Grid getGridById(Long id){
		return gridMap.get(id);
	}
	public static Grid getGridByCode(String code){
		return gridCodeMap.get(code);
	}
	public static Collection<Grid> getAllGrid(){
		return gridMap.values();
	}
	
	//Menu method
	public static void constructMenuItemCache(List<MenuItem> menuItems){
		if(menuItems == null || menuItems.isEmpty()){
			return;
		}
		menuItemOfMenuMap.clear();
		List<MenuItem> parentSet = new ArrayList<MenuItem>();
		Map<Long, List<MenuItem>> childrenMap = new HashMap<Long,List<MenuItem>>();
		
		//Prepare Data
		for(MenuItem item : menuItems){
			Long parentId = item.getParentId();
			if(parentId == null || parentId == 0L){
				parentSet.add(item);
			}
			else{
				List<MenuItem> childrenList = childrenMap.get(parentId);
				if(childrenList == null){
					childrenList = new ArrayList<MenuItem>();
				}
				childrenList.add(item);
				childrenMap.put(parentId, childrenList);
			}
		}
		
		//Set Parent Menu
		for(MenuItem item : parentSet){
			//Set children
			List<MenuItem> subMenuList = childrenMap.get(item.getId());
			item.setSubMenu(subMenuList);
			
			//Set First Level Menu
			Long menuId = item.getMenuId();
			List<MenuItem> itemsOfMenu = menuItemOfMenuMap.get(menuId);
			if(itemsOfMenu == null){
				itemsOfMenu = new ArrayList<MenuItem>();
			}
			itemsOfMenu.add(item);
			menuItemOfMenuMap.put(menuId, itemsOfMenu);
		}
		
		if(log.isDebugEnabled())
			log.debug("menuItemOfMenuMap = "+JSONUtil.toJson(menuItemOfMenuMap));
	}
	
	public static void constructMenuCache(List<Menu> menus){
		if(menus == null || menus.isEmpty()){
			return;
		}
		
		for(Menu menu : menus){
			List<MenuItem> items = menuItemOfMenuMap.get(menu.getId());
			menu.setItems(items);
			gridMenuMap.put(menu.getGridId(), menu);
			menuMap.put(menu.getId(), menu);
		}
		
		if(log.isDebugEnabled())
			log.debug("gridMenuMap = "+JSONUtil.toJson(gridMenuMap));
	}
	
	public static Menu getMenuById(Long id){
		if(id == null){
			return null;
		}
		return menuMap.get(id);
	}
	
}
