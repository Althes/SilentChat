package jp.co.crowdworks.fugafugaworks;

public class Rooms {
    public String member01;
    public String member02;

    public Rooms(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Rooms(String member01,String member02) {
        this.member01 = member01;
        this.member02 = member02;
    }

}
