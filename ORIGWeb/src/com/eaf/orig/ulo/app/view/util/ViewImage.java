package com.eaf.orig.ulo.app.view.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;

@SuppressWarnings("serial")
@WebServlet("/ViewImage")
public class ViewImage extends HttpServlet {

	private static transient Logger logger = Logger.getLogger(ViewImage.class);
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String IMAGE_PLUGIN_URL = SystemConfig.getProperty("IMAGE_PLUGIN_URL");
		String IM_IMAGE_PART = SystemConfig.getProperty("IM_IMAGE_PART");
		String IMAGE_CONTENT_TYPE = SystemConfig.getProperty("IMAGE_CONTENT_TYPE");
		String imageId = request.getParameter("imageId");
		String imageSize = request.getParameter("imageSize");
		response.setContentType(IMAGE_CONTENT_TYPE);
		logger.debug("imageId : "+imageId);
		logger.debug("imageSize : "+imageSize);
		if(!Util.empty(imageId)){
			OutputStream outputStream = response.getOutputStream();
			try {
				String imageURL = IMAGE_PLUGIN_URL+IM_IMAGE_PART.replace("{ImageSize}",imageSize)
						.replace("{IMInternalID}",FormatUtil.displayText(imageId));
				logger.debug("imageURL : "+imageURL);
				RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
					@Override
					protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
				        if(connection instanceof HttpsURLConnection ){
				            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
								@Override
								public boolean verify(String arg0, SSLSession arg1) {
									return true;
								}
							});
				        }
						super.prepareConnection(connection, httpMethod);
					}
				});
		        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(imageURL, HttpMethod.GET, null,byte[].class);
				byte[] imgByte = responseEntity.getBody();
				outputStream.write(imgByte, 0,imgByte.length);
				outputStream.flush();
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}finally{
				if(null!=outputStream) outputStream.close();
			}
		}
	}
	
}
