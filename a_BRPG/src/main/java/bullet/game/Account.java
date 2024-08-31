package bullet.game;

import bullet.game.command.Command;
import bullet.game.command.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record Account(UUID id, String name) {
    public static final Map<UUID, Account> ACCOUNTS = new HashMap<>();

    public Account(UUID id, String name) {
        this.id = id;
        this.name = name;
        ACCOUNTS.put(id, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Command(name = "login")
    public static void login(UUID id, String name) {
        if (ACCOUNTS.containsKey(id)) return;
        new Account(id, name);
    }

    @Parser(type = Account.class)
    public static Account find(String id) {
        return find(UUID.fromString(id));
    }

    public static Account find(UUID id) {
        return ACCOUNTS.get(id);
    }
}
