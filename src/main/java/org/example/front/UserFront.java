package org.example.front;


import org.example.card.CardService;
import org.example.card.CardServiceImp;
import org.example.category.CategoryService;
import org.example.category.CategoryServiceImp;
import org.example.dto.ResponseOfBasket;
import org.example.entities.*;
import org.example.enums.CardType;
import org.example.order.OrderService;
import org.example.order.OrderServiceImp;
import org.example.payment.PaymentService;
import org.example.payment.PaymentServiceImp;
import org.example.subCategory.SubCategoryService;
import org.example.subCategory.SubCategoryServiceImp;
import org.example.user.UserService;
import org.example.user.UserServiceImp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.front.ManagerFront.*;

public class UserFront {
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);
    static UserService userService = new UserServiceImp();
    static CategoryService categoryService = new CategoryServiceImp();
    static SubCategoryService subCategoryService = new SubCategoryServiceImp();
    static OrderService orderService = new OrderServiceImp();
    static CardService cardService = new CardServiceImp();
    static PaymentService paymentService = new PaymentServiceImp();

    public static void UserPage(User currentUser) throws SQLException {
        while (true) {
            System.out.println("1.Catalog\t\t2.Search\t\t3.History\t\t4.Basket\t\t5.Card\t\t0.Exit");
            int option = scannerInt.nextInt();
            if (option == 1) {
                enterToCategory(currentUser);
            } else if (option == 2) {
                searchProduct(currentUser);
            } else if (option == 3) {
                paymentHistory(currentUser);
            } else if (option == 4) {
                enterToBasket(currentUser);
            } else if (option == 5) {
                card(currentUser);
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Wrong option!");
            }
        }
    }

    private static void paymentHistory(User currentUser) throws SQLException {

        Card card = cardService.getByOwnerId(currentUser.getId());
        List<Integer> paymentIds = paymentService.getPaymentIds(currentUser.getId());

        for (Integer paymentId : paymentIds) {
            List<Integer> orderFromPaymentOrder = paymentService.getOrderFromPaymentOrder(paymentId);
            for (Integer integer : orderFromPaymentOrder) {
                Order order = orderService.get(integer);
                Product product = productService.get(order.getProductId());
                System.out.println(product.getName());
                System.out.println(order.getAmountProduct());
            }
        }



//        List<Payment> newPayment = new ArrayList<>();
//
//        List<Payment> paymentList = paymentService.getAll();
//
//        for (int i = 0; i < paymentList.size(); i++) {
//
//            List<Integer> orderIds = paymentService.getOrderFromPaymentOrder(paymentList.get(i).getId());
//            List<Order> orders = new ArrayList<>();
//            for (Integer orderId : orderIds) {
//
//                Order order = orderService.get(orderId);
//                if (order.getUserId() == currentUser.getId()) {
//                    orders.add(order);
//                }
//            }
//
//            Payment payment1 = Payment.builder()
//                    .id(paymentList.get(i).getId())
//                    .cardId(paymentList.get(i).getCardId())
//                    .orders(orders)
//                    .price(paymentList.get(i).getPrice())
//                    .createdDate(paymentList.get(i).getCreatedDate())
//                    .build();
//            newPayment.add(payment1);
//        }
//        int cnt = 1;
//        for (Payment payment : newPayment) {
//            System.out.println(payment.getPrice() + " $ => date: " + payment.getCreatedDate());
//            cnt++;
//        }
    }

    private static void searchProduct(User currentUser) throws SQLException {
        System.out.println("Enter product name:");
        String productName = scannerStr.nextLine();
        Product product = productService.getProductWithName(productName);
        if (product != null) {
            System.out.println(
                    " name: " + product.getName() +
                            " => price: " + product.getPrice() +
                            " => description: " + product.getDescription() +
                            " => color: " + product.getColor() +
                            " => size: " + product.getSize() +
                            " => brand: " + product.getBrand() +
                            " => amount of product: " + product.getAmount());

            System.out.println("Amount product in base: " + product.getAmount());
            System.out.println("Enter amount of product to purchase: ");
            int amount = scannerInt.nextInt();
            if (product.getAmount() >= amount) {
                int orderId = orderService.createOrder(currentUser.getId(), product.getId(), amount);
                if (orderId != 0) {
                    int i = orderService.checkOrderFromBasket(currentUser.getId(), product.getName());
                    if (i < 0) {
                        boolean result = productService.addToBasket(currentUser.getId(), orderId);
                        if (result) {
                            System.out.println("Added to basket");
                        } else {
                            System.out.println("Error");
                        }

                    } else {
                        System.out.println("Such order already exist");
                    }
                } else {
                    System.out.println("Error");
                }
            } else {
                System.out.println("Product is not enough in base to purchase. Enter other amount!");
            }
        } else {
            System.out.println("Such product was not found");
        }
    }

    private static void card(User currentUser) throws SQLException {
        while (true) {
            System.out.println("1.Create card\t\t2.See balance\t\t0.Exit");
            int option = scannerInt.nextInt();
            if (option == 1) {
                createCard(currentUser);
            } else if (option == 2) {
                seeBalanceOfUser(currentUser);
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Wrong option");
            }
        }
    }

    private static void createCard(User currentUser) throws SQLException {
        System.out.println("Enter serial number: ");
        String serialNumber = scannerStr.nextLine();

        System.out.println("Choose type of card [1.HUMO, 2.VISA, 3.UZCARD]");
        int i = scannerInt.nextInt();
        String type = "";
        if (i == 1) {
            type = CardType.HUMO.name();
        } else if (i == 2) {
            type = CardType.VISA.name();
        } else if (i == 3) {
            type = CardType.UZCARD.name();
        } else {
            System.out.println("Wrong option!");
        }

        System.out.println("Enter month:");
        String month = scannerStr.nextLine();
        System.out.println("Enter year:");
        String year = scannerStr.nextLine();
        String date = year + "-" + month + "-01 00:00:00";
        Timestamp timestamp = Timestamp.valueOf(date);

        System.out.println("Enter password: ");
        int password = scannerInt.nextInt();

        System.out.println("Enter balance: ");
        double balance = scannerDbl.nextDouble();

        var card = Card.builder()
                .serialNumber(serialNumber)
                .cardType(CardType.getCardType(type))
                .date(timestamp)
                .password(password)
                .balance(balance)
                .ownerId(currentUser.getId())
                .build();
        int result = cardService.create(card);
        if (result != 0) {
            System.out.println("Card was created!");
        } else {
            System.out.println("Error");
        }
    }

    private static void seeBalanceOfUser(User currentUser) throws SQLException {
        System.out.println("Enter number: ");
        String number = scannerStr.nextLine();
        Card card = cardService.getCardBySerialNumber(number);
        if (card != null && card.getOwnerId() == currentUser.getId()) {
            System.out.println("Your balance: " + card.getBalance());
        } else {
            System.out.println("Card was not found");
        }

    }

    private static void enterToBasket(User currentUser) throws SQLException {
        List<ResponseOfBasket> productsFromBasket = productService.getProductsFromBasket(currentUser.getId());
        int[] cnt = {1};
        productsFromBasket.forEach(product -> {
            System.out.println(cnt[0] +
                    ". name: " + product.getName() +
                    " => price: " + product.getPrice() +
                    " => description: " + product.getDescription() +
                    " => color: " + product.getColor() +
                    " => size: " + product.getSize() +
                    " => brand: " + product.getBrand() +
                    " => amount of product: " + product.getAmountProduct());
            cnt[0]++;
        });

        double sumOfPriceFromBasket = productService.getSumOfPriceFromBasket(currentUser.getId());
        if (sumOfPriceFromBasket != 0) {
            System.out.println("Price of products: " + sumOfPriceFromBasket);
            while (true) {
                System.out.println("1.Buy\t\t2.Add amount to order\t\t3.Eliminate order from basket\t\t0.Exit");
                int option = scannerInt.nextInt();
                if (option == 1) {
                    createPayment(currentUser);
                    break;
                } else if (option == 2) {
                    addAmountToOrder(currentUser);
                } else if (option == 3) {
                    eliminateOrderFromBasket(currentUser);
                } else if (option == 0) {
                    break;
                } else {
                    System.out.println("Wrong option!");
                }
            }
        } else {
            System.out.println("There is not products in the basket");
        }
    }

    private static void eliminateOrderFromBasket(User currentUser) throws SQLException {
        List<ResponseOfBasket> productsFromBasket = productService.getProductsFromBasket(currentUser.getId());
        int[] cnt = {1};
        productsFromBasket.forEach(product -> {
            System.out.println(cnt[0] +
                    ". name: " + product.getName() +
                    " => price: " + product.getPrice() +
                    " => description: " + product.getDescription() +
                    " => color: " + product.getColor() +
                    " => size: " + product.getSize() +
                    " => brand: " + product.getBrand() +
                    " => amount of product: " + product.getAmountProduct());
            cnt[0]++;
        });

        System.out.println("Enter name of product: ");
        String nameProduct = scannerStr.nextLine();
        boolean result = orderService.deleteOrderFromBasket(currentUser.getId(), nameProduct);
        if (result) {
            System.out.println("Product was deleted from basket");
        } else {
            System.out.println("Error");
        }
    }

    private static void addAmountToOrder(User currentUser) throws SQLException {
        List<ResponseOfBasket> productsFromBasket = productService.getProductsFromBasket(currentUser.getId());
        int[] cnt = {1};
        productsFromBasket.forEach(product -> {
            System.out.println(cnt[0] +
                    ". name: " + product.getName() +
                    " => price: " + product.getPrice() +
                    " => description: " + product.getDescription() +
                    " => color: " + product.getColor() +
                    " => size: " + product.getSize() +
                    " => brand: " + product.getBrand() +
                    " => amount of product: " + product.getAmountProduct());
            cnt[0]++;
        });

        System.out.println("Enter name of product: ");
        String nameProduct = scannerStr.nextLine();
        var responseOfBasket = productService.get(currentUser.getId(), nameProduct);
        if (responseOfBasket != null) {
            var product = productService.getProductWithName(nameProduct);
            if (product != null) {
                System.out.println("Enter amount");
                int amount = scannerInt.nextInt();
                boolean result = orderService.changeOrder(currentUser.getId(), product.getId(), amount);
                if (result) {
                    System.out.println("Amount was changed!");
                } else {
                    System.out.println("Error");
                }
            } else {
                System.out.println("Product was not found");
            }

        } else {
            System.out.println("Such product was not found");
        }


    }

    private static void createPayment(User currentUser) throws SQLException {
        System.out.println("Enter serial number of card: ");
        String serialNumber = scannerStr.nextLine();

        Card cardBySerialNumber = cardService.getCardBySerialNumber(serialNumber);
        if (cardBySerialNumber!=null) {
            if (cardBySerialNumber.getOwnerId() == currentUser.getId()) {
                double sumOfPriceFromBasket = productService.getSumOfPriceFromBasket(currentUser.getId());

                var payment = Payment.builder()
                        .cardId(cardBySerialNumber.getId())
                        .price(sumOfPriceFromBasket)
                        .build();
                int id = paymentService.create(payment);
                if (id > 0) {
                    List<Integer> orderIdFromBasket = productService.getOrderIdFromBasket(currentUser.getId());
                        boolean result = false;

                    for (int i = 0; i < orderIdFromBasket.size(); i++) {

                        paymentService.createPaymentOrder(currentUser.getId(), orderIdFromBasket.get(i));
                        Order order = orderService.get(orderIdFromBasket.get(i));
                        Product product = productService.get(order.getProductId());

                        User owner = userService.getByRole("OWNER");
                        Card ownerCard = cardService.getByOwnerId(owner.getId());

                        result = paymentService.takeMoneyFromUserToProductOwner(
                                serialNumber,
                                product.getPrice(),
                                product.getOwnerId(),
                                order.getAmountProduct(),
                                order.getProductId(),
                                ownerCard.getOwnerId());
                    }
                        if (result) {
                            System.out.println("Success!");
                        } else {
                            System.out.println("Fail");
                        }

                    boolean result1 = orderService.cleanBasket(currentUser.getId());

                } else {
                    System.out.println("Error");
                }
            } else {
                System.out.println("This card is not yours!");
            }
        }else {
            System.out.println("Card not found!!!");
        }
    }

    public static void enterCatalog() throws SQLException {
        List<Category> categories = categoryService.getAll();
        int[] cnt = {1};
        categories.forEach(category -> {
            System.out.println(cnt[0] + ". name: " + category.getName());
            cnt[0]++;
        });

        System.out.println("Enter category: ");
        String categoryName = scannerStr.nextLine();

        var category = categoryService.get(categoryName);
        if (category != null) {
            List<SubCategory> subCategories = subCategoryService.getAll();
            int[] cnt1 = {1};
            subCategories.forEach(subCategory -> {
                System.out.println(cnt1[0] + ". name: " + subCategory.getName());
                cnt1[0]++;
            });
            System.out.println("Enter sub category: ");
            String subCategory = scannerStr.nextLine();
            var subCategory1 = subCategoryService.get(subCategory);
            if (subCategory1 != null) {
                List<Product> products = productService.getAll();
                int[] cnt2 = {1};
                products.forEach(product -> {
                    System.out.println(cnt2[0] +
                            ". name:" + product.getName() +
                            " => price: " + product.getPrice() +
                            " => description: " + product.getDescription() +
                            " => color: " + product.getColor() +
                            " => size: " + product.getSize() +
                            " => amount: " + product.getAmount() +
                            " => brand: " + product.getBrand()
                    );
                    cnt2[0]++;
                });
            } else {
                System.out.println("Sub category was not found");
            }
        } else {
            System.out.println("Category was not found!");
        }
    }


    private static void enterToCategory(User currentUser) throws SQLException {

        seeCategory(currentUser);
        System.out.println("Enter category name: ");
        String name = scannerStr.nextLine();
        var category = categoryService.get(name);
        if (category != null) {
            seeSubCategory(currentUser, category.getId());
            List<SubCategory> subCategories = subCategoryService.subCategoriesByCategoryName(name);
            if (subCategories != null&&subCategories.size()!=0){
            System.out.println("Enter sub category name: ");
            String subCategoryName = scannerStr.nextLine();
            var subCategory = subCategoryService.get(subCategoryName);
            if (subCategory != null) {
                seeProduct(currentUser);
                System.out.println("Enter name of product: ");
                String productName = scannerStr.nextLine();
                var product = productService.get(productName);
                if (product != null) {
                    System.out.println("Amount product in base: " + product.getAmount());
                    System.out.println("Enter amount of product to purchase: ");
                    int amount = scannerInt.nextInt();
                    if (product.getAmount() >= amount) {
                        int orderId = orderService.createOrder(currentUser.getId(), product.getId(), amount);
                        if (orderId != 0) {
                            int i = orderService.checkOrderFromBasket(currentUser.getId(), product.getName());
                            if (i < 0) {
                                boolean result = productService.addToBasket(currentUser.getId(), orderId);
                                if (result) {
                                    System.out.println("Added to basket");
                                } else {
                                    System.out.println("Error");
                                }

                            } else {
                                System.out.println("Such order already exist");
                            }
                        } else {
                            System.out.println("Error");
                        }
                    } else {
                        System.out.println("Product is not enough in base to purchase. Enter other amount!");
                    }
                } else {
                    System.out.println("Product not found");
                }
            } }else {
                System.out.println("Sub category not found");
            }
        } else {
            System.out.println("Category not found");
        }
    }

}

