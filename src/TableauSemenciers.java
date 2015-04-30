import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableauSemenciers extends TableauBase 
{
 	private static final long serialVersionUID = 1L;
	
	//***************************************************************************
	//
	// Constructeur
	//
	//--------------------------------------------------------------------------
    public TableauSemenciers() 
    {
        super(new GridLayout(1,0));
        
        m_table_model = new DefaultTableModel()
        {
			private static final long serialVersionUID = 1240139939845556980L;

			@Override
            public boolean isCellEditable(int row, int column) 
            {
               // Rendre les cases non editables
               return false;
            }
        }; 
        m_jtable = new JTable(m_table_model);
        m_table_model.addColumn("Cle"); 
        m_table_model.addColumn("Nom"); 

        m_jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        m_jtable.setFillsViewportHeight(true);
        
        // Definition de la largeur des colonnes
        m_jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m_jtable.getColumnModel().getColumn(0).setPreferredWidth(30);      // Colonne Cle
        m_jtable.getColumnModel().getColumn(1).setPreferredWidth(250);     // Colonne Nom

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
	public int getSelectedSemencierId() 
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
	public void bd_lance_requete() 
	{
		// Vider la grille semenciers
		m_table_model.setRowCount(0);
		
		try 
		{
		    Statement st = m_connexion_sql.createStatement();
		    ResultSet rs = st.executeQuery("SELECT sec_id, sec_nom FROM semencier");
		    while (rs.next())
		    {
		    	// Remplissage du tableau
		    	m_table_model.addRow(new Object[]{rs.getString(1), rs.getString(2)});
		    } 
		    rs.close();
		    st.close(); 
		}
		catch (SQLException sqle) 
		{
			String l_message;
			
			l_message = this.getClass().getName()+".bd_lance_requete() ->"+sqle.getMessage();
			JOptionPane.showMessageDialog(null, l_message);
		    System.err.println(l_message);
		    System.exit(1);
		}
	}
 }
