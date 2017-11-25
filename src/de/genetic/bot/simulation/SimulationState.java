package de.genetic.bot.simulation;

import java.util.ArrayList;
import java.util.Scanner;

public class SimulationState {
    public int width;
    public int height;

    public int[] player;
    public ArrayList<int[]> zombies = new ArrayList<int[]>();
    public ArrayList<int[]> bombs= new ArrayList<int[]>();;
    public int score = 0;

    SimulationState(Map map,int score) {
        this.width = map.getWidth();
        this.height = map.getHeight();

        this.player = new int[3];
        player[0] = map.getPlayer().getPosition().x;
        player[1] = map.getPlayer().getPosition().y;
        player[2] = map.getPlayer().getHealth();

        for(Zombie z : map.getZombies()) {
            int[] zombie = new int[2];
            zombie[0] = z.getPosition().x;
            zombie[1] = z.getPosition().y;
            zombies.add(zombie);
        }

        for(Bomb b : map.getBombs()) {
            int[] bomb = new int[3];
            bomb[0] = b.getPosition().x;
            bomb[1] = b.getPosition().y;
            bomb[2] = b.getCountdown();
            bombs.add(bomb);
        }

        this.score = score;
    }

    public SimulationState(String state,int width, int height) {
        this.width = width;
        this.height = height;

        Scanner scanner = new Scanner(state);
        String line = "";

        line = scanner.nextLine();
        int[] player = stringToInt(line.split(" "));
        this.player = player;

        line = scanner.nextLine();
        int[] zombieCount = stringToInt(line.split(" "));
        for(int i = 0; i < zombieCount[0]; i++) {
            line = scanner.nextLine();
            int[] zombie = stringToInt(line.split(" "));
            zombies.add(zombie);
        }

        line = scanner.nextLine();
        int[] bombCount = stringToInt(line.split(" "));
        for(int i = 0; i < bombCount[0]; i++) {
            line = scanner.nextLine();
            int[] bomb = stringToInt(line.split(" "));
            bombs.add(bomb);
        }

        line = scanner.nextLine();
        int[] score = stringToInt(line.split(" "));
        this.score = score[0];

        scanner.close();
    }

    public static int[] stringToInt(String[] s) {
        int[] converted = new int[s.length];

        for(int i = 0; i < s.length; i++) {
            converted[i] = Integer.parseInt(s[i]);
        }

        return converted;
    }
}
