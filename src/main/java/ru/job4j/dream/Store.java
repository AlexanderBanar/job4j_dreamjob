package ru.job4j.dream;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Collection<City> findAllCities();

    Collection<Candidate> findTodayCandidates();

    Collection<Post> findTodayPosts();

    void savePost(Post post);

    void saveCan(Candidate candidate);

    Post findPostById(int id);

    Candidate findCanById(int id);

    void saveUser(User user);

    User findByEmail(String email);
}
