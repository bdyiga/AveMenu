import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a single item on the menu.
 * Each MenuItem has a name, price, and description.
 */
class MenuItem {
    private String name;
    private double price;
    private String description;

    /**
     * Constructor for creating a new MenuItem.
     * 
     * @param name The name of the menu item
     * @param price The price of the menu item
     * @param description A brief description of the menu item
     */
    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Provides a string representation of the MenuItem.
     * 
     * @return A formatted string containing the item's name, price, and description
     */
    @Override
    public String toString() {
        return String.format("%s - $%.2f\n   %s", name, price, description);
    }
}

/**
 * Represents a category of menu items (e.g., Appetizers, Main Course, Desserts).
 * Each MenuCategory contains a list of MenuItems.
 */
class MenuCategory {
    private String name;
    private List<MenuItem> items;

    /**
     * Constructor for creating a new MenuCategory.
     * 
     * @param name The name of the category
     */
    public MenuCategory(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    /**
     * Adds a new MenuItem to this category.
     * 
     * @param item The MenuItem to be added
     */
    public void addItem(MenuItem item) {
        items.add(item);
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    /**
     * Displays all items in this category.
     * Each item is numbered for easy selection.
     */
    public void displayItems() {
        System.out.println(name + ":");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        System.out.println();
    }
}

/**
 * Manages the entire hotel menu system, including categories, items, and order processing.
 */
class HotelMenu {
    private List<MenuCategory> categories;
    private Map<String, Integer> order;

    /**
     * Constructor for creating a new HotelMenu.
     * Initializes the categories list and order map.
     */
    public HotelMenu() {
        categories = new ArrayList<>();
        order = new HashMap<>();
    }

    /**
     * Adds a new MenuCategory to the menu.
     * 
     * @param category The MenuCategory to be added
     */
    public void addCategory(MenuCategory category) {
        categories.add(category);
    }

    /**
     * Displays all categories and their items in the menu.
     */
    public void displayMenu() {
        for (MenuCategory category : categories) {
            category.displayItems();
        }
    }

    /**
     * Handles the order placement process.
     * Allows users to select categories, items, and specify quantities.
     * 
     * @param scanner Scanner object for user input
     */
    public void placeOrder(Scanner scanner) {
        while (true) {
            // Display available categories
            System.out.println("Available categories:");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getName());
            }
            System.out.println("Enter the category number (or 0 to finish ordering): ");
            
            // Get user input for category selection
            int categoryNumber = Integer.parseInt(scanner.nextLine());

            // Check if user wants to finish ordering
            if (categoryNumber == 0) {
                break;
            }

            // Validate category number
            if (categoryNumber < 1 || categoryNumber > categories.size()) {
                System.out.println("Invalid category number. Please try again.");
                continue;
            }

            // Display items in the selected category
            MenuCategory selectedCategory = categories.get(categoryNumber - 1);
            selectedCategory.displayItems();
            System.out.println("Enter the item number you want to order (or 0 to go back to categories): ");
            
            // Get user input for item selection
            int itemNumber = Integer.parseInt(scanner.nextLine());

            // Check if user wants to go back to category selection
            if (itemNumber == 0) {
                continue;
            }

            // Validate item number
            if (itemNumber < 1 || itemNumber > selectedCategory.getItems().size()) {
                System.out.println("Invalid item number. Please try again.");
                continue;
            }

            // Get selected item and quantity
            MenuItem selectedItem = selectedCategory.getItems().get(itemNumber - 1);
            System.out.println("Enter the quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            // Add item to the order
            order.put(selectedItem.getName(), order.getOrDefault(selectedItem.getName(), 0) + quantity);
            System.out.println("Added to order: " + quantity + "x " + selectedItem.getName());
        }
    }

    /**
     * Displays the current order and calculates the total price.
     */
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

    /**
     * Helper method to find a MenuItem by its name.
     * 
     * @param name The name of the MenuItem to find
     * @return The MenuItem if found, null otherwise
     */
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

/**
 * Main class that runs the Hotel Menu System.
 * Handles user interaction and menu navigation.
 */
public class HotelMenuSystem {
    public static void main(String[] args) {
        // Create the main menu object
        HotelMenu menu = new HotelMenu();

        // Create menu categories and items
        MenuCategory appetizers = new MenuCategory("Appetizers");
        appetizers.addItem(new MenuItem("Bruschetta", 8.99, "Toasted bread topped with tomatoes, garlic, and basil"));
        appetizers.addItem(new MenuItem("Calamari", 10.99, "Fried squid rings served with marinara sauce"));

        MenuCategory mainCourse = new MenuCategory("Main Course");
        mainCourse.addItem(new MenuItem("Grilled Salmon", 22.99, "Fresh salmon fillet with lemon butter sauce"));
        mainCourse.addItem(new MenuItem("Chicken Parmesan", 18.99, "Breaded chicken breast topped with marinara and mozzarella"));

        MenuCategory desserts = new MenuCategory("Desserts");
        desserts.addItem(new MenuItem("Tiramisu", 7.99, "Classic Italian coffee-flavored dessert"));
        desserts.addItem(new MenuItem("Chocolate Lava Cake", 8.99, "Warm chocolate cake with a gooey center"));

        // Add categories to the menu
        menu.addCategory(appetizers);
        menu.addCategory(mainCourse);
        menu.addCategory(desserts);

        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Main program loop
        while (true) {
            // Display main menu options
            System.out.println("\nHotel Menu System");
            System.out.println("1. Display Menu");
            System.out.println("2. Place Order");
            System.out.println("3. View Order");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            // Get user choice
            int choice = Integer.parseInt(scanner.nextLine());

            System.out.println(); 
            // Process user choice
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