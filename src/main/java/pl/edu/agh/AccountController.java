package pl.edu.agh;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.agh.account.Account;
import pl.edu.agh.dao.AccountDAO;

@Controller
@RequestMapping(value = "/accounts")
public class AccountController {
	
	@Inject
	private AccountDAO accountDAO;
	
	@RequestMapping(value = "accounts", method = RequestMethod.GET)
	public String manager() {
		return "accounts/manager";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String newAccount(Model model) {		
		model.addAttribute(new Account());
		return "accounts/create";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String saveAccount(@Valid Account account, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println("Incorrect values: \n"+account);
			return "accounts/create";
		}
		
		long id = accountDAO.saveAccount(account);

		return "redirect:account/" + id;
	}
	
	@RequestMapping(value="account/{id}", method = RequestMethod.GET)
	public String account(@PathVariable Long id, Model model){
		model.addAttribute("account", accountDAO.getAccountByID(id));		
		return "accounts/account";	   
	}
}
