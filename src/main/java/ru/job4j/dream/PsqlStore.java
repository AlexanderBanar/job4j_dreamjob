package ru.job4j.dream;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {
    private final BasicDataSource pool = new BasicDataSource();
    private static final Logger LOG = Logger.getLogger(PsqlStore.class);

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            LOG.error("DB properties are not properly configured or loaded", e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            LOG.error("Class.forName did not get jdbc driver", e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        LOG.info("PsqlStore was initialized");
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM dreamjob.public.post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt(1), it.getString(2)));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
                PreparedStatement ps = cn.prepareStatement("SELECT * FROM dreamjob.public.candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"),
                            it.getInt("cityId")));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return candidates;
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            createPost(post);
        } else {
            updatePost(post);
        }
    }

    private Post createPost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO dreamjob.public.post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return post;
    }

    private void updatePost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE dreamjob.public.post SET name = (?) WHERE id = (?)")
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
    }

    @Override
    public Post findPostById(int id) {
        Post searchedPost = null;
        try (Connection cn = pool.getConnection();
            PreparedStatement ps = cn.prepareStatement(
                    "SELECT * FROM dreamjob.public.post WHERE id = (?)", PreparedStatement.RETURN_GENERATED_KEYS
            )) {
            ps.setInt(1, id);
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    searchedPost = new Post(id, rs.getString(2));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return searchedPost;
    }

    @Override
    public void saveCan(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCan(candidate);
        } else {
            updateCan(candidate);
        }
    }

    private Candidate createCan(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO dreamjob.public.candidate(name) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return candidate;
    }

    private void updateCan(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE dreamjob.public.candidate SET name = (?) WHERE id = (?)")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
    }

    @Override
    public Candidate findCanById(int id) {
        Candidate searchedCandidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM dreamjob.public.candidate WHERE id = (?)")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    searchedCandidate = new Candidate(id, rs.getString("name"), rs.getInt("cityId"));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return searchedCandidate;
    }

    @Override
    public void saveUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO dreamjob.public.users (email, password) VALUES (?, ?)")
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.execute();
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        User searchedUser = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM dreamjob.public.users WHERE email = (?)")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    searchedUser = new User();
                    searchedUser.setEmail(email);
                    searchedUser.setPassword(rs.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return searchedUser;
    }

    @Override
    public Collection<City> findAllCities() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM dreamjob.public.cities")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return cities;
    }

    public List<Candidate> findTodayCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "select * from dreamjob.public.candidate where candidate.date > (current_timestamp - interval '24 hours')")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"),
                            it.getInt("cityId")));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return candidates;
    }

    public List<Post> findTodayPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "select * from dreamjob.public.post where post.date > (current_timestamp - interval '24 hours')")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("check db connection", e);
        }
        return posts;
    }
}
