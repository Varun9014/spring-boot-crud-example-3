package in.ashokit.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer>{

	
	public Counsellor findByEmail(String gmail);
	public Counsellor findByEmailAndPassword(String email,String pwd);
	
	
}
