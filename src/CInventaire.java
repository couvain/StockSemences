import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class CInventaire  extends CObjetMetier implements IObjetMetier
{

	protected int m_inv_id;

	protected CDate m_inv_date; 
	protected int m_inv_pla_id;
	protected CDate m_inv_date_peremption; 
	
	// Propriétés réintégrées à la classe de base pour permettre le Select
	
	protected int m_inv_nb_plants;     // Propre à CInventaireNbPlants
	protected int m_inv_nb_graines;    // Propre à CInventaireNbGraines
	protected int m_inv_nb_lot;        // Propre à CInventaireLot
	protected int m_inv_lot_id;        // Propre à CInventaireLot - pas forcément renseigné (dans le cas de graines auto produites)
	protected int m_inv_poids_grammes; // Propre à CInventairePoids

	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CInventaire()
	{
		super();
		m_inv_date            = new CDate();
		m_inv_date_peremption = new CDate();
		init();
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void init()
    {
    	m_inv_id            = -1;
    	m_inv_date.init(); 
    	m_inv_pla_id        = -1;
    	m_inv_date_peremption.init(); 
    	
    	// Propriétés réintégrées à la classe de base pour permettre le Select
    	
    	m_inv_nb_plants     =  0;     
    	m_inv_nb_graines    =  0;    
    	m_inv_nb_lot        =  0;        
    	m_inv_lot_id        = -1;       
    	m_inv_poids_grammes =  0; 
    }
	
	//******************************************************************************************
	//
	// Teste si l'objet est vide (utile suite à une lecture en BDD qui appelle init si rien n'est renvoyé)
	//
	//******************************************************************************************
	public boolean est_vide()
	{
		if (m_inv_id == -1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int get_inv_id() 
	{
		return m_inv_id;
	}


	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_inv_id(int p_inv_id) 
	{
		this.m_inv_id = p_inv_id;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CDate get_inv_date() 
	{
		return m_inv_date;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_inv_date(CDate p_inv_date) 
	{
		this.m_inv_date = p_inv_date;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_inv_date_bigint(long p_inv_date_bigint) 
	{
		this.m_inv_date.set_bigint(p_inv_date_bigint);
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int get_inv_pla_id() 
	{
		return m_inv_pla_id;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_inv_pla_id(int p_inv_pla_id) 
	{
		this.m_inv_pla_id = p_inv_pla_id;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CDate get_inv_date_peremption() 
	{
		return m_inv_date_peremption;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_inv_date_peremption(CDate p_inv_date_premeption) 
	{
		this.m_inv_date_peremption = p_inv_date_premeption;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_inv_date_peremption_bigint(long p_inv_date_premeption_bigint) 
	{
		this.m_inv_date_peremption.set_bigint(p_inv_date_premeption_bigint);
	}
		
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	@Override
	public void bd_delete() 
	{
		try 
		{
			PreparedStatement pst = null;
			String stm = "DELETE FROM inventaire where inv_id=?";
            pst = m_connexion_sql.prepareStatement(stm);
            pst.setInt(1, this.m_inv_id);                    
            pst.executeUpdate();
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_delete(" + this.m_inv_id + ") -> " + sqle.getMessage();
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
		// tout se passe dans les classes filles
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	@Override
	public void bd_insert() 
	{
		// tout se passe dans les classes filles
	}

	//******************************************************************************************
	//
	// insere un inventaire nul sur toutes les valeurs à la création d'une plante
	//
	//******************************************************************************************
/*	public void bd_insert_initial() 
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
	            ") VALUES (20000101, NULL, 20000101, 0, 0, 0, 0, ?);";
		try 
		{
			PreparedStatement pst = null;
            pst = m_connexion_sql.prepareStatement(stm);
            
            pst.setInt(1, this.m_inv_pla_id);
            
            pst.executeUpdate();
            
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_insert_initial('" + this.m_inv_lot_id + "') -> " + sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
	}
*/
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	@Override
	public void bd_select() 
	{
		try 
		{
			PreparedStatement pst = null;

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
						 " WHERE inv_id=(?);";
			
			pst = m_connexion_sql.prepareStatement(stm,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
			pst.setInt(1, m_inv_id); 
			ResultSet rs = pst.executeQuery();
			int rowCount = rs.last() ? rs.getRow() : 0;

			if (rowCount > 0)
			{
				// On a lu l'inventaire
				
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
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_select() [inv_id="+m_inv_id+"]-> "+sqle.getMessage();
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
	/*
	@Override
	public String toString()
	{
		return null;
		
	}
	*/
}
