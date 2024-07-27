package com.dream.live.cricket.score.hd.streaming.utils.playerutils

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View

////Class for player touch listener....
//internal open class OnSwipeTouchListener(c: Context?) :
//    View.OnTouchListener {
//    private val gestureDetector: GestureDetector?
//
//
//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//
//        return event?.let { gestureDetector?.onTouchEvent(it) } == true
//    }
////    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
//////        view.performClick()
////        Log.d("Eventdetector", "motion$motionEvent")
////        return gestureDetector.onTouchEvent(motionEvent)
////
////    }
//
//
//    private inner class GestureListener : SimpleOnGestureListener() {
//        private val swipeThereShould: Int = 100
//        private val swipeVelocityThereShould: Int = 100
//        override fun onDown(e: MotionEvent): Boolean {
//            return true
//        }
//
//        override fun onSingleTapUp(e: MotionEvent): Boolean {
//            onClick()
//            return super.onSingleTapUp(e)
//        }
//
//        override fun onDoubleTap(e: MotionEvent): Boolean {
//            onDoubleClick()
//            return super.onDoubleTap(e)
//        }
//
//        override fun onLongPress(e: MotionEvent) {
//            onLongClick()
//            super.onLongPress(e)
//        }
//
//        override fun onFling(
//            e1: MotionEvent,
//            e2: MotionEvent,
//            velocityX: Float,
//            velocityY: Float
//        ): Boolean {
//            try {
//                val diffY = e1.y.let { e2.y.minus(it) }
//                val diffX = e1.x.let { e2.x.minus(it) }
//                if (diffX.let { kotlin.math.abs(it) } > diffY.let { kotlin.math.abs(it) }) {
//                    if (kotlin.math.abs(diffX) > swipeThereShould && kotlin.math.abs(
//                            velocityX
//                        ) > swipeVelocityThereShould
//                    ) {
//                        if (diffX > 0) {
//                            onSwipeRight()
//                        } else {
//                            onSwipeLeft()
//                        }
//
//                    }
//                } else {
//                    if (kotlin.math.abs(diffY) > swipeThereShould && kotlin.math.abs(
//                            velocityY
//                        ) > swipeVelocityThereShould
//                    ) {
//                        if (diffY < 0) {
//                            onSwipeUp()
//                        } else {
//                            onSwipeDown()
//                        }
//                    }
//                }
//            } catch (exception: Exception) {
//                Log.d("Exception","message"+exception.message)
//            }
//            return false
//        }
//
//
//
//
//    }
//
//    open fun onSwipeRight() {}
//    open fun onSwipeLeft() {}
//    open fun onSwipeUp() {}
//    open fun onSwipeDown() {}
//    open fun onClick() {}
//    private fun onDoubleClick() {}
//    private fun onLongClick() {}
//
//    init {
//        gestureDetector = GestureDetector(c, GestureListener())
//    }
//
//}