/*
*   Author: Jonathan
*/

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class ThrowAngle extends Actor
{   
    //Ref
    MyWorld myWorld;    

    /*
    *   Konstruktor speicher die Referenz zu MyWorld lokal
    */
    public ThrowAngle(MyWorld myWorld)
    {
        this.myWorld = myWorld;
    }

    /*
    *   Der Abwurfwinkel wird durch die Funktion showThrowAnlge die ganze zeit Aktualisiert und ausgegeben
    */
    public void act() 
    {
        showThrowAngle();
    } 
    
    /*
    *   Diese Methode gibt die aktuelle Position der Mous, wenn nicht null (also auserhalb des Spielfeldes) in einem Array zurück
    */
    public int[] getMousePosition()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        int mouseX = 0;
        int mouseY = 0;
        
        if (mouse != null) 
        {
            mouseX = mouse.getX();
            mouseY = mouse.getY();
        }

        return new int[] {mouseX, mouseY};
    }

    /*
    *   Diese Methode berechnet den Wurfwinkel zwischen der Mousposition und der Position des Balles, wenn sich der Winkel im ersten Qudranten befindet
    */
    public int calcThrowAngle()
    {
        int mousePoint[] = getMousePosition();
        int ballPoint[] = myWorld.getBall().getBallPosition();

        int x = mousePoint[0] - ballPoint[0];
        int y = mousePoint[1] - ballPoint[1];

        double Angle;

        if (x >= 0 && y <= 0)
        {
            Angle = Math.atan2(-y, x);
            Angle = (Angle * 180) / 3.1415;
            return (int) Angle;
        }else{
            return 0;
        }
    }
    
    /*
    *   Abwurfwinkel wird als Text unterhalb der Energyanzeige ausgegeben
    */
    public void showThrowAngle()
    {
        getWorld().showText(String.valueOf(calcThrowAngle()) + " °", 100, 520);
    }

    /*
    *   Getter-Methode // stellt den Abwurfwinkel als Gradmaß global zu verfügung
    */
    public int getThrowAngle()
    {
        return calcThrowAngle();
    }
}