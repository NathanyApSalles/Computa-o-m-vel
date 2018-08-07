package br.ufop.nathany.futmannathany;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListarEstatisticas extends AppCompatActivity {
    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadJogador();
       // Log.d("CSI489", jogadores.toString());
        setContentView(R.layout.activity_listar_estatisticas);
        ListView lv = (ListView) findViewById(R.id.listEstatisticas);
        lv.setAdapter(new JogadorEstatisticasAdapter(this, jogadores));

        setTitle("Estatísticas");

    }

    public void loadJogador(){

        try{
            fis = this.openFileInput("t.tmp");
            ois = new ObjectInputStream(fis);
            jogadores = (ArrayList<Jogador>) ois.readObject();
           // Log.d("CSI489", "Carregado: " + jogadores.toString());
            //  time = (ArrayList<Jogador>) ois.readObject();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
