package poo.atividade;

import java.io.IOException;


public class App {
    public static void main( String[] args ) throws IOException{
        
        String[] resultado = ResultadoMegasena.obtemUltimoResultado();
        for (String dezena: resultado) {
        System.out.print(dezena + "-");
        }
    }
}
