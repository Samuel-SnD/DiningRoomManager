package com.example.diningroommanager;

import android.app.AlertDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class MessageDialogFragment extends DialogFragment {

    // Clase para crear un diálogo para los términos y condiciones
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String termsAndConditions = getResources().getString(R.string.termsAndConditions);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Términos y condiciones");
        builder.setMessage(termsAndConditions);
        builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();
    }
}
