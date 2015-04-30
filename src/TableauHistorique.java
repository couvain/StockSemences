import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class TableauHistorique extends TableauBase
{
	//***************************************************************************
	//
	// Constructeur
	//
	//--------------------------------------------------------------------------
    public TableauHistorique() 
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

}
