public abstract class Request implements Prioritizable {
    private String description;
    private int priority;
    private int difficulty;
    private int factor;
    private int startTime;
    private int endTime;
    private Status status;

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority(){
        return priority;
    }

    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public void setFactor(int factor){
        this.factor = factor;
    }

    public int getFactor() {
        return factor;
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public int getStartTime(){
        return startTime;
    }

    public void setEndTime(int endTime){
        this.endTime = endTime;
    }

    public int getEndTime(){
        return endTime;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    

    public abstract int calculateProcessingTime();







}