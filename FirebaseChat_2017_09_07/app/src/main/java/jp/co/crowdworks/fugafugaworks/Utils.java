package jp.co.crowdworks.fugafugaworks;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by 4163101 on 2018/04/17.
 */

public class Utils {

    // 呼び出し元
    private Context mParent;
    // プログレスバー
    public ProgressDialog mProgressDialog;

    /**
     * コンストラクタ
     *
     * @param parent
     */
    public Utils(Context parent) {
        mParent = parent;
    }

    /**
     * プログレスバーの表示
     *
     * @param title
     * @param description
     */
    public void progressShow(String title, String description) {
        mProgressDialog = new ProgressDialog(mParent);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(description);
        mProgressDialog.show();
    }

    /**
     * プログレスバーを閉じる
     */
    public void progressDismiss() {
        mProgressDialog.dismiss();
    }
}
