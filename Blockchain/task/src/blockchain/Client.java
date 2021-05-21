package blockchain;

import java.util.Random;

public class Client extends Account {
    private Blockchain blockchain;
//    private MessagesRepo messagesRepo;
//    String message;

    public Client(String name, Blockchain blockchain) {
        super(name);
        this.blockchain = blockchain;
//        this.messagesRepo = messagesRepo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(5000));
                int amount = new Random().nextInt(100);
                Transaction transaction = new Transaction(this, Account.getRandomAccount(), amount);
                blockchain.addTransaction(transaction);
//                blockchain.addMessage(name + ": " + messagesRepo.getMessage());
            } catch (InterruptedException e) {
//                e.printStackTrace();
                break;
            }
        }
    }
}
