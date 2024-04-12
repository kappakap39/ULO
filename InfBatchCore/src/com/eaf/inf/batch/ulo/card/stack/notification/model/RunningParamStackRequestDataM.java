package com.eaf.inf.batch.ulo.card.stack.notification.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class RunningParamStackRequestDataM implements Serializable, Cloneable {

	private String paramType;
	private ProcessResponse processResponse;
	private ArrayList<RunningParamStackInfoM> runningParamStackInfoM;

	public RunningParamStackRequestDataM(String paramType) {
		this.paramType = paramType;
		runningParamStackInfoM = new ArrayList<RunningParamStackInfoM>();
	}

	/**
	 * @return the paramType
	 */
	public String getParamType() {
		return paramType;
	}

	/**
	 * @param paramType the paramType to set
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public RunningParamStackRequestDataM() {
	}

	/**
	 * @return the runningParamStackInfoM
	 */
	public ArrayList<RunningParamStackInfoM> getRunningParamStackInfoM() {
		return runningParamStackInfoM;
	}

	/**
	 * @param runningParamStackInfoM the runningParamStackInfoM to set
	 */
	public void setRunningParamStackInfoM(ArrayList<RunningParamStackInfoM> runningParamStackInfoM) {
		this.runningParamStackInfoM = runningParamStackInfoM;
	}

	public void add(RunningParamStackInfoM runningParamStackInfoM) {
		this.runningParamStackInfoM.add(runningParamStackInfoM);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RunningParamStackInfoM [paramType=");
		builder.append(paramType);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the processResponse
	 */
	public ProcessResponse getProcessResponse() {
		return processResponse;
	}

	/**
	 * @param processResponse the processResponse to set
	 */
	public void setProcessResponse(ProcessResponse processResponse) {
		this.processResponse = processResponse;
	}

}
