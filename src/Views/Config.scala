package com.ilianazz.kmeans

package Views


import Controller.Controller

import java.awt.event.{ActionEvent, ActionListener, MouseAdapter, MouseEvent}
import java.awt.{GridLayout, BorderLayout, FileDialog, Frame}
import javax.swing._
import scala.util.Random

class Config(controller: Controller) extends JPanel with ColorsConstants {

    setLayout(new BorderLayout())
    setBackground(BLUE)
    add(new JPanel() {{
        setOpaque(false)
        setLayout(new GridLayout(2,1))
        add(new Views.Button("Start K-means", controller, 1, 0))
        add(new Views.Button("Go home", controller, 0, 1))
    }}, BorderLayout.SOUTH)


    private val content = new JPanel() {{
        setOpaque(false)
        setLayout(new GridLayout(2,2))
    }}
    add(content, BorderLayout.CENTER)


    /* top left */
    private val file = new Views.Label("No file selected", align=0)
    private val fd = new FileDialog(null.asInstanceOf[Frame])
    private val delimiter = new TextArea(";", 1, 10)
    private val attr1 = new Views.Spinner(new SpinnerNumberModel(0, 0, 1000, 1))
    private val attr2 = new Views.Spinner(new SpinnerNumberModel(1, 0, 1000, 1))

    /* top right */
    private val j = new Spinner(new SpinnerNumberModel(2, 2, 1000, 1))
    private val check = new JCheckBox("Random number of clusters ?") {{
        setOpaque(false)
        setForeground(YELLOW)
        setSize(20,20)
        /*
        addActionListener(new ActionListener { override def actionPerformed(e: ActionEvent): Unit = {
        if (isSelected) { j.disable(); j.setBackground(new Color(200, 200, 200)); controller.action(3, 0) }
        else            {j.enable()  ; j.setBackground(new Color(255, 255, 255)); controller.action(3, 1)}

    } } )*/ }}

    /* bottom left */
    private val stepbystep = new JCheckBox("Step by step") {{
        setOpaque(false)
        setForeground(YELLOW)
        setSize(20,20)
        setEnabled(true)
    }}
    private val speed = new JSlider(0, 100, 10) {{
        setValue(50)
        setOpaque(false)
        setPaintTrack(true)
        setPaintTicks(true)
        setPaintLabels(true)
        setMajorTickSpacing(10)
        setMinorTickSpacing(10)
        setForeground(YELLOW)
    }}

    topLeftPane()
    topRightPane()
    bottomLeftPane()
    bottomRightPane()


    def topLeftPane():Unit = {
        content.add(new Panel("Data") {{
            add(new JButton("Import file") {{
                setBackground(YELLOW)
                setForeground(BLUE)

                addMouseListener(new MouseAdapter() {
                    override def mouseEntered(e: MouseEvent): Unit = setBackground(YELLOW_DARK)
                    override def mouseExited(e: MouseEvent): Unit = setBackground(YELLOW)
                    override def mousePressed(e: MouseEvent): Unit = setBackground(YELLOW)
                })

                addActionListener(new ActionListener {
                    override def actionPerformed(e: ActionEvent): Unit = {

                        fd.setVisible(true)
                        if (fd.getFile != null) {
                            val array = controller.importFile(fd.getDirectory + fd.getFile, delimiter.getText)
                            if (array.length > 0) {
                                val nbL = array.length
                                val nbA = array(0).length
                                if (nbA < 2 || nbL < 10) { file.setText(fd.getFile + s" : $nbL lines found with $nbA attributes.<br> Not enough") ; fd.setDirectory(null) ;fd.setFile(null)
                                } else { file.setText(fd.getFile + s" : $nbL lines <br>found with $nbA attributes") }

                            } else {
                                file.setText("<u>" + fd.getFile + "</u> : Data not found, maybe change delimiter and reselect file.")
                                fd.setDirectory(null)
                                fd.setFile(null)
                            }
                        }
                    }
                }) }})
            add(file)
            add(new Views.Label("Delimiter", align=SwingConstants.RIGHT))
            add(delimiter)
            add(new Views.Label("First attribute column<br>(column index, start at 0)", align=SwingConstants.RIGHT))
            add(attr1)
            add(new Views.Label("Second attribute column<br>(column index, start at 0)", align=SwingConstants.RIGHT))
            add(attr2)
        }})


    }



    def topRightPane():Unit = {
        content.add(new Panel("Clusters") {{
            add(check)
            add(new Label("If no, how many :"))
            add(j)
        }})
    }

    def bottomLeftPane():Unit = {
        content.add(new Panel("Visualisation") {{
            add(stepbystep)
            add(new Label("Step speed"))
            add(speed)
        }})


    }
    def bottomRightPane():Unit = {
        content.add(new Panel("") {{

        }})
    }


    def getOptions(): Map[String, String] = {
        var options : Map[String, String] = Map()

        /* nb of clusters */
        if (check.isSelected) {
            options += ("nbW" -> (Random.nextInt(18)+2).toString)
        } else {
            try { options += ("nbW" -> (j.getValue).toString)
            } catch { case e : Exception => options += ("nbW" -> (Random.nextInt(18)+2).toString) }
        }

        /*File and columns*/
        options += ("file" -> (fd.getDirectory + fd.getFile))

        /* Delimiter */
        options += ("delimiter" -> delimiter.getText())

        /* Attributes */
        options += ("attr1" -> attr1.getValue.toString)
        options += ("attr2" -> attr2.getValue.toString)

        /*step by step and speed*/
        options += ("stepByStep" -> stepbystep.isSelected.toString)
        options += ("speed" -> speed.getValue.toString)

        options
    }
}
