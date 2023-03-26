package projetocampominado.modelo;

public class ResultadoEvento {

    private final boolean ganhou;
    ResultadoEvento(boolean ganhou){
        this.ganhou = ganhou;

    }

    public boolean isGanhou(){
        return ganhou;
    }
}
