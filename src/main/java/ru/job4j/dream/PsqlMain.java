package ru.job4j.dream;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        System.out.println(store.findByEmail("s595659@mail.ru").getPassword());
    }
}
