package unme.app.com.ume.model;

public class Todo {
    private String userId;
    private String taskName;
    private String task;
    private boolean taskStatus;
    private String date;

    public Todo(){}

    public Todo(String userId, String taskName, String task, boolean taskStatus, String date) {
        this.userId = userId;
        this.taskName = taskName;
        this.task = task;
        this.taskStatus = taskStatus;
        this.date = date;
    }

    public String getUserId(){
        return userId;
    }

    public String getTaskName(){
        return taskName;
    }

    public String getTask(){
        return task;
    }

    public boolean isTaskStatus() {
        return taskStatus;
    }

    public String getDate(){
        return date;
    }


    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setTaskName(String taskName){
        this.taskName = taskName;
    }
    

    public void setTask(String task){
        this.task = task;
    }

    public void setTaskStatus(boolean taskStatus){
        this.taskStatus = taskStatus;
    }

    public void setDate(String date){
        this.date = date;
    }

}
