import java.awt.GridLayout;
import java.sql.Connection;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class TableauBase extends JPanel 
{
	private static final long serialVersionUID = 7976670951823438671L;
	protected Connection 		  m_connexion_sql = null;
	protected JTable              m_jtable = null;
	protected DefaultTableModel   m_table_model = null;
	protected EcranBase           m_ecran_parent;

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public TableauBase(GridLayout gridLayout) 
	{
        super(new GridLayout(1,0));
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public Connection get_connexion_sql() 
	{
		return m_connexion_sql;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_connexion_sql(Connection m_connexion_sql) 
	{
		this.m_connexion_sql = m_connexion_sql;
	}
	
	//***************************************************************************
	//
	//
	//
	//---------------------------------------------------------------------------
	public EcranBase get_ecran_parent() 
	{
		return m_ecran_parent;
	}

	//***************************************************************************
	//
	//
	//
	//---------------------------------------------------------------------------
	public void set_ecran_principal(EcranBase p_ecran_parent) 
	{
		this.m_ecran_parent = p_ecran_parent;
	}

}
