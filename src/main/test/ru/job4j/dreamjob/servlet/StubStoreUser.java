package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.psql.StoreUser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StubStoreUser extends StoreUser {
    private final List<User> userList = new ArrayList<>();
    private int ids = 0;

    @Override
    public void save(User user) {
        var temp = find(userLocal -> userLocal.getId() == user.getId());
        if (temp.getId() == -1) {
            user.setId(this.ids++);
            userList.add(user);
        } else {
            userList.add(userList.indexOf(temp), user);
        }
    }


    @Override
    public User getByEmail(String email) {
        return find(user -> user.getEmail().equals(email));
    }

    public User getById(int id) {
        return find(user -> user.getId() == id);
    }

    public List<User> getAll() {
        return new ArrayList<>(userList);
    }

    private User find(Predicate<User> predicate) {
        for (var user : userList) {
            if (predicate.test(user)) {
                return user;
            }
        }
        return new User(-1, "error", "error", "error");
    }
}
