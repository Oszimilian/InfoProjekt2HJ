/*
*   Author: Jonathan
*/

import greenfoot.*; 


public class Ball extends Actor
{
    //Ref
    private MyWorld myWorld;

    /*
    *   Fals die Referenz auf MyWorld mal gebruach wird
    */
    public Ball (MyWorld myWorld)
    {
        this.myWorld = myWorld;
    }    

    public void act() 
    {
        // Erg�nzen Sie Ihren Quelltext hier...
    }

    /*
    *   Diese Funktion �bergibt in einem Array die Aktuelle X und Y Position des Balles
    *   Wenn das Objekt nicht mehr existiert l�st die Exception aus
    */
    public int[] getBallPosition()
    {   
        try{
            return new int[]{getX(), getY()};
        }catch(Exception e)
        {
            return new int[]{0,0};
        }
    }

    public void placeBall(int x, int y)
    {
        setLocation(x,y);
    } 
}