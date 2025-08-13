package com.example.smartsolar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BatteryChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    // Data from the image: [This Week, Last Week] for each day
    private val weekData = arrayOf(
        intArrayOf(480, 350), // Sunday
        intArrayOf(280, 150), // Monday
        intArrayOf(500, 300), // Tuesday
        intArrayOf(450, 400), // Wednesday
        intArrayOf(250, 100), // Thursday
        intArrayOf(450, 280), // Friday
        intArrayOf(300, 280)  // Saturday
    )

    private val thisWeekColor = Color.parseColor("#6AABD2")
    private val lastWeekColor = Color.parseColor("#2F688A")

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // Set background color to match the page
        canvas.drawColor(Color.parseColor("#0A0A0A"))
        
        val width = width.toFloat()
        val height = height.toFloat()
        val maxValue = 500f // Max value from the chart
        
        // Fixed dimensions for consistent chart with much more spacing
        val chartPadding = 80f // Reduced padding for wider chart
        val availableWidth = width - chartPadding * 2
        val barWidth = availableWidth / 7f // 7 days
        val maxBarHeight = height - chartPadding * 2 // Leave space for labels
        val startX = chartPadding
        val bottomY = height - chartPadding
        
        // Draw horizontal grid lines
        paint.color = Color.parseColor("#333333")
        paint.strokeWidth = 1f
        for (i in 0..5) {
            val y = chartPadding + (maxBarHeight / 5) * i
            canvas.drawLine(chartPadding, y, width - chartPadding, y, paint)
        }
        
        for (i in weekData.indices) {
            val x = startX + i * barWidth + (barWidth * 0.35f) // Much more spacing between bar groups
            
            // This week bar
            val thisWeekHeight = (weekData[i][0] / maxValue) * maxBarHeight
            paint.color = thisWeekColor
            canvas.drawRect(x, bottomY - thisWeekHeight, x + barWidth * 0.4f, bottomY, paint)
            
            // Last week bar
            val lastWeekHeight = (weekData[i][1] / maxValue) * maxBarHeight
            paint.color = lastWeekColor
            canvas.drawRect(x + barWidth * 0.5f, bottomY - lastWeekHeight, x + barWidth * 0.9f, bottomY, paint)
        }
    }
} 