package com.example.examen2prom;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Parte2 extends AppCompatActivity {

    private final String[] url = new String[] {""};
    private Tiempo tiempo;
    private TextView txtHora, txtTemperatura, txtEstado;
    private ImageView imgEstado;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte2);

        // Declaracion de Views
        TextView txtLocalidad = findViewById(R.id.txtLocalidad);
        txtHora = findViewById(R.id.fechaHora);
        txtTemperatura = findViewById(R.id.temperatura);
        txtEstado = findViewById(R.id.estado);
        imgEstado = findViewById(R.id.imgEstado);
        Button bilbao = findViewById(R.id.bilbao);
        Button vitoria = findViewById(R.id.vitoria);
        Button donosti = findViewById(R.id.donosti);
        Button volver = findViewById(R.id.volver);

        // Metodo onClick del boton volver, vuelve a la ventana principal
        volver.setOnClickListener(view -> finish());

        // Metodo onClick del boton bilbao, cargar los datos de la pagina de bilbao
        bilbao.setOnClickListener(view -> {
            txtLocalidad.setText(" " + bilbao.getText());
            url[0] = "https://api.tutiempo.net/xml/?lan=es&apid=qsTX4X4qq44as6Q&lid=8050";
            cargarConSAXSimplificado();
        });

        // Metodo onClick del boton vitoria, cargar los datos de la pagina de vitoria
        vitoria.setOnClickListener(view -> {
            txtLocalidad.setText(" " + vitoria.getText());
            url[0] = "https://api.tutiempo.net/xml/?lan=es&apid=qsTX4X4qq44as6Q&lid=8043";
            cargarConSAXSimplificado();
        });

        // Metodo onClick del boton donosti, cargar los datos de la pagina de donosti
        donosti.setOnClickListener(view ->{
            txtLocalidad.setText(" " + donosti.getText());
            url[0] = "https://api.tutiempo.net/xml/?lan=es&apid=qsTX4X4qq44as6Q&lid=4917";
            cargarConSAXSimplificado();
        });
    }

    // Metodo que ejecuta la tarea en segundo plano de la carga de datos
    public void cargarConSAXSimplificado(){
        CargarXmlTask tarea = new CargarXmlTask();
        tarea.execute(url);
    }

    @SuppressLint("StaticFieldLeak")
    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        // Metodo doInBackground, llama al parse para coger el objeto Tiempo
        protected Boolean doInBackground(String... params) {
            RssParserSAXSimplificadoTiempo saxparserSimplificado =
                    new RssParserSAXSimplificadoTiempo(params[0]);
            tiempo = saxparserSimplificado.parse();
            return true;
        }

        // Metodo onPostExecute, añade los datos de ese Tiempo a los Views
        @SuppressLint("SetTextI18n")
        protected void onPostExecute(Boolean result) {
            txtHora.setText(" " + tiempo.getDia() + "/" + tiempo.getHora());
            txtEstado.setText(" " + tiempo.getEstado());
            txtTemperatura.setText(" " + tiempo.getTemperatura() + " (Min: " + tiempo.getTemperatura_min() + " / Max: " + tiempo.getTemperatura_max() + ")");

            String icon = "a" + tiempo.getIcono();
            Resources res = getResources();
            int resID = res.getIdentifier(icon, "drawable", getPackageName());
            imgEstado.setImageResource(resID);
        }
    }
}