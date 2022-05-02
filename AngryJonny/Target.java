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
    *   �berpr�ft ob der Ball das TargetObjekt ber�hrt
    *   M�chte die Explosionsanimation ausf�hren
    */
    public void act() 
    {
        updateTargetDestroy();
        showExplosion();
    }
    
    /*
    *   Wenn das Spiel noch nicht gewonnen ist, wird �berpr�ft ob sich der Ball in der n�he des Target befindet
    *   Ist der Speicherort nicht NULL wird der Ball von der Welt entfernt.
    *   Das Flag f�r die Explosion wird gesetzt
    *   durch winFlag wird danach verhindert, das diese Funktion noch mal ausgef�rht wird
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