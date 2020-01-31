CREATE TABLE sss_backoffice_db.tr_eleve_classe_niveaux
(
  tr_eleve_classe_niveau_id numeric(4,0) NOT NULL DEFAULT nextval('sss_backoffice_db.seq_eleve_classes_niveau'::regclass),
  libelle character varying(200),
  CONSTRAINT pk_eleve_classe_niveaux PRIMARY KEY (tr_eleve_classe_niveau_id)
);
ALTER TABLE sss_backoffice_db.tr_eleve_classes ADD COLUMN tr_eleve_classe_niveau_id numeric(4,0);
ALTER TABLE sss_backoffice_db.tr_eleve_classes ALTER COLUMN tr_eleve_classe_niveau_id SET NOT NULL;
ALTER TABLE sss_backoffice_db.tr_eleve_classes
  ADD CONSTRAINT fk_eleve_classes_niveau FOREIGN KEY (tr_eleve_classe_niveau_id)
      REFERENCES sss_backoffice_db.tr_eleve_classe_niveaux (tr_eleve_classe_niveau_id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE sss_backoffice_db.tr_eleve_saisons ADD COLUMN tr_eleve_panier boolean;
ALTER TABLE sss_backoffice_db.tr_eleve_saisons ADD COLUMN email character varying(260);

DELETE FROM sss_backoffice_db.tr_eleve_demande_cartes;
DELETE FROM sss_backoffice_db.tr_eleve_saisons;
DELETE FROM sss_backoffice_db.tr_eleves;
DELETE FROM sss_backoffice_db.tr_eleve_classes;
DELETE FROM sss_backoffice_db.tr_eleve_classe_niveaux;

INSERT INTO sss_backoffice_db.tr_eleve_classe_niveaux(libelle) SELECT DISTINCT classe  FROM sss_backoffice_db.tr_eleve_demande ;
INSERT INTO sss_backoffice_db.tr_eleve_classes(nom, tr_eleve_classe_niveau_id)
SELECT DISTINCT d.niveau,(SELECT n.tr_eleve_classe_niveau_id  FROM sss_backoffice_db.tr_eleve_classe_niveaux n where n.libelle=d.classe limit 1)  
    FROM sss_backoffice_db.tr_eleve_demande d;
INSERT INTO sss_backoffice_db.tr_eleves(tr_code_permanent, prenom, nom, date_naisance)
SELECT code_permanent, prenom,nom, to_date(date_naissance,'DD/MM/YYYY') FROM sss_backoffice_db.tr_eleve_demande;

INSERT INTO sss_backoffice_db.tr_eleve_saisons(
            tr_eleve_saison_id, tr_code_permanent, nom_responsable_1, prenom_responsable_1, tel_responsable_1, 
            nom_responsable_2, prenom_responsable_2, tel_responsable_2, service_cantine, 
            service_garde,service_panier, email, tr_eleve_classe_id)
        SELECT   2019, d.code_permanent,d.nom_responsable_1, d.prenom_responsable_1,d.tel_responsable_1,
             d.nom_responsable_2, d.prenom_responsable_2, d.tel_responsable_2, 
              (CASE WHEN d.service_cantine='Oui' THEN true ELSE false END), (CASE WHEN d.service_garde='Oui' THEN true ELSE false END), (CASE WHEN d.service_panier='Oui' THEN true ELSE false END), d.email, ( SELECT c.tr_eleve_classe_id 
  FROM sss_backoffice_db.tr_eleve_classes c 
  left join sss_backoffice_db.tr_eleve_classe_niveaux n on c.tr_eleve_classe_niveau_id=n.tr_eleve_classe_niveau_id
  where c.nom=d.niveau and n.libelle=d.classe limit 1)
  FROM sss_backoffice_db.tr_eleve_demande d;
  