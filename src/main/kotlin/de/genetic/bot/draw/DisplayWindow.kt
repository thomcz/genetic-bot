package de.genetic.bot.draw

import de.genetic.bot.simulation.SimulationState

import java.awt.HeadlessException
import javax.swing.JFrame


class DisplayWindow @Throws(HeadlessException::class)
constructor(internal var width: Int, internal var height: Int) : JFrame("Zombie Shooter") {
    private var drawArea: DrawArea

    init {

        setSize(600, 600)
        drawArea = DrawArea(600, 600)

        setLocationRelativeTo(null)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane = drawArea
        isVisible = true
    }

    fun drawSimulationState(state: String) {
        val s = SimulationState(state, width, height)
        drawArea.updateSimulationState(s)
    }

}
