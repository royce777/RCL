import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Laboratory {

  private ArrayList<Boolean> Computers ;
  private int profswaiting;
  ReentrantLock lock;
  final Condition allPCFree;
  final Condition iPCFree;
  final Condition aPCFree;

  public Laboratory () {

    lock = new ReentrantLock();
    allPCFree = lock.newCondition();
    iPCFree = lock.newCondition();
    aPCFree = lock.newCondition();
    profswaiting = 0;

    Computers = new ArrayList<Boolean>();
    for (int i=0; i< 20; i++ ){
      Computers.add(true);
    }
  }

  public void selectNextUser(String name){
    // System.out.println(name + " SCEGLIE NEXT");
    if (this.lock.hasWaiters(this.allPCFree)) {
      this.allPCFree.signal();
      // System.out.println("SIGNALED PROFS");
    }
    else if (this.lock.hasWaiters(this.iPCFree)){
     this.iPCFree.signalAll();
    //  System.out.println("SIGNALED THESISTS");
    }
		else if (this.lock.hasWaiters(this.aPCFree)){
     this.aPCFree.signalAll();
    //  System.out.println("SIGNALED STUDS");
    }
  }

  public void profWait(){
    this.profswaiting++;
  }

  public void profDone(){
    this.profswaiting--;
  }

  public boolean canEnter(){
    // System.out.println(profswaiting+ " profswaiting================================");
    System.out.flush();
    if(this.profswaiting > 0 )
      return false;
    return true;
  }

  
  public int getFreePCs(){
    int res = 0;
    for(int i=0;i<20;i++){
      if(Computers.get(i)){
        res++;
      }
    }
    return res;
  }

  public boolean isFree(int i){
    return Computers.get(i);
  }

  public void occupyAll(){
    for(int i=0; i<20;i++){
      Computers.set(i,false);
    }
  }

  public void freeAll(){
    for(int i=0; i<20;i++){
      Computers.set(i,true);
    }
  }

  public void occupy_i(int i){
    Computers.set(i,false);
  }
  public void free_i(int i){
    Computers.set(i,true);
  }

  public int occupyOnePC(){
    for(int i=0; i<20;i++){
      if(Computers.get(i)){
        Computers.set(i,false);
        return i;
      }
    }
    return -1;
  }


}
