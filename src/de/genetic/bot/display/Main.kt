package de.genetic.bot.display

import de.genetic.bot.bot.RandomBot
import de.genetic.bot.draw.DisplayWindow
import de.genetic.bot.simulation.Simulation
import de.genetic.bot.simulation.SimulationState

import java.awt.Point
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.PrintWriter
import java.nio.file.Paths
import java.util.ArrayList
import java.util.Scanner

object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        val path = "res"
        val initPath = Paths.get(path, "init\\init.sim")
        val inPath = Paths.get(path, "input\\player.simin")
        val outPath = Paths.get(path, "output\\state.simout")

        val sim = initSim(initPath.toString())

        val simString = runSim(sim, inPath.toString())

        try {
            val file = File(outPath.toString())
            val writer = PrintWriter(file.absolutePath, "UTF-8")
            println(file.absolutePath)
            writer.println(simString)
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        println(simString)

        displaySim(simString)
    }

    private fun displaySim(simString: String?) {
        val scanner = Scanner(simString!!)
        var line = scanner.nextLine()
        val size = SimulationState.stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val window = DisplayWindow(size[0], size[1])

        var simState = StringBuilder()
        while (scanner.hasNextLine()) {
            line = scanner.nextLine()
            if (line != "#") {
                simState.append(line + "\n")
            } else {
                window.drawSimulationState(simState.toString())
                simState = StringBuilder()

                try {
                    Thread.sleep(100)
                } catch (e1: InterruptedException) {
                    e1.printStackTrace()
                }

            }
        }

        scanner.close()
    }

    private fun runSim(sim: Simulation?, playerInputPath: String): String? {
        try {
            val file = File(playerInputPath)
            val writer = PrintWriter(file.absolutePath, "UTF-8")
            println(file.absolutePath)

            val simSB = StringBuilder()
            simSB.append(sim!!.getSimulationState().width.toString() + " " + sim.getSimulationState().height + "\n")

            println("Loop")
            var steps = 0
            while (steps < 200) {
                val state = sim.getSimulationStateString()
                simSB.append(state)
                simSB.append("#\n")

                if (!runStep(sim, writer)) {
                    break
                }

                steps++
            }

            writer.close()
            return simSB.toString()

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    private fun initSim(simFilePath: String): Simulation? {
        println("Init")

        val file = File(simFilePath)
        println(file.absolutePath)

        val input: Scanner
        try {
            input = Scanner(file)

            var line = input.nextLine()
            val size = SimulationState.stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            val sim = Simulation(size[0], size[1])

            line = input.nextLine()
            val playerPos = SimulationState.stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            val player = Point(playerPos[0], playerPos[1])
            sim.addPlayer(player)

            val zombies = ArrayList<Point>()
            while (input.hasNextLine()) {
                line = input.nextLine()
                val zombiePos = SimulationState.stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                zombies.add(Point(zombiePos[0], zombiePos[1]))
            }
            sim.addZombies(zombies)

            input.close()
            return sim

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }

    }

    internal var bot = RandomBot(2500, 2500, 42)

    private fun runStep(sim: Simulation, out: PrintWriter) : Boolean {
        val simState = sim.getSimulationState()
        bot.update(simState)

        if (bot.bomb) {
            println("true")
            out.println("true")
            sim.update(true)
        } else {
            val x = bot.x
            val y = bot.y

            println(x.toString() + " " + y)
            out.println(x.toString() + " " + y)
            if (!sim.update(Point(x, y))) {
                return false
            }
        }
        return true
    }
}
