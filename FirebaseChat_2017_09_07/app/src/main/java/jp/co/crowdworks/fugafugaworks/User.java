package jp.co.crowdworks.fugafugaworks;

import com.google.firebase.database.DatabaseReference;

public class User {
    public String UUID;
    public String UserName;
    public String FUUID;
    private DatabaseReference mDatabase;//データベースの大本// かな　ここからツリー構造？

    public User(){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる

    public User(String id) {
        this.FUUID = id;//ユーザーのID
    }

    private void writeNewUser(String FUUID){//ユーザーデータ追加の為のユーザーIDとユーザー名を設定
        User user = new User(FUUID);//userにデータをインスタンス化？

        //たぶんどっちか
        //mDatabase.child("User").s
        // etValue(user);//ユーザーの追加
        mDatabase.child("users").child("UUID").setValue(user);//ユーザーの追加
    }
}











/*
public User(String id, String username) {
        this.UUID = id;//ユーザーのID
        this.UserName = username;//ユーザーネーム
    }
*/

/*
private void writeNewUser(String userId,String name){//ユーザーデータ追加の為のユーザーIDとユーザー名を設定
        User user = new User(userId,name);//userにデータをインスタンス化？

        //たぶんどっちか
        //mDatabase.child("users").setValue(user);//ユーザーの追加
        mDatabase.child("users").child(userId).setValue(user);//ユーザーの追加
    }
 */
