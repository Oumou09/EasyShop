package org.yearup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class ShoppingCartItem
{
    private int product = 0;
    private int quantity = 1;
    private BigDecimal discountPercent = BigDecimal.ZERO;

    public ShoppingCartItem() {
        this.product = product;
        this.quantity = quantity;
        this.discountPercent = discountPercent;
    }

    public int getProduct()
    {
        return product;
    }

    public void setProduct(int product)
    {
        this.product = product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public BigDecimal getDiscountPercent()
    {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent)
    {
        this.discountPercent = discountPercent;
    }

    @JsonIgnore
    public int getProductId()
    {
        return this.product;
    }

    public BigDecimal getLineTotal()
    {
        BigDecimal basePrice = BigDecimal.valueOf(product);
        BigDecimal quantity = new BigDecimal(this.quantity);

        BigDecimal subTotal = basePrice.multiply(quantity);
        BigDecimal discountAmount = subTotal.multiply(discountPercent);

        return subTotal.subtract(discountAmount);
    }
}
