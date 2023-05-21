package org.example.front;

import org.example.card.CardService;
import org.example.card.CardServiceImp;
import org.example.entities.Card;
import org.example.entities.Product;
import org.example.entities.User;
import org.example.product.ProductService;
import org.example.product.ProductServiceImp;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProductOwnerFront {
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);
    static Scanner scannerDbl = new Scanner(System.in);
    static ProductService productService = new ProductServiceImp();
    static CardService cardService = new CardServiceImp();

    public static void ProductOwnerPage(User currentUser) throws SQLException {
        while (true) {
            System.out.println("1.See product\t\t2.Account\t\t0.Exit");
            int option = scannerInt.nextInt();
            if (option == 1) {
                seeProduct(currentUser);
            } else if (option == 2) {
                balance(currentUser);
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Wrong option");
            }

        }
    }

    private static void balance(User currentUser) throws SQLException {
        System.out.println("Enter serial number: ");
        String number = scannerStr.nextLine();
        Card card = cardService.getCardBySerialNumber(number);
        if (card != null && card.getOwnerId()==currentUser.getId()) {
            System.out.println("Your balance: " + card.getBalance());
        }else {
            System.out.println("Card was not found");
        }
    }

    static void seeProduct(User currentUser) throws SQLException {
        List<Product> products = productService.getAll();
        int[] cnt = {1};
        products.forEach(product -> {
            if (product.getOwnerId() == currentUser.getId()) {
                System.out.println(cnt[0] + ") id: " + product.getId() +
                        " => name: " + product.getName() +
                        " => price: " + product.getPrice() +
                        " => description: " + product.getDescription() +
                        " => color: " + product.getColor() +
                        " => size: " + product.getSize() +
                        " => amount: " + product.getAmount() +
                        " => brand: " + product.getBrand());
                cnt[0]++;
            }
        });
    }
}
