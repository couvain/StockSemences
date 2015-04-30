import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
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

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;


public class EcranLot extends EcranBase implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	
	private EcranPrincipal       m_ecran_principal;
	private EcranSemencier       m_ecran_semencier;
	
	private CPlante              m_plante_selectionnee;
	private JPanel               m_contentPane;
	private JTextField           m_textField_plante;
	private JTextField           m_textField_nom_lot;

	private JRadioButton         m_radio_poids_g;
	private JRadioButton         m_radio_nb_graines;
	private JRadioButton         m_radio_nb_plants;
	private JNumberTextField     m_textField_poids_g;
	private JNumberTextField     m_textField_nb_graines;
	private JNumberTextField     m_textField_nb_plants;
	private JLabel               m_lbl_poids_g;
	private JLabel               m_lbl_nb_graines;
	private JLabel               m_lbl_nb_plants;
	private JLabel               m_lbl_plante;
	private JLabel               m_lbl_nom_lot;
	private ButtonGroup          m_radiogroup;
	
	private String POIDS_G = "Poids en grammes";
	private String NB_GRAINES = "Nombre de graines";
	private String NB_PLANTS = "Nombre de plants";

	private Vector<CSemencier> 		m_model_combobox_semencier; // Modele de la combobox semencier
	private JComboBox<CSemencier>   m_comboBox_semencier;
	private Map<Object, String>     m_map_semencier = null;
	
	
	//****************************************************************************************
	//
	//
	//
	//----------------------------------------------------------------------------------------
	public EcranLot() 
	{
		setBounds(10, 10, 500, 500);
		this.setTitle("Ecran Ajout d'un nouveau Lot");
		centreWindow();
		
		m_ecran_semencier = new EcranSemencier();
		m_ecran_semencier.setTitle("Ecran Semencier");	
		
		m_contentPane = new JPanel();
		m_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		m_contentPane.setBackground(SystemColor.window);
		m_contentPane.setLayout(null);
		setContentPane(m_contentPane);
		
		// Textfield "Plante" en lecture seule
		m_textField_plante = new JTextField();
		m_textField_plante.setEditable(false);
		m_textField_plante.setBounds(120, 39, 287, 19);
		m_textField_plante.setFont(new Font("Dialog", Font.BOLD, 12));
		m_contentPane.add(m_textField_plante);
		m_textField_plante.setColumns(10);
		
		m_lbl_plante = new JLabel("Plante");
		m_lbl_plante.setBounds(10, 41, 70, 15);
		m_contentPane.add(m_lbl_plante);
		
		// Textfield "Nom du lot" modifiable
		m_textField_nom_lot = new JTextField();
		m_textField_nom_lot.setEditable(true);
		m_textField_nom_lot.setBounds(120, 69, 287,19);
		m_textField_nom_lot.setFont(new Font("Dialog", Font.BOLD, 12));
		m_contentPane.add(m_textField_nom_lot);
		m_textField_nom_lot.setColumns(10);
		
		m_lbl_nom_lot = new JLabel("Nom du lot");
		m_lbl_nom_lot.setBounds(10, 71, 70, 15);
		m_contentPane.add(m_lbl_nom_lot);
		
		
		// Combobox semencier
		m_map_semencier = new HashMap<Object, String>();
		m_model_combobox_semencier = new Vector<CSemencier>();
		m_comboBox_semencier = new JComboBox<CSemencier>(m_model_combobox_semencier);
		m_comboBox_semencier.setBounds(120, 95, 287, 31);
		m_contentPane.add(m_comboBox_semencier);
		
		m_comboBox_semencier.addItemListener(new ItemListener() 
		{
	        public void itemStateChanged(ItemEvent event) 
	        {
	        	 if (event.getStateChange() == ItemEvent.SELECTED) 
	        	 {
	                 // Evenement de selection d'un element dans la combobox de semencier
	        		 //JComboBox comboBox = (JComboBox)event.getSource();
	        		 //CFamillePlante item = (CFamillePlante)comboBox.getSelectedItem();
	                 //JOptionPane.showMessageDialog(null,"fap_id="+item.get_fap_id()+ " - famille="+item.toString());
	              }
	        }
		}
	    );		
		

		JLabel lblSemencier = new JLabel("Semencier");
		lblSemencier.setBounds(10, 103, 121, 15);
		m_contentPane.add(lblSemencier);
		
		JLabel lblPackaging = new JLabel("Packaging");
		lblPackaging.setBounds(10, 230, 121, 15);
		m_contentPane.add(lblPackaging);

	    // Create the radio buttons.
	    m_radio_poids_g = new JRadioButton(POIDS_G);
	    m_radio_poids_g.setActionCommand(POIDS_G);
	    m_radio_poids_g.setSelected(true);
	    
	    m_radio_nb_graines = new JRadioButton("Nombre de graines");
	    m_radio_nb_graines.setActionCommand("Nombre de graines");
	    
	    m_radio_nb_plants = new JRadioButton("Nombre de plants");
	    m_radio_nb_plants.setActionCommand("Nombre de plants");
	    
	    // Register a listener for the radio buttons.
	    m_radio_poids_g.addActionListener(this);
	    m_radio_nb_graines.addActionListener(this);
	    m_radio_nb_plants.addActionListener(this);

	    // Group the radio buttons.
	    m_radiogroup = new ButtonGroup();
	    
	    m_radiogroup.add(m_radio_poids_g);
	    m_radiogroup.add(m_radio_nb_graines); 
	    m_radiogroup.add(m_radio_nb_plants);
	   
	    JPanel radioPanel = new JPanel(new GridLayout(0, 1));
	    radioPanel.setBackground(SystemColor.window);
	    radioPanel.setBounds(120, 230, 200, 120);
        radioPanel.add(m_radio_poids_g);
        radioPanel.add(m_radio_nb_graines);
        radioPanel.add(m_radio_nb_plants);
        
	    m_contentPane.add(radioPanel, BorderLayout.LINE_START);
		
		m_textField_poids_g = new JNumberTextField();
		m_textField_poids_g.setBounds(350, 240, 80, 19);
		m_contentPane.add(m_textField_poids_g);
		m_textField_poids_g.setColumns(10);

		m_lbl_poids_g = new JLabel("grammes");
		m_lbl_poids_g.setBounds(440, 240, 100, 15);
		m_contentPane.add(m_lbl_poids_g);

		m_textField_nb_graines = new JNumberTextField();
		m_textField_nb_graines.setBounds(350, 280, 80, 19);
		m_contentPane.add(m_textField_nb_graines);
		m_textField_nb_graines.setColumns(10);
		
		m_lbl_nb_graines = new JLabel("graines");
		m_lbl_nb_graines.setBounds(440, 280, 100, 15);
		m_contentPane.add(m_lbl_nb_graines);
		
		m_textField_nb_plants = new JNumberTextField();
		m_textField_nb_plants.setBounds(350, 320, 80, 19);
		m_contentPane.add(m_textField_nb_plants);
		m_textField_nb_plants.setColumns(10);

		m_lbl_nb_plants = new JLabel("plants");
		m_lbl_nb_plants.setBounds(440, 320, 100, 15);
		m_contentPane.add(m_lbl_nb_plants);
		
		//---------------------------------------------------
		// Bouton Enregistrer
		//---------------------------------------------------
		JButton btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnEnregistrerClick();
			}
		});
		btnEnregistrer.setBounds(146, 502, 117, 25);
		m_contentPane.add(btnEnregistrer);

		//---------------------------------------------------
		// Bouton Annuler
		//---------------------------------------------------
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnAnnulerClick();
			}
		});
		btnAnnuler.setBounds(325, 502, 117, 25);
		m_contentPane.add(btnAnnuler);
		
		//---------------------------------------------
		// Bouton "Semencier"
		//---------------------------------------------
		JButton btnSemencier = new JButton("Nouveau Semencier");
		btnSemencier.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnSemencier();
			}
		});
		btnSemencier.setBounds(120, 130, 242, 25);
		m_contentPane.add(btnSemencier);
		
		
		m_textField_poids_g.setVisible(true);
		m_lbl_poids_g.setVisible(true);
		
		m_textField_nb_graines.setVisible(false);
		m_lbl_nb_graines.setVisible(false);
		
		m_textField_nb_plants.setVisible(false);
		m_lbl_nb_plants.setVisible(false);		
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_connexion_sql(Connection p_connexion_sql) 
	{
		super.set_connexion_sql(p_connexion_sql);
		m_ecran_semencier.set_connexion_sql(p_connexion_sql);
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnSemencier() 
	{
		m_ecran_semencier.setVisible(true);
		m_ecran_semencier.set_ecran_lot(this);
		m_ecran_semencier.bd_lance_requete();
	}

	//****************************************************************************************
	//
	//
	//
	//----------------------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand() == POIDS_G)
		{
			m_textField_poids_g.setVisible(true);
			m_lbl_poids_g.setVisible(true);
			
			m_textField_nb_graines.setVisible(false);
			m_lbl_nb_graines.setVisible(false);
			
			m_textField_nb_plants.setVisible(false);
			m_lbl_nb_plants.setVisible(false);
			
		}
		else if (e.getActionCommand() == NB_GRAINES)
		{
			m_textField_poids_g.setVisible(false);
			m_lbl_poids_g.setVisible(false);
			
			m_textField_nb_graines.setVisible(true);
			m_lbl_nb_graines.setVisible(true);
			
			m_textField_nb_plants.setVisible(false);
			m_lbl_nb_plants.setVisible(false);
		}
		else if (e.getActionCommand() == NB_PLANTS)
		{
			m_textField_poids_g.setVisible(false);
			m_lbl_poids_g.setVisible(false);
			
			m_textField_nb_graines.setVisible(false);
			m_lbl_nb_graines.setVisible(false);
			
			m_textField_nb_plants.setVisible(true);
			m_lbl_nb_plants.setVisible(true);
		}
						
	}
	
	//****************************************************************************************
	//
	//
	//
	//----------------------------------------------------------------------------------------
	private void btnEnregistrerClick() 
	{
		CLotPlante l_lot_plante = new CLotPlante();
		boolean l_insertion_possible = true;
		String l_poids_grammes;
		String l_nb_graines;
		String l_nb_plants;
		
		l_lot_plante.set_pla_id_plante(m_plante_selectionnee.get_pla_id());

		if (("".equals(m_textField_nom_lot.getText().trim())) || (m_textField_nom_lot.getText() == null))
		{
			JOptionPane.showMessageDialog(null,"Aucun nom de lot n'a été entré. Saisie obligatoire.");
			l_insertion_possible = false;
		}
		else
		{
			l_lot_plante.set_lot_nom(m_textField_nom_lot.getText().trim());
		}
		
		// Combobox de semencier
        CSemencier item = (CSemencier)m_comboBox_semencier.getSelectedItem();
        
        if (item != null)
        {
            if (item.get_sec_id() != -1)
            {
            	l_lot_plante.set_sec_id_semencier(item.get_sec_id());
            }
            else
            {
            	JOptionPane.showMessageDialog(null,"Aucun semencier n'a ete selectionne (1) - Choix obligatoire.");
            	l_insertion_possible = false;
            }            
        }
        else
        {
        	JOptionPane.showMessageDialog(null,"Aucun semencier n'a ete selectionne (2) - Choix obligatoire.");
        	l_insertion_possible = false;
        }
        
        //----------------------------------------------------------------------------
        if( m_radiogroup.getSelection() == null) 
        {
            // rien de selectionne - cas impossible normalement
        } 
        //----------------------------------------------------------------------------
        else if (m_radiogroup.getSelection() == m_radio_poids_g.getModel()) 
        {
        	// "Poids en grammes" selectionne
    		l_poids_grammes = m_textField_poids_g.getText();
    		l_poids_grammes = l_poids_grammes.trim();
        	if ( l_poids_grammes.length() != 0)
        	{  
        		// JOptionPane.showMessageDialog(null,"poids en grammes ='" + l_poids_grammes + "'  - longueur = "+l_poids_grammes.length());
        		l_lot_plante.set_lot_poids_g(Integer.parseInt(l_poids_grammes));
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(null, "Le poids en grammes n'a pas ete saisi. Saisie obligatoire");
        		l_insertion_possible = false;
        	}
        } 
        //----------------------------------------------------------------------------
        else if (m_radiogroup.getSelection() == m_radio_nb_graines.getModel()) 
        {
        	// "Nombre de graines" selectionne
    		l_nb_graines = m_textField_nb_graines.getText();
    		l_nb_graines = l_nb_graines.trim();
           	if ( l_nb_graines.length() != 0)
        	{
           		l_lot_plante.set_lot_nb_graines(Integer.parseInt(l_nb_graines));
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(null, "Le nombre de graines n'a pas ete saisi. Saisie obligatoire");
        		l_insertion_possible = false;
        	}
        }        
        //----------------------------------------------------------------------------
        else if (m_radiogroup.getSelection() == m_radio_nb_plants.getModel()) 
        {
        	// "Nombre de plants" selectionne
      		l_nb_plants = m_textField_nb_plants.getText();
      		l_nb_plants = l_nb_plants.trim();
      	    if (l_nb_plants.length() != 0)
        	{
      	    	l_lot_plante.set_lot_nb_plants(Integer.parseInt(l_nb_plants));
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(null,"Le nombre de plants n'a pas ete saisi. Saisie obligatoire");
        		l_insertion_possible = false;
        	}
        }        
        //----------------------------------------------------------------------------
        if (l_insertion_possible)
        {
        	l_lot_plante.bd_insert();
            // Fermer la fenetre
            m_ecran_principal.bd_charge_donnees_interface();
    		setVisible(false); 
    		dispose();
        }    

	}	
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnAnnulerClick() 
	{
		setVisible(false); 
		dispose(); 
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

	//****************************************************************************************
	//
	//
	//
	//----------------------------------------------------------------------------------------
	public void bd_charge_donnees_interface(CPlante p_plante_selectionnee) 
	{
		m_plante_selectionnee = p_plante_selectionnee;
		m_textField_plante.setText(m_plante_selectionnee.get_pla_nom());
		bd_charge_combobox_semencier();		
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void bd_charge_combobox_semencier()
	{
		m_model_combobox_semencier.removeAllElements();
		m_map_semencier.clear();
		
		// Premier element de la liste vide
		m_map_semencier.put( new Integer(-1), new String("") );
		m_model_combobox_semencier.addElement(new CSemencier(-1, ""));
    	
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery("SELECT sec_id, sec_nom FROM semencier ORDER BY sec_nom");
		    
		    while (rs.next())
		    {
		    	m_map_semencier.put( new Integer(rs.getInt(1)), new String(rs.getString(2)) );		    	
		    	m_model_combobox_semencier.addElement( new CSemencier(rs.getInt(1), rs.getString(2) ));
		    } 
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_charge_combobox_semencier -> " + sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
	}
}
