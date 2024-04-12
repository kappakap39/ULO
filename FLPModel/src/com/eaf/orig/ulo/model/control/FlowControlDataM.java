package com.eaf.orig.ulo.model.control;

import java.io.Serializable;
import java.util.HashMap;

public class FlowControlDataM implements Serializable,Cloneable{
	public FlowControlDataM(){
		super();
		this.storeAction = new HashMap<String, String>();
	}
	private String tobMenuId;
	private String menuId;
	private String menuName;
	private String menuAction;
	private String role;
	private String formRole;
	private String actionType;
	private String actionTarget;
	private String context;
	private String menuUrl;
	private String outsideUrl;
	private HashMap<String,String> storeAction;	
	public String getTobMenuId() {
		return tobMenuId;
	}
	public void setTobMenuId(String tobMenuId) {
		this.tobMenuId = tobMenuId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuAction() {
		return menuAction;
	}
	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setStoreAction(HashMap<String, String> storeAction) {
		this.storeAction = storeAction;
	}
	public HashMap<String, String> getStoreAction() {
		return storeAction;
	}
	public String getStoreAction(String id){
		if(null == storeAction){
			this.storeAction = new HashMap<String, String>();
		}
		return storeAction.get(id);
	}	
	public String getFormRole() {
		return formRole;
	}
	public void setFormRole(String formRole) {
		this.formRole = formRole;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionTarget() {
		return actionTarget;
	}
	public void setActionTarget(String actionTarget) {
		this.actionTarget = actionTarget;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getOutsideUrl() {
		return outsideUrl;
	}
	public void setOutsideUrl(String outsideUrl) {
		this.outsideUrl = outsideUrl;
	}	
}
