package com.ilianazz.kmeans
package Views

import com.ilianazz.kmeans.Model.Model

import java.awt.{Color, Graphics}
import javax.swing.JPanel
import scala.math.BigDecimal.double2bigDecimal

class Graph(private val model : Model) extends JPanel {
    private final val MARGIN : Int = 50
    private final val GRADUATE_HEIGHT: Int=10

    private final val xMAX = this.model.xMax()
    private final val yMAX = this.model.yMax()
    private final val xMIN = this.model.xMin()
    private final val yMIN = this.model.yMin()

    override def paintComponent(g : Graphics) : Unit  = {
        val xScreen = this.getSize().width
        val yScreen = this.getSize().height

        /* screen clear */
        g.setColor(Color.WHITE)
        g.fillRect(0, 0, xScreen, yScreen)


        /* Drawing axes */
        g.setColor(Color.BLACK)
        g.drawLine(xScreen - MARGIN, yScreen - (MARGIN/2) ,xScreen - MARGIN, (MARGIN/2))
        g.drawLine(MARGIN/2, yScreen - (MARGIN/2) ,xScreen - (MARGIN), yScreen - (MARGIN/2))


        val stepX = (xMAX - xMIN) / 10
        val stepY = (yMAX - yMIN) / 10
        for (i <- 1 to 11) {
            val xPosAxisX = ((stepX*i - xMIN) / xMAX * (xScreen - MARGIN*2)).toInt + MARGIN
            g.drawLine(xPosAxisX, yScreen - MARGIN/2 + GRADUATE_HEIGHT, xPosAxisX, yScreen - MARGIN/2)
            g.drawString((stepX*i).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble.toString, xPosAxisX+3, yScreen- MARGIN/4)


            val yPosAxisY = yScreen - ((stepY*i - yMIN) / yMAX * (yScreen - MARGIN*2)).toInt + MARGIN
            g.drawLine(xScreen - MARGIN, yPosAxisY ,xScreen - MARGIN  + GRADUATE_HEIGHT , yPosAxisY)
            g.drawString((stepY*i).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble.toString, xScreen - MARGIN+3, yPosAxisY -3)
        }


        /* Drawing points and clusters */
        val pointSize = 1000/model.points().size()+5
        model.points().forEach(el => drawPoint(g, el.x, el.y , pointSize, el.color))
        model.W().forEach(el => drawPoint(g, el.x, el.y, 12))
    }

    def drawPoint(g : Graphics, x : Double, y : Double, width : Int = 10, color : Color = Color.BLACK) : Unit = {
        val xScreen = this.getSize().width
        val yScreen = this.getSize().height
        g.setColor(color)
        val xPos = ((x - xMIN) / xMAX * (xScreen - MARGIN*2)).toInt + MARGIN
        val yPos = yScreen - ((y - yMIN) / yMAX * (yScreen - MARGIN*2)).toInt - MARGIN
        g.fillRect(xPos, yPos, width, width)
    }
}
