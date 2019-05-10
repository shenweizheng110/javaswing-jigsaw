package com;

import java.util.ArrayList;

public class OpenItem {
    private int depth;
    private int fn;
    private ArrayList<ImageItem> current;
    private ArrayList<ImageItem> parent;

    public OpenItem(int depth,int fn,ArrayList<ImageItem> current, ArrayList<ImageItem> parent){
        this.current = current;
        this.depth = depth;
        this.fn = fn;
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    public ArrayList<ImageItem> getCurrent() {
        return current;
    }

    public void setCurrent(ArrayList<ImageItem> current) {
        this.current = current;
    }

    public ArrayList<ImageItem> getParent() {
        return parent;
    }

    public void setParent(ArrayList<ImageItem> parent) {
        this.parent = parent;
    }
}
