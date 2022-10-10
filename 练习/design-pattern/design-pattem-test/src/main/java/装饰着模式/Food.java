package 装饰着模式;

/**
 * 这是一个 实物，它会被其它的类装饰
 */
public class Food {

    private String foodName;

    public Food(){

    }

    public Food(String foodName) {
        this.foodName = foodName;
    }

    public String make(){
        return foodName;
    }
}
