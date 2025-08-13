package com.example.smartsolar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

class PVChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    // Data for 7 days of the week with PV production in kWh (exact values from image)
    private val weekData = arrayOf(
        90f,  // Saturday
        45f,  // Friday
        75f,  // Thursday
        30f,  // Wednesday
        55f,  // Tuesday
        70f,  // Monday
        25f   // Sunday
    )

    private val dayNames = arrayOf("Saturday", "Friday", "Thursday", "Wednesday", "Tuesday", "Monday", "Sunday")
    private val maxValue = 100f // Max kWh value for scale

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        // Set background color to match the chart area
        canvas.drawColor(Color.parseColor("#0A0A0A"))

        // Draw bar chart
        drawBarChart(canvas, width, height)

        // Draw day labels
        drawDayLabels(canvas, width, height)

        // Draw legend
        drawLegend(canvas, width, height)
    }

    private fun drawBarChart(canvas: Canvas, width: Float, height: Float) {
        val chartPadding = 60f
        val availableWidth = width - chartPadding * 2
        val availableHeight = height - chartPadding * 2
        val startX = chartPadding
        val bottomY = height - chartPadding

        // Draw horizontal grid lines
        paint.color = Color.parseColor("#333333")
        paint.strokeWidth = 1f
        paint.style = Paint.Style.STROKE

        for (i in 0..5) {
            val y = chartPadding + (availableHeight / 5) * i
            canvas.drawLine(startX, y, width - chartPadding, y, paint)
        }

        // Draw Y-axis labels
        paint.color = Color.WHITE
        paint.textSize = 10f
        paint.textAlign = Paint.Align.RIGHT
        paint.style = Paint.Style.FILL

        for (i in 0..5) {
            val y = chartPadding + (availableHeight / 5) * i
            val value = (5 - i) * 20
            canvas.drawText("$value", startX - 10f, y + 4f, paint)
        }

        // Calculate bar dimensions - make bars much thinner with more spacing
        val barWidth = availableWidth / 20f  // Even thinner bars
        val barSpacing = barWidth * 2f  // Much more spacing between bars

        // Draw the bars
        for (i in weekData.indices) {
            val x = startX + (barWidth + barSpacing) * i + barSpacing / 2
            val barHeight = (weekData[i] / maxValue) * availableHeight
            val cornerRadius = barWidth * 0.1f

            // Draw bar with rounded corners - light blue/yellow
            paint.color = Color.parseColor("#87CEEB")  // Light blue
            paint.style = Paint.Style.FILL
            canvas.drawRoundRect(x, bottomY - barHeight, x + barWidth, bottomY, cornerRadius, cornerRadius, paint)

            // Draw bar border - lighter border
            paint.color = Color.parseColor("#5F9EA0")
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1f
            canvas.drawRoundRect(x, bottomY - barHeight, x + barWidth, bottomY, cornerRadius, cornerRadius, paint)
        }
    }



    private fun drawDayLabels(canvas: Canvas, width: Float, height: Float) {
        paint.color = Color.WHITE
        paint.textSize = 12f
        paint.textAlign = Paint.Align.CENTER
        paint.style = Paint.Style.FILL

        val chartPadding = 60f
        val availableWidth = width - chartPadding * 2
        val barWidth = availableWidth / 20f  // Match the thinner bars
        val barSpacing = barWidth * 2f  // Match the increased spacing

        // Draw day labels below the bars
        for (i in dayNames.indices) {
            val x = chartPadding + (barWidth + barSpacing) * i + barSpacing / 2 + barWidth / 2
            val y = height - 20f
            canvas.drawText(dayNames[i], x, y, paint)
        }
    }

    private fun drawLegend(canvas: Canvas, width: Float, height: Float) {
        val legendX = width / 2f - 40f
        val legendY = height - 2f  // Move to the very bottom

        // Draw legend rectangle (bar representation) - light blue
        paint.color = Color.parseColor("#87CEEB")
        paint.style = Paint.Style.FILL
        canvas.drawRect(legendX, legendY - 8f, legendX + 20f, legendY + 8f, paint)

        // Draw legend text
        paint.color = Color.WHITE
        paint.textSize = 12f
        paint.textAlign = Paint.Align.LEFT
        paint.style = Paint.Style.FILL
        canvas.drawText("This Week", legendX + 30f, legendY + 4f, paint)
    }
} 