import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;


public class CMouvementLot extends CMouvement 
{
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int get_mvt_nb_lot() 
	{
		return m_mvt_nb_lot;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_mvt_nb_lot(int p_mvt_nb_lot) 
	{
		this.m_mvt_nb_lot = p_mvt_nb_lot;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int get_mvt_lot_id() 
	{
		return m_mvt_lot_id;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_mvt_lot_id(int p_mvt_lot_id) 
	{
		this.m_mvt_lot_id = p_mvt_lot_id;
	}
	
	//******************************************************************************************
	//
	// Lecture d'une liste de mouvements entre deux dates
	//
	//******************************************************************************************
	static public LinkedList<CMouvementLot> bd_select_date(int p_id_plante, int p_lot_id, long p_date_peremption, CDate p_date_debut, CDate p_date_fin) 
	{
		ResultSet   rs = null;
		LinkedList<CMouvementLot> l_liste_mouvements = new LinkedList<CMouvementLot>();
		CMouvementLot l_objet_mouvement = new CMouvementLot();
		int i;

		try 
		{
			PreparedStatement pst = null;
			
			// On retourne les lignes de mouvement
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
				    " FROM mouvement"+
    				" WHERE pla_id_plante = (?)"+
					" AND lot_id_lot_plante = (?)"+ 
				    " AND mvt_dperemption = (?)"+
					" AND mvt_date >= (?)"+
        			" AND mvt_date <= (?);";
			
			pst = m_connexion_sql.prepareStatement(
													stm, 
													ResultSet.TYPE_SCROLL_INSENSITIVE, 
													ResultSet.CONCUR_READ_ONLY, 
													ResultSet.HOLD_CURSORS_OVER_COMMIT);
			pst.setInt (1,p_id_plante);
			pst.setInt (2,p_lot_id);
			pst.setLong(3,p_date_peremption);
			pst.setLong(4, p_date_debut.get_bigint()); 
			pst.setLong(5, p_date_fin.get_bigint()); 
			
			rs = pst.executeQuery();
			int rowCount = rs.last() ? rs.getRow() : 0;
			if (rowCount > 0)
			{
				rs.first();
				
				for ( i = 1 ; i <= rowCount ; i++ )
				{
					// On a lu l'inventaire
					l_objet_mouvement = new CMouvementLot();
					l_objet_mouvement.init();
					
					l_objet_mouvement.m_mvt_id                       = rs.getInt(1);
					l_objet_mouvement.m_mvt_date.set_bigint(           rs.getLong(2));
					l_objet_mouvement.m_mvt_lot_id                   = rs.getInt(3);
					l_objet_mouvement.m_mvt_date_peremption.set_bigint(rs.getLong(4));
					l_objet_mouvement.m_mvt_nb_plants                = rs.getInt(5);
					l_objet_mouvement.m_mvt_poids_grammes            = rs.getInt(6);
					l_objet_mouvement.m_mvt_nb_graines               = rs.getInt(7);
					l_objet_mouvement.m_mvt_nb_lot                   = rs.getInt(8);
					l_objet_mouvement.m_mvt_pla_id                   = rs.getInt(9);
					l_objet_mouvement.m_mvt_sens                     = rs.getString(10);
					
					l_liste_mouvements.add(l_objet_mouvement);
					rs.next();
				}
			}
			else
			{
				// Si on ne trouve pas le mouvement demande
				// ne rien faire
			}  
			rs.close();
			pst.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CMouvementLot.bd_select_date_racine() [p_id_plante="+p_id_plante+", p_lot_id="+p_lot_id+" ,date_debut="+p_date_debut.toString() +", date_fin="+p_date_fin.toString()+ "]-> "+sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}	
		return l_liste_mouvements;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	@Override
	public void bd_insert() 
	{
		String stm;
		
		stm = "INSERT INTO mouvement("+
	            "mvt_date, "+
	            "lot_id_lot_plante, "+
	            "mvt_dperemption, "+
	            "mvt_nb_plants, "+ 
	            "mvt_poids_g, "+
	            "mvt_nb_graines, "+
	            "mvt_nb_lot, "+
	            "pla_id_plante," +
	            "mvt_sens "+
	            ") VALUES (?, ?, ?, NULL, NULL, NULL, ?, ?, ?);";
		try 
		{
			PreparedStatement pst = null;
            pst = m_connexion_sql.prepareStatement(stm);
            
            // champ inv_date
            pst.setLong(1, this.m_mvt_date.get_bigint());
            pst.setInt(2, this.m_mvt_lot_id);
            pst.setLong(3, this.m_mvt_date_peremption.get_bigint());
            pst.setInt(4, this.m_mvt_nb_lot);
            pst.setInt(5, this.m_mvt_pla_id);
            pst.setString(6, this.m_mvt_sens.toString());
            
            pst.executeUpdate();
            
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_insert(m_mvt_lot_id='" + this.m_mvt_lot_id + "') -> " + sqle.getMessage();
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
	public void bd_update()
	{
		String stm;
	
		stm = "UPDATE mouvement SET " +
				"mvt_date=?, " +
				"lot_id_lot_plante=?, " +
				"mvt_dperemption=?,  " +
				"mvt_nb_plants=NULL, " +
				"mvt_poids_g=NULL, " +
				"mvt_nb_graines=NULL, " +
				"mvt_nb_lot=?, " +
				"pla_id_plante=?," +
				"mvt_sens=? " +
				"WHERE mvt_id=?";
		try 
		{
			PreparedStatement pst = null;
            pst = m_connexion_sql.prepareStatement(stm);
            
            // champ mvt_date
            pst.setLong(1, this.m_mvt_date.get_bigint());
            pst.setInt(2, this.m_mvt_lot_id);
            pst.setLong(3, this.m_mvt_date_peremption.get_bigint());
            pst.setInt(4, this.m_mvt_nb_lot);
            pst.setInt(5, this.m_mvt_pla_id);
            pst.setString(6, this.m_mvt_sens.toString());
            pst.setInt(7, this.m_mvt_id);
            
            pst.executeUpdate();   
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_update(m_mvt_id=" + this.m_mvt_id + ") -> " + sqle.getMessage();
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
	public String bd_toString() 
	{
		CPlante l_plante = new CPlante();
		String l_resultat;
		
		// Lire l'objet 
		l_plante.set_pla_id(m_mvt_pla_id);
		l_plante.bd_select();
		
		if (this.m_mvt_sens == CSensMouvement.MOUVEMENT_RETRAIT_STOCK())
		{
			l_resultat = this.m_mvt_date.toStringFR() + " sortie de " + this.m_mvt_nb_lot + " lots de " +l_plante.get_pla_nom();
		}
		else
		{
			l_resultat = this.m_mvt_date.toStringFR() + " entrée de " + this.m_mvt_nb_lot + " lots de " +l_plante.get_pla_nom();			
		}
		if (this.m_mvt_date_peremption.est_date_infinie())
		{
			l_resultat += " sans péremption";
		}
		else
		{
			l_resultat += " péremption le "+ this.m_mvt_date_peremption.toStringFR();
		}
		
		return l_resultat;
	}
}
