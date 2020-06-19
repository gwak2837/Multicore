import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ex1 {
    public static void main(String[] args) {
        BlockingQueue<Food> foodStorage = new ArrayBlockingQueue<Food>(10);

        for (int i = 0; i < 3; i++)
            new Farmer("Farmer " + Integer.toString(i), foodStorage).start();

        for (int i = 0; i < 3; i++)
            new Predator("Predator " + Integer.toString(i), foodStorage).start();

        while (true) {
            try {
                Thread.sleep(500);
                System.out.println(foodStorage.size());
            } catch (Exception e) {
            }
        }
    }
}

class Food {
    int calorie;

    Food(int calorie) {
        this.calorie = calorie;
    }
}

class Farmer extends Thread {
    String name;
    BlockingQueue<Food> foodStorage;

    Farmer(String name, BlockingQueue<Food> foodStorage) {
        this.name = name;
        this.foodStorage = foodStorage;
    }

    public void produce() {
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Food f = new Food((int) (Math.random() * 100));
                foodStorage.put(f);
                System.out.println(name + " produced food with " + f.calorie + " kcal.");
                sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}

class Predator extends Thread {
    String name;
    AtomicInteger hunger;
    BlockingQueue<Food> foodStorage;

    Predator(String name, BlockingQueue<Food> foodStorage) {
        this.name = name;
        this.foodStorage = foodStorage;
        hunger = new AtomicInteger(100);
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(100);
                int h = hunger.addAndGet(-5);
                if (h < 0)
                    new Plunder().start();
            } catch (InterruptedException e) {
            }
        }
    }

    class Plunder extends Thread {
        @Override
        public void run() {
            try {
                int hungerAfterEat;
                do {
                    int calorie = foodStorage.take().calorie;
                    hungerAfterEat = hunger.addAndGet(calorie);
                    System.out.println(name + " plunder food with " + calorie + " kcal.");
                } while (hungerAfterEat < 100);
            } catch (InterruptedException e) {
            }
        }
    }
}