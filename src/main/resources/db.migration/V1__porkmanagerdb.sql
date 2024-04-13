-- Tipos ENUM
CREATE TYPE Roles AS ENUM ('ADMIN', 'CRIADOR');
CREATE TYPE SexoSuino AS ENUM ('MACHO', 'FEMEA');
CREATE TYPE TipoSuino AS ENUM ('GESTACAO', 'CRECHE', 'ENGORDA');
CREATE TYPE StatusAlojamento AS ENUM ('ATIVO', 'INATIVO');

-- Tabelas
CREATE TABLE usuario
(
    id       BIGSERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    cpf      VARCHAR(11)  NOT NULL UNIQUE,
    senha    VARCHAR(255) NOT NULL,
    roles    Roles        NOT NULL,
    active   BOOLEAN      NOT NULL
);

CREATE TABLE raca_suino
(
    id            BIGSERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    descricao     TEXT,
    caracteristicas TEXT,
    criado_em     DATE         NOT NULL,
    atualizado_em DATE         NOT NULL
);

CREATE TABLE alojamento
(
    id             BIGSERIAL PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL,
    tipo           TipoSuino    NOT NULL,
    capacidade     INT          NOT NULL,
    status         StatusAlojamento NOT NULL,
    criado_em      DATE         NOT NULL,
    atualizado_em  DATE         NOT NULL
);

CREATE TABLE suino
(
    id              BIGSERIAL PRIMARY KEY,
    raca_id         BIGINT       NOT NULL,
    identificacao_orelha VARCHAR(50) NOT NULL,
    tipo_suino      TipoSuino    NOT NULL,
    sexo            SexoSuino    NOT NULL,
    data_nasc       DATE         NOT NULL,
    observacoes     TEXT,
    alojamento_id   BIGINT       NOT NULL,
    usuario_id      BIGINT       NOT NULL,
    criado_em       DATE         NOT NULL,
    atualizado_em   DATE         NOT NULL,
    FOREIGN KEY (raca_id) REFERENCES raca_suino (id),
    FOREIGN KEY (alojamento_id) REFERENCES alojamento (id),
    FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);

CREATE TABLE saude_suino
(
    id                    BIGSERIAL PRIMARY KEY,
    suino_id              BIGINT       NOT NULL,
    peso                  DECIMAL(10, 2) NOT NULL,
    data_entradacio       DATE,
    tipo_tratamento       VARCHAR(100)   NOT NULL,
    data_inicio_tratamento DATE           NOT NULL,
    observacoes           TEXT,
    criado_em             DATE           NOT NULL,
    atualizado_em         DATE           NOT NULL,
    FOREIGN KEY (suino_id) REFERENCES suino (id)
);