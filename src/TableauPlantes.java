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

public class TableauPlantes extends TableauBase 
{
 	private static final long serialVersionUID = 1L;
	//private Connection          m_connexion_sql = null;
	//private JTable              m_jtable_plantes = null;
	//private DefaultTableModel   m_table_model = null;
	//private EcranPrincipal      m_ecran_principal;
	
	//***************************************************************************
	//
	// Constructeur
	//
	//--------------------------------------------------------------------------
    public TableauPlantes() 
    {
        super(new GridLayout(1,0));
        
        m_table_model = new DefaultTableModel()
        {
			private static final long serialVersionUID = -6618499995948516988L;

			@Override
            public boolean isCellEditable(int row, int column) 
            {
               // Rendre les cases non editables
               return false;
            }
        }; 

        m_jtable = new JTable(m_table_model);
        
        // Création des colonnes
        m_table_model.addColumn("Cle"); 
        m_table_model.addColumn("Plante"); 
        m_table_model.addColumn("Stock"); 

        m_jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        m_jtable.setFillsViewportHeight(true);
        
        // Definition de la largeur des colonnes
        m_jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m_jtable.getColumnModel().getColumn(0).setPreferredWidth(30);     // Colonne Cle
        m_jtable.getColumnModel().getColumn(1).setPreferredWidth(160);    // Colonne Plante
        m_jtable.getColumnModel().getColumn(2).setPreferredWidth(200);    // Colonne Stock
        
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
             	    	   // Selectionne et affiche la nouvelle plante selectionnee
               	    	  EcranPrincipal l_ecran_principal=(EcranPrincipal) m_ecran_parent;
               	    	  l_ecran_principal.set_pla_id_selectionne(getSelectedPlanteId());
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
          	    	   l_ecran_principal.set_pla_id_selectionne(getSelectedPlanteId());
                   	
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
	public int getSelectedPlanteId() 
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
	// Lance une évaluation du stock de chaque plante du tableau à la date du jour
	//
	//---------------------------------------------------------------------------
	public void bd_lance_requete() 
	{
		// Vider le tableau plantes
		m_table_model.setRowCount(0);
		CDate l_date_du_jour = new CDate();
		l_date_du_jour.init_date_du_jour();
		
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    // Requête du tableau plantes de l'écran principal - trié par nom de plante
		    ResultSet rs = st.executeQuery(
		    		"SELECT plante.pla_id, plante.pla_nom" +
		    		" FROM public.plante" +
		    		" ORDER BY plante.pla_nom;");
		    
		    while (rs.next())
		    {
		    	// Lecture du stock
		    	int  l_plante_id = rs.getInt(1);
		    	String l_stock_plante_texte = bd_lit_stock_plante_texte(l_plante_id,l_date_du_jour.get_bigint());
		    	
		    	// Remplissage du tableau
		    	m_table_model.addRow(new Object[]{rs.getString(1), rs.getString(2),l_stock_plante_texte});
		    } 
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_lance_requete() -> " + sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
	}


	private String bd_lit_stock_plante_texte(int p_plante_id, long p_date_evaluation) {
		String l_resultat;
		String l_plants  = "";
		String l_lots    = "";
		String l_graines = "";
		String l_poids   = "";
		CPlante l_plante = new CPlante();
		
		int l_nb_graines_ok;
		int l_nb_plants_ok;
		int l_nb_lots_ok;
		int l_poids_g_ok;
		
		int l_nb_graines_ko;
		int l_nb_plants_ko;
		int l_nb_lots_ko;
		int l_poids_g_ko;
		
		l_plante.set_pla_id(p_plante_id);
		
		// Lectures des plantes non périmées
		l_nb_graines_ok = l_plante.bd_lit_stock_plante_nb_graines_ok(p_date_evaluation);
		l_nb_plants_ok  = l_plante.bd_lit_stock_plante_nb_plants_ok(p_date_evaluation);
		l_nb_lots_ok    = l_plante.bd_lit_stock_plante_nb_lots_ok(p_date_evaluation);
		l_poids_g_ok    = l_plante.bd_lit_stock_plante_poids_g_ok(p_date_evaluation);
		
		// Lectures des plantes périmées
		l_nb_graines_ko = l_plante.bd_lit_stock_plante_nb_graines_ko(p_date_evaluation);
		l_nb_plants_ko  = l_plante.bd_lit_stock_plante_nb_plants_ko(p_date_evaluation);
		l_nb_lots_ko    = l_plante.bd_lit_stock_plante_nb_lots_ko(p_date_evaluation);
		l_poids_g_ko    = l_plante.bd_lit_stock_plante_poids_g_ko(p_date_evaluation);
		
		if ((l_nb_plants_ok != 0) || (l_nb_plants_ko != 0))
		{
			l_plants  ="("+l_nb_plants_ok  + "|" + l_nb_plants_ko  + ") plants ";
		}
		if ((l_nb_lots_ok != 0) || (l_nb_lots_ko != 0))
		{
			l_lots    ="("+l_nb_lots_ok    + "|" + l_nb_lots_ko    + ") lots";
		}
		if ((l_nb_graines_ok != 0) || (l_nb_graines_ko != 0))
		{
			l_graines ="("+l_nb_graines_ok + "|" + l_nb_graines_ko + ") graines ";
		}
		if ((l_poids_g_ok != 0) || (l_poids_g_ko != 0))
		{
			l_poids   ="("+l_poids_g_ok    + "|" + l_poids_g_ko    + ") grammes ";
		}
		
		l_resultat = l_plants + l_graines + l_poids + l_lots;
					 
		return l_resultat;
	}
 }
