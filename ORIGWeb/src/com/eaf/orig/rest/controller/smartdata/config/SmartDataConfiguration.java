package com.eaf.orig.rest.controller.smartdata.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

@Configuration
public class SmartDataConfiguration {
	static final transient Logger log = Logger.getLogger(SmartDataConfiguration.class);
	private final String jdbc = "jdbc/orig";//Should be constant, note to revise in the next version
	
	@Bean(name="smartDataSource")
	public DataSource widgetDataSource() {
		DataSource dataSource = null;
		JndiTemplate jndi = new JndiTemplate();
		try {
			dataSource = (DataSource) jndi.lookup(jdbc);
		} catch (NamingException e) {
			log.error("NamingException for "+jdbc, e);
		}
		log.debug("Instanciate SmartData data source : "+jdbc+" successfully!");
		return dataSource;
	}
}
