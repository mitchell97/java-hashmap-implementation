package ca.uwo.eng.se2205b.lab5.model;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a Transaction
 */
@ParametersAreNonnullByDefault @Immutable
public final class Transaction {

    private final LocalDateTime dateTime;

    private final double amount;

    public Transaction(LocalDateTime dateTime, double amount) {
        this.dateTime = checkNotNull(dateTime, "dateTime == null");
        this.amount = checkNotNull(amount, "amount == null");
    }

    /**
     * The time this transaction took place.
     * @return Date and time of a transaction.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * The size of the transaction
     * @return
     */
    public double getAmount() {
        return amount;
    }
}
