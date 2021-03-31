package com.ilianazz.kmeans
package Controller

import Model.{Kmeans, Model, Point}
import Views.View

import java.awt.event.{ActionEvent, ActionListener}
import java.util
import javax.swing.Timer
import scala.io.Source

class ControllerKmeans() extends Controller {
    private var view : View = _
    private var modelK : Model = _

    override def setView(v : View): Unit = { this.view = v }
    override def refresh(): Unit = {}
    override def model(): Model = { this.modelK }

    override def action(zone : Int, action :Int): Unit = {
        zone match {
            // 0 : global actions
            case 0 =>
                action match {
                    case 0 => view.destroy() // Quit
                    case 1 => view.goHome()  // Go Home
                    case _ => default()
                }

            // 1 : From home menu
            case 1 =>
                action match {
                    case 0 => initKmeans() // Go main
                    case 1 => view.goConfig()  // Go options
                    case 2 => view.goCredits()  // Go Credits
                    case _ => default()
                }


            // 2 : From home main
            case 2 =>
                action match {
                    case 0 => {val o: Map[String, String] = this.view.getOptions ; startKmeans(stepByStep=o("stepByStep").toBoolean, speed=o("speed").toInt) }  // Start
                    case 1 => reloadKmeans()  // Go options
                    case _ => default()
                }


            // 3 : From config menu
            case 3 =>
                action match {
                    case 0 => default()
                    case 1 => default()
                    case _ => default()
                }

            case _ => default()
        }
    }

    def parseDouble(s: String) = try { Some(s.toDouble) } catch { case _ => None }
    def parseInt(s: String) = try { Some(s.toInt) } catch { case _ => None }

    override def importFile(filepath: String, delimiter : String = ";"): Array[Array[Double]] = {
        try {
            val file = Source.fromFile(filepath)
            val lines: Array[Array[Double]] = file.getLines().filterNot(_.isEmpty).map { line =>
                line.split(delimiter).filter(e => parseDouble(e).isDefined).map(_.toDouble)
            }.toArray

            lines

        } catch {
            case e : Exception => println(e) ; Array()
        }

    }

    def startKmeans(i : Int = 1, speed : Int = 50, stepByStep : Boolean = true ): Unit = {
        if (i == 1) {reloadKmeans() }

        val wait: Int = (((100.0-speed)/100 * 1950) + 50).toInt

        val wait1 = if (stepByStep) wait/2 else 0
        val wait2 = if (stepByStep) wait/2 else wait

        val kmeans = this.modelK.W()
        val affectation :Array[Int] = this.modelK.coloratePoints()

        val t = new Timer(wait1, new ActionListener() {
            def actionPerformed(e : ActionEvent): Unit = {
                modelK.movePrototypes(affectation)
                if (!modelK.sameW(kmeans)) {
                    val t = new Timer(wait2, new ActionListener() {
                        def actionPerformed(e : ActionEvent): Unit = {
                            startKmeans(i+1, speed, stepByStep)
                        }
                    })
                    t.setRepeats(false)
                    t.start()
                } else {
                    println("Ended with " + i + " iterations")
                }

            }
        })
        t.setRepeats(false)
        t.start()

    }


    def reloadKmeans(): Unit = {
        initKmeans()
        this.view.restart()
    }

    def initKmeans() :Unit  = {
        val o: Map[String, String] = this.view.getOptions

        val file      = if (o("file")=="nullnull") "data.txt" else o("file")
        val delimiter = if (o("delimiter") == "")   ";"        else o("delimiter")
        val fromFile  = importFile(file, delimiter)
        val nbW       = if (parseInt(o("nbW")).isEmpty) 2 else o("nbW").toInt

        val attr1     = if (parseInt(o("attr1")).isEmpty ||  o("attr1").toInt > fromFile(0).length ) 0 else o("attr1").toInt
        val attr2     = if (parseInt(o("attr2")).isEmpty ||  o("attr2").toInt > fromFile(0).length ) 0 else o("attr2").toInt

        val stepByStep:Boolean= o("stepByStep").toBoolean
        val speed: Int     = o("speed").toInt


        val tmp = new Array[Point](Source.fromFile(file).getLines.size)
        var i : Int = 0
        var failed = 0
        for (line <- Source.fromFile(file).getLines) {
            val a = line.split(delimiter)
            try { tmp(i-failed) = new Point(a(attr1).toDouble, a(attr2).toDouble)
            } catch { case e: Exception => {failed+=1} }
            i = i + 1
        }
        val points = tmp.slice(0, i-failed)

        println(s"${i-failed} / $i points loaded ")

        this.modelK = new Kmeans(new util.ArrayList[Point]() {{ points.foreach(e => add(e)) }}, nbW)

        this.modelK.clearObs()
        this.modelK.addObs(this)
        this.modelK.addObs(view)

        this.view.model(this.modelK)
        this.view.goMain()

        this.modelK.init(errorsDisplayed = false)

    }
    def default() :Unit = println("Not implemented")
}
