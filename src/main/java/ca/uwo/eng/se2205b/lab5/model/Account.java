package ca.uwo.eng.se2205b.lab5.model;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Represents an Account with a list of time organized transactions.
 */
@ParametersAreNonnullByDefault
public final class Account {

    SortedSet<Transaction> transactions;
    double balance;

    public Account() {
        transactions = new TreeSet<>(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });

        balance = 0;
    }

    /**
     * Add a transaction to the account, updating the balance
     * @param transaction
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        balance += transaction.getAmount();
    }

    /**
     * Get the Balance of this account
     * @return Current balance
     */
    public double getBalance() {
        return balance;
    }

    public SortedSet<Transaction> getTransactions(){
        return transactions;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*31 + transactions.hashCode();
        hash = hash*31 + Double.hashCode(balance);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account)) return false;

        Account that = (Account) obj;

        return (transactions.equals(that.getTransactions()) && balance == that.getBalance());
    }

    @Override
    public String toString() {
        return "Accout Balance: " + balance;
    }
}
