
# AveMenu System Design Document

## 1. Introduction

### 1.1 Purpose
The Hotel Menu System is a Java-based console application designed to manage a hotel's menu, allow customers to place orders, and display order summaries. This system aims to streamline the food ordering process in a hotel setting.

### 1.2 Scope
The system includes functionality for displaying the menu, placing orders, and viewing order summaries. It does not include features such as payment processing, kitchen management, or user authentication.

## 2. System Architecture

The Hotel Menu System is built using a simple object-oriented architecture with the following main components:

1. MenuItem
2. MenuCategory
3. HotelMenu
4. HotelMenuSystem (main class)

These components interact to create a hierarchical structure representing the menu and its items.

## 3. Component Design

### 3.1 MenuItem

#### Description
Represents a single item on the menu.

#### Attributes
- name: String
- price: double
- description: String

#### Methods
- Constructor: MenuItem(String name, double price, String description)
- Getters: getName(), getPrice(), getDescription()
- toString(): Returns a formatted string representation of the item

### 3.2 MenuCategory

#### Description
Represents a category of menu items (e.g., Appetizers, Main Course, Desserts).

#### Attributes
- name: String
- items: List<MenuItem>

#### Methods
- Constructor: MenuCategory(String name)
- addItem(MenuItem item): Adds an item to the category
- Getters: getName(), getItems()
- displayItems(): Displays all items in the category

### 3.3 HotelMenu

#### Description
Manages the entire menu system, including categories, items, and order processing.

#### Attributes
- categories: List<MenuCategory>
- order: Map<String, Integer>

#### Methods
- Constructor: HotelMenu()
- addCategory(MenuCategory category): Adds a category to the menu
- displayMenu(): Displays all categories and items
- placeOrder(Scanner scanner): Handles the order placement process
- displayOrder(): Shows the current order and total price
- findMenuItem(String name): Helper method to find a menu item by name

### 3.4 HotelMenuSystem

#### Description
Main class that runs the Hotel Menu System and handles user interaction.

#### Methods
- main(String[] args): Entry point of the application, manages the main program loop and user input

## 4. Data Flow

1. The system initializes with predefined menu categories and items.
2. Users interact with the main menu to view the menu, place orders, or view their current order.
3. When placing an order:
   a. Users select a category
   b. Users select an item from the chosen category
   c. Users specify the quantity
   d. The system adds the item to the order
4. When viewing an order, the system calculates the total price and displays the order summary.

## 5. User Interface

The system uses a console-based interface with the following main options:

1. Display Menu
2. Place Order
3. View Order
4. Exit

Users navigate through these options and sub-menus using numeric inputs.

## 6. Error Handling

The system includes basic error handling for:
- Invalid menu selections
- Invalid category or item selections
- Invalid quantity inputs

Error messages are displayed to guide the user in case of incorrect inputs.

## 7. Limitations and Future Enhancements

### 7.1 Current Limitations
- Console-based interface may not be user-friendly for all users
- No persistent storage of menu items or orders
- Limited error handling and input validation

### 7.2 Potential Future Enhancements
- Graphical User Interface (GUI) for improved user experience
- Database integration for persistent storage of menu items and orders
- User authentication and role-based access (e.g., customers, staff, admin)
- Kitchen management system integration
- Payment processing integration
- Order customization options (e.g., special requests, allergen information)
- Reporting and analytics features
- Mobile application version

## 8. Conclusion

The Hotel Menu System provides a foundation for managing a hotel's food ordering process. While currently limited in scope, the object-oriented design allows for easy expansion and enhancement of features in the future.