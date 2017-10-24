package jp.co.crowdworks.fugafugaworks;

public class Message {
    public String UUID;
    public String Message;

    public Message(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public Message(String id, String message) {
        this.UUID = id;
        this.Message = message;
    }
}
