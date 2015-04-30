import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class CInventaireNbPlants extends CInventaire 
{

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CInventaireNbPlants()
	{
		super();
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
    public int get_inv_nb_plants() 
    {
		return m_inv_nb_plants;
	}


	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_inv_nb_plants(int p_inv_nb_plants) 
	{
		this.m_inv_nb_plants = p_inv_nb_plants;
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
		
		stm = "INSERT INTO inventaire("+
	            "inv_date, "+
	            "lot_id_lot_plante, "+
	            "inv_dperemption, "+
	            "inv_nb_plants, "+ 
	            "inv_poids_g, "+
	            "inv_nb_graines, "+
	            "inv_nb_lot, "+
	            "pla_id_plante "+
	            ") VALUES (?, NULL, ?, ?, NULL, NULL, NULL, ?);";
		try 
		{
			PreparedStatement pst = null;
            pst = m_connexion_sql.prepareStatement(stm);
            
            pst.setLong(1, this.m_inv_date.get_bigint());
            pst.setLong(2, this.m_inv_date_peremption.get_bigint());
            pst.setInt(3, this.m_inv_nb_plants);
            pst.setInt(4, this.m_inv_pla_id);
            
            pst.executeUpdate();
            
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_insert('" + this.m_inv_pla_id + "') -> " + sqle.getMessage();
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
	public void bd_update()
	{
		String stm;
	
		stm = "UPDATE inventaire SET " +
				"inv_date=?, " +
				"lot_id_lot_plante=NULL, " +
				"inv_dperemption=?,  " +
				"inv_nb_plants=?, " +
				"inv_poids_g=NULL, " +
				"inv_nb_graines=NULL, " +
				"inv_nb_lot=NULL, " +
				"pla_id_plante=? " +
				"WHERE  inv_id=?";
		try 
		{
			PreparedStatement pst = null;
            pst = m_connexion_sql.prepareStatement(stm);
            
            // champ inv_date
            pst.setLong(1, this.m_inv_date.get_bigint());
            pst.setLong(2, this.m_inv_date_peremption.get_bigint());
            pst.setInt(3, this.m_inv_nb_plants);
            pst.setInt(4, this.m_inv_pla_id);
            pst.setInt(5, this.m_inv_id);
            
            pst.executeUpdate();   
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_update(m_inv_id=" + this.m_inv_id + ") -> " + sqle.getMessage();
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
	public String bd_toString() {
		CPlante l_plante = new CPlante();
		String l_resultat;
		
		// Lire l'objet 
		l_plante.set_pla_id(m_inv_pla_id);
		l_plante.bd_select();
		
		l_resultat = this.m_inv_date.toStringFR() + " inventaire de " + this.m_inv_nb_plants + " plants de " +l_plante.get_pla_nom();
		
		if (this.m_inv_date_peremption.est_date_infinie())
		{
			l_resultat += " sans péremption";
		}
		else
		{
			l_resultat += " péremption le "+ this.m_inv_date_peremption.toStringFR();
		}
		
		return l_resultat;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void bd_select_le_plus_recent_a_dperemption(int p_pla_id, long p_date_peremption)
	{
		String l_chaine_sql= 
	    		"SELECT inv_id, inv_date, inv_nb_plants " +
	    		"FROM inventaire " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// pour un inventaire en nb_de_plants
	    		" AND inv_nb_plants is not null" +
	    		" AND inv_dperemption=" + p_date_peremption+
	    		" ORDER BY inv_date DESC";
		
		this.init();
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur de l'inventaire sur la première ligne qui correspond à l'inventaire le plus récent 
		    	 m_inv_id              = rs.getInt(1);
		    	 m_inv_date.set_bigint(rs.getLong(2)); 
		    	 m_inv_pla_id          = p_pla_id;
		    	 m_inv_date_peremption.set_bigint(p_date_peremption); 
		    	 m_inv_nb_plants      =rs.getInt(3);   
		    }
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_select_le_plus_recent_a_dperemption() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
	}

	//******************************************************************************************
	//
	// Renvoie le nb de plants de l'inventaire s'il existe sionon renvoie -1
	//
	//******************************************************************************************
	public static int bd_inventaire_existe(int p_pla_id, long p_date_peremption, long p_date_inventaire)
	{
		int resultat;
		String l_chaine_sql= 
	    		"SELECT inv_nb_plants " +
	    		"FROM inventaire " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// pour un inventaire en nb_de_graines
	    		" AND inv_nb_plants is not null" +
	    		" AND inv_dperemption=" + p_date_peremption+
	    		" AND inv_date=" + p_date_inventaire +";";
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur de l'inventaire 
		    	resultat = rs.getInt(1);   
		    }
		    else
		    {
		    	resultat =-1;
		    }
		    rs.close();
		    st.close();
		    
		    return resultat;
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CInventaireNbPlants.bd_inventaire_existe() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		return -1;
	}

	//******************************************************************************************
	//
	// Spec: fonction qui doit retourner LE DERNIER inventaire connu avant la date précisée pour la plante choisie
	// TODO: ne renvoyer qu'un seul objet
	//
	//******************************************************************************************
	public void bd_select_date(int p_id_plante, CDate p_date, long p_date_peremption) 
	{
        //JOptionPane.showMessageDialog(null,"DEBUG - CInventaire.bd_select_date( p_id_plante="+p_id_plante+" ,p_date="+p_date.get_bigint()+" )");
		ResultSet   rs = null;
		try 
		{
			PreparedStatement pst = null;
			
			// On retourne les lignes d'inventaire avant la date indiquée pour la plante choisie
			String stm = "SELECT "+
						 "inv_id, "+
						 "inv_date,"+
						 "lot_id_lot_plante,"+ 
						 "inv_dperemption,"+ 
						 "inv_nb_plants,"+ 
						 "inv_poids_g,"+ 
						 "inv_nb_graines,"+ 
						 "inv_nb_lot,"+ 
						 "pla_id_plante"+
						 " FROM inventaire" +
						 " WHERE pla_id_plante = (?)"+
						 " AND inv_nb_plants IS NOT NULL"+
						 " AND inv_dperemption = (?)"+
						 " AND inv_date < (?);";
			
			pst = m_connexion_sql.prepareStatement(
													stm, 
													ResultSet.TYPE_SCROLL_INSENSITIVE, 
													ResultSet.CONCUR_READ_ONLY, 
													ResultSet.HOLD_CURSORS_OVER_COMMIT);
			pst.setInt (1, p_id_plante); 
			pst.setLong(2, p_date_peremption);
			pst.setLong(3, p_date.get_bigint()); 
			
			rs = pst.executeQuery();
			int rowCount = rs.last() ? rs.getRow() : 0;

			if (rowCount > 0)
			{
				// On a lu l'inventaire
				this.init();
				this.m_inv_id              = rs.getInt(1);
				this.m_inv_date.set_bigint(rs.getLong(2));
				this.m_inv_lot_id          = rs.getInt(3);
				this.m_inv_date_peremption.set_bigint(rs.getLong(4));
				this.m_inv_nb_plants       = rs.getInt(5);
				this.m_inv_poids_grammes   = rs.getInt(6);
				this.m_inv_nb_graines      = rs.getInt(7);
				this.m_inv_nb_lot          = rs.getInt(8);
				this.m_inv_pla_id          = rs.getInt(9);
			}
			else
			{
				// Si on ne trouve pas l'inventaire demande
				this.init();
			}  
			rs.close();
			
			pst.close(); 
		}
		catch (Exception e)
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_select_date() -> ";
			
			if (e.getClass().toString() == "java.sql.SQLException")
			{
				// Exception SQL
				SQLException sqle = (SQLException)e;
				
				l_message =  l_message+"Erreur de lecture en BDD - Table INVENTAIRE -> \n "+
						"Message: "+ sqle.getMessage() + "\n" +
						"SQLState: "+sqle.getSQLState() +"\n"+
						"ErrorCode: "+sqle.getErrorCode()+"\n" +
						"Class:"+sqle.getClass().toString()+"\n" +
						"StackTrace: "+sqle.fillInStackTrace().toString();
				
			}
			else
			{
				// Exception "normale"
				l_message =  l_message+"'\n'"+e.getMessage()+"'\n'"+e.getLocalizedMessage()+"'";
			}
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);				
		}
	}
	
	
}
