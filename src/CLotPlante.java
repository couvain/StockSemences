import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;


public class CLotPlante extends CObjetMetier implements IObjetMetier
{
	private int			m_lot_id;
	private String		m_lot_nom;
	private int			m_pla_id_plante;
	private int			m_sec_id_semencier;
	private String		m_lot_avis;
	private double		m_lot_nb_etoiles;
	
	private int			m_lot_poids_g;
	private int			m_lot_nb_graines;
	private int			m_lot_nb_plants;

	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CLotPlante()
	{
		m_lot_nom  = new String();
		m_lot_avis = new String();
		init();
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CLotPlante(
			 int			p_lot_id,
			 String			p_lot_nom,
			 int			p_pla_id_plante,
			 int			p_sec_id_semencier,
			 String		    p_lot_avis,
			 double		    p_lot_nb_etoiles,
			 int			p_lot_poids_g,
			 int			p_lot_nb_graines,
			 int			p_lot_nb_plants)
	{
		//private Connection m_connexion_sql = null;
		
		m_lot_id 			= p_lot_id;
		m_lot_nom           = p_lot_nom;
		m_pla_id_plante 	= p_pla_id_plante;
		m_sec_id_semencier 	= p_sec_id_semencier;
		
		m_lot_avis 			= p_lot_avis;
		m_lot_nb_etoiles 	= p_lot_nb_etoiles;
		
		m_lot_poids_g 		= p_lot_poids_g;
		m_lot_nb_graines 	= p_lot_nb_graines;
		m_lot_nb_plants 	= p_lot_nb_plants;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public String toString()
	{
		// TODO: améliorer
		return m_lot_nom;
	}
	
	//******************************************************************************************
	//
	// initialise tous les champs sauf la connexion SQL
	//
	//******************************************************************************************
	public void init()
	{
		//private Connection m_connexion_sql = null;
	
		m_lot_id 			= -1;
		m_lot_nom           = "";
		m_pla_id_plante 	= -1;
		m_sec_id_semencier 	= -1;
		
		m_lot_avis 			= "";
		m_lot_nb_etoiles 	= 0.0;
		
		m_lot_poids_g 		= 0;
		m_lot_nb_graines 	= 0;
		m_lot_nb_plants 	= 0;
		
	}
	
	public int get_lot_id() 
	{
		return m_lot_id;
	}
	
	public void set_lot_id(int m_lot_id) 
	{
		this.m_lot_id = m_lot_id;
	}
	
	public int get_pla_id_plante() 
	{
		return m_pla_id_plante;
	}
	
	public void set_pla_id_plante(int m_pla_id_plante) 
	{
		this.m_pla_id_plante = m_pla_id_plante;
	}
	
	public int get_sec_id_semencier() 
	{
		return m_sec_id_semencier;
	}
	
	public void set_sec_id_semencier(int m_sec_id_semencier) 
	{
		this.m_sec_id_semencier = m_sec_id_semencier;
	}
	
	public String get_lot_avis() 
	{
		return m_lot_avis;
	}
	
	public void set_lot_avis(String m_lot_avis) 
	{
		this.m_lot_avis = m_lot_avis;
	}
	
	public double get_lot_nb_etoiles() 
	{
		return m_lot_nb_etoiles;
	}
	
	public void set_lot_nb_etoiles(double m_lot_nb_etoiles) 
	{
		this.m_lot_nb_etoiles = m_lot_nb_etoiles;
	}
	
	public int get_lot_poids_g() 
	{
		return m_lot_poids_g;
	}
	
	public void set_lot_poids_g(int m_lot_poids_g) 
	{
		this.m_lot_poids_g = m_lot_poids_g;
	}
	
	public int get_lot_nb_graines() 
	{
		return m_lot_nb_graines;
	}
	
	public void set_lot_nb_graines(int m_lot_nb_graines) 
	{
		this.m_lot_nb_graines = m_lot_nb_graines;
	}
	
	public int get_lot_nb_plants() 
	{
		return m_lot_nb_plants;
	}
	
	public void set_lot_nb_plants(int m_lot_nb_plants) 
	{
		this.m_lot_nb_plants = m_lot_nb_plants;
	}
	
	public String get_lot_nom() 
	{
		return m_lot_nom;
	}
	
	public void set_lot_nom(String m_lot_nom) 
	{
		this.m_lot_nom = m_lot_nom;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CSemencier bd_get_semencier()
	{
		CSemencier l_semencier = new CSemencier();
		l_semencier.set_sec_id(this.m_sec_id_semencier);
		l_semencier.bd_select();
		
		return l_semencier;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void bd_insert()
	{

		String stm;
		
		stm = "INSERT INTO lot_plante ("+
		
			  "pla_id_plante,"+
			  "sec_id_semencier,"+
			  "lot_avis,"+
			  "lot_nb_etoiles,"+
			  "lot_poids_g,"+
			  "lot_nb_graines,"+
			  "lot_nb_plants," +
			  "lot_nom"+

				") VALUES (?,?,?,?,?,?,?,?);";
		try 
		{
			PreparedStatement pst = null;
            pst = m_connexion_sql.prepareStatement(stm);
            
           	pst.setInt(1, this.m_pla_id_plante);
            pst.setInt(2, this.m_sec_id_semencier);
            pst.setString(3, this.m_lot_avis);
           	pst.setDouble(4, this.m_lot_nb_etoiles);
			pst.setInt(5, this.m_lot_poids_g);
			pst.setInt(6, this.m_lot_nb_graines);
			pst.setInt(7, this.m_lot_nb_plants);
			pst.setString(8, m_lot_nom);
           
            pst.executeUpdate();
            
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_insert(pla_id_plante=" + this.m_pla_id_plante + "sec_id_semencier="+this.m_sec_id_semencier+") -> " + sqle.getMessage();
		    System.err.println(l_message);
    		JOptionPane.showMessageDialog(null, l_message);   
		    System.exit(1);
		}
	}

	//******************************************************************************************
	//
	//  
	//
	//******************************************************************************************
	public void bd_select() 
	{
		try 
		{
			PreparedStatement pst = null;

			String stm = "SELECT "+
						"lot_id,"+
						"pla_id_plante,"+
						"sec_id_semencier,"+
						"lot_avis,"+
						"lot_nb_etoiles,"+
						"lot_poids_g,"+
						"lot_nb_graines,"+
						"lot_nb_plants," +
						"lot_nom"+


		    " FROM lot_plante WHERE lot_id=(?)";
			pst = m_connexion_sql.prepareStatement(stm,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
			pst.setInt(1, m_lot_id); 
			ResultSet rs = pst.executeQuery();
			int rowCount = rs.last() ? rs.getRow() : 0;

			if (rowCount > 0)
			{
				// On a lu le lot de plante
				this.m_lot_id           = rs.getInt(1);
				this.m_pla_id_plante    = rs.getInt(2);
				this.m_sec_id_semencier = rs.getInt(3);
				this.m_lot_avis         = rs.getString(4);
				this.m_lot_nb_etoiles   = rs.getDouble(5);
				this.m_lot_poids_g      = rs.getInt(6);
				this.m_lot_nb_graines   = rs.getInt(7);
				this.m_lot_nb_plants    = rs.getInt(8);
				this.m_lot_nom          = rs.getString(9);
			}
			else
			{
				// Si on ne trouve pas le lot de plante demandÃ©
				this.init();
			}  
			rs.close();
			pst.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_select() [lot_id="+m_lot_id+"]-> "+sqle.getMessage();
		    System.err.println(l_message);
    		JOptionPane.showMessageDialog(null, l_message);   
			System.exit(1);
		}
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void bd_delete()
	{
		try 
		{
			PreparedStatement pst = null;
			String stm = "DELETE FROM lot_plante where lot_id=?";
            pst = m_connexion_sql.prepareStatement(stm);
            pst.setInt(1, this.m_lot_id);                    
            pst.executeUpdate();
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName() + ".bd_delete(" + this.m_lot_id + ") -> " + sqle.getMessage();
		    System.err.println(l_message);
    		JOptionPane.showMessageDialog(null, l_message);   
		    System.exit(1);
		}
	}
}

//******************************************************************************************
//
//Classe permettant l'affichage d'un CLotPlante dans une combobox
//
//******************************************************************************************
class CLotPlanteRenderer extends BasicComboBoxRenderer
{
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
  {
    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

    if (value != null)
    {
    	CLotPlante item = (CLotPlante)value;
        setText( item.toString().toUpperCase() );
    }

    if (index == -1)
    {
    	CLotPlante item = (CLotPlante)value;
        setText( "" + item.get_lot_id() );
    }

    return this;
  }
}
