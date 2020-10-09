import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class MainUfficioPostale {
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
        List<ClientePoste> bigRoom = new ArrayList<ClientePoste>(ncust);
        for(int i =0 ; i<ncust; i++){
            ClientePoste cust = new ClientePoste("Customer" + i);
            bigRoom.add(cust);
        }
        UfficioPostale office = new UfficioPostale(4, k);
        while(!bigRoom.isEmpty()){
            int ok  = k -  office.postiOccupati();
            if( ok > 0){
                try{
                    office.serveCustomer(bigRoom.remove(0));
                }
                catch ( Exception e) {
                    e.printStackTrace();
                }
             }
        }
        office.chiudiUfficio();      
    }
}