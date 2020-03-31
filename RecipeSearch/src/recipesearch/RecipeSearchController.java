
package recipesearch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;


public class RecipeSearchController implements Initializable {

    backendController backend=new backendController();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    public void updateSearch(){

    }
    public void clearSearch(){

    }
    private void updateRecipeList(){

        //recipeListFlowPane.getChildren().clear();

        for(Recipe r:backend.getRecipes()){
            new RecipeListItem(r)
            //recipeListFlowPane.getChildren().add();
        }



        //Denna metod ska göra tre saker:
        //
        //Tömma er flowpane från tidigare listitems (denna metod kommer att anropas varje gång användaren ändrar i kontrollerna). Detta görs genom metodanropet
        //     recipeListFlowPane.getChildren().clear();
        //   (beroende på vilket fx:id ni satt på er FlowPane i stegen ovanför)
        //
        //Anropar recipeBackendController för att hämta listan på alla recept (denna borde vara osorterad vid start)
        //För varje recept i listan skapa ett nytt recipeListitem och lägga till det med metodanropet:
        //     recipeListFlowPane.getChildren().add(…);
        //Se sedan till att denna metod anropas från metoden initialize i RecipeSearchController.
    }

}