CREATE TABLE categoria (
  id_categoria SERIAL NOT NULL,
  nome_categoria text NOT NULL,
  CONSTRAINT categoria_pkey PRIMARY KEY (id_categoria)
);
CREATE TABLE despesas (
  id_despesas SERIAL NOT NULL,
  id_usuario integer NOT NULL,
  id_categoria integer NOT NULL,
  data date NOT NULL,
  valor double precision NOT NULL,
  nome_despesa text NOT NULL,
  CONSTRAINT despesas_pkey PRIMARY KEY (id_despesas),
  FOREIGN KEY (id_usuario) REFERENCES usuario on update cascade on delete cascade,
  FOREIGN KEY (id_categoria) REFERENCES categoria on update cascade on delete cascade
);


CREATE TABLE renda (
  id_renda SERIAL NOT NULL,
  id_usuario integer NOT NULL,
  nome_renda text NOT NULL,
  valor double precision NOT NULL,
  data date NOT NULL,
  tipo_renda boolean,
  CONSTRAINT renda_pkey PRIMARY KEY (id_renda),
  FOREIGN KEY (id_usuario) REFERENCES usuario on update cascade on delete cascade
);

CREATE TABLE usuario (
  id_usuario SERIAL NOT NULL,
  nome text NOT NULL,
  cpf text NOT NULL UNIQUE,
  email text NOT NULL UNIQUE,
  senha text NOT NULL UNIQUE,
  data_nascimento date NOT NULL,
  CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario)
);