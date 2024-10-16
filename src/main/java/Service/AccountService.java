package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // Consider another constructor - refer to AuthorService from library


    public Account addAccount(Account account){
        if (accountDAO.getAccountByUsername(account.getUsername()) != null ||
            account.getPassword().length() < 4 ||
            account.getUsername() == ""){
            return null;
        }
        return accountDAO.addAccount(account);
    }
}
