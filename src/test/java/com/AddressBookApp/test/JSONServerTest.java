
package com.AddressBookApp.test;

import com.AddressBookApp.model.Contact;
import com.AddressBookApp.service.AddressBook;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JSONServerTest {


    private AddressBook addressBook;

    @BeforeEach
    public void setup() {
        addressBook = new AddressBook();
        RestAssured.baseURI = "http://localhost:3000";
    }

    @Test
    public void testRetrieveContactsFromJSONServer() {
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/contacts")
                .then()
                .statusCode(200)
                .extract().response();

        Contact[] contactsFromServer = response.as(Contact[].class);

        List<Contact> contactList = Arrays.asList(contactsFromServer);
        contactList.forEach(addressBook::addContact);

        
        assertFalse(addressBook.getContacts().isEmpty());
        System.out.println("Contacts retrieved from JSON Server and added to memory:");
        addressBook.getContacts().forEach(System.out::println);
    }
}