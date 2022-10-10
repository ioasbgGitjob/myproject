package 装饰着模式;

/**
 * 解决继承关系复杂的问题
 * 应用：IO
 */
public class DecoratorMain {

    /**
     * 创建三明治的时候，备其它类装饰
     *
     */
    public static void main(String[] args) {
        Food food = new Decorators.Bread(new Decorators.Vegetable(new Decorators.Cream(new Food("三明治"))));
        System.out.println(food.make());

    }
}
