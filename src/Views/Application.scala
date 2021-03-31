package com.ilianazz.kmeans
package Views

import Controller.Controller
import Model.Model

import javax.swing.{ImageIcon, JFrame}

class Application(controller: Controller) extends JFrame("K-means - Ilian Azz") with View with ColorsConstants {
    private final val HOME    : Home =    new Home(controller)
    private var MAIN    : Main =    null
    private final val CREDITS : Credits = new Credits(controller)
    private final val CONFIG  : Config = new Config(controller)
    private var model : Model = _

    setIconImage(new ImageIcon("logo.png").getImage)
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    setSize(1000, 800)
    setBackground(BLUE)
    setForeground(YELLOW)
    setContentPane(HOME)
    setVisible(true)

    def model(m : Model): Unit = { this.model = m }
    def restart(): Unit = { this.MAIN = new Main(controller); this.MAIN.restart();setContentPane(MAIN); repaint(); revalidate() }
    def getOptions(): Map[String, String] = {this.CONFIG.getOptions()}
    def refresh() :Unit = { this.MAIN.refresh() ; repaint(); revalidate()}
    def destroy() :Unit = { setVisible(false); dispose()}
    def goHome()  :Unit = { setContentPane(HOME)   ; repaint(); revalidate()}
    def goMain()  :Unit = { if (this.MAIN == null) {this.MAIN = new Main(controller)} ;setContentPane(MAIN)   ; repaint(); revalidate()}
    def goConfig():Unit = { setContentPane(CONFIG) ; repaint(); revalidate()}
    def goCredits():Unit= { setContentPane(CREDITS); repaint(); revalidate()}
}
