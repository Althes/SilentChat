package jp.co.crowdworks.fugafugaworks;

/**
 * Created by 4163211 on 2017/12/05.
 */

class SnapshotData{
    Long id;
    private String Uuid;
    private String Message;
    private int price;


    public void setUuid(String uuid){
        this.Uuid = uuid;
    }

    public void setMessage(String message){
        this.Message = message;
    }

    public String getUuid(){
        return Uuid;
    }

    public String getMessage(){
        return Message;
    }

    public SnapshotData(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる


    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    /*
    public SnapshotData(String id, String message) {
        this.Uuid = id;
        this.Message = message;
    }
    */
}