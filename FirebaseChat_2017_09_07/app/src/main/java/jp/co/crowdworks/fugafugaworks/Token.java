package jp.co.crowdworks.fugafugaworks;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.GetTokenResult;

public class Token {
    public String UUID;
    public String Token;

    public Token(String uid, Task<GetTokenResult> token){} //これがないとcom.google.firebase.database.DatabaseException: Class jp.co.crowdworks.fugafugaworks.Message is missing a constructor with no arguments みたいなエラーで落ちる
}

