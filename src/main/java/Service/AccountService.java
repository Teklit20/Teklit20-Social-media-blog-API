package Service;
import DAO.*;
import Model.*;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();

    public Account registerAccount(Account account) {
        if (account.getUsername() != null && !account.getUsername().isEmpty() && account.getPassword().length() > 4) {
            return accountDAO.createAccount(account);
        }
        return null;
    }
   
    public Account authenticateUser(Account account) {
        Account storedAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (storedAccount != null && storedAccount.getPassword().equals(account.getPassword())) {
            return storedAccount;
        }
        
        return null;
    }
    public Account login(String username, String password) {
        Account account = accountDAO.getAccountByUsername(username);
        
        if (account == null || !account.getPassword().equals(password)) {
            return null; // Invalid username or password
        }

        return account; // Successful login
    }
    
}
