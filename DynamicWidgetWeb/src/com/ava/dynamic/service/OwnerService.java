package com.ava.dynamic.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.dynamic.model.Owner;
import com.ava.dynamic.repo.OwnerDAO;

@Service
public class OwnerService {
	@Autowired
	private OwnerDAO ownerDAO;
	
	public Owner getOwnerById(String userId){
		if(userId != null){
			Owner owner = ownerDAO.getOwnerById(userId);
			
			//Add other member in team those who are authorized to view dashboard
			updateOtherTeamMembers(owner);
			
			return owner;
		}else{
			return null;
		}
	}
	
	public Owner updateOtherTeamMembers(Owner owner){
		if(owner == null)return owner;
		
		owner.setOtherTeamMember(ownerDAO.getOtherTeamMembers(owner.getPositionId(), owner.getUserId()));
		if(owner.getOtherTeamMember() == null){
			owner.setOtherTeamMember(new ArrayList<Owner>());
		}
		owner.getOtherTeamMember().add(owner);//Add itself to appear in the list
		return owner;
	}
	
	public String getTeamNameById(String teamId){
		if(teamId == null || teamId.isEmpty()){
			return null;
		}
		return ownerDAO.getTeamNameByID(teamId);
	}
	
	/**Use getDelegateOwnerByTeamId(String,String) instead for more accurate result
	 * @param teamId
	 * @return
	 */
	@Deprecated
	public Owner getRepresentativeOwnerByTeamId(String teamId){
		if(teamId == null || teamId.isEmpty()){
			return null;
		}
		return ownerDAO.getDelegateOwnerByTeamId(teamId);
	}
	
	/**Get delegates by team Id
	 * @param teamId
	 * @param positionId
	 * @return
	 */
	public Owner getDelegateOwnerByTeamId(String teamId, String positionId){
		if(teamId == null || teamId.isEmpty()){
			return null;
		}
		return ownerDAO.getDelegateOwnerByTeamId(teamId, positionId);
	}
}
