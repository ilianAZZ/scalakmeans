package com.ilianazz.kmeans
package Views

import Controller.Controller

import java.awt.event.{MouseAdapter, MouseEvent}
import java.awt.{GridLayout, GridBagLayout, Cursor, Desktop, Color }
import java.net.URI
import javax.swing.JPanel

class Home (controller: Controller) extends JPanel with ColorsConstants {
    setBackground(BLUE)
    setForeground(YELLOW)

    setLayout( new GridBagLayout() )
    add(new JPanel() {{

        setOpaque(false)

        setLayout(new GridLayout(6,1))
        add(new Views.Label("K-means Scala Project", 80))
        add(new JPanel() {{
            setOpaque(false)
            add(new Views.Label("By Ilian Azz - ", 30))
            add(new Views.Label("https://iazz.fr/", 30, Color.WHITE) {{
                addMouseListener(new MouseAdapter {
                    override def mouseClicked(e: MouseEvent): Unit = { openWebpage("https://iazz.fr/") }
                    override def mouseEntered(e: MouseEvent): Unit = { setText("<html><u>https://iazz.fr/</u></html>"); setCursor(new Cursor(Cursor.HAND_CURSOR)) }
                    override def mouseExited (e: MouseEvent): Unit = { setText("<html>https://iazz.fr/</html>");        setCursor(new Cursor(Cursor.DEFAULT_CURSOR)) }
                })
            }})
        }})
        add(new Views.Button("Start",   controller, 1, 0))
        add(new Views.Button("Configuration", controller, 1, 1))
        add(new Views.Button("Credits", controller, 1, 2))
        add(new Views.Button("Quit",    controller, 0, 0))
    }})


    def openWebpage(uri: String): Boolean = {
        val desktop = if (Desktop.isDesktopSupported) Desktop.getDesktop
        else null
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) try {
            desktop.browse(new URI(uri))
            return true
        } catch {
            case e: Exception =>
                e.printStackTrace()
        }
        false
    }
}
