package de.genetic.bot.simulation

import java.awt.Point


class Simulation(val width: Int, val height: Int) {
    val map:Map = Map(width, height)
    var placedBombs = 0
    var killScore = 0

    fun addPlayer(point: Point) {
        map.addPlayer(Player(point))
    }

    fun addZombies(points : List<Point>) {
        for (point in points) {
            map.addZombie(Zombie(point))
        }
    }
    fun update(placeBomb : Boolean) {
        if (placeBomb) {
            map.addBomb(Bomb(map.player!!.position))
            placedBombs++
        }
    }

    fun update(position : Point) {
        updatePlayer(position)
        update()
    }

    private fun update() {
        if (!isGameOver()) {
            updateBomb()
            updateZombies()
        }
    }
    private fun updatePlayer(position: Point) {
        if (map.isInMap(position)) {
            map.player!!.move(position)
        }
    }

    private fun isGameOver() : Boolean {
        return map.player!!.isDead || map.zombies.isEmpty()
    }

    private fun updateBomb() {
        for (bomb in map.bombs) {
            if (bomb.isExploded) {
                map.bombs.remove(bomb)
            }
        }
    }

    private fun updateZombies() {
        var killedZombies = 0
        for (zombie in map.zombies) {
            if (zombie.isDead) {
                map.zombies.remove(zombie)
                killedZombies++
            } else {
                zombie.move(map.player!!.position)
                if (zombie.position.equals(map.player!!.position)) {
                    zombie.hit(map.player!!)
                }
            }
        }
        killScore += killedZombies * (killedZombies * 100)
    }

    fun getScore(): Int {
        return map.player!!.health / 10 * (map.player!!.health * 10 - placedBombs * 20 + killScore)
    }

    fun getSimulationState(): SimulationState {
        return SimulationState(map, getScore())
    }

    fun getSimulationStateString(): String {
        val sb = StringBuilder()

        sb.append(map.player.toString())

        sb.append(map.zombies.size)
        sb.append("\n")
        for (z in map.zombies) {
            sb.append(z.toString())
        }

        sb.append(map.bombs.size)
        sb.append("\n")
        for (b in map.bombs) {
            sb.append(b.toString())
        }

        sb.append(getScore())
        sb.append("\n")

        return sb.toString()
    }
}