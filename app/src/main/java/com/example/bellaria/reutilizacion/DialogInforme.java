package com.example.bellaria.reutilizacion;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.bellaria.R;

/**
 * Muestra un dialog informativo para el usuario.
 */
public class DialogInforme {
    /**
     *
     * @param context
     * @param texto
     */
    public static void dialogResultado(Context context, String texto) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_aviso);
        dialog.setTitle("Informe");

        TextView informe = (TextView) dialog.findViewById(R.id.txv_dialog_aviso_informe);
        informe.setText(texto);
        Button cerrar = dialog.findViewById(R.id.bttn_dialog_aviso_cerrar);
        cerrar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
