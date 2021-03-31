package com.ilianazz.kmeans
package Views

import Controller.Controller
import java.awt.event.{ActionEvent, ActionListener, MouseAdapter, MouseEvent}
import java.awt.{Color, Font, Insets}
import javax.swing.JButton
import javax.swing.border.LineBorder

class Button(text : String, controller : Controller, zone : Int, action : Int) extends JButton(text) with ColorsConstants {

    setBorder(new LineBorder(Color.BLACK))
    setMargin(new Insets(50, 50, 50, 50))
    setBackground(YELLOW)
    setForeground(BLUE)
    setFont(new Font("Poppins", Font.PLAIN, 40))

    addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = controller.action(zone, action)
    })

    addMouseListener(new MouseAdapter() {
        override def mouseEntered(e: MouseEvent): Unit = setBackground(YELLOW_DARK)
        override def mouseExited(e: MouseEvent): Unit = setBackground(YELLOW)
        override def mousePressed(e: MouseEvent): Unit = setBackground(YELLOW)
    })


}
