import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class TableauStock extends TableauBase 
{
	private static final long 	serialVersionUID = 1L;

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
    public TableauStock() 
    {
        super(new GridLayout(1,0));
        
        m_table_model = new DefaultTableModel(); 
        m_jtable = new JTable(m_table_model)
        {
			private static final long serialVersionUID = 6181340638571094491L;

			// Système de rendu en couleur des lignes du tableau de stock
        	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
        	{
        		Component c = super.prepareRenderer(renderer, row, column);
        		
        		// Add custom rendering here
        		if (!isRowSelected(row))
        		{
        			c.setBackground(getBackground());
        			int modelRow = convertRowIndexToModel(row);
        			String l_date_peremption = (String)getModel().getValueAt(modelRow, 2);
        			if ("sans".equals(l_date_peremption))
        			{
        				// Orange pour une ligne sans date de péremption
        				c.setBackground(Color.ORANGE);
        			}
        			else
        			{
        				String l_date2 = new String();
        				l_date2=l_date_peremption.substring(6)+l_date_peremption.substring(3,5)+l_date_peremption.substring(0,2);
	        			if (Long.parseLong(l_date2)>CDate.get_date_du_jour_bigint())
	        			{
	        				// Blanc pour une ligne non périmée 
	        				c.setBackground(Color.WHITE);
	        			}
	        			else
	        			{
	        				// Rouge pour une ligne ayant périmée
	        				c.setBackground(Color.RED);
	        			}
        			}
        		}
        		
        		
        		return c;
        	}
        	
        	
        };
        m_table_model.addColumn("Stock"); 
        m_table_model.addColumn("Lot"); 
        m_table_model.addColumn("Péremption"); 
        
        m_jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        m_jtable.setFillsViewportHeight(true);
        
        // Definition de la largeur des colonnes
        m_jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m_jtable.getColumnModel().getColumn(0).setPreferredWidth(200);
        m_jtable.getColumnModel().getColumn(1).setPreferredWidth(150);
        m_jtable.getColumnModel().getColumn(2).setPreferredWidth(100);

        m_jtable.addMouseListener(new MouseAdapter() 
        {
        	   public void mouseClicked(MouseEvent e) 
        	   {
         		   if (e.getButton() == java.awt.event.MouseEvent.BUTTON3)
        		   {
        			   // bouton droit de la souris 
        			   // Ne rien faire
        		   }
        		   else
        		   {
        			   // bouton gauche de la souris
             	       if (e.getClickCount() == 1) 
             	       {
             	    	   /* 
             	    	  EcranPrincipal l_ecran_principal=(EcranPrincipal) m_ecran_parent;
             	    	  
             	    	  l_ecran_principal.set_lot_id_selectionne(getSelectedStockId());
             	    	  l_ecran_principal.bd_affiche_distributeur();
             	    	   
             	    	  //m_ecran_principal.bd_affiche_plante(1);
             	    	   
             	    	   */
             	    	  
             	       }
        		   }
        	   }
        	});
    
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(m_jtable);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

	//***************************************************************************
	//
	//
	//
	//---------------------------------------------------------------------------
    /*
	public int getSelectedStockId() 
	{
		if (m_jtable.getSelectedRow() == -1)
		{
			// Aucune selection n'a ete faite -> on renvoie -1
			return -1;
		}
		else
		{
			return  Integer.parseInt((String)m_jtable.getValueAt(m_jtable.getSelectedRow(), 0).toString());
		}
	}
     */
    
    
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void bd_remplit_tableau(int p_pla_id)
	{
		// Vider la grille
		m_table_model.setRowCount(0);
		
		// Pour la plante sélectionnée
		// Pour chaque conditionnement
		bd_remplit_tableau_poids_g(p_pla_id);
		bd_remplit_tableau_nb_graines(p_pla_id);
		bd_remplit_tableau_nb_plants(p_pla_id);
		bd_remplit_tableau_nb_lots1(p_pla_id);
	}
	

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	private int bd_calcule_stock_nb_graines(int p_pla_id, long l_date_peremption) 
	{
		CInventaireNbGraines l_dernier_inventaire = new CInventaireNbGraines(); 
		int l_entrees_stock;
		int l_sorties_stock;
		
		// Prendre l'inventaire le plus récent pour la plante à la date de peremption définie
		// Infos utiles: valeur_inventaire / date_inventaire
		l_dernier_inventaire.bd_select_le_plus_recent_a_dperemption(p_pla_id,l_date_peremption);
		
		// Ajouter les entrées en stock à la même date de préremption pour la plante choisie
		l_entrees_stock = CPlante.bd_get_entrees_stock_nb_graines_a_dperemption_apres_dernier_inventaire(p_pla_id,l_date_peremption,l_dernier_inventaire.get_inv_date().get_bigint());
		
		// Retirer les sorties de stock à la même date de péremption pour la plante choisie
		l_sorties_stock = CPlante.bd_get_sorties_stock_nb_graines_a_dperemption_apres_dernier_inventaire(p_pla_id,l_date_peremption,l_dernier_inventaire.get_inv_date().get_bigint());
		
		return l_dernier_inventaire.get_inv_nb_graines() + l_entrees_stock - l_sorties_stock;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	private int bd_calcule_stock_nb_plants(int p_pla_id, long l_date_peremption) 
	{
		CInventaireNbPlants l_dernier_inventaire = new CInventaireNbPlants(); 
		int l_entrees_stock;
		int l_sorties_stock;
		
		// Prendre l'inventaire le plus récent pour la plante à la date de peremption définie
		// Infos utiles: valeur_inventaire / date_inventaire
		l_dernier_inventaire.bd_select_le_plus_recent_a_dperemption(p_pla_id,l_date_peremption);
		
		// Ajouter les entrées en stock à la même date de préremption pour la plante choisie
		l_entrees_stock = CPlante.bd_get_entrees_stock_nb_plants_a_dperemption_apres_dernier_inventaire(p_pla_id,l_date_peremption,l_dernier_inventaire.get_inv_date().get_bigint());
		
		// Retirer les sorties de stock à la même date de péremption pour la plante choisie
		l_sorties_stock = CPlante.bd_get_sorties_stock_nb_plants_a_dperemption_apres_dernier_inventaire(p_pla_id,l_date_peremption,l_dernier_inventaire.get_inv_date().get_bigint());
		
		return l_dernier_inventaire.get_inv_nb_plants() + l_entrees_stock - l_sorties_stock;
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	private int bd_calcule_stock_poids_g(int p_pla_id, long l_date_peremption) 
	{
		CInventairePoids l_dernier_inventaire = new CInventairePoids(); 
		int l_entrees_stock;
		int l_sorties_stock;
		
		// Prendre l'inventaire le plus récent pour la plante à la date de peremption définie
		// Infos utiles: valeur_inventaire / date_inventaire
		l_dernier_inventaire.bd_select_le_plus_recent_a_dperemption(p_pla_id,l_date_peremption);
		
		// Ajouter les entrées en stock à la même date de préremption pour la plante choisie
		l_entrees_stock = CPlante.bd_get_entrees_stock_poids_g_a_dperemption_apres_dernier_inventaire(p_pla_id,l_date_peremption,l_dernier_inventaire.get_inv_date().get_bigint());
		
		// Retirer les sorties de stock à la même date de péremption pour la plante choisie
		l_sorties_stock = CPlante.bd_get_sorties_stock_poids_g_a_dperemption_apres_dernier_inventaire(p_pla_id,l_date_peremption,l_dernier_inventaire.get_inv_date().get_bigint());
		
		return l_dernier_inventaire.get_inv_poids_grammes() + l_entrees_stock - l_sorties_stock;
	}
	
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	private int bd_calcule_stock_nb_lots(int p_pla_id, int p_lot_id, long l_date_peremption) 
	{
		CInventaireLot l_dernier_inventaire = new CInventaireLot(); 
		int l_entrees_stock;
		int l_sorties_stock;
		
		// Prendre l'inventaire le plus récent pour la plante à la date de peremption définie
		// Infos utiles: valeur_inventaire / date_inventaire
		l_dernier_inventaire.bd_select_le_plus_recent_a_dperemption(p_pla_id,p_lot_id,l_date_peremption);
		
		// Ajouter les entrées en stock à la même date de préremption pour la plante choisie
		l_entrees_stock = CPlante.bd_get_entrees_stock_nb_lots_a_dperemption_apres_dernier_inventaire(p_pla_id,p_lot_id,l_date_peremption,l_dernier_inventaire.get_inv_date().get_bigint());
		
		// Retirer les sorties de stock à la même date de péremption pour la plante choisie
		l_sorties_stock = CPlante.bd_get_sorties_stock_nb_lots_a_dperemption_apres_dernier_inventaire(p_pla_id,p_lot_id,l_date_peremption,l_dernier_inventaire.get_inv_date().get_bigint());
		
		return l_dernier_inventaire.get_inv_nb_lot() + l_entrees_stock - l_sorties_stock;
	}

	//******************************************************************************************
	//
	// Remplissage du tableau
	//
	//******************************************************************************************
	private void ajoute_affichage_stock_nb_graines(int p_pla_id, long p_date_peremption, int p_valeur_stock) 
	{
		String l_date_peremption;
		if (CDate.DATE_INFINIE == p_date_peremption)
		{
			l_date_peremption = "sans";
		}
		else
		{
			l_date_peremption = CDate.bigintToStringFR(p_date_peremption);
		}
    	m_table_model.addRow(new Object[]{""+ p_valeur_stock +" graines", "", l_date_peremption});
    	
	}
	
	//******************************************************************************************
	//
	// Remplissage du tableau
	//
	//******************************************************************************************
	private void ajoute_affichage_stock_nb_plants(int p_pla_id, long p_date_peremption, int p_valeur_stock) 
	{
		String l_date_peremption;
		if (CDate.DATE_INFINIE == p_date_peremption)
		{
			l_date_peremption = "sans";
		}
		else
		{
			l_date_peremption = CDate.bigintToStringFR(p_date_peremption);
		}
    	m_table_model.addRow(new Object[]{""+ p_valeur_stock +" plants", "", l_date_peremption});
	}
	
	//******************************************************************************************
	//
	// Remplissage du tableau
	//
	//******************************************************************************************
	private void ajoute_affichage_stock_poids_g(int p_pla_id, long p_date_peremption, int p_valeur_stock) 
	{
		String l_date_peremption;
		if (CDate.DATE_INFINIE == p_date_peremption)
		{
			l_date_peremption = "sans";
		}
		else
		{
			l_date_peremption = CDate.bigintToStringFR(p_date_peremption);
		}
    	m_table_model.addRow(new Object[]{""+ p_valeur_stock +" grammes", "", l_date_peremption});
	}
	
	//******************************************************************************************
	//
	// Remplissage du tableau
	//
	//******************************************************************************************
	private void ajoute_affichage_stock_nb_lots(int p_pla_id, long p_date_peremption, int p_valeur_stock, String p_lot_nom, int p_lot_nb_plants, int p_lot_nb_graines, int p_lot_poids_g) 
	{
		String l_date_peremption;
		if (CDate.DATE_INFINIE == p_date_peremption)
		{
			l_date_peremption = "sans";
		}
		else
		{
			l_date_peremption = CDate.bigintToStringFR(p_date_peremption);
		}
    	m_table_model.addRow(new Object[]{""+ p_valeur_stock +" lots", p_lot_nom, l_date_peremption});
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	private void bd_remplit_tableau_nb_graines(int p_pla_id) 
	{
		// Pour chaque date de péremption dans les inventaires nb de graines
		long l_date_peremption;
		int l_valeur_stock;
		String l_chaine_sql; 

		if (p_pla_id != -1)
		{
			try 
			{
			    Statement st = m_connexion_sql.createStatement();
			    
				l_chaine_sql="SELECT DISTINCT inv_dperemption " +
						"FROM inventaire " +
						// pour la plante choisie
						" WHERE pla_id_plante = "+p_pla_id+
						// pour un inventaire en nb_de_graines
						" AND inv_nb_graines is not null";
				
			    ResultSet rs = st.executeQuery(l_chaine_sql);
			    
			    while (rs.next())
			    {
			    	// Pour chaque date de péremption pour la plante choisie calculer le stock 
			    	l_date_peremption = rs.getLong(1);
			    	l_valeur_stock = bd_calcule_stock_nb_graines(p_pla_id,l_date_peremption);
			    	if (l_valeur_stock > 0)
			    	{
			    		ajoute_affichage_stock_nb_graines(p_pla_id,l_date_peremption,l_valeur_stock);
			    	}
			    }
			    rs.close();
			    st.close(); 
			}
			catch (SQLException sqle) 
			{
				String l_message;
				
				l_message = this.getClass().getName()+".bd_remplit_tableau_nb_graines() ->"+sqle.getMessage();
				JOptionPane.showMessageDialog(null, l_message);
			    System.err.println(l_message);
			    System.exit(1);
			}
		}
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	private void bd_remplit_tableau_poids_g(int p_pla_id) 
	{
		// Pour chaque date de péremption dans les inventaires poids en grammes
		long l_date_peremption;
		int l_valeur_stock;
		String l_chaine_sql; 

		if (p_pla_id != -1)
		{
			try 
			{
			    Statement st = m_connexion_sql.createStatement();
			    
				l_chaine_sql="SELECT DISTINCT inv_dperemption " +
						"FROM inventaire " +
						// pour la plante choisie
						" WHERE pla_id_plante = "+p_pla_id+
						// pour un inventaire en poids grammes
						" AND inv_poids_g is not null";
				
			    ResultSet rs = st.executeQuery(l_chaine_sql);
			    
			    while (rs.next())
			    {
			    	// Pour chaque date de péremption pour la plante choisie calculer le stock 
			    	l_date_peremption = rs.getLong(1);
			    	l_valeur_stock = bd_calcule_stock_poids_g(p_pla_id,l_date_peremption);
			    	if (l_valeur_stock > 0)
			    	{
			    		ajoute_affichage_stock_poids_g(p_pla_id,l_date_peremption,l_valeur_stock);
			    	}
			    }
			    rs.close();
			    st.close(); 
			}
			catch (SQLException sqle) 
			{
				String l_message;
				
				l_message = this.getClass().getName()+".bd_remplit_tableau_poids_g() ->"+sqle.getMessage();
				JOptionPane.showMessageDialog(null, l_message);
			    System.err.println(l_message);
			    System.exit(1);
			}
		}
		
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	private void bd_remplit_tableau_nb_plants(int p_pla_id) 
	{
		// Pour chaque date de péremption dans les inventaires nb de plants

		long l_date_peremption;
		int l_valeur_stock;
		String l_chaine_sql; 

		if (p_pla_id != -1)
		{
			try 
			{
			    Statement st = m_connexion_sql.createStatement();
			    
				l_chaine_sql="SELECT DISTINCT inv_dperemption " +
						"FROM inventaire " +
						// pour la plante choisie
						" WHERE pla_id_plante = "+p_pla_id+
						// pour un inventaire en nb_de_plants
						" AND inv_nb_plants is not null";
				
			    ResultSet rs = st.executeQuery(l_chaine_sql);
			    
			    while (rs.next())
			    {
			    	// Pour chaque date de péremption pour la plante choisie calculer le stock 
			    	l_date_peremption = rs.getLong(1);
			    	l_valeur_stock = bd_calcule_stock_nb_plants(p_pla_id,l_date_peremption);
			    	if (l_valeur_stock > 0)
			    	{
			    		ajoute_affichage_stock_nb_plants(p_pla_id,l_date_peremption,l_valeur_stock);
			    	}
			    }
			    rs.close();
			    st.close(); 
			}
			catch (SQLException sqle) 
			{
				String l_message;
				
				l_message = this.getClass().getName()+".bd_remplit_tableau_nb_plants() ->"+sqle.getMessage();
				JOptionPane.showMessageDialog(null, l_message);
			    System.err.println(l_message);
			    System.exit(1);
			}
		}
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	private void bd_remplit_tableau_nb_lots1(int p_pla_id) 
	{
		// Pour chaque type de lot différent dans les types de lots
		int l_lot_id;
		String l_lot_nom;
		int l_lot_nb_plants;
		int l_lot_nb_graines;
		int l_lot_poids_g;
		
		String l_chaine_sql; 

		if (p_pla_id != -1)
		{
			try 
			{
			    Statement st = m_connexion_sql.createStatement();
			    
				l_chaine_sql="SELECT DISTINCT lot_id,lot_nom,lot_nb_plants,lot_nb_graines,lot_poids_g " +
						"FROM lot_plante " +
						// pour la plante choisie
						" WHERE pla_id_plante = "+p_pla_id;
				
			    ResultSet rs = st.executeQuery(l_chaine_sql);
			    
			    while (rs.next())
			    {
			    	// Pour chaque date de péremption dans les inventaires du type de lot choisi
			    	// Pour chaque date de péremption pour la plante choisie calculer le stock 
			    	l_lot_id         = rs.getInt(1);
			    	l_lot_nom        = rs.getString(2);
					l_lot_nb_plants  = rs.getInt(3);
					l_lot_nb_graines = rs.getInt(4);
					l_lot_poids_g    = rs.getInt(5);

					bd_remplit_tableau_nb_lots2(p_pla_id,l_lot_id,l_lot_nom,l_lot_nb_plants,l_lot_nb_graines,l_lot_poids_g);
			    }
			    rs.close();
			    st.close(); 
			}
			catch (SQLException sqle) 
			{
				String l_message;
				
				l_message = this.getClass().getName()+".bd_remplit_tableau_nb_lots1() ->"+sqle.getMessage();
				JOptionPane.showMessageDialog(null, l_message);
			    System.err.println(l_message);
			    System.exit(1);
			}
		}
		  
	}

	private void bd_remplit_tableau_nb_lots2(int p_pla_id, int p_lot_id,String p_lot_nom, int p_lot_nb_plants, int p_lot_nb_graines,int p_lot_poids_g) 
	{
		// Pour chaque date de péremption dans les inventaires nb de plants

		long l_date_peremption;
		int l_valeur_stock;
		String l_chaine_sql; 

		if (p_pla_id != -1)
		{
			try 
			{
			    Statement st = m_connexion_sql.createStatement();
			    
				l_chaine_sql="SELECT DISTINCT inv_dperemption " +
						"FROM inventaire " +
						// pour la plante choisie
						" WHERE pla_id_plante = "+p_pla_id+
						// pour un inventaire en nb de lots
						" AND lot_id_lot_plante is not null";
				
			    ResultSet rs = st.executeQuery(l_chaine_sql);
			    
			    while (rs.next())
			    {
			    	// Pour chaque date de péremption pour la plante choisie calculer le stock 
			    	l_date_peremption = rs.getLong(1);
			    	l_valeur_stock = bd_calcule_stock_nb_lots(p_pla_id, p_lot_id, l_date_peremption);
			    	if (l_valeur_stock > 0)
			    	{
			    		ajoute_affichage_stock_nb_lots(p_pla_id, l_date_peremption, l_valeur_stock, p_lot_nom, p_lot_nb_plants, p_lot_nb_graines, p_lot_poids_g);
			    	}
			    }
			    rs.close();
			    st.close(); 
			}
			catch (SQLException sqle) 
			{
				String l_message;
				
				l_message = this.getClass().getName()+".bd_remplit_tableau_nb_plants() ->"+sqle.getMessage();
				JOptionPane.showMessageDialog(null, l_message);
			    System.err.println(l_message);
			    System.exit(1);
			}
		}
	}

	
}
