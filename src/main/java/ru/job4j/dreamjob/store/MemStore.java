package ru.job4j.dreamjob.store;


import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
public class MemStore {
    private static final MemStore INST = new MemStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", "desc 1", "30.07.20."));
        posts.put(2, new Post(2, "Middle Java Job", "desc 2", "30.07.20."));
        posts.put(3, new Post(3, "Senior Java Job", "desc 3", "30.07.20."));
        candidates.put(1, new Candidate(1, "Junior Candidate"));
        candidates.put(2, new Candidate(2, "Middle Candidate"));
        candidates.put(3, new Candidate(3, "Senior Candidate"));
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public Post findByIdPost(int id) {
        return posts.get(id);
    }
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public void save(Candidate candidate) {
        candidate.setId(CANDIDATE_ID.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }
}
