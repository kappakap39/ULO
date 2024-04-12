package com.eaf.orig.ulo.app.view.util.pa;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage; 
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import com.eaf.orig.shared.dao.OrigObjectDAO;

public class StampDutyUtil 
{
	private static Logger logger = Logger.getLogger(StampDutyUtil.class);
	
	public static PDDocument loadPDF(String contextPath, String appGroupNo)
	{
		//Loading an existing document
		
		File file = new File(contextPath + "/pdf/stamp_duty.pdf");
    	PDDocument document = null;
    	boolean stampDataExists = false;
    	
		try 
		{
			document = PDDocument.load(file); 
	    	PDDocumentCatalog docCatalog = document.getDocumentCatalog();
	        PDAcroForm acroForm = docCatalog.getAcroForm();
	        
	        //add embedded fonts to default resources
	        //acroForm.setDefaultResources(prepareEmbeddedFont(acroForm, document));
	        
	        PDResources res = acroForm.getDefaultResources();
	        PDFont addfont = PDType0Font.load(document, new File(contextPath+"/fonts/angsanaUPC.ttf"));
	        String fontName = res.add(addfont).getName();
	        String defaultAppearanceString = "/" + fontName + " 0 Tf 0 g"; // adjust to replace existing font name
	        acroForm.setDefaultAppearance(defaultAppearanceString);
	        
	        //Get value from LOAN_SETUP_CLAIM/LOAN_SETUP_STAMP_DUTY
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;

			OrigObjectDAO orig = new OrigObjectDAO();
			
			try{
				conn = orig.getConnection();
				StringBuilder sql = new StringBuilder("");
				sql.append("SELECT * FROM LOAN_SETUP_CLAIM LSC ");
				sql.append(" JOIN LOAN_SETUP_STAMP_DUTY LSSD ");
				sql.append(" ON LSC.STAMP_DUTY_ID = LSSD.STAMP_DUTY_ID ");
				sql.append(" WHERE APPLICATION_GROUP_NO = ?");
				String dSql = String.valueOf(sql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, appGroupNo);
				logger.debug("getAccountSetupClaimStatus : " + appGroupNo);
				rs = ps.executeQuery();
				
		        logger.debug("Set stamp duty data to PDF fields");
		        while(rs.next())
		        {
		        	stampDataExists = true;
				    //Set value to each field
				    setFieldValue(acroForm,"Text1.4","บมจ.ธนาคารกสิกรไทย");
				    setFieldValue(acroForm,"Tin1","0 1075 36000 31 5");
				        
				    setFieldValue(acroForm,"Text1.5","400/22");
				    setFieldValue(acroForm,"Text1.6","-");
				    setFieldValue(acroForm,"Text1.7","-");
				    setFieldValue(acroForm,"Text1.8","พหลโยธิน");
				    setFieldValue(acroForm,"Text1.9","สามเสนใน");
				    setFieldValue(acroForm,"Text1.10","พญาไท");
				    setFieldValue(acroForm,"Text1.11","กรุงเทพมหานคร");
				    setFieldValue(acroForm,"Text1.12","10400");
				    setFieldValue(acroForm,"Text1.13","02-273-2126");
				    setFieldValue(acroForm,"Text1.14","5 ,17, 23");
				      
				    setFieldValue(acroForm,"Text1.15",rs.getString("PERIOD_NO"));
				    setFieldValue(acroForm,"Text1.16",rs.getString("PERIOD_DATE_FROM"));
				    setFieldValue(acroForm,"Text1.17",rs.getString("PERIOD_DATE_TO"));
				    setFieldValue(acroForm,"Text1.18",getThaiMonth(rs.getString("PERIOD_MONTH")));
				    setFieldValue(acroForm,"Text1.19",rs.getString("PERIOD_YEAR"));
				        
				    setFieldValue(acroForm,"Text1.20",rs.getString("STAMP_DUTY_FEE"));
				    setFieldValue(acroForm,"Text1.22","พญาไทย");
				        
				    setFieldValue(acroForm,"Text2.1","1");
				    setFieldValue(acroForm,"Text2.2",rs.getString("TH_FIRST_NAME") + " " + rs.getString("TH_LAST_NAME"));
				        
				    setFieldValue(acroForm,"Text2.3",rs.getString("STAMP_DUTY_ID"));
				    setFieldValue(acroForm,"Text2.4",rs.getString("STAMP_DUTY_ID"));
				    setFieldValue(acroForm,"Text2.5","1");
				    
				    if(rs.getString("FINAL_LOAN_AMT") != null)
				    {
				    	String[] flas = rs.getString("FINAL_LOAN_AMT").split("\\.");
				    	setFieldValue(acroForm,"Text2.6",flas[0]);
				    	setFieldValue(acroForm,"Text2.7",(flas.length > 1) ? flas[1] : "00");
				    }
				    
				    if(rs.getString("STAMP_DUTY_FEE") != null)
				    {
				    	String[] sdfs = rs.getString("STAMP_DUTY_FEE").split("\\.");
				    	setFieldValue(acroForm,"Text2.8",sdfs[0]);
						setFieldValue(acroForm,"Text2.9",(sdfs.length > 1) ? sdfs[1] : "00");
						
						setFieldValue(acroForm,"Text17.4",sdfs[0]);
						setFieldValue(acroForm,"Text17.5",(sdfs.length > 1) ? sdfs[1] : "00");
				    }
				    
				    if(rs.getString("FINAL_LOAN_AMT") != null)
				    {
				    	String[] flas = rs.getString("FINAL_LOAN_AMT").split("\\.");
				    	setFieldValue(acroForm,"Text17.2",flas[0]);
						setFieldValue(acroForm,"Text17.3",(flas.length > 1) ? flas[1] : "00");
				    }
				    
					setFieldValue(acroForm,"Text24",rs.getString("REQUESTOR_NAME"));
					setFieldValue(acroForm,"Text25",rs.getString("REQUESTOR_POSITION"));
					
		        }
		        
			}catch(Exception e)
			{
				logger.fatal(e.getLocalizedMessage());
			}finally
			{
				try{
					orig.closeConnection(conn, rs, ps);
				}catch(Exception e){
					logger.fatal(e.getLocalizedMessage());
				}
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    	
    	/*File tempFile;
    	String tempFilePath = null;
		try 
		{   
			tempFile = File.createTempFile("tempPDF_", ".pdf");
			tempFilePath = tempFile.getAbsolutePath();
			logger.debug("tempFilePath " + tempFilePath);
			document.save(tempFilePath);
			document.close();
		}
    	catch (IOException e) 
    	{
			e.printStackTrace();
		} */
		
		if(document != null && !stampDataExists)
		{
			try {document.close();} catch (IOException e) {logger.error(e.getMessage());}
		}
    	
		return stampDataExists ? document : null;
	}
	
	public static void setFieldValue(PDAcroForm acroForm,String fieldName , String value)
	{
		logger.debug("setFieldValue - fieldName : " + fieldName + " - value : " + value);
		try
		{
			PDTextField textField = (PDTextField) acroForm.getField(fieldName);
			if(isThai(value))
			{textField.setDefaultAppearance(acroForm.getDefaultAppearance());}
			textField.setValue(value);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}
	
	public static void addText(PDDocument document, String text, int pageNo, float x, float y)
	{
		 //Retrieving the pages of the document 
		  System.out.println("addText to pageNo : " + pageNo);
	      PDPage page = document.getPage(pageNo);
	      PDPageContentStream contentStream;
	      
		try 
		{
			contentStream = new PDPageContentStream(document, page);
			
		    //Begin the Content stream 
		    contentStream.beginText(); 
		       
		    //Setting the font to the Content stream  
		    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
	
		    //Setting the position for the line 
		    contentStream.newLineAtOffset(x, y);
		    //Adding text in the form of string 
		    contentStream.showText(text);      
	
		    //Ending the content stream
		    contentStream.endText();
	
		    System.out.println("Content added");
	
		    //Closing the content stream
		    contentStream.close();
	      
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

	}
	
	public static void list(PDField field)
	{
	    System.out.println(field.getFullyQualifiedName());
	    //System.out.println(field.getPartialName());
	    if (field instanceof PDNonTerminalField)
	    {
	        PDNonTerminalField nonTerminalField = (PDNonTerminalField) field;
	        for (PDField child : nonTerminalField.getChildren())
	        {
	            list(child);
	        }
	    }
	    else
	    {
        	System.out.println(field.getFullyQualifiedName() + " # " + field.getFieldType());
        	if(field.getFieldType() == null || !field.getFieldType().equals("Btn"))
        	{
        		try 
        		{
        			
					field.setValue(field.getFullyQualifiedName());
				} catch (IOException e) 
        		{
					e.printStackTrace();
				}
        	}
	    }
	}
	
	public static String getThaiMonth(String monthNo)
	{
		if(monthNo == null)
		{return "";}
		
		switch(monthNo)
		{
			case"1" : monthNo = "มกราคม";
			 break;
			case"2" : monthNo = "กุมภาพันธ์";
			 break;
			case"3" : monthNo = "มีนาคม";
			 break;
			case"4" : monthNo = "เมษายน";
			 break;
			case"5" : monthNo = "พฤษภาคม";
			 break;
			case"6" : monthNo = "มิถุนายน";
			 break;
			case"7" : monthNo = "กรกฎาคม";
			 break;
			case"8" : monthNo = "สิงหาคม";
			 break;
			case"9" : monthNo = "กันยายน";
			 break;
			case"10" : monthNo = "ตุลาคม";
			 break;
			case"11" : monthNo = "พฤศจิกายน";
			 break;
			case"12" : monthNo = "ธันวาคม";
			 break;
			default: monthNo = "";
			break;
		}
		
		return monthNo;
	}
	
	public static String getContextURL(HttpServletRequest req)
	{
		StringBuffer url = req.getRequestURL();
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
		return base;
	}
	
	public static boolean isThai(String text)
	{
		//match if text contains atleast 1 Thai char
		String regExp = "^.*([\\u0E01-\\u0E2E\\u0E40\\u0E41\\u0E42\\u0E43\\u0E44]).*$";
		if(text.matches(regExp))
		{return true;}
		else
		{return false;}
	}
	
	public static PDResources prepareEmbeddedFont(PDAcroForm acroForm, PDDocument document)
	{
		 	PDResources res = acroForm.getDefaultResources();
		    if (res == null)
		    {res = new PDResources();}
		    
	        PDPage page = document.getPage(0);
	        
	        PDResources resources = page.getResources();
	        Iterable<COSName> ite = resources.getFontNames();
	        for ( COSName name : ite) {
	            PDFont font = null;
				try 
				{
					font = resources.getFont(name);
					res.add(font);
				} catch (IOException e) 
				{
					logger.error(e.getMessage());
				}
	        }
		    
		    res = acroForm.getDefaultResources();
		    Iterable<COSName> ite2 = res.getFontNames();
	        for ( COSName name : ite2) {
	        	try 
	            {
					PDFont font = res.getFont(name);
					logger.debug(name + " # " + font.getName() + " # " + font.isEmbedded());
				} catch (IOException e)
				{
					logger.error(e.getMessage());
				}
	        }    
	        
	        return res;
	}
	
}
