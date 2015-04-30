import java.sql.Connection;


public class CObjetMetier 
{
	static protected Connection	m_connexion_sql = null;
	
	static public Connection get_connexion_sql() 
	{
		return CObjetMetier.m_connexion_sql;
	}
	static public void set_connexion_sql(Connection p_connexion_sql) 
	{
		CObjetMetier.m_connexion_sql = p_connexion_sql;
	}

}
