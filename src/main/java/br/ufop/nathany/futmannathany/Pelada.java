package br.ufop.nathany.futmannathany;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Pelada extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
    ArrayList<Jogador> time = new ArrayList<Jogador>();
    ArrayList<Jogador> timeA = new ArrayList<Jogador>();
    ArrayList<Jogador> timeB = new ArrayList<Jogador>();
    public static final String[] QTDJOGADORES = {"5","6","7"};
    public static final String[] DURACAO = {"05:00","06:00","07:00","08:00","09:00","10:00"};
    String qtdSelecionada = null;
    String duracaoPartida = null;
    private int tamanhoTime = 0;
    private int tamPartida = 0;
    private int position = -1;
    Intent it;
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        it = getIntent();
        Bundle params = it.getExtras();
        setTitle("Configurações da Partida");
        setContentView(R.layout.activity_pelada);
        loadJogador();
        loadTime();
        initSpinner1();
        initiSpinner2();
    }

    public void initSpinner1(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,QTDJOGADORES);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerQtdJogadores);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);//responsável por retornar o item escolhido no spinner


    }
    public void initiSpinner2(){
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,DURACAO);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerDuracaoPartida);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);//responsável por retornar o item escolhido no spinner
    }

    public void escolherJogadores(View view){
        it = new Intent(this, ConfirmarJogadores.class);
        startActivity(it);
    }


    public void iniciarPartida(View view){

        it = new Intent(this, PartidaAtual.class);
        it.putExtra("tamanhoTime",tamanhoTime);
        it.putExtra("duracaoPartida",tamPartida);
        startActivity(it);
    }


    @Override
    public void onBackPressed() {
        //saveJogadores();
       // saveTime();
        finish();
    }

    public void loadJogador(){

        try{
            fis = this.openFileInput("t.tmp");
            ois = new ObjectInputStream(fis);
            jogadores = (ArrayList<Jogador>) ois.readObject();
            time = (ArrayList<Jogador>) ois.readObject();
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
            oos.writeObject(time);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadTime(){

        try{
            fis = this.openFileInput("temp.tmp");
            ois = new ObjectInputStream(fis);
            time = (ArrayList<Jogador>) ois.readObject();
            timeA = (ArrayList<Jogador>) ois.readObject();
            timeB = (ArrayList<Jogador>) ois.readObject();
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
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if(parent.getId() == R.id.spinnerQtdJogadores){
            qtdSelecionada = QTDJOGADORES[position];
            tamanhoTime = Integer.parseInt(qtdSelecionada);
           // Toast.makeText(this,qtdSelecionada,Toast.LENGTH_SHORT).show();
        }
        if(parent.getId() == R.id.spinnerDuracaoPartida){
            duracaoPartida = DURACAO[position];
            String[] hora = duracaoPartida.split(":");
            String minutos = hora[0];
            tamPartida = Integer.parseInt(minutos);
          //  Toast.makeText(this,DURACAO[position],Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
