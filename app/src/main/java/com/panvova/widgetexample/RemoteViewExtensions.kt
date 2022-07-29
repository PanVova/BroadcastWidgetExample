package com.panvova.widgetexample

import android.widget.RemoteViews

fun RemoteViews.setBackgroundColor(color: Int) {
  this.setInt(R.id.textView, "setBackgroundColor", color)
}