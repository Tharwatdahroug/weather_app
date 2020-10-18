package com.example.sun

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun buGetSunriseTimeEvent(view:View){
        val city = ettCityName.text.toString()
        val url ="http://api.openweathermap.org/data/2.5/weather?q=$city&appid=3e384c3201fed4eac881492d626a6a4e"
        MyASyncTesk().execute(url)
    }
    inner class MyASyncTesk:AsyncTask<String,String,String>(){
        override fun onPreExecute() {

        }

        override fun doInBackground(vararg params: String?): String {
           try {
                val url=URL(params[0])
               val urlconnect= url.openConnection() as HttpURLConnection
               urlconnect.connectTimeout=700

               val dataJsonAsStr= convertStreamToString(urlconnect.inputStream)
               publishProgress(dataJsonAsStr)
           }catch (ex:Exception){

           }
            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
        val json=JSONObject(values[0])
            val main=json.getJSONObject("main")
            val temp  = main.getInt("temp")-272
            val tempMin = "Min Temp: " + (main.getInt("temp_min")-272)
            val tempMax = "Max Temp: " + (main.getInt("temp_max")-272)

            tvSunriseTime.text="temp is:"+temp+"°C"
            tvSunriseTime2.text=tempMin+"°C"
            tvSunriseTime3.text=tempMax+"°C"
        }

        override fun onPostExecute(result: String?) {

        }
    }
    fun convertStreamToString(inputStream:InputStream):String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var allString: String = ""
        try {
            do {
                line = bufferReader.readLine()
                if (line != null)
                    allString += line
            } while (line != null)

            bufferReader.close()

        } catch (ex: Exception) {
        }
        return allString
    }

}


