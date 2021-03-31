package com.ilianazz.kmeans
package Model

import java.util

trait Model {
    def addObs(o: Observer): Unit

    def removeObs(o: Observer): Unit

    def clearObs(): Unit

    def notifyObs(): Unit

    def init(errorsDisplayed : Boolean): Unit

    def points(): util.ArrayList[Point]

    def W(): util.ArrayList[Point]

    def sameW(kmeans: util.ArrayList[Point]): Boolean

    def iteration(): util.ArrayList[Point]

    def coloratePoints(): Array[Int]

    def movePrototypes(affectations: Array[Int]): util.ArrayList[Point]

    def xMin(): Double

    def yMin(): Double

    def xMax(): Double

    def yMax(): Double
}
