import java.util.Random;
import java.awt.Graphics;
import java.lang.Math;

public class Alien{
  double xVel, yVel, xAlien, yAlien;
  double xShip, yShip;
  Random rnd;
  
  public Alien(int xShip, int yShip){
    rnd = new Random();
    this.xShip = xShip;
    this.yShip = yShip;
    xVel = rnd.nextDouble()*2-1;
    yVel = rnd.nextDouble()*2-1;
    xAlien = (rnd.nextDouble() * 968);
    yAlien = (rnd.nextDouble() * 618);
    while(Math.abs(xAlien-xShip)<150 && Math.abs(yAlien-yShip)<150){
      xAlien = rnd.nextDouble() * 968;
      yAlien = rnd.nextDouble() * 618;
    }
    
  }
  
  public void draw(Graphics g){
    g.fillOval((int)xAlien, (int)yAlien, 30, 30);
  }
  
  public void move(){
    if(xAlien<=0 || xAlien>=970){
     xVel = -xVel;
    }
    if(yAlien<=0 || yAlien>=620){
      yVel = -yVel;
    }
    xAlien += xVel; yAlien += yVel;
  }
  
  /*public boolean collidesWith(int xBullet, int yBullet){
    if((xAlien>=xBullet) && (xAlien<=xBullet+10) && (yAlien>=yBullet) && (yAlien<=yBullet+10)){
     return true; 
    }
    else return false;
  }*/
  
  public int getDistanceToBullet(double xBullet, double yBullet){
    double x = xAlien-xBullet;
    double y = yAlien-yBullet;
    return (int) Math.sqrt((x*x)+(y*y));
  }
  
  public int getDistanceToShip(double xS, double yS){
    double x = xAlien-xS+40;
    double y = yAlien-yS+40;
    return (int) Math.sqrt((x*x)+(y*y));
  }
  
  public boolean shipCollision(int shipX, int shipY){
    if(Math.pow(40, 2) > Math.pow(shipX-xAlien, 2) + Math.pow(shipY - yAlien, 2)){
      return true;
    }
    else return false;
  }
  
  public int getX() {return (int)xAlien;}
  public int getY() {return (int)yAlien;}
}