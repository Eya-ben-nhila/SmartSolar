package com.example.smartsolar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class ThresholdsChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    // Data from the image: percentage values for each threshold
    private val thresholdsData = arrayOf(
        80f, // sensitivity of anti-theft system (80%)
        45f  // Irrigation Schedules (45%)
    )

    private val barColor = Color.parseColor("#6AABD2")
    private val gridColor = Color.parseColor("#333333")

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        
        // Set background color to match the chart area
        canvas.drawColor(Color.parseColor("#0A0A0A"))

        val maxValue = 100f // Max percentage value

        // Use smaller padding to make chart more visible
        val chartPadding = 20f
        val availableWidth = width - chartPadding * 2
        val availableHeight = height - chartPadding * 2
        val startX = chartPadding
        val bottomY = height - chartPadding

        // Draw horizontal grid lines
        paint.color = gridColor
        paint.strokeWidth = 1f
        for (i in 0..5) {
            val y = chartPadding + (availableHeight / 5) * i
            canvas.drawLine(startX, y, width - chartPadding, y, paint)
        }

        // Calculate bar dimensions - make bars narrower
        val barWidth = availableWidth / 4f
        val barSpacing = barWidth * 0.5f

        // Draw the bars as cylinders
        for (i in thresholdsData.indices) {
            val x = startX + (barWidth + barSpacing) * i + barSpacing / 2
            val barHeight = (thresholdsData[i] / maxValue) * availableHeight
            val cornerRadius = barWidth * 0.1f // Rounded corners

            // Draw the remaining 20% as light blue shadow (above 100%)
            val remainingHeight = availableHeight - barHeight
            if (remainingHeight > 0) {
                paint.color = Color.parseColor("#1A4A8BA2") // Light blue with low opacity
                val remainingRect = RectF(x, bottomY - availableHeight, x + barWidth, bottomY - barHeight)
                canvas.drawRoundRect(remainingRect, cornerRadius, cornerRadius, paint)
            }

            // Draw main cylinder body with same color throughout
            paint.color = barColor
            val mainRect = RectF(x, bottomY - barHeight, x + barWidth, bottomY)
            canvas.drawRoundRect(mainRect, cornerRadius, cornerRadius, paint)
        }
    }
} 