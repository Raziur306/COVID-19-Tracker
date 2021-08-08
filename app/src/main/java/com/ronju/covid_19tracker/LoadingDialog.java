package com.ronju.covid_19tracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class LoadingDialog {

    public static Dialog getCustomLoadingDialog(Context context)
    {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_loading_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    return dialog;
    }

    public static Dialog getUnitDialog(Context context)
    {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ads_layout_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return dialog;
    }

}
