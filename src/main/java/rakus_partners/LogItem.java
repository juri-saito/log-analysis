package rakus_partners;

public class LogItem {

    private String time;
    private int countUnder500;
    private int countUnder2000;
    private int countOther;
    private int totalResponseTime;

    public LogItem(String time) {
        this.time = time;
        this.countUnder500 = 0;
        this.countUnder2000 = 0;
        this.countOther = 0;
        this.totalResponseTime = 0;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCountUnder500() {
        return countUnder500;
    }

    public void setCountUnder500(int countUnder500) {
        this.countUnder500 = countUnder500;
    }

    public int getCountUnder2000() {
        return countUnder2000;
    }

    public void setCountUnder2000(int countUnder2000) {
        this.countUnder2000 = countUnder2000;
    }

    public int getCountOther() {
        return countOther;
    }

    public void setCountOther(int countOther) {
        this.countOther = countOther;
    }

    public int getTotalResponseTime() {
        return totalResponseTime;
    }

    public void setTotalResponseTime(int totalResponseTime) {
        this.totalResponseTime = totalResponseTime;
    }

    public void addResponse(int responseTime) {
        totalResponseTime += responseTime;
        if (responseTime <= 500) {
            countUnder500++;
        } else if (responseTime <= 2000) {
            countUnder2000++;
        } else {
            countOther++;
        }
    }

    public int getResponseTimeAverage() {
        int totalAccessCount = (countUnder500 + countUnder2000 + countOther);
        if (totalAccessCount != 0) {
            return totalResponseTime / totalAccessCount;
        }
        return 0;
    }

    @Override
    public String toString() {
        return time + "\t" + countUnder500 + "\t" + countUnder2000 + "\t" + countOther + "\t" + getResponseTimeAverage();
    }
}
