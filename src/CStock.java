//import java.util.Date;
//import java.util.LinkedList;

//import javax.swing.table.DefaultTableModel;


public class CStock 
{
	// Le stock utilise les classes mouvement et inventaire et n'a pas de table STOCK associée en base de données
	// La classe stock gère les objets mouvement et inventaire

	public static final int STOCK_INDETERMINE = -1;
	//--------------------------------------------------------------------------------------------------------
	// Règle générale: le stock à une date d correspond à l'inventaire le plus proche précédemment réalisé, 
	// corrigé des mouvements suivants jusque la date d d'évaluation du stock
	//--------------------------------------------------------------------------------------------------------
	// Données membres
	protected int       m_pla_id;           // La plante concernée
	protected long      m_date_peremption;  // La dete de péremption
	protected CDate     m_date_evaluation_stock;

	// Un stock est composé, pour une plante, de lots de plante, de poids de graines et de nombre de plants à différentes dates de péremption
	
	// une liste de lots (pas forcément les mêmes)
	// une liste de poids de graines avec date de péremption
	// une liste de nb de plants avec une date de péremption 
    // une liste de nb de graines avec date de peremption
	
	
	// Enregistrement d'un inventaire
	
	// Enregistrement d'une sortie du stock
	
	// Enregistrement d'une entrée dans le stock

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CStock()
	{
		super();
		m_date_evaluation_stock = new CDate();    // Par défaut la date créée est à la date du jour (à vérifier)
	}

}
