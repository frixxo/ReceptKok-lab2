
package recipesearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.lab2.Recipe;


public class RecipeSearchController implements Initializable {

    backendController backend=new backendController();
    ToggleGroup difficultyToggleGroup;

    private Map<String, ListItem> recipeListItemMap = new HashMap<String, ListItem>();

    @FXML public ComboBox CuisineCombobox;
    @FXML public ComboBox MainIngridientCombobox;

    @FXML public RadioButton AllButton;
    @FXML public RadioButton EasyButton;
    @FXML  public RadioButton MediumButton;
    @FXML public RadioButton HardButton;

    @FXML public Spinner     MaxPriceSpinner;
    @FXML public Slider      MaxTimeSlider;

    @FXML public FlowPane recipeListFlowPane;

    @FXML public AnchorPane dispAnchor;
    @FXML public ImageView dispImage;
    @FXML public Label dispLable;
    @FXML public Button dispClose;

    @FXML public ImageView logoImage;
    @FXML public ImageView easyDiffImage;
    @FXML public ImageView mediumDiffImage;
    @FXML public ImageView hardDiffImage;

    @FXML public ImageView timeImg;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboboxes();
        try {
            initializeRadiobuttons();

            File file=new File("resources");
            String filePath= file.getAbsolutePath();

            logoImage.setImage(new Image(new FileInputStream(filePath+"/logo.png")));
            timeImg.setImage(new Image(new FileInputStream(filePath+"/icon_time.png")));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initializeSlider();
        initializeSpinner();

        for (Recipe recipe: backend.getRecipes())
        {
            ListItem recipeListItem = new ListItem(recipe, this);
            recipeListItemMap.put(recipe.getName(), recipeListItem);
        }

        updateRecipeList();
    }

    private void updateRecipeList(){

        recipeListFlowPane.getChildren().clear();

        for(Recipe r:backend.getRecipes()){
            recipeListFlowPane.getChildren().add(recipeListItemMap.get(r.getName()));
        }
    }


    @FXML
    public void closeRecipeView(){
        dispAnchor.toBack();
    }
    
    void RecipeDetailView (Recipe recipe)
    {
        dispLable.setText(recipe.getName());
        dispImage.setImage(recipe.getFXImage());
    }

    //region Initializers
    private void initializeComboboxes(){

        CuisineCombobox.getItems().addAll("Visa alla", "Sverige", "Grekland", "Indien", "Asien","Afrika","Frankrike");

        CuisineCombobox.getSelectionModel().select("Visa alla");

        CuisineCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backend.setCuisine(newValue);
                updateRecipeList();
            }
        });


        MainIngridientCombobox.getItems().addAll("Visa alla", "Kött", "Fisk", "Kyckling", "Vegetarisk");

        MainIngridientCombobox.getSelectionModel().select("Visa alla");

        MainIngridientCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                backend.setMainIngredient(newValue);
                updateRecipeList();
            }
        });

    }
    private void initializeRadiobuttons() throws FileNotFoundException {
        difficultyToggleGroup = new ToggleGroup();              //skapar togglegroup

        AllButton.setToggleGroup(difficultyToggleGroup);        //lägger till alla knappar i gruppen
        EasyButton.setToggleGroup(difficultyToggleGroup);
        MediumButton.setToggleGroup(difficultyToggleGroup);
        HardButton.setToggleGroup(difficultyToggleGroup);

        AllButton.setSelected(true);                            //väljer All som standard

        //fixar bilder
        File file=new File("resources");
        String filePath= file.getAbsolutePath();

        easyDiffImage.setImage(new Image(new FileInputStream(filePath+"/icon_difficulty_easy.png")));
        mediumDiffImage.setImage(new Image(new FileInputStream(filePath+"/icon_difficulty_medium.png")));
        hardDiffImage.setImage(new Image(new FileInputStream(filePath+"/icon_difficulty_hard.png")));

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

    private void initializeSpinner(){

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 100, 1);
        MaxPriceSpinner.setValueFactory(valueFactory);

        MaxPriceSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                backend.setMaxPrice(newValue);
                updateRecipeList();
            }
        });

        MaxPriceSpinner.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    Integer value = Integer.valueOf(MaxPriceSpinner.getEditor().getText());
                    backend.setMaxPrice(value);
                    updateRecipeList();
                }

            }
        });

    }
    private void initializeSlider(){

        MaxPriceSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                backend.setMaxPrice(newValue);
                updateRecipeList();
            }
        });
    }
    //endregion
}