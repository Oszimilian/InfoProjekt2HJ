import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)


public class MyWorld extends World
{
    Debuging debuging = new Debuging();

    EnergyBackground energyBackgroundObject = new EnergyBackground(this);
    EnergyDisplay energyDisplayObject = new EnergyDisplay(this);
    Ball ballObject = new Ball(this); 
    ThrowAngle throwAngleObject = new ThrowAngle(this); 
    ObjectPlacing objectPlacingObject = new ObjectPlacing(this);


    Label labelObject1 = new Label(0,50);
    Label labelObject2 = new Label(0,50);

    ScoreBoard scoreBoardObject = new ScoreBoard(labelObject1, labelObject2, 10, this);


    ObliqueThrow obliqueThrowObject = new ObliqueThrow(this, scoreBoardObject);
    Object object = new Object();
    Target targetObject = new Target(this);


    public MyWorld()
    {    
        super(1200, 700, 1);        

        prepareEnergyDisplay();
        prepareBall();
        prepareThrowAngle();
        prepareObliqueThrow();
        prepareScoreboard();
        prepareTarget();
        objectPlacingObject.selectObjectPreset();
    }
    
    public void prepareTarget()
    {
        addObject(targetObject, 800,100);
    }
    
    public void prepareScoreboard()
    {
        addObject(labelObject1, 1100, 30);
        addObject(labelObject2, 1100, 65);
        addObject(scoreBoardObject, 0, 0);
    }

    public ScoreBoard getScoreBoard()
    {
        return scoreBoardObject;
    }

    public EnergyBackground getEnergyBackground()
    {
        return energyBackgroundObject;
    }  

    public EnergyDisplay getEnergyDisplay()
    {
        return energyDisplayObject;
    }

    public Ball getBall()
    {
        return ballObject;
    }

    public ThrowAngle getThrowAngle()
    {
        return throwAngleObject;
    }

    public ObliqueThrow getObliqueThrow()
    {
        return obliqueThrowObject;
    }

    public Debuging getDebug()
    {
        return debuging;
    }

    public Target getTarget()
    {
        return targetObject;
    }


    public void prepareEnergyDisplay()
    {
        addObject(energyBackgroundObject,100, 400);
        addObject(energyDisplayObject,100,400);
    }

    public void prepareBall()
    { 
        addObject(ballObject, 600, 500);
    }

    public void prepareThrowAngle()
    {
        addObject(throwAngleObject, 0,0);
    }

    public void prepareObliqueThrow()
    {
        addObject(obliqueThrowObject, 0, 0);
    }       

}