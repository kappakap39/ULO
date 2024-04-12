package com.ava.dynamic.model.nav;

import java.util.ArrayList;
import java.util.List;

/**Navigation class should be valid in session scope, otherwise, it's not going to be worthwhile!
 * 
 * @author TOP
 *
 */
public class Navigation {
	private List<NavigationItem> items;
	
	public List<NavigationItem> getItems() {
		return items;
	}

	public void setItems(List<NavigationItem> items) {
		this.items = items;
	}

	/**Not finish yet, use the method below instead.
	 * @param link
	 * @param desc
	 */
	public void addItem(String link, String desc){
		if(containLink(link)){
			return;
		}
		if(items == null){
			items = new ArrayList<NavigationItem>();
		}
		NavigationItem item = new NavigationItem();
		item.setId(items.size());
		item.setLink(link);
		item.setDisplayName(desc);
		items.add(item);
	}
	
	/**Add navigation item to navigation bar. If the input URL matches any URL in item list, 
	 * all items positioned from the matched item to the last element in navigation item list will be removed.
	 * 
	 * @param requestUrl Request URL without query string
	 * @param queryString string represents all parameters from request
	 * @param desc Navigation description or display name
	 */
	public void addItem(String requestUrl, String queryString, String desc){
		if(items == null){
			items = new ArrayList<NavigationItem>();
		}

		NavigationItem item = popNavigation(requestUrl);
		if(item == null){
			item = new NavigationItem();
			item.setId(items.size());
			item.setDisplayName(desc);
			item.setRequestUrl(requestUrl);
			item.setQueryString(queryString);
		}
		items.add(item);
	}
	
	private boolean containLink(String link){
		if(link == null || link.isEmpty())
			return false;
		if(items == null || items.isEmpty())
			return false;
		
		for(NavigationItem item : items){
			if(item == null)continue;
			if(link.equals(item.getLink())){
				return true;
			}
		}
		return false;
	}
	
//	private boolean containUrl(String url){
//		if(url == null || url.isEmpty())
//			return false;
//		if(items == null || items.isEmpty())
//			return false;
//		
//		for(NavigationItem item : items){
//			if(item == null)continue;
//			if(url.equals(item.getRequestUrl())){
//				return true;
//			}
//		}
//		return false;
//	}
	
	public String getLink(Integer id){
		if(id == null)
			return null;
		if(items == null || items.isEmpty())
			return null;
		
		NavigationItem nav = null;
		for(NavigationItem item : items){
			if(item == null)continue;
			if(id == item.getId()){
				nav = item;
				break;
			}
		}
		
		//Prune nav list
		if(nav != null){
			int index = items.lastIndexOf(nav) - 1;
			items = items.subList(Math.max(index, 0), items.size());
		}
		return nav.getLink();
	}
	
	public NavigationItem popNavigation(String url){
		if(url == null)
			return null;
		if(items == null || items.isEmpty())
			return null;
		
		NavigationItem nav = null;
		for(NavigationItem item : items){
			if(item == null)continue;
			if(url.equals(item.getRequestUrl())){
				nav = item;
				break;
			}
		}
		
		//Prune nav list
		if(nav != null){
			int startIndex = items.lastIndexOf(nav);
			items.subList(Math.max(startIndex, 0), items.size()).clear();
		}
		return nav;
	}
}
