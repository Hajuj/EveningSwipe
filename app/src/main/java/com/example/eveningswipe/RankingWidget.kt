package com.example.eveningswipe

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.net.Uri
import android.widget.RemoteViews
import com.example.eveningswipe.httpRequests.HttpRequests

/**
 * Implementation of App Widget functionality.
 */
class RankingWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val groupName = "gruppenname"
    val movie1 = "test1"
    val movie2 = "test1"
    val movie3 = "test1"

    val widgetText = "Last " + groupName + " voting: \n\n1. " + movie1 +
            "\n2. " + movie2 + "\n3. " + movie3
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName , R.layout.ranking_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}