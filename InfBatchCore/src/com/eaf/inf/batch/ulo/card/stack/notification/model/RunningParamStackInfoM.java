package com.eaf.inf.batch.ulo.card.stack.notification.model;

import java.io.Serializable;

import com.eaf.core.ulo.common.cont.InfBatchConstant;

@SuppressWarnings("serial")
public class RunningParamStackInfoM implements Serializable, Cloneable {

	private String processType;
	private String paramType;
	private Integer numOfParamType;
	private ProcessResponse processResponse;

	public RunningParamStackInfoM(String processType, String paramType, Integer numOfParamType) {

		this.paramType = paramType;
		this.processType = processType;
		this.numOfParamType = numOfParamType;
		processResponse = new ProcessResponse(InfBatchConstant.Status.SUCCESS, InfBatchConstant.StatusDesc.SUCCESS);

	}

	public RunningParamStackInfoM() {
	}

	/**
	 * @return the numOfParamType
	 */
	public Integer getNumOfParamType() {
		return numOfParamType;
	}

	/**
	 * @param numOfParamType the numOfParamType to set
	 */
	public void setNumOfParamType(Integer numOfParamType) {
		this.numOfParamType = numOfParamType;
	}

	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RunningParamStackInfoM [processType=");
		builder.append(processType);
		builder.append(", numOfParamType=");
		builder.append(numOfParamType);
		builder.append("]");
		return builder.toString();
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

}
