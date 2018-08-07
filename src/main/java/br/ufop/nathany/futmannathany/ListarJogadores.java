package br.ufop.nathany.futmannathany;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class ListarJogadores extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
    private int position = -1;
    Intent it;
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_jogadores);
        loadJogador();

        initList();

        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE,
                Manifest.permission.INTERNET}, 1);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarJogadores.this,CadastrarJogadores.class);
                startActivity(intent);

            }
        });


    }

    public void initList(){
        ArrayAdapter<Jogador> adapter = new ArrayAdapter<Jogador>(this,android.R.layout.simple_list_item_1,jogadores);

        ListView listView = (ListView) findViewById(R.id.listJogadores);

        listView.setOnItemClickListener(this);

        listView.setAdapter(adapter);


    }
    public int jogadorClicado;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.listJogadores){

            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_ligar_editar);
            dialog.show();

            jogadorClicado = i;

        }

    }

    public void ligarJogador(View view){
        dialog.dismiss();//tirar da dela

        long numero = jogadores.get(jogadorClicado).getTelJogador();
        String num = ""+numero;
        Uri uri = Uri.parse("tel:" + numero);//define padrão

        Intent it = new Intent(Intent.ACTION_CALL,uri);

        //verificar se a permissão foi concedida
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            startActivity(it);
        }
    }

    public void editar(View view){
        dialog.dismiss();//tirar da dela
        it = new Intent(this,EditarJogador.class);
        it.putExtra("posicao",jogadorClicado);
        startActivity(it);

    }



    @Override
    public void onBackPressed() {
        saveJogadores();
        finish();
    }

    @Override
    protected void onResume() {
        loadJogador();
        initList();
        super.onResume();
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

}
