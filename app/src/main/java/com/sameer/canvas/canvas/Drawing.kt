package com.sameer.canvas.canvas

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class Drawing(context: Context, attrs:AttributeSet) :View(context,attrs) {

    private var path:CustomPath?=null
    private var mCanvasBitmap:Bitmap?=null
    private var mDrawPaint:Paint?=null
    private var mCanvasPaint:Paint?=null
    private var mBrushSize:Float = 0.toFloat()
    private var color: Int = Color.BLACK
    private var canvas:Canvas?= null
    private var patharray=ArrayList<CustomPath>()

    init {
        setupDrawaing()
    }

    private fun setupDrawaing() {
        mDrawPaint=Paint()
        path=CustomPath(color,mBrushSize)
        mDrawPaint!!.color=color
        mDrawPaint!!.style=Paint.Style.STROKE
        mDrawPaint!!.strokeJoin=Paint.Join.ROUND
        mDrawPaint!!.strokeCap=Paint.Cap.ROUND
        mCanvasPaint=Paint(Paint.DITHER_FLAG)
        mBrushSize=20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas=Canvas(mCanvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mDrawPaint)
        for (p in patharray){
            mDrawPaint!!.strokeWidth=p.brushthickness
            mDrawPaint!!.color=p.color
            canvas.drawPath(p,mDrawPaint!!)
        }
            if (!path!!.isEmpty){
                mDrawPaint!!.strokeWidth=path!!.brushthickness
                mDrawPaint!!.color=path!!.color
                canvas.drawPath(path!!,mDrawPaint!!)
            }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchx=event?.x
        val touchy=event?.y
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                path!!.color=color
                path!!.brushthickness=mBrushSize
               // path!!.reset()
                if (touchx != null) {
                    if (touchy != null) {
                        path!!.moveTo(touchx,touchy)
                    }
                }

            }

            MotionEvent.ACTION_MOVE->{
                if (touchx != null) {
                    if (touchy != null) {
                        path!!.lineTo(touchx,touchy)
                    }
                }
            }



            MotionEvent.ACTION_UP->{
                patharray.add(path!!)
                path=CustomPath(color,mBrushSize)

            }

            else ->{
                return false
            }


        }

        invalidate()
        return true
    }

    internal inner  class CustomPath(var color:Int, var brushthickness:Float):android.graphics.Path() {

    }


}