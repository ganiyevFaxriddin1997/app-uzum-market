package org.example;

import org.example.service.card.CardService;
import org.example.service.card.CardServiceImp;
import org.example.entities.Card;
import org.example.entities.User;
import org.example.enums.CardType;
import org.example.enums.Gender;
import org.example.enums.Role;
import org.example.front.ManagerFront;
import org.example.front.ProductOwnerFront;
import org.example.front.SystemOwnerFront;
import org.example.front.UserFront;
import org.example.service.user.UserService;
import org.example.service.user.UserServiceImp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Main {
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);
    static UserService userService = new UserServiceImp();
    static CardService cardService = new CardServiceImp();

    public static void main(String[] args) throws Exception {

        createOwner();

        while (true) {
            System.out.println("1.Catalog\t\t2.Sign up\t\t3.Sign in\t\t0.Exit");
            int option = scannerInt.nextInt();
            if (option == 1) {
                UserFront.enterCatalog();
            } else if (option == 2) {
                signUp();
            } else if (option == 3) {
                signIn();
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Wrong option!");
            }
        }
    }

    private static void createOwner() throws SQLException {

        User user = User.builder()
                .name("Uzum market")
                .phoneNumber("000")
                .role(Role.OWNER)
                .gender(Gender.MALE)
                .build();
        int systemOwnerId = userService.create(user);

        Card card = Card.builder()
                .serialNumber("0000")
                .cardType(CardType.UZCARD)
                .date(Timestamp.valueOf("2030-12-31 00:00:00"))
                .password(1001)
                .balance(1000000)
                .ownerId(systemOwnerId)
                .build();

        cardService.create(card);

    }

    private static void signIn() throws SQLException {
        System.out.println("Enter your phone number: ");
        String phoneNumber = scannerStr.nextLine();
        User currentUser = userService.getByPhone(phoneNumber);
        if (currentUser != null) {
            if (currentUser.getRole() == Role.ADMIN) {
                while (true) {
                    System.out.println("1.ManagerFront\t\t2.UserFront\t\t 0.Exit");
                    int option = scannerInt.nextInt();
                    if (option == 1) {
                        ManagerFront.CategoryPage(currentUser);
                    } else if (option == 2) {
                        UserFront.UserPage(currentUser);
                    } else if (option == 0) {
                        break;
                    } else {
                        System.out.println("Wrong option!");
                    }
                }
            } else if (currentUser.getRole() == Role.PRODUCT_OWNER) {
                while (true) {
                    System.out.println("1.UserFront\t\t2.ProductOwnerPage\t\t 0.Exit");
                    int option = scannerInt.nextInt();
                    if (option == 1) {
                        UserFront.UserPage(currentUser);
                    } else if (option == 2) {
                        ProductOwnerFront.ProductOwnerPage(currentUser);
                    } else if (option == 0) {
                        break;
                    } else {
                        System.out.println("Wrong option!");
                    }
                }
            } else if (currentUser.getRole() == Role.USER) {
                UserFront.UserPage(currentUser);
            } else if (currentUser.getRole() == Role.OWNER) {
                SystemOwnerFront.CategoryPage(currentUser);
            }
        } else {
            System.out.println("User not found");
        }
    }


    private static void signUp() throws SQLException {
        System.out.println("Enter your name : ");
        String name = scannerStr.nextLine();
        if (name.length() != 0) {
            System.out.println("Enter phone number: ");
            String phoneNumber = scannerStr.nextLine();
            if (phoneNumber.length() != 0) {
                User userByPhone = userService.getByPhone(phoneNumber);
                if (userByPhone == null) {
                    System.out.println("Enter gender [1.MALE,2.FEMALE]: ");
                    int i = scannerInt.nextInt();

                    String gender = "";
                    if (i == 1) {
                        gender = Gender.MALE.name();
                    } else if (i == 2) {
                        gender = Gender.FEMALE.name();
                    }
                    if (Gender.getGender(gender)!=null) {
                        var user = User
                                .builder()
                                .name(name)
                                .phoneNumber(phoneNumber)
                                .gender(Gender.getGender(gender))
                                .role(Role.USER)
                                .build();
                        int result = userService.create(user);
                        if (result > 0) {
                            System.out.println("User was created");
                        } else {
                            System.out.println("Error!");
                        }
                    }else {
                        System.out.println("Wrong gender!");
                    }

                } else {
                    System.out.println("Such user with this phone number already exist!");
                }
            } else {
                System.out.println("You didn't entered phone number");
            }
        } else {
            System.out.println("You didn't entered anything!");
        }
    }
}
