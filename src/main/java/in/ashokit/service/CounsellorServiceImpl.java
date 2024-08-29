package in.ashokit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import in.ashokit.dto.DashboardResponse;
import in.ashokit.entity.Counsellor;
import in.ashokit.entity.Enquiry;
import in.ashokit.repos.CounsellorRepo;
import in.ashokit.repos.EnquiryRepo;

public class CounsellorServiceImpl implements CounsellorService {

	
	@Autowired
	private CounsellorRepo crepository;
	
	@Autowired
	private EnquiryRepo erepository;
	@Override
	public boolean registration(Counsellor counsellor) {
		Counsellor savedCounsellor = crepository.save(counsellor);
		if(null!=savedCounsellor.getCounsellorId())
		{
			return true;
		}
		return false;
	}

	@Override
	public Counsellor login(String email, String pwd) {
	
		Counsellor counsellor  =  crepository.findByEmailAndPassword( email, pwd);
		
		 return counsellor;
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {
		
		// here important that we are only one time we accessing the database and process the data using java 8 stream api
		DashboardResponse response = new DashboardResponse();
		List<Enquiry> enqList = erepository.getEnquirysByCounsellorId(counsellorId);
		int totalEnq = enqList.size();

		int enrolledEnqs = enqList.stream().filter(e -> e.getEnqStatus().equals("Enrolled"))
				.collect(Collectors.toList()).size();
		int lostEnqs = enqList.stream().filter(e -> e.getEnqStatus().equals("Lost")).collect(Collectors.toList())
				.size();
		int openEnqs = enqList.stream().filter(e -> e.getEnqStatus().equals("Open")).collect(Collectors.toList())
				.size();

		response.setTotalEnqs(totalEnq);
		response.setEnrolledEnqs(enrolledEnqs);
		response.setOpenEnqs(openEnqs);
		return response;
	}

	@Override
	public Counsellor findByEmail(String email) 
	{		
		return crepository.findByEmail(email);
	}

}
