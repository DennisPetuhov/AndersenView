package com.example.andersenview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.icu.util.Calendar
import android.util.AttributeSet
import android.view.View
import java.lang.Float.min


class CustomClockView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val minutePaint: Paint = Paint()
    private val secondPaint: Paint = Paint()
    private var startPointDelimiter = PointF(0f, 0f)
    private var endPointDelimiter = PointF(0f, 0f)
    private var yEndPointDelimiter: Float = 0f
    var circleColor: Int
    var secondHandColor: Int
    var minutesHandColor: Int
    private var circleRadius: Float = 250f
    private val secondStartPoint = PointF(0f, 0f)
    private val secondEndPoint = PointF(0f, 0f)
    private val minuteStartPoint = PointF(0f, 0f)
    private val minuteEndPoint = PointF(0f, 0f)
    private val hourStartPoint = PointF(0f, 0f)
    private val hourEndPoint = PointF(0f, 0f)
    private var widthContent: Int = 500
    private var heightContent: Int = 500
    private var _clockPointCenter = PointF()

    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        strokeWidth = 15f
        style = Paint.Style.STROKE
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleView)
        circleColor = typedArray.getColor(R.styleable.CustomCircleView_circleColor, Color.BLACK)
        circleRadius = typedArray.getDimension(R.styleable.CustomCircleView_circleRadius, 300f)
        secondHandColor =
            typedArray.getColor(R.styleable.CustomCircleView_secondHandColor, Color.BLUE)
        minutesHandColor =
            typedArray.getColor(R.styleable.CustomCircleView_minuteHandColor, Color.RED)
        typedArray.recycle()

        minutePaint.color = minutesHandColor
        minutePaint.style = Paint.Style.STROKE
        minutePaint.strokeWidth = 15f

        secondPaint.color = secondHandColor
        secondPaint.style = Paint.Style.STROKE
        secondPaint.strokeWidth = 10f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = circleRadius.toInt() * 2 + paddingLeft + paddingRight + 15
        val height = circleRadius.toInt() * 2 + paddingTop + paddingBottom + 15
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val myCalendar = Calendar.getInstance()
        val hours = myCalendar.get(Calendar.HOUR)
        val minutes = myCalendar.get(Calendar.MINUTE)
        val seconds = myCalendar.get(Calendar.SECOND)
        val hourAngle = (hours + minutes * 1f / 60f + seconds * 1f / 3600f) * 30f
        val minuteAngle = (minutes + seconds * 1f / 60f) * 6f
        val secondsAngle = seconds * 6F
        val centerX = width / 2f
        val centerY = height / 2f

        canvas?.translate(centerX, centerY)
        canvas?.save()
        canvas?.rotate(hourAngle)
        canvas?.drawLine(
            hourStartPoint.x,
            hourStartPoint.y,
            hourEndPoint.x,
            hourEndPoint.y,
            circlePaint
        )
        canvas?.restore()
        canvas?.save()
        canvas?.rotate(minuteAngle)
        canvas?.drawLine(
            minuteStartPoint.x,
            minuteStartPoint.y,
            minuteEndPoint.x,
            minuteEndPoint.y,
            minutePaint
        )
        canvas?.restore()
        canvas?.save()
        canvas?.rotate(secondsAngle)
        canvas?.drawLine(
            secondStartPoint.x,
            secondStartPoint.y,
            secondEndPoint.x,
            secondEndPoint.y,
            secondPaint
        )
        canvas?.restore()
        canvas?.drawCircle(0f, 0f, circleRadius, circlePaint)
        drawDelimiters(canvas)
        postInvalidateDelayed(500)
        postInvalidate()
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        circleRadius = min(
            500 / 2f,
            500 / 2f
        )
        val _circleHandsRadius = circleRadius
        startPointDelimiter.y = -10 * _circleHandsRadius / 12
        yEndPointDelimiter = -1 * _circleHandsRadius

        hourStartPoint.y = _circleHandsRadius / 5
        hourEndPoint.y = -5 * _circleHandsRadius / 7

        minuteStartPoint.y = _circleHandsRadius / 5
        minuteEndPoint.y = -5 * _circleHandsRadius / 9

        secondStartPoint.y = _circleHandsRadius / 5
        secondEndPoint.y = -5 * _circleHandsRadius / 12
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthContent = w - paddingStart - paddingEnd
        heightContent = h - paddingTop - paddingBottom
        _clockPointCenter = PointF(
            (widthContent / 2 + paddingStart).toFloat(),
            (heightContent / 2 + paddingTop).toFloat()
        )
    }

    private fun drawDelimiters(canvas: Canvas?) {
        for (i in 0..11) {
            endPointDelimiter.y = yEndPointDelimiter
            canvas?.drawLine(
                startPointDelimiter.x,
                startPointDelimiter.y,
                endPointDelimiter.x,
                endPointDelimiter.y,
                circlePaint
            )
            canvas?.rotate(30f)
        }
    }
}