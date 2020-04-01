package il.co.menora.soaarchive.ejb;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import il.co.menora.soaarchive.entities.Incoming;
import il.co.menora.soaarchive.entities.Outgoing;
import il.co.menora.soaarchive.entities.Payload;
import il.co.menora.soaarchive.shared.IncomingDto;
import il.co.menora.soaarchive.shared.IncomingDtoEx;
import il.co.menora.soaarchive.shared.IncomingResponse;
import il.co.menora.soaarchive.shared.OutgoingDto;
import il.co.menora.soaarchive.shared.OutgoingResponse;
import il.co.menora.soaarchive.shared.SearchDto;

@Stateless
public class IncomingDao {

	private static Logger log = LogManager.getLogger(IncomingDao.class);
	private static final String DATE_PATTERN = "dd/MM/yyyy hh:mm";
	private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private int incomingLimit = 1000;
	private int outgoingLimit = 1000;
	@PersistenceContext 
	EntityManager em;
	
	private static Properties props = null;
	
	public IncomingDao()
	{
		if(props == null) {
			props = readProperties();
			String dateFormat = props.getProperty("soaarchive.dateFormat");
			df = new SimpleDateFormat(dateFormat);
			incomingLimit = new Integer(props.getProperty("soaarchive.incomingLimit")).intValue();
			outgoingLimit = new Integer(props.getProperty("soaarchive.outgoingLimit")).intValue();

		}
		
	}
	
	public String getPayload(long id)
	{
		Incoming inc = em.find(Incoming.class, id);
		if(inc == null) {
			return(null);
		}
		Payload payload = inc.getPayload();
		String s = payload.getPayload();
		return(s);
	}
	
	
	public String getErrorMessage(long id)
	{
		String msg = null;
		Incoming inc = em.find(Incoming.class, id);
		if(inc != null) {
			msg = inc.getErrorMessage();
		} 
		return(msg);
	}
	
	
	public IncomingDtoEx findById(long id)
	{
		Incoming inc = em.find(Incoming.class, id);
		IncomingDtoEx dto = convertToDtoEx(inc);
		return(dto);
	}
	
	public IncomingResponse findAll()
	{
		Query query = em.createQuery("SELECT inc FROM Incoming inc WHERE inc.expiryTime > ?1 ORDER BY inc.fromOutgoingId");
		query.setParameter(1, new Date());
		log.info("findAll query = " + query.toString());
		List<Incoming> list = query.setMaxResults(incomingLimit).getResultList();
		List<IncomingDto> dtoList = convertToDtoList(list);
		
		Query countQuery = em.createQuery("SELECT COUNT(inc) FROM Incoming inc WHERE inc.expiryTime > ?1");
		countQuery.setParameter(1, new Date());
		log.info("findAll count query = " + countQuery.toString());
		long totalRows = (long) countQuery.getSingleResult();
		log.info("totalRows = " + totalRows);
		IncomingResponse resp = new IncomingResponse();
		resp.setData(dtoList);
		resp.setReturnedRows(dtoList.size());
		resp.setTotalRows((int)totalRows);
		return(resp);
	}
	
	
	public IncomingResponse findIncoming(SearchDto dto)
	{
		log.info("searchDao=" + ToStringBuilder.reflectionToString(dto));
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Incoming> cq = cb.createQuery(Incoming.class);
		Root<Incoming> root = cq.from(Incoming.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		if(!dto.isShowInvalid()) {
			predicates.add(cb.greaterThan(root.get("expiryTime"), new Date()));
		}
		if(dto.getServiceName() != null && dto.getServiceName().length() > 0) {
			predicates.add(cb.like(root.get("serviceName"), dto.getServiceName()));
		}
		if(dto.getFromDate() != null && dto.getFromDate().length() > 0) {
			try {
				Date fromDate = df.parse(dto.getFromDate());
				predicates.add(cb.greaterThanOrEqualTo(root.get("incomingTime"),fromDate));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		if(dto.getToDate() != null && dto.getToDate().length() > 0) {
			try {
				Date toDate = df.parse(dto.getToDate());
				predicates.add(cb.lessThanOrEqualTo(root.get("incomingTime"),toDate));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		List<Predicate> orPredicates = new LinkedList<Predicate>();

		for(String s : dto.getStatuses()) {
			orPredicates.add(cb.equal(root.get("status"),s));			
		}
		for(String t : dto.getServiceTypes()) {
			orPredicates.add(cb.equal(root.get("serviceType"),t));			
		}
		if(orPredicates.size() > 0) {
			Predicate sp = cb.or(orPredicates.toArray(new Predicate[]{}));
			predicates.add(sp);
		}
		if(predicates.size() > 0) {
			cq.where(predicates.toArray(new Predicate[]{}));
		}
		Query  query = em.createQuery(cq);
		log.info("query = " + query.toString());
		
		List<Incoming> list = query.setMaxResults(incomingLimit).getResultList();
		List<IncomingDto> dtoList = convertToDtoList(list);
		// get the total row count
		CriteriaQuery<Long> cq1 = cb.createQuery(Long.class);
		cq1.select(cb.count(cq1.from(Incoming.class)));
		cq1.where(predicates.toArray(new Predicate[]{}));
		Query countQuery = em.createQuery(cq1);
		log.info("count query = " + countQuery.toString());
		long totalRows = (long) countQuery.getSingleResult();
		log.info("totalRows = "  + totalRows);
		IncomingResponse resp = new IncomingResponse();
		resp.setData(dtoList);
		resp.setTotalRows((int)totalRows);
		resp.setReturnedRows(dtoList.size());
		return(resp);
	}
	
	public OutgoingResponse findAllOutgoing()
	{
		Query query = em.createQuery("SELECT o FROM Outgoing o");
		List<Outgoing> list = query.setMaxResults(outgoingLimit).getResultList();
		List<OutgoingDto> dtoList = convertToOutgoingDtoList(list);
		Query countQuery = em.createQuery("SELECT COUNT(o) FROM Outgoing o");
		long totalRows = (long) countQuery.getSingleResult();
		OutgoingResponse resp = new OutgoingResponse();
		resp.setData(dtoList);
		resp.setReturnedRows(dtoList.size());
		resp.setTotalRows((int)totalRows);
		return(resp);
	}
	
	
	private List<OutgoingDto> convertToOutgoingDtoList(List<Outgoing> list)
	{
		LinkedList<OutgoingDto> dtoList = new LinkedList<OutgoingDto>();
		for(Outgoing out : list) {
			OutgoingDto dto = new OutgoingDto();
			dto.setId(out.getId());
			dto.setIncomingId(out.getIncomingId());
			dto.setMqId(out.getMqId());
			dto.setMqRfh2(out.getMqRfh2());
			dto.setResendTime(formatDate(out.getResendTime()));
			dto.setToQMgr(out.getToQMgr());
			dto.setToQueue(out.getToQueue());
			dtoList.add(dto);
		}	
		return(dtoList);
	}
	
	private List<IncomingDto> convertToDtoList(List<Incoming> list)
	{
		LinkedList<IncomingDto> dtoList = new LinkedList<IncomingDto>();
		for(Incoming inc : list) {
			IncomingDto dto = convertToDto(inc);
			if(dto != null) {
				dtoList.add(dto);
			}
		}
		
		return(dtoList);
	}
	
	private IncomingDto convertToDto(Incoming inc)
	{
		if(inc == null) {
			return(null);
		}
		IncomingDto dto = new IncomingDto();
		dto.setId(inc.getId());
		dto.setStatus(inc.getStatus());
		dto.setStatusTime(formatDate(inc.getStatusTime()));
		dto.setAdditionalId(inc.getId());
		dto.setErrorCode(inc.getErrorCode());
		dto.setErrorMessage(inc.getErrorMessage());
		dto.setErrorTime(formatDate(inc.getErrorTime()));
		dto.setFromOutgoingId(inc.getFromOutgoingId());
		dto.setFromQueue(inc.getFromQueue());
		dto.setIncomingTime(formatDate(inc.getIncomingTime()));
		dto.setServiceType(inc.getServiceType());
		dto.setServiceName(inc.getServiceName());
		dto.setLastUser(inc.getLastUser());
		dto.setFromQMgr(inc.getFromQMgr());
		dto.setPayloadSize(inc.getPayload().getPayloadSize());

		return(dto);
		
	}
	
	private IncomingDtoEx convertToDtoEx(Incoming inc)
	{
		if(inc == null) {
			return(null);
		}
		IncomingDtoEx dto = new IncomingDtoEx();
		dto.setId(inc.getId());
		dto.setStatus(inc.getStatus());
		dto.setStatusTime(formatDate(inc.getStatusTime()));
		dto.setAdditionalId(inc.getId());
		dto.setErrorCode(inc.getErrorCode());
		dto.setErrorMessage(inc.getErrorMessage());
		dto.setErrorTime(formatDate(inc.getErrorTime()));
		dto.setFromOutgoingId(inc.getFromOutgoingId());
		dto.setFromQueue(inc.getFromQueue());
		dto.setIncomingTime(formatDate(inc.getIncomingTime()));
		dto.setServiceType(inc.getServiceType());
		dto.setServiceName(inc.getServiceName());
		dto.setLastUser(inc.getLastUser());
		dto.setFromQMgr(inc.getFromQMgr());
		dto.setMqRfh2(inc.getMqRfh2());
		dto.setPayloadSize(inc.getPayload().getPayloadSize());
		dto.setBipMsg(inc.getBipMsg());
		dto.setCloseTime(formatDate(inc.getCloseTime()));
		dto.setFromServerIp(inc.getFromServerIp());
		dto.setFromServerName(inc.getFromServerName());
		dto.setGetTime(formatDate(inc.getGetTime()));
		dto.setIncomingTime(formatDate(inc.getIncomingTime()));
		dto.setExpiryTime(formatDate(inc.getExpiryTime()));
		
		return(dto);
		
	}
	
	private String formatDate(Date d)
	{
		return(df.format(d));
	}
	
	
	private Properties readProperties()
	{
		Properties  props = new Properties();
		try {
			ClassLoader cl = this.getClass().getClassLoader();
			InputStream is = cl.getResourceAsStream("soaarchive.properties");
			props.load(is);
			log.debug("read properties =" + props.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			props = null;
		}
		return(props);
	}

}
