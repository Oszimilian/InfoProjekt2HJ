import greenfoot.*;  
/*
*   Author: Maximilian
*/

import javax.swing.*;

public class Object extends Actor
{
    //vars
    int sizeX;
    int sizeY;
    int threshHold = 10;

    //Flags
    private boolean initFlag = true; 
    private boolean sizePrepare = true;

    //Refs
    GreenfootImage Object;
    ObliqueThrow obliqueThrowObject;
    
    /*
    *   Leerer Konstruktor
    */
    public Object()
    {

    }

    /*
    *   Dieser Konstruktor wird ausgeführt, wenn man ein Objekt über die Presetfunktion ausführt
    *   X und Y - Größe werden übergeben
    *   Wenn die Presetfunktion verwendet wird, dann muss keine größe mehr von Hand eingeben werde, deshalb wird "sizePrepare" auf false gesetzt
    */
    public Object(int sizeX, int sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizePrepare = false;
    }

    public void act() 
    {
        intiObject();
    }

    /*
    *   Diese Methode wird nur einmal ausgeführt und vordert den Nutzer einmal auf, die Größe des Objektes einzugeben
    *   Da dieses Objekt auch per Hand erstellt werden kann (ohne Konstruktor) kann ihr nicht immer eine Referenz zur MyWorld-Klasse übergeben werden
    *   Durch die eine "gecastete" getWorld-Referenz und auf MyWorld und die dadurch aufgerufene Getter-Methode, welche eine Referenz auf das ObliqueThrow-Object liefert kann diese auch lokal
    *   gespeichert werden.
    *   Um der ObliqueThrowMethode die Referenz zu diesem Abprallobjekt zu übergeben, wird der transmitReference-Methode über "this" die Referenz übergeben.
    */
    public void intiObject()
    {
        if (initFlag)
        {
            if (sizePrepare) prepareSize();
            prepareImage(); 

            exchangeReference();
            
            initFlag = false;
        }
    }

    public void exchangeReference()
    {
        obliqueThrowObject = ((MyWorld)getWorld()).getObliqueThrow();
        obliqueThrowObject.transmitReference(this);
    }

    /*
    *   In einem Exception-Block wird eine J-Option-Pane eingabeaufforderung ausgeführt um die größe des Abprallobjektes zu bestimmen
    */
    public void prepareSize()
    {
        try{
            sizeX = Integer.parseInt(JOptionPane.showInputDialog(null,"X-Groeße eingeben."," ",JOptionPane.PLAIN_MESSAGE));
            sizeY = Integer.parseInt(JOptionPane.showInputDialog(null,"Y-Groeße eingeben."," ",JOptionPane.PLAIN_MESSAGE));
        }catch(Exception e){
            sizeX = 100;
            sizeY = 100;
        }
    }

    /*
    *   Diese Methode erstellt eine neues Bildobjekt mit der eingegebenen größe
    */
    public void prepareImage()
    {
        Object = new GreenfootImage(sizeX,sizeY);
        Object.setColor(Color.ORANGE);
        Object.drawRect(0,0,sizeX,sizeY);
        Object.fillRect(0,0,sizeX,sizeY);
        setImage(Object);
    }

    /*
    *   Dieses Objekt berechnet die Eckpunkte eines erstellten Abprallobjektes und gibt diese als Array zurück
    */
    public int[] getLocation()
    {
        int[] points = new int[8];

        points[0] = getX() + (int)(sizeX / 2);
        points[1] = getY() + (int)(sizeY / 2);

        points[2] = getX() + (int)(sizeX / 2);
        points[3] = getY() - (int)(sizeY / 2);

        points[4] = getX() - (int)(sizeX / 2);
        points[5] = getY() + (int)(sizeY / 2);

        points[6] = getX() - (int)(sizeX / 2);
        points[7] = getY() - (int)(sizeY / 2);
       
        return points;
    }
    
    /*
    *   Diese Methode überprüft ob die übergebenen x und y Werte sich innerhalb bzw. am Rand des Abprallobjektes befinden und welche Seite sie berühren
    */
    public int detactBounce(int x, int y)
    {
        int[] edgePoint = new int[8];
        edgePoint = getLocation();
        
        if (x == (edgePoint[6] - threshHold) && y >= (edgePoint[7] - threshHold) && y <= (edgePoint[5] + threshHold))
        {
            ((MyWorld)getWorld()).getDebug().toTerminal("kommt von rechts und soll nach links : 0  // X: " + x + " Y: " + y);
            ((MyWorld)getWorld()).getDebug().toTerminal(" x-> " + edgePoint[6] + " " + edgePoint[7] + " <- Y -> " + edgePoint[5]);
            return 0;      //kommt von rechts und soll nach links
        }
        if (x == (edgePoint[2] + threshHold) && y >= (edgePoint[3] - threshHold) && y <= (edgePoint[1] + threshHold))
        {
            ((MyWorld)getWorld()).getDebug().toTerminal("kommt von links und soll nach rechts : 1 // X: " + x + " Y: " + y);
            ((MyWorld)getWorld()).getDebug().toTerminal(" x-> " + edgePoint[2] + " " + edgePoint[3] + " <- Y -> " + edgePoint[1]);
            return 1;      //kommt von links und soll nach rechts
        }
        if (y <= edgePoint[7] && y >= (edgePoint[7] - threshHold) && x >= (edgePoint[6] - threshHold) && x <= (edgePoint[2] + threshHold))
        {
            ((MyWorld)getWorld()).getDebug().toTerminal("kommt von oben und soll nach oben : 2 // X: " + x + " Y: " + y);
            ((MyWorld)getWorld()).getDebug().toTerminal(" y-> " + edgePoint[7] + " " + edgePoint[6] + " <- X -> " + edgePoint[2]);
            return 2;      //kommt von oben und soll nach oben
        }
        if ((y >= edgePoint[5] && y <= (edgePoint[5] + threshHold)) && x >= (edgePoint[4] - threshHold) && x <= (edgePoint[0] + threshHold))
        {
            ((MyWorld)getWorld()).getDebug().toTerminal("kommt von unten und soll nach unten : 3 // X: " + x + " Y: " + y);
            ((MyWorld)getWorld()).getDebug().toTerminal(" y-> " + edgePoint[5] + " " + edgePoint[4] + " <- X -> " + edgePoint[0]);
            return 3;      //kommt von unten und soll nach unten
        }
        
        return 4;          //kann weiter fliegen
    }
}