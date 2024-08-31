package bullet.game;

import bullet.game.command.Command;
import bullet.game.command.Parser;
import bullet.game.event.EventListener;
import bullet.game.event.EventSource;

import java.util.Objects;

public class Player {
    public static final int INITIAL_HEALTH = 6;
    public final Account account;
    public final int seat;
    public boolean alive = true;
    public int health;
    public final EventSource playerEvent = new EventSource();
    public final EventListener listener = System.out::println;

    private Player(Account account) {
        this.account = account;
        this.seat = Game.join(this);
    }

    @Command(name = "join")
    public static void join(Account account) {
        new Player(account);
    }

    @Parser(type = Player.class)
    public static Player parse(String source) {
        try {
            return Game.players.get(Integer.parseInt(source));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "account=" + account +
                ", seat=" + seat +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return seat == player.seat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seat);
    }
}
