package com.studymatch.app;

import static spark.Spark.*;
import com.studymatch.service.*;

public class Aplicacao {

    private static EstudanteService estudanteService = new EstudanteService();
    private static DisciplinaService disciplinaService = new DisciplinaService();
    private static HorarioService horarioService = new HorarioService();
    private static MatriculaService matriculaService = new MatriculaService();
    private static GrupoService grupoService = new GrupoService();
    private static ParticipaService participaService = new ParticipaService();
    private static PlanoEstudoService planoEstudoService = new PlanoEstudoService();
    private static HorarioGrupoService horarioGrupoService = new HorarioGrupoService();
    private static DisponibilidadeService disponibilidadeService = new DisponibilidadeService();

    public static void main(String[] args) {
        port(6789);
        staticFiles.location("/public");

        // --- ROTAS: ESTUDANTE ---
        post("/estudante/insert", (req, res) -> estudanteService.insert(req, res));
        get("/estudante/:id", (req, res) -> estudanteService.get(req, res));
        get("/estudante/list/:orderby", (req, res) -> estudanteService.getAll(req, res));
        post("/estudante/update/:id", (req, res) -> estudanteService.update(req, res));
        get("/estudante/delete/:id", (req, res) -> estudanteService.delete(req, res));
        post("/estudante/login", (req, res) -> estudanteService.login(req, res));

        // --- ROTAS: GRUPO ---
        post("/grupo/insert", (req, res) -> grupoService.insert(req, res));
        get("/grupo/:id", (req, res) -> grupoService.get(req, res));
        get("/grupo/list/:orderby", (req, res) -> grupoService.getAll(req, res));
        post("/grupo/update/:id", (req, res) -> grupoService.update(req, res));
        get("/grupo/delete/:id", (req, res) -> grupoService.delete(req, res));

        // --- ROTAS: DISCIPLINA ---
        get("/disciplina/:id", (req, res) -> disciplinaService.get(req, res));
        get("/disciplina/list/:orderby", (req, res) -> disciplinaService.getAll(req, res));

        // --- ROTAS: MATRICULA ---
        post("/matricula/insert", (req, res) -> matriculaService.insert(req, res));
        get("/matricula/list/:orderby", (req, res) -> matriculaService.getAll(req, res));
        get("/matricula/delete/:cpf/:idDisciplina", (req, res) -> matriculaService.delete(req, res));

        // --- ROTAS: PARTICIPA (Entrar/Sair de Grupos) ---
        post("/participa/insert", (req, res) -> participaService.insert(req, res));
        get("/participa/list/:orderby", (req, res) -> participaService.getAll(req, res));
        get("/participa/delete/:cpf/:idGrupo", (req, res) -> participaService.delete(req, res));

        // --- ROTAS: PLANO DE ESTUDO ---
        post("/planoestudo/insert", (req, res) -> planoEstudoService.insert(req, res));
        get("/planoestudo/:id", (req, res) -> planoEstudoService.get(req, res));
        get("/planoestudo/list/:orderby", (req, res) -> planoEstudoService.getAll(req, res));

        // --- ROTAS: HORARIO ---
        get("/horario/:id", (req, res) -> horarioService.get(req, res));
        get("/horario/list/:orderby", (req, res) -> horarioService.getAll(req, res));

        // --- ROTAS: DISPONIBILIDADE ---
        post("/disponibilidade/insert", (req, res) -> disponibilidadeService.insert(req, res));
        get("/disponibilidade/list/:orderby", (req, res) -> disponibilidadeService.getAll(req, res));

        // --- ROTAS: HORARIO GRUPO ---
        post("/horariogrupo/insert", (req, res) -> horarioGrupoService.insert(req, res));
        get("/horariogrupo/list/:orderby", (req, res) -> horarioGrupoService.getAll(req, res));

        System.out.println("Servidor rodando em: http://localhost:6789/index.html");
    }
}