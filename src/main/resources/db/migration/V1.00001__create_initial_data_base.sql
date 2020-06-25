CREATE TABLE person
(
  id              UUID PRIMARY KEY NOT NULL,
  name            TEXT             NOT NULL,
  cpf             TEXT             NOT NULL,
  born_date       DATE             NOT NULL,
  address         TEXT             NOT NULL,
  created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  updated_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  deleted_at      TIMESTAMP WITH TIME ZONE,
  enabled         BOOLEAN not null default true
);

CREATE UNIQUE INDEX person_id_idx
  ON person
  USING btree
  (id);

CREATE INDEX person_cpf_idx
   ON person
   USING btree
   (cpf);

CREATE INDEX person_name_idx
   ON person
   USING btree
   (name);