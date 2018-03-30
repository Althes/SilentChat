package jp.co.crowdworks.fugafugaworks;

/**
 * Created by 4163211 on 2017/11/09.
 */

public class Friend {
    private String FName;
    private String friendID;
    private String user;
    private String Rooms;


    public Friend(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

//    public Friend( String u ){
//        this.FName = u;
//    }

    public void setFName(String u){
        this.FName =u;
    }

    public String getFName(){
        return FName;
    }

    public void setFriendId(String id){
        this.friendID = id;
    }

    public String getFriendId(){
        return friendID;
    }
//
//    public Friend(String u, String id) {
//        this.FName = u;
//        this.FriendId = id;
//    }

//    public Friend(String u, String name,String roomes) {
//        this.user= u;
//        this.FName = name;
//        this.Rooms = roomes;
//    }
}
