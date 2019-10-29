import forms.areaJuego;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static int cont = 0;
    private static boolean iniciarJuego = false;
    //guarda los jugadores que se vayan uniendo hasta maximo de 4
    private static HashMap<Integer,PrintWriter> jugadores = new HashMap<>();
    //guarda el numero de sala y el numero de jugador mas su canal de salida
    private static HashMap<Integer,HashMap<Integer,PrintWriter>> salas = new HashMap<Integer,HashMap<Integer,PrintWriter>>();
    //guarda que salas estan disponibles
    private static Map<Integer,Boolean> esSalaAbierta = new HashMap<>();
    //guarda las matrices creadas para ser consultadas
    private static Map<Integer,areaJuego> matrices = new HashMap<Integer,areaJuego>();
    //guarda el numero de sala con el numero de matriz asociada
    private static Map<Integer,Integer> consultarMatrices = new HashMap<>();
    //guarda el puntaje de cada jugador
    private static Map<PrintWriter,Integer> puntaje = new HashMap<>();
    //
    private static Map<Integer,Integer >ded = new HashMap<>();
    private static int mm =0;
    //guarda las banderas que ha puesto cada jugador
    private static Map<String,PrintWriter> banderas = new HashMap<>();
    //guarda el total de minas de cada sala
    private static Map<Integer,Integer> totalMinas = new HashMap<>();
    //guarda el numero de minas puestas en cada sala
     private static Map<Integer,Integer> minasPuestas = new HashMap<>();
     //se usan para poder crear ka matriz al iniciar el juego
    private static boolean partidaEmpezada = false;
    private static boolean matrizGenerada = false;
    private static int consultMatriz = 0;
    //sirve para saber que sala ya termino su juego
    private static int salaPartidaTerminada = 0;

    public static void main(String[] args) {
       System.out.println("Ejecuando buscaminas...");
        ExecutorService pool = Executors.newFixedThreadPool(400);
        try (ServerSocket listener = new ServerSocket(55001)) {
            while (true)
            {
                pool.execute(new Handler(listener.accept()));
            }
        }catch(Exception e)
        {
            System.out.println("Error al crear el pool " + e.toString());
            System.exit(1);
        }
    }
    private static class Handler implements Runnable
    {
            private Socket socket;
            private Scanner in;
            private PrintWriter out;
            
            //intentar que sea lo mas rapido posible porque corre en el main
            public Handler(Socket socket)
            {
                this.socket = socket;
            }
           
            @Override
            public void run()
            {
                try {
                    in = new Scanner(socket.getInputStream());
                    out = new PrintWriter(socket.getOutputStream(),true);
                    cont++;
                    //verifica que no hay salas
                    if(salas.isEmpty())
                    {
                        salas.put(salas.size()+1, null);
                        esSalaAbierta.put(salas.size(), true);
                        totalMinas.put(salas.size(), 50);
                        minasPuestas.put(salas.size(), 0);
                        ded.put(salas.size(), 0);
                    }
                    else
                    {
                        //verifica que la ultima sala este disponible
                        if(!esSalaAbierta.get(salas.size()))
                        {
                            salas.put(salas.size()+1, null);
                            esSalaAbierta.put(salas.size(), true);
                            totalMinas.put(salas.size(), 50);
                            minasPuestas.put(salas.size(), 0);
                            cont = 1;
                            jugadores.clear();
                            ded.put(salas.size(), 0);
                            //System.out.println(" ee " + salas.size());
                        }
                        else
                        {
                            //verifica si ya se unio un 5to jugador, si es asi, se crea otra sala
                            if(cont > 4)
                            {
                                esSalaAbierta.replace(salas.size(), false);
                                salas.put(salas.size()+1, null);
                                esSalaAbierta.put(salas.size(), true);
                                totalMinas.put(salas.size(), 50);
                                minasPuestas.put(salas.size(), 0);
                                cont = 1;
                                jugadores.clear();
                                ded.put(salas.size(), 0);
                            }
                        }
                    }
                    //se agrega el jugador y su puntaje inicial
                    jugadores.put(cont, out);
                    //se va guardando en las salas los jugadores que ingresan
                    salas.replace(salas.size(), new HashMap(jugadores));
                   
                    out.println("NUMEROJUGADOR " + cont + "tot" + cont + "sala" + salas.size());
                    puntaje.put(out, 0);
                    int cantJug = cont;
                    if(cantJug >= 1) 
                    {
                        for(int x = 1;x<=cantJug;x++)
                        {
                           jugadores.get(x).println("TOTALJUGADORES " + cantJug);
                        }
                    }
                    
                    if (cantJug > 1) 
                    {
                        for(int x = 1;x<=cantJug;x++)
                        {
                           jugadores.get(x).println("HABILITARBOTON");
                        }
                    }
                    if(cantJug >= 1)
                    {
                        //ciclo que sirve para verificar quien inicio y hacer que los demas jugadores de la sala puedan iniciar
                        while(!partidaEmpezada)
                        {
                            String juego = in.nextLine();
                            Map<Integer,PrintWriter> jugadoresSala = new HashMap<>();
                            int sa = 0;
                            //sirve para saber quien presiono el boton
                            if(juego.startsWith("EMPEZARPARTIDA"))
                            {
                                sa = Integer.parseInt(juego.substring(15)); 
                                jugadoresSala.putAll(salas.get(sa));
                            }
                            //para crear la matriz a esa sala
                            if(!partidaEmpezada && juego.startsWith("EMPEZARPARTIDA"))
                            { 
                               for(int x = 1;x<=jugadoresSala.size();x++)
                               {
                                   jugadoresSala.get(x).println("EMPEZARPARTIDA");                  
                               }
                               partidaEmpezada = true;
                               esSalaAbierta.replace(sa, false);
                               matrices.put(matrices.size()+1, new areaJuego());
                               matrizGenerada = true;
                               consultarMatrices.put(sa, matrices.size());
                            }
                            //para que el resto de jugadores de la sala salgan del bucle
                            if(juego.startsWith("ya empezo"))
                            {
                                break;
                            }
                        }
                            
                    }
                        matrizGenerada = false;
                        jugadores = new HashMap<>();
                        partidaEmpezada = false;
                        
                    //ciclo que sirve para detectar los clics izquierdos o derechos
                    while(true)
                    {
                        
                        int jug=0;
                        String ladoMouse = in.nextLine();
                        //System.out.println(ladoMouse);
                        if(ladoMouse.startsWith("IZQUIERDO"))
                        {
                            //se toman las gordenadas y la sala de donde provienen
                            String coordenadas = ladoMouse.substring(10);
                            String recortando =  coordenadas;
                            int x = Integer.parseInt(recortando.substring(0, recortando.indexOf(" ")));
                            recortando = recortando.substring(recortando.indexOf(" ")+ 1);
                           
                            int y = Integer.parseInt(recortando.substring(0, recortando.indexOf(" ")));
                            recortando = recortando.substring(recortando.indexOf(" ")+ 1);
                           
                            int sala = Integer.parseInt(recortando.substring(5));
                            //System.out.println( x + " " + y + " " + sala);
                            
                            String sinc = x + " " + y;
                            //se sincroniza para no repetir clics
                            synchronized(sinc)
                            {
                                int numeroMatriz = consultarMatrices.get(sala);
                                areaJuego f = (areaJuego)matrices.get(numeroMatriz);
                               
                                Map<Integer,PrintWriter> jugadoresSala = new HashMap<>();
                                jugadoresSala.putAll(salas.get(sala));
                                //f.imprimirMatriz();
                                //se verifica si la celda no esta ya esta descubierta o con bandera
                                if(!f.verSiVista(x, y) && !f.verSiBandera(x, y))
                                {
                                    if(f.obtenerContenido(x, y) == 0)
                                    {
                                        //se obtienen todas las celdas que se abren si la celda es 0
                                        String res = f.resultadoCoordenadas(x, y, f);
                                        //System.out.println(res);
                                        for(int n = 1;n<=jugadoresSala.size();n++)
                                        {
                                            
                                            jugadoresSala.get(n).println("RESULTADOS " + res);
                                       
                                        }
                                         //se guardan en arrays las coordenas x y y para usarlas al enviar el contenido
                                         ArrayList<Integer> aX = new ArrayList<>();
                                         ArrayList<Integer> aY = new ArrayList<>();
                                         String restante = res;
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
                                         //se obtiene el contenido de las coordenadas anteriores y se envian
                                         String contenido = "";
                                         for (int re = 0; re < aX.size(); re++) 
                                         {
                                           
                                            contenido = contenido + f.obtenerDatos(aX.get(re), aY.get(re)) + " ";
                                         }
                                         for(int n = 1;n<=jugadoresSala.size();n++)
                                         {
                                            jugadoresSala.get(n).println("CONTENIDO " + contenido + jug);
                                            
                                         }
                                         
                                    }
                                    //verifica si la celda tiene contenido mina o numero y se envian las coordenadas y el cotenido
                                    else if(f.obtenerContenido(x, y) == -1 || f.obtenerContenido(x, y) > 0)
                                    {
                                        String res = x + " " + y + " ";
                                        for(int n = 1;n<=jugadoresSala.size();n++)
                                        {
                                            jugadoresSala.get(n).println("RESULTADOS " + res);
                                            
                                            f.ponerVista(x, y);
                                        }
                                        jug=0;
                                        String contenido = "";
                                        contenido = contenido + f.obtenerDatos(x,y) + " ";
                                        for(int n = 1;n<=jugadoresSala.size();n++)
                                        {
                                            
                                            if(jugadoresSala.get(n) == out)
                                            {
                                                jug=n;
                                            }
                                            jugadoresSala.get(n).println("CONTENIDO " + contenido +jug);
                                            
                                        }
                                        if(f.obtenerContenido(x, y) == -1)
                                        {
                                            totalMinas.replace(sala, totalMinas.get(sala)-1);
                                            System.out.println(totalMinas);
                                           // ded.replace(sala, 3);
                                           // System.out.println("es sala" + sala);
                                            ded.replace(sala, (ded.get(sala)+1));
                                          //  System.out.println((int)ded.get(sala));
                                        }
                                    }
                                }
                            }
                           
                            Map<Integer, PrintWriter> d =  salas.get(sala);
                            //salas.get(1);
                           
                            int tt =d.size();
                            //System.out.println(ded.get(sala));
                            //System.out.println("jogadores " + tt);
                            if (ded.get(sala) == tt) {
                                System.out.println("sd");
                                salaPartidaTerminada = sala;
                                String puntajeTotal = "";
                                Map<Integer, PrintWriter> jugadoresSala = new HashMap<>();
                                jugadoresSala.putAll(salas.get(salaPartidaTerminada));
                                for (int n = 0; n < jugadoresSala.size(); n++) {
                                    PrintWriter pr = jugadoresSala.get(n + 1);
                                    puntajeTotal = puntajeTotal + "Jugador " + (n + 1) + ": " + puntaje.get(pr) + "     ";
                                }
                                
                                for (int n = 0; n <= jugadoresSala.size(); n++) {
                                    jugadoresSala.get(n + 1).println("GANADOR " + puntajeTotal);
                                }
                                
                                //mm=0;
                            }
                            
                        }
                        else if(ladoMouse.startsWith("DERECHO"))
                        {
                            //se obtienen contenido y sala 
                            String coorDer = ladoMouse.substring(8);
                            String recortando =  coorDer;
                            int x = Integer.parseInt(recortando.substring(0, recortando.indexOf(" ")));
                            recortando = recortando.substring(recortando.indexOf(" ")+ 1);
                           
                            int y = Integer.parseInt(recortando.substring(0, recortando.indexOf(" ")));
                            recortando = recortando.substring(recortando.indexOf(" ")+ 1);
                           
                            int sala = Integer.parseInt(recortando.substring(5,recortando.indexOf("u")));
                            recortando = recortando.substring(recortando.indexOf("u")+2);
                            int bi = Integer.parseInt(recortando);
                            //System.out.println(bi);
                            String sincro = x + " " + y;
                            System.out.println("Sala: " + sala);
                            System.out.println(x + " " + y);
                            synchronized(sincro)
                            {
                                Map<Integer,PrintWriter> jugadoresSala = new HashMap<>();
                                jugadoresSala.putAll(salas.get(sala));
                                
                                String bandera =  x + " " + y;
                                int numeroMatriz = consultarMatrices.get(sala);
                                areaJuego fe = matrices.get(numeroMatriz);
                                //se verifica si la celda ya se vio
                                if(!fe.verSiVista(x, y))
                                {
                                    //se verifica si tiene bandera
                                    if(!fe.verSiBandera(x, y) && bi>0)
                                    {
                                        //se obtiene el contenido y si es mina se suma puntos y minas puestas
                                        int conte = fe.obtenerContenido(x, y);
                                        if(conte == -1)
                                        {
                                            puntaje.replace(out, puntaje.get(out)+1);
                                            minasPuestas.replace(sala, minasPuestas.get(sala)+1);
                                        }
                                        //se guarda la coordenada de la bandera y se pone la marca de que ya tiene bandera
                                        banderas.put(bandera, out);
                                        fe.ponerBandera(x, y);
                                        int jugBand = 0;
                                        for(int n = 1;n<=jugadoresSala.size();n++)
                                        {
                                            if(jugadoresSala.get(n) == out)
                                            {
                                                jugBand = n;
                                            }
                                        }
                                        //se envian a todos los jugadores para que actualicen sus tableros
                                        for(int n = 1;n<=jugadoresSala.size();n++)
                                        {
                                            jugadoresSala.get(n).println("PONERBANDERAS " + bandera + " " + jugBand + " " +jugadoresSala.size());
                                        }
                                        //cuando se pone una bandera se verifica si ya se marcaron todas para terminar el juego
                                        if(minasPuestas.get(sala) == totalMinas.get(sala))
                                        {
                                            salaPartidaTerminada = sala;
                                            break;
                                        }
                                    }
                                    else if(bi>=0)
                                    {
                                        //se revisa si quien quito bandera es el mismo que la puso
                                        if(out == banderas.get(bandera))
                                        {
                                            //se obtiene el contenido y si es mina se resta 1 punto
                                            int conte = fe.obtenerContenido(x, y);
                                            if(conte == -1)
                                            {
                                                puntaje.replace(out, puntaje.get(out)-1);
                                                minasPuestas.replace(sala, minasPuestas.get(sala)-1);
                                            }
                                            //se quita la marca de bandera de la celca y se envia la actualizacion a los jugadores de la sala
                                            fe.quitarBandera(x, y);
                                            banderas.remove(bandera);
                                            int jugBand = 0;
                                            for(int n = 1;n<=jugadoresSala.size();n++)
                                            {
                                              if(jugadoresSala.get(n) == out)
                                              {
                                                jugBand = n;
                                              }
                                            }
                                            for(int n = 1;n<=jugadoresSala.size();n++)
                                            {
                                                jugadoresSala.get(n).println("QUITARBANDERAS " + bandera + " " + jugBand + " " + jugadoresSala.size());
                                            }
                                        }
                                        
                                    }
                                }
                                else
                                {
                                    if(fe.verSiBandera(x, y))
                                    {
                                        if(out == banderas.get(bandera))
                                        {
                                            fe.quitarBandera(x, y);
                                            banderas.remove(bandera);
                                            int jugBand = 0;
                                            for(int n = 1;n<=jugadoresSala.size();n++)
                                            {
                                              if(jugadoresSala.get(n) == out)
                                              {
                                                jugBand = n;
                                              }
                                            }
                                            for(int n = 1;n<=jugadoresSala.size();n++)
                                            {
                                                jugadoresSala.get(n).println("QUITARBANDERAS " + bandera + " " + jugBand + " " + jugadoresSala.size());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                //se obtiene la sala que ya acabo el juego, los puntajes de cada jugador y se envian para mostrar resultado
                String  puntajeTotal= "";
                Map<Integer,PrintWriter> jugadoresSala = new HashMap<>();
                jugadoresSala.putAll(salas.get(salaPartidaTerminada));
                for(int n = 0;n<jugadoresSala.size();n++)
                {
                    PrintWriter pr = jugadoresSala.get(n+1);
                    puntajeTotal = puntajeTotal + "Jugador " + (n+1) + ": " + puntaje.get(pr) + "     ";
                }
                for(int n = 0;n<=jugadoresSala.size();n++)
                {
                        jugadoresSala.get(n+1).println("GANADOR " + puntajeTotal);
                }
                
                }catch(Exception ee){
                    System.out.println(ee);
                }finally
                {
                   
                }
            }
        
        }
}
