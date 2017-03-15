package ca.uwo.eng.se2205b.lab5.model;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a Bank
 */
@ParametersAreNonnullByDefault
public final class Bank {

    List<Person> people;
    String name;

    public Bank() {
        people = new ArrayList<>();
    }

    public Bank(String name){
        people = new ArrayList<>();
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }


    /**
     * Get all of the accounts for a person
     * @param person Person whom has an account
     * @return Possibly empty {@code Set} of accounts for a Person
     *
     * @throws NullPointerException if {@code person} is {@code null}
     */
    public Set<Account> getAccounts(Person person) {
        if (person == null) return null;
        if (!people.contains(person)) return null;

        return (Set<Account>) people.get(people.indexOf(person)).getAccounts();
    }

    /**
     * Opens a new {@link Account} for an individual.
     * @param person Person who is on an account
     * @return New {@code Account}
     *
     * @throws NullPointerException if {@code person} is {@code null}
     */
    public Account openAccount(Person person) {
        if (person == null) return null;
        if (!people.contains(person)) addPerson(person);

        Account newAcc = new Account();

        people.get(people.indexOf(person)).addAccount(newAcc);

        return newAcc;
    }

    public void addPerson(Person person){
        if (person == null) return;

        people.add(person);
    }

    public List<Person> getPeople(){ return people; }

    /**
     * Closes an account for an individual
     * @param person Person who is on an account
     * @param account Account to close
     *
     * @throws NullPointerException if {@code person} or {@code account} is {@code null}
     * @throws AccountCloseException if the Account can not be closed
     */
    public void closeAccount(Person person, Account account) throws AccountCloseException {
        if (person == null || account == null) throw new AccountCloseException(account, "Null account/person");

        if (!people.contains(person)) return;

        if (!person.getAccounts().contains(account)) throw new AccountCloseException(account, "Account does not exist");

        person.getAccounts().remove(person.getAccounts().indexOf(account));
    }

    /**
     * Get <em>all</em> of the wealth of a person, the total of all of a person's accounts.
     * @param person Non-{@code null} person to get the total wealth of.
     * @return Total wealth
     *
     * @throws NullPointerException if {@code person} is {@code null}
     */
    public double getTotalWealth(Person person) {
        if (person == null) return 0;

        double wealth = 0;
        for (Account acc : person.getAccounts())
            wealth += acc.balance;

        return wealth;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + people.hashCode();
        hash = hash * 31 + name.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Bank)) return false;

        Bank comp = (Bank) obj;

        if (!name.equals(comp.getName())) return false;

        return (people.equals(comp.getPeople()));
    }

    @Override
    public String toString() {
        if (name == null)
            return "Null-Named-Bank";
        else return name;
    }
}
