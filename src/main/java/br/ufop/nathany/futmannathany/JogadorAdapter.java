package br.ufop.nathany.futmannathany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by nathany on 24/06/17.
 */

public class JogadorAdapter extends BaseAdapter {

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

    public JogadorAdapter(Context context, List<Jogador> jogadores) {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Jogador jogador = jogadores.get(i);

//        Toast.makeText(context, jogador.toString(), Toast.LENGTH_SHORT).show();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.jogador_detail,null);


        TextView textCode = (TextView) v.findViewById(R.id.textView);
        textCode.setText(""+(jogador.nomeJogador));


        return v;
    }
}
