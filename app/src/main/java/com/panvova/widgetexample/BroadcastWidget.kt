package com.panvova.widgetexample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES
import android.widget.RemoteViews
import androidx.annotation.RequiresApi

class BroadcastWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    @RequiresApi(VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val views = RemoteViews(context.packageName, R.layout.broadcast_widget)

        when (intent.action) {
            INCREASE_COUNT -> {
                counter++
                views.setTextViewText(R.id.textView, counter.toString())
            }
            COLOR_BLUE -> {
                val color = context.getColor(R.color.colorPrimary)
                views.setBackgroundColor(color)
            }
            COLOR_PINK -> {
                val color = context.getColor(R.color.colorAccent)
                views.setBackgroundColor(color)
            }
        }

        val broadcastWidget = ComponentName(context, BroadcastWidget::class.java)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.updateAppWidget(broadcastWidget, views)
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.broadcast_widget)

        val increaseCountButton = setupButton(context = context, action = INCREASE_COUNT)
        val colorBlueButton = setupButton(context = context, action = COLOR_BLUE)
        val colorPinkButton = setupButton(context = context, action = COLOR_PINK)

        views.setOnClickPendingIntent(R.id.button_increase_count, increaseCountButton)
        views.setOnClickPendingIntent(R.id.button_blue, colorBlueButton)
        views.setOnClickPendingIntent(R.id.button_pink, colorPinkButton)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun setupButton(
        context: Context,
        action: String
    ): PendingIntent {
        val intent = Intent(context, BroadcastWidget::class.java).apply {
            this.action = action
        }
        return PendingIntent.getBroadcast(
          context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {
        private const val INCREASE_COUNT = "INCREASE_COUNT"
        private const val COLOR_BLUE = "COLOR_BLUE"
        private const val COLOR_PINK = "COLOR_PINK"

        private var counter = 0
    }
}