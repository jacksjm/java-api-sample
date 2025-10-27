import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class App {
    public static void main(String[] args) throws IOException {
        // Criando o servidor HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(8089), 0);

        // Definindo as rotas
        server.createContext("/user", new Usuario());

        // Iniciando o servidor
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em http://localhost:8089/");
    }
}
