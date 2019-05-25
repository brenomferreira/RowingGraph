package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.example.myapplication.R;
import com.example.myapplication.data.SecurityPreferences;
import com.example.myapplication.util.MyTask;

import java.util.ArrayList;

public class TreinoActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private MyTask task;

    private float fs, spDrive, spRecovery;
    private ArrayList<Float> vetor = new ArrayList<Float>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.voltar = (Button) findViewById(R.id.btn_voltar);
        this.mViewHolder.voltar.setOnClickListener(this);
        this.mViewHolder.start = (Button) findViewById(R.id.btn_start);
        this.mViewHolder.start.setOnClickListener(this);
        this.mViewHolder.stop = (Button) findViewById(R.id.btn_stop);
        this.mViewHolder.stop.setOnClickListener(this);

        this.mViewHolder.cadencia = (SeekBar) findViewById(R.id.seekBar_Cadencia);



        //declarar depois de tudo
        this.task = new MyTask(this, this.mViewHolder.cadencia);



        this.spDrive = this.mSecurityPreferences.getStoredFloat("spDrive");
        this.spRecovery = this.mSecurityPreferences.getStoredFloat("spRecovery");
        this.fs = this.mSecurityPreferences.getStoredFloat("fs");

        //int n = (int) (spDrive + 1f); // tamanho do vetor
//        float vetor[] = new float[n]; // declaração e alocação de espaço para o vetor "v"
//        vetor[0] = fs;
        this.vetor.add(this.fs);
        this.vetor.add(this.spDrive + this.spRecovery);

        for (int i = 0; i < this.spDrive - 1; i++) {

            //     DRIVE(i+1) = floor(50-50*cos(i*2*pi()/fs/Drive_seg/2));

            String txt = "Drive_" + i;
            this.vetor.add(this.mSecurityPreferences.getStoredFloat(txt));

        }
        for (int i = 0; i < this.spRecovery - 1; i++) {

            //     DRIVE(i+1) = floor(50-50*cos(i*2*pi()/fs/Drive_seg/2));

            String txt = "Recov_" + i;
            this.vetor.add(this.mSecurityPreferences.getStoredFloat(txt));

        }




    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_voltar) {
            // Intent intent = new Intent(getApplicationContext(), ParametrosActivity.class);
            Intent intent = new Intent(this, MainActivity.class); // chama outra view
            startActivity(intent);
        }
        if (id == R.id.btn_start) {

            this.mViewHolder.start.setText("Running");
            this.task = new MyTask(this, this.mViewHolder.cadencia);
//
            ArrayList<Float> vetor = new ArrayList<Float>();


            this.task.execute(this.vetor);
//
//
//
//
//            this.task.execute(10f, 10f, 9f, 8f, 7f, 6f, 5f, 4f, 3f, 2f, 1f  );
        }


        if (id == R.id.btn_stop){
            this.mViewHolder.start.setText("Start");
            this.mViewHolder.cadencia.setProgress(0);
            this.task.cancel(true);



        }


    }

    private static class ViewHolder {
        Button voltar;
        Button start;
        Button stop;
        SeekBar cadencia;
    }


}
