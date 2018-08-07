package br.ufop.nathany.futmannathany;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listarJogadores(View view){
        it = new Intent(this,ListarJogadores.class);
        startActivity(it);
    }
    public void mostrarPeladas(View view){
        it = new Intent(this, Pelada.class);
        startActivity(it);
    }
    public void mostrarEstatisticas(View view){
        it = new Intent(this, ListarEstatisticas.class);
        startActivity(it);
    }

}
