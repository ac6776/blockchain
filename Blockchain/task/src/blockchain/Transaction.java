package blockchain;

public class Transaction {
    private Account from;
    private Account to;
    private int amount;

    public Transaction(Account from, Account to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("%s sent %d VC to %s", from.getName(), amount, to.getName());
    }
}
