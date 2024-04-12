package com.ava.dynamic.repo;

import java.util.List;

import com.ava.dynamic.model.MenuTeam;

public interface MenuDAO {

	List<MenuTeam> loadTeamByUsername(String username, String teamname) throws Exception;

	List<MenuTeam> loadChildrenTeamByTeamId(String teamId) throws Exception;

}
