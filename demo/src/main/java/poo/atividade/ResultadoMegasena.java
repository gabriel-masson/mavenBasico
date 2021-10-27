package poo.atividade;

import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.regex.Matcher;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class ResultadoMegasena {
    
    // https://apiloterias.com.br/app/resultado?loteria=megasena&token=p6lz2krVJIrMMvU&concurso=2100
    private final static String URL = "https://apiloterias.com.br/app/resultado?loteria=megasena&";
    private final static String TOKEN = "p6lz2krVJIrMMvU";
    /** Marcação inicial para extrair as dezenas do retorno HTML. */
   
    /**
     * Método que se conecta ao site da CEF para obter as dezenas do último sorteio.
     * 
     * @return array de Strings, onde cada elemento é uma dezena sorteada.
     */
    public static String[] obtemUltimoResultado() {
        
        Scanner leitor = new Scanner(System.in);

        System.out.print("informe o numero do sorteio: ");
        String numSorteio = leitor.next();
        
        // Criação do cliente HTTP que fará a conexão com o site
        HttpClient httpclient = new DefaultHttpClient();
        try {
            // montar a Url complea com o token e o numero do concurso
            String urlCompleta = URL + "token=" + TOKEN + "&concurso="+ numSorteio;
            // Definição da URL a ser utilizada
            HttpGet httpget = new HttpGet(urlCompleta);

           

            // Manipulador da resposta da conexão com a URL
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            

            // Resposta propriamente dita
            String jsonMegasena = httpclient.execute(httpget, responseHandler);

            // Retorno das dezenas, após tratamento
            String jsonString = jsonMegasena.toString();
           

            return obterDezenas(jsonString);
        } catch (Exception e) {
            // Caso haja erro, dispara exceção.
            throw new RuntimeException(
                    "\n=====================================\nUm erro inesperado ocorreu.\n=====================================",
                    e);
        } finally {
            // Destruição do cliente para liberação dos recursos do sistema.
            httpclient.getConnectionManager().shutdown();
        }
    }

    /**
     * Tratamento da resposta HTML obtida pelo método obtemUltimoResultado().
     * 
     * @param html resposta HTML obtida
     * @return array de Strings, onde cada elemento é uma dezena sorteada.
     */
    private static String[] obterDezenas(String html) {

        // regex

        String regex = "\"dezenas\":[";
        // Posição inicial de onde começam as dezenas
        Integer parteInicial = html.indexOf(regex) + regex.length();
        // Posição final de onde começam as dezenas
        Integer parteFinal = html.indexOf("]");

        // Substring montada com base nas posições, com remoção de espaços.
        String extracao = html.substring(parteInicial, parteFinal);
        
        // Criação de array, com base no método split(), separando por hifen.
        String[] numeros = extracao.split(",");
        return numeros;
    }

}
