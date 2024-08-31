package test;

import bullet.game.Account;
import bullet.game.Game;
import bullet.game.command.Commands;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 4; i++) {
            UUID id = UUID.randomUUID();
            Commands.invoke("login", id, "" + i);
            Commands.invoke("join", Account.find(id));
        }
        Commands.invoke("start_game");
        while (true) {
            Commands.invokeByString(new Scanner(System.in).nextLine());
        }
    }
}
