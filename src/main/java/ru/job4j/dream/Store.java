package ru.job4j.dream;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CAN_ID = new AtomicInteger(6);

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "knows Java core",
                LocalDate.of(2021, Month.MARCH, 20)));
        posts.put(2, new Post(2, "Middle Java Job", "knows Java Servlets",
                LocalDate.of(2021, Month.APRIL, 17)));
        posts.put(3, new Post(3, "Senior Java Job", "knows everything...",
                LocalDate.of(2021, Month.FEBRUARY, 8)));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CAN_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    public Candidate findCanById(int id) {
        return candidates.get(id);
    }
}
