package jp.co.crowdworks.fugafugaworks;

public class Users {
    public String MyName;

    public Users(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる
    public Users(String name) {
        this.MyName = name;
    }

}
