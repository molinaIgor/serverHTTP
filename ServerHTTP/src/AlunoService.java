import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class AlunoService {

private static final ConcurrentHashMap<Integer, String> alunos = new ConcurrentHashMap<>();
private static final AtomicInteger idGenerator = new AtomicInteger(1);

public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

    server.createContext("/aluno", new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            URI uri = exchange.getRequestURI();
            String path = uri.getPath();

            if (method.equals("GET") && path.matches("/aluno/\\d+")) {
                handleGet(exchange);
            } else if (method.equals("DELETE") && path.matches("/aluno/\\d+")) {
                handleDelete(exchange);
            } else if (method.equals("POST") && path.equals("/aluno")) {
                handlePost(exchange);
            } else {
                sendResponse(exchange, 404, "<html><body><h1>404 Not Found</h1></body></html>");
            }
        }
    });

    server.setExecutor(null); // Default executor
    server.start();
    System.out.println("Server started on port 8080");
}

private static void handleGet(HttpExchange exchange) throws IOException {
    String path = exchange.getRequestURI().getPath();
    int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

    String aluno = alunos.get(id);
    if (aluno != null) {
        String response = String.format("<html><body><h1>Aluno</h1><p>ID: %d</p><p>Nome: %s</p></body></html>", id, aluno);
        sendResponse(exchange, 200, response);
    } else {
        sendResponse(exchange, 404, "<html><body><h1>404 Not Found</h1><p>Aluno não encontrado.</p></body></html>");
    }
}

private static void handleDelete(HttpExchange exchange) throws IOException {
    String path = exchange.getRequestURI().getPath();
    int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

    if (alunos.remove(id) != null) {
        sendResponse(exchange, 200, "<html><body><h1>Aluno Excluído</h1><p>O aluno foi removido com sucesso.</p></body></html>");
    } else {
        sendResponse(exchange, 404, "<html><body><h1>404 Not Found</h1><p>Aluno não encontrado para exclusão.</p></body></html>");
    }
}

private static void handlePost(HttpExchange exchange) throws IOException {
    int id = idGenerator.getAndIncrement();
    String nome = "Aluno" + id;
    alunos.put(id, nome);

    String response = String.format("<html><body><h1>Aluno Criado</h1><p>ID: %d</p><p>Nome: %s</p></body></html>", id, nome);
    sendResponse(exchange, 201, response);
}

private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
    exchange.sendResponseHeaders(statusCode, response.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
}

}