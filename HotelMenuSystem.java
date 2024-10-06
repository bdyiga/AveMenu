import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// MenuItem class to represent each item on the menu
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

// MenuCategory class to represent different categories of menu items
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

// HotelMenu class to manage the entire menu system
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
            System.out.println("Enter the category name (or 'done' to finish ordering): ");
            String categoryName = scanner.nextLine().trim();

            if (categoryName.equalsIgnoreCase("done")) {
                break;
            }

            MenuCategory selectedCategory = findCategory(categoryName);
            if (selectedCategory == null) {
                System.out.println("Category not found. Please try again.");
                continue;
            }

            selectedCategory.displayItems();
            System.out.println("Enter the item number you want to order: ");
            int itemNumber = Integer.parseInt(scanner.nextLine()) - 1;

            if (itemNumber < 0 || itemNumber >= selectedCategory.getItems().size()) {
                System.out.println("Invalid item number. Please try again.");
                continue;
            }

            MenuItem selectedItem = selectedCategory.getItems().get(itemNumber);
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

    private MenuCategory findCategory(String name) {
        for (MenuCategory category : categories) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
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

// Main class to run the hotel menu system
public class HotelMenuSystem {
    public static void main(String[] args) {
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
