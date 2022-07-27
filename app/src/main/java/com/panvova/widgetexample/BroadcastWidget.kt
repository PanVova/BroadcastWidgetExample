package com.panvova.widgetexample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

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

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (ACTION_SIMPLE_APP_WIDGET == intent.action) {
            counter++
            val views = RemoteViews(context.packageName, R.layout.broadcast_widget)
            views.setTextViewText(R.id.tvWidget, counter.toString())
            val appWidget = ComponentName(context, BroadcastWidget::class.java)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            appWidgetManager.updateAppWidget(appWidget, views)
        }
    }

    companion object {
        private const val ACTION_SIMPLE_APP_WIDGET = "ACTION_SIMPLE_APP_WIDGET"
        private var counter = 0

        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.broadcast_widget)
            val intent = Intent(context, BroadcastWidget::class.java).apply {
                action = ACTION_SIMPLE_APP_WIDGET
            }
            val pendingIntent = PendingIntent.getBroadcast(
              context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}