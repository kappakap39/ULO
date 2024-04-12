package com.eaf.inf.batch.ulo.memo.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class InstructionMemoOutputDataM implements Serializable,Cloneable{
	private String outputFile;
	private String outputPath;
	private boolean generateResult;
	public String getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}
	public boolean isGenerateResult() {
		return generateResult;
	}
	public void setGenerateResult(boolean generateResult) {
		this.generateResult = generateResult;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}	
}
