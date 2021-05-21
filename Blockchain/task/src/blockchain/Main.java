package blockchain;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        File file = new File("data.txt");
        Blockchain blockchain = null;
//        if (file.exists()) {
//            blockchain = read(file);
//        }
//        if (blockchain == null) {
//            blockchain = new Blockchain();
//        }
            blockchain = new Blockchain();

//        MessagesRepo messagesRepo = new MessagesRepo();
        Thread tom = new Thread(new Client("Tom", blockchain));
        Thread ani = new Thread(new Client("Ani", blockchain));
        Thread ket = new Thread(new Client("Ket", blockchain));
        Thread zak = new Thread(new Client("Zak", blockchain));
        tom.start();
        ani.start();
        ket.start();
        zak.start();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 15; i++) {
            executorService.submit(new Miner(blockchain));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tom.interrupt();
        ani.interrupt();
        ket.interrupt();
        zak.interrupt();
//        System.out.println(blockchain);
//        write(blockchain, file);
    }

    private static Blockchain read(File file) {
        try (FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Blockchain) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean write(Blockchain blockchain, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(blockchain);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
