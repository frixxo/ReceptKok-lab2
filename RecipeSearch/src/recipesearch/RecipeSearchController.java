
package recipesearch;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import recipesearch.ListItem.ListItem;
import se.chalmers.ait.dat215.lab2.Recipe;


public class RecipeSearchController implements Initializable {

    backendController backend=new backendController();
    @FXML
    ComboBox CuisineCombobox;
    ComboBox MainIngridientCombobox;

    RadioButton AllButton;
    RadioButton EasyButton;
    RadioButton MediumButton;
    RadioButton HardButton;

    Spinner     MaxPriceSpinner;
    Slider      MaxTimeSlider;


    ToggleGroup difficultyToggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboboxes();



        updateRecipeList();
    }


    public void updateMaxPrice(){
        backend.setMaxPrice((int) MaxPriceSpinner.getValue());
    }
    public void updateMaxTime(){
        backend.setMaxTime((int)MaxTimeSlider.getValue());
    }

    private void updateRecipeList(){

        //recipeListFlowPane.getChildren().clear();

        for(Recipe r:backend.getRecipes()){
            new ListItem(r,this);
            //recipeListFlowPane.getChildren().add();
        }
    }

    void initializeComboboxes(){

        CuisineCombobox.getItems().addAll("Visa alla", "Apa", "Bepa", "Cepa", "Depa");

        CuisineCombobox.getSelectionModel().select("Visa alla");

        CuisineCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backend.setMainIngredient(newValue);
                updateRecipeList();
            }
        });


        MainIngridientCombobox.getItems().addAll("Visa alla", "Apa", "Bepa", "Cepa", "Depa");

        MainIngridientCombobox.getSelectionModel().select("Visa alla");

        MainIngridientCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backend.setMainIngredient(newValue);
                updateRecipeList();
            }
        });

    }
    void initializeRadiobuttons(){
        difficultyToggleGroup = new ToggleGroup();              //skapar togglegroup

        AllButton.setToggleGroup(difficultyToggleGroup);        //lägger till alla knappar i gruppen
        EasyButton.setToggleGroup(difficultyToggleGroup);
        MediumButton.setToggleGroup(difficultyToggleGroup);
        HardButton.setToggleGroup(difficultyToggleGroup);

        AllButton.setSelected(true);                            //väljer All som standard


        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    backend.setDifficulty(selected.getText());
                    updateRecipeList();
                }
            }
        });
    }

    void initializeSpinner(){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 100, 1);

    }
}