package Service;

import DAO.AccountDAO;
import Model.Account;

/**
 * Service class for interaction between the controller and DAO.
 */
public class AccountService {
    private AccountDAO accountDAO;

    /**
     * No-args constructor.
     */
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    // Consider another constructor - refer to AuthorService from library

    /**
     * Add account method.
     * 
     * @param account
     * @return
     */
    public Account addAccount(Account account) {
        if (accountDAO.getAccountByUsername(account.getUsername()) != null ||
                account.getPassword().length() < 4 ||
                account.getUsername() == "") {
            return null;
        }
        return accountDAO.addAccount(account);
    }

    /**
     * Verify account method.
     * 
     * @param account
     * @return
     */
    public Account verifyAccount(Account account) {
        Account verifiedAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (verifiedAccount == null || !verifiedAccount.getPassword().equals(account.getPassword())) {
            return null;
        }
        return verifiedAccount;
    }
}
