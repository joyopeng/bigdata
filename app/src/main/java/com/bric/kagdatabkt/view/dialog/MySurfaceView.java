package com.bric.kagdatabkt.view.dialog;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.bric.kagdatabkt.R;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.bric.kagdatabkt.utils.OnDrawThread;
import com.bric.kagdatabkt.utils.PicRunThread;


public class MySurfaceView extends SurfaceView implements Callback, Runnable {

    //此处实现SurfaceHolder.Callback接口，为surfaceView添加生命周期回调函数
    int dy = Display.DEFAULT_DISPLAY;
    Context ma;                          //得到MyActivity的引用
    Paint paint;                            //画笔的引用
    OnDrawThread odt;                       //OnDrawThread类引用
    PicRunThread prt;                       //图片运动的Thread类引用
    private float picX = 0;                       //图片x坐标
    private float picY = 0;                       //图片y坐标
    public boolean picAlphaFlag = false;                 //图片变暗效果的标记，false为不显示，true为显示。
    int picAlphaNum = 0;                          //图片变暗效果中画笔的alpha值
    private int move_x = 2, x = 20;
    private Thread th;
    private SurfaceHolder sfh;
    private Canvas canvas;
    private Paint p;

    public MySurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ma = context;
        //将ma的引用指向调用了该Surfaceview类构造器方法的对象，本例为MyActivity
        this.getHolder().addCallback(this);     //注册回调接口
        paint = new Paint();                      //实例化画笔
        odt = new OnDrawThread(this);             //实例化OnDrawThread类
        prt = new PicRunThread(this);             //实例化PicRunThread类
        prt.start();


//        p = new Paint();
//        p.setAntiAlias(true);
//        sfh = this.getHolder();
//        sfh.addCallback(this);
//        th = new Thread(this);
    }

    public MySurfaceView(Context context) {
        super(context);
        this.ma = context;
        //将ma的引用指向调用了该Surfaceview类构造器方法的对象，本例为MyActivity
        this.getHolder().addCallback(this);     //注册回调接口
        paint = new Paint();                      //实例化画笔
        odt = new OnDrawThread(this);             //实例化OnDrawThread类
        prt = new PicRunThread(this);             //实例化PicRunThread类
        prt.start();
    }

    public void setPicX(float picX) {           //图片x坐标的设置器
        this.picX = picX;
    }

    public void setPicY(float picY) {           //图片y坐标的设置器
        this.picY = picY;
    }

    public void setPicAlphaNum(int picAlphaNum) {//图片变暗效果alpha参数设置器
        this.picAlphaNum = picAlphaNum;
    }

    private void logic() {
        x += move_x;
        if (x > 1200 || x < 8) {
            move_x = -move_x;
        }
    }

    @Override
    public void draw(Canvas canvas) {  //onDraw方法，此方法用于绘制图像，图形等
        super.draw(canvas);
        paint.setColor(Color.WHITE);        //设置画笔为白色
        canvas.drawRect(0, 0, CommonConstField.SCREENWIDTH, CommonConstField.SCREENHEIGHT, paint);
        //此处画了一个白色的全屏幕的矩形，目的是设置背景为白色，同时每次重绘时清除背景
        //进行平面贴图
        Bitmap bitmapDuke = BitmapFactory.decodeResource(ma.getResources(), R.drawable.bulao);
        canvas.drawBitmap(bitmapDuke, picX, picY, paint);
        //图片渐暗效果
        if (picAlphaFlag) {
            Bitmap bitmapBG = BitmapFactory.decodeResource(ma.getResources(), R.drawable.add_picture);
            paint.setAlpha(picAlphaNum);
            canvas.drawBitmap(bitmapBG, 0, 0, paint);
        }
    }

    public void draw() {
        canvas = sfh.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            p.setTextSize(34);
            canvas.drawArc(new RectF(0, 0, 128, 128), 0, 360, true, p);
            canvas.drawText("我是   - Surfaceview", x + move_x, 280, p);
            sfh.unlockCanvasAndPost(canvas);
        }
    }

    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            draw();
            logic();
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {           //此方法为当surfaceView改变时调用，如屏幕大小改变。
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {//此方法为在surfaceView创建时调用
        odt.start();                //启动onDraw的绘制线程
//        th.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {//此方法为在surfaceView销毁前调用
    }
}
