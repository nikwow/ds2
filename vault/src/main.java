import core.user;
import search.*;
import ui.menu;

import java.util.*;

public class main {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, user> users = new HashMap<>();
        user current = null;
        boolean run = true;

        while (run) {
            menu.menu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":{
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();

                    if (users.containsKey(username)) {
                        System.out.println("Username already in use");
                        break;
                    }

                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();

                    try {
                        user new_user = new user(username, password, users.size() + 1);
                        users.put(username, new_user);
                        System.out.println("New user created");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid username or password");
                    }
                    break;
                }
                case "2":{
                    if (current != null) {
                        System.out.println("User is already logged in: " + current.getUsername());
                        break;
                    }

                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();

                    user u =  users.get(username);
                    if (u != null && u.authenticate(username, password)) {
                        current = u;
                        System.out.println("Welcome, " + current.getUsername() + "!");
                        vault(scanner, new ArrayList<>(users.values()));
                    } else {
                        System.out.println("Invalid username or password");
                    }
                    break;
                }
                case "3":{
                    System.out.println("Exiting. Thanks for using our Vault.");
                    run = false;
                    scanner.close();
                    break;
                }
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static void vault(Scanner scanner, List<user> users) {
        while (true) {
            System.out.println("\n===VAULT MENU===");
            System.out.println("[1] Linear Search");
            System.out.println("[2] Binary Search");
            System.out.println("[3] Rabin-Karp.");
            System.out.println("[4] Back to main menu");
            System.out.println("Choose an option: ");

            String choice = scanner.nextLine();
            search search = null;

            switch (choice) {
                case "1":{
                    search = new linear_search();
                    break;
                }

                case "2":{
                    search = new binary_search();

                    System.out.println("All accounts: ");
                    for (user u : users) {
                        System.out.println("Username: " + u.getUser_hash() + ", ID: " + u.getId());
                    }
                    break;
                }

                case "3":{
                    search = new rabin_karp();
                    break;
                }

                case "4":{
                    return;
                }

                default:
                    System.out.println("Invalid choice");
            }
            System.out.println("Enter search input: ");
            String query = scanner.nextLine().trim();
            search.search(users, query);
        }
    }
}