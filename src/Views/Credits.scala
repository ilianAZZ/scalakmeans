package com.ilianazz.kmeans
package Views

import Controller.Controller

import java.awt.{BorderLayout, GridLayout}
import javax.swing.{BoxLayout, JPanel}

class Credits(controller: Controller) extends JPanel with ColorsConstants {
    setBackground(BLUE)
    setLayout(new BorderLayout())
    add(new Button("Go home", controller, 0, 1), BorderLayout.SOUTH)
    add(new Panel("Credits") {{
        setLayout(new GridLayout(5,2))
        add(new Label("The project", 25, align = 2))
        add(new Label("This project was created as part of my DUT's 4th semester. The k-means algorithm was explained by my Scala teacher.",20, align = 2))

        add(new Label("The code", 25, align = 2))
        add(new Label("This project (k-means algorithm and MVC interface) was entirely coded by Ilian Azz.", 20, align = 2))
    }}, BorderLayout.CENTER)

}
