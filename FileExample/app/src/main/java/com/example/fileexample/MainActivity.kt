package com.example.fileexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dict = hashMapOf("LIT" to "Little Rock", "DFW" to "Dallas/Fort Worth")
        var dict1 = HashMap<String,Any>()
        dict1["LIT"] = "Little Rock"
        dict1.put("LIT","Little Rock Ar")
        dict1.put("DFW","WFAA")
        dict1.put("OKC", "Oklahoma City");

        var value = dict1.get("Lit")
        var keys = dict1.keys
        var values = dict1.values

        for (key in keys)
        {
            println("Key " + key + " value: " + dict1[key])
        }

        //Display the # of elements
        println("# of elements: " + dict1.size)

        //Create an array of HashMaps
        var arrayOfHashMaps = arrayOf(dict,dict1)

        //Create a HashMaps of HashMaps
        var dict2 = HashMap<String, HashMap<String,Any>>()
        dict2.put("id1", dict1)
        dict2.put("id2", dict as HashMap<String,Any>)

        var is1 = this.getAssets().open("people1.txt")

        //Convert to a BufferedReader using the following statement:

        var reader1 = BufferedReader(InputStreamReader(is1))

        //Read all the lines in the file and store in an array:
        var lines1 = reader1.readLines()

        //Convert the List to an array of Lines
        var arrayLines1 = lines1.toTypedArray()

        //Create a 2D Array
        var allData1 = arrayOf<Array<String>>()

        //Store the fields as a 2-D Array of Strings (Parse the , delimited fields)
        //Parse into fields
        for (i in 0..arrayLines1.size - 1) {
            var array1 = arrayLines1[i].split(",")
            allData1 += array1.toTypedArray()
        }

        //Close the Reader
        reader1.close()


        var fileWrite = File(this.filesDir, "data.txt")
        fileWrite.createNewFile()
        println(fileWrite)
        var out_file = PrintStream(fileWrite)

        var array = arrayOf<Array<String>>(
            arrayOf("Joe Smith", "1215 Main", "Conway", "AR", "72035"),
            arrayOf("Jim Davis", "1320 South", "Morrilton", "AR", "71912"),
            arrayOf("Mary Jones", "1919 Front St", "Mayflower", "AR", "75121")
        )

        for (i in 0..(array.size - 1)) {
            var str: String = ""
            for (j in 0..(array[i].size - 1)) {
                str += array[i][j]
                if (j < array[i].size - 1)
                    str += ","
            }
            //Write the line to the file
            out_file.println(str)
        }
        //Close the out
        out_file.close()

        //Open file for reading
        var reader = fileWrite.bufferedReader()
        var lines = reader.readLines()
        var arrayLines = lines.toTypedArray()
        var allData = arrayOf<Array<String>>()
        //Parse into fields
        for (i in 0..arrayLines.size - 1) {
            var array1 = arrayLines[i].split(",")
            allData += array1.toTypedArray()
        }
        reader.close()


    }
}