# 🛍️ Sale Campaign Management System (Spring Boot + MySQL)

A system to manage **products, discounts, and sale campaigns** for a company that manages ~100,000 products.  
The system dynamically adjusts product prices during sales and reverts them afterward.  

---

## 🚀 Features / Requirements

1. **Product Management**
   - Manage a large catalogue (~100K products).
   - Attributes: `Product ID, Title, MRP, Current Price, Discount, Inventory`.

2. **Sale Campaigns**
   - Create campaigns with a title, start/end date, and per-product discounts.
   - During the campaign, product prices are adjusted.
   - After the campaign ends, prices revert to normal.

3. **Dynamic Pricing**
   - Example:  
     - MRP: `1000`  
     - Current Price: `900`  
     - Sale Campaign Discount: `10%`  
     - **Final Price in Sale** = `900 - (10% of 900) = 810`  
     - After sale ends → revert back to `900`.

4. **Data Queries**
   - Retrieve all products with current prices (adjusted with active campaigns).
   - View product price history.
   - Fetch all campaign information (past, current, upcoming).

---

## 🛠️ Tech Stack

- **Java 17+**  
- **Spring Boot 3+**  
- **Spring Data JPA + Hibernate**  
- **MySQL** (scalable schema for products & campaigns)  
- **Maven / Gradle**  

---
