package recipesearch;
import java.io.FileNotFoundException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import recipesearch.RecipeSearchController;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ListItem extends AnchorPane {

    private RecipeSearchController parentController;
    private Recipe recipe;
    @FXML   ImageView ItemPicture;
    @FXML   ImageView ItemPicture2;
    @FXML   ImageView ItemPicture3;
    @FXML   ImageView ItemPicture4;
    @FXML   Text ItemName;
    @FXML   Text ItemDesc;
    @FXML   Text ItemTime;
    @FXML   ImageView ItemTimeP;
    @FXML   Text ItemCost;

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
        this.ItemDesc.setText(recipe.getDescription());
        this.ItemTime.setText(recipe.getTime()+" min");
        this.ItemCost.setText(recipe.getPrice()+" kr");

        try {
            this.ItemTimeP.setImage(recipeSearchController.getTimeIcon());
        } catch (FileNotFoundException e){}

        this.ItemPicture.setImage(recipe.getFXImage());
        try {
            this.ItemPicture2.setImage(recipeSearchController.getCuisineImage(recipe.getCuisine()));
        } catch (FileNotFoundException e){}

        try {
            this.ItemPicture4.setImage(recipeSearchController.getDifficultyImage(recipe.getDifficulty()));
        } catch (FileNotFoundException e){}

        try {
            this.ItemPicture3.setImage(recipeSearchController.getMainIngredientImage(recipe.getMainIngredient()));
        } catch (FileNotFoundException e){}


        this.parentController = recipeSearchController;
    }
    
    @FXML
    protected void onClick(Event event){
        parentController.RecipeDetailView(recipe);
    }
}

