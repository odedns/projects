package il.co.menora.soaarchive.ejb;

import il.co.menora.soaarchive.entities.Incoming;
import il.co.menora.soaarchive.entities.Outgoing;
import il.co.menora.soaarchive.entities.Payload;
import il.co.menora.soaarchive.entities.QMgrConnData;
import il.co.menora.soaarchive.entities.QmgrAlias;
import il.co.menora.soaarchive.mq.MQRequestsHandler;
import il.co.menora.soaarchive.mq.SendMQRequest;
import il.co.menora.soaarchive.mq.SendMQResponse;
import il.co.menora.soaarchive.shared.IncomingDto;
import il.co.menora.soaarchive.shared.IncomingDtoEx;
import il.co.menora.soaarchive.shared.IncomingResponse;
import il.co.menora.soaarchive.shared.MQDto;
import il.co.menora.soaarchive.shared.MQResponse;
import il.co.menora.soaarchive.shared.OutgoingDto;
import il.co.menora.soaarchive.shared.OutgoingResponse;
import il.co.menora.soaarchive.shared.SOAServiceType;
import il.co.menora.soaarchive.shared.SearchDto;
import il.co.menora.soaarchive.shared.StatusDto;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
public class IncomingDao {

	
	private static Logger log = LogManager.getLogger(IncomingDao.class);
	private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private int incomingLimit = 1000;
	private int outgoingLimit = 1000;
	@PersistenceContext 
	EntityManager em;
	@Resource
	SessionContext ctx;
	
	private static Properties props = null;
	private static List<QMgrConnData> mqConnData = null;
	private static HashMap<String,String> qmgrAliases = null;
	
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
	
	public String getPayload(String id)
	{
		Incoming inc = em.find(Incoming.class, id);
		if(inc == null) {
			return(null);
		}
		Payload payload = inc.getPayload();
		String s = payload.getPayload();
		return(s);
	}
	
	
	public String getErrorMessage(String id)
	{
		String msg = null;
		Incoming inc = em.find(Incoming.class, id);
		if(inc != null) {
			if(inc.getServiceType().equals(SOAServiceType.TYPE_IIB)) {
				msg = inc.getBipText();
			} else {
				msg = inc.getErrorMessage();
			}
		} 
		return(msg);
	}
	

	public List<QMgrConnData> findAllQMgr()
	{
		Query query = em.createQuery("SELECT qmgr FROM QMgrConnData qmgr");
		List<QMgrConnData> list = query.getResultList();
		log.debug("qmgr size = " + list.size());
		return(list);
	}
	
	public IncomingDtoEx findById(String id)
	{
		Incoming inc = em.find(Incoming.class, id);
		IncomingDtoEx dto = convertToDtoEx(inc);
		return(dto);
	}
	
	public IncomingResponse findAll()
	{
		Query query = em.createQuery("SELECT inc FROM Incoming inc WHERE (inc.expiryTime > ?1 OR inc.expiryTime IS NULL)  ORDER BY inc.fromOutgoingId");
		query.setParameter(1, new Date());
		log.debug("findAll query = " + query.toString());
		List<Incoming> list = query.setMaxResults(incomingLimit).getResultList();
		List<IncomingDto> dtoList = convertToDtoList(list);
		
		Query countQuery = em.createQuery("SELECT COUNT(inc) FROM Incoming inc WHERE (inc.expiryTime > ?1 OR inc.expiryTime IS NULL)");
		countQuery.setParameter(1, new Date());
		log.debug("findAll count query = " + countQuery.toString());
		long totalRows = (long) countQuery.getSingleResult();
		log.debug("totalRows = " + totalRows);
		IncomingResponse resp = new IncomingResponse();
		resp.setData(dtoList);
		resp.setReturnedRows(dtoList.size());
		resp.setTotalRows((int)totalRows);
		return(resp);
	}
	
	private List<Predicate> createCriteria(SearchDto dto,CriteriaQuery<Incoming> cq, CriteriaBuilder cb) 
	{
		log.debug("searchDao=" + ToStringBuilder.reflectionToString(dto));
		
		Root<Incoming> root = cq.from(Incoming.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		if(!dto.isShowInvalid()) {			
			Predicate p1 = cb.greaterThan(root.get("expiryTime"), new Date());
			Predicate p2 = cb.isNull(root.get("expiryTime"));
			Predicate orP = cb.or(p1,p2);
			predicates.add(orP);
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
				log.error(e);
				throw new RuntimeException(e);
				
			}
		}
		if(dto.getToDate() != null && dto.getToDate().length() > 0) {
			try {
				Date toDate = df.parse(dto.getToDate());
				predicates.add(cb.lessThanOrEqualTo(root.get("incomingTime"),toDate));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e);
				throw new RuntimeException(e);
				
			}
		}
		List<Predicate> orPredicates = new LinkedList<Predicate>();

		if(dto.getStatuses() != null) {
			for(String s : dto.getStatuses()) {
				orPredicates.add(cb.equal(root.get("status"),s));			
			}
		}
		if(orPredicates.size() > 0) {
			Predicate sp = cb.or(orPredicates.toArray(new Predicate[]{}));
			predicates.add(sp);
		}
		List<Predicate> serviceTypePredicates = new LinkedList<Predicate>();
		if(dto.getServiceTypes() != null) {
			for(String t : dto.getServiceTypes()) {
				serviceTypePredicates.add(cb.equal(root.get("serviceType"),t));			
			}
		}
		if(serviceTypePredicates.size() > 0) {
			Predicate sp = cb.or(serviceTypePredicates.toArray(new Predicate[]{}));
			predicates.add(sp);
		}
		if(predicates.size() > 0) {
			cq.where(predicates.toArray(new Predicate[]{}));
		}
		cq.orderBy(cb.desc(root.get("statusTime")));
		return(predicates);
	}
	
	public IncomingResponse findIncoming(SearchDto dto)
	{
		log.debug("****searchDao=" + ToStringBuilder.reflectionToString(dto));
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Incoming> cq = cb.createQuery(Incoming.class);
		List<Predicate> predicates = createCriteria(dto,cq,cb);
		Query  query = em.createQuery(cq);
		log.debug("query = " + query.toString());
		
		List<Incoming> list = query.setMaxResults(incomingLimit).getResultList();
		List<IncomingDto> dtoList = convertToDtoList(list);
		
		log.debug("result size = " + list.size());
		IncomingResponse resp = new IncomingResponse();
		resp.setData(dtoList);
		resp.setReturnedRows(dtoList.size());

		if(list.size() < incomingLimit) {
			resp.setTotalRows(list.size());
			
		}else {
			// get the total row count
			CriteriaQuery<Long> cq1 = cb.createQuery(Long.class);
			cq1.select(cb.count(cq1.from(Incoming.class)));
			cq1.where(predicates.toArray(new Predicate[]{}));
			Query countQuery = em.createQuery(cq1);
			log.debug("count query = " + countQuery.toString());
			long totalRows = (long) countQuery.getSingleResult();
			log.debug("totalRows = "  + totalRows);
			resp.setTotalRows((int)totalRows);
		}
		return(resp);
	}
	
	public List<Incoming> findIncoming(List<String>ids)
	{
		String queryStr = "SELECT inc FROM Incoming inc WHERE inc.id IN ( :ids ) ";
		Query query = em.createQuery(queryStr);
		query.setParameter("ids", ids);
		List<Incoming> list = query.getResultList();
		
		return(list);
	}
	
	
	public int updateStatus(StatusDto dto)
	{
		log.debug("updateStatus : " + ToStringBuilder.reflectionToString(dto));
		String user = ctx.getCallerPrincipal().getName();
		String queryStr = "UPDATE Incoming inc set  inc.status= :status, inc.lastUser= :user WHERE inc.id IN ( :ids ) ";
		Query query = em.createQuery(queryStr);
		query.setParameter("ids", dto.getIds());
		query.setParameter("status", dto.getStatus());
		query.setParameter("user", user);
		int count = query.executeUpdate();
		log.debug("update status done ...");
		return(count);
	}
	
	public int updateStatusAll(SearchDto dto, String status)
	{
		log.debug("updateStatusAll =" + ToStringBuilder.reflectionToString(dto));
		String user = ctx.getCallerPrincipal().getName();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Incoming> cq = cb.createQuery(Incoming.class);
		createCriteria(dto,cq,cb);
		Query  query = em.createQuery(cq);
		log.debug("query = " + query.toString());
		List<Incoming> list = query.getResultList();
		log.debug("updateStatusAll got results size: " + list.size());
		for(Incoming inc : list) {
			inc.setStatus(status);
			inc.setLastUser(user);
		}
		return(list.size());
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
		dto.setStatus(StatusDto.cvtStatus(inc.getStatus()));
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
		dto.setRerunsNum(inc.getRerunsNum());
		dto.setPayloadSize(formatSize(inc.getPayload().getPayloadSize()));

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
		dto.setPayloadSize(formatSize(inc.getPayload().getPayloadSize()));
		dto.setBipText(inc.getBipText());
		dto.setBipCode(inc.getBipCode());
		dto.setCloseTime(formatDate(inc.getCloseTime()));
		dto.setFromServerIp(inc.getFromServerIp());
		dto.setFromServerName(inc.getFromServerName());
		dto.setGetTime(formatDate(inc.getGetTime()));
		dto.setIncomingTime(formatDate(inc.getIncomingTime()));
		dto.setExpiryTime(formatDate(inc.getExpiryTime()));
		dto.setMqMD(inc.getMqMD());
		dto.setDpSubCode(inc.getDpSubCode());
		return(dto);
		
	}
	
	private String formatDate(Date d)
	{
		String s = null;
		if(d != null) {
			s = df.format(d);
		}
		return(s);
	}
	
	
	private String formatSize(int size)
	{
		
		String s = null;
		if(size < 1024) {
			s = new Integer(size).toString();
		} else {
			if(size > 1024 && size < 1024*1024) {
				s = new Integer(size/1024).toString() + "KB";
			} else {
				s = new Integer(size/(1024*1024)).toString() + "MB";
			}
			
		}
		return(s);
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
	
	private Outgoing toOutgoing(Incoming inc, String queue, String queueMgr)
	{
		if(null == inc) {
			return(null);
		}
		Outgoing out = new Outgoing();
		// generate uuid
		out.setIncomingId(inc.getId());
		out.setMqRfh2(inc.getMqRfh2());
		out.setPayload(inc.getPayload());
		out.setResendTime(new Date());
		if(queue != null) {
			out.setToQueue(queue);
		} else {
			out.setToQueue(inc.getFromQueue());
		}
		if(queueMgr != null) {
			out.setToQMgr(queueMgr);
		} else {
			out.setToQMgr(inc.getFromQMgr());
		}
		return(out);
	}
	
	
	public MQResponse sendToMq(MQDto dto) 
	{
		
		log.debug("got dto =" + ToStringBuilder.reflectionToString(dto));
		MQResponse resp = new MQResponse();
		
		if(mqConnData == null){
			mqConnData = findAllQMgr();
			qmgrAliases = getQmgrAliases();
			MQRequestsHandler.init(mqConnData, qmgrAliases);
			 
		}
		
		List<Incoming> incoming = findIncoming(dto.getIds());
		SendMQRequest request = new SendMQRequest(incoming, dto, ctx.getCallerPrincipal().getName());
		log.debug("calling processMQRequest");
		SendMQResponse res = MQRequestsHandler.processMQRequest(request);
		// now create outgoing entries;
		log.debug("now saving outgoing entries....");
		
		List<Outgoing> outList = res.getOutgoing();
		for(Outgoing out : outList) {
			em.persist(out);
		}

		resp.setNumProcessed(res.getOutgoing().size());
		resp.setNumExpired(res.getNumExpired());

		if(!res.isResultSuccess()) {
			resp.setStatus(false);
			resp.setMsg(res.getErrorMessage());
			ctx.setRollbackOnly();
			
		} else {
			resp.setStatus(true);
			resp.setMsg("OK");
		}
		return(resp);
	}
	
	
	public HashMap<String,String> getQmgrAliases()
	{
		HashMap<String,String> map = new HashMap<String,String>();
		Query query = em.createQuery("SELECT qmgralias from QmgrAlias qmgralias");
		List<QmgrAlias> list = query.getResultList();
		for(QmgrAlias qa : list) {
			map.put(qa.getAliasName(),qa.getQmgrName());			
		}
				
		return(map);
	}
	
	
	

}
