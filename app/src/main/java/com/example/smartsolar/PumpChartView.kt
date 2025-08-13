package com.example.smartsolar

import android.content.Context
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent
import kotlin.math.roundToInt

class PumpChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    // Data for 7 days of the week with pump production in kWh
    private val weekData = arrayOf(
        1850f, // Monday
        2100f, // Tuesday
        1650f, // Wednesday
        1950f, // Thursday
        2300f, // Friday
        1750f, // Saturday
        2000f  // Sunday
    )

    private val dayColors = arrayOf(
        Color.parseColor("#FF6B6B"), // Red
        Color.parseColor("#4ECDC4"), // Teal
        Color.parseColor("#45B7D1"), // Blue
        Color.parseColor("#96CEB4"), // Green
        Color.parseColor("#FFEAA7"), // Yellow
        Color.parseColor("#DDA0DD"), // Purple
        Color.parseColor("#FF8C42")  // Orange
    )

    private val dayNames = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    private var animationProgress: Float = 0f
    private var animator: ValueAnimator? = null
    private var selectedIndex: Int = -1

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        
        // Set background color to match the chart area
        canvas.drawColor(Color.parseColor("#0A0A0A"))

        // Calculate center and radius for the pie chart
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = minOf(width, height) / 2.5f
        val innerRadius = radius * 0.5f
        val outerRadius = radius * 1.1f

        // Calculate total for percentage calculations
        val total = weekData.sum()

        // Draw outer glow effect
        paint.color = Color.parseColor("#1A6AABD2")
        paint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, outerRadius, paint)

        // Draw the pie chart segments with enhanced effects
        var startAngle = -90f // Start from top
        for (i in weekData.indices) {
            val sweepAngle = (weekData[i] / total) * 360f
            val animatedSweep = sweepAngle * animationProgress
            
            // Draw segment with gradient effect
            paint.color = dayColors[i]
            paint.style = Paint.Style.FILL
            
            // Main segment
            val rect = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
            canvas.drawArc(rect, startAngle, animatedSweep, true, paint)
            
            // Add highlight effect (lighter version of the color)
            val highlightColor = getLighterColor(dayColors[i])
            paint.color = highlightColor
            val highlightRect = RectF(centerX - radius * 0.8f, centerY - radius * 0.8f, 
                                    centerX + radius * 0.8f, centerY + radius * 0.8f)
            canvas.drawArc(highlightRect, startAngle, animatedSweep, true, paint)
            
            // Draw segment border
            paint.color = Color.WHITE
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2f
            canvas.drawArc(rect, startAngle, animatedSweep, true, paint)
            
            // Draw value on the segment
            if (animationProgress > 0.95f) {
                drawValueOnSegment(canvas, centerX, centerY, radius, startAngle, sweepAngle, weekData[i])
            }
            
            startAngle += animatedSweep
        }

        // Draw inner circle to create donut effect
        paint.color = Color.parseColor("#0A0A0A")
        paint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, innerRadius, paint)

        // Draw inner circle border
        paint.color = Color.parseColor("#333333")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3f
        canvas.drawCircle(centerX, centerY, innerRadius, paint)

        // Center text (selected segment or total)
        paint.color = Color.BLACK
        paint.textSize = 22f
        paint.textAlign = Paint.Align.CENTER
        paint.style = Paint.Style.FILL
        paint.clearShadowLayer()

        if (selectedIndex in weekData.indices) {
            val label = dayNames[selectedIndex]
            val valText = "${weekData[selectedIndex].roundToInt()} kWh"
            canvas.drawText(label, centerX, centerY - 8f, paint)
            paint.textSize = 32f
            paint.color = Color.BLACK
            canvas.drawText(valText, centerX, centerY + 20f, paint)
        } else {
            canvas.drawText("Weekly", centerX, centerY - 12f, paint)
            paint.textSize = 32f
            paint.color = Color.BLACK
            val totalProduction = weekData.sum().roundToInt()
            canvas.drawText("${totalProduction} kWh", centerX, centerY + 18f, paint)
        }

        paint.clearShadowLayer()

        // Legend (bottom)
        if (animationProgress > 0.95f) {
            drawLegend(canvas, centerX, height - 24f)
        }
    }

    private fun drawValueOnSegment(canvas: Canvas, centerX: Float, centerY: Float, radius: Float, startAngle: Float, sweepAngle: Float, value: Float) {
        // Calculate the middle angle of the segment
        val middleAngle = startAngle + (sweepAngle / 2f)
        val angleInRadians = Math.toRadians(middleAngle.toDouble())
        
        // Calculate position on the segment (at 70% of radius)
        val textRadius = radius * 0.7f
        val x = centerX + (textRadius * kotlin.math.cos(angleInRadians)).toFloat()
        val y = centerY + (textRadius * kotlin.math.sin(angleInRadians)).toFloat()
        
        // Draw the value text larger and black
        paint.color = Color.BLACK
        paint.textSize = 18f
        paint.textAlign = Paint.Align.CENTER
        paint.style = Paint.Style.FILL
        paint.clearShadowLayer()
        
        val text = "${value.toInt()} kWh"
        canvas.drawText(text, x, y + 4f, paint)
        paint.clearShadowLayer()
    }

    private fun getLighterColor(color: Int): Int {
        val red = android.graphics.Color.red(color)
        val green = android.graphics.Color.green(color)
        val blue = android.graphics.Color.blue(color)
        
        return android.graphics.Color.rgb(
            minOf(255, red + 50),
            minOf(255, green + 50),
            minOf(255, blue + 50)
        )
    }

    private fun drawLegend(canvas: Canvas, startX: Float, baselineY: Float) {
        paint.style = Paint.Style.FILL
        paint.textSize = 14f
        paint.textAlign = Paint.Align.LEFT
        var x = 16f
        val y = baselineY
        for (i in dayNames.indices) {
            paint.color = dayColors[i]
            canvas.drawCircle(x, y - 6f, 6f, paint)
            x += 14f
            paint.color = Color.WHITE
            canvas.drawText(dayNames[i], x, y, paint)
            x += paint.measureText(dayNames[i]) + 24f
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 900
            addUpdateListener {
                animationProgress = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        animator = null
        super.onDetachedFromWindow()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val centerX = width / 2f
                val centerY = height / 2f
                val radius = minOf(width, height) / 3f
                val innerRadius = radius * 0.5f
                val dx = event.x - centerX
                val dy = event.y - centerY
                val dist = kotlin.math.sqrt(dx * dx + dy * dy)
                if (dist in innerRadius..radius * 1.05f) {
                    var angle = Math.toDegrees(kotlin.math.atan2(dy, dx).toDouble()).toFloat()
                    angle += 90f
                    if (angle < 0f) angle += 360f
                    val total = weekData.sum()
                    var accum = 0f
                    for (i in weekData.indices) {
                        val sweep = (weekData[i] / total) * 360f
                        if (angle >= accum && angle < accum + sweep) {
                            selectedIndex = i
                            invalidate()
                            break
                        }
                        accum += sweep
                    }
                } else {
                    selectedIndex = -1
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // no-op
            }
        }
        return true
    }
} 