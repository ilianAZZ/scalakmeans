package com.ilianazz.kmeans
package Model

import java.awt.Color
import java.util
import java.util.Collections
import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`


class Kmeans(private val points_ : util.ArrayList[Point], private val nbW : Int, private val randomW : Boolean = true) extends Model {
    private final val OBSERVERS : util.ArrayList[Observer] = new util.ArrayList[Observer]()
    private final val COLORS : Array[Color] = Array(
        new Color(0,150,0),
        new Color(20,50,150),
        new Color(255,255,0),
        new Color(180,0,255),
        new Color(255,106,0),
        new Color(255,0,255),
        new Color(88,88,88),
        new Color(0,0,255),
        new Color(0,0,180),
        new Color(170,0,100),
        new Color(100,50,0),
        new Color(180,180,50),
        new Color(0,250,100),
        new Color(0,255,200),
        new Color(100,100,255),
        new Color(0,255,0),
        new Color(127,10,107),
    )

    private final val MARGIN : Double = 5/100

    def addObs(o: Observer):    Unit = { this.OBSERVERS.add(o) }
    def removeObs(o: Observer): Unit = { this.OBSERVERS.remove(o) }
    def clearObs():             Unit = { this.OBSERVERS.clear()}
    def notifyObs():            Unit = { this.OBSERVERS.forEach(el => el.refresh()) }

    private var W_ : util.ArrayList[Point] = _
    private var errorsDisplayed : Boolean = _

    def xMax() :Double = points_.map(_.x).max
    def yMax() :Double = points_.map(_.y).max
    def xMin() :Double = points_.map(_.x).min
    def yMin() :Double = points_.map(_.y).min
    def points(): util.ArrayList[Point] = points_
    def W(): util.ArrayList[Point] = this.W_

    def init (errorsDisplayed : Boolean): Unit = {
        this.W_ = generateW
        this.errorsDisplayed = errorsDisplayed
        notifyObs()
    }

    private def generateW: util.ArrayList[Point] = {
        val W_ = new util.ArrayList[Point](this.nbW)
        //if (this.randomW) {
            val melange = new util.ArrayList[Point](points_)
            Collections.shuffle(melange)
            for (i <- 0 until nbW) { W_.add(new Point(melange.get(i))) }
            W_
        //}
    }

    def sameW(W : util.ArrayList[Point]): Boolean = {
        for (j <- 0 until this.W_.size) {
            if (this.W_.get(j) != W.get(j)) { return false }
        }
        true
    }

    def avgPoint(points: Array[Point]): Point = {
        var x_sum, y_sum = 0.0
        for (p <- points) { x_sum += p.x ; y_sum += p.y}
        new Point(x_sum/points.length, y_sum/points.length)
    }



    def iteration(): util.ArrayList[Point] = { this.W_ }

    /**
     * For each point, we put his affectation (the index of the W in the list) into affectation list
     * the nearest W is affected to each point
     */
    def coloratePoints(): Array[Int] = {
        var affectations = Array.fill[Int](points_.size)(-1)
        for (i <- 0 until this.points_.size()) {
            affectations(i) = this.points_.get(i).nearestPoint(this.W_)
            this.points_.set(i, new Point(points_.get(i), COLORS(affectations(i) % COLORS.length)))
        }

        if (this.errorsDisplayed) {
            val avg_distances = new Array[Double](this.W_.size())
            val ecarts_distances = new Array[Double](this.W_.size())

            for (i <- 0 until this.W_.size()) {
                var indexed = affectations.zipWithIndex.filter(_._1 == i).map(_._2)
                var clustersTemp_ = new Array[Point](indexed.length)
                for (j <- indexed.indices) { clustersTemp_(j) = points_.get(indexed(j)) }
                avg_distances(i) = this.W_.get(i).avgDist(clustersTemp_)
                ecarts_distances(i) = this.W_.get(i).ecartType(clustersTemp_)
            }
            for (i <- 0 until this.points_.size()) {
                if (this.W_.get(affectations(i)).dist(this.points_.get(i)) > (avg_distances(affectations(i)) + ecarts_distances(affectations(i))  ) ) {
                    this.points_.set(i, new Point(this.points_.get(i), Color.RED))
                }
            }
        }


        notifyObs()
        affectations
    }

    /**
     * Moving the prototypes (points in W array) based on average point
     */
    def movePrototypes(affectations : Array[Int]) :util.ArrayList[Point] = {

        val newW = new util.ArrayList[Point](this.nbW)
        for (j <- 0 until this.W_.size()) {

            /** We get all the points having Wj as prototype */
            var indexed = affectations.zipWithIndex.filter(_._1 == j).map(_._2)
            var clustersTemp_ = new Array[Point](indexed.length)

            for (i <- indexed.indices) { clustersTemp_(i) = points_.get(indexed(i)) }
            val avg_dist = this.W_.get(j).avgDist(clustersTemp_)

            /** Recreating the points to change their colors */
            for (i <- indexed.indices) { clustersTemp_(i) = new Point(points_.get(indexed(i))) }

            /** Defining a new Wj base on average point  */
            newW.add(avgPoint(clustersTemp_))
        }

        this.W_ = newW
        notifyObs()
        this.W_
    }

}
