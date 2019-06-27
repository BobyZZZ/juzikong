package com.bb.kanjuzi.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bb.kanjuzi.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Boby on 2019/6/25.
 */

public class Dialog extends DialogFragment {
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
//        android.app.Dialog dialog = new android.app.Dialog(getActivity());
//        dialog.setContentView(R.layout.item_other);
//        dialog.setCanceledOnTouchOutside(true);
//        Window window = dialog.getWindow();
//        window.setBackgroundDrawableResource(android.R.color.transparent);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.CENTER;
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(lp);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.setTitle(bundle.getString("title"))
                .setMessage(bundle.getString("message"))
                .setPositiveButton("取消", null)
                .create();
        return dialog;
    }

    public void showAndDismissAfter(FragmentManager manager, String tag, long delay) {
        show(manager, tag);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Dialog.this.dismiss();
            }
        }, delay);
    }
}
