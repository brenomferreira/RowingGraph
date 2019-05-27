package com.example.myapplication.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.data.SecurityPreferences;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LineGraphSeries<DataPoint> series;
    Random aleatorio = new Random();
    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //series = new LineGraphSeries<DataPoint>();
        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.drive = (EditText) findViewById(R.id.editText_Drive);
        this.mViewHolder.freq = (EditText) findViewById(R.id.editText_Freq);
        this.mViewHolder.voga = (EditText) findViewById(R.id.editText_voga);

        this.mViewHolder.calcular = (Button) findViewById(R.id.btn_Calcular);
        this.mViewHolder.calcular.setOnClickListener(this);
        this.mViewHolder.treino = (Button) findViewById(R.id.btn_Treino);
        this.mViewHolder.treino.setOnClickListener(this);

        this.mViewHolder.graph = (GraphView) findViewById(R.id.graph1);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btn_Treino) {
            // Intent intent = new Intent(getApplicationContext(), ParametrosActivity.class);
            Intent intent = new Intent(this, TreinoActivity.class); // chama outra view
            startActivity(intent);

        }// Fim Botão Treino

        if (id == R.id.btn_Calcular) {
            float drive = Float.parseFloat(String.valueOf(this.mViewHolder.drive.getText()));
            float voga = Float.parseFloat(String.valueOf(this.mViewHolder.voga.getText()));
            float fs = Float.parseFloat(String.valueOf(this.mViewHolder.freq.getText()));

            geraGrafico(voga, drive, fs);

        }// Fim Botão Calcular

    }

    /**
     * Função para gerar o gráfico da voga.
     * @param voga
     * @param drive
     * @param fs
     */
    public void geraGrafico(Float voga, Float drive, Float fs) {
        series = new LineGraphSeries<DataPoint>();

        float cicloSeg, recov, spDrive, spRecovery, t, xd, yd;

        xd = 0;
        t = 1 / fs;
        cicloSeg = 60 / voga;
        recov = cicloSeg - drive;
        spDrive = drive / t;
        spRecovery = recov / t;

        for (int i = 0; i < spDrive - 1; i++) {

            /*func em MatLab: DRIVE(i+1) = floor(50-50*cos(i*2*pi()/fs/Drive_seg/2));*/
            yd = (float) (50 - 50 * Math.cos(i * 2 * Math.PI / (drive - t) / 2 * t));
            series.appendData(new DataPoint(xd, yd), true, (int) (spDrive + spRecovery)); // o mesmo valor do for
            xd = xd + t;
            String txt = "Drive_" + i;
            this.mSecurityPreferences.storeFloat(txt, yd);
        }
        for (int i = 0; i < spRecovery - 1; i++) {

            /*func em MatLab: RECOVERY(i+1) = floor(50+50*cos(i*2*pi()/fs/Recovery_seg/2));*/
            yd = (float) (50 - 50 * -Math.cos(i * 2 * Math.PI / (recov - t) / 2 * t));
            series.appendData(new DataPoint(xd, yd), true, (int) (spDrive + spRecovery)); // o mesmo valor do for
            xd = xd + t;
            String txt = "Recov_" + i;
            this.mSecurityPreferences.storeFloat(txt, yd);

            this.mViewHolder.graph.addSeries(series);

            this.mViewHolder.graph.getViewport().setMinY(0);
            series.setColor(Color.rgb(aleatorio.nextInt(255), aleatorio.nextInt(255), aleatorio.nextInt(255)));
            this.mViewHolder.graph.getViewport().setMaxY(100);
            this.mViewHolder.graph.getViewport().setMinX(0);
            this.mViewHolder.graph.getViewport().setMaxX(drive + recov);
            this.mViewHolder.graph.getViewport().setYAxisBoundsManual(true);
            this.mViewHolder.graph.getViewport().setXAxisBoundsManual(true);

        }

        this.mSecurityPreferences.storeFloat("spDrive", spDrive);
        this.mSecurityPreferences.storeFloat("spRecovery", spRecovery);
        this.mSecurityPreferences.storeFloat("fs", fs);

    }

    private static class ViewHolder {
        EditText drive;
        EditText voga;
        EditText freq;

        GraphView graph;

        Button calcular;
        Button treino;
    }

}// FIM //
