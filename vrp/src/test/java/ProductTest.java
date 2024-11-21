




import info.uaic.vrp.Entities.Product;
import info.uaic.vrp.Repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ioana
 */
public class ProductTest {
    long idMock = 1;
    String nameMock = "Keyboard";
    Product productMock = new Product();
    List<Product> productsMock = new ArrayList<>();
    ProductRepository productRepositoryMock = new ProductRepository();

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        productRepositoryMock = new ProductRepository();
        productMock.setName(nameMock);
        productMock.setPrice(120.0);
        productMock.setStock(10);
        em.getTransaction().begin();
        em.persist(productMock); 
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testProductPersistence() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product product = new Product();
        product.setName("Sample Product");
        product.setPrice(9.99);
        em.persist(product);
        product = (Product) em.createQuery(
                "SELECT p FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", "Sample Product")
                .getSingleResult();
        product.setName("Tesst");
        em.getTransaction().commit();
        
        Product updatedProduct = em.find(Product.class, product.getId());
        assertEquals("Tesst", updatedProduct.getName(), "Product name was not updated");
        em.close();
        emf.close();
    }
    
    @Test
    public void findAllTest() {
        List<Product> result = productRepositoryMock.findAll();
        assertEquals(productsMock, result, "Expected products do not match actual results.");
    }

    @Test
    public void findByIdTest() {
        Product result = productRepositoryMock.findById(idMock);
        assertNotNull(result, "Product found by ID.");
    }

    @Test
    public void findByNameTest() {
        Product result = productRepositoryMock.findByName(nameMock);
        assertNotNull(result, "Product found by name.");
    }

    @Test
    public void createTest() {
        String createdProductName = productMock.getName();
        productRepositoryMock.create(productMock);
        assertNotNull(productRepositoryMock.findByName(createdProductName), "Product created");
    }

    @Test
    public void editTest() {
        productMock.setName("Updated Keyboard");
        productRepositoryMock.edit(productMock);
        assertEquals("Updated Keyboard", productMock.getName(), "Product updated.");
    }

    @Test
    public void removeTest() {
        String nameDeletedProduct = productMock.getName();
        productRepositoryMock.remove(productMock);
        assertNull(productRepositoryMock.findByName(nameDeletedProduct), "Product deleted");
    }
    
//        @Test
//    public void testCreateProduct() {
//        Product product = new Product();
//        product.setName("Laptop");
//        product.setPrice(1200.0);
//        product.setQuantity(10);
//
//        Product savedProduct = productRepository.save(product);
//
//        assertNotNull(savedProduct.getId(), "Product ID should not be null after saving.");
//        assertEquals("Laptop", savedProduct.getName());
//        assertEquals(1200.0, savedProduct.getPrice());
//        assertEquals(10, savedProduct.getQuantity());
//    }
//
//    @Test
//    public void testFindProductById() {
//        Product product = new Product();
//                product.setName("Tablet");
//        product.setPrice(500.0);
//        product.setQuantity(5);
//        Product savedProduct = productRepository.save(product);
//
//        Product foundProduct = productRepository.findById(savedProduct.getId());
//
//        assertNotNull(foundProduct, "Product should be found.");
//        assertEquals("Tablet", foundProduct.getName());
//        assertEquals(500.0, foundProduct.getPrice());
//    }
//
//    @Test
//    public void testUpdateProduct() {
//
//        Product product = new Product();
//        product.setName("LaptopTest");
//        product.setPrice(1200.0);
//        product.setQuantity(20);
//        Product savedProduct = productRepository.save(product);
//
//        savedProduct.setName("Smartphone");
//        savedProduct.setPrice(350.0);
//        savedProduct.setQuantity(20);
//        Product updatedProduct = productRepository.save(savedProduct);
//
//        assertEquals("Smartphone", updatedProduct.getName());
//        assertEquals(1200.0, updatedProduct.getPrice());
//        assertEquals(20, updatedProduct.getQuantity());
//    }
//
//    @Test
//    public void testDeleteProduct() {
//        Product product = new Product();
//        product.setName("Monitor Test");
//        product.setPrice(300.0);
//        product.setQuantity(10);
//        Product savedProduct = productRepository.save(product);
//
//        productRepository.delete(savedProduct);
//
//        Product deletedProduct = productRepository.findById(savedProduct.getId());
//        assertNull(deletedProduct, "Product deleted");
//    }
//
//    @Test
//    public void testFindAllProducts() {
//        Product product1 = new Product();
//        product1.setName("Keyboard Test");
//        product1.setPrice(50.0);
//        product1.setQuantity(25);
//        Product product2 = new Product();
//        product2.setName("Mouse Test");
//        product2.setPrice(30.0);
//        product2.setQuantity(40);
//        productRepository.save(product1);
//        productRepository.save(product2);
//
//        List<Product> products = productRepository.findAll();
//
//        assertFalse(products.isEmpty(), "Product list should not be empty.");
//        assertTrue(products.size() >= 2, "Product list should contain at least two products.");
//    }
//
//    @Test
//    public void testFindByName() {
//        Product product = new Product();
//        product.setName("Headphones Test");
//        product.setPrice(100.0);
//        product.setQuantity(15);
//        productRepository.save(product);
//
//        List<Product> products = productRepository.findByName("Headphones Test");
//
//        assertFalse(products.isEmpty(), "Should find products with the given name.");
//        assertEquals("Headphones Test", products.get(0).getName());
//    }

}
