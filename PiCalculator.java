import java.lang.Math;
import java.lang.Thread;

public class PiCalculator implements Runnable {
    private double accuracy;
    private double result;
    private int den;

    public PiCalculator(double accuracy) {
        this.accuracy = accuracy;
        this.den = 1;
        this.result = 0;
    }

    public void run() {
        while (Math.abs(Math.PI - result) > accuracy) {
            result = result + (double) 4 / den;
            // System.out.println(result);
            if (den > 0) {
                den = -(den + 2);
            } else {
                den = -(den - 2);
            }
            // System.out.println(den);
            if (Thread.interrupted()) {
                System.out.println("Thread has been interrupted \n");
                break;
            }
        }
        System.out.println("Result : " + result);
    }

    public static void main(String args[]) {
        if(args.length != 2){
            System.out.println("Sintassi:");
            System.out.println(" java PiCalculator < accuracy (double) positive ! > < max_time ( ms ) positive ! >");
            return;
        }
        double acc;
        int time;
        try
        {
            acc = Double.valueOf(args[0]).doubleValue();
            time = Integer.valueOf(args[1]).intValue();
            if(acc <= 0 || time <= 0){
                System.out.println("Wrong parameters : accuracy > 0, time > 0");
                return;
            }
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong ( only numeric ones are accepted ! )");
            return;
        }
        PiCalculator calc = new PiCalculator(acc);
        Thread worker = new Thread(calc);
        worker.start();
        try {
            worker.join(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(worker.isAlive()){
            worker.interrupt();
        }
    }

}