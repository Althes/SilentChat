package jp.co.crowdworks.fugafugaworks;

import android.app.ProgressDialog;

/**
 * Created by 4163101 on 2018/04/17.
 */

public class MyThread extends Thread {
    public ProgressDialog target;
    public String uuid;

    public void run(){
        try{
            this.sleep(5000);
        }
        catch(InterruptedException e){
        }
        if (target != null && uuid != null){
            target.dismiss();
        }
    }
}
