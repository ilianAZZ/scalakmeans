package com.ilianazz.kmeans
package Views

import Controller.Controller
import javax.swing.JPanel

class Credits(controller: Controller) extends JPanel {
    add(new Button("Go home", controller, 0, 1))
}
