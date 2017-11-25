package de.genetic.bot.bot

import de.genetic.bot.simulation.SimulationState
import java.util.Random



class RandomBot(var x: Int, var y: Int, var seed: Int) {
    var rand = Random(seed.toLong())
    var bomb = false


    fun update(simState: SimulationState) {

        if (rand.nextDouble() > 0.9) {
            bomb = true
        } else {
            bomb = false
            if (x == simState.player[0] && y == simState.player[1]) {
                x = rand.nextInt(5000)
                y = rand.nextInt(5000)
            }
        }

    }
}