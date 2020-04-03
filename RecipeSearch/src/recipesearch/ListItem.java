package recipesearch;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import recipesearch.RecipeSearchController;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.File;
import java.io.IOException;

public class ListItem extends AnchorPane {

    private RecipeSearchController parentController;
    private Recipe recipe;
    @FXML   ImageView ItemPicture;
    @FXML   Text ItemName;

    public ListItem(Recipe recipe, RecipeSearchController recipeSearchController){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);



        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        //sets pic and name
        this.recipe=recipe;
        String s=recipe.getName();
        this.ItemName.setText(s);
        this.ItemPicture.setImage(recipe.getFXImage());


        this.parentController = recipeSearchController;
    }
}

