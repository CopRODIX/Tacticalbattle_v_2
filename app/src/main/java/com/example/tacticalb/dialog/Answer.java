package com.example.tacticalb.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.example.tacticalb.R;

public class Answer extends DialogFragment {
        //Класс-конструктор диолога для запроса на восстановление незаконченной игры
    private Dialog_Fr dialog_fr;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dialog_fr=(Dialog_Fr) context;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom);

        return builder.setTitle(getResources().getString(R.string.continuegame)).
                setMessage(getResources().getString(R.string.newgamestart)).
                setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog_fr.canal(true);
                    }
                }).
                setNegativeButton(getResources().getString(R.string.newgame), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog_fr.canal(false);
                    }
                }).create();

    }



    public interface Dialog_Fr {
        void canal(boolean otvet);
    }
}
