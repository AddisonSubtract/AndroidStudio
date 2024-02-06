package com.example.app6

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.opengl.Visibility
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class MyView : View {
    private var lion : ImageView? = null
    private var cobra : ImageView? = null
    private var rabbit : ImageView? = null
    private var lionCoords : Rect = Rect(0,0,0,0)
    private var cobraCoords : Rect = Rect(0,0,0,0)
    private var rabbitCoords : Rect = Rect(0,0,0,0)
    private var playerCoord = Rect(0, 0, 0, 0)
    private var computerCoord = Rect(0, 0, 0, 0)
    private var startLionPoint : Point = Point(0,0)
    private var startCobraPoint : Point = Point(0,0)
    private var startRabbitPoint : Point = Point(0,0)
    private var defaultLion = Rect(0,0,0,0)
    private var defaultCobra = Rect(0,0,0,0)
    private var defaultRabbit = Rect(0,0,0,0)
    private var backgroundCoord = Rect(0,0,0,0)
    private var touchingLion = false
    private var touchingCobra = false
    private var touchingRabbit = false
    private var start = true
    private var start2 = true
    private var images = arrayOf<Int>(R.drawable.lion0, R.drawable.lion1, R.drawable.lion2, R.drawable.lion3)
    private var computers = arrayOf<Int>(R.drawable.lion0, R.drawable.cobra, R.drawable.rabbit)
    private var computer : ImageView? = null
    private var results : TextView? = null

    companion object {
        private var instance : MyView? = null
        public fun getInstance() : MyView {
            return instance!!
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.setWillNotDraw(false)
        instance = this
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (start) {
            start = false
            computer?.visibility = INVISIBLE
        }
        else {
            lion?.setX(lionCoords.left.toFloat())
            lion?.setY(lionCoords.top.toFloat())
            cobra?.setX(cobraCoords.left.toFloat())
            cobra?.setY(cobraCoords.top.toFloat())
            rabbit?.setX(rabbitCoords.left.toFloat())
            rabbit?.setY(rabbitCoords.top.toFloat())
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        lion = MainActivity.getInstance().findViewById(R.id.lion)
        cobra = MainActivity.getInstance().findViewById(R.id.cobra)
        rabbit = MainActivity.getInstance().findViewById(R.id.rabbit)
        computer = MainActivity.getInstance().findViewById(R.id.computer)
        results = MainActivity.getInstance().findViewById(R.id.results)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (start2) {
            defaultLion = MainActivity.getInstance().getDefaultLion()
            defaultCobra = MainActivity.getInstance().getDefaultCobra()
            defaultRabbit = MainActivity.getInstance().getDefaultRabbit()
            backgroundCoord = MainActivity.getInstance().getBackgroundCoord()
            playerCoord = MainActivity.getInstance().getPlayerCoord()
            computerCoord = MainActivity.getInstance().getComputerCoord()
            lionCoords = defaultLion
            cobraCoords = defaultCobra
            rabbitCoords = defaultRabbit
            start2 = false
        }
        var x = event.getX()
        var y = event.getY()

        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
            if ( (x > lion?.left!!) && (x < lion?.right!!) &&
                (y > lion?.top!!) && (y < lion?.bottom!!)) {
                touch("Lion")

                val handler = Handler()
                var i = 0
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        lion!!.setImageResource(images.get(i))
                        handler.postDelayed(this, 2000)
                        i++
                        if (i > images.size-1)
                            i = 0
                        if (!touchingLion) {
                            handler.removeCallbacks(this)
                            lion!!.setImageResource(images.get(0))
                            lion!!.left = lionCoords.left
                            lion!!.right = lionCoords.right
                            lion!!.top = lionCoords.top
                            lion!!.bottom = lionCoords.bottom
                            invalidate()
                        }
                    }
                }, 2000)
            }
            else if ((x > cobra?.left!!) && (x < cobra?.right!!) &&
                (y > cobra?.top!!) && (y < cobra?.bottom!!)) {
                touch("Cobra")
            }
            else if ((x > rabbit?.left!!) && (x < rabbit?.right!!) &&
                (y > rabbit?.top!!) && (y < rabbit?.bottom!!)) {
               touch("Rabbit")
            }

        }
        else if (event.getAction() ==  MotionEvent.ACTION_MOVE) {
            if (touchingLion) {
                move("Lion", x, y)
            }
            else if (touchingCobra) {
                move("Cobra", x, y)
            }
            else if (touchingRabbit) {
                move("Rabbit", x, y)
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (touchingLion) {
                if (doOverlap(backgroundCoord, lionCoords)) {
                    lionCoords = playerCoord;
                    lion?.left = lionCoords.left
                    lion?.right = lionCoords.right
                    lion?.top = lionCoords.top
                    lion?.bottom = lionCoords.bottom
                    touchingLion = false
                    var rival = computers.get((0..2).random())
                    computer?.setImageResource(rival)
                    computer?.visibility = VISIBLE
                    results?.visibility = VISIBLE
                    lion?.let { results(it, rival) }
                }
                else {
                    lionCoords = defaultLion
                    lion?.left = lionCoords.left
                    lion?.right = lionCoords.right
                    lion?.top = lionCoords.top
                    lion?.bottom = lionCoords.bottom
                    touchingLion = false
                }
                this.invalidate()
            }
            else if (touchingCobra) {
                if (doOverlap(backgroundCoord, cobraCoords)) {
                    cobraCoords = playerCoord;
                    cobra?.left = cobraCoords.left
                    cobra?.right = cobraCoords.right
                    cobra?.top = cobraCoords.top
                    cobra?.bottom = cobraCoords.bottom
                    touchingCobra = false
                    var rival = computers.get((0..2).random())
                    computer?.setImageResource(rival)
                    computer?.visibility = VISIBLE
                    results?.visibility = VISIBLE
                    cobra?.let { results(it, rival) }
                }
                else {
                    cobraCoords = defaultCobra
                    cobra?.left = cobraCoords.left
                    cobra?.right = cobraCoords.right
                    cobra?.top = cobraCoords.top
                    cobra?.bottom = cobraCoords.bottom
                    touchingCobra = false
                }
                this.invalidate()
            }
            else if (touchingRabbit) {
                if (doOverlap(backgroundCoord, rabbitCoords)) {
                    rabbitCoords = playerCoord;
                    rabbit?.left = rabbitCoords.left
                    rabbit?.right = rabbitCoords.right
                    rabbit?.top = rabbitCoords.top
                    rabbit?.bottom = rabbitCoords.bottom
                    rabbit?.scaleX = -1.0f
                    touchingRabbit = false
                    var rival = computers.get((0..2).random())
                    computer?.setImageResource(rival)
                    computer?.visibility = VISIBLE
                    results?.visibility = VISIBLE
                    rabbit?.let { results(it, rival) }
                }
                else {
                    rabbitCoords = defaultRabbit
                    rabbit?.left = rabbitCoords.left
                    rabbit?.right = rabbitCoords.right
                    rabbit?.top = rabbitCoords.top
                    rabbit?.bottom = rabbitCoords.bottom
                    if (rabbit?.scaleX == -1.0f)
                        rabbit?.scaleX = 1.0f
                    touchingRabbit = false
                }
                this.invalidate()
            }

        }

        return true
    }
    fun touch(animal: String) {
        if (animal == "Lion"){
            lionCoords = Rect(lion?.left!!,lion?.top!!,lion?.right!!,lion?.bottom!!)
            startLionPoint.set(x.toInt(),y.toInt())
            touchingLion = true
        }
        else if (animal == "Cobra"){
            cobraCoords = Rect(cobra?.left!!,cobra?.top!!,cobra?.right!!,cobra?.bottom!!)
            startCobraPoint.set(x.toInt(),y.toInt())
            touchingCobra = true
        }else if (animal == "Rabbit"){
            rabbitCoords = Rect(rabbit?.left!!,rabbit?.top!!,rabbit?.right!!,rabbit?.bottom!!)
            startRabbitPoint.set(x.toInt(),y.toInt())
            touchingRabbit = true
        }
    }

    fun move (animal: String, x: Float, y: Float) {
        if (animal == "Lion"){
            lionCoords.left = x.toInt()
            lionCoords.top = y.toInt()
            lionCoords.right = (x + lion?.getWidth()!!).toInt()
            lionCoords.bottom = (y + lion?.getHeight()!!).toInt()
            this.invalidate()
        }
        else if (animal == "Cobra"){
            cobraCoords.left = x.toInt()
            cobraCoords.top = y.toInt()
            cobraCoords.right = (x + cobra?.getWidth()!!).toInt()
            cobraCoords.bottom = (y + cobra?.getHeight()!!).toInt()
            this.invalidate()
        }
        else if (animal == "Rabbit"){
            rabbitCoords.left = x.toInt()
            rabbitCoords.top = y.toInt()
            rabbitCoords.right = (x + rabbit?.getWidth()!!).toInt()
            rabbitCoords.bottom = (y + rabbit?.getHeight()!!).toInt()
            this.invalidate()
        }
    }

    fun results (animal: ImageView, rival: Int){
        var score = MainActivity.getInstance().findViewById<TextView>(R.id.playerScore)
        var scoreOpp = MainActivity.getInstance().findViewById<TextView>(R.id.phoneScore)

        var myFadeIn = AnimationUtils.loadAnimation(MyView.getInstance().context, R.anim.fade_in)
        var fadeHandler = FadeInHandler()
        myFadeIn.setAnimationListener(fadeHandler)

        var myFadeOut = AnimationUtils.loadAnimation(MyView.getInstance().context, R.anim.fade_out)
        var fadeOutHandler = FadeOutHandler()
        myFadeOut.setAnimationListener(fadeOutHandler)

        var tie = false
        var lose = false
        var win = false

        if(animal == MainActivity.getInstance().findViewById(R.id.lion)){
            if (rival == R.drawable.lion0) {
                results?.text = "Tie!"
                tie = true
            }else if (rival == R.drawable.cobra){
                results?.text = "Cobra defeats Lion - You Lose!"
                lose = true
            }else if(rival == R.drawable.rabbit){
                results?.text = "Lion defeats Rabbit - You Win!"
                win = true
            }
        }else if (animal == MainActivity.getInstance().findViewById(R.id.cobra)){
            if (rival == R.drawable.lion0) {
                results?.text = "Cobra defeats Lion - You Win!!"
                win = true
            }else if (rival == R.drawable.cobra){
                results?.text = "Tie!"
                tie = true
            }else if(rival == R.drawable.rabbit){
                results?.text = "Rabbit defeats Cobra - You Lose!"
                lose = true
            }
        }else if(animal == MainActivity.getInstance().findViewById(R.id.rabbit)){
            if (rival == R.drawable.lion0) {
                results?.text = "Lion defeats Rabbit - You Lose!!"
                lose = true
            }else if (rival == R.drawable.cobra){
                results?.text = "Rabbit defeats Cobra - You Win!"
                win = true
            }else if(rival == R.drawable.rabbit){
                results?.text = "Tie!"
                tie = true
            }
        }


        if (tie) {
            computer?.scaleX = -1.0f
            computer?.startAnimation(myFadeIn)
            results?.startAnimation(myFadeIn)
            myFadeOut.fillAfter = false
            animal?.startAnimation(myFadeOut)
            computer?.startAnimation(myFadeOut)
            tie = false
        }
        if (lose) {
            scoreOpp.setText("" + (Integer.parseInt(scoreOpp.text.toString()) + 1))

            computer?.scaleX = -1.0f
            results?.startAnimation(myFadeIn)
            computer?.startAnimation(myFadeIn)
            animal?.startAnimation(myFadeOut)
            var deltaX = (playerCoord.left-computerCoord.left).toFloat()
            var deltaY = (playerCoord.top-computerCoord.top).toFloat()
            var moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
            moveAnim.duration = 2000
            moveAnim.fillAfter = true
            var handler = MoveHandler()
            moveAnim.setAnimationListener(handler)
            computer?.startAnimation(moveAnim)
            animal?.startAnimation(myFadeOut)
            lose = false
        }
        if (win) {
            score.setText("" + (Integer.parseInt(score.text.toString()) + 1))

            computer?.startAnimation(myFadeIn)
            results?.startAnimation(myFadeIn)
            var deltaX = (computerCoord.left-playerCoord.left).toFloat()
            var deltaY = (computerCoord.top-playerCoord.top).toFloat()
            var moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
            moveAnim.duration = 2000
            moveAnim.fillAfter = false
            var handler = MoveHandler()
            moveAnim.setAnimationListener(handler)
            animal?.startAnimation(moveAnim)
            computer?.startAnimation(myFadeOut)
            win = false
        }
    }

    /*********************FadeOutHandler**********************************/
    inner class FadeOutHandler : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }
        override fun onAnimationEnd(animation: Animation?) {
            computer?.visibility = INVISIBLE
            results?.visibility = INVISIBLE
            if (lionCoords == playerCoord) {
                lionCoords = defaultLion
                lion?.left = lionCoords.left
                lion?.right = lionCoords.right
                lion?.top = lionCoords.top
                lion?.bottom = lionCoords.bottom
                invalidate()
                var myFadeOut = AnimationUtils.loadAnimation(MyView.getInstance().context, R.anim.fade_out)
                var fadeOutHandler = FadeOutHandler()
                myFadeOut.setAnimationListener(fadeOutHandler)
                results?.startAnimation(myFadeOut)
            }
            if (cobraCoords == playerCoord) {
                cobraCoords = defaultCobra
                cobra?.left = cobraCoords.left
                cobra?.right = cobraCoords.right
                cobra?.top = cobraCoords.top
                cobra?.bottom = cobraCoords.bottom
                invalidate()
            }
            if (rabbitCoords == playerCoord) {
                rabbitCoords = defaultRabbit
                rabbit?.left = rabbitCoords.left
                rabbit?.right = rabbitCoords.right
                rabbit?.top = rabbitCoords.top
                rabbit?.bottom = rabbitCoords.bottom
                if (rabbit?.scaleX == -1.0f)
                    rabbit?.scaleX = 1.0f
                invalidate()
            }
        }
        override fun onAnimationStart(animation: Animation?) {

        }
    }

    fun doOverlap(rect1 : Rect, rect2 : Rect) : Boolean {
        if (rect1.left > rect2.right || rect2.left > rect1.right)
            return false
        if (rect1.top > rect2.bottom || rect2.top > rect1.bottom)
            return false
        return true
    }
    /*********************Move Handler**********************************/
    inner class MoveHandler : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }
        override fun onAnimationEnd(animation: Animation?) {

        }
        override fun onAnimationStart(animation: Animation?) {

        }
    }
    /**************************FadeInHandler**********************************************/
    inner class FadeInHandler : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }
        override fun onAnimationEnd(animation: Animation?) {

        }
        override fun onAnimationStart(animation: Animation?) {

        }
    }
}