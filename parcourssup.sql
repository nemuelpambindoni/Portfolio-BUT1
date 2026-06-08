-- ============================================================
--  parcourssup.sql  –  Import, normalisation et requêtage
--  SAé S2.04 – R2.06 BDD – BUT Info N2
--  Script idempotent : peut être lancé autant de fois que voulu
-- ============================================================

DROP TABLE IF EXISTS academie       CASCADE;
DROP TABLE IF EXISTS departement      CASCADE;
DROP TABLE IF EXISTS etablissement        CASCADE;
DROP TABLE IF EXISTS formation   CASCADE;
DROP TABLE IF EXISTS statistique    CASCADE;
DROP TABLE IF EXISTS import           CASCADE;

CREATE TABLE import (
    n1 INT, n2 TEXT, n3 CHAR(8), n4 TEXT, n5 TEXT, n6 TEXT, n7 TEXT, n8 TEXT, n9 TEXT, n10 TEXT, n11 TEXT, n12 TEXT, n13 TEXT, n14 TEXT, n15 TEXT, n16 TEXT, n17 TEXT, n18 INT, n19 INT,
n20 INT, n21 INT, n22 INT, n23 INT, n24 INT, n25 INT, n26 INT, n27 INT, n28 INT, n29 INT, n30 INT, n31 INT, n32 INT, n33 INT, n34 INT, n35 INT, n36 INT, n37 INT,
n38 INT, n39 INT, n40 INT, n41 INT, n42 INT, n43 INT, n44 INT, n45 INT, n46 INT, n47 INT, n48 INT, n49 INT, n50 INT, n51 INT, n52 INT, n53 INT, n54 INT, n55 INT,
n56 INT, n57 INT, n58 INT, n59 INT, n60 INT, n61 INT, n62 INT, n63 INT, n64 INT, n65 INT, n66 INT, n67 INT, n68 INT, n69 INT, n70 INT, n71 INT, n72 INT, n73 INT, n74 FLOAT, n75 FLOAT,
n76 FLOAT, n77 FLOAT, n78 FLOAT, n79 FLOAT, n80 FLOAT, n81 FLOAT, n82 FLOAT, n83 FLOAT, n84 FLOAT, n85 FLOAT, n86 FLOAT, n87 FLOAT, n88 FLOAT, n89 FLOAT, n90 FLOAT, n91 FLOAT, n92 FLOAT, n93 FLOAT, n94 FLOAT, n95 FLOAT,
n96 FLOAT, n97 FLOAT, n98 FLOAT, n99 FLOAT, n100 FLOAT, n101 FLOAT, n102 TEXT, n103 INT, n104 TEXT, n105 INT, n106 TEXT, n107 INT, n108 TEXT, n109 TEXT, n110 INT, n111 TEXT, n112 TEXT, n113 INT,
n114 INT, n115 INT, n116 INT, n117 TEXT, n118 TEXT);

\copy import FROM '/home/infoetu/nemuel-manoah.pambindoni.etu/SAE204/fr-esr-parcoursup.csv' WITH (FORMAT csv, DELIMITER ';', HEADER true, ENCODING 'UTF8', NULL '');

CREATE TABLE academie (
    nom_academie TEXT PRIMARY KEY,
    nom_region TEXT
);

INSERT INTO academie (nom_academie, nom_region)
SELECT DISTINCT n8, n7
FROM import;

CREATE TABLE departement (
    code_dept TEXT PRIMARY KEY,
    nom_dept TEXT,
    nom_academie TEXT,
    FOREIGN KEY (nom_academie) REFERENCES academie(nom_academie)
);

INSERT INTO departement (code_dept, nom_dept, nom_academie)
SELECT DISTINCT n5, n6, n8
FROM import;

CREATE TABLE etablissement (
    code_uai CHAR(8) PRIMARY KEY,
    statut TEXT,
    nom TEXT,
    commune TEXT,
    code_dept TEXT,
    FOREIGN KEY (code_dept) REFERENCES departement(code_dept)
);

INSERT INTO etablissement (code_uai, statut, nom, commune, code_dept)
SELECT DISTINCT n3, n2, n4, n9, n5
FROM import;

CREATE TABLE formation (
    cod_aff_form INT PRIMARY KEY,  -- n110
    session INT,     -- n1
    libelle TEXT,     -- n10
    selectivite TEXT,               -- n11
    filiere TEXT,               -- n12
    capacite INT,                       -- n18
    code_uai CHAR(8),
    FOREIGN KEY (code_uai) REFERENCES etablissement(code_uai)
);

INSERT INTO formation (cod_aff_form, session, libelle, selectivite, filiere, capacite, code_uai)
SELECT DISTINCT n110, n1, n10, n11, n12, n18, n3
FROM import;

CREATE TABLE statistique  (
    id_stat SERIAL PRIMARY KEY,
    nb_candidat_total INT,
    nb_admis INT,
    taux_acces INT,
    taux_admission FLOAT,
    cod_aff_form INT,
    FOREIGN KEY (cod_aff_form) REFERENCES formation(cod_aff_form)
);

INSERT INTO statistique (nb_candidat_total, nb_admis, taux_acces, taux_admission, cod_aff_form)
SELECT n19, n47, n113, n74, n110    
FROM import;



\copy (
    SELECT a.nom_region, a.nom_academie, e.nom, e.commune, 
    f.libelle, f.filiere, f.selectivite,
    s.nb_candidat_total,
    s.nb_admis,
    s.taux_acces,
    s.taux_admission,
    i.n20 AS nb_candidates,
    i.n23 AS nb_nebac_gen,
    i.n25 AS nb_nebac_tech,
    i.n27 AS nb_nebac_pro,
    i.n48 AS nb_admises,
    i.n55 AS nb_admis_boursiers,
    i.n57 AS nb_admis_gen,
    i.n58 AS nb_admis_tech,
    i.n59 AS nb_admis_pro,
    i.n77 AS pct_admis_filles,
    i.n81 AS pct_boursiers,
    i.n89 AS pct_gen,
    i.n91 AS pct_tech
    FROM statistique s
    JOIN import i ON s.cod_aff_form = i.n110
    JOIN formation f ON s.cod_aff_form = f.cod_aff_form
    JOIN etablissement e ON f.code_uai = e.code_uai
    JOIN departement d ON e.code_dept = d.code_dept
    JOIN academie a ON d.nom_academie = a.nom_academie
    WHERE a.nom_region = 'Grand-Est'
) TO '~/SAE204/grand_est.csv' WITH (FORMAT csv, DELIMITER ';', HEADER true);