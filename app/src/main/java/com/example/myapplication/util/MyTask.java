package com.example.myapplication.util;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.myapplication.view.TreinoActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MyTask extends AsyncTask<ArrayList<Float>, Float, String> {

    private WeakReference<TreinoActivity> activityWeakReference;
    SeekBar progressBar;

    // CONSTRUTOR
    public MyTask(TreinoActivity activity, SeekBar progressBar) {
        activityWeakReference = new WeakReference<TreinoActivity>(activity);
        this.progressBar = progressBar;
    }// Fim do CONSTRUTOR

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        TreinoActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
    }// Fim onPreExecute

    @Override
    protected String doInBackground(ArrayList<Float>... floats) {
        ArrayList<Float> teste = floats[0];

        for (int i = 1; i < 10; i++)
        {

            publishProgress((teste.get(i) * 100) / teste.size());
            if (isCancelled()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
