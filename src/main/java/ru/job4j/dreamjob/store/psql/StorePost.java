package ru.job4j.dreamjob.store.psql;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.psql.PsqlPoolConnect;
import ru.job4j.dreamjob.store.Store;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StorePost implements Store<Post> {
    private static final class Lazy {
        private static final Store<Post> INST = new StorePost();
    }

    public static Store<Post> instOf() {
        return StorePost.Lazy.INST;
    }

    @Override
    public Collection<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        var sql = "SELECT * FROM post";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
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

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    private void create(Post post) {
        var sql = "INSERT INTO post(name) VALUES (?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {
            prepStat.setString(1, post.getName());
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(Post postUpd) {
        var sql = "UPDATE post SET name=(?) WHERE id=(?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {
            prepStat.setString(1, postUpd.getName());
            prepStat.setInt(2, postUpd.getId());
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post getById(int id) {
        Post rsl = new Post(0, "");
        var sql = "SELECT * FROM Post WHERE id=(?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
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

    @Override
    public void deleteById(int id) {
        var sql = "DELETE FROM post WHERE id=(?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {
            prepStat.setInt(1, id);
            prepStat.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
