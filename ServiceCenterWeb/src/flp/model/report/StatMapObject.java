package flp.model.report;

import java.util.ArrayList;
import java.util.List;

public class StatMapObject {

	private ResponseStat responseStat;
    private List<Double> durationList;

    public StatMapObject(String statTitle) {
        responseStat = new ResponseStat(statTitle);
        durationList = new ArrayList<>();
    }

    public ResponseStat getResponseStat() {
        return responseStat;
    }
    public List<Double> getDurationList() {
        return durationList;
    }
    
}
