package ru.job4j.dreamjob.store.psql;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.psql_db_connect.PsqlConnect;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlPostStore {

    public static Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = prepStat.executeQuery()) {
                while (it.next()) {
                    posts.add(
                            new Post(it.getInt("id"),
                                    it.getString("name"))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    private static void create(Post post) {
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("INSERT INTO post(name) VALUES (?)")
        ) {
            prepStat.setString(1, post.getName());
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void update(Post postUpd) {
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("UPDATE post SET name=(?) WHERE id=(?)")
        ) {
            prepStat.setString(1, postUpd.getName());
            prepStat.setInt(2, postUpd.getId());
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Post findByIdPost(int id) {
        Post rsl = new Post(0, "");
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM Post where id=(?)")
        ) {
            prepStat.setInt(1, id);
            ResultSet resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                rsl.setId(resultSet.getInt("id"));
                rsl.setName(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
