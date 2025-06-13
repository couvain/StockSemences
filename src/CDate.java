import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class CDate extends GregorianCalendar 
{
	private static final long serialVersionUID = 7147894066005321371L;
	public static long DATE_INFINIE    = 25000101; 
	public static long DATE_2000_01_01 = 20000101;
	
	public CDate()
	{
		init();
	}

	public CDate(int p_jour, int p_mois, int p_annee)
	{
		// TODO ajout de contrôles de validité de date
                this.set(Calendar.DAY_OF_MONTH, p_jour);
                this.set(Calendar.MONTH,        p_mois-1);
                this.set(Calendar.YEAR,         p_annee);
	}
	
	public CDate(Calendar p_calendar) 
	{
		init_calendar(p_calendar);
	}
	
	public CDate(long p_bigint) 
	{
		init_bigint(p_bigint);
	}

	public void init()
	{
                // initialisation au 01/01/2000
                this.set(Calendar.DAY_OF_MONTH, 1);
                this.set(Calendar.MONTH,        0);
                this.set(Calendar.YEAR,      2000);
	}

	public void init_calendar(Calendar p_calendar)
	{
		this.set(Calendar.DAY_OF_MONTH, p_calendar.get(Calendar.DAY_OF_MONTH));
		this.set(Calendar.MONTH,        p_calendar.get(Calendar.MONTH));
		this.set(Calendar.YEAR,         p_calendar.get(Calendar.YEAR));				
	}
	
	public void init_date_du_jour()
	{
		Calendar l_date_du_jour = new GregorianCalendar();
		
		// initialisation au jour courant
		this.set(Calendar.DAY_OF_MONTH, l_date_du_jour.get(Calendar.DAY_OF_MONTH));
		this.set(Calendar.MONTH,        l_date_du_jour.get(Calendar.MONTH));
		this.set(Calendar.YEAR,         l_date_du_jour.get(Calendar.YEAR));		
	}
	
	public void init_date_infinie()
	{
		this.set_bigint(CDate.DATE_INFINIE);
	}
	
	public boolean est_date_infinie()
	{
		return (this.get_bigint() == CDate.DATE_INFINIE);
	}
	
	// Renvoie un entier long contenant la date sous la forme AAAAMMJJ
	public long get_bigint()
	{
		return (long)this.get(Calendar.DAY_OF_MONTH) +
			  (long)(this.get(Calendar.MONTH)+1) * 100L +
			  (long)(this.get(Calendar.YEAR)) *10000L;
	}
	
	public void init_bigint(long p_bigint)
	{
		set_bigint(p_bigint);
	}
	
	public void set_bigint(long p_bigint)
	{
		int l_jour;
		int l_mois;
		int l_annee;
		
		l_annee = (int)p_bigint /  10000;
		l_mois = (int)(p_bigint - (10000 * l_annee)) / 100;
		l_jour = (int)(p_bigint - (10000 * l_annee)) - (100 * l_mois);
		
		this.set(Calendar.DAY_OF_MONTH, l_jour);
		this.set(Calendar.MONTH, l_mois-1);
		this.set(Calendar.YEAR, l_annee);
	}

	public void copie(CDate p_date) 
	{
		this.set_bigint(p_date.get_bigint());
	}

	public String toStringFR()
	{
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		String formatted = format1.format(this.getTime());
		
		return formatted;
	}
	
	public static String bigintToStringFR(long p_bigint)
	{
		CDate l_date = new CDate(p_bigint);
		return l_date.toStringFR();
	}
	
	public static long get_date_du_jour_bigint()
	{
		CDate l_date = new CDate();
		l_date.init_date_du_jour();
		return l_date.get_bigint();
	}
}
