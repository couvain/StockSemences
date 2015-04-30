rem localhost
rem port 5432
rem base amap

echo **********************************************
echo * Creation de la base "amap" sur le serveur" *
echo **********************************************
psql -p 5432 -U postgres -f "./create_database.sql"
echo ************************
echo * Structure de la base *
echo ************************
psql -p 5432 -d amap -U postgres -f "./base_amap.sql"
echo ***********************************
echo * Données élémentaires de la base *
echo ***********************************
psql -p 5432 -d amap -U postgres -f "./data_amap_famille_plante.sql"
psql -p 5432 -d amap -U postgres -f "./data_amap_semencier.sql"
pause
