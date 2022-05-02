/*
*   Author: Maximilian
*/

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class Debuging extends Actor
{
    //Flags
    boolean DebugON = false;
    boolean DebugThrow = false;

    
    /*
    *   Dezentrale Terminal Debug Outputfunktion
    *   Kann durch ein Flag ein und ausgeschalten werden
    */
    public void toTerminal(String string)
    {
        if (DebugON)
        {
            System.out.println(string);
        }
    }

    public void toTerminal2(String string)
    {
        if (DebugThrow)
        {
            System.out.println(string);
        }
    }
}