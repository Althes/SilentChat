package jp.co.crowdworks.fugafugaworks;

/**
 * Created by 4163211 on 2017/11/09.
 */

public class Users {
    public String UUID;
    public String Name;

    public Users(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Users(String id, String name) {
        this.UUID = id;
        this.Name = name;
    }
}
