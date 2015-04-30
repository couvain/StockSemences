import java.util.Iterator;
import java.util.LinkedList;


public class CStockLot extends CStock
{
	
	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	// Constructeur
	public CStockLot()
	{
		super();
		//m_dernier_inventaire = new CInventaireLot();
	}
	

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public static int bd_get_stock_actuel(int p_id_plante, int p_lot_id, long p_date_peremption) 
	{
		CInventaireLot                  l_dernier_inventaire = new CInventaireLot();
		LinkedList<CMouvementLot>       l_liste_mouvements;
		CDate                           l_aujourdhui = new CDate();  // Initialisé par défaut à la date du jour
		CMouvementLot                   l_mouvement;
		int                             l_somme;
		
		// On lit le dernier inventaire
		l_dernier_inventaire.bd_select_date(p_id_plante, p_lot_id, l_aujourdhui, p_date_peremption);
		
		if (!l_dernier_inventaire.est_vide())
		{
			// Si l'inventaire existe en BDD 
	
			l_somme = l_dernier_inventaire.get_inv_nb_lot();
			
			// On lit les mouvements dans le stock à partir de la date de l'inventaire jusqu'à aujourd'hui
			l_liste_mouvements = CMouvementLot.bd_select_date(p_id_plante, p_lot_id, p_date_peremption, l_dernier_inventaire.get_inv_date(), l_aujourdhui);
			
			// On calcule le cumul des mouvements
			Iterator<CMouvementLot> it = l_liste_mouvements.iterator();
			while (it.hasNext())
			{
				l_mouvement = it.next();
				if (l_mouvement.get_mvt_sens() == CSensMouvement.MOUVEMENT_AJOUT_STOCK() )
				{
					l_somme += l_mouvement.get_mvt_nb_lot();
				}
				else if (l_mouvement.get_mvt_sens() == CSensMouvement.MOUVEMENT_RETRAIT_STOCK())
				{
					l_somme -= l_mouvement.get_mvt_nb_lot();
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
