package com.ava.dynamic.model.widget;

import com.ava.dynamic.config.WidgetConstant.OnlineStatus;

public class TeamPerformanceDetail {

	/**These type classes will append html class attribute of output indicator
	 * @author TOP
	 *
	 */
	public static enum OutputTypeClass {
		UP("fa fa-caret-up text-success icon-direction"), DOWN("fa fa-caret-down text-danger icon-direction"), STABLE("fa fa-caret-up text-muted icon-direction");
		String value;

		private OutputTypeClass(String val) {
			value = val;
		}
	}
	
	public static enum PerformanceClass{
		UP("text-success"), DOWN("text-danger"), STABLE("");
		String value;
		private PerformanceClass(String val) {
			value = val;
		}
	}
	
	public static enum OnlineStatusClass{
		ONLINE("fa fa-user text-success"), OFFLINE("fa fa-user");
		String value;
		private OnlineStatusClass(String val) {
			value = val;
		}
	}
	
	private String userId;
	private String name;
	private Integer input;
	private Integer assign;
	private Integer output;
	private Integer target;
	private Integer performance;
	private String performanceClass;
	private String averageWorkingTime;
	private String status;
	private String statusClass;
    private String teamName;    
	private String outputClass;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getInput() {
		return input;
	}

	public void setInput(Integer input) {
		this.input = input;
	}

	public Integer getAssign() {
		return assign;
	}

	public void setAssign(Integer assign) {
		this.assign = assign;
	}

	public Integer getOutput() {
		return output;
	}

	public void setOutput(Integer output) {
		this.output = output;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer getPerformance() {
		return performance;
	}

	public void setPerformance(Integer performance) {
		this.performance = performance;
	}

	public String getAverageWorkingTime() {
		return averageWorkingTime;
	}

	public void setAverageWorkingTime(String averageWorkingTime) {
		this.averageWorkingTime = averageWorkingTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getOutputClass() {
		if(this.performance > 0){
			outputClass = OutputTypeClass.UP.value;
		}
		else if(this.performance < 0){
			outputClass = OutputTypeClass.DOWN.value;
		}
		else{
			outputClass = OutputTypeClass.STABLE.value;
		}			
		
		return outputClass;
	}

	public void setOutputClass(String outputClass) {
		this.outputClass = outputClass;
	}

	public String getPerformanceClass() {
		if(this.performance > 0){
			performanceClass = PerformanceClass.UP.value;
		}
		else if(this.performance < 0){
			performanceClass = PerformanceClass.DOWN.value;
		}
		else{
			performanceClass = PerformanceClass.STABLE.value;
		}	
		return performanceClass;
	}

	public void setPerformanceClass(String performanceClass) {
		this.performanceClass = performanceClass;
	}

	public String getStatusClass() {
		if(status != null && status.equalsIgnoreCase(OnlineStatus.ONLINE)){
			statusClass = OnlineStatusClass.ONLINE.value;
		}else{
			statusClass = OnlineStatusClass.OFFLINE.value;
		}
		return statusClass;
	}

	public void setStatusClass(String statusClass) {
		this.statusClass = statusClass;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TeamPerformance [getUserId()=");
		builder.append(getUserId());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getInput()=");
		builder.append(getInput());
		builder.append(", getAssign()=");
		builder.append(getAssign());
		builder.append(", getOutput()=");
		builder.append(getOutput());
		builder.append(", getTarget()=");
		builder.append(getTarget());
		builder.append(", getPerformance()=");
		builder.append(getPerformance());
		builder.append(", getAverageWorkingTime()=");
		builder.append(getAverageWorkingTime());
		builder.append(", getStatus()=");
		builder.append(getStatus());
		builder.append(", getTeamName()=");
		builder.append(getTeamName());
		builder.append(", getOutputClass()=");
		builder.append(getOutputClass());
		builder.append(", getPerformanceClass()=");
		builder.append(getPerformanceClass());
		builder.append(", getStatusClass()=");
		builder.append(getStatusClass());
		builder.append("]");
		return builder.toString();
	}

}
