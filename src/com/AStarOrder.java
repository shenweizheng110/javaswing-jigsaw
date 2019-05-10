package com;

import java.util.ArrayList;

public class AStarOrder {
    private ArrayList<ImageItem> target;
    private ArrayList<OpenItem> open;
    private ArrayList<OpenItem> close;

    public AStarOrder(ArrayList<ImageItem> target){
        this.target = new ArrayList<>();
        this.target = target;
        this.open = new ArrayList<>();
        this.close = new ArrayList<>();
    }

    public ArrayList<ImageItem> getTarget() {
        return target;
    }

    public void setTarget(ArrayList<ImageItem> target) {
        this.target = target;
    }

    public ArrayList<OpenItem> getOpen() {
        return open;
    }

    public void setOpen(ArrayList<OpenItem> open) {
        this.open = open;
    }

    public ArrayList<OpenItem> getClose() {
        return close;
    }

    public void setClose(ArrayList<OpenItem> close) {
        this.close = close;
    }

    public ArrayList<OpenItem> startOrder(int depth, ArrayList<ImageItem> current,ArrayList<OpenItem> log, ArrayList<ImageItem> parent){
        System.out.print("depth: " + depth + " current: ");
        for(int i = 0; i < current.size(); i++){
            System.out.print(current.get(i).getFlag());
        }
        System.out.println();
        OpenItem logOpenItem = new OpenItem(depth,Util.notLocation(current,this.getTarget()),current,parent);
        log.add(logOpenItem);
        // 不再为数量为0
        if(Util.notLocation(current,this.getTarget()) == 0){
            return log;
        }
        // open表为空 close表不为空
        if(this.getOpen().size() == 0 && this.getClose().size() != 0){
            return log;
        }
        // 判断是否已走过
        if(!Util.isExist(this.getOpen(),this.getClose(),current)){
            // 找到1的位置
            int flagIndex = Util.findFlag(current,1);
            // 扩展节点
            // 上
            if(flagIndex - 3 >= 0 && flagIndex - 3 <= 8){
                ArrayList<ImageItem> newCurrent = Util.swap(flagIndex,current,"up");
                if(!Util.isExist(this.getOpen(),this.getClose(),newCurrent)){
                    this.resetOpen(depth + 1,depth + 1 + Util.notLocation(newCurrent,this.getTarget()),newCurrent,current);
                }
            }
            // 下
            if(flagIndex + 3 >= 0 && flagIndex + 3 <= 8){
                ArrayList<ImageItem> newCurrent = Util.swap(flagIndex,current,"down");
                if(!Util.isExist(this.getOpen(),this.getClose(),newCurrent)){
                    this.resetOpen(depth + 1,depth + 1 + Util.notLocation(newCurrent,this.getTarget()),newCurrent,current);
                }
            }
            // 左
            if(flagIndex % 3 != 0){
                ArrayList<ImageItem> newCurrent = Util.swap(flagIndex,current,"left");
                if(!Util.isExist(this.getOpen(),this.getClose(),newCurrent)){
                    this.resetOpen(depth + 1,depth + 1 + Util.notLocation(newCurrent,this.getTarget()),newCurrent,current);
                }
            }
            // 右
            if(flagIndex % 3 != 2){
                ArrayList<ImageItem> newCurrent = Util.swap(flagIndex,current,"right");
                if(!Util.isExist(this.getOpen(),this.getClose(),newCurrent)){
                    this.resetOpen(depth + 1,depth + 1 + Util.notLocation(newCurrent,this.getTarget()),newCurrent,current);
                }
            }
            sortOpen();
            addClose(current);
            OpenItem openItem = this.getOpen().get(0);
            ArrayList<OpenItem> subOpen = new ArrayList<>();
            for(int i = 1; i < this.getOpen().size(); i++){
                subOpen.add(this.getOpen().get(i));
            }
            this.setOpen(subOpen);
            startOrder(openItem.getDepth(),openItem.getCurrent(),log,openItem.getParent());
            return log;
        }
        return log;
    }

    public void resetOpen(int depth,int fn,ArrayList<ImageItem> newCurrent,ArrayList<ImageItem> parent){
        OpenItem openItem = new OpenItem(depth,fn,newCurrent,parent);
        ArrayList<OpenItem> newOpen = this.getOpen();
        newOpen.add(openItem);
        this.setOpen(newOpen);
    }

    public void addClose(ArrayList<ImageItem> current){
         OpenItem openItem = new OpenItem(0,0,current,null);
         ArrayList<OpenItem> close = this.getClose();
         close.add(openItem);
         this.setClose(close);
    }

    public void sortOpen(){
        ArrayList<OpenItem> open = this.getOpen();
        ArrayList<OpenItem> sortedOpen = this.quickSort(0,open.size() - 1,open);
        this.setOpen(sortedOpen);
    }

    public int tailQuickSort(int left,int right,ArrayList<OpenItem> open){
        int i = left,j = right,flag = open.get(left).getFn();
        OpenItem openItem = open.get(left);
        while(i < j){
            while(i < j && open.get(j).getFn() >= flag)
                j --;
            while(i < j && open.get(i).getFn() <= flag)
                i ++;
            OpenItem temp = open.get(i);
            open.set(i,open.get(j));
            open.set(j,temp);
        }
        open.set(left,open.get(i));
        open.set(i,openItem);
        return i;
    }

    public ArrayList<OpenItem> quickSort(int left, int right, ArrayList<OpenItem> open){
        int pivot = left;
        while(left < right){
            pivot = this.tailQuickSort(left,right,open);
            quickSort(left,pivot - 1,open);
            left = pivot + 1;
        }
        return open;
    }
}
