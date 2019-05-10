package com;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Util {
    // 3 * 3切割图片
    public static ArrayList<ImageItem> splitImage(){
        ArrayList<ImageItem> subLists = new ArrayList<>();
        File file = new File("src/resources/test.jpg");
        Font font = new Font("微软雅黑", Font.BOLD, 32);
        try{
            BufferedImage bufferedImage = ImageIO.read(file);
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            int num = 1;
            for(int i = 0; i < 3; i++){
                for(int j = 0;j < 3; j++){
                    ImageItem ImageItem = new ImageItem();
                    BufferedImage subImage = bufferedImage.getSubimage(j * width / 3,i * height / 3,width / 3,height / 3);
                    Graphics graphics = subImage.createGraphics();
                    graphics.setFont(font);
                    graphics.setColor(Color.RED);
                    graphics.drawString(String.valueOf(num),subImage.getWidth() / 2,subImage.getHeight() / 2);
                    ImageItem.setFlag(num);
                    ImageItem.setImage(subImage);
                    subLists.add(ImageItem);
                    File subFile = new File("src/resources/subImage/" + num + ".jpg");
                    ImageIO.write(subImage,"jpg",subFile);
                    num ++;
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return subLists;
    }

    // 随机交换50次
    public static ArrayList<ImageItem> outOfOrder(ArrayList<ImageItem> images){
        String operations[] = {"up","down","left","right"};
        for(int i = 0; i < 50; i++){
            int random = (int)(Math.random() * 4);
            int flagIndex = Util.findFlag(images,1);
            switch(operations[random]){
                case "up":
                    if(flagIndex - 3 >= 0 && flagIndex - 3 <= 8){
                        images = Util.swap(flagIndex,images,operations[random]);
                    }
                    break;
                case "down":
                    if(flagIndex + 3 >= 0 && flagIndex + 3 <= 8){
                        images = Util.swap(flagIndex,images,operations[random]);
                    }
                    break;
                case "left":
                    if(flagIndex % 3 != 0){
                        images = Util.swap(flagIndex,images,operations[random]);
                    }
                    break;
                case "right":
                    if(flagIndex % 3 != 2){
                        images = Util.swap(flagIndex,images,operations[random]);
                    }
                    break;
            }
        }

        return images;
    }

    // 填充面板
    public static ArrayList<ImageItem> fillPanel(ArrayList<ImageItem> images){
        ArrayList<ImageItem> panels = new ArrayList<>();
        int row,col,x,y;
        for(int i = 0; i < images.size(); i++){
            row = i / 3;
            col = i - row * 3;
            x = col * images.get(i).getImage().getWidth();
            y = row * images.get(i).getImage().getHeight();
            ImageItem jPanel = new ImageItem(images.get(i).getImage(),images.get(i).getFlag());
            jPanel.setBounds(x,y,images.get(i).getImage().getWidth(),images.get(i).getImage().getHeight());
            panels.add(jPanel);
        }
        return panels;
    }

    // 计算不在位数量
    public static int notLocation(ArrayList<ImageItem> current,ArrayList<ImageItem> target){
        int num = 0;
        for(int i = 0; i < current.size(); i++){
            if(current.get(i).getFlag() != target.get(i).getFlag()){
                num ++;
            }
        }
        return num;
    }

    // 找到 targetFlag 的位置
    public static int findFlag(ArrayList<ImageItem> current,int targetFlag){
        int flag = 0;
        for(int i = 0; i < current.size(); i ++){
            if(current.get(i).getFlag() == targetFlag){
                flag = i;
                break;
            }
        }
        return flag;
    }

    // 判断 open 表 close表中是否存在
    public static boolean isExist(ArrayList<OpenItem> open,ArrayList<OpenItem> close,ArrayList<ImageItem> current){
        boolean flag = false;
        // 比较close表
        for(int i = 0; i < close.size(); i++){
            // 存在
            if(Util.notLocation(current,close.get(i).getCurrent()) == 0){
                flag = true;
                break;
            }
        }
        // 比较open表
        for(int i = 0; i < open.size(); i++){
            // 存在
            if(Util.notLocation(current,open.get(i).getCurrent()) == 0){
                flag = true;
                break;
            }
        }
        return flag;
    }

    // 交换位置
    public static ArrayList<ImageItem> swap(int flagIndex,ArrayList<ImageItem> current,String type){
        ImageItem temp = current.get(flagIndex);
        ArrayList<ImageItem> currentCopy = (ArrayList<ImageItem>) current.clone();
        switch(type){
            case "up":
                currentCopy.set(flagIndex, current.get(flagIndex - 3));
                currentCopy.set(flagIndex - 3, temp);
                break;
            case "down":
                currentCopy.set(flagIndex, current.get(flagIndex + 3));
                currentCopy.set(flagIndex + 3, temp);
                break;
            case "left":
                currentCopy.set(flagIndex, current.get(flagIndex - 1));
                currentCopy.set(flagIndex - 1, temp);
                break;
            case "right":
                currentCopy.set(flagIndex, current.get(flagIndex + 1));
                currentCopy.set(flagIndex + 1, temp);
                break;
        }
        return currentCopy;
    }
}
