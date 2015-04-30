import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;



public class CPlante extends CObjetMetier implements IObjetMetier
{
	private int     m_pla_id;
	private String  m_pla_nom;
	private String  m_pla_notes;
	private int		m_fap_id_famille_plante;

	private boolean m_pla_semis_ext_jan;
	private boolean m_pla_semis_ext_fev;
	private boolean m_pla_semis_ext_mar;
	private boolean m_pla_semis_ext_avr;
	private boolean m_pla_semis_ext_mai;
	private boolean m_pla_semis_ext_jun;
	private boolean m_pla_semis_ext_jul;
	private boolean m_pla_semis_ext_aou;
	private boolean m_pla_semis_ext_sep;
	private boolean m_pla_semis_ext_oct;
	private boolean m_pla_semis_ext_nov;
	private boolean m_pla_semis_ext_dec;

	private boolean m_pla_semis_ser_jan;
	private boolean m_pla_semis_ser_fev;
	private boolean m_pla_semis_ser_mar;
	private boolean m_pla_semis_ser_avr;
	private boolean m_pla_semis_ser_mai;
	private boolean m_pla_semis_ser_jun;
	private boolean m_pla_semis_ser_jul;
	private boolean m_pla_semis_ser_aou;
	private boolean m_pla_semis_ser_sep;
	private boolean m_pla_semis_ser_oct;
	private boolean m_pla_semis_ser_nov;
	private boolean m_pla_semis_ser_dec;

	public CPlante()
	{
		init();
	}
	
	// Constructeur pour combobox de selection de plante
	public CPlante(int p_pla_id, String p_pla_nom) 
	{
		m_pla_id  = p_pla_id;
		m_pla_nom = p_pla_nom;
	}
	
	public void affiche_court()
	{
		JOptionPane.showMessageDialog(null, "pla_id="+m_pla_id +" - pla_nom='"+m_pla_nom+"'");
	}
	
	public int get_pla_id() 
	{
		return m_pla_id;
	}
	public void set_pla_id(int p_pla_id) 
	{
		this.m_pla_id = p_pla_id;
	}
	public String get_pla_nom() 
	{
		return m_pla_nom;
	}
	public void set_pla_nom(String p_pla_nom) 
	{
		this.m_pla_nom = p_pla_nom;
	}
	public String get_pla_notes() 
	{
		return m_pla_notes;
	}
	public void set_pla_notes(String p_pla_notes) 
	{
		this.m_pla_notes = p_pla_notes;
	}
	public int get_fap_id_famille_plante() 
	{
		return m_fap_id_famille_plante;
	}
	public void set_fap_id_famille_plante(int p_fap_id_famille_plante) 
	{
		this.m_fap_id_famille_plante = p_fap_id_famille_plante;
	}
	public boolean get_pla_semis_ext_jan() 
	{
		return m_pla_semis_ext_jan;
	}
	public void set_pla_semis_ext_jan(boolean p_pla_semis_ext_jan) 
	{
		this.m_pla_semis_ext_jan = p_pla_semis_ext_jan;
	}
	public boolean get_pla_semis_ext_fev() 
	{
		return m_pla_semis_ext_fev;
	}
	public void set_pla_semis_ext_fev(boolean p_pla_semis_ext_fev) 
	{
		this.m_pla_semis_ext_fev = p_pla_semis_ext_fev;
	}
	public boolean get_pla_semis_ext_mar() 
	{
		return m_pla_semis_ext_mar;
	}
	public void set_pla_semis_ext_mar(boolean p_pla_semis_ext_mar) 
	{
		this.m_pla_semis_ext_mar = p_pla_semis_ext_mar;
	}
	public boolean get_pla_semis_ext_avr() 
	{
		return m_pla_semis_ext_avr;
	}
	public void set_pla_semis_ext_avr(boolean p_pla_semis_ext_avr) 
	{
		this.m_pla_semis_ext_avr = p_pla_semis_ext_avr;
	}
	public boolean get_pla_semis_ext_mai() 
	{
		return m_pla_semis_ext_mai;
	}
	public void set_pla_semis_ext_mai(boolean p_pla_semis_ext_mai) 
	{
		this.m_pla_semis_ext_mai = p_pla_semis_ext_mai;
	}
	public boolean get_pla_semis_ext_jun() 
	{
		return m_pla_semis_ext_jun;
	}
	public void set_pla_semis_ext_jun(boolean p_pla_semis_ext_jun) 
	{
		this.m_pla_semis_ext_jun = p_pla_semis_ext_jun;
	}
	public boolean get_pla_semis_ext_jul() 
	{
		return m_pla_semis_ext_jul;
	}
	public void set_pla_semis_ext_jul(boolean p_pla_semis_ext_jul) 
	{
		this.m_pla_semis_ext_jul = p_pla_semis_ext_jul;
	}
	public boolean get_pla_semis_ext_aou() 
	{
		return m_pla_semis_ext_aou;
	}
	public void set_pla_semis_ext_aou(boolean p_pla_semis_ext_aou) 
	{
		this.m_pla_semis_ext_aou = p_pla_semis_ext_aou;
	}
	public boolean get_pla_semis_ext_sep() 
	{
		return m_pla_semis_ext_sep;
	}
	public void set_pla_semis_ext_sep(boolean p_pla_semis_ext_sep) 
	{
		this.m_pla_semis_ext_sep = p_pla_semis_ext_sep;
	}
	public boolean get_pla_semis_ext_oct() 
	{
		return m_pla_semis_ext_oct;
	}
	public void set_pla_semis_ext_oct(boolean p_pla_semis_ext_oct) 
	{
		this.m_pla_semis_ext_oct = p_pla_semis_ext_oct;
	}
	public boolean get_pla_semis_ext_nov() 
	{
		return m_pla_semis_ext_nov;
	}
	public void set_pla_semis_ext_nov(boolean p_pla_semis_ext_nov) 
	{
		this.m_pla_semis_ext_nov = p_pla_semis_ext_nov;
	}
	public boolean get_pla_semis_ext_dec() 
	{
		return m_pla_semis_ext_dec;
	}
	public void set_pla_semis_ext_dec(boolean p_pla_semis_ext_dec) 
	{
		this.m_pla_semis_ext_dec = p_pla_semis_ext_dec;
	}
	public boolean get_pla_semis_ser_jan() 
	{
		return m_pla_semis_ser_jan;
	}
	public void set_pla_semis_ser_jan(boolean p_pla_semis_ser_jan) 
	{
		this.m_pla_semis_ser_jan = p_pla_semis_ser_jan;
	}
	public boolean get_pla_semis_ser_fev() 
	{
		return m_pla_semis_ser_fev;
	}
	public void set_pla_semis_ser_fev(boolean p_pla_semis_ser_fev) 
	{
		this.m_pla_semis_ser_fev = p_pla_semis_ser_fev;
	}
	public boolean get_pla_semis_ser_mar() 
	{
		return m_pla_semis_ser_mar;
	}
	public void set_pla_semis_ser_mar(boolean p_pla_semis_ser_mar) 
	{
		this.m_pla_semis_ser_mar = p_pla_semis_ser_mar;
	}
	public boolean get_pla_semis_ser_avr() 
	{
		return m_pla_semis_ser_avr;
	}
	public void set_pla_semis_ser_avr(boolean p_pla_semis_ser_avr) 
	{
		this.m_pla_semis_ser_avr = p_pla_semis_ser_avr;
	}
	public boolean get_pla_semis_ser_mai() 
	{
		return m_pla_semis_ser_mai;
	}
	public void set_pla_semis_ser_mai(boolean p_pla_semis_ser_mai) 
	{
		this.m_pla_semis_ser_mai = p_pla_semis_ser_mai;
	}
	public boolean get_pla_semis_ser_jun() 
	{
		return m_pla_semis_ser_jun;
	}
	public void set_pla_semis_ser_jun(boolean p_pla_semis_ser_jun) 
	{
		this.m_pla_semis_ser_jun = p_pla_semis_ser_jun;
	}
	public boolean get_pla_semis_ser_jul() 
	{
		return m_pla_semis_ser_jul;
	}
	public void set_pla_semis_ser_jul(boolean p_pla_semis_ser_jul) 
	{
		this.m_pla_semis_ser_jul = p_pla_semis_ser_jul;
	}
	public boolean get_pla_semis_ser_aou() 
	{
		return m_pla_semis_ser_aou;
	}
	public void set_pla_semis_ser_aou(boolean p_pla_semis_ser_aou) 
	{
		this.m_pla_semis_ser_aou = p_pla_semis_ser_aou;
	}
	public boolean get_pla_semis_ser_sep() 
	{
		return m_pla_semis_ser_sep;
	}
	public void set_pla_semis_ser_sep(boolean p_pla_semis_ser_sep) 
	{
		this.m_pla_semis_ser_sep = p_pla_semis_ser_sep;
	}
	public boolean get_pla_semis_ser_oct() {
		return m_pla_semis_ser_oct;
	}
	public void set_pla_semis_ser_oct(boolean p_pla_semis_ser_oct) 
	{
		this.m_pla_semis_ser_oct = p_pla_semis_ser_oct;
	}
	public boolean get_pla_semis_ser_nov() 
	{
		return m_pla_semis_ser_nov;
	}
	public void set_pla_semis_ser_nov(boolean p_pla_semis_ser_nov) 
	{
		this.m_pla_semis_ser_nov = p_pla_semis_ser_nov;
	}
	public boolean get_pla_semis_ser_dec() 
	{
		return m_pla_semis_ser_dec;
	}
	public void set_pla_semis_ser_dec(boolean p_pla_semis_ser_dec) 
	{
		this.m_pla_semis_ser_dec = p_pla_semis_ser_dec;
	}

	//******************************************************************************************
	//
	// initialise tous les champs sauf la connexion SQL
	//
	//******************************************************************************************
	public void init()
	{
		//private Connection m_connexion_sql = null;
		
		m_pla_id = -1;
		m_pla_nom = "";
		m_pla_notes = "";;
		m_fap_id_famille_plante = -1;

		m_pla_semis_ext_jan = false;
		m_pla_semis_ext_fev = false;
		m_pla_semis_ext_mar = false;
		m_pla_semis_ext_avr = false;
		m_pla_semis_ext_mai = false;
		m_pla_semis_ext_jun = false;
		m_pla_semis_ext_jul = false;
		m_pla_semis_ext_aou = false;
		m_pla_semis_ext_sep = false;
		m_pla_semis_ext_oct = false;
		m_pla_semis_ext_nov = false;
		m_pla_semis_ext_dec = false;

		m_pla_semis_ser_jan = false;
		m_pla_semis_ser_fev = false;
		m_pla_semis_ser_mar = false;
		m_pla_semis_ser_avr = false;
		m_pla_semis_ser_mai = false;
		m_pla_semis_ser_jun = false;
		m_pla_semis_ser_jul = false;
		m_pla_semis_ser_aou = false;
		m_pla_semis_ser_sep = false;
		m_pla_semis_ser_oct = false;
		m_pla_semis_ser_nov = false;
		m_pla_semis_ser_dec = false;
		
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void bd_insert()
	{

		String stm;
		
		stm = "INSERT INTO plante ("+
						  "pla_nom,"+
						  "pla_notes,"+
						  "fap_id_famille_plante," +
						  "pla_semis_ext_jan,"+
						  "pla_semis_ext_fev,"+
						  "pla_semis_ext_mar,"+
						  "pla_semis_ext_avr,"+
						  "pla_semis_ext_mai,"+
						  "pla_semis_ext_jun,"+
						  "pla_semis_ext_jul,"+
						  "pla_semis_ext_aou,"+
						  "pla_semis_ext_sep,"+
						  "pla_semis_ext_oct,"+
						  "pla_semis_ext_nov,"+
						  "pla_semis_ext_dec,"+
						  
						  "pla_semis_ser_jan,"+
						  "pla_semis_ser_fev,"+
						  "pla_semis_ser_mar,"+
						  "pla_semis_ser_avr,"+
						  "pla_semis_ser_mai,"+
						  "pla_semis_ser_jun,"+
						  "pla_semis_ser_jul,"+
						  "pla_semis_ser_aou,"+
						  "pla_semis_ser_sep,"+
						  "pla_semis_ser_oct,"+
						  "pla_semis_ser_nov,"+
						  "pla_semis_ser_dec"+
				") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		try 
		{
			PreparedStatement pst = null;
            pst = m_connexion_sql.prepareStatement(stm);
            
            // champ pla_nom
            pst.setString(1, this.m_pla_nom);
            // champ pla_notes
            pst.setString(2, this.m_pla_notes);
            
            // champ fap_id_famille_plante
           	pst.setInt(3, this.m_fap_id_famille_plante);
            
            // champs booleens
           	pst.setBoolean(4, this.m_pla_semis_ext_jan);
			pst.setBoolean(5, this.m_pla_semis_ext_fev);
			pst.setBoolean(6, this.m_pla_semis_ext_mar);
			pst.setBoolean(7, this.m_pla_semis_ext_avr);
			pst.setBoolean(8, this.m_pla_semis_ext_mai);
			pst.setBoolean(9, this.m_pla_semis_ext_jun);
			pst.setBoolean(10, this.m_pla_semis_ext_jul);
			pst.setBoolean(11, this.m_pla_semis_ext_aou);
			pst.setBoolean(12, this.m_pla_semis_ext_sep);
			pst.setBoolean(13, this.m_pla_semis_ext_oct);
			pst.setBoolean(14, this.m_pla_semis_ext_nov);
			pst.setBoolean(15, this.m_pla_semis_ext_dec);

			pst.setBoolean(16, this.m_pla_semis_ser_jan);
			pst.setBoolean(17, this.m_pla_semis_ser_fev);
			pst.setBoolean(18, this.m_pla_semis_ser_mar);
			pst.setBoolean(19, this.m_pla_semis_ser_avr);
			pst.setBoolean(20, this.m_pla_semis_ser_mai);
			pst.setBoolean(21, this.m_pla_semis_ser_jun);
			pst.setBoolean(22, this.m_pla_semis_ser_jul);
			pst.setBoolean(23, this.m_pla_semis_ser_aou);
			pst.setBoolean(24, this.m_pla_semis_ser_sep);
			pst.setBoolean(25, this.m_pla_semis_ser_oct);
			pst.setBoolean(26, this.m_pla_semis_ser_nov);
			pst.setBoolean(27, this.m_pla_semis_ser_dec);
            
            pst.executeUpdate();
            
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_insert('" + this.m_pla_nom + "') -> " + sqle.getMessage();
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
	
		stm = "UPDATE plante set "+
						  "pla_nom=?,"+
						  "pla_notes=?,"+
						  "fap_id_famille_plante=?," +
						  "pla_semis_ext_jan=?,"+
						  "pla_semis_ext_fev=?,"+
						  "pla_semis_ext_mar=?,"+
						  "pla_semis_ext_avr=?,"+
						  "pla_semis_ext_mai=?,"+
						  "pla_semis_ext_jun=?,"+
						  "pla_semis_ext_jul=?,"+
						  "pla_semis_ext_aou=?,"+
						  "pla_semis_ext_sep=?,"+
						  "pla_semis_ext_oct=?,"+
						  "pla_semis_ext_nov=?,"+
						  "pla_semis_ext_dec=?,"+
						  
						  "pla_semis_ser_jan=?,"+
						  "pla_semis_ser_fev=?,"+
						  "pla_semis_ser_mar=?,"+
						  "pla_semis_ser_avr=?,"+
						  "pla_semis_ser_mai=?,"+
						  "pla_semis_ser_jun=?,"+
						  "pla_semis_ser_jul=?,"+
						  "pla_semis_ser_aou=?,"+
						  "pla_semis_ser_sep=?,"+
						  "pla_semis_ser_oct=?,"+
						  "pla_semis_ser_nov=?,"+
						  "pla_semis_ser_dec=?"+
						  " WHERE pla_id=?";
		try 
		{
			PreparedStatement pst = null;
            pst = m_connexion_sql.prepareStatement(stm);
            
            // champ pla_nom
            pst.setString(1, this.m_pla_nom);
            // champ pla_notes
            pst.setString(2, this.m_pla_notes);
            
            // champ fap_id_famille_plante
           	pst.setInt(3, this.m_fap_id_famille_plante);
            
            // champs booleens
           	pst.setBoolean(4, this.m_pla_semis_ext_jan);
			pst.setBoolean(5, this.m_pla_semis_ext_fev);
			pst.setBoolean(6, this.m_pla_semis_ext_mar);
			pst.setBoolean(7, this.m_pla_semis_ext_avr);
			pst.setBoolean(8, this.m_pla_semis_ext_mai);
			pst.setBoolean(9, this.m_pla_semis_ext_jun);
			pst.setBoolean(10, this.m_pla_semis_ext_jul);
			pst.setBoolean(11, this.m_pla_semis_ext_aou);
			pst.setBoolean(12, this.m_pla_semis_ext_sep);
			pst.setBoolean(13, this.m_pla_semis_ext_oct);
			pst.setBoolean(14, this.m_pla_semis_ext_nov);
			pst.setBoolean(15, this.m_pla_semis_ext_dec);

			pst.setBoolean(16, this.m_pla_semis_ser_jan);
			pst.setBoolean(17, this.m_pla_semis_ser_fev);
			pst.setBoolean(18, this.m_pla_semis_ser_mar);
			pst.setBoolean(19, this.m_pla_semis_ser_avr);
			pst.setBoolean(20, this.m_pla_semis_ser_mai);
			pst.setBoolean(21, this.m_pla_semis_ser_jun);
			pst.setBoolean(22, this.m_pla_semis_ser_jul);
			pst.setBoolean(23, this.m_pla_semis_ser_aou);
			pst.setBoolean(24, this.m_pla_semis_ser_sep);
			pst.setBoolean(25, this.m_pla_semis_ser_oct);
			pst.setBoolean(26, this.m_pla_semis_ser_nov);
			pst.setBoolean(27, this.m_pla_semis_ser_dec);
			pst.setInt(28, this.m_pla_id);
            
            pst.executeUpdate();   
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_update('" + this.m_pla_nom + "') -> " + sqle.getMessage();
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

			String stm = "SELECT "+
					"pla_id,"+
					"pla_nom,"+
					"pla_notes,"+
					"fap_id_famille_plante," +
					"pla_semis_ext_jan,"+
					"pla_semis_ext_fev,"+
					"pla_semis_ext_mar,"+
					"pla_semis_ext_avr,"+
					"pla_semis_ext_mai,"+
					"pla_semis_ext_jun,"+
					"pla_semis_ext_jul,"+
					"pla_semis_ext_aou,"+
					"pla_semis_ext_sep,"+
					"pla_semis_ext_oct,"+
					"pla_semis_ext_nov,"+
					"pla_semis_ext_dec,"+

					"pla_semis_ser_jan,"+
					"pla_semis_ser_fev,"+
					"pla_semis_ser_mar,"+
					"pla_semis_ser_avr,"+
					"pla_semis_ser_mai,"+
					"pla_semis_ser_jun,"+
					"pla_semis_ser_jul,"+
					"pla_semis_ser_aou,"+
					"pla_semis_ser_sep,"+
					"pla_semis_ser_oct,"+
					"pla_semis_ser_nov,"+
					"pla_semis_ser_dec"+
					" FROM plante WHERE pla_id=(?)";
			
			pst = m_connexion_sql.prepareStatement(stm,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
			pst.setInt(1, m_pla_id); 
			ResultSet rs = pst.executeQuery();
			int rowCount = rs.last() ? rs.getRow() : 0;

			if (rowCount > 0)
			{
				// On a lu la plante
				this.m_pla_id  = rs.getInt(1);
				this.m_pla_nom = rs.getString(2);
				this.m_pla_notes= rs.getString(3);
				this.m_fap_id_famille_plante = rs.getInt(4);

				this.m_pla_semis_ext_jan = rs.getBoolean(5);
				this.m_pla_semis_ext_fev = rs.getBoolean(6);
				this.m_pla_semis_ext_mar = rs.getBoolean(7);
				this.m_pla_semis_ext_avr = rs.getBoolean(8);
				this.m_pla_semis_ext_mai = rs.getBoolean(9);
				this.m_pla_semis_ext_jun = rs.getBoolean(10);
				this.m_pla_semis_ext_jul = rs.getBoolean(11);
				this.m_pla_semis_ext_aou = rs.getBoolean(12);
				this.m_pla_semis_ext_sep = rs.getBoolean(13);
				this.m_pla_semis_ext_oct = rs.getBoolean(14);
				this.m_pla_semis_ext_nov = rs.getBoolean(15);
				this.m_pla_semis_ext_dec = rs.getBoolean(16);

				this.m_pla_semis_ser_jan = rs.getBoolean(17);
				this.m_pla_semis_ser_fev = rs.getBoolean(18);
				this.m_pla_semis_ser_mar = rs.getBoolean(19);
				this.m_pla_semis_ser_avr = rs.getBoolean(20);
				this.m_pla_semis_ser_mai = rs.getBoolean(21);
				this.m_pla_semis_ser_jun = rs.getBoolean(22);
				this.m_pla_semis_ser_jul = rs.getBoolean(23);
				this.m_pla_semis_ser_aou = rs.getBoolean(24);
				this.m_pla_semis_ser_sep = rs.getBoolean(25);
				this.m_pla_semis_ser_oct = rs.getBoolean(26);
				this.m_pla_semis_ser_nov = rs.getBoolean(27);
				this.m_pla_semis_ser_dec = rs.getBoolean(28);
			}
			else
			{
				// Si on ne trouve pas la plante demandÃ©e
				this.init();
			}  
			rs.close();
			pst.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_select() [pla_id="+m_pla_id+"]-> "+sqle.getMessage();
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
	public void bd_delete()
	{
		try 
		{
			PreparedStatement pst = null;
			String stm = "DELETE FROM plante where pla_id=?";
            pst = m_connexion_sql.prepareStatement(stm);
            pst.setInt(1, this.m_pla_id);                    
            pst.executeUpdate();
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_delete(" + this.m_pla_id + ") -> " + sqle.getMessage();
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
    public String toString()
    {
        return this.m_pla_nom;
    }


	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int bd_get_stock_actuel_nb_graines(long p_date_peremption)
	{
		return CStockNbGraines.bd_get_stock_actuel(this.m_pla_id, p_date_peremption);
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int bd_get_stock_actuel_nb_plants(long p_date_peremption)
	{
		return CStockNbPlants.bd_get_stock_actuel(this.m_pla_id, p_date_peremption);
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int bd_get_stock_actuel_nb_lots(int p_lot_id, long p_date_peremption)
	{
		return CStockLot.bd_get_stock_actuel(this.m_pla_id, p_lot_id, p_date_peremption);
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public int bd_get_stock_actuel_poids_g(long p_date_peremption)
	{
		return CStockPoids.bd_get_stock_actuel(this.m_pla_id,p_date_peremption);
	}

	//******************************************************************************************
	//
	// MOUVEMENT SORTIE
	// NOMBRE DE GRAINES
	//
	//******************************************************************************************
	public boolean bd_stock_mvt_sortie_nb_graines_possible(int p_nb_graines_mvt_sortie,long p_date_peremption) 
	{
        int l_stock = CStockNbGraines.bd_get_stock_actuel(this.m_pla_id,p_date_peremption);   
        
        if (l_stock == CStock.STOCK_INDETERMINE)
        {
            JOptionPane.showMessageDialog(null,"Aucun inventaire saisi en nombre de graines pour cette plante. Stock indéterminé.");
			return false;        	
        }
        else
        {
	        if (l_stock >= p_nb_graines_mvt_sortie)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
        }
	}
	
	//******************************************************************************************
	//
	// MOUVEMENT SORTIE
	// NOMBRE DE LOTS
	//
	//******************************************************************************************
	public boolean bd_stock_mvt_sortie_nb_lots_possible(int p_nb_lots_mvt_sortie, long p_date_peremption, int p_lot_id) 
	{
		//int l_stock = CStockLot.bd_get_stock_actuel(this.m_pla_id, p_date_peremption, p_lot_id);   
		//TODO: à tester
        int l_stock = bd_lit_stock_plante_nb_lots_actuel(p_date_peremption, p_lot_id);
        
        if (l_stock == CStock.STOCK_INDETERMINE)
        {
            JOptionPane.showMessageDialog(null,"Aucun inventaire saisi en nombre de lots pour cette plante. Stock indéterminé.");
			return false;        	
        }
        else
        {
        	if (l_stock >= p_nb_lots_mvt_sortie)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
        }
	}

	//******************************************************************************************
	//
	// MOUVEMENT SORTIE
	// NOMBRE DE PLANTS
	//
	//******************************************************************************************
	public boolean bd_stock_mvt_sortie_nb_plants_possible(int p_nb_plants_mvt_sortie, long p_date_peremption) 
	{
        int l_stock = CStockNbPlants.bd_get_stock_actuel(this.m_pla_id, p_date_peremption);   
        
        if (l_stock == CStock.STOCK_INDETERMINE)
        {
            JOptionPane.showMessageDialog(null,"Aucun inventaire saisi en nombre de plants pour cette plante. Stock indéterminé.");
			return false;        	
        }
        else
        {
	        if (l_stock >= p_nb_plants_mvt_sortie)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
        }
	}

	//******************************************************************************************
	//
	// MOUVEMENT SORTIE
	// POIDS EN GRAMMES
	//
	//******************************************************************************************
	public boolean bd_stock_mvt_sortie_poids_g_possible(int p_poids_g_mvt_sortie,long p_date_peremption) 
	{
        //JOptionPane.showMessageDialog(null,"DEBUG - CPlante.bd_stock_mvt_entree_poids_g_possible A");			

        int l_stock = CStockPoids.bd_get_stock_actuel(this.m_pla_id, p_date_peremption);   
        //JOptionPane.showMessageDialog(null,"DEBUG - CPlante.bd_stock_mvt_entree_poids_g_possible B");			
        
        if (l_stock == CStock.STOCK_INDETERMINE)
        {
            JOptionPane.showMessageDialog(null,"Aucun inventaire saisi en poids en grammes pour cette plante. Stock indéterminé.");
			return false;        	
        }
        else
        {
	        if (l_stock >= p_poids_g_mvt_sortie)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
        }
	}

	//******************************************************************************************
	//
	// MOUVEMENT ENTREE
	// NOMBRE DE GRAINES
	//
	//******************************************************************************************
	public boolean bd_stock_mvt_entree_nb_graines_possible(int p_nb_graines_mvt_entree,long p_date_peremption) 
	{
        int l_stock = CStockNbGraines.bd_get_stock_actuel(this.m_pla_id, p_date_peremption);   
        
        if (l_stock == CStock.STOCK_INDETERMINE)
        {
            JOptionPane.showMessageDialog(null,"Aucun inventaire saisi en nombre de graines pour cette plante. Stock indéterminé.");
			return false;        	
        }
        else
        {
        	return true;
        }
	}

	//******************************************************************************************
	//
	// MOUVEMENT ENTREE
	// NOMBRE DE LOTS
	//
	//******************************************************************************************
	public boolean bd_stock_mvt_entree_nb_lots_possible(int p_nb_lots_mvt_entree, long p_date_peremption, int p_lot_id) 
	{
		// TODO: ajouter le  type de lots au contrôle (ici on ne fait que le nombre comme si c'étaient tous les mêmes lots)
        int l_stock = CStockLot.bd_get_stock_actuel(this.m_pla_id, p_lot_id, p_date_peremption);
       
        if (l_stock == CStock.STOCK_INDETERMINE)
        {
            JOptionPane.showMessageDialog(null,"Aucun inventaire saisi en nombre de lots pour cette plante. Stock indéterminé.");
			return false;        	
        }
        else
        {
        	return true;
        }
	}

	//******************************************************************************************
	//
	// MOUVEMENT ENTREE
	// NOMBRE DE PLANTS
	//
	//******************************************************************************************
	public boolean bd_stock_mvt_entree_nb_plants_possible(int p_nb_plants_mvt_entree, long p_date_peremption) 
	{
        int l_stock = CStockNbPlants.bd_get_stock_actuel(this.m_pla_id, p_date_peremption);   
        
        if (l_stock == CStock.STOCK_INDETERMINE)
        {
            JOptionPane.showMessageDialog(null,"Aucun inventaire saisi en nombre de plants pour cette plante. Stock indéterminé.");
			return false;        	
        }
        else
        {
        	return true;
        }
	}

	//******************************************************************************************
	//
	// MOUVEMENT ENTREE
	// POIDS EN GRAMMES
	//
	//******************************************************************************************
	public boolean bd_stock_mvt_entree_poids_g_possible(int p_poids_g_mvt_entree,long p_date_peremption) 
	{
        JOptionPane.showMessageDialog(null,"DEBUG - CPlante.bd_stock_mvt_entree_poids_g_possible A");			

        int l_stock = CStockPoids.bd_get_stock_actuel(this.m_pla_id, p_date_peremption);   
        JOptionPane.showMessageDialog(null,"DEBUG - CPlante.bd_stock_mvt_entree_poids_g_possible B");			
        
        if (l_stock == CStock.STOCK_INDETERMINE)
        {
            JOptionPane.showMessageDialog(null,"Aucun inventaire saisi en poids en grammes pour cette plante. Stock indéterminé.");
			return false;        	
        }
        else
        {
        	return true;
        }
	}

	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_nb_graines_ok(long p_date_evaluation)
	{
		return bd_lit_stock_plante_nb_graines(p_date_evaluation,false);
	}
	
	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_nb_graines_ko(long p_date_evaluation) 
	{
		return bd_lit_stock_plante_nb_graines(p_date_evaluation,true);
	}

	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_nb_graines(long p_date_evaluation, boolean p_perime) 
	{
		int  l_resultat       = 0;
		int  l_qte_inventaire = 0;
		int  l_nb_entrees     = 0;
		int  l_nb_sorties     = 0;
		int  l_pla_id;
		long l_date_inventaire;
		long l_date_peremption;
		
		// Renvoie la liste des derniers inventaires en date en nb de graines --
		
		String l_chaine_sql = 
		
		"SELECT i1.inv_id, i1.pla_id_plante, i1.inv_date, i1.inv_nb_graines, i1.inv_dperemption "+
		"FROM inventaire i1 "+
		"INNER JOIN ("+
		"SELECT pla_id_plante, max(inv_date) as maxdate , inv_dperemption "+
		"FROM inventaire "+
		"WHERE  inv_nb_graines IS NOT NULL "+
		"GROUP BY inv_dperemption, pla_id_plante "+
		") i2 "+
		"ON i1.pla_id_plante = i2.pla_id_plante "+
		"AND i1.inv_date     = i2.maxdate "+
		"AND i1.inv_dperemption  = i2.inv_dperemption "+
		"WHERE i1.inv_nb_graines IS NOT NULL "+
		"AND i1.pla_id_plante="+this.m_pla_id+";"; 
		
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
			
			while (rs.next())
			{
				l_qte_inventaire  = rs.getInt(4);
				l_pla_id          = rs.getInt(2); 
				l_date_inventaire = rs.getLong(3);
				l_date_peremption = rs.getLong(5);
				
				
				if (p_perime == false)
				{
					// Si on comptabilise les graines non périmées / OK
					
					if (l_date_peremption > p_date_evaluation)
					{
						// Si non périmé, on ajoute au résultat
						
						// Pour chaque inventaire, récupérer les entrées et sorties de stock de la même plante à la même date de péremption
						l_nb_entrees = CPlante.bd_get_entrees_stock_nb_graines_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_nb_sorties = CPlante.bd_get_sorties_stock_nb_graines_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_resultat += (l_qte_inventaire + l_nb_entrees - l_nb_sorties);
					}
				}
				else
				{
					// Si on comptabilise les graines périmées / KO
					if (l_date_peremption <= p_date_evaluation)
					{
						// Si périmé, on ajoute au résultat
						
						// Pour chaque inventaire, récupérer les entrées et sorties de stock de la même plante à la même date de péremption
						l_nb_entrees = CPlante.bd_get_entrees_stock_nb_graines_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_nb_sorties = CPlante.bd_get_sorties_stock_nb_graines_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_resultat += (l_qte_inventaire + l_nb_entrees - l_nb_sorties);
					}
				}
			}
			rs.close();
			st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_lit_stock_plante_nb_graines() [pla_id="+m_pla_id+",p_date_evaluation="+p_date_evaluation+",p_perime="+p_perime+"]-> "+sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		
		return l_resultat;
	}
	

	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_nb_plants_ko(long p_date_evaluation) 
	{
		return bd_lit_stock_plante_nb_plants(p_date_evaluation, true);
	}

	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_nb_plants_ok(long p_date_evaluation) 
	{
		return bd_lit_stock_plante_nb_plants(p_date_evaluation,false);
	}

	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_nb_plants(long p_date_evaluation, boolean p_perime) 
	{
		int  l_resultat       = 0;
		int  l_qte_inventaire = 0;
		int  l_nb_entrees     = 0;
		int  l_nb_sorties     = 0;
		int  l_pla_id;
		long l_date_inventaire;
		long l_date_peremption;
		
		// Renvoie la liste des derniers inventaires en date en nb de plants --
		
		String l_chaine_sql = 
		
		"SELECT i1.inv_id, i1.pla_id_plante, i1.inv_date, i1.inv_nb_plants, i1.inv_dperemption "+
		"FROM inventaire i1 "+
		"INNER JOIN ("+
		"SELECT pla_id_plante, max(inv_date) as maxdate , inv_dperemption "+
		"FROM inventaire "+
		"WHERE  inv_nb_plants IS NOT NULL "+
		"GROUP BY inv_dperemption, pla_id_plante "+
		") i2 "+
		"ON i1.pla_id_plante = i2.pla_id_plante "+
		"AND i1.inv_date     = i2.maxdate "+
		"AND i1.inv_dperemption  = i2.inv_dperemption "+
		"WHERE i1.inv_nb_plants IS NOT NULL "+
		"AND i1.pla_id_plante="+this.m_pla_id+";"; 
		
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
			
			while (rs.next())
			{
				l_qte_inventaire  = rs.getInt(4);
				l_pla_id          = rs.getInt(2); 
				l_date_inventaire = rs.getLong(3);
				l_date_peremption = rs.getLong(5);
				
				
				if (p_perime == false)
				{
					// Si on comptabilise les graines non périmées / OK
					
					if (l_date_peremption > p_date_evaluation)
					{
						// Si non périmé, on ajoute au résultat
						
						// Pour chaque inventaire, récupérer les entrées et sorties de stock de la même plante à la même date de péremption
						l_nb_entrees = CPlante.bd_get_entrees_stock_nb_plants_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_nb_sorties = CPlante.bd_get_sorties_stock_nb_plants_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_resultat += (l_qte_inventaire + l_nb_entrees - l_nb_sorties);
					}
				}
				else
				{
					// Si on comptabilise les graines périmées / KO
					if (l_date_peremption <= p_date_evaluation)
					{
						// Si périmé, on ajoute au résultat
						
						// Pour chaque inventaire, récupérer les entrées et sorties de stock de la même plante à la même date de péremption
						l_nb_entrees = CPlante.bd_get_entrees_stock_nb_plants_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_nb_sorties = CPlante.bd_get_sorties_stock_nb_plants_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_resultat += (l_qte_inventaire + l_nb_entrees - l_nb_sorties);
					}
				}
			}
			rs.close();
			st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_lit_stock_plante_nb_plants() [pla_id="+m_pla_id+",p_date_evaluation="+p_date_evaluation+",p_perime="+p_perime+"]-> "+sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		
		return l_resultat;
	}


	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_poids_g_ok(long p_date_evaluation) 
	{
		return bd_lit_stock_plante_poids_g(p_date_evaluation,false);
	}

	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_poids_g_ko(long p_date_evaluation) 
	{
		return bd_lit_stock_plante_poids_g(p_date_evaluation,true);
	}

	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_poids_g(long p_date_evaluation, boolean p_perime) 
	{
		int  l_resultat       = 0;
		int  l_qte_inventaire = 0;
		int  l_nb_entrees     = 0;
		int  l_nb_sorties     = 0;
		int  l_pla_id;
		long l_date_inventaire;
		long l_date_peremption;
		
		// Renvoie la liste des derniers inventaires en date en poids en grammes --
		
		String l_chaine_sql = 
		
		"SELECT i1.inv_id, i1.pla_id_plante, i1.inv_date, i1.inv_poids_g, i1.inv_dperemption "+
		"FROM inventaire i1 "+
		"INNER JOIN ("+
		"SELECT pla_id_plante, max(inv_date) as maxdate , inv_dperemption "+
		"FROM inventaire "+
		"WHERE  inv_poids_g IS NOT NULL "+
		"GROUP BY inv_dperemption, pla_id_plante "+
		") i2 "+
		"ON i1.pla_id_plante = i2.pla_id_plante "+
		"AND i1.inv_date     = i2.maxdate "+
		"AND i1.inv_dperemption  = i2.inv_dperemption "+
		"WHERE i1.inv_poids_g IS NOT NULL "+
		"AND i1.pla_id_plante="+this.m_pla_id+";"; 
		
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
			
			while (rs.next())
			{
				l_qte_inventaire  = rs.getInt(4);
				l_pla_id          = rs.getInt(2); 
				l_date_inventaire = rs.getLong(3);
				l_date_peremption = rs.getLong(5);
				
				
				if (p_perime == false)
				{
					// Si on comptabilise les graines non périmées / OK
					
					if (l_date_peremption > p_date_evaluation)
					{
						// Si non périmé, on ajoute au résultat
						
						// Pour chaque inventaire, récupérer les entrées et sorties de stock de la même plante à la même date de péremption
						l_nb_entrees = CPlante.bd_get_entrees_stock_poids_g_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_nb_sorties = CPlante.bd_get_sorties_stock_poids_g_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_resultat += (l_qte_inventaire + l_nb_entrees - l_nb_sorties);
					}
				}
				else
				{
					// Si on comptabilise les graines périmées / KO
					if (l_date_peremption <= p_date_evaluation)
					{
						// Si périmé, on ajoute au résultat
						
						// Pour chaque inventaire, récupérer les entrées et sorties de stock de la même plante à la même date de péremption
						l_nb_entrees = CPlante.bd_get_entrees_stock_poids_g_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_nb_sorties = CPlante.bd_get_sorties_stock_poids_g_a_dperemption_apres_dernier_inventaire(l_pla_id,l_date_peremption,l_date_inventaire);
						l_resultat += (l_qte_inventaire + l_nb_entrees - l_nb_sorties);
					}
				}
			}
			rs.close();
			st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_lit_stock_plante_poids_g() [pla_id="+m_pla_id+",p_date_evaluation="+p_date_evaluation+",p_perime="+p_perime+"]-> "+sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		
		return l_resultat;
	}

	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_nb_lots_ok(long p_date_evaluation) 
	{
		return bd_lit_stock_plante_nb_lots(p_date_evaluation,false);
	}

	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_nb_lots_ko(long p_date_evaluation) 
	{
		return bd_lit_stock_plante_nb_lots(p_date_evaluation,true);
	}
		
	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	private int bd_lit_stock_plante_nb_lots(long p_date_evaluation, boolean p_perime) 
	{
		int  l_resultat       = 0;
		int  l_qte_inventaire = 0;
		int  l_nb_entrees     = 0;
		int  l_nb_sorties     = 0;
		int  l_pla_id;
		int  l_lot_id;
		long l_date_inventaire;
		long l_date_peremption;
		
		// Renvoie la liste des derniers inventaires en date en nb de lots --
		
		String l_chaine_sql = 
		
		"SELECT i1.inv_id, i1.pla_id_plante, i1.inv_date, i1.inv_nb_lot, i1.inv_dperemption, i1.lot_id_lot_plante "+
		"FROM inventaire i1 "+
		"INNER JOIN ("+
		"SELECT pla_id_plante, max(inv_date) as maxdate , inv_dperemption, lot_id_lot_plante "+
		"FROM inventaire "+
		"WHERE  lot_id_lot_plante IS NOT NULL "+
		"GROUP BY inv_dperemption, pla_id_plante, lot_id_lot_plante "+
		") i2 "+
		"ON i1.pla_id_plante = i2.pla_id_plante "+
		"AND i1.lot_id_lot_plante=i2.lot_id_lot_plante "+
		"AND i1.inv_date     = i2.maxdate "+
		"AND i1.inv_dperemption  = i2.inv_dperemption "+
		"WHERE i1.inv_nb_lot IS NOT NULL "+
		"AND i1.pla_id_plante="+this.m_pla_id+";"; 
		
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
			
			while (rs.next())
			{
				l_qte_inventaire  = rs.getInt(4);
				l_pla_id          = rs.getInt(2); 
				l_date_inventaire = rs.getLong(3);
				l_date_peremption = rs.getLong(5);
				l_lot_id          = rs.getInt(6);
				
				if (p_perime == false)
				{
					// Si on comptabilise les graines non périmées / OK
					
					if (l_date_peremption > p_date_evaluation)
					{
						// Si non périmé, on ajoute au résultat
						
						// Pour chaque inventaire, récupérer les entrées et sorties de stock de la même plante à la même date de péremption
						l_nb_entrees = CPlante.bd_get_entrees_stock_nb_lots_a_dperemption_apres_dernier_inventaire(l_pla_id,l_lot_id,l_date_peremption,l_date_inventaire);
						l_nb_sorties = CPlante.bd_get_sorties_stock_nb_lots_a_dperemption_apres_dernier_inventaire(l_pla_id,l_lot_id,l_date_peremption,l_date_inventaire);
						l_resultat += (l_qte_inventaire + l_nb_entrees - l_nb_sorties);
					}
				}
				else
				{
					// Si on comptabilise les graines périmées / KO
					if (l_date_peremption <= p_date_evaluation)
					{
						// Si périmé, on ajoute au résultat
						
						// Pour chaque inventaire, récupérer les entrées et sorties de stock de la même plante à la même date de péremption
						l_nb_entrees = CPlante.bd_get_entrees_stock_nb_lots_a_dperemption_apres_dernier_inventaire(l_pla_id,l_lot_id,l_date_peremption,l_date_inventaire);
						l_nb_sorties = CPlante.bd_get_sorties_stock_nb_lots_a_dperemption_apres_dernier_inventaire(l_pla_id,l_lot_id,l_date_peremption,l_date_inventaire);
						l_resultat += (l_qte_inventaire + l_nb_entrees - l_nb_sorties);
					}
				}
			}
			rs.close();
			st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_lit_stock_plante_poids_g() [pla_id="+m_pla_id+",p_date_evaluation="+p_date_evaluation+",p_perime="+p_perime+"]-> "+sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		
		return l_resultat;
	}
	
	//******************************************************************************************
	//
	// 
	// 
	//
	//******************************************************************************************
	public int bd_lit_stock_plante_nb_lots_actuel(long p_date_peremption, int p_lot_id)
	{
		int  l_resultat       = 0;
		int  l_qte_inventaire = 0;
		int  l_nb_entrees     = 0;
		int  l_nb_sorties     = 0;
		int  l_pla_id;
		int  l_lot_id;
		long l_date_inventaire;
		long l_date_peremption;
		
		// Renvoie la liste des derniers inventaires en date en nb de lots --
		
		String l_chaine_sql = 
		
		"SELECT i1.inv_id, i1.pla_id_plante, i1.inv_date, i1.inv_nb_lot, i1.inv_dperemption, i1.lot_id_lot_plante " +
		"FROM inventaire i1 " +
		"INNER JOIN (" +
		"SELECT pla_id_plante, max(inv_date) as maxdate , inv_dperemption, lot_id_lot_plante " +
		"FROM inventaire " +
		"WHERE  lot_id_lot_plante =" + p_lot_id +
		" GROUP BY inv_dperemption, pla_id_plante, lot_id_lot_plante " +
		") i2 "+
		"ON i1.pla_id_plante = i2.pla_id_plante " +
		"AND i1.inv_dperemption=" + p_date_peremption +
		" AND i1.lot_id_lot_plante=i2.lot_id_lot_plante " +
		"AND i1.inv_date     = i2.maxdate "+
		"AND i1.inv_dperemption  = i2.inv_dperemption " +
		"WHERE i1.inv_nb_lot IS NOT NULL " +
		"AND i1.pla_id_plante=" + this.m_pla_id + ";"; 
		
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
			
			while (rs.next())
			{
				l_qte_inventaire  = rs.getInt(4);
				l_pla_id          = rs.getInt(2); 
				l_date_inventaire = rs.getLong(3);
				l_date_peremption = rs.getLong(5);
				l_lot_id          = rs.getInt(6);
				
				// Pour chaque inventaire, récupérer les entrées et sorties de stock de la même plante à la même date de péremption
				l_nb_entrees = CPlante.bd_get_entrees_stock_nb_lots_a_dperemption_apres_dernier_inventaire(l_pla_id,l_lot_id,l_date_peremption,l_date_inventaire);
				l_nb_sorties = CPlante.bd_get_sorties_stock_nb_lots_a_dperemption_apres_dernier_inventaire(l_pla_id,l_lot_id,l_date_peremption,l_date_inventaire);
				l_resultat += (l_qte_inventaire + l_nb_entrees - l_nb_sorties);
			}
			rs.close();
			st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_lit_stock_plante_nb_lots_actuel() [pla_id="+m_pla_id+",p_date_peremption="+p_date_peremption+"]-> "+sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.err.println(l_chaine_sql);
		    System.exit(1);
		}
		return l_resultat;
	}


	//*******************************************************************************************************
	//
	// Renvoie la somme des entrées en stock à la même date de péremption pour la plante choisie apres le dernier inventaire 
	//
	//-------------------------------------------------------------------------------------------------------
	static public int bd_get_entrees_stock_nb_graines_a_dperemption_apres_dernier_inventaire(int p_pla_id, long p_date_peremption, long p_date_dernier_inventaire) 
	{
		int l_resultat = -1;
		
		String l_chaine_sql= 
	    		"SELECT SUM(mvt_nb_graines) " +
	    		"FROM mouvement " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// les mouvements en nb_de_graines
	    		" AND mvt_nb_graines is not null" +
	    		// Après le dernier inventaire
	    		" AND mvt_date >= " + p_date_dernier_inventaire+
	    		// Pour la même date de péremption
	    		" AND mvt_dperemption=" + p_date_peremption+
	    		// Pour des entrees en stock
	    		" AND mvt_sens='"+CSensMouvement.MOUVEMENT_AJOUT_STOCK()+"'";
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur 
		    	l_resultat = rs.getInt(1);
		    }
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CPlante.bd_get_entrees_stock_nb_graines_a_dperemption_apres_dernier_inventaire() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}	
		return l_resultat;
	}
	
	//*******************************************************************************************************
	//
	// Renvoie la somme des sorties de stock à la même date de péremption pour la plante choisie
	//
	//-------------------------------------------------------------------------------------------------------
	static public int bd_get_sorties_stock_nb_graines_a_dperemption_apres_dernier_inventaire(int p_pla_id, long p_date_peremption, long p_date_dernier_inventaire) 
	{
		int l_resultat = -1;
		
		String l_chaine_sql= 
	    		"SELECT SUM(mvt_nb_graines) " +
	    		"FROM mouvement " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// les mouvements en nb_de_graines
	    		" AND mvt_nb_graines is not null" +
	    		// Après le dernier inventaire
	    		" AND mvt_date >= " + p_date_dernier_inventaire+
	    		// Pour la même date de péremption
	    		" AND mvt_dperemption=" + p_date_peremption+
	    		// Pour des sorties de stock
	    		" AND mvt_sens='"+CSensMouvement.MOUVEMENT_RETRAIT_STOCK()+"'";
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur 
		    	l_resultat              = rs.getInt(1);
		    }
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CPlante.bd_get_sorties_stock_nb_graines_a_dperemption_apres_dernier_inventaire() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		
		return l_resultat;
	}
	
	//*******************************************************************************************************
	//
	// Renvoie la somme des entrées en stock à la même date de péremption pour la plante choisie apres le dernier inventaire 
	//
	//-------------------------------------------------------------------------------------------------------
	static public int bd_get_entrees_stock_nb_plants_a_dperemption_apres_dernier_inventaire(int p_pla_id, long p_date_peremption, long p_date_dernier_inventaire) 
	{
		int l_resultat = -1;
		
		String l_chaine_sql= 
	    		"SELECT SUM(mvt_nb_plants) " +
	    		"FROM mouvement " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// les mouvements en nb_de_graines
	    		" AND mvt_nb_plants is not null" +
	    		// Après le dernier inventaire
	    		" AND mvt_date >= " + p_date_dernier_inventaire+
	    		// Pour la même date de péremption
	    		" AND mvt_dperemption=" + p_date_peremption+
	    		// Pour des entrees en stock
	    		" AND mvt_sens='"+CSensMouvement.MOUVEMENT_AJOUT_STOCK()+"'";
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur 
		    	l_resultat = rs.getInt(1);
		    }
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CPlante.bd_get_entrees_stock_nb_plants_a_dperemption_apres_dernier_inventaire() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}	
		return l_resultat;
	}
	
	//*******************************************************************************************************
	//
	// Renvoie la somme des sorties de stock à la même date de péremption pour la plante choisie
	//
	//-------------------------------------------------------------------------------------------------------
	static public int bd_get_sorties_stock_nb_plants_a_dperemption_apres_dernier_inventaire(int p_pla_id, long p_date_peremption, long p_date_dernier_inventaire) 
	{
		int l_resultat = -1;
		
		String l_chaine_sql= 
	    		"SELECT SUM(mvt_nb_plants) " +
	    		"FROM mouvement " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// les mouvements en nb_de_graines
	    		" AND mvt_nb_plants is not null" +
	    		// Après le dernier inventaire
	    		" AND mvt_date >= " + p_date_dernier_inventaire+
	    		// Pour la même date de péremption
	    		" AND mvt_dperemption=" + p_date_peremption+
	    		// Pour des sorties de stock
	    		" AND mvt_sens='"+CSensMouvement.MOUVEMENT_RETRAIT_STOCK()+"'";
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur 
		    	l_resultat              = rs.getInt(1);
		    }
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CPlante.bd_get_sorties_stock_nb_plants_a_dperemption_apres_dernier_inventaire() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		
		return l_resultat;
	}
	
	//*******************************************************************************************************
	//
	// Renvoie la somme des entrées en stock à la même date de péremption pour la plante choisie apres le dernier inventaire 
	//
	//-------------------------------------------------------------------------------------------------------
	static public int bd_get_entrees_stock_poids_g_a_dperemption_apres_dernier_inventaire(int p_pla_id, long p_date_peremption, long p_date_dernier_inventaire) 
	{
		int l_resultat = -1;
		
		String l_chaine_sql= 
	    		"SELECT SUM(mvt_poids_g) " +
	    		"FROM mouvement " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// les mouvements en nb_de_graines
	    		" AND mvt_poids_g is not null" +
	    		// Après le dernier inventaire
	    		" AND mvt_date >= " + p_date_dernier_inventaire+
	    		// Pour la même date de péremption
	    		" AND mvt_dperemption=" + p_date_peremption+
	    		// Pour des entrees en stock
	    		" AND mvt_sens='"+CSensMouvement.MOUVEMENT_AJOUT_STOCK()+"'";
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur 
		    	l_resultat = rs.getInt(1);
		    }
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CPlante.bd_get_entrees_stock_poids_g_a_dperemption_apres_dernier_inventaire() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}	
		return l_resultat;
	}
	
	//*******************************************************************************************************
	//
	// Renvoie la somme des sorties de stock à la même date de péremption pour la plante choisie
	//
	//-------------------------------------------------------------------------------------------------------
	static public int bd_get_sorties_stock_poids_g_a_dperemption_apres_dernier_inventaire(int p_pla_id, long p_date_peremption, long p_date_dernier_inventaire) 
	{
		int l_resultat = -1;
		
		String l_chaine_sql= 
	    		"SELECT SUM(mvt_poids_g) " +
	    		"FROM mouvement " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// les mouvements en nb_de_graines
	    		" AND mvt_poids_g is not null" +
	    		// Après le dernier inventaire
	    		" AND mvt_date >= " + p_date_dernier_inventaire+
	    		// Pour la même date de péremption
	    		" AND mvt_dperemption=" + p_date_peremption+
	    		// Pour des sorties de stock
	    		" AND mvt_sens='"+CSensMouvement.MOUVEMENT_RETRAIT_STOCK()+"'";
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur 
		    	l_resultat              = rs.getInt(1);
		    }
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CPlante.bd_get_sorties_stock_poids_g_a_dperemption_apres_dernier_inventaire() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		
		return l_resultat;
	}


	//*******************************************************************************************************
	//
	// Renvoie la somme des entrees de stock à la même date de péremption pour la plante choisie
	//
	//-------------------------------------------------------------------------------------------------------
	public static int bd_get_entrees_stock_nb_lots_a_dperemption_apres_dernier_inventaire(int p_pla_id, int p_lot_id, long p_date_peremption, long p_date_dernier_inventaire) 
	{
		int l_resultat = -1;
		
		String l_chaine_sql= 
	    		"SELECT SUM(mvt_nb_lot) " +
	    		"FROM mouvement " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// les mouvements en nb_de_lots
	    		" AND lot_id_lot_plante=" +p_lot_id+
	    		// Après le dernier inventaire
	    		" AND mvt_date >= " + p_date_dernier_inventaire+
	    		// Pour la même date de péremption
	    		" AND mvt_dperemption=" + p_date_peremption+
	    		// Pour des entrees en stock
	    		" AND mvt_sens='"+CSensMouvement.MOUVEMENT_AJOUT_STOCK()+"'";
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur 
		    	l_resultat = rs.getInt(1);
		    }
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CPlante.bd_get_entrees_stock_nb_lots_a_dperemption_apres_dernier_inventaire() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}	
		return l_resultat;
	}
	
	//*******************************************************************************************************
	//
	// Renvoie la somme des sorties de stock à la même date de péremption pour la plante choisie
	//
	//-------------------------------------------------------------------------------------------------------
	public static int bd_get_sorties_stock_nb_lots_a_dperemption_apres_dernier_inventaire(int p_pla_id, int p_lot_id, long p_date_peremption, long p_date_dernier_inventaire) 
	{
		int l_resultat = -1;
		
		String l_chaine_sql= 
	    		"SELECT SUM(mvt_nb_lot) " +
	    		"FROM mouvement " +
	    		" WHERE pla_id_plante = " + p_pla_id+
	    		// les mouvements en nb_de_lots
	    		" AND lot_id_lot_plante=" +p_lot_id+
	    		// Après le dernier inventaire
	    		" AND mvt_date >= " + p_date_dernier_inventaire+
	    		// Pour la même date de péremption
	    		" AND mvt_dperemption=" + p_date_peremption+
	    		// Pour des sorties de stock
	    		" AND mvt_sens='"+CSensMouvement.MOUVEMENT_RETRAIT_STOCK()+"'";
		
		// Execution de la requête
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery(l_chaine_sql);
		    
		    if (rs.next())
		    {
		    	// On récupère la valeur 
		    	l_resultat              = rs.getInt(1);
		    }
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = "CPlante.bd_get_sorties_stock_nb_lots_a_dperemption_apres_dernier_inventaire() ->"+sqle.getMessage();
			l_message=l_message+"\n"+ l_chaine_sql;
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
		
		return l_resultat;
	}
}

//******************************************************************************************
//******************************************************************************************
//******************************************************************************************
//******************************************************************************************
//******************************************************************************************
//******************************************************************************************
//
// Classe permettant l'affichage d'un CPlante dans une combobox
//
//******************************************************************************************
class CPlanteRenderer extends BasicComboBoxRenderer
{
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

      if (value != null)
      {
      	CPlante item = (CPlante)value;
          setText( item.toString().toUpperCase() );
      }

      if (index == -1)
      {
      	CPlante item = (CPlante)value;
          setText( "" + item.get_pla_id() );
      }

      return this;
  }
}
