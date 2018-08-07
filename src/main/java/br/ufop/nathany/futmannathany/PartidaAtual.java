package br.ufop.nathany.futmannathany;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PartidaAtual extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
    private ArrayList<Jogador> time = new ArrayList<Jogador>();
    ArrayList<Jogador> timeA = new ArrayList<Jogador>();
    ArrayList<Jogador> timeB = new ArrayList<Jogador>();
    ArrayList<Jogador> emEspera = new ArrayList<Jogador>();
    private int position = -1;
    private int tamanhoTime = 0;
    private int tamPartida = 0;
    Intent it;
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;

    private long millisElapsed = 0;
    private boolean isStooped = true;
    int golA = 0;
    int golB = 0;
    int cartaoA = 0;
    int cartaoB = 0;
    boolean timeVencedorA = true;// se for false, time B venceu, se for true, time A venceu
    boolean empate = true;//se for true teve empate, se for false, não teve empate
    MediaPlayer mediaPlayer = null;
    int qtdPartida = 1;//variável aux para auxiliar na contagem de partidas disputadas pelo jogador
    Dialog dialogEspera;//dialog para jogadores na reserva
    Dialog dialogEncerrar;//encerrar partida antes do tempo
    private Dialog dialog;//dialog quando acaba o jogo
    private Dialog dialogEmpate;//dialog para empate
    Dialog dialogEmpateEnc;//dialog para empate referente ao encerrar partida
    int jogadorEmEsperaSubs = 0;
    public int jogadorClicadoA = 999, jogadorClicadoB = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        it = getIntent();
        setTitle("Partida");
        Bundle params = it.getExtras();
        loadJogador();
        loadTime();
        tamanhoTime = (int) params.get("tamanhoTime");
        tamPartida = (int) params.get("duracaoPartida");
        setContentView(R.layout.activity_partida_atual);
        sortearTime();
        initList();
        initChronometer();
    }

    //dialog para mostrar jogadores em espera
    public void jogadoresEspera(View view){
        dialogEspera = new Dialog(this);
        dialogEspera.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEspera.setContentView(R.layout.dialog_em_espera);

        if(!emEspera.isEmpty()) {
            ListView lv = (ListView) dialogEspera.findViewById(R.id.listaEspera);
            lv.setAdapter(new JogadorEsperaAdapter(this, emEspera));

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    jogadorEmEsperaSubs = position;
                }
            });

            dialogEspera.show();
        }else{
            Toast.makeText(this,"Nenhum jogador em espera",Toast.LENGTH_SHORT).show();
        }
    }

    //sorteio dos dois times
    public void sortearTime(){
        timeA.clear();
        timeB.clear();
        emEspera.clear();

        if(time.size()>= tamanhoTime*2){

           //preenche o time A pegando os N primeiros jogadores presentes
               for (int i = 0; i < tamanhoTime; i++) {

                   timeA.add(time.get(i));
                   qtdPartida++;
                   timeA.get(i).setQtdPartidas(qtdPartida);

               }
               //preenche o time B pegando pegando jogadores da posição tamanhoTime até a posição
               // tamanhoTime+tamanhoTime, para que o time fique com o tamanho requisitado pelo usuário.
               for (int i = tamanhoTime; i < tamanhoTime * 2; i++) {

                   timeB.add(time.get(i));
                   qtdPartida++;
                   timeB.get(i-tamanhoTime).setQtdPartidas(qtdPartida);

               }
               //preenche a lista de espera com os jogadores restantes
               for (int i = tamanhoTime * 2; i < time.size(); i++) {

                   emEspera.add(time.get(i));
               }

//           else if(!timeVencedorA){
//               //time B ganhou
//               Toast.makeText(this, "DEU RUIIIIIIM",Toast.LENGTH_SHORT).show();
//           }

        } else{
            Toast.makeText(this,"Número de jogadores insuficiente! Acrescente mais jogadores para o time",Toast.LENGTH_LONG).show();
        }

        saveTime();
    }

    //sotear time A
    public void substituirTimeA(){
        //time B ganhou
        for(int i = 0; i< timeA.size(); i++){
            emEspera.add(timeA.get(i));

            //  timeB.remove(i);
            //adiciono o timeB no final do emEspera
        }
        timeA.clear();
        for (int j = 0; j<tamanhoTime;j++){
            timeA.add(emEspera.get(0));
//            qtdPartida++;
//            timeA.get(j).setQtdPartidas(qtdPartida);
            emEspera.remove(0);
        }
        saveTime();
        // finish();
        initList();
        // startActivity(it);


    }

    //sortear time B
    public void substituirTimeB(){
       //time A ganhou
       for(int i = 0; i< timeB.size(); i++){
           emEspera.add(timeB.get(i));
           //  timeB.remove(i);
           //adiciono o timeB no final do emEspera
       }
       timeB.clear();
        for (int j = 0; j<tamanhoTime;j++){
            timeB.add(emEspera.get(0));
//            qtdPartida++;
//            timeB.get(j).setQtdPartidas(qtdPartida);
            emEspera.remove(0);
       }
       saveTime();
      // finish();
       initList();
      // startActivity(it);
    }

    //iniciar nova partida com outras configurações
    public void novaPartida(View view){
        timeA.clear();
        timeB.clear();
        emEspera.clear();
        tamanhoTime = 0;
        tamPartida = 0;
        golA = 0;
        golB = 0;
        cartaoA = 0;
        cartaoB = 0;
        finish();

        TextView tvA = (TextView) findViewById(R.id.tvGolTimeA);
        tvA.setText("0");
        TextView tvB = (TextView) findViewById(R.id.tvGolTimeB);
        tvB.setText("0");
        it = new Intent(this, Pelada.class);
        startActivity(it);

    }

    //lista para exibir times
    public void initList(){

        //exibe jogadores do timeA
        ListView listViewA = (ListView) findViewById(R.id.listaTimeA);
        ArrayAdapter<Jogador> adapterA = new ArrayAdapter<Jogador>(this,android.R.layout.simple_list_item_1,timeA);

        listViewA.setOnItemClickListener(this);

        listViewA.setAdapter(adapterA);

        //exibe jogadores do timeB
        ListView listViewB = (ListView) findViewById(R.id.listaTimeB);
        ArrayAdapter<Jogador> adapterB = new ArrayAdapter<Jogador>(this,android.R.layout.simple_list_item_1,timeB);

        listViewB.setOnItemClickListener(this);

        listViewB.setAdapter(adapterB);

    }

    // alertar para fim do jogo
    private void Vibrar(){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 1500;//tempo da vibração
        vibrator.vibrate(milliseconds);
    }

    //iniciar cronometro
    public void initChronometer(){
        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer2);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                if(elapsedTime > tamPartida*60000){
                   // Toast.makeText(PartidaAtual.this,"FIM DO JOGO",Toast.LENGTH_SHORT).show();
                    Vibrar();
                    initDialog();

                    if(mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(PartidaAtual.this, R.raw.fimdejogo);
                        mediaPlayer.start();

                    }else{
                        mediaPlayer.start();
                    }

                    resetChronometer(findViewById(R.id.buttonReset));
                }
            }
        });
    }

    //dialog que mostra as estatísticas da partida
    public void initDialog(){
        dialog = new Dialog(PartidaAtual.this);
        dialog.setContentView(R.layout.dialog_estatisticas);


        TextView tvGolA = (TextView) findViewById(R.id.tvGolTimeA);
        String golA = tvGolA.getText().toString();

        TextView tvPlacarA = (TextView) dialog.findViewById(R.id.tvPlacarA);
        tvPlacarA.setText(golA);

        TextView tvGolB = (TextView) findViewById(R.id.tvGolTimeB);
        String golB = tvGolB.getText().toString();

        TextView tvPlacarB = (TextView) dialog.findViewById(R.id.tvPlacarB);
        tvPlacarB.setText(golB);


        TextView tvTimeVencedor = (TextView) dialog.findViewById(R.id.tvTimeVencedor);


        if(Integer.parseInt(golA) > Integer.parseInt(golB)){
            timeVencedorA = true;
            empate = false;
            tvTimeVencedor.setText("TIME A");
            //chamar a função para substituir timeB

        }
        else if(Integer.parseInt(golA) < Integer.parseInt(golB)){
            timeVencedorA = false;
            empate = false;
            tvTimeVencedor.setText("TIME B");
            //chamar a função para substituir timeA

        }else{
            timeVencedorA = false;
            empate = true;
            tvTimeVencedor.setText("EMPATE");
        }

        TextView tvCartaoA = (TextView) dialog.findViewById(R.id.tvCartaoA);
        tvCartaoA.setText(""+cartaoA);
        TextView tvCartaoB = (TextView) dialog.findViewById(R.id.tvCartaoB);
        tvCartaoB.setText(""+cartaoB);

        dialog.show();


    }

    // fecha dialog das estatisticas da partida e contabiliza as estatisticas dos jogadores
    public void buttonESTOK(View view){

        //Log.d("CSI489", "Antes ok " + jogadores.toString());

        dialog.dismiss();


        TextView tvPlacarA = (TextView) findViewById(R.id.tvGolTimeA);
        tvPlacarA.setText("0");

        TextView tvPlacarB = (TextView) findViewById(R.id.tvGolTimeB);
        tvPlacarB.setText("0");

        cartaoA = 0;
        cartaoB = 0;
        golA = 0;
        golB = 0;
        resetChronometer(view);
        TextView tvTimeVencedor = (TextView) dialog.findViewById(R.id.tvTimeVencedor);

        for(int k = 0; k < timeA.size(); k++){
            for(int l = 0; l < jogadores.size();l++){
                if(timeA.get(k).getNomeJogador().equals(jogadores.get(l).getNomeJogador())) {
                    jogadores.get(l).setQtdPartidas(jogadores.get(l).getQtdPartidas() + 1);
                }

            }
        }
        for(int k = 0; k < timeB.size(); k++){
            for(int l = 0; l < jogadores.size();l++){
                if(timeB.get(k).getNomeJogador().equals(jogadores.get(l).getNomeJogador())) {
                    jogadores.get(l).setQtdPartidas(jogadores.get(l).getQtdPartidas() + 1);
                }

            }
        }

        if(tvTimeVencedor.getText().equals("TIME A")){
            for(int i = 0; i < timeA.size();i++){
                for(int j = 0; j < jogadores.size(); ++j) {
                    if(timeA.get(i).getNomeJogador().equals(jogadores.get(j).getNomeJogador())) {
                       // jogadores.get(j).setQtdPartidas(jogadores.get(j).getQtdPartidas() + 1);
                        jogadores.get(j).setQtdPeladasVencedoras(jogadores.get(j).getQtdPeladasVencedoras() + 1);
                    }
                }
            }
            substituirTimeB();

        } else if(tvTimeVencedor.getText().equals("TIME B")){
            for(int i = 0; i < timeB.size();i++){
                for(int j = 0; j < jogadores.size(); ++j) {
                    if(timeB.get(i).getNomeJogador().equals(jogadores.get(j).getNomeJogador())) {
                      //  jogadores.get(j).setQtdPartidas(jogadores.get(j).getQtdPartidas() + 1);
                        jogadores.get(j).setQtdPeladasVencedoras(jogadores.get(j).getQtdPeladasVencedoras() + 1);
                    }
                }
            }
            substituirTimeA();

        }
        else {
            // se for empate

            initDialogEmpate();

        }

        saveJogadores();
        saveTime();


    }

    //dialog para empate, podendo escolher qual time será substituido
    public void initDialogEmpate(){


        for(int i = 0; i < timeA.size();i++){
            for(int j = 0; j < jogadores.size(); ++j) {
                if(timeA.get(i).getNomeJogador().equals(jogadores.get(j).getNomeJogador())) {
                    // jogadores.get(j).setQtdPartidas(jogadores.get(j).getQtdPartidas() + 1);
                    jogadores.get(j).setQtdPeladasEmpates(jogadores.get(j).getQtdPeladasEmpates() + 1);
                }
            }
        }

        for(int i = 0; i < timeB.size();i++){
            for(int j = 0; j < jogadores.size(); ++j) {
                if(timeB.get(i).getNomeJogador().equals(jogadores.get(j).getNomeJogador())) {
                    // jogadores.get(j).setQtdPartidas(jogadores.get(j).getQtdPartidas() + 1);
                    jogadores.get(j).setQtdPeladasEmpates(jogadores.get(j).getQtdPeladasEmpates() + 1);
                }
            }
        }
        dialogEmpate = new Dialog(PartidaAtual.this);
        dialogEmpate.setContentView(R.layout.dialog_empate);

        dialogEmpate.show();



    }

    //confirma qual time será substituido
    public void okEmpate(View view){

        RadioGroup radioGroup = (RadioGroup) dialogEmpate.findViewById(R.id.radiogroup);

        RadioButton radioA = (RadioButton) dialogEmpate.findViewById(R.id.radioButtonTimeA);
        RadioButton radioB = (RadioButton) dialogEmpate.findViewById(R.id.radioButtonTimeB);

        if(radioA.isChecked()){
            substituirTimeA();

        }else if(radioB.isChecked()){
            substituirTimeB();

        }

        dialogEmpate.dismiss();
    }

    //contador de gols para time A
    public void golTimeA(View view){
        golA++;
        TextView tv = (TextView) findViewById(R.id.tvGolTimeA);
        tv.setText(""+golA);

    }

    //contador de gols para time B
    public void golTimeB(View view){
        golB++;
        TextView tv = (TextView) findViewById(R.id.tvGolTimeB);
        tv.setText(""+golB);

    }

    //contador de cartões para time A
    public void cartaoTimeA(View view){
        cartaoA++;
        Toast.makeText(this,"Cartão(ões) A: "+cartaoA,Toast.LENGTH_SHORT).show();
    }

    //contador de cartões para time B
    public void cartaoTimeB(View view){
        cartaoB++;
        Toast.makeText(this,"Cartão(ões) B: "+cartaoB,Toast.LENGTH_SHORT).show();
    }

    // dialog para escolher entre deletar jogador e substituir
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.listaTimeA){

            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_substituir_deletar);
            dialog.show();
            jogadorClicadoA = i;


        } else if(adapterView.getId() == R.id.listaTimeB){


            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_substituir_deletar);
            dialog.show();
            jogadorClicadoB = i;


        }
    }

    // deleta jogador da pelada
    public void deletarDaPelada(View view){
        if(emEspera.get(0)!=null) {
            if(jogadorClicadoA!=999) {
                time.remove(jogadorClicadoA);
                timeA.remove(jogadorClicadoA);
                timeA.add(emEspera.get(0));
                emEspera.remove(0);
            }
            else if(jogadorClicadoB!=999){
                time.remove(jogadorClicadoB+tamanhoTime);
                timeB.remove(jogadorClicadoB);
                timeB.add(emEspera.get(0));
                emEspera.remove(0);
            }
        }
        else {
            Toast.makeText(this,"Nenhum jogador em espera",Toast.LENGTH_SHORT).show();
        }

        saveJogadores();
        saveTime();
        dialog.dismiss();
        initList();
        // finish();
       // startActivity(it);

    }

    //substitui jogador da pelada
    public void substituirJogador(View view){
        if(!emEspera.get(0).toString().isEmpty()) {
            if (jogadorClicadoA != 999) {
                time.set(jogadorClicadoA, emEspera.get(0));
                emEspera.add(timeA.get(jogadorClicadoA));
                timeA.set(jogadorClicadoA, emEspera.get(0));
                emEspera.remove(0);
            } else if (jogadorClicadoB != 999) {
                time.set(jogadorClicadoB + tamanhoTime, emEspera.get(0));
                emEspera.add(timeB.get(jogadorClicadoB));
                timeB.set(jogadorClicadoB, emEspera.get(0));
                emEspera.remove(0);

            }
        }
        else {
            Toast.makeText(this,"Nenhum jogador em espera",Toast.LENGTH_SHORT).show();
        }

        saveJogadores();
        saveTime();
        dialog.dismiss();
        initList();
//        finish();
//        startActivity(it);

    }

    //inicia e para cronometro
    public void playStopChronometer(View view){
        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer2);

        if(isStooped) {//se tá parado, o cronometro é iniciado
            chronometer.start();

            chronometer.setBase(SystemClock.elapsedRealtime() + millisElapsed);
        //    muda texto do botão
            Button button = (Button) findViewById(R.id.buttonPlayStop);
            button.setText("Pausar");
            isStooped = false;

        }else{
            millisElapsed = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            Button button = (Button) findViewById(R.id.buttonPlayStop);
            button.setText("Iniciar");
            isStooped = true;

        }
    }

    //dialog para encerrar partida antes do tempo
    public void initDialogEncerrar(View view){
        dialogEncerrar = new Dialog(PartidaAtual.this);
        dialogEncerrar.setContentView(R.layout.dialog_encerrar);
        dialogEncerrar.show();
    }

    public void buttonEncerrar(View view){


        dialogEncerrar.dismiss();
        resetChronometer(view);
    }

    //resetar cronometro
    public void resetChronometer(View view){
        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer2);
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        millisElapsed = 0;
        isStooped = true;
        Button button = (Button) findViewById(R.id.buttonPlayStop);
        button.setText("Iniciar");

        golA = 0;
        golB = 0;
        cartaoA = 0;
        cartaoB = 0;
    }

    @Override
    public void onBackPressed() {
        saveJogadores();
        saveTime();
        finish();
    }

    protected void onResume() {
        loadJogador();
        loadTime();
        initList();
        initChronometer();
        super.onResume();
    }

    public void loadJogador(){

        try{
            fis = this.openFileInput("t.tmp");
            ois = new ObjectInputStream(fis);
            jogadores = (ArrayList<Jogador>) ois.readObject();
          //  time = (ArrayList<Jogador>) ois.readObject();
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
         //   Log.d("CSI489", "Salvo: " + jogadores.toString());
         //   oos.writeObject(time);
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
