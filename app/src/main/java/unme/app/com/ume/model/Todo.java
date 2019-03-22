package unme.app.com.ume.model;

public class Todo {
    private String userId;
    private String taskName;
    private String task;
    private String taskStatus;
    private String date;
    private String key;

    public Todo(){}

    public Todo(String userId, String key, String taskName, String task, String taskStatus, String date) {
        this.userId = userId;
        this.taskName = taskName;
        this.task = task;
        this.taskStatus = taskStatus;
        this.date = date;
        this.key = key;
    }

    public String getUserId(){
        return userId;
    }

    public String getKey(){
       return key;
    }

    public String getTaskName(){
        return taskName;
    }

    public String getTask(){
        return task;
    }

    public String isTaskStatus() {
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

    public void setTaskStatus(String taskStatus){
        this.taskStatus = taskStatus;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setKey(String key){
        this.key = key;
    }

}
