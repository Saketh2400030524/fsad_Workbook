package experiment2;

import java.util.Properties;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class ProductApp {
    
    private static Scanner scanner = new Scanner(System.in);
    private static SessionFactory factory = null;
    
    public static void main(String[] args) {
        
        try {
            // Create Configuration object
            Configuration cfg = new Configuration();
            
            // Hibernate settings
            Properties properties = new Properties();
            properties.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/fsad03");
            properties.setProperty(Environment.USER, "root");
            properties.setProperty(Environment.PASS, "#Sakethreddy21");
            properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.setProperty(Environment.SHOW_SQL, "true");
            properties.setProperty(Environment.FORMAT_SQL, "true");
            properties.setProperty(Environment.HBM2DDL_AUTO, "update");
            
            cfg.setProperties(properties);
            cfg.addAnnotatedClass(Product.class);
            
            factory = cfg.buildSessionFactory();
            System.out.println("✅ SessionFactory created successfully!\n");
            
            // Menu-driven program
            boolean running = true;
            while (running) {
                System.out.println("\n========== PRODUCT MANAGEMENT SYSTEM ==========");
                System.out.println("1. Insert Product");
                System.out.println("2. View Product by ID");
                System.out.println("3. View All Products");
                System.out.println("4. Update Product");
                System.out.println("5. Delete Product");
                System.out.println("6. Exit");
                System.out.println("===============================================");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        insertProduct();
                        break;
                    case 2:
                        viewProductById();
                        break;
                    case 3:
                        viewAllProducts();
                        break;
                    case 4:
                        updateProduct();
                        break;
                    case 5:
                        deleteProduct();
                        break;
                    case 6:
                        running = false;
                        System.out.println("\n✅ Exiting... Thank you!");
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please try again.");
                }
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }
    
    // INSERT Product
    private static void insertProduct() {
        System.out.println("\n=== INSERT PRODUCT ===");
        
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Product Description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();
        
        System.out.print("Enter Product Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Product product = new Product(name, description, price, quantity);
            session.save(product);
            
            tx.commit();
            System.out.println("✅ Product inserted successfully! Product ID: " + product.getId());
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("❌ Error inserting product: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    // VIEW Product by ID
    private static void viewProductById() {
        System.out.println("\n=== VIEW PRODUCT BY ID ===");
        
        System.out.print("Enter Product ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        
        Session session = factory.openSession();
        
        try {
            Product product = session.get(Product.class, id);
            
            if (product != null) {
                System.out.println("\n" + product);
            } else {
                System.out.println("❌ Product with ID=" + id + " not found!");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error retrieving product: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    // VIEW All Products
    private static void viewAllProducts() {
        System.out.println("\n=== ALL PRODUCTS ===");
        
        Session session = factory.openSession();
        
        try {
            java.util.List<Product> products = session.createQuery("from Product", Product.class).list();
            
            if (products.isEmpty()) {
                System.out.println("No products found in the database.");
            } else {
                System.out.println("Total Products: " + products.size());
                System.out.println("----------------------------------------");
                products.forEach(System.out::println);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error retrieving products: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    // UPDATE Product
    private static void updateProduct() {
        System.out.println("\n=== UPDATE PRODUCT ===");
        
        System.out.print("Enter Product ID to update: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Product product = session.get(Product.class, id);
            
            if (product != null) {
                System.out.println("Current Product: " + product);
                System.out.println("\nWhat would you like to update?");
                System.out.println("1. Name");
                System.out.println("2. Description");
                System.out.println("3. Price");
                System.out.println("4. Quantity");
                System.out.println("5. Update All");
                System.out.print("Enter choice: ");
                
                int updateChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (updateChoice) {
                    case 1:
                        System.out.print("Enter new Name: ");
                        product.setName(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Enter new Description: ");
                        product.setDescription(scanner.nextLine());
                        break;
                    case 3:
                        System.out.print("Enter new Price: ");
                        product.setPrice(scanner.nextDouble());
                        scanner.nextLine();
                        break;
                    case 4:
                        System.out.print("Enter new Quantity: ");
                        product.setQuantity(scanner.nextInt());
                        scanner.nextLine();
                        break;
                    case 5:
                        System.out.print("Enter new Name: ");
                        product.setName(scanner.nextLine());
                        System.out.print("Enter new Description: ");
                        product.setDescription(scanner.nextLine());
                        System.out.print("Enter new Price: ");
                        product.setPrice(scanner.nextDouble());
                        System.out.print("Enter new Quantity: ");
                        product.setQuantity(scanner.nextInt());
                        scanner.nextLine();
                        break;
                    default:
                        System.out.println("❌ Invalid choice!");
                        tx.rollback();
                        session.close();
                        return;
                }
                
                session.update(product);
                tx.commit();
                System.out.println("✅ Product updated successfully!");
                System.out.println("Updated Product: " + product);
                
            } else {
                System.out.println("❌ Product with ID=" + id + " not found!");
            }
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("❌ Error updating product: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    
    // DELETE Product
    private static void deleteProduct() {
        System.out.println("\n=== DELETE PRODUCT ===");
        
        System.out.print("Enter Product ID to delete: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Product product = session.get(Product.class, id);
            
            if (product != null) {
                System.out.println("Product to delete: " + product);
                System.out.print("Are you sure you want to delete? (yes/no): ");
                String confirm = scanner.nextLine();
                
                if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
                    session.delete(product);
                    tx.commit();
                    System.out.println("✅ Product deleted successfully!");
                } else {
                    System.out.println("⚠️ Delete operation cancelled.");
                    tx.rollback();
                }
                
            } else {
                System.out.println("❌ Product with ID=" + id + " not found!");
            }
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("❌ Error deleting product: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}