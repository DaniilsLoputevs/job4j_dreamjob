package ru.job4j.dreamjob.store.psql;

import ru.job4j.dreamjob.ahelptools.ConslLog;
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
        ConslLog.log("str1");
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM candidate")
        ) {
            ConslLog.log("str2");
            try (ResultSet it = prepStat.executeQuery()) {
                ConslLog.log("str3");
                ConslLog.log("before while");
                int i = 0;
                while (it.next()) {
                    ConslLog.log("loop", i);
                    ConslLog.log("id", it.getInt("id"));
                    ConslLog.log("name", it.getString("name"));
                    ConslLog.log("img_id", it.getInt("img_id"));
                    candidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("img_id")
                    ));
                    ConslLog.log("loop - finish", i++);
                }
            }
            ConslLog.log("str4 - finish");
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
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("INSERT INTO candidate(name, img_id) VALUES (?, ?)")
        ) {
            // img with id=0 is default img - else it won't work.
//            if (candidate.getImgId() < 0) {
//                candidate.setImgId(1);
//            }
            ConslLog.log("IMPORTANT imgId", candidate.getImgId());
            ConslLog.log("IMPORTANT name", candidate.getName());
            prepStat.setString(1, candidate.getName());
            prepStat.setInt(2, candidate.getImgId());
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
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("UPDATE candidate SET name=(?), img_id=(?) WHERE id=(?)")
        ) {
            prepStat.setString(1, candidate.getName());
            prepStat.setInt(2, candidate.getImgId());
            prepStat.setInt(3, candidate.getId());
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Candidate getById(int id) {
        Candidate rsl = new Candidate(0, "");
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM candidate WHERE id=(?)")
        ) {
            prepStat.setInt(1, id);
            ResultSet resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                rsl.setId(resultSet.getInt("id"));
                rsl.setName(resultSet.getString("name"));
                rsl.setImgId(resultSet.getInt("img_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public void deleteById(int id) {
        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
                .prepareStatement("DELETE FROM candidate WHERE id=(?)")
        ) {
            prepStat.setInt(1, id);
            prepStat.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
