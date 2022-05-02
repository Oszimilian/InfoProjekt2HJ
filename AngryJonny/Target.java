/*
*   Author: Jonathan
*/

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class Target extends Actor
{
    //Refs
    MyWorld myWorld;

    //Flags
    boolean explosionFlag = false;
    boolean winFlag = true;

    GifImage gifImageObject = new GifImage("explosion.gif");
    
    /*
    *   Konstruktor mit Referenz auf MyWorld
    */
    public Target(MyWorld myWorld)
    {
        this.myWorld = myWorld;
    }    

    /*
    *   Überprüft ob der Ball das TargetObjekt berührt
    *   Möchte die Explosionsanimation ausführen
    */
    public void act() 
    {
        updateTargetDestroy();
        showExplosion();
    }
    
    /*
    *   Wenn das Spiel noch nicht gewonnen ist, wird überprüft ob sich der Ball in der nähe des Target befindet
    *   Ist der Speicherort nicht NULL wird der Ball von der Welt entfernt.
    *   Das Flag für die Explosion wird gesetzt
    *   durch winFlag wird danach verhindert, das diese Funktion noch mal ausgefürht wird
    *   Der Winningscreen wird angezeigt
    */
    public void updateTargetDestroy()
    {
        if (winFlag)
        {
            Actor ball = getOneObjectAtOffset(10, 10, Ball.class);
            if (ball != null)
            {
                //myWorld.getObliqueThrow().startNewMotion();
                getWorld().removeObject(myWorld.getBall());
                explosionFlag = true;
                winFlag = false;
                myWorld.getScoreBoard().showWinningScreen();
            }
        }
    } 

    /*
    *   Wechselt das Bild des Zieles zu einem Gif
    */
    public void showExplosion()
    {
        if (explosionFlag)
        {
            this.setImage(gifImageObject.getCurrentImage());
        }
    }

    public void placeTarget(int x, int y)
    {
        this.setLocation(x,y);
    }
}