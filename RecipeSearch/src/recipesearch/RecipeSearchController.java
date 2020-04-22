
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
import javafx.util.Callback;
import se.chalmers.ait.dat215.lab2.Recipe;


public class RecipeSearchController implements Initializable {

    backendController backend=new backendController();
    ToggleGroup difficultyToggleGroup;

    private Map<String, ListItem> recipeListItemMap = new HashMap<String, ListItem>();

    @FXML public ComboBox       CuisineCombobox;
    @FXML public ComboBox       MainIngridientCombobox;

    @FXML public RadioButton    AllButton;
    @FXML public RadioButton    EasyButton;
    @FXML  public RadioButton   MediumButton;
    @FXML public RadioButton    HardButton;

    @FXML public Spinner        MaxPriceSpinner;
    @FXML public Slider         MaxTimeSlider;
    @FXML public Text           sliderText;

    @FXML public FlowPane       recipeListFlowPane;

    @FXML public AnchorPane     dispAnchor;
    @FXML public ImageView      dispImage;
    @FXML public Label          dispLable;
    @FXML public Button         dispClose;

    @FXML public ImageView logoImage;
    @FXML public ImageView easyDiffImage;
    @FXML public ImageView mediumDiffImage;
    @FXML public ImageView hardDiffImage;

    @FXML public ImageView timeImg;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboboxes();
        populateMainIngredientComboBox();
        populateCuisineComboBox();
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
        dispAnchor.toFront();
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

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 10);
        MaxPriceSpinner.setValueFactory(valueFactory);

        MaxPriceSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                Integer value = Integer.valueOf(MaxPriceSpinner.getEditor().getText());
                if(value>100) value=100;
                if(value<0) value=0;
                value-=value%10;
                MaxPriceSpinner.getEditor().setText(value+"");
                backend.setMaxPrice(value);
                updateRecipeList();
            }
        });


        MaxPriceSpinner.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){


                }
                else{
                    Integer value = Integer.valueOf(MaxPriceSpinner.getEditor().getText());
                    if(value>100) value=100;
                    if(value<0) value=0;
                    value-=value%10;
                    MaxPriceSpinner.getEditor().setText(value+"");
                    backend.setMaxPrice(value);
                    updateRecipeList();
                }

            }
        });

    }
    private void initializeSlider(){

        MaxTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sliderText.setText(newValue.intValue()+" Minuter");
                if(newValue != null && !newValue.equals(oldValue) && !MaxTimeSlider.isValueChanging()) {
                    backend.setMaxTime(newValue.intValue());
                    updateRecipeList();
                }

            }
        });
    }

    private void populateMainIngredientComboBox() {
        System.out.println("Apabepa");
        Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> p) {

                return new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setText(item);

                        if (item == null || empty) {
                            System.out.println("item" + item);

                            System.out.println("empty" + empty);
                            setGraphic(null);
                        } else {
                            Image icon = null;
                            String iconPath;
                            try {
                                switch (item) {

                                    case "Kött":
                                        iconPath = "/Users/joelOlausson/Documents/GitHub/Lab2/RecipeSearch/resources/icon_main_beef.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        System.out.println("Kött");
                                        break;
                                    case "Fisk":
                                        iconPath = "RecipeSearch/resources/icon_main_fish.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Kyckling":
                                        iconPath = "RecipeSearch/resources/icon_main_chicken.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Vegetarisk":
                                        iconPath = "RecipeSearch/resources/icon_main_veg.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                }
                            } catch (NullPointerException ex) {
                                //This should never happen in this lab but could load a default image in case of a NullPointer
                            }
                            ImageView iconImageView = new ImageView(icon);
                            iconImageView.setFitHeight(12);
                            iconImageView.setPreserveRatio(true);
                            setGraphic(iconImageView);
                        }
                    }
                };
            }
        };
        MainIngridientCombobox.setButtonCell(cellFactory.call(null));
        MainIngridientCombobox.setCellFactory(cellFactory);
    }
    private void populateCuisineComboBox() {
        Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> p) {

                return new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setText(item);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            Image icon = null;
                            String iconPath;
                            try {
                                switch (item) {
                                    case "Sverige":
                                        iconPath = "RecipeSearch/resources/icon_flag_sweden.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Grekland":
                                        iconPath = "RecipeSearch/resources/icon_flag_greece.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Indien":
                                        iconPath = "RecipeSearch/resources/icon_flag_india.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Asien":
                                        iconPath = "RecipeSearch/resources/icon_flag_asia.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Afrika":
                                        iconPath = "RecipeSearch/resources/icon_flag_africa.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                    case "Frankrike":
                                        iconPath = "RecipeSearch/resources/icon_flag_france.png";
                                        icon = new Image(getClass().getClassLoader().getResourceAsStream(iconPath));
                                        break;
                                }
                            } catch (NullPointerException ex) {
                                //This should never happen in this lab but could load a default image in case of a NullPointer
                            }
                            ImageView iconImageView = new ImageView(icon);
                            iconImageView.setFitHeight(12);
                            iconImageView.setPreserveRatio(true);
                            setGraphic(iconImageView);
                        }
                    }
                };
            }
        };
        CuisineCombobox.setButtonCell(cellFactory.call(null));
        CuisineCombobox.setCellFactory(cellFactory);
    }
    //endregion

    public Image getCuisineImage(String cuisine) throws FileNotFoundException {
        File file = new File("resources");
        String filePath = file.getAbsolutePath();

        switch (cuisine) {
            case "Sverige":
                filePath += "/icon_flag_sweden.png";
                return new Image(new FileInputStream(filePath));
            case "Frankrike":
                filePath += "/icon_flag_france.png";
                return new Image(new FileInputStream(filePath));
            case "Afrika":
                filePath += "/icon_flag_africa.png";
                return new Image(new FileInputStream(filePath));
            case "Asien":
                filePath += "/icon_flag_asia.png";
                return new Image(new FileInputStream(filePath));
            case "Grekland":
                filePath += "/icon_flag_greece.png";
                return new Image(new FileInputStream(filePath));
            case "Indien":
                filePath += "/icon_flag_india.png";
                return new Image(new FileInputStream(filePath));
            default:
                return null;
        }
    }

    public Image getDifficultyImage (String diff) throws FileNotFoundException {
        File file = new File("resources");
        String filePath = file.getAbsolutePath();

        switch (diff) {
            case "Lätt":
                filePath += "/icon_difficulty_easy.png";
                return new Image(new FileInputStream(filePath));
            case "Mellan":
                filePath += "/icon_difficulty_medium.png";
                return new Image(new FileInputStream(filePath));
            case "Svår":
                filePath += "/icon_difficulty_Hard.png";
                return new Image(new FileInputStream(filePath));
            default:
                return null;
        }
    }

    public Image getMainIngredientImage (String ingr) throws FileNotFoundException {
        File file = new File("resources");
        String filePath = file.getAbsolutePath();

        switch (ingr)
        {
            case "Biff":
                filePath += "/icon_main_beef.png";
                return new Image(new FileInputStream(filePath));
            case "Kyckling":
                filePath += "/icon_main_chicken.png";
                return new Image(new FileInputStream(filePath));
            case "Fisk":
                filePath += "/icon_main_fish.png";
                return new Image(new FileInputStream(filePath));
            case "Kött":
                filePath += "/icon_main_meat.png";
                return new Image(new FileInputStream(filePath));
            case "Vegetarisk":
                filePath += "/icon_main_veg.png";
                return new Image(new FileInputStream(filePath));
            default: return null;
        }
    }
}