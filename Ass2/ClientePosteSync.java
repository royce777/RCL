import java.util.concurrent.BlockingQueue;

public class ClientePosteSync implements Runnable{
    public String name;
    private BoolSpace space;
    private BlockingQueue<Runnable> queue;
    public ClientePosteSync(String name, BoolSpace freespace, BlockingQueue<Runnable> queue){
        this.name = name;
        this.space = freespace;
        this.queue = queue;
    }
    public void run() {
        System.out.println(Thread.currentThread().getName() + " sta servendo il cliente " + name);
        try{
            Long durata = (long) (Math.random()*200);
            //System.out.println(Thread.currentThread().getName() + " sta facendo un task che dura " + durata + "ms per " + name);
            Thread.sleep(durata);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ha finito di servire " + name + " che sta uscendo");
        synchronized ( space ){
            //System.out.println("controllo lo spazio " + queue.remainingCapacity() + "freespace = " + space.freespace);
            if( !space.freespace && queue.remainingCapacity() > 0){
                space.freespace = true;
                space.notify();
                //System.out.println("setto freespace a TRUE");
            }
        }
    }
}
