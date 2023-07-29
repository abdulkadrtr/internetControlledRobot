package com.example.sensorshare
import android.Manifest
import android.app.PendingIntent
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sensorshare.ui.theme.SensorShareTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import android.hardware.usb.UsbManager
import android.provider.Settings
import android.widget.Toast
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import java.lang.StringBuilder


var latitude: Double = 0.0
var longitude: Double = 0.0
var gyroscopeX: Float = 0.0f
var gyroscopeY: Float = 0.0f
var gyroscopeZ: Float = 0.0f
var temperatureValue : Float = 0.0f
var lightValue : Float = 0.0f
var accelerometerX : Float = 0.0f
var accelerometerY : Float = 0.0f
var accelerometerZ : Float = 0.0f
var orientationX : Float = 0.0f
var orientationY : Float = 0.0f
var orientationZ : Float = 0.0f

private val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorShareTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CenteredButton()
                }
            }
        }
        //GPS Konum Bilgisi
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val locationListener = MyLocationListener()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        //JIROSOP Bilgisi
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val gyroscopeListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                gyroscopeX = event.values[0]
                gyroscopeY = event.values[1]
                gyroscopeZ = event.values[2]
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        //Işık Bilgisi
        val light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        val lightListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                lightValue = event.values[0]
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        sensorManager.registerListener(lightListener, light, SensorManager.SENSOR_DELAY_NORMAL)

        //Sıcaklık Bilgisi

        val temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        val temperatureListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                temperatureValue = event.values[0]
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        sensorManager.registerListener(temperatureListener, temperature, SensorManager.SENSOR_DELAY_NORMAL)
        //İvmeölçer Bilgisi
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val accelerometerListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                accelerometerX = event.values[0]
                accelerometerY = event.values[1]
                accelerometerZ = event.values[2]
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        // Orientation Sensor
        val orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        val orientationListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                orientationX = event.values[0]
                orientationY = event.values[1]
                orientationZ = event.values[2]
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        sensorManager.registerListener(orientationListener, orientation, SensorManager.SENSOR_DELAY_NORMAL)
        println("SERVER BASLATILDI 1")
        //usb bağlantısı
        val manager = getSystemService(Context.USB_SERVICE) as UsbManager
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers.isEmpty()) {
            println("SERVER BASLATILDI 2")
            return
        }
        val driver = availableDrivers[0]
        val connection = manager.openDevice(driver.device)
        if (connection == null) {
            val permissionIntent = PendingIntent.getBroadcast(
                this,
                0,
                Intent(ACTION_USB_PERMISSION),
                PendingIntent.FLAG_IMMUTABLE
            )
            manager.requestPermission(driver.device, permissionIntent)
            return
        }
        val port = driver.ports[0]
        port.open(connection)
        port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
        startServer(port)
        showToast(this, "Server Başlatıldı")
    }

    inner class MyLocationListener : LocationListener{
        override fun onLocationChanged(location: Location){
            latitude = location.latitude
            longitude = location.longitude
        }
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
@Composable
fun CenteredButton() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { enableHotspot(context) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Ağ Ayarları")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CenteredButtonPreview() {
    SensorShareTheme {
        CenteredButton()
    }
}
fun enableHotspot(context: Context) {
    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
    context.startActivity(intent)
}

fun startServer(port: UsbSerialPort) {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
    val serverThread = Thread {
        try {
            val serverSocket = ServerSocket(8080)
            while (true) {
                /*
                val socket = serverSocket.accept()
                val inputStream = BufferedReader(InputStreamReader(socket.getInputStream()))
                val printWriter = PrintWriter(socket.getOutputStream(), true)
                val request = inputStream.readLine()
                */
                val socket = serverSocket.accept()
                val inputStream = socket.getInputStream()
                val printWriter = PrintWriter(socket.getOutputStream())
                val bytes = ByteArray(1024)
                val bytesRead = inputStream.read(bytes)
                val request = String(bytes, 0, bytesRead-1)
                if (request.startsWith("OPTIONS")) {
                    printWriter.println("HTTP/1.1 200 OK")
                    printWriter.println("Access-Control-Allow-Origin: *")
                    printWriter.println("Access-Control-Allow-Methods: POST")
                    printWriter.println("Access-Control-Allow-Headers: Content-Type")
                    printWriter.flush()
                } else if (request.startsWith("GET /gps HTTP/1.1")) {
                    printWriter.println("HTTP/1.1 200 OK")
                    printWriter.println("Access-Control-Allow-Origin: *")
                    printWriter.println("Access-Control-Allow-Methods: POST")
                    printWriter.println("Access-Control-Allow-Headers: Content-Type")
                    printWriter.println("Content-Type: application/json")
                    printWriter.println()
                    printWriter.println("{\"latitude\": \" $latitude \", \"longitude\": \" $longitude \"}")
                    printWriter.flush()
                } else if (request.startsWith("GET /gyro HTTP/1.1")){
                    printWriter.println("HTTP/1.1 200 OK")
                    printWriter.println("Access-Control-Allow-Origin: *")
                    printWriter.println("Access-Control-Allow-Methods: POST")
                    printWriter.println("Access-Control-Allow-Headers: Content-Type")
                    printWriter.println("Content-Type: application/json")
                    printWriter.println()
                    printWriter.println("{\"gyroscopeX\": \" $gyroscopeX \", \"gyroscopeY\": \" $gyroscopeY \", \"gyroscopeZ\": \" $gyroscopeZ \"}")
                    printWriter.flush()
                } else if (request.startsWith("GET /temperature HTTP/1.1")){
                    printWriter.println("HTTP/1.1 200 OK")
                    printWriter.println("Access-Control-Allow-Origin: *")
                    printWriter.println("Access-Control-Allow-Methods: POST")
                    printWriter.println("Access-Control-Allow-Headers: Content-Type")
                    printWriter.println("Content-Type: application/json")
                    printWriter.println()
                    printWriter.println("{\"temperature\": \" $temperatureValue \"}")
                    printWriter.flush()
                } else if (request.startsWith("GET /light HTTP/1.1")){
                    printWriter.println("HTTP/1.1 200 OK")
                    printWriter.println("Access-Control-Allow-Origin: *")
                    printWriter.println("Access-Control-Allow-Methods: POST")
                    printWriter.println("Access-Control-Allow-Headers: Content-Type")
                    printWriter.println("Content-Type: application/json")
                    printWriter.println()
                    printWriter.println("{\"light\": \" $lightValue \"}")
                    printWriter.flush()
                } else if(request.startsWith("GET /accelerometer HTTP/1.1")){
                    printWriter.println("HTTP/1.1 200 OK")
                    printWriter.println("Access-Control-Allow-Origin: *")
                    printWriter.println("Access-Control-Allow-Methods: POST")
                    printWriter.println("Access-Control-Allow-Headers: Content-Type")
                    printWriter.println("Content-Type: application/json")
                    printWriter.println()
                    printWriter.println("{\"accelerometerX\": \" $accelerometerX \", \"accelerometerY\": \" $accelerometerY \", \"accelerometerZ\": \" $accelerometerZ \"}")
                    printWriter.flush()
                } else if(request.startsWith("GET /orientation HTTP/1.1")){
                    printWriter.println("HTTP/1.1 200 OK")
                    printWriter.println("Access-Control-Allow-Origin: *")
                    printWriter.println("Access-Control-Allow-Methods: POST")
                    printWriter.println("Access-Control-Allow-Headers: Content-Type")
                    printWriter.println("Content-Type: application/json")
                    printWriter.println()
                    printWriter.println("{\"orientationX\": \" $orientationX \", \"orientationY\": \" $orientationY \", \"orientationZ\": \" $orientationZ \"}")
                    printWriter.flush()
                } else if(request.startsWith("POST /control HTTP/1.1")){
                    printWriter.println("HTTP/1.1 200 OK")
                    printWriter.println("Access-Control-Allow-Origin: *")
                    printWriter.println("Access-Control-Allow-Methods: POST")
                    printWriter.println("Access-Control-Allow-Headers: Content-Type")
                    printWriter.println("Content-Type: application/json")
                    printWriter.println()
                    try{
                        var controlSignal = request.substringAfterLast(":").trim()
                        controlSignal = controlSignal.substring(1, controlSignal.length - 1)
                        println("ARDUINO SIGNAL: $controlSignal")
                        port.write(controlSignal.toByteArray(), 1000)
                        printWriter.println("{\"controlSignal\": \" OK \"}")
                        printWriter.flush()
                    }catch (e: Exception){
                        printWriter.println("{\"controlSignal\": \" ERROR \"}")
                        printWriter.flush()
                    }
                }else{
                    printWriter.println("HTTP/1.1 404 Not Found")
                    printWriter.println()
                    printWriter.flush()
                }
                socket.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    serverThread.start()
}


