package com.panvova.widgetexample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.rznnike.widgetexample.R.id
import com.rznnike.widgetexample.R.layout

/**
 * Implementation of App Widget functionality.
 */
class BroadcastWidget : AppWidgetProvider() {
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) { // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (ACTION_SIMPLE_APP_WIDGET == intent.action) {
            mCounter++
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, layout.broadcast_widget)
            views.setTextViewText(id.tvWidget, mCounter.toString())
            // This time we don`t have widgetId. Reaching our widget with that way.
            val appWidget = ComponentName(context, BroadcastWidget::class.java)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidget, views)
        }
    }

    companion object {
        private const val ACTION_SIMPLE_APP_WIDGET = "ACTION_SIMPLE_APP_WIDGET"
        private var mCounter = 0

        fun updateAppWidget(
                context: Context,
                appWidgetManager: AppWidgetManager,
                appWidgetId: Int
        ) { // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, layout.broadcast_widget)
            // Construct an Intent which is pointing this class.
            val intent = Intent(context, BroadcastWidget::class.java)
            intent.action = ACTION_SIMPLE_APP_WIDGET
            // And this time we are sending a broadcast with getBroadcast
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(id.tvWidget, pendingIntent)
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}