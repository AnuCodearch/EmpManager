package com.emp2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emp2.entities.Emp;


public interface EmpRepository extends JpaRepository<Emp, String>{

	@Query("SELECT e FROM Emp e WHERE e.email LIKE %?1%")
	public List<Emp> listAll(String keyword);

	
}
//




































//@Query("SELECT e FROM emptable e where e.email LIKE %?1%") 
	/*
	 * //pagination
	 * 
	 * @Query("from Emp as e where e.emp.email=:empEmail") //currentPage-page
	 * //Contact Per page -5 public Page<Emp> findBYId(@Param("empEmail")int
	 * empEmail, Pageable pageable);
	 */

	//public List<Emp> listAll(String keyword);






/*
 * @Query("SELECT * FROM empdetails") public Emp
 * getEmpByFirstname(@Param("email") String email);
 */