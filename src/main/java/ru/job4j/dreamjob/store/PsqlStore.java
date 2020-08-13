package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.psql_db_connect.PsqlConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlStore implements Store {

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }


    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (var ps = PsqlConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
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
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
//        try (Connection cn = pool.getConnection();
        try (Connection cn = PsqlConnect.getPool().getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Post create(Post post) {
//        try (Connection cn = pool.getConnection();
        try (Connection cn = PsqlConnect.getPool().getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(name) VALUES (?)")
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void update(Post postUpd) {
//        try (Connection cn = pool.getConnection();
        try (Connection cn = PsqlConnect.getPool().getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET name=(?) WHERE id=(?)")
        ) {
            ps.setString(1, postUpd.getName());
            ps.setInt(2, postUpd.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Candidate create(Candidate candidate) {
//        try (var ps = pool.getConnection().prepareStatement(
        try (var ps = PsqlConnect.getPool().getConnection()
                .prepareStatement("INSERT INTO candidate(name) VALUES (?)")
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }
    private void update(Candidate candidate) {
//        try (Connection cn = pool.getConnection();
        try (Connection cn = PsqlConnect.getPool().getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidate SET name=(?) WHERE id=(?)")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post findByIdPost(int id) {
        Post rsl = new Post(0, "");
//        try (var prepStat = pool.getConnection().prepareStatement(
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

    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate rsl = new Candidate(0, "");
//        try (var prepStat = pool.getConnection().prepareStatement(
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM candidate where id=(?)")
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