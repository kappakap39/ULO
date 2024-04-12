package flp.controller;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import flp.model.report.AsyncMapObject;
import flp.model.report.ResponseStat;
import flp.model.report.StatMapObject;
import flp.model.report.StatType;
import flp.model.report.TimeType;
import flp.utils.Calculator;
import flp.utils.Percentile;

public class ResponseStatController {
	
	private Map<String, Map<StatType, Map<String, StatMapObject>>> statMap; // <callAction, <statType, <activityType, <responseStat, durationList>>>>
    private Map<String, AsyncMapObject> asyncMap; // <msgId, asyncMapObject>
    private Map<String, List<String>> warningTransactionIdMap;

    public ResponseStatController() {
        statMap = new TreeMap<>();
        asyncMap = new HashMap<>();
        warningTransactionIdMap = new HashMap<>();
    }
    
    public Map<String, List<String>> getWarningTransactionIdMap() {
        return warningTransactionIdMap;
    }

    public void checkMsgId(String msgId, String activityType, TimeType timeType, Timestamp timestamp) {
        if (!asyncMap.containsKey(msgId)) {
            asyncMap.put(msgId, new AsyncMapObject());
        }
        AsyncMapObject asyncMapObject = asyncMap.get(msgId);
        asyncMapObject.setActivityType(activityType);
        if (timeType == TimeType.REQUEST_TIME) {
            asyncMapObject.setRequestTime(timestamp);
        } else {
            asyncMapObject.setFinishTime(timestamp);
        }
    }

    public String getAsyncActivityType(String msgId) {
        return asyncMap.get(msgId).getActivityType();
    }

    public boolean isFoundMsgIdPair(String msgId) {
        return asyncMap.get(msgId).isFoundPair();
    }

    public double getAsyncDuration(String msgId) {
        return asyncMap.get(msgId).getDuration();
    }

    public void setValue(String callAction, StatType statType, String statTitle, double duration, boolean isPass) {
        if (!statMap.containsKey(callAction)) {
            statMap.put(callAction, new TreeMap<StatType, Map<String, StatMapObject>>());
        }
        Map<StatType, Map<String, StatMapObject>> typeMap = statMap.get(callAction);
        if (!typeMap.containsKey(statType)) {
            typeMap.put(statType, new TreeMap<String, StatMapObject>());
        }
        Map<String, StatMapObject> titleMap = typeMap.get(statType);
        if (!titleMap.containsKey(statTitle)) {
            titleMap.put(statTitle, new StatMapObject(statTitle));
        }
        StatMapObject statMapObject = titleMap.get(statTitle);
        statMapObject.getDurationList().add(duration);
        if (isPass) {
            statMapObject.getResponseStat().addPassCount();
        }
    }

    public void populate() {
        Percentile percentile = new Percentile();

        for (Map.Entry<String, Map<StatType, Map<String, StatMapObject>>> callActionEntry : statMap.entrySet()) {
            for (Map.Entry<StatType, Map<String, StatMapObject>> statTypeEntry : callActionEntry.getValue().entrySet()) {
                for (Map.Entry<String, StatMapObject> titleEntry : statTypeEntry.getValue().entrySet()) {
                    StatMapObject statMapObject = titleEntry.getValue();
                    ResponseStat responseStat = statMapObject.getResponseStat();
                    List<Double> durationList = statMapObject.getDurationList();
                    double[] durationArray = new double[ durationList.size() ];
                    if ( null != durationList && durationList.size() > 0 ) {
                    	for (int i = 0; i < durationList.size(); i++) {
                    		durationArray[i] = durationList.get(i).doubleValue();
						}
                    }

                    responseStat.setMin(Calculator.roundTo(Collections.min(durationList), 3));
                    responseStat.setMax(Calculator.roundTo(Collections.max(durationList), 3));
                    responseStat.setAvg(Calculator.roundTo(Calculator.avg(durationArray), 3));
                    responseStat.setP85(Calculator.roundTo(percentile.evaluate(durationArray, 85), 3));
                    responseStat.setP90(Calculator.roundTo(percentile.evaluate(durationArray, 90), 3));
                    responseStat.setP95(Calculator.roundTo(percentile.evaluate(durationArray, 95), 3));
                    responseStat.setTotalCount(durationArray.length);
                }
            }
        }
    }
    
}
