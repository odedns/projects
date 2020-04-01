/**
 * File: TestQuery.java
 * Date: 29 баеч 2014
 * Author: ODEDNI
 */
package menora.ccm.taxreport.tests;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import menora.ccm.taxreport.entities.TaxReportInsured;
import menora.ccm.utils.EntityManagerFactoryCache;

/**
 * @author ODEDNI
 *
 */
public class TestQuery {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long offset = 489762;
		//String sql =  "SELECT *  FROM (select t.* ,row_number() over() as rn from VHRF.TAX_REPORT_INSURED  as t where t.execution_number=1)  as col where rn between 100000 and  100050";
		EntityManager em = EntityManagerFactoryCache.getEntityManagerFactory("taxreport.dev").createEntityManager();
		Query query = em.createNamedQuery("TaxReportInsured.findByPopulation",TaxReportInsured.class );
		
		query.setFirstResult((int) offset);
		query.setMaxResults(50);		
		/*
		Query query = em.createNamedQuery("TaxReportInsured.findByPopulationEx",TaxReportInsured.class );
		long last = offset + 50;
		query.setParameter("first", offset);
		query.setParameter("last", last);
		*/
		List<TaxReportInsured> listReports = query.setParameter("popCode", 300).getResultList();
		
		System.out.println("got list size = " + listReports.size() + " for offset= " + offset);
		System.out.println("first = " + listReports.get(0).getInsuredSequence() + " last = " + listReports.get(listReports.size()-1).getInsuredSequence());
		Query q2 = em.createNamedQuery("TaxReportInsured.min_max");
		q2.setParameter("popCode", 300);
		Object o[] = (Object[]) q2.getSingleResult();
		System.out.println("result = " +o.toString());
		System.out.println("min = " + o[0] + " max=" + o[1]);
	}

}
