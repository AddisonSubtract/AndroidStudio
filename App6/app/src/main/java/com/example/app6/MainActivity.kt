package com.example.app6

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private var defaultLion = Rect(0,0,0,0)
    private var defaultCobra = Rect(0,0,0,0)
    private var defaultRabbit = Rect(0,0,0,0)
    private var backgroundCoord = Rect(0,0,0,0)
    private var playerCoord = Rect(0, 0, 0, 0)
    private var computerCoord = Rect(0, 0, 0, 0)
    private var imageView : ImageView? = null


    companion object
    {
        private var instance : MainActivity? = null
        public fun getInstance() : MainActivity
        {
            return instance!!
        }
    }

    // setters and getters for starting position of respective images
    fun setDefaultLion(origtouchedLion : Rect) {
        this.defaultLion = origtouchedLion
    }

    fun getDefaultLion() : Rect {
        return defaultLion
    }

    fun setDefaultCobra(origtouchedCobra : Rect) {
        this.defaultCobra = origtouchedCobra
    }

    fun getDefaultCobra() : Rect {
        return defaultCobra
    }

    fun setDefaultRabbit(origtouchedRabbit : Rect) {
        this.defaultRabbit = origtouchedRabbit
    }

    fun getDefaultRabbit() : Rect {
        return defaultRabbit
    }

    fun setBackgroundCoord(backgroundCoord : Rect) {
        this.backgroundCoord = backgroundCoord
    }

    fun getBackgroundCoord() : Rect {
        return backgroundCoord
    }

    fun setPlayerCoord(playerCoord : Rect) {
        this.playerCoord = playerCoord
    }

    fun getPlayerCoord() : Rect {
        return playerCoord
    }

    fun setComputerCoord(computerCoord : Rect) {
        this.computerCoord = computerCoord
    }

    fun getComputerCoord() : Rect {
        return computerCoord
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.activity_main)

        imageView = ImageView(this)

        val player = findViewById<ImageView>(R.id.player)
        val phoneText = findViewById<TextView>(R.id.phoneText)
        val phoneScore = findViewById<TextView>(R.id.phoneScore)
        val playerText = findViewById<TextView>(R.id.playerText)
        val playerScore = findViewById<TextView>(R.id.playerScore)
        val titleImage = findViewById<ImageView>(R.id.titleImage)

        player.visibility = View.INVISIBLE
        phoneText.visibility = View.INVISIBLE
        phoneScore.visibility = View.INVISIBLE
        playerText.visibility = View.INVISIBLE
        playerScore.visibility = View.INVISIBLE

        var myZoom = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.zoom_in)
        var zoomHandler = ZoomHandler()
        myZoom.setAnimationListener(zoomHandler)
        titleImage.startAnimation(myZoom)

        imageView = ImageView(this)


    }


    /*********************FadeOutHandler**********************************/
    inner class FadeOutHandler : Animation.AnimationListener
    {
        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {
            val phoneText = findViewById<TextView>(R.id.phoneText)
            val phoneScore = findViewById<TextView>(R.id.phoneScore)
            val playerText = findViewById<TextView>(R.id.playerText)
            val playerScore = findViewById<TextView>(R.id.playerScore)

            println("Fade out finshied")
            var myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
            var fadeHandler = FadeInHandler()
            myFadeIn.setAnimationListener(fadeHandler)
            phoneScore.startAnimation(myFadeIn)
            phoneText.startAnimation(myFadeIn)
            playerScore.startAnimation(myFadeIn)
            playerText.startAnimation(myFadeIn)

        }
        override fun onAnimationStart(animation: Animation?)
        {
            println("Fade out start")
        }
    }
    /****************************************************************/
    /**************************FadeInHandler**********************************************/
    inner class FadeInHandler : Animation.AnimationListener
    {

        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {
            val phoneText = findViewById<TextView>(R.id.phoneText)
            val phoneScore = findViewById<TextView>(R.id.phoneScore)
            val playerText = findViewById<TextView>(R.id.playerText)
            val playerScore = findViewById<TextView>(R.id.playerScore)

            println("Fade in finshied")
            phoneText.visibility = View.VISIBLE
            phoneScore.visibility = View.VISIBLE
            playerText.visibility = View.VISIBLE
            playerScore.visibility = View.VISIBLE

        }
        override fun onAnimationStart(animation: Animation?)
        {
                 }
    }
    /**********************************************************************************/

    /***********************************Zoom in Handler**********************************/

    inner class ZoomHandler : Animation.AnimationListener
    {
        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {
            val titleImage = findViewById<ImageView>(R.id.titleImage)

            var myFade = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
            var fadeHandler = FadeOutHandler()
            myFade.setAnimationListener(fadeHandler)
            titleImage.startAnimation(myFade)
        }
        override fun onAnimationStart(animation: Animation?)
        {
            val lion = findViewById<ImageView>(R.id.lion)
            val cobra = findViewById<ImageView>(R.id.cobra)
            val rabbit = findViewById<ImageView>(R.id.rabbit)
            val frame = findViewById<ImageView>(R.id.frame)
            val player = findViewById<ImageView>(R.id.player)
            val computer = findViewById<ImageView>(R.id.computer)

            setDefaultLion(Rect(lion.left,lion.top,lion.right, lion.bottom))
            setDefaultCobra(Rect(cobra.left,cobra.top,cobra.right, cobra.bottom))
            setDefaultRabbit(Rect(rabbit.left,rabbit.top,rabbit.right, rabbit.bottom))
            setBackgroundCoord(Rect(frame.left, frame.top, frame.right, frame.bottom))
            setPlayerCoord(Rect(player.left, player.top, player.right, player.bottom))
            setComputerCoord(Rect(computer.left, computer.top, computer.right, computer.bottom))
        }
    }
    /*************************************************************************************************/

}