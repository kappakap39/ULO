package com.ava.dynamic.repo;

import java.util.List;

import com.ava.dynamic.model.Owner;

public interface OwnerDAO {
	Owner getOwnerById(String userId);
	
	String getTeamNameByID(String teamId);

	Owner getDelegateOwnerByTeamId(String teamId);

	Owner getDelegateOwnerByTeamId(String teamId, String positionId);

	/**Retrieve all delegates according to Session Owner who log-in the system
	 * 
	 * @param positionId
	 * @param userId
	 * @return
	 */
	List<Owner> getOtherTeamMembers(String positionId, String userId);
}
