/*
*   Author: Bastian
*/

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.*;

public class ScoreBoard extends Actor
{
    //vars
    int counter = 0;
    int subCounter = 0;
    int maxFail;

    //Flags
    boolean showFail = true;

    //Refs
    Label labelObject1;
    Label labelObject2;
    Label labelObject3 = new Label(0,100);
    MyWorld myWorld;
    
    /*
    *   Konstruktor Speichert die Referenz auf die Zwei Labels 
    *   �bergabe des Max-Fail-Count an die Klasse
    *   Referenz auf myWorld
    */
    public ScoreBoard(Label labelObject1, Label labelObject2, int maxFail, MyWorld myWorld)
    {
        this.labelObject1 = labelObject1;
        this.labelObject2 = labelObject2;
        this.maxFail = maxFail;
        this.myWorld = myWorld;
    }
    
    /*
    *   Aktualisert die das Sichtbare Label
    *   �berpr�ft ob der Counter gleich dem FailCounter ist
    */
    public void act() 
    {
       updateScore();
       checkCounter();
    }  
    
    /*
    *   Setter-Methode erh�ht bei jedem Abprallen den Fail-Counter
    */  
    public void incScore()
    {
        counter++;
    }

    /*
    *   Erh�ht den Unter-Fail-Counter (Z�hlt alle die Versuche bis zum Abschuss des Zieles)
    */
    public void incSubScore()
    {
        subCounter++;
    }
    
    /*
    *   Aktuallisert das Fail und SubFail Label
    */
    public void updateScore()
    {
        labelObject1.setFillColor(Color.RED);
        labelObject1.setValue(counter + " Fails");
        labelObject2.setFillColor(Color.ORANGE);
        labelObject2.setValue(subCounter + " Subfails");
    }
    
    /*
    *   Wenn der Fail-Counter gleich dem Max-Fail-Counter ist, dann erfolgt eine JOptionPaneAbfrage
    *   Es wird gefragt ob man aufh�ren oder weiter machen m�chte
    *   Wird aufgeh�rt, wird eine neue Instanz der Welt erstellt und das Spiel beginnt nochmal komplett von vorne
    *   Wird weiter gemacht, werden alle bedingungen f�r einen neuen Wurf durch startNewMotion erzeugt
    */
    public void checkCounter()
    {
        if((counter - 1) == maxFail && showFail)
        {
            showFail = false;
            int a = JOptionPane.showConfirmDialog(null, "Willst du einen neuen Versuch starten?");

            if (a == JOptionPane.YES_OPTION)
            {
                myWorld.getObliqueThrow().startNewMotion();
            }else{
                Greenfoot.setWorld(new MyWorld());
            }
    
            resetScore();
        }
    }

    /*
    *  schaft in dieser Klasse die Bedingungen f�r einen neuen Wurf bzw. f�r das Korrekte anzeigen des Spielstandes
    */
    public void resetScore()
    {
        incSubScore();
        counter = 0;
        getWorld().removeObject(labelObject3);
        showFail = true;
    }

    /*
    *   Zeigt den Winningscreen   
    */
    public void showWinningScreen()
    {
        labelObject3.setFillColor(Color.RED);
        labelObject3.setValue("You Won");
        getWorld().addObject(labelObject3, 600, 400);
        getWorld().showText("Press Reset to start a new Game!", 600, 450);
        myWorld.getObliqueThrow().stopMotion();
    }
}
