/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_cs102;

/**
 *
 * @author ACER
 */
public class Cart {

    private Product[] items;
    private int[] quantities;
    private int size;

    public Cart() {
        this.items = new Product[10];
        this.quantities = new int[10];
        this.size = 0;
    }

    public void addItem(Product item, int Quant) {
        if (this.size == this.items.length) {
            

            
            Product[] newItems = new Product[this.size * 2];
            int[] newQuantities = new int[this.size * 2];
            
            for (int i = 0; i < this.items.length; i++) {
                newItems[i] = this.items[i];
                newQuantities[i] = this.quantities[i];
            }
            
            newItems[size] = item;
            newQuantities[size] = Quant;
            
            this.items = newItems;
            this.quantities = newQuantities;
            
            this.size++;

        } else {
            /
            this.items[size] = item;
            this.quantities[size] = Quant;
            this.size++;
        }

    }

    public void removeItem(Product item) {

        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(item)) {
                this.items[i] = null;
                this.quantities[i] = 0;
                this.size--;
                break;
            }
        }
    }

    public boolean containsItem(Product item) {
        
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                continue;
            }
            if (items[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public void setQuantities(int[] quantities) {
        this.quantities = quantities;
    }

    public Product[] getItems() {
        return items;
    }

    public void setItems(Product[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        String result = String.format("%20S\n", "CART ITEMS");
        result += String.format("%-15s%-10s%-10s\n\n", "Product Name", "Price", "Quantity");
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                continue;
            }
            result += String.format("%-15S%5.2f%10d\n", items[i].getName(), items[i].getPrice(), quantities[i]);
        }

        return result;
    }

}


