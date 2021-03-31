package com.ilianazz.kmeans
package Views

import Model.Model

import Controller.Controller

import java.awt.{BorderLayout, GridLayout}
import javax.swing.{JCheckBox, JPanel}

class Main(controller: Controller ) extends JPanel {
    private final val MARGIN : Int = 20
    private final var GRAPH : Graph =  _

    private var model : Model = controller.model()
    setLayout(new BorderLayout())
    add(new Button("Go home", controller, 0, 1), BorderLayout.PAGE_START)
    initComposants()

    def initComposants() = {
        this.model = controller.model()
        this.GRAPH = new Graph(model)

        add(GRAPH, BorderLayout.CENTER)

        val bottom = new JPanel()
        bottom.setLayout(new GridLayout(2,2))
        bottom.add(new Button("Start K-means", controller, 2, 0))
        bottom.add(new Button("Reload", controller, 2, 1))

        new JCheckBox("Enable logging")
        add(bottom, BorderLayout.PAGE_END)
    }

    def restart():Unit = {
        this.model = controller.model()
        this.GRAPH = new Graph(this.model)
        refresh()
    }

    def refresh(): Unit = {

        GRAPH.repaint()
        GRAPH.revalidate()
    }


}
