/*
*   Author: Jonathan
*/

import greenfoot.*;  
import javax.swing.*;
import java.util.*;

public class ObjectPlacing  
{
    //Ref
    private MyWorld myWorld;

    public ObjectPlacing(MyWorld myWorld)
    {
        this.myWorld = myWorld;
    }

    /*
    *   Durch eine JOptionPane-OptionDialog Abfrage, kann zwischen verschiedenen Vorgefertigten Spielfeldern (mit Abprallobjekten) gewählt werden
    */
    public void selectObjectPreset()
    {
        String[] options = {"Pre 1", "Pre 2", "Pre 3", "No"};

        int x = JOptionPane.showOptionDialog(null, "Bitte Preset Wählen", "Einstellungen", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        switch(x)
        {
            case 0: presetOne(); break;
            case 1: presetTwo(); break;
            case 2: presetThree(); break;
            case 3: break;
            default: break;
        }
    }

    public void presetOne()
    {
        Object object1 = new Object(200,100);
        Object object2 = new Object(150, 50);
        Object object3 = new Object(200, 300);

        myWorld.addObject(object1, 100, 100);
        myWorld.addObject(object2, 500, 600);
        myWorld.addObject(object3, 1000, 400);
    }

    public void presetTwo()
    {
        Object object1 = new Object(200,100);
        Object object2 = new Object(150, 50);
        Object object3 = new Object(200, 300);
        Object object4 = new Object(200,100);
        Object object5 = new Object(150, 50);
        Object object6 = new Object(20, 300);

        myWorld.addObject(object1, 100, 6000);
        myWorld.addObject(object2, 550, 450);
        myWorld.addObject(object3, 1100, 400);
        myWorld.addObject(object4, 800, 550);
        myWorld.addObject(object5, 400, 300);
        myWorld.addObject(object6, 800, 200);

        myWorld.getBall().placeBall(200,600);
        myWorld.getTarget().placeTarget(1000, 150);
    }

    public void presetThree()
    {
        Object object1 = new Object(200,100);
        Object object2 = new Object(150, 50);
        Object object3 = new Object(200, 300);
        Object object4 = new Object(200,100);
        Object object5 = new Object(150, 50);
        Object object6 = new Object(20, 300);

        myWorld.addObject(object1, 100, 6000);
        myWorld.addObject(object2, 550, 450);
        myWorld.addObject(object3, 1100, 400);
        myWorld.addObject(object4, 800, 550);
        myWorld.addObject(object5, 400, 300);
        myWorld.addObject(object6, 800, 200);

        myWorld.getBall().placeBall(200,600);
        myWorld.getTarget().placeTarget(1000, 150);
    }
}