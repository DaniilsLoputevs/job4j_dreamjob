package ru.job4j.dreamjob.store.psql;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.psql_db_connect.PsqlConnect;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlCandidateStore {

    public static Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = prepStat.executeQuery()) {
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

    public static void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private static Candidate create(Candidate candidate) {
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("INSERT INTO candidate(name) VALUES (?)")
        ) {
            prepStat.setString(1, candidate.getName());
            prepStat.execute();
            try (ResultSet id = prepStat.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private static void update(Candidate candidate) {
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("UPDATE candidate SET name=(?) WHERE id=(?)")
        ) {
            prepStat.setString(1, candidate.getName());
            prepStat.setInt(2, candidate.getId());
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Candidate findByIdCandidate(int id) {
        Candidate rsl = new Candidate(0, "");
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
