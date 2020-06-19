import java.util.concurrent.atomic.AtomicInteger;

public class ex4 {
    public static void main(String[] args) {
        Castle castle = new Castle("Castle 1", 1000);
        castle.start();

        for (int i = 0; i < 5; i++)
            new Attacker("Attacker " + Integer.toString(i), castle).start();

        while (true) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
    }
}

class Castle extends Thread {
    String name;
    AtomicInteger health;

    Castle(String name, int initialHealth) {
        this.name = name;
        health = new AtomicInteger(initialHealth);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("Castle health: " + health.addAndGet((int) (Math.random() * 100)));
            } catch (Exception e) {
            }
        }
    }
}

class Attacker extends Thread {
    String name;
    Castle castle;

    Attacker(String name, Castle castle) {
        this.name = name;
        this.castle = castle;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((int) (Math.random() * 2000));
                System.out.println("Castle health: " + castle.health.addAndGet((int) (Math.random() * -20))
                        + ". Castle was attack by " + name);
            } catch (Exception e) {
            }
        }
    }
}