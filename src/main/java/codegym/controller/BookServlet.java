package codegym.controller;

import codegym.model.Book;
import codegym.model.Category;
import codegym.service.bookService.BookService;
import codegym.service.bookService.IBookService;
import codegym.service.categoryService.CategoryService;
import codegym.service.categoryService.ICategoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookServlet" , urlPatterns = "/books")
public class BookServlet extends HttpServlet {
    private BookService iBookService = new BookService();
    private ICategoryService iCategoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null){
            action = "";
        } switch (action){
            case "create":
                showCreateForm(req,resp);
                break;
            case "edit":
                showEditForm(req,resp);
                break;
            case "delete":
                deleteForm(req,resp);
                break;
            case "view":
                showBookView(req,resp);
                break;
            case "sort":
                sortByName(req,resp);
                break;
            default:
                listBook(req,resp);
                break;
        }
    }

    private void sortByName(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("book/list.jsp");
        List<Book> bookList = iBookService.sortByName();
        req.setAttribute("bookList",bookList);
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showBookView(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("book/view.jsp");
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = iBookService.findById(id);
        req.setAttribute("book",book);
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteForm(HttpServletRequest req, HttpServletResponse resp) {
        int id =Integer.parseInt(req.getParameter("id"));
        iBookService.delete(id);
        try {
            resp.sendRedirect("/books");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = iBookService.findById(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("book/edit.jsp");
        req.setAttribute("book",book);
        req.setAttribute("categoryList",iCategoryService.findAll());
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("book/create.jsp");
        req.setAttribute("categoryList",iCategoryService.findAll());
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listBook(HttpServletRequest req, HttpServletResponse resp) {
    RequestDispatcher dispatcher = req.getRequestDispatcher("book/list.jsp");
    req.setAttribute("bookList",iBookService.findAll());
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null){
            action = "";
        } switch (action){
            case "create" :
                createBook(req,resp);
                break;
            case "edit":
                updateBook(req,resp);
                break;
        }
    }

    private void updateBook(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher= req.getRequestDispatcher("book/edit.jsp");
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        int id_category = Integer.parseInt(req.getParameter("id_category"));
        Category category = iCategoryService.findById(id_category);
        Book book = new Book(id,name,description,category);
        req.setAttribute("book",book);
        req.setAttribute("categoryList",iCategoryService.findAll());
        req.setAttribute("message","da update thanh cong");
        iBookService.update(book);
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createBook(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("book/create.jsp");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        int id_customer = Integer.parseInt(req.getParameter("id_customer"));
        Category category = iCategoryService.findById(id_customer);

        Book newBook = new Book(name,description,category);
        iBookService.insert(newBook);
        req.setAttribute("categoryList",iCategoryService.findAll());
        req.setAttribute("message","Da tao thanh cong");
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
