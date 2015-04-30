import java.awt.BorderLayout;
import java.awt.Color;
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
//import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;


//****************************************************************
//
// Evenement défini pour le changement de la combobox de plante
//
//****************************************************************
class PlanteChangeListener implements ItemListener
{
	EcranSaisieStock m_ecran_saisie_stock=null;
	
	public void set_ecran_saisie_stock(EcranSaisieStock m_ecran_saisie_stock) 
	{
		this.m_ecran_saisie_stock = m_ecran_saisie_stock;
	}

	// Quand on fait un OnChange sur la combobox de plante
	@Override
	public void itemStateChanged(ItemEvent event)
	{
		
		if (event.getStateChange() == ItemEvent.SELECTED)
		{
			if (m_ecran_saisie_stock != null)
			{
				m_ecran_saisie_stock.recharge_objet_plante();
    			m_ecran_saisie_stock.bd_charge_combobox_lot();		
			}
		}
	}
}

//****************************************************************
//
//Evenement défini pour le changement de la combobox de lot
//
//****************************************************************
class LotChangeListener implements ItemListener
{
	EcranSaisieStock m_ecran_saisie_stock=null;
	
	public void set_ecran_saisie_stock(EcranSaisieStock m_ecran_saisie_stock) 
	{
		this.m_ecran_saisie_stock = m_ecran_saisie_stock;
	}
	

	// Quand on fait un OnChange sur la combobox de lot
	@Override
	public void itemStateChanged(ItemEvent event)
	{
		
		if (event.getStateChange() == ItemEvent.SELECTED)
		{
			
			if (m_ecran_saisie_stock != null)
			{
				m_ecran_saisie_stock.evenement_saisie_lot();
			}
			
		}
	}
	

}



//***********************************************************************************************************
//
//
// Ecran permettant: 
// - Saisie d'inventaire
// - Saisie de mouvement sortant
// - saisie de mouvement entrant
//
//
//************************************************************************************************************
public class EcranSaisieStock  extends EcranBase implements ActionListener
{	
	private static final long       serialVersionUID = 1L;
	
	// Combobox choix de plante
	protected JComboBox<CPlante>    m_combobox_plante; 
	protected Vector<CPlante> 	    m_model_combobox_plante; // Modele de la combobox famille de plante 
	protected Map<Object, String>  	m_map_plante = null;  // HashMap (Clé:FAP_ID / Valeur:FAP_NOM)

	// Combobox choix de lot
	protected JComboBox<CLotPlante> m_combobox_lot; 
	protected Vector<CLotPlante> 	m_model_combobox_lot; // Modele de la combobox lot de plante 
	protected Map<Object, String>  	m_map_lot = null;  // HashMap (Clé:LOT_ID / Valeur:libellé Semencier)
	
	// Date de saisie
	// Nb lots de plante
	// Nb plants
	// Nb de graines
	// Poids en g
	private JRadioButton         m_radio_nb_lots;
	private JRadioButton         m_radio_poids_g;
	private JRadioButton         m_radio_nb_graines;
	private JRadioButton         m_radio_nb_plants;
	private JNumberTextField     m_textField_nb_lots;
	private JNumberTextField     m_textField_poids_g;
	private JNumberTextField     m_textField_nb_graines;
	private JNumberTextField     m_textField_nb_plants;
	
	private JLabel               m_lbl_nb_lots;
	private JLabel               m_lbl_poids_g;
	private JLabel               m_lbl_nb_graines;
	private JLabel               m_lbl_nb_plants;
	private JLabel               m_lbl_plante;
	private JLabel               m_lbl_lot;
	private JLabel               m_lbl_date;
	private JLabel               m_lbl_date_peremption;
	
	private ButtonGroup          m_radiogroup_releve;
    private JPanel               m_radioPanel_releve;

    private JDateChooser         m_date;
    private JDateChooser         m_date_peremption;
	
	private JRadioButton         m_radio_inventaire;
	private JRadioButton         m_radio_mouvement_sortie;
	private JRadioButton         m_radio_mouvement_entree;
	private ButtonGroup          m_radiogroup_type;
    private JPanel               m_radioPanel_type;

	private JPanel               m_contentPane;
	private JButton              m_btnEnregistrerEtSuivant;
	private JButton              m_btnFermer;
	private JButton              m_btnAnnulerDerniereOperation;
	private JLabel               m_lbl_information;
	
	// Objets métier
	private CPlante			     m_plante;
	private CLotPlante           m_lot_plante;
	private int					 m_saisie_nb_graines;
	private int					 m_saisie_nb_plants;
	private int					 m_saisie_nb_lots;
	private int					 m_saisie_poids_g;

	private String NB_LOTS      = "Nombre de lots";
	private String POIDS_G      = "Poids en grammes";
	private String NB_GRAINES   = "Nombre de graines";
	private String NB_PLANTS    = "Nombre de plants";
    
	private String INVENTAIRE   = "Inventaire";
	private String SORTIE_STOCK = "Sortie du stock";
	private String ENTREE_STOCK = "Entrée en stock";

	private EcranPrincipal		m_ecran_principal;
	

	//******************************************************************************************
	//
	// Constructeur
	//
	//------------------------------------------------------------------------------------------
	public EcranSaisieStock()
	{
		this.setBounds(15, 15,700,500);
		this.centreWindow();
		this.setBackground(SystemColor.window);
		//
		m_plante = new CPlante();
		m_lot_plante = new CLotPlante();
		
		// JPanel de contenu global à toute la fenêtre
		m_contentPane = new JPanel();
		m_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		m_contentPane.setBackground(SystemColor.window);
		
		// On se met en coordonnées relatives
		m_contentPane.setLayout(null);
		
		//--------------------
        // Combobox plante
		//--------------------
		m_lbl_plante = new JLabel("Plante");
		m_lbl_plante.setBounds(50, 30, 100, 15);
		m_contentPane.add(m_lbl_plante);

		m_map_plante            = new HashMap<Object, String>();
		m_model_combobox_plante = new Vector<CPlante>();
		m_combobox_plante       = new JComboBox<CPlante>(m_model_combobox_plante);
		m_combobox_plante.setBounds(200, 30, 200, 24);
		
		PlanteChangeListener l_plante_change_listener =new PlanteChangeListener();
		l_plante_change_listener.set_ecran_saisie_stock(this);
		m_combobox_plante.addItemListener(l_plante_change_listener);
		m_contentPane.add(m_combobox_plante);

		// Boutons radio mode de saisie 
		m_radio_inventaire = new JRadioButton(INVENTAIRE);
		m_radio_inventaire.setActionCommand(INVENTAIRE);
		//m_radio_inventaire.setSelected(true);
	    
		m_radio_mouvement_sortie = new JRadioButton(SORTIE_STOCK);
		m_radio_mouvement_sortie.setActionCommand(SORTIE_STOCK);
	    
		m_radio_mouvement_entree = new JRadioButton(ENTREE_STOCK);
		m_radio_mouvement_entree.setActionCommand(ENTREE_STOCK);
		
	    //Register a listener for the radio buttons.
		m_radio_inventaire.addActionListener(this);
		m_radio_mouvement_sortie.addActionListener(this);
		m_radio_mouvement_entree.addActionListener(this);
	    
	    // Group the radio buttons.
	    m_radiogroup_type = new ButtonGroup();
	    m_radiogroup_type.add(m_radio_inventaire);
	    m_radiogroup_type.add(m_radio_mouvement_sortie); 
	    m_radiogroup_type.add(m_radio_mouvement_entree);
	 
	    m_radioPanel_type = new JPanel();
	    m_radioPanel_type.setVisible(true);
	    m_radioPanel_type.setLayout(new GridLayout(1, 3));  // Disposer les JRadioButton à l'horizontale: 1 ligne, 3 colonnes
	    m_radioPanel_type.setBackground(SystemColor.window);
	    m_radioPanel_type.setBounds(50, 70, 400, 30);
	    m_radioPanel_type.add(m_radio_inventaire);
	    m_radioPanel_type.add(m_radio_mouvement_sortie);
	    m_radioPanel_type.add(m_radio_mouvement_entree);
	    m_radioPanel_type.validate();
	    
	    m_contentPane.add(m_radioPanel_type, BorderLayout.LINE_START);
		
		//--------
		// Date
		//--------
		m_lbl_date = new JLabel("Date");
		m_lbl_date.setBounds(50, 110, 100, 15);
		m_contentPane.add(m_lbl_date);
		
		m_date = new JDateChooser();
		m_date.setBounds(200, 110, 200, 24);
		m_contentPane.add(m_date);
		
		//-----------------
		// Date péremption
		//-----------------
		m_lbl_date_peremption = new JLabel("Date de péremption");
		m_lbl_date_peremption.setBounds(50, 140, 150, 15);
		m_contentPane.add(m_lbl_date_peremption);
		
		m_date_peremption = new JDateChooser();
		m_date_peremption.setBounds(200, 140, 200, 24);
		m_contentPane.add(m_date_peremption);
	    
		//-------------------------------
		// Boutons radio type de relevé
		//--------------------------------
	    m_radio_poids_g = new JRadioButton(POIDS_G);
	    m_radio_poids_g.setActionCommand(POIDS_G);
	    m_radio_poids_g.setSelected(true);
	    
	    m_radio_nb_graines = new JRadioButton(NB_GRAINES);
	    m_radio_nb_graines.setActionCommand(NB_GRAINES);
	    
	    m_radio_nb_plants = new JRadioButton(NB_PLANTS);
	    m_radio_nb_plants.setActionCommand(NB_PLANTS);
	    
	    m_radio_nb_lots = new JRadioButton(NB_LOTS);
	    m_radio_nb_lots.setActionCommand(NB_LOTS);
	    
	    // Register a listener for the radio buttons.
	    
	    m_radio_poids_g.addActionListener(this);
	    m_radio_nb_graines.addActionListener(this);
	    m_radio_nb_plants.addActionListener(this);
	    m_radio_nb_lots.addActionListener(this);
	    
	    // Grouper les boutons radio.
	    
	    m_radiogroup_releve = new ButtonGroup();
	    
	    m_radiogroup_releve.add(m_radio_poids_g);
	    m_radiogroup_releve.add(m_radio_nb_graines); 
	    m_radiogroup_releve.add(m_radio_nb_plants);
	    m_radiogroup_releve.add(m_radio_nb_lots);
	   
	    m_radioPanel_releve = new JPanel(new GridLayout(0, 1));
	    m_radioPanel_releve.setBackground(SystemColor.window);
	    m_radioPanel_releve.setBounds(50, 180, 180, 160);
        m_radioPanel_releve.add(m_radio_poids_g);
        m_radioPanel_releve.add(m_radio_nb_graines);
        m_radioPanel_releve.add(m_radio_nb_plants);
        m_radioPanel_releve.add(m_radio_nb_lots);
      
	    m_contentPane.add(m_radioPanel_releve, BorderLayout.LINE_START);

		// Cases de saisie des infos de stock
	    
		m_textField_poids_g = new JNumberTextField();
		m_textField_poids_g.setBounds(250, 190, 80, 19);
		m_contentPane.add(m_textField_poids_g);
		m_textField_poids_g.setColumns(10);

		m_lbl_poids_g = new JLabel("grammes");
		m_lbl_poids_g.setBounds(340, 190, 100, 15);
		m_contentPane.add(m_lbl_poids_g);

		m_textField_nb_graines = new JNumberTextField();
		m_textField_nb_graines.setBounds(250, 230, 80, 19);
		m_contentPane.add(m_textField_nb_graines);
		m_textField_nb_graines.setColumns(10);
		
		m_lbl_nb_graines = new JLabel("graines");
		m_lbl_nb_graines.setBounds(340, 230, 100, 15);
		m_contentPane.add(m_lbl_nb_graines);
		
		m_textField_nb_plants = new JNumberTextField();
		m_textField_nb_plants.setBounds(250, 270, 80, 19);
		m_contentPane.add(m_textField_nb_plants);
		m_textField_nb_plants.setColumns(10);

		m_lbl_nb_plants = new JLabel("plants");
		m_lbl_nb_plants.setBounds(340, 270, 100, 15);
		m_contentPane.add(m_lbl_nb_plants);

		// zone de saisie du Nb lots
		
		m_textField_nb_lots = new JNumberTextField();
		m_textField_nb_lots.setBounds(250, 310, 80, 19);
		m_contentPane.add(m_textField_nb_lots);
		m_textField_nb_lots.setColumns(10);

		m_lbl_nb_lots = new JLabel("lots");
		m_lbl_nb_lots.setBounds(340, 310, 100, 15);
		m_contentPane.add(m_lbl_nb_lots);
		
		setContentPane(m_contentPane);
		
	    //--------------------
        // Combobox lot
		//--------------------
		m_lbl_lot = new JLabel("Lot");
		m_lbl_lot.setBounds(200, 338, 100, 15);
		m_contentPane.add(m_lbl_lot);

		m_map_lot            = new HashMap<Object, String>();
		m_model_combobox_lot = new Vector<CLotPlante>();
		m_combobox_lot       = new JComboBox<CLotPlante>(m_model_combobox_lot);
		m_combobox_lot.setBounds(250, 338, 200, 24);
		LotChangeListener l_lot_change_listener =new LotChangeListener();
		l_lot_change_listener.set_ecran_saisie_stock(this);
		m_combobox_lot.addItemListener(l_lot_change_listener);
		m_contentPane.add(m_combobox_lot);
		
		//-------------------------------------------------------
		// Label d'information sur la dernière opération effectuée
		//--------------------------------------------------------
		m_lbl_information = new JLabel();
		m_lbl_information.setBounds(25, 378, 605, 15);
		m_lbl_information.setForeground(Color.BLUE);
		m_contentPane.add(m_lbl_information);
		
		//-----------------------------------------------------------------
		// Rendre visibles les zones de saisie initialement non visibles
		//-----------------------------------------------------------------
		m_textField_poids_g.setVisible(true);
		m_lbl_poids_g.setVisible(true);
		
		m_textField_nb_graines.setVisible(false);
		m_lbl_nb_graines.setVisible(false);
		
		m_textField_nb_plants.setVisible(false);
		m_lbl_nb_plants.setVisible(false);
		
		m_textField_nb_lots.setVisible(false);
		m_lbl_nb_lots.setVisible(false);
		m_lbl_lot.setVisible(false);
		m_combobox_lot.setVisible(false);

		//---------------------------------------------------
		// Bouton Enregistrer
		//---------------------------------------------------
		m_btnEnregistrerEtSuivant = new JButton("Enregistrer et suivant");
		m_btnEnregistrerEtSuivant.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnEnregistrerEtSuivantClick();
			}
		});
		m_btnEnregistrerEtSuivant.setBounds(100, 430, 170, 25);
		m_contentPane.add(m_btnEnregistrerEtSuivant);
		
		//---------------------------------------------------
		// Bouton Fermer
		//---------------------------------------------------
		m_btnFermer = new JButton("Fermer");
		m_btnFermer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnFermerClick();
			}
		});
		m_btnFermer.setBounds(300, 430, 80, 25);
		m_contentPane.add(m_btnFermer);

		//---------------------------------------------------
		// Bouton Annuler la derniere opération
		//---------------------------------------------------
		m_btnAnnulerDerniereOperation = new JButton("Annuler la derniere operation");
		m_btnAnnulerDerniereOperation.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnAnnulerDerniereOperationClick();
			}

		});
		m_btnAnnulerDerniereOperation.setBounds(430, 380, 220, 25);
		m_contentPane.add(m_btnAnnulerDerniereOperation);
		m_btnAnnulerDerniereOperation.setEnabled(false);
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	private void btnAnnulerDerniereOperationClick() 
	{
		// TODO implémentation
		
		m_btnAnnulerDerniereOperation.setEnabled(false);
	}
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void set_connexion_sql(Connection p_connexion_sql) 
	{
		super.set_connexion_sql(p_connexion_sql);		
	}

	//******************************************************************************************
	//
	//  Evenement appelé lorsqu'on change de lot dans la combobox
	//
	//------------------------------------------------------------------------------------------
	public void evenement_saisie_lot()
	{
        CLotPlante item = (CLotPlante)m_combobox_lot.getSelectedItem();
        
        if (item != null)
        {
        	m_lot_plante.set_lot_id(item.get_lot_id());
            if (m_lot_plante.get_lot_id() == -1)
            {
            	affiche_erreur("Aucun lot de plante n'a ete selectionne (1) - Choix obligatoire.");
            }            
        }
        else
        {
        	affiche_erreur("Aucun lot de plante n'a ete selectionne (2) - Choix obligatoire.");
        	m_lot_plante.set_lot_id(-1);
        }
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
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
	//------------------------------------------------------------------------------------------
	public void set_pla_id_selectionne(int p_pla_id_selectionne) 
	{
		m_plante.set_pla_id(p_pla_id_selectionne);
		m_plante.bd_select();
	    charge_interface_depuis_objet_plante();
	    bd_charge_combobox_lot();  // dépend de la plante sélectionnée
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void recharge_objet_plante() 
	{
        CPlante item = (CPlante)m_combobox_plante.getSelectedItem();
        
        if (item != null)
        {
        	m_plante.set_pla_id(item.get_pla_id());
        	m_plante.bd_select();
        }
        else
        {
        	m_plante.set_pla_id(-1);
        }		
	}
	
	//******************************************************************************************
	//
	//
	// retourne -1 si plante non trouvée
	//------------------------------------------------------------------------------------------
	public int get_position_plante_selectionnee()
	{
        CPlante l_plante = new CPlante();
        
        // Recherche de la plante pour la sélection automatique de la bonne plante dans la combobox
        int     l_position;
        boolean l_trouve;
        boolean l_termine;
        
        l_position = -1;
        l_trouve   = false;
        l_termine  = false;
        
        while ((!l_trouve) && (!l_termine))
        {
        	l_position++;
        	l_plante =  m_model_combobox_plante.get(l_position);
        	// l_plante.affiche_court();
        	if (l_plante.get_pla_id() == m_plante.get_pla_id())
        	{
        		l_trouve = true;
        	}
        	if (l_position == m_model_combobox_plante.size())
        	{
        		l_termine = true;
        	}
        }
        
        if (l_trouve)
        {
        	return l_position;
        }
        else
        {
        	// -1 signifie non trouvé
        	return -1;
        }
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public int get_id_plante_selectionnee() 
	{
		return m_plante.get_pla_id();
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
	public void set_ecran_principal(EcranPrincipal p_ecran_principal) 
	{
		this.m_ecran_principal = p_ecran_principal;
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
    protected void charge_interface_depuis_objet_plante()
    {
    	int l_position;
    	
    	l_position = get_position_plante_selectionnee();
        if (l_position != -1)
        {
            m_combobox_plante.setSelectedIndex(l_position);        	
        }
        else
        {
        	JOptionPane.showMessageDialog(null, "Plante recherchée non trouvée en BDD.");
        }		
	}

    //****************************************************************************************
	//
	// Pour les radio buttons, au moment du clic sur un radiobutton
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
						
			m_textField_nb_lots.setVisible(false);
			m_lbl_nb_lots.setVisible(false);
			m_lbl_lot.setVisible(false);
			m_combobox_lot.setVisible(false);
		}
		else if (e.getActionCommand() == NB_GRAINES)
		{
			m_textField_poids_g.setVisible(false);
			m_lbl_poids_g.setVisible(false);
			
			m_textField_nb_graines.setVisible(true);
			m_lbl_nb_graines.setVisible(true);
			
			m_textField_nb_plants.setVisible(false);
			m_lbl_nb_plants.setVisible(false);
			
			m_textField_nb_lots.setVisible(false);
			m_lbl_nb_lots.setVisible(false);
			m_lbl_lot.setVisible(false);
			m_combobox_lot.setVisible(false);
		}
		else if (e.getActionCommand() == NB_PLANTS)
		{
			m_textField_poids_g.setVisible(false);
			m_lbl_poids_g.setVisible(false);
			
			m_textField_nb_graines.setVisible(false);
			m_lbl_nb_graines.setVisible(false);
			
			m_textField_nb_plants.setVisible(true);
			m_lbl_nb_plants.setVisible(true);
			
			m_textField_nb_lots.setVisible(false);
			m_lbl_nb_lots.setVisible(false);
			m_lbl_lot.setVisible(false);
			m_combobox_lot.setVisible(false);
		}
		else if (e.getActionCommand() == NB_LOTS)
		{
			m_textField_poids_g.setVisible(false);
			m_lbl_poids_g.setVisible(false);
			
			m_textField_nb_graines.setVisible(false);
			m_lbl_nb_graines.setVisible(false);
			
			m_textField_nb_plants.setVisible(false);
			m_lbl_nb_plants.setVisible(false);
			
			m_textField_nb_lots.setVisible(true);
			m_lbl_nb_lots.setVisible(true);			
			m_lbl_lot.setVisible(true);
			m_combobox_lot.setVisible(true);
		}
		else if (e.getActionCommand() == INVENTAIRE)
		{
			// a priori, rien à faire
		}
		else if (e.getActionCommand() == SORTIE_STOCK)
		{
			// a priori, rien à faire			
		}
		else if (e.getActionCommand() == ENTREE_STOCK)
		{
			// a priori, rien à faire
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Type d'opération Stock inconnue -> "+e.getActionCommand()+
					                            ".\n Options possibles:(POIDS_G="+POIDS_G+"; NB_GRAINES="+NB_GRAINES+"; NB_PLANTS="+NB_PLANTS+"; NB_LOTS"+NB_LOTS+")");
		}				
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void bd_charge_donnees_interface() 
	{
		bd_charge_combobox_plante();
		//bd_charge_combobox_lot();
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void bd_charge_combobox_plante()
	{
		m_model_combobox_plante.removeAllElements();
		m_map_plante.clear();
		
		// Premier element de la liste vide
		m_map_plante.put( new Integer(-1), new String("") );
    	m_model_combobox_plante.addElement(new CPlante(-1, ""));
    	
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery("SELECT pla_id, pla_nom FROM plante ORDER BY pla_nom");
		    while (rs.next())
		    {
		    	m_map_plante.put( new Integer(rs.getInt(1)), new String(rs.getString(2)) );		    	
		    	m_model_combobox_plante.addElement( new CPlante(rs.getInt(1), rs.getString(2) ));
		    } 
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_charge_combobox_plante() -> " + sqle.getMessage();
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
	protected void bd_charge_combobox_lot()
	{
		m_model_combobox_lot.removeAllElements();
		m_map_lot.clear();
		
		// Premier element de la liste vide
		m_map_lot.put( new Integer(-1), new String("") );
    	m_model_combobox_lot.addElement(new CLotPlante());
	    	
		if (m_plante.get_pla_id() != -1)
		{
			try 
			{
			    Statement st = m_connexion_sql.createStatement();
			    // TODO: ajouter le nom du semencier et de la plante
			    // TODO: mettre une requete paramétrée (?) plutot qu'une requete faussement paramétrée 
			    ResultSet rs = st.executeQuery("SELECT "+
			    		"lot_id,"+
			    		"lot_nom,"+
						"pla_id_plante,"+
						"sec_id_semencier,"+
						"lot_avis,"+
						"lot_nb_etoiles,"+
						"lot_poids_g,"+
						"lot_nb_graines,"+
						"lot_nb_plants" +
						" FROM lot_plante"+
						" WHERE pla_id_plante = "+ m_plante.get_pla_id() +
						" ORDER BY lot_nom");

			    while (rs.next())
			    {
			    	m_map_lot.put( new Integer(rs.getInt(1)), new String(rs.getString(9)) );		    	
			    	m_model_combobox_lot.addElement( new CLotPlante( 	rs.getInt(1),
			    														rs.getString(2),
			    														rs.getInt(3),
			    														rs.getInt(4),
			    														rs.getString(5),
			    														rs.getInt(6),
			    														rs.getInt(7),
			    														rs.getInt(8),
			    														rs.getInt(9)
			    			                                         ));
			    } 
			    rs.close();
			    st.close();
			    
			    m_combobox_lot.updateUI();  // nécessaire sinon la combobox apparait parfois avec des lignes blanches au lieu des libellés
			}
			catch (SQLException sqle) 
			{
				String l_message;
				
				l_message = this.getClass().getName()+".bd_charge_combobox_lot() -> " + sqle.getMessage();
				JOptionPane.showMessageDialog(null, l_message);
			    System.err.println(l_message);
			    System.exit(1);
			}
		}
		
		active_desactive_lot();
	}

	//******************************************************************************************
	// 
	// 
	//
	//------------------------------------------------------------------------------------------
	public void active_desactive_lot()
	{
		//JOptionPane.showMessageDialog(null,"nb de lots dans la combo = "+m_model_combobox_lot.size());
		if (m_model_combobox_lot.size() == 1)
		{
			// Désactiver le lot
			this.m_radio_nb_lots.setEnabled(false);
			m_textField_nb_lots.setVisible(false);
			m_lbl_nb_lots.setVisible(false);
			m_lbl_lot.setVisible(false);
			m_combobox_lot.setVisible(false);
			
			
			// Si le lot est sélectionné, selectionner le premier
			if (m_radio_nb_lots.isSelected())
			{
				// Selectionner le poids en grammes
				m_radio_nb_lots.setSelected(false);
				m_radio_poids_g.setSelected(true);
				this.m_lbl_poids_g.setVisible(true);
				this.m_textField_poids_g.setVisible(true);
			}
		}
		else
		{
			// activer le lot
			this.m_radio_nb_lots.setEnabled(true);
		}
	}
	
	//******************************************************************************************
	// 
	// 
	//
	//------------------------------------------------------------------------------------------
	protected void btnFermerClick() 
	{
		m_ecran_principal.bd_charge_donnees_interface();
		fermer_la_fenetre();	
	}

	//******************************************************************************************
	// 
	// 
	//
	//------------------------------------------------------------------------------------------
	protected void affiche_info(String p_info)
	{
		if (m_lbl_information.getForeground() != Color.BLUE)
		{
			m_lbl_information.setForeground(Color.BLUE);
		}
		else
		{
			m_lbl_information.setForeground(Color.GREEN);
		}
		m_lbl_information.setText(p_info);
	}

	//******************************************************************************************
	// 
	// 
	//
	//------------------------------------------------------------------------------------------
	protected void affiche_erreur(String p_info)
	{
		m_lbl_information.setForeground(Color.RED);
		m_lbl_information.setText(p_info);
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void btnEnregistrerEtSuivantClick() 
	{
    	//JOptionPane.showMessageDialog(null, "DEBUG -> Début btnEnregistrerEtSuivantClick()");
		boolean l_resultat = false;
		
		// Test du choix (Inventaire; Sortie du stock; Entrée en stock)
		if (m_radio_inventaire.isSelected())
		{
			l_resultat = controle_et_enregistre_inventaire();
		}
		else if (m_radio_mouvement_entree.isSelected())
		{
			l_resultat = controle_et_enregistre_mouvement_entree();
		}
		else if (m_radio_mouvement_sortie.isSelected())
		{
			l_resultat = controle_et_enregistre_mouvement_sortie();
		}
		else
		{
			// Erreur
    		affiche_erreur("Veuillez choisir si votre saisie est un inventaire, une sortie de stock ou une entrée en stock.");
		}
		
		if (l_resultat == true)
		{
			// Remise à zéro des contrôles de l'écran de saisie
			initialise_ecran_pour_nouvelle_saisie();
		}
		else
		{
			// Erreur
    		//JOptionPane.showMessageDialog(
    		//		null,
    		//		"Echec de l'opération. Données non enregistrées.");   
			
		}
	}

	
	public void lit_saisie_plante()
	{
        CPlante item = (CPlante)m_combobox_plante.getSelectedItem();
        
        if (item != null)
        {
        	m_plante.set_pla_id(item.get_pla_id());
            if (m_plante.get_pla_id() == -1)
            {
            	affiche_erreur("Aucune plante n'a ete selectionnee (1) - Choix obligatoire.");
            }            
        }
        else
        {
        	affiche_erreur("Aucune plante n'a ete selectionnee (2) - Choix obligatoire.");
        	m_plante.set_pla_id(-1);
        }
	}
	
	
	public boolean teste_mouvement_sortie_nb_graines(long p_date_peremption)
	{
		if (saisie_nb_graines_ok(false))
        {   
    		// Test que la sortie n'excède pas le stock connu (simple avertissement??) dans le cas d'une sortie de stock
            if (m_plante.bd_stock_mvt_sortie_nb_graines_possible(m_saisie_nb_graines,p_date_peremption))
            {
            	return true;
            }
            else
            {
            	int l_stock;
            	l_stock = m_plante.bd_get_stock_actuel_nb_graines(p_date_peremption);
            	affiche_erreur("La saisie depasse le stock. Veuillez sortir une quantite inferieure à "+l_stock);
            	return false;		        	            	
            }
        }
		else
		{
			// Le message d'erreur est géré au niveau de la méthode saisie_nb_graines_ok 
			return false;
		}
	}
	
	public boolean teste_mouvement_sortie_nb_lots()
	{
		//JOptionPane.showMessageDialog(null, "DEBUG - EcranSaisieStock.teste_mouvement_sortie_nb_lots - DEBUT");
		if (saisie_nb_lots_ok(false))        
		{
			CDate l_date_peremption = new CDate();
			// On gère les dates vides 
			if (m_date_peremption.getCalendar() != null)
			{
				l_date_peremption.init_calendar(m_date_peremption.getCalendar());
			}
			else
			{
				// Date de péremption à l'infini
				l_date_peremption.init_date_infinie();			
			}
			
    		// Test que la sortie n'excède pas le stock connu (simple avertissement??) dans le cas d'une sortie de stock
			if (m_lot_plante.get_lot_id() != -1)
			{
	            if (m_plante.bd_stock_mvt_sortie_nb_lots_possible(m_saisie_nb_lots,l_date_peremption.get_bigint(),m_lot_plante.get_lot_id()))
	            {
	            	return true;
	            }
	            else
	            {
	            	// TODO: verifier qu'on n'a pas besoin de realiser des sorties de stock sur stock vide
	            	int l_stock;
	            	l_stock = m_plante.bd_get_stock_actuel_nb_lots(m_lot_plante.get_lot_id(), l_date_peremption.get_bigint());
	            	if (l_stock !=0)
	            	{
	            		affiche_erreur("La saisie depasse le stock. Veuillez sortir une quantite inferieure à "+l_stock);
	            	}
	            	else
	            	{
	            		affiche_erreur("Le stock est vide. Impossible d'effectuer une sortie de stock.");
	            	}
	            	return false;		        	            	
	            }
			}
			else
			{
            	affiche_erreur("Aucun lot n'a ete selectionne. Veuillez en choisir un dans la liste déroulante.");
				return false;
			}
        }
		else
		{
			// messages d'erreur gérés dans la methode saisie_nb_lots_ok
			return false;
		}
	}
	
	public boolean teste_mouvement_sortie_nb_plants()
	{
    	// TODO adapter la fonction au modele des autres
    	if (saisie_nb_plants_ok(false))
        {
      		CDate l_date_peremption = new CDate();
    		// On gère les dates vides 
    		if (m_date_peremption.getCalendar() != null)
    		{
    			l_date_peremption.init_calendar(m_date_peremption.getCalendar());
    		}
    		else
    		{
    			// Date de péremption à l'infini
    			l_date_peremption.init_date_infinie();			
    		}
   		    // Test que la sortie n'excède pas le stock connu (simple avertissement??) dans le cas d'une sortie de stock
            if (m_plante.bd_stock_mvt_sortie_nb_plants_possible(m_saisie_nb_plants,l_date_peremption.get_bigint()))
            {
            	return true;
            }
            else
            {
            	int l_stock;
             	l_stock = m_plante.bd_get_stock_actuel_nb_plants(l_date_peremption.get_bigint());
            	affiche_erreur("La saisie depasse le stock. Veuillez sortir une quantite inferieure a "+l_stock);
            	return false;		        	            	
            }
        }		
    	else
    	{
			// message d'erreur gérés dans la methode saisie_nb_plants_ok 
    		return false;
    	}
	}	
	
	
	public boolean teste_mouvement_sortie_poids_g()
	{
    	// TODO adapter la fonction au modele des autres
    	if (saisie_poids_g_ok(false))
        {
       		CDate l_date_peremption = new CDate();
       		
    		// On gère les dates vides 
    		if (m_date_peremption.getCalendar() != null)
    		{
    			l_date_peremption.init_calendar(m_date_peremption.getCalendar());
    		}
    		else
    		{
    			// Date de péremption à l'infini
    			l_date_peremption.init_date_infinie();			
    		}
    		// Test que la sortie n'excède pas le stock connu (simple avertissement??) dans le cas d'une sortie de stock
            if (m_plante.bd_stock_mvt_sortie_poids_g_possible(m_saisie_poids_g,l_date_peremption.get_bigint()))
            {
            	return true;
            }
            else
            {
            	int l_stock;
            	l_stock = m_plante.bd_get_stock_actuel_poids_g(l_date_peremption.get_bigint());
            	affiche_erreur("La saisie dépasse le stock. Veuillez sortir une quantite inferieure à "+l_stock);
            	return false;		        	            	
            }
        }		
    	else
    	{
			// message d'erreur géré dans la methode saisie_poids_g_ok			
    		return false;
    	}
	}
	
	private boolean teste_inventaire_poids_g() 
	{
		
    	return saisie_poids_g_ok(true);
	}

	private boolean teste_inventaire_nb_plants() 
	{
		return saisie_nb_plants_ok(true);
	}

	private boolean teste_inventaire_nb_lots() 
	{
		return saisie_nb_lots_ok(true);
	}

	private boolean teste_inventaire_nb_graines() 
	{
		return saisie_nb_graines_ok(true);
	}

	private boolean teste_mouvement_entree_poids_g() 
	{
    	return saisie_poids_g_ok(false);
	}
	private boolean teste_mouvement_entree_nb_plants() 
	{
		return saisie_nb_plants_ok(false);
	}
	private boolean teste_mouvement_entree_nb_lots() 
	{
		return saisie_nb_lots_ok(false);
	}
	private boolean teste_mouvement_entree_nb_graines(long p_date_peremption) 
	{
		if (saisie_nb_graines_ok(false))
        {   
    		// Test que la sortie n'excède pas le stock connu (simple avertissement??) dans le cas d'une sortie de stock
            if (m_plante.bd_stock_mvt_entree_nb_graines_possible(m_saisie_nb_graines, p_date_peremption))
            {
            	return true;
            }
            else
            {
            	affiche_erreur("Aucun inventaire saisi pour cette plante. Veuillez saisir un inventaire d'abord.");
            	return false;		        	            	
            }
        }
		else
		{
			// message d'erreur géré dans la méthode saisie_nb_graines_ok
			return false;
		}
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public boolean saisie_nb_graines_ok(boolean p_valeur_zero_autorisee)
	{
    	String l_valeur="";

    	try
    	{
      		l_valeur = m_textField_nb_graines.getText();
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, "ERREUR: "+e.toString());
    	}
    	
        // Test qu'une valeur a été saisie
        if (l_valeur.trim().length() == 0)
        {
        	JOptionPane.showMessageDialog(null, "Aucun nombre de graines n'a été saisi. - Saisie obligatoire.");
        	return false;		        	
        }
        else
        {
        	m_saisie_nb_graines = m_textField_nb_graines.getInt();
        	if (!p_valeur_zero_autorisee)
        	{
                if (m_saisie_nb_graines == 0)
                {
                	affiche_erreur("Mouvement nul impossible. Valeur supérieure à zéro obligatoire.");
                	return false;		        	
                }
        	}
        	return true;
        }
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public boolean saisie_nb_lots_ok(boolean p_valeur_zero_autorisee)
	{
    	String  l_valeur="";

    	// Test qu'un lot a bien été sélectionné

    	if (m_lot_plante.get_lot_id() == -1)
    	{
    		affiche_erreur("Aucun lot n'a été sélectionné dans la liste déroulante. - Saisie obligatoire.");
    		return false;
    	}
    	
    	// Saisie du nombre de lots
    	try
    	{
      		l_valeur = m_textField_nb_lots.getText();
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, "ERREUR: " + e.toString());
    	}
    	
        // Test qu'une valeur a été saisie
        if (l_valeur.trim().length() == 0)
        {
        	affiche_erreur("Aucun nombre de lots n'a été saisi. - Saisie obligatoire.");
        	return false;		        	
        }
        else
        {
        	m_saisie_nb_lots = m_textField_nb_lots.getInt();
        	if (!p_valeur_zero_autorisee)
        	{
                if (m_saisie_nb_lots == 0)
                {
                	affiche_erreur("Mouvement nul impossible. Valeur supérieure à zéro obligatoire.");
                	return false;		        	
                }
        	}
        	return true;
        }
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public boolean saisie_nb_plants_ok(boolean p_valeur_zero_autorisee)
	{
    	String  l_valeur="";

    	try
    	{
      		l_valeur = m_textField_nb_plants.getText();
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, "ERREUR: "+e.toString());
    	}
    	
        // Test qu'une valeur a été saisie
        if (l_valeur.trim().length() == 0)
        {
        	affiche_erreur("Aucun nombre de plants n'a été saisi. - Saisie obligatoire.");
        	return false;		        	
        }
        else
        {
        	m_saisie_nb_plants = m_textField_nb_plants.getInt();
        	if (!p_valeur_zero_autorisee)
        	{
                if (m_saisie_nb_plants == 0)
                {
                	affiche_erreur("Mouvement nul impossible. Valeur supérieure à zéro obligatoire.");
                	return false;		        	
                }
        	}
        	return true;
        }

	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public boolean saisie_poids_g_ok(boolean p_valeur_zero_autorisee)
	{
    	String  l_valeur="";

    	try
    	{
      		l_valeur = m_textField_poids_g.getText();
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, "ERREUR: "+e.toString());
    	}
    	
        // Test qu'une valeur a été saisie
        if (l_valeur.trim().length() == 0)
        {
        	affiche_erreur("Aucun poids en grammes n'a été saisi. - Saisie obligatoire.");
        	return false;		        	
        }
        else
        {
        	m_saisie_poids_g = m_textField_poids_g.getInt();
        	if (!p_valeur_zero_autorisee)
        	{
                if (m_saisie_poids_g == 0)
                {
                	affiche_erreur("Mouvement nul impossible. Valeur supérieure à zéro obligatoire.");
                	return false;		        	
                }
        	}
        	return true;
        }
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public boolean saisie_est_valide()
	{
    	//JOptionPane.showMessageDialog(null, "DEBUG -> Début saisie_est_valide()");
		CDate l_date_peremption = new CDate();
		
		// Test que la plante a bien été sélectionnée
		lit_saisie_plante();
		
		// On gère les dates vides 
		if (m_date_peremption.getCalendar() != null)
		{
			l_date_peremption.init_calendar(m_date_peremption.getCalendar());
		}
		else
		{
			// Date de péremption à l'infini
			l_date_peremption.init_date_infinie();			
		}

		if (m_plante.get_pla_id() == -1)
		{
			return false;
		}
		
		// Test que la date est bien saisie
		if (m_date.getDate() == null)
		{
			if (m_radio_inventaire.isSelected())
			{
				affiche_erreur("Veuillez saisir une date d'inventaire.");			
			}
			else if (m_radio_mouvement_entree.isSelected())
			{
				affiche_erreur("Veuillez saisir une date d'entrée dans le stock.");			
			}
			else if (m_radio_mouvement_sortie.isSelected())
			{
				affiche_erreur("Veuillez saisir une date de sortie du stock.");			
			}
			return false;
		}		
		
		//---------------------------------------------------------------
		if (m_radio_mouvement_sortie.isSelected())
		{	
			if (m_radio_nb_graines.isSelected())
			{
				return teste_mouvement_sortie_nb_graines(l_date_peremption.get_bigint());
			}
			else if (m_radio_nb_lots.isSelected())
			{
				return teste_mouvement_sortie_nb_lots();
			}
			else if (m_radio_nb_plants.isSelected())
			{
				return teste_mouvement_sortie_nb_plants();
			}
			else if (m_radio_poids_g.isSelected())
			{
				return teste_mouvement_sortie_poids_g();
			}
		}
		else if (m_radio_mouvement_entree.isSelected())
		{
			if (m_radio_nb_graines.isSelected())
			{
				return teste_mouvement_entree_nb_graines(l_date_peremption.get_bigint());
			}
			else if (m_radio_nb_lots.isSelected())
			{
				return teste_mouvement_entree_nb_lots();
			}
			else if (m_radio_nb_plants.isSelected())
			{
				return teste_mouvement_entree_nb_plants();
			}
			else if (m_radio_poids_g.isSelected())
			{
				return teste_mouvement_entree_poids_g();	
			}
		}
		else if (m_radio_inventaire.isSelected())
		{
	    	//JOptionPane.showMessageDialog(null, "DEBUG -> saisie_est_valide() - INVENTAIRE SELECTIONNE");

			if (m_radio_nb_graines.isSelected())
			{
				return teste_inventaire_nb_graines();
			}
			else if (m_radio_nb_lots.isSelected())
			{
		    	//JOptionPane.showMessageDialog(null, "DEBUG -> saisie_est_valide() - NOMBRE DE LOTS SELECTIONNE");
				return teste_inventaire_nb_lots();
			}
			else if (m_radio_nb_plants.isSelected())
			{
				return teste_inventaire_nb_plants();
			}
			else if (m_radio_poids_g.isSelected())
			{
				return teste_inventaire_poids_g();
			}
		}
		else
		{
			affiche_erreur("Veuillez sélectionner une unité de saisie.\n(Nb graines / Nb plants / Poids g / Nb lots)");			
            return false;
		}
		
		// Vérifier qu'on n'a pas saisi une valeur nulle pour 
		
		return true;
	}


	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void enregistre_objet_mouvement_depuis_interface(String p_mvt_sens)
	{
		CDate l_date_peremption = new CDate();
		CDate l_date_saisie     = new CDate();
		
		// On gère les dates vides 1/2
		if (m_date.getCalendar() != null)
		{
			l_date_saisie.init_calendar(m_date.getCalendar());
		}
		else
		{
			l_date_saisie.init();
		}
		
		// On gère les dates vides 2/2
		if (m_date_peremption.getCalendar() != null)
		{
			l_date_peremption.init_calendar(m_date_peremption.getCalendar());
		}
		else
		{
			// Date de péremption à l'infini
			l_date_peremption.init_date_infinie();			
		}
				
		//------------------------------------------------------------------------
		// Tous les tests de validité des données ont été faits auparavant
		if (m_radio_nb_graines.isSelected())
		{
			CMouvementNbGraines l_mouvement_nb_graines = new CMouvementNbGraines();
			l_mouvement_nb_graines.set_mvt_pla_id(m_plante.get_pla_id());
			l_mouvement_nb_graines.set_mvt_sens(p_mvt_sens);
			l_mouvement_nb_graines.set_mvt_nb_graines(m_saisie_nb_graines);
			l_mouvement_nb_graines.set_mvt_date(l_date_saisie);
			// TODO: gestion de la date de péremption
			l_mouvement_nb_graines.set_mvt_date_peremption(l_date_peremption);
			
			// Enregistrement en BD
			l_mouvement_nb_graines.bd_insert();
			
			affiche_info("Enregistré -> le "+ l_mouvement_nb_graines.bd_toString());
			m_btnAnnulerDerniereOperation.setEnabled(true);
		}
		//------------------------------------------------------------------------
		else if (m_radio_nb_lots.isSelected())
		{
			CMouvementLot l_mouvement_nb_lots = new CMouvementLot();
			l_mouvement_nb_lots.set_mvt_pla_id(m_plante.get_pla_id());
			l_mouvement_nb_lots.set_mvt_lot_id(m_lot_plante.get_lot_id());
			l_mouvement_nb_lots.set_mvt_sens(p_mvt_sens);
			l_mouvement_nb_lots.set_mvt_nb_lot(m_saisie_nb_lots);
			l_mouvement_nb_lots.set_mvt_date(l_date_saisie);
			// TODO: gestion de la date de péremption
			l_mouvement_nb_lots.set_mvt_date_peremption(l_date_peremption);
			
			// Enregistrement en BD
			l_mouvement_nb_lots.bd_insert();
			
			affiche_info("Enregistré -> le "+ l_mouvement_nb_lots.bd_toString());
			m_btnAnnulerDerniereOperation.setEnabled(true);
		}
		//------------------------------------------------------------------------
		else if (m_radio_nb_plants.isSelected())
		{
			CMouvementNbPlants l_mouvement_nb_plants = new CMouvementNbPlants();
			l_mouvement_nb_plants.set_mvt_pla_id(m_plante.get_pla_id());
			l_mouvement_nb_plants.set_mvt_sens(p_mvt_sens);
			l_mouvement_nb_plants.set_mvt_nb_plants(m_saisie_nb_plants);
			l_mouvement_nb_plants.set_mvt_date(l_date_saisie);
			// TODO: gestion de la date de péremption
			l_mouvement_nb_plants.set_mvt_date_peremption(l_date_peremption);
			
			// Enregistrement en BD
			l_mouvement_nb_plants.bd_insert();
			
			affiche_info("Enregistré -> le "+ l_mouvement_nb_plants.bd_toString());
			m_btnAnnulerDerniereOperation.setEnabled(true);
		}
		//------------------------------------------------------------------------
		else if (m_radio_poids_g.isSelected())
		{
            //JOptionPane.showMessageDialog(null,"DEBUG - enregistre_objet_mouvement_depuis_interface A");			
			CMouvementPoids l_mouvement_poids_g = new CMouvementPoids();
			
			l_mouvement_poids_g.set_mvt_pla_id(m_plante.get_pla_id());
			l_mouvement_poids_g.set_mvt_sens(p_mvt_sens);
			l_mouvement_poids_g.set_mvt_poids_grammes(m_saisie_poids_g);
			l_mouvement_poids_g.set_mvt_date(l_date_saisie);
			l_mouvement_poids_g.set_mvt_date_peremption(l_date_peremption);
			
			// Enregistrement en BD
			l_mouvement_poids_g.bd_insert();
			
			affiche_info("Enregistré -> le "+ l_mouvement_poids_g.bd_toString());
			m_btnAnnulerDerniereOperation.setEnabled(true);
		}
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	private void enregistre_objet_inventaire_depuis_interface() 
	{
    	//JOptionPane.showMessageDialog(null, "DEBUG -> Début enregistre_objet_inventaire_depuis_interface()");
		CDate l_date_peremption_stockee;
		int l_valeur_inventaire_precedent;
		
		if (m_date_peremption.getDate() == null)
		{
			l_date_peremption_stockee = new CDate();
			// Date de péremption à l'infini
			l_date_peremption_stockee.init_date_infinie();			
		}
		else
		{
			l_date_peremption_stockee = new CDate(m_date_peremption.getCalendar());
		}
		
		// Tous les tests de validité des données ont été faits auparavant
		CDate l_date_inventaire = new CDate(m_date.getCalendar());
		
		//------------------------------------------------------------------------
		if (m_radio_nb_graines.isSelected())
		{
			CInventaireNbGraines l_inventaire_nb_graines = new CInventaireNbGraines();
			l_inventaire_nb_graines.set_inv_pla_id(m_plante.get_pla_id());
			l_inventaire_nb_graines.set_inv_nb_graines(m_saisie_nb_graines);
			l_inventaire_nb_graines.set_inv_date(l_date_inventaire);
			l_inventaire_nb_graines.set_inv_date_peremption(l_date_peremption_stockee);
			
			l_valeur_inventaire_precedent = CInventaireNbGraines.bd_inventaire_existe(	m_plante.get_pla_id(), 
															l_date_peremption_stockee.get_bigint(), 
															l_date_inventaire.get_bigint());
			
			if (l_valeur_inventaire_precedent == -1)
			{
				// Enregistrement en BD
				l_inventaire_nb_graines.bd_insert();
				
				affiche_info("Enregistré -> le "+ l_inventaire_nb_graines.bd_toString());
				m_btnAnnulerDerniereOperation.setEnabled(true);
			}
			else
			{
				// Un inventaire existe déjà!
				affiche_erreur("Non enregistré. Un inventaire en nombre de graines existe déjà au "+l_date_inventaire.toStringFR()+" pour la date de péremption indiquée.");
			}
			
		}
		//------------------------------------------------------------------------
		else if (m_radio_nb_lots.isSelected())
		{
			CInventaireLot l_inventaire_nb_lots = new CInventaireLot();
			l_inventaire_nb_lots.set_inv_pla_id(m_plante.get_pla_id());
			l_inventaire_nb_lots.set_inv_nb_lot(m_saisie_nb_lots);
			l_inventaire_nb_lots.set_inv_lot_id(m_lot_plante.get_lot_id());
			l_inventaire_nb_lots.set_inv_date(l_date_inventaire);
			l_inventaire_nb_lots.set_inv_date_peremption(l_date_peremption_stockee);
			
			l_valeur_inventaire_precedent = 
					CInventaireLot.bd_inventaire_existe(	m_plante.get_pla_id(), 
															l_date_peremption_stockee.get_bigint(), 
															l_date_inventaire.get_bigint(),
															m_lot_plante.get_lot_id());

			if (l_valeur_inventaire_precedent == -1)
			{
				// Enregistrement en BD
				l_inventaire_nb_lots.bd_insert();
				
				affiche_info("Enregistré -> le "+ l_inventaire_nb_lots.bd_toString());
				m_btnAnnulerDerniereOperation.setEnabled(true);
			}
			else
			{
				// Un inventaire existe déjà!
				affiche_erreur("Non enregistré. Un inventaire en nombre de lots existe déjà pour ce lot au "+l_date_inventaire.toStringFR()+" à la date de péremption indiquée.");
				
			}
		}
		//------------------------------------------------------------------------
		else if (m_radio_nb_plants.isSelected())
		{
			CInventaireNbPlants l_inventaire_nb_plants = new CInventaireNbPlants();
			l_inventaire_nb_plants.set_inv_pla_id(m_plante.get_pla_id());
			l_inventaire_nb_plants.set_inv_nb_plants(m_saisie_nb_plants);
			l_inventaire_nb_plants.set_inv_date(l_date_inventaire);
			l_inventaire_nb_plants.set_inv_date_peremption(l_date_peremption_stockee);
			
			l_valeur_inventaire_precedent = CInventaireNbPlants.bd_inventaire_existe(	m_plante.get_pla_id(), 
					l_date_peremption_stockee.get_bigint(), 
					l_date_inventaire.get_bigint());

			if (l_valeur_inventaire_precedent == -1)
			{
				// Enregistrement en BD
				l_inventaire_nb_plants.bd_insert();
			
				affiche_info("Enregistré -> le "+ l_inventaire_nb_plants.bd_toString());
				m_btnAnnulerDerniereOperation.setEnabled(true);
			}
			else
			{
				// Un inventaire existe déjà!
				affiche_erreur("Non enregistré. Un inventaire en nombre de plants existe déjà au "+l_date_inventaire.toStringFR()+" pour la date de péremption indiquée.");				
			}
		}
		//------------------------------------------------------------------------
		else if (m_radio_poids_g.isSelected())
		{
			CInventairePoids l_inventaire_poids_g = new CInventairePoids();
			
			l_inventaire_poids_g.set_inv_pla_id(m_plante.get_pla_id());
			l_inventaire_poids_g.set_inv_poids_grammes(m_saisie_poids_g);
			l_inventaire_poids_g.set_inv_date(l_date_inventaire);
			l_inventaire_poids_g.set_inv_date_peremption(l_date_peremption_stockee);
			
			l_valeur_inventaire_precedent = CInventairePoids.bd_inventaire_existe(	m_plante.get_pla_id(), 
					l_date_peremption_stockee.get_bigint(), 
					l_date_inventaire.get_bigint());

			if (l_valeur_inventaire_precedent == -1)
			{
				// Enregistrement en BD
				l_inventaire_poids_g.bd_insert();

				affiche_info("Enregistré -> le "+ l_inventaire_poids_g.bd_toString());
				m_btnAnnulerDerniereOperation.setEnabled(true);
			}
			else
			{
				// Un inventaire existe déjà!
				affiche_erreur("Non enregistré. Un inventaire en poids existe déjà au "+l_date_inventaire.toStringFR()+" pour la date de péremption indiquée.");				
			}
		}		
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	private boolean controle_et_enregistre_mouvement_sortie() 
	{
		boolean l_resultat;
		l_resultat = saisie_est_valide(); 
		if (l_resultat == true)
		{
			enregistre_objet_mouvement_depuis_interface(CSensMouvement.MOUVEMENT_RETRAIT_STOCK() );
		}		
		return l_resultat;
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	private boolean controle_et_enregistre_mouvement_entree() 
	{
		boolean l_resultat;
		l_resultat = saisie_est_valide(); 
		if (l_resultat == true)
		{
			enregistre_objet_mouvement_depuis_interface(CSensMouvement.MOUVEMENT_AJOUT_STOCK() );
		}		
		return l_resultat;
	}


	//******************************************************************************************
	//
	//  
	//
	//------------------------------------------------------------------------------------------
	private boolean controle_et_enregistre_inventaire() 
	{
    	//JOptionPane.showMessageDialog(null, "DEBUG -> Début controle_et_enregistre_inventaire()");
		boolean l_resultat;
		l_resultat = saisie_est_valide(); 
		if (l_resultat == true)
		{
			enregistre_objet_inventaire_depuis_interface();
		}		
		else
		{
			//JOptionPane.showMessageDialog(null, "DEBUG -> Echec de saisie_est_valide()");
		}
		return l_resultat;
	}


	//******************************************************************************************
	//
	// Mettre l'interface à 0 pour permettre une nouvelle saisie
	//
	//------------------------------------------------------------------------------------------
	private void initialise_ecran_pour_nouvelle_saisie() 
	{
		// Par défaut on conserve le même type de saisie
		
		// On remet les valeurs des champs de saisie à 0
		m_textField_nb_graines.setText("0");
		m_textField_nb_lots.setText("0");
		m_textField_nb_plants.setText("0");
		m_textField_poids_g.setText("0");
	}

}
