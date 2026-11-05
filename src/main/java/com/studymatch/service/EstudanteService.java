package com.studymatch.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.studymatch.dao.EstudanteDAO;
import com.studymatch.model.Estudante;
import spark.Request;
import spark.Response;

public class EstudanteService {

    private EstudanteDAO dao = new EstudanteDAO();
    private Gson gson = new Gson();
    private static final List<String> MODALIDADES_VALIDAS = Arrays.asList("presencial", "online", "hibrido");

    public Object insert(Request request, Response response) {
        response.type("application/json");
        try {
            String cpf = request.queryParams("cpf");
            String nome = request.queryParams("nome");
            String email = request.queryParams("email");
            String senha = request.queryParams("senha");
            String curso = request.queryParams("curso");
            int semestre = Integer.parseInt(request.queryParams("semestre"));
            String modalidade = request.queryParams("modalidadePreferida");

            Estudante e = new Estudante(cpf, nome, email, senha, curso, semestre, modalidade);
            validar(e);

            if (dao.buscarPorEmail(e.getEmail()) != null) {
                response.status(400);
                return "{\"erro\": \"Já existe um estudante cadastrado com esse e-mail.\"}";
            }

            dao.inserir(e);
            response.status(201);
            return gson.toJson(e);
        } catch (Exception e) {
            response.status(400);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object login(Request request, Response response) {
        response.type("application/json");
        try {
            String email = request.queryParams("email");
            String senha = request.queryParams("senha");

            Estudante estudante = dao.buscarPorEmail(email);
            if (estudante == null || !estudante.getSenha().equals(senha)) {
                response.status(401);
                return "{\"erro\": \"E-mail ou senha inválidos.\"}";
            }

            response.status(200);
            return gson.toJson(estudante);
        } catch (Exception e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object get(Request request, Response response) {
        response.type("application/json");
        try {
            String cpf = request.params(":id");
            Estudante e = dao.buscarPorCpf(cpf);
            if (e != null) {
                return gson.toJson(e);
            }
            response.status(404);
            return "{\"erro\": \"Estudante não encontrado.\"}";
        } catch (SQLException e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object getAll(Request request, Response response) {
        response.type("application/json");
        try {
            List<Estudante> lista = dao.listarTodos();
            return gson.toJson(lista);
        } catch (SQLException e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object update(Request request, Response response) {
        response.type("application/json");
        try {
            String cpf = request.params(":id");
            Estudante e = new Estudante(
                cpf,
                request.queryParams("nome"),
                request.queryParams("email"),
                request.queryParams("senha"),
                request.queryParams("curso"),
                Integer.parseInt(request.queryParams("semestre")),
                request.queryParams("modalidadePreferida")
            );
            validar(e);
            dao.atualizar(e);
            return gson.toJson(e);
        } catch (Exception e) {
            response.status(400);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object delete(Request request, Response response) {
        response.type("application/json");
        try {
            String cpf = request.params(":id");
            dao.remover(cpf);
            response.status(200);
            return "{\"mensagem\": \"Removido com sucesso.\"}";
        } catch (SQLException e) {
            response.status(500);
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
    }

    private void validar(Estudante e) {
        if (e.getCpf() == null || !e.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve ter exatamente 11 dígitos numéricos.");
        }
        if (e.getNome() == null || e.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (e.getEmail() == null || e.getEmail().isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório.");
        }
        if (!MODALIDADES_VALIDAS.contains(e.getModalidadePreferida())) {
            throw new IllegalArgumentException("Modalidade preferida deve ser presencial, online ou hibrido.");
        }
    }
}