package recipesearch;



import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.List;

public class backendController {
    String difficulty;
    String cuisine;
    String mainIngredient;
    int maxTime;
    int maxPrice;

    RecipeDatabase DB= RecipeDatabase.getSharedInstance();

    public List<Recipe> getRecipes(){
        return DB.search(new SearchFilter(difficulty,maxTime,cuisine,maxPrice,mainIngredient));
    }
    public List<Recipe> getRecipesName(String name){
        return DB.search(new SearchFilter(difficulty,maxTime,cuisine,maxPrice,mainIngredient,name));
    }
    public List<Recipe> getRecipesAmount(int k){
        return DB.search(new SearchFilter(difficulty,maxTime,cuisine,maxPrice,mainIngredient), k);
    }


    public void setCuisine(String cuisine){
        this.cuisine=cuisine;
    }
    public void setMainIngredient(String mainIngredient){
        this.mainIngredient = mainIngredient;
    }
    public void setDifficulty(String difficulty){
        this.difficulty=difficulty;
    }
    public void setMaxPrice(int maxPrice){
        this.maxPrice=maxPrice;
    }
    public void setMaxTime(int maxTime){
        this.maxTime=maxTime;
    }

}
