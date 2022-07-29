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

class NewWidget : AppWidgetProvider() {
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
        val views = RemoteViews(context.packageName, R.layout.new_widget)

        when (intent.action) {
            INCREASE_COUNT -> {
                counter++
                views.setTextViewText(R.id.textView, counter.toString())
            }
        }

        val appWidget = ComponentName(context, NewWidget::class.java)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.updateAppWidget(appWidget, views)
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.new_widget)

        val increaseCountButton = setupButton(context = context, action = INCREASE_COUNT)

        views.setOnClickPendingIntent(R.id.increase_count, increaseCountButton)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun setupButton(
        context: Context,
        action: String
    ): PendingIntent {
        val intent = Intent(context, NewWidget::class.java).apply {
            this.action = action
        }
        return PendingIntent.getBroadcast(
          context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {
        private const val INCREASE_COUNT = "INCREASE_COUNT"
        private var counter = 0
    }
}