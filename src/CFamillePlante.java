import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;


public class CFamillePlante extends CObjetMetier implements IObjetMetier
{
	private int    	m_fap_id;
	private String 		m_fap_nom;

	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CFamillePlante() 
	{
		super();
		this.m_fap_id  = 0;
		this.m_fap_nom = "";
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public CFamillePlante(int p_fap_id, String p_fap_nom) 
	{
		super();
		this.m_fap_id  = p_fap_id;
		this.m_fap_nom = p_fap_nom;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void affiche() 
	{
		JOptionPane.showMessageDialog(null,"fap_id = "+this.m_fap_id +" - fap_nom = '" +this.m_fap_nom + "'");
	}
	
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int get_fap_id() 
	{
		return m_fap_id;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_fap_id(int p_fap_id) 
	{
		this.m_fap_id = p_fap_id;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public String get_fap_nom() 
	{
		return m_fap_nom;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_fap_nom(String p_fap_nom) 
	{
		this.m_fap_nom = p_fap_nom;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
    public String toString()
    {
        return this.m_fap_nom;
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
			String stm = "DELETE FROM famille_plante where fap_id=?";
            pst = m_connexion_sql.prepareStatement(stm);
            pst.setInt(1, this.m_fap_id);                    
            pst.executeUpdate();
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_delete(" + this.m_fap_id + ") -> " + sqle.getMessage();
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
			String stm = "INSERT INTO famille_plante (fap_nom) VALUES (?)";
            pst = m_connexion_sql.prepareStatement(stm);
            pst.setString(1, this.m_fap_nom);                    
            pst.executeUpdate();
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_insert('" + this.m_fap_nom + "') -> " + sqle.getMessage();
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
			
		    String stm = "SELECT fap_id, fap_nom FROM famille_plante WHERE fap_id=?";
            pst = m_connexion_sql.prepareStatement(stm,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            pst.setInt(1, m_fap_id); 
            ResultSet rs = pst.executeQuery();
            int rowCount = rs.last() ? rs.getRow() : 0;
            
            if (rowCount > 0)
            {
            	// On a lu le semencier
        		this.m_fap_id  = rs.getInt(1);
        		this.m_fap_nom = rs.getString(2);
            }
            else
            {
            	// Si on ne trouve pas le semencier demandé
        		this.m_fap_id  = 0;
        		this.m_fap_nom = "";
            }  
		    rs.close();
		    pst.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_select() [fap_id="+m_fap_id+"]-> "+sqle.getMessage();
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
			
		    String stm = "SELECT fap_id FROM famille_plante WHERE fap_nom=?";
            pst = m_connexion_sql.prepareStatement(stm,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            pst.setString(1, m_fap_nom); 
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
			
			l_message = this.getClass().getName()+".bd_existe() -> "+ sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		return false;
	}
}

//******************************************************************************************
//
//  Classe permettant l'affichage d'un CFamilllePlante dans une combobox
//
//******************************************************************************************
class CFamillePlanteRenderer extends BasicComboBoxRenderer
{
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value != null)
        {
        	CFamillePlante item = (CFamillePlante)value;
            setText( item.toString().toUpperCase() );
        }

        if (index == -1)
        {
        	CFamillePlante item = (CFamillePlante)value;
            setText( "" + item.get_fap_id() );
        }

        return this;
    }
}
