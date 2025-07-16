CREATE TABLE categoria (
  id_categoria integer NOT NULL,
  nome_categoria text NOT NULL,
  CONSTRAINT categoria_pkey PRIMARY KEY (id_categoria)
);
CREATE TABLE categoriadespesas (
  id_corretor Integer NOT NULL,
  id_despesas integer NOT NULL,
  id_categoria integer NOT NULL,
  CONSTRAINT categoriadespesas_pkey PRIMARY KEY (id_corretor),
  CONSTRAINT categoriadespesas_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
  CONSTRAINT categoriadespesas_id_despesas_fkey FOREIGN KEY (id_despesas) REFERENCES despesas(id_despesas)
);
CREATE TABLE despesas (
  id_despesas integer NOT NULL,
  id_usuario integer NOT NULL,
  id_categoria integer NOT NULL,
  data_inicio date NOT NULL,
  data_fim date NOT NULL,
  CONSTRAINT despesas_pkey PRIMARY KEY (id_despesas),
  CONSTRAINT despesas_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
  CONSTRAINT despesas_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);
CREATE TABLE relatorio (
  id_relatorio integer NOT NULL,
  id_usuario integer NOT NULL,
  data date NOT NULL,
  horario text NOT NULL,
  CONSTRAINT relatorio_pkey PRIMARY KEY (id_relatorio),
  CONSTRAINT relatorio_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE renda (
  id_renda integer NOT NULL,
  id_usuario integer NOT NULL,
  nome_renda text NOT NULL,
  valor double precision NOT NULL,
  data date NOT NULL,
  tipo_renda boolean,
  CONSTRAINT renda_pkey PRIMARY KEY (id_renda),
  CONSTRAINT renda_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE rendarelatorio (
  id_rendarelatorio integer,
  id_renda integer NOT NULL,
  id_relatorio integer NOT NULL,
  CONSTRAINT rendarelatorio_pkey PRIMARY KEY (id_rendarelatorio),
  CONSTRAINT rendarelatorio_id_renda_fkey FOREIGN KEY (id_renda) REFERENCES renda(id_renda),
  CONSTRAINT rendarelatorio_id_relatorio_fkey FOREIGN KEY (id_relatorio) REFERENCES relatorio(id_relatorio)
);
CREATE TABLE usuario (
  id_usuario integer NOT NULL,
  nome text NOT NULL,
  cpf text NOT NULL UNIQUE,
  email text NOT NULL UNIQUE,
  senha text NOT NULL UNIQUE,
  data_nascimento date NOT NULL,
  CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario)
);