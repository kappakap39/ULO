package com.ava.dynamic.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.dynamic.model.MenuTeam;
import com.ava.dynamic.model.grid.Grid;
import com.ava.dynamic.model.grid.Menu;
import com.ava.dynamic.model.grid.MenuItem;
import com.ava.dynamic.repo.MenuDAO;

@Service
public class MenuService {
	static final transient Logger log = LogManager.getLogger(MenuService.class);
	private static final String DEFAULT_ICON = "";
	private static final String DEFAULT_RELATIVE_PATH = "grid";
	@Autowired
	private MenuDAO menuDAO;
	
	public void loadGridMenu(Grid grid, String username, String teamId) throws Exception{
		if(grid == null){
			return;
		}
		
		Menu menu = grid.getMenu();
		if(menu == null){
			return;
		}
		
		List<MenuItem> items = menu.getItems();
		if(items == null){
			items = new ArrayList<MenuItem>();
			menu.setItems(items);
		}
		
		//Begin constructing items
		log.debug("Constructing menu for Grid ID : "+grid.getId());
		if(grid.getId() == 1l){
			constructTopLevelMenu(items, username, teamId, String.valueOf(grid.getChildId()));
		}
		else if(grid.getId() == 2l){
			constructTopLevelMenu(items, username, teamId, String.valueOf(grid.getChildId()));
		}
		else if(grid.getId() == 3l){
			constructTopLevelMenu(items, username, teamId, String.valueOf(grid.getChildId()));
		}
		else if(grid.getId() == 4l){
			MenuItem item = constructSingleMenuItem("Team Performance", "All Staff Performance", String.valueOf(grid.getChildId())+"?teamId="+teamId);
			items.clear();
			items.add(item);
		}
		
		//Finalize submenu
		menu.setItems(items);

	}
	
	private void constructTopLevelMenu(List<MenuItem> items, String username, String teamId, String gridId) throws Exception{
		if(username == null && teamId == null){
			throw new IllegalArgumentException("Username/Teamname to retrieve menu list can not be null!");
		}
		
		//construct first menu		
		try{
			String menuName = items.get(0).getTitle();
			MenuItem first = constructMenuByTeam(menuName, username, teamId, gridId);
			items.set(0, first);
		}catch(Exception e){
			log.warn("First menu for Grid ID : "+gridId+", which is mandatory, is not configured properly!");
			MenuItem first = constructMenuByTeam(null, username, teamId, gridId);
			items.add(first);
		}
		
	}
	
	
	
	private MenuItem constructMenuByTeam(String menuName, String username, String teamId, String gridId) throws Exception{
		if(menuName == null)menuName = "";
		MenuItem item = new MenuItem();
		item.setActive(true);
		item.setTitle(menuName);
		
		//Load sub-menu names
		List<MenuTeam> menuNames = menuDAO.loadChildrenTeamByTeamId(teamId);
		log.debug("First menu's submenus : "+menuNames);
		List<MenuItem> subMenus = new ArrayList<MenuItem>();
		if(menuNames != null && !menuNames.isEmpty()){
			for(MenuTeam name : menuNames){
				MenuItem subMenu = new MenuItem();
				subMenu.setIcon(DEFAULT_ICON);
				subMenu.setTitle(name.getTeamName());
				subMenu.setLink(DEFAULT_RELATIVE_PATH+"/"+gridId+"?teamId="+name.getTeamId());
				//Add submenu
				subMenus.add(subMenu);
			}
		}
		item.setSubMenu(subMenus);
		
		return item;
	}
	
	private MenuItem constructSingleMenuItem(String menuName, String subMenuName, String gridId) throws Exception{
		if(menuName == null)menuName = "";
		MenuItem item = new MenuItem();
		item.setActive(true);
		item.setTitle(menuName);
		
		//Create sub-menu names
		log.debug("First menu's submenus : "+subMenuName);
		List<MenuItem> subMenus = new ArrayList<MenuItem>();
		MenuItem subMenu = new MenuItem();
		subMenu.setTitle(subMenuName);
		subMenu.setIcon(DEFAULT_ICON);
		subMenu.setLink(DEFAULT_RELATIVE_PATH+"/"+gridId);
		
		//Set sub-menu
		subMenus.add(subMenu);
		item.setSubMenu(subMenus);
		
		return item;
	}
	
	
}
