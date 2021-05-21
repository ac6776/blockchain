package blockchain;

import java.util.Date;
import java.util.Random;

public class Miner extends Account implements Runnable {
    private Blockchain blockchain;

    public Miner(Blockchain blockchain) {
        super("miner");
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        long miner = Thread.currentThread().getId() % 10;
        setName(getName() + miner);
        boolean accepted;
        long startTime = new Date().getTime();
        long endTime;
        Block nBlock;
        do {
            int numOfZeros;
            long id = blockchain.getId();
            String prevHash = blockchain.getPrevHash();
            long magic;
            String hash;
            do {
//                numOfZeros = blockchain.getNumOfZeros();
                numOfZeros = 2;
                endTime = new Date().getTime();
                magic = generateMagic();
                hash = StringUtil.applySha256(id + " " + endTime + " " + magic + " " + prevHash);
                nBlock = new Block(id, miner, endTime, magic, hash, prevHash, (endTime - startTime) / 1000);
            } while (!hash.startsWith("0".repeat(numOfZeros)));
            nBlock.setTransactions(blockchain.getTempTransactions());
            accepted = blockchain.accept(nBlock);
        } while (!accepted);
        blockchain.clear();
    }

    private long generateMagic() {
        Random random = new Random();
        return random.nextLong();
    }
}
