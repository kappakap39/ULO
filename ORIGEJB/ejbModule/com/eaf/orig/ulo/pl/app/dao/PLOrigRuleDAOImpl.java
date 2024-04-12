package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.RulesDetailsDataM;

public class PLOrigRuleDAOImpl extends OrigObjectDAO implements PLOrigRuleDAO {
	private static Logger log = Logger.getLogger(PLOrigRuleDAOImpl.class);
	@Override
	public Vector<RulesDetailsDataM> getRulesDetailsConfig(String busClass)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<RulesDetailsDataM> resultVT = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select r_rule.policy_group_id,r_rule.policy_group_name,r_rule.policy_rules_id,r_rule.policy_rules_name, ");
			sql.append("r_rule.reject_reason_code, r_rule.policy_rules_desc ");
            sql.append("from business_class bus, ");
            sql.append("(select rb.policy_group_id,rg.policy_group_name,rgd.policy_rules_id,r.policy_rules_name,bc.org_id,bc.product_id,bc.channel_id, ");
            sql.append("r.reject_reason_code, r.policy_rules_desc ");
            sql.append("from ms_policy_rules_bus_class rb, ms_policy_rules_group rg, ms_policy_rules_group_detail rgd, ms_policy_rules r, business_class bc ");
            sql.append("where rg.policy_group_id = rb.policy_group_id ");
            sql.append("and rgd.policy_group_id = rg.policy_group_id ");
            sql.append("and r.policy_rules_id = rgd.policy_rules_id ");
            sql.append("and bc.bus_class_id = rb.bus_class_id ");
            sql.append("order by rb.seq, rgd.seq) r_rule ");
            sql.append("where bus.org_id = decode(r_rule.org_id,'ALL',bus.org_id,r_rule.org_id) ");
            sql.append("and bus.product_id = decode(r_rule.product_id,'ALL',bus.product_id,r_rule.product_id) ");
            sql.append("and bus.channel_id = decode(r_rule.channel_id,'ALL',bus.channel_id,r_rule.channel_id) ");
            sql.append("and bus.bus_class_id = ? ");
            
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, busClass);
			rs = ps.executeQuery();
			resultVT = new Vector<RulesDetailsDataM>();
			while (rs.next()) {
				RulesDetailsDataM ruleDetailsM = new RulesDetailsDataM();
				ruleDetailsM.setPolicyGroupID(rs.getString("policy_group_id"));
				ruleDetailsM.setPolicyGroupName(rs.getString("policy_group_name"));
				ruleDetailsM.setPolicyRuleID(rs.getString("policy_rules_id"));
				ruleDetailsM.setPolicyRuleName(rs.getString("policy_rules_name"));
				ruleDetailsM.setRejectReasonCode(rs.getString("reject_reason_code"));
				ruleDetailsM.setPolicyRuleDesc(rs.getString("policy_rules_desc"));
				resultVT.add(ruleDetailsM);
			}			
			return resultVT;			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public Vector<RulesDetailsDataM> getRulesDetailsVt(String policyRulesIDs) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<RulesDetailsDataM> resultVT = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select p.policy_rules_id, p.policy_rules_name, p.reject_reason_code, p.policy_rules_desc ");
			sql.append("from ms_policy_rules p ");
            sql.append("where p.policy_rules_id in("+ policyRulesIDs +")");
			String dSql = String.valueOf(sql);
			log.debug("getRulesDetailsVt Sql=" + dSql +"|policyRulesIDs=" + policyRulesIDs);
			ps = conn.prepareStatement(dSql);
			rs = ps.executeQuery();
			resultVT = new Vector<RulesDetailsDataM>();
			while (rs.next()) {
				RulesDetailsDataM ruleDetailsM = new RulesDetailsDataM();
				ruleDetailsM.setPolicyRuleID(rs.getString("policy_rules_id"));
				ruleDetailsM.setPolicyRuleName(rs.getString("policy_rules_name"));
				ruleDetailsM.setRejectReasonCode(rs.getString("reject_reason_code"));
				ruleDetailsM.setPolicyRuleDesc(rs.getString("policy_rules_desc"));
				log.debug("@@@@@ ruleDetailsM.getRejectReasonCode():" + ruleDetailsM.getRejectReasonCode());
				resultVT.add(ruleDetailsM);
			}			
			return resultVT;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

}
