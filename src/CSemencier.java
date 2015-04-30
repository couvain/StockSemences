
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class CSemencier extends CObjetMetier implements IObjetMetier
{
	private int    	m_sec_id;
	private String 	m_sec_nom;

	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CSemencier() 
	{
		super();
		this.m_sec_id  = 0;
		this.m_sec_nom = "";
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CSemencier(int p_sec_id, String p_sec_nom) 
	{
		super();
		this.m_sec_id  = p_sec_id;
		this.m_sec_nom = p_sec_nom;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void affiche() 
	{
		JOptionPane.showMessageDialog(null,"sec_id = "+this.m_sec_id +" - sec_nom = '" +this.m_sec_nom + "'");
	}
	
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int get_sec_id() 
	{
		return m_sec_id;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_sec_id(int p_sec_id) 
	{
		this.m_sec_id = p_sec_id;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public String get_sec_nom() 
	{
		return m_sec_nom;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_sec_nom(String p_sec_nom) 
	{
		this.m_sec_nom = p_sec_nom;
	}


	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
    public String toString()
    {
        return this.m_sec_nom;
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
			String stm = "DELETE FROM semencier where sec_id=?";
            pst = m_connexion_sql.prepareStatement(stm);
            pst.setInt(1, this.m_sec_id);                    
            pst.executeUpdate();
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_delete(" + this.m_sec_id + ") -> " + sqle.getMessage();
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
	public void bd_insert()
	{
		try 
		{
			PreparedStatement pst = null;
			String stm = "INSERT INTO semencier(sec_nom) VALUES(?)";
            pst = m_connexion_sql.prepareStatement(stm);
            pst.setString(1, this.m_sec_nom);                    
            pst.executeUpdate();
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_insert('" + this.m_sec_nom + "') -> " + sqle.getMessage();
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
	public void bd_select() 
	{
		try 
		{
			PreparedStatement pst = null;
			
		    String stm = "SELECT sec_id, sec_nom FROM semencier WHERE sec_id=(?)";
            pst = m_connexion_sql.prepareStatement(stm,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            pst.setInt(1, m_sec_id); 
            ResultSet rs = pst.executeQuery();
            int rowCount = rs.last() ? rs.getRow() : 0;
            
            if (rowCount > 0)
            {
            	// On a lu le semencier
        		this.m_sec_id  = rs.getInt(1);
        		this.m_sec_nom = rs.getString(2);
            }
            else
            {
            	// Si on ne trouve pas le semencier demandé
        		this.m_sec_id  = 0;
        		this.m_sec_nom = "";
            }  
		    rs.close();
		    pst.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_select() [sec_id="+m_sec_id+"]-> "+sqle.getMessage();
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
	public boolean bd_existe() 
	{
		try 
		{
			PreparedStatement pst = null;
			
		    String stm = "SELECT sec_id FROM semencier WHERE sec_nom=(?)";
            pst = m_connexion_sql.prepareStatement(stm,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            pst.setString(1, m_sec_nom); 
            ResultSet rs = pst.executeQuery();
            int rowCount = rs.last() ? rs.getRow() : 0;
		    rs.close();
		    pst.close(); 
            
            if (rowCount > 0)
            {
            	return true;
            }
            else
            {
            	return false;
            }  
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_existe() -> "+sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		return false;
	}
}

//******************************************************************************************
//
// Classe permettant l'affichage d'un CSemencier dans une combobox
//
//******************************************************************************************
/*
class CSemencierRenderer extends BasicComboBoxRenderer
{
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
  {
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

      if (value != null)
      {
      	CSemencier item = (CSemencier)value;
          setText( item.toString().toUpperCase() );
      }

      if (index == -1)
      {
    	  CSemencier item = (CSemencier)value;
          setText( "" + item.get_sec_id() );
      }

      return this;
  }
}
*/
