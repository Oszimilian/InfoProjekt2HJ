/*
*   Author: Maximilian & Jonathon
*/

import greenfoot.*; 
import java.util.*;

public class ObliqueThrow extends Actor
{
    //Refs
    MyWorld myWorld; 
    Object[] object = new Object[10];
    ScoreBoard scoreBoardObject;

    //Flags
    private boolean startThrowFlag = false;
    private boolean firstThrowFlag = false;
    private boolean leftThrowFlag = false;
    private boolean rightThrowFlag = false;
    
    //Vars
    private static double boardStartX;
    private static double boardStartY;
    private double calcStartX;
    private double calcStartY;
    private double energy;
    private double slideEnergy;
    private double angle;
    private int bounceX;
    private int bounceY;

    //Pro
    private double x = 0;
    private double gravity = -10;
    private boolean direction = true;
    private double lastSlideX;
    private int referenceCounter = 0;
    private double energyLose = 1;
    
    /*
    *   Konstruktor speichert Referenz zur MyWorld-Klasse
    */
    public ObliqueThrow(MyWorld myWorld, ScoreBoard scoreBoardObject)
    {
        this.myWorld = myWorld;
        this.scoreBoardObject = scoreBoardObject;
    }

    /*
    *   polling der Hauptmethoden (permanentes aufrufen)
    */
    public void act() 
    {
        bounceDetection();
        objectBounceDetection();
        throwHandling();
    }

    /*
    *   Methode kümmert sich darum zwischen den drei möglichen Szenarien der Ballbewegung umzuschalten
    *   1. Abwurfbewegung  2. Bewegung nach Rechts (nach Abprallen)   3. Bewegung nach links (nach Abprallen)
    */
    public void throwHandling()
    {
        if(startThrowFlag)
        {
            if(firstThrowFlag)
            {
                /*
                *  Anfangsposition des Balles wird in einer statischen Variable gespeichert
                *  Der StartX und StartY Wert wird auf Null gesetzt, da sich beim Abwurf der Ball im Koordinatenurspurng befindet (Umrechnung auf die realen Koordinaten erfolgt in der Calc-Methode)
                *  Die Abwurfenergy und der Abwurfwinkel werden mit Getter-Methoden global gespeichert. Der Winkel wird in Bogenmaß umgerechnet.
                *  firstThrowFlag wird auf Null gesetzt, somit kann diese if-Bedingung nicht mehr erfüllt sein. 
                */
                ObliqueThrow.boardStartX = (double) myWorld.getBall().getX();
                ObliqueThrow.boardStartY = (double) myWorld.getBall().getY();
    
                calcStartX = 0;
                calcStartY = 0;
    
                energy = myWorld.getEnergyDisplay().getEnergy();
                slideEnergy = energy;
                //energy = 100;
                angle = myWorld.getThrowAngle().getThrowAngle();
                //angle = 30;
                angle = (angle * 3.1415) / 180;

                slideEnergy = slideEnergy * energyLose;
    
                firstThrowFlag = false;
            }

            if(leftThrowFlag)
            {
                /*
                *   In lastSlideX wird der letzte X-Verschiebungswert der Wurffunktion gespeichert
                *   Zusammen mit dem globalen zähler X (bildet nicht den tatsächlichen X-Wert auf dem Spielfeld ab) wird dann der aktuelle X-Wert im KS berechnet.
                *   In pitch wird die Steigung im Abprallpunkt gespeichert.
                *   für die für den neuen X-Verschiebungswert wird die Steigung im Punkt X in slideX gespeichert
                *   In valueAtThrow wird die Abwurfhöhe im KS gespeichert. (Ist zwar in diesem Fall immer Null) -> sollte der Ball aber in späteren Versionen des Spiels Energy verlieren,
                *   wird diese Funktion sehr wichtig für die Berechnung der neuen Funktion.
                *   Die X-Verschiebungsrichtung und Wurfhöhe werden in calcStartX und calcStartY gespeichert.
                *   Damit die dieser Teil nicht immer wieder ausgeführt wird (sondern nur einmal wenn der Ball abprallt), wird leftThrowFlag wieder auf false gestellt.
                */
                
                lastSlideX = calcStartX;
                double deltaX = x + lastSlideX; 

                double pitch = deriveAtPoint(deltaX);
                double slideX = slideToPoint(pitch, x);
                
                double valueAtThrow = valueAtThrow();
                
                ((MyWorld)getWorld()).getDebug().toTerminal("deltaX: " + deltaX + " pitch: " + pitch + " slideX: " + slideX + " ValueAtThrow: " + valueAtThrow);
                
                
                calcStartX = slideX;
                calcStartY = throwHight(valueAtThrow, deltaX);

                ((MyWorld)getWorld()).getDebug().toTerminal("calcStartX: " + calcStartX + " calcStartY: " + calcStartY);

                leftThrowFlag = false;
            }

            if(rightThrowFlag)
            {
                /*
                *   Genau der gleiche ablauf wie oben!
                */
                lastSlideX = calcStartX;
                double deltaX = x + lastSlideX;

                double pitch = deriveAtPoint(deltaX);
                double slideX = slideToPoint(pitch, x);
                
                double valueAtThrow = valueAtThrow();    

                ((MyWorld)getWorld()).getDebug().toTerminal("deltaX: " + deltaX + " pitch: " + pitch + " slideX: " + slideX + " ValueAtThrow: " + valueAtThrow);
                
                calcStartX = slideX;
                calcStartY = throwHight(valueAtThrow, deltaX);

                ((MyWorld)getWorld()).getDebug().toTerminal("calcStartX: " + calcStartX + " calcStartY: " + calcStartY);

                rightThrowFlag = false;
            }
            
            //Diese Methode wird immer ausgeführt und berechnet den Schiefen Wurf.
            calcObliqueThrow();
        }
    }
    
    /*
    *   Methode fragt ab ob der Ball eines der plazierten Objekte berührt
    *   Das Ganze kommt in einem Exception block, da es vorkommen kann, das ein NullPointer entsteht :(
    */
    public void objectBounceDetection()
    {
        try
        {   
            //schleife durchläuft alle exestierenden Abprallobjekte...
            for(int i = 0; i <= referenceCounter - 1; i++)
            {
                //Die Funktion detactBounce() ermittelt, ob die Übergebenen werte ein Object berühren
                //In object[i] steckt die Referenz zu dem Object
                int k = object[i].detactBounce(myWorld.getBall().getX(), myWorld.getBall().getY());
                
                //Da der Ball von vier verschiednen Seiten das Object berühren kann, muss hier unterschieden werden
                switch(k)
                {
                    case 0: leftThrowFlag = true; changeDirection(); scoreBoardObject.incScore(); break;
                    case 1: rightThrowFlag = true; changeDirection(); scoreBoardObject.incScore(); break;
                    case 2: if (direction) leftThrowFlag = true; else rightThrowFlag = true; scoreBoardObject.incScore(); break;
                    case 3: if (direction) leftThrowFlag = true; else rightThrowFlag = true; scoreBoardObject.incScore(); break;
                    case 4: break;
                    default: break; 
                }
            }
        }
        catch(Exception e)
        {
              ((MyWorld)getWorld()).getDebug().toTerminal("blöd, aber es wurde halt ne exception geschmissen!!!");
        }
    }


    /*
    *   Diese Methode überprüft ob der Ball Den Boden/Decke oder die zwei Seitenwände berührt.
    *   Ist dies der Fall wird per Methodenaufruf die Bewegungsrichtung für die nächste Flugkurfe geändert
    */
    public void bounceDetection()
    {
        //int XPos = myWorld.getBall().getX();
        //int YPos = myWorld.getBall().getY();
        int[] position = myWorld.getBall().getBallPosition();
        int XPos = position[0];
        int YPos = position[1];

        if (XPos == (getWorld().getWidth() - 1))
        {
            leftThrowFlag = true;
            changeDirection();
            scoreBoardObject.incScore();
        }
        if (XPos == 1)
        {
            rightThrowFlag = true;
            changeDirection();
            scoreBoardObject.incScore();
        }
        if (YPos <= 1 && direction)
        {
            leftThrowFlag = true;
        }
        if (YPos <= 1 && !direction)
        {
            rightThrowFlag = true;
        }
        if (YPos >= (getWorld().getHeight() - 1) && direction)
        {
            leftThrowFlag = true;
        }
        if (YPos >= (getWorld().getHeight() - 1) && !direction)
        {
            rightThrowFlag = true;
        }
    }

    /*
    *   Diese Methode wechselt beim Aufruf der Funktion den boolean-WERT von direction um die Wurfrichtung zu ändern. (Abprallen und so)
    */
    public void changeDirection()
    {
        if (direction) 
        {
            direction = false;
        }else{
            direction = true;
        }
    }

    /*
    *   Diese Methode berechnet den f(x)-Wert im Abprallpunkt
    */
    public double valueAtThrow()
    {
        double a = (gravity / (2 * Math.pow( Math.cos(angle), 2.0) * Math.pow(energy, 2.0) ) ) * Math.pow((x + calcStartX), 2);
        double b = Math.tan(angle) * (x + calcStartX);
        double c = calcStartY;

        double f = a + b + c;

        return f;
    }

    /*
    *   Diese Methode berechnet die höhe des Abwurfes der neuen Abprallfunktion
    */
    public double throwHight(double valueAtThrow, double deltaX)
    {
        double a = (gravity / (2 * Math.pow( Math.cos(angle), 2.0) * Math.pow(energy, 2.0) ) ) * Math.pow((calcStartX + deltaX), 2);
        double b = Math.tan(angle) * (calcStartX + deltaX);

        double throwHeight = valueAtThrow - a - b;
        
        return throwHeight;
    }

    /*
    *   Diese Methode berechnet die Steigung im Abprallpunkt der Funktion, um später eine Funktion mit genau der negierten Steigung in diesem Punkt zu bilden
    */
    public double deriveAtPoint(double deltaX)
    {
        double pitch = ( (gravity * deltaX) / ( Math.pow(Math.cos(angle), 2) * Math.pow(energy, 2) ) + Math.tan(angle) );

        return pitch;
    }

    /*
    *   Diese Methode berechnet die X-Verschiebung der neuen Funktion, um eine Funktion mit der negierten steigung tangential durch den Abprall punkt laufen zu lassen
    */
    public double slideToPoint(double pitch, double calcStartX)
    {
        double slideX = ( ( ( ((-1 * pitch) - Math.tan(angle)) * Math.pow(Math.cos(angle), 2) * Math.pow(slideEnergy, 2) ) ) / gravity ) - calcStartX;

        return slideX;
    }

    /*
    *   Berechnung der Wurf-Funktion
    *   kann durch "direction" den Wurf in beide richtungen ablaufen lassen
    *   Mit den Statischen Methoden boardStartX und boardStartY wird der reale Punkt auf dem Spielfeld errechnet 
    *   Durch Typcasting und eine Referenz auf das Ballobjekt wird die Position des Balles(Objekt) gesetz
    */
    public void calcObliqueThrow()
    {
        if (direction)
        {
            x = x + 1;
        }else{
            x = x - 1;
        }

        double a = (gravity / (2 * Math.pow( Math.cos(angle), 2.0) * Math.pow(energy, 2.0) ) ) * Math.pow((x + calcStartX), 2);
        double b = Math.tan(angle) * (x + calcStartX);
        double c = 0;

        double f = a + b + c;
        
        double resultX = x + ObliqueThrow.boardStartX;
        double resultY = (-1) * f;
        resultY = (resultY * 2) + ObliqueThrow.boardStartY;

        ((MyWorld)getWorld()).getDebug().toTerminal2(" X: " + resultX + " Y: " + resultY);

        myWorld.getBall().setLocation((int)resultX, (int)resultY);
    }

    /*
    *   Diese Methode wird durch das loslassen der Taste "a" aufgerufen und leitet den Abwurf ein
    *   aufruf dieser Methode befindet sich in EnergyDisplay
    */
    public void startThrow()
    {
        startThrowFlag = true;
        firstThrowFlag = true;
    }
    
    /*
    *   Jedes Mal wenn ein neues Abprallobject erzeugt wird, wird durch den Aufruf dieser Funktion die entsprechende Referenz dieses Objektes in einem neuen Array-Platz gespeichert
    */
    public void transmitReference(Object reference)
    {
        object[referenceCounter] = reference;
        ((MyWorld)getWorld()).getDebug().toTerminal("Ref Success: " + referenceCounter + " Ref: " + reference);
        referenceCounter++;
    }

        
    /*
    *   Startet einen neuen Wurf, bzw. stellt die Startbedingungen für einen neuen Wurf während der Laufzeit her.
    */ 
    public void startNewMotion()
    {
        myWorld.getBall().setLocation((int)ObliqueThrow.boardStartX, (int)ObliqueThrow.boardStartY);
        leftThrowFlag = false;
        rightThrowFlag = false;
        startThrowFlag = false;
        x = 0;
        direction = true;
        myWorld.getEnergyDisplay().setNewThrow();
    }

    /*
    *   Stopt die Bewegung des Wurfes
    */
    public void stopMotion()
    {
        startThrowFlag = false;
    }
}