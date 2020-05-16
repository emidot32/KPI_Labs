package EngineeringSoftWare.labwork3;

public class Main {
    public static void main(String[] args) {
        Directory users = new Directory("Users", 14, 10, 2018);
        Directory user1 = new Directory("Illia", 15, 10, 2018);
        Directory user2 = new Directory("Vitaliy", 15, 10, 2018);
        File file1 = new File(123, "photo.png", 15, 10, 2018);
        File file2 = new File(232, "text.txt", 16, 10, 2018);
        File file3 = new File(231, "word.docs", 16, 10, 2018);
        File file4 = new File(321, "program.exe", 17, 10, 2018);
        users.add(file1);
        user1.add(file2);
        user1.add(file3);
        user2.add(file4);
        users.add(user1);
        users.add(user2);
        System.out.println("Method toString() for users: " + users);
        System.out.println("Method toString() for user1: " + user1);
        System.out.println("Method toString() for user2: " + user2);
        System.out.println("Method ls() for users:");
        users.ls();
        System.out.println("Method ls() for user1:");
        user1.ls();
        System.out.println("Method ls() for user2:");
        user2.ls();
    }
}
