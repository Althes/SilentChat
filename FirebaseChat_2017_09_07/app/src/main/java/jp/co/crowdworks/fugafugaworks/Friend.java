package jp.co.crowdworks.fugafugaworks;

public class Friend {
    public String UUID;
    public String FrendID;

    public Friend(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Friend(String id, String frendid) {
        this.UUID = id;
        this.FrendID = frendid;
    }

}
