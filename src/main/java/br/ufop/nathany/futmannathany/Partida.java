package br.ufop.nathany.futmannathany;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nathany on 29/06/17.
 */

public class Partida implements Serializable {
    public int idPartida;
    public int qtdJogadores;
    public String timePartida;
    public ArrayList<Jogador> jogadoresNaPartida;

    public Partida(int idPartida, int qtdJogadores, String timePartida, ArrayList<Jogador> jogadoresNaPartida) {
        this.idPartida = idPartida;
        this.qtdJogadores = qtdJogadores;
        this.timePartida = timePartida;
        this.jogadoresNaPartida = jogadoresNaPartida;
    }

    public ArrayList<Jogador> getJogadoresNaPartida() {
        return jogadoresNaPartida;
    }

    public void setJogadoresNaPartida(ArrayList<Jogador> jogadoresNaPartida) {
        this.jogadoresNaPartida = jogadoresNaPartida;
    }



    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getQtdJogadores() {
        return qtdJogadores;
    }

    public void setQtdJogadores(int qtdJogadores) {
        this.qtdJogadores = qtdJogadores;
    }

    public String getTimePartida() {
        return timePartida;
    }

    public void setTimePartida(String timePartida) {
        this.timePartida = timePartida;
    }


}
