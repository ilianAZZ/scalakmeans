package com.ilianazz.kmeans
package Views

import java.awt.{BorderLayout, GridLayout, LayoutManager}
import javax.swing.{JComponent, JPanel}

class Panel(title : String) extends JPanel {
    private val container = new JPanel() {
        setOpaque(false)
        setLayout(new GridLayout(5,2))
    }
    setOpaque(false)
    setLayout(new BorderLayout())
    add(new Views.Label(title, 40, align = 0), BorderLayout.NORTH)
    add(container, BorderLayout.CENTER)

    def add(component : JComponent): Unit = {
        this.container.add(component)
    }
    def layout(layout : LayoutManager ): Unit = {
        this.container.setLayout(layout)
    }
}
