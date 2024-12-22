AlunoService - Serviço HTTP em Java

Descrição
- GET /aluno/:id: Recupera os dados de um aluno com ID específico.
- DELETE /aluno/:id: Exclui permanentemente um aluno do sistema.
- POST /aluno: Cria um novo aluno com ID único e dados gerados dinamicamente.
As respostas são formatadas como HTML, e o serviço lida com a concorrência de forma apropriada, utilizando estruturas seguras para múltiplas threads.

Estrutura do Código

O código está contido em uma única classe chamada AlunoService. Esta abordagem foi escolhida para simplificar a estrutura do projeto e facilitar o entendimento, dado o escopo relativamente limitado do problema. Todos os métodos e lógicas estão centralizados, tornando o projeto mais acessível para revisão e execução.

Principais Decisões de Implementação

0. Bibliotecas Padrão

Utilizamos a biblioteca com.sun.net.httpserver.HttpServer do Java SE para implementar o servidor HTTP. Isso elimina a necessidade de dependências externas e facilita a execução em qualquer ambiente Java.

1. Framework Spark

O Spark Framework foi escolhido por sua simplicidade e capacidade de criar serviços HTTP rapidamente, sem a necessidade de configuração complexa.
Ideal para protótipos ou serviços pequenos, como este.

2. Mapeamento de Rotas

Cada operação HTTP (GET, DELETE, POST) é mapeada diretamente a um endpoint.
A rota get("/aluno/:id") lida com a recuperação de alunos, enquanto delete("/aluno/:id") e post("/aluno") lidam com exclusão e criação, respectivamente.

3. Armazenamento em Memória

Os dados dos alunos são armazenados em uma ConcurrentHashMap, que oferece segurança para múltiplas threads sem a necessidade de sincronização manual.
IDs únicos são gerados com um AtomicInteger, garantindo que não haja colisões mesmo em ambientes concorrentes.

4. Formato de Resposta

Todas as respostas são formatadas como HTML para atender aos requisitos especificados.
Mensagens de erro também são renderizadas em HTML, garantindo consistência na apresentação.

5. Código em Uma Classe

- A decisão de agrupar toda a lógica em uma única classe foi motivada por:
- Escopo restrito do projeto, que não exige separação complexa de camadas.
- Facilidade para novos desenvolvedores entenderem o fluxo do programa.
- Redução da complexidade de configuração.
- Caso o sistema fosse expandido, o código poderia ser modularizado em camadas (controladores, serviços e repositórios) para maior organização.

6. Código Simples e Direto
O código foi projetado para ser simples, com todas as funções em uma única classe, tornando-o ideal para aprendizado ou pequenas demonstrações.

Como Executar

Teste as rotas usando Postman, curl ou qualquer ferramenta de requisições HTTP:

GET /aluno/:id:

curl http://localhost:4567/aluno/1

POST /aluno:

curl -X POST http://localhost:4567/aluno

DELETE /aluno/:id:

curl -X DELETE http://localhost:4567/aluno/1

Melhorias Futuras

- Modularizar o código em camadas (Controller, Service, Repository) para facilitar manutenção e escalabilidade.
- Substituir o armazenamento em memória por um banco de dados persistente.
- Adicionar autenticação e autorização para proteger as operações de inclusão e exclusão.
- Melhorar o tratamento de erros para casos inesperados.