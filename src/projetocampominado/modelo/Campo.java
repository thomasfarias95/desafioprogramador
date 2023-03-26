package projetocampominado.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();

    Campo(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }
    public void registrarObservador(CampoObservador observador){
        observadores.add(observador);
    }
    private void notificarObservadores(CampoEvento evento){
        observadores.stream()
                .forEach(o -> o.eventoOcorreu(this, evento));
    }
     public void adicionarVizinhos(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
        }
    }

    public void alternarMarcacao() {
        if(!aberto){
            marcado = !marcado;
            if (marcado) {
                notificarObservadores(CampoEvento.MARCAR);
            } else {
                notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    public boolean abrir() {
        if(!aberto && !marcado) {
            if(minado){
                notificarObservadores(CampoEvento.EXPLODIR);
                return true;
            }
            setAberto(true);

            if (vizinhancaSegura ()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;

        } else {
            return false;
        }
    }

    public void abrirBomba() {
        if(minado){
            notificarObservadores(CampoEvento.ABRIRBOMBA);

        }
    }

    public boolean vizinhancaSegura() {
        return vizinhos.stream()
                .noneMatch(v -> v.minado);
    }
    void minar(){
        minado = true;
    }
    public boolean isMinado(){
        return minado;
    }
    public boolean isMarcado(){
        return marcado;
    }
    public void setAberto(boolean aberto){
        this.aberto = aberto;
        if (aberto){
            notificarObservadores(CampoEvento.ABRIR);
        }
    }

    public boolean isAberto() {
        return aberto;
    }
    public boolean isFechado(){
        return !isAberto();
    }
    public int getLinha(){
        return linha;
    }
    public int getColuna(){
        return coluna;
    }
    boolean objetivoAlcancado(){
        boolean desvendado  = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    public int minasNaVizinhanca() {
        return (int) vizinhos.stream()
                .filter(v -> v.minado)
                .count();
    }
    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
        notificarObservadores(CampoEvento.REINICIAR);
    }
    public String toString() {
        if (marcado) {
            return "X";
        } else if (aberto && minado) {
            return "*";
        } else if (aberto && minasNaVizinhanca() > 0) {
            return Long.toString(minasNaVizinhanca());
        } else if(aberto) {
            return " ";
        } else {
            return "?";
        }
    }
}

