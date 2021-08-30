package ru.job4j.dream;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CAN_ID = new AtomicInteger(6);

    private MemStore() {
//        posts.put(1, new Post(1, "Junior Java Job"));
//        posts.put(2, new Post(2, "Middle Java Job"));
//        posts.put(3, new Post(3, "Senior Java Job"));
//        candidates.put(1, new Candidate(1, "Junior Java"));
//        candidates.put(2, new Candidate(2, "Middle Java"));
//        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void saveCan(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CAN_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public Post findPostById(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findCanById(int id) {
        return candidates.get(id);
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
