package jp.co.crowdworks.fugafugaworks;

public class Tamesi {
    public String ID;
    public String Name;
    public String FUUID;

    public Tamesi(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Tamesi(String id, String name) {
        this.ID = id;
        this.Name = name;
    }

    public Tamesi(String id, String message,String FUUID) {
        this.ID = id;
        this.Name = message;
        this.FUUID = FUUID;
    }
}
