package jp.co.crowdworks.fugafugaworks;

public class Test {
    public String UUID;
    public String NAME;
    public String FUUID;

    public Test(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Test(String id, String name) {
        this.UUID = id;
        this.NAME = name;
    }

    public Test(String id, String message,String FUUID) {
        this.UUID = id;
        this.NAME = message;
        this.FUUID = FUUID;
    }
}
