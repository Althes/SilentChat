package jp.co.crowdworks.fugafugaworks;

/**
 * Created by 4163211 on 2017/11/09.
 */

public class Search {
    public String FName;
    public String friendID;
    public String Rooms;

    public Search(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Search( String u ){
        this.FName = u;
    }

    public Search(String u, String name) {
        this.FName = u;
        this.friendID = name;
    }
    public Search(String u, String name,String roomes) {
        this.friendID = u;
        this.FName = name;
        this.Rooms = roomes;
    }
}
