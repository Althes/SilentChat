package jp.co.crowdworks.fugafugaworks;

/**
 * Created by 4163211 on 2017/11/09.
 */

public class Friend_id {
    public String friendID;
    public String user;
    public String Rooms;

    public Friend_id(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Friend_id( String u ){
        this.friendID = u;
    }
}