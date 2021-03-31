package com.ilianazz.kmeans
package Views

import Model.{Model, Observer}

trait View extends Observer {
    def destroy()
    def goMain()
    def goHome()
    def goConfig()
    def goCredits()
    def model(m: Model)
    def getOptions: Map[String, String]
    def restart(): Unit
}
