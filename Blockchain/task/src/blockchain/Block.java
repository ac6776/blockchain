package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Block implements Serializable {
    private long id;
    private long miner;
    private long timestamp;
    private long magicNum;
    private String hash;
    private String hashPrev;
    private long creationTime;
    private String data;
    private List<Transaction> transactions;

    public void setData(String data) {
        this.data = data;
    }

    public Block(long id, String hashPrev) {
        this.id = id;
        this.timestamp = new Date().getTime();
        this.hashPrev = hashPrev;
        this.hash = StringUtil.applySha256(this.id + " " + timestamp + " " + hashPrev);
    }

    public Block(long id, long miner, long timestamp, long magicNum, String hash, String hashPrev, long creationTime) {
        this.id = id;
        this.miner = miner;
        this.timestamp = timestamp;
        this.magicNum = magicNum;
        this.hash = hash;
        this.hashPrev = hashPrev;
        this.creationTime = creationTime;
//        this.transactions = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public String toString() {
        return String.format("Block:\n" +
                        "Created by miner # %d\n" +
                        "Miner %d gets 100 VC\n" +
                        "Id: %d\n" +
                        "Timestamp: %d\n" +
                        "Magic number: %d\n" +
                        "Hash of the previous block:\n" +
                        "%s\n" +
                        "Hash of the block:\n" +
                        "%s\n" +
                        "Block data:\n%s\n" +
                        "Block was generating for %d seconds\n",
                miner, miner, id, timestamp, magicNum, hashPrev, hash, getData(), creationTime);
    }

    private String getData() {
        if (transactions.size() == 0) {
            return "no transactions";
        }
        return transactions.stream().map(Transaction::toString).collect(Collectors.joining("\n"));
    }

    public void setTransactions(List<Transaction> transactions) {
//        Collections.copy(this.transactions, transactions);
        this.transactions = new ArrayList<>(transactions);
    }

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public String getHashPrev() {
        return hashPrev;
    }

    public long getMagicNum() {
        return magicNum;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setMiner(long miner) {
        this.miner = miner;
    }

    public long getMiner() {
        return miner;
    }
}
