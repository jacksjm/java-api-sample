import java.util.ArrayList;
import java.util.Date;

// Importações para o servidor HTTP
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Usuario implements HttpHandler {
    private int id;
    private String nome;
    private String email;
    private String dataNascimento;
    private int cep;
    private String genero;
    private String senha;
    private Date dataInscricao;

    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static int contador = 1;

    public Usuario() {

    }

    public Usuario(
        String nome,
        String email,
        String dataNascimento,
        int cep,
        String genero,
        String senha,
        Date dataInscricao
    ) {
        this.id = contador++;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.cep = cep;
        this.genero = genero;
        this.senha = senha;
        this.dataInscricao = dataInscricao;

        usuarios.add(this);
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public void setCep(int cep) {
        this.cep = cep;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public int getId() {
        return this.id;
    }
    public String getNome() {
        return this.nome;
    }
    public String getEmail() {
        return this.email;
    }
    public String getDataNascimento() {
        return this.dataNascimento;
    }
    public int getCep() {
        return this.cep;
    }
    public String getGenero() {
        return this.genero;
    }
    public String getSenha() {
        return this.senha;
    }
    public Date getDataInscricao() {
        return this.dataInscricao;
    }

    @Override
    public String toString() {
        return "" +
            "Id:" + this.getId() +
            "Nome:" + this.getNome() +
            "Email:" + this.getEmail() +
            "Data Nascimento:" + this.getDataNascimento() +
            "CEP:" + this.getCep() +
            "Gênero:" + this.getGenero() +
            "Senha:" + this.getSenha() +
            "Data Inscrição:" + this.getDataInscricao();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            handleGet(exchange);
        } else if (method.equalsIgnoreCase("POST")) {
            handlePost(exchange);
        } else {
            String response = "Método não suportado";
            byte[] bytes = response.getBytes("UTF-8");
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(405, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            json.append(String.format("{\"id\": \"%s\",\"nome\": \"%s\",\"email\": \"%s\",\"cep\": \"%s\",\"genero\": \"%s\",\"senha\": \"%s\",\"dataNascimento\": \"%s\",\"dataInscricao\": \"%s\"}",
                    u.getId(), u.getNome(), u.getEmail(), u.getCep(), u.getGenero(), u.getSenha(), u.getDataNascimento(), u.getDataInscricao()));
            if (i < usuarios.size() - 1) json.append(",");
        }
        json.append("]");

        byte[] bytes = json.toString().getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        // Parse simples (sem Gson)
        // Exemplo: {"nome": "Tadeu", "email": "tadeu@mail.com", "dataNascimento": "01/01/1990", "cep": "89205035", "genero": "Masculino", "senha": "123456"}
        String nome = body.replaceAll(".*\"nome\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        String email = body.replaceAll(".*\"email\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        String dataNascimento = body.replaceAll(".*\"dataNascimento\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        String cep = body.replaceAll(".*\"cep\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        String genero = body.replaceAll(".*\"genero\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        String senha = body.replaceAll(".*\"senha\"\\s*:\\s*\"([^\"]+)\".*", "$1");    


        new Usuario(nome, email, dataNascimento, Integer.parseInt(cep), genero, senha, new Date());

        String response = "{\"message\": \"Usuário adicionado com sucesso\"}";
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(201, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
    
}
