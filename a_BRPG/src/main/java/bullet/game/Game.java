package bullet.game;

import bullet.game.command.Command;
import bullet.game.command.Parser;
import bullet.game.event.EventSource;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    public static final ArrayList<Player> players = new ArrayList<>();
    public static final ArrayList<Player> alive = new ArrayList<>();
    public static final ArrayList<Player> ranking = new ArrayList<>();
    public static final Gun gun = new Gun();
    public static final EventSource gameEvent = new EventSource();
    public static GameState state = GameState.PREPARING;
    public static Player round;

    public static int join(Player player) {
        if (state.equals(GameState.PREPARING)) {
            players.add(player);
            gameEvent.addListener(player.listener);
            return players.size() - 1;
        } else {
            return -1;
        }
    }

    @Command(name = "init")
    public static void init() {
        for (Player player : players) {
            player.alive = true;
            player.health = Player.INITIAL_HEALTH;
        }
        alive.clear();
        alive.addAll(players);
        ranking.clear();
        loadGun();
        gameEvent.trigger(new JSONObject(new HashMap<>() {{
            put("type", GameEvent.INIT);
        }}));
    }

    @Command(name = "start_game")
    public static void startGame() {
        if (!state.equals(GameState.PREPARING)) return;
        if (players.size() > 0) {
            state = GameState.ONGOING;
            init();
            startRound(alive.get(0));
            gameEvent.trigger(new JSONObject(new HashMap<>() {{
                put("type", GameEvent.START_GAME);
            }}));
        } else {
            endGame();
        }
    }

    @Command(name = "end_game")
    public static void endGame() {
        state = GameState.COMPLETED;
        gameEvent.trigger(new JSONObject(new HashMap<>() {{
            put("type", GameEvent.END_GAME);
            put("ranking", ranking);
        }}));
    }

    @Command(name = "start_round")
    public static void startRound(Player player) {
        round = player;
        if (!player.alive) endRound();
        gameEvent.trigger(new JSONObject(new HashMap<>() {{
            put("type", GameEvent.START_ROUND);
            put("round", round);
        }}));
    }

    @Command(name = "end_round")
    public static void endRound() {
        if (gun.isEmpty()) updateGame();
        gameEvent.trigger(new JSONObject(new HashMap<>() {{
            put("type", GameEvent.END_ROUND);
            put("round", round);
        }}));
        startRound(players.get((round.seat + 1) % players.size()));
    }

    @Command(name = "update_game")
    public static void updateGame() {
        loadGun();
        dealItems();
    }

    @Command(name = "deal_items")
    public static void dealItems() {
        gameEvent.trigger(new JSONObject(new HashMap<>() {{
            put("type", GameEvent.DEAL_ITEMS);
        }}));
    }

    @Command(name = "load_gun")
    public static void loadGun() {
        int live = Gun.LIVE_BULLET_COUNT, blank = Gun.BLANK_BULLET_COUNT;
        gun.load(live, blank);
        gameEvent.trigger(new JSONObject(new HashMap<>() {{
            put("type", GameEvent.LOAD_GUN);
            put("live", live);
            put("blank", blank);
        }}));
    }

    @Command(name = "eliminate")
    public static void eliminate(Player player) {
        player.alive = false;
        alive.remove(player);
        ranking.add(player);
        gameEvent.trigger(new JSONObject(new HashMap<>() {{
            put("type", GameEvent.ELIMINATE);
            put("player", player);
        }}));
        if (alive.isEmpty()) {
            endGame();
        }
    }

    @Command(name = "shoot")
    public static void shoot(Player player) {
        if (player == null) return;
        if (!player.alive) return;
        Bullet bullet = gun.shoot();
        if (bullet.type.equals(Bullet.BulletType.LIVE)) {
            player.health -= bullet.damage;
            if (player.health <= 0) {
                eliminate(player);
            }
        }
        if (!player.equals(round) || bullet.type.equals(Bullet.BulletType.LIVE)) {
            endRound();
        }
        gameEvent.trigger(new JSONObject(new HashMap<>() {{
            put("type", GameEvent.SHOOT);
            put("player", player);
            put("bullet", bullet);
        }}));
        if (gun.isEmpty()) {
            endRound();
        }
    }

    public enum GameState {
        PREPARING, ONGOING, COMPLETED
    }

    public enum GameEvent {
        INIT, START_GAME, END_GAME, START_ROUND, END_ROUND, UPDATE_GAME, DEAL_ITEMS, LOAD_GUN, ELIMINATE, SHOOT;

        @Parser(type = GameEvent.class)
        public static GameEvent parse(String s) {
            return GameEvent.valueOf(s);
        }
    }
}
