package com.ilianazz.kmeans
package Views

import java.awt.Font
import javax.swing.JTextArea

class TextArea(text : String, rows : Int = 1, columns :Int = 1) extends JTextArea(text, rows, columns) with ColorsConstants {
    setFont(new Font("Poppins", Font.PLAIN, 25))
    setBackground(BLUE_DARK)
    setForeground(YELLOW)

}
