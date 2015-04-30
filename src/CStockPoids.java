import java.util.Iterator;
import java.util.LinkedList;


public class CStockPoids extends CStock 
{
	
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public static int bd_get_stock_actuel(int p_id_plante, long p_date_peremption) 
	{
		CInventairePoids                l_dernier_inventaire = new CInventairePoids();
		LinkedList<CMouvementPoids>     l_liste_mouvements;
		CDate                           l_aujourdhui = new CDate(); 
		CMouvementPoids                 l_mouvement;
		int                             l_somme;
		l_aujourdhui.init_date_du_jour();
		String                          l_detail="";
		
		// On lit le dernier inventaire
		l_dernier_inventaire.bd_select_date(p_id_plante, l_aujourdhui, p_date_peremption);
		
		if (!l_dernier_inventaire.est_vide())
		{
	        l_detail = "Inventaire du "+l_dernier_inventaire.get_inv_date().get_bigint()+" releve= "+l_dernier_inventaire.get_inv_poids_grammes()+"grammes\n";
			// Si l'inventaire existe en BDD 
	
			l_somme = l_dernier_inventaire.get_inv_poids_grammes();
			
			// On lit les mouvements dans le stock à partir de la date de l'inventaire jusqu'à aujourd'hui
			l_liste_mouvements = CMouvementPoids.bd_select_date(p_id_plante, p_date_peremption, l_dernier_inventaire.get_inv_date(), l_aujourdhui);
			
			// On calcule le cumul des mouvements
			Iterator<CMouvementPoids> it = l_liste_mouvements.iterator();
			
			while (it.hasNext())
			{
				l_mouvement = it.next();
			
				if ( l_mouvement.get_mvt_sens().equals(CSensMouvement.MOUVEMENT_AJOUT_STOCK() ))
				{
					l_somme += l_mouvement.get_mvt_poids_grammes();
					l_detail += "Entrée en stock le "+l_mouvement.get_mvt_date().get_bigint()+" de "+l_mouvement.get_mvt_poids_grammes()+" grammes\n";
				}
				else
				{
					if ( l_mouvement.get_mvt_sens().equals(CSensMouvement.MOUVEMENT_RETRAIT_STOCK()))
					{
						l_somme -= l_mouvement.get_mvt_poids_grammes();
						l_detail += "Sortie du stock le "+l_mouvement.get_mvt_date().get_bigint()+" de "+l_mouvement.get_mvt_poids_grammes()+" grammes\n";
					}
					else
					{
						l_detail +="Mouvement non reconnu\n";
						l_mouvement.affiche();
					}
				}
			}
			
			// Retour
			return l_somme;
		}
		else
		{
	        // Pas d'inventaire en BDD
			return CStock.STOCK_INDETERMINE;		// Stock indéterminé
		}
	}

}
