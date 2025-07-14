import algorithms.search.binary_search;
import algorithms.search.linear_search;
import algorithms.search.rabin_karp;
import core.*;
import utils.multi_strategy;
import utils.search_strategy;

import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);
        Map<String, user> users = new HashMap<>();
        Map<String, vault_manager> vaults = new HashMap<>();
        boolean running = true;

        while (running) {
            System.out.println("\n== MAIN MENU ==");
            System.out.println("1. Register new user.");
            System.out.println("2. Login.");
            System.out.println("3. Logout.");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:{
                    System.out.println("Choose a username: ");
                    String username = scanner.next();

                    if (users.containsKey(username)) {
                        System.out.println("Username already exists!");
                        break;
                    }

                    System.out.println("Choose a password: ");
                    String password = scanner.next();

                    user new_user = new user(username, password);
                    users.put(username, new_user);

                    System.out.println("Choose Caesar Cipher shift for your vault: ");
                    int shift = scanner.nextInt();

                    vault_manager new_vault = new vault_manager(shift);
                    vaults.put(username, new_vault);

                    System.out.println("User registered successfully!");
                    break;
                }

                case 2:{
                    System.out.println("Enter your username: ");
                    String username = scanner.next();

                    if (!users.containsKey(username)) {
                        System.out.println("Username doesn't exist!");
                        break;
                    }

                    System.out.println("Enter your password: ");
                    String password = scanner.next();

                    user u = users.get(username);

                    if (!u.authenticate(password)) {
                        System.out.println("Incorrect password!");
                        break;
                    }

                    vault_manager u_vault = vaults.get(username);
                    System.out.println("Welcome, " + u.getName());
                    vault_menu(scanner, u_vault);
                    break;
                }

                case 3:
                    running = false;
                    System.out.println("Thank you for using our Vault!");
                    break;

                default:
                    System.out.println("Wrong choice!");
            }
        }
    }

    private static void vault_menu(Scanner scanner, vault_manager vm) {
        boolean running = true;

        while (running) {
            System.out.println("\n== VAULT MENU ==");
            System.out.println("1. Add entry.");
            System.out.println("2. Update entry.");
            System.out.println("3. Delete entry.");
            System.out.println("4. Search by name (Linear).");
            System.out.println("5. Search by name (Binary).");
            System.out.println("6. Search passwords by pattern (Rabin-Karp).");
            System.out.println("7. List all keys.");
            System.out.println("8. List all entries.");
            System.out.println("9. Logout.");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:{
                    System.out.println("Enter entry name: ");
                    String name = scanner.nextLine();

                    System.out.println("Enter URL: ");
                    String url = scanner.nextLine();

                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();

                    boolean added = vm.add(name, url, username, password);
                    System.out.println(added ? "Entry added successfully!" : "Entry not found!");
                    break;
                }

                case 2:{
                    System.out.println("Enter the name of the entry to update: ");
                    String upd_name = scanner.nextLine();

                    if (vm.get(upd_name) == null) {
                        System.out.println("Entry not found!");
                        break;
                    }

                    System.out.println("New URL: ");
                    String upd_url = scanner.nextLine();

                    System.out.println("New username: ");
                    String upd_username = scanner.nextLine();

                    System.out.println("New password: ");
                    String upd_password = scanner.nextLine();

                    boolean updated = vm.update(upd_name, upd_url, upd_username, upd_password);
                    System.out.println(updated ? "Entry updated successfully!" : "Entry not found!");
                    break;
                }

                case 3:{
                    System.out.println("Enter the name of the entry to delete: ");
                    String del_name = scanner.nextLine();

                    boolean deleted = vm.delete(del_name);
                    System.out.println(deleted ? "Entry deleted successfully!" : "Entry not found!");
                    break;
                }

                case 4: {
                    System.out.println("Enter name to search: ");
                    String key =  scanner.nextLine().trim();

                    if (key.isEmpty()) {
                        System.out.println("Linear search requires a non-empty search key!");
                        break;
                    }

                    search_strategy<String> linear = new linear_search();
                    List<entry> found = vm.search_key(key, linear);

                    if (found.isEmpty()) {
                        System.out.println("No such entry found!");
                    } else {
                        for (entry e : found) {
                            System.out.println(e);
                            System.out.println("--------------------");
                        }
                    }
                    break;
                }

                case 5: {
                    String key =  null;

                    search_strategy<String> binary = new binary_search();
                    List<entry> found = vm.search_key(key, binary);

                    if (found.isEmpty()) {
                        System.out.println("No such entry found!");
                    } else {
                        for (entry e : found) {
                            System.out.println(e);
                            System.out.println("--------------------");
                        }
                    }
                    break;
                }

                case 6:
                    System.out.println("Enter password pattern to search: ");
                    String pattern = scanner.nextLine();
                    scanner.nextLine();
                    multi_strategy rk = new rabin_karp();

                    List<String> matches = vm.search_values(pattern, rk);
                    if (matches.isEmpty()) {
                        System.out.println("No matches found!");
                    } else {
                        for (String password : matches) {
                            System.out.println(password);
                            System.out.println("----------------------");
                        }
                    }
                    break;

                case 7:
                    System.out.println("Stored keys: ");
                    vm.list_keys();
                    break;

                case 8:
                    System.out.println("All entries: ");
                    vm.list_all();
                    break;

                case 9:
                    running = false;
                    System.out.println("Thank you for using our Vault!");
                    break;

                default:
                    System.out.println("Wrong choice!");
            }
        }
    }
}