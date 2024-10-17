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

    /**
     * Add account method.
     * 
     * @param account
     * @return added account
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
     * Getter for account given username.
     * 
     * @param username
     * @return account
     */
    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    /**
     * Getter for account given id.
     * 
     * @param id
     * @return account
     */
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    /**
     * Verify account method.
     * 
     * @param account
     * @return verified account
     */
    public Account verifyAccount(Account account) {
        Account verifiedAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (verifiedAccount == null || !verifiedAccount.getPassword().equals(account.getPassword())) {
            return null;
        }
        return verifiedAccount;
    }
}
