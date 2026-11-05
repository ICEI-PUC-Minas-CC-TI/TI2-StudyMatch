-- =========================================================
-- StudyMatch — Script de Criação do Banco e Dados Iniciais
-- =========================================================

-- 1. estudante 
CREATE TABLE estudante (
    cpf CHAR(11) PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    curso VARCHAR(100) NOT NULL,
    semestre INT NOT NULL,
    modalidade_preferida VARCHAR(20) NOT NULL,
    CHECK (modalidade_preferida IN ('presencial', 'online', 'hibrido')),
    CHECK (cpf ~ '^[0-9]{11}$')
);

-- 2. disciplina (dado mockado/pré-cadastrado)
CREATE TABLE disciplina (
    id_disciplina SERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(120) NOT NULL
);

-- 3. horario (catálogo fixo/mockado, de hora em hora)
CREATE TABLE horario (
    id_horario SERIAL PRIMARY KEY,
    dia_semana VARCHAR(15) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    CHECK (hora_fim > hora_inicio)
);

-- 4. matricula 
CREATE TABLE matricula (
    cpf CHAR(11) NOT NULL REFERENCES estudante(cpf) ON DELETE CASCADE,
    id_disciplina INT NOT NULL REFERENCES disciplina(id_disciplina) ON DELETE CASCADE,
    nivel_conhecimento VARCHAR(20) NOT NULL,
    objetivo VARCHAR(30) NOT NULL,
    PRIMARY KEY (cpf, id_disciplina),
    CHECK (nivel_conhecimento IN ('basico', 'intermediario', 'avancado')),
    CHECK (objetivo IN ('preparar_prova', 'reforcar_base', 'aprofundar', 'trabalhos'))
);

-- 5. disponibilidade 
CREATE TABLE disponibilidade (
    cpf CHAR(11) NOT NULL REFERENCES estudante(cpf) ON DELETE CASCADE,
    id_horario INT NOT NULL REFERENCES horario(id_horario) ON DELETE CASCADE,
    PRIMARY KEY (cpf, id_horario)
);

-- 6. grupo
CREATE TABLE grupo (
    id_grupo SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    objetivo VARCHAR(30) NOT NULL,
    modalidade VARCHAR(20) NOT NULL,
    descricao VARCHAR(255),
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    limite_participantes INT NOT NULL DEFAULT 6,
    id_disciplina INT NOT NULL REFERENCES disciplina(id_disciplina),
    cpf_criador CHAR(11) NOT NULL REFERENCES estudante(cpf),
    CHECK (modalidade IN ('presencial', 'online', 'hibrido')),
    CHECK (objetivo IN ('preparar_prova', 'reforcar_base', 'aprofundar', 'trabalhos')),
    CHECK (limite_participantes >= 2)
);

-- 7. horario_grupo 
CREATE TABLE horario_grupo (
    id_grupo INT NOT NULL REFERENCES grupo(id_grupo) ON DELETE CASCADE,
    id_horario INT NOT NULL REFERENCES horario(id_horario) ON DELETE CASCADE,
    PRIMARY KEY (id_grupo, id_horario)
);

-- 8. participa 
CREATE TABLE participa (
    cpf CHAR(11) NOT NULL REFERENCES estudante(cpf) ON DELETE CASCADE,
    id_grupo INT NOT NULL REFERENCES grupo(id_grupo) ON DELETE CASCADE,
    data_entrada TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (cpf, id_grupo)
);

-- 9. plano_estudo (gerado por IA — só criação e consulta)
CREATE TABLE plano_estudo (
    id_plano SERIAL PRIMARY KEY,
    cpf CHAR(11) NOT NULL REFERENCES estudante(cpf) ON DELETE CASCADE,
    id_disciplina INT NOT NULL REFERENCES disciplina(id_disciplina) ON DELETE CASCADE,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_prova DATE NOT NULL,
    tempo_disponivel VARCHAR(30) NOT NULL,
    dificuldades VARCHAR(255) NOT NULL,
    conteudo TEXT NOT NULL
);


INSERT INTO disciplina (codigo, nome) VALUES
('CC101', 'ALGORITMOS E ESTRUTURAS DE DADOS I'),
('CC102', 'CÁLCULO I'),
('CC103', 'INTRODUÇÃO À COMPUTAÇÃO'),
('CC104', 'MATEMÁTICA DISCRETA E COMPUTABILIDADE'),
('CC105', 'MENTORIA DE CARREIRA'),
('CC106', 'METODOLOGIAS DE EXTENSÃO UNIVERSITÁRIA'),
('CC107', 'TRABALHO INTERDISCIPLINAR I: PROGRAMAÇÃO'),
('CC201', 'ALGORITMOS E ESTRUTURAS DE DADOS II'),
('CC202', 'BANCO DE DADOS I'),
('CC203', 'CÁLCULO II'),
('CC204', 'DESENVOLVIMENTO DE INTERFACES WEB'),
('CC205', 'ENGENHARIA DE SOFTWARE I'),
('CC206', 'TRABALHO INTERDISCIPLINAR II: FRONT-END'),
('CC301', 'ALGORITMOS E ESTRUTURAS DE DADOS III'),
('CC302', 'ARQUITETURA DE COMPUTADORES I'),
('CC303', 'CÁLCULO III'),
('CC304', 'ENGENHARIA DE SOFTWARE II'),
('CC305', 'ESTATÍSTICA E PROBABILIDADE'),
('CC306', 'INTRODUÇÃO À PESQUISA EM INFORMÁTICA'),
('CC307', 'TRABALHO INTERDISCIPLINAR III: BACK-END'),
('CC401', 'ARQUITETURA DE COMPUTADORES II'),
('CC402', 'BANCO DE DADOS II'),
('CC403', 'COMUNICAÇÃO PERSUASIVA E APRESENTAÇÃO DE IDEIAS'),
('CC404', 'DESENVOLVIMENTO DE APLICAÇÕES MÓVEIS'),
('CC405', 'FUNDAMENTOS DE INTELIGÊNCIA ARTIFICIAL'),
('CC406', 'LINGUAGENS DE PROGRAMAÇÃO'),
('CC407', 'TEORIA DOS GRAFOS'),
('CC408', 'TRABALHO INTERDISCIPLINAR IV: APLICAÇÕES MÓVEIS'),
('CC501', 'ARQUITETURA DE COMPUTADORES III'),
('CC502', 'FILOSOFIA: LÓGICA E PENSAMENTO CRÍTICO'),
('CC503', 'INTRODUÇÃO A INOVAÇÃO TECNOLÓGICA'),
('CC504', 'OPTATIVA I'),
('CC505', 'PROJETO E ANÁLISE DE ALGORITMOS'),
('CC506', 'REDES DE COMPUTADORES I'),
('CC507', 'SISTEMAS OPERACIONAIS'),
('CC508', 'STARTUP: DESENVOLVIMENTO E MERCADO PARA TECNOLOGIA'),
('CC509', 'TÓPICOS EM COMPUTAÇÃO I'),
('CC510', 'TRABALHO INTERDISCIPLINAR V: SISTEMAS COMPUTACIONAIS'),
('CC601', 'CIÊNCIA DE DADOS'),
('CC602', 'COMPUTAÇÃO DISTRIBUÍDA'),
('CC603', 'COMPUTAÇÃO PARALELA'),
('CC604', 'FUNDAMENTOS TEÓRICOS DA COMPUTAÇÃO'),
('CC605', 'GEOMETRIA ANALÍTICA E ÁLGEBRA LINEAR'),
('CC606', 'INTELIGÊNCIA ARTIFICIAL'),
('CC607', 'OPTATIVA II'),
('CC608', 'TÓPICOS EM COMPUTAÇÃO II'),
('CC609', 'TRABALHO INTERDISCIPLINAR VI: SISTEMAS INTELIGENTES'),
('CC701', 'COMPILADORES'),
('CC702', 'COMPUTAÇÃO GRÁFICA'),
('CC703', 'CULTURA RELIGIOSA: SABERES E TRADIÇÕES'),
('CC704', 'FILOSOFIA: ANTROPOLOGIA E ÉTICA'),
('CC705', 'OPTATIVA III'),
('CC706', 'PROCESSAMENTO E ANÁLISE DE IMAGENS'),
('CC707', 'REDES DE COMPUTADORES II'),
('CC708', 'TÓPICOS EM COMPUTAÇÃO III'),
('CC709', 'TRABALHO DE CONCLUSÃO DE CURSO I'),
('CC801', 'CIBERSEGURANÇA'),
('CC802', 'CIDADANIA E RESPONSABILIDADE SOCIAL'),
('CC803', 'CULTURA RELIGIOSA: HUMANISMO, SOCIEDADE E ECOLOGIA INTEGRAL'),
('CC804', 'MODELAGEM E AVALIAÇÃO DE DESEMPENHO'),
('CC805', 'OPTATIVA IV'),
('CC806', 'OPTATIVA V'),
('CC807', 'OTIMIZAÇÃO DE SISTEMAS'),
('CC808', 'SEGURANÇA E AUDITORIA DE SISTEMAS'),
('CC809', 'SEMINÁRIOS AVANÇADOS EM CIÊNCIA DA COMPUTAÇÃO'),
('CC810', 'TÓPICOS EM COMPUTAÇÃO IV'),
('CC811', 'TRABALHO DE CONCLUSÃO DE CURSO II');


INSERT INTO horario (dia_semana, hora_inicio, hora_fim) VALUES
('Segunda', '07:00:00', '08:00:00'),
('Segunda', '08:00:00', '09:00:00'),
('Segunda', '09:00:00', '10:00:00'),
('Segunda', '10:00:00', '11:00:00'),
('Segunda', '11:00:00', '12:00:00'),
('Segunda', '12:00:00', '13:00:00'),
('Segunda', '13:00:00', '14:00:00'),
('Segunda', '14:00:00', '15:00:00'),
('Segunda', '15:00:00', '16:00:00'),
('Segunda', '16:00:00', '17:00:00'),
('Segunda', '17:00:00', '18:00:00'),
('Segunda', '18:00:00', '19:00:00'),
('Segunda', '19:00:00', '20:00:00'),
('Segunda', '20:00:00', '21:00:00'),
('Segunda', '21:00:00', '22:00:00'),
('Terca', '07:00:00', '08:00:00'),
('Terca', '08:00:00', '09:00:00'),
('Terca', '09:00:00', '10:00:00'),
('Terca', '10:00:00', '11:00:00'),
('Terca', '11:00:00', '12:00:00'),
('Terca', '12:00:00', '13:00:00'),
('Terca', '13:00:00', '14:00:00'),
('Terca', '14:00:00', '15:00:00'),
('Terca', '15:00:00', '16:00:00'),
('Terca', '16:00:00', '17:00:00'),
('Terca', '17:00:00', '18:00:00'),
('Terca', '18:00:00', '19:00:00'),
('Terca', '19:00:00', '20:00:00'),
('Terca', '20:00:00', '21:00:00'),
('Terca', '21:00:00', '22:00:00'),
('Quarta', '07:00:00', '08:00:00'),
('Quarta', '08:00:00', '09:00:00'),
('Quarta', '09:00:00', '10:00:00'),
('Quarta', '10:00:00', '11:00:00'),
('Quarta', '11:00:00', '12:00:00'),
('Quarta', '12:00:00', '13:00:00'),
('Quarta', '13:00:00', '14:00:00'),
('Quarta', '14:00:00', '15:00:00'),
('Quarta', '15:00:00', '16:00:00'),
('Quarta', '16:00:00', '17:00:00'),
('Quarta', '17:00:00', '18:00:00'),
('Quarta', '18:00:00', '19:00:00'),
('Quarta', '19:00:00', '20:00:00'),
('Quarta', '20:00:00', '21:00:00'),
('Quarta', '21:00:00', '22:00:00'),
('Quinta', '07:00:00', '08:00:00'),
('Quinta', '08:00:00', '09:00:00'),
('Quinta', '09:00:00', '10:00:00'),
('Quinta', '10:00:00', '11:00:00'),
('Quinta', '11:00:00', '12:00:00'),
('Quinta', '12:00:00', '13:00:00'),
('Quinta', '13:00:00', '14:00:00'),
('Quinta', '14:00:00', '15:00:00'),
('Quinta', '15:00:00', '16:00:00'),
('Quinta', '16:00:00', '17:00:00'),
('Quinta', '17:00:00', '18:00:00'),
('Quinta', '18:00:00', '19:00:00'),
('Quinta', '19:00:00', '20:00:00'),
('Quinta', '20:00:00', '21:00:00'),
('Quinta', '21:00:00', '22:00:00'),
('Sexta', '07:00:00', '08:00:00'),
('Sexta', '08:00:00', '09:00:00'),
('Sexta', '09:00:00', '10:00:00'),
('Sexta', '10:00:00', '11:00:00'),
('Sexta', '11:00:00', '12:00:00'),
('Sexta', '12:00:00', '13:00:00'),
('Sexta', '13:00:00', '14:00:00'),
('Sexta', '14:00:00', '15:00:00'),
('Sexta', '15:00:00', '16:00:00'),
('Sexta', '16:00:00', '17:00:00'),
('Sexta', '17:00:00', '18:00:00'),
('Sexta', '18:00:00', '19:00:00'),
('Sexta', '19:00:00', '20:00:00'),
('Sexta', '20:00:00', '21:00:00'),
('Sexta', '21:00:00', '22:00:00'),
('Sabado', '08:00:00', '09:00:00'),
('Sabado', '09:00:00', '10:00:00'),
('Sabado', '10:00:00', '11:00:00'),
('Sabado', '11:00:00', '12:00:00'),
('Sabado', '12:00:00', '13:00:00');