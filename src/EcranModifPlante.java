

public class EcranModifPlante extends EcranPlante 
{
	private static final long serialVersionUID = -2279875873057612214L;

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public EcranModifPlante()
	{
		super(); // appel du constructeur du parent EcranPlante
		setTitle("Modification d'une plante");
	}
	
	//******************************************************************************************
	//
	// 
	//
	//******************************************************************************************
	public void set_pla_id_selectionne(int p_pla_id_selectionne) 
	{
		m_plante.set_pla_id(p_pla_id_selectionne);
	}
	
	//******************************************************************************************
	//
	//
	//
	//------------------------------------------------------------------------------------------
	public void bd_charge_donnees_interface() 
	{
		super.bd_charge_donnees_interface();
		bd_charge_plante();
	}

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public void bd_charge_plante()
	{
		// On charge la plante dont on a chargé l'ID dans l'objet m_plante
		m_plante.bd_select();
		charge_interface_depuis_objet_plante();
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
    protected void charge_interface_depuis_objet_plante()
    {
        CFamillePlante l_famille_de_plante=new CFamillePlante();
        
        m_txtNomDeLaPlante.setText(m_plante.get_pla_nom());
        m_textArea_note.setText(m_plante.get_pla_notes());

        // Recherche de la famille de plante pour la selection dans la combobox
        int     l_position;
        boolean l_trouve;
        boolean l_termine;
        
        l_position = -1;
        l_trouve   = false;
        l_termine  = false;
        
        while ((!l_trouve) && (!l_termine))
        {
        	l_position++;
        	l_famille_de_plante =  m_model_combobox_famille_plante.get(l_position);
        	
        	if (l_famille_de_plante.get_fap_id() == m_plante.get_fap_id_famille_plante())
        	{
        		l_trouve = true;
        	}
        	if (l_position == m_model_combobox_famille_plante.size())
        	{
        		l_termine=true;
        	}
        }
        if (l_trouve)
        {
            m_combobox_famille.setSelectedIndex(l_position);        	
        }
        else
        {
        	// TODO: Erreur à gérer
        }
		
		m_checkboxExterieur[0].setSelected(m_plante.get_pla_semis_ext_jan());
		m_checkboxExterieur[1].setSelected(m_plante.get_pla_semis_ext_fev());
		m_checkboxExterieur[2].setSelected(m_plante.get_pla_semis_ext_mar());
		m_checkboxExterieur[3].setSelected(m_plante.get_pla_semis_ext_avr());
		m_checkboxExterieur[4].setSelected(m_plante.get_pla_semis_ext_mai());
		m_checkboxExterieur[5].setSelected(m_plante.get_pla_semis_ext_jun());
		m_checkboxExterieur[6].setSelected(m_plante.get_pla_semis_ext_jul());
		m_checkboxExterieur[7].setSelected(m_plante.get_pla_semis_ext_aou());
		m_checkboxExterieur[8].setSelected(m_plante.get_pla_semis_ext_sep());
		m_checkboxExterieur[9].setSelected(m_plante.get_pla_semis_ext_oct());
		m_checkboxExterieur[10].setSelected(m_plante.get_pla_semis_ext_nov());
		m_checkboxExterieur[11].setSelected(m_plante.get_pla_semis_ext_dec());
		
		m_checkboxSerre[0].setSelected(m_plante.get_pla_semis_ser_jan());
		m_checkboxSerre[1].setSelected(m_plante.get_pla_semis_ser_fev());
		m_checkboxSerre[2].setSelected(m_plante.get_pla_semis_ser_mar());
		m_checkboxSerre[3].setSelected(m_plante.get_pla_semis_ser_avr());
		m_checkboxSerre[4].setSelected(m_plante.get_pla_semis_ser_mai());
		m_checkboxSerre[5].setSelected(m_plante.get_pla_semis_ser_jun());
		m_checkboxSerre[6].setSelected(m_plante.get_pla_semis_ser_jul());
		m_checkboxSerre[7].setSelected(m_plante.get_pla_semis_ser_aou());
		m_checkboxSerre[8].setSelected(m_plante.get_pla_semis_ser_sep());
		m_checkboxSerre[9].setSelected(m_plante.get_pla_semis_ser_oct());
		m_checkboxSerre[10].setSelected(m_plante.get_pla_semis_ser_nov());
		m_checkboxSerre[11].setSelected(m_plante.get_pla_semis_ser_dec());
    }
    
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
    protected void bd_maj_plante()
    {
		if (saisie_est_valide() == true)
		{
			charge_objet_plante_depuis_interface();
			
			// Maj en base de donnees
	    	m_plante.bd_update();
	    	
	        // On recharge la liste de plantes de l'ecran principal et on remet ses controles a jour 
	        m_ecran_principal.bd_charge_donnees_interface();
			fermer_la_fenetre();
		}
    }

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	protected void btnEnregistrerClick() 
	{
		bd_maj_plante();
	}

}
