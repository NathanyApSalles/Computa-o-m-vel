package br.ufop.nathany.futmannathany;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ConfirmarJogadores extends AppCompatActivity {
    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
    private ArrayList<Jogador> time = new ArrayList<Jogador>();
    ArrayList<Jogador> emEspera = new ArrayList<Jogador>();
    private int position = -1;
    Intent it;
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de jogadores presentes");
        setContentView(R.layout.activity_confirmar_jogadores);
        loadJogador();
        loadTime();
        time.clear();
        initList();


    }

    public void initList(){
        ListView listView = (ListView) findViewById(android.R.id.list);
        ArrayAdapter<Jogador> adapter = new ArrayAdapter<Jogador>(this,android.R.layout.simple_list_item_multiple_choice,jogadores);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemChecked(position, true);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    jogadores.get(i).setPresent(true);
                    time.add(jogadores.get(i));
            }
        });
        saveJogadores();
        saveTime();
        Toast.makeText(this,time.toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        loadJogador();
        loadTime();
       // initList();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        saveJogadores();
        saveTime();
        finish();
    }

    public void loadJogador(){

        try{
            fis = this.openFileInput("t.tmp");
            ois = new ObjectInputStream(fis);
            jogadores = (ArrayList<Jogador>) ois.readObject();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void loadTime(){

        try{
            fis = this.openFileInput("temp.tmp");
            ois = new ObjectInputStream(fis);
            time = (ArrayList<Jogador>) ois.readObject();
            emEspera = (ArrayList<Jogador>) ois.readObject();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void saveJogadores(){

        try{
            fos = this.openFileOutput("t.tmp", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(jogadores);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveTime(){

        try{
            fos = this.openFileOutput("temp.tmp", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(time);
            oos.writeObject(emEspera);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
