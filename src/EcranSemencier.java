import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class EcranSemencier extends EcranBase 
{

	private static final long serialVersionUID = 1L;
	private JPanel              m_contentPane;
	private TableauSemenciers   m_tableau_semencier;           // Affichage de la liste des semenciers
	private EcranLot            m_ecran_lot;
	
	public EcranSemencier() 
	{
		this.setBounds(10, 10, 330, 550);
		centreWindow();
		m_contentPane = new JPanel();
		m_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		m_contentPane.setBackground(SystemColor.window);
		
		// On se met en coordonnees relatives
		m_contentPane.setLayout(null);
		
		// Liste de semenciers en base de donnees
		m_tableau_semencier = new TableauSemenciers(); 
		m_tableau_semencier.setBounds(15, 15,300,400);
		m_contentPane.add(m_tableau_semencier);
		
		//-------------------------------------------
		// Bouton Ajouter semencier
		//-------------------------------------------
		JButton btnAjouterSemencier = new JButton("Ajouter");
		btnAjouterSemencier.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnAjouterSemencierClick();
			}
		});
		btnAjouterSemencier.setBounds(15, 420, 117, 25);
		m_contentPane.add(btnAjouterSemencier);
		
		//-------------------------------------------
		// Bouton Supprimer Famille
		//-------------------------------------------
		JButton btnSupprimerSemencier = new JButton("Supprimer");
		btnSupprimerSemencier.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnSupprimerSemencierClick();
			}
		});
		btnSupprimerSemencier.setBounds(197, 420, 117, 25);
		m_contentPane.add(btnSupprimerSemencier);	
		
		//---------------------------------------------------
		// Bouton Fermer
		//---------------------------------------------------
		JButton btnFermer = new JButton("Fermer");
		btnFermer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnFermerClick();
			}
		});
		btnFermer.setBounds(110, 465, 117, 25);
		m_contentPane.add(btnFermer);
		
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
		m_tableau_semencier.bd_lance_requete();
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnAjouterSemencierClick() 
	{
		String l_nom_semencier;

		l_nom_semencier = JOptionPane.showInputDialog("Entrez le nom du semencier a ajouter");
		
		if (l_nom_semencier != null)
		{		
			l_nom_semencier = l_nom_semencier.trim();
			
			if (l_nom_semencier != "")
			{
				CSemencier l_semencier = new CSemencier();
				
				l_semencier.set_sec_nom(l_nom_semencier);
		
				if (l_semencier.bd_existe())
				{
					JOptionPane.showMessageDialog(null, "Le semencier existe deja  dans la base de donnees. Operation annulee");
				}
				else
				{
					// On ajoute l'element en BDD
					l_semencier.bd_insert();
					
					// On rafraichit la fenetre
					bd_lance_requete();			
					
					// On rafraichit la combobox semencier de l'ecran lot
					m_ecran_lot.bd_charge_combobox_semencier();
				}
			}
			else
			{
				// On n'a rien saisi dans le champ de l'inputDialog
				JOptionPane.showMessageDialog(null, "Aucun nom de semencier n'a ete saisi. Operation annulee");
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
	protected void btnSupprimerSemencierClick() 
	{
		int l_sec_id;
		
		l_sec_id = m_tableau_semencier.getSelectedSemencierId();
		if ( l_sec_id != -1 )
		{
			CSemencier l_semencier = new CSemencier();
			
			l_semencier.set_sec_id(l_sec_id);	
			l_semencier.bd_select();
			//l_semencier.affiche();
			
			int l_choix = JOptionPane.showConfirmDialog(null, "Confirmez-vous la suppression du semencier '"+l_semencier.get_sec_nom()+"'?", "Suppression d'un semencier", JOptionPane.YES_NO_OPTION);
			
			//JOptionPane.showMessageDialog(null,""+ l_choix);
			if (l_choix == JOptionPane.YES_OPTION)
			{
				// On supprime l'element en BDD
				l_semencier.bd_delete();
				
				// On rafraichit la fenetre
				bd_lance_requete();	
				
				// On rafraichit la combobox semencier de l'Ã©cran lot
				m_ecran_lot.bd_charge_combobox_semencier();
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Suppression du semencier '"+l_semencier.get_sec_nom()+"' annulee");
			}
			//-----------------------------------------
			//String l_semencier_a_supprimer;
			//l_semencier_a_supprimer = (String)m_liste_semencier.getSelectedValue();
			//JOptionPane.showMessageDialog(null,"Vous voulez supprimer le semencier "+l_semencier_a_supprimer+" - NON IMPLEMENTE");
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Aucun semencier n'est selectionne. Action annulee.");
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
		m_tableau_semencier.set_connexion_sql(p_connexion_sql);
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void set_ecran_lot(EcranLot p_ecran_lot) 
	{
		m_ecran_lot = p_ecran_lot;
	}
		
}
