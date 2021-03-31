package com.ilianazz.kmeans
package Model

import java.awt.Color
import java.util.ArrayList
import scala.math.{pow, sqrt}

class Point (private val x_ :Double, private val y_ :Double, private val color_ :Color = Color.BLUE) {
    private final val MARGIN : Double = 1/100

    def this(point_ :Point) = this(point_.x, point_.y, point_.color)
    def this(point_ :Point, color_ :Color) = this(point_.x, point_.y, color_)

    def x :Double = this.x_
    def y :Double = this.y_
    def color :Color = this.color_

    /** Return the euclidean distance between this and the parameter point  */
    def dist(p : Point) : Double = sqrt(pow(this.x - p.x, 2) + pow(this.y - p.y, 2))

    /** Return the index (between 0 et n-1) of the nearest point in all of the points in the array parameter */
    def nearestPoint(points : ArrayList[Point]) : Int = {
        var point = 0
        var dist  = this.dist(points.get(0))
        for (i <- 1 until points.size) {
            val distTemp = this.dist(points.get(i))
            if (distTemp < dist) {
                point = i
                dist = distTemp
            }
        }
        point
    }

    /** The distance average ith all points in the list */
    def avgDist(points : Array[Point]) : Double = {
        var s = 0.0
        for (p <- points) { s+= this.dist(p) }
        s/points.length
    }
    def ecartType(points : Array[Point]) : Double = {
        var sum = 0.0
        val avg = this.avgDist(points)

        for (point <- points) { sum += math.pow(this.dist(point) - avg, 2) }
        return math.sqrt(sum/points.size)
    }


    /** Redefining toString method */
    override def toString : String = s"(${this.x_} ; ${this.y_}) : ${this.color_}"

    /** Redefining equals method */
    override def equals(obj: Any): Boolean = {
        obj match {
            case obj: Point => {
                obj.isInstanceOf[Point] &&
                    (1 - MARGIN) <= (this.x_ / obj.x) && (this.x_ / obj.x) <= (1 + MARGIN) &&
                    (1 - MARGIN) <= (this.y_ / obj.y) && (this.y_ / obj.y) <= (1 + MARGIN)
            }
            case _ => false
        }
    }
}
