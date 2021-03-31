package com.ilianazz.kmeans
package Views

import java.awt.Font
import javax.swing.border.EmptyBorder
import javax.swing.{JSpinner, SpinnerNumberModel}

class Spinner(spinnerNumberModel: SpinnerNumberModel) extends JSpinner(spinnerNumberModel) with ColorsConstants {


    setFont(new Font("Poppins", Font.PLAIN, 25))
    getEditor.getComponent(0).setBackground(BLUE_DARK)
    getEditor.getComponent(0).setForeground(YELLOW)
    setBorder(null)
}
