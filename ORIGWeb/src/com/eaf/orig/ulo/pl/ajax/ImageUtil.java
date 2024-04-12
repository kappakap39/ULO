package com.eaf.orig.ulo.pl.ajax;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.PixelInterleavedSampleModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import sun.awt.image.ByteInterleavedRaster;

import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;

public class ImageUtil{	
	private static ImageUtil me;	
	static Logger logger = Logger.getLogger(ImageUtil.class); 
	
	public static final class IMAGE_TYPE{
		public static final String PNG = "PNG";
	}
    public synchronized static ImageUtil getInstance() {
    	ImageIO.setUseCache(false);
        if (me == null) {
            me = new ImageUtil();
        }
        return me;
    }
    public BufferedImage readImage (String path) throws IOException{    	
//    	logger.debug("do readImage()..");    	
//    	return JAI.create("fileload", path).getAsBufferedImage();    	
    	return ImageIO.read(new File(path));
    }
    public void writeImage(BufferedImage image ,String path, String imageType)throws IOException{ 
    	ImageIO.write(image, imageType, new File(path));
    }
    
    public BufferedImage rotate90ToRight(BufferedImage inputImage){
    	int width = inputImage.getWidth();
    	int height = inputImage.getHeight();
    	BufferedImage returnImage = new BufferedImage(height,width,inputImage.getType());
    	for( int x = 0; x < width; x++ ) {
    		for(int y = 0; y < height; y++) {
    			returnImage.setRGB(height-y-1, x, inputImage.getRGB(x,y));
    		}
    	}
    	return returnImage;
    }
    
    public BufferedImage rotate90ToLeft( BufferedImage inputImage ){
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage(height,width,inputImage.getType() );
		for( int x = 0; x < width; x++ ) {
			for( int y = 0; y < height; y++ ) {
				returnImage.setRGB(y,width-x-1,inputImage.getRGB( x, y  ) );
			}
		}
		return returnImage;
    }
    
    
    public BufferedImage rotateImage(BufferedImage image,int angle) {
    	
//    	logger.debug("[ImageUtil]..[rotateImage]..angle "+angle);
    	
        if (image == null){throw new IllegalArgumentException("\"image\" param cannot be null.");}

        int imageWidth = image.getWidth();

        int imageHeight = image.getHeight();

        Map<String, Integer> boundingBoxDimensions = calculateRotatedDimensions(imageWidth, imageHeight, angle);

        int newWidth = boundingBoxDimensions.get("width");

        int newHeight = boundingBoxDimensions.get("height");

//        logger.debug("image.getType() "+image.getType());
        
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());

        Graphics2D newImageGraphic = newImage.createGraphics();

        AffineTransform transform = new AffineTransform();

        transform.setToTranslation((newWidth-imageWidth)/2, (newHeight-imageHeight)/2);

        transform.rotate(Math.toRadians(angle), imageWidth/2, imageHeight/2);

        newImageGraphic.drawImage(image, transform, null);

        newImageGraphic.dispose();
        
        return newImage;

    }
    /**
     * Takes a BufferedImage object, and if the color model is DirectColorModel, 
     * converts it to be a ComponentColorModel suitable for fast PNG writing. If 
     * the color model is any other color model than DirectColorModel, a 
     * reference to the original image is simply returned.
     * @param source The source image.
     * @return The converted image.
     */
    public static BufferedImage convertColorModelPNG(BufferedImage source) {
         if (!(source.getColorModel() instanceof DirectColorModel)){
              return source;
         }
         ICC_Profile newProfile = ICC_Profile.getInstance(ColorSpace.CS_sRGB);
         ICC_ColorSpace newSpace = new ICC_ColorSpace(newProfile);
         ComponentColorModel newModel = new ComponentColorModel(newSpace, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
         
         PixelInterleavedSampleModel newSampleModel = new PixelInterleavedSampleModel(DataBuffer.TYPE_BYTE, source.getWidth(), source.getHeight(), 4, source.getWidth() * 4, new int[] { 0, 1, 2, 3 });
         DataBufferByte newDataBuffer = new DataBufferByte(source.getWidth() * source.getHeight() * 4);
         ByteInterleavedRaster newRaster = new ByteInterleavedRaster(newSampleModel, newDataBuffer, new Point(0, 0));
         
         BufferedImage dest = new BufferedImage(newModel, newRaster, false, new Hashtable());

         int[] srcData = ((DataBufferInt)source.getRaster().getDataBuffer()).getData();
         byte[] destData = newDataBuffer.getData();
         int j = 0;
         byte argb = 0;
         for (int i = 0; i < srcData.length; i++){
              j = i * 4;
              argb = (byte)(srcData[i] >> 24);
              destData[j] = argb;
              destData[j + 1] = 0;
              destData[j + 2] = 0;
              destData[j + 3] = 0;
         }         
         return dest;
    }
    
    private Map<String, Integer> calculateRotatedDimensions(int imageWidth,int imageHeight,int angle) {

	    Map<String, Integer> dimensions = new HashMap<String, Integer>();
	
	    int[][] points = {{0, 0},	{imageWidth, 0},{0, imageHeight},{imageWidth, imageHeight}};
	
	    Map<String, Integer> boundBox = new HashMap<String, Integer>(){{	
	        put("left", 0);	
	        put("right", 0);	
	        put("top", 0);	
	        put("bottom", 0);
	
	     }};
	
	    double theta = Math.toRadians(angle);
	
	    for (int[] point : points) {	
	    	 int x = point[0];	
	    	 int y = point[1];	
	    	 int newX = (int) (x * Math.cos(theta) + y * Math.sin(theta));	  
	    	 int newY = (int) (x * Math.sin(theta) + y * Math.cos(theta));
	
	    	 //assign the bounds	
	         boundBox.put("left", Math.min(boundBox.get("left"), newX));	
	         boundBox.put("right", Math.max(boundBox.get("right"), newX));	
	         boundBox.put("top", Math.min(boundBox.get("top"), newY));	
	         boundBox.put("bottom", Math.max(boundBox.get("bottom"), newY));
	    }
	
	    dimensions.put("width", Math.abs(boundBox.get("right") - boundBox.get("left")));
	 
	    dimensions.put("height", Math.abs(boundBox.get("bottom") - boundBox.get("top")));
   
	    return dimensions;    
    }    
    public BufferedImage waterMarkImage(BufferedImage image,String text) {

        if (image == null){throw new IllegalArgumentException("\"image\" param cannot be null.");}

        // create a new image

        BufferedImage waterMarked = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        Graphics2D imageG = waterMarked.createGraphics();

        // draw original image.
 
    	        imageG.drawImage(image, null, 0, 0);
 
    	        imageG.dispose();

    	Graphics2D g  = waterMarked.createGraphics();

    	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% transparency

    	        g.setColor(Color.white);

    	        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    	        g.setFont(new Font("Arial", Font.BOLD, 30));
  
    	 FontMetrics fontMetrics = g.getFontMetrics();
 
    	 Rectangle2D rect = fontMetrics.getStringBounds(text, g);

    	 int centerX = (image.getWidth() - (int) rect.getWidth()) /2;

    	 int centerY = (image.getHeight() - (int) rect.getHeight()) /2;

    	 g.drawString(text, centerX, centerY);
    
    	 g.dispose();
    
    	 return waterMarked;  
    }
    
	public static String IMAGEPath(String type ,PLApplicationImageSplitDataM splitM){
		StringBuilder PATH = new StringBuilder("");
			PATH.append(ORIGConfig.IMAGE_FILE_PATH);
			PATH.append(splitM.getRealPath());
			PATH.append((type).toLowerCase());
			PATH.append(File.separator);
			String FILE_NAME = splitM.getFileName();
			PATH.append(FILE_NAME);
			String IMAGE_TYPE = splitM.getFileType();
			if("TIFF".equals(IMAGE_TYPE) || "TIF".equals(IMAGE_TYPE)){
				IMAGE_TYPE = "PNG";
			}
			PATH.append(".");
			PATH.append(IMAGE_TYPE);
		logger.debug("IMAGEPath >> "+PATH);
		return PATH.toString();
	}
	
	public static String IMAGEPath(String type ,String consentRefNo,PLApplicationImageSplitDataM splitM){
		StringBuilder PATH = new StringBuilder("");
			PATH.append(ORIGConfig.IMAGE_FILE_PATH);
			PATH.append(splitM.getRealPath());
			PATH.append((type).toLowerCase());
			PATH.append(File.separator);
			PATH.append(consentRefNo);
			PATH.append(File.separator);
			String FILE_NAME = splitM.getFileName();
			PATH.append(FILE_NAME);
			String IMAGE_TYPE = splitM.getFileType();
			if("TIFF".equals(IMAGE_TYPE) || "TIF".equals(IMAGE_TYPE)){
				IMAGE_TYPE = "PNG";
			}
			PATH.append(".");
			PATH.append(IMAGE_TYPE);
		logger.debug("IMAGEPath >> "+PATH);
		return PATH.toString();
	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if(!sourceFile.exists()){
			return;
		}
		if(!destFile.exists()){
			new File(destFile.getParent()).mkdirs();
			destFile.createNewFile();
		}
		FileChannel source = null;
		FileChannel destination = null;
		source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
			destination.transferFrom(source, 0, source.size());
		}
		if (source != null) {
			source.close();
		}
		if (destination != null) {
			destination.close();
		}
	}
	
}
