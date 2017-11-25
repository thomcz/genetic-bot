package de.genetic.bot.simulation

import java.awt.Point

class Bomb(position: Point) {
    val position : Point = position.clone() as Point
    var countdown = 10
    private val radius = 500
    private val damage = 50
    var isExploded = false

    fun tick() {
        countdown--
        if (countdown <=0) {
            isExploded = true
        }
    }

    fun explode(player: Player, zombies:ArrayList<Zombie>) {
        isExploded = true
        if (inRadius(player.position)) {
            player.damage(damage)
        }

        zombies
                .filter { inRadius(it.position) }
                .forEach { it.kill() }
    }

    private fun inRadius(position: Point) : Boolean {
        return (this.position.distance(position) < radius)
    }

    override fun toString(): String {
        return String.format("%d %d %d\n", position.x, position.y, countdown)
    }

}