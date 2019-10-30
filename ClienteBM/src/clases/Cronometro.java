
package clases;

import static java.lang.Thread.sleep;
import javax.swing.JLabel;


public class Cronometro extends JLabel implements Runnable
{ 
    private int nuMin=0; //El Contador de minutos
    private int nuSeg=0; //El Contador de segundos
    private int nuHora=0; //E contador de horas
    Thread hilo1;
    Thread cr;
    public Cronometro()
    {// Contructor porque la clase es heredada 
        this.setText(nuHora+":"+nuMin+":"+nuSeg);
        this.setFont(new java.awt.Font("Tahoma", 1, 20));
    }
    public void iniciarHilo()
    {
        hilo1 = new Thread(this);
        hilo1.start();
    }
    public void pararHilo()
    {
        hilo1.interrupt();
    }
        @Override
        public void run()  
        {
            cr = Thread.currentThread();
            while(cr==hilo1)
            {
                nuMin=0; //El Contador de minutos
                nuSeg=0; //El Contador de de segundos
                nuHora=0; //El Contador de Horas   
                   try {//si ocurre un error al dormir el proceso(sleep(999))
                       while(true){ //inicio del for infinito           
                          if(nuSeg!=59) {//si no es el ultimo segundo
                              nuSeg++; //incremento el numero de segundos                                  
                           }else{
                               if(nuMin!=59){//si no es el ultimo minuto
                                   nuSeg=0;//pongo en cero los segundos 
                                   nuMin++;//incremento el numero de minutos
                               }else{//incremento el numero de horas
                                       nuHora++;
                                       nuMin=0;//pongo en cero los minutos
                                       nuSeg=0;//pongo en cero los segundos           
                               }
                           }               
                           this.setText(nuHora+":"+nuMin+":"+nuSeg);//Muestro en pantalla el cronometro
                           //System.out.println(nuHora+":"+nuMin+":"+nuSeg);
                         sleep(999);//Duermo el hilo durante 999 milisegundos(casi un segundo, quintandole el tiempo de proceso)
                       }//Fin del for infinito             
                   } catch (Exception ex) {
                        //System.out.println(ex.getMessage());//Imprima el error
                        break;
                   }               
            }
        }
}