import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Thread;

public class LabMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Insert Students number ( an integer, positive value ): ");
        int nStud = scan.nextInt();
        while(nStud <= 0 ){
            System.out.println("Please insert an integer number > 0 !\n");
            nStud = scan.nextInt();
        }
        System.out.println("Insert Thesists number ( an integer, positive value ): ");
        int nThes = scan.nextInt();
        while(nThes <= 0 ){
            System.out.println("Please insert an integer number > 0 !\n");
            nThes = scan.nextInt();
        }
        System.out.println("Insert Professors number ( an integer, positive value ): ");
        int nProf = scan.nextInt();
        while(nProf <= 0 ){
            System.out.println("Please insert an integer number > 0 !\n");
            nProf = scan.nextInt();
        }
        scan.close();
        int i;
        Laboratory lab = new Laboratory();
        ArrayList<User> Users = new ArrayList<User>();
        int k = (int) (Math.random()*5) + 1;

        
        for( i = 0 ; i< nStud ; i++){
            Users.add(new User("Student-"+i, 0, lab, k));
        }
        for(i = 0; i<nThes; i++) {
			Users.add(new User("Thesist-"+i, 1, lab, k));
        }
        for(i = 0; i<nProf; i++) {
			Users.add(new User("Professor-"+i, 2, lab, k));
        }

        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(i = 0; i<Users.size(); i++) {
            threads.add(new Thread(Users.get(i)));
            // System.out.println("starting thread " + Users.get(i).name);
			threads.get(i).start();
        }
        for(i = 0; i<Users.size(); i++){
            try{
            threads.get(i).join();
            }
            catch ( InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
