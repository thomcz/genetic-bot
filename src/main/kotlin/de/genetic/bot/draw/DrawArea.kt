package de.genetic.bot.draw

import de.genetic.bot.simulation.SimulationState

import java.awt.*

import javax.swing.JPanel


class DrawArea(internal var width: Int, internal var height: Int) : JPanel() {
    private var state: SimulationState? = null

    override fun paintComponent(g: Graphics) {
        g.color = Color.white
        g.fillRect(0, 0, width, height)

        if (state != null) {
            g.color = Color.blue
            g.fillOval(state!!.player[0] * width / state!!.width - 10, state!!.player[1] * height / state!!.height - 10, 20, 20)
            g.color = Color.red
            g.drawString("HP: " + state!!.player[2], state!!.player[0] * width / state!!.width - 8, state!!.player[1] * height / state!!.height + 4)

            for (bomb in state!!.bombs) {
                if (bomb[2] == 0) {
                    g.color = Color.orange
                    g.fillOval(bomb[0] * width / state!!.width - 500 * height / state!!.height, bomb[1] * height / state!!.height - 500 * height / state!!.height, 1000 * height / state!!.height, 1000 * height / state!!.height)
                }
                g.color = Color.black
                g.fillOval(bomb[0] * width / state!!.width - 10, bomb[1] * height / state!!.height - 10, 20, 20)
                g.color = Color.orange
                g.drawString("" + bomb[2], bomb[0] * width / state!!.width - 3, bomb[1] * height / state!!.height + 4)
            }

            for (zombie in state!!.zombies) {
                g.color = Color.green
                g.fillOval(zombie[0] * width / state!!.width - 10, zombie[1] * height / state!!.height - 10, 20, 20)
            }

            g.color = Color.black
            g.drawString("Score: " + state!!.score, 50, 50)

            g.color = Color.red
            g.drawString("HP: " + state!!.player[2], 500, 50)

        }
    }

    fun updateSimulationState(state: SimulationState) {
        this.state = state
        repaint()
    }

}
