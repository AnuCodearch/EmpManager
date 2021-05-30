package com.emp2.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.emp2.dao.EmpRepository;
import com.emp2.entities.Emp;
import com.emp2.helper.Message;

@Controller

public class EmpController {
	
	@Autowired
	private EmpRepository emprepository;
	
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - Employee Manager");
		return "home";
		}
	
	
	@RequestMapping("/login")
	public String about(Model model) {
		model.addAttribute("title", "Login - Employee Manager");
		return "login";
		}
	
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Signup - Employee Manager");
		return "signup";
		}


	
	  @RequestMapping(value="/do_register",method=RequestMethod.POST) 
	  public String registeremp(
			  @ModelAttribute("emp") Emp emp, 
			  @RequestParam(value="agreement",defaultValue="false")boolean agreement,Model model,HttpSession session)
	          
	  {try {
		  if(!agreement)
		  	 {System.out.println("!!!! NO Agreement !!"); throw new Exception("Yo havent agreed to terms click checkbox!");}
		      
							//----------------------for image-----------------------------------------------
								  /*@RequestParam("photo")MultipartFile file)
									 if(file.isEmpty()){System.out.
									println("==================================photo not uploaded===============================");}
									 else { 
										 emp.setPhoto(file.getOriginalFilename()); 
										 File savefile=new ClassPathResource("static/img").getFile(); 
										 Path filepath=Paths.get(savefile.getAbsolutePath(),File.separator+file.getOriginalFilename());
										 Files.copy(file.getInputStream(),filepath, StandardCopyOption.REPLACE_EXISTING); 
										 System.out.println("=================================Emp :================================================== "+filepath); }
									
									  */
						 //--------------------for image END---------------------------------------------
			  emp.setImageurl("img.jpg"); 
			  System.out.println("Agreement : "+agreement); 
			  System.out.println("Emp : "+emp); 		  
			  Emp result= this.emprepository.save(emp);			  
			  model.addAttribute("emp",new Emp());
			  session.setAttribute("message", new  Message(" Successfully registered !","alert-success"));
			  return "signup";
		  }
		 catch(Exception e) {
		  model.addAttribute("emp",emp);
		  session.setAttribute("message", new  Message(" Something went wrong!"+e.getMessage(),"alert-danger"));
		  return "signup"; 
		  }
	  }
	  
	/*-------------------------------------------------- VIEW LIST----------------------------------------------------
	 * 	  */
	  
	 @GetMapping("/viewlist") 
	 public String viewlist(Model model,@ModelAttribute("emp") Emp emp) 
	 { 
		 model.addAttribute("title","Viewlist - Employee Manager"); 
	     List<Emp> emplist=this.emprepository.findAll(); 
	     model.addAttribute("emp",emplist);
	     return "viewlist"; 
	  // return findpaginated(1,model);
	 }
		/*--------------------------------------------------END VIEW LIST----------------------------------------------------	  */
	
	//------------------------------------filter//-------------------------------------------
		@RequestMapping("/viewsearch")
		public String viewsearch(Model model) {
			model.addAttribute("title", "Viewsearch - Employee Manager");
			return "viewsearch";
			}
		 @RequestMapping("/do_search") 
		 public String viewsear(Model model,@Param("keyword") String keyword, Emp emp) 
		 {   
			 java.util.Optional<Emp> empoptional=this.emprepository.findById(keyword);
		     model.addAttribute("keyword",keyword);
		     List<Emp> emplist=this.emprepository.findAll(); 
			 model.addAttribute("emplist",emplist);
		     System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");			 
		     System.out.println("////////////////////////////////////////////////////////////////"+emplist+"//////////////////////////////////////////////////////////////////////////////////////");
		     System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");			 
		     
		     return "viewsearch"; 
		 }
		 
	//------------------------------------filter end//-------------------------------------------
		
	  @GetMapping("/emp/delete/{email}")
	  public String delete(@PathVariable("email") String email,HttpSession session)
	  { 
		  java.util.Optional<Emp> empoptional=this.emprepository.findById(email);
		  Emp emp= empoptional.get();
		  this.emprepository.delete(emp);
		  session.setAttribute("messagedel", new Message("Contact deleted successfully","alert-success"));
		  return "viewlist";
	  }
	  
	  @PostMapping("/emp/update/{email}")
	  public String update(Model model,@PathVariable("email") String email,HttpSession session)
	  { 
		  model.addAttribute("title","Updateform - Employee Manager");
		  Emp emp=this.emprepository.findById(email).get();
		  model.addAttribute("emp",emp);
		  return "update";
	  }
	  
	 
	 @RequestMapping(value="/process-update",method=RequestMethod.POST)
	 public String updatehandler(@ModelAttribute Emp emp,HttpSession session,Principal principal,Model model)
	 { try {
		 System.out.println("firstname="+emp.getFirstname()); 
		 Emp result= this.emprepository.save(emp);			  
		 model.addAttribute("emp",new Emp());
		 session.setAttribute("messageup", new Message("Updated successfully","alert-success"));
		 return "process-update";
		 } 
	     catch(Exception e)
	 	{e.printStackTrace();} 
	 return "process-update"; 
	 }

	  
	 
	  
}

/*
 * public String test() {
 * 
 * Emp emp=new Emp(); emp.setFirstname("Anushree Bobhate");
 * emp.setEmail("a@b.c");
 * 
 * 
 * emprepository.save(emp); return ".............WORKINGGGGG........."; }
 */