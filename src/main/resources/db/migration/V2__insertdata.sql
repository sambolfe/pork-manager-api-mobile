-- Inserindo dados na tabela usuario
INSERT INTO usuario (nome, cpf, senha, roles, active)
VALUES ('Pedro', '12345678922', '$2a$12$5EnUPOUtkOSrDiOvLM.GROe6xIpBrs9l5NuscONyWGcY36WS0421a', 'ADMIN', TRUE),
       ('Maria Souza', '98765432131', '$2a$12$5EnUPOUtkOSrDiOvLM.GROe6xIpBrs9l5NuscONyWGcY36WS0421a', 'CRIADOR', TRUE);

-- Inserindo dados na tabela raca
INSERT INTO raca (nome, descricao, caracteristicas, criado_em, atualizado_em)
VALUES ('Duroc', 'Raca de suinos vermelhos', 'Rapido crescimento, carne de qualidade', '2024-01-01', '2024-01-01'),
       ('Landrace', 'Raca de suinos brancos', 'Alta adaptabilidade, boa conversao alimentar', '2024-01-02', '2024-01-02');

-- Inserindo dados na tabela alojamento
INSERT INTO alojamento (nome, tipo, capacidade, status, criado_em, atualizado_em)
VALUES ('Alojamento 1', 'GESTACAO', 100, 'ATIVO', '2024-01-01', '2024-01-01'),
       ('Alojamento 2', 'CRECHE', 50, 'INATIVO', '2024-01-02', '2024-01-02');

-- Inserindo dados na tabela suino
INSERT INTO suino (raca_id, identificacao_orelha, tipo_suino, sexo, data_nasc, observacoes, alojamento_id, usuario_id, criado_em, atualizado_em)
VALUES (1, '1', 'GESTACAO', 'MACHO', '2023-01-01', 'Nenhum', 1, 1, '2024-01-01', '2024-01-01'),
       (2, '2', 'CRECHE', 'FEMEA', '2023-02-01', 'Nenhum', 2, 2, '2024-01-02', '2024-01-02');

-- Inserindo dados na tabela saude
INSERT INTO saude (suino_id, peso, data_entradacio, tipo_tratamento, data_inicio_tratamento, observacoes, criado_em, atualizado_em)
VALUES (1, 50.5, '2024-01-05', 'Vermifugação', '2024-01-05', 'Sem observações', '2024-01-05', '2024-01-05'),
       (2, 60.2, '2024-01-06', 'Vacinação', '2024-01-06', 'Sem observações', '2024-01-06', '2024-01-06');
