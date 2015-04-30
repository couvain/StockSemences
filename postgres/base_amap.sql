SET client_encoding = 'UTF8';

-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.7.0
-- PostgreSQL version: 9.3
-- Project Site: pgmodeler.com.br
-- Model Author: ---

SET check_function_bodies = false;
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: nouvelle_base | type: DATABASE --
-- -- DROP DATABASE nouvelle_base;
-- CREATE DATABASE nouvelle_base
-- ;
-- -- ddl-end --
-- 

-- object: public.semencier | type: TABLE --
-- DROP TABLE public.semencier;
CREATE TABLE public.semencier(
	sec_id serial NOT NULL,
	sec_nom varchar(50),
	CONSTRAINT pk_semencier PRIMARY KEY (sec_id)

);
-- ddl-end --
COMMENT ON TABLE public.semencier IS 'liste de tous les semenciers fournisseurs de l''amap';
COMMENT ON COLUMN public.semencier.sec_id IS 'identifiant du semencier - clé';
COMMENT ON COLUMN public.semencier.sec_nom IS 'nom du semencier';
-- ddl-end --

-- object: public.famille_plante | type: TABLE --
-- DROP TABLE public.famille_plante;
CREATE TABLE public.famille_plante(
	fap_id serial NOT NULL,
	fap_nom varchar(50),
	CONSTRAINT pk_famille_plante PRIMARY KEY (fap_id)

);
-- ddl-end --
COMMENT ON TABLE public.famille_plante IS 'familles de plantes';
-- ddl-end --

-- object: public.plante | type: TABLE --
-- DROP TABLE public.plante;
CREATE TABLE public.plante(
	pla_id serial NOT NULL,
	fap_id_famille_plante integer NOT NULL,
	pla_semis_ser_mar boolean,
	pla_semis_ser_avr boolean,
	pla_semis_ser_mai boolean,
	pla_semis_ser_jun boolean,
	pla_semis_ser_jul boolean,
	pla_semis_ser_aou boolean,
	pla_semis_ser_sep boolean,
	pla_semis_ser_oct boolean,
	pla_semis_ser_nov boolean,
	pla_semis_ser_dec boolean,
	pla_notes text,
	pla_nom varchar(50),
	pla_semis_ext_jan boolean,
	pla_semis_ext_fev boolean,
	pla_semis_ext_mar boolean,
	pla_semis_ext_avr boolean,
	pla_semis_ext_mai boolean,
	pla_semis_ext_jun boolean,
	pla_semis_ext_jul boolean,
	pla_semis_ext_aou boolean,
	pla_semis_ext_sep boolean,
	pla_semis_ext_oct boolean,
	pla_semis_ext_nov boolean,
	pla_semis_ext_dec boolean,
	pla_semis_ser_jan boolean,
	pla_semis_ser_fev boolean,
	CONSTRAINT pk_plante PRIMARY KEY (pla_id)

);
-- ddl-end --
COMMENT ON TABLE public.plante IS 'liste des plantes / semences';
COMMENT ON COLUMN public.plante.pla_id IS 'clé de la table plante';
COMMENT ON COLUMN public.plante.pla_semis_ser_mar IS 'semis de la plante sous serre en mars possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_avr IS 'semis de la plante sous serre en avril possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_mai IS 'semis de la plante sous serre en mai possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_jun IS 'semis de la plante sous serre en juin possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_jul IS 'semis de la plante sous serre en juillet possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_aou IS 'semis de la plante sous serre en août possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_sep IS 'semis de la plante sous serre en septembre possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_oct IS 'semis de la plante sous serre en octobre possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_nov IS 'semis de la plante sous serre en novembre possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_dec IS 'semis de la plante sous serre en decembre possible';
COMMENT ON COLUMN public.plante.pla_notes IS 'notes sur la plante';
COMMENT ON COLUMN public.plante.pla_nom IS 'nom de la plante';
COMMENT ON COLUMN public.plante.pla_semis_ext_jan IS 'semis de la plante en extérieur en janvier possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_fev IS 'semis de la plante en extérieur en février possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_mar IS 'semis de la plante en extérieur en mars possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_avr IS 'semis de la plante en extérieur en avril possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_mai IS 'semis de la plante en extérieur en mai possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_jun IS 'semis de la plante en extérieur en juin possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_jul IS 'semis de la plante en extérieur en juillet possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_aou IS 'semis de la plante en extérieur en août possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_sep IS 'semis de la plante en extérieur en septembre possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_oct IS 'semis de la plante en extérieur en octobre possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_nov IS 'semis de la plante en extérieur en novembre possible';
COMMENT ON COLUMN public.plante.pla_semis_ext_dec IS 'semis de la plante en extérieur en decembre possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_jan IS 'semis de la plante sous serre en janvier possible';
COMMENT ON COLUMN public.plante.pla_semis_ser_fev IS 'semis de la plante sous serre en février possible';
-- ddl-end --

-- object: famille_plante_fk | type: CONSTRAINT --
-- ALTER TABLE public.plante DROP CONSTRAINT famille_plante_fk;
ALTER TABLE public.plante ADD CONSTRAINT famille_plante_fk FOREIGN KEY (fap_id_famille_plante)
REFERENCES public.famille_plante (fap_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


-- object: public.lot_plante | type: TABLE --
-- DROP TABLE public.lot_plante;
CREATE TABLE public.lot_plante(
	lot_id serial,
	lot_nom varchar(50),
	pla_id_plante integer NOT NULL,
	sec_id_semencier integer NOT NULL,
	lot_nb_etoiles smallint,
	lot_nb_plants int4,
	lot_poids_g int4,
	lot_nb_graines int4,
	lot_avis text,
	CONSTRAINT pk_lot_plante PRIMARY KEY (lot_id)

);
-- ddl-end --
COMMENT ON COLUMN public.lot_plante.lot_id IS 'identifiant de lot';
COMMENT ON COLUMN public.lot_plante.pla_id_plante IS 'clé de la table plante';
COMMENT ON COLUMN public.lot_plante.sec_id_semencier IS 'identifiant du semencier - clé';
COMMENT ON COLUMN public.lot_plante.lot_nb_plants IS 'nombre de plants du lot';
COMMENT ON COLUMN public.lot_plante.lot_avis IS 'avis sur le lot';
-- ddl-end --

-- object: plante_fk | type: CONSTRAINT --
-- ALTER TABLE public.lot_plante DROP CONSTRAINT plante_fk;
ALTER TABLE public.lot_plante ADD CONSTRAINT plante_fk FOREIGN KEY (pla_id_plante)
REFERENCES public.plante (pla_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


-- object: semencier_fk | type: CONSTRAINT --
-- ALTER TABLE public.lot_plante DROP CONSTRAINT semencier_fk;
ALTER TABLE public.lot_plante ADD CONSTRAINT semencier_fk FOREIGN KEY (sec_id_semencier)
REFERENCES public.semencier (sec_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


-- object: public.inventaire | type: TABLE --
-- DROP TABLE public.inventaire;
CREATE TABLE public.inventaire(
	inv_id serial,
	inv_date bigint,
	lot_id_lot_plante integer,
	inv_nb_lot int4,
	inv_dperemption bigint,
	inv_nb_plants int4,
	inv_poids_g int4,
	inv_nb_graines int4,
	pla_id_plante integer NOT NULL,
	CONSTRAINT pk_inventaire PRIMARY KEY (inv_id)

);
-- ddl-end --
COMMENT ON COLUMN public.inventaire.lot_id_lot_plante IS 'identifiant de lot';
COMMENT ON COLUMN public.inventaire.inv_dperemption IS 'date de péremption';
COMMENT ON COLUMN public.inventaire.pla_id_plante IS 'clé de la table plante';
-- ddl-end --

-- object: lot_plante_fk | type: CONSTRAINT --
-- ALTER TABLE public.inventaire DROP CONSTRAINT lot_plante_fk;
ALTER TABLE public.inventaire ADD CONSTRAINT lot_plante_fk FOREIGN KEY (lot_id_lot_plante)
REFERENCES public.lot_plante (lot_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: plante_fk | type: CONSTRAINT --
-- ALTER TABLE public.inventaire DROP CONSTRAINT plante_fk;
ALTER TABLE public.inventaire ADD CONSTRAINT plante_fk FOREIGN KEY (pla_id_plante)
REFERENCES public.plante (pla_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


-- object: public.mouvement | type: TABLE --
-- DROP TABLE public.mouvement;
CREATE TABLE public.mouvement(
	mvt_id serial,
	mvt_date bigint,
	mvt_nb_graines int4,
	mvt_nb_lot int4,
	mvt_dperemption bigint,
	mvt_nb_plants int4,
	mvt_poids_g int4,
	pla_id_plante integer NOT NULL,
	lot_id_lot_plante integer,
	mvt_sens char NOT NULL,
	CONSTRAINT pk_mouvement PRIMARY KEY (mvt_id)

);
-- ddl-end --
COMMENT ON COLUMN public.mouvement.mvt_dperemption IS 'date de péremption';
COMMENT ON COLUMN public.mouvement.pla_id_plante IS 'clé de la table plante';
COMMENT ON COLUMN public.mouvement.lot_id_lot_plante IS 'identifiant de lot';
-- ddl-end --

-- object: plante_fk | type: CONSTRAINT --
-- ALTER TABLE public.mouvement DROP CONSTRAINT plante_fk;
ALTER TABLE public.mouvement ADD CONSTRAINT plante_fk FOREIGN KEY (pla_id_plante)
REFERENCES public.plante (pla_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


-- object: lot_plante_fk | type: CONSTRAINT --
-- ALTER TABLE public.mouvement DROP CONSTRAINT lot_plante_fk;
ALTER TABLE public.mouvement ADD CONSTRAINT lot_plante_fk FOREIGN KEY (lot_id_lot_plante)
REFERENCES public.lot_plante (lot_id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: public.parametres | type: TABLE --
-- DROP TABLE public.parametres;
CREATE TABLE public.parametres(
	par_id serial,
	par_pla_id smallint,
	par_date_du_jour bigint,
	CONSTRAINT par_id_pk PRIMARY KEY (par_id)

);
-- ddl-end --
COMMENT ON COLUMN public.parametres.par_pla_id IS 'id de plante';
-- ddl-end --


