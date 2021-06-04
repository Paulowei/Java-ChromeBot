import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.geom.*;
import java.awt.Rectangle;
import java.awt.*;
import javax.swing.*;
public class ChromeBot {
    int x;
    int y;
    int width;
    int height;
    int thresh;
    int thresh_;
    Robot robot;
    Rectangle rect;
    boolean isDino;
    public ChromeBot(int x1,int y1,int width1,int height1){
        try{
            robot=new Robot();

        } catch (AWTException e){
            e.printStackTrace();
        }
        x=x1;
        y=y1;
        width=width1;
        height=height1;
        rect=new Rectangle(x,y,width,height);
    }
    public boolean jump() throws AWTException{
        int sum=0;
        int width2;
        int height2;
        Color c;
        robot.keyPress(KeyEvent.VK_DOWN);
        BufferedImage image=robot.createScreenCapture(rect);
        width2 = image.getWidth();
        height2 = image.getHeight();
        for(int i=0;i<width2;i+=1){
            for(int j=0;j<height2;j+=1){
                c=new Color(image.getRGB(i,j));
                sum+=c.getRed();
                sum+=c.getBlue();
                sum+=c.getGreen();
                }
            }
           
        if(sum<=thresh&&isDino==true ){
        robot.delay(250);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.delay(200);
        robot.keyRelease(KeyEvent.VK_SPACE);
        robot.keyPress(KeyEvent.VK_DOWN);
        }
        if( sum<=-1) {throw new AWTException("Error"); 
        }  
        if(isDino==false){
            if(sum>=thresh){
                return true;
            }    
            else{     
            return false;
            }
        }
        else{
            return false;
        }
    }        
    public int  scale() throws AWTException{
        int width;    
        int height;  
        int sum=0;  
        Color c;
        robot.keyPress(KeyEvent.VK_DOWN);
        BufferedImage image=robot.createScreenCapture(rect);
        width=image.getWidth();
        height=image.getHeight();
        for(int i=0;i<width;i+=1){
            for(int j=0;j<height;j+=1){
                c=new Color(image.getRGB(i,j));
                sum+=c.getRed();
                sum+=c.getBlue();
                sum+=c.getGreen();
            }
        }
        if(sum>=thresh_){
            robot.delay(50);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.delay(200);
            robot.keyRelease(KeyEvent.VK_SPACE);
            robot.keyPress(KeyEvent.VK_DOWN);
        }
        if( sum<=-1) {throw new AWTException("Error"); 
        }
        return sum;
    }
    public int restart() throws AWTException{
        int width2;
        int height2;
        int sum2=0;
        Color c;
        BufferedImage image=robot.createScreenCapture(rect);
        width2 =  image.getWidth();
        height2 = image.getHeight();
        for(int i=0;i<width2;i+=1){
            for(int j=0;j<height2;j+=1){
                c=new Color(image.getRGB(i,j));
                sum2+=c.getRed();
                sum2+=c.getBlue();
                sum2+=c.getGreen();
                }
        }
        if(sum2<=thresh){
            robot.mouseMove(x,y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(200);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseMove(x,y+200);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
        if(sum2<=-1){throw new AWTException("Error"); 
                }
        return sum2;
    } 
    public void init(){
        robot.mouseMove(x,y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_SPACE);
        robot.mouseMove(x,y+200);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void main(String[] args){
        ChromeBot Dinosaur=new ChromeBot(300,308,50,50);
        ChromeBot Button=new ChromeBot(321,257,50,50);
        ChromeBot Modulator=new ChromeBot(227,175,50,50);
        Dinosaur.thresh=1860000;
        Dinosaur.thresh_=100000;
        Modulator.thresh=50000;
        Button.thresh=1400000; 
        Dinosaur.isDino=true;
        Modulator.isDino=false;
        Button.init();    
        while(true){            
            try{    
                Button.restart();
                if(Modulator.jump()==true){
                     Dinosaur.jump();
                }  
                if  (Modulator.jump()==false){
                    Dinosaur.scale();
                }
                
            }   
            catch(AWTException e){
            e.printStackTrace();
             System.out.println("Missing colour fragment");
            }     
        }
    } 
}                                                                