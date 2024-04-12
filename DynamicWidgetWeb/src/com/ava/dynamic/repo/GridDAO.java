package com.ava.dynamic.repo;

import java.sql.SQLException;

import com.ava.dynamic.model.grid.Grid;

public interface GridDAO {

	int saveGrid(Grid grid) throws Exception;

	int saveOnlyGrid(Grid grid) throws Exception;

	int deleteGridById(Long id) throws Exception;

}
