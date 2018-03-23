package jp.co.crowdworks.fugafugaworks;

public class UsersR {
    public String room;

    public UsersR(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる
    public UsersR(String room) {
        this.room = room;
    }

}
