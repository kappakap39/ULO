package com.eaf.core.ulo.common.img;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import com.eaf.core.ulo.common.api.InfBatchManager;
import com.imagero.reader.tiff.TiffReader;
import com.imagero.reader.tiff.TiffUtils;

public class TIFFConvertUtil {
	private static transient Logger logger = Logger.getLogger(TIFFConvertUtil.class);
	static {
	  	System.setProperty("com.sun.media.jai.disableMediaLib", "true"); 
		System.setProperty("com.sun.media.imageio.disableCodecLib", "true");
	}
	public static void main(String[] args) {
		logger.debug("TIFFConvertUtil..start");
		InfBatchManager.init();
		processAction();
		logger.debug("TIFFConvertUtil..finish");
	}
	public static void processAction(){
		String imageTemplatePath = "C:\\WorkSpace\\FLP_WorkSpace\\ImageTemplate";
		String templateCode = "QR-000000000004"+File.separator+"UniqueSmart"+File.separator+"CCGeneric10";
		String templateFile = "APPLI_FORM-001.tif";
		String templatePath = imageTemplatePath+"\\"+templateCode+"\\template\\"+templateFile;
		String outputPath = imageTemplatePath+"\\"+templateCode;
		logger.debug("templatePath >> "+templatePath);
		logger.debug("outputPath >> "+outputPath);
		TIFFConvert(templatePath,outputPath,"PNG",templateFile);
		System.gc();
	}
	private static void TIFFConvert(String inputPath,String outputPath,String outputType,String templateFile){
		try{
			String[] templateSmart = templateFile.replace(".tif","").split("\\-");		
			String docGroup = templateSmart[0];
			String docCode = templateSmart[1];
			logger.debug("docGroup >> "+docGroup);
			logger.debug("docCode >> "+docCode);
			String imageId = String.valueOf(System.currentTimeMillis());
			TiffReader tiffReader = new TiffReader(new File(inputPath));
			File output = new File(outputPath+File.separator+"tiff");
			if(!output.exists()){
				output.mkdir();
			}
			TiffUtils.split(tiffReader,output,imageId,1,0x4949);
			int page = tiffReader.getImageCount();
			logger.debug("page >> "+page);
			for (int i = 1; i <= page; i++) {
				try {
					int index = i;
					String tiffFile = outputPath+File.separator+"tiff"+File.separator+imageId+i+".tif";
					if(i == 9){
						index = 1;
						docGroup = "ID_DOC";
						docCode = "003";
					}else if(i == 10){
						index = 1;
						docGroup = "INCOME_DOC";
						docCode = "031";
					}
					String imageFileName = getImageFileName(docGroup,docCode,index)+"-"+i;
					String imageFile =  outputPath+File.separator+imageFileName+".png";
					logger.debug("tiffFile >> "+tiffFile);
					logger.debug("imageFile >> "+imageFile);
					ImageTo(outputType,tiffFile,imageFile,1);
					String _thumb =  outputPath+File.separator+imageFileName+"thumb.png";
					createThumbnail(imageFile, _thumb, 90, outputType);
				} catch (Exception e) {
					logger.fatal("ERROR",e);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}
	public static String getImageFileName(String docGroup,String docCode,int index){
		return String.valueOf(System.currentTimeMillis())+"-"+docGroup+"-"+docCode+"-"+String.valueOf(index);
	}
	public static void createThumbnail(String inputImage, String outputImage,int w,String outputType) throws IOException {
		BufferedImage src = ImageIO.read(new File(inputImage));
		BufferedImage thumbnail = Scalr.resize(src, w);
		ImageIO.write(thumbnail,outputType,new FileOutputStream(outputImage));
	}
	public static void ImageTo(String fileType, String inFile, String outFile, float zoom) {
		ParameterBlock pb = (new ParameterBlock()).add(inFile);
		RenderedImage src = JAI.create("fileload", pb);
		RenderedImage transformedImage;
		ParameterBlock pb2 = new ParameterBlock();
			pb2.addSource(src);
			pb2.add(zoom);
			pb2.add(zoom);
			pb2.add(.0f);
			pb2.add(.0f);
		transformedImage = JAI.create("scale", pb2, null);
		RenderedOp op = JAI.create("filestore", transformedImage, outFile, fileType);
		op = null;
		transformedImage = null;
		src = null;
		pb2 = null;
	}
}
