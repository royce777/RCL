import java.util.concurrent.*;


public class UfficioPostale{
	
	private ThreadPoolExecutor executor;
	private ArrayBlockingQueue<Runnable> Q;
	
	public UfficioPostale(int numWorkers, int dimQ) {
		Q = new ArrayBlockingQueue<Runnable>(dimQ);
		executor = new ThreadPoolExecutor(numWorkers, numWorkers, 1, TimeUnit.SECONDS, Q);
	}
	
	
	public void serveCustomer (ClientePoste task) {
		executor.execute(task);
	}
	
	public void chiudiUfficio() {
		executor.shutdown();
	}
	
	public int postiOccupati() {
		return Q.size();
	}
}