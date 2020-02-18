
CREATE TABLE siac.siac_t_xbrl_ente (
  xbrl_ente_id SERIAL, 
  xbrl_ente_bdap_codice VARCHAR(200) NOT NULL,
  validita_inizio TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now() NOT NULL,
  validita_fine TIMESTAMP WITHOUT TIME ZONE,
  ente_proprietario_id INTEGER NOT NULL,
  data_creazione TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  data_modifica TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  data_cancellazione TIMESTAMP WITHOUT TIME ZONE,
  login_operazione VARCHAR(200) NOT NULL,
  CONSTRAINT siac_t_ente_proprietario_siac_t_xbrl_report FOREIGN KEY (ente_proprietario_id)
    REFERENCES siac.siac_t_ente_proprietario(ente_proprietario_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    NOT DEFERRABLE
)
WITH (oids = false);


CREATE TABLE siac.siac_t_xbrl_mapping_fatti (
  xbrl_rep_codice VARCHAR(200) NOT NULL,
  xbrl_rep_variabile VARCHAR(500) NOT NULL,
  xbrl_rep_fatto VARCHAR(500) NOT NULL,
  xbrl_rep_tupla_nome VARCHAR(50),
  xbrl_rep_tupla_group_key VARCHAR(500),
  xbrl_rep_periodo_code VARCHAR(200) NOT NULL,
  xbrl_rep_unit_code VARCHAR(50),
  xbrl_rep_decimali VARCHAR(50) DEFAULT 0,
  validita_inizio TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now() NOT NULL,
  validita_fine TIMESTAMP WITHOUT TIME ZONE,
  ente_proprietario_id INTEGER NOT NULL,
  data_creazione TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  data_modifica TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  data_cancellazione TIMESTAMP WITHOUT TIME ZONE,
  login_operazione VARCHAR(200) NOT NULL,
  CONSTRAINT pk_siac_t_xbrl_mapping_fatti PRIMARY KEY(ente_proprietario_id, xbrl_rep_codice, xbrl_rep_variabile),
  CONSTRAINT siac_t_ente_proprietario_siac_t_xbrl_mapping_fatti FOREIGN KEY (ente_proprietario_id)
    REFERENCES siac.siac_t_ente_proprietario(ente_proprietario_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    NOT DEFERRABLE
)
WITH (oids = false);

COMMENT ON COLUMN siac_t_xbrl_mapping_fatti.xbrl_rep_variabile IS 'Nome della variabile nel report';
COMMENT ON COLUMN siac_t_xbrl_mapping_fatti.xbrl_rep_fatto IS 'Fatto corrispondente nella tassonomia';
COMMENT ON COLUMN siac_t_xbrl_mapping_fatti.xbrl_rep_tupla_nome IS 'Nome del tag della tupla';
COMMENT ON COLUMN siac_t_xbrl_mapping_fatti.xbrl_rep_tupla_group_key IS 'Chiave di raggruppamento della tupla';
COMMENT ON COLUMN siac_t_xbrl_mapping_fatti.xbrl_rep_periodo_code IS 'Codice del periodo';
COMMENT ON COLUMN siac_t_xbrl_mapping_fatti.xbrl_rep_unit_code IS 'Riferimento all''unita'' di misura';
COMMENT ON COLUMN siac_t_xbrl_mapping_fatti.xbrl_rep_decimali IS 'Numero di decimali per cui il valore e'' accurato';




-- drop table siac.siac_t_xbrl_report;
CREATE TABLE siac.siac_t_xbrl_report (
  xbrl_rep_codice VARCHAR(200) NOT NULL,
  xbrl_rep_fase_code VARCHAR(4) NOT NULL,
  xbrl_rep_tipologia_code VARCHAR(3) NOT NULL,
  xbrl_rep_xsd_tassonomia VARCHAR(500) NOT NULL,
  validita_inizio TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now() NOT NULL,
  validita_fine TIMESTAMP WITHOUT TIME ZONE,
  ente_proprietario_id INTEGER NOT NULL,
  data_creazione TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  data_modifica TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  data_cancellazione TIMESTAMP WITHOUT TIME ZONE,
  login_operazione VARCHAR(200) NOT NULL,
  CONSTRAINT pk_siac_t_xbrl_report PRIMARY KEY(ente_proprietario_id, xbrl_rep_codice),
  CONSTRAINT siac_t_xbrl_report_chk CHECK (xbrl_rep_tipologia_code IN ('SDB', 'DCA', 'IND', 'BUD', 'BIL')),
  CONSTRAINT siac_t_ente_proprietario_siac_t_xbrl_report FOREIGN KEY (ente_proprietario_id)
    REFERENCES siac.siac_t_ente_proprietario(ente_proprietario_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    NOT DEFERRABLE
)
WITH (oids = false);


