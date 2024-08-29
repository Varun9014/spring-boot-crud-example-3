package in.ashokit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import in.ashokit.dto.ViewEnqsFilterRequest;
import in.ashokit.entity.Counsellor;
import in.ashokit.entity.Enquiry;
import in.ashokit.repos.CounsellorRepo;
import in.ashokit.repos.EnquiryRepo;
import io.micrometer.common.util.StringUtils;

public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	public EnquiryRepo erepository;

	@Autowired
	public CounsellorRepo crepository;

	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {
		Counsellor counsellor = crepository.findById(counsellorId).orElse(null);
		if (counsellor == null) {
			throw new Exception("No counsellor found");
		}

		enq.setCounsellor(counsellor);  	//assign the of counsellorId as a foreign key to enquiry table

		Enquiry save = erepository.save(enq); // save the given row of data to the enquiry
		if (save.getEnqId() != null) 			// is the enquiry is saved or not
		{
			return true;							// if save then successfull
		}
	    	return false;								// else unsuccessfull
	
}

					
	@Override
	public List<Enquiry> getAllEnquires(Integer counsellorId) {
		erepository.getEnquirysByCounsellorId(counsellorId);         // get the record of the quiry based on the counsellorId
		return null;
	}

	
	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		return erepository.findById(enqId).orElse(null);
		
	}
	
	
	@Override  //implement the dynamic condition here ok because user may select or not select one of that ok they may select 1/2/3 filter 
				// as u are applying the filters in the amazoon like cost range, location filter
	
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId) {

		// 	QBE implementation(Dynamic Query preparation)

	      Enquiry enq = new Enquiry();
	      
	      if(StringUtils.isNotEmpty(filterReq.getClassMode()))
	      {
	    	  enq.setClassMode(filterReq.getClassMode());
	      }
	      
	      if(StringUtils.isNotEmpty(filterReq.getCourseMode()))
	      {
	    	  enq.setCourseName(filterReq.getCourseMode());
	      }
	      
	      if(StringUtils.isNotEmpty(filterReq.getEnqStatus()))
	      {
	    	  enq.setEnqStatus(null);
	      }
	      
	      Counsellor c=  crepository.findById(counsellorId).orElse(null);
	      
	      enq.setCounsellor(c);

	      Example<Enquiry> of = Example.of(enq);  //Example is used for dynamic query //The Example class is a part of Spring Data JPA and provides a way to perform searches by example
	      
	      List<Enquiry> enqList = erepository.findAll(of);
	      
	      return enqList;
		
		 
	}

	

}