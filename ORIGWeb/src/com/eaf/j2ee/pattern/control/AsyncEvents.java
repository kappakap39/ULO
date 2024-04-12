package com.eaf.j2ee.pattern.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


@WebServlet(value="/AsyncEvents",loadOnStartup=-1,asyncSupported=true)
public class AsyncEvents extends HttpServlet {	
	private static Logger logger = Logger.getLogger(AsyncEvents.class);
	private static final long serialVersionUID = 1L;
    public AsyncEvents() {
        super();
    }   
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String textMsg = request.getParameter("textMsg");
		logger.debug("textMsg >> "+textMsg);
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(textMsg);
	}
}