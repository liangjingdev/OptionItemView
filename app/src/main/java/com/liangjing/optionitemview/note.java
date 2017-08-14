package com.liangjing.optionitemview;

/**
 * Created by liangjing on 2017/8/13.
 * function:笔记
 */

public class note {

    /**
     * 水平居中很简单,paint.setTextAlign(Align.CENTER);然后drawText的x坐标设置为rect.centerX()即可(rect是可绘制的范围)，
     * 竖直居中稍显麻烦，可以使用FontMetrics对象计算文字高度，然后计算baseline，使文字垂直居中
     */
    
      /**
       *  return true 无法触发点击事件；return false时无法发生滑动 ; 
       *  return super.onTouchEvent(event);才能触发这两个事件
       *    为什么?
       *   return super.onTouchEvent(event);
       *
       *   OnClickListener.onClick()是在原生的View.onTouchEvent()方法里面回调的, 你自己重写了这个方法, 
       *    而且不调用super.onTouchEvent(event)的话当然就不会回调onClick()方法了.
       */
         
       
       

}
