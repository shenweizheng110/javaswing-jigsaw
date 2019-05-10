package com;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageItem extends JPanel {
    private BufferedImage image;
    private int x;
    private int y;
    private int flag;

    public ImageItem(){
        this.image = null;
        this.x = -1;
        this.y = -1;
        this.flag = -1;
    }

    public ImageItem(BufferedImage image,int flag){
        this.image = image;
        this.flag = flag;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0,0,this.image.getWidth(),this.image.getHeight());
        g.drawImage(this.image,0,0,null);
    }
}
