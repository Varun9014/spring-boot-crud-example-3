package in.ashokit.service;

import java.util.List;

import in.ashokit.dto.ViewEnqsFilterRequest;
import in.ashokit.entity.Enquiry;

public interface EnquiryService {

	
	public boolean addEnquiry(Enquiry enq,Integer counsellorId)  throws Exception;
	public List<Enquiry> getAllEnquires(Integer counsellorId);
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqsFilterRequest filterReq,Integer counsellorId);
	public Enquiry getEnquiryById(Integer enqId);
	


}
