import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;


public class CStockNbGraines extends CStock implements IObjetStock
{
//	private CInventaireNbGraines            m_dernier_inventaire;
//	private LinkedList<CMouvementNbGraines> m_liste_mouvements;

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CStockNbGraines()
	{
		super();
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	static public int bd_get_stock_actuel(int p_id_plante, long p_date_peremption)
	{
		CInventaireNbGraines            l_dernier_inventaire = new CInventaireNbGraines();
		LinkedList<CMouvementNbGraines> l_liste_mouvements;
		CDate                           l_aujourdhui = new CDate();  // Initialisé par défaut à la date du jour
		CMouvementNbGraines             l_mouvement;
		int                             l_somme;
		
		l_aujourdhui.init_date_du_jour();
		
		// On lit le dernier inventaire
		l_dernier_inventaire.bd_select_date(p_id_plante, l_aujourdhui, p_date_peremption);
		JOptionPane.showMessageDialog(null, "");
		
		if (!l_dernier_inventaire.est_vide())
		{
			JOptionPane.showMessageDialog(null, "l_dernier_inventaire non vide");
			// Si l'inventaire existe en BDD 
	
			l_somme = l_dernier_inventaire.get_inv_nb_graines();
			JOptionPane.showMessageDialog(null, "");
			
			// On lit les mouvements dans le stock à partir de la date de l'inventaire jusqu'à aujourd'hui
			l_liste_mouvements = CMouvementNbGraines.bd_select_date(p_id_plante, p_date_peremption, l_dernier_inventaire.get_inv_date(), l_aujourdhui);
			
			// On calcule le cumul des mouvements
			Iterator<CMouvementNbGraines> it = l_liste_mouvements.iterator();
			while (it.hasNext())
			{
				l_mouvement = it.next();
				if (l_mouvement.get_mvt_sens() == CSensMouvement.MOUVEMENT_AJOUT_STOCK() )
				{
					l_somme += l_mouvement.get_mvt_nb_graines();
				}
				else if (l_mouvement.get_mvt_sens() == CSensMouvement.MOUVEMENT_RETRAIT_STOCK() )
				{
					l_somme -= l_mouvement.get_mvt_nb_graines();
				}
			}
			
			JOptionPane.showMessageDialog(null, "l_somme = "+l_somme);
			// Retour
			return l_somme;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "l_dernier_inventaire VIDE");
			// Pas d'inventaire en BDD
			return CStock.STOCK_INDETERMINE;		// Stock indéterminé
		}
	}


}
