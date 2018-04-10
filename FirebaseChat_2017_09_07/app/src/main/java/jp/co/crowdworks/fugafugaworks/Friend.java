package jp.co.crowdworks.fugafugaworks;

/**
 * Created by 4163211 on 2017/11/09.
 */

public class Friend {
    public String FName;
    public String user;
    public String Rooms;

    public Friend(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Friend( String u ){
        this.FName = u;
    }

    public Friend(String u, String name) {
        this.FName = u;
        this.user = name;
    }
    public Friend(String u, String name,String roomes) {
        this.user= u;
        this.FName = name;
        this.Rooms = roomes;
    }
}
