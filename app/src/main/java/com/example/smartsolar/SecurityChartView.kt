package com.example.smartsolar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class SecurityChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    // Data from the image: CVSS values for each day (Jul 23-30)
    private val pumpVitesseData = arrayOf(
        3.5f, // Jul 23
        2.0f, // Jul 24
        6.5f, // Jul 25
        4.0f, // Jul 26
        7.5f, // Jul 27
        3.0f, // Jul 28
        5.5f, // Jul 29
        7.0f  // Jul 30
    )

    private val lineColor = Color.parseColor("#6AABD2")
    private val gridColor = Color.parseColor("#333333")

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // Set background color to match the chart area
        canvas.drawColor(Color.parseColor("#0A0A0A"))
        
        val width = width.toFloat()
        val height = height.toFloat()
        val maxValue = 10f // Max CVSS value
        
        // Fixed dimensions for consistent chart
        val chartPadding = 60f
        val availableWidth = width - chartPadding * 2
        val availableHeight = height - chartPadding * 2
        val startX = chartPadding
        val startY = chartPadding
        val endX = width - chartPadding
        val endY = height - chartPadding
        
        // Draw horizontal grid lines
        paint.color = gridColor
        paint.strokeWidth = 1f
        for (i in 0..4) {
            val y = startY + (availableHeight / 4) * i
            canvas.drawLine(startX, y, endX, y, paint)
        }
        
        // Draw vertical grid lines
        for (i in 0..7) {
            val x = startX + (availableWidth / 7) * i
            canvas.drawLine(x, startY, x, endY, paint)
        }
        
        // Draw the line chart
        paint.color = lineColor
        paint.strokeWidth = 3f
        paint.style = Paint.Style.STROKE
        
        val path = Path()
        var isFirst = true
        
        for (i in pumpVitesseData.indices) {
            val x = startX + (availableWidth / 7) * i
            val normalizedValue = pumpVitesseData[i] / maxValue
            val y = endY - (availableHeight * normalizedValue)
            
            if (isFirst) {
                path.moveTo(x, y)
                isFirst = false
            } else {
                path.lineTo(x, y)
            }
        }
        
        canvas.drawPath(path, paint)
        
        // Draw data points
        paint.style = Paint.Style.FILL
        for (i in pumpVitesseData.indices) {
            val x = startX + (availableWidth / 7) * i
            val normalizedValue = pumpVitesseData[i] / maxValue
            val y = endY - (availableHeight * normalizedValue)
            
            // Draw circle for each data point
            canvas.drawCircle(x, y, 6f, paint)
        }
        
        // Draw final point with larger circle (as shown in image)
        val finalX = startX + (availableWidth / 7) * 7
        val finalNormalizedValue = pumpVitesseData[7] / maxValue
        val finalY = endY - (availableHeight * finalNormalizedValue)
        canvas.drawCircle(finalX, finalY, 10f, paint)
    }
} 