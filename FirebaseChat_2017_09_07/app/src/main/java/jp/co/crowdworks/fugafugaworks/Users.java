package jp.co.crowdworks.fugafugaworks;

/**
 * Created by 4163211 on 2017/11/09.
 */

public class Users {
    public String MyName;
    public String user;
    public String Rooms;

    public Users(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Users( String u ){
        this.MyName = u;
    }

    public Users(String u, String name) {
        this.MyName = u;
        this.user = name;
    }
    public Users(String u, String name,String roomes) {
        this.user= u;
        this.MyName = name;
        this.Rooms = roomes;
    }
}
