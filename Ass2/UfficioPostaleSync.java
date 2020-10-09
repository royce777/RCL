import java.util.concurrent.*;


public class UfficioPostaleSync{
	
	private ThreadPoolExecutor executor;
    private ArrayBlockingQueue<Runnable> Q;
    private BoolSpace space;
	
	public UfficioPostaleSync(int numWorkers, BoolSpace space, ArrayBlockingQueue<Runnable> Q ) {
		this.Q = Q;
        executor = new ThreadPoolExecutor(numWorkers, numWorkers, 1, TimeUnit.SECONDS, Q);
        this.space = space;
	}
	
	
	public void serveCustomer (ClientePosteSync task) {
        synchronized(space){
            while(!space.freespace){
                try{
                    space.wait();
                }
                catch ( InterruptedException e){
                    System.out.println("interrupt captured");
                }
            }
            executor.execute(task);
            if(Q.remainingCapacity()==0)
                 space.freespace = false;
        }     
	}
	
	public void chiudiUfficio() {
		executor.shutdown();
	}
	
	public int postiOccupati() {
		return Q.size();
	}
}