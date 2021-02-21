package edu.sp.cw;

public class Tact {
    Task task;
    Integer sendData;
    Integer receiveData;

    public Tact(Task task, Integer sendData, Integer receiveData) {
        this.task = task;
        this.sendData = sendData;
        this.receiveData = receiveData;
    }
    public Tact(){}

    public boolean isFullFree(){
        return task == null && sendData == null && receiveData == null;
    }

    public boolean isFreeForTaskLoad(){
        return task == null;
    }

    public boolean isFreeForSendReceive(){
        return sendData == null && receiveData == null;
    }

    public boolean isJustExecute() {
        return task != null && sendData == null && receiveData == null;
    }

    @Override
    public String toString() {
        return String.format("%s%s", task != null ? "["+task.id+"]":"", sendData != null ? "/S["+sendData+"]" :
                receiveData != null ? "/R["+receiveData+"]" : "");
    }
}
