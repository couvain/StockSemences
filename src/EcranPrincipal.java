import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Font;


public class EcranPrincipal extends EcranBase 
{

	private static final long        	serialVersionUID = 1L;
	
	private EcranAjoutPlante       		m_ecran_ajout_plante;
	private EcranModifPlante       		m_ecran_modif_plante;
	private EcranSaisieStock            m_ecran_saisie_stock;
	private EcranLot          			m_ecran_lot;

	private JFrame               		m_frmGestionDuStock;
	private JPanel                      m_1_panel_ecran_liste_plantes; // Panel contenu dans l'onglet "Liste de plantes" du tabbedpane et qui contient les contrôles de l'écran principal
	private JTabbedPane                 m_2_tabbed_pane_details; // Onglets en bas à droite de la fenêtre principale
	private JTextField           		m_texte_nom_de_la_plante;
	
	private TableauDistributeurs 		m_tableau_distributeur;
	private TableauPlantes       		m_tableau_plantes;
	private TableauInventaire			m_tableau_inventaire;
	private TableauMouvement			m_tableau_mouvement;
	private TableauHistorique			m_tableau_historique;
	
	private JTextField            		m_textfield_famille; 
	
	private HashMap<Integer, String> 	m_map_famille_plante = null;  // pour permettre la recherche [Cle famille -> Nom de famille de plante]
	private Connection           		m_connexion_sql = null;
	private JTextField           		m_textfield_cle_plante;
	private JLabel[]                    m_lblMoisSemis;
	private JCheckBox[]                	m_checkboxExterieur;
	private JCheckBox[]                	m_checkboxSerre;
	private JTextArea                  	m_textarea_notes_plante;
	private JTextArea                  	m_textarea_notes_distributeur;

	private CPlante                    	m_plante_selectionnee;

	private JButton 					m_btnAjouterPlante;
	private JButton 					m_btnModifierPlante;
	private JButton 					m_btnSupprimerPlante;
	
	private JButton 					m_btnGestionDuStock;
	
	// Sous-Ecran distributeur
	
	private CLotPlante					m_lot_plante_selectionne;
	private StarRater 					m_starRater;
	private JButton                    	m_btnAjouterDistributeur;
	private JButton 					m_btnSupprimerDistributeur;
	private JTabbedPane                 m_0_tabbed_pane_general;
	
	private JPanel                      m_2_2_panel_stock;
	private JPanel 						m_2_1_panel_informations_plante;
	private JPanel 						m_2_3_panel_distributeur;
	private TableauStock                m_tableau_stock;

	// Dans l'onglet Stock on trouve deux autres onglets
	private JTabbedPane					m_tabbed_pane_stock;
	// Les deux onglets dans l'onglet Stock
	private JPanel						m_panel_stock_detail;
	private JPanel						m_panel_stock_synthese;
	private JPanel						m_panel_stock_historique;
	
	//******************************************************************************************
	//
	// Lancer l'application.
	//
	//******************************************************************************************
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					System.out.println("Lancement de l'applicaton");
					EcranPrincipal window = new EcranPrincipal();
					System.out.println("Affichage de la fenetre principale");
					window.m_frmGestionDuStock.setVisible(true);
					// Ici l'application est en fonctionnement
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void set_pla_id_selectionne(int p_pla_id_selectionne) 
	{
		m_plante_selectionnee.set_pla_id(p_pla_id_selectionne);
		
		if (p_pla_id_selectionne == -1)
		{
			// On a deselectionne (ctrl + clic sur la plante selectionnee) -> aucune plante selectionnee
			m_btnAjouterDistributeur.setEnabled(false);
			m_btnSupprimerPlante.setEnabled(false);
			m_btnModifierPlante.setEnabled(false);
			m_btnGestionDuStock.setEnabled(false);
		}
		else
		{
			// On a effectivement une plante de selectionnee
			m_btnAjouterDistributeur.setEnabled(true);
			m_btnSupprimerPlante.setEnabled(true);
			m_btnModifierPlante.setEnabled(true);
			m_btnGestionDuStock.setEnabled(true);
		}
		// On met toutes les etoiles a zero
		m_starRater.setRating(0);
		m_starRater.setSelection(0);
		
		// On desactive le bouton supprimer
		m_btnSupprimerDistributeur.setEnabled(false);
		
		// On efface le texte du distributeur
		m_textarea_notes_distributeur.setText("");
		
		// Affichage de la plante
		bd_affiche_plante();
		
		// Chargement et affichage du stock dans le tableau Stock
		m_tableau_stock.bd_remplit_tableau(p_pla_id_selectionne);
		
		// chargement et affichage du tableau de détail inventaire
		m_tableau_inventaire.bd_remplit_tableau(p_pla_id_selectionne);
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public CPlante get_plante_selectionnee() 
	{
		return m_plante_selectionnee;
	}

	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void set_lot_id_selectionne(int p_lot_id_selectionne) 
	{
		m_lot_plante_selectionne.set_lot_id(p_lot_id_selectionne);
		if (p_lot_id_selectionne == -1)
		{
			// On a deselectionne (ctrl + clic sur le distributeur selectionne) -> aucun distributeur selectionne
			
			// On met toutes les etoiles a zero
			m_starRater.setRating(0);
			m_starRater.setSelection(0);
			
			// On desactive le bouton supprimer
			m_btnSupprimerDistributeur.setEnabled(false);
			
			// On efface le texte du distributeur
			m_textarea_notes_distributeur.setText("");
		}
		else
		{
			// On a effectivement un distributeur de selectionne
			
			// On active le bouton supprimer
			m_btnSupprimerDistributeur.setEnabled(true);
		}
	}
	
	//******************************************************************************************
	//
	// Créer l'application.
	//
	//******************************************************************************************
	public EcranPrincipal() 
	{
		initialize();
	}

	//******************************************************************************************
	//
	// Initialize the contents of the frame.
	//
	//******************************************************************************************
	private void initialize() 
	{
		m_plante_selectionnee = new CPlante();
		m_plante_selectionnee.set_pla_id(-1);  // Par defaut aucune plante n'est selectionnee

		m_lot_plante_selectionne = new CLotPlante();
		m_lot_plante_selectionne.set_lot_id(-1);  // Par defaut aucun lot de plante n'est selectionne
		
		m_ecran_ajout_plante = new EcranAjoutPlante();
		
		m_ecran_modif_plante = new EcranModifPlante();
		
		m_ecran_saisie_stock = new EcranSaisieStock();
		m_ecran_saisie_stock.set_ecran_principal(this);
		
		m_ecran_lot = new EcranLot();
		m_ecran_lot.setBounds(100,100,600,600);
		m_ecran_lot.setTitle("Ajout d'un nouveau lot");
		m_ecran_lot.set_ecran_principal(this);

		
		m_frmGestionDuStock = new JFrame();
		m_frmGestionDuStock.setResizable(false);
		m_frmGestionDuStock.setTitle("Gestion du stock de semence");
		m_frmGestionDuStock.getContentPane().setLayout(null);
		m_frmGestionDuStock.setBounds(100, 100, 1010, 600);
		m_frmGestionDuStock.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		m_frmGestionDuStock.addWindowListener( new AreYouSure() );
		
		//m_frmGestionDuStock.centreWindow();
		
		m_0_tabbed_pane_general = new JTabbedPane(JTabbedPane.TOP);
		m_0_tabbed_pane_general.setBounds(5, 0, 1000, 579);
		// getContentPane() Retourne l'objet "contentPane" (de type Container) de l'objet nomme "frame"
		
		// Ajoute le tabbedPane au frame general
		m_frmGestionDuStock.getContentPane().add(m_0_tabbed_pane_general, BorderLayout.CENTER);
		
		
		//-------------------------------------------------------------
		// Ajout de 2 onglets à l'intérieur de l'onglet de Stock
		//-------------------------------------------------------------
		m_tabbed_pane_stock = new JTabbedPane();
		
		//TODO: ajuster dimensions
		m_tabbed_pane_stock.setBounds(5, 5, 100, 100);
		
		//-------------------------------------------------------------
		
		// Onglet "Liste de plantes"
		creeEcranListePlantes();
		m_0_tabbed_pane_general.addTab("Liste de plantes", m_1_panel_ecran_liste_plantes);
		
		// Connexion a la base de donnees
		connexion_JDBC();
		bd_charge_donnees_interface();
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void bd_charge_donnees_interface() 
	{
		bd_charge_hashmap_famille_de_plante();
		bd_charge_liste_plantes();
		bd_charge_liste_ditributeurs();
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	private void bd_charge_liste_ditributeurs() 
	{
		m_tableau_distributeur.bd_lance_requete(m_plante_selectionnee.get_pla_id());	
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	private void bd_charge_liste_plantes() 
	{
		m_tableau_plantes.bd_lance_requete();
		// Lorsqu'on recharge la liste de plantes, on perd la sélection de plante
		this.set_pla_id_selectionne(-1);
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void bd_affiche_distributeur()
	{
		//JOptionPane.showMessageDialog(null, "bd_affiche_distributeur() -> lot_id = "+m_lot_plante_selectionne.get_lot_id());

		m_lot_plante_selectionne.bd_select();
		
		// Affichage du nombre d'etoiles
		m_starRater.setRating(m_lot_plante_selectionne.get_lot_nb_etoiles());
		m_starRater.setSelection(0);

		// Affichage de l'avis
		m_textarea_notes_distributeur.setText(m_lot_plante_selectionne.get_lot_avis());
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void bd_affiche_plante()
	{
		m_plante_selectionnee.bd_select();
		
		// On recupere a famille de plante dans la hashmap
		CFamillePlante l_famille = new CFamillePlante();
		l_famille.set_fap_id(m_plante_selectionnee.get_fap_id_famille_plante());
		l_famille.set_fap_nom(m_map_famille_plante.get(new Integer(l_famille.get_fap_id())));
		
		// Affichage des donnees lues dans l'interface graphique
		m_textfield_cle_plante.setText("" + m_plante_selectionnee.get_pla_id());
		m_texte_nom_de_la_plante.setText(m_plante_selectionnee.get_pla_nom());
		m_textfield_famille.setText(l_famille.get_fap_nom());
		
		m_checkboxSerre[ 0].setSelected(m_plante_selectionnee.get_pla_semis_ser_jan());
		m_checkboxSerre[ 1].setSelected(m_plante_selectionnee.get_pla_semis_ser_fev());
		m_checkboxSerre[ 2].setSelected(m_plante_selectionnee.get_pla_semis_ser_mar());
		m_checkboxSerre[ 3].setSelected(m_plante_selectionnee.get_pla_semis_ser_avr());
		m_checkboxSerre[ 4].setSelected(m_plante_selectionnee.get_pla_semis_ser_mai());
		m_checkboxSerre[ 5].setSelected(m_plante_selectionnee.get_pla_semis_ser_jun());
		m_checkboxSerre[ 6].setSelected(m_plante_selectionnee.get_pla_semis_ser_jul());
		m_checkboxSerre[ 7].setSelected(m_plante_selectionnee.get_pla_semis_ser_aou());
		m_checkboxSerre[ 8].setSelected(m_plante_selectionnee.get_pla_semis_ser_sep());
		m_checkboxSerre[ 9].setSelected(m_plante_selectionnee.get_pla_semis_ser_oct());
		m_checkboxSerre[10].setSelected(m_plante_selectionnee.get_pla_semis_ser_nov());
		m_checkboxSerre[11].setSelected(m_plante_selectionnee.get_pla_semis_ser_dec());
		
		m_checkboxExterieur[ 0].setSelected(m_plante_selectionnee.get_pla_semis_ext_jan());
		m_checkboxExterieur[ 1].setSelected(m_plante_selectionnee.get_pla_semis_ext_fev());
		m_checkboxExterieur[ 2].setSelected(m_plante_selectionnee.get_pla_semis_ext_mar());
		m_checkboxExterieur[ 3].setSelected(m_plante_selectionnee.get_pla_semis_ext_avr());
		m_checkboxExterieur[ 4].setSelected(m_plante_selectionnee.get_pla_semis_ext_mai());
		m_checkboxExterieur[ 5].setSelected(m_plante_selectionnee.get_pla_semis_ext_jun());
		m_checkboxExterieur[ 6].setSelected(m_plante_selectionnee.get_pla_semis_ext_jul());
		m_checkboxExterieur[ 7].setSelected(m_plante_selectionnee.get_pla_semis_ext_aou());
		m_checkboxExterieur[ 8].setSelected(m_plante_selectionnee.get_pla_semis_ext_sep());
		m_checkboxExterieur[ 9].setSelected(m_plante_selectionnee.get_pla_semis_ext_oct());
		m_checkboxExterieur[10].setSelected(m_plante_selectionnee.get_pla_semis_ext_nov());
		m_checkboxExterieur[11].setSelected(m_plante_selectionnee.get_pla_semis_ext_dec());
		
		m_textarea_notes_plante.setText(m_plante_selectionnee.get_pla_notes());
		
		bd_charge_liste_ditributeurs();
	}
		
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void creeEcranListePlantes() 
	{
		m_1_panel_ecran_liste_plantes = new JPanel();
        
		// On se met en coordonnees relatives
		m_1_panel_ecran_liste_plantes.setLayout(null);
		
		m_texte_nom_de_la_plante = new JTextField();
		m_texte_nom_de_la_plante.setFont(new Font("Dialog", Font.BOLD, 12));
		m_texte_nom_de_la_plante.setEditable(false);
		m_texte_nom_de_la_plante.setBounds(15, 15, 423, 24);
		m_texte_nom_de_la_plante.setColumns(10);
		m_texte_nom_de_la_plante.setToolTipText("Nom de la plante");
		m_1_panel_ecran_liste_plantes.add(m_texte_nom_de_la_plante);

		// Liste des plantes
		m_tableau_plantes = new TableauPlantes();
		m_tableau_plantes.set_ecran_principal(this);
		m_tableau_plantes.setBounds(15, 55, 423, 430);
		m_1_panel_ecran_liste_plantes.add(m_tableau_plantes);
		
		// Bouton Ajouter plante
		m_btnAjouterPlante = new JButton("Ajouter plante");
		m_btnAjouterPlante.setToolTipText("Ajouter une plante à la base de données");
		m_btnAjouterPlante.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnAjouterPlanteClick();
			}
		});
		m_btnAjouterPlante.setBounds(15, 505, 120, 25);
		m_1_panel_ecran_liste_plantes.add(m_btnAjouterPlante);

		// Bouton Modifier plante
		m_btnModifierPlante = new JButton("Modifier plante");
		m_btnModifierPlante.setEnabled(false);
		m_btnModifierPlante.setToolTipText("Modifier la plante sélectionnée");
		m_btnModifierPlante.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnModifierPlanteClick();
			}
		});
		m_btnModifierPlante.setBounds(155, 505, 120, 25);
		m_1_panel_ecran_liste_plantes.add(m_btnModifierPlante);
		
		// Bouton Supprimer plante
		m_btnSupprimerPlante = new JButton("Supprimer plante");
		m_btnSupprimerPlante.setEnabled(false);
		m_btnSupprimerPlante.setToolTipText("Retirer une plante de la base de données");
		m_btnSupprimerPlante.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnSupprimerPlanteClick();
			}
		});
		m_btnSupprimerPlante.setBounds(295, 505, 140, 25);
		m_1_panel_ecran_liste_plantes.add(m_btnSupprimerPlante);
		
		// Onglets en bas a droite de la fenetre principale
		m_2_tabbed_pane_details = new JTabbedPane(JTabbedPane.TOP);
		m_2_tabbed_pane_details.setBounds(454, 10, 530, 520);
		m_1_panel_ecran_liste_plantes.add(m_2_tabbed_pane_details);
		
		// Onglet Stock 
		creePanelStockPlante();
		m_2_tabbed_pane_details.addTab("Stock", m_2_2_panel_stock);

		// Onglet Informations plante
		creePanelInformationsPlante();
		m_2_tabbed_pane_details.addTab("Informations plante", m_2_1_panel_informations_plante);
		
		// Onglet Distributeur
		creePanelDistributeur();
		m_2_tabbed_pane_details.addTab("Distributeurs", m_2_3_panel_distributeur);
	}
	
	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	protected void cases_a_cocher(int p_decalage_x, int p_decalage_y, JPanel p_panel)
	{		
		JLabel lblPrioseD = new JLabel("Periode de semis");
		lblPrioseD.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPrioseD.setBounds(p_decalage_x, p_decalage_y+7, 200, 15);
		p_panel.add(lblPrioseD);

		// Periode de semis
		JLabel lblSemisExterieur = new JLabel("Exterieur");
		lblSemisExterieur.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblSemisExterieur.setBounds(p_decalage_x+10, p_decalage_y+47, 200, 15);
		p_panel.add(lblSemisExterieur);
		
		JLabel lblSemisSerre = new JLabel("Serre");
		lblSemisSerre.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblSemisSerre.setBounds(p_decalage_x+10, p_decalage_y+67, 200, 15);
		p_panel.add(lblSemisSerre);
		int i;
		
		// Libelles des mois au dessus des cases a cocher de periodes de semis
		m_lblMoisSemis = new JLabel[12];
		for (i = 0; i < 12; i++)
		{
			switch (i)
			{
				case  0: m_lblMoisSemis[i] = new JLabel("J"); break;
				case  1: m_lblMoisSemis[i] = new JLabel("F"); break;
				case  2: m_lblMoisSemis[i] = new JLabel("M"); break;
				case  3: m_lblMoisSemis[i] = new JLabel("A"); break;
				case  4: m_lblMoisSemis[i] = new JLabel("M"); break;
				case  5: m_lblMoisSemis[i] = new JLabel("J"); break;
				case  6: m_lblMoisSemis[i] = new JLabel("J"); break;
				case  7: m_lblMoisSemis[i] = new JLabel("A"); break;
				case  8: m_lblMoisSemis[i] = new JLabel("S"); break;
				case  9: m_lblMoisSemis[i] = new JLabel("O"); break;
				case 10: m_lblMoisSemis[i] = new JLabel("N"); break;
				case 11: m_lblMoisSemis[i] = new JLabel("D"); break;
			}
			
			m_lblMoisSemis[i].setBounds(80+(20*i)+p_decalage_x, p_decalage_y+25, 20, 20);
			p_panel.add(m_lblMoisSemis[i]);
		}
		
		// Cases a cocher des semis en extérieur
		m_checkboxExterieur = new JCheckBox[12];
		for (i = 0; i < 12; i++)
		{
			m_checkboxExterieur[i] = new JCheckBox("");
			m_checkboxExterieur[i].setBounds(75+(20*i)+p_decalage_x, p_decalage_y+45, 20, 20);
			m_checkboxExterieur[i].setEnabled(false);
			p_panel.add(m_checkboxExterieur[i]);
		}

		// Cases à cocher des semis sous serre
		m_checkboxSerre = new JCheckBox[12];
		for (i = 0; i < 12; i++)
		{
			m_checkboxSerre[i] = new JCheckBox("");
			m_checkboxSerre[i].setBounds(75+(20*i)+p_decalage_x, p_decalage_y+65, 20, 20);
			m_checkboxSerre[i].setEnabled(false);
			p_panel.add(m_checkboxSerre[i]);
		}
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void connexion_JDBC()
	{
		try
		{
		    Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException cnfe)
		{
			String l_message;
			
			l_message = this.getClass().getName()+".connexion_JDBC -> Pilote JDBC introuvable. ";
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
   	        System.exit(1);
		}
		
		try 
		{
			// Ouverture de la connexion SQL
			//m_connexion_sql = DriverManager.getConnection("jdbc:postgresql://localhost:5432/amap", "david", "postgres");
			m_connexion_sql = DriverManager.getConnection("jdbc:postgresql://localhost:5432/amap", "postgres", "postgres");
		    System.out.println("Connexion JDBC OK");
		    
		    //
		    m_tableau_plantes.set_connexion_sql(m_connexion_sql);
		    m_tableau_distributeur.set_connexion_sql(m_connexion_sql);
		    m_tableau_historique.set_connexion_sql(m_connexion_sql);
		    m_tableau_inventaire.set_connexion_sql(m_connexion_sql);
		    m_tableau_mouvement.set_connexion_sql(m_connexion_sql);
		    m_tableau_stock.set_connexion_sql(m_connexion_sql);
		    CLotPlante.set_connexion_sql(m_connexion_sql);
		    CPlante.set_connexion_sql(m_connexion_sql);
		    CSemencier.set_connexion_sql(m_connexion_sql);
		    CFamillePlante.set_connexion_sql(m_connexion_sql);
			CMouvementNbGraines.set_connexion_sql(this.m_connexion_sql);
			CMouvementLot.set_connexion_sql(this.m_connexion_sql);
			CMouvementNbPlants.set_connexion_sql(this.m_connexion_sql);
			CMouvementPoids.set_connexion_sql(this.m_connexion_sql);
			CInventaireNbGraines.set_connexion_sql(this.m_connexion_sql);
			CInventaireLot.set_connexion_sql(this.m_connexion_sql);
			CInventaireNbPlants.set_connexion_sql(this.m_connexion_sql);
			CInventairePoids.set_connexion_sql(this.m_connexion_sql);
		    m_ecran_ajout_plante.set_connexion_sql(m_connexion_sql);
		    m_ecran_modif_plante.set_connexion_sql(m_connexion_sql);
		    m_ecran_lot.set_connexion_sql(m_connexion_sql);
		    m_ecran_saisie_stock.set_connexion_sql(m_connexion_sql);
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".connexion_JDBC -> Connexion JDBC impossible:" + sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
	}
	
	//******************************************************************************************
	//
	// Création de l'onglet "Stock" dans le tabbedPane en bas à droite de l'écran principal
	//
	//------------------------------------------------------------------------------------------
	protected void creePanelStockPlante() 
	{
		// TODO: remplir l'onglet Stock
		// 
		m_tableau_stock = new TableauStock();
		m_tableau_stock.set_ecran_principal(this);
		m_tableau_stock.setBounds(10, 10, 490, 410);

		m_tableau_inventaire = new TableauInventaire();
		m_tableau_inventaire.set_ecran_principal(this);
		m_tableau_inventaire.setBounds(10, 30, 490, 190);
		
		m_tableau_mouvement = new TableauMouvement();
		m_tableau_mouvement.set_ecran_principal(this);
		m_tableau_mouvement.setBounds(10, 230, 490, 190);
		
		m_tableau_historique = new TableauHistorique();
		m_tableau_historique.set_ecran_principal(this);
		m_tableau_historique.setBounds(10, 10, 490, 410);


		m_panel_stock_detail = new JPanel();
		m_panel_stock_synthese = new JPanel();
		m_panel_stock_historique = new JPanel();
		
		
		// On se met en coordonnees relatives
		m_panel_stock_detail.setLayout(null);
		m_panel_stock_synthese.setLayout(null);
		m_panel_stock_historique.setLayout(null);
		
		//m_2_2_panel_stock.add(m_tableau_stock);
		m_panel_stock_synthese.add(m_tableau_stock);
		
		m_panel_stock_detail.add(m_tableau_inventaire);
		m_panel_stock_detail.add(m_tableau_mouvement);
		
		m_panel_stock_historique.add(m_tableau_historique);

		m_tabbed_pane_stock = new JTabbedPane();
		m_tabbed_pane_stock.setBounds(5,25,510,460);
		m_tabbed_pane_stock.addTab("Synthese", m_panel_stock_synthese);
		m_tabbed_pane_stock.addTab("Detail", m_panel_stock_detail);
		m_tabbed_pane_stock.addTab("Historique", m_panel_stock_historique);
		
		m_2_2_panel_stock = new JPanel();
		
		// On ajoute les 2 onglets
		m_2_2_panel_stock.setLayout(null);		
		m_2_2_panel_stock.add(m_tabbed_pane_stock);
		
		// Bouton Nouvel Inventaire
		m_btnGestionDuStock = new JButton("Gestion du stock");
		m_btnGestionDuStock.setToolTipText("Gestion des inventaires, des entrées et des sorties du stock");
		m_btnGestionDuStock.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnNouvelInventaireClick();
			}
		});
		m_btnGestionDuStock.setBounds(375, 10, 135, 25);
		m_2_2_panel_stock.add(m_btnGestionDuStock);
		
		//m_tabbed_pane_stock.addTab("Synthese", m_panel_stock_synthese);
		//m_tabbed_pane_stock.addTab("Details", m_panel_stock_detail);
	}
	

	//******************************************************************************************
	//
	// Création de l'onglet "Notes" dans le tabbedPane en bas à droite de l'écran principal
	//
	//------------------------------------------------------------------------------------------
	protected void creePanelInformationsPlante() 
	{
		//*********************************************************
		// Encadre de la plante selectionnee
		//---------------------------------------------------------
		int l_decal_y=15;
		
		m_2_1_panel_informations_plante = new JPanel();
		//m_PanelInformationsPlante.setBackground(SystemColor.window);
        
		// On se met en coordonnees relatives
		m_2_1_panel_informations_plante.setLayout(null);
		
		// Cle
		m_textfield_cle_plante = new JTextField();
		m_textfield_cle_plante.setHorizontalAlignment(SwingConstants.CENTER);
		m_textfield_cle_plante.setBackground(Color.YELLOW);
		m_textfield_cle_plante.setBounds(433, l_decal_y, 74, 19);
		m_textfield_cle_plante.setColumns(10);
		m_textfield_cle_plante.setEditable(false);
		m_textfield_cle_plante.setToolTipText("Clé");
		m_2_1_panel_informations_plante.add(m_textfield_cle_plante);
		
		// Label famille de plante 
		JLabel lblFamilleDePlante = new JLabel("Famille de plante");
		lblFamilleDePlante.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblFamilleDePlante.setBounds(12, l_decal_y, 200, 15);
		m_2_1_panel_informations_plante.add(lblFamilleDePlante);

		// famille de plante
		m_map_famille_plante = new HashMap<Integer, String>();
		m_textfield_famille = new JTextField(); 
		m_textfield_famille.setFont(new Font("Dialog", Font.BOLD, 12));
		m_textfield_famille.setEditable(false);
		m_textfield_famille.setBounds(12, l_decal_y+24, 210, 24);
		m_2_1_panel_informations_plante.add(m_textfield_famille);

		cases_a_cocher(12, l_decal_y+60, m_2_1_panel_informations_plante);
		
		//-------------------------------------
		// Note textuelle relative à la plante
		//-------------------------------------
		// Label famille de plante 
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNote.setBounds(12, l_decal_y+192, 200, 15);
		m_2_1_panel_informations_plante.add(lblNote);

		m_textarea_notes_plante = new JTextArea();
		//m_textarea_notes_plante.setBackground(m_textfield_famille.getBackground());
		m_textarea_notes_plante.setLineWrap(true);
		m_textarea_notes_plante.setEditable(false);
		m_textarea_notes_plante.setBounds(12, l_decal_y+215, 500, 250);
		m_2_1_panel_informations_plante.add(m_textarea_notes_plante);
	}
	
	//******************************************************************************************
	//
	// Création de l'onglet "Distributeur" dans le tabbedPane en bas à droite de l'écran principal
	//
	//------------------------------------------------------------------------------------------
	protected void creePanelDistributeur() 
	{
		m_2_3_panel_distributeur = new JPanel();
        
		// On se met en coordonnees relatives
		m_2_3_panel_distributeur.setLayout(null);
		
		//------------------------------------------------
		// Liste de semenciers distributeurs de la plante
		//------------------------------------------------
		m_tableau_distributeur = new TableauDistributeurs();
		m_tableau_distributeur.set_ecran_principal(this);

		m_tableau_distributeur.setBounds(12, 12, 500, 200);
		m_2_3_panel_distributeur.add(m_tableau_distributeur);
		
		//-----------------------------
		// Bouton Ajouter distributeur
		//-----------------------------
		m_btnAjouterDistributeur = new JButton("Ajouter");
		m_btnAjouterDistributeur.setEnabled(false);
		m_btnAjouterDistributeur.setToolTipText("Ajouter un nouveau semencier distributeur de cette plante");
		m_btnAjouterDistributeur.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnAjouterDistributeurClick();
			}
		});
		m_btnAjouterDistributeur.setBounds(12, 220, 110, 25);
		m_2_3_panel_distributeur.add(m_btnAjouterDistributeur);

		//-------------------------------
		// Bouton Supprimer distributeur
		//-------------------------------
		m_btnSupprimerDistributeur = new JButton("Supprimer");
		m_btnSupprimerDistributeur.setEnabled(false);
		m_btnSupprimerDistributeur.setToolTipText("Enlever le semencier selectionne des distributeurs de cette plante");
		m_btnSupprimerDistributeur.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnSupprimerDistributeurClick();
			}
		});
		m_btnSupprimerDistributeur.setBounds(150, 220, 110, 25);
		m_2_3_panel_distributeur.add(m_btnSupprimerDistributeur);

		//-------------------------------
		// Notation sous forme d'étoiles
		//-------------------------------
		m_starRater = new StarRater(11, 5.0, 5);  // 11 etoiles et valeur 6 par defaut (5 en realite)
		
		m_starRater.setBounds(330, 255, 175, 50);
		m_starRater.addStarListener(new StarRater.StarListener() 
		{
		  public void handleSelection(int selection) 
		  {
		    // a new number of stars has been selected
		    //do something...
		  }
		});
		m_2_3_panel_distributeur.add(m_starRater);   // add the component to the container...

		//-----------------------------------------
		// Note textuelle relative au distributeur
		//-----------------------------------------
		m_textarea_notes_distributeur = new JTextArea();
		m_textarea_notes_distributeur.setLineWrap(true);
		m_textarea_notes_distributeur.setEditable(false);
		m_textarea_notes_distributeur.setBounds(12, 280, 500, 200);
		m_2_3_panel_distributeur.add(m_textarea_notes_distributeur);	
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void bd_charge_hashmap_famille_de_plante()
	{
		m_map_famille_plante.clear();
		
		// Premier element de la liste vide
		m_map_famille_plante.put( new Integer(-1), new String("") );
   	
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery("SELECT fap_id, fap_nom FROM famille_plante ORDER BY fap_nom");
		    while (rs.next())
		    {
		    	
		    	m_map_famille_plante.put( new Integer(rs.getInt(1)), new String(rs.getString(2)) );
		    } 
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_charge_hashmap_famille_de_plante -> " + sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
	}

	//******************************************************************************************
	//
	// Ajout d'un distributeur a  la plante selectionnee (nouveau lot)
	//
	//------------------------------------------------------------------------------------------
	protected void btnAjouterDistributeurClick() 
	{
		m_ecran_lot.setVisible(true);
		m_ecran_lot.set_ecran_principal(this);
		m_ecran_lot.bd_charge_donnees_interface(m_plante_selectionnee);
		m_ecran_lot.centreWindow();		
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void btnSupprimerDistributeurClick() 
	{
		CSemencier l_semencier;
        int option;
        
        l_semencier = m_lot_plante_selectionne.bd_get_semencier();
        
		option = JOptionPane.showOptionDialog(
        		m_frmGestionDuStock,
        		"Voulez-vous retirer le semencier '"+l_semencier.get_sec_nom()+"' de la liste des distributeurs de '"+m_plante_selectionnee.get_pla_nom()+"'?","Suppression d'un distributeur de plante", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.WARNING_MESSAGE, 
				null, null, null );
        
        if( option == JOptionPane.YES_OPTION ) 
        {
        	// Suppression du distributeur
        	this.m_lot_plante_selectionne.bd_delete();
        	
        	// Rafraichir la liste des distributeurs
        	m_tableau_distributeur.bd_lance_requete(m_plante_selectionnee.get_pla_id());
        }
        else
        {
        	// Annulation de la suppression du distributeur
        }
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void btnAjouterPlanteClick() 
	{
		m_ecran_ajout_plante.setVisible(true);
		m_ecran_ajout_plante.set_ecran_principal(this);
		m_ecran_ajout_plante.bd_charge_donnees_interface();
		m_ecran_ajout_plante.centreWindow();
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void btnModifierPlanteClick() 
	{
		m_ecran_modif_plante.setVisible(true);
		m_ecran_modif_plante.set_ecran_principal(this);
		m_ecran_modif_plante.set_pla_id_selectionne(m_plante_selectionnee.get_pla_id());
		m_ecran_modif_plante.bd_charge_donnees_interface();
		m_ecran_modif_plante.centreWindow();
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void btnSupprimerPlanteClick() 
	{
		int option;
		
		option = JOptionPane.showOptionDialog(m_frmGestionDuStock,
				"Voulez-vous supprimer la plante '" + m_plante_selectionnee.get_pla_nom() + "'?",  
                "Suppression d'une plante en base de donnees", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE, 
                null, null, null ); 
		
        if( option == JOptionPane.YES_OPTION ) 
        {  
        	// On supprime la plante
        	//JOptionPane.showMessageDialog(m_frmGestionDuStock,"SUPPRESSION");
        	m_plante_selectionnee.bd_delete();
        	
        	// On efface l'objet m_plante_selectionnee
        	m_plante_selectionnee.init();
        	
        	// Rafraichir la liste de plantes
        	m_tableau_plantes.bd_lance_requete();
        	
        	// On affiche la nouvelle selection
        	
        	 this.set_pla_id_selectionne(m_tableau_plantes.getSelectedPlanteId());
        }
        else
        {
    		JOptionPane.showMessageDialog(
    				m_frmGestionDuStock,
    				"Operation de suppression annulee. Plante '" + m_plante_selectionnee.get_pla_nom() 
    															 + "' conservee en base de donnees.");   
        }
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void btnNouvelInventaireClick() 
	{		
		m_ecran_saisie_stock.bd_charge_donnees_interface();
		m_ecran_saisie_stock.set_pla_id_selectionne(m_tableau_plantes.getSelectedPlanteId());
		m_ecran_saisie_stock.setVisible(true);
		m_ecran_saisie_stock.affiche_info("");
		m_ecran_saisie_stock.centreWindow();
		//m_ecran_saisie_stock.set_ecran_principal(this);
	}

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	protected void btnNouveauMouvementClick() 
	{
		// TODO Auto-generated method stub
		
	}

	//******************************************************************************************
	//
	//  Permet d'intercepter la fermeture de l'application
	//
	//------------------------------------------------------------------------------------------
	private class AreYouSure extends WindowAdapter 
	{  
        public void windowClosing( WindowEvent e ) 
        {  
            int option;
            
            option = JOptionPane.showOptionDialog(
            			m_frmGestionDuStock,
            			"Voulez-vous quitter l'application?",
            			"Sortie de l'application", 
            			JOptionPane.YES_NO_OPTION, 
            			JOptionPane.WARNING_MESSAGE, null, null, null );
            
            if( option == JOptionPane.YES_OPTION ) 
            {  
            	System.out.println("Tentative de deconnexion JDBC");
            	try
            	{
            		// TODO: Crer une fonction pour la deconnexion SQL
            		// Fermeture de la connexion SQL
            		m_connexion_sql.close();
            		System.out.println("Deconnexion JDBC OK");
            	}
            	catch (SQLException sqle) 
            	{
        			String l_message;
        			
        			l_message = this.getClass().getName()+".AreYouSure() -> Deconnexion JDBC KO ->" + sqle.getMessage();
        			JOptionPane.showMessageDialog(null, l_message);
        		    System.err.println(l_message);
        		    System.exit(1);
            	}
            	System.out.println("Fin normale de l'application");
                System.exit( 0 );  
            }  
        }  
    }  
}
