package flp.model.report;

import java.sql.Timestamp;

public class AsyncMapObject {

	 private String activityType;
     private Timestamp requestTime;
     private Timestamp finishTime;

     public String getActivityType() {
         return activityType;
     }
     
     public void setActivityType(String activityType) {
         this.activityType = activityType;
     }
     public void setRequestTime(Timestamp requestTime) {
         this.requestTime = requestTime;
     }
     public void setFinishTime(Timestamp finishTime) {
         this.finishTime = finishTime;
     }
     public Timestamp getRequestTime() {
		return requestTime;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public boolean isFoundPair() {
         return requestTime != null && finishTime != null;
     }
     
     public double getDuration() {
         return (finishTime.getTime() - requestTime.getTime()) / 1000d;
     }

}
