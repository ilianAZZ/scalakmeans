package com.ilianazz.kmeans
package Controller

import Model.{Model, Observer}
import Views.View

trait Controller extends Observer {
    def setView(v: View)

    def model(): Model

    def action(zone: Int, action: Int)

    def importFile(filepath: String, delimiter: String = ";"): Array[Array[Double]]

}
