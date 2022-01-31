package com.example.eveningswipe

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.eveningswipe.ui.filmswipe.groupName

/**
 * Implementation of App Widget functionality
 * source: https://www.geeksforgeeks.org/how-to-create-a-basic-widget-of-an-android-app/
 */
var widgetIds: IntArray? = null

class RankingWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        widgetIds = appWidgetIds
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}

/**
 * function to update widget
 * called in RankingActivity
 */
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    var widgetText: String? = null
    if (groupName == null || movie1 == null || movie2 == null || movie3 == null) {
        widgetText =
            "The top three of 'groupname':\n\n1. 'moviename'\n2. 'moviename'\n3. 'moviename'"
    } else {
        widgetText =
            "The top three of " + groupName + ":\n\n1." + movie1 + "\n2." + movie2 + "\n3." + movie3
    }
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.ranking_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}