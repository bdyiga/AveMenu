import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// MenuItem class remains unchanged
class MenuItem {
    private String name;
    private double price;
    private String description;

    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f\n   %s", name, price, description);
    }
}

// MenuCategory class remains unchanged
class MenuCategory {
    private String name;
    private List<MenuItem> items;

    public MenuCategory(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public String getName() {
        return name;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void displayItems() {
        System.out.println(name + ":");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        System.out.println();
    }
}

// HotelMenu class with modified placeOrder method
class HotelMenu {
    private List<MenuCategory> categories;
    private Map<String, Integer> order;

    public HotelMenu() {
        categories = new ArrayList<>();
        order = new HashMap<>();
    }

    public void addCategory(MenuCategory category) {
        categories.add(category);
    }

    public void displayMenu() {
        for (MenuCategory category : categories) {
            category.displayItems();
        }
    }

    public void placeOrder(Scanner scanner) {
        while (true) {
            System.out.println("Available categories:");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getName());
            }
            System.out.println("Enter the category number (or 0 to finish ordering): ");
            int categoryNumber = Integer.parseInt(scanner.nextLine());

            if (categoryNumber == 0) {
                break;
            }

            if (categoryNumber < 1 || categoryNumber > categories.size()) {
                System.out.println("Invalid category number. Please try again.");
                continue;
            }

            MenuCategory selectedCategory = categories.get(categoryNumber - 1);
            selectedCategory.displayItems();
            System.out.println("Enter the item number you want to order (or 0 to go back to categories): ");
            int itemNumber = Integer.parseInt(scanner.nextLine());

            if (itemNumber == 0) {
                continue;
            }

            if (itemNumber < 1 || itemNumber > selectedCategory.getItems().size()) {
                System.out.println("Invalid item number. Please try again.");
                continue;
            }

            MenuItem selectedItem = selectedCategory.getItems().get(itemNumber - 1);
            System.out.println("Enter the quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            order.put(selectedItem.getName(), order.getOrDefault(selectedItem.getName(), 0) + quantity);
            System.out.println("Added to order: " + quantity + "x " + selectedItem.getName());
        }
    }

    public void displayOrder() {
        if (order.isEmpty()) {
            System.out.println("No items in the order.");
            return;
        }

        System.out.println("Your order:");
        double total = 0;
        for (Map.Entry<String, Integer> entry : order.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            MenuItem item = findMenuItem(itemName);
            double itemTotal = item.getPrice() * quantity;
            total += itemTotal;
            System.out.printf("%dx %s - $%.2f\n", quantity, itemName, itemTotal);
        }
        System.out.printf("Total: $%.2f\n", total);
    }

    private MenuItem findMenuItem(String name) {
        for (MenuCategory category : categories) {
            for (MenuItem item : category.getItems()) {
                if (item.getName().equals(name)) {
                    return item;
                }
            }
        }
        return null;
    }
}

// Main class remains largely unchanged, with a small modification in the main method
public class HotelMenuSystem {
    public static void main(String[] args) {
        HotelMenu menu = new HotelMenu();

        // Create menu categories and items
        MenuCategory appetizers = new MenuCategory("Appetizers");
        System.out.println();
        appetizers.addItem(new MenuItem("Bruschetta", 8.99, "Toasted bread topped with tomatoes, garlic, and basil"));
        appetizers.addItem(new MenuItem("Calamari", 10.99, "Fried squid rings served with marinara sauce"));

        MenuCategory mainCourse = new MenuCategory("Main Course");
        System.out.println();
        mainCourse.addItem(new MenuItem("Grilled Salmon", 22.99, "Fresh salmon fillet with lemon butter sauce"));
        mainCourse.addItem(new MenuItem("Chicken Parmesan", 18.99, "Breaded chicken breast topped with marinara and mozzarella"));

        MenuCategory desserts = new MenuCategory("Desserts");
        System.out.println();
        desserts.addItem(new MenuItem("Tiramisu", 7.99, "Classic Italian coffee-flavored dessert"));
        desserts.addItem(new MenuItem("Chocolate Lava Cake", 8.99, "Warm chocolate cake with a gooey center"));

        // Add categories to the menu
        menu.addCategory(appetizers);
        menu.addCategory(mainCourse);
        menu.addCategory(desserts);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Menu System");
            System.out.println("1. Display Menu");
            System.out.println("2. Place Order");
            System.out.println("3. View Order");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    menu.displayMenu();
                    break;
                case 2:
                    menu.placeOrder(scanner);
                    break;
                case 3:
                    menu.displayOrder();
                    break;
                case 4:
                    System.out.println("Thank you for using the Hotel Menu System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}