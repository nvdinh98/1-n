package codegym.service.categoryService;

import codegym.config.ConnectionSingleton;
import codegym.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryService implements ICategoryService {
    Connection connection = ConnectionSingleton.getConnection();


    @Override
    public void insert(Category category) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("insert into category(name, description) VALUES (?,?);");
            preparedStatement.setString(1,category.getName());
            preparedStatement.setString(2,category.getDescription());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category findById(int id) {
        PreparedStatement preparedStatement = null;
        Category category = null;
        try {
            preparedStatement = connection.prepareStatement("select  * from category where id=?;" );
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                category = new Category(id,name,description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
            return category;
    }

    @Override
    public boolean update(Category category) {
        boolean rowUpdate = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update category set name=?,description=?;");
            preparedStatement.setString(1,category.getName());
            preparedStatement.setString(2,category.getDescription());
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
            PreparedStatement preparedStatement = connection.prepareStatement("delete from  category where id=?;");
            preparedStatement.setInt(1,id);
            rowDelete = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
       return rowDelete;
    }

    @Override
    public List<Category> findAll() {
       List<Category> categoryList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select  * from category");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                categoryList.add(new Category(id,name,description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }
}
