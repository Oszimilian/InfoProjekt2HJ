/*
*   Author: Bastian
*/

import greenfoot.*;

public class EnergyBackground extends Actor
{
    //Vars
    private int x = 80;
    private int y = 160;

    //Flags
    private boolean presetFlag = true;

    //Refs
    private GreenfootImage Background;
    private MyWorld myWorld;

    /*
    *   Konstruktor speichert lokal die Referenz von MyWorld
    */ 
    public EnergyBackground(MyWorld myWorld)
    {
        this.myWorld = myWorld;
    }

    public void act() 
    {
        InitPreset();
    } 

    /*
    *   Diese Funktion wird nur einmal ausgeführt, um den Hintergrund für das EnergyDisplay zu erstellen
    */  
    public void InitPreset()
    {
        if (presetFlag)
        {
            Background = new GreenfootImage(x,y);        
            placeEnergyBackground(x, y);
        }

        presetFlag = false;
    }

    /*
    *   Skalliert den Grauen Hintergrund für das EnergyDisplay
    */
    public void placeEnergyBackground(int x, int y)
    {
        Background.setColor(Color.GRAY);
        Background.drawRect(0,0,x,y);
        Background.fillRect(0,0,x,y);
        setImage(Background);
    } 
}