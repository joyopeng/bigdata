package com.bric.kagdatabkt.utils;


import com.bric.kagdatabkt.view.dialog.MySurfaceView;

public class PicRunThread extends Thread {
    MySurfaceView msv;                                  //MySurfaceView的引用
    private float picX = 0;           //图片x坐标
    private float picY = CommonConstField.SCREENHEIGHT - CommonConstField.PICHEIGHT;            //图片y坐标
    boolean yRunFlag = false;     //y方向上的运动标记，false时y=y+speed，true时y=y-speed
    int picAlphaNum = 0;                  //图片变暗效果中画笔的alpha值

    public PicRunThread(MySurfaceView msv) {
        super();
        this.msv = msv;         //将该线程类的引用指向调用其的MySurfaceView的对象
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            //控制duke图片的运动
            while (this.picX < CommonConstField.SCREENWIDTH) {           //当图片的左边完全超过屏幕的右边时，循环结束
                msv.setPicX(picX);
                msv.setPicY(picY);
                picX = picX + CommonConstField.PICXSPEED;
                if (yRunFlag) {//应该向上运动，自减
                    picY = picY - CommonConstField.PICYSPEED;
                } else {//应该向下运动，自加
                    picY = picY + CommonConstField.PICYSPEED;
                }
                if (picY <= 0) {                                 //到达屏幕上沿
                    yRunFlag = false;
                } else if (picY > CommonConstField.SCREENHEIGHT - CommonConstField.PICHEIGHT) {     //到达屏幕下沿
                    yRunFlag = true;
                }
                try {
                    Thread.sleep(CommonConstField.PICRUNSPEED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //图片变暗效果演示
            msv.picAlphaFlag = true;                          //开启图片变暗效果
            for (picAlphaNum = 0; picAlphaNum <= 255; picAlphaNum++) {
                if (picAlphaNum == 255) {
                    msv.picAlphaFlag = false;                 //当图片变暗效果结束，标记重置
                    picX = 0;         //图片x坐标
                    picY = CommonConstField.SCREENHEIGHT - CommonConstField.PICHEIGHT;          //图片y坐标
                    System.out.println(msv.picAlphaFlag + "picX:" + picX + "picY:" + picY);
                }
                msv.setPicAlphaNum(picAlphaNum);
                try {
                    Thread.sleep(CommonConstField.PICALPHASPEED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
