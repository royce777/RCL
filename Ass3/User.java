public class User implements Runnable {
  String name;
  int type;
  int k;
  int pcIndex;
  Laboratory lab;

  public User(String name, int type, Laboratory lab, int k){
    this.name = name;
    this.type = type;
    this.k = k;
    this.lab = lab;
    if(this.type==1) {  // se si tratta di un tesista
			this.pcIndex = (int) (Math.random()*20);
		}
  }


  @Override
  public void run(){
    System.out.println("Sono " + name + " entro "+k +" volte");
    for(int i = 0 ; i < k; i++){
      try{
        Thread.sleep((long)(Math.random()*1000));
      }
      catch ( InterruptedException e){
        e.printStackTrace();
      }
      if (type == 2) { // type = 2  ==> professore, ha massima priorita'
        lab.lock.lock();
        // System.out.println(name +" ha preso la LOCK");
        lab.profWait();
        while(lab.getFreePCs()!=20){
          try{
            // System.out.println(name +" is waiting");
            lab.allPCFree.await();
            // System.out.println(name + " woke up after waiting");
          }
          catch(InterruptedException e){
            e.printStackTrace();
          }
        }
        lab.profDone();
        try {
          lab.occupyAll();
          System.out.println("User " + name + " entered the Lab and is going to release lock. Managed by : " + Thread.currentThread().getName());
          lab.lock.unlock();
          // System.out.println("User " + name + " released lock, and will sleep ");
          Thread.sleep((long)(Math.random()*1000));
          lab.lock.lock();
          lab.freeAll();
          lab.selectNextUser(this.name);
          lab.lock.unlock();
        }
        catch( InterruptedException e){
          e.printStackTrace();
        }
      }
      if ( type == 1 ){ // type = 1 ==> tesista, priorita' media
        lab.lock.lock();
        while(lab.isFree(pcIndex)==false || !lab.canEnter()) {
          try {
            lab.iPCFree.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        try{
          lab.occupy_i(pcIndex);
          System.out.println("User " + name + " entered the Lab and is going to release lock. Managed by : " + Thread.currentThread().getName());
          lab.lock.unlock();
          // System.out.println("User " + name + " released lock, and will sleep ");
          Thread.sleep((long)(Math.random()*1000));
          lab.lock.lock();
          // System.out.println("User " + name + " acquired lock, after sleep ");
          lab.free_i(pcIndex);
          lab.selectNextUser(this.name);
          lab.lock.unlock();
          // System.out.println("User " + name + " released lock after selecting NEXT");
        }
        catch( InterruptedException e){
          e.printStackTrace();
        }
      }
      if( type == 0){ //type = 0 ==> studente, minima priorita'
        lab.lock.lock();
        while(lab.getFreePCs()==0 || !lab.canEnter()) {
          try{
            lab.aPCFree.await();
          }
          catch ( InterruptedException e) {
            e.printStackTrace();
          }
        }
        try{
          pcIndex = lab.occupyOnePC();
          System.out.println("User " + name + " entered the Lab and is going to release lock. Managed by : " + Thread.currentThread().getName());
          lab.lock.unlock();
          // System.out.println("User " + name + " released lock, and will sleep ");
          Thread.sleep((long)(Math.random()*1000));
          // System.out.println("User " + name + " WOKE UP");
          lab.lock.lock();
          // System.out.println("User " + name + " acquired lock, after sleep ");
          lab.free_i(pcIndex);
          lab.selectNextUser(this.name);
          lab.lock.unlock();
          // System.out.println("User " + name + " released lock after selecting NEXT");
        }
        catch ( InterruptedException e){
          e.printStackTrace();
        }
      }
    }
    System.out.println(name + " exits");
  } 
}
