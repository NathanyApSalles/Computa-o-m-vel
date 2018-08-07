package br.ufop.nathany.futmannathany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by nathany on 24/06/17.
 */

public class JogadorEstatisticasAdapter extends BaseAdapter {

    private Context context;
    private List<Jogador> jogadores;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public JogadorEstatisticasAdapter(Context context, List<Jogador> jogadores) {
        this.context = context;
        this.jogadores = jogadores;
    }

    @Override
    public int getCount() {
        return jogadores.size();
    }

    @Override
    public Object getItem(int i) {
        return jogadores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        Jogador jogador = jogadores.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.estatisticas_detail,null);


        TextView textNome = (TextView) v.findViewById(R.id.tVNomeJogadorEst);
        textNome.setText(jogador.nomeJogador);

        TextView textPartidas = (TextView) v.findViewById(R.id.tVPartidasEst);
        textPartidas.setText(""+(jogador.qtdPartidas));

        TextView textVitorias = (TextView) v.findViewById(R.id.tVVitóriaEst);
        textVitorias.setText(""+(jogador.qtdPeladasVencedoras));

        int derrotas = jogador.qtdPartidas - jogador.qtdPeladasVencedoras - jogador.qtdPeladasEmpates;

        TextView textDerrotas = (TextView) v.findViewById(R.id.tVDerrotaEst);
        textDerrotas.setText(""+derrotas);

        TextView textEmpates = (TextView) v.findViewById(R.id.tVEmpateEst);
        textEmpates.setText(""+(jogador.qtdPeladasEmpates));


        return v;
    }
}
