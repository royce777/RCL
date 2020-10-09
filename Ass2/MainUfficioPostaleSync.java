import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.List;
import java.util.ArrayList;


public class MainUfficioPostaleSync {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Insert customers number ( an integer, positive value ): ");
        int ncust = scan.nextInt();
        while(ncust <= 0 ){
            System.out.println("Please insert an integer number > 0 !\n");
            ncust = scan.nextInt();
        }
        System.out.println("Specify k value ( an integer, positive value and < customers number ): ");
        int k = scan.nextInt();
        while(k<=0 || k > ncust){
            System.out.println("Wrong k value! Retry : ");
            k = scan.nextInt();
        }
        scan.close();
        List<ClientePosteSync> bigRoom = new ArrayList<ClientePosteSync>(ncust);
        BoolSpace space = new BoolSpace(true);
        ArrayBlockingQueue<Runnable> Q = new ArrayBlockingQueue<Runnable>(k);
        for(int i =0 ; i<ncust; i++){
            ClientePosteSync cust = new ClientePosteSync("Customer" + i, space, Q);
            bigRoom.add(cust);
        }
        UfficioPostaleSync office = new UfficioPostaleSync(4, space, Q);
        while(!bigRoom.isEmpty()){
             try{
                office.serveCustomer(bigRoom.remove(0));
            }
            catch ( Exception e) {
                e.printStackTrace();
            }
        }
        office.chiudiUfficio();      
    }
}