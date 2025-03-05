// server.js - Simplified AveMenu backend
const express = require('express');
const bodyParser = require('body-parser');
const path = require('path');
const { v4: uuidv4 } = require('uuid');

// Initialize express app
const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, 'public')));

// In-memory data store (no file persistence for simplicity)
const menuData = {
  categories: [
    {
      id: 'appetizers',
      name: 'Appetizers',
      items: [
        { 
          id: 1, 
          name: 'Bruschetta', 
          price: 8.99, 
          description: 'Toasted bread topped with tomatoes, garlic, and basil',
          categoryId: 'appetizers'
        },
        { 
          id: 2, 
          name: 'Calamari', 
          price: 10.99, 
          description: 'Fried squid rings served with marinara sauce',
          categoryId: 'appetizers'
        }
      ]
    },
    {
      id: 'main-course',
      name: 'Main Course',
      items: [
        { 
          id: 3, 
          name: 'Grilled Salmon', 
          price: 22.99, 
          description: 'Fresh salmon fillet with lemon butter sauce',
          categoryId: 'main-course'
        },
        { 
          id: 4, 
          name: 'Chicken Parmesan', 
          price: 18.99, 
          description: 'Breaded chicken breast topped with marinara and mozzarella',
          categoryId: 'main-course'
        }
      ]
    },
    {
      id: 'desserts',
      name: 'Desserts',
      items: [
        { 
          id: 5, 
          name: 'Tiramisu', 
          price: 7.99, 
          description: 'Classic Italian coffee-flavored dessert',
          categoryId: 'desserts'
        },
        { 
          id: 6, 
          name: 'Chocolate Lava Cake', 
          price: 8.99, 
          description: 'Warm chocolate cake with a gooey center',
          categoryId: 'desserts'
        }
      ]
    }
  ]
};

// In-memory orders
const orders = [];

// API Routes

// Get all menu data
app.get('/api/menu', (req, res) => {
  res.json(menuData);
});

// Get all items in a category
app.get('/api/menu/category/:id', (req, res) => {
  const category = menuData.categories.find(cat => cat.id === req.params.id);
  if (!category) {
    return res.status(404).json({ message: "Category not found" });
  }
  res.json(category.items);
});

// Create a new order
app.post('/api/orders', (req, res) => {
  const { items, customerName, tableNumber } = req.body;
  
  if (!items || !Array.isArray(items) || items.length === 0) {
    return res.status(400).json({ message: "Order must contain at least one item" });
  }
  
  // Calculate order total
  let total = 0;
  const orderItems = [];
  
  for (const orderItem of items) {
    const { id, quantity } = orderItem;
    
    // Find the menu item
    let menuItem = null;
    for (const category of menuData.categories) {
      const item = category.items.find(item => item.id === id);
      if (item) {
        menuItem = item;
        break;
      }
    }
    
    if (!menuItem) {
      return res.status(400).json({ message: `Item with ID ${id} not found` });
    }
    
    const itemTotal = menuItem.price * quantity;
    total += itemTotal;
    
    orderItems.push({
      id,
      name: menuItem.name,
      price: menuItem.price,
      quantity,
      total: itemTotal
    });
  }
  
  // Create new order
  const newOrder = {
    id: uuidv4(),
    items: orderItems,
    customerName: customerName || 'Guest',
    tableNumber: tableNumber || null,
    status: 'pending',
    total,
    timestamp: new Date().toISOString()
  };
  
  // Add to orders
  orders.push(newOrder);
  
  res.status(201).json(newOrder);
});

// Get all orders
app.get('/api/orders', (req, res) => {
  res.json(orders);
});

// Get a specific order
app.get('/api/orders/:id', (req, res) => {
  const order = orders.find(order => order.id === req.params.id);
  if (!order) {
    return res.status(404).json({ message: "Order not found" });
  }
  res.json(order);
});

// Update order status
app.patch('/api/orders/:id', (req, res) => {
  const { status } = req.body;
  const validStatuses = ['pending', 'preparing', 'ready', 'delivered', 'cancelled'];
  
  if (!status || !validStatuses.includes(status)) {
    return res.status(400).json({ message: "Invalid status" });
  }
  
  const orderIndex = orders.findIndex(order => order.id === req.params.id);
  if (orderIndex === -1) {
    return res.status(404).json({ message: "Order not found" });
  }
  
  orders[orderIndex].status = status;
  res.json(orders[orderIndex]);
});

// Serve index.html for all routes (for client-side routing)
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

// Start the server
app.listen(PORT, () => {
  console.log(`AveMenu server running on port ${PORT}`);
});
