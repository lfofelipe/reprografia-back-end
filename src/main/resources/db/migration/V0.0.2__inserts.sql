INSERT INTO permissao(nome, papel, descricao, permissao_pai_id)
    VALUES ('Permissão Geral.', 'ROLE_GERAL', 'Permissão geral da aplicação. Todos os perfis devem ter ao menos essa permissão.', null);

INSERT INTO permissao(nome, papel, descricao, permissao_pai_id)
    VALUES ('Permissão de Professor.', 'ROLE_PROFESSOR', 'Usuário com permissao de professor.', null);

INSERT INTO permissao(nome, papel, descricao, permissao_pai_id)
    VALUES ('Permissão de TI.', 'ROLE_TI', 'Usuário com permissão de TI.', null);

INSERT INTO permissao(nome, papel, descricao, permissao_pai_id)
  VALUES ('Permissão de Gerente.', 'ROLE_GERENTE', 'Usuário com permissão de Gerente.', null);

INSERT INTO permissao(nome, papel, descricao, permissao_pai_id)
  VALUES ('Permissão de reprografia.', 'ROLE_REPROGRAFIA', 'Usuário com permissão de Reprografia.', null);

INSERT INTO permissao(nome, papel, descricao, permissao_pai_id)
  VALUES ('Permissão de coordenador.', 'ROLE_COORDENADOR', 'Usuário com permissão de Coordenador.', null);

INSERT INTO permissao(nome, papel, descricao, permissao_pai_id)
  VALUES ('Permissão de diretor.', 'ROLE_DIRETOR', 'Usuário com permissão de Diretor.', null);

INSERT INTO permissao(nome, papel, descricao, permissao_pai_id)
  VALUES ('Permissão de gestor de custos.', 'ROLE_GESTOR_DE_CUSTOS', 'Usuário com permissão de Gestor de custos.', null);


INSERT INTO perfil(nome, descricao)
    VALUES ('Perfil TI', 'Perfil com permissão de TI.');

INSERT INTO perfil(nome, descricao)
    VALUES ('Perfil Professor', 'Perfil com permissão de professor.');

INSERT INTO perfil(nome, descricao)
  VALUES ('Perfil Gerente', 'Perfil com permissão de gerente.');

INSERT INTO perfil(nome, descricao)
  VALUES ('Perfil Coordenador', 'Perfil com permissão de coordenador.');

INSERT INTO perfil(nome, descricao)
  VALUES ('Perfil Reprografia', 'Perfil com permissão de reprografia.');

INSERT INTO perfil(nome, descricao)
  VALUES ('Perfil Diretor', 'Perfil com permissão de diretor.');

INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (1, 1);
INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (1, 3);

INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (2, 1);
INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (2, 2);

INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (3, 1);
INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (3, 4);
INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (3, 8);

INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (4, 1);
INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (4, 6);

INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (5, 1);
INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (5, 5);

INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (6, 1);
INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (6, 7);
INSERT INTO perfil_permissao(perfil_id, permissoes_id) VALUES (6, 8);



INSERT INTO pessoa(nome, login, senha, email, ativo, tipo_pessoa, perfil_id)
    VALUES ('TI - Suporte', 'suporte', 'e10adc3949ba59abbe56e057f20f883e', 'ti@reprografia.com.br', 1, 'normal', 1);

INSERT INTO pessoa(nome, login, senha, email, ativo, tipo_pessoa, perfil_id)
    VALUES ('Professor', 'professor', 'e10adc3949ba59abbe56e057f20f883e', 'professor@reprografia.com.br', 1, 'professor', 2);

INSERT INTO pessoa(nome, login, senha, email, ativo, tipo_pessoa, perfil_id)
    VALUES ('gerente', 'gerente', 'e10adc3949ba59abbe56e057f20f883e', 'gerente@reprografia.com.br', 1, 'normal', 3);

INSERT INTO disciplina(descricao, serie, segmento, ativo)
    VALUES ('Matematica', '8º Serie', 'Tarde', 1);

INSERT INTO disciplina(descricao, serie, segmento, ativo)
VALUES ('Portugues', '8º Serie', 'Manha', 1);

INSeRT INTO professor_disciplina (disciplina_id, professor_id) VALUES (1,2);

INSeRT INTO professor_disciplina (disciplina_id, professor_id) VALUES (2,2);

INSERT INTO centro_custo(cota_preta, custo_fixo_preta, custo_fixo_colorida, valor_exc_preta, valor_exc_colorida, data_inicio, data_fim) VALUES
    (27000, 954.8, 1000, 0.426, 0.0853, STR_TO_DATE( "19/05/2018", "%d/%m/%Y" ), STR_TO_DATE( "20/05/2019", "%d/%m/%Y" ));

INSERT INTO status (descricao) VALUE ("Em Elaboração");

INSERT INTO status (descricao) VALUE ("Em Avaliação");

INSERT INTO status (descricao) VALUE ("Aprovada");

INSERT INTO status (descricao) VALUE ("Em Impressão");

INSERT INTO status (descricao) VALUE ("Cancelada");

INSERT INTO status (descricao) VALUE ("Concluída");
