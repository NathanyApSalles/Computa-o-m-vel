package br.ufop.nathany.futmannathany;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CadastrarJogadores extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
    private static final String[] POSICAOJOGADOR = {"Atacante","Defesa","Goleiro","Meio-campo"};
    private int position = -1;
    Intent it;
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadJogador();
        setTitle("Cadastro de jogadores");
        setContentView(R.layout.activity_cadastrar_jogadores);
        initSpinner();
    }

    public void initSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,POSICAOJOGADOR);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerPosicao);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);//responsável por retornar o item escolhido no spinner
    }

    public void cadastrarJogador(View view){

        EditText et1 = (EditText) findViewById(R.id.editTextNomeJogador);
        String nomeJogador = et1.getText().toString();
        if(et1.getText().toString().isEmpty()){
            Toast.makeText(this, "Campo inválido", Toast.LENGTH_SHORT).show();
            return;
        }


        Spinner spinner = (Spinner) findViewById(R.id.spinnerPosicao);
        String spinnerStr = (String) spinner.getSelectedItem();

        long telJogador;
        EditText edittextTel = (EditText) findViewById(R.id.editTextTelC);
        try {
            telJogador = Long.parseLong(edittextTel.getText().toString());

        }catch (NumberFormatException e){
            Toast.makeText(this, "Campo inválido", Toast.LENGTH_SHORT).show();
            return;
        }


        Jogador jogador = new Jogador(position, nomeJogador, spinnerStr, telJogador);
        jogadores.add(jogador);

        saveJogadores();
        finish();

        Toast.makeText(this, nomeJogador + " foi cadastrado!", Toast.LENGTH_SHORT).show();

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
