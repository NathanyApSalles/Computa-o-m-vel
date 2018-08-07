package br.ufop.nathany.futmannathany;

import java.io.Serializable;

/**
 * Created by nathany on 24/06/17.
 */

public class Jogador implements Serializable {

    public int idJogador;
    public String nomeJogador = "";
    public String posicaoJogador;
    public long telJogador = -9999999;
    public boolean isPresent = false;
    public int qtdPartidas = 0;
    public int  qtdPeladasVencedoras= 0;
    public int qtdPeladasEmpates = 0;



    public Jogador(int idJogador, String nomeJogador, String posicaoJogador, long telJogador) {
        this.idJogador = idJogador;
        this.nomeJogador = nomeJogador;
        this.posicaoJogador = posicaoJogador;
        this.telJogador = telJogador;
       // this.isPresent = isPresent;
    }


    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public String getPosicaoJogador() {
        return posicaoJogador;
    }

    public void setPosicaoJogador(String posicaoJogador) {
        this.posicaoJogador = posicaoJogador;
    }

    public long getTelJogador() {
        return telJogador;
    }

    public void setTelJogador(long telJogador) {
        this.telJogador = telJogador;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public int getQtdPartidas() {
        return qtdPartidas;
    }

    public void setQtdPartidas(int qtdPartidas) {
        this.qtdPartidas = qtdPartidas;
    }

    public int getQtdPeladasVencedoras() {
        return qtdPeladasVencedoras;
    }

    public void setQtdPeladasVencedoras(int qtdPeladasVencedoras) {
        this.qtdPeladasVencedoras = qtdPeladasVencedoras;
    }
    public int getQtdPeladasEmpates() {
        return qtdPeladasEmpates;
    }

    public void setQtdPeladasEmpates(int qtdPeladasEmpates) {
        this.qtdPeladasEmpates = qtdPeladasEmpates;
    }



    @Override
    public String toString() {
        return nomeJogador;
    }

//
//    public String toString() {
//        return "Jogador{" +
//                ", nomeJogador='" + nomeJogador + '\'' +
//                ", isPresent=" + isPresent +
//                ", qtdPartidas=" + qtdPartidas +
//                ", qtdPeladasVencedoras=" + qtdPeladasVencedoras +
//                ", qtdPeladasEmpates=" + qtdPeladasEmpates +
//                '}';
//    }
}
