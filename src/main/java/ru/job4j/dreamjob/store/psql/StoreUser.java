package ru.job4j.dreamjob.store.psql;

import ru.job4j.dreamjob.ahelptools.ConslLog;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.psql.PsqlPoolConnect;
import ru.job4j.dreamjob.store.Store;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StoreUser implements Store<User> {
    private static final class Lazy {
        private static final StoreUser INST = new StoreUser();
    }

    public static StoreUser instOf() {
        return StoreUser.Lazy.INST;
    }

    @Override
    public Collection<User> getAll() {
        List<User> users = new ArrayList<>();
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM \"user\"")
        ) {
            try (ResultSet it = prepStat.executeQuery()) {
                while (it.next()) {
                    users.add(new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User user) {
        ConslLog.log("method: save() id=", user.getId());
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    public User getByEmail(String email) {
        User rsl = new User(0, "", "", "");
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM \"user\" WHERE email=(?)")
        ) {
            prepStat.setString(1, email);
            ResultSet resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                rsl.setId(resultSet.getInt("id"));
                rsl.setName(resultSet.getString("name"));
                rsl.setEmail(resultSet.getString("email"));
                rsl.setPassword(resultSet.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public void deleteByEmail(String email) {
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("DELETE FROM \"user\" WHERE email=(?)")
        ) {
            prepStat.setString(1, email);
            prepStat.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void create(User user) {
        ConslLog.log("method: create() - START");
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("INSERT INTO \"user\" "
                        + "(name, email, password) VALUES (?, ?, ?)")
        ) {
            ConslLog.log("method: create() id=", user.getId());
            ConslLog.log("method: create() name=", user.getName());
            ConslLog.log("method: create() email=", user.getEmail());
            ConslLog.log("method: create() password=", user.getPassword());

            prepStat.setString(1, user.getName());
            prepStat.setString(2, user.getEmail());
            prepStat.setString(3, user.getPassword());
            ConslLog.log("before exe");
            prepStat.execute();

            try (ResultSet id = prepStat.getGeneratedKeys()) {
                ConslLog.log("before if");
                if (id.next()) {
                    ConslLog.log("in if");
                    user.setId(id.getInt(1));
                }
            }
            ConslLog.log("method: create() - FINISH");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(User user) {
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("UPDATE \"user\" "
                        + "SET name=(?), email=(?), password=(?) WHERE id=(?)")
        ) {
            prepStat.setString(1, user.getName());
            prepStat.setString(2, user.getEmail());
            prepStat.setString(3, user.getPassword());
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getById(int id) {
        throw new UnsupportedOperationException("Dont't realize this thing.");
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Dont't realize this thing.");
    }
}
