package br.ufop.nathany.futmannathany;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import android.view.View;
import android.widget.Toast;

public class EditarJogador extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
    private static final String[] POSICAOJOGADOR = {"Goleiro","Atacante","Defesa","Meio-campo"};
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
        loadJogador();
        setTitle("Editar jogador");
        position = (int) params.get("posicao");
        setContentView(R.layout.activity_editar_jogador);
        initSpinner();
        EditText et1 = (EditText) findViewById(R.id.etNomeJogador);
        et1.setText("" + jogadores.get(position).nomeJogador);

        Spinner spinner = (Spinner) findViewById(R.id.sPosicao);

        String posicaoJogador = jogadores.get(position).getPosicaoJogador();
        for(int i = 0; i < POSICAOJOGADOR.length; ++i) {
            if(POSICAOJOGADOR[i].equals(posicaoJogador)) {
                spinner.setSelection(i);
            }
        }

        EditText et2 = (EditText) findViewById(R.id.etTel);
        et2.setText("" + jogadores.get(position).telJogador);

    }
    public void initSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, POSICAOJOGADOR);
        Spinner spinner = (Spinner) findViewById(R.id.sPosicao);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);//responsável por retornar o item escolhido no spinner
    }


    public void salvarJogador(View view){

        EditText et1 = (EditText) findViewById(R.id.etNomeJogador);
        String nomeJogador = et1.getText().toString();
        if(et1.getText().toString().isEmpty()){
            Toast.makeText(this, "Campo inválido", Toast.LENGTH_SHORT).show();
            return;
        }


        Spinner spinner = (Spinner) findViewById(R.id.sPosicao);
        String spinnerStr = (String) spinner.getSelectedItem();

        long telJogador;
        EditText et2 = (EditText) findViewById(R.id.etTel);
        try {
            telJogador = Long.parseLong(et2.getText().toString());

        }catch (NumberFormatException e){
            Toast.makeText(this, "Campo inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        Jogador jogador = new Jogador(position,nomeJogador,spinnerStr,telJogador);
        jogadores.set(position,jogador);

        saveJogadores();
        finish();
    }
    public void deletarJogador(View view){

        jogadores.remove(position);
        saveJogadores();
        finish();
    }

    @Override
    public void onBackPressed() {
        saveJogadores();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if(parent.getId() == R.id.spinnerPosicao){
            String posicaoSelecionada = POSICAOJOGADOR[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
