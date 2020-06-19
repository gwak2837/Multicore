import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ex3 {
    public static void main(String[] args) {
        GameServer gameServer = new GameServer(100);

        for (int i = 0; i < 3; i++)
            new Manager("Manager " + Integer.toString(i), gameServer).start();

        for (int i = 0; i < 3; i++)
            new User("User " + Integer.toString(i), gameServer).start();
    }
}

class GameServer {
    ReadWriteLock rwLock = new ReentrantReadWriteLock();
    int essentialValue;

    GameServer(int essentialValue) {
        this.essentialValue = essentialValue;
    }
}

class Manager extends Thread {
    String name;
    GameServer gameServer;

    Manager(String name, GameServer gameServer) {
        this.name = name;
        this.gameServer = gameServer;
    }

    @Override
    public void run() {
        while (true) {
            gameServer.rwLock.writeLock().lock();
            System.out.println(name + " is maintaining the server.");

            int essentialValue = (int) (Math.random() * 1000);
            gameServer.essentialValue = essentialValue;
            System.out.println("Server's essential value is changed to " + essentialValue + ".");

            int time = (int) (Math.random() * 5);
            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException e) {
            }

            System.out.println(name + " completed the maintenance of the server. It takes " + time + " s.");
            gameServer.rwLock.writeLock().unlock();

            try {
                Thread.sleep((int) (Math.random() * 4000));
            } catch (InterruptedException e) {
            }
        }

    }
}

class User extends Thread {
    String name;
    GameServer gameServer;

    User(String name, GameServer gameServer) {
        this.name = name;
        this.gameServer = gameServer;
    }

    @Override
    public void run() {
        while (true) {
            gameServer.rwLock.readLock().lock();
            System.out.println(name + " connected to server.");
            System.out.println(name + " got essential value of " + gameServer.essentialValue + " connected to server.");

            try {
                Thread.sleep((int) (Math.random() * 2000));
            } catch (InterruptedException e) {
            }

            System.out.println(name + " disconnected to server.");
            gameServer.rwLock.readLock().unlock();

            try {
                Thread.sleep((int) (Math.random() * 4000));
            } catch (InterruptedException e) {
            }
        }

    }
}