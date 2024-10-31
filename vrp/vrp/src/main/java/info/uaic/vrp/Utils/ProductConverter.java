/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Utils;

/**
 *
 * @author ioana
 */
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import info.uaic.vrp.Entities.Product;
import info.uaic.vrp.Services.ProductService;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
@FacesConverter("productConverter")
public class ProductConverter implements Converter<Product> {

    private final ProductService productService;
    public ProductConverter() {
        try {
            productService = new ProductService(DatabaseConnection.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection error", e);
        }
    }
    @Override
    public Product getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            int id = Integer.parseInt(value); 
            return productService.findProductById(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ProductConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Product product) {
        if (product == null) {
            return "";
        }
        return String.valueOf(product.getId());
    }
}
