import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class TableauInventaire  extends TableauBase
{
	//***************************************************************************
	//
	// Constructeur
	//
	//--------------------------------------------------------------------------
    public TableauInventaire() 
    {
        super(new GridLayout(1,0));
        
        m_table_model = new DefaultTableModel()
        {

			@Override
            public boolean isCellEditable(int row, int column) 
            {
               // Rendre les cases non editables
               return false;
            }
        }; 
        m_jtable = new JTable(m_table_model);
        m_table_model.addColumn("Cle"); 
        m_table_model.addColumn("Date");
        m_table_model.addColumn("Operation");
        m_table_model.addColumn("Quantite");
        m_table_model.addColumn("Peremption");

        m_jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        m_jtable.setFillsViewportHeight(true);
        
        // Definition de la largeur des colonnes
        m_jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m_jtable.getColumnModel().getColumn(0).setPreferredWidth(30);      // Colonne Cle
        m_jtable.getColumnModel().getColumn(1).setPreferredWidth(100);     // Colonne Date
        m_jtable.getColumnModel().getColumn(2).setPreferredWidth(100);     // Colonne Operation
        m_jtable.getColumnModel().getColumn(3).setPreferredWidth(100);     // Colonne Quantite
        m_jtable.getColumnModel().getColumn(4).setPreferredWidth(100);     // Colonne Peremption

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(m_jtable);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    public void bd_remplit_tableau(int p_pla_id)
    {
    	
		// Pour chaque date de péremption dans les inventaires nb de graines
		String l_chaine_sql;
		int    l_inv_id;
		CDate  l_inv_date = new CDate(); 
		int    l_inv_pla_id;
		CDate  l_inv_date_peremption = new CDate();; 
		int    l_inv_nb_plants;     // Propre à CInventaireNbPlants
		int    l_inv_nb_graines;    // Propre à CInventaireNbGraines
		int    l_inv_nb_lot;        // Propre à CInventaireLot
		int    l_inv_lot_id;        // Propre à CInventaireLot - pas forcément renseigné (dans le cas de graines auto produites)
		int    l_inv_poids_grammes; // Propre à CInventairePoids
		String l_operation = ""; 
		String l_quantite  = "";
	
		// Vider la grille
		m_table_model.setRowCount(0);

		if (p_pla_id != -1)
		{
			try 
			{
			    Statement st = m_connexion_sql.createStatement();
			    
			    // Cette requête transforme les NULL en -1 pour les quantités
			    
				l_chaine_sql=	"SELECT inv_id, inv_date, inv_dperemption,"+
								" CASE WHEN inv_nb_plants IS NULL THEN -1 ELSE inv_nb_plants END," +
								" CASE WHEN inv_nb_graines IS NULL THEN -1 ELSE inv_nb_graines END," +
								" CASE WHEN inv_nb_lot IS NULL THEN -1 ELSE inv_nb_lot END," +
								" lot_id_lot_plante," +
								" CASE WHEN inv_poids_g IS NULL THEN -1 ELSE inv_poids_g END" +
								" FROM inventaire" +
								// pour la plante choisie
								" WHERE pla_id_plante = "+p_pla_id+
								" ORDER BY inv_id DESC";
				
			    ResultSet rs = st.executeQuery(l_chaine_sql);
			    
			    while (rs.next())
			    {
					l_inv_id            = rs.getInt(1);
					l_inv_date.set_bigint(rs.getLong(2)); 
					l_inv_pla_id = p_pla_id;
					l_inv_date_peremption.set_bigint(rs.getLong(3)); 
					l_inv_nb_plants     = rs.getInt(4);     // Propre à CInventaireNbPlants
					l_inv_nb_graines    = rs.getInt(5);    // Propre à CInventaireNbGraines
					l_inv_nb_lot        = rs.getInt(6);        // Propre à CInventaireLot
					l_inv_lot_id        = rs.getInt(7);        // Propre à CInventaireLot - pas forcément renseigné (dans le cas de graines auto produites)
					l_inv_poids_grammes = rs.getInt(8); // Propre à CInventairePoids
			    	
					l_operation="Inventaire"; 
					if (l_inv_nb_graines != -1)
					{
						l_quantite = "" + l_inv_nb_graines +" graines";
					}
					else if (l_inv_nb_plants != -1)
					{
						l_quantite = "" + l_inv_nb_plants +" plants";
					}
					else if (l_inv_poids_grammes != -1)
					{
						l_quantite = "" + l_inv_poids_grammes +" grammes";
					}
					else if (l_inv_nb_lot != -1)
					{
						l_quantite = "" + l_inv_nb_lot +" lots";
					}
					ajoute_affichage(l_inv_id, l_inv_date, l_operation, l_quantite, l_inv_date_peremption);		
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
	// Remplissage du tableau
    //
	// ID / DATE / OPERATION / QUANTITE / PEREMPTION
    //
	//******************************************************************************************
	private void ajoute_affichage(int p_inv_id, CDate p_date, String p_operation, String p_quantite, CDate p_date_peremption) 
	{
		String l_date_peremption;
		if (CDate.DATE_INFINIE == p_date_peremption.get_bigint())
		{
			l_date_peremption = "sans";
		}
		else
		{
			l_date_peremption = p_date_peremption.toStringFR();
		}
    	m_table_model.addRow(new Object[]
    									{
    										""+p_inv_id, 
    										p_date.toStringFR(), 
    										p_operation,
    										p_quantite,
    										l_date_peremption
    									});
	}

}
