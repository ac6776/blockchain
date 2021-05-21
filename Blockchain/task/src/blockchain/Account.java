package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Account implements Runnable {
    static List<Account> accounts = new ArrayList<>();
    private String name;

    public Account(String name) {
        this.name = name;
        accounts.add(this);
    }

    public static Account getRandomAccount() {
        if (accounts == null) {
            return null;
        }
        return accounts.get(new Random().nextInt(accounts.size()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
