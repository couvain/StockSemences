import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableauDistributeurs extends TableauBase 
{

	private static final long 	serialVersionUID = 1L;

	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
    public TableauDistributeurs() 
    {
        super(new GridLayout(1,0));
        
        m_table_model = new DefaultTableModel(); 
        m_jtable = new JTable(m_table_model);
        m_table_model.addColumn("Cle"); 
        m_table_model.addColumn("Semencier"); 
        m_table_model.addColumn("Nom du lot");
        m_table_model.addColumn("Conditionnement"); 
        m_table_model.addColumn("Note"); 
        
        m_jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        m_jtable.setFillsViewportHeight(true);
        
        // Definition de la largeur des colonnes
        m_jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m_jtable.getColumnModel().getColumn(0).setPreferredWidth(30);
        m_jtable.getColumnModel().getColumn(1).setPreferredWidth(160);
        m_jtable.getColumnModel().getColumn(2).setPreferredWidth(130);
        m_jtable.getColumnModel().getColumn(3).setPreferredWidth(115);
        m_jtable.getColumnModel().getColumn(4).setPreferredWidth(40);

        
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
              	    	  EcranPrincipal l_ecran_principal=(EcranPrincipal) m_ecran_parent;
             	    	  
              	    	  l_ecran_principal.set_lot_id_selectionne(getSelectedDistributeurId());
              	    	  l_ecran_principal.bd_affiche_distributeur();
             	    	   
             	    	  //m_ecran_principal.bd_affiche_plante(1);
             	    	  
             	       }
        		   }
        	   }
        	});
        //-------------------
        m_jtable.addKeyListener(new KeyListener ()
        {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) 
            {
            	
            	int keyCode = e.getKeyCode();
                switch( keyCode ) 
                { 
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_PAGE_UP:
                    case KeyEvent.VK_PAGE_DOWN :
                    case KeyEvent.VK_HOME:
                    case KeyEvent.VK_END:
           	    	   EcranPrincipal l_ecran_principal=(EcranPrincipal) m_ecran_parent;
           	    	  l_ecran_principal.set_lot_id_selectionne(getSelectedDistributeurId());
           	    	  l_ecran_principal.bd_affiche_distributeur();
                   	
                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}       	
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
	public int getSelectedDistributeurId() 
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

	
	//***************************************************************************
	//
	//
	//
	//---------------------------------------------------------------------------
	public void bd_lance_requete(int p_pla_id) 
	{
		String l_chaine_sql         = "";
		int l_stock_poids_g;
		int l_stock_qte_graines;
		int l_stock_qte_plants;
		String l_conditionnement    = "";
		String l_lot_nom            = "";
		
		// Vider le tableau distributeurs
		m_table_model.setRowCount(0);
		
		if (p_pla_id != -1)
		{
			try 
			{
			    Statement st = m_connexion_sql.createStatement();
			    
			    l_chaine_sql = "SELECT " +
						"lot_plante.lot_id,"+
			    		"semencier.sec_nom, " +
						"lot_plante.lot_poids_g,"+   // poids grammes
						"lot_plante.lot_nb_graines,"+   // qte graines
						"lot_plante.lot_nb_plants,"+   // qte plants
			    		"lot_plante.lot_nb_etoiles," +
						"lot_plante.lot_nom "+
			    		"FROM " +
			    		"public.lot_plante " +
						"INNER JOIN semencier on lot_plante.sec_id_semencier = semencier.sec_id "+
			    		"INNER JOIN plante on lot_plante.pla_id_plante = plante.pla_id " +
			    		"WHERE plante.pla_id =" + p_pla_id + ";";
				
			    ResultSet rs = st.executeQuery(l_chaine_sql);
			    
			    while (rs.next())
			    {
			    	// Remplissage du tableau
					l_stock_poids_g     =  rs.getInt(3);
					l_stock_qte_graines =  rs.getInt(4);
					l_stock_qte_plants  =  rs.getInt(5);
					l_lot_nom           =  rs.getString(7);

					if (l_stock_poids_g != 0)
					{
						l_conditionnement = l_stock_poids_g+" grammes";
					}
					else if (l_stock_qte_graines != 0)
					{
						l_conditionnement = l_stock_qte_graines+" graines";
					}
					else if (l_stock_qte_plants != 0)
					{
						l_conditionnement = l_stock_qte_plants+" plants";
					}
					else
					{
						// TODO: message d'erreur à ajouter
						// rien n'est remonté; pas de plante en stock
						l_conditionnement = "vide";
					}
			    	
			    	m_table_model.addRow(new Object[]{rs.getString(1), rs.getString(2), l_lot_nom,l_conditionnement, rs.getString(6)});
			    } 
			    rs.close();
			    st.close(); 
			}
			catch (SQLException sqle) 
			{
				String l_message;
				
				l_message = this.getClass().getName()+".bd_lance_requete() -> "+sqle.getMessage();
				JOptionPane.showMessageDialog(null, l_message);
			    System.err.println(l_message);
			    System.exit(1);
			}
		}
	}
	
}
