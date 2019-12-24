package com.example.tacticalb.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.tacticalb.R;

//Диалог для показа загрузки
public class Loading extends ProgressDialog {
    ProgressDialog progressDialog;
    Context context;




public void show(){
progressDialog.show();
}

    @Override
    public void dismiss() {
        super.dismiss();
        progressDialog.dismiss();
    }

    public Loading(Context context) {
        super(context);
        this.context=context;
        progressDialog=new ProgressDialog(context,R.style.AlertDialogCustom);
        progressDialog.setTitle(context.getResources().getString(R.string.wait)+"...");
        progressDialog.setMessage(context.getResources().getString(R.string.loading)+"...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

    }
}
