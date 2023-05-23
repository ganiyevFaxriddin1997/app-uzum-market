package org.example.front;

import org.example.service.category.CategoryService;
import org.example.service.category.CategoryServiceImp;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.SubCategory;
import org.example.entities.User;
import org.example.enums.Role;
import org.example.service.product.ProductService;
import org.example.service.product.ProductServiceImp;
import org.example.service.subCategory.SubCategoryService;
import org.example.service.subCategory.SubCategoryServiceImp;
import org.example.service.user.UserService;
import org.example.service.user.UserServiceImp;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static org.example.front.SystemOwnerFront.seeSubCategory;

public class ManagerFront {
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);
    static Scanner scannerDbl = new Scanner(System.in);
    static UserService userService = new UserServiceImp();
    static CategoryService categoryService = new CategoryServiceImp();
    static SubCategoryService subCategoryService = new SubCategoryServiceImp();
    static ProductService productService = new ProductServiceImp();

    public static void CategoryPage(User currentUser) throws SQLException {
        while (true) {
            System.out.println("1.Category\t\t2.Sub category\t\t3.Product\t\t4.User\t\t0.Exit");
            int option = scannerInt.nextInt();
            if (option == 1) {
                workWithCategory(currentUser);
            } else if (option == 2) {
                workWithSubCategory(currentUser);
            } else if (option == 3) {
                workWithProduct(currentUser);
            } else if (option == 4) {
                workWithUsers(currentUser);
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Wrong option");
            }

        }
    }

    private static void workWithSubCategory(User currentUser) throws SQLException {
        while (true) {
            System.out.println("1.See sub category\t\t2.Add sub category\t\t3.Delete sub category\t\t0.Exit");
            int option = scannerInt.nextInt();
            if (option == 1) {
                int i = enterSubcategory();
                seeSubcategory(currentUser, i);
            } else if (option == 2) {
                addSubCategory(currentUser);
            } else if (option == 3) {
                int categoryId = enterSubcategory();
                deleteSubCategory(currentUser, categoryId);
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Wrong option!");
            }
        }
    }

    static void seeSubcategory(User currentUser, int categoryId) throws SQLException {
        Category category = categoryService.get(categoryId);
        int cnt = 1;
        if (category != null) {
            List<SubCategory> subCategories = subCategoryService.subCategoriesByCategoryName(category.getName());
            if (subCategories.size()!=0) {
                for (SubCategory subCategory : subCategories) {
                    System.out.println(cnt + ". SubCategory name: " + subCategory.getName() +
                            " => category:" + category.getName());
                    cnt++;
                }
            }else {
                System.out.println("Sub category not found");
            }
        } else {
            System.out.println("Category was not found");
        }
    }

    private static int enterSubcategory() throws SQLException {
        List<Category> categories = categoryService.getAll();
        int cnt = 1;
        for (Category category : categories) {
            System.out.println(cnt + ". name: " + category.getName());
        }
        System.out.print("enter category name: ");
        String name = scannerStr.nextLine();
        Category category = categoryService.get(name);
        if (category != null) return category.getId();
        return -1;
    }

    private static void workWithUsers(User currentUser) throws SQLException {
        while (true) {
            System.out.println("1.Delete user\t\t2.Giving role\t\t0.Exit");
            int option = scannerInt.nextInt();
            if (option == 1) {
                deleteUser(currentUser);
            } else if (option == 2) {
                givingRole(currentUser);
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Wrong option");
            }

        }

    }

    private static void deleteUser(User currentUser) throws SQLException {
        seeUsers();
        System.out.println("Enter user's ID: ");
        int userId = scannerInt.nextInt();
        if ((currentUser.getRole() == Role.OWNER || currentUser.getRole() == Role.ADMIN)) {
            boolean delete = userService.delete(userId);
            if (delete) {
                System.out.println("Deleted!");
            } else {
                System.out.println("Error!");
            }
        } else {
            System.out.println("You are not OWNER or ADMIN, Sorry! ");
        }
    }

    private static void givingRole(User currentUser) throws SQLException {
        seeUsers();
        if (currentUser.getRole() == Role.OWNER) {
            System.out.println("Enter user's ID: ");
            int userId = scannerInt.nextInt();
            System.out.println("Enter role name [1.OWNER, 2.ADMIN, 3.USER, 4.PRODUCT_OWNER]");
            int i = scannerInt.nextInt();
            String role = "";
            if (i == 1) {
                role = Role.OWNER.name();
            } else if (i == 2) {
                role = Role.ADMIN.name();
            } else if (i == 3) {
                role = Role.USER.name();
            } else if (i == 4) {
                role = Role.PRODUCT_OWNER.name();
            }
            boolean result = userService.changeRole(role, userId);
            if (result) {
                System.out.println("Role has been changed!");
            } else {
                System.out.println("Error");
            }
        } else {
            System.out.println(" Sorry, You are not OWNER!");
        }
    }

    private static void seeUsers() throws SQLException {
        List<User> users = userService.getAll();
        int[] cnt = {1};
        users.forEach(user -> {
            System.out.println(cnt[0] + ") id: " + user.getId() +
                    " => phone: " + user.getPhoneNumber() +
                    " => role: " + user.getRole() +
                    " => gender: " + user.getGender());
            cnt[0]++;
        });
    }

    private static void workWithProduct(User currentUser) throws SQLException {
        while (true) {
            System.out.println("1.See products\t\t2.Add product\t\t3.Delete product\t\t0.Exit");
            int option = scannerInt.nextInt();
            if (option == 1) {
                seeProduct(currentUser);
            } else if (option == 2) {
                addProduct(currentUser);
            } else if (option == 3) {
                deleteProduct(currentUser);
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Wrong option!");
            }
        }
    }

    private static void deleteProduct(User currentUser) throws SQLException {
        seeProduct(currentUser);
        System.out.print("Enter name of product: ");
        String name = scannerStr.nextLine();
        var product = productService.get(name);
        if (product != null && (currentUser.getRole().equals(Role.OWNER) || currentUser.getRole().equals(Role.ADMIN))) {
            boolean delete = productService.delete(product.getId());
            String response = delete ? "Deleted" : "Error";
            System.out.println(response);
        } else {
            System.out.println("Group not found");
        }
    }

    private static void addProduct(User currentUser) throws SQLException {
        System.out.print("Enter name of category: ");
        String categoryName = scannerStr.nextLine();
        Category category = categoryService.get(categoryName);
        if (category == null) return;
        seeSubcategory(currentUser, category.getId());
        System.out.print("Enter name of sub category: ");
        String name = scannerStr.nextLine();
        var subCategory = subCategoryService.get(name);
        System.out.println("Enter name of product: ");
        String nameProduct = scannerStr.nextLine();
        System.out.println("Enter price of product: ");
        double priceProduct = scannerDbl.nextDouble();
        System.out.println("Enter description of product: ");
        String descriptionProduct = scannerStr.nextLine();
        System.out.println("Enter color of product: ");
        String colorProduct = scannerStr.nextLine();
        System.out.println("Enter size of product: ");
        String sizeProduct = scannerStr.nextLine();
        System.out.println("Enter amount of product: ");
        int amountProduct = scannerInt.nextInt();
        System.out.println("Enter brand of product: ");
        String brandProduct = scannerStr.nextLine();
        System.out.println("Enter phone number of owner: ");
        String number = scannerStr.nextLine();

        User owner = userService.getByPhone(number);

        var product = Product
                .builder()
                .name(nameProduct)
                .price(priceProduct)
                .subCategoryId(subCategory.getId())
                .description(descriptionProduct)
                .color(colorProduct)
                .size(sizeProduct)
                .ownerId(owner.getId())
                .amount(amountProduct)
                .brand(brandProduct)
                .build();
        int productId = productService.create(product);
        var response = productId != -1 ? "Success" : "Error";
        System.out.println(response);
    }

    static void seeProduct(User currentUser) throws SQLException {
        List<Product> products = productService.getAll();
        int[] cnt = {1};
        System.out.println("<===========Products===========>");
        products.forEach(product -> {
            System.out.println(cnt[0] + "." +
                    " => name: " + product.getName() +
                    " => price: " + product.getPrice() +
                    " => description: " + product.getDescription() +
                    " => color: " + product.getColor() +
                    " => size: " + product.getSize() +
                    " => amount: " + product.getAmount() +
                    " => brand: " + product.getBrand());
            cnt[0]++;
        });

    }

    private static void deleteSubCategory(User currentUser, int categoryId) throws SQLException {
        Category category = categoryService.get(categoryId);
        if (category!=null) {
            System.out.print("Enter name of sub category: ");
            String subCategoryName = scannerStr.nextLine();
            var subCategory = subCategoryService.get(subCategoryName);
            if (subCategory != null && (currentUser.getRole().equals(Role.OWNER) || currentUser.getRole().equals(Role.ADMIN))) {
                boolean delete = subCategoryService.delete(subCategory.getId());
                String response = delete ? "success" : "fail";
                System.out.println(response);
            } else {
                System.out.println("Sub category was not found");
            }
        }else {
            System.out.println("Category was not found");
        }
    }

    private static void addSubCategory(User currentUser) throws SQLException {
        seeCategory(currentUser);
        System.out.print("Enter category name: ");
        String categoryName = scannerStr.nextLine();
        if (categoryName.length() != 0) {
            Category category = categoryService.get(categoryName);
            if (category != null) {
                System.out.println("Enter sub category name: ");
                String subCategoryName = scannerStr.nextLine();
                if (subCategoryName.length() != 0) {
                    var subCategory = SubCategory
                            .builder()
                            .name(subCategoryName)
                            .categoryId(category.getId())
                            .build();
                    int subCategoryId = subCategoryService.create(subCategory);
                    var response = subCategoryId != -1 ? "Sub category created!" : "Error";
                    System.out.println(response);
                } else {
                    System.out.println("You entered nothing for sub category!");
                }
            } else {
                System.out.println("Category was not found!");
            }
        } else {
            System.out.println("You entered nothing!");
        }
    }

    private static void workWithCategory(User currentUser) throws SQLException {
        while (true) {
            System.out.println("1.SeeCategory\t\t2.Add category\t\t3.Delete category\t\t0.Exit");
            int option = scannerInt.nextInt();
            if (option == 1) {
                seeCategory(currentUser);
            } else if (option == 2) {
                addCategory(currentUser);
            } else if (option == 3) {
                deleteCategory(currentUser);
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Wrong option!");
            }
        }
    }


    private static void deleteCategory(User currentUser) throws SQLException {
        seeCategory(currentUser);
        System.out.print("Enter name of category: ");
        String name = scannerStr.nextLine();
        var category = categoryService.get(name);
        if (category != null && (currentUser.getRole().equals(Role.OWNER) || currentUser.getRole().equals(Role.ADMIN))) {
            boolean delete = categoryService.delete(category.getId());
            String response = delete ? "Deleted" : "Error";
            System.out.println(response);
        } else {
            System.out.println("Category was not found");
        }
    }

    static void seeCategory(User currentUser) throws SQLException {
        List<Category> categories = categoryService.getAll();
        int[] cnt = {1};
        System.out.println("<===========Categories===========>");
        categories.forEach(category -> {
            System.out.println(cnt[0] + "." + category.getName());
            cnt[0]++;
        });
    }

    private static void addCategory(User currentUser) throws SQLException {
        System.out.print("Enter name of category: ");
        String name = scannerStr.nextLine();
        if (name.length() != 0) {
            var category = Category
                    .builder()
                    .name(name)
                    .build();
            int categoryId = categoryService.create(category);
            var response = categoryId != -1 ? "Category was created" : "Error";
            System.out.println(response);
        } else {
            System.out.println("You didn't enter anything!");
        }
    }
}
