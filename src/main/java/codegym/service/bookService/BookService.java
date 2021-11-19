package codegym.service.bookService;

import codegym.config.ConnectionSingleton;
import codegym.model.Book;
import codegym.model.Category;
import codegym.service.categoryService.CategoryService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService implements IBookService{
    Connection connection = ConnectionSingleton.getConnection();
    CategoryService categoryService = new CategoryService();

    @Override
    public void insert(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into books(name, description, id_category) VALUES (?,?,?);");
            preparedStatement.setString(1,book.getName());
            preparedStatement.setString(2,book.getDescription());
            preparedStatement.setInt(3,book.getCategory().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book findById(int id) {
        Book book = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where id=?;");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int id_category = resultSet.getInt("id_category");
                Category category = categoryService.findById(id_category);
                book = new Book(id,name,description,category);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return book;
    }

    @Override
    public boolean update(Book book) {
        boolean rowUpdate = false;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("update books set name=?,description=?,id_category=? where id=?;");
            preparedStatement.setString(1,book.getName());
            preparedStatement.setString(2,book.getDescription());
            preparedStatement.setInt(3,book.getCategory().getId());
           preparedStatement.setInt(4,book.getId());
           rowUpdate = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdate;

    }

    @Override
    public boolean delete(int id) {
        boolean rowDelete = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from books where id=?;");
            preparedStatement.setInt(1,id);
            rowDelete = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
            return rowDelete;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select  * from books");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id =resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int id_category = resultSet.getInt("id_category");
                Category category = categoryService.findById(id_category);
                bookList.add(new Book(id,name,description,category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public List<Book> sortByName(){
        List<Book> bookList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books order by name;");
            ResultSet rs  = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int id_category = rs.getInt("id_category");
                Category category = categoryService.findById(id_category);
                bookList.add(new Book(id,name,description,category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

}
