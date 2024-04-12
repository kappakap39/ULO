package com.eaf.orig.shared.dao.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.utility.exception.BusinessClassByUserDAOException;
import com.eaf.orig.shared.model.BusinessClassM;
import com.eaf.orig.shared.model.BusinessClassMapM;
import com.eaf.orig.shared.service.OrigServiceLocator;

/**
 * @author kitinat
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BusinessClassByUserDAOImpl extends OrigObjectDAO implements BusinessClassByUserDAO {
	
	private static final Logger log = Logger.getLogger(BusinessClassByUserDAOImpl.class);
	
	/**
	 * Method getBusinessClassByUser()
	 * 
	 * @param userName 
	 * @return Vector of BusinessClassM
	 */
	public Vector getBusinessClassByUser(String username) throws BusinessClassByUserDAOException{		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		Vector result = new Vector();		
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
			sql.append("   select distinct t6.BUS_CLASS_ID,t6.BUS_CLASS_DESC,t6.ORG_ID,");
			sql.append("   t6.PRODUCT_ID,t6.CHANNEL_ID,t6.ACTIVE_STATUS,t6.EFFECTIVE_DATE ");
			sql.append("   from USER_PROFILE t1, PROFILE t2, PROFILE_BUS_GRP t3, ");
			sql.append("   BUSINESS_CLASS_GROUP t4,BUSS_CLASS_GRP_MAP t5,  BUSINESS_CLASS t6,");
			sql.append("  PROFILE_GROUP t7");
			sql.append("   where t1.PROFILE_ID=t2.PROFILE_ID ");
			sql.append("   and t2.PROFILE_ID=t3.PROFILE_ID");
			sql.append("   and t3.BUS_GRP_ID=t4.BUSS_GRP_ID");
			sql.append("   and t4.BUSS_GRP_ID=t5.BUSS_GRP_ID");
			sql.append("   and t5.BUS_CLASS_ID=t6.BUS_CLASS_ID");
			sql.append("  and t7.PROFILE_GRP_ID=t2.PROFILE_GRP_ID");
			sql.append("  and t7.ACTIVE_STATUS='Y'");
			sql.append("   and t1.USER_NAME=?  ");		
			sql.append("   and t6.ACTIVE_STATUS='Y'  ");			
			
		//	log.debug("BusinessClassByUserDAOImpl :[getBusinessClassByUser] sql="+sql);
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);			
			ps.setString(1, username);			
			rs = ps.executeQuery();		
			BusinessClassM businessclassM;			
			while(rs.next()){
				businessclassM = new BusinessClassM();
				businessclassM.setId(rs.getString("BUS_CLASS_ID"));
				businessclassM.setDescription(rs.getString("BUS_CLASS_DESC"));
				businessclassM.setOrgId(rs.getString("ORG_ID"));
				businessclassM.setProductId(rs.getString("PRODUCT_ID"));
				businessclassM.setChannelId(rs.getString("CHANNEL_ID"));																					
				result.add(businessclassM);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
//				try{
//					if(con!=null)con.commit();
//				}catch(Exception e){}
				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}
		
		return result;
		
	}
public Vector getBusinessClassByUserDealer(String username,String dealerId) throws BusinessClassByUserDAOException{		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		Vector result = new Vector();	
		Vector allResult =new Vector();		
		HashMap tmp=new HashMap();
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
			sql.append("   select distinct t6.BUS_CLASS_ID,t6.BUS_CLASS_DESC,t6.ORG_ID,");
			sql.append("   t6.PRODUCT_ID,t6.CHANNEL_ID,t6.ACTIVE_STATUS,t6.EFFECTIVE_DATE ");
			sql.append("   from USER_PROFILE t1, PROFILE t2, PROFILE_BUS_GRP t3, ");
			sql.append("   BUSINESS_CLASS_GROUP t4,BUSS_CLASS_GRP_MAP t5,  BUSINESS_CLASS t6,");
			sql.append("  PROFILE_GROUP t7");
			sql.append("   where t1.PROFILE_ID=t2.PROFILE_ID ");
			sql.append("   and t2.PROFILE_ID=t3.PROFILE_ID");
			sql.append("   and t3.BUS_GRP_ID=t4.BUSS_GRP_ID");
			sql.append("   and t4.BUSS_GRP_ID=t5.BUSS_GRP_ID");
			sql.append("   and t5.BUS_CLASS_ID=t6.BUS_CLASS_ID");
			sql.append("  and t7.PROFILE_GRP_ID=t2.PROFILE_GRP_ID");
			sql.append("  and t7.ACTIVE_STATUS='Y'");
			sql.append("   and t1.USER_NAME=?  ");		
			sql.append("   and t6.ACTIVE_STATUS='Y'  ");	
			sql.append("   and t2.PROFILE_ID in ( ");
			sql.append("   	select t8.PROFILE_ID from    PROFILE t8, PROFILE_LOCATION t9,LOCATION t10,LOCATION_DEALER t11,DEALER t12");
			sql.append("  	 where t8.PROFILE_ID=t9.PROFILE_ID and t9.LOCATION_ID=t10.LOCATION_ID");
			sql.append("  	 and t10.LOCATION_ID=t11.LOCATION_ID and t11.DEALER_ID=t12.DEALER_ID");
			sql.append("  	 and t12.DEALER_ID=?" );
			sql.append("  )");		
		//	log.debug("sql for getBusinessClassByUserDealer = " + sql.toString());
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);			
			ps.setString(1, username.trim());	
			ps.setString(2, dealerId.trim());		
			rs = ps.executeQuery();		
			BusinessClassM businessclassM;			
			while(rs.next()){
				businessclassM = new BusinessClassM();
				businessclassM.setId(rs.getString("BUS_CLASS_ID"));
				businessclassM.setDescription(rs.getString("BUS_CLASS_DESC"));
				businessclassM.setOrgId(rs.getString("ORG_ID"));
				businessclassM.setProductId(rs.getString("PRODUCT_ID"));
				businessclassM.setChannelId(rs.getString("CHANNEL_ID"));																					
				result.add(businessclassM);
				//Get all subset
				HashMap allsubset =getSubSetBusinessClass(businessclassM.getId());
				Set set =allsubset.keySet();
				Object oo[]=set.toArray();
				for(int i=0;i<oo.length;i++){//for
					if(!tmp.containsKey(oo[i])){//if
						tmp.put(oo[i],oo[i]);
						allResult.add(allsubset.get(oo[i]));	
						System.out.println("BUSINESSCLASS FORM DAO>>"+oo[i]);					
					}//if
				}//for
			}					
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
//				try{
//					if(con!=null)con.commit();
//				}catch(Exception e){}
				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}
		
		return result;
		
	}
	

 public Vector getDealerByUserHaveExcep3(String username) throws BusinessClassByUserDAOException{
 	Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		Vector result = new Vector();		
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
			sql.append("  select d.dealer_id from  dealer d,location_dealer ld,location l,profile_location pl,profile p,profile_group pg ");
			sql.append("  where d.DEALER_ID=ld.DEALER_ID ");
			sql.append("  and ld.LOCATION_ID=l.LOCATION_ID");
			sql.append("  and l.LOCATION_ID=pl.LOCATION_ID");
			sql.append("  and pl.PROFILE_ID=p.PROFILE_ID");
			sql.append("  and p.PROFILE_GRP_ID=pg.PROFILE_GRP_ID");
			sql.append("  and pg.ACTIVE_STATUS='Y' ");
			sql.append("  and p.PROFILE_ID in (");
			sql.append("  select distinct p2.PROFILE_ID from user_profile up,profile p2,profile_group pg2");
			sql.append("  where p2.PROFILE_ID=up.PROFILE_ID");
			sql.append(" and p2.PROFILE_GRP_ID=pg2.PROFILE_GRP_ID");
			sql.append(" and pg2.ACTIVE_STATUS='Y'");
			sql.append(" and up.USER_NAME=?");
			sql.append(" and (up.EXCEP_FLAG3='Y'");
			sql.append("  or up.EXCEP_FLAG2='Y'");
			sql.append("  or up.EXCEP_FLAG1='Y'");
			sql.append("	 	))");
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);			
			ps.setString(1, username);			
			rs = ps.executeQuery();		
			String dealer= "";			
			while(rs.next()){			
				dealer=(rs.getString("DEALER_ID"));																								
				result.add(dealer);
			}			
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
//				try{
//					if(con!=null)con.commit();
//				}catch(Exception e){}
				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}
		
		return result;
		
	}
public boolean checkAccessBusinessClass(String username,String dealerId,String busID) throws BusinessClassByUserDAOException{		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		//Vector result = new Vector();	
		boolean resultB=false;
		//Vector allResult =new Vector();		
		HashMap tmp=new HashMap();
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
			sql.append("   select distinct t6.BUS_CLASS_ID,t6.BUS_CLASS_DESC,t6.ORG_ID,");
			sql.append("   t6.PRODUCT_ID,t6.CHANNEL_ID,t6.ACTIVE_STATUS,t6.EFFECTIVE_DATE ");
			sql.append("   from USER_PROFILE t1, PROFILE t2, PROFILE_BUS_GRP t3, ");
			sql.append("   BUSINESS_CLASS_GROUP t4,BUSS_CLASS_GRP_MAP t5,  BUSINESS_CLASS t6,");
			sql.append("  PROFILE_GROUP t7");
			sql.append("   where t1.PROFILE_ID=t2.PROFILE_ID ");
			sql.append("   and t2.PROFILE_ID=t3.PROFILE_ID");
			sql.append("   and t3.BUS_GRP_ID=t4.BUSS_GRP_ID");
			sql.append("   and t4.BUSS_GRP_ID=t5.BUSS_GRP_ID");
			sql.append("   and t5.BUS_CLASS_ID=t6.BUS_CLASS_ID");
			sql.append("  and t7.PROFILE_GRP_ID=t2.PROFILE_GRP_ID");
			sql.append("  and t7.ACTIVE_STATUS='Y'");
			sql.append("   and t1.USER_NAME=?  ");		
			sql.append("   and t6.ACTIVE_STATUS='Y'  ");	
			sql.append("   and t2.PROFILE_ID in ( ");
			sql.append("   	select t8.PROFILE_ID from    PROFILE t8, PROFILE_LOCATION t9,LOCATION t10,LOCATION_DEALER t11,DEALER t12");
			sql.append("  	 where t8.PROFILE_ID=t9.PROFILE_ID and t9.LOCATION_ID=t10.LOCATION_ID");
			sql.append("  	 and t10.LOCATION_ID=t11.LOCATION_ID and t11.DEALER_ID=t12.DEALER_ID");
			sql.append("  	 and t12.DEALER_ID=?" );
			sql.append("  )");		
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);			
			ps.setString(1, username.trim());	
			ps.setString(2, dealerId.trim());		
			rs = ps.executeQuery();		
			BusinessClassM businessclassM;			
			while(rs.next()){
				businessclassM = new BusinessClassM();
				businessclassM.setId(rs.getString("BUS_CLASS_ID"));
				businessclassM.setDescription(rs.getString("BUS_CLASS_DESC"));
				businessclassM.setOrgId(rs.getString("ORG_ID"));
				businessclassM.setProductId(rs.getString("PRODUCT_ID"));
				businessclassM.setChannelId(rs.getString("CHANNEL_ID"));																					
				//result.add(businessclassM);
				//Get all subset
				HashMap allsubset =getSubSetBusinessClass(businessclassM.getId());
				if(allsubset.containsKey(busID.trim())){
					resultB=true;
				}
			}					
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
//				try{
//					if(con!=null)con.commit();
//				}catch(Exception e){}				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}		
		return resultB;		
	}
	
	public Vector getUserProfile(String busID,String DealerID,String userName) throws  BusinessClassByUserDAOException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		Vector result = new Vector();		
		HashMap busMap=new HashMap();
		busMap=getSuperSetBusinessClass(busID);
		String busIDIN = getStringSqlIN(busMap);
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
				
			sql.append("  select p.profile_id from profile p where p.profile_id in");
			sql.append(" (");
			sql.append("  select p2.profile_id from dealer d,location_dealer ld,location l,profile_location pl,profile p2");
			sql.append("  where d.DEALER_ID=ld.DEALER_ID");
			sql.append("  and ld.LOCATION_ID=l.LOCATION_ID");
			sql.append("  and l.LOCATION_ID=pl.LOCATION_ID");
			sql.append("  and pl.PROFILE_ID=p2.PROFILE_ID ");
			sql.append("  and d.DEALER_ID=? ");//check dealer
			sql.append(" ) ");
			sql.append(" and p.profile_id in");
			sql.append(" (");
			sql.append("  select p3.profile_id from user_profile up,profile p3,profile_bus_grp pbg ,business_class_group bcg,buss_class_grp_map bcgm,business_class b");
			sql.append("  where up.profile_id=p3.profile_id");
			//sql.append("  and p3.profile_grp_id=pg.profile_grp_id");
			//sql.append("  and pg.active_status='Y'");
			sql.append("  and p3.profile_id=pbg.profile_id");
			sql.append("  and pbg.bus_grp_id=bcg.buss_grp_id");
			sql.append("  and bcg.buss_grp_id=bcgm.buss_grp_id");
			sql.append("  and bcgm.bus_class_id=b.bus_class_id");
			sql.append("  and up.user_name=?");//check user
			sql.append("  and b.bus_class_id in "+busIDIN);//check busclass
			sql.append(" )");		
		
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);			
			ps.setString(1, DealerID);		
			ps.setString(2, userName);	
			//ps.setString(3, busID);		
			rs = ps.executeQuery();		
			String profileID= "";			
			while(rs.next()){			
				profileID=(rs.getString("PROFILE_ID"));																												
				result.add(profileID);
			}			
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
//				try{
//					if(con!=null)con.commit();
//				}catch(Exception e){}
				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}		
		return result;
	}
	public boolean checkUserProfileExcepFlag3(String busID,String DealerID,String userName) throws  BusinessClassByUserDAOException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		Vector result = new Vector();	
		boolean re = false;	
		HashMap busMap=new HashMap();
		busMap=getSuperSetBusinessClass(busID);
		String busIDIN = getStringSqlIN(busMap);
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
				
			sql.append("  select p.profile_id from profile p where p.profile_id in");
			sql.append(" (");
			sql.append("  select p2.profile_id from dealer d,location_dealer ld,location l,profile_location pl,profile p2");
			sql.append("  where d.DEALER_ID=ld.DEALER_ID");
			sql.append("  and ld.LOCATION_ID=l.LOCATION_ID");
			sql.append("  and l.LOCATION_ID=pl.LOCATION_ID");
			sql.append("  and pl.PROFILE_ID=p2.PROFILE_ID ");
			sql.append("  and d.DEALER_ID=? ");//check dealer
			sql.append(" ) ");
			sql.append(" and p.profile_id in");
			sql.append(" (");
			sql.append("  select p3.profile_id from user_profile up,profile p3,profile_bus_grp pbg ,business_class_group bcg,buss_class_grp_map bcgm,business_class b");
			sql.append("  where up.profile_id=p3.profile_id");
			//sql.append("  and p3.profile_grp_id=pg.profile_grp_id");
			//sql.append("  and pg.active_status='Y'");
			sql.append("  and p3.profile_id=pbg.profile_id");
			sql.append("  and pbg.bus_grp_id=bcg.buss_grp_id");
			sql.append("  and bcg.buss_grp_id=bcgm.buss_grp_id");
			sql.append("  and bcgm.bus_class_id=b.bus_class_id");
			sql.append("  and up.user_name=?");//check user
			sql.append("  and up.EXCEP_FLAG3='Y'");//check user
			sql.append("  and b.bus_class_id in "+busIDIN);//check busclass
			sql.append(" )");		
		
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);			
			ps.setString(1, DealerID);		
			ps.setString(2, userName);	
			//ps.setString(3, busID);		
			System.out.println(sql);
			rs = ps.executeQuery();		
			String profileID= "";			
			while(rs.next()){			
				profileID=(rs.getString("PROFILE_ID"));																												
				result.add(profileID);
			}			
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
				try{
//					if(con!=null)con.commit();
				}catch(Exception e){}
				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}	
		if(result.size()>0){
			re=true;
		}	
		return re;
	}
	/**
	 * Method getAllBusinessClass()
	 *  * @return Vector of BusinessClassM
	 */
	private  Vector getAllBusinessClass() throws BusinessClassByUserDAOException{		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		Vector result = new Vector();		
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
			sql.append("   select * from BUSINESS_CLASS where ACTIVE_STATUS='Y' ");		
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);								
			rs = ps.executeQuery();		
			BusinessClassM businessclassM;			
			while(rs.next()){
				businessclassM = new BusinessClassM();
				businessclassM.setId(rs.getString("BUS_CLASS_ID"));
				businessclassM.setDescription(rs.getString("BUS_CLASS_DESC"));
				businessclassM.setOrgId(rs.getString("ORG_ID"));
				businessclassM.setProductId(rs.getString("PRODUCT_ID"));
				businessclassM.setChannelId(rs.getString("CHANNEL_ID"));
				String status=	rs.getString("ACTIVE_STATUS");		
				businessclassM.setActive((status.equalsIgnoreCase("Y")? true:false));																		
				result.add(businessclassM);
			}			
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
				try{
//					if(con!=null)con.commit();
				}catch(Exception e){}
				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}		
		return result;		
	}		
/**
	 * Method getAllBusinessClass()
	 * 
	 */
	private  BusinessClassM getBusinessClass(String busID) throws BusinessClassByUserDAOException{		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		BusinessClassM businessclassM=null;					
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
			sql.append("   select * from BUSINESS_CLASS where ACTIVE_STATUS='Y' ");	
			sql.append("  and  BUS_CLASS_ID =?");	
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);								
			ps.setString(1, busID);	
			rs = ps.executeQuery();							
			while(rs.next()){
				businessclassM = new BusinessClassM();
				businessclassM.setId(rs.getString("BUS_CLASS_ID"));
				businessclassM.setDescription(rs.getString("BUS_CLASS_DESC"));
				businessclassM.setOrgId(rs.getString("ORG_ID"));
				businessclassM.setProductId(rs.getString("PRODUCT_ID"));
				businessclassM.setChannelId(rs.getString("CHANNEL_ID"));
				String status=	rs.getString("ACTIVE_STATUS");		
				businessclassM.setActive((status.equalsIgnoreCase("Y")? true:false));																					
			}			
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
				try{
//					if(con!=null)con.commit();
				}catch(Exception e){}
				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}		
		return businessclassM;		
	}			
	
	
	private  HashMap getSubSetBusinessClass(String busID){
		HashMap hSub =new HashMap();
		BusinessClassM businessclass =null;
		Vector vecB =new Vector();
		try{
			businessclass = getBusinessClass(busID);
			 vecB = getAllBusinessClass();
		}
		catch (BusinessClassByUserDAOException e){
			e.printStackTrace();
		}
		String ALL="ALL";
		String OrgParam = (businessclass.getOrgId()!=null)? businessclass.getOrgId():"";
		String ProdParam = (businessclass.getProductId()!=null)? businessclass.getProductId():"";
		String ChanParam = (businessclass.getChannelId()!=null)? businessclass.getChannelId():"";			
		for(int i=0;i<vecB.size();i++){//for
			BusinessClassM proData =(BusinessClassM) vecB.get(i);
			String Org=proData.getOrgId();
			String Prod=proData.getProductId();
			String Chan=proData.getChannelId();
			 if(OrgParam.equalsIgnoreCase(ALL) || OrgParam.equalsIgnoreCase(Org)){//if1
			 	if(ProdParam.equalsIgnoreCase(ALL) || ProdParam.equalsIgnoreCase(Prod)){//if2
			 		if(ChanParam.equalsIgnoreCase(ALL) || ChanParam.equalsIgnoreCase(Chan)){//if3
			 			hSub.put(proData.getId(),proData);
			 		}//if3
	            }//if2
			 }//if1			                                                      
		}//for		
		return hSub;
	}

private  HashMap getSuperSetBusinessClass(String busID){
		HashMap hSup =new HashMap();
		BusinessClassM businessclass =null;
		Vector vecB =new Vector();
		try{
			businessclass = getBusinessClass(busID);
			 vecB = getAllBusinessClass();
		}
		catch (BusinessClassByUserDAOException e){
			e.printStackTrace();
		}
		String ALL="ALL";
		String OrgParam = (businessclass.getOrgId()!=null)? businessclass.getOrgId():"";
		String ProdParam = (businessclass.getProductId()!=null)? businessclass.getProductId():"";
		String ChanParam = (businessclass.getChannelId()!=null)? businessclass.getChannelId():"";			
		for(int i=0;i<vecB.size();i++){//for
			BusinessClassM proData =(BusinessClassM) vecB.get(i);
			String Org=proData.getOrgId();
			String Prod=proData.getProductId();
			String Chan=proData.getChannelId();
			 if(Org.equalsIgnoreCase(ALL) || Org.equalsIgnoreCase(OrgParam)){//if1
			 	if(Prod.equalsIgnoreCase(ALL) || Prod.equalsIgnoreCase(ProdParam)){//if2
			 		if(Chan.equalsIgnoreCase(ALL) || Chan.equalsIgnoreCase(ChanParam)){//if3
			 			hSup.put(proData.getId(),proData);			 			
			 		}//if3
	            }//if2
			 }//if1			                                                      
		}//for		
		return hSup;
	}	
	
	private  String getStringSqlIN(HashMap map) {
		String re="( ";		
		//Vector v=new Vector();
		if(map==null){
			map=new HashMap();
		}
		Set set =map.keySet();
		Object o[]=set.toArray();
		if(o.length==0){
			return "( '' )";
		}else{				
			for(int j=0;j<o.length;j++){											
						re= re.concat("'");
						re=re.concat((String)o[j]);
						re=re.concat("'");
						if(j!=(o.length-1))
								re=re.concat(",");									
			}		
		re=re.concat(")");	
		return re;
		}	
	}	
	
	
	/************Retrun HashMap of businessclass key is businessclass_id****************/
	public HashMap getAccessBusinessClassByUser(String username) throws  BusinessClassByUserDAOException{
		HashMap map =new HashMap();
		HashMap map_tmp =new HashMap();
		Vector vBus =new Vector();
		vBus =getBusinessClassByUser(username);
		BusinessClassM businessClassM;
		if(vBus!=null){//if1
			for(int i=0;i<vBus.size();i++){//for
				businessClassM=(BusinessClassM) vBus.get(i);
				map_tmp=getSubSetBusinessClass(businessClassM.getId());
				Set set = map_tmp.keySet();
				Object object[] =set.toArray();
				for(int j=0;j<object.length;j++){//for2
					String buz =(String)object[j];
					if(buz.indexOf("ALL")==-1&&!map.containsKey(buz)){//if2
						map.put(buz,buz);
					}//if2
				}//for2
			}//for
		}//if1		
		
		// modify for filter AL only
		HashMap mapAL =new HashMap();
		if(map==null){
			map=new HashMap();
			return map;
		}
		Set set =map.keySet();
		Object o[]=set.toArray();		
		for(int j=0;j<o.length;j++){		
			String buz = (String)o[j];
			if(buz.length()>=2){
				if("AL".equalsIgnoreCase(buz.substring(0,2))){
					mapAL.put(buz,buz);
				}
			}
		}
		
		return mapAL;
	}
		
	/* For PARAM SystemSupport */
	public BusinessClassM getBusinessClassUserLevel(String userName) throws  BusinessClassByUserDAOException {		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		BusinessClassM businessclassM=null;		
	//	log.debug(">>>>>>>>>>.. in getBusinessClassUserLevel of >> " + userName);			
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT bc.BUS_CLASS_ID, bc.BUS_CLASS_DESC, bc.ORG_ID, bc.PRODUCT_ID, bc.CHANNEL_ID, bc.ACTIVE_STATUS  ");	
			sql.append("FROM BUSINESS_CLASS bc , US_USER_DETAIL us  ");
			sql.append("WHERE us.BUS_GRP_MASTER = bc.BUS_CLASS_ID AND us.STATUS = 'Y' AND bc.ACTIVE_STATUS = 'Y'  ");
			sql.append("AND  us.USER_NAME =?");	
			
		//	log.debug("sql for getBusinessClassUserLevel = " + sql.toString());
			
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);								
			ps.setString(1, userName);	
			rs = ps.executeQuery();							
			while(rs.next()){
				businessclassM = new BusinessClassM();
				businessclassM.setId(rs.getString("BUS_CLASS_ID"));
				businessclassM.setDescription(rs.getString("BUS_CLASS_DESC"));
				businessclassM.setOrgId(rs.getString("ORG_ID"));
				businessclassM.setProductId(rs.getString("PRODUCT_ID"));
				businessclassM.setChannelId(rs.getString("CHANNEL_ID"));
				String status=	rs.getString("ACTIVE_STATUS");		
				businessclassM.setActive((status.equalsIgnoreCase("Y")? true:false));															
				businessclassM.setBusClassGroups(loadBusinessClassGroup(businessclassM.getId()));
			}			
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
//				try{
//					if(con!=null)con.commit();
//				}catch(Exception e){}
				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}		
		return businessclassM;
	}
	
	Vector loadBusinessClassGroup(String id)throws BusinessClassByUserDAOException {
		try{
			Vector prmVtBusinessGroupM = selectTableBUSS_CLASS_GRP_MAP(id);
			return prmVtBusinessGroupM;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException(e.getMessage());
		}
	}
	
	Vector selectTableBUSS_CLASS_GRP_MAP(String id)throws BusinessClassByUserDAOException {
		Connection conn = null;			
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT BUSS_GRP_ID FROM BUSS_CLASS_GRP_MAP  ");
			sql.append("WHERE BUS_CLASS_ID = ?");
			String dSql = String.valueOf( sql);
			//log.debug("dSql of BusClass group map = " + dSql);
			PreparedStatement ps = conn.prepareStatement(dSql);
			ResultSet rs = null;

			ps.setString(1, id);

			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				BusinessClassMapM prmBusClassMapM = new BusinessClassMapM();
				prmBusClassMapM.setBusClassGroupId(rs.getString(1));
				
				vt.add(prmBusClassMapM);
			}
			rs.close();
			ps.close();

			return vt;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException(e.getMessage());
		}finally{
			try{
				try{
//					if(conn!=null)conn.commit();
				}catch(Exception e){}
				
				if(conn!=null)conn.close();
			}catch(Exception e){}
		}
	}
	
	Vector selectByProdTableBUSS_CLASS_GRP_MAP(String id)throws BusinessClassByUserDAOException {
		Connection conn = null;			
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT DISTINCT BUSS_GRP_ID FROM BUSS_CLASS_GRP_MAP  ");
			sql.append("WHERE BUS_CLASS_ID in (SELECT BUS_CLASS_ID FROM BUSINESS_CLASS  ");
			sql.append("WHERE ORG_ID =(SELECT ORG_ID FROM BUSINESS_CLASS WHERE BUS_CLASS_ID = ?)  ");
			sql.append("AND PRODUCT_ID = (SELECT PRODUCT_ID FROM BUSINESS_CLASS WHERE BUS_CLASS_ID = ?))");
			String dSql = String.valueOf( sql);

			PreparedStatement ps = conn.prepareStatement(dSql);
			ResultSet rs = null;

			ps.setString(1, id);
			ps.setString(2, id);

			rs = ps.executeQuery();
			Vector vt = new Vector();
			while (rs.next()) {
				BusinessClassMapM prmBusClassMapM = new BusinessClassMapM();
				prmBusClassMapM.setBusClassGroupId(rs.getString(1));
				
				vt.add(prmBusClassMapM);
			}
			rs.close();
			ps.close();

			return vt;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException(e.getMessage());
		}finally{
			try{
				try{
//					if(conn!=null)conn.commit();
				}catch(Exception e){}
				
				if(conn!=null)conn.close();
			}catch(Exception e){}
		}
	}
	
	public HashMap getBusinessClassByOrgLevel(BusinessClassM prmBusinessClassM) throws BusinessClassByUserDAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		HashMap hResult = null;
		String ALL = "ALL";

		if(prmBusinessClassM != null){
			try{
				con = getConnection(OrigServiceLocator.ORIG_DB);			
				StringBuffer sql = new StringBuffer("");
				sql.append("SELECT BUS_CLASS_ID, BUS_CLASS_DESC, ORG_ID, PRODUCT_ID, CHANNEL_ID, ACTIVE_STATUS FROM BUSINESS_CLASS  ");
				
				if(prmBusinessClassM.getOrgId()!=null && !prmBusinessClassM.getOrgId().equals(ALL)){
					sql.append("WHERE BUS_CLASS_ID IN (SELECT DISTINCT BUS_CLASS_ID FROM BUSINESS_CLASS WHERE ORG_ID = ? ) ");
				}
				//log.debug("sql for getBusinessClassByOrgLevel = " + sql.toString());
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);								
				if(prmBusinessClassM.getOrgId()!=null && !prmBusinessClassM.getOrgId().equals(ALL)){
					ps.setString(1, prmBusinessClassM.getOrgId());	
				//	log.debug("prmBusinessClassM.getOrgId() = "+prmBusinessClassM.getOrgId());
				}
				rs = ps.executeQuery();		
				
				BusinessClassM businessclassM;			
				hResult = new HashMap();
				while(rs.next()){
					businessclassM = new BusinessClassM();
					businessclassM.setId(rs.getString("BUS_CLASS_ID"));
					businessclassM.setDescription(rs.getString("BUS_CLASS_DESC"));
					businessclassM.setOrgId(rs.getString("ORG_ID"));
					businessclassM.setProductId(rs.getString("PRODUCT_ID"));
					businessclassM.setChannelId(rs.getString("CHANNEL_ID"));
					String status=	rs.getString("ACTIVE_STATUS");		
					businessclassM.setActive((status.equalsIgnoreCase("Y")? true:false));
					hResult.put(businessclassM.getId(), businessclassM);
	//				log.debug("============= BusinessClass in OrgLevel : " + businessclassM.getId());
	//				hResult.put(String.valueOf(i), businessclassM);
	//				i++;
				}			
			}catch(Exception e){
				e.printStackTrace();
				throw new BusinessClassByUserDAOException();
			}finally{
				try{
					try{
//						if(con!=null)con.commit();
					}catch(Exception e){}
					
					if(ps!=null)ps.close();
					if(rs!=null)rs.close();
					if(con!=null)con.close();
				}catch(Exception e){}
			}
		}
	//	log.debug("size of hResult (getBusinessClassByOrgLevel) = " + hResult.size());
		return hResult;
	}
	
	public HashMap getBusinessClassByProdLevel(BusinessClassM prmBusinessClassM) throws BusinessClassByUserDAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		HashMap hResult = null;
		String ALL = "ALL";
		
		if(prmBusinessClassM != null){
			try{
				con = getConnection(OrigServiceLocator.ORIG_DB);			
				StringBuffer sql = new StringBuffer("");
				sql.append("SELECT BUS_CLASS_ID, BUS_CLASS_DESC, ORG_ID, PRODUCT_ID, CHANNEL_ID, ACTIVE_STATUS FROM BUSINESS_CLASS  ");
				
				if(!prmBusinessClassM.getOrgId().equals(ALL)){
					sql.append("WHERE BUS_CLASS_ID IN (SELECT BUS_CLASS_ID FROM BUSINESS_CLASS WHERE ORG_ID = ? ");
					if(!prmBusinessClassM.getProductId().equals(ALL)){
						sql.append("AND PRODUCT_ID = ? ) ");
					} else {
						sql.append(" ) ");
 					}
				} else {
					if(!prmBusinessClassM.getProductId().equals(ALL)){
						sql.append("WHERE BUS_CLASS_ID IN (SELECT BUS_CLASS_ID FROM BUSINESS_CLASS WHERE PRODUCT_ID = ? ) ");
					}
				}
			//	log.debug("sql for getBusinessClassByProdLevel = " + sql.toString());
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);								
				if(!prmBusinessClassM.getOrgId().equals(ALL)){
					ps.setString(1, prmBusinessClassM.getOrgId());	
					if(!prmBusinessClassM.getProductId().equals(ALL)){
						ps.setString(2, prmBusinessClassM.getProductId());	
					}
				} else {
					if(!prmBusinessClassM.getProductId().equals(ALL)){
						ps.setString(1, prmBusinessClassM.getProductId());	
					}
				}
				rs = ps.executeQuery();		
				
				BusinessClassM businessclassM;			
				hResult = new HashMap();
				while(rs.next()){
					businessclassM = new BusinessClassM();
					businessclassM.setId(rs.getString("BUS_CLASS_ID"));
					businessclassM.setDescription(rs.getString("BUS_CLASS_DESC"));
					businessclassM.setOrgId(rs.getString("ORG_ID"));
					businessclassM.setProductId(rs.getString("PRODUCT_ID"));
					businessclassM.setChannelId(rs.getString("CHANNEL_ID"));
					String status=	rs.getString("ACTIVE_STATUS");		
					businessclassM.setActive((status.equalsIgnoreCase("Y")? true:false));
					hResult.put(businessclassM.getId(), businessclassM);
	//				log.debug("============= BusinessClass in PdtLevel : " + businessclassM.getId());
	//				hResult.put(String.valueOf(i), businessclassM);
	//				i++;
				}			
			}catch(Exception e){
				e.printStackTrace();
				throw new BusinessClassByUserDAOException();
			}finally{
				try{
					try{
//						if(con!=null)con.commit();
					}catch(Exception e){}
					
					if(ps!=null)ps.close();
					if(rs!=null)rs.close();
					if(con!=null)con.close();
				}catch(Exception e){}
			}
		}
	//	log.debug("size of hResult (getBusinessClassByProdLevel) = " + hResult.size());
		return hResult;
	}
	
	public HashMap getBusinessGrpByOrgLevel(BusinessClassM prmBusinessClassM) throws BusinessClassByUserDAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		HashMap hResult = null;
		String ALL = "ALL";
		
		if(prmBusinessClassM != null){
			try{
				con = getConnection(OrigServiceLocator.ORIG_DB);			
				StringBuffer sql = new StringBuffer("");
				sql.append("SELECT DISTINCT BUSS_GRP_ID FROM BUSS_CLASS_GRP_MAP  ");
				if(!prmBusinessClassM.getOrgId().equals(ALL)){
					sql.append(" WHERE BUS_CLASS_ID IN (SELECT BUS_CLASS_ID FROM BUSINESS_CLASS WHERE ORG_ID = ? ) ");
				}
			//	log.debug("sql for getBusinessGrpByOrgLevel = " + sql.toString());
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);								
				if(!prmBusinessClassM.getOrgId().equals(ALL)){
					ps.setString(1, prmBusinessClassM.getOrgId());	
				}
				rs = ps.executeQuery();		
				
				hResult =new HashMap();
				String busGrpId = "";
				while(rs.next()){
					busGrpId = rs.getString("BUSS_GRP_ID");
					hResult.put(busGrpId, busGrpId);
				}			
			}catch(Exception e){
				e.printStackTrace();
				throw new BusinessClassByUserDAOException();
			}finally{
				try{
					try{
//						if(con!=null)con.commit();
					}catch(Exception e){}
					
					if(ps!=null)ps.close();
					if(rs!=null)rs.close();
					if(con!=null)con.close();
				}catch(Exception e){}
			}
		}
		return hResult;
	}
	
	public HashMap getBusinessGrpByProdLevel(BusinessClassM prmBusinessClassM) throws BusinessClassByUserDAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		HashMap hResult = null;
		String ALL = "ALL";
		
		if(prmBusinessClassM != null){
			try{
				con = getConnection(OrigServiceLocator.ORIG_DB);			
				StringBuffer sql = new StringBuffer("");
				sql.append("SELECT DISTINCT BUSS_GRP_ID FROM BUSS_CLASS_GRP_MAP  ");
				if(!prmBusinessClassM.getOrgId().equals(ALL)){
					sql.append("WHERE BUS_CLASS_ID IN (SELECT BUS_CLASS_ID FROM BUSINESS_CLASS WHERE ORG_ID = ? ");
					if(!prmBusinessClassM.getProductId().equals(ALL)){
						sql.append("AND PRODUCT_ID = ? ) ");
					} else {
						sql.append(" ) ");
 					}
				} else {
					if(!prmBusinessClassM.getProductId().equals(ALL)){
						sql.append("WHERE BUS_CLASS_ID IN (SELECT BUS_CLASS_ID FROM BUSINESS_CLASS WHERE PRODUCT_ID = ? ) ");
					}
				}
				
				log.debug("sql for getBusinessGrpByProdLevel = " + sql.toString());
				ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);								
				if(!prmBusinessClassM.getOrgId().equals(ALL)){
					ps.setString(1, prmBusinessClassM.getOrgId());	
					if(!prmBusinessClassM.getProductId().equals(ALL)){
						ps.setString(2, prmBusinessClassM.getProductId());	
					}
				} else {
					if(!prmBusinessClassM.getProductId().equals(ALL)){
						ps.setString(1, prmBusinessClassM.getProductId());	
					}
				}
				
				rs = ps.executeQuery();		
				
				hResult =new HashMap();
				String busGrpId = "";
				while(rs.next()){
					busGrpId = rs.getString("BUSS_GRP_ID");
					hResult.put(busGrpId, busGrpId);
				}			
			}catch(Exception e){
				e.printStackTrace();
				throw new BusinessClassByUserDAOException();
			}finally{
				try{
					try{
//						if(con!=null)con.commit();
					}catch(Exception e){}
					
					if(ps!=null)ps.close();
					if(rs!=null)rs.close();
					if(con!=null)con.close();
				}catch(Exception e){}
			}
		}
		return hResult;
	}
	
	/************Retrun HashMap of businessclass key is businessclass_id****************/
	public HashMap getSearchAccessBusinessClassByUser(String username) throws  BusinessClassByUserDAOException{
		HashMap map =new HashMap();
		HashMap map_tmp =new HashMap();
		Vector vBus =new Vector();
		vBus =getSearchBusinessClassByUser(username);
		BusinessClassM businessClassM;
		if(vBus!=null){//if1
			for(int i=0;i<vBus.size();i++){//for
				businessClassM=(BusinessClassM) vBus.get(i);
				map_tmp=getSubSetBusinessClass(businessClassM.getId());
				Set set = map_tmp.keySet();
				Object object[] =set.toArray();
				for(int j=0;j<object.length;j++){//for2
					String buz =(String)object[j];
					if(buz.indexOf("ALL")==-1&&!map.containsKey(buz)){//if2
						map.put(buz,buz);
					}//if2
				}//for2
			}//for
		}//if1		
		return map;
	}
	
	/**
	 * Method getSearchBusinessClassByUser()
	 * 
	 * @param userName 
	 * @return Vector of BusinessClassM
	 */
	public Vector getSearchBusinessClassByUser(String username) throws BusinessClassByUserDAOException{		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		Vector result = new Vector();		
		try{
			con = getConnection(OrigServiceLocator.ORIG_DB);			
			StringBuffer sql = new StringBuffer("");
			sql.append("   select distinct t6.BUS_CLASS_ID,t6.BUS_CLASS_DESC,t6.ORG_ID,");
			sql.append("   t6.PRODUCT_ID,t6.CHANNEL_ID,t6.ACTIVE_STATUS,t6.EFFECTIVE_DATE ");
			sql.append("   from SEARCH_USER_PROFILE t1, PROFILE t2, PROFILE_BUS_GRP t3, ");
			sql.append("   BUSINESS_CLASS_GROUP t4,BUSS_CLASS_GRP_MAP t5,  BUSINESS_CLASS t6,");
			sql.append("  PROFILE_GROUP t7");
			sql.append("   where t1.PROFILE_ID=t2.PROFILE_ID ");
			sql.append("   and t2.PROFILE_ID=t3.PROFILE_ID");
			sql.append("   and t3.BUS_GRP_ID=t4.BUSS_GRP_ID");
			sql.append("   and t4.BUSS_GRP_ID=t5.BUSS_GRP_ID");
			sql.append("   and t5.BUS_CLASS_ID=t6.BUS_CLASS_ID");
			sql.append("  and t7.PROFILE_GRP_ID=t2.PROFILE_GRP_ID");
			sql.append("  and t7.ACTIVE_STATUS='Y'");
			sql.append("   and t1.USER_NAME=?  ");		
			sql.append("   and t6.ACTIVE_STATUS='Y'  ");			
			
		//	log.debug("BusinessClassByUserDAOImpl :[getBusinessClassByUser] sql="+sql);
			ps = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);			
			ps.setString(1, username);			
			rs = ps.executeQuery();		
			BusinessClassM businessclassM;			
			while(rs.next()){
				businessclassM = new BusinessClassM();
				businessclassM.setId(rs.getString("BUS_CLASS_ID"));
				businessclassM.setDescription(rs.getString("BUS_CLASS_DESC"));
				businessclassM.setOrgId(rs.getString("ORG_ID"));
				businessclassM.setProductId(rs.getString("PRODUCT_ID"));
				businessclassM.setChannelId(rs.getString("CHANNEL_ID"));																					
				result.add(businessclassM);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessClassByUserDAOException();
		}finally{
			try{
				try{
//					if(con!=null)con.commit();
				}catch(Exception e){}
				
				if(ps!=null)ps.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){}
		}
		
		return result;
		
	}
}
