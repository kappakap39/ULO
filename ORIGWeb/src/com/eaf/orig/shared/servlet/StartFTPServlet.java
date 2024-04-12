package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.services.adapter.transferfile.FTPDao;
import com.eaf.services.adapter.transferfile.FTPDaoImpl;

public class StartFTPServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(CheckMatchCampaignSchemeServlet.class);
	public StartFTPServlet() {
		super();
	}

	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
	    doPost(arg0,arg1);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            logger.debug("Start FTPServlet");
            
            FTPDao dao = new FTPDaoImpl();
            dao.startGetFiles();
        }catch (Exception e) {
            logger.error("Error FTPServlet >> ", e);
        }
	}

}