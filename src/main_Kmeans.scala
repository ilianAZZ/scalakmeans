package com.ilianazz.kmeans

import com.ilianazz.kmeans.Controller.ControllerKmeans
import com.ilianazz.kmeans.Views.Application

import java.awt.Color


object main_Kmeans {


    def main(args: Array[String]): Unit = {


        val c = new ControllerKmeans()
        val v = new Application(c)
        c.setView(v)

        /**
         * Zone 0 : Global
         * 0 : Leave
         * 1 : Go Home
         *
         * Zone 1 : Home
         * 0 : Go Main
         * 1 : Go Config
         * 2 : Go Credits
         *
         * Zone 2 : Main
         * 0 : start
         * 1 : reload
         *
         * Zone 3 : Config
         * 0 : random k check box
         *
         */
    }
}
