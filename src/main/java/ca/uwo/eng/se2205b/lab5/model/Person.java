package ca.uwo.eng.se2205b.lab5.model;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Person
 */
@ParametersAreNonnullByDefault
@Immutable
public final class Person {

    private final String firstName;
    private final String lastName;
    private Bank bank;
    private List<Account> accounts;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bank = null;
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public List<Account> getAccounts(){
        return accounts;
    }

    public void setBank (Bank bank){this.bank = bank;}

    public Bank getBank(){return bank;}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = hash * 31 + firstName.hashCode();
        hash = hash * 31 + lastName.hashCode();
        hash = hash * 31 + bank.hashCode();
        for (Account acc : accounts)
            hash = hash * 31 + acc.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)) return false;

        Person comp = (Person) obj;

        if (!firstName.equals(comp.getFirstName()) || !lastName.equals(comp.getLastName())) return false;

        if (!bank.equals(comp.bank) || !accounts.equals(comp.getAccounts())) return false;

        return true;
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName + " of bank " + bank.toString();
    }
}
