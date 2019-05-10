package com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ImageFrame extends JFrame {
    private ArrayList<ImageItem> images;
    private ArrayList<ImageItem> panels;

    public ArrayList<ImageItem> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageItem> images) {
        this.images = images;
    }

    public ArrayList<ImageItem> getPanels() {
        return panels;
    }

    public void setPanels(ArrayList<ImageItem> panels) {
        this.panels = panels;
    }

    public ImageFrame(){
        super();
        this.setTitle("Test Window");
        this.setLayout(null);
        this.setBounds(100,100,600,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setImages(Util.splitImage());
        this.setPanels(Util.fillPanel(this.getImages()));
        this.addFooterButton();
        this.fill(this.getImages());
    }

    public void fill(ArrayList<ImageItem> images){
        ArrayList<ImageItem> panels = Util.fillPanel(images);
        this.setPanels(panels);
        for(int i = 0; i < this.getPanels().size(); i++){
            this.add(this.getPanels().get(i));
        }
        this.addMouseClick(panels);
    }

    public void addMouseClick(ArrayList<ImageItem> panels){
        for(int i = 0; i < panels.size(); i++){
            panels.get(i).addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ImageItem imageItem = (ImageItem)(e.getSource());
                    int currentIndex = Util.findFlag(panels,imageItem.getFlag());
                    int flagIndex = Util.findFlag(panels,1);
                    if(Math.abs(flagIndex - currentIndex) == 1){
                        if((currentIndex > flagIndex && currentIndex % 3 == 0) || (currentIndex < flagIndex && currentIndex % 3 == 2)){

                        }else{
                            ImageItem temp = panels.get(currentIndex);
                            panels.set(currentIndex,panels.get(flagIndex));
                            panels.set(flagIndex,temp);
                            ImageFrame.this.updateImage(panels);
                        }
                    }else if(Math.abs(flagIndex - currentIndex) == 3){
                        if(flagIndex % 3 == currentIndex % 3){
                            ImageItem temp = panels.get(currentIndex);
                            panels.set(currentIndex,panels.get(flagIndex));
                            panels.set(flagIndex,temp);
                            ImageFrame.this.updateImage(panels);
                        }
                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }
    }

    public void addFooterButton(){
        // 打乱按钮
        JButton outOfOrderButton = new JButton("打乱");
        outOfOrderButton.setBounds(100,400,60,25);
        outOfOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ImageItem> outOfOrderImages = Util.outOfOrder(ImageFrame.this.getImages());
                ImageFrame.this.setImages(outOfOrderImages);
                ImageFrame.this.updateImage(outOfOrderImages);
            }
        });
        this.add(outOfOrderButton);
        // 自动排序
        JButton autoOrderButton = new JButton("A*搜索");
        autoOrderButton.setBounds(200,400,100,25);
        autoOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AStarOrder aStarOrder = new AStarOrder(Util.splitImage());
                ArrayList<OpenItem> log = aStarOrder.startOrder(0,ImageFrame.this.getImages(),new ArrayList<OpenItem>(),null);
                ArrayList<OpenItem> routes = new ArrayList<>();
                int i = log.size() - 1;
                while(log.get(i).getCurrent() != null){
                    ArrayList<ImageItem> current = log.get(i).getCurrent();
                    routes.add(new OpenItem(0,0,current,null));
                    if(log.get(i).getParent() != null){
                        for(int j = 0; j < log.size(); j ++){
                            if(Util.notLocation(log.get(j).getCurrent(),log.get(i).getParent()) == 0){
                                i = j;
                                break;
                            }
                        }
                    }else{
                        break;
                    }
                }
                ImageFrame.this.showRoute(routes);
//                System.out.println("-------------------------");
//                for(int j = 0 ; j < log.size(); j++){
//                    System.out.print("current: ");
//                    for(int k = 0; k < log.get(j).getCurrent().size(); k++){
//                        System.out.print(log.get(j).getCurrent().get(k).getFlag());
//                    }
//                    System.out.print("  parent: ");
//                    if(log.get(j).getParent() != null){
//                        for(int k = 0; k < log.get(j).getParent().size(); k++){
//                            System.out.print(log.get(j).getParent().get(k).getFlag());
//                        }
//                    }
//
//                    System.out.println();
//                }
//                System.out.println("-------------------------");
//                System.out.println("-------------------------");
//                for(int j = routes.size() - 1 ; j >= 0; j--){
//                    System.out.print("current: ");
//                    for(int k = 0; k < routes.get(j).getCurrent().size(); k++){
//                        System.out.print(routes.get(j).getCurrent().get(k).getFlag());
//                    }
//                    System.out.println();
//                }
//                System.out.println("-------------------------");
                System.out.println("routes: SIZE:  " + routes.size());
            }
        });
        this.add(autoOrderButton);
    }

    public void updateImage(ArrayList<ImageItem> newImages){
        // 清空旧面板
        for(int i = 0; i < this.getPanels().size(); i++){
            this.remove(this.getPanels().get(i));
        }
        this.fill(newImages);
        this.repaint();
    }

    public void showRoute(ArrayList<OpenItem> routes){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int j = routes.size() -  1; j >= 0; j --){
                    ImageFrame.this.updateImage(routes.get(j).getCurrent());
                    try{
                        Thread.sleep(1000);
                    }catch(Exception es){

                    }
                }
            }
        });
        thread.start();
    }
}
