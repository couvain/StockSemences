
public class EcranAjoutPlante extends EcranPlante 
{
	private static final long serialVersionUID = 1L;

	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
	public EcranAjoutPlante()
	{
		super();
		setTitle("Ajout d'une plante");		
	}
	
	//******************************************************************************************
	//
	//
	//
	//******************************************************************************************
    protected void bd_inserer_nouvelle_plante()
    {
		if (saisie_est_valide() == true)
		{
			charge_objet_plante_depuis_interface();
			
			// Insertion en base de donnees
	    	m_plante.bd_insert();
	    	
	    	// Création d'un inventaire nul à la date du 01/01/2000
	    	
	    	
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
		bd_inserer_nouvelle_plante();
	}

}
