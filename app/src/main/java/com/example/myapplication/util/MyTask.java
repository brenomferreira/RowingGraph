package com.example.myapplication.util;

import android.os.AsyncTask;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.myapplication.view.TreinoActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MyTask extends AsyncTask<ArrayList<Float>, Float, String> {

    private WeakReference<TreinoActivity> activityWeakReference;
    SeekBar progressBar;
    SeekBar posicaoCadeira;

    // CONSTRUTOR
    public MyTask(TreinoActivity activity, SeekBar progressBar, SeekBar posicaoCadeira) {
        activityWeakReference = new WeakReference<TreinoActivity>(activity);
        this.progressBar = progressBar;
        this.posicaoCadeira = posicaoCadeira;

    }// Fim do CONSTRUTOR

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        TreinoActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        posicaoCadeira.setVisibility(View.VISIBLE);
    }// Fim onPreExecute

    @Override
    protected String doInBackground(ArrayList<Float>... floats) {
        ArrayList<Float> teste = floats[0];
        while (true) {
            for (int i = 2; i < teste.get(1); i++) { // começa do dois pois os dois primeiros valores sao frequencia e n samples

                publishProgress(teste.get(i));
                if (isCancelled()) {
                    break;
                }
                try {
                    Thread.sleep((long) (1 / teste.get(0) * 1000));// frequencia de amostragem
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (isCancelled())
                break;
        }

        return "Finished!";
    }// Fim doInBackground

    @Override
    protected void onProgressUpdate(Float... values) {
        super.onProgressUpdate(values);
        TreinoActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }
        float temp = values[0];

        progressBar.setProgress((int) temp);
        if (temp < 60)
            posicaoCadeira.setProgress((int) temp);
        else
            posicaoCadeira.setProgress(60);


    }// Fim onProgressUpdate

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        TreinoActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
        progressBar.setProgress(0);
        progressBar.setVisibility(View.INVISIBLE);
    }// Fim onPostExecute


}
