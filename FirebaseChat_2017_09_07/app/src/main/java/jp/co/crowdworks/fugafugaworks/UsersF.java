package jp.co.crowdworks.fugafugaworks;

public class UsersF {
    public String FName;

    public UsersF(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる
    public UsersF(String name) {
        this.FName = name;
    }

}
