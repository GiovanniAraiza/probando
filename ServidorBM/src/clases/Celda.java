package clases;

import clases.areaJuego;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JButton;


public class Celda extends JButton{
     private int x;
     private int y;
     private int tipoCelda;
     private boolean bandera;
     private boolean vis;
 
     
    public Celda(int x, int y) {
        this.x = x;
        this.y = y;
        this.vis=false;
       // this.color= new Color[]{Color.BLUE,Color.GREEN,Color.ORANGE,Color.YELLOW,Color.PINK,Color.BLACK,Color.MAGENTA,Color.lightGray};
        this.setPreferredSize(new Dimension(9, 9));
        this.setBackground(new java.awt.Color(0,0,204));
        this.setFont(new java.awt.Font("Tahoma", 1, 8));
    }

    public void setTipoCelda(int tipoCelda) {
        this.tipoCelda = tipoCelda;
    }

    public int getTipoCelda() {
        return tipoCelda;
    }
    public boolean getVis() {
        return vis;
    }
    public void setVis(boolean vis) {
        this.vis = vis;
    }
    public boolean getBandera() {
        return bandera;
    }
    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }
    
   //se ponen en visto las celdad descubiertas
    public void accionesEnCeldaDe0(int x, int y, areaJuego f)
    {
        f.buttons[x][y].setVis(true);
    }
    public void accionesEnCeldaMayor0(int x, int y, areaJuego f)
    {
        f.buttons[x][y].setVis(true);
    }
     public String accionesEnCelda0ConRecur(int x, int y,int recur, areaJuego f)
    {
        String datos = "";
         if (recur < 50 && (f.buttons[x][y].getVis() == false)) 
         {
             f.buttons[x][y].setVis(true);
             datos = datos + clic(x, y, recur, f);
         }
         return datos;
    }
    //metodo que sirve para descubrir las celdas, el mismo que el cliente
    public String clic(int xx, int yy,int recur , areaJuego f){
        int xActual = xx;
        int yActual = yy;
        String conjuntoDeXY = "";
        conjuntoDeXY = xx + " " + yy + " ";
        f.buttons[xx][yy].setVis(true);
        
        ArrayList<Integer> cerosY = new ArrayList<>();
        ArrayList<Integer> cerosX = new ArrayList<>();
        //izquierda de x
        if(yActual > 0)
        {   
            for(int n = yActual-1;n>-1;n--)
            {
                if (f.buttons[xActual][n].getTipoCelda() == 0) 
                {
                    accionesEnCeldaDe0(xActual, n,f);
                    conjuntoDeXY = conjuntoDeXY + xActual + " " + n + " " ;
                    cerosY.add(n);
                } 
                else if (f.buttons[xActual][n].getTipoCelda() > 0) 
                {
                    accionesEnCeldaMayor0(xActual, n, f);
                    conjuntoDeXY = conjuntoDeXY + xActual + " " + n + " ";
                    if((xActual-1) > -1)
                    {
                         //izquina inferior izquierda
                        if(f.buttons[xActual-1][n].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(xActual-1, n, f);
                           conjuntoDeXY = conjuntoDeXY + (xActual-1) + " " + n + " " ;
                        }
                    }
                    //izquina inferior derecha
                    if((xActual+1) < (f.filas))
                    {
                        if(f.buttons[xActual+1][n].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(xActual+1, n, f);
                           conjuntoDeXY = conjuntoDeXY + (xActual+1) + " " + n + " " ;
                        }
                    }
                    break;
                }
                
            }
        }
        //arriba
        if(xActual > 0)
        {
            for(int n = xActual-1;n>-1;n--)
            {
                if (f.buttons[n][yActual].getTipoCelda() == 0) 
                {
                   accionesEnCeldaDe0(n, yActual,f);
                   conjuntoDeXY = conjuntoDeXY + n + " " + yActual + " " ;
                   cerosX.add(n);
                } 
                else if (f.buttons[n][yActual].getTipoCelda() > 0) 
                {
                    accionesEnCeldaMayor0(n, yActual,f);
                    conjuntoDeXY = conjuntoDeXY + n + " " + yActual + " " ;
                    //izquina superior izquierda
                    if((yActual-1) > -1)
                    {
                        
                        if(f.buttons[n][yActual-1].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(n, yActual-1,f);
                            conjuntoDeXY = conjuntoDeXY + n + " " + (yActual-1) + " " ;
                        }
                    }
                    //izquina superior derecha
                    if((yActual+1) < (f.columnas))
                    {
                        if(f.buttons[n][yActual+1].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(n, yActual+1,f);
                            conjuntoDeXY = conjuntoDeXY + n + " " + (yActual+1) + " " ;
                        }
                    }
                    break;
                }
            }
        }
        //derecha
        if(yActual < f.columnas)
        {
            for(int n = yActual+1;n<f.columnas;n++)
            {
                if (f.buttons[xActual][n].getTipoCelda() == 0) 
                {
                    accionesEnCeldaDe0(xActual, n,f);
                    conjuntoDeXY = conjuntoDeXY + xActual + " " + n + " " ;
                    cerosY.add(n);
                } 
                else if (f.buttons[xActual][n].getTipoCelda() > 0) 
                {
                    accionesEnCeldaMayor0(xActual, n,f);
                    conjuntoDeXY = conjuntoDeXY + xActual + " " + n + " " ;
                    if((xActual-1) > -1)
                    {
                        //revisa hacia arriba
                        if(f.buttons[xActual-1][n].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(xActual-1, n,f);
                            conjuntoDeXY = conjuntoDeXY + (xActual-1) + " " + n + " " ;
                        }
                    }
                    //revisa hacia abajo 
                    if((xActual+1) < (f.filas))
                    {
                        if(f.buttons[xActual+1][n].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(xActual+1, n,f);
                            conjuntoDeXY = conjuntoDeXY + (xActual+1) + " " + n + " " ;
                        }
                    }
                    break;
                }
            }
        }
        //abajo
        if(xActual < f.filas)
        {
            for(int n = xActual+1;n<f.filas;n++)
            {
                if (f.buttons[n][yActual].getTipoCelda() == 0) 
                {
                    accionesEnCeldaDe0(n, yActual,f);
                    conjuntoDeXY = conjuntoDeXY + n + " " + yActual + " " ;
                    cerosX.add(n);
                } 
                else if (f.buttons[n][yActual].getTipoCelda() > 0) 
                {
                    accionesEnCeldaMayor0(n, yActual,f);
                    conjuntoDeXY = conjuntoDeXY + n + " " + yActual + " " ;
                    //izquina inferior izquierda
                    if((yActual-1) > -1)
                    {
                        
                        if(f.buttons[n][yActual-1].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(n, yActual-1,f);
                            conjuntoDeXY = conjuntoDeXY + n + " " + (yActual-1)+ " " ;
                        }
                    }
                    //izquina inferior derecha
                    if((yActual+1) < (f.columnas))
                    {
                        if(f.buttons[n][yActual+1].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(n, yActual+1,f);
                            conjuntoDeXY = conjuntoDeXY + n + " " + (yActual+1) + " " ;
                        }
                    }
                    break;
                }
            }
        }
        if((cerosX.size()) == 0 && (cerosY.size() == 0))
        {
           cerosX.add(xActual);
           cerosY.add(yActual);
        }
        //recorrer cuales fueron 0 en x
        for(int paraRecorrer = 0;paraRecorrer<cerosY.size();paraRecorrer++)
        {
            int revelarY = cerosY.get(paraRecorrer);
            //arriba
            if(xActual > 0)
            {
                for(int n = xActual-1;n>-1;n--)
                {
                   if (f.buttons[n][revelarY].getTipoCelda() == 0) 
                   {
                        accionesEnCeldaDe0(n, revelarY,f);
                        conjuntoDeXY = conjuntoDeXY + n + " " + revelarY + " " ;
                        //verifica si es el ultimo 0 de todo arriba
                        if(n-1 == -1)
                        {
                            if((revelarY-1) > -1)
                            {
                                //celda oeste
                                if(f.buttons[n][revelarY-1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY-1,f);
                                    conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY-1) + " ";
                                }
                                if(f.buttons[n][revelarY-1].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY  + accionesEnCelda0ConRecur(n, revelarY-1, recur+1,f);
                                }
                            }
                            //celda este
                            if((revelarY+1) < (f.columnas))
                            {
                                if(f.buttons[n][revelarY+1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY+1,f);
                                    conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY+1) + " " ;
                                }
                                if(f.buttons[n][revelarY+1].getTipoCelda() == 0)
                                {
                                   conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY+1, recur+1,f);
                                }
                            }
                        }
                        else
                        {
                            //revisa celda oeste
                            if((revelarY-1) > -1)
                            {

                                if(f.buttons[n][revelarY-1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY-1,f);
                                    conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY-1) + " " ;
                                }
                                if(f.buttons[n][revelarY-1].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY-1, recur+1,f);
                                }
                            }
                            //revisa celda este
                            if((revelarY+1) < (f.columnas))
                            {
                                if(f.buttons[n][revelarY+1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY+1,f);
                                    conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY+1)+ " " ;
                                }
                                if(f.buttons[n][revelarY+1].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY+1, recur+1,f);
                                }
                            }
                        }
                    }
                    else if (f.buttons[n][revelarY].getTipoCelda() > 0) 
                    {
                        accionesEnCeldaMayor0(n, revelarY,f);
                        conjuntoDeXY = conjuntoDeXY + n + " " + revelarY + " " ;
                        //izquina superior izquierda
                        if((revelarY-1) > -1)
                        {

                            if(f.buttons[n][revelarY-1].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(n, revelarY-1,f);
                               conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY-1)+ " " ;
                            }
                           if(f.buttons[n][revelarY-1].getTipoCelda() == 0)
                           {
                                conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY-1, recur+1,f);
                           }
                        }
                        //izquina superior derecha
                        if((revelarY+1) < (f.columnas))
                        {
                            if(f.buttons[n][revelarY+1].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(n, revelarY+1,f);
                                conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY+1) + " " ;
                            }
                            if(f.buttons[n][revelarY+1].getTipoCelda() == 0)
                            {
                               
                                conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY+1, recur+1,f);
                            }
                        }
                        break;
                    } 
                }
            }
            //abajo
            if(xActual < f.filas)
            {
                for(int n = xActual+1;n<f.filas;n++)
                {
                    if (f.buttons[n][revelarY].getTipoCelda() == 0) 
                    {
                        accionesEnCeldaDe0(n, revelarY,f);
                       conjuntoDeXY = conjuntoDeXY + n + " " + revelarY + " " ;
                        //verifica si es el ultimo 0 de todo abajo
                        if(n+1 == f.filas)
                        {
                            if((revelarY-1) > -1)
                            {
                                //celda oeste
                                if(f.buttons[n][revelarY-1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY-1,f);
                                   conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY-1) + " " ;
                                }
                                if(f.buttons[n][revelarY-1].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY-1, recur+1,f);
                                }
                            }
                            //celda este
                            if((revelarY+1) < (f.columnas))
                            {
                                if(f.buttons[n][revelarY+1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY+1,f);
                                    conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY+1)+ " " ;
                                }
                                if(f.buttons[n][revelarY+1].getTipoCelda() == 0)
                                {
                                   
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY+1, recur+1,f);
                                }
                            }
                        }
                        else
                        {
                            //revisa celda oeste
                            if((revelarY-1) > -1)
                            {
                                if(f.buttons[n][revelarY-1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY-1,f);
                                    conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY-1)+ " " ;
                                }
                                if(f.buttons[n][revelarY-1].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY-1, recur+1,f);
                                }
                            }
                            //revisa celda este
                            if((revelarY+1) < (f.columnas))
                            {
                                if(f.buttons[n][revelarY+1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY+1,f);
                                    conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY + 1) + " " ;
                                }
                                if(f.buttons[n][revelarY+1].getTipoCelda() == 0)
                                {
                                   conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY+1, recur+1,f);
                                }
                            }
                        }
                    } 
                    else if (f.buttons[n][revelarY].getTipoCelda() > 0) 
                    {
                        accionesEnCeldaMayor0(n, revelarY,f);
                        conjuntoDeXY = conjuntoDeXY + n + " " + revelarY + " " ;
                        //izquierda 
                        if((revelarY-1) > -1)
                        {
                            if(f.buttons[n][revelarY-1].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(n, revelarY-1,f);
                                conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY-1)+ " " ;
                            }
                            if(f.buttons[n][revelarY-1].getTipoCelda() == 0)
                            {
                                
                                conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY-1, recur+1,f);
                                
                            }
                        }
                        //derecha de el ultimo
                        if((revelarY+1) < (f.columnas))
                        {
                            if(f.buttons[n][revelarY+1].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(n, revelarY+1,f);
                                conjuntoDeXY = conjuntoDeXY + n + " " + (revelarY+1)+ " " ;
                            }
                            if(f.buttons[n][revelarY+1].getTipoCelda() == 0)
                            {
                                conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(n, revelarY+1, recur+1,f);
                            }
                        }
                        break;
                    } 
                }
            }
        }
        for(int paraRecorrer = 0;paraRecorrer<cerosX.size();paraRecorrer++)
        {
            int revelarX = cerosX.get(paraRecorrer);
            //izquierda
            if(yActual > 0)
            {
                for(int n = yActual-1;n>-1;n--)
                {
                    if (f.buttons[revelarX][n].getTipoCelda() == 0) 
                    {
                        accionesEnCeldaDe0(revelarX, n,f);
                        conjuntoDeXY = conjuntoDeXY + revelarX + " " + n + " " ;
                        //verifica si es el ultimo 0 de todo a la izquierda
                        if(n-1 == -1)
                        {
                            if((revelarX-1) > -1 )
                            {
                                //celda arriba
                                if(f.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX-1, n,f);
                                    conjuntoDeXY = conjuntoDeXY + (revelarX-1) + " " + n + " " ;
                                }
                                if(f.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX-1, n, recur+1,f);
                                }
                            }
                            //celda abajo
                            if((revelarX+1) < (f.filas))
                            {
                                if(f.buttons[revelarX+1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX+1, n,f);
                                    conjuntoDeXY = conjuntoDeXY + (revelarX+1) + " " + n + " " ;
                                }
                                if(f.buttons[revelarX+1][n].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX+1, n, recur+1,f);
                                }
                            }
                        }
                        else
                        {
                           if((revelarX-1) > -1 )
                           {
                                //revisa celda norte
                                if(f.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX-1, n,f);
                                    conjuntoDeXY = conjuntoDeXY + (revelarX-1) + " " + n + " " ;
                                }
                                if(f.buttons[revelarX-1][n].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX-1, n, recur+1,f);   
                                }
                           }
                           //revisa celda sur
                           if((revelarX+1) < (f.filas))
                           {
                                if(f.buttons[revelarX+1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX+1, n,f);
                                    conjuntoDeXY = conjuntoDeXY + (revelarX+1) + " " + n + " " ;
                                }
                                if(f.buttons[revelarX+1][n].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX+1, n, recur+1,f);
                                }
                           }
                        }
                    } 
                    else if (f.buttons[revelarX][n].getTipoCelda() > 0) 
                    {
                        accionesEnCeldaMayor0(revelarX, n,f);
                        conjuntoDeXY = conjuntoDeXY + revelarX + " " + n + " " ;
                        if((revelarX-1) > -1)
                        {

                            if(f.buttons[revelarX-1][n].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(revelarX-1, n,f);
                                conjuntoDeXY = conjuntoDeXY + (revelarX-1) + " " + n + " " ;
                            }
                            if(f.buttons[revelarX-1][n].getTipoCelda() == 0)
                            {
                               conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX-1, n, recur+1,f);
                            }
                            //veriica si hay otro grupo de 0 en esta posicion
                        }
                        //izquina inferior derecha
                        if((revelarX+1) < (f.filas))
                        {
                            if(f.buttons[revelarX+1][n].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(revelarX+1, n,f);
                               conjuntoDeXY = conjuntoDeXY + (revelarX+1) + " " + n + " " ;
                            }
                            if(f.buttons[revelarX+1][n].getTipoCelda() == 0)
                            {
                                conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX+1, n, recur+1,f);
                            }
                            //veriica si hay otro grupo de 0 en esta posicion  
                        }
                        break;
                    }
                }
            }
            //derecha
            if(yActual < f.columnas)
            {
                for(int n = yActual+1;n<f.columnas;n++)
                {
                    if (f.buttons[revelarX][n].getTipoCelda() == 0) 
                    {
                        accionesEnCeldaDe0(revelarX, n,f);
                        conjuntoDeXY = conjuntoDeXY + revelarX + " " + n + " " ;
                        //verifica si es el ultimo 0 de todo a la derecha
                        if(n+1 == f.columnas)
                        {
                            //norte
                            if((revelarX-1) > -1)
                            {
                                if(f.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                   accionesEnCeldaMayor0(revelarX-1, n,f);
                                   conjuntoDeXY = conjuntoDeXY + (revelarX-1) + " " + n + " " ;
                                }
                                if(f.buttons[revelarX-1][n].getTipoCelda() == 0)
                                {
                                   conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX-1, n, recur+1,f);
                                }
                            }
                            //sur
                            if((revelarX+1) < (f.filas))
                            {
                                if(f.buttons[revelarX+1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX+1, n,f);
                                    conjuntoDeXY = conjuntoDeXY + (revelarX+1) + " " + n + " " ;
                                }
                                if(f.buttons[revelarX+1][n].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX+1, n, recur+1,f);
                                }
                            }
                        }
                        else
                        {
                            if((revelarX-1) > -1)
                            {
                                //practicamente revisa su celda norte
                                if(f.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX-1, n,f);
                                    conjuntoDeXY = conjuntoDeXY + (revelarX-1) + " " + n + " " ;
                                }
                                if(f.buttons[revelarX-1][n].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX-1, n, recur+1,f);
                                }
                            }
                            if((revelarX+1) < (f.filas))
                            {
                                //practicamente revisa su celda sur
                                if (f.buttons[revelarX + 1][n].getTipoCelda() > 0) 
                                {
                                    accionesEnCeldaMayor0(revelarX+1, n,f);
                                  conjuntoDeXY = conjuntoDeXY + (revelarX+1) + " " + n + " " ;
                                }
                                if(f.buttons[revelarX + 1][n].getTipoCelda() == 0)
                                {
                                    conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX+1, n, recur+1,f);
                                }
                            }
                        }
                    } 
                    else if (f.buttons[revelarX][n].getTipoCelda() > 0) 
                    {
                        accionesEnCeldaMayor0(revelarX, n,f);
                        conjuntoDeXY = conjuntoDeXY + revelarX + " " + n + " " ;
                        if((revelarX-1) > -1)
                        {
                            //norte
                            if(f.buttons[revelarX-1][n].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(revelarX-1, n,f);
                               conjuntoDeXY = conjuntoDeXY + (revelarX-1) + " " + n + " " ;
                            }
                            if(f.buttons[revelarX-1][n].getTipoCelda() == 0)
                            {
                               conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX-1, n, recur+1,f);
                            }
                            //veriica si hay otro grupo de 0 en esta posicion
                        }
                        //izquina inferior derecha
                        if((revelarX+1) < (f.filas))
                        {
                            //sur
                            if(f.buttons[revelarX+1][n].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(revelarX+1, n,f);
                               conjuntoDeXY = conjuntoDeXY + (revelarX+1) + " " + n + " " ;
                            }
                            if(f.buttons[revelarX+1][n].getTipoCelda() == 0)
                            {
                                conjuntoDeXY = conjuntoDeXY + accionesEnCelda0ConRecur(revelarX+1, n, recur+1,f);
                            }
                            //veriica si hay otro grupo de 0 en esta posicion
                        }
                        break;
                    }
                }
            }
        }
        return conjuntoDeXY;
    }
}
