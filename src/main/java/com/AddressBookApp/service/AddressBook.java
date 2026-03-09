package com.AddressBookApp.service;

import com.AddressBookApp.model.Contact;
import com.AddressBookApp.util.DBConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.sql.*;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class AddressBook {

    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() { return contacts; }

    public void addContact(Contact contact) {
        boolean duplicate = contacts.stream().anyMatch(c -> c.equals(contact));
        if(duplicate)
            System.out.println("Duplicate contact! Name '" + contact.getName() + "' already exists.");
        else {
            contacts.add(contact);
            System.out.println("Contact added successfully!");
        }
    }

    public void viewContacts() {
        if(contacts.isEmpty()) System.out.println("No contacts found.");
        else contacts.forEach(System.out::println);
    }

    public boolean editContact(String name, Contact updatedContact) {
        for(int i=0; i<contacts.size(); i++) {
            if(contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.set(i, updatedContact);
                System.out.println("Contact updated successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }

    public boolean deleteContact(String name) {
        for(int i=0; i<contacts.size(); i++) {
            if(contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.remove(i);
                System.out.println("Contact deleted successfully!");
                return true;
            }
        }
        System.out.println("Contact not found!");
        return false;
    }

    public void searchPerson(String city, String state) {
        List<Contact> result = contacts.stream()
                .filter(c -> (city != null && c.getCity().equalsIgnoreCase(city)) ||
                        (state != null && c.getState().equalsIgnoreCase(state)))
                .collect(Collectors.toList());
        if(result.isEmpty()) System.out.println("No person found.");
        else result.forEach(System.out::println);
    }

    public Map<String, List<Contact>> viewPersonsByCity() {
        return contacts.stream().collect(Collectors.groupingBy(Contact::getCity));
    }

    public Map<String, List<Contact>> viewPersonsByState() {
        return contacts.stream().collect(Collectors.groupingBy(Contact::getState));
    }

    public Map<String, Long> countByCity() {
        return contacts.stream().collect(Collectors.groupingBy(Contact::getCity, Collectors.counting()));
    }

    public Map<String, Long> countByState() {
        return contacts.stream().collect(Collectors.groupingBy(Contact::getState, Collectors.counting()));
    }

    public List<Contact> sortByName() {
        return contacts.stream()
                .sorted((c1,c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());
    }

    public void writeContactsToFile(String fileName) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for(Contact c : contacts) {
                writer.write(c.getName() + "," + c.getPhone() + "," + c.getEmail() + "," +
                        c.getCity() + "," + c.getState());
                writer.newLine();
            }
            System.out.println("Contacts written to file successfully.");
        } catch(IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public void readContactsFromFile(String fileName) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length < 5) continue;
                Contact contact = new Contact(data[0], data[1], data[2], data[3], data[4]);
                contacts.add(contact);
            }
            System.out.println("Contacts loaded from file.");
        } catch(IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void readContactsFromCSV(String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] nextLine;
            reader.readNext(); // skip header
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine.length < 5) continue;
                Contact contact = new Contact(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4]);
                contacts.add(contact);
            }
            System.out.println("Contacts loaded from CSV successfully!");
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
    }
    public void writeContactsToCSV(String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            // Header
            String[] header = {"Name", "Phone", "Email", "City", "State"};
            writer.writeNext(header);

            for (Contact c : contacts) {
                String[] data = {c.getName(), c.getPhone(), c.getEmail(), c.getCity(), c.getState()};
                writer.writeNext(data);
            }
            System.out.println("Contacts saved to CSV successfully!");
        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }

    public void writeContactsToJSON(String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(this.contacts, writer);
            System.out.println("Contacts saved to JSON successfully!");
        } catch (IOException e) {
            System.out.println("Error writing JSON: " + e.getMessage());
        }
    }

    public void readContactsFromJSON(String fileName) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fileName)) {
            Contact[] contactArray = gson.fromJson(reader, Contact[].class);
            if(contactArray != null) {
                contacts.addAll(Arrays.asList(contactArray));
            }
            System.out.println("Contacts loaded from JSON successfully!");
        } catch (IOException e) {
            System.out.println("Error reading JSON: " + e.getMessage());
        }
    }

    public void loadContactsFromDB() {
        try (Connection conn = com.AddressBookApp.util.DBConnection.getConnection()) {
            String query = "SELECT name, phone, email, city, state FROM contacts";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            contacts.clear(); // clear existing contacts
            while (rs.next()) {
                Contact c = new Contact(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("city"),
                        rs.getString("state")
                );
                contacts.add(c);
            }
            System.out.println("Contacts loaded from DB successfully!");
        } catch (SQLException e) {
            System.out.println("Error loading contacts from DB: " + e.getMessage());
        }
    }
    public void saveContactToDB(Contact c) {
        try (Connection conn = com.AddressBookApp.util.DBConnection.getConnection()) {
            String query = "INSERT INTO contacts (name, phone, email, city, state) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getPhone());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getCity());
            stmt.setString(5, c.getState());
            stmt.executeUpdate();
            System.out.println("Contact saved to DB successfully!");
        } catch (SQLException e) {
            System.out.println("Error saving contact to DB: " + e.getMessage());
        }
    }


    public boolean updateContactInDB(String name, Contact updatedContact) {
        try (Connection conn = com.AddressBookApp.util.DBConnection.getConnection()) {
            String query = "UPDATE contacts SET name=?, phone=?, email=?, city=?, state=? WHERE name=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, updatedContact.getName());
            stmt.setString(2, updatedContact.getPhone());
            stmt.setString(3, updatedContact.getEmail());
            stmt.setString(4, updatedContact.getCity());
            stmt.setString(5, updatedContact.getState());
            stmt.setString(6, name);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Contact updated in DB successfully!");

                // Sync memory
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getName().equalsIgnoreCase(name)) {
                        contacts.set(i, updatedContact);
                        break;
                    }
                }
                return true;
            } else {
                System.out.println("Contact not found in DB!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error updating contact in DB: " + e.getMessage());
            return false;
        }
    }
    public List<Contact> getContactsByPeriod(Date start, Date end) {
        List<Contact> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT name, phone, email, city, state FROM contacts WHERE date_added BETWEEN ? AND ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, (java.sql.Date) start);
            stmt.setDate(2, (java.sql.Date) end);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                result.add(new Contact(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("city"),
                        rs.getString("state")
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error retrieving contacts by period: " + e.getMessage());
        }
        return result;
    }

    public Map<String, Long> countContactsByCityDB() {
        Map<String, Long> cityCount = new HashMap<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT city, COUNT(*) AS total FROM contacts GROUP BY city";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                cityCount.put(rs.getString("city"), rs.getLong("total"));
            }
        } catch(SQLException e) {
            System.out.println("Error counting contacts by city: " + e.getMessage());
        }
        return cityCount;
    }

    public Map<String, Long> countContactsByStateDB() {
        Map<String, Long> stateCount = new HashMap<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT state, COUNT(*) AS total FROM contacts GROUP BY state";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                stateCount.put(rs.getString("state"), rs.getLong("total"));
            }
        } catch(SQLException e) {
            System.out.println("Error counting contacts by state: " + e.getMessage());
        }
        return stateCount;
    }

    public boolean addContactToDBWithTransaction(Contact c) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            String query1 = "INSERT INTO contacts (name, phone, email, city, state, date_added) VALUES (?, ?, ?, ?, ?, CURDATE())";
            PreparedStatement stmt1 = conn.prepareStatement(query1);
            stmt1.setString(1, c.getName());
            stmt1.setString(2, c.getPhone());
            stmt1.setString(3, c.getEmail());
            stmt1.setString(4, c.getCity());
            stmt1.setString(5, c.getState());
            stmt1.executeUpdate();

            String query2 = "INSERT INTO contact_history (contact_name, action, action_date) VALUES (?, ?, CURDATE())";
            PreparedStatement stmt2 = conn.prepareStatement(query2);
            stmt2.setString(1, c.getName());
            stmt2.setString(2, "Added");
            stmt2.executeUpdate();

            conn.commit();
            contacts.add(c);
            System.out.println("Contact added successfully with transaction!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction failed, rolling back...");
                DBConnection.getConnection().rollback();
            } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        }
    }

}