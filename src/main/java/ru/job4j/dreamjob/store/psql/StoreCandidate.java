package ru.job4j.dreamjob.store.psql;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.psql.PsqlPoolConnect;
import ru.job4j.dreamjob.store.Store;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StoreCandidate implements Store<Candidate> {
    private static final class Lazy {
        private static final Store<Candidate> INST = new StoreCandidate();
    }

    public static Store<Candidate> instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Candidate> getAll() {
        List<Candidate> candidates = new ArrayList<>();
//        ConslLog.log("str1");
        var sql = "SELECT * FROM candidate";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {
//            ConslLog.log("str2");
            try (ResultSet it = prepStat.executeQuery()) {
//                ConslLog.log("str3");
//                ConslLog.log("before while");
//                int i = 0;
                while (it.next()) {
//                    ConslLog.log("loop", i);
//                    ConslLog.log("id", it.getInt("id"));
//                    ConslLog.log("name", it.getString("name"));
//                    ConslLog.log("img_id", it.getInt("img_id"));
//                    ConslLog.log("city_id", it.getInt("city_id"));
                    candidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("img_id"),
                            it.getInt("city_id")
                    ));
//                    ConslLog.log("loop - finish", i++);
                }
            }
//            ConslLog.log("str4 - finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private void create(Candidate candidate) {
        var sql = "INSERT INTO candidate(name, img_id, city_id) VALUES (?, ?, ?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {

//            ConslLog.log("IMPORTANT imgId", candidate.getImgId());
//            ConslLog.log("IMPORTANT name", candidate.getName());

            prepStat.setString(1, candidate.getName());
            prepStat.setInt(2, candidate.getImgId());
            prepStat.setInt(3, candidate.getCityId());
            prepStat.execute();
            try (ResultSet id = prepStat.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(Candidate candidate) {
        var sql = "UPDATE candidate SET name=(?), img_id=(?), city_id=(?) WHERE id=(?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {
            prepStat.setString(1, candidate.getName());
            prepStat.setInt(2, candidate.getImgId());
            prepStat.setInt(3, candidate.getCityId());
            prepStat.setInt(4, candidate.getId());
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Candidate getById(int id) {
        Candidate rsl = new Candidate(0, "", 0, 0);
        var sql = "SELECT * FROM candidate WHERE id=(?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {
            prepStat.setInt(1, id);
            ResultSet resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                rsl.setId(resultSet.getInt("id"));
                rsl.setName(resultSet.getString("name"));
                rsl.setImgId(resultSet.getInt("img_id"));
                rsl.setCityId(resultSet.getInt("city_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public void deleteById(int id) {
        var sql = "DELETE FROM candidate WHERE id=(?)";
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
