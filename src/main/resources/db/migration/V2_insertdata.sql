-- Inserts for 'usuario' table
INSERT INTO usuario (id, nome, cpf, senha, roles, active)
VALUES (1, 'Mark', '13688811062', '$2a$12$5EnUPOUtkOSrDiOvLM.GROe6xIpBrs9l5NuscONyWGcY36WS0421a', 'ADMIN', true),
       (2, 'Gordon', '34856757091', '$2a$12$5EnUPOUtkOSrDiOvLM.GROe6xIpBrs9l5NuscONyWGcY36WS0421a', 'CRIADOR', true),
       (3, 'Zac', '23512586066', '$2a$12$5EnUPOUtkOSrDiOvLM.GROe6xIpBrs9l5NuscONyWGcY36WS0421a', 'CRIADOR', true),
       (4, 'Jane', '07020974031', '$2a$12$5EnUPOUtkOSrDiOvLM.GROe6xIpBrs9l5NuscONyWGcY36WS0421a', 'CRIADOR', true);
SELECT setval('usuario_id_seq', COALESCE((SELECT MAX(id)+1 FROM usuario), 1), false);

-- Inserts for 'raca_suino' table
INSERT INTO raca (id, nome, descricao, caracteristicas, criado_em, atualizado_em)
VALUES (1, 'Landrace', 'White, lop-eared', 'Good meat production', '2023-01-01', '2023-01-01'),
       (2, 'Duroc', 'Reddish-brown', 'Fast growth rate', '2023-01-02', '2023-01-02'),
       (3, 'Hampshire', 'Black with white belt', 'High meat quality', '2023-01-03', '2023-01-03'),
       (4, 'Yorkshire', 'Large, white with erect ears', 'Large size', '2023-01-04', '2023-01-04');
SELECT setval('raca_id_seq', COALESCE((SELECT MAX(id)+1 FROM raca), 1), false);

-- Inserts for 'alojamento' table
INSERT INTO alojamento (id, nome, tipo, capacidade, status, criado_em, atualizado_em)
VALUES (1, 'Alojamento 1', 'GESTACAO', 50, 'ATIVO', '2023-01-05', '2023-01-05'),
       (2, 'Alojamento 2', 'CRECHE', 100, 'ATIVO', '2023-01-06', '2023-01-06'),
       (3, 'Alojamento 3', 'ENGORDA', 200, 'ATIVO', '2023-01-07', '2023-01-07');
SELECT setval('alojamento_id_seq', COALESCE((SELECT MAX(id)+1 FROM alojamento), 1), false);

-- Inserts for 'suino' table
INSERT INTO suino (id, raca_id, identificacao_orelha, tipo_suino, sexo, data_nasc, observacoes, alojamento_id, usuario_id, criado_em, atualizado_em)
VALUES (1, 1, 'E1A2B3', 'GESTACAO', 'FEMEA', '2020-05-15', 'White color', 1, 1, '2023-01-05', '2023-01-05'),
       (2, 2, 'F4C5D6', 'CRECHE', 'MACHO', '2021-02-20', 'Reddish-brown color', 2, 2, '2023-01-06', '2023-01-06'),
       (3, 3, 'G7E8F9', 'ENGORDA', 'FEMEA', '2022-09-10', 'Black with white belt', 3, 3, '2023-01-07', '2023-01-07'),
       (4, 4, 'H0I1J2', 'ENGORDA', 'MACHO', '2023-04-01', 'Large, white', 1, 4, '2023-01-08', '2023-01-08');
SELECT setval('suino_id_seq', COALESCE((SELECT MAX(id)+1 FROM suino), 1), false);

-- Inserts for 'saude_suino' table
INSERT INTO saude (id, suino_id, peso, data_entradacio, tipo_tratamento, data_inicio_tratamento, observacoes, criado_em, atualizado_em)
VALUES (1, 1, 50.0, '2023-01-09', 'Vaccination', '2023-01-10', 'Regular checkup', '2023-03-05', '2023-03-05'),
       (2, 2, 70.0, '2023-01-11', 'Deworming', '2023-01-12', 'Monitoring weight gain', '2023-01-05', '2023-01-05'),
       (3, 3, 45.0, '2023-01-13', 'Antibiotic', '2023-01-14', 'Recovering from illness', '2023-01-07', '2023-01-07'),
       (4, 4, 30.0, '2023-01-15', 'Vaccination', '2023-01-16', 'Regular checkup', '2023-01-08', '2023-01-08');
SELECT setval('saude_id_seq', COALESCE((SELECT MAX(id)+1 FROM saude), 1), false);
