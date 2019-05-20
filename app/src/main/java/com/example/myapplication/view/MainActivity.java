package com.example.myapplication.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;


    Random aleatorio = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.drive = (EditText) findViewById(R.id.editText_Drive);
        this.mViewHolder.freq = (EditText) findViewById(R.id.editText_Freq);
        this.mViewHolder.voga = (EditText) findViewById(R.id.editText_voga);

        this.mViewHolder.calcular = (Button) findViewById(R.id.btn_Calcular);
        this.mViewHolder.calcular.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btn_Calcular) {

            float xd;
            float yd;
            float drive;
            float voga;
            float fs;
            float t;
            float cicloSeg;
            float recov;
            float spDrive;
            float spRecovery;
            float driveBanco;

            /*
            drive = 1; //seg
            voga = 15;//remadas/min
            fs = 50; //Hz
            */

            drive = Float.parseFloat(String.valueOf(this.mViewHolder.drive.getText()));
            voga = Float.parseFloat(String.valueOf(this.mViewHolder.voga.getText()));
            fs = Float.parseFloat(String.valueOf(this.mViewHolder.freq.getText()));


            xd = 0;

            t = 1 / fs;
            cicloSeg = 60 / voga;
            recov = cicloSeg - drive;

            spDrive = drive / t;
            spRecovery = recov / t;

            driveBanco = 70; // porecentagem


            GraphView graph = (GraphView) findViewById(R.id.graph1);
            series = new LineGraphSeries<DataPoint>();
        /*
        for (int i = 0; i < 10; i++){
            xd = xd + 1;
            yd = Math.sin(xd);
            series.appendData(new DataPoint(xd ,yd), true, 5); // o mesmo valor do for
        }
        */


            for (int i = 0; i < spDrive - 1; i++) {

                //     DRIVE(i+1) = floor(50-50*cos(i*2*pi()/fs/Drive_seg/2));
                yd = (float) (50 - 50 * Math.cos(i * 2 * Math.PI / (drive - t) / 2 * t));
                series.appendData(new DataPoint(xd, yd), true, (int) (spDrive + spRecovery)); // o mesmo valor do for
                xd = xd + t;
                String txt = "Drive_" + i;
                this.mSecurityPreferences.storeFloat(txt, yd);
            }
            for (int i = 0; i < spRecovery - 1; i++) {

                // RECOVERY(i+1) = floor(50+50*cos(i*2*pi()/fs/Recovery_seg/2));
                yd = (float) (50 - 50 * -Math.cos(i * 2 * Math.PI / (recov - t) / 2 * t));
                series.appendData(new DataPoint(xd, yd), true, (int) (spDrive + spRecovery)); // o mesmo valor do for
                xd = xd + t;
                String txt = "Recov_" + i;
                this.mSecurityPreferences.storeFloat(txt, yd);


            }





            graph.addSeries(series);

            graph.getViewport().setMinY(0);
            series.setColor(Color.rgb(aleatorio.nextInt(255),aleatorio.nextInt(255),aleatorio.nextInt(255)));
            graph.getViewport().setMaxY(100);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(drive + recov);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setXAxisBoundsManual(true);

        }

    }


    private static class ViewHolder {
        EditText drive;
        EditText voga;
        EditText freq;

        Button calcular;
    }
}
