package com.zkkc.track.moudle.pic.entity;

import java.io.File;

/**
 * Created by ShiJunRan on 2019/2/19
 */
public class PicBean {
    private String name;
    private boolean ed;
    private boolean isShow;
    private File mFile;

    public PicBean(File mFile, String name, boolean ed,boolean isShow) {
        this.name = name;
        this.ed = ed;
        this.mFile = mFile;
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isEd() {
        return ed;
    }

    public void setEd(boolean ed) {
        this.ed = ed;
    }

    public File getmFile() {
        return mFile;
    }

    public void setmFile(File mFile) {
        this.mFile = mFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
