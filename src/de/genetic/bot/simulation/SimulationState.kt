package de.genetic.bot.simulation

import java.util.ArrayList
import java.util.Scanner

class SimulationState {
    var width: Int = 0
    var height: Int = 0

    var player: IntArray
    var zombies = ArrayList<IntArray>()
    var bombs = ArrayList<IntArray>()
    var score = 0

    internal constructor(map: Map, score: Int) {
        this.width = map.width
        this.height = map.height

        this.player = IntArray(3)
        player[0] = map.player.position.x
        player[1] = map.player.position.y
        player[2] = map.player.health

        for (z in map.zombies) {
            val zombie = IntArray(2)
            zombie[0] = z.position.x
            zombie[1] = z.position.y
            zombies.add(zombie)
        }

        for (b in map.bombs) {
            val bomb = IntArray(3)
            bomb[0] = b.position.x
            bomb[1] = b.position.y
            bomb[2] = b.countdown
            bombs.add(bomb)
        }

        this.score = score
    }

    constructor(state: String, width: Int, height: Int) {
        this.width = width
        this.height = height

        val scanner = Scanner(state)
        var line = scanner.nextLine()
        val player = stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        this.player = player

        line = scanner.nextLine()
        val zombieCount = stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        for (i in 0..zombieCount[0] - 1) {
            line = scanner.nextLine()
            val zombie = stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            zombies.add(zombie)
        }

        line = scanner.nextLine()
        val bombCount = stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        for (i in 0..bombCount[0] - 1) {
            line = scanner.nextLine()
            val bomb = stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            bombs.add(bomb)
        }

        line = scanner.nextLine()
        val score = stringToInt(line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        this.score = score[0]

        scanner.close()
    }

    companion object {

        fun stringToInt(s: Array<String>): IntArray {
            val converted = IntArray(s.size)

            for (i in s.indices) {
                converted[i] = Integer.parseInt(s[i])
            }

            return converted
        }
    }
}
