import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.lang.Math;

public class AlienAttack extends Applet implements KeyListener, Runnable
{
  final int HEIGHT = 650, WIDTH = 1000;
  double xVel; double yVel; final double SPEED = 0.02;
  Thread thread;
  int direction;
  double x, y;
  final double FRICTION = 0.98;
  boolean upAccel, downAccel, leftAccel, rightAccel;
  ArrayList<Shot> shots;
  ArrayList<Alien> aliens;
  boolean shipActive;
  
  public void init(){
    x=475;
    y=300;
    direction = 1;
    xVel = 0; yVel = 0;
    shipActive = true;
    upAccel = false; downAccel = false; leftAccel = false; rightAccel = false;
    this.resize(WIDTH, HEIGHT);
    shots = new ArrayList<Shot>();
    aliens = new ArrayList<Alien>();
    this.addKeyListener(this);
    thread = new Thread(this);
    thread.start();
  }
  
  public void update(Graphics g){
    paint(g);
  }
  
  public void paint(Graphics g){
    
    //DRAWS AN OVAL AND STUFF
    /*g.setColor(Color.BLUE);
     g.drawOval(100, 50, 300, 400);
     g.fillOval(175, 150, 30, 30);
     g.fillOval(295, 150, 30, 30);
     g.setColor(Color.RED);
     g.fillRect(230, 220, 40, 40);*/
    
    
    //DRAWS A STAR
    /*int[] xPoints = {50, 80, 90, 100, 150, 150, 200, 210, 220, 250};
     int[] yPoints = {150, 400, 200, 150, 50, 350, 150, 200, 400, 150};
     g.setColor(Color.BLACK);
     g.fillPolygon(xPoints, yPoints, 10);*/
    
    //System.out.println("("+(int)x+","+(int)y+")");
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    
    g.setColor(Color.BLUE);
    g.fillRect((int)x, (int)y, 50, 50);
    
    g.setColor(Color.ORANGE);
    switch(direction){
      case 1: g.fillRect((int) x+20,(int) y-20, 10, 20); break;
      case 2: g.fillRect((int) x+50,(int) y+20, 20, 10); break;
      case 3: g.fillRect((int) x+20,(int) y+50, 10, 20); break;
      case 4: g.fillRect((int) x-20,(int) y+20 , 20, 10); break;
    }
    
    g.setColor(Color.YELLOW);
    for(int a =0;a<shots.size();a++){
      (shots.get(a)).draw(g);
    }
    g.setColor(Color.GREEN);
    for(int d =0;d<aliens.size();d++){
      (aliens.get(d)).draw(g);
    }
  }
  
  public void keyPressed(KeyEvent e){
    if(shipActive){
      if(e.getKeyCode() == KeyEvent.VK_DOWN){
        downAccel = true;
      }
      else if(e.getKeyCode() == KeyEvent.VK_UP){
        upAccel = true;
      }
      else if(e.getKeyCode() == KeyEvent.VK_LEFT){
        leftAccel = true;
      }
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
        rightAccel = true;
      }
      else if(e.getKeyCode() == KeyEvent.VK_A){
        if(direction == 1){
          direction = 4;
        }
        else direction -= 1;
      }
      else if(e.getKeyCode() == KeyEvent.VK_D){
        if(direction == 4){
          direction = 1;
        }
        else direction += 1;
      }
      else if(e.getKeyCode() == KeyEvent.VK_SPACE){
        if(shots.size()<7){
          shots.add(new Shot((int)x, (int)y, direction));
        }
      }
      else if(e.getKeyCode() == KeyEvent.VK_P){
        aliens.add(new Alien((int)x, (int) y));
      }
    }
  }
  
  public void run(){
    while(true){
      if(!upAccel && !downAccel){
        yVel *= FRICTION;
      }
      if(!leftAccel && !rightAccel){
        xVel *= FRICTION;
      }
      if(Math.abs(yVel)<3){
        if(upAccel){
          yVel -= SPEED;
        }
        if(downAccel){
          yVel += SPEED;
        }
      }
      if(Math.abs(xVel)<3){
        if(leftAccel){
          xVel -= SPEED;
        }
        if(rightAccel){
          xVel += SPEED;
        }
      }
      if(shipActive){
        x += xVel;
        y += yVel;
      }
      
      if(direction == 1 || direction ==3){
        if(direction == 1){
          if(y<=20){
            y=599;
          }
          if(y>=600){
            y=21;
          }
        }
        else{
          if(y<=0){
            y=579;
          }
          if(y>=580){
            y = 1;
          }
        }
        if(x<=0){
          x=949; 
        }
        if(x>=950){
          x = 1;
        }
      }
      else{
        if(direction == 2){
          if(x<=0){
            x=929; 
          }
          if(x>=930){
            x = 1;
          }
        }
        else{
          if(x<=20){
            x=949; 
          }
          if(x>=950){
            x = 21 ;
          }
        }
        if(y<=0){
          y=599;
        }
        if(y>=600){
          y = 1;
        }
      }
      for(int b=0;b<shots.size();b++){
        (shots.get(b)).move();
      }
      for(int c=0;c<shots.size() && c>=0;c++){
        if((shots.get(c)).getX()<=10 || (shots.get(c)).getX()>=990 || (shots.get(c)).getY()<=10 || (shots.get(c)).getY()>=640){
          shots.remove(c);
          c--;
        }
      }
      for(int e=0;e<aliens.size();e++){
        (aliens.get(e)).move();
      }
      for(int f=0;f<shots.size() && f>= 0;f++){
        for(int g=0;g<aliens.size() && g>=0;g++){
          if(shots.size()>0 && aliens.size()>0){
            if(((aliens.get(g)).getDistanceToBullet((shots.get(f)).getX(), (shots.get(f)).getY()))<=20){
              aliens.remove(g);
              g--;
              shots.remove(f);
              f--;
            }
          }
        }
      }
      
      if(shipActive){
        for(int g =0;g<aliens.size();g++){
          if((aliens.get(g)).shipCollision((int)x, (int)y)){
            shipActive = false;
          }
        }
      }
      
      
      repaint();
      try{
        thread.sleep(5);
      }
      catch(InterruptedException e){
      }
    }
  }
  
  public void keyReleased(KeyEvent e){
    if(e.getKeyCode() == KeyEvent.VK_UP){
      upAccel = false;
    }
    else if(e.getKeyCode() == KeyEvent.VK_DOWN){
      downAccel = false;
    }
    else if(e.getKeyCode() == KeyEvent.VK_LEFT){
      leftAccel = false;
    }
    else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
      rightAccel = false;
    }
  }
  
  public void keyTyped(KeyEvent arg0){
    
  }
}