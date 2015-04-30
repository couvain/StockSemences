import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.sql.Connection;

import javax.swing.JFrame;


public class EcranBase extends JFrame
{
	private static final long serialVersionUID = 1L;
	protected Connection          m_connexion_sql  = null;

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected EcranBase()
	{
		super();
		// Empecher le redimensionnement manuel des fenetres
		this.setResizable(false);
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
	public void set_connexion_sql(Connection p_connexion_sql) 
	{
		this.m_connexion_sql = p_connexion_sql;
	}
	
	//******************************************************************************************
	//
	// Centrer la fenetre dans l'ecran
	//
	//******************************************************************************************
	public void centreWindow() 
	{
		Window frame = (JFrame)this;
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}

}
