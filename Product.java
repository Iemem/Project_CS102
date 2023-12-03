/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_cs102;

import java.util.Objects;

/**
 *
 * @author ACER
 */
public class Product implements Discountable, Comparable<Product> {

    private String name;
    private double price;
    private int inventoryLevel;

    private static int numProducts;

    public Product(String name, double price, int inventoryLevel) {
        setName(name);
        setPrice(price);
        setInventoryLevel(inventoryLevel);
        ++numProducts;
    }

    public static int getNumProducts() {
        return numProducts;
    }

    public static void decreaseNumProducts() {
        --numProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventoryLevel() {
        return inventoryLevel;
    }

    public void setInventoryLevel(int inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return Double.compare(other.price, price) == 0 && Objects.equals(name, other.name);
    }

    @Override
    public double calculateDiscount(double discountRate) {
        return price - (price * discountRate);
    }

    @Override
    public int compareTo(Product o) {
        return (int) (this.getPrice() - o.getPrice()) * 1000;
    }

    @Override
    public String toString() {
        return "Product{" + "name=" + name + ", price=" + price + ", inventoryLevel=" + inventoryLevel + '}';
    }

}

