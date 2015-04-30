//import java.awt.Dimension;
//import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
//import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;


public class EcranPlante extends EcranBase 
{
	private static final long  		    serialVersionUID = 1L;
	private JPanel               	    m_contentPane;
	protected JTextField           	    m_txtNomDeLaPlante;
	protected JCheckBox[]          	    m_checkboxSerre;
	protected JCheckBox[]          	    m_checkboxExterieur;
	protected JTextArea                 m_textArea_note;
	protected JButton                   m_btnEnregistrer;
	protected JButton                   m_btnAnnuler;
	protected JLabel                    m_lblNomDeLaPlante;
	protected JLabel                    m_lblNomDeLaFamille;
	
	private EcranFamillePlante   	    m_ecran_famille_plante;
	protected EcranPrincipal       	    m_ecran_principal;
	
	protected Vector<CFamillePlante> 	m_model_combobox_famille_plante; // Modele de la combobox famille de plante
	protected JComboBox<CFamillePlante> m_combobox_famille; 
	protected Map<Object, String>  	    m_map_famille_plante = null;  // HashMap (Clé:FAP_ID / Valeur:FAP_NOM)  

	protected CPlante				    m_plante;
	
	public EcranPlante() 
	{
		//setResizable(false);
		setBounds(100,100,600,600);
		//this.getContentPane().setSize(600, 600);
		//this.setPreferredSize(new Dimension(600, 600));
		m_plante = new CPlante();
		
		m_ecran_famille_plante = new EcranFamillePlante();
		m_ecran_famille_plante.setTitle("Ecran Famille de plante");
		
		// JPanel 
		m_contentPane = new JPanel();
		m_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// On se met en coordonnees relatives
		m_contentPane.setLayout(null);
		
		//---------------------------------------------------
		// Bouton Enregistrer
		//---------------------------------------------------
		m_btnEnregistrer = new JButton("Enregistrer");
		m_btnEnregistrer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnEnregistrerClick();
			}
		});
		m_btnEnregistrer.setBounds(146, 537, 117, 25);
		m_contentPane.add(m_btnEnregistrer);

		//---------------------------------------------------
		// Bouton Annuler
		//---------------------------------------------------
		m_btnAnnuler = new JButton("Annuler");
		m_btnAnnuler.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnAnnulerClick();
			}
		});
		m_btnAnnuler.setBounds(325, 537, 117, 25);
		m_contentPane.add(m_btnAnnuler);
		
		
		// Nom de la plante
		m_lblNomDeLaPlante = new JLabel("Nom de la plante");
		m_lblNomDeLaPlante.setBounds(33, 10, 150, 15);
		m_contentPane.add(m_lblNomDeLaPlante);

		m_txtNomDeLaPlante = new JTextField();
		m_txtNomDeLaPlante.setBounds(33, 35, 500, 24);
		m_contentPane.add(m_txtNomDeLaPlante);
		m_txtNomDeLaPlante.setColumns(10);
				
		//--------------------------------------
		// Combobox de famille de plante
		//--------------------------------------
		m_lblNomDeLaFamille = new JLabel("Famille de Plante");
		m_lblNomDeLaFamille.setBounds(33, 70, 150, 15);
		m_contentPane.add(m_lblNomDeLaFamille);
		
		m_map_famille_plante            = new HashMap<Object, String>();
		m_model_combobox_famille_plante = new Vector<CFamillePlante>();
		m_combobox_famille              = new JComboBox<CFamillePlante>(m_model_combobox_famille_plante);
		m_combobox_famille.setBounds(33, 97, 200, 24);
		m_contentPane.add(m_combobox_famille);
		
		m_combobox_famille.addItemListener(new ItemListener() 
		{
	        public void itemStateChanged(ItemEvent event) 
	        {
	        	 if (event.getStateChange() == ItemEvent.SELECTED) 
	        	 {
	                 // Evenement de selection d'un element dans la combobox de Famille de plante
	        		 //JComboBox comboBox = (JComboBox)event.getSource();
	        		 //CFamillePlante item = (CFamillePlante)comboBox.getSelectedItem();
	                 //JOptionPane.showMessageDialog(null,"fap_id="+item.get_fap_id()+ " - famille="+item.toString());
	              }
	        }
		}
	    );

		//---------------------------------------------
		// Bouton "Famille de plante"
		//---------------------------------------------
		JButton btnFamilleDePlante = new JButton("Nouvelle Famille de plante");
		btnFamilleDePlante.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnEcranFamilleDePlanteClick();
			}
		});
		btnFamilleDePlante.setBounds(263, 97, 242, 25);
		m_contentPane.add(btnFamilleDePlante);
		
		//---------------------------------------------
		// Periode de semis
		//---------------------------------------------
		int i;
		int l_pos_x = 100;
		int l_pos_y = 151;
		
		JLabel lblPrioseD = new JLabel("Periode de semis");
		lblPrioseD.setBounds(l_pos_x+58, l_pos_y, 200, 15);
		m_contentPane.add(lblPrioseD);
		
		JLabel lblSemisExterieur = new JLabel("Exterieur");
		lblSemisExterieur.setBounds(33, l_pos_y+35+2, 200, 15);
		m_contentPane.add(lblSemisExterieur);
		
		JLabel lblSemisSerre = new JLabel("Serre");
		lblSemisSerre.setBounds(33, l_pos_y+55+2, 200, 15);
		m_contentPane.add(lblSemisSerre);

		
		JLabel[] lblMoisSemis;
		lblMoisSemis = new JLabel[12];
		for (i = 0; i < 12; i++)
		{
			switch (i)
			{
				case 0:  lblMoisSemis[i] = new JLabel("J"); break;
				case 1:  lblMoisSemis[i] = new JLabel("F"); break;
				case 2:  lblMoisSemis[i] = new JLabel("M"); break;
				case 3:  lblMoisSemis[i] = new JLabel("A"); break;
				case 4:  lblMoisSemis[i] = new JLabel("M"); break;
				case 5:  lblMoisSemis[i] = new JLabel("J"); break;
				case 6:  lblMoisSemis[i] = new JLabel("J"); break;
				case 7:  lblMoisSemis[i] = new JLabel("A"); break;
				case 8:  lblMoisSemis[i] = new JLabel("S"); break;
				case 9:  lblMoisSemis[i] = new JLabel("O"); break;
				case 10: lblMoisSemis[i] = new JLabel("N"); break;
				case 11: lblMoisSemis[i] = new JLabel("D"); break;
			}
			
			lblMoisSemis[i].setBounds(l_pos_x+5+20*i, l_pos_y+15, 20, 20);
			m_contentPane.add(lblMoisSemis[i]);
		}
		
		m_checkboxExterieur = new JCheckBox[12];
		for (i = 0; i < 12; i++)
		{
			m_checkboxExterieur[i] = new JCheckBox("");
			m_checkboxExterieur[i].setBounds(l_pos_x+20*i, l_pos_y+35, 20, 20);
			m_contentPane.add(m_checkboxExterieur[i]);
		}
		
		m_checkboxSerre = new JCheckBox[12];
		for (i = 0; i < 12; i++)
		{
			m_checkboxSerre[i] = new JCheckBox("");
			m_checkboxSerre[i].setBounds(l_pos_x+20*i, l_pos_y+55, 20, 20);
			m_contentPane.add(m_checkboxSerre[i]);
		}
				
		// Zone de notes
		m_textArea_note = new JTextArea();
		m_textArea_note.setLineWrap(true);
		m_textArea_note.setBounds(33, 284, 516, 240);
		m_contentPane.add(m_textArea_note);
		
		JLabel lblNote = new JLabel("Notes");
		lblNote.setBounds(33, 257, 70, 15);
		m_contentPane.add(lblNote);

		setContentPane(m_contentPane);
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void bd_charge_donnees_interface() 
	{
		bd_charge_combobox_famille_de_plante();
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnEcranFamilleDePlanteClick() 
	{
		m_ecran_famille_plante.setVisible(true);
		m_ecran_famille_plante.centreWindow();
		m_ecran_famille_plante.set_ecran_plante(this);
		m_ecran_famille_plante.bd_lance_requete();
	}
	
	//******************************************************************************************
	//
	// renvoie si oui ou non la saisie est valide
	//
	//******************************************************************************************
    protected boolean saisie_est_valide()
    {
        boolean result;
        String l_nom_plante;
        
        l_nom_plante = m_txtNomDeLaPlante.getText().trim();
    	
    	result = true;
    	
        if (l_nom_plante.length() == 0)
        {
        	JOptionPane.showMessageDialog(null,"Nom de plante non renseigne - Saisie obligatoire.");
        	result = false;
        }
        CFamillePlante item = (CFamillePlante)m_combobox_famille.getSelectedItem();
        
        if (item !=null)
        {
            if (item.get_fap_id()==-1)
            {
            	JOptionPane.showMessageDialog(null,"Aucune famille de plante n'a ete selectionnee (1) - Choix obligatoire.");
            	result = false;
            }            
        }
        else
        {
        	JOptionPane.showMessageDialog(null,"Aucune famille de plante n'a ete selectionnee (2) - Choix obligatoire.");
        	result = false;
        }
        
    	return result;
    }

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
    protected void charge_objet_plante_depuis_interface()
    {
        CFamillePlante l_famille_de_plante;
        
       	m_plante.set_pla_nom(m_txtNomDeLaPlante.getText().trim());
        m_plante.set_pla_notes(m_textArea_note.getText().trim());
        l_famille_de_plante = (CFamillePlante)m_combobox_famille.getSelectedItem();
       	m_plante.set_fap_id_famille_plante(l_famille_de_plante.get_fap_id());
		m_plante.set_pla_semis_ext_jan(m_checkboxExterieur[0].isSelected());
		m_plante.set_pla_semis_ext_fev(m_checkboxExterieur[1].isSelected());
		m_plante.set_pla_semis_ext_mar(m_checkboxExterieur[2].isSelected());
		m_plante.set_pla_semis_ext_avr(m_checkboxExterieur[3].isSelected());
		m_plante.set_pla_semis_ext_mai(m_checkboxExterieur[4].isSelected());
		m_plante.set_pla_semis_ext_jun(m_checkboxExterieur[5].isSelected());
		m_plante.set_pla_semis_ext_jul(m_checkboxExterieur[6].isSelected());
		m_plante.set_pla_semis_ext_aou(m_checkboxExterieur[7].isSelected());
		m_plante.set_pla_semis_ext_sep(m_checkboxExterieur[8].isSelected());
		m_plante.set_pla_semis_ext_oct(m_checkboxExterieur[9].isSelected());
		m_plante.set_pla_semis_ext_nov(m_checkboxExterieur[10].isSelected());
		m_plante.set_pla_semis_ext_dec(m_checkboxExterieur[11].isSelected());

		m_plante.set_pla_semis_ser_jan(m_checkboxSerre[0].isSelected());
		m_plante.set_pla_semis_ser_fev(m_checkboxSerre[1].isSelected());
		m_plante.set_pla_semis_ser_mar(m_checkboxSerre[2].isSelected());
		m_plante.set_pla_semis_ser_avr(m_checkboxSerre[3].isSelected());
		m_plante.set_pla_semis_ser_mai(m_checkboxSerre[4].isSelected());
		m_plante.set_pla_semis_ser_jun(m_checkboxSerre[5].isSelected());
		m_plante.set_pla_semis_ser_jul(m_checkboxSerre[6].isSelected());
		m_plante.set_pla_semis_ser_aou(m_checkboxSerre[7].isSelected());
		m_plante.set_pla_semis_ser_sep(m_checkboxSerre[8].isSelected());
		m_plante.set_pla_semis_ser_oct(m_checkboxSerre[9].isSelected());
		m_plante.set_pla_semis_ser_nov(m_checkboxSerre[10].isSelected());
		m_plante.set_pla_semis_ser_dec(m_checkboxSerre[11].isSelected());
    	
    }
    
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnEnregistrerClick() 
	{
		// Ne rien faire (tout est dans les descendants) -> EcranAjoutPlante / EcranModifPlante
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnAnnulerClick() 
	{
		fermer_la_fenetre();
	}
    
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void fermer_la_fenetre()
	{
		setVisible(false); 
		// Détruire l'objet JFrame
		dispose();
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_connexion_sql(Connection p_connexion_sql) 
	{
		super.set_connexion_sql(p_connexion_sql);
		m_ecran_famille_plante.set_connexion_sql(p_connexion_sql);
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void bd_charge_combobox_famille_de_plante()
	{
		m_model_combobox_famille_plante.removeAllElements();
		m_map_famille_plante.clear();
		
		// Premier element de la liste vide
		m_map_famille_plante.put( new Integer(-1), new String("") );
    	m_model_combobox_famille_plante.addElement(new CFamillePlante(-1, ""));
    	
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery("SELECT fap_id, fap_nom FROM famille_plante ORDER BY fap_nom");
		    while (rs.next())
		    {
		    	m_map_famille_plante.put( new Integer(rs.getInt(1)), new String(rs.getString(2)) );		    	
		    	m_model_combobox_famille_plante.addElement( new CFamillePlante(rs.getInt(1), rs.getString(2) ));
		    } 
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_charge_combobox_famille_de_plante -> " + sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public EcranPrincipal get_ecran_principal() 
	{
		return m_ecran_principal;
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void set_ecran_principal(EcranPrincipal m_ecran_principal) 
	{
		this.m_ecran_principal = m_ecran_principal;
	}
}
