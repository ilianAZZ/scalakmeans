package com.ilianazz.kmeans
package Views

import java.awt.{Color, Font}
import javax.swing.{JLabel, SwingConstants}
import javax.swing.border.EmptyBorder

class Label(text : String, font_size : Int = 15, color : Color = null, align : Int = SwingConstants.LEFT) extends JLabel("<html>" + text + "</html>", align) with ColorsConstants {
    setFont(new Font("Poppins", Font.PLAIN, font_size))
    setForeground(if (color == null) YELLOW else color)
    setOpaque(false)
    setBorder(new EmptyBorder(10, 10, 10, 10))
}
