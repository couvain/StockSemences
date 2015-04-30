import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class EcranFamillePlante extends EcranBase 
{
	private static final long  serialVersionUID = 1L;

	private JPanel               m_contentPane;
	private TableauFamillePlante m_liste_famille_plante;           // Affichage de la liste des semenciers
	private EcranPlante          m_ecran_plante;
	private JButton              m_btnAjouterFamillePlante;
	private JButton              m_btnSupprimerFamillePlante;
	private JButton              m_btnFermer;

	
	public EcranFamillePlante() 
	{
		// dimensions de la fenêtre
		this.setBounds(10, 10, 330, 535);
		centreWindow();
		m_contentPane = new JPanel();
		m_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		m_contentPane.setBackground(SystemColor.window);
		
		// On se met en coordonnees relatives
		m_contentPane.setLayout(null);
		
		// Liste de semenciers en base de donnees
		m_liste_famille_plante = new TableauFamillePlante(); 
		m_liste_famille_plante.setBounds(15, 15,300,400);
		m_contentPane.add(m_liste_famille_plante);
		
		// Bouton Ajouter semencier
		m_btnAjouterFamillePlante = new JButton("Ajouter");
		m_btnAjouterFamillePlante.setToolTipText("Ajouter une nouvelle famille de plante à la base de données");
		m_btnAjouterFamillePlante.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnAjouterFamillePlanteClick();
			}
		});
		m_btnAjouterFamillePlante.setBounds(15, 425, 117, 25);
		m_contentPane.add(m_btnAjouterFamillePlante);
		
		// Bouton Supprimer Famille
		m_btnSupprimerFamillePlante = new JButton("Supprimer");
		m_btnSupprimerFamillePlante.setToolTipText("Supprimer la famille de plante sélectionnée de la base de données");
		m_btnSupprimerFamillePlante.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnSupprimerFamillePlanteClick();
			}
		});
		m_btnSupprimerFamillePlante.setBounds(197, 425, 117, 25);
		m_contentPane.add(m_btnSupprimerFamillePlante);		
		//---------------------------------------------------
		// Bouton Fermer
		//---------------------------------------------------
		m_btnFermer = new JButton("Fermer");
		m_btnFermer.setToolTipText("Fermer la fenêtre Famille de Plante");
		m_btnFermer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnFermerClick();
			}
		});
		m_btnFermer.setBounds(110, 465, 117, 25);
		m_contentPane.add(m_btnFermer);
		
		setContentPane(m_contentPane);
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnFermerClick() 
	{
		setVisible(false); 
		dispose(); 
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void bd_lance_requete()
	{
		m_liste_famille_plante.bd_lance_requete();
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnAjouterFamillePlanteClick() 
	{
		String l_nom_famille_plante;

		l_nom_famille_plante = JOptionPane.showInputDialog("Entrez le nom de la famille de plante a ajouter");
		
		if (l_nom_famille_plante != null)
		{		
			l_nom_famille_plante = l_nom_famille_plante.trim();
			
			if (l_nom_famille_plante != "")
			{
				CFamillePlante l_famille_plante = new CFamillePlante();
				
				l_famille_plante.set_fap_nom(l_nom_famille_plante);
		
				if (l_famille_plante.bd_existe())
				{
					JOptionPane.showMessageDialog(null, "La famille de plante existe deja dans la base de donnees. Operation annulee");
				}
				else
				{
					// On ajoute l'element en BDD
					l_famille_plante.bd_insert();
					
					// On rafraichit la fenetre
					bd_lance_requete();				
					
					// On rafraichit la fenetre appelante;
					m_ecran_plante.bd_charge_donnees_interface();
				}
			}
			else
			{
				// On n'a rien saisi dans le champ de l'inputDialog
				JOptionPane.showMessageDialog(null, "Aucun nom de famille de plante n'a ete saisi. Operation annulee");
			}
		}
		else
		{
			// On a appuye sur Cancel
		}
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnSupprimerFamillePlanteClick() 
	{
		int l_sec_id;
		
		l_sec_id = m_liste_famille_plante.getSelectedFamillePlanteId();
		if ( l_sec_id != -1 )
		{
			
			CFamillePlante l_famille_plante = new CFamillePlante();
			
			l_famille_plante.set_fap_id(l_sec_id);
			
			l_famille_plante.bd_select();
			//l_semencier.affiche();
			
			int l_choix = JOptionPane.showConfirmDialog(null, "Confirmez-vous la suppression de la famille de plante '"+l_famille_plante.get_fap_nom()+"'?", "Suppression d'un semencier", JOptionPane.YES_NO_OPTION);
			
			//JOptionPane.showMessageDialog(null,""+ l_choix);
			if (l_choix == JOptionPane.YES_OPTION)
			{
				// On supprime l'element en BDD
				l_famille_plante.bd_delete();
				
				// On rafraichit la fenetre
				bd_lance_requete();	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Suppression de la famille de plante '"+l_famille_plante.get_fap_nom()+"' annulee");
			}
			//-----------------------------------------
			//String l_semencier_a_supprimer;
			//l_semencier_a_supprimer = (String)m_liste_semencier.getSelectedValue();
			//JOptionPane.showMessageDialog(null,"Vous voulez supprimer le semencier "+l_semencier_a_supprimer+" - NON IMPLEMENTE");
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Aucune famille de plante n'est selectionnee. Action annulee.");
		}
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_connexion_sql(Connection p_connexion_sql) 
	{
		super.set_connexion_sql(p_connexion_sql);
		m_liste_famille_plante.set_connexion_sql(p_connexion_sql);
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public EcranPlante get_ecran_plante() 
	{
		return m_ecran_plante;
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_ecran_plante(EcranPlante m_ecran_plante) 
	{
		this.m_ecran_plante = m_ecran_plante;
	}	
}

