package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Blockchain implements Serializable {
    private static final long serialVersionUID = 26292552485L;
    private final List<Block> blockchain;
//    private final List<String> messages;
    private int numOfZeros;
    private List<Transaction> tempTransactions;

    public Blockchain() {
        blockchain = Collections.synchronizedList(new LinkedList<>());
//        messages = Collections.synchronizedList(new LinkedList<>());
        tempTransactions = Collections.synchronizedList(new ArrayList<>());
    }

    public Blockchain(int numOfZeros) {
        this();
        this.numOfZeros = numOfZeros;
    }

//    public List<String> getMessages() {
//        return messages;
//    }

//    public void addMessage(String message) {
//        messages.add(message);
//    }

    public void addTransaction(Transaction transaction) {
        tempTransactions.add(transaction);
    }

    public List<Transaction> getTempTransactions() {
        return tempTransactions;
    }

    public void clear() {
        tempTransactions.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = blockchain.size() - 5; i < blockchain.size(); i++) {
            sb.append(blockchain.get(i));
        }
        return sb.toString();
    }

    public synchronized long getId() {
        Block lBlock = lastBlock();
        return lBlock == null ? 1L : lBlock.getId() + 1;
    }

    public synchronized boolean accept(Block nBlock) {
        if (push(nBlock)) {
            printBlock(nBlock);
            return true;
        }
        return false;
    }

    private synchronized String processNumOfZero(long time) {
        if (time == 0) {
            numOfZeros++;
            return "N was increased to " + numOfZeros;
        } else if (time > 60) {
            numOfZeros--;
            return "N was decreased by 1";
        }
        return "N stays the same";
    }

    public synchronized boolean push(Block nBlock) {
        if (isValid(nBlock)) {
            blockchain.add(nBlock);
            return true;
        }
        return false;
    }

    private synchronized void printBlock(Block nBlock) {
        System.out.print(nBlock);
        System.out.println(processNumOfZero(nBlock.getCreationTime()));
        System.out.println();
    }

    private synchronized boolean isValid(Block nBlock) {
        Block lBlock = lastBlock();
        if (lBlock != null) {
            return lBlock.getHash().equals(nBlock.getHashPrev());
        }
        return nBlock.getHashPrev().equals("0");
    }

    private synchronized Block lastBlock() {
        int lastIndex = blockchain.size() - 1;
        return lastIndex >= 0 ? blockchain.get(lastIndex) : null;
    }

    public synchronized String getPrevHash() {
        Block lBlock = lastBlock();
        return lBlock == null ? "0" : lBlock.getHash();
    }

    public synchronized int getNumOfZeros() {
        return numOfZeros;
    }
}
