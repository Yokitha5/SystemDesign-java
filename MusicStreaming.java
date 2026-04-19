import java.util.*;

// ENUM
enum SubscriptionType {
    FREE, PREMIUM
}

// USER
class User {
    private int id;
    private String name, email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
}

// SONG
class Song {
    private int id;
    private String name, artist;

    public Song(int id, String name, String artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    public String getName() { return name; }

    public void play() {
        System.out.println("Now Playing: " + name + " by " + artist);
    }
}

// PLAYLIST
class Playlist {
    private int id;
    private String name;
    private List<Song> songs;

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void showSongs() {
        System.out.println("\nPlaylist: " + name);
        for (int i = 0; i < songs.size(); i++) {
            System.out.println((i + 1) + ". " + songs.get(i).getName());
        }
    }

    public Song getSong(int index) {
        return songs.get(index);
    }
}

// ABSTRACT PAYMENT
abstract class Payment {
    abstract void pay(int amount);
}

// UPI
class UPI extends Payment {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " via UPI");
    }
}

// NETBANKING
class NetBanking extends Payment {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " via NetBanking");
    }
}

// SUBSCRIPTION
class Subscription {
    private SubscriptionType type;

    public Subscription(SubscriptionType type) {
        this.type = type;
    }

    public SubscriptionType getType() {
        return type;
    }
}

// MAIN APP
public class MusicStreamingApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // USER
        System.out.print("Enter User Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        User user = new User(1, name, email);

        // SONGS
        Song s1 = new Song(1, "Shape of You", "Ed Sheeran");
        Song s2 = new Song(2, "Believer", "Imagine Dragons");

        // PLAYLIST
        Playlist playlist = new Playlist(1, "My Playlist");
        playlist.addSong(s1);
        playlist.addSong(s2);

        // SHOW SONGS
        playlist.showSongs();

        System.out.print("Select song number to play: ");
        int choice = sc.nextInt();

        Song selected = playlist.getSong(choice - 1);
        selected.play();

        // SUBSCRIPTION
        System.out.println("\nChoose Subscription: 1. FREE  2. PREMIUM");
        int subChoice = sc.nextInt();

        Subscription sub;

        if (subChoice == 2) {
            sub = new Subscription(SubscriptionType.PREMIUM);

            System.out.println("Choose Payment: 1. UPI  2. NetBanking");
            int payChoice = sc.nextInt();

            Payment payment = (payChoice == 1) ? new UPI() : new NetBanking();
            payment.pay(199);

        } else {
            sub = new Subscription(SubscriptionType.FREE);
            System.out.println("Using FREE version (ads enabled)");
        }

        sc.close();
    }
}
