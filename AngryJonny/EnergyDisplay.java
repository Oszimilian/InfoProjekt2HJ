/*
*   Author: Bastian
*/

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Erg�nzen Sie hier eine Beschreibung f�r die Klasse EnergyDisplay.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class EnergyDisplay extends Actor
{
    //vars
    private int x = 40;
    private int y = 150; 
    private int loadCounter = 0;

    //flags
    private boolean presetFlag = true; 
    private boolean loadPermission = true;
    private boolean reloadFlag = true;

    //Refs
    private MyWorld myWorld;
    private GreenfootImage Display; 

    /*
    *   Konstruktor speicher Referenz auf das MyWorld Objekt
    */
    public EnergyDisplay(MyWorld myWorld)
    {
        this.myWorld = myWorld;
    }

    /*
    *   Neben der InitMethode wird hier dauernd die eingestellte Energy durch den tastendurck abgefragt und die gr��e des Ladebalckens gepr�ft
    */
    public void act() 
    {
        InitPreset();
        loadEnergy();
        showEnergyFont();
    }
    
    /*
    *   Einmaliges erstellen des Roten Ladebalkens
    */
    public void InitPreset()
    {
        if (presetFlag)
        {
            Display = new GreenfootImage(x,y);
            placeEnergyDisplay(x, y);
        }
        
        presetFlag = false;
    }

    /*
    *   Skalierung des Roten Ladebalckens, kann auch im nachhinein ver�ndert werden
    */
    public void placeEnergyDisplay(int x,  int y)
    {
        Display.clear();
        Display.setColor(Color.RED);
        Display.drawRect(0,0, x, y);
        Display.fillRect(0,0, x, y);
        Display.rotate(180);
        setImage(Display);
    }

    /*
    *   Setzt die Prozentuale Energy in eine h�he des Ladebalkens um
    */
    public void setEnergy(int percentage)
    {
        float value = (percentage * y) / 100;         
        
        placeEnergyDisplay(x, (int) value);
    }

    /*
    *   Diese Methode erh�ht die Energy je l�nger die Taste a gedr�ckt wird
    *   Wird a losgelassen wird duch startThrow() der Wurf gestartet
    */
    public void loadEnergy()
    {
        setEnergy(loadCounter);
        
        if (Greenfoot.isKeyDown("a") && reloadFlag)
        {
            if (loadPermission) loadCounter = 0;
            if (loadCounter < 100)
            {
                loadPermission = false;
                loadCounter++;
            }
        }
        if (!Greenfoot.isKeyDown("a") && loadCounter > 0 && reloadFlag)
        {
            loadPermission = true;
            myWorld.getObliqueThrow().startThrow();
            reloadFlag = false;
        }
    }

    /*
    *   Zeigt die Energy in Prozent als Text unter dem Ladebalcken an
    */ 
    public void showEnergyFont()
    {
        getWorld().showText(String.valueOf(loadCounter) + "%",100,500);
    }

    /*
    *   Getter-Methode // �bergibt die Energy f�r den Wurf
    */
    public int getEnergy()
    {
        return loadCounter;
    }

    /*
    *   Stellt w�hrend des Spiels den Zustand vom Start des Spieles herstellen um einen neuen Abwurf zu generieren
    */ 
    public void setNewThrow()
    {
        loadCounter = 0;
        reloadFlag = true;
    }
}