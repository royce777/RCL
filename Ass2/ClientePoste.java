public class ClientePoste implements Runnable{
    private String name;
    public ClientePoste(String name){
        this.name = name;
    }
    public String getName (){
        return this.name;
    }
    public void run() {
        System.out.println(Thread.currentThread().getName() + " sta servendo il cliente " + name);
        try{
            Long durata = (long) (Math.random()*200);
            System.out.println(Thread.currentThread().getName() + " sta facendo un task che dura " + durata + "ms per " + name);
            Thread.sleep(durata);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ha finito di servire " + name + " che sta uscendo");
    }
}
