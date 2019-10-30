
package clientebm;

import clases.areaJuego;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteBM {
    String ipServidor;
    Scanner in;
    PrintWriter out;
    static areaJuego areaJuego; 
    int jugador;
    boolean estado=true;
    boolean picar=false;
    boolean conVida = true;
    int numSala;
    int band;
    ArrayList<Integer> aX = new ArrayList<>();
    ArrayList<Integer> aY = new ArrayList<>();
     
    public ClienteBM(String ip)
    {
        areaJuego = new areaJuego();
        this.ipServidor = ip;
    }
   
    private void run()
    {
         try {
            Socket socket = new Socket(ipServidor, 55001);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(),true);
            //las banderas seran igual al total de minas
            band= areaJuego.getMinas();
            
            while(in.hasNextLine() )
            {
                 String line = in.nextLine();
                 if(line.startsWith("NUMEROJUGADOR"))
                 {
                     insertarNumJugador(line);
                 }
                 else if(line.startsWith("TOTALJUGADORES"))
                 {
                     actualizarTotalJugadores(line);
                 }
                 else if(line.startsWith("HABILITARBOTON"))
                 {
                     habilitarBoton(line);
                 }
                 else if(line.startsWith("EMPEZARPARTIDA"))
                 {
                     empezarPartida(line);
                 }
                 else if(line.startsWith("RESULTADOS"))
                 {
                     coordenadasRecibidas(line);
                     descubrirCoordenadasRecibidas();
                    
                 }
                 else if(line.startsWith("CONTENIDO"))
                 {
                     guardarContenidoCoordenadas(line);
                 }
                 else if(line.startsWith("PONERBANDERAS"))
                 {
                     ponerBanderas(line);
                 }
                 else if(line.startsWith("QUITARBANDERAS"))
                 {
                     quitarBanderas(line);
                 }
                 else if(line.startsWith("GANADOR"))
                 {
                     mostrarGanador(line);
                     break;
                 }else if(line.startsWith("ACTUALIZATE")){ 
                     actualizarMinas(line);
                 }
            }
         }catch(Exception e)
         {
             System.out.println("Error en el juego " + e.toString());
             System.exit(1);
         }finally
         {
             areaJuego.pararCrono();
             areaJuego.frame.setVisible(false);
             areaJuego.frame.dispose();
             
         }
    }
    public void insertarNumJugador(String line)
    {
        //se crea el tablero
        jugador = Integer.parseInt(line.substring(line.indexOf(" ") + 1, line.indexOf(" ") + 2));
        int total = Integer.parseInt(line.substring(line.indexOf("tot") + 3, line.indexOf("tot") + 4));
        numSala = Integer.parseInt(line.substring(line.indexOf("sala") + 4));
        areaJuego.crearTablero(jugador, total);
    }
    public void actualizarTotalJugadores(String line)
    {
        //se modifica la parte donde se muestran los jugadores
        String tot = line.substring(line.indexOf(" ") + 1);
        int tot2 = Integer.parseInt(tot);
        areaJuego.editarDatosZonaJugador(jugador, tot2, areaJuego.getMinas());
        areaJuego.frame.repaint();
        areaJuego.frame.revalidate();
    }
    public void habilitarBoton(String line)
    {
        //se habilita el boton para empezar el juego
        areaJuego.habilitarBoton();
        areaJuego.frame.repaint();
        areaJuego.frame.revalidate();
    }
    public void empezarPartida(String line)
    {
         //se envia mensaje al servidor para salir del bucle
          out.println("ya empezo");
          areaJuego.deshabilitarBoton();
          areaJuego.generarAreaJuego(jugador);
          areaJuego.iniciarCrono();
        //Solo se muestra el area que se puede usar
    }
    public void coordenadasRecibidas(String line)
    {
        //Todo esto para extraer X y Y
        String coord = line.substring(11);
        String restante = coord;
        while (restante.length() > 0) {
            int empezarX = restante.indexOf(" ");
            String coordX = restante.substring(restante.indexOf(" ") - empezarX, restante.indexOf(" "));
            aX.add(Integer.parseInt(coordX));
            restante = restante.substring(restante.indexOf(" ") + 1);
            int empezarY = restante.indexOf(" ");
            String coordY = restante.substring(restante.indexOf(" ") - empezarY, restante.indexOf(" "));
            aY.add(Integer.parseInt(coordY));
            restante = restante.substring(restante.indexOf(" ") + 1);

        }
    }
    public void descubrirCoordenadasRecibidas()
    {
         for(int re = 0;re < aX.size();re++)
         {
             areaJuego.marcarVista(aX.get(re), aY.get(re));
             areaJuego.celdaDescubierta(aX.get(re), aY.get(re));
         }
    }
    public void guardarContenidoCoordenadas(String line)
    {
         //se recibe el contenido en forma de string
         String cont = line.substring(10);
         String restante = cont;
         ArrayList<Integer> contenido = new ArrayList<>();
         //se separa cada contenido de cada par de coordenadas en un array
         while (restante.length() > 1) {
             int empezarDato = restante.indexOf(" ");
             String coordX = restante.substring(restante.indexOf(" ") - empezarDato, restante.indexOf(" "));
             contenido.add(Integer.parseInt(coordX));
             restante = restante.substring(restante.indexOf(" ") + 1);
         }
         int m = mostrarEnCeldaContenido(contenido);
         verSiPerdiste(restante, m);
         
    }
    public int mostrarEnCeldaContenido(ArrayList<Integer> contenido)
    {
        int m=-44;
        //se muestra el contenido de cada coordenadas en el tablero
        for (int re = 0; re < contenido.size(); re++) {
            areaJuego.mostrarContenidoVisualCelda(aX.get(re), aY.get(re), contenido.get(re));
            m = contenido.get(re);
        }
        return m;
    }
    public void verSiPerdiste(String restante, int m)
    {
        //se verifica si un contenido recibido no es una bomba, si es asi, se pierde
        if ((Integer.parseInt(restante)) == jugador && m == -1) {

            //Meter el mostrar perdedor
            areaJuego.pararCrono();
            areaJuego.mostrarPerdedor();
            estado = false;
        }
        aX.clear();
        aY.clear();
    }
    public void ponerBanderas(String line)
    {
        //se recibe las coordenadas y quien puso una bandera
        String texto = line.substring(14);

        String x = texto.substring(0, texto.indexOf(" "));
        texto = texto.substring(texto.indexOf(" ") + 1);

        String y = texto.substring(0, texto.indexOf(" "));
        texto = texto.substring(texto.indexOf(" ") + 1);

        String jug = texto.substring(0, texto.indexOf(" "));
        texto = texto.substring(texto.indexOf(" ") + 1);

        int tot = Integer.parseInt(texto);
        areaJuego.ponerBandera(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(jug));
        //Actualizar banderas
        if (Integer.parseInt(jug) == jugador) {
            areaJuego.editarDatosZonaJugador(jugador, tot, band = band - 1);
        }
        areaJuego.frame.repaint();
        areaJuego.frame.revalidate();
    }
    public void quitarBanderas(String line)
    {
        //se recibe coordenadas y que jugador quito una bandera
        String texto = line.substring(15);
        String x = texto.substring(0, texto.indexOf(" "));
        texto = texto.substring(texto.indexOf(" ") + 1);
        String y = texto.substring(0, texto.indexOf(" "));
        texto = texto.substring(texto.indexOf(" ") + 1);
        String jug = texto.substring(0, texto.indexOf(" "));
        texto = texto.substring(texto.indexOf(" ") + 1);

        int tot = Integer.parseInt(texto);
        //se usa el metodo para quitarla

        if (areaJuego.verSiVista((Integer.parseInt(x)), Integer.parseInt(y))) {
            areaJuego.quitarBanderaConvista((Integer.parseInt(x)), Integer.parseInt(y));
            areaJuego.mostrarContenidoConBandera(Integer.parseInt(x), Integer.parseInt(y));
        } else {
            areaJuego.quitarBandera(Integer.parseInt(x), Integer.parseInt(y));
        }
        //Actualizar banderas
        if (Integer.parseInt(jug) == jugador) {
            areaJuego.editarDatosZonaJugador(jugador, tot, band = band + 1);
        }
        areaJuego.frame.repaint();
        areaJuego.frame.revalidate();
    }
    public void mostrarGanador(String line)
    {
         //se recibem los puntajes
         areaJuego.pararCrono();
         String texto = line.substring(8);
         areaJuego.mostrarGanador(texto);
    }
    public void actualizarMinas(String line)
    {
         //se recibem los puntajes
         String tot = line.substring(12, 13);
         String texto = line.substring(14);
         String fin = line.substring(line.length() - 1);
         int fin2 = Integer.parseInt(fin);
         areaJuego.setMinas(areaJuego.getMinas() + 1);
         band = band + 1;
         areaJuego.editarDatosZonaJugador(jugador, Integer.parseInt(tot), band);

         //System.out.println(texto);
         for (int i = 1; i <= fin2; i++) {
            String x = texto.substring(0, texto.indexOf(","));
            texto = texto.substring(texto.indexOf(",") + 1);
            String y = texto.substring(0, texto.indexOf(" "));
            if (i != fin2) {
                texto = texto.substring(texto.indexOf(" ") + 1);
            }
           // System.out.println("x " + x + " y " + y);
            areaJuego.cambiarTableroBombaAgregada(Integer.parseInt(x), Integer.parseInt(y));
         }
         areaJuego.frame.repaint();
         areaJuego.frame.revalidate();
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
        ClienteBM buscaminas = new ClienteBM(args[0]);
        //la variable de la clase areaJuego se asocia a la creada aqui
        areaJuego.varCliente = buscaminas;
        try {
            buscaminas.run();
        } catch (Exception en) {
            System.out.println("Error en la conexion el frame" + en.toString());
            System.exit(1);
        }
    }
}