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
        return accountDAO.addAccount(account);
    }
}
