package br.ufop.nathany.futmannathany;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EmEspera extends AppCompatActivity {
    private ArrayList<Jogador> time = new ArrayList<Jogador>();
    ArrayList<Jogador> timeA = new ArrayList<Jogador>();
    ArrayList<Jogador> timeB = new ArrayList<Jogador>();
    ArrayList<Jogador> emEspera = new ArrayList<Jogador>();
    private int position = -1;
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTime();
        setContentView(R.layout.dialog_em_espera);
        initList();
    }
    public void initList(){

        //exibe jogadores em espera
        ListView listViewE = (ListView) findViewById(R.id.listaEspera);
        ArrayAdapter<Jogador> adapterE = new ArrayAdapter<Jogador>(this,android.R.layout.simple_list_item_1,emEspera);
//        //Toast.makeText(this,emEspera.toString(),Toast.LENGTH_SHORT).show();
        //  listViewE.setOnItemClickListener(this);
        listViewE.setAdapter(adapterE);

    }
    protected void onResume() {
        loadTime();
        initList();
        super.onResume();
    }
    @Override
    public void onBackPressed() {

        saveTime();
        finish();
    }

    public void loadTime(){

        try{
            fis = this.openFileInput("temp.tmp");
            ois = new ObjectInputStream(fis);
            time = (ArrayList<Jogador>) ois.readObject();
            timeA = (ArrayList<Jogador>) ois.readObject();
            timeB = (ArrayList<Jogador>) ois.readObject();
            emEspera = (ArrayList<Jogador>) ois.readObject();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void saveTime(){

        try{
            fos = this.openFileOutput("temp.tmp", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(time);
            oos.writeObject(timeA);
            oos.writeObject(timeB);
            oos.writeObject(emEspera);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
