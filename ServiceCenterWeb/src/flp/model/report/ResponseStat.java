package flp.model.report;

public class ResponseStat {
	
	private String title;
    private double min;
    private double max;
    private double avg;
    private double p85;
    private double p90;
    private double p95;
    private int totalCount;
    private int passCount;

    public ResponseStat(String title) {
        this.title = title;
        passCount = 0;
    }

    public String getTitle() {
        return title;
    }
    public double getMin() {
        return min;
    }
    public void setMin(double min) {
        this.min = min;
    }
    public double getMax() {
        return max;
    }
    public void setMax(double max) {
        this.max = max;
    }
    public double getAvg() {
        return avg;
    }
    public void setAvg(double avg) {
        this.avg = avg;
    }
    public double getP85() {
        return p85;
    }
    public void setP85(double p85) {
        this.p85 = p85;
    }
    public double getP90() {
        return p90;
    }
    public void setP90(double p90) {
        this.p90 = p90;
    }
    public double getP95() {
        return p95;
    }
    public void setP95(double p95) {
        this.p95 = p95;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getPassCount() {
        return passCount;
    }

    public void addPassCount() {
        passCount++;
    }
    
}
