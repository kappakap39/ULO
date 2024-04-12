package com.eaf.orig.rest.controller.smartdata.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.rest.controller.smartdata.model.SMImgM;

/**Assist with Template mapping for Image Splits. Allow the process to find matching template for each image
 * and map some values to enable SmartDataEntry engine.
 * 
 * @author TOP
 *
 */
public class SmartConditionResolver {
	static final transient Logger log = Logger.getLogger(SmartConditionResolver.class);
	private static final String SEPARATOR = "|";
	protected Map<String, SMImgM> imageMap;
	protected String[] conditinalFields;
	protected int docTypeCount = 1;
	protected int docTypeSeqCount = 1;
	protected int personalTypeCount = 1;
	String SMART_DATA_DOCTYPE_APPFORM = SystemConstant.getConstant("SMART_DATA_DOCTYPE_APPFORM");	
	
	public SmartConditionResolver(Collection<SMImgM> templateImages){
		//Validation
		if(templateImages == null || templateImages.isEmpty()){
			log.debug("SmartConditionResolver constructor : template images are null!");
			return;
		}

		//Construct key
		imageMap = new HashMap<String, SMImgM>();
		for(SMImgM image : templateImages){
			if(image == null)continue;
			String key = generateTemplateKey(image);
			imageMap.put(key, image);
		}
	}
	
	public SmartConditionResolver(Collection<SMImgM> templateImages, String...conditionalFields){
		//Validation
		if(templateImages == null || templateImages.isEmpty()){
			log.debug("SmartConditionResolver constructor : template images are null!");
			return;
		}
		if(conditionalFields == null || conditionalFields.length == 0){
			log.debug("SmartConditionResolver constructor : conditional fields are null!");
			return;
		}
		
		this.conditinalFields = conditionalFields;
		//Construct key
		imageMap = new HashMap<String, SMImgM>();
		for(SMImgM image : templateImages){
			if(image == null)continue;
			String key = generateKeyByCondition(image, conditionalFields);
			imageMap.put(key, image);
		}
	}
	
	/**For further use.... not applicable for current Business Model
	 * @param image
	 * @param conditionalFields
	 * @return
	 */
	private String generateKeyByCondition(SMImgM image, String...conditionalFields){
		if(image == null)return null;
		if(conditionalFields == null || conditionalFields.length == 0)return null;
		
		Class<? extends SMImgM> clazz = image.getClass();
		StringBuilder result = new StringBuilder();
		String separator = "";
		for(int i = 0, len = conditionalFields.length; i < len; i++){
			String fieldName = conditionalFields[i];
			if(fieldName == null)continue;			
			try{
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				String value = null;
				try {
					Object valueObj = field.get(image);
					value = valueObj == null? null : valueObj.toString();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.warn("generateKeyByCondition() Error unable to get field value of field name : "+fieldName+" not found!");
					e.printStackTrace();
				}
				result.append(separator).append(value);
				separator = SEPARATOR;
			}catch(NoSuchFieldException | SecurityException e){
				log.warn("generateKeyByCondition() Error field name : "+fieldName+" not found!");
				e.printStackTrace();
			}
		}		
		return result.toString();
	}
	
	/**Unique key of images is combination of Document type + Document Seq + Personal Type
	 * @param image
	 * @return List of possible keys ordered by less information to rich information
	 */
	private List<String> generateKeyByDefault(SMImgM image){
		if(image == null)return null;		
		List<String> result = new ArrayList<String>();
		StringBuilder key = new StringBuilder();		
		//Prepare data
		String docType = image.getDoctypeid() == null?"TEMP_DOC_"+(docTypeCount++) : image.getDoctypeid();//Generate temp so it can never be matched
		String docTypeSeq = image.getDocTypeSeq() == null?"TEMP_DOC_SEQ_"+(docTypeSeqCount++) : String.valueOf(image.getDocTypeSeq());
		String personalType = image.getPersonalType();		
		//Set result
		key.append(docType).append(SEPARATOR).append(docTypeSeq);
		result.add(key.toString());
		if(!Util.empty(personalType)&&!SMART_DATA_DOCTYPE_APPFORM.equals(docType)){
			key.append(SEPARATOR).append(personalType);
			result.add(key.toString());
		}		
		return result;
	}
	
	private String generateTemplateKey(SMImgM image){
		if(image == null)return null;		
		StringBuilder result = new StringBuilder();		
		//Prepare data
		String docType = image.getDoctypeid() == null?"TEMP_DOC_"+(docTypeCount++) : image.getDoctypeid();//Generate dummy key so it can never be matched
		String docTypeSeq = image.getDocTypeSeq() == null?"TEMP_DOC_SEQ_"+(docTypeSeqCount++) : String.valueOf(image.getDocTypeSeq());
		String personalType = image.getPersonalType();		
		//Set result
		result.append(docType).append(SEPARATOR).append(docTypeSeq);
		if(!Util.empty(personalType)&&!SMART_DATA_DOCTYPE_APPFORM.equals(docType)){
			result.append(SEPARATOR).append(personalType);
		}
		return result.toString();
	}
	
	public SMImgM getTemplateByRequestImage(SMImgM requestObj){
		if(requestObj == null){
			return null;
		}		
		List<String> keys = generateKeyByDefault(requestObj);
		if(keys == null || keys.isEmpty())
			return null;
		for(String key : keys){
			SMImgM result =  imageMap.get(key);
			if(result != null){
				return result;
			}
		}
		return null;
	}	
	public void mapRequestImagesWithTemplateImage(Collection<SMImgM> requestImage){
		if(requestImage == null || requestImage.isEmpty()){
			log.info("mapRequestImagesWithTemplateImage() : Requested image splits are empty!");
			return;
		}
		if(imageMap == null || imageMap.isEmpty()){
			//not have Template will auto create SmartPageNo
			int maxPageNo=1;
			for(SMImgM image : requestImage){
				image.setSmartPageNo(maxPageNo);
				maxPageNo++;
			}
			log.info("mapRequestImagesWithTemplateImage() : Template Images are empty!");
			return;
		}		
		int maxPageNo = Math.max(requestImage.size(), imageMap.size());
		for(SMImgM image : requestImage){
			if(image == null)continue;
			SMImgM template = getTemplateByRequestImage(image);
			++maxPageNo;
			if(template == null){
				log.debug("mapRequestImagesWithTemplateImage() no matching template of image : "+image);
				image.setSmartPageNo(maxPageNo);
				continue;
			}			
			image.setSmartPageNo(template.getPagenumber());
		}
	}
}
