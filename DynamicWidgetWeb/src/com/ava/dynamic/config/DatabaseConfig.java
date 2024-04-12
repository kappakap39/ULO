package com.ava.dynamic.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

@Configuration
public class DatabaseConfig {
	static final transient Logger log = LogManager.getLogger(DatabaseConfig.class);
	@Value("${db.widget.jdbc}")
	private String widgetJndi;
	@Value("${db.ulo.jdbc}")
	private String uloJndi;
	
	@Bean(name="widgetDatasource")
	public DataSource widgetDataSource() {
		DataSource dataSource = null;
		JndiTemplate jndi = new JndiTemplate();
		try {
			dataSource = (DataSource) jndi.lookup(widgetJndi);
		} catch (NamingException e) {
			log.error("NamingException for "+widgetJndi, e);
		}
		log.debug("Instanciate Widget data source : "+widgetJndi+" successfully!");
		
//		WidgetConfig.initWidgetEntity(dataSource);
		return dataSource;
	}
	@Bean(name="uloDatasource")
	public DataSource uloDataSource() {
		DataSource dataSource = null;
		JndiTemplate jndi = new JndiTemplate();
		try {
			dataSource = (DataSource) jndi.lookup(uloJndi);
		} catch (NamingException e) {
			log.error("NamingException for "+uloJndi, e);
		}
		log.debug("Instanciate ULO data source : "+uloJndi+" successfully!");
		return dataSource;
	}
}
