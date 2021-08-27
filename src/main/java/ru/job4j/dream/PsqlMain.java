package ru.job4j.dream;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
//        for (Post post : store.findAllPosts()) {
//            System.out.println(post.getId() + " " + post.getName());
//        }
//        for (Candidate candidate : store.findAllCandidates()) {
//            System.out.println(candidate.getId() + " " + candidate.getName());
//        }
        System.out.println(store.findByEmail("test@mail.ru").getPassword());
        User user2 = new User();
        user2.setEmail("wr@mail.ru");
        user2.setPassword("psswrd");
        store.saveUser(user2);

    }
}
