
package buscaminascs;


import forms.areaJuego;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;

public class BuscaminasCS {
    String ipServidor;
    Scanner in;
    PrintWriter out;
    static areaJuego p; 
    int jugador;
    JButton bo;
    boolean estado=true;
    boolean picar=false;
    boolean conVida = true;
    int numSala;
    int band;
    ArrayList<Integer> aX = new ArrayList<>();
    ArrayList<Integer> aY = new ArrayList<>();
     
    public BuscaminasCS(String ip)
    {
        p = new areaJuego();
        this.ipServidor = ip;
    }
   
    private void run()
    {
         try {
            Socket socket = new Socket(ipServidor, 55001);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(),true);
            //las banderas seran igual al total de minas
            band= p.getMinas();
            while(in.hasNextLine() )
            {
                 String line = in.nextLine();
                 if(line.startsWith("NUMEROJUGADOR"))
                 {
                     //se crea el tablero
                     jugador = Integer.parseInt(line.substring(line.indexOf(" ")+1,line.indexOf(" ")+2));
                     int total = Integer.parseInt(line.substring(line.indexOf("tot")+3,line.indexOf("tot")+4));
                     numSala = Integer.parseInt(line.substring(line.indexOf("sala")+4));
                    
                     p.crearTablero(jugador,total);
                 }
                 else if(line.startsWith("TOTALJUGADORES"))
                 {
                     //se modifica la parte donde se muestran los jugadores
                     String tot = line.substring(line.indexOf(" ") + 1);
                     int tot2 = Integer.parseInt(tot);
                     p.editarJugador(jugador , tot2, p.getMinas());
                     p.frame.repaint();
                     p.frame.revalidate();
                 }
                 else if(line.startsWith("HABILITARBOTON"))
                 {
                     //se habilita el boton para empezar el juego
                     p.habilitarBoton();
                     p.frame.repaint();
                     p.frame.revalidate();
                 }
                 else if(line.startsWith("EMPEZARPARTIDA"))
                 {
                    //se envia mensaje al servidor para salir del bucle
                    out.println("ya empezo");
                    p.deshabilitarBoton();
                    p.generarAreaJuego(jugador);
                    //Solo se muestra el area que se puede usar
                 }
                 else if(line.startsWith("RESULTADOS"))
                 {
                     //Todo esto para extraer X y Y
                     String coord = line.substring(11);
                     String restante = coord;
                     while(restante.length() > 0)
                     {
                         int empezarX = restante.indexOf(" ");
                         String coordX = restante.substring(restante.indexOf(" ")-empezarX, restante.indexOf(" "));
                         aX.add(Integer.parseInt(coordX));
                         restante = restante.substring(restante.indexOf(" ")+1);
                         int empezarY = restante.indexOf(" ");
                         String coordY = restante.substring(restante.indexOf(" ")-empezarY, restante.indexOf(" "));
                         aY.add(Integer.parseInt(coordY));
                         restante = restante.substring(restante.indexOf(" ")+1);
                         
                     }
                     for(int re = 0;re < aX.size();re++)
                     {
                         p.marcarVista(aX.get(re),aY.get(re));
                         p.celdaDescubierta(aX.get(re), aY.get(re));
                        
                     }
                 }
                 else if(line.startsWith("CONTENIDO"))
                 {
                     //se recibe el contenido en forma de string
                     String cont = line.substring(10);
                     String restante = cont;
                     ArrayList<Integer> contenido = new ArrayList<>();
                     //se separa cada contenido de cada par de coordenadas en un array
                     while(restante.length() > 1)
                     {
                         int empezarDato = restante.indexOf(" ");
                         String coordX = restante.substring(restante.indexOf(" ")-empezarDato, restante.indexOf(" "));
                         contenido.add(Integer.parseInt(coordX));
                         restante = restante.substring(restante.indexOf(" ")+1);
                     }
                     int m=-44;
                     //se muestra el contenido de cada coordenadas en el tablero
                     for(int re = 0;re < contenido.size();re++)
                     {
                         p.mostrarContenido(aX.get(re), aY.get(re), contenido.get(re));
                         m= contenido.get(re);
                     }
                     //se verifica si un contenido recibido no es una bomba, si es asi, se pierde
                     if((Integer.parseInt(restante))==jugador && m==-1){
                         //Meter el mostrar perdedor
                         p.mostrarPerdedor();
                         estado=false;
                     }
                     
                     aX.clear();
                     aY.clear();
                     
                 }
                 else if(line.startsWith("PONERBANDERAS"))
                 {
                     //se recibe las coordenadas y quien puso una bandera
                     String texto = line.substring(14);
                     
                     String x = texto.substring(0,texto.indexOf(" "));
                     texto = texto.substring(texto.indexOf(" ")+1);
                     
                     String y = texto.substring(0,texto.indexOf(" "));
                     texto = texto.substring(texto.indexOf(" ")+1);
                     
                     String jug = texto.substring(0,texto.indexOf(" "));
                     texto = texto.substring(texto.indexOf(" ")+1);
                     
                     int tot= Integer.parseInt(texto);
                     p.ponerBandera(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(jug));
                     //Actualizar banderas
                     if(Integer.parseInt(jug)==jugador){
                         p.editarJugador(jugador, tot, band=band-1);
                     }
                     p.frame.repaint();
                     p.frame.revalidate();
                 }
                 else if(line.startsWith("QUITARBANDERAS"))
                 {
                     //se recibe coordenadas y que jugador quito una bandera
                     String texto = line.substring(15);
                     String x = texto.substring(0,texto.indexOf(" "));
                     texto = texto.substring(texto.indexOf(" ")+1);
                     String y = texto.substring(0,texto.indexOf(" "));
                     texto = texto.substring(texto.indexOf(" ")+1);
                     String jug = texto.substring(0,texto.indexOf(" "));
                     texto = texto.substring(texto.indexOf(" ")+1);
                     
                     int tot= Integer.parseInt(texto);
                     //se usa el metodo para quitarla
                   
                     if(p.verSiVista((Integer.parseInt(x)), Integer.parseInt(y)))
                     {
                         p.quitarBanderaConvista((Integer.parseInt(x)), Integer.parseInt(y));
                         p.mostrarContenidoConBandera(Integer.parseInt(x), Integer.parseInt(y));
                     }
                     else
                     {
                        p.quitarBandera(Integer.parseInt(x), Integer.parseInt(y));
                     }
                    //Actualizar banderas
                     if(Integer.parseInt(jug)==jugador){
                         p.editarJugador(jugador, tot, band=band+1);
                     }
                     p.frame.repaint();
                     p.frame.revalidate();
                 }
                 else if(line.startsWith("GANADOR"))
                 {
                     //se recibem los puntajes
                     String texto = line.substring(8);
                     p.mostrarGanador(texto);
                     break;
                 }
            }
         }catch(Exception e)
         {
             System.out.println("Error en el juego " + e.toString());
             System.exit(1);
         }finally
         {
             p.frame.setVisible(false);
             p.frame.dispose();
         }
        
    }

    public int getNumSala() {
        return numSala;
    }

    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
    }
    
    
    
    public boolean isConVida() {
        return conVida;
    }
    public void setConVida(boolean conVida) {
        this.conVida = conVida;
    }
    public PrintWriter getOut()
    {
        return this.out;
    }

    public int getJugador() {
        return jugador;
    }
    public boolean isEstado() {
        return estado;
    }

    public void setPicar(boolean picar) {
        this.picar = picar;
    }

    public boolean isPicar() {
        return picar;
    }
    
    
    
    
    
    public static void main(String[] args) {
        if (args.length != 1)
        {
            System.err.println("Ingresa la ip del servidor como argumento");
            return;
        }
        BuscaminasCS buscaminas = new BuscaminasCS(args[0]);
        //la variable de la clase areaJuego se asocia a la creada aqui
        areaJuego.c = buscaminas;
        try {
            buscaminas.run();
        } catch (Exception en) {
            System.out.println("Error en la conexion el frame" + en.toString());
            System.exit(1);
        }
    }
    
    
}
