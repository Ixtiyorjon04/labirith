package uz.xsoft.lessonsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    private val sensorManager: SensorManager by lazy { getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    private val sensor: Sensor by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    private lateinit var ballImg: ImageView
    private val repository: Repository = RepositoryImpl()
    private val map = repository.getMapByLevel(1)
    private var cWidth = 0
    private var cHeight = 0
    private var ballPI = -1
    private var ballPJ = -1

    private var _height: Int? = null
    private var _width: Int? = null
    private lateinit var container: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        container = findViewById(R.id.container)


        container.post {
            cWidth = container.width
            cHeight = container.height
            loadView()
        }


    }

    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


    private fun loadView() {
        _height = cHeight / 20
        _width = cWidth / 30
        var bI = 0
        var bJ = 0
        map.forEachIndexed { i, rows ->
            rows.forEachIndexed { j, value ->
                if (value == 2) {
                    bI = i
                    bJ = j
                }

                if (value == 1) {
                    val img = ImageView(this)
                    img.scaleType = ImageView.ScaleType.FIT_XY
                    container.addView(img)
                    img.layoutParams.apply {
                        width = _width!!
                        height = _height!!
                    }
                    img.x = _width!! * j * 1f
                    img.y = _height!! * i * 1f

                    img.setImageResource(R.drawable.cube)
                }
            }
        }

        addBall(bI, bJ, _width!!, _height!!)
    }

    private fun addBall(i: Int, j: Int, _width: Int, _height: Int) {
        ballPI = i
        ballPJ = j
        ballImg = ImageView(this)
        ballImg.setImageResource(R.drawable.metallball)
        ballImg.scaleType = ImageView.ScaleType.FIT_XY
        container.addView(ballImg)
        ballImg.layoutParams.apply {
            height = _height
            width = _width
        }

        ballImg.x = _width * j * 1f
        ballImg.y = _height * i * 1f

    }

}