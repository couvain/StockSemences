import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;


public class CMouvement  extends CObjetMetier implements IObjetMetier
{
	protected int m_mvt_id;
	
	protected String m_mvt_sens;
	protected CDate m_mvt_date;  
	protected int m_mvt_pla_id;
	protected CDate m_mvt_date_peremption; 
	
	// Propriétés réintégrées à la classe de base pour permettre le Select
	
	protected int m_mvt_nb_plants;      // Propre à CMouvementNbPlants
	protected int m_mvt_nb_graines;     // Propre à CMouvementNbGraines
	protected int m_mvt_nb_lot;         // Propre à CMouvementLot
	protected int m_mvt_lot_id;         // pas forcément renseigné dans le cas de graines auto produites
	protected int m_mvt_poids_grammes;

	// Constantes pour identifier le type de Mouvement (pour la fonction get_type_mouvement())
	static int TYPE_MOUVEMENT_INCONNU    = -1;
	static int TYPE_MOUVEMENT_LOT        =  1;
	static int TYPE_MOUVEMENT_NB_GRAINES =  2;
	static int TYPE_MOUVEMENT_NB_PLANTS  =  3;
	static int TYPE_MOUVEMENT_POIDS      =  4;

	
	//******************************************************************************************
	//
	// Constructeur par défaut
	//
	//------------------------------------------------------------------------------------------
	public CMouvement()
	{
		super();
		m_mvt_date = new CDate();
		m_mvt_date_peremption = new CDate();
	}
	
	//******************************************************************************************
	//
	// Conversion de l'objet mouvement en chaine pour affichage utilisateur / log
	//
	//------------------------------------------------------------------------------------------
	public String toString()
	{
		String l_chaine = new String();
		
		l_chaine =             "m_mvt_id              = " + m_mvt_id + "\n";
		l_chaine = l_chaine +  "m_mvt_sens            = " + m_mvt_sens.toString() + "\n";
		l_chaine = l_chaine +  "m_mvt_date            = " + m_mvt_date.get_bigint() + "\n";  
		l_chaine = l_chaine +  "m_mvt_pla_id          = " + m_mvt_pla_id + "\n";
		l_chaine = l_chaine +  "m_mvt_date_peremption = " + m_mvt_date_peremption.get_bigint() + "\n";
		
		l_chaine = l_chaine +  "m_mvt_nb_plants     = " + m_mvt_nb_plants     + "\n";     
		l_chaine = l_chaine +  "m_mvt_nb_graines    = " + m_mvt_nb_graines    + "\n";   
		l_chaine = l_chaine +  "m_mvt_nb_lot        = " + m_mvt_nb_lot        + "\n";        
		l_chaine = l_chaine +  "m_mvt_lot_id        = " + m_mvt_lot_id        + "\n";        
		l_chaine = l_chaine +  "m_mvt_poids_grammes = " + m_mvt_poids_grammes;
		
		return l_chaine;
	}

	//******************************************************************************************
	//
	// Affichage de l'objet mouvement dans une boite de dialogue OK
	//
	//------------------------------------------------------------------------------------------
	public void affiche()
	{
		JOptionPane.showMessageDialog(null, this.toString());
	}
	
	//******************************************************************************************
	//
	// Renvoie à partir d'une heuristique sur les données de l'objet le type de mouvement de stock
	//
	//    - TYPE_MOUVEMENT_INCONNU    = -1;
	//    - TYPE_MOUVEMENT_LOT        =  1;
	//    - TYPE_MOUVEMENT_NB_GRAINES =  2;
	//    - TYPE_MOUVEMENT_NB_PLANTS  =  3;
	//    - TYPE_MOUVEMENT_POIDS      =  4;
	//
	//  Utilisée dans les classes filles de CMouvement
	//    - CMouvementLot.bd_select_date()
	//
	//******************************************************************************************
	public int get_type_mouvement()
	{
		int l_resultat;
		
		// Heuristique permettant d'identifier à quel type de mouvement on a affaire
		if ((m_mvt_nb_lot != 0) && (m_mvt_lot_id != -1))
		{
			l_resultat = TYPE_MOUVEMENT_LOT       ;
		}
		else if (m_mvt_nb_graines != 0)
		{
			l_resultat = TYPE_MOUVEMENT_NB_GRAINES;
		}
		else if (m_mvt_nb_plants != 0)
		{
			l_resultat = TYPE_MOUVEMENT_NB_PLANTS ;
		}
		else if (m_mvt_poids_grammes != 0)
		{
			l_resultat = TYPE_MOUVEMENT_POIDS     ;
		}
		else
		{
			l_resultat = TYPE_MOUVEMENT_INCONNU;
		}
		
		return l_resultat;
	}
	
	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void init()
    {
    	m_mvt_id            = -1;
    	m_mvt_sens          = CSensMouvement.MOUVEMENT_INDEFINI();
    	m_mvt_date.init(); 
    	m_mvt_pla_id        = -1;
    	m_mvt_date_peremption.init(); 
    	
    	// Propriétés réintégrées à la classe de base pour permettre le Select
    	
    	m_mvt_nb_plants     =  0;     
    	m_mvt_nb_graines    =  0;    
    	m_mvt_nb_lot        =  0;        
    	m_mvt_lot_id        = -1;       
    	m_mvt_poids_grammes =  0; 
    }

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public int get_mvt_id() 
	{
		return m_mvt_id;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void set_mvt_id(int m_mvt_id) 
	{
		this.m_mvt_id = m_mvt_id;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public String get_mvt_sens() 
	{
		return m_mvt_sens;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void set_mvt_sens(String p_mvt_sens) 
	{
		this.m_mvt_sens = p_mvt_sens;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public CDate get_mvt_date() 
	{
		return m_mvt_date;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void set_mvt_date(CDate m_mvt_date) 
	{
		this.m_mvt_date = m_mvt_date;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public int get_mvt_pla_id() 
	{
		return m_mvt_pla_id;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void set_mvt_pla_id(int m_mvt_pla_id) 
	{
		this.m_mvt_pla_id = m_mvt_pla_id;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public CDate get_mvt_date_peremption() 
	{
		return m_mvt_date_peremption;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void set_mvt_date_peremption(CDate m_mvt_date_premeption) 
	{
		this.m_mvt_date_peremption = m_mvt_date_premeption;
	}

	//**********************************************************************************************
	//
	// Copie les informations d'un CMouvement passé en paramètre dans l'objet courant
	//
	//----------------------------------------------------------------------------------------------
	public void copie(CMouvement p_mouvement)
	{
		this.m_mvt_id     = p_mouvement.m_mvt_id;
		this.m_mvt_sens   = p_mouvement.m_mvt_sens;
		this.m_mvt_date.copie(p_mouvement.m_mvt_date);  
		this.m_mvt_pla_id = p_mouvement.m_mvt_pla_id;
		this.m_mvt_date_peremption.copie(p_mouvement.m_mvt_date_peremption); 
		
		// Propriétés réintégrées à la classe de base pour permettre le Select
		
		this.m_mvt_nb_plants     = p_mouvement.m_mvt_nb_plants;      // Propre à CMouvementNbPlants
		this.m_mvt_nb_graines    = p_mouvement.m_mvt_nb_graines;     // Propre à CMouvementNbGraines
		this.m_mvt_nb_lot        = p_mouvement.m_mvt_nb_lot;         // Propre à CMouvementLot
		this.m_mvt_lot_id        = p_mouvement.m_mvt_lot_id;         // pas forcément renseigné dans le cas de graines auto produites
		this.m_mvt_poids_grammes = p_mouvement.m_mvt_poids_grammes;
	
	}
	
	//******************************************************************************************
	//
	// Suppression en bdd d'un objet mouvement
	//
	//******************************************************************************************
	@Override
	public void bd_delete() 
	{
		try 
		{
			PreparedStatement pst = null;
			String stm = "DELETE FROM mouvement where mvt_id=?";
            pst = m_connexion_sql.prepareStatement(stm);
            pst.setInt(1, this.m_mvt_id);                    
            pst.executeUpdate();
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_delete(" + this.m_mvt_id + ") -> " + sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}

	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	@Override
	public void bd_insert() 
	{
		/*
		INSERT INTO mouvement(
	            mvt_id, mvt_date, mvt_nb_graines, mvt_nb_lot, mvt_dperemption, 
	            mvt_nb_plants, mvt_poids_g, pla_id_plante, lot_id_lot_plante, 
	            mvt_sens)
	    VALUES (?, ?, ?, ?, ?, 
	            ?, ?, ?, ?, 
	            ?);
*/
	}
	
	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void bd_update() 
	{
		/*
		UPDATE mouvement
		   SET mvt_id=?, mvt_date=?, mvt_nb_graines=?, mvt_nb_lot=?, mvt_dperemption=?, 
		       mvt_nb_plants=?, mvt_poids_g=?, pla_id_plante=?, lot_id_lot_plante=?, 
		       mvt_sens=?
		 WHERE <condition>;
*/
	}

	//******************************************************************************************
	//
	// Lecture d'un objet mouvement
	//
	//******************************************************************************************
	@Override
	public void bd_select() 
	{
		try 
		{
			this.init();
			
			PreparedStatement pst = null;

			String stm = "SELECT "+
					"mvt_id, "+
					"mvt_date,"+
					"lot_id_lot_plante,"+ 
					"mvt_dperemption,"+ 
					"mvt_nb_plants,"+ 
				    "mvt_poids_g,"+ 
				    "mvt_nb_graines,"+ 
				    "mvt_nb_lot,"+ 
				    "pla_id_plante," +
				    "mvt_sens"+
				    " FROM mouvement WHERE mvt_id=(?);";
			
			pst = m_connexion_sql.prepareStatement(stm,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
			pst.setInt(1, m_mvt_id); 
			ResultSet rs = pst.executeQuery();
			int rowCount = rs.last() ? rs.getRow() : 0;

			if (rowCount > 0)
			{
				// On a lu le mouvement
				
				this.m_mvt_id              = rs.getInt(1);
				this.m_mvt_date.set_bigint(rs.getLong(2));
				this.m_mvt_lot_id          = rs.getInt(3);
				this.m_mvt_date_peremption.set_bigint(rs.getLong(4));
				this.m_mvt_nb_plants       = rs.getInt(5);
				this.m_mvt_poids_grammes  = rs.getInt(6);
				this.m_mvt_nb_graines      = rs.getInt(7);
				this.m_mvt_nb_lot          = rs.getInt(8);
				this.m_mvt_pla_id          = rs.getInt(9);
				this.m_mvt_sens            = rs.getString(10);
			}
			else
			{
				// Si on ne trouve pas l'inventaire demande
				this.init();
			}  
			rs.close();
			pst.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_select() [mvt_id="+m_mvt_id+"]-> "+sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
	}
		
}  // class
