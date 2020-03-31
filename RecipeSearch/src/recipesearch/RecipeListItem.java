package recipesearch;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.File;
import java.io.IOException;

public class RecipeListItem extends AnchorPane {

    private RecipeSearchController parentController;
    private Recipe recipe;
    @FXML   ImageView ItemPicture;
    @FXML   Text ItemName;

    public RecipeListItem(Recipe recipe, RecipeSearchController recipeSearchController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        //sets pic and name
        this.ItemName.setText(recipe.getName());
        this.ItemPicture.setImage(recipe.getFXImage());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.recipe = recipe;
        this.parentController = recipeSearchController;
    }
}

