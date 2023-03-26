package projetocampominado.visao;

import projetocampominado.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {
    public PainelTabuleiro (Tabuleiro tabuleiro){
        setLayout(new GridLayout(
                tabuleiro.getLinhas(), tabuleiro.getColunas()));
        tabuleiro.paraCadaCampo (c -> add(new BotaoCampo(c)));
//        tabuleiro.registrarObservador(e -> {
//            if(e.isGanhou()){
//                JOptionPane.showMessageDialog(this, "Ganhou :))!");
//            }else {
//                JOptionPane.showMessageDialog(this, "Perdeu!!");
//            }
//            tabuleiro.reiniciar();
//        });
    }
}
