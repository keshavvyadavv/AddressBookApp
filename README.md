# Address Book Application

## Introduction
The Address Book Application is a Java project used to manage contact details.  
It demonstrates concepts like **OOP, Collections, File Handling, JDBC, Multithreading, and REST API integration**.  
The application evolves from basic contact management to database and JSON server integration.

## UC-1: Create Contact
Create a **Contact class** to store contact details.

**Fields:**
- First Name
- Last Name
- Address
- City
- State
- Zip
- Phone Number
- Email

This use case establishes the **basic data structure for contacts** in the Address Book.

---

## UC-2: Add Contact to Address Book

Ability to **add a new contact** to the Address Book.

**Implementation:**
- Created an `AddressBook` class.
- Stored contacts using a **Java Collection (ArrayList)**.
- Added a method to insert new `Contact` objects into the address book.

This allows users to **store multiple contacts in the Address Book**.

---

## UC-3: Prevent Duplicate Contact

Ability to **prevent duplicate contacts** in the Address Book.

**Implementation:**
- Checked existing contacts before adding a new one.
- Compared contacts using **first name and last name**.

This ensures that **duplicate entries are not added** to the Address Book.

---

## UC-4: Edit Existing Contact

Ability to **edit an existing contact** in the Address Book.

**Implementation:**
- Search contact using **first name**.
- Update required contact fields like **address, city, state, phone, or email**.

This allows users to **modify contact details when needed**.

---

## UC-5: Delete Contact

Ability to **delete a contact** from the Address Book.

**Implementation:**
- Search contact using **first name**.
- Remove the contact from the **Address Book list**.

This allows users to **remove unwanted contacts** from the Address Book.

---

## UC-6: Add Multiple Contacts

Ability to **add multiple contacts** to the Address Book.

**Implementation:**
- Used a **loop to accept multiple contact details**.
- Stored contacts in the **Address Book collection (ArrayList)**.

This allows users to **manage multiple contacts in the Address Book**.

---

## UC-7: Search Contacts by City or State

Ability to **search contacts by city or state**.

**Implementation:**
- Used **Java Collections** to filter contacts.
- Displayed contacts matching the **given city or state**.

This helps users **find contacts based on location**.

---

## UC-8: View Persons by City or State

Ability to **view all contacts grouped by city or state**.

**Implementation:**
- Used **Map collection** to store contacts by city or state.
- Displayed persons belonging to the **same city or state**.

This helps in **organizing contacts based on location**.

---

## UC-9: Count Contacts by City or State

Ability to **count number of contacts in a particular city or state**.

**Implementation:**
- Used **Map and Java Collections**.
- Calculated the **total contacts for each city or state**.

This helps users **know how many contacts belong to a specific location**.

---

## UC-10: Sort Contacts by Name

Ability to **sort contacts alphabetically by name**.

**Implementation:**
- Used **Java Collections sorting**.
- Sorted contacts based on **person name**.

This helps users **view contacts in an organized alphabetical order**.

---

## UC-11: Sort Contacts by City, State or Zip

Ability to **sort contacts by city, state, or zip**.

**Implementation:**
- Used **Java Comparator with Collections.sort()**.
- Sorted contacts based on **city, state, or zip fields**.

This helps organize contacts **based on location details**.

---

## UC-12: Write Address Book to File

Ability to **store Address Book contacts into a file**.

**Implementation:**
- Used **Java File I/O**.
- Wrote contact details from memory to a **text file**.

This allows **persistent storage of Address Book data**.

---

## UC-13: Read Address Book from File

Ability to **read Address Book contacts from a file**.

**Implementation:**
- Used **Java File I/O**.
- Loaded contact details from the **file into application memory**.

This allows the application to **retrieve stored contacts**.

---

## UC-14: Read/Write Address Book using CSV

Ability to **store and retrieve contacts using a CSV file**.

**Implementation:**
- Used **CSV file format** for storing contacts.
- Implemented **read and write operations** for CSV files.

This allows contacts to be **easily managed in spreadsheet format**.

---

## UC-15: Read/Write Address Book using JSON

Ability to **store and retrieve contacts using JSON format**.

**Implementation:**
- Used **JSON file format** for storing contact data.
- Implemented **read and write operations** using a JSON library.

This allows structured **data storage and easy data exchange**.

---

## UC-16: Connect Address Book to Database

Ability to **connect the Address Book Application with a database**.

**Implementation:**
- Used **JDBC for database connectivity**.
- Retrieved contact data from the **Address Book Database**.

This enables **database-based storage and management of contacts**.

---

## UC-17: Retrieve Contacts from Database

Ability to **retrieve Address Book contacts from the database**.

**Implementation:**
- Used **JDBC for database operations**.
- Fetched contact records and loaded them into **application memory**.

This allows the application to **read and display contacts stored in the database**.

---

## UC-18: Update Contact in Database

Ability to **update contact details in the Address Book Database**.

**Implementation:**
- Used **JDBC for update operations**.
- Modified existing contact details in the **database table**.

This keeps the **database and application data synchronized**.

---

