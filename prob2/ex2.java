import java.util.concurrent.Semaphore;

public class ex2 {
    public static void main(String[] args) {
        Girl girl = new Girl("Girl 1", 1);

        for (int i = 0; i < 5; i++) {
            new Boy("Boy " + Integer.toString(i), girl).start();
        }

        while (true) {
            try {
                Thread.sleep(500);
                // System.out.println(girl.love.availablePermits());
            } catch (Exception e) {
            }
        }
    }
}

class Girl {
    String name;
    Semaphore love;

    Girl(String name, int maxNumOfSimultaneousRelationship) {
        this.name = name;
        love = new Semaphore(maxNumOfSimultaneousRelationship, true);
    }

    public void getLove(String boyName) {
        try {
            love.acquire();
            System.out.println(boyName + " ask her out.");

            int day = (int) (Math.random() * 100);
            Thread.sleep(day * 10);

            System.out.println(
                    boyName + " and " + name + " broke up after " + Integer.toString(day) + " days of dating.");
            love.release();
        } catch (InterruptedException e) {
        }
    }
}

class Boy extends Thread {
    String name;
    Girl girl;

    Boy(String name, Girl girl) {
        this.name = name;
        this.girl = girl;
    }

    @Override
    public void run() {
        while (true) {
            girl.getLove(name);
        }
    }
}