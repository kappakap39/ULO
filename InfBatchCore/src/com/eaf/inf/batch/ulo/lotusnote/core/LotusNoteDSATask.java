package com.eaf.inf.batch.ulo.lotusnote.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.file.FileControl;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.inf.batch.ulo.lotusnote.dao.LotusNoteDAO;
import com.eaf.inf.batch.ulo.lotusnote.dao.LotusNoteFactory;
import com.eaf.inf.batch.ulo.lotusnote.model.LotusNoteDataM;

public class LotusNoteDSATask implements TaskInf{
	private static transient Logger logger = Logger.getLogger(LotusNoteDSATask.class);
	String LOTUS_NOTE_DSA_PREFIX_FILENAME = InfBatchProperty.getInfBatchConfig("LOTUS_NOTE_DSA_PREFIX_FILENAME");
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			String LOTUS_NOTE_DSA_INPUT_PATH = PathUtil.getPath("LOTUS_NOTE_DSA_INPUT_PATH");
			logger.debug("LOTUS_NOTE_DSA_INPUT_PATH >> "+LOTUS_NOTE_DSA_INPUT_PATH);
			FileControl.mkdir(LOTUS_NOTE_DSA_INPUT_PATH);
			File dir = new File(LOTUS_NOTE_DSA_INPUT_PATH);
			File[] files = dir.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.contains(LOTUS_NOTE_DSA_PREFIX_FILENAME);
			    }
			});
			if(null != files){
				logger.debug("files >> "+files);
				for(File file : files){
					logger.debug("file >> "+file);
					BufferedReader br = null;
					try{
						br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
						String line = "";
						while((line = br.readLine()) != null){
							LotusNoteDataM lotusNote = mappingLotusNoteDataM(line);
							TaskObjectDataM taskObject = new TaskObjectDataM();
								taskObject.setUniqueId(lotusNote.getSaleId());
								taskObject.setObject(lotusNote);
							taskObjects.add(taskObject);
						}
					}catch(Exception e){
						logger.fatal("ERROR ",e);
						throw new TaskException(e.getLocalizedMessage());
					}finally{
						if(null != br){
							br.close();
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new TaskException(e.getLocalizedMessage());
		}
		logger.debug("taskOnjects size >> "+taskObjects.size());
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
			taskExecute.setUniqueId(task.getTaskId());
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
				LotusNoteDataM lotusNote = (LotusNoteDataM)taskObject.getObject();
				LotusNoteDAO lotusNoteDao = LotusNoteFactory.getLotusNoteDAO();
				lotusNoteDao.insertUpdateMSSaleInfo(lotusNote);
			taskExecute.setResponseObject(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
	
	private LotusNoteDataM mappingLotusNoteDataM(String line) throws Exception{
		//check size and offset in KBMF lotus note
		String saleId = line.substring(0, 8);
		String saleType = line.substring(8, 9);
		String teamId = line.substring(9, 13);
		String name = line.substring(13, 63).trim();
		String region = line.substring(63, 65);
		String zone = line.substring(65, 68);
		String teamName = line.substring(68, 108).trim();
		String status = line.substring(108, 109);
		LotusNoteDataM lotusNote = new LotusNoteDataM();
			lotusNote.setSaleId(saleId);
			lotusNote.setSaleType(Integer.parseInt(saleType));
			lotusNote.setTeamId(Integer.parseInt(teamId));
			lotusNote.setName(name);
			lotusNote.setRegion(Integer.parseInt(region));
			lotusNote.setZone(Integer.parseInt(zone));
			lotusNote.setTeamName(teamName);
			lotusNote.setStatus(Integer.parseInt(status));
		return lotusNote;
	}
}
