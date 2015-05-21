-- On efface toutes les donnees de la table semencier
delete from semencier;

-- On remet le compteur de séquence à redémarrer à 1
alter SEQUENCE semencier_sec_id_seq RESTART WITH 1;

-- On insere les donnees de semencier
insert into semencier (sec_nom) values 
('Essem Bio'),
('Voltz'),
('Agrosemens'),
('Germinance'),
('La ferme de Ste Marthe'),
('Pépimat');
