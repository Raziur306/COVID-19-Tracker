package com.ronju.covid_19tracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class LoadingDialog extends Dialog {
    private Activity activity;
    AlertDialog alertDialog;
    public LoadingDialog(Context context) {
        super(context);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(params);
        setCancelable(false);
        setOnCancelListener(null);
        setTitle(null);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog,null);
        setContentView(view);
    }
}
