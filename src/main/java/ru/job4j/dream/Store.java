package ru.job4j.dream;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

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

    public void put(Post post) {
        posts.put(post.getId(), post);
    }
}
