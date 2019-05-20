package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.myapplication.R;
import com.example.myapplication.data.SecurityPreferences;

public class TreinoActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.voltar = (Button) findViewById(R.id.btn_voltar);
        this.mViewHolder.voltar.setOnClickListener(this);
        this.mViewHolder.start = (Button) findViewById(R.id.btn_start);
        this.mViewHolder.voltar.setOnClickListener(this);


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
        }


    }

    private static class ViewHolder {
        Button voltar;
        Button start;
        SeekBar cadencia;
    }


}
