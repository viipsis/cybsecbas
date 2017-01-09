package sec.project.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import static org.hibernate.annotations.common.util.impl.LoggerFactory.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    private List<String> list;
    private List<String> regstatus;

    public SignupController () {
       this.list = new ArrayList<>();
       this.regstatus = new ArrayList<>();
    }
    
    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("/")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value="/redir", method = RequestMethod.GET)
    public String redir(@RequestParam String address) {
        String newaddr = "redirect:".concat(address);  // issue
        return newaddr;
    }

    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    
    @RequestMapping(value = "/test", method = RequestMethod.GET )
    public String testArea(@RequestParam String htmltxt)
    {
        String txt = "Hello! ".concat(htmltxt);
        
        return txt;
        
    }
      
    
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, @RequestParam String vipcode, Model model) {
        signupRepository.save(new Signup(name, address, vipcode));
        if (!name.isEmpty()) {
            if (vipcode != null && vipcode.length()>=1 && Integer.parseInt(vipcode.trim())>0 ) {
                name = name.concat("*");
            } else {
                vipcode = "";
            }
            
            this.list.add(name); // 
            
            if (name.endsWith("*")) {
                
                name = name.substring(0, name.length() - 1); // drop last char
                
            }
        }
        
      
        
        //list.get(list.lastIndexOf(list));
        model.addAttribute("name",name);
        ///model.addAttribute("status",regstatus);

        model.addAttribute("list",list);
        return "done";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listForm(Model model) { // Model model
        model.addAttribute("list", list);      
        
        return "list";
    }
    
 
    @RequestMapping(value="/admin2",  method = RequestMethod.POST  )
    public String defaultResp(@RequestParam String msg) {
        return "Correct! ".concat(msg);
    }
    

}
